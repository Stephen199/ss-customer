package com.yonyou.sh.common.http;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * 网络抽象层接口
 */

public interface IHttpProcessor {

    void get(String url, Map<String, Object> params, Object tag, HttpCallback callback);

    void postJson(String url, Map<String, Object> params, Object tag, HttpCallback callback);

    void postForm(String url, Map<String, Object> params, Object tag, HttpCallback callback);

    void postFile(String url, File file, Object tag, HttpCallback callback);

    void postFiles(String url, ArrayList<String> pathList, Object tag, HttpCallback callback);

    void cancel(Object tag);
}
