package com.yonyou.sh.common.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.yonyou.sh.common.R;

import org.json.JSONException;
import org.json.JSONObject;


public class JsonUtils {

    /**
     * @param result 返回值
     * @param key    key值
     * @return json是否存在key
     */
    public static boolean isHasKey(String result, String key) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param result 返回值json
     * @param key    key值
     * @return 获取jsonStr的某个字段  只限第一层
     */
    public static String getJsonStr(String result, String key) {
        String jsonStr = "";
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                jsonStr = jsonObject.getString(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static int getJsonInt(String result, String key) {
        int jsonInt = 0;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has(key)) {
                jsonInt = jsonObject.getInt(key);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonInt;
    }

    /**
     * @param str str
     * @param key key
     * @return JSONObject
     */
    public static JSONObject getJsonObject(String str, String key) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            return jsonObject.optJSONObject(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得json的  返回错误代码
     *
     * @param result json字符串
     * @return err信息
     */
    public static String getErrMsg(String result) {
        String errMsg = ContextHelper.getContext().getString(R.string.un_know_error);
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("errMsg")) {
                errMsg = jsonObject.getString("errMsg");
                String resultCode = jsonObject.getString("resultCode");
                if ("40104".equals(resultCode)) {
                    errMsg = ContextHelper.getContext().getString(R.string.please_go_login);
                } else if ("40105".equals(resultCode)) {
                    errMsg = ContextHelper.getContext().getString(R.string.login_dated);
                } else if ("900".equals(resultCode)) {
                    errMsg = ContextHelper.getContext().getString(R.string.system_error);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return errMsg;
    }

    public static int getResultCode(String result){
        int code = 0;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("resultCode")) {
                code = jsonObject.getInt("resultCode");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void showErrMsg(String result) {
        final String errMsg = getErrMsg(result);
        if (!TextUtils.isEmpty(errMsg)) {
            UIUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ContextHelper.getContext(), errMsg, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * @param result json
     * @return 判断 请求数据是否成功
     */
    public static boolean isSuccess(String result) {
        boolean isSucc = false;
        try {
            JSONObject jsonObject = new JSONObject(result);
            if (jsonObject.has("resultCode")) {
                String code = jsonObject.getString("resultCode");
                isSucc = ("200".equals(code));
                if (!isSucc) {
                    if ("40105".equals(code)){
                        AccountUtils.clearAccountInfo();
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isSucc;
    }
}
