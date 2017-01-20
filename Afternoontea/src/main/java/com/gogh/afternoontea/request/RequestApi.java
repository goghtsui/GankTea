package com.gogh.afternoontea.request;

import android.support.annotation.NonNull;

import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.entity.gank.GankEntity;
import com.gogh.afternoontea.entity.weather.WeatherEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 接口请求地址，请求方式及参数组合</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public interface RequestApi {

    /**
     * 根据城市获取对应的天气信息
     * @param city 城市名称
     * @return 天气
     */
    @NonNull
    @GET(Urls.Weather.PATH)
    Observable<WeatherEntity> getWeatherByCity(@Query("cityip") String city);

    @NonNull
    @GET("page/2")
    Observable<String> getMeiziPic();

    @NonNull
    @GET("data/{category}/{num}/{page}")
    Observable<GankEntity> getDataByCategory(@Path("category") String category
            , @Path("num") int num, @Path("page") int page);

    @NonNull
    @GET("{params}")
    Observable<String> getHtmlByUrl(@Path("params") String path);

}
