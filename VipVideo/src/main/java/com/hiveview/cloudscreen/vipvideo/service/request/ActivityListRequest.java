package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangbei on 2015/11/18.
 */
public class ActivityListRequest implements BaseGetRequest {

    private Integer isVip;
    private Integer actionId;
    private Integer pageNo;
    private Integer pageSize;

    public ActivityListRequest( Integer isVip, Integer actionId, Integer pageNo, Integer pageSize) {
        this.isVip = isVip;
        this.actionId = actionId;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }


    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Log.i("getUrl","url:"+UrlMaker.getUrl(UrlFormatter.formatUrl(ACTIVITY_LIST, isVip,actionId, pageNo, pageSize)));
        return UrlMaker.getUrl(UrlFormatter.formatUrl(ACTIVITY_LIST,isVip,actionId, pageNo, pageSize));
    }
}
