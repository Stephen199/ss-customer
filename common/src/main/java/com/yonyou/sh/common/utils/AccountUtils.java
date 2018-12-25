package com.yonyou.sh.common.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.yonyou.sh.common.bean.AccountInfo;
import com.yonyou.sh.common.constant.Constant;
import com.yonyou.sh.common.constant.RouterPath;
import com.yonyou.sh.common.constant.SPKeys;

/**
 * 作者：邵帅
 * 时间：2018/12/21 4:01 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class AccountUtils {

    public static void updateAccountInfo(AccountInfo accountInfo) {
        SPUtils.keepContent(ContextHelper.getContext(), SPKeys.SP_KEY_USER_TOKEN, accountInfo.getJwt());
        SPUtils.keepContent(ContextHelper.getContext(), SPKeys.SP_KEY_USER_ID, accountInfo.getRData().getUserId());
    }

    public static void clearAccountInfo() {
        SPUtils.removeByKey(ContextHelper.getContext(), SPKeys.SP_KEY_USER_TOKEN);
        SPUtils.removeByKey(ContextHelper.getContext(), SPKeys.SP_KEY_USER_ID);
        SPUtils.removeByKey(ContextHelper.getContext(), SPKeys.SP_KEY_USER_AVATAR_URL);
    }

    /**
     * @return 判断用户是否登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }

    public static long getUserId() {
        return SPUtils.readLong(ContextHelper.getContext(), SPKeys.SP_KEY_USER_ID, 0);
    }

    public static String getVin() {
        return SPUtils.readString(ContextHelper.getContext(), SPKeys.SP_KEY_USER_VIN);
    }

    public static String getToken() {
        return SPUtils.readString(ContextHelper.getContext(), SPKeys.SP_KEY_USER_TOKEN);
    }

    /**
     * @param context
     * 跳转登录页面
     */
    public static void goLogin(Context context){
        if (context instanceof Activity) {
            ARouter.getInstance().build(RouterPath.MAIN_ACTIVITY_LOGIN).navigation((Activity) context, Constant.REQUEST_CODE_LOGIN);
        } else if (context instanceof Application) {
            ARouter.getInstance().build(RouterPath.MAIN_ACTIVITY_LOGIN).navigation(context);
        }
    }

    /**
     * @param fragment
     * 跳转登录页面
     */
    public static void goLogin(Fragment fragment){
        fragment.startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("carowner://main/login")), Constant.REQUEST_CODE_LOGIN);
    }
}
