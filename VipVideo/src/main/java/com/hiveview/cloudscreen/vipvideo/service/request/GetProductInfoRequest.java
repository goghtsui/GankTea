package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * 根据产品ID获取商品信息request
 */

public class GetProductInfoRequest implements BaseGetRequest {
    private int templetId;
    private String apkName = "com.hiveview.cloudscreen.vipvideo";
    private int videosetId;
    private int cid;

    public GetProductInfoRequest(int templetId, int videosetId, int cid) {
        this.templetId = templetId;
        this.videosetId = videosetId;
        this.cid = cid;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Log.i("getUrl", UrlMaker.getUrl(UrlFormatter.formatUrl(GET_PRODUCTINFO, templetId, apkName, videosetId, cid)));
        return UrlMaker.getUrl(UrlFormatter.formatUrl(GET_PRODUCTINFO, templetId, apkName, videosetId, cid));
    }
}
