package com.gogh.afternoontea.utils;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.constant.Urls;
import com.gogh.afternoontea.log.Logger;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/6/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/6/2017 do fisrt create. </li>
 */
public class Resource {

    private static final String TAG = "Resource";

    public static int getResIdByType(String type) {
        Logger.d(TAG, "getTypeResId : " + type);
        switch (type) {
            case Urls.GANK_URL.ANDROID:
                return R.drawable.ic_android;
            case Urls.GANK_URL.IOS:
                return R.drawable.ic_iphone;
            case Urls.GANK_URL.WEB:
                return R.drawable.ic_web;
            case Urls.GANK_URL.WELFARE:
                return R.drawable.ic_image;
            case Urls.GANK_URL.REST_VIDEO:
                return R.drawable.ic_movies;
            case Urls.GANK_URL.MATERIAL:
                return R.drawable.ic_power;
            case Urls.GANK_URL.APP:
                return R.drawable.ic_smartphone;
            case Urls.GANK_URL.RECOMMEND:
                return R.drawable.ic_recommend;
        }

        Logger.d(TAG, "getTypeResId error : " + type);
        return R.drawable.ic_default;
    }

}
