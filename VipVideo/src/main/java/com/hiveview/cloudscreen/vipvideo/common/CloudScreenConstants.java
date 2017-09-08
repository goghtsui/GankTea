package com.hiveview.cloudscreen.vipvideo.common;

import com.hiveview.cloudscreen.vipvideo.service.net.RequestToolType;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

public interface CloudScreenConstants {
    /**
     * 当前生产环境
     */
    int ENVIRONMENT = Logger.DEBUG;
    /**
     * 当前网络请求使用的方式
     */
    RequestToolType REQUEST_TOOL_TYPE = RequestToolType.VOLLEY;
}