package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.service.ApiConstants;

import java.io.UnsupportedEncodingException;
import java.util.Random;


public class MoiveDetailAuthenticationRequest implements BaseGetRequest {

    private String apkBagName;

    private String userId;

    private String epgIds;

    private String sign;

    private String templetId;


    public MoiveDetailAuthenticationRequest(String templetId, String userId, String epgIds) {
        this.apkBagName = "com.hiveview.cloudscreen.vipvideo";
        this.userId = userId;
        this.epgIds = epgIds;
        sign = "12";//TODO 加密字段
        this.templetId = templetId;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = "";
        url = UrlMaker.getUrl(String.format(ApiConstants.GET_MOVIE_DETAILS_STATUS, templetId, apkBagName, epgIds, userId, sign));
        Log.d("test", "url=" + url);
        return url;
    }
}
