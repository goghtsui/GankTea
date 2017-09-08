package com.hiveview.cloudscreen.vipvideo.statistic.imp;

import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;

/**
 * Created by wangbei on 2015/12/7.
 * 计费包详情页（点击行为）
 */
public class PackageActionInfo extends BaseActionInfo {

    private final String SOURCE = "source";
    private final String CLICKTYPE = "clickType";
    private final String PACKAGEID = "packageId";
    private final String INTERNETSOURCE = "internetSource";
    private final String VIPPRIVILEGE = "VIPPrivilege";

    private String source;
    private String clickType;
    private String packageId;
    private String internetSource;
    private String VIPPrivilege;

    public PackageActionInfo(String tabNo, String actionType, String source, String clickType, String packageId, String internetSource, String VIPPrivilege) {
        this.tabNo = tabNo;
        this.actionType = actionType;
        this.source = source;
        this.clickType = clickType;
        this.packageId = packageId;
        this.internetSource = internetSource;
        this.VIPPrivilege = VIPPrivilege;
        buildActionInfo();
    }

    @Override
    protected void buildActionInfo() {

        if (source != null) {
            actionInfos.put(SOURCE, source);
        }
        if (clickType != null) {
            actionInfos.put(CLICKTYPE, clickType);
        }
        if (packageId != null) {
            actionInfos.put(PACKAGEID, packageId);
        }
        actionInfos.put(INTERNETSOURCE, internetSource);
        if (VIPPrivilege != null) {
            actionInfos.put(VIPPRIVILEGE, VIPPrivilege);
        }
    }
}
