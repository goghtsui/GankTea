package com.gogh.afternoontea.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gogh.afternoontea.request.Property;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: 本地自定义httpclient，可以通过内部类Builder，添加不同的header值</p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */
class HttpClient {

    private static HttpClient INSTANCE;

    @Nullable
    private List<Property> properties;

    private HttpClient() {
    }

    protected static HttpClient newInstance() {
        if (null == INSTANCE) {
            INSTANCE = SingleHolder.CLIENT;
        }
        return INSTANCE;
    }

    @NonNull
    protected okhttp3.OkHttpClient build(@Nullable Builder builder) {

        if (null != builder) {
            this.properties = builder.properties;
        }

        okhttp3.OkHttpClient.Builder httpClient = new okhttp3.OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {

            Request original = chain.request();
            Request.Builder builder1 = original.newBuilder();

            if (null != properties) {
                for (Property property : properties) {
                    builder1.addHeader(property.getKey(), property.getValue());
                }
            }

            Request request = builder1.method(original.method(), original.body())
                    .build();

            Response response = chain.proceed(request);
            return response;
        });

        return httpClient.build();
    }

    private static final class SingleHolder {
        private static final HttpClient CLIENT = new HttpClient();
    }

    /**
     * 内部类，用于给http请求添加header属性
     */
    static class Builder {

        @Nullable
        private List<Property> properties;

        Builder() {
            if (null != properties) {
                properties.clear();
            }
        }

        @NonNull
        Builder addHeaders(@Nullable List<Property> propertyList) {
            if (propertyList == null) {
                throw new NullPointerException("addHeaders(List<Property>) : The value of param is null, plear ensure you pass an avaible value. ");
            }
            if (null == properties) {
                this.properties = propertyList;
            } else {
                properties.clear();
                properties.addAll(propertyList);
            }
            return this;
        }

        @NonNull
        Builder addHeader(@Nullable Property property) {
            if (property == null) {
                throw new NullPointerException("addProperty(Property) : The value of param is null, plear ensure you pass an avaible value. ");
            }
            if (null == properties) {
                properties = new ArrayList<>();
            }
            properties.add(property);
            return this;
        }

        @NonNull
        okhttp3.OkHttpClient build() {
            return HttpClient.newInstance().build(this);
        }
    }


}
