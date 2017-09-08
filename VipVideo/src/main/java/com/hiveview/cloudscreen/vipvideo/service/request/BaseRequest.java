package com.hiveview.cloudscreen.vipvideo.service.request;


import com.hiveview.cloudscreen.vipvideo.service.ApiConstants;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/10/12.
 */
public interface BaseRequest extends ApiConstants {
    public String getRequestUrl() throws UnsupportedEncodingException;
}
