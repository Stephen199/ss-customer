package com.yonyou.sh.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/**
 * 作者：邵帅
 * 时间：2018/12/21 4:01 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class NetworkUtils {

    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    private static NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) ContextHelper.getContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return null;
        return cm.getActiveNetworkInfo();
    }
}
