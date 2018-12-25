package com.yonyou.sh.common.http;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.google.gson.Gson;
import com.yonyou.sh.common.R;
import com.yonyou.sh.common.bean.HttpResponse;
import com.yonyou.sh.common.constant.SPKeys;
import com.yonyou.sh.common.utils.AccountUtils;
import com.yonyou.sh.common.utils.ContextHelper;
import com.yonyou.sh.common.utils.EncryptUtils;
import com.yonyou.sh.common.utils.JsonUtils;
import com.yonyou.sh.common.utils.LogUtils;
import com.yonyou.sh.common.utils.NetworkUtils;
import com.yonyou.sh.common.utils.NumberUtils;
import com.yonyou.sh.common.utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.OkHttpRequestBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.builder.PostStringBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
/**
 * 作者：邵帅
 * 时间：2018/12/21 3:58 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class OkHttpProcessor implements IHttpProcessor {
    private static final String TAG = "OkHttpProcessor";
    private static final String METHOD_GET = "method_get";
    private static final String METHOD_POST_JSON = "method_post_json";
    private static final String METHOD_POST_FORM = "method_post_form";
    private static final String METHOD_POST_FORM_FILE = "method_post_form_file";

    private HashMap<String, String> headerMap = new HashMap<>();
    private Handler handler;
    private final OkHttpUtils okHttpUtils;
    private String randomNum;
    private long timeMillis;

    public OkHttpProcessor() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        okHttpUtils = OkHttpUtils.initClient(okHttpClient);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void get(String url, Map<String, Object> params, Object tag, HttpCallback callback) {
        RequestCall requestCall = buildGetRequest(url, params, tag);
        handleRequest(requestCall, callback);
    }

    @Override
    public void postJson(String url, Map<String, Object> params, Object tag, HttpCallback callback) {
        RequestCall requestCall = buildPostJsonRequest(url, params, tag);
        handleRequest(requestCall, callback);
    }

    @Override
    public void postForm(String url, Map<String, Object> params, Object tag, HttpCallback callback) {
        RequestCall requestCall = buildPostFormRequest(url, params, tag);
        handleRequest(requestCall, callback);
    }

    @Override
    public void postFile(String url, File file, Object tag, HttpCallback callback) {
        RequestCall requestCall = buildPostFileRequest(url, file, tag);
        handleRequest(requestCall, callback);
    }

    @Override
    public void postFiles(String url, ArrayList<String> pathList, Object tag, HttpCallback callback) {
        RequestCall requestCall = buildPostFilesRequest(url, pathList, tag);
        handleRequest(requestCall, callback);
    }

    private RequestCall buildGetRequest(String url, Map<String, Object> params, Object tag) {
        LogUtils.i(TAG, "request url--->" + url);
        LogUtils.i(TAG, "request method--->" + "GET");
        if (params == null) {
            params = new HashMap<>();
        }
        params.put("nonce", NumberUtils.getRandomNum(8));
        params.put("occurtime", System.currentTimeMillis());

        GetBuilder builder = OkHttpUtils.get().url(url).tag(tag);
        GetBuilder newBuilder = (GetBuilder) addHeader(builder, params, METHOD_GET);
        if (params != null && !params.isEmpty()) {
            LogUtils.i(TAG, "request params--->" + params.toString());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    String key = entry.getKey();
                    String val = entry.getValue().toString();
                    newBuilder.addParams(key, val);
                }
            }
        }
        return newBuilder.build();
    }

    private RequestCall buildPostJsonRequest(String url, Map<String, Object> params, Object tag) {
        randomNum = NumberUtils.getRandomNum(8);
        timeMillis = System.currentTimeMillis();
        String newUrl = appendParam(url, randomNum, timeMillis);
        LogUtils.i(TAG, "request url--->" + newUrl);
        LogUtils.i(TAG, "request method--->" + "POST application/json");
        if (params != null) {
            LogUtils.i(TAG, "request params--->" + params.toString());
        }
        PostStringBuilder builder = OkHttpUtils.postString().url(newUrl).tag(tag);
        RequestCall requestCall = ((PostStringBuilder) addHeader(builder, params, METHOD_POST_JSON))
                .content(new Gson().toJson(params))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build();
        return requestCall;
    }

    private RequestCall buildPostFormRequest(String url, Map<String, Object> params, Object tag) {
        LogUtils.i(TAG, "request url--->" + url);
        LogUtils.i(TAG, "request method--->" + "POST form");

        if (params == null) {
            params = new HashMap<>();
        }
        params.put("nonce", NumberUtils.getRandomNum(8));
        params.put("occurtime", System.currentTimeMillis());

        PostFormBuilder builder = OkHttpUtils.post().url(url).tag(tag);
        PostFormBuilder newBuilder = (PostFormBuilder) addHeader(builder, params, METHOD_POST_FORM);
        if (params != null && !params.isEmpty()) {
            LogUtils.i(TAG, "request params--->" + params.toString());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                String val = entry.getValue().toString();
                newBuilder.addParams(key, val);
            }
        }
        return newBuilder.build();
    }

    private RequestCall buildPostFileRequest(String url, File file, Object tag) {
        randomNum = NumberUtils.getRandomNum(8);
        timeMillis = System.currentTimeMillis();
        String newUrl = appendParam(url, randomNum, timeMillis);
        LogUtils.i(TAG, "request url--->" + newUrl);
        LogUtils.i(TAG, "request method--->" + "PostForm file");

        PostFormBuilder builder = OkHttpUtils.post().addFile("file", file.getName(), file).url(newUrl).tag(tag);
        PostFormBuilder newBuilder = (PostFormBuilder) addHeader(builder, null, METHOD_POST_FORM_FILE);
        return newBuilder.build();
    }

    private RequestCall buildPostFilesRequest(String url, ArrayList<String> pathList, Object tag) {
        randomNum = NumberUtils.getRandomNum(8);
        timeMillis = System.currentTimeMillis();
        String newUrl = appendParam(url, randomNum, timeMillis);
        LogUtils.i(TAG, "request url--->" + newUrl);
        LogUtils.i(TAG, "request method--->" + "PostForm files");

        HashMap<String, File> map = new HashMap<>();
        for (String path : pathList) {
            File file = new File(path);
            map.put(file.getName(), file);
        }
        PostFormBuilder builder = OkHttpUtils.post().files("file", map).url(newUrl).tag(tag);
        PostFormBuilder newBuilder = (PostFormBuilder) addHeader(builder, null, METHOD_POST_FORM_FILE);
        return newBuilder.build();
    }

    public OkHttpRequestBuilder addHeader(OkHttpRequestBuilder builder, Map<String, Object> params, String method) {
        headerMap.put("token", AccountUtils.getToken());
        headerMap.put("x-client-id", "001");
        headerMap.put("sign", generateSign(params, method));
        builder.headers(headerMap);
        LogUtils.i(TAG, "request headers--->" + headerMap.toString());
        return builder;
    }

    private void handleRequest(final RequestCall requestCall, final HttpCallback callback) {
        if (!NetworkUtils.isConnected()) {
            sendFailedCallback(callback, HttpResponse.NETWORK_ERROR, ContextHelper.getContext().getString(R.string.net_error));
            return;
        }

        requestCall.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(TAG, "response errMsg--->" + e.toString());
                if (e != null) {
                    if (e.toString().contains("SocketTimeoutException")) {
                        sendFailedCallback(callback, HttpResponse.NETWORK_TIME_OUT, ContextHelper.getContext().getString(R.string.net_timeout));
                    } else if (e.getMessage().contains("Canceled") || e.getMessage().contains("Socket closed")) {
                        LogUtils.i(TAG, "网络请求已取消");
                    } else if (e.getMessage().contains("Failed to connect")) {
                        LogUtils.i(TAG, "连接服务器失败");
                        sendFailedCallback(callback, HttpResponse.HTTP_FAILED, ContextHelper.getContext().getString(R.string.unable_connect_server));
                    } else {
                        sendFailedCallback(callback, HttpResponse.UNKNOW_ERROR, ContextHelper.getContext().getString(R.string.un_know_error));
                    }
                } else {
                    sendFailedCallback(callback, HttpResponse.UNKNOW_ERROR, ContextHelper.getContext().getString(R.string.un_know_error));
                }
            }

            @Override
            public void onResponse(final String response, int id) {
                LogUtils.i(TAG, "response data--->" + response);
                if (JsonUtils.isSuccess(response)) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSucc(JsonUtils.getJsonStr(response, "data"));
                        }
                    });
                } else {
                    sendFailedCallback(callback, JsonUtils.getResultCode(response), JsonUtils.getErrMsg(response));
                }
            }
        });
    }

    private void sendFailedCallback(final HttpCallback callback, final int code, final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                HttpResponse<String> response = new HttpResponse<>(code, message);
                callback.onFailure(response);
                Toast.makeText(ContextHelper.getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void cancel(Object tag) {
        okHttpUtils.cancelTag(tag);
    }

    public String generateSign(Map<String, Object> params, String method){
        StringBuilder builder = new StringBuilder();
        HashMap<String, Object> map = new HashMap<>();
        if (METHOD_GET.equals(method) || METHOD_POST_FORM.equals(method)) {
            if (params != null) {
                map.putAll(params);
            }
        } else {
            map.put("nonce", randomNum);
            map.put("occurtime", timeMillis);
        }

        ArrayList<String> keyList = new ArrayList<>(map.keySet());
        Collections.sort(keyList);
        for (String key : keyList) {
            builder.append(key + "=" + map.get(key));
        }
        if (METHOD_POST_JSON.equals(method)) {
            builder.append("json=" + new JSONObject(params).toString());
        }
        LogUtils.i(TAG, "sort params--->" + builder.toString());
        return EncryptUtils.md5(EncryptUtils.md5(builder.toString()) + "admin");
    }

    public String appendParam(String url, String randomNum, long timeMillis){
        return url + "?nonce=" + randomNum + "&occurtime=" + timeMillis;
    }

}
