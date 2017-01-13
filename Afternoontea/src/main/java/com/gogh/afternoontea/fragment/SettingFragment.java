package com.gogh.afternoontea.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.app.CardModeManager;
import com.gogh.afternoontea.location.Weather;
import com.gogh.afternoontea.main.ATApplication;
import com.gogh.afternoontea.preference.imp.Configuration;
import com.gogh.afternoontea.theme.ThemeManager;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 开启天气主题、 开启夜间模式、移动网络不显示图片、 推荐给朋友、清除缓存、关于</p>
 * <p> Created by <b>高晓峰</b> on 1/10/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/10/2017 do fisrt create. </li>
 */
public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener,
        ThemeManager.OnUpdateThemeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //从xml文件加载选项
        addPreferencesFromResource(R.xml.prefrences);
        ThemeManager.newInstance().registerUpdateThemeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ThemeManager.newInstance().unRegisterUpdateThemeListener(this);
    }

    /**
     * Called when a shared preference is changed, added, or removed. This
     * may be called even if a preference is set to its existing value.
     * <p>
     * <p>This callback will be run on your main thread.
     *
     * @param sharedPreferences The {@link SharedPreferences} that received
     *                          the change.
     * @param key               The key of the preference that was changed, added, or
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getResources().getString(R.string.settting_prefrences_theme_weather_status))) {// 天气主题
            if (sharedPreferences.getBoolean(key, false)) {
                new Weather(getActivity().getApplicationContext()).onLocation();
            }
        } else if (key.equals(getResources().getString(R.string.settting_prefrences_display_car_list_key))) {// 卡片列表
            CardModeManager.newInstance().notifyCardModeChanged();
        } else if (key.equals(getResources().getString(R.string.setting_prefrences_theme_night_status))) {// 夜间主题
            if (sharedPreferences.getBoolean(key, false)) {
                ThemeManager.newInstance().setThemeColor(R.color.colorDarkPrimary);
            } else {
                ATApplication.THEME = new Configuration(getActivity().getApplicationContext(), Configuration.FLAG_CUSTOM).getTheme();
                ThemeManager.newInstance().setThemeStyle(ATApplication.THEME);
            }
        } else if (key.equals(getResources().getString(R.string.settting_prefrences_display_no_pic_key))) {// 无图模式
            if (!sharedPreferences.getBoolean(key, false)) {
                CardModeManager.newInstance().notifyCardModeChanged();
            }
        } else if(key.equals(getResources().getString(R.string.settting_prefrences_display_wifi_pic_key))){// wifi模式
            if (!sharedPreferences.getBoolean(key, false)) {
                CardModeManager.newInstance().notifyCardModeChanged();
            }
        }
    }

    @Override
    public void onUpdateTheme(int themeColor) {
        // 动态设置标题栏的颜色
        getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(getActivity(), themeColor));
        // 动态设置导航栏的颜色
        getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(getActivity(), themeColor));
    }
}
