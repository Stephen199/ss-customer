package com.yonyou.sh.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.List;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:28 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class SystemUtils {
    /**
     * 判断应用是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isAppForground(Context context) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            ActivityManager.RunningTaskInfo task = tasks.get(0);
            if (task != null) {
                return TextUtils.equals(task.topActivity.getPackageName(), context.getPackageName());
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * 判断当前应用是否是debug状态
     */
    public static boolean isDebug(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    //判断有没有安装app
    public static boolean isInstall(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
