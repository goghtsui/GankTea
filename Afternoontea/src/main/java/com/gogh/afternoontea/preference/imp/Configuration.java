package com.gogh.afternoontea.preference.imp;

import android.content.Context;
import android.content.SharedPreferences;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Constant;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.preference.Preference;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/12/2017 do fisrt create. </li>
 */
public class Configuration implements Preference {

    public static final int FLAG_SYSTEM = 0x11010;
    public static final int FLAG_CUSTOM = 0x11011;

    private static final String PREFERENCE_NAME = "_preferences";
    private static final String USER_PREFERENCE_NAME = "_userpreferences";

    private Context context;
    private SharedPreferences preferences;
    private SharedPreferences userPreferences;

    private SharedPreferences.Editor userEditor;

    public Configuration(Context context, int flag) {
        this.context = context;
        initConfig(flag);
    }

    private void initConfig(int flag) {
        switch (flag) {
            case FLAG_SYSTEM:
                if (preferences == null) {
                    preferences = context.getSharedPreferences(context.getPackageName() + PREFERENCE_NAME, Context.MODE_PRIVATE);
                }
                break;
            case FLAG_CUSTOM:
                if (userPreferences == null) {
                    userPreferences = context.getSharedPreferences(context.getPackageName() + USER_PREFERENCE_NAME, Context.MODE_PRIVATE);
                    userEditor = userPreferences.edit();
                }
                break;
        }
    }

    /**
     * 是否开启天气主题
     *
     * @return
     */
    @Override
    public boolean isWeatherTheme() {
        return preferences.getBoolean(context.getString(R.string.settting_prefrences_theme_weather_status), false);
    }

    /**
     * 是否开启夜间主题
     *
     * @return
     */
    @Override
    public boolean isNightTheme() {
        return preferences.getBoolean(context.getString(R.string.setting_prefrences_theme_night_status), false);
    }

    /**
     * 是否开启夜间模式
     *
     * @return
     */
    @Override
    public boolean isNightMode() {
        return preferences.getBoolean(context.getString(R.string.setting_prefrences_theme_night_mode_status), false);
    }

    /**
     * 是否开启无图模式
     *
     * @return
     */
    @Override
    public boolean isNopicMode() {
        return preferences.getBoolean(context.getString(R.string.settting_prefrences_display_no_pic_key), false);
    }

    /**
     * 是否开启仅WIFI下显示图片
     *
     * @return
     */
    @Override
    public boolean isWifiPicMode() {
        return preferences.getBoolean(context.getString(R.string.settting_prefrences_display_wifi_pic_key), false);
    }

    /**
     * 是否开启卡片模式
     *
     * @return
     */
    @Override
    public boolean isCardMode() {
        return preferences.getBoolean(context.getString(R.string.settting_prefrences_display_car_list_key), false);
    }

    @Override
    public int getTheme() {
        return userPreferences.getInt(Constant.THEME_KEY, R.style.DefaultTheme);
    }

    @Override
    public void setTheme(int theme) {
        Logger.d("Configuration","setTheme : " + theme);
        userEditor.putInt(Constant.THEME_KEY, theme);
        userEditor.commit();
    }
}
