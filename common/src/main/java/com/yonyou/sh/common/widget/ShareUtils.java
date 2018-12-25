package com.yonyou.sh.common.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.open.utils.ThreadManager;
import com.tencent.tauth.Tencent;
import com.yonyou.owner.wxapi.ShareListener;
import com.yonyou.sh.common.R;
import com.yonyou.sh.common.constant.Constant;
import com.yonyou.sh.common.utils.ImageUtils;
import com.yonyou.sh.common.utils.ToastUtils;
import com.yonyou.sh.common.utils.UIUtils;
/**
 * 作者：邵帅
 * 时间：2018/12/24 2:26 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：分享
 */
public class ShareUtils {

    public static Tencent mTencent;
    private static WbShareHandler shareHandler;
    public static final int WX_TIME_LINE = SendMessageToWX.Req.WXSceneTimeline;//微信朋友圈
    public static final int WX_SESSION = SendMessageToWX.Req.WXSceneSession;//微信好友


    private static ShareUtils instance;


    public static ShareUtils getInstance() {
        if (instance == null) {
            synchronized (ShareUtils.class) {
                if (instance == null)
                    instance = new ShareUtils();
            }
        }

        return instance;
    }

    private static final int THUMB_SIZE = 150;
    //QQ分享
    public void shareQQ(Activity activity, String url, String title, String description, String imgUrl) {
        //qq分享
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, activity);
        if (UIUtils.isInstall(activity,Constant.QQ_APP_URL)) {
            final Bundle param = new Bundle();
            param.putString(QQShare.SHARE_TO_QQ_APP_NAME, "news");//app的名字
            param.putString(QQShare.SHARE_TO_QQ_TITLE, title);//必填
            param.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);
            param.putString(QQShare.SHARE_TO_QQ_TARGET_URL, url);//必填
            param.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);//必填
            doShareToQQ(activity, param);
        } else {
            ToastUtils.showShort(activity, "请检查是否安装最新版QQ！");
        }
    }

    //QQ空间分享
    public void shareQQCircle(Activity activity, String url, String title, String description, String imgUrl) {
        //qq分享
        mTencent = Tencent.createInstance(Constant.QQ_APP_ID, activity);
        if (UIUtils.isInstall(activity,Constant.QZON_APP_URL)) {
            final Bundle params = new Bundle();
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);//（图文）
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);//必填
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, description);//选填
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, url);//必填
            params.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrl);
            mTencent.shareToQzone(activity, params, new ShareListener());
        } else {
            ToastUtils.showShort(activity,"请检查是否安装最新版QQ空间！");
        }
    }


    //微博分享
    public static void shareWeiBo(final Activity activity, String url, String title) {
        WbSdk.install(activity, new AuthInfo(activity, Constant.SINA_APP_KEY, Constant.REDIRECT_URL, Constant.SCOPE));
        shareHandler = new WbShareHandler(activity);
        shareHandler.registerApp();
        WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
        TextObject textObject = new TextObject();
        textObject.text = title + url;
        weiboMultiMessage.textObject = textObject;
        shareHandler.shareMessage(weiboMultiMessage, false);
    }

    /**
     * 微信好友，朋友圈分享
     *
     */
    public void shareWX(Context context, final String url, final String title, final String description, String imgUrl, final int shareChannel) {
        if (!TextUtils.isEmpty(url)) {
            final IWXAPI api = WXAPIFactory.createWXAPI(context, Constant.WEI_APP_ID);
            getBitmap(context, imgUrl, new onGetBitmapListener() {
                @Override
                public void onSucceed(Bitmap bitmap) {
                    WXWebpageObject wxWebpageObject = new WXWebpageObject();
                    wxWebpageObject.webpageUrl = url;
                    WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
                    wxMediaMessage.title = title;
                    wxMediaMessage.description = description;
                    Bitmap thumb = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
                    wxMediaMessage.thumbData = ImageUtils.bitmap2Bytes(thumb, 32 * 1024);//微信分享的图片大小不能超过32kb,否则无法调起微信

                    SendMessageToWX.Req req = new SendMessageToWX.Req();
                    req.transaction = String.valueOf(System.currentTimeMillis());
                    req.message = wxMediaMessage;
                    req.scene = shareChannel;
                    api.sendReq(req);
                }
            });
        }else {
            ToastUtils.showShort(context, "分享地址有误");
        }
    }

    public static void doShareToQQ(final Activity activity, final Bundle params) {
        // QQ分享要在主线程做
        ThreadManager.getMainHandler().post(new Runnable() {

            @Override
            public void run() {
                if (null != mTencent) {
                    mTencent.shareToQQ(activity, params, new ShareListener());
                }
            }
        });
    }

    public interface onGetBitmapListener {
        void onSucceed(Bitmap bitmap);

    }

    private void getBitmap(Context context, String imgUrl, final onGetBitmapListener listener) {
        if (TextUtils.isEmpty(imgUrl)) {
            listener.onSucceed(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
        } else {
            Glide.with(context.getApplicationContext())
                    .load(imgUrl)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .dontAnimate()
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable drawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            int w = drawable.getIntrinsicWidth();
                            int h = drawable.getIntrinsicHeight();
                            Bitmap.Config config =
                                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                            : Bitmap.Config.RGB_565;
                            Bitmap bitmap = Bitmap.createBitmap(w, h, config);
                            Canvas canvas = new Canvas(bitmap);
                            drawable.setBounds(0, 0, w, h);
                            drawable.draw(canvas);
                            listener.onSucceed(bitmap);
                        }
                    });
        }
    }
}
