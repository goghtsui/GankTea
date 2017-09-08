package com.hiveview.cloudscreen.vipvideo.service.request;

import com.hiveview.cloudscreen.vipvideo.util.New;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/19
 * @Description
 */
public class CollectDeletePostRequest extends CollectBasePostRequest {
    private static final String USER_ID = "userId";
    private static final String COLLECT_ID = "collectId";

    private String userId;
    private Integer collectId;

    public CollectDeletePostRequest(String userId, Integer collectId) {
        this.userId = userId;
        this.collectId = collectId;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, Object> params = New.newHashMap();
        params.put(USER_ID, userId);
        if (null != collectId) {
            params.put(COLLECT_ID, collectId);
        }

        Map<String, String> parametersMap = New.newHashMap();
        parametersMap.put(PARAMS, getParamsString(params));
        parametersMap.put(CLIENT_ID, clientId);
        parametersMap.put(CLIENT_SECRET, clientSecret);
        return parametersMap;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return UrlMaker.getCollectUrl(COLLECT_DELETE_DATA);
    }
}
