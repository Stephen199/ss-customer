package com.yonyou.sh.common.widget;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yonyou.sh.common.constant.Constant;
/**
 * 作者：邵帅
 * 时间：2018/5/14 下午2:44
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class ShareHelper{

    public static IWXAPI wxApi;

    public static void init(Context context) {
        wxApi = WXAPIFactory.createWXAPI(context, Constant.WEI_APP_ID, false);
        wxApi.registerApp(Constant.WEI_APP_ID);
    }

    /**f
     * 微信好友，朋友圈分享
     *
     */
    public static void shareViaWX(String url, String title, String description, int judge) {
        WXWebpageObject wxWebpageObject = new WXWebpageObject();
        wxWebpageObject.webpageUrl = url;
        WXMediaMessage wxMediaMessage = new WXMediaMessage(wxWebpageObject);
        wxMediaMessage.title = title;
        wxMediaMessage.description = description;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = wxMediaMessage;
        req.scene = judge;
        wxApi.sendReq(req);
    }
    /**
     * 检查分享的字段
     *
     * @param cover
     * @param target
     * @param title
     * @param desc
     * @return
     */
    private static boolean checkShareFiled(String cover, String target, String title, String desc) {
        if (TextUtils.isEmpty(cover)) {
//            ToastUtil.showToastSafe("预览图不能为空！");
            return true;
        }
        if (TextUtils.isEmpty(target)) {
//            ToastUtil.showToastSafe("链接地址不能为空！");
            return true;
        }
        if (TextUtils.isEmpty(title)) {
//            ToastUtil.showToastSafe("分享标题不能为空");
            return true;
        }
        if (TextUtils.isEmpty(desc)) {
//            ToastUtil.showToastSafe("分享描述不能为空");
            return true;
        }
        return false;
    }

    /**
     * 检查微信App
     *
     * @return
     */
    private static boolean checkWeixinApp() {
        if (!wxApi.isWXAppInstalled()) {
//            ToastUtil.showToastSafe("没有安装微信客户端");
            return true;
        }
        return false;
    }

}
