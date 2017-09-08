package com.hiveview.cloudscreen.vipvideo.statistic.imp;

import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/7
 * @Description 活动页埋点（浏览行为）
 */
public class ActivityBrowseInfo extends BaseActionInfo {
    private final String ACTIVITYID = "activityId";
    private String activityId;

    public ActivityBrowseInfo(String tabNo, String actionType, String activityId) {
        this.tabNo = tabNo;
        this.actionType = actionType;
        this.activityId = activityId;
        buildActionInfo();
    }

    @Override
    protected void buildActionInfo() {
        actionInfos.put(ACTIVITYID, activityId);
    }
}
