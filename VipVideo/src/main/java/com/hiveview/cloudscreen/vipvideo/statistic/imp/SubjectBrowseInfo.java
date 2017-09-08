package com.hiveview.cloudscreen.vipvideo.statistic.imp;

import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;

/**
 * Created by wangbei on 2015/12/7.
 * 专题页面浏览（浏览行为）
 */
public class SubjectBrowseInfo extends BaseActionInfo {

    private final String SUBJECT_ID = "subjectId";
    private final String SUBJECT_NAME = "subjectName";
    private final String STAYTIMELENGTH = "stayTimeLength";
    private final String NOACTION = "noAction";

    private String subject_id;
    private String subject_name;
    private String stayTimeLength;
    private String noAction;

    public SubjectBrowseInfo(String tabNo, String actionType, String subject_id, String subject_name, String stayTimeLength, String noAction) {
        this.tabNo = tabNo;
        this.actionType = actionType;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.stayTimeLength = stayTimeLength;
        this.noAction = noAction;
        buildActionInfo();
    }

    @Override
    protected void buildActionInfo() {

        actionInfos.put(SUBJECT_ID, subject_id);
        actionInfos.put(SUBJECT_NAME, subject_name);
        actionInfos.put(STAYTIMELENGTH, stayTimeLength);
        actionInfos.put(NOACTION, noAction);
    }
}
