package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.UrlFormatter;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/20
 * @Description
 */
public class HotWordContentRequest implements BaseGetRequest {
    private int wordId;
    private int cid;
    private int pageNo;
    private int pageSize;

    public HotWordContentRequest(int wordId, int cid, int pageNo, int pageSize) {
        this.wordId = wordId;
        this.cid = cid;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(UrlFormatter.formatUrl(HOTWORD_CONTENT,
                DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext()).templetId
                , AppConstants.APK_PACKAGE_NAME, cid, wordId, pageNo, pageSize));
        return url;
    }
}
