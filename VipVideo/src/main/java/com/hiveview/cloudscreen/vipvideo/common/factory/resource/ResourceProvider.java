package com.hiveview.cloudscreen.vipvideo.common.factory.resource;

import com.hiveview.cloudscreen.vipvideo.common.factory.resource.imp.ChinaResource;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.imp.UsResource;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.manager.SystemInfoManager;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/6/13
 * @Description
 */
public class ResourceProvider {

    private static final String TAG = ResourceProvider.class.getSimpleName();
    private static IResource mResouce;

    public static void init() {
        buildResource();
    }

    public static IResource getInstance() {
        if (null != mResouce) {
            return mResouce;
        } else {
            mResouce = new ChinaResource();
            return mResouce;
        }
    }

    private static void buildResource() {
        //读取设备信息
        SystemInfoManager sm = SystemInfoManager.getSystemInfoManager();
        int resAreaInt;
        try {
            resAreaInt = sm.getSofewareInfo();
            Logger.d(TAG, "resAreaInt=" + resAreaInt);
        } catch (Exception | Error e) {
            resAreaInt = -1;
            e.printStackTrace();
            Logger.e(TAG, "e=" + e.toString());
        }
        //判断地域并初始化对应的资源类
        Logger.d(TAG, "resAreaInt=" + resAreaInt);
        switch (resAreaInt) {
            case 0:
                mResouce = new ChinaResource();
                break;
            case 1:
                mResouce = new ChinaResource();
                break;
            case 2:
                mResouce = new UsResource();
                break;
            default:
                mResouce = new ChinaResource();
        }
    }

}
