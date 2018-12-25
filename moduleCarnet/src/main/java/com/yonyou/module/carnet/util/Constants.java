package com.yonyou.module.carnet.util;

import android.os.Environment;

/**
 * 作者：shaoshuai
 * 时间：2018/11/14 6:06 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class Constants {

    public static final String HTTP_URL = "https://salesclouds.dearcc.cn/";//测试环境
    public static final String sp_userId = "userId";//用户ID
    public static final String sp_isLogin = "isLogin";//是否登陆
    public static final String sp_dealerCode = "dealerCode";//是否登陆
    public static final String sp_accessToken = "accessToken";//用户accessToken
    public static final String sp_Currentpage = "sp_Currentpage";//当前页
    public static final String SP_THEME_NAME = "SP_THEME_NAME";//主题名称
    public static final String messageType = "messageType";//消息类型
    public static final String phone = "phone";//主题名称
    public static final String log = "log";
    public static final String lat = "lat";
    public static final String City_Code = "City_Code";

    public static final String city = "city";
    public static final String AdCode = "AdCode";
    public static final String address = "address";

    public static final String stationInfoByForWord  = "api/charging/v1/charging/stationInfoByForWord";  //搜索
    public static final String regionParentAndChild = "api/master/v1/regionParentAndChild";  //城市
    public static final String regionParentAndChild3 = "api/master/v1/regionParentAndChild3";  //充电桩城市
    //账号密码登录
    public static final String AppUserLogin = "api/dearccCustomer/v1/appUserLogin";
    //登录获取验证码
    public static final String phoneMsgCode = "api/customer/v1/phoneMsgCode";
    //获取个人信息
    public static final String getCarOwnerInfo = "api/customer/v1/getCarOwnerInfo";
    //获取车辆列表
    public static final String getCarList = "api/customer/v1/loadCarinfoList";
    //添加车辆
    public static final String carInfoNew = "api/customer/v1/carInfoNew";
    //注册获取验证码
    public static final String sendCheckCodeMessage = "api/customer/v1/sendCheckCodeMessage";
    //注册
    public static final String registerAppUser = "api/dearccCustomer/v1/registerAppUser";
    //修改个人信息
    public static final String updateCarOwnerInfo = "api/customer/v1/updateCarOwnerInfo";
    //邦车获取验证码
    public static final String sendBindCarSms = "api/customer/v1/sendBindCarSms";
    //服务首页
    public static final String propertiesValueList = "api/master/v1/propertiesValueList";
    //学历
    public static final String education = "api/master/v1/fixCodeByParentId";
    //网点列表
    public static final String searchInit = "api/customer/v1/searchInit";
    //网点获取服务类型
    public static final String searchInitServiceType = "api/master/v1/fixCodeByParentId?parentId=5002";
    //网点详情
    public static final String findDealerDetail = "api/customer/v1/findDealerDetail";
    //网点关注
    public static final String upGzDealerCode = "api/customer/v1/upGzDealerCode";
    //修改密码获取验证码
    public static final String confrimPwdCode = "api/customer/v1/forgetPassword";
    //修改密码效验验证码
    public static final String checkConfrimPwdCode = "api/customer/v1/forgetCheckCode";
    //修改密码
    public static final String resetPassword = "api/customer/v1/resetPassword";
    //效验注册验证码
    public static final String checkRegistCode = "api/customer/v1/registerCheckCodeValid";
    //消息列表
    public static final String messageList = "api/customer/v1/messageList";
    //消息详情
    public static final String messageIndex = "api/customer/v1/message";
    //消息状态
    public static final String readingFlag = "api/customer/v1/readingFlag";
    //签到
    public static final String qiandao = "api/customer/v1/sign";
    //上传头像
    public static final String setCarOwnerPhoto = "api/customer/v1/setCarOwnerPhoto";
    //保险公司
    public static final String InsuranceMap = "api/customer/v1/InsuranceMap";
    //更改车辆信息
    public static final String modifyCarinfo = "api/customer/v1/modifyCarinfo";
    //我的等级
    public static final String mypoints = "api/customer/v1/mypoints";
    //设置等级
    public static final String setHint = "api/customer/v1/setHint";
    //获取等级
    public static final String getHint = "api/customer/v1/getHint";
    //解绑车辆
    public static final String updateCarinfoStu = "api/customer/v1/updateCarinfoStu";
    //消息数量
    public static final String messageCount = "api/customer/v1/messageCount";
    //分享获取积分
    public static final String sharePoints = "api/customer/v1/sharePoints";
    //下载pdf文件
    public static final String downloadpdf = "api/master/v1/carModel/handBook";
    //手机快捷登录验证码z
    public static final String sendLoginCheckCode = "dealerApp/api/login/sendLoginCheckCode";
    //首页
    public static final String getHomePageInfo = "dealerApp/api/homePage/getHomePageInfo";
    //忘记密码获取验证码
    public static final String sendForgetCheckCode = "dealerApp/api/login/sendForgetCheckCode";
    //忘记密码效验验证码
    public static final String checkCodeForget = "dealerApp/api/login/checkCodeForget";
    //忘记密码重置密码
    public static final String resetPasswordForget = "dealerApp/api/login/resetPasswordForget";
    //修改密码获取验证码
    public static final String sendAlertPwCode  = "dealerApp/api/login/sendAlertPwCode";
    //修改密码获取验证码
    public static final String checkCodeAlertPW  = "dealerApp/api/login/checkCodeAlertPW";
    //上传头像
    public static final String uploadingPhoto = "dealerApp/api/login/uploadingPhoto";
    //推荐购车记录
    public static final String myRecommend = "api/recommend/v1/myRecommend";
    //获取系统时间
    public static final String sysDateTime = "api/tsp/vehicle/sysDateTime";

    public static final String stationInfoList  = "api/charging/v1/stationInfoList";  //充电桩 列表
    public static final String stationInfo  = "api/charging/v1/charging/stationInfo";  //充电桩

    public static final String appPay  = "api/pay/v1/appPay";  //支付宝接口


    /**
     * SD卡路径
     */
    public final static String PATH_SDCARD = Environment.getExternalStorageDirectory().getPath();
    /**
     * 缓存文件夹保存路径<br>
     * "/com.yongyou/cache/"
     */
//    public static final String PATH_CACHE = PATH_SDCARD + "/" + com.yonyou.dearcc.BuildConfig.APPLICATION_ID + "/cachess/";


}
