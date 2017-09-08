package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ActivityListEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wangbei on 2015/11/18.
 */
public class ActivityListParser extends  BaseParser<ResultEntity,byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json=new JSONObject(new String(result));
        json=json.optJSONObject("data");
        ResultEntity resultEntity=new ResultEntity();
//        List<ActivityListEntity> entity= JSON.parseArray(json.optString("result").toString(), ActivityListEntity.class);
//        resultEntity.setList(entity);
        resultEntity.arg1 = json.getJSONObject("result").optInt("recCount");
        JSONArray object = json.getJSONObject("result").getJSONArray("pageContent");
        List<ActivityListEntity> list = JSON.parseArray(object.toString(), ActivityListEntity.class);
        Log.i("fragment", "list:" + list.toString());
        resultEntity.setList(list);
        return resultEntity;
    }
}
