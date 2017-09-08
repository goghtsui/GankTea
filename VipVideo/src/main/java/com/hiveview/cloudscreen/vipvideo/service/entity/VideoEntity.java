package com.hiveview.cloudscreen.vipvideo.service.entity;



public class VideoEntity extends HiveviewBaseEntity {
	
	private static final long serialVersionUID = 155319625585392553L;
	
	private Integer cid;
	private Integer cp;
	private String desc;
	private String directors;
	private String epfocus;
	private Long eplen;
	private String epname;
	private Long eporder;
	private Integer eptype;
	private String epvid;
	private String mainActors;
	private String picurl;
	private Long videoId;
	private Long videosetId;
	private String year;
	private int playTime;
	private int duration;

	private String thirdPartyID;//片源方影片标识
	
	public Integer getCp() {
		if (null == cp) {
			return 0;
		}
		return cp;
	}
	public void setCp(Integer cp) {
		this.cp = cp;
	}
	public String getDirectors() {
		return directors;
	}
	public void setDirectors(String directors) {
		this.directors = directors;
	}
	public Long getEplen() {
		if (null == eplen) {
			return 0L;
		}
		return eplen;
	}
	public void setEplen(Long eplen) {
		this.eplen = eplen;
	}
	public Integer getEptype() {
		if (null == eptype) {
			return 0;
		}
		return eptype;
	}
	public void setEptype(Integer eptype) {
		this.eptype = eptype;
	}
	public String getEpvid() {
		return epvid;
	}
	public void setEpvid(String epvid) {
		this.epvid = epvid;
	}
	public String getMainActors() {
		return mainActors;
	}
	public void setMainActors(String mainActors) {
		this.mainActors = mainActors;
	}
	public Long getVideosetId() {
		if (null == videosetId) {
			return 0L;
		}
		return videosetId;
	}
	public void setVideosetId(Long videosetId) {
		this.videosetId = videosetId;
	}
	public int getPlayTime() {
		return playTime;
	}
	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public Long getEporder() {
		if (null == eporder) {
			return 0L;
		}
		return eporder;
	}
	public void setEporder(Long eporder) {
		this.eporder = eporder;
	}
	public Long getVideoId() {
		if (null == videoId) {
			return 0L;
		}
		return videoId;
	}
	public void setVideoId(Long videoId) {
		this.videoId = videoId;
	}
	public String getEpname() {
		return epname;
	}
	public void setEpname(String epname) {
		this.epname = epname;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getEpfocus() {
		return epfocus;
	}
	public void setEpfocus(String epfocus) {
		this.epfocus = epfocus;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getCid() {
		if (null == cid) {
			return 0;
		}
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String getThirdPartyID() {
		return thirdPartyID;
	}

	public void setThirdPartyID(String thirdPartyID) {
		this.thirdPartyID = thirdPartyID;
	}
}
