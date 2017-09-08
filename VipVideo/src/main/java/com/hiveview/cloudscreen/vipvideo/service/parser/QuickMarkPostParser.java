package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.QuickMarkEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONObject;

/**
 * @Title CloudScreenVIPVideo
 * @Auther wangbei
 * @Date 2016/3/18
 * @Description
 */
public class QuickMarkPostParser extends BaseParser<ResultEntity, byte[]> {

    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        JSONObject json = new JSONObject(new String(result));
        Log.i("QuickMarkPostParser", "json:" + json);
        QuickMarkEntity freeActivityOrderEntity = JSON.parseObject(json.getString("msg"), QuickMarkEntity.class);
        resultEntity.setEntity(freeActivityOrderEntity);
        return resultEntity;
    }
}
