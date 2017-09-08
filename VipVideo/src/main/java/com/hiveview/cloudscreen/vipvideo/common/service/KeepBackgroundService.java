package com.hiveview.cloudscreen.vipvideo.common.service;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class KeepBackgroundService extends Service {
    private final static String TAG = "HiveviewDogService";

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Notification notification = new Notification();
        notification.tickerText = "hiveview memory service low ...";
        this.startForeground(254, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
