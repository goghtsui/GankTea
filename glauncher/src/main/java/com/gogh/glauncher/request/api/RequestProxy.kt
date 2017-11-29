package com.gogh.glauncher.request.api

import com.gogh.glauncher.bean.daily.DailyBean
import com.gogh.glauncher.bean.day.DayBean
import com.gogh.okrxretrofit.request.OnResponseListener

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/27/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/27/2017 do fisrt create. </li>
 */
internal class RequestProxy : Request {

    override fun getDayWeather(lat: String, lon: String, onResponseListener: OnResponseListener<DayBean>) {
        RequestImp().getDayWeather(lat, lon, onResponseListener)
    }

    override fun getDailyWeather(lat: String, lon: String, onResponseListener: OnResponseListener<DailyBean>) {
        RequestImp().getDailyWeather(lat, lon, onResponseListener)
    }


}