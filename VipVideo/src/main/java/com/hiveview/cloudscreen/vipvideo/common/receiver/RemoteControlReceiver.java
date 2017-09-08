package com.hiveview.cloudscreen.vipvideo.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @Title CloudScreenNPlayer
 * @Auther Spr_ypt
 * @Date 2015/12/23
 * @Description
 */
public class RemoteControlReceiver extends BroadcastReceiver {
    private static final String TAG = RemoteControlReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "RemoteControlReceiver.onReceive");
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
