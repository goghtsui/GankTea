package com.gogh.afternoontea.utils;

import android.support.annotation.NonNull;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Urls;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public class Resource {

    private static final String TAG = "Resource";

    public static int getResIdByType(@NonNull String type) {
        Logger.d(TAG, "getTypeResId : " + type);
        switch (type) {
            case Urls.GANK_URL.ANDROID:
                return R.drawable.ic_type_android;
            case Urls.GANK_URL.IOS:
                return R.drawable.ic_type_iphone;
            case Urls.GANK_URL.WEB:
                return R.drawable.ic_type_web;
            case Urls.GANK_URL.WELFARE:
                return R.drawable.ic_type_image;
            case Urls.GANK_URL.REST_VIDEO:
                return R.drawable.ic_type_movies;
            case Urls.GANK_URL.MATERIAL:
                return R.drawable.ic_type_power;
            case Urls.GANK_URL.APP:
                return R.drawable.ic_type_smartphone;
            case Urls.GANK_URL.RECOMMEND:
                return R.drawable.ic_type_recommend;
        }

        Logger.d(TAG, "getTypeResId error : " + type);
        return R.drawable.ic_type_default;
    }

}
