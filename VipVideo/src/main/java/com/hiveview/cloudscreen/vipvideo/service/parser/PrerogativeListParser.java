package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.PrerogativeListEntity;

import org.json.JSONObject;

import java.util.List;

/**
 * vip特权信息
 * Created by wangbei on 2015/11/18.
 */
public class PrerogativeListParser extends BaseParser<ResultEntity, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        ResultEntity resultEntity = new ResultEntity();
        List<PrerogativeListEntity> listEntity = JSON.parseArray(json.optString("result").toString(), PrerogativeListEntity.class);
        Log.i("json", "list:" + listEntity.toString());
        resultEntity.setList(listEntity);

        return resultEntity;
    }

//        resultEntity.arg1 = json.getJSONObject("result").optInt("recCount");
//        JSONArray object = json.getJSONObject("result").getJSONArray("pageContent");
//        List<PrerogativeListEntity> list = JSON.parseArray(object.toString(), PrerogativeListEntity.class);
//        Log.i("fragment","list:" +list.toString());
//        resultEntity.setList(list);
}
