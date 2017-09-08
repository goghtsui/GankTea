package com.hiveview.cloudscreen.vipvideo.common.factory.resource;

import android.content.Context;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/6/13
 * @Description
 */
public interface IResource {
    /**
     * @return 获取地域信息
     */
    ResArea getResArea();

    /**
     * @return 播控域名
     */
    String getCloudScreenDomain();

    /**
     * @return 活动域名
     */
    String getActivityDomain();

    /**
     * @return 推荐域名
     */
    String getRecommendDomain();

    /**
     * @return 收藏域名
     */
    String getCollectDomain();

    /**
     * @return 设备域名
     */
    String getDeviceDomain();

    /**
     * @return 统计域名
     */
    String getStatisticsDomain();

    /**
     * @return bg_launcher_cupe图片资源
     */
    int getBgLauncherCupe();

    /**
     * @return bg_launcher_landscape_long图片资源
     */
    int getBgLauncherLandscapeLong();

    /**
     * @return bg_launcher_landscape_short图片资源
     */
    int getBgLauncherLandscapeShort();

    /**
     * @return bg_launcher图片资源
     */
    int getBgLauncher();

    /**
     * @return bg_launcher_portrait_high图片资源
     */
    int getBgLauncherPortraitHigh();

    /**
     * @return bg_launcher_portrait_low图片资源
     */
    int getBgLauncherPortraitLow();

    /**
     * @return d260360图片资源
     */
    int getD260360();

    /**
     * @return d280160图片资源
     */
    int getD280160();

    /**
     * @return d529733图片资源
     */
    int getD529733();

    /**
     * @return default_cover_detail图片资源
     */
    int getDefaultCoverDetail();

    /**
     * @return default_cover_list_landscape 图片资源
     */
    int getDefaultCoverListLandscape();

    /**
     * @return default_cover_list_portrait图片资源
     */
    int getDefaultCoverListPortrait();

    /**
     * @return ic_collect_user图片资源
     */
    int getIcCollectUser();

    /**
     * @return launcher_headerview_logo图片资源
     */
    int getLauncherHeaderviewLogo();

    /**
     * @return view_dialog_bg图片资源
     */
    int getViewDialogBg();

    /**
     * @return welcome_txt图片资源
     */
    int getWelcomeTxt();

    /**
     * @return 添加了额外值的url
     */
    String addUrlExtra(Context ctx, String url);

}
