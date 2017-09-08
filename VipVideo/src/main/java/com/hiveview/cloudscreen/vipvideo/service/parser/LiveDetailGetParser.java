package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveDetailEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;


/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/12/7
 * @Description
 */

public class LiveDetailGetParser extends BaseParser<ResultEntity<LiveDetailEntity>, byte[]> {
    @Override
    public ResultEntity<LiveDetailEntity> parseJSON(byte[] result) throws Exception {
        JSONObject json = JSON.parseObject(new String(result));
        Log.d("4test","LiveDetailGetParser json="+json.toString());
        String detail = json.getJSONObject("data").getJSONObject("result").getJSONArray("pageContent").get(0).toString();
        ResultEntity<LiveDetailEntity> resultEntity = new ResultEntity();
        LiveDetailEntity entity = JSON.parseObject(detail, LiveDetailEntity.class);
        resultEntity.setEntity(entity);
        return resultEntity;
    }
}
