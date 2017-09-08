package com.hiveview.cloudscreen.vipvideo.service.request;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/12.
 */
public interface BasePostRequest extends BaseRequest {
    Map<String, String> getParams();
}
