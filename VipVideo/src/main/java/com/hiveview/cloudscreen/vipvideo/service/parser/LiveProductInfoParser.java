package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveDetailEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2017/3/2
 * @Description
 */

public class LiveProductInfoParser extends BaseParser<ResultEntity<LiveDetailEntity>, byte[]> {
    private LiveDetailEntity entity;

    public LiveProductInfoParser(LiveDetailEntity entity) {
        this.entity = entity;
    }

    @Override
    public ResultEntity<LiveDetailEntity> parseJSON(byte[] result) throws Exception {
        JSONObject json = JSON.parseObject(new String(result));
        Log.d("4test", "FilmProductInfoParser json=" + json.toString());
        ResultEntity<LiveDetailEntity> resultEntity = new ResultEntity<LiveDetailEntity>();
        String info = json.getJSONObject("data").getString("result");
        Log.d("4test", "FilmProductInfoParser info=" + info);
        if (info.contains("{") && info.contains("}")) {
            String album = JSON.toJSONString(entity);
            String sum = album.substring(0, album.lastIndexOf("}")) + "," + info.substring(info.indexOf("{") + 1, info.length());
            LiveDetailEntity entity = com.alibaba.fastjson.JSONObject.parseObject(sum, LiveDetailEntity.class);
            resultEntity.setEntity(entity);
        } else {
            resultEntity.setEntity(this.entity);
        }
        return resultEntity;
    }
}
