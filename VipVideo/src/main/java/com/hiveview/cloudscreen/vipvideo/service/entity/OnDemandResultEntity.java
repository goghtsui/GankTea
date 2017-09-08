package com.hiveview.cloudscreen.vipvideo.service.entity;


public class OnDemandResultEntity extends HiveviewBaseEntity {
    private int epgId;                //影片ID
    private String orderTime;            //下单时间
    private Integer royaltyType;        //1表示商品包是单片单点包，2表示多片包
    private Integer isBooking;            //是否属于预售：1是 0 否
    private String bookingStartTime;    //预售开始时间
    private String bookingEndTime;        //预售结束时间
    private String durationStartTime;    //该订单开始时间
    private String durationEndTime;        //该订单结束时间
    private String singlEpgTime; //有效时长

    public int getEpgId() {
        return epgId;
    }

    public void setEpgId(int epgId) {
        this.epgId = epgId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getRoyaltyType() {
        return royaltyType;
    }

    public void setRoyaltyType(Integer royaltyType) {
        this.royaltyType = royaltyType;
    }

    public Integer getIsBooking() {
        return isBooking;
    }

    public void setIsBooking(Integer isBooking) {
        this.isBooking = isBooking;
    }

    public String getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(String bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public String getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(String bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getDurationStartTime() {
        return durationStartTime;
    }

    public void setDurationStartTime(String durationStartTime) {
        this.durationStartTime = durationStartTime;
    }

    public String getDurationEndTime() {
        return durationEndTime;
    }

    public void setDurationEndTime(String durationEndTime) {
        this.durationEndTime = durationEndTime;
    }

    public String getSinglEpgTime() {
        return singlEpgTime;
    }

    public void setSinglEpgTime(String singlEpgTime) {
        this.singlEpgTime = singlEpgTime;
    }

    @Override
    public String toString() {
        return "OnDemandResultEntity{" +
                "epgId=" + epgId +
                ", orderTime='" + orderTime + '\'' +
                ", royaltyType=" + royaltyType +
                ", isBooking=" + isBooking +
                ", bookingStartTime='" + bookingStartTime + '\'' +
                ", bookingEndTime='" + bookingEndTime + '\'' +
                ", durationStartTime='" + durationStartTime + '\'' +
                ", durationEndTime='" + durationEndTime + '\'' +
                ", singlEpgTime='" + singlEpgTime + '\'' +
                '}';
    }
}
