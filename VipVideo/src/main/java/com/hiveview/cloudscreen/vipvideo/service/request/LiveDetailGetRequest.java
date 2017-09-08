package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.service.ApiConstants;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;

import java.io.UnsupportedEncodingException;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/12/7
 * @Description
 */

public class LiveDetailGetRequest implements BaseGetRequest {

    private String tvId;
    private String templeteId;
    private String apkBagName;
    private String pagNo;
    private String pageSize;

    public LiveDetailGetRequest(String tvId) {
        this.tvId = tvId;
        templeteId = DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext()).templetId + "";
        templeteId="";//可以不用传
        apkBagName = AppConstants.APK_PACKAGE_NAME;
        apkBagName="";//可以不用传
        pagNo = "1";
        pageSize = "10";
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(String.format(ApiConstants.GET_LIVE_CHANNEL_DETAIL, tvId, templeteId, apkBagName, pagNo, pageSize));
        return url;
    }
}
