package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.DetailRecommendListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wangbei on 2015/11/18.
 */
public class DetailRecommendListParser extends BaseParser<ResultEntity, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {

        ResultEntity resultEntity=new ResultEntity();
        JSONObject object = new JSONObject(new String(result));
        JSONArray json = object.optJSONArray("result");
        List<DetailRecommendListEntity> list=JSON.parseArray(json.toString(),DetailRecommendListEntity.class);
        Log.i("recomment_url","entity:"+list);
        resultEntity.setList(list);

        return resultEntity;
    }
}
