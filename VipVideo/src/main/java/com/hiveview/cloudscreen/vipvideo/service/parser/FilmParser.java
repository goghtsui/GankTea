package com.hiveview.cloudscreen.vipvideo.service.parser;

import com.alibaba.fastjson.JSON;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/17
 * @Description
 */
public class FilmParser extends BaseParser<ResultEntity<AlbumEntity>, byte[]> {
    @Override
    public ResultEntity parseJSON(byte[] result) throws Exception {
        ResultEntity<AlbumEntity> entity = new ResultEntity<AlbumEntity>();
        JSONObject json = new JSONObject(new String(result));
        json = json.optJSONObject("data");
        entity.arg1 = json.optInt("count");
        JSONArray object = json.getJSONArray("result");
        List<AlbumEntity> list = JSON.parseArray(object.toString(), AlbumEntity.class);
        entity.setList(list);

        return entity;
    }
}
