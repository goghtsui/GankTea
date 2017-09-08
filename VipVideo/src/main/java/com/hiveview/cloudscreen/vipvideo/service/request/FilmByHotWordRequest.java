package com.hiveview.cloudscreen.vipvideo.service.request;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/17
 * @Description
 */
public class FilmByHotWordRequest implements BaseGetRequest {

    private int cid;
    private String contentType;
    private int pageNo;
    private int pageSize;

    public FilmByHotWordRequest(int cid, String contentType, int pageNo, int pageSize) {
        this.cid = cid;
        this.contentType = contentType;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return null;
    }
}
