package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectVideoSetVoEntity;

import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/17
 * @Description
 */
public class SubjectDetailParser extends BaseParser<ResultEntity, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        ResultEntity entity = new ResultEntity();
        List<SubjectEntity> subjectEntitys = JSON.parseArray(json.optString("result"), SubjectEntity.class);
        entity.setEntity(subjectEntitys.get(0));
        List<SubjectVideoSetVoEntity> subEntity = JSON.parseArray(json.optJSONArray("result").optJSONObject(0).optString("videosetList"), SubjectVideoSetVoEntity.class);
        entity.setList(subEntity);
        return entity;
    }
}
