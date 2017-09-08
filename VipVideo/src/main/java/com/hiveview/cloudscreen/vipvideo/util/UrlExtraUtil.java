package com.hiveview.cloudscreen.vipvideo.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/6/11
 * @Description
 */
public class UrlExtraUtil {

    private UrlExtraUtil() {
    }

    private static class UrlHolder {
        private static final UrlExtraUtil INSTANCE = new UrlExtraUtil();
    }

    public static UrlExtraUtil getInstance() {
        return UrlHolder.INSTANCE;
    }

    public String addUrlExtra(Context ctx, String url) {
        if (url.contains("?")) {
            url += "&" + createEtra(ctx, url);
        } else {
            url += "?" + createEtra(ctx, url);
        }
        return url;
    }

    private String createEtra(Context ctx, String url) {
        DeviceInfoUtil.DeviceInfo info = DeviceInfoUtil.getInstance().getDeviceInfo(ctx, false);
        StringBuilder builder = new StringBuilder();
        if (!url.contains("mac")) {
            builder.append("mac=" + info.mac);
        }
        if (!url.contains("sn")) {
            builder.append("&sn=" + info.sn);
        }
        if (!url.contains("model")) {
            builder.append("&model=" + info.model);
        }
        if (!url.contains("romversion")) {
            builder.append("&romversion=" + info.softwareVersion);
        }
        if (!url.contains("apkversion")) {
            builder.append("&apkversion=" + getApkVersion(ctx));
        }
        if (!url.contains("duiversion")) {
            builder.append("&duiversion=" + getDuiversion());
        }
        String result = builder.toString();
        return "&".equals(result.substring(0, 1)) ? result.substring(1) : result;
    }

    private String getApkVersion(Context ctx) {
        PackageManager packageManager = ctx.getPackageManager();
        try {
            PackageInfo packInfo = packageManager.getPackageInfo(ctx.getPackageName(), 0);
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getDuiversion() {
        String duiversion = "";
        try {
            Context context = CloudScreenApplication.getInstance().getApplicationContext();
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            duiversion = appInfo.metaData.get("duiversion").toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return duiversion;
    }
}
