package com.hiveview.cloudscreen.vipvideo.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.manager.SystemInfoManager;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/29
 * @Description
 */
public class DeviceInfoUtil {
    private static final String TAG = DeviceInfoUtil.class.getSimpleName();
    DeviceInfo info = new DeviceInfo();
    private static final Uri LIVE_AREA_URI = Uri.parse("content://HiveViewCloudVideLiveAuthorities/live_area_dao");

    private DeviceInfoUtil() {
    }

    private static class Holder {
        private static final DeviceInfoUtil INSTANCE = new DeviceInfoUtil();
    }

    public static final DeviceInfoUtil getInstance() {
        return Holder.INSTANCE;
    }

    public DeviceInfo getDeviceInfo(Context context) {
        Log.i(TAG, "getDeviceInfo");
        return getDeviceInfo(context, false);
    }

    public DeviceInfo getDeviceInfo(Context context, boolean isNeedFresh) {
        if (isNeedFresh) {
            initDeviceInfo(context);
            Log.i(TAG, "initDeviceInfo");
        }
        return info;
    }

    public void initDeviceInfo(Context context) {

//        ContentResolver resolver = context.getContentResolver();
//        Cursor cursor = null;
//        try {
//            // 获取dataprovider开放数据库的数据
//            cursor = resolver.query(Uri.parse("content://HiveViewAuthoritiesDataProvider/TABLE_DEVICE_INFO"), null, null, null, null);
//            if (null != cursor && cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                // 获取设备名称
//                info.deviceName = cursor.getString(cursor.getColumnIndex("deviceName"));
//                // 获取硬件版本
//                info.hardwareVersion = cursor.getString(cursor.getColumnIndex("hardwareVersion"));
//                // 获取软件版本
//                info.softwareVersion = cursor.getString(cursor.getColumnIndex("softwareVersion"));
//                // 获取无线mac地址
//                info.wlanMac = cursor.getString(cursor.getColumnIndex("wlanMac"));
//                // 获取有线mac地址
//                info.mac = cursor.getString(cursor.getColumnIndex("mac"));
//                // 获取sn
//                info.sn = cursor.getString(cursor.getColumnIndex("sn"));
//                // 获取设备类型
//                info.model = cursor.getString(cursor.getColumnIndex("model"));
//                // 获取设备ID
//                info.androidId = cursor.getString(cursor.getColumnIndex("androidId"));
//                // 获取设备版本
//                info.versionCode = cursor.getInt(cursor.getColumnIndex("versionCode"));
//                // 获取设备码
//                info.deviceCode = cursor.getString(cursor.getColumnIndex("deviceCode"));
//            }else {
//                getDeviceInfoFromCore(context);
//            }
//        } catch (Exception e) {
//            Logger.i(TAG, "Get deviceInfo error !");
//            getDeviceInfoFromCore(context);
//
//        } finally {
//            if (null != cursor && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//
//        info.androidId = getAndroidId(context);

        //目前直接调用hiveviewcore获取设备信息
        getDeviceInfoFromCore(context);
        getTempletId(context);
        getWhiteList(context);
//        getLiveArea(context);
        getNetWork(context);
        //获取启动页图片
        getAppAD(context);
        Logger.d(TAG, info.toString());

    }

    /**
     * @Author Spr_ypt
     * @Date 2016/3/28
     * @Description 通过程序本身获取设备信息而不是从dataProvider中拿
     */
    private void getDeviceInfoFromCore(Context context) {
        SystemInfoManager sm = SystemInfoManager.getSystemInfoManager();
        if (null == sm) {
            return;
        }
        try {
            info.deviceName = sm.getProductModel() == null ? "" : sm.getProductModel().trim();
            info.hardwareVersion = sm.getHWVersion() == null ? "" : sm.getHWVersion().trim();
            info.softwareVersion = sm.getFirmwareVersion() == null ? "" : sm.getFirmwareVersion().trim();
            info.wlanMac = sm.getWMacInfo() == null ? "" : sm.getWMacInfo().trim();
            info.mac = sm.getMacInfo() == null ? "" : sm.getMacInfo().trim();
            info.sn = sm.getSnInfo() == null ? "" : sm.getSnInfo().trim();
            info.model = sm.getProductModel() == null ? "" : sm.getProductModel().trim();
            info.androidId = getAndroidId(context) == null ? "" : getAndroidId(context).trim();
            info.versionCode = sm.getProductModel() == null ? 0 : getVersionCode(sm.getProductModel().trim());
            if (null == info.deviceCode || "".equals(info.deviceCode)) {
                CloudScreenService.getInstance().getDeviceCode(sm.getMacInfo(), sm.getSnInfo(), new OnRequestResultListener<String>() {
                    @Override
                    public void onSucess(String s) {
                        info.deviceCode = s.trim();
                        Logger.d(TAG, "deviceCode=" + s.trim());
                    }

                    @Override
                    public void onFail(Exception e) {

                    }

                    @Override
                    public void onParseFail(HiveviewException e) {

                    }
                });
            }

        } catch (RemoteException e1) {
            e1.printStackTrace();
        }
    }


    public class DeviceInfo {
        // 获取设备类型 getModel方法设为static
        public String model = "";
        // 获取设备的mac地址
        public String mac = "";
        // 获取设备的sn地址
        public String sn = "";
        // 获取设备的软件版本
        public String softwareVersion = "";
        // 获取设备的版本1为1.0盒子,2为2.0盒子
        public int versionCode = 0;
        // 获取设备的名称
        public String deviceName = "";
        // 获取设备的id
        public String androidId = "";
        // 获取设备的硬件版本信息
        public String hardwareVersion = "";
        // 获取设备的无线mac部分盒子 只有在连接过无线后才有数据
        public String wlanMac = "";
        // 设备码,需要访问访问网络所以放到了子线程中
        public String deviceCode = "";
        //平台ID
        public int templetId = 0;
        //白名单身份，>0表示在白名单，开启骇客模式
        public int white = 0;
        //直播省代号
        public String proCode = "";
        //直播市代号
        public String cityCode = "";
        //直播数据
        public int liveOpen = 0;
        //直播数据
        public int isCarousel = 0;
        //开机启动页地址
        public String filePath = "";

        //判断用户内外网  false: 外网  true：内网
        public boolean userIsOutWork = false;

        @Override
        public String toString() {
            return "DeviceInfo{" +
                    "model='" + model + '\'' +
                    ", mac='" + mac + '\'' +
                    ", sn='" + sn + '\'' +
                    ", softwareVersion='" + softwareVersion + '\'' +
                    ", versionCode=" + versionCode +
                    ", deviceName='" + deviceName + '\'' +
                    ", androidId='" + androidId + '\'' +
                    ", hardwareVersion='" + hardwareVersion + '\'' +
                    ", wlanMac='" + wlanMac + '\'' +
                    ", deviceCode='" + deviceCode + '\'' +
                    ", templetId=" + templetId +
                    ", white=" + white +
                    ", proCode='" + proCode + '\'' +
                    ", cityCode='" + cityCode + '\'' +
                    ", liveOpen=" + liveOpen +
                    ", isCarousel=" + isCarousel +
                    '}';
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/3/28
     * @Description 获取内外网情况
     */
    public void getNetWork(Context context) {
        try {
            ContentResolver resolver = context.getContentResolver();
            //通过call方法
            Bundle bundle = resolver.call(Uri.parse("content://HiveViewAuthoritiesDataProvider"), "getNetwork", null, null);
            if (null != bundle) {
                boolean isGroupUser = (Boolean) bundle.get("isGroupUser");
                //如果isGroupUser为true：内外，false：外网
                Log.i(TAG, "isGroupUser:" + isGroupUser);
                info.userIsOutWork = isGroupUser;
                if (isGroupUser) {
                    Log.i(TAG, "onNetworkListener");
                } else {
                    //外网回调
                    Log.i(TAG, "onOuterNetworkListener");
                }
            } else {
                Logger.e(TAG, "获取内外网信息为空");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Logger.e(TAG, "获取内外网信息出错");
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/3/28
     * @Description 获取androidId
     */
    private String getAndroidId(Context ctx) {
        String AndroidId = Settings.Secure.getString(ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if (AndroidId == null) {
            AndroidId = "";
        }
        return AndroidId;
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/5
     * @Description 获取模板id
     */
    public void getTempletId(Context context) {
        Log.i(TAG, "getTempletId");
        try {
            Uri uri = Uri.parse("content://HiveViewAuthoritiesDataProvider/TABLE_TEMPLET");
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            while (null != cursor && cursor.moveToNext()) {
                info.templetId = cursor.getInt(cursor.getColumnIndex("templetId"));
                Log.i(TAG, "templetId=" + info.templetId);
            }
            if (null != cursor) {
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getTempletId fail e=" + e.toString());
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/11/5
     * @Description 获取白名单信息
     */
    public void getWhiteList(Context context) {
        try {
            Uri uri = Uri.parse("content://HiveViewAuthoritiesDataProvider/TABLE_DATA_WHITE_LIST");
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            while (null != cursor && cursor.moveToNext()) {
                info.white = cursor.getInt(cursor.getColumnIndex("vipVideo"));
            }
            if (info.white > 0) {
                ToastUtil.showToast(context, "欢迎您尊贵的白名单用户，已为您开启【骇客模式】。", Toast.LENGTH_LONG);
            }
            if (null != cursor) {
                cursor.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "getWhiteList fail e=" + e.toString());
        }
    }

    /**
     * 获取设备的版本 model的第三位表示当前的设备版本，电视中不需要版本
     *
     * @return
     * @Title: Device
     * @author:郭松胜
     * @Description: TODO
     */
    public static int getVersionCode(String model) {
        int deviceVersion = 0;
        try {
            if (TextUtils.isEmpty(model)) {
                SystemInfoManager sys = SystemInfoManager.getSystemInfoManager();
                model = sys.getProductModel();
                deviceVersion = TextUtils.isEmpty(model) ? 0 : Integer.parseInt(model.substring(2, 3));
                return deviceVersion;
            } else {
                deviceVersion = Integer.parseInt(model.substring(2, 3));
            }
        } catch (Exception e) {
            Log.e(TAG, "Get deviceModel error!");
        }
        return deviceVersion;
    }

    /**
     * @Author Spr_ypt
     * @Date 2016/12/10
     * @Description 获取直播的地区信息
     */
    private void getLiveArea(Context context) {
        try {
            ContentResolver cr = context.getContentResolver();
            Cursor cursorUser = cr.query(LIVE_AREA_URI, null, null, null, null);
            if (cursorUser != null && cursorUser.getCount() > 0) {
                cursorUser.moveToFirst();
                info.proCode = cursorUser.getString(cursorUser.getColumnIndex("proCode"));
                info.cityCode = cursorUser.getString(cursorUser.getColumnIndex("cityCode"));
                info.liveOpen = cursorUser.getInt(cursorUser.getColumnIndex("liveOpen"));
                info.isCarousel = cursorUser.getInt(cursorUser.getColumnIndex("isCarousel"));
                Log.d("gss_liver", "getLiveArea: proCode = " + info.proCode + " cityCode =  " + info.cityCode + " liveOpen= " + info.liveOpen + " isCarousel =  " + info.isCarousel);
            } else {
                Log.d("gss_liver", "getLiveArea: live area  is null ");
            }
            if (null != cursorUser) {
                cursorUser.close();
            }
        } catch (Exception e) {
            // 异常要抓一下做逻辑处理，以防旧的apk没有对应的数据库
            e.printStackTrace();
        }
    }

    /**
     * 大麦影视开机广告
     *
     * @param context
     */
    private void getAppAD(Context context) {
        Log.i(TAG, "getAppAD");
        try {
            ContentResolver resolver = context.getContentResolver();
            Bundle extras = new Bundle();
            extras.putInt("adtype", 4);
            Bundle bundle = resolver.call(Uri.parse("content://HiveViewAuthoritiesDataProvider"), "getAppAd", null, extras);
            if (null != bundle) {
                Log.i(TAG, "bundle==" + (null != bundle));
                info.filePath = bundle.getString("filePath");
                int adowner = bundle.getInt("adowner");
                int adtype = bundle.getInt("adtype");
                int adId = bundle.getInt("adId");
                int adplaytype = bundle.getInt("adplaytype");
                int netType = bundle.getInt("netType");
                String thirdurl = bundle.getString("thirdurl");

                Log.i(TAG, "info.filePath==" + info.filePath + " adowner=" + adowner + " adtype=" + adtype + " adId=" + adId + " adplaytype=" + adplaytype + " netType=" + netType + " thirdurl=" + thirdurl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OnExtranetListener listener;

    public interface OnExtranetListener {
        //外网开通
        void onOuterNetworkListener();

        //内网开通
        void onNetworkListener();

    }

    public void setOnExtranetListener(OnExtranetListener listener) {
        this.listener = listener;
    }


}
