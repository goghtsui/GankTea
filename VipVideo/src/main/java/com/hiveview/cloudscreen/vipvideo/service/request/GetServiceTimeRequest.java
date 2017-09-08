package com.hiveview.cloudscreen.vipvideo.service.request;

import android.content.UriMatcher;

import java.io.UnsupportedEncodingException;

/**
 * 获取服务器时间
 */

public class GetServiceTimeRequest implements BaseGetRequest {
    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return UrlMaker.getUrl(GET_SERVICETIME);
    }
}
