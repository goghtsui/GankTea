package com.hiveview.cloudscreen.vipvideo.service.entity;

/**
 * @author xieyi
 * @ClassName AlbumEntity
 * @Description 专辑实体
 * @date 2014-9-5 下午2:19:02
 */
public class AlbumEntity extends HiveviewBaseEntity {
    /**
     * 是否预售
     */

    private int programsetId;
    /**
     * 上线时间
     */
    private String onlineTime;
    /**
     * 是否付费（1=是，0=否）
     */
    private Integer chargingType;
    private String CornerImage;
    private String albumDesc;//影片详情
    private String albumType;//专辑类型 0单剧集 1多剧集
    private String albumHbPic;//极清专用详情图
    private Integer chnId;//所属频道id
    private String directors;//导演
    private String duration;//时长
    private Integer episodeTotal;//总集数
    private Integer episodeUpdated;//更新集数
    private String focus;//看点
    private Integer id;//主键id
    private String issueTime;//首次上线时间 格式："2014-11-12 14:58:56'
    private Integer is3d;//是否3D 1=是，0=否
    private Integer isVip;//是否vip:1=是，0=否
    private String keyword;//关键词
    private String labels;//标签
    private String mainActors;//主演
    private String albumName;//专辑名称
    private int page;
    private int pageIndex;
    private int pageSize;
    private String albumXqyPic;//通用类型的图片
    private String score;//评分
    private Integer seq;//排序
    private Integer year;//年份
    private int isEffective; //剧集状态【3-爱奇艺有效、极清有效，2-爱奇艺有效、极清无效，1爱奇艺无效、极清有效，0-爱奇艺无效、极清无效】
    private String aqyId;
    private Integer aqyIsEffective;//判断超清按钮显示或隐藏（1：显示0：隐藏）
    private String jqId;
    private Integer jqIsEffective;//判断4k按钮显示或隐藏（1：显示0：隐藏）
    private int stype;
    private Integer videoId;


    public String getOnlineTime() {
        return onlineTime;
    }

    public String getCornerImage() {
        return CornerImage;
    }

    public void setCornerImage(String cornerImage) {
        CornerImage = cornerImage;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;

    }

    public Integer getChargingType() {
        return chargingType;
    }

    public void setChargingType(Integer chargingType) {
        this.chargingType = chargingType;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public String getAlbumType() {
        return albumType;
    }

    public void setAlbumType(String albumType) {
        this.albumType = albumType;
    }

    public String getAlbumHbPic() {
        return albumHbPic;
    }

    public void setAlbumHbPic(String albumHbPic) {
        this.albumHbPic = albumHbPic;
    }

    public Integer getChnId() {
        return null != chnId ? chnId : 0;
    }

    public void setChnId(Integer chnId) {
        this.chnId = chnId;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getEpisodeTotal() {
        return episodeTotal;
    }

    public void setEpisodeTotal(Integer episodeTotal) {
        this.episodeTotal = episodeTotal;
    }

    public Integer getEpisodeUpdated() {
        return episodeUpdated;
    }

    public void setEpisodeUpdated(Integer episodeUpdated) {
        this.episodeUpdated = episodeUpdated;
    }

    public String getFocus() {
        return focus;
    }

    public void setFocus(String focus) {
        this.focus = focus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(String issueTime) {
        this.issueTime = issueTime;
    }

    public Integer getIs3d() {
        return is3d;
    }

    public void setIs3d(Integer is3d) {
        this.is3d = is3d;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getMainActors() {
        return mainActors;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getAlbumXqyPic() {
        return albumXqyPic;
    }

    public void setAlbumXqyPic(String albumXqyPic) {
        this.albumXqyPic = albumXqyPic;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public int getIsEffective() {
        return isEffective;
    }

    public void setIsEffective(int isEffective) {
        this.isEffective = isEffective;
    }

    public String getAqyId() {
        return aqyId;
    }

    public void setAqyId(String aqyId) {
        this.aqyId = aqyId;
    }

    public Integer getAqyIsEffective() {
        return aqyIsEffective;
    }

    public void setAqyIsEffective(Integer aqyIsEffective) {
        this.aqyIsEffective = aqyIsEffective;
    }

    public String getJqId() {
        return jqId;
    }

    public void setJqId(String jqId) {
        this.jqId = jqId;
    }

    public Integer getJqIsEffective() {
        return jqIsEffective;
    }

    public void setJqIsEffective(Integer jqIsEffective) {
        this.jqIsEffective = jqIsEffective;
    }

    public int getStype() {
        return stype;
    }

    public void setStype(int stype) {
        this.stype = stype;
    }

    public Integer getVideoId() {
        return videoId;
    }

    public void setVideoId(Integer videoId) {
        this.videoId = videoId;
    }

    public int getProgramsetId() {
        return programsetId;
    }

    public void setProgramsetId(Integer programsetId) {
        this.programsetId = programsetId;
    }

    @Override
    public String toString() {
        return "AlbumEntity{" +
                "programsetId=" + programsetId +
                ", onlineTime='" + onlineTime + '\'' +
                ", chargingType=" + chargingType +
                ", CornerImage='" + CornerImage + '\'' +
                ", albumDesc='" + albumDesc + '\'' +
                ", albumType='" + albumType + '\'' +
                ", albumHbPic='" + albumHbPic + '\'' +
                ", chnId=" + chnId +
                ", directors='" + directors + '\'' +
                ", duration='" + duration + '\'' +
                ", episodeTotal=" + episodeTotal +
                ", episodeUpdated=" + episodeUpdated +
                ", focus='" + focus + '\'' +
                ", id=" + id +
                ", issueTime='" + issueTime + '\'' +
                ", is3d=" + is3d +
                ", isVip=" + isVip +
                ", keyword='" + keyword + '\'' +
                ", labels='" + labels + '\'' +
                ", mainActors='" + mainActors + '\'' +
                ", albumName='" + albumName + '\'' +
                ", page=" + page +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", albumXqyPic='" + albumXqyPic + '\'' +
                ", score='" + score + '\'' +
                ", seq=" + seq +
                ", year=" + year +
                ", isEffective=" + isEffective +
                ", aqyId='" + aqyId + '\'' +
                ", aqyIsEffective=" + aqyIsEffective +
                ", jqId='" + jqId + '\'' +
                ", jqIsEffective=" + jqIsEffective +
                ", stype=" + stype +
                ", videoId=" + videoId +
                '}';
    }
}
