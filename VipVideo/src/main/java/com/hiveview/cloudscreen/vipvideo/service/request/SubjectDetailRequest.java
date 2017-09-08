package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/17
 * @Description
 */
public class SubjectDetailRequest implements BaseGetRequest {
    private int subjectId;

    public SubjectDetailRequest(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {

        String Url = UrlMaker.getUrl(UrlFormatter.formatUrl(SUBJECT_DETAIL, subjectId));
        Log.i("SubjectDetailUrl", "url=" + Url);
        return Url;
    }
}
