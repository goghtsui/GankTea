package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/12/7
 * @Description
 */

public class LiveStatisticEntity extends HiveviewBaseEntity {
    /**
     * action类型
     */
    private String actionType = "03";
    /**
     * 影片名字
     */
    private String albumName;
    /**
     * 0试看播放  1正常播放
     */
    private int playType = 1;
    /**
     * 1.直播 、2.点播 、3.轮播
     */
    private int programType;
    /**
     * 影片id
     */
    private int contentId;
    /**
     * 该影片购买类型 0：无需购买，1：单片购买，2：集合商品购买
     */
    private String buyType;

    public LiveStatisticEntity(String albumName, int programType, int contentId, String buyType) {
        this.albumName = albumName;
        this.programType = programType;
        this.contentId = contentId;
        this.buyType = buyType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public int getProgramType() {
        return programType;
    }

    public void setProgramType(int programType) {
        this.programType = programType;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getBuyType() {
        return buyType;
    }

    public void setBuyType(String buyType) {
        this.buyType = buyType;
    }
}
