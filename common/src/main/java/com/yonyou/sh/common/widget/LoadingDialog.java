package com.yonyou.sh.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.yonyou.sh.common.R;

/**
 * 作者：邵帅
 * 时间：2018/12/21 2:58 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class LoadingDialog extends Dialog {

    private TextView tvLoadText;

    public LoadingDialog(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView(context);
    }

    protected LoadingDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView(context);
    }

    private void initView(Context context) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置无标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawable(new ColorDrawable());//设置无背景边框
        setCancelable(false);
        setContentView(R.layout.dialog_loading);
        tvLoadText = (TextView) findViewById(R.id.tv_load_text);
    }

    /**
     * 设置文本
     *
     * @param text
     */
    public void setLoadText(String text) {
        if (TextUtils.isEmpty(text))
            return;
        tvLoadText.setText(text);
    }
}
