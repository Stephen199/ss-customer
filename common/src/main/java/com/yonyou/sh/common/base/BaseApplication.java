package com.yonyou.sh.common.base;

import android.app.Application;
import com.yonyou.sh.common.http.HttpHelper;
import com.yonyou.sh.common.http.OkHttpProcessor;
import com.yonyou.sh.common.utils.ContextHelper;

/**
 * 作者：邵帅
 * 时间：2018/12/21 4:34 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ContextHelper.init(getApplicationContext());
        HttpHelper.init(new OkHttpProcessor());
    }
}
