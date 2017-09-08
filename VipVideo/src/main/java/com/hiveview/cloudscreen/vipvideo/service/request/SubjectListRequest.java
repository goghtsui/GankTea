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
public class SubjectListRequest implements BaseGetRequest {
    private int templateId;
    private String apkBagName;
    private int pageNo;
    private int pageSize;

    public SubjectListRequest(int templateId, String apkBagName, int pageNo, int pageSize) {
        this.templateId = templateId;
        this.apkBagName = apkBagName;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(UrlFormatter.formatUrl(SUBJECT_LIST, templateId, apkBagName, pageNo, pageSize));
        Log.i("SubjectListUrl","url=" + url);
        return url;
    }
}
