package com.gogh.afternoontea.theme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.location.Weather;
import com.gogh.afternoontea.log.Logger;
import com.gogh.afternoontea.main.ATApplication;

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

    @Nullable
    private static ThemeManager INSTANCE = null;
    @NonNull
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
    @Nullable
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
    private int getThemeByWeather(@Nullable String weather) {
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
        int themeColor = getThemeByWeather(weather);
        if (updateThemeListeners != null && updateThemeListeners.size() > 0) {
            for (OnUpdateThemeListener themeListener : updateThemeListeners) {
                themeListener.onUpdateTheme(themeColor);
            }
        }
    }

    public void setThemeColor(int colorResId) {
        Logger.d("ThemeManager", "setTheme color : " + colorResId);
        if (updateThemeListeners != null && updateThemeListeners.size() > 0) {
            for (OnUpdateThemeListener themeListener : updateThemeListeners) {
                themeListener.onUpdateTheme(colorResId);
            }
        }
    }

    public void setThemeStyle(int styleResId) {
        Logger.d("ThemeManager", "getTheme style : " + styleResId);
        int colorResId = getColorByTheme(styleResId);
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

    /**
     * 根据选择的颜色设置对应的主题
     *
     * @param context
     * @param selectedColor 选择的颜色
     */
    public void updateThemeByColor(@NonNull Context context, int selectedColor) {
        if (selectedColor == ContextCompat.getColor(context, R.color.colorBluePrimary)) {
            ATApplication.THEME = R.style.BlueTheme;
            context.setTheme(R.style.BlueTheme);
            this.setThemeColor(R.color.colorBluePrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorRedPrimary)) {
            ATApplication.THEME = R.style.RedTheme;
            context.setTheme(R.style.RedTheme);
            this.setThemeColor(R.color.colorRedPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorBrownPrimary)) {
            ATApplication.THEME = R.style.BrownTheme;
            context.setTheme(R.style.BrownTheme);
            this.setThemeColor(R.color.colorBrownPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorGreenPrimary)) {
            ATApplication.THEME = R.style.GreenTheme;
            context.setTheme(R.style.GreenTheme);
            this.setThemeColor(R.color.colorGreenPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorPurplePrimary)) {
            ATApplication.THEME = R.style.PurpleTheme;
            context.setTheme(R.style.PurpleTheme);
            this.setThemeColor(R.color.colorPurplePrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorTealPrimary)) {
            ATApplication.THEME = R.style.TealTheme;
            context.setTheme(R.style.TealTheme);
            this.setThemeColor(R.color.colorTealPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorPinkPrimary)) {
            ATApplication.THEME = R.style.PinkTheme;
            context.setTheme(R.style.PinkTheme);
            this.setThemeColor(R.color.colorPinkPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorDeepPurplePrimary)) {
            ATApplication.THEME = R.style.DeepPurpleTheme;
            context.setTheme(R.style.DeepPurpleTheme);
            this.setThemeColor(R.color.colorDeepPurplePrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorOrangePrimary)) {
            ATApplication.THEME = R.style.OrangeTheme;
            context.setTheme(R.style.OrangeTheme);
            this.setThemeColor(R.color.colorOrangePrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorIndigoPrimary)) {
            ATApplication.THEME = R.style.IndigoTheme;
            context.setTheme(R.style.IndigoTheme);
            this.setThemeColor(R.color.colorIndigoPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorLightGreenPrimary)) {
            ATApplication.THEME = R.style.LightGreenTheme;
            context.setTheme(R.style.LightGreenTheme);
            this.setThemeColor(R.color.colorLightGreenPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorDeepOrangePrimary)) {
            ATApplication.THEME = R.style.DeepOrangeTheme;
            context.setTheme(R.style.DeepOrangeTheme);
            this.setThemeColor(R.color.colorDeepOrangePrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorLimePrimary)) {
            ATApplication.THEME = R.style.LimeTheme;
            context.setTheme(R.style.LimeTheme);
            this.setThemeColor(R.color.colorLimePrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorBlueGreyPrimary)) {
            ATApplication.THEME = R.style.BlueGreyTheme;
            context.setTheme(R.style.BlueGreyTheme);
            this.setThemeColor(R.color.colorBlueGreyPrimary);
        } else if (selectedColor == ContextCompat.getColor(context, R.color.colorCyanPrimary)) {
            ATApplication.THEME = R.style.CyanTheme;
            context.setTheme(R.style.CyanTheme);
            this.setThemeColor(R.color.colorCyanPrimary);
        }
    }

    public  int getColorByTheme(int theme){
        switch (theme) {// weather
            case R.style.SunnyTheme:
                return R.color.colorSunnyPrimary;
            case R.style.CloudyTheme:
                return R.color.colorCloudyPrimary;
            case R.style.OvercastTheme:
                return R.color.colorOvercastPrimary;
            case R.style.RainTheme:
                return R.color.colorRainPrimary;
            case R.style.HailstonesTheme:
                return R.color.colorHailstonesPrimary;
            case R.style.SnowTheme:
                return R.color.colorSnowPrimary;
            case R.style.FogTheme:
                return R.color.colorFogPrimary;
            case R.style.FogAndHazeTheme:
                return R.color.colorFogAndHazePrimary;
            case R.style.SandstormTheme:
                return R.color.colorSandstormPrimary;
            case R.style.DarkTheme:
                return R.color.colorDarkPrimary;
            case R.style.DefaultTheme:
                return R.color.colorDefaultPrimary;
            // custom
            case R.style.BlueTheme:
                return R.color.colorBluePrimary;
            case R.style.RedTheme:
                return R.color.colorRedPrimary;
            case R.style.BrownTheme:
                return R.color.colorBrownPrimary;
            case R.style.GreenTheme:
                return R.color.colorGreenPrimary;
            case R.style.PurpleTheme:
                return R.color.colorPurplePrimary;
            case R.style.TealTheme:
                return R.color.colorTealPrimary;
            case R.style.PinkTheme:
                return R.color.colorPinkPrimary;
            case R.style.DeepPurpleTheme:
                return R.color.colorDeepPurplePrimary;
            case R.style.OrangeTheme:
                return R.color.colorOrangePrimary;
            case R.style.IndigoTheme:
                return R.color.colorIndigoPrimary;
            case R.style.LightGreenTheme:
                return R.color.colorLightGreenPrimary;
            case R.style.DeepOrangeTheme:
                return R.color.colorDeepOrangePrimary;
            case R.style.LimeTheme:
                return R.color.colorLimePrimary;
            case R.style.BlueGreyTheme:
                return R.color.colorBlueGreyPrimary;
            case R.style.CyanTheme:
                return R.color.colorCyanPrimary;
            default:
                return R.color.colorDefaultPrimary;
        }
    }

    /**
     * 根据天气类型设置相应的主题
     *
     * @param context
     * @param weatherColor 天气对应的主题色
     */
    public void updateThemeByWeather(@NonNull Context context, int weatherColor) {
        Theme theme = ThemeImp.newInstance();

        if (weatherColor == theme.getSunnyTheme()) {
            ATApplication.THEME = R.style.SunnyTheme;
            context.setTheme(R.style.SunnyTheme);
        } else if (weatherColor == theme.getCloudyTheme()) {
            ATApplication.THEME = R.style.CloudyTheme;
            context.setTheme(R.style.CloudyTheme);
        } else if (weatherColor == theme.getOvercastTheme()) {
            ATApplication.THEME = R.style.OvercastTheme;
            context.setTheme(R.style.OvercastTheme);
        } else if (weatherColor == theme.getRainTheme()) {
            ATApplication.THEME = R.style.RainTheme;
            context.setTheme(R.style.RainTheme);
        } else if (weatherColor == theme.getHailstonesTheme()) {
            ATApplication.THEME = R.style.HailstonesTheme;
            context.setTheme(R.style.HailstonesTheme);
        } else if (weatherColor == theme.getSnowTheme()) {
            ATApplication.THEME = R.style.SnowTheme;
            context.setTheme(R.style.SnowTheme);
        } else if (weatherColor == theme.getFogTheme()) {
            ATApplication.THEME = R.style.FogTheme;
            context.setTheme(R.style.FogTheme);
        } else if (weatherColor == theme.getFogAnHazeTheme()) {
            ATApplication.THEME = R.style.FogAndHazeTheme;
            context.setTheme(R.style.FogAndHazeTheme);
        } else if (weatherColor == theme.getSandstormTheme()) {
            ATApplication.THEME = R.style.SandstormTheme;
            context.setTheme(R.style.SandstormTheme);
        } else if (weatherColor == theme.getDarkTheme()) {
            ATApplication.THEME = R.style.DarkTheme;
            context.setTheme(R.style.DarkTheme);
        } else if (weatherColor == theme.getDefaultTheme()) {
            ATApplication.THEME = R.style.DefaultTheme;
            context.setTheme(R.style.DefaultTheme);
        }

    }

    public interface OnUpdateThemeListener {
        void onUpdateTheme(int themeColor);
    }

    private static final class SingleHolder {
        private static final ThemeManager MANAGER = new ThemeManager();
    }

}
