package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/29
 * @Description
 */
public class SaveOrderRequest implements BaseGetRequest {
    private int orderType;
    private int userId;
    private DeviceInfoUtil.DeviceInfo info;
    private String duration;

    public SaveOrderRequest(int orderType, int userId, DeviceInfoUtil.DeviceInfo info, String duration) {
        this.orderType = orderType;
        this.userId = userId;
        this.info = info;
        this.duration = duration;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Log.i("getUrl",UrlMaker.getUrl(UrlFormatter.formatUrl(SAVE_ORDER,orderType,userId,info.mac,info.sn,info.deviceCode,duration)));
        return UrlMaker.getUrl(UrlFormatter.formatUrl(SAVE_ORDER,orderType,userId,info.mac,info.sn,info.deviceCode,duration));
    }
}
