package com.yonyou.module.carnet.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yonyou.module.carnet.R;
import com.yonyou.module.carnet.util.TextUtil;
import com.yonyou.sh.common.base.BaseActivity;
import com.yonyou.sh.common.base.IBasePresenter;
import com.yonyou.sh.common.constant.Constant;
import com.yonyou.sh.common.utils.GaoDeMapUtils;
import com.yonyou.sh.common.utils.LogUtils;
import com.yonyou.sh.common.utils.UIUtils;

import java.io.File;
import java.util.List;

/**
 * 作者：邵帅
 * 时间：2018/11/30 5:13 PM
 * 邮箱：shaoshuai1@yonyou.com
 * 说明：地图
 */
public class MapActivity extends BaseActivity {

    private String position;
    private String myPosition, carPosition;
    private MapView mapView;
    private AMap aMap;
    private CameraUpdate cameraUpdate;
    private GaoDeMapUtils gaodeMapUtils;
    private double myLat, myLng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapView = findViewById(R.id.gMap);
        mapView.onCreate(savedInstanceState);
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    public void initParam(Bundle params) {
        position = params.getString("location");
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_map;
    }

    @Override
    public void initView(View view) {
        initTitleBar(getString(R.string.title_car_location));
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        getMyPosition();
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                    }
                })
                .start();
    }

    @Override
    public void onViewClick(View v) {
    }

    @Override
    public void initListener() {
    }

    @Override
    public void doBusiness() {

    }

    private void getMyPosition() {
        gaodeMapUtils = GaoDeMapUtils.getInstance();
        gaodeMapUtils.initGaodeMap(this);
        gaodeMapUtils.setOnLocationListener(new GaoDeMapUtils.OnLocationListener() {
            @Override
            public void onLocation(AMapLocation location) {
                myLat = location.getLatitude();
                myLng = location.getLongitude();
                myPosition = myLat + "," + myLng;
                LogUtils.e("起点==", myPosition);
                if (!TextUtil.isEmpty(position)) {
                    aMap = mapView.getMap();
                    aMap.getUiSettings().setZoomControlsEnabled(false);//去掉缩放按钮
                    String[] result1 = position.split(",");
                    Double carLa = Double.parseDouble(result1[1]);
                    Double carLt = Double.parseDouble(result1[0]);
                    carPosition = carLa + "," + carLt;
                    makePoint(carLa, carLt);
                } else {
                    showToast("无法获取车辆位置");
                }
            }
        });
    }

    //根据地址绘制需要显示的点
    public void makePoint(double carLat, double carLng) {
        LatLng latLng = new LatLng(carLat, carLng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_g_coding));
        markerOptions.icon(bitmapDescriptor);
        aMap.addMarker(markerOptions);
        //改变可视区域为指定位置
        //CameraPosition4个参数分别为位置，缩放级别，目标可视区域倾斜度，可视区域指向方向（正北逆时针算起，0-360）
        cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, 15, 0, 30));
        aMap.moveCamera(cameraUpdate);//地图移向指定区域
        //位置坐标的点击事件
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent;
                String uris = "baidumap://map/direction?origin=" + myPosition + "&destination=" + carPosition + "&mode=driving";
                intent = new Intent();
                intent.setData(Uri.parse(uris));
                if (UIUtils.isInstall(MapActivity.this, Constant.BAIDU_APP_URL)) {
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "没有安装百度地图客户端", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    //重新绘制加载地图
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    //暂停地图的绘制
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    //保存地图当前的状态方法必须重写
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    //销毁地图
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
