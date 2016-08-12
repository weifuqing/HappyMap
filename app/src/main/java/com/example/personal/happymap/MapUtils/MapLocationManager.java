package com.example.personal.happymap.mapUtils;

import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.personal.happymap.global.HappyMapApplication;

/**
 * Created by dell on 2016/7/25.
 */
public class MapLocationManager {

    BaiduMap baiduMap;
    LocationClient locationClient;
    BDLocationListener locationListener;
    BDNotifyListener notifyListener;
    // 定位模式 （普通-跟随-罗盘）
    private MyLocationConfiguration.LocationMode currentMode;
    // 定位图标描述
    private BitmapDescriptor currentMarker = null;


    int locType;

    private double longitude;//经度
    private double latitude;//纬度
    private float radius;// 定位精度半径，单位是米
    private String addrStr;// 反地理编码
    private String province;// 省份信息
    private String city;// 城市信息
    private String district;// 区县信息
    private float direction;// 手机方向信息

    //振动器设备
    private Vibrator mVibrator;

    public MapLocationManager(BaiduMap baiduMap){
        this.baiduMap = baiduMap;
        init();
    }

    public void init() {
        locationClient = new LocationClient(HappyMapApplication.getgContext());
        mVibrator = (Vibrator) HappyMapApplication.getgContext().getSystemService(Service.VIBRATOR_SERVICE);

    }

    public void startLocation(MyLocationConfiguration.LocationMode mode) {

        locationListener = new MyLocationListener();
        locationClient.registerLocationListener(locationListener);

        //设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType(MapConstantUtil.MapCoorType_baidu_Latlng);
        option.setScanSpan(5000);
        option.setIsNeedAddress(true);
        option.setNeedDeviceDirect(true);
        locationClient.setLocOption(option);

        //注册位置提醒监听
        notifyListener = new MyNotifyListener();
        notifyListener.SetNotifyLocation(longitude, latitude, 1000, MapConstantUtil.MapCoorType_baidu_Latlng);
        locationClient.registerNotify(notifyListener);

        locationClient.start();

        currentMode = mode;
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(currentMode, true, currentMarker));

    }

    public void startLocation() {
        startLocation(MyLocationConfiguration.LocationMode.NORMAL);
    }

    public void stopLocation() {
        if(locationListener!=null){
            locationClient.unRegisterLocationListener(locationListener);
        }
        if(notifyListener!=null){
            locationClient.removeNotifyEvent(notifyListener);
        }
        locationClient.stop();
    }


    //定位监听
    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            }

            locType = bdLocation.getLocType();
            latitude = bdLocation.getLatitude();
            longitude = bdLocation.getLongitude();
            if (bdLocation.hasRadius()) {
                radius = bdLocation.getRadius();
            }
            if (locType == BDLocation.TypeNetWorkLocation) {
                addrStr = bdLocation.getAddrStr();
            }

            direction = bdLocation.getDirection();
            province = bdLocation.getProvince();
            city = bdLocation.getCity();
            district = bdLocation.getDistrict();

            //构造定位数据
            MyLocationData data = new MyLocationData.Builder()
                    .direction(direction)
                    .latitude(latitude)
                    .longitude(longitude)
                    .accuracy(radius)
                    .build();

            //设置定位数据
            baiduMap.setMyLocationData(data);
            LatLng latLng = new LatLng(latitude, longitude);
            //MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);

            MapStatus mapStatus = new MapStatus.Builder()
                    .target(latLng)
                    .zoom(15)
                    .build();
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mapStatus);

            baiduMap.animateMapStatus(mapStatusUpdate);

        }
    }

    //位置提醒监听器
    class MyNotifyListener extends BDNotifyListener {
        @Override
        public void onNotify(BDLocation bdLocation, float v) {
            super.onNotify(bdLocation, v);
            mVibrator.vibrate(1000);
        }
    }
}
