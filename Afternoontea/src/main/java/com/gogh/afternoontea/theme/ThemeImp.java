package com.gogh.afternoontea.theme;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.log.Logger;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 根据当前的天气返回不同配色的主题，晚上时间统一夜间模式</p>
 * <p> Created by <b>高晓峰</b> on 12/21/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/21/2016 do fisrt create. </li>
 */
class ThemeImp implements Theme {

    private static final String TAG = "ThemeImp";

    private static ThemeImp MANAGER = null;

    private ThemeImp() {
        Logger.d(TAG, "ThemeImp constructor method..");
    }

    static ThemeImp newInstance() {
        if (MANAGER == null) {
            MANAGER = SingleHolder.MANAGER;
        }
        return MANAGER;
    }

    /**
     * 晴天主题
     *
     * @return
     */
    @Override
    public int getSunnyTheme() {
        return R.color.colorSunny;
    }

    /**
     * 多云主题
     *
     * @return
     */
    @Override
    public int getCloudyTheme() {
        return R.color.colorCloudy;
    }

    /**
     * 阴天主题
     *
     * @return
     */
    @Override
    public int getOvercastTheme() {
        return R.color.colorOvercast;
    }

    /**
     * 雨主题
     *
     * @return
     */
    @Override
    public int getRainTheme() {
        return R.color.colorRain;
    }

    /**
     * 冰雹主题
     *
     * @return
     */
    @Override
    public int getHailstonesTheme() {
        return R.color.colorHailstones;
    }

    /**
     * 雪主题
     *
     * @return
     */
    @Override
    public int getSnowTheme() {
        return R.color.colorSnow;
    }

    /**
     * 雾主题
     *
     * @return
     */
    @Override
    public int getFogTheme() {
        return R.color.colorFog;
    }

    /**
     * 雾霾主题
     *
     * @return
     */
    @Override
    public int getFogAnHazeTheme() {
        return R.color.colorFogAndHaze;
    }

    /**
     * 沙尘主题
     *
     * @return
     */
    @Override
    public int getSandstormTheme() {
        return R.color.colorSandstorm;
    }

    /**
     * 夜间主题
     *
     * @return
     */
    @Override
    public int getDarkTheme() {
        return R.color.colorDark;
    }

    /**
     * 在没有网络的情况下，使用默认的主题色
     *
     * @return
     */
    @Override
    public int getDefaultTheme() {
        return R.color.colorDefault;
    }

    private static final class SingleHolder {
        private static final ThemeImp MANAGER = new ThemeImp();
    }

}
