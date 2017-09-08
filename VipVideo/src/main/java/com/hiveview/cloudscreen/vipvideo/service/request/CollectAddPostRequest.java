package com.hiveview.cloudscreen.vipvideo.service.request;

import com.hiveview.cloudscreen.vipvideo.service.entity.CollectEntity;
import com.hiveview.cloudscreen.vipvideo.util.New;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/19
 * @Description
 */
public class CollectAddPostRequest extends CollectBasePostRequest {
    private static final String USER_ID = "userId";
    private static final String COLLECT_ID = "collectId";
    private static final String NAME = "name";
    private static final String CID = "cid";
    private static final String CPID = "cpId";
    private static final String EPISODE_UPDATED = "episodeUpdated";
    private static final String EPISODE_TOTAL = "episodeTotal";
    private static final String PIC_URL = "picUrl";
    private static final String BLUE_RAY_IMG = "blueRayImg";
    private static final String CORNER_PIC = "cornerPic";
    private static final String COLLECT_TIME = "collectTime";

    private String userId;
    private CollectEntity entity;

    public CollectAddPostRequest(String userId, CollectEntity entity) {
        this.userId = userId;
        this.entity = entity;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, Object> params = New.newHashMap();
        params.put(USER_ID, userId);
        params.put(COLLECT_ID, entity.getCollectId());
        params.put(NAME, entity.getName());
        params.put(CID, entity.getCid());
        params.put(CPID, entity.getCpId());
        params.put(EPISODE_UPDATED, entity.getEpisodeUpdate());
        params.put(EPISODE_TOTAL, entity.getEpisodeTotal());
        params.put(PIC_URL, entity.getPicUrl());
        params.put(BLUE_RAY_IMG, entity.getBlueRayImg());
        params.put(CORNER_PIC, entity.getCornerPic());
        params.put(COLLECT_TIME, entity.getCollectTime());

        Map<String, String> parametersMap = New.newHashMap();
        parametersMap.put(PARAMS, getParamsString(params));
        parametersMap.put(CLIENT_ID, clientId);
        parametersMap.put(CLIENT_SECRET, clientSecret);
        return parametersMap;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return UrlMaker.getCollectUrl(COLLECT_ADD_DATA);
    }
}
