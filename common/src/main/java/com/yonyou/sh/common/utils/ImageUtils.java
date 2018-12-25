package com.yonyou.sh.common.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:30 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class ImageUtils {
    /**
     * Bitmap转换成byte[]并且进行压缩,压缩到不大于maxkb
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 5) {
            output.reset(); //清空output
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, output);//这里压缩options%，把压缩后的数据存放到output中
            options -= 5;
        }
        return output.toByteArray();
    }
}
