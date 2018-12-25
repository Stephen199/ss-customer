package com.yonyou.sh.common.http;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class HttpHelper implements IHttpProcessor {

    private static HttpHelper instance;

    public static HttpHelper getInstance() {
        if (instance == null) {
            synchronized (HttpHelper.class) {
                if (instance == null) {
                    instance = new HttpHelper();
                }
            }
        }
        return instance;
    }


    private static IHttpProcessor mHttpProcessor = null;

    public static void init(IHttpProcessor httpProcessor) {
        mHttpProcessor = httpProcessor;
    }


    @Override
    public void get(String url, Map<String, Object> params, Object tag, HttpCallback callback) {
        mHttpProcessor.get(url, params, tag, callback);
    }

    @Override
    public void postJson(String url, Map<String, Object> params, Object tag, HttpCallback callback) {
        mHttpProcessor.postJson(url, params, tag, callback);
    }

    @Override
    public void postForm(String url, Map<String, Object> params, Object tag, HttpCallback callback) {
        mHttpProcessor.postForm(url, params, tag, callback);
    }

    @Override
    public void postFile(String url, File file, Object tag, HttpCallback callback) {
        mHttpProcessor.postFile(url, file, tag, callback);
    }

    @Override
    public void postFiles(String url, ArrayList<String> pathList, Object tag, HttpCallback callback) {
        mHttpProcessor.postFiles(url, pathList, tag, callback);
    }

    @Override
    public void cancel(Object tag) {
        mHttpProcessor.cancel(tag);
    }
}
