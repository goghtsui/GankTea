package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.LiveStatusEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONObject;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/11/2
 * @Description
 */
public class LiveStatusParser extends BaseParser<ResultEntity<LiveStatusEntity>, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        JSONObject json = new JSONObject(new String(result));
        Log.d("4test", "json=" + json.toString());
        ResultEntity<LiveStatusEntity> resultEntity = new ResultEntity();
        String liveStatus = JSON.parseObject(json.optJSONArray("result").get(0).toString()).getString("parmVo");
        LiveStatusEntity entity = new LiveStatusEntity();
        if (null != liveStatus) {
            resultEntity.arg1 = 1;
            entity = JSON.parseObject(liveStatus, LiveStatusEntity.class);
        } else {
            resultEntity.arg1 = 0;
            entity.setIsPay(JSON.parseObject(json.optJSONArray("result").get(0).toString()).getInteger("isFree").toString());
            entity.setTvId(JSON.parseObject(json.optJSONArray("result").get(0).toString()).getInteger("tvId"));
            entity.setOrderStatus(JSON.parseObject(json.optJSONArray("result").get(0).toString()).getInteger("isOrder").toString());
        }
        resultEntity.setEntity(entity);
        return resultEntity;
    }
}
