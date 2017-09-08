package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/12/11
 * @Description
 */
public class FreeActivityOrderEntity extends HiveviewBaseEntity {

    public Integer id;
    public String activityOrderId;//活动订单id
    public Integer activityOrderType;//活动订单类型 1：免费vip 2：促销活动卡
    public String activityOrderTypeName;//活动订单类型名称
    public Integer activityOrderStatus;//订单状态 1：已下单 2：已激活 3：激活失败 4：已退订
    public String activityOrderStatusName;//订单状态名称
    public Integer recordStatus;//活动记录状态  1：可查询  2：不可查询
    public Integer chargingId;//计费id
    public String chargingName;//计费名称
    public Integer chargingPrice;//计费价格
    public Integer chargingDuration;//计费时长
    public String chargingImg;//图片路径
    public String submitTime;//下单时间
    public String activityTime;//更改时间
    public Integer uid;//用户id
    public String mac;//mac
    public String sn;//sn
    public String devicecode;//设备码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getActivityOrderId() {
        return activityOrderId;
    }

    public void setActivityOrderId(String activityOrderId) {
        this.activityOrderId = activityOrderId;
    }

    public Integer getActivityOrderType() {
        return activityOrderType;
    }

    public void setActivityOrderType(Integer activityOrderType) {
        this.activityOrderType = activityOrderType;
    }

    public String getActivityOrderTypeName() {
        return activityOrderTypeName;
    }

    public void setActivityOrderTypeName(String activityOrderTypeName) {
        this.activityOrderTypeName = activityOrderTypeName;
    }

    public Integer getActivityOrderStatus() {
        return activityOrderStatus;
    }

    public void setActivityOrderStatus(Integer activityOrderStatus) {
        this.activityOrderStatus = activityOrderStatus;
    }

    public String getActivityOrderStatusName() {
        return activityOrderStatusName;
    }

    public void setActivityOrderStatusName(String activityOrderStatusName) {
        this.activityOrderStatusName = activityOrderStatusName;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getChargingId() {
        return chargingId;
    }

    public void setChargingId(Integer chargingId) {
        this.chargingId = chargingId;
    }

    public String getChargingName() {
        return chargingName;
    }

    public void setChargingName(String chargingName) {
        this.chargingName = chargingName;
    }

    public Integer getChargingPrice() {
        return chargingPrice;
    }

    public void setChargingPrice(Integer chargingPrice) {
        this.chargingPrice = chargingPrice;
    }

    public Integer getChargingDuration() {
        return chargingDuration;
    }

    public void setChargingDuration(Integer chargingDuration) {
        this.chargingDuration = chargingDuration;
    }

    public String getChargingImg() {
        return chargingImg;
    }

    public void setChargingImg(String chargingImg) {
        this.chargingImg = chargingImg;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }
}
