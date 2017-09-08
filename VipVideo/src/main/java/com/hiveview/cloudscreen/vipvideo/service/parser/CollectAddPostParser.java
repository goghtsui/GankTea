package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import org.json.JSONObject;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/19
 * @Description
 */
public class CollectAddPostParser extends BaseParser<ResultEntity, byte[]> {
    private static final String TAG =CollectAddPostParser.class.getSimpleName() ;

    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        JSONObject json = new JSONObject(new String(result));
        Logger.d(TAG,"json="+json.toString());
        resultEntity.errorCode = json.getString("code");
        resultEntity.str2 = json.getString("message");
        return resultEntity;
    }
}
