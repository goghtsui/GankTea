package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.common.AppConstants;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/29
 * @Description
 */
public class GetMessagesRequest implements BaseGetRequest {


    public GetMessagesRequest() {
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(String.format(GET_MESSAGES, AppConstants.APK_PACKAGE_NAME));
        return url;
    }
}
