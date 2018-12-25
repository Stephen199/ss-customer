package com.yonyou.sh.customerss.base;

import android.content.Context;
import com.alibaba.android.arouter.launcher.ARouter;
import com.yonyou.sh.common.base.BaseApplication;
import com.yonyou.sh.customerss.BuildConfig;

/**
 * 作者：邵帅
 * 时间：2018/12/21 5:31 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class MyApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
        //极光推送
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
    }
}
