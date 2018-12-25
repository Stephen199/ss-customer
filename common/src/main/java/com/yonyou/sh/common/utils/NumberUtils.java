package com.yonyou.sh.common.utils;


import java.text.DecimalFormat;
import java.util.Random;

public class NumberUtils {

    public static int parseInt(String strNum){
        int num = 0;
        try {
            num = Integer.parseInt(strNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return num;
    }

    /**
     * 生成指定位数的随机数
     * @param length
     * @return
     */
    public static String getRandomNum(int length){
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            val += String.valueOf(random.nextInt(10));
        }
        return val;
    }

    public static String formatNum(int num){
        if (num < 10000){
            return num + "";
        }
        return new DecimalFormat("#.0").format((double)num / 10000) + "w";
    }
}
