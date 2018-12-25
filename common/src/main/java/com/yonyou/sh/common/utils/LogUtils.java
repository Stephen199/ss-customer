package com.yonyou.sh.common.utils;

import android.util.Log;

import com.yonyou.sh.common.BuildConfig;

public class LogUtils {
    public static void v(String tag, String str) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, str == null ? "null" : str);
        }
    }

    public static void d(String tag, String str) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, str == null ? "null" : str);
        }
    }

    public static void i(String tag, String str) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, str == null ? "null" : str);
        }
    }

    public static void w(String tag, String str) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, str == null ? "null" : str);
        }
    }

    public static void e(String tag, String str) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, str == null ? "null" : str);
        }
    }
}
