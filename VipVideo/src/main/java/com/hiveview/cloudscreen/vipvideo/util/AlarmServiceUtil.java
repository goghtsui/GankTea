/**
 * @Title AlarmServiceUtil.java
 * @Package com.hiveview.testtip
 * @author haozening
 * @date 2016-10-28 下午8:50:04
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author haozening
 * @ClassName AlarmServiceUtil
 * @Description
 * @date 2016-10-28 下午8:50:04
 */
public class AlarmServiceUtil {


    private static final String TAG = "AlarmServiceUtil";
    private static final String ACTION = "com.hiveview.service.ALARM_KNOCK";
    private static final String PACKAGES = "com.hiveview.cloudscreen.tip";

    private Messenger serviceMessenger;
    private AlarmServiceConnection connection = new AlarmServiceConnection();

    public static final int LOCATION_TOP = -1;
    public static final int LOCATION_MIDDLE = 0;
    public static final int LOCATION_BOTTOM = 1;
    private static final int REMIND = 0;
    private static final int CANCEL = 1;

    private static final class AlarmServiceConnectionHolder {
        private static final AlarmServiceUtil util = new AlarmServiceUtil();
    }

    private AlarmServiceUtil() {
    }

    public static AlarmServiceUtil getInstant() {
        return AlarmServiceConnectionHolder.util;
    }

    /**
     * 设置提醒
     *
     * @throws RemoteException 发送失败异常，调用处处理
     * @Title send
     * @author haozening
     * @Description
     */
    public void remind(Context context, Bundle bundle) throws RemoteException {
        sendMessage(context, bundle, REMIND);
    }

    /**
     * 取消提醒
     *
     * @param context
     * @param bundle
     * @throws RemoteException
     * @Title cancel
     * @author haozening
     * @Description
     */
    public void cancel(Context context, Bundle bundle) throws RemoteException {
        sendMessage(context, bundle, CANCEL);
    }

    private void sendMessage(Context context, Bundle bundle, int what) throws RemoteException {
        synchronized (this) {
            if (null == bundle) {
                Log.e(TAG, "sendMessage数据为空，预约提醒失败！请保证含有正确数据内容");
                return;
            }
            Message message = Message.obtain();
            message.what = what;
            message.setData(bundle);
            if (serviceMessenger == null) {
                Log.e(TAG, "service 尚未连接成功，提醒预约失败！请保证service绑定成功并成功连接");
            } else {
                serviceMessenger.send(message);
            }
        }
    }

    private class AlarmServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            serviceMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceMessenger = null;
        }

    }

    public synchronized void bindService(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION);
        intent.setPackage(PACKAGES);
        if (null == context) {
            Log.e(TAG, "Context为空bindService失败！");
        } else {
            boolean bind = context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
            Log.d(TAG, "bindService = " + bind);
        }
    }

    public synchronized void unbindService(Context context) {
        if (null == context) {
            Log.e(TAG, "Context为空unbindService失败！");
        } else {
            context.unbindService(connection);
        }
    }
}
