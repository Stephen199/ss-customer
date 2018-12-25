package com.yonyou.sh.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;
import com.yonyou.sh.common.widget.LoadingDialog;

/**
 * 作者：邵帅
 * 时间：2018/12/21 2:48 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public abstract class BasePresenterActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView{

    protected P presenter;
    private LoadingDialog loadingDialog;

    protected abstract P getPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
    }

    @Override
    public void showProgress() {
        showProgress("");
    }

    @Override
    public void showProgress(String text) {
        showProgress(text, false);
    }

    /**
     * 显示loading
     * @param text
     * @param cancle 是否可按返回键退出
     */
    @Override
    public void showProgress(String text, boolean cancle) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
        if (loadingDialog != null) {
            loadingDialog.setCancelable(cancle);
            loadingDialog.setLoadText(text);
        }

        if (!loadingDialog.isShowing() && !isFinishing()) {
            loadingDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if (!isFinishing() && loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog.cancel();
        }
    }

    @Override
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}
