package com.gogh.afternoontea.location;

import android.content.Context;
import android.util.Log;

import com.gogh.afternoontea.app.Location;
import com.gogh.afternoontea.listener.OnResponListener;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.request.RequestProxy;
import com.gogh.afternoontea.theme.ThemeManager;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 天气值，用于转换主题色</p>
 * <p> Created by <b>高晓峰</b> on 12/21/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/21/2016 do fisrt create. </li>
 */
public class Weather implements Location {

    public static final String TAG = "Weather";

    public static final String NIGHT = "夜";
    public static final String SUNNY = "晴";
    public static final String CLOUDY = "多云";
    public static final String OVERCAST = "阴";
    public static final String RAIN = "雨";
    public static final String HAILSTONES = "冰雹";
    public static final String SONW = "雪";
    public static final String FOG = "雾";
    public static final String FOG_HAZE = "霾";
    public static final String SAND_STORM = "沙尘";

    private Context context;

    public Weather(Context context) {
        this.context = context;
    }

    @Override
    public void onLocation() {
        Logger.d(TAG, " onLocation is run. ");
        RequestProxy.newInstance().getLocation(context.getApplicationContext(), new OnResponListener<String>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onResponse(String response) {
                requestWeather(response);
            }
        });
    }

    @Override
    public void requestWeather(String cityIp) {
        Log.d(TAG, " requestWeather city : " + cityIp);
        RequestProxy.newInstance().getWeatherByCity(cityIp, new OnResponListener<String>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onResponse(String response) {
                ThemeManager.newInstance().setTheme(response);
                LocationClient.newInstance().build(context.getApplicationContext()).stopLocation();
            }
        });
    }

}
