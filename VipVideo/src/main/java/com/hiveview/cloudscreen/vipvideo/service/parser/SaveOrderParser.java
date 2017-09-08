package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.SaveOrderEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import org.json.JSONObject;


/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/29
 * @Description
 */
public class SaveOrderParser extends BaseParser<SaveOrderEntity, byte[]> {
    private static final String TAG = SaveOrderParser.class.getSimpleName();
    @Override
    public SaveOrderEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        Logger.d(TAG, "json=" + json.toString());
        json = json.optJSONObject("data");
        SaveOrderEntity entity = JSON.parseObject(json.optString("result"), SaveOrderEntity.class);
        return entity;
    }
}
