package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONObject;

/**
 * Created by wangbei on 2015/11/18.
 */
public class VideoDetailParser extends BaseParser<ResultEntity, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {

        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        ResultEntity resultEntity = new ResultEntity();
        AlbumEntity entity = JSON.parseObject(json.optString("result"), AlbumEntity.class);
        resultEntity.setEntity(entity);
        return resultEntity;
    }
}
