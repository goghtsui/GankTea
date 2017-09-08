package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * Created by wangbei on 2015/11/18.
 */
public class EpisodeListRequest implements BaseGetRequest {

    private Integer videoset_id;
    private Integer cid;
    private String stype;
    private Integer page_number;
    private Integer page_size;


    public EpisodeListRequest(Integer videoset_id, Integer cid, String stype, Integer page_number, Integer page_size) {
        this.videoset_id = videoset_id;
        this.cid = cid;
        if (null != stype && !"null".equals(stype)) {
            this.stype = stype;
        } else {
            this.stype = "";
        }
        this.page_number = page_number;
        this.page_size = page_size;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String episode = UrlMaker.getUrl(UrlFormatter.formatUrl(VIDEO_LIST, videoset_id, cid, stype, page_number, page_size));
        Log.i("getUrl", "episode url:" + episode);
//        return UrlMaker.getUrl(UrlFormatter.formatUrl(VIDEO_LIST, videoset_id, cid, stype, page_number, page_size));
        return episode;
    }
}
