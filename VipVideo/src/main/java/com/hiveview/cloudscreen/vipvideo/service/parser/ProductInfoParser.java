package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;


public class ProductInfoParser extends BaseParser<ResultEntity<ProductInfoEntity>, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = JSON.parseObject(new String(result));
        long longTime = json.getJSONObject("data").getLong("longTime");
        String resultStr = json.getJSONObject("data").getJSONObject("result").toString();
        ResultEntity resultEntity = new ResultEntity();
        ProductInfoEntity entity = JSON.parseObject(resultStr, ProductInfoEntity.class);
        entity.setLongTime(longTime);
        resultEntity.setEntity(entity);
        return resultEntity;
    }
}
