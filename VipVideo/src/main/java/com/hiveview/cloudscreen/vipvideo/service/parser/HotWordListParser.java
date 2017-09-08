package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.HotWordEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/17
 * @Description
 */
public class HotWordListParser extends BaseParser<ResultEntity, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        List<HotWordEntity> hotWordEntities = JSON.parseArray(json.optString("result").toString(),HotWordEntity.class);
        ResultEntity entity = new ResultEntity();
        entity.setList(hotWordEntities);
        return entity;
    }
}
