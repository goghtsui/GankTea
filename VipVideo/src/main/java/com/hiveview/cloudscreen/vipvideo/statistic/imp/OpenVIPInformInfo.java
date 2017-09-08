package com.hiveview.cloudscreen.vipvideo.statistic.imp;

import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;

/**
 * Created by wangbei on 2016/1/6.
 * 开通成功失败统计
 */
public class OpenVIPInformInfo  extends BaseActionInfo {

    private final String ISOPEN = "isOpen";
    private final String SOURCE = "source";
    private final String INTERNETSOURCE = "internetSource";
    private final String PACKAGEID = "packageId";
    private String isOpen;
    private String source;
    private String internetSource;
    private String packageId;


    public OpenVIPInformInfo(String tabNo, String actionType, String isOpen, String source, String internetSource, String packageId) {
        this.tabNo = tabNo;
        this.actionType = actionType;
        this.isOpen = isOpen;
        this.source = source;
        this.internetSource = internetSource;
        this.packageId = packageId;
        buildActionInfo();
    }

    @Override
    protected void buildActionInfo() {
        actionInfos.put(ISOPEN, isOpen);
        actionInfos.put(SOURCE, source);
        actionInfos.put(INTERNETSOURCE, internetSource);
        actionInfos.put(PACKAGEID, packageId);

    }
}
