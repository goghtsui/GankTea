package com.hiveview.cloudscreen.vipvideo.service.parser;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/3/28
 * @Description
 */
public class DeviceCodeParser extends BaseParser<String,byte[]> {

    @Override
    public String parseJSON(byte[] result) throws Exception {
        return new String(result);
    }
}
