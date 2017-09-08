package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoChannelEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/16
 * @TODO
 */
public class ChannelListParser extends BaseParser<ResultEntity, byte[]> {

    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity<VideoChannelEntity> entity = new ResultEntity<>();
        JSONObject json = new JSONObject(new String(result));
        json=json.optJSONObject("data");
        List<VideoChannelEntity> list = JSON.parseArray(json.optJSONArray("result").toString(), VideoChannelEntity.class);
        entity.setList(list);
        return entity;
    }
}
