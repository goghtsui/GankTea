package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/16
 * @TODO
 */
public class ChannelListRequest implements BaseGetRequest {

    private int templateId;

    private String apkBagName;

    public ChannelListRequest(int templateId, String apkBagName) {
        this.templateId = templateId;
        this.apkBagName = apkBagName;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(UrlFormatter.formatUrl(HIVEVIEW_API_GET_CHANNEL_LIST, templateId, apkBagName));
        Log.i("MainActivity", "firstClassListUrl:" + url);
        return url;
    }
}
