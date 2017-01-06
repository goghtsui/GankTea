package com.gogh.afternoontea.theme;

import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.constant.Weather;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: t主题色管理，根据不同的天气返回不同的主题色</p>
 * <p> Created by <b>高晓峰</b> on 12/21/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/21/2016 do fisrt create. </li>
 */
public class ThemeManager {

    private static ThemeManager INSTANCE = null;
    private List<OnUpdateThemeListener> updateThemeListeners = new ArrayList<>();

    /**
     * constructor
     */
    private ThemeManager() {
    }

    /**
     * create an instance of class ThemeManager.
     *
     * @return
     */
    public static ThemeManager newInstance() {
        if (INSTANCE == null) {
            INSTANCE = SingleHolder.MANAGER;
        }
        return INSTANCE;
    }

    /**
     * 根据天气值读取不同的颜色值
     *
     * @param weather 天气值，具体查看 {@link Weather} 类
     * @return int 格式颜色值
     */
    private int getThemeByWeather(String weather) {
        Theme theme = ThemeImp.newInstance();
        if (null == weather) {
            return theme.getDefaultTheme();
        }

        if (weather.equals(Weather.NIGHT)) {
            return theme.getDarkTheme();
        } else if (weather.equals(Weather.SUNNY) || weather.contains(Weather.SUNNY)) {
            return theme.getSunnyTheme();
        } else if (weather.equals(Weather.CLOUDY) || weather.contains(Weather.CLOUDY)) {
            return theme.getCloudyTheme();
        } else if (weather.equals(Weather.OVERCAST) || weather.contains(Weather.OVERCAST)) {
            return theme.getOvercastTheme();
        } else if (weather.equals(Weather.RAIN) || weather.contains(Weather.RAIN)) {
            return theme.getRainTheme();
        } else if (weather.equals(Weather.HAILSTONES) || weather.contains(Weather.HAILSTONES)) {
            return theme.getHailstonesTheme();
        } else if (weather.equals(Weather.SONW) || weather.contains(Weather.SONW)) {
            return theme.getSnowTheme();
        } else if (weather.equals(Weather.FOG) || weather.contains(Weather.FOG)) {
            return theme.getFogTheme();
        } else if (weather.equals(Weather.FOG_HAZE) || weather.contains(Weather.FOG_HAZE)) {
            return theme.getFogAnHazeTheme();
        } else if (weather.equals(Weather.SAND_STORM) || weather.contains(Weather.SAND_STORM)) {
            return theme.getSandstormTheme();
        }

        return theme.getDefaultTheme();
    }

    /**
     * 获取主题色
     *
     * @return
     */
    public void setTheme(String weather) {
        Logger.d("ThemeManager", "getTheme : " + weather);
        int theme = getThemeByWeather(weather);
        if (updateThemeListeners != null && updateThemeListeners.size() > 0) {
            for (OnUpdateThemeListener themeListener : updateThemeListeners) {
                themeListener.onUpdateTheme(theme);
            }
        }
    }

    public void setTheme(int colorResId) {
        Logger.d("ThemeManager", "getTheme : " + colorResId);
        if (updateThemeListeners != null && updateThemeListeners.size() > 0) {
            for (OnUpdateThemeListener themeListener : updateThemeListeners) {
                themeListener.onUpdateTheme(colorResId);
            }
        }
    }

    public void registerUpdateThemeListener(OnUpdateThemeListener updateThemeListener) {
        updateThemeListeners.add(updateThemeListener);
    }

    public void unRegisterUpdateThemeListener(OnUpdateThemeListener updateThemeListener) {
        updateThemeListeners.remove(updateThemeListener);
    }

    public interface OnUpdateThemeListener {
        void onUpdateTheme(int themeStyleId);
    }

    private static final class SingleHolder {
        private static final ThemeManager MANAGER = new ThemeManager();
    }


}
