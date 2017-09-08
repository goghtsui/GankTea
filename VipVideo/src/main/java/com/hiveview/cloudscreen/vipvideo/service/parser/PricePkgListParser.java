package com.hiveview.cloudscreen.vipvideo.service.parser;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.SubjectVideoSetVoEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VIpGoodsContentApiVo;
import com.hiveview.cloudscreen.vipvideo.service.entity.VipGoodsApiVo;
import com.hiveview.cloudscreen.vipvideo.service.entity.VipProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.util.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by wangbei on 2015/11/24.
 */
public class PricePkgListParser extends BaseParser<ResultEntity, byte[]> {
    private static final String TAG = PricePkgListParser.class.getSimpleName();

    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {

        ResultEntity resultEntity = new ResultEntity();
        JSONObject json = new JSONObject(new String(result));
        Logger.d(TAG, "json=" + json);
        JSONObject data = json.optJSONObject("data");
        json = null != data ? data : json;

        List<VipGoodsApiVo> vipGoodsApiVoList = JSON.parseArray(json.optString("result"), VipGoodsApiVo.class);
        if (vipGoodsApiVoList.size() > 0) {
            Log.i(TAG, "vipGoodsApiVoList.size > 0");
            resultEntity.setEntity(vipGoodsApiVoList.get(0));
            List<VIpGoodsContentApiVo> vipGoodsContentApiVoList = JSON.parseArray(json.optJSONArray("result").optJSONObject(0).optString("contentList"), VIpGoodsContentApiVo.class);
            resultEntity.setList(vipGoodsContentApiVoList);
        } else {
            if (null != json.getString("backgroundPic")) {
                resultEntity.str2 = json.getString("backgroundPic");
                Log.i(TAG,  "str2:" + resultEntity.str2);
            }
        }
        return resultEntity;
    }
}
