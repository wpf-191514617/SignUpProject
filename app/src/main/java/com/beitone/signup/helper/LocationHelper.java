package com.beitone.signup.helper;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beitone.signup.SignUpApplication;

public class LocationHelper {

    private static LocationHelper mInstance;

    public LocationClient mLocationClient;

    private double mLatitude ,mLongitude;

    private LocationHelper(){
        //首先创建LocationClient实例
        mLocationClient=new LocationClient(SignUpApplication.getContext());
        //利用registerLocationListener方法注册定位监听器
        mLocationClient.registerLocationListener(new MyLocationListener());
        initLocation();
    }

    public static LocationHelper getInstance(){
        if (mInstance == null){
            synchronized (LocationHelper.class){
                if (mInstance == null){
                    mInstance = new LocationHelper();
                }
            }
        }
        return mInstance;
    }


    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);  //每五秒更新当前位置
        //指定百度LBS　SDK的定位模式，一共有三种模式可选
        //Hight_Accuracy,Battery_Saving,Device_Sensors
        //分别表示高精度（优先使用GPS，没有GPS的地方采用网络定位）
        //节电模式，只采用网络定位
        //传感器模式，只能使用GPS定位
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true); //传入当前位置的详细地址信息
        mLocationClient.setLocOption(option);
    }

    public double getLongitude(){
        return mLongitude;
    }

    public double getLatitude(){
        return mLatitude;
    }

    public void startLocation(){
        mLocationClient.start();
    }

    public void stopLocation(){
        if (mLocationClient != null && mLocationClient.isStarted()){
            mLocationClient.stop();
            mLocationClient = null;
        }
    }

    class MyLocationListener implements BDLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation != null){
                mLatitude = bdLocation.getLatitude();
                mLongitude = bdLocation.getLongitude();
            }
        }
    }

}
