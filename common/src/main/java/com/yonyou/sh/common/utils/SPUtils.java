
package com.yonyou.sh.common.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SPUtils {

    static final String PREFERENCES_NAME = "YonYouCarOwner";

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void keepContent(Context context, String tag, Object content) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        if (content instanceof Boolean) {
            Boolean new_content = (Boolean) content;
            editor.putBoolean(tag, new_content);

        } else if (content instanceof String) {
            String new_content = (String) content;
            editor.putString(tag, new_content);

        } else if (content instanceof Long) {
            Long new_content = (Long) content;
            editor.putLong(tag, new_content);

        } else if (content instanceof Integer) {
            int new_content = (Integer) content;
            editor.putInt(tag, new_content);

        } else if (content instanceof Float) {
            float new_content = (Float) content;
            editor.putFloat(tag, new_content);

        } else if (content instanceof Double) {
            editor.putLong(tag, Double.doubleToRawLongBits((Double) content));
        }
        editor.apply();
    }

    public static boolean readBoolean(Context context, String tag) {
        return readBoolean(context, tag, false);
    }

    public static boolean readBoolean(Context context, String tag, boolean defaultValue) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getBoolean(tag, defaultValue);
    }

    public static String readString(Context context, String tag) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getString(tag, "");
    }

    public static long readLong(Context context, String tag, long defaultVal) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getLong(tag, defaultVal);
    }

    public static double readDouble(Context context, String tag, long defaultVal) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return Double.longBitsToDouble(pref.getLong(tag, Double.doubleToLongBits(defaultVal)));
    }

    public static int readInt(Context context, String tag, int defaultVal) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        return pref.getInt(tag, defaultVal);
    }

    public static void clear(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

    public static void removeByKey(Context context, String key) {
        if (TextUtils.isEmpty(key)) return;
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.remove(key);
        editor.apply();
    }

}
