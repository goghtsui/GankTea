package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangbei on 2015/11/24.
 */
public class PricePkgPostRequest implements BaseGetRequest {

    private int templetId;

    public PricePkgPostRequest(int templetId) {
        this.templetId = templetId;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Log.i("getUrl::", UrlMaker.getUrl(UrlFormatter.formatUrl(VIPGOODS,templetId)));
        return UrlMaker.getUrl(UrlFormatter.formatUrl(VIPGOODS,templetId));
    }
}
