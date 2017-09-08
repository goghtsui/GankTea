package com.hiveview.cloudscreen.vipvideo.service.entity;

import java.sql.Timestamp;

/**
 * Created by wangbei on 2015/11/24.
 */
public class VipPackageListEntity extends HiveviewBaseEntity {

    //计费包Id
    private Integer pricePkgId;
    //vip包Id
    private Integer vipPackageId;
    //分类
    private Integer categoryId;
    //顺序
    private Integer seq;
    //名称
    private String name;
    //产品包海报
    private String pic;
    //简介
    private String priceDesc;
    //价格
    private Float price;
    //会员价格
    private Float vipPrice;
    //折扣类型
    private Integer discountId;
    //折扣名称
    private String discountName;
    //有效期 单位:秒
    private Integer expiryTime;
    //有效播放次数
    private Integer expiryPlay;
    //状态
    private Integer state;
    private java.sql.Timestamp ctime;
    private java.sql.Timestamp utime;

    public Integer getPricePkgId() {
        return pricePkgId;
    }

    public void setPricePkgId(Integer pricePkgId) {
        this.pricePkgId = pricePkgId;
    }

    public Integer getVipPackageId() {
        return vipPackageId;
    }

    public void setVipPackageId(Integer vipPackageId) {
        this.vipPackageId = vipPackageId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPriceDesc() {
        return priceDesc;
    }

    public void setPriceDesc(String priceDesc) {
        this.priceDesc = priceDesc;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public Integer getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Integer expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getExpiryPlay() {
        return expiryPlay;
    }

    public void setExpiryPlay(Integer expiryPlay) {
        this.expiryPlay = expiryPlay;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getCtime() {
        return ctime;
    }

    public void setCtime(Timestamp ctime) {
        this.ctime = ctime;
    }

    public Timestamp getUtime() {
        return utime;
    }

    public void setUtime(Timestamp utime) {
        this.utime = utime;
    }

    @Override
    public String toString() {
        return "VipPackageListEntity{" +
                "pricePkgId=" + pricePkgId +
                ", vipPackageId=" + vipPackageId +
                ", categoryId=" + categoryId +
                ", seq=" + seq +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", priceDesc='" + priceDesc + '\'' +
                ", price=" + price +
                ", vipPrice=" + vipPrice +
                ", discountId=" + discountId +
                ", discountName='" + discountName + '\'' +
                ", expiryTime=" + expiryTime +
                ", expiryPlay=" + expiryPlay +
                ", state=" + state +
                ", ctime=" + ctime +
                ", utime=" + utime +
                '}';
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(Float vipPrice) {
        this.vipPrice = vipPrice;
    }
}
