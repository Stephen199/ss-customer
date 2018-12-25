package com.yonyou.sh.common.http;

import com.yonyou.sh.common.bean.HttpResponse;

/**
 * 回调接口
 */
public interface ICallback {

    void onSucc(String result);

    void onFailure(HttpResponse response);
}
