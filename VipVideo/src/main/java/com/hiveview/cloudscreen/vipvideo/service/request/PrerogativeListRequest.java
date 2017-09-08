package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangbei on 2015/11/18.
 */
public class PrerogativeListRequest implements BaseGetRequest {


    public PrerogativeListRequest() {
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(UrlFormatter.formatUrl(PREROGATIVE_LIST));
        Log.i("getUrl", "url:" + url);
        return UrlMaker.getUrl(UrlFormatter.formatUrl(PREROGATIVE_LIST));

    }
}
