package com.gogh.glauncher.request

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/27/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/27/2017 do fisrt create. </li>
 */
class Path {
    companion object {

        const val DOMAIN = "http://api.openweathermap.org/"

        const val APP_ID = "4c611c35d833f16ecb07f313673ce9c6"
        /**
         * /data/2.5/weather?lat=39.9075&lon=116.3972&appid=4c611c35d833f16ecb07f313673ce9c6
         */
        const val PATH_DAY = "data/2.5/weather"

        /**
         * /data/2.5/forecast?&units=metric&lat=39.9075&lon=116.3972&appid=4c611c35d833f16ecb07f313673ce9c6
         */
        const val PATH_DAILY = "data/2.5/forecast"
    }
}