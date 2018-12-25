package com.yonyou.sh.common.utils;

import android.content.Context;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 作者：邵帅
 * 时间：2018/12/25 2:15 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：
 */
public class GaoDeMapUtils implements AMapLocationListener{

    private static final String TAG = "GaodeMapUtils";
    private static GaoDeMapUtils instance;
    private AMapLocationClient mLocationClient;

    public static GaoDeMapUtils getInstance() {
        if (instance == null) {
            synchronized (GaoDeMapUtils.class) {
                if (instance == null)
                    instance = new GaoDeMapUtils();
            }
        }
        return instance;
    }

    public void initGaodeMap(Context mContext) {
        mLocationClient = new AMapLocationClient(mContext);
        //设置定位回调监听
        mLocationClient.setLocationListener(this);


        //初始化AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //获取一次定位结果：
        mLocationOption.setOnceLocation(true);
        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                if (onLocationListener != null) {
                    onLocationListener.onLocation(aMapLocation);
                }
                String address = aMapLocation.getAddress();
                LogUtils.e(TAG, "address--->" + address);
            } else {
                LogUtils.e(TAG, "location Error, errorCode:" + aMapLocation.getErrorCode() + ", errorInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private OnLocationListener onLocationListener;

    public void setOnLocationListener(OnLocationListener onLocationListener) {
        this.onLocationListener = onLocationListener;
    }

    public interface OnLocationListener {

        void onLocation(AMapLocation location);
    }

    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
    }
}
