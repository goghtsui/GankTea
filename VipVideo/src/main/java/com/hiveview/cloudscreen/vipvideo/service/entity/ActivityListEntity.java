package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * Created by wangbei on 2015/11/17.
 */
public class ActivityListEntity extends HiveviewBaseEntity{

    // 活动ID
    private Integer activityId;
    // 活动名称
    private String activityName;
    // 活动海报图
    private String activityImg;
    // 活动状态 0未发布 1已发布
    private Integer activityStatus;
    //活动类型
    private Integer activityType;
    //是否是VIP 0 否 1是
    private Integer isVip;
    //关联videoId
    private Integer videoId;
    // 创建时间
    private String createTime;
    // 更新时间
    private String updateTime;
    // 关联影片名称
    private String videoName;
    //关联影片类型
    private Integer videoType;
    //关联影片类型名
    private String videoTypeName;
    //关联影片图片
    private String videoPic;


    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityImg() {
        return activityImg;
    }

    public void setActivityImg(String activityImg) {
        this.activityImg = activityImg;
    }

    public Integer getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(Integer activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }

    public String getVideoTypeName() {
        return videoTypeName;
    }

    public void setVideoTypeName(String videoTypeName) {
        this.videoTypeName = videoTypeName;
    }

    public String getVideoPic() {
        return videoPic;
    }

    public void setVideoPic(String videoPic) {
        this.videoPic = videoPic;
    }

    @Override
    public String toString() {
        return "ActivityListEntity{" +
                "activityId=" + activityId +
                ", activityName='" + activityName + '\'' +
                ", activityImg='" + activityImg + '\'' +
                ", activityStatus=" + activityStatus +
                ", activityType=" + activityType +
                ", isVip=" + isVip +
                ", videoId=" + videoId +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", videoName='" + videoName + '\'' +
                ", videoType=" + videoType +
                ", videoTypeName='" + videoTypeName + '\'' +
                ", videoPic='" + videoPic + '\'' +
                '}';
    }
}
