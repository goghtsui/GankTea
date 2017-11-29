package com.gogh.glauncher.model

import com.gogh.glauncher.bean.daily.DailyBean
import com.gogh.glauncher.bean.day.DayBean
import com.gogh.glauncher.request.api.RequestProxy
import com.gogh.okrxretrofit.request.OnResponseListener
import com.gogh.okrxretrofit.util.Logger

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/27/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/27/2017 do fisrt create. </li>
 */
class WeatherModel {

    companion object {
        val TAG = "WeatherModel"
        val DAY = 1
        val DAILY = 2
    }

    fun load(lat: String, lon: String, from: Int) {
        when (from) {
            DAY -> loadDay(lat, lon)
            DAILY -> loadDaily(lat, lon)
            else -> Logger.d(TAG, "error type for request.")
        }
    }

    private fun loadDay(lat: String, lon: String) {
        RequestProxy().getDayWeather(lat, lon, object : OnResponseListener<DayBean>{
            /**
             * 请求接口异常或解析异常
             * @param e
             */
            override fun onError(e: Throwable?) {
                Logger.d(TAG, "loadDay onError")
            }

            /**
             * 数据解析完成
             * @param t
             */
            override fun onNext(t: DayBean?) {
                Logger.d(TAG, "loadDay onNext " + t?.toString())
            }

            /**
             * 数据请求成功且解析完成
             */
            override fun onCompleted() {
                Logger.d(TAG, "loadDay onCompleted")
            }

        })
    }

    private fun loadDaily(lat: String, lon: String) {
        RequestProxy().getDailyWeather(lat, lon, object : OnResponseListener<DailyBean>{
            /**
             * 请求接口异常或解析异常
             * @param e
             */
            override fun onError(e: Throwable?) {
                Logger.d(TAG, "loadDaily onError")
            }

            /**
             * 数据解析完成
             * @param t
             */
            override fun onNext(t: DailyBean?) {
                Logger.d(TAG, "loadDaily onNext " + t?.toString())
            }

            /**
             * 数据请求成功且解析完成
             */
            override fun onCompleted() {
                Logger.d(TAG, "loadDaily onCompleted")
            }
        })
    }
}