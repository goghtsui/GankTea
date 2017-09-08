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
 * @Date 2015/11/17
 * @Description
 */
public class HotWordListRequest implements BaseGetRequest {
    private int cid;

    public HotWordListRequest(int cid) {
        this.cid = cid;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(UrlFormatter.formatUrl(HOTWORD_LIST, DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext()).templetId, AppConstants.APK_PACKAGE_NAME, cid));
        Log.d("4test", "url=" + url);
        return url;
    }
}
