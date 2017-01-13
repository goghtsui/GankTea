package com.gogh.afternoontea.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/13/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/13/2017 do fisrt create. </li>
 */
public class NetWorkInfo {

    private Context context;
    private static ConnectivityManager connectivityManager;

    public NetWorkInfo(Context context) {
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isConnected(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
       if(networkInfo != null){
           return networkInfo.isAvailable() && networkInfo.isConnected();
       }
        return false;
    }

    public static boolean isWifi(){
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            return networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
        }
        return false;
    }

}
