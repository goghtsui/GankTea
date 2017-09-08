package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/31.
 */
public class GetActivityRequest implements BaseGetRequest {

    private int templateId;
    private String apkPkgName;

    public GetActivityRequest(int templateId, String apkPkgName) {
        this.templateId = templateId;
        this.apkPkgName = apkPkgName;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Log.i("GetActivityRequest", "url:" + UrlMaker.getUrl(UrlFormatter.formatUrl(GET_ACTIVITY, templateId, apkPkgName)));
        return UrlMaker.getUrl(UrlFormatter.formatUrl(GET_ACTIVITY, templateId, apkPkgName));
    }
}
