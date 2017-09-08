package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wangbei on 2015/11/18.
 */
public class EpisodeListParser extends BaseParser<ResultEntity, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        JSONArray array = json.getJSONObject("result").getJSONArray("pageContent");
        ArrayList<EpisodeListEntity> entity = (ArrayList<EpisodeListEntity>) JSON.parseArray(array.toString(), EpisodeListEntity.class);
        if (entity.size() > 0) {
            for (int i = 0; i < entity.size(); i++) {
                if (entity.get(i).getPhase() <= 0) {
                    entity.get(i).setPhase((i + 1) + "");
                }
            }
        }
        resultEntity.setList(entity);
        return resultEntity;
    }
}
