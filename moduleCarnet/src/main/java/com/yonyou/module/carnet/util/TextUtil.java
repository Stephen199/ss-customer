package com.yonyou.module.carnet.util;

import android.content.Context;
import android.content.res.Resources;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作字符串的工具类。
 *
 * @author wuzhen
 * @version Version 1.0, 2014-11-06
 */
public final class TextUtil {

    /**
     * 比较2个字符串是否相等。
     *
     * @return 若 str1、str2 都为 null 则返回true，否则调用 {@link String#equals(Object)} 方法进行判断，该方法处理了空指针异常。
     */
    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return (str2 == null);
        }
        return str1.equals(str2);
    }

    /**
     * 封装 {@link String#trim()} 方法，避免空指针异常。
     */
    public static String trim(String value) {
        if (value == null) {
            return null;
        }
        return value.trim();
    }

    /**
     * 判断给定的字符串是否为空（忽略字符串开头、结尾的空格）。
     */
    public static boolean isEmpty(CharSequence value) {
        return value == null || value.toString().trim().length() == 0;
    }

    public static boolean isEmpty(String value) {
        return value == null || value.toString().trim().length() == 0;
    }

    /**
     * 封装 {@link Resources#getString(int)} 方法，处理异常。
     *
     * @return 若发生异常返回 ""
     */
    public static String getString(Context context, int resId) {
        if (context == null) {
            return "";
        }
        try {
            return context.getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 封装 {@link Resources#getText(int)} 方法，处理异常。
     *
     * @return 若发生异常返回 ""
     */
    public static CharSequence getText(Context context, int resId) {
        if (context == null) {
            return "";
        }
        try {
            return context.getText(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 封装 {@link Resources#getTextArray(int)} 方法，处理异常。
     *
     * @return 若异常返回空数组
     */
    public static CharSequence[] getTextArray(Context context, int resId) {
        if (context == null) {
            return new CharSequence[0];
        }
        try {
            return context.getResources().getTextArray(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CharSequence[0];
    }

    /**
     * 封装 {@link String#format(String, Object...)} 方法，格式化字符串。 <br> 占位符格式为：<b>%[index]$[type]</b>。
     *
     * @param resId 字符串资源的id。
     * @return 若指定的资源不存在则返回 ""
     */
    public static String formatStrings(Context context, int resId, Object... args) {
        if (context == null) {
            return "";
        }
        return formatStrings(getString(context, resId), args);
    }

    /**
     * 封装 {@link String#format(String, Object...)} 方法，格式化字符串。<br> 占位符格式为：<b>%[index]$[type]</b>。
     */
    public static String formatStrings(String format, Object... args) {
        return String.format(format, args);
    }

    /**
     * 封装 {@link String#format(String, Object...)} 方法，格式化字符串。<br> 占位符格式为：<b>%[index]$[type]</b>。<br>
     *
     * @param arg 如果该值为 null 则自动替换为 ""
     */
    public static String formatString(String format, Object arg) {
        if (arg == null) {
            return String.format(format, "");
        }
        return String.format(format, arg);
    }

    /**
     * 封装 {@link String#format(String, Object...)} 方法，格式化字符串。 <br> 占位符格式为：<b>%[index]$[type]</b>。
     *
     * @param arg 如果该值为 null 则自动替换为 ""
     */
    public static String formatString(Context context, int resId, Object arg) {
        if (context == null) {
            return "";
        }
        String format = getString(context, resId);
        if (arg == null) {
            return String.format(format, "");
        }
        return String.format(format, arg);
    }

    /**
     * 判断给定的字符串是否为完整的 URL 地址<b>（形如：<i>http://www.xxx.com</i> 格式）</b>。
     */
    public static boolean isFullUrl(String url) {
        if (url == null) {
            return false;
        }
        final String urlLowerCase = url.toLowerCase(Locale.getDefault());
        if (urlLowerCase.startsWith("http://")) {
            return true;
        }
        return urlLowerCase.startsWith("https://");
    }

    /**
     * 获取给定 URL 的 host。
     */
    public static String getHostFromUrl(String url) {
        if (url == null) {
            return "";
        }
        int indexEnd = -1;
        final String urlLowerCase = url.toLowerCase(Locale.getDefault());
        if (urlLowerCase.startsWith("http://")) {
            indexEnd = url.indexOf("/", url.indexOf("http://") + 8);
        }
        if (urlLowerCase.startsWith("https://")) {
            indexEnd = url.indexOf("/", url.indexOf("https://") + 9);
        }
        if (indexEnd == -1) {
            return url;
        }
        return url.substring(0, indexEnd);
    }

    /**
     * 获取给定的 URL 的路径部分。
     */
    public static String getPathFromUrl(String url) {
        if (url == null) {
            return "";
        }
        int indexStart = -1;
        final String urlLowerCase = url.toLowerCase(Locale.getDefault());
        if (urlLowerCase.startsWith("http://")) {
            indexStart = url.indexOf("/", url.indexOf("http://") + 8);
        }
        if (urlLowerCase.startsWith("https://")) {
            indexStart = url.indexOf("/", url.indexOf("https://") + 9);
        }
        if (indexStart == -1) {
            return url;
        }
        return url.substring(indexStart);
    }

    /**
     * 截取字符串，根据开始和结束的字符串截取中间部分（不包括开始和结束的字符串）。<br>
     * <p/>
     * 如果 finish 为空则截取到源字符串的末尾。
     */
    public static String getSubString(String src, String begin, String finish) {
        int start = src.indexOf(begin);

        if (finish == null || "".equals(finish)) {
            if (start >= 0) {
                return src.substring(start + begin.length());
            }
        }

        int end = src.lastIndexOf(finish);
        if (end <= 0) {
            end = src.length();
        }

        if (start >= 0 && (start + begin.length()) < end) {
            return src.substring((start + begin.length()), end);
        }
        return "";
    }

    /**
     * 使用 UTF-8 编码对给定的字符串做 URLDecode。<br>
     */
    public static String decodeUTF8(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 对给定的字符串做 URLEncode，并把 encode 结果中的 "+" 全部替换成 "%20"。
     */
    public static String encodeUTF8(String value) {
        String result = "";
        try {
            result = URLEncoder.encode(value, "UTF-8");
            result = result.replaceAll("\\+", "%20");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 判断是否是身份证
     *
     * @param IDCardNo
     * @return
     */
    public static boolean isIDCardNo(String IDCardNo) {
        // 15或18位
        Pattern pattern = Pattern.compile("\\d{15}|\\d{18}");
        Matcher matcher = pattern.matcher(IDCardNo);
        return matcher.matches();
    }

    /**
     * 判断昵称是否有表情符号
     *
     * @param nickName
     * @return
     */
    public static boolean isNickCorrect(String nickName) {
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa50-9A-Za-z]{1,30}");
        Matcher matcher = pattern.matcher(nickName);
        return matcher.matches();
    }

    /**
     * 把给定字符串的第一个字母变成大写。
     */
    public String toFirstLetterUpperCase(String value) {
        if (value == null || value.length() == 0) {
            return "";
        }
        if (value.length() == 1) {
            return value.toUpperCase(Locale.getDefault());
        }
        String firstLetter = value.substring(0, 1).toUpperCase(Locale.getDefault());
        return firstLetter + value.substring(1, value.length());
    }


}
