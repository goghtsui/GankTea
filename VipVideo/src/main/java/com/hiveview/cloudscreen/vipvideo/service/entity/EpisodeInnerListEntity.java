package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * Created by tianyejun on 16/11/9.
 */

public class EpisodeInnerListEntity extends HiveviewBaseEntity {
    public Integer sourceId;
    public Integer belongVideoId;
    public String playAddress;
    public String playFormat;//mp4 或者是 m3u8
    public Integer sourceType;//来源方
    public Integer area;
    public String definition;//分辨率
    public String crEndDate;//版权结束时间
    public Integer isEffective;
    public Integer isHand;
    public String partnerId;
    public String videoName;
    public String stream;

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getBelongVideoId() {
        return belongVideoId;
    }

    public void setBelongVideoId(Integer belongVideoId) {
        this.belongVideoId = belongVideoId;
    }

    public String getPlayAddress() {
        return playAddress;
    }

    public void setPlayAddress(String playAddress) {
        this.playAddress = playAddress;
    }

    public String getPlayFormat() {
        return playFormat;
    }

    public void setPlayFormat(String playFormat) {
        this.playFormat = playFormat;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public String getCrEndDate() {
        return crEndDate;
    }

    public void setCrEndDate(String crEndDate) {
        this.crEndDate = crEndDate;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public Integer getIsHand() {
        return isHand;
    }

    public void setIsHand(Integer isHand) {
        this.isHand = isHand;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    @Override
    public String toString() {
        return "EpisodeInnerListEntity{" +
                "sourceId=" + sourceId +
                ", belongVideoId=" + belongVideoId +
                ", playAddress='" + playAddress + '\'' +
                ", playFormat='" + playFormat + '\'' +
                ", sourceType=" + sourceType +
                ", area=" + area +
                ", definition='" + definition + '\'' +
                ", crEndDate='" + crEndDate + '\'' +
                ", isEffective=" + isEffective +
                ", isHand=" + isHand +
                ", partnerId='" + partnerId + '\'' +
                ", videoName='" + videoName + '\'' +
                ", stream='" + stream + '\'' +
                '}';
    }
}
