package com.hiveview.cloudscreen.vipvideo.statistic.imp;

import android.text.TextUtils;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/6
 * @Description 首页埋点（点击行为）
 */
public class LauncherActionInfo extends BaseActionInfo {
    private final String HOME_BUTTON = "homeButton";

    private final String CHANNEL_SOURCE = "channelSource";

    private final String RECOMMEND = "recommend";

    private final String CLICK_TYPE = "clickType";

    private final String STAFF_PROPERTY = "staffProperty";

    private String homeButton;

    private String channelSource;

    private String recommend;

    private String clickType;

    private String staffProperty;

    public LauncherActionInfo(String tabNo, String actionType, String clickType, String staffProperty, String homeButton, String channelSource, String recommend) {
        this.tabNo = tabNo;
        this.actionType = actionType;
        this.clickType = clickType;
        this.staffProperty = staffProperty;
        this.homeButton = homeButton;
        this.channelSource = channelSource;
        this.recommend = recommend;
        buildActionInfo();
    }

    @Override
    protected void buildActionInfo() {
        actionInfos.put(CLICK_TYPE, clickType);
        actionInfos.put(STAFF_PROPERTY, staffProperty);
        if (null != homeButton) {
            actionInfos.put(HOME_BUTTON, homeButton);
        }
        if (null != channelSource) {
            actionInfos.put(CHANNEL_SOURCE, channelSource);
        }
        if (null != recommend) {
            actionInfos.put(RECOMMEND, recommend);
        }
    }
}
