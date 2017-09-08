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
 * @Description 获取电影列表的命令请求
 */
public class FilmRequest implements BaseGetRequest {
    private int channelId;
    private int channelType;
    private int pageSize;
    private int pageNumber;

    public FilmRequest(int channelId, int channelType, int pageSize, int pageNumber) {
        this.channelId = channelId;
        this.channelType = channelType;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getUrl(UrlFormatter.formatUrl(FILM_LIST,
                DeviceInfoUtil.getInstance().getDeviceInfo(CloudScreenApplication.getInstance().getApplicationContext()).templetId,
                AppConstants.APK_PACKAGE_NAME, channelId, channelType, pageSize, pageNumber));
        return url;
    }
}
