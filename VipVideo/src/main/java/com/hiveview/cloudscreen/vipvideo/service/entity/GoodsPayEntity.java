package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * Created by Administrator on 2016/11/16.
 */

public class GoodsPayEntity extends HiveviewBaseEntity {


    private String goodsId;
    private String goodsName;

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    private Integer orderType;
    private String originalPrice;       //商品原价
    private String transactionPrice;    //商品交易价
    private String strategyId;          //优惠策略ID

    private String templetId;           //模板ID

    private String apkPkgName;            //apk包名

    private Integer contentId;            //VIP商品包中所购买的内容ID
    private String contentName;            //VIP商品包中所购买的内容名称

    private Integer durationTime;        //VIP有效时长
    private String saleStartTime;        //销售开始时间
    private String saleEndTime;            //销售结束时间

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getTransactionPrice() {
        return transactionPrice;
    }

    public void setTransactionPrice(String transactionPrice) {
        this.transactionPrice = transactionPrice;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    public String getApkPkgName() {
        return apkPkgName;
    }

    public void setApkPkgName(String apkPkgName) {
        this.apkPkgName = apkPkgName;
    }

    public Integer getContentId() {
        return contentId;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Integer getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(Integer durationTime) {
        this.durationTime = durationTime;
    }

    public String getSaleStartTime() {
        return saleStartTime;
    }

    public void setSaleStartTime(String saleStartTime) {
        this.saleStartTime = saleStartTime;
    }

    public String getSaleEndTime() {
        return saleEndTime;
    }

    public void setSaleEndTime(String saleEndTime) {
        this.saleEndTime = saleEndTime;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    @Override
    public String toString() {
        return "GoodsPayEntity{" +
                "goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", transactionPrice='" + transactionPrice + '\'' +
                ", strategyId='" + strategyId + '\'' +
                ", templetId='" + templetId + '\'' +
                ", apkPkgName='" + apkPkgName + '\'' +
                ", contentId=" + contentId +
                ", contentName='" + contentName + '\'' +
                ", durationTime=" + durationTime +
                ", saleStartTime='" + saleStartTime + '\'' +
                ", saleEndTime='" + saleEndTime + '\'' +
                '}';
    }
}
