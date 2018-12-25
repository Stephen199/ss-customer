package com.yonyou.sh.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.Toast;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:43 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public abstract class BasePresenterFragment<P extends IBasePresenter> extends Fragment implements IBaseView{
    protected P presenter;
    private BasePresenterActivity activity;
    private Context context;

    protected abstract P getPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof BasePresenterActivity) {
            activity = (BasePresenterActivity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = getPresenter();
    }

    @Override
    public void showProgress() {
        showProgress("");
    }

    @Override
    public void showProgress(String text) {
        if (activity != null && isAdded()) {
            activity.showProgress(text);
        }
    }

    @Override
    public void showProgress(String text, boolean cancle) {
        if (activity != null && isAdded()) {
            activity.showProgress(text, cancle);
        }
    }

    @Override
    public void dismissProgress() {
        if (activity != null && isAdded()) {
            activity.dismissProgress();
        }
    }

    @Override
    public void showToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
            presenter = null;
        }
    }
}
