package com.hiveview.cloudscreen.vipvideo.service.request;

import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/19
 * @Description
 */
public abstract class CollectBasePostRequest implements BasePostRequest {

    protected static final String PARAMS = "params";

    protected static final String CLIENT_ID = "clientId";

    protected static final String CLIENT_SECRET = "clientSecret";

    protected String clientId = "hiveview";

    protected String clientSecret = "31871fa18f49742f95295ef7fe5d3550";

    protected String getParamsString(Map<String, Object> params) {
        return JSON.toJSONString(params);
    }
}
