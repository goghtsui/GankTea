package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/11/3
 * @Description
 */
public class LiveDetailEntity extends HiveviewBaseEntity {
    private String apkBagName;
    private String apkName;
    private String begin_date;
    private String channelTypeId;
    private String channelTypeName;
    private String cityCode;
    private String cityName;
    private String comment;
    private String cp;
    private String cpChannelId;
    private String createTime;
    private String current_time;
    private String damaiDetailPic;
    private String damaiId;
    private String damaiListPic;
    private String damaiName;
    private String details;
    private String displayType;
    private String end_date;
    private String epg_date;
    private String focus;
    private String horpic;
    private String isEffective;
    private String isLoop;
    private String isPay;
    private String isVip;
    private String jiqingListPic;
    private String jiqingMovieTicketPic;
    private String jiqingPreviewPic;
    private String label;
    private String liveCornerContent;
    private String liveEndPic;
    private String liveurl;
    private String oldIsEffective;
    private String onlineTime;
    private String overage;
    private String platform_id;
    private String proCode;
    private String proName;
    private String seq;
    private String signalBreakPic;
    private String signalType;
    private String templateId;
    private String trailerAddress;
    private String trailerWhile;
    private String tvId;
    private String tvName;
    private String tvNum;
    private String tvPyname;
    private String tvlogo;
    private String updateTime;
    private String verpic;
    private String viewback;
    private String playType;
    private Integer liveId;
    private Integer isBooking;
    private String bookingStartTime;
    private String bookingEndTime;
    private String saleStartTime;
    private String saleEndTime;
    private String beforePlayLogo;//播放前海报图
    private String endPlayLogo;//播放后海报图
    private String playLogo;//播放中海报图

    /*
    *直播产品包策略相关信息
	* */
    private String strategyName;//策略名称
    private Integer strategyType;//策略类型
    private Integer productPlayAfterMinutes;//直播播放后不能购买的时间
    private String notIsPayPic;//直播开启后不能进行购买图片
    private String saleStyle; //销售样式(1表示单一商品包，2表示集合商品包)

    private Integer nowPrice;//现价
    private Integer vipNowPrice;//vip现价


    public String getBeforePlayLogo() {
        return beforePlayLogo;
    }

    public void setBeforePlayLogo(String beforePlayLogo) {
        this.beforePlayLogo = beforePlayLogo;
    }

    public String getEndPlayLogo() {
        return endPlayLogo;
    }

    public void setEndPlayLogo(String endPlayLogo) {
        this.endPlayLogo = endPlayLogo;
    }

    public String getPlayLogo() {
        return playLogo;
    }

    public void setPlayLogo(String playLogo) {
        this.playLogo = playLogo;
    }

    public String getSaleStyle() {
        return null != saleStyle ? saleStyle : "1";
    }

    public void setSaleStyle(String saleStyle) {
        this.saleStyle = saleStyle;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public Integer getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(Integer strategyType) {
        this.strategyType = strategyType;
    }

    public Integer getProductPlayAfterMinutes() {
        return productPlayAfterMinutes;
    }

    public void setProductPlayAfterMinutes(Integer productPlayAfterMinutes) {
        this.productPlayAfterMinutes = productPlayAfterMinutes;
    }

    public String getNotIsPayPic() {
        return notIsPayPic;
    }

    public void setNotIsPayPic(String notIsPayPic) {
        this.notIsPayPic = notIsPayPic;
    }

    public Integer getIsBooking() {
        return null != isBooking ? isBooking : 0;
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

    public Integer getLiveId() {
        return null != liveId ? liveId : 0;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public String getApkBagName() {
        return apkBagName;
    }

    public void setApkBagName(String apkBagName) {
        this.apkBagName = apkBagName;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(String channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getChannelTypeName() {
        return channelTypeName;
    }

    public void setChannelTypeName(String channelTypeName) {
        this.channelTypeName = channelTypeName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCpChannelId() {
        return cpChannelId;
    }

    public void setCpChannelId(String cpChannelId) {
        this.cpChannelId = cpChannelId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCurrent_time() {
        return current_time;
    }

    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public String getDamaiDetailPic() {
        return damaiDetailPic;
    }

    public void setDamaiDetailPic(String damaiDetailPic) {
        this.damaiDetailPic = damaiDetailPic;
    }

    public String getDamaiId() {
        return damaiId;
    }

    public void setDamaiId(String damaiId) {
        this.damaiId = damaiId;
    }

    public String getDamaiListPic() {
        return damaiListPic;
    }

    public void setDamaiListPic(String damaiListPic) {
        this.damaiListPic = damaiListPic;
    }

    public String getDamaiName() {
        return damaiName;
    }

    public void setDamaiName(String damaiName) {
        this.damaiName = damaiName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEpg_date() {
        return epg_date;
    }

    public void setEpg_date(String epg_date) {
        this.epg_date = epg_date;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public String getHorpic() {
        return horpic;
    }

    public void setHorpic(String horpic) {
        this.horpic = horpic;
    }

    public String getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(String isEffective) {
        this.isEffective = isEffective;
    }

    public String getIsLoop() {
        return isLoop;
    }

    public void setIsLoop(String isLoop) {
        this.isLoop = isLoop;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getIsVip() {
        return isVip;
    }

    public void setIsVip(String isVip) {
        this.isVip = isVip;
    }

    public String getJiqingListPic() {
        return jiqingListPic;
    }

    public void setJiqingListPic(String jiqingListPic) {
        this.jiqingListPic = jiqingListPic;
    }

    public String getJiqingMovieTicketPic() {
        return jiqingMovieTicketPic;
    }

    public void setJiqingMovieTicketPic(String jiqingMovieTicketPic) {
        this.jiqingMovieTicketPic = jiqingMovieTicketPic;
    }

    public String getJiqingPreviewPic() {
        return jiqingPreviewPic;
    }

    public void setJiqingPreviewPic(String jiqingPreviewPic) {
        this.jiqingPreviewPic = jiqingPreviewPic;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLiveCornerContent() {
        return liveCornerContent;
    }

    public void setLiveCornerContent(String liveCornerContent) {
        this.liveCornerContent = liveCornerContent;
    }

    public String getLiveEndPic() {
        return liveEndPic;
    }

    public void setLiveEndPic(String liveEndPic) {
        this.liveEndPic = liveEndPic;
    }

    public String getLiveurl() {
        return liveurl;
    }

    public void setLiveurl(String liveurl) {
        this.liveurl = liveurl;
    }

    public String getOldIsEffective() {
        return oldIsEffective;
    }

    public void setOldIsEffective(String oldIsEffective) {
        this.oldIsEffective = oldIsEffective;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public String getOverage() {
        return overage;
    }

    public void setOverage(String overage) {
        this.overage = overage;
    }

    public String getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(String platform_id) {
        this.platform_id = platform_id;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getSignalBreakPic() {
        return signalBreakPic;
    }

    public void setSignalBreakPic(String signalBreakPic) {
        this.signalBreakPic = signalBreakPic;
    }

    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTrailerAddress() {
        return trailerAddress;
    }

    public void setTrailerAddress(String trailerAddress) {
        this.trailerAddress = trailerAddress;
    }

    public String getTrailerWhile() {
        return trailerWhile;
    }

    public void setTrailerWhile(String trailerWhile) {
        this.trailerWhile = trailerWhile;
    }

    public String getTvId() {
        return tvId;
    }

    public void setTvId(String tvId) {
        this.tvId = tvId;
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvNum() {
        return tvNum;
    }

    public void setTvNum(String tvNum) {
        this.tvNum = tvNum;
    }

    public String getTvPyname() {
        return tvPyname;
    }

    public void setTvPyname(String tvPyname) {
        this.tvPyname = tvPyname;
    }

    public String getTvlogo() {
        return tvlogo;
    }

    public void setTvlogo(String tvlogo) {
        this.tvlogo = tvlogo;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getVerpic() {
        return verpic;
    }

    public void setVerpic(String verpic) {
        this.verpic = verpic;
    }

    public String getViewback() {
        return viewback;
    }

    public void setViewback(String viewback) {
        this.viewback = viewback;
    }

    public Integer getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(Integer nowPrice) {
        this.nowPrice = nowPrice;
    }

    public Integer getVipNowPrice() {
        return vipNowPrice;
    }

    public void setVipNowPrice(Integer vipNowPrice) {
        this.vipNowPrice = vipNowPrice;
    }
}
