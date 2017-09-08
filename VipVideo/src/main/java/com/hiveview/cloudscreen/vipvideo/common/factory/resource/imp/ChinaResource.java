package com.hiveview.cloudscreen.vipvideo.common.factory.resource.imp;


import android.content.Context;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.IResource;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResArea;
import com.hiveview.cloudscreen.vipvideo.service.ApiConstants;
import com.hiveview.cloudscreen.vipvideo.util.UrlExtraUtil;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/6/13
 * @Description 大中国区资源适配类
 */
public class ChinaResource implements IResource {
    @Override
    public ResArea getResArea() {
        return ResArea.CH;
    }

    @Override
    public String getCloudScreenDomain() {
        return ApiConstants.CLOUDSCREEN_DOMAIN;
    }

    @Override
    public String getActivityDomain() {
        return ApiConstants.CLOUDSCREEN_ACTIVITY;
    }

    @Override
    public String getRecommendDomain() {
        return ApiConstants.RECOMMEND_DOMAIN;
    }

    @Override
    public String getCollectDomain() {
        return ApiConstants.COLLECT_DOMAIN;
    }

    @Override
    public String getDeviceDomain() {
        return ApiConstants.DEVICE_DOMAIN;
    }

    @Override
    public String getStatisticsDomain() {
        return ApiConstants.STATISTICS_URL;
    }

    @Override
    public int getBgLauncherCupe() {
        return R.drawable.bg_launcher_cupe;
    }

    @Override
    public int getBgLauncherLandscapeLong() {
        return R.drawable.bg_launcher_landscape_long;
    }

    @Override
    public int getBgLauncherLandscapeShort() {
        return R.drawable.bg_launcher_landscape_short;
    }

    @Override
    public int getBgLauncher() {
        return R.drawable.bg_launcher;
    }

    @Override
    public int getBgLauncherPortraitHigh() {
        return R.drawable.bg_launcher_portrait_high;
    }

    @Override
    public int getBgLauncherPortraitLow() {
        return R.drawable.bg_launcher_portrait_low;
    }

    @Override
    public int getD260360() {
        return R.drawable.d260360;
    }

    @Override
    public int getD280160() {
        return R.drawable.d280160;
    }

    @Override
    public int getD529733() {
        return R.drawable.d529733;
    }

    @Override
    public int getDefaultCoverDetail() {
        return R.drawable.default_cover_detail;
    }

    @Override
    public int getDefaultCoverListLandscape() {
        return R.drawable.default_cover_list_landscape;
    }

    @Override
    public int getDefaultCoverListPortrait() {
        return R.drawable.default_cover_list_portrait;
    }

    @Override
    public int getIcCollectUser() {
        return R.drawable.ic_collect_user_os;
    }

    @Override
    public int getLauncherHeaderviewLogo() {
        return R.drawable.launcher_headerview_logo;
    }

    @Override
    public int getViewDialogBg() {
        return R.drawable.view_dialog_bg_os;
    }

    @Override
    public int getWelcomeTxt() {
        return R.drawable.welcome_txt;
    }

    @Override
    public String addUrlExtra(Context ctx, String url) {
        return UrlExtraUtil.getInstance().addUrlExtra(ctx, url);
    }
}
