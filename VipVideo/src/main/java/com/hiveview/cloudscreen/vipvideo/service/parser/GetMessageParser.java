package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.MessageEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/29
 * @Description
 */
public class GetMessageParser extends BaseParser<ResultEntity<MessageEntity>, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity<MessageEntity> resultEntity = new ResultEntity<>();
        JSONObject json = new JSONObject(new String(result));
        Logger.d("GetMessageParser", "json=" + json);
        json = json.optJSONObject("data");
        List<MessageEntity> messageEntities = JSON.parseArray(json.optString("result"), MessageEntity.class);
        Logger.d("GetMessageParser", messageEntities.get(0).getMessageDesc());
        resultEntity.setList(messageEntities);
        return resultEntity;
    }
}
