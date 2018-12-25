package com.yonyou.module.carnet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 作者：shaoshuai
 * 时间：2018/11/16 11:48 AM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class RegexUtil {
    /**
     * 大陆手机号码11位数，匹配格式：前三位固定格式+后8位任意数
     * 此方法中前三位格式有：
     * 13+任意数
     * 15+除4的任意数
     * 18+除1和4的任意数
     * 17+除9的任意数
     * 147
     * 166
     */

    public static boolean isSixNumber(String str) throws PatternSyntaxException {
        String regExp = "^\\d{6}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }


    public static boolean isChinaPhoneLegal(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147)|(166))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static boolean isHttpUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式

        Pattern pat = Pattern.compile(regex.trim());//比对
        Matcher mat = pat.matcher(urls.trim());
        isurl = mat.matches();//判断是否匹配
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }

    public static boolean isWechat(String url) {
        String regex = "^(https://mp.weixin.qq.com).*";
        Pattern pat = Pattern.compile(regex);//比对
        Matcher mat = pat.matcher(url.trim());
        return mat.matches();//判断是否匹配
    }

    public static boolean isRightPWD(String pwd) {
        boolean hasNum = pwd.matches(".*\\d+.*");
        boolean hasEng = pwd.matches(".*[a-zA-Z]+.*");
        boolean right = pwd.length() >= 8;

        return hasNum && hasEng && right;//判断是否匹配
    }

    public static boolean isHomeUrl(String url) {
        return url.endsWith("/discover/index") || url.endsWith("/lovecar") || url.endsWith("/info") || url.endsWith("/my")
                || url.endsWith("/friend") || url.endsWith("/surprise")
                || url.endsWith("/") || url.endsWith("/discover/recommend")
                || url.endsWith("/discover/now") || url.endsWith("/disc")
                || url.endsWith("/discover/allActivity") || url.endsWith("/discover/information");
    }

    public static String changeEnter(String s) {
        s = s.replaceAll("\n", "\\\\n");
        return s;
    }
}
