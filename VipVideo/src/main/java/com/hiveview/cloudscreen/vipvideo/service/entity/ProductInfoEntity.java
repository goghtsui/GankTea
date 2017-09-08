package com.hiveview.cloudscreen.vipvideo.service.entity;

import android.text.TextUtils;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ProductInfoEntity extends HiveviewBaseEntity {
    private String orderEndTime;
    private long longTime;//服务器返回当前时间
    private int productId;//产品包ID
    private int goodId;//商品包ID
    private String onlineTime;//上映时间
    private int contentId;//内容ID
    private int contentType;//内容类型(4直播1点播6轮播7商品包)
    private int category;//产品包类型 1 单剧集 2多剧集
    private String contentName;//产品包ID
    private int isVipStrategy;//是否VIP
    private String bookingEndTime;//预售结束时间
    private String bookingStartTime;//预售开始时间
    private String saleStartTime;//销售开始时间
    private String saleEndTime;//销售结束时间
    private String strategyDesc;//策略描述
    private Integer isBooking;//是否预售(1.是 0.否)
    private int saleStyle;//销售样式(1单一产品包2集合产品包)
    private int priceStyle;//价格样式(1单片2包月)
    private String firstPosterPic;//极清横图1920*1080
    private String secondPosterPic;
    private String goodsDesc;//详情
    private String goodName;//商品包名称
    private String playAddress;//影片播放地址
    private int singleSetMinutes;//点播单集付费试看分钟数(试看时长)
    private int multiSetNumbers;//点播多集付费试看集数(试看集数)
    private String effectiveHours;//点播单片点播有效时长策略(单片有效时长)
    private String nowPrice;//普通用户优惠后现价
    private String price;//普通用户原价
    private int days;//包月包年天数
    private String vipNowPrice;//VIP现价
    private String partnerId;//商户ID
    private boolean authenticationFlag = false;//false 鉴权失败 true 鉴权成功
    private List<OnDemandResultEntity> dianboList = new ArrayList<OnDemandResultEntity>();

    public List<OnDemandResultEntity> getDianboList() {
        return dianboList;
    }

    public long getLongTime() {
        return longTime;
    }

    public void setLongTime(long longTime) {
        this.longTime = longTime;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getGoodId() {
        return goodId;
    }

    public void setGoodId(int goodId) {
        this.goodId = goodId;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getContentName() {
        return contentName;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public int getIsVipStrategy() {
        return isVipStrategy;
    }

    public void setIsVipStrategy(int isVipStrategy) {
        this.isVipStrategy = isVipStrategy;
    }

    public String getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(String bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(String bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
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

    public String getStrategyDesc() {
        return strategyDesc;
    }

    public void setStrategyDesc(String strategyDesc) {
        this.strategyDesc = strategyDesc;
    }

    public int getSaleStyle() {
        return saleStyle;
    }

    public void setSaleStyle(int saleStyle) {
        this.saleStyle = saleStyle;
    }

    public int getPriceStyle() {
        return priceStyle;
    }

    public void setPriceStyle(int priceStyle) {
        this.priceStyle = priceStyle;
    }

    public String getFirstPosterPic() {
        return firstPosterPic;
    }

    public void setFirstPosterPic(String firstPosterPic) {
        this.firstPosterPic = firstPosterPic;
    }

    public String getSecondPosterPic() {
        return secondPosterPic;
    }

    public void setSecondPosterPic(String secondPosterPic) {
        this.secondPosterPic = secondPosterPic;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getPlayAddress() {
        return playAddress;
    }

    public void setPlayAddress(String playAddress) {
        this.playAddress = playAddress;
    }

    public int getMultiSetNumbers() {
        return multiSetNumbers;
    }

    public void setMultiSetNumbers(int multiSetNumbers) {
        this.multiSetNumbers = multiSetNumbers;
    }

    public String getEffectiveHours() {
        return effectiveHours;
    }

    public void setEffectiveHours(String effectiveHours) {
        this.effectiveHours = effectiveHours;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public Double getNowPrice() {
        return TextUtils.isEmpty(nowPrice) ? null : Double.parseDouble(nowPrice);
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Double getVipNowPrice() {
        return TextUtils.isEmpty(vipNowPrice) ? null : Double.parseDouble(vipNowPrice);
    }

    public void setVipNowPrice(String vipNowPrice) {
        this.vipNowPrice = vipNowPrice;
    }

    public Double getPrice() {
        return TextUtils.isEmpty(price) ? null : Double.parseDouble(price);
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getIsBooking() {
        //isBooking == null 默认返回0
        return isBooking == null ? 0 : isBooking;
    }

    public void setIsBooking(Integer isBooking) {
        this.isBooking = isBooking;
    }

    public int getSingleSetMinutes() {
        return this.singleSetMinutes == 0 ? 0 : singleSetMinutes * 60000;//代表 singleSetMinutes*60*1000 换算成毫秒
    }

    public void setSingleSetMinutes(int singleSetMinutes) {
        this.singleSetMinutes = singleSetMinutes;
    }

    /**
     * 如果是通过鉴权已买该影片，判断是否在观看日期内
     *
     * @return
     */
    public boolean isAuthenticationFlag() {

        if (null != dianboList && dianboList.size() > 0) {
            OnDemandResultEntity entity = dianboList.get(0);
            Log.d("test", "通过鉴权已买该影片，在观看日期内");
            orderEndTime = entity.getDurationEndTime();
            return StringUtils.currentTimeInStartTimeAndEndTime(entity.getOrderTime(), entity.getDurationEndTime());
        }
        Log.d("test", "authenticationFlag = " + authenticationFlag);
        return this.authenticationFlag;
    }

    /**
     * 获取购买后影片的有效截止日期
     *
     * @return
     */
    public String getDurationEndTime() {
        return orderEndTime;
    }

    public void setAuthenticationFlag(boolean authenticationFlag) {
        this.authenticationFlag = authenticationFlag;
    }

    public void setDianboList(List<OnDemandResultEntity> dianboList) {
        this.dianboList = dianboList;
    }

    @Override
    public String toString() {
        return "ProductInfoEntity{" +
                "productId=" + productId +
                ", goodId=" + goodId +
                ", onlineTime='" + onlineTime + '\'' +
                ", contentId=" + contentId +
                ", contentType=" + contentType +
                ", category=" + category +
                ", contentName='" + contentName + '\'' +
                ", isVipStrategy=" + isVipStrategy +
                ", bookingEndTime='" + bookingEndTime + '\'' +
                ", bookingStartTime='" + bookingStartTime + '\'' +
                ", saleStartTime='" + saleStartTime + '\'' +
                ", saleEndTime='" + saleEndTime + '\'' +
                ", strategyDesc='" + strategyDesc + '\'' +
                ", isBooking=" + isBooking +
                ", saleStyle=" + saleStyle +
                ", priceStyle=" + priceStyle +
                ", firstPosterPic='" + firstPosterPic + '\'' +
                ", secondPosterPic='" + secondPosterPic + '\'' +
                ", goodsDesc='" + goodsDesc + '\'' +
                ", goodName='" + goodName + '\'' +
                ", playAddress='" + playAddress + '\'' +
                ", singleSetMinutes=" + singleSetMinutes +
                ", multiSetNumbers=" + multiSetNumbers +
                ", effectiveHours='" + effectiveHours + '\'' +
                ", nowPrice='" + nowPrice + '\'' +
                ", price='" + price + '\'' +
                ", days=" + days +
                ", vipNowPrice='" + vipNowPrice + '\'' +
                ", partnerId='" + partnerId + '\'' +
                '}';
    }
}
