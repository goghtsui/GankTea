package com.gogh.afternoontea.main;

import android.app.Application;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.Initializer;
import com.gogh.afternoontea.location.Weather;
import com.gogh.afternoontea.preference.imp.Configuration;
import com.gogh.afternoontea.utils.AndroidUtil;
import com.gogh.afternoontea.utils.Logger;
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

    private static float[] SCREEN_SIZE;

    public static float getWidth() {
        return SCREEN_SIZE[0];
    }

    public static float getDensity() {
        return SCREEN_SIZE[1];
    }

    @Override
    public void initView() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "ATApplication  onCreate.");
//        LeakCanary.install(this);
        Configuration.newInstance().init(this);
        THEME = Configuration.newInstance().getTheme();
        init();
    }

    @Override
    public void init() {
        Logger.d(TAG, "ATApplication  init.");
        if (Configuration.newInstance().isNightTheme()) {
            THEME = R.style.DarkTheme;
        } else {
            if (Configuration.newInstance().isNightMode()) {
                Logger.d(TAG, "is night mode. ");
                if (Utility.isNight()) {
                    Logger.d(TAG, " is night mode and set up night theme. ");
                    THEME = R.style.DarkTheme;
                } else {
                    if (Configuration.newInstance().isWeatherTheme()) {// 开启天气主题
                        Logger.d(TAG, "had opened weather theme.");
                        new Weather(this).onLocation();
                    }
                }
            } else {
                if (Configuration.newInstance().isWeatherTheme()) {// 开启天气主题
                    Logger.d(TAG, "had opened weather theme.");
                    new Weather(this).onLocation();
                }
            }
        }
        SCREEN_SIZE = AndroidUtil.getScreenSize(getApplicationContext());
    }

}
