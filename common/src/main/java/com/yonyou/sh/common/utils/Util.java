package com.yonyou.sh.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：shaoshuai
 * 时间：2017/9/21:下午3:22
 * 邮箱：15601683595@163.com
 * 说明：
 */
public class Util {

    private static long lastClickTime;

    //隐藏软件盘
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 防止连续点击
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 要更加准确的匹配手机号码只匹配11位数字是不够的，比如说就没有以144开始的号码段，
     * 故先要整清楚现在已经开放了多少个号码段，国家号码段分配如下：
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186、176 　　电信：133、153、180、189、177（1349卫通）
     */
    public static boolean isPhone(String number) {
        String num = "[1][0123456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            // matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    /**
     * 判断email格式是否正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }


    /**
     * 比较两个日期的大小，日期格式为yyyy-MM-ddc
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() > dt2.getTime()) {
            isBigger = true;
        } else if (dt1.getTime() < dt2.getTime()) {
            isBigger = false;
        }
        return isBigger;
    }

    /**
     * 返回当前程序版本号
     */
    public static String getAppVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //检查输入手机号码是否正确
    public static boolean checkPhone(Context context, TextView tv) {
        if (tv.getText().length() == 0) {
            ToastUtils.showShort(context, "请输入手机号码！");
            return false;
        }
        if (tv.getText().length() != 11) {
            ToastUtils.showShort(context, "请填写正确的手机号码");
            return false;
        }
        return true;
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void closeKeyBoard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        if (context != null && ((Activity) context).getCurrentFocus() != null
                && ((Activity) context).getCurrentFocus().getWindowToken() != null) {
            imm.hideSoftInputFromWindow(((Activity) context).getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 弹出(关闭)软键盘(如果已打开,则关闭;如果关闭,则打开)
     *
     * @param context 上下文环境
     */
    public static void openKeyBoard(final Context context) {
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
    }


    /**
     * 获取当前应用名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String appName = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            //CharSequence这两者效果是一样的.
            appName = packageManager.getApplicationLabel(applicationInfo).toString();
            appName = (String) packageManager.getApplicationLabel(applicationInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return appName;
    }
}
