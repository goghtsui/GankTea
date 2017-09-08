package com.gogh.afternoontea.theme;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 根据天气显示不同的主题色，如果是晚上一律使用暗色系</p>
 * <p> Created by <b>高晓峰</b> on 12/21/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/21/2016 do fisrt create. </li>
 */
interface Theme {

    /**
     *  晴天主题
     * @return
     */
    int getSunnyTheme();//晴

    /**
     *  多云主题
     * @return
     */
    int getCloudyTheme();// 多云

    /**
     *  阴天主题
     * @return
     */
    int getOvercastTheme();// 阴

    /**
     * 雨主题
     * @return
     */
    int getRainTheme();// 雨

    /**
     * 冰雹主题
     * @return
     */
    int getHailstonesTheme();//  冰雹

    /**
     * 雪主题
     * @return
     */
    int getSnowTheme();// 雪

    /**
     * 雾主题
     * @return
     */
    int getFogTheme();// 雾

    /**
     * 雾霾主题
     * @return
     */
    int getFogAnHazeTheme();// 雾霾

    /**
     * 沙尘主题
     * @return
     */
    int getSandstormTheme();// 沙尘

    /**
     *  夜间主题
     * @return
     */
    int getDarkTheme();// 夜间

    int getHotTheme();// 热

    /**
     *  在没有网络的情况下，使用默认的主题色
     * @return
     */
    int getDefaultTheme();// 默认
}
