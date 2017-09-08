package com.hiveview.cloudscreen.vipvideo.service.request;

import android.text.TextUtils;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.New;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import org.apache.commons.codec.digest.DigestUtils;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * @Title CloudScreenVIPVideo
 * @Auther wangbei
 * @Date 2016/3/18
 * @Description
 */
public class QuickMarkPostRequest implements BasePostRequest {
    private static final String TAG = QuickMarkPostRequest.class.getSimpleName();
    private UserStateUtil.UserInfo userInfo;
    private DeviceInfoUtil.DeviceInfo deviceInfo;
    String socketUrl;
    private static final String KEY = "31871fa18f49742f95295ef7fe5d3550";

    public QuickMarkPostRequest(UserStateUtil.UserInfo userInfo, DeviceInfoUtil.DeviceInfo deviceInfo) {
        this.userInfo = userInfo;
        this.deviceInfo = deviceInfo;
        socketUrl = "ws://" + getLocalIpAddress4() + ":9123";
    }

    @Override
    public String getRequestUrl() throws UnsupportedEncodingException {
        return API_POST_PUSH_CODE;
    }

    @Override
    public Map<String, String> getParams() {
        Map<String, String> params = New.newHashMap();
        params.put("msgType", "12");
        params.put("userId", userInfo.userId);
        if (TextUtils.isEmpty(userInfo.userPhone)) {
            params.put("userStatus", "2");
        } else {
            params.put("userStatus", "1");
        }
        params.put("mac", deviceInfo.mac);
        params.put("sn", deviceInfo.sn);
        params.put("socketUrl", socketUrl);
        String sign = getSign(params, KEY);//加密获取sign值
        params.put("sign", sign);
        return params;
    }

    public static String getLocalIpAddress4() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    // Inet4Address判断是不是ip4
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "null";
    }

    static String getSign(Map<String, String> orderMap, String key) {
        String _params =buildQuery(orderMap);
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
