package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2016/11/3
 * @Description
 */
public class LiveStatusEntity extends HiveviewBaseEntity {
    //频道id必须要传
    private int tvId;
    // 频道名称 必须要传
    private String tvName;
    // 频道名称拼音 不是必须 目前没有用到
    private String tvPyname;
    // 频道台标 必须要传
    private String tvlogo;
    // 直播地址
    private String liveurl;
    // 直播类型 最好传上
    private Integer cp;
    // 1 直播，2：轮播 必须要传
    private String signalType;
    private String tvNum;
    private long playedTime;
    private int typeId;// 频道所属的分类类型的id；
    private boolean isFavourite;
    //必须要传
    private String damaiId;
    //必须要传 目前获取的值应该是null
    private String dmIdVersion;//其epg当前的version，用来判断是否有更新。
    /**
     * 播放前
     */
    private String beforePlayLogo;
    /**
     * 播放后
     */
    private String endPlayLogo;
    /**
     * 播放时
     */
    private String playLogo;
    /**
     * 频道播完时间
     */
    private String endTime;
    /**
     * 频道开始播的时间
     */
    private String startTime;
    /**
     * 频道播放日期
     */
    private String playDate;
    /**
     * 是否免费 1免费 2收费
     */
    private String isPay;
    /**
     * 0 未购买   1 已购买
     */
    private String orderStatus;
    /**
     * 播放模式   0 直接调用   1 调度调用
     */
    private int playType;  //必须传
    /**
     * 回看地址
     */
    private String viewbackUrl;

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

    public String getLiveurl() {
        return liveurl;
    }

    public void setLiveurl(String liveurl) {
        this.liveurl = liveurl;
    }

    public Integer getCp() {
        return cp;
    }

    public void setCp(Integer cp) {
        this.cp = cp;
    }

    public String getSignalType() {
        return signalType;
    }

    public void setSignalType(String signalType) {
        this.signalType = signalType;
    }

    public String getTvNum() {
        return tvNum;
    }

    public void setTvNum(String tvNum) {
        this.tvNum = tvNum;
    }

    public long getPlayedTime() {
        return playedTime;
    }

    public void setPlayedTime(long playedTime) {
        this.playedTime = playedTime;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public String getDamaiId() {
        return damaiId;
    }

    public void setDamaiId(String damaiId) {
        this.damaiId = damaiId;
    }

    public String getDmIdVersion() {
        return dmIdVersion;
    }

    public void setDmIdVersion(String dmIdVersion) {
        this.dmIdVersion = dmIdVersion;
    }

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

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }

    public String getViewbackUrl() {
        return viewbackUrl;
    }

    public void setViewbackUrl(String viewbackUrl) {
        this.viewbackUrl = viewbackUrl;
    }

//    public String getTestJson() {
//        return "{\"tvId\":10547,\"tvName\":\"CCTV1综合\",\"tvPyname\":\"cctv1gz\"," +
//                "\"tvlogo\":\"http://pic.pthv.gitv.tv/tvimg/2016/04/19/14/03/41/1461045821828.jpg\"," +
//                "\"liveurl\":\"http://220.112.196.79:81/live/1/live.m3u8\",\"seq\":null,\"tvNum\":\"001\",\"cp\":2," +
//                "\"signalType\":1,\"proName\":\"广东省\",\"proCode\":\"19\",\"cityName\":\"深圳\",\"cityCode\":\"1915\"," +
//                "\"viewbackUrl\":\"http://replay.video.pthv.gitv.tv/replay/cctv1/\"," +
//                "\"damaiId\":\"cca3e0e85a2211e6bb9e28d244f88479\",\"damaiName\":null,\"dmIdVersion\":null," +
//                "\"typeName\":null,\"typeSeq\":null,\"typeId\":null,\"productPackId\":null,\"epgList\":null}";
//    }
}
