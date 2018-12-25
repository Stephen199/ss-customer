package com.yonyou.sh.common.bean;

import java.io.Serializable;

/**
 * 作者：邵帅
 * 时间：2018/12/21 2:52 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class HttpResponse<T> implements Serializable {

    /**
     * resultCode : 200
     * errMsg : null
     * elapsedMilliseconds : 13
     * data : {"id":19,"saicUserId":19780717821518,"name":"15821292063","mobilePhone":"15821292063","password":"","photoUrl":"https://oss-qa.roewe.com.cn/f_b_92214_b_201808231723271.png","nickName":"森林大王","accountStatus":"ACTIVATED","createDate":"2018-06-05 07:44:53","updateDate":"2018-09-13 16:04:30","userType":3,"email":null,"lastErrTs":null,"isUsernameUpdatable":null,"laiwang":null,"registerSource":null,"loginTs":null,"photoId":null,"lastLoginTs":null,"lowerName":null,"wangwang":null,"errCount":null,"qq":null,"userBackgroundId":null,"userPendantId":null,"sex":0,"birthday":null,"background":null,"pendant":null,"medalCount":null,"constellation":null,"city":null,"drivingYear":null,"modelName":null,"watchCount":null,"fansCount":null,"articleCount":null,"watchStatus":null,"showAge":null,"roleBlock":[{"article_role_code":3,"user_id":19780717821518,"block_id":1}],"nickChangeTimes":0,"lastNickchangeTime":null,"lastNickchangeTimeDate":null,"nickModTimes":null,"nickDaymodTimes":null}
     * success : true
     */
    public static final int UNKNOW_ERROR = 0; //未知错误
    public static final int HTTP_FAILED = 1; //请求失败
    public static final int NETWORK_ERROR = 2; //没有网络
    public static final int NETWORK_TIME_OUT = 3;//网络超时

    private int resultCode;
    private String errMsg;
    private int elapsedMilliseconds;
    private T data;
    private boolean success;

    public HttpResponse(int resultCode, String errMsg) {
        this.resultCode = resultCode;
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
