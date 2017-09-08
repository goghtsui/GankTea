package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @author li.sz
 * @date：2016年11月5日 下午5:45:50
 * @类说明: VIP商品包内容
 */

public class VIpGoodsContentApiVo extends HiveviewBaseEntity {

    private Integer priceId;    //会员价格ID
    private String name;        //会员价格名称
    private String price;        //价格
    private Integer duration;    //时长：天
    private String pic;            //图片
    //策略信息
    private Integer strategyId;        //所属的策略ID
    private String strategyName;    //优惠名称
    private String startTime;        //优惠开始时间
    private String endTime;            //优惠结束时间
    private String presentPrice;    //现价
    private Integer discount;        //折扣，整数，使用时/100
    private Integer giveDuration;    //赠送时长


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getPriceId() {
        return priceId;
    }

    public void setPriceId(Integer priceId) {
        this.priceId = priceId;
    }

    public Integer getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(Integer strategyId) {
        this.strategyId = strategyId;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPresentPrice() {
        return presentPrice != null ? presentPrice : price;
    }

    public void setPresentPrice(String presentPrice) {
        this.presentPrice = presentPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getGiveDuration() {
        return giveDuration;
    }

    public void setGiveDuration(Integer giveDuration) {
        this.giveDuration = giveDuration;
    }

    @Override
    public String toString() {
        return "VIpGoodsContentApiVo{" +
                "priceId=" + priceId +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", duration=" + duration +
                ", pic='" + pic + '\'' +
                ", strategyId=" + strategyId +
                ", strategyName='" + strategyName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", presentPrice='" + presentPrice + '\'' +
                ", discount=" + discount +
                ", giveDuration=" + giveDuration +
                '}';
    }
}
