package com.hiveview.cloudscreen.vipvideo.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/30
 * @Description
 */
public class UserRefreshReceiver extends BroadcastReceiver {
    private static final String TAG = UserRefreshReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "接收广播");
        String userinfo = intent.getStringExtra("user_info");
        String vipUserInfo = intent.getStringExtra("vip_user_info");
        Logger.i(TAG, "null != userinfo::" + (null != userinfo));
        if (null != userinfo && !"".equals(userinfo)) {
            Logger.i(TAG, "userinfo=" + userinfo);
            UserStateUtil.getInstance().notifyReFreshUserState(context);
            Log.i(TAG, "更新用户状态");
        }

    }
}
