package com.hiveview.cloudscreen.vipvideo.statistic.imp;

import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;

/**
 * Created by wangbei on 2015/12/7.
 * 会员领取提示框（点击行为）
 */
public class GetVIPActionInfo extends BaseActionInfo {

    /** 3.2 添加
     * actionID 活动id
     * actionName 活动名称
     */
    private final String BOXSOURCE = "boxSource";
    private final String PROMPTBUTTON = "promptButton";
    private final String ACTIONID = "actionId";
    private final String ACTIONNAME = "actionName";
    private String boxSource;
    private String promptButton;
    private String actionId;
    private String actionName;

    public GetVIPActionInfo(String tabNo, String actionType, String boxSource, String promptButton, String actionId, String actionName) {
        this.tabNo = tabNo;
        this.actionType = actionType;
        this.boxSource = boxSource;
        this.promptButton = promptButton;
        this.actionId = actionId;
        this.actionName = actionName;
        buildActionInfo();
    }

    @Override
    protected void buildActionInfo() {
        actionInfos.put(BOXSOURCE, boxSource);
        actionInfos.put(PROMPTBUTTON, promptButton);
        actionInfos.put(ACTIONID, actionId);
        actionInfos.put(ACTIONNAME, actionName);
    }
}
