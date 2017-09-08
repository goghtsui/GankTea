package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.HotWordsContentEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/24
 * @Description
 */
public class HotWordsContentParser extends BaseParser<ResultEntity<HotWordsContentEntity>, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity<HotWordsContentEntity> entity = new ResultEntity<>();
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        entity.arg1 = json.optInt("count");
        JSONArray object = json.getJSONArray("result");
        List<HotWordsContentEntity> list = JSON.parseArray(object.toString(), HotWordsContentEntity.class);
        entity.setList(list);
        return entity;
    }
}
