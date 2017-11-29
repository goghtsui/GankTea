package com.gogh.glauncher.request

import com.gogh.glauncher.bean.daily.DailyBean
import com.gogh.glauncher.bean.day.DayBean
import retrofit2.http.GET
import rx.Observable

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/27/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/27/2017 do fisrt create. </li>
 */
interface RequestApi {

    @GET(Path.PATH_DAY)
    fun getDayWeather(@retrofit2.http.Query("lat") lat: String,
                      @retrofit2.http.Query("lon") lon: String,
                      @retrofit2.http.Query("appid") appid: String): Observable<DayBean>

    @GET(Path.PATH_DAILY)
    fun getDailyWeather(@retrofit2.http.Query("units") units: String,
                        @retrofit2.http.Query("lat") lat: String,
                        @retrofit2.http.Query("lon") lon: String,
                        @retrofit2.http.Query("appid") appid: String): Observable<DailyBean>
}