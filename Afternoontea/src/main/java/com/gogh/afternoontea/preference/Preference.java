package com.gogh.afternoontea.preference;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/12/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/12/2017 do fisrt create. </li>
 */

public interface Preference {

    /**
     *  是否开启天气主题
     * @return
     */
    boolean isWeatherTheme();

    /**
     * 是否开启夜间主题
     * @return
     */
    boolean isNightTheme();

    /**
     * 是否开启夜间模式
     * @return
     */
    boolean isNightMode();

    /**
     * 是否开启无图模式
     * @return
     */
    boolean isNopicMode();

    /**
     * 是否开启仅WIFI下显示图片
     * @return
     */
    boolean isWifiPicMode();

    /**
     * 是否开启卡片模式
     * @return
     */
    boolean isCardMode();

    void setTheme(int theme);

    int getTheme();

}
