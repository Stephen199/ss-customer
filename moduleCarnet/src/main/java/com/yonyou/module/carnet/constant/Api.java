package com.yonyou.module.carnet.constant;

/**
 * 作者：shaoshuai
 * 时间：2018/11/21 3:08 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class Api {

    //基础url
    public static final String HTTP_BASE_URL = "http://10.180.4.224:31080/api/";
    //public static final String HTTP_BASE_URL = "http://10.180.8.208:";

    public static final String ACCOUNT_LOGIN = HTTP_BASE_URL + "customer/appLogin";
    //车辆状态查询
    public static final String CAR_STATE_QUERY = "iov/tsp/vehicle/queryVehicleStatus";
    //public static final String CAR_STATE_QUERY = "tsp/vehicle/getLastVehicleStatus";

    //远程控制
    public static final String CAR_CONTROL = "iov/tsp/vehicle/remoteControl";

    //获取验证码
    public static final String GET_VERIFY_CODE = "iov/tsp/user/verify";

    //授权记录查询
    public static final String QUERY_AUTHOR_RECORDER = "iov/tsp/role/queryGrantHistory";

    //添加授权
    public static final String ADD_RECORDER = "iov/tsp/role/grant";

    //取消授权
    public static final String CANCEL_RECORDER = "iov/tsp/role/cancel";

    //安防密码设置
    public static final String SET_SECURITY_PWD = "iov/tsp/user/setScyPass";

    //安防密码修改
    public static final String CHANGE_SECURITY_PWD = "iov/tsp/user/modifyScyPass";

    //获取我的车辆列表
    public static final String QUERY_MY_VEHICLES = "iov/tsp/vehicle/queryMyVehicles";

    //切换车辆
    public static final String QUERY_CHOOSE_CAR = "iov/tsp/vehicle/chooseVehicle";
    //预约充电状态
    public static final String QUERY_CHARGE_STATUS = "iov/tsp/vehicle/queryChargeStatus";

    //车控登录
    public static final String USER_LOGIN = "iov/tsp/user/login";

    //获取最新远控结果
    public static final String GET_CONTROL_RESULT = "iov/tsp/vehicle/getLastRemoteResult";

    //获取最新车况
    public static final String GET_LATEST_VEHCILSTATUE = "iov/tsp/vehicle/getLastVehicleStatus";

    //获取最新车况
    public static final String HISTORYTRACK = "iov/tsp/vehicle/historyTrack";

    //获取当前服务器时间
    public static final String SYSDATETIME = "iov/tsp/vehicle/sysDateTime";

    /*
    * 绑车相关
    */
    public static final String ADD_CAR = "vinCheck";//添加车辆
    public static final String VERIFY_CAR = "engineCodeCheck";//验证车辆
    public static final String GET_AUTH_CODE_BIND = "captcha";//获取绑车验证码
    public static final String VERIFY_PHONE = "phoneCheck";//验证手机（通过手机号绑车）
    public static final String VERIFY_CREDENTIAL = "certificateCheck";//验证证件号码

}
