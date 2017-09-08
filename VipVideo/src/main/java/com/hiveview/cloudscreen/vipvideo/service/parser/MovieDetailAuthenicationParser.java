package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.FilmAuthenticationEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.OnDemandResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class MovieDetailAuthenicationParser extends BaseParser<ResultEntity<FilmAuthenticationEntity>, byte[]> {
    @Override
    public ResultEntity<FilmAuthenticationEntity> parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result)).getJSONObject("data");
        FilmAuthenticationEntity entity = new FilmAuthenticationEntity();
        ResultEntity resultEntity = new ResultEntity();
        entity.setCode(json.getString("code"));
        entity.setBackgroundPic(json.getString("backgroundPic"));
        entity.setStringTime(json.getString("stringTime"));
        entity.setSuccess(json.getBoolean("success"));
        JSONObject jsonObject = json.optJSONObject("result");
        if (null != jsonObject) {
            JSONArray jsonArray = jsonObject.getJSONArray("dianboList");
            if (null != jsonArray) {
                String onDemand = jsonArray.toString();
                if (!TextUtils.isEmpty(onDemand)) {
                    List<OnDemandResultEntity> onDemandResultEntitiesList = JSON.parseArray(onDemand, OnDemandResultEntity.class);
                    entity.setDianboList(onDemandResultEntitiesList);
                }
            }
        }

        resultEntity.setEntity(entity);
        return resultEntity;
    }
}
