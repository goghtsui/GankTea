package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/10/31
 * @Description
 */
public class RemindStrategyEntity extends HiveviewBaseEntity {

    private Integer avgRemindDays;

    private Integer endBeforeRemindDays;

    private String endRemindPic;

    private int id;

    private int isEffective;

    private Integer remindNum;

    private String remindNumPic;

    private String strategyName;

    public Integer getAvgRemindDays() {
        return null != avgRemindDays ? avgRemindDays : 0;
    }

    public void setAvgRemindDays(Integer avgRemindDays) {
        this.avgRemindDays = avgRemindDays;
    }

    public Integer getEndBeforeRemindDays() {
        return endBeforeRemindDays;
    }

    public void setEndBeforeRemindDays(Integer endBeforeRemindDays) {
        this.endBeforeRemindDays = endBeforeRemindDays;
    }

    public String getEndRemindPic() {
        return endRemindPic;
    }

    public void setEndRemindPic(String endRemindPic) {
        this.endRemindPic = endRemindPic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public int getRemindNum() {
        return null != remindNum ? remindNum : 1;
    }

    public void setRemindNum(int remindNum) {
        this.remindNum = remindNum;
    }

    public String getRemindNumPic() {
        return remindNumPic;
    }

    public void setRemindNumPic(String remindNumPic) {
        this.remindNumPic = remindNumPic;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    @Override
    public String toString() {
        return "RemindStrategyEntity{" +
                "avgRemindDays=" + avgRemindDays +
                ", endBeforeRemindDays=" + endBeforeRemindDays +
                ", endRemindPic='" + endRemindPic + '\'' +
                ", id=" + id +
                ", isEffective=" + isEffective +
                ", remindNum=" + remindNum +
                ", remindNumPic='" + remindNumPic + '\'' +
                ", strategyName='" + strategyName + '\'' +
                '}';
    }
}
