package com.gogh.fortest.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/6/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/6/2016 do fisrt create. </li>
 */
public class SecretService extends BroadcastReceiver {

    private static final String ACTION = "android.provider.Telephony.SECRET_CODE";
    private static final String SECRET_CODE = "2016";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION)){
            if(intent.getData().getHost().equals(SECRET_CODE)){
                Intent target = new Intent(context, ScrollingActivity.class);
                target.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(target);
            }
        }
    }

    private void startCode(Context context, String code){
       /* String secretCode = "123456789";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:##" + secretCode + "##"));
        startActivity(intent);*/

        Uri uri = Uri.parse("android_secret_code://" + code);
        Intent intent = new Intent(ACTION, uri);
        context.sendBroadcast(intent);
    }

}
