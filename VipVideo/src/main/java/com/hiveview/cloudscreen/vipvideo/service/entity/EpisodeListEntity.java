package com.hiveview.cloudscreen.vipvideo.service.entity;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by wangbei on 2015/11/18.
 */
public class EpisodeListEntity extends HiveviewBaseEntity {

    private String albumName;            //剧集名称
    private String year;            //综艺作为第几期使用，格式20130101
    private Integer chnId;            //频道id
    private String phase;            //视频集数
    public Integer isEffective;
    private String aqyId;
    private Integer aqyIsEffective;
    private String jqId;
    private Integer jqIsEffective;
    private String jqVideoId;
    private String aqyVideoId;
    private String focus;
    private Integer videoId;
    private String videoName;

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getJqVideoId() {
        return jqVideoId;
    }

    public void setJqVideoId(String jqVideoId) {
        this.jqVideoId = jqVideoId;
    }

    public String getAqyVideoId() {
        return aqyVideoId;
    }

    public void setAqyVideoId(String aqyVideoId) {
        this.aqyVideoId = aqyVideoId;
    }

    private List<EpisodeInnerListEntity> sourceList;

    public String getAqyId() {
        return aqyId;
    }

    public void setAqyId(String aqyId) {
        this.aqyId = aqyId;
    }

    public Integer getAqyIsEffective() {
        return aqyIsEffective;
    }

    public void setAqyIsEffective(Integer aqyIsEffective) {
        this.aqyIsEffective = aqyIsEffective;
    }

    public String getJqId() {
        return jqId;
    }

    public void setJqId(String jqId) {
        this.jqId = jqId;
    }

    public Integer getJqIsEffective() {
        return jqIsEffective;
    }

    public void setJqIsEffective(Integer jqIsEffective) {
        this.jqIsEffective = jqIsEffective;
    }

    public int getPhase() {
        return TextUtils.isEmpty(phase) ? 0 : Integer.parseInt(phase);
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public Integer getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(Integer isEffective) {
        this.isEffective = isEffective;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getChnId() {
        return chnId;
    }

    public void setChnId(Integer chnId) {
        this.chnId = chnId;
    }

    public List<EpisodeInnerListEntity> getSourceList() {
        return sourceList;
    }

    public void setSourceList(List<EpisodeInnerListEntity> sourceList) {
        this.sourceList = sourceList;
    }

    @Override
    public String toString() {
        return "EpisodeListEntity{" +
                "albumName='" + albumName + '\'' +
                ", year='" + year + '\'' +
                ", chnId=" + chnId +
                ", phase='" + phase + '\'' +
                ", isEffective=" + isEffective +
                ", aqyId='" + aqyId + '\'' +
                ", aqyIsEffective=" + aqyIsEffective +
                ", jqId='" + jqId + '\'' +
                ", jqIsEffective=" + jqIsEffective +
                ", jqVideoId='" + jqVideoId + '\'' +
                ", aqyVideoId='" + aqyVideoId + '\'' +
                ", sourceList=" + sourceList +
                '}';
    }
}
