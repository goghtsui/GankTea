package com.hiveview.cloudscreen.vipvideo.service.request;

import com.hiveview.cloudscreen.vipvideo.service.entity.GetActivityEntity;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.New;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/11
 * @Description
 */
public class FreeActivityPostRequest implements BasePostRequest {
    private static final String TAG = FreeActivityPostRequest.class.getSimpleName();
    private static final String PARTNER_KEY = "863b4ec37d93eb96276ca74d04edf66f";

    private UserStateUtil.UserInfo userInfo;
    private DeviceInfoUtil.DeviceInfo deviceInfo;
    GetActivityEntity entity;

    public FreeActivityPostRequest(GetActivityEntity entity, UserStateUtil.UserInfo userInfo, DeviceInfoUtil.DeviceInfo deviceInfo) {
        this.entity = entity;
        this.userInfo = userInfo;
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        Logger.d(TAG, "url=" + UrlMaker.getActivityUrl(FREE_ACTIVTY_POST));
        return UrlMaker.getActivityUrl(FREE_ACTIVTY_POST);
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> parametersMap = New.newHashMap();
        parametersMap.put("templateId", "" + deviceInfo.templetId);
        parametersMap.put("apkPkgName", "com.hiveview.cloudscreen.vipvideo");
        parametersMap.put("mac", deviceInfo.mac);
        parametersMap.put("sn", deviceInfo.sn);
        parametersMap.put("devicecode", deviceInfo.deviceCode);
        parametersMap.put("duration", entity.getDuration() + "");
        parametersMap.put("userId", userInfo.userId);
        parametersMap.put("activityId", entity.getId() + "");
        parametersMap.put("activityName", entity.getName());
        parametersMap.put("conditionType", entity.getConditionType() + "");
        parametersMap.put("activityType", entity.getActivityType() + "");
        if (entity.getActivityType() == 1) {
            parametersMap.put("durationYear", entity.getDurationYear() + "");
            parametersMap.put("durationMonth", entity.getDurationMonth() + "");
            parametersMap.put("durationDay", entity.getDurationDay() + "");
            parametersMap.put("durationEnd", entity.getDurationEnd());
        } else {
            parametersMap.put("durationEnd", entity.getDurationEnd());
        }
        parametersMap.put("orderType","1");
        String sign = getSign(parametersMap, PARTNER_KEY);//加密获取sign值
        parametersMap.put("sign", sign);
        Logger.i(TAG, "post=" + buildQuery(parametersMap));
        return parametersMap;
    }


    static String getSign(Map<String, String> orderMap, String key) {
        String _params = buildQuery(orderMap);
        String data = _params + "&key=" + key;
        System.out.println("data=" + data);
        String sign = DigestUtils.md5Hex(data);
        return sign;
    }

    private static String buildQuery(Map<String, String> map) {
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
