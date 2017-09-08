package com.hiveview.cloudscreen.vipvideo.service.request;

import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.service.ApiConstants;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/11/2
 * @Description
 */
public class LiveStatusRequest implements BaseGetRequest {
    /**
     * TAG
     */
    private static final String TAG = LiveStatusRequest.class.getSimpleName();
    /**
     * 加密常量
     */
    private static final String LIVE_TOKEN = "851b7dd48148b8f3ed013943da35cbdb";

    private String mac;

    private String sn;

    private String userId;

    private String channelId;

    private String DmID;

    private String random;

    private String sign;

    public LiveStatusRequest(String mac, String sn, String userId, String channelId, String dmID) {
        this.mac = mac.replaceAll(":", "");
        this.sn = sn;
        this.userId = userId;
        this.channelId = channelId;
        DmID = dmID + "";
        random = System.currentTimeMillis() + new Random().nextInt(10000) + "";//随机数
        sign = createSign();
    }


    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        String url = UrlMaker.getRecommendUrl(String.format(ApiConstants.GET_LIVE_CHANNEL_STATUS, mac, sn, userId, channelId, DmID, random, sign));
        return url;
    }


    private String createSign() {
        HashMap<String, String> orderMap = new HashMap<String, String>();
        orderMap.put("mac", this.mac);
        orderMap.put("sn", this.sn);
        orderMap.put("random", random);
        try {
            return toLinkForNotify(orderMap, LIVE_TOKEN);
        } catch (UnsupportedEncodingException e) {
            Log.e(TAG, "createSign error=" + e.toString());
            e.printStackTrace();
        }
        return "";
    }

    private String toLinkForNotify(Map<String, String> orderMap, String partnerKey) throws UnsupportedEncodingException {
        //生成通知参数
        String _params = buildQuery(orderMap);
        Log.i(TAG, "request params: " + _params);
        //生成通知签名
        String data = _params + "&partnerKey=" + partnerKey;
        return DigestUtils.md5Hex(data.getBytes("UTF-8"));
    }

    private String buildQuery(Map<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        StringBuffer query = new StringBuffer();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = map.get(key);
            if (value == null || value.length() == 0) {
                continue;
            }
            query.append("&").append(key);
            query.append("=").append(value);
        }
        return query.substring(1);
    }
}
