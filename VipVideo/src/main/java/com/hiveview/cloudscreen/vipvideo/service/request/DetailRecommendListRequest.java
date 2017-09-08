package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangbei on 2015/11/25.
 */
public class DetailRecommendListRequest implements BaseGetRequest {

    private int id;
    private int cid;

    public DetailRecommendListRequest(int id, int cid) {
        this.id = id;
        this.cid = cid;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Log.i("getUrl", UrlFormatter.formatUrl(UrlMaker.getRecommendUrl(DETAIL_RECOMMEND_LIST), id,cid));
        return UrlFormatter.formatUrl(UrlMaker.getRecommendUrl(DETAIL_RECOMMEND_LIST), id,cid);
    }
}
