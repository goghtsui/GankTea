package com.gogh.afternoontea.http;

import com.gogh.afternoontea.request.Property;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 接口请求任务管理器，所有的网络请求都求该类发起</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
public class RequestTask {

    private static RequestTask INSTANCE = new RequestTask();

    private RequestTask() {
    }

    public static RequestTask getInstance() {
        if (null == INSTANCE) {
            INSTANCE = SingleHolder.TASK;
        }
        return INSTANCE;
    }

    private static final class SingleHolder {
        private static final RequestTask TASK = new RequestTask();
    }

    public Retrofit retrofit (List<Property> propertyList, String baseUrl, Converter.Factory convertFactory) {
        OkHttpClient client = new HttpClient.Builder()
                .addHeaders(propertyList).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(convertFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }


}
