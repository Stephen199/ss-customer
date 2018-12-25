package com.yonyou.sh.common.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.Tencent;
import com.yonyou.owner.wxapi.ShareListener;
import com.yonyou.sh.common.R;
import com.yonyou.sh.common.constant.Constant;
import com.yonyou.sh.common.utils.UIUtils;

/**
 * 作者：邵帅
 * 时间：2018/5/14 下午2:39
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class ShareDialog extends Dialog implements WbShareCallback {
    private LinearLayout weChatTimeLine;
    private LinearLayout weChat;
    private LinearLayout qq;
    private LinearLayout qqQ;
    private LinearLayout sina;
    private LinearLayout llCancel;
    private LinearLayout llOne;
    private String url = "";//链接地址
    private String title = "";//标题
    private String description = "";//描述
    public static Tencent mTencent;
    public static Context context;
    private WbShareHandler shareHandler;
    boolean isWeiXin, isQQ, isWeiBo;
    private String imgUrl;


    public ShareDialog(Context context) {
        super(context, R.style.CommonDialogStyle);
        this.context = context;
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        window.getDecorView().setPadding(0, 0, 0, 0);
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_share);
        init();
        ShareHelper.init(getContext());
        if (mTencent == null) {
            mTencent = Tencent.createInstance(Constant.QQ_APP_ID, context);
        }
        WbSdk.install(context, new AuthInfo(context, Constant.SINA_APP_KEY, Constant.REDIRECT_URL, Constant.SCOPE));
        shareHandler = new WbShareHandler((Activity) context);
        shareHandler.registerApp();
        initStatus();
        initView();
        setCanceledOnTouchOutside(true);
    }

    private void init() {
        weChatTimeLine = findViewById(R.id.we_chat_time_line);
        weChat = findViewById(R.id.we_chat);
        qq = findViewById(R.id.qq);
        qqQ = findViewById(R.id.qq_q);
        sina = findViewById(R.id.sina);
        llCancel = findViewById(R.id.ll_cancel);
        llOne = findViewById(R.id.ll_one);
    }

    private void initStatus() {
        isWeiXin = UIUtils.isInstall(context, Constant.WEI_APP_URL);
        isQQ = UIUtils.isInstall(context, Constant.QQ_APP_URL);
        isWeiBo = UIUtils.isInstall(context, Constant.SINA_APP_URL);
        if (isWeiXin == true && isQQ == false && isWeiBo == false) {//只安装微信
            llOne.setVisibility(View.VISIBLE);
            weChatTimeLine.setVisibility(View.VISIBLE);
            weChat.setVisibility(View.VISIBLE);
            qq.setVisibility(View.GONE);
            qqQ.setVisibility(View.GONE);
            sina.setVisibility(View.GONE);
        } else if (isWeiXin == true && isQQ == true && isWeiBo == false) {//只安装微信,QQ
            llOne.setVisibility(View.VISIBLE);
            weChatTimeLine.setVisibility(View.VISIBLE);
            weChat.setVisibility(View.VISIBLE);
            qq.setVisibility(View.VISIBLE);
            qqQ.setVisibility(View.VISIBLE);
            sina.setVisibility(View.GONE);
        } else if (isWeiXin == true && isQQ == false && isWeiBo == true) {//只安装微信,weibo
            llOne.setVisibility(View.VISIBLE);
            weChatTimeLine.setVisibility(View.VISIBLE);
            weChat.setVisibility(View.VISIBLE);
            qq.setVisibility(View.GONE);
            qqQ.setVisibility(View.GONE);
            sina.setVisibility(View.VISIBLE);
        } else if (isWeiXin == true && isQQ == true && isWeiBo == true) {//只安装微信,QQ,weibo
            llOne.setVisibility(View.VISIBLE);
            weChatTimeLine.setVisibility(View.VISIBLE);
            weChat.setVisibility(View.VISIBLE);
            qq.setVisibility(View.VISIBLE);
            qqQ.setVisibility(View.VISIBLE);
            sina.setVisibility(View.VISIBLE);
        } else if (isWeiXin == false && isQQ == true && isWeiBo == true) {//只安装QQ,wei
            llOne.setVisibility(View.VISIBLE);
            weChatTimeLine.setVisibility(View.GONE);
            weChat.setVisibility(View.GONE);
            qq.setVisibility(View.VISIBLE);
            qqQ.setVisibility(View.VISIBLE);
            sina.setVisibility(View.VISIBLE);
        } else if (isWeiXin == false && isQQ == true && isWeiBo == false) {//只安装QQ
            llOne.setVisibility(View.VISIBLE);
            weChatTimeLine.setVisibility(View.GONE);
            weChat.setVisibility(View.GONE);
            qq.setVisibility(View.VISIBLE);
            qqQ.setVisibility(View.VISIBLE);
            sina.setVisibility(View.GONE);
        } else if (isWeiXin == false && isQQ == false && isWeiBo == true) {//只安装weibo
            llOne.setVisibility(View.VISIBLE);
            weChatTimeLine.setVisibility(View.GONE);
            weChat.setVisibility(View.GONE);
            qq.setVisibility(View.GONE);
            qqQ.setVisibility(View.GONE);
            sina.setVisibility(View.VISIBLE);
        } else if (isWeiXin == false && isQQ == false && isWeiBo == false) {//什么都没装
            llOne.setVisibility(View.GONE);
        }
    }

    private void initView() {
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        weChatTimeLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.getInstance().shareWX(context, url, title, description, imgUrl, ShareUtils.WX_TIME_LINE);
                dismiss();
            }
        });
        weChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.getInstance().shareWX(context, url, title, description, imgUrl, ShareUtils.WX_SESSION);
                dismiss();
            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.getInstance().shareQQ((Activity) context, url, title, description, imgUrl);
                dismiss();
            }
        });
        qqQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShareUtils.getInstance().shareQQCircle((Activity) context, url, title, description, imgUrl);
                dismiss();
            }
        });
        sina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
                TextObject textObject = new TextObject();
                textObject.text = title + url;
                weiboMultiMessage.textObject = textObject;
                shareHandler.shareMessage(weiboMultiMessage, false);
                dismiss();
            }
        });
    }

    public void setData(String url, String title, String description, String imgUrl) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    private void doShareToQQ(final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ((Activity) context, params, new ShareListener());
                }
            }
        });
    }

    @Override
    public void onWbShareSuccess() {

    }

    @Override
    public void onWbShareCancel() {

    }

    @Override
    public void onWbShareFail() {

    }
}
