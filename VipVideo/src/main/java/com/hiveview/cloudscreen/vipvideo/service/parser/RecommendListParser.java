package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecommendListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/18
 * @Description
 */
public class RecommendListParser extends BaseParser<ResultEntity<RecommendListEntity>, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        ResultEntity entity = new ResultEntity();
        List<RecommendListEntity> entitys = JSON.parseArray(json.optJSONObject("result").optString("pageContent"), RecommendListEntity.class);
        entity.setList(entitys);
        return entity;
    }
}
