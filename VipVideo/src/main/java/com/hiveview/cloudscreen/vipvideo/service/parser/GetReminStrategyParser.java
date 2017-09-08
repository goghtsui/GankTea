package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.RemindStrategyEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONObject;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/10/31
 * @Description
 */
public class GetReminStrategyParser extends BaseParser<ResultEntity<RemindStrategyEntity>, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        RemindStrategyEntity entity = JSON.parseObject(json.optString("result"), RemindStrategyEntity.class);
        ResultEntity<RemindStrategyEntity> resultEntity = new ResultEntity<>();
        resultEntity.setEntity(entity);
        return resultEntity;
    }
}
