package com.gogh.afternoontea.main;

import android.app.Application;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.location.Weather;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.preference.imp.Configuration;
import com.gogh.afternoontea.utils.Utility;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description:  启动定位，并且在成功后根据所在位置请求天气数据，然后根据天气设置对应的主题色。</p>
 * <p> Created by <b>高晓峰</b> on 12/21/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/21/2016 do fisrt create. </li>
 */
public class ATApplication extends Application implements Initializer {

    private static final String TAG = "ATApplication";

    public static int THEME = R.style.DefaultTheme;
    private Configuration configuration;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "ATApplication  onCreate.");
        THEME = new Configuration(getApplicationContext(), Configuration.FLAG_CUSTOM).getTheme();
        init();
    }

    @Override
    public void init() {
        Logger.d(TAG, "ATApplication  init.");
        configuration = new Configuration(getApplicationContext(), Configuration.FLAG_SYSTEM);
        if (configuration.isNightMode()) {
            Logger.d(TAG, "is night mode. ");
            if (Utility.isNight()) {
                Logger.d(TAG, " is night mode and set up night theme. ");
                THEME = R.style.DarkTheme;
            } else {
                new Weather(this).onLocation();
            }
        } else {
            if (configuration.isWeatherTheme()) {// 开启天气主题
                Logger.d(TAG, "had opened weather theme.");
                new Weather(this).onLocation();
            }
        }
    }

    @Override
    public void initView() {
    }

}
