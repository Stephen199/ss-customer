package com.yonyou.sh.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.yonyou.sh.common.R;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:04 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class LZJShareDialog extends Dialog {
    public LZJShareDialog(@NonNull Context context, final OnShareDialogClickListener listener) {
        super(context, R.style.CommonDialogStyle);
        setContentView(R.layout.dialog_share);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

        findViewById(R.id.tv_we_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onWeChatClick();
                dismiss();
            }
        });

        findViewById(R.id.tv_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCircleClick();
                dismiss();
            }
        });
    }

    public interface OnShareDialogClickListener{
        void onWeChatClick();

        void onCircleClick();
    }
}
