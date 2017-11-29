package com.gogh.glauncher.request.api

import com.gogh.glauncher.bean.daily.DailyBean
import com.gogh.glauncher.bean.day.DayBean
import com.gogh.glauncher.request.Path
import com.gogh.glauncher.request.RequestApi
import com.gogh.okrxretrofit.HttpClient
import com.gogh.okrxretrofit.request.OnResponseListener
import rx.Observable

/**
 * Copyright (c) 2017 All Rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 11/27/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 11/27/2017 do fisrt create. </li>
 */
internal class RequestImp : Request {

    override fun getDayWeather(lat: String, lon: String, onResponseListener: OnResponseListener<DayBean>) {
        val request = HttpClient.newInstance().getRequestApi(RequestApi::class.java)
        val dayWeather: Observable<DayBean> = request.getDayWeather(lat, lon, Path.APP_ID)
        com.gogh.okrxretrofit.request.Request.get().request(
                dayWeather, object : OnResponseListener<DayBean> {
            /**
             * 数据解析完成
             * @param t
             */
            override fun onNext(t: DayBean?) {
                onResponseListener.onNext(t)
            }

            /**
             * 请求接口异常或解析异常
             * @param e
             */
            override fun onError(e: Throwable?) {
                onResponseListener.onError(e)
            }

            /**
             * 数据请求成功且解析完成
             */
            override fun onCompleted() {
                onResponseListener.onCompleted()
            }

        })
    }

    override fun getDailyWeather(lat: String, lon: String, onResponseListener: OnResponseListener<DailyBean>) {
        val request = HttpClient.newInstance().getRequestApi(RequestApi::class.java)
        val dailyWeather: Observable<DailyBean> = request.getDailyWeather("metric", lat, lon, Path.APP_ID)
        com.gogh.okrxretrofit.request.Request.get().request(
                dailyWeather, object : OnResponseListener<DailyBean> {
            /**
             * 数据解析完成
             * @param t
             */
            override fun onNext(t: DailyBean?) {
                onResponseListener.onNext(t)
            }

            /**
             * 请求接口异常或解析异常
             * @param e
             */
            override fun onError(e: Throwable?) {
                onResponseListener.onError(e)
            }

            /**
             * 数据请求成功且解析完成
             */
            override fun onCompleted() {
                onResponseListener.onCompleted()
            }
        })
    }

}
