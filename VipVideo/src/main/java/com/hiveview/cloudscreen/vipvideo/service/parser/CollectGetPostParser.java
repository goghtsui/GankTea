package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.CollectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/19
 * @Description
 */
public class CollectGetPostParser extends BaseParser<ResultEntity<CollectEntity>, byte[]> {
    private static final String TAG = CollectGetPostParser.class.getSimpleName();

    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity<CollectEntity> resultEntity = new ResultEntity<>();
        JSONObject json = new JSONObject(new String(result));
        Logger.d(TAG, "json=" + json.toString());
        resultEntity.errorCode = json.getString("code");
        resultEntity.str2 = json.getString("message");
        List<CollectEntity> lists = JSON.parseArray(json.getString("result"), CollectEntity.class);
        resultEntity.setList(lists);
        return resultEntity;
    }
}
