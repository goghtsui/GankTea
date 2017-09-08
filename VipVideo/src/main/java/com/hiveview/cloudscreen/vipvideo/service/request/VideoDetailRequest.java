package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * 根据产品ID获取商品信息request
 */
public class VideoDetailRequest implements BaseGetRequest {

    private Integer videoset_id;
    private int temptedId;
    private String packageName = "com.hiveview.cloudscreen.vipvideo";

    public VideoDetailRequest(int temptedId, int videoset_id) {
        this.temptedId = temptedId;
        this.videoset_id = videoset_id;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Log.i("getUrl", UrlMaker.getUrl(UrlFormatter.formatUrl(FILM_DETAIL, temptedId, packageName, videoset_id)));
        return UrlMaker.getUrl(UrlFormatter.formatUrl(FILM_DETAIL, temptedId, packageName, videoset_id));
    }
}
