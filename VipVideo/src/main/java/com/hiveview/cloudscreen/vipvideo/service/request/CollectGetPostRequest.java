package com.hiveview.cloudscreen.vipvideo.service.request;

import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.New;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/19
 * @Description
 */
public class CollectGetPostRequest extends CollectBasePostRequest {

    private static final String USER_ID = "userId";

    private static final String PAGE_NO = "pageNo";

    private static final String PAGE_SIZE = "pageSize";

    private String userId;

    private int pageNo;

    private int pageSize;

    public CollectGetPostRequest(String userId, int pageNo, int pageSize) {
        this.userId = userId;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, Object> params = New.newHashMap();
        params.put(USER_ID, userId);
        params.put(PAGE_NO, pageNo);
        params.put(PAGE_SIZE, pageSize);

        Map<String, String> parametersMap = New.newHashMap();
        parametersMap.put(PARAMS, getParamsString(params));
        parametersMap.put(CLIENT_ID, clientId);
        parametersMap.put(CLIENT_SECRET, clientSecret);
        return parametersMap;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return UrlMaker.getCollectUrl(COLLECT_GET_DATA);
    }
}
