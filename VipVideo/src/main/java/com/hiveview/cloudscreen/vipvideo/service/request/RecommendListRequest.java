package com.hiveview.cloudscreen.vipvideo.service.request;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/18
 * @Description
 */
public class RecommendListRequest implements BaseGetRequest {
    private int isVip;
    private int pageNo;
    private int pageSize;
    private int templateId;

    public RecommendListRequest(int isVip, int pageNo, int pageSize) {
        this.isVip = isVip;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.templateId = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext()).templetId;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return UrlMaker.getUrl(UrlFormatter.formatUrl(RECOMMEND_LIST, templateId, isVip, pageNo, pageSize));
    }
}
