package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.FreeActivityEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.GetActivityEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SaveOrderEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/30
 * @Description
 */
public class FreeActivityParser extends BaseParser<GetActivityEntity, byte[]> {

    @Override
    public GetActivityEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        Log.i("getActivity", "json=" + json);
        json = json.optJSONObject("data");
        GetActivityEntity entitys = JSON.parseObject(json.optString("result"), GetActivityEntity.class);
        return entitys;
    }
}
