package com.gogh.afternoontea.location;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.gogh.afternoontea.location.listener.OnLocationListener;
import com.gogh.afternoontea.utils.Logger;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 用于定位，通过位置请求天气，然后根据天气设置主题色</p>
 * <p> Created by <b>高晓峰</b> on 12/23/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/23/2016 do fisrt create. </li>
 */
public class LocationClient {

    private static final String TAG = "LocationClient";
    //声明AMapLocationClient类对象

    @Nullable
    private static AMapLocationClient mLocationClient = null;

    private OnLocationListener locationListener;

    @NonNull
    public AMapLocationListener mLocationListener = aMapLocation -> {

        if (null != aMapLocation) {
            if (locationListener != null) {
                locationListener.onLocationed(aMapLocation.getCity());
            }
            // 解析定位结果
            Logger.d(TAG, "onLocationChanged  : " + getLocationStr(aMapLocation));
        } else {
            if (locationListener != null) {
                locationListener.onError(-1, "response is null.");
            }
            // 定位失败
            Logger.e(TAG, "onLocationChanged error : result is null.");
        }
    };

    private LocationClient() {
    }

    @Nullable
    public static LocationClient newInstance() {
        return SingleHolder.CLIENT;
    }

    /**
     * 根据定位结果返回定位信息的字符串
     *
     * @param location
     * @return
     */
    @Nullable
    private synchronized static String getLocationStr(@Nullable AMapLocation location) {
        if (null == location) {
            return null;
        }
        StringBuffer sb = new StringBuffer();
        //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
        if (location.getErrorCode() == 0) {
            sb.append("定位成功" + "\n");
            sb.append("定位类型: " + location.getLocationType() + "\n");
            sb.append("经    度    : " + location.getLongitude() + "\n");
            sb.append("纬    度    : " + location.getLatitude() + "\n");
            sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
            sb.append("提供者    : " + location.getProvider() + "\n");

            if (location.getProvider().equalsIgnoreCase(
                    android.location.LocationManager.GPS_PROVIDER)) {
                // 以下信息只有提供者是GPS时才会有
                sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                sb.append("角    度    : " + location.getBearing() + "\n");
                // 获取当前提供定位服务的卫星个数
                sb.append("星    数    : "
                        + location.getSatellites() + "\n");
            } else {
                // 提供者是GPS时是没有以下信息的
                sb.append("国    家    : " + location.getCountry() + "\n");
                sb.append("省            : " + location.getProvince() + "\n");
                sb.append("市            : " + location.getCity() + "\n");
                sb.append("城市编码 : " + location.getCityCode() + "\n");
                sb.append("区            : " + location.getDistrict() + "\n");
                sb.append("区域 码   : " + location.getAdCode() + "\n");
                sb.append("地    址    : " + location.getAddress() + "\n");
                sb.append("兴趣点    : " + location.getPoiName() + "\n");
            }
        } else {
            //定位失败
            sb.append("定位失败" + "\n");
            sb.append("错误码:" + location.getErrorCode() + "\n");
            sb.append("错误信息:" + location.getErrorInfo() + "\n");
            sb.append("错误描述:" + location.getLocationDetail() + "\n");
        }

        return sb.toString();
    }

    @Nullable
    public synchronized AMapLocationClient build(@NonNull Context context) {
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(context.getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(mLocationListener);
            mLocationClient.setLocationOption(getDefaultOption());
        }
        return mLocationClient;
    }

    /**
     * 默认的定位参数
     *
     * @since 2.8.0
     */
    @NonNull
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(3000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        return mOption;
    }

    public void setOnLocationListener(OnLocationListener locationListener) {
        this.locationListener = locationListener;
    }

    private static final class SingleHolder {
        private static final LocationClient CLIENT = new LocationClient();
    }
}
