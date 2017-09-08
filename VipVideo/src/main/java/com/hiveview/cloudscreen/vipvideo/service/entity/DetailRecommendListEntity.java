package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.math.BigDecimal;

/**
 * Created by wangbei on 2015/11/25.
 */
public class DetailRecommendListEntity extends HiveviewBaseEntity {

    private int cid;//所属频道id
    private int id;//主键id
    private String name;//专辑名称
    private String picUrl;//通用类型的图片
    private BigDecimal score;//评分
    private String cornerPic;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCornerPic() {
        return cornerPic;
    }

    public void setCornerPic(String cornerPic) {
        this.cornerPic = cornerPic;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
