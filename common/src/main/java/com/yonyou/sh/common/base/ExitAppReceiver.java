package com.yonyou.sh.common.base;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

/**
 * 作者：shaoshuai
 * 时间：2018/11/8 下午3:30
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：定义一个广播
 */
public class ExitAppReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        //接收发送过来的广播内容
        if (context != null) {

            if (context instanceof Activity) {

                ((Activity) context).finish();
            } else if (context instanceof FragmentActivity) {

                ((FragmentActivity) context).finish();
            } else if (context instanceof Service) {

                ((Service) context).stopSelf();
            }
        }
    }
}
