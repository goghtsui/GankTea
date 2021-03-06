package com.gogh.afternoontea.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.gogh.afternoontea.preference.PreferenceManager;
import com.gogh.afternoontea.preference.imp.Configuration;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 1/13/2017. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 1/13/2017 do fisrt create. </li>
 */
public class NetWorkChangedReceiver extends BroadcastReceiver {

    private int netStatus = -100;

    @Override
    public void onReceive(@NonNull Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            if(networkInfo.isConnected() && networkInfo.isAvailable()){
                if (netStatus != networkInfo.getType() && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    if(Configuration.newInstance().isWifiPicMode()){
                        PreferenceManager.newInstance().notifyCardModeChanged();
                    }
                }
                netStatus = networkInfo.getType();
            }
        }
    }

}
