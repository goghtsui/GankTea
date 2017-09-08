package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/5/3
 * @Description
 */
public class SubjectLiveVoEntity extends HiveviewBaseEntity {
    private int cp;
    private int isEffective;
    private String liveurl;
    private int tvId;
    private String tvName;
    private String tvlogo;
    private String details;

    public CornerEntity getLiveCornerContent() {
        return liveCornerContent;
    }

    public void setLiveCornerContent(CornerEntity liveCornerContent) {
        this.liveCornerContent = liveCornerContent;
    }

    private CornerEntity liveCornerContent;

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public String getLiveurl() {
        return liveurl;
    }

    public void setLiveurl(String liveurl) {
        this.liveurl = liveurl;
    }

    public int getTvId() {
        return tvId;
    }

    public void setTvId(int tvId) {
        this.tvId = tvId;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvlogo() {
        return tvlogo;
    }

    public void setTvlogo(String tvlogo) {
        this.tvlogo = tvlogo;
    }
}
