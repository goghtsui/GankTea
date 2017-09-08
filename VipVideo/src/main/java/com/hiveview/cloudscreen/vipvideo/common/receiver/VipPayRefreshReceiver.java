package com.hiveview.cloudscreen.vipvideo.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

/**
 * @Title CloudScreenVIPVideo
 * @Auther wangbei
 * @Date 2017/01/11
 * @Description
 */
public class VipPayRefreshReceiver extends BroadcastReceiver {
    private static final String TAG = VipPayRefreshReceiver.class.getSimpleName();
    private final String IS_PAY_SUCCESS = "isSuccess";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "支付成功后接收广播");
        if (intent.getBooleanExtra(IS_PAY_SUCCESS, false)) {
            UserStateUtil.getInstance().notifyReFreshUserState(context);
            Log.i(TAG, "更新用户状态");
        }

    }
}
