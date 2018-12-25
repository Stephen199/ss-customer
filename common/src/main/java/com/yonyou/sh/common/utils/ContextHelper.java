package com.yonyou.sh.common.utils;

import android.content.Context;
/**
 * 作者：邵帅
 * 时间：2018/12/21 4:01 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class ContextHelper {
    private static Context context;

    public static void init(Context context){
        ContextHelper.context = context;
    }

    public static Context getContext() {
        return context;
    }
}
