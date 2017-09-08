package com.hiveview.cloudscreen.vipvideo.service.request;

import com.hiveview.cloudscreen.vipvideo.service.ApiConstants;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/10/31
 * @Description
 */
public class GetRemindStrategyRequest implements BaseGetRequest {

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(ApiConstants.GET_REMIND_STRATEGY);
        return url;
    }
}
