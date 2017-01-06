package com.gogh.afternoontea.main;

import android.app.Application;
import android.util.Log;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.iinterface.OnResponListener;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.request.RequestProxy;
import com.gogh.afternoontea.theme.ThemeManager;
import com.gogh.afternoontea.utils.Utility;
import com.gogh.afternoontea.constant.Weather;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  启动定位，并且在成功后根据所在位置请求天气数据，然后根据天气设置对应的主题色。</p>
 * <p> Created by <b>高晓峰</b> on 12/21/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/21/2016 do fisrt create. </li>
 */
public class ATApplication extends Application implements com.gogh.afternoontea.iinterface.AT {

    private static final String TAG = "ATApplication";

    public static String WEATHER = null;
    public static int THEME = R.style.BlueTheme;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "ATApplication  onCreate.");
        init();
    }

    @Override
    public void init() {
        onLocation();
    }

    @Override
    public void onLocation() {
        if (Utility.isNight()) {
            Logger.d(TAG, " onLocation is night. ");
            WEATHER = Weather.NIGHT;
        } else {
            Logger.d(TAG, " onLocation is normal. ");
            RequestProxy.newInstance().getLocation(getApplicationContext(), new OnResponListener<String>() {
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
                WEATHER = response;
                ThemeManager.newInstance().setTheme(WEATHER);
            }
        });
    }

}
