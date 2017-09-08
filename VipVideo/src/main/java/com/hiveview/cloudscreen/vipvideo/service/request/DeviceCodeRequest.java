package com.hiveview.cloudscreen.vipvideo.service.request;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/3/28
 * @Description
 */
public class DeviceCodeRequest implements BaseGetRequest {

    private String mac;

    private String sn;

    public DeviceCodeRequest(String mac, String sn) {
        this.mac = mac;
        this.sn = sn;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return UrlFormatter.formatUrl(UrlMaker.getDeviceUrl(GET_DEVICE_CODE), mac, sn);
    }
}
