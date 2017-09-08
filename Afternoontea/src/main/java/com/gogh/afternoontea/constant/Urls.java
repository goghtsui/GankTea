package com.gogh.afternoontea.constant;

/**
 * Copyright (c) 2016 All rights reserved by gaoxiaofeng
 * <p> Description: </p>
 * <p> Created by <b>高晓峰</b> on 12/22/2016. </p>
 * <p> ChangeLog: </p>
 * <li> 高晓峰 on 12/22/2016 do fisrt create. </li>
 */

public interface Urls {

    /**
     * https://api.seniverse.com/v3/weather/now.json?key=klxvmngb60g6ickb&location=ip&language=zh-Hans&unit=c
     */
    interface Weather {
        String KEY = "klxvmngb60g6ickb";
        String LANGUAGE = "zh-Hans";
        String UNIT = "c";
        String BASE_URL = "https://api.seniverse.com/";
        String PATH = "v3/weather/now.json";
    }

    interface Meizi {
        String BASE_URL = "http://www.mzitu.com/";
    }

    interface GANK_URL {
        String BASE_URL = "http://gank.io/api/";

        String BUNDLE_KEY = "detail_info";

        String APP = "App";

        String RECOMMEND = "瞎推荐";

        String ALL = "all";
        String IOS = "iOS";
        String WEB = "前端";
        String WELFARE = "福利";
        String ANDROID = "Android";
        String MATERIAL = "拓展资源";
        String REST_VIDEO = "休息视频";
    }
}
