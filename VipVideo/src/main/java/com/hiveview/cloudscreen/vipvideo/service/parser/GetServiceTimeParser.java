package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.MessageEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ServiceTimeEntity;
import com.hiveview.cloudscreen.vipvideo.service.request.BaseGetRequest;

import org.json.JSONObject;

/**
 * Created by tianyejun on 2017/1/20.
 */

public class GetServiceTimeParser extends BaseParser<ResultEntity<ServiceTimeEntity>, byte[]> {
    @Override
    public ResultEntity<ServiceTimeEntity> parseJSON(byte[] result) throws Exception {

        JSONObject json = new JSONObject(new String(result));
        ResultEntity<ServiceTimeEntity> resultEntity = new ResultEntity<ServiceTimeEntity>();
        ServiceTimeEntity entity = JSON.parseObject(json.optJSONObject("data").toString(), ServiceTimeEntity.class);
        resultEntity.setEntity(entity);
        return resultEntity;
    }
}
