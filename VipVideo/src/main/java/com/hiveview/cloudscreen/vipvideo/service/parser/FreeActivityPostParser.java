package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONObject;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/11
 * @Description
 */
public class FreeActivityPostParser extends BaseParser<ResultEntity, byte[]> {

    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        JSONObject json = new JSONObject(new String(result));
        Log.i("FreeActivityPostParser", "json=" + json);
        JSONObject data = json.getJSONObject("data");
        json = null != data ? data : json;
        resultEntity.str2 = json.getString("code");
        resultEntity.errorCode = json.getString("desc");
        return resultEntity;
    }
}
