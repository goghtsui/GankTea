package com.hiveview.cloudscreen.vipvideo.service.entity;


import com.hiveview.cloudscreen.vipvideo.util.DateWithRecordUtils;

/**
 * 
 * @ClassName VideoRecordEntity
 * @Description 用来存放点播和极清影片观看记录的实体
 * @author xieyi
 * @date 2014-9-24 下午5:03:15
 * 
 */
public class VideoRecordEntity extends HiveviewBaseEntity {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private int source;// 标识来源 0:点播 1:极清
	private String albumId;// 极清的专辑Id
	private String picUrl;// 图片路径
	private String movieName;// 影片名称
	private long recordTime;// 播放时的时间
	private String formatTime;// 按yyyy-MM-dd格式化的时间
	private Integer videoset_type;// 专辑频道（电影、电视剧）
	private String vrsAlbumId;
	private String vrsTvId;
	private Integer cpId;
	private int videoset_id;
	private String currentEpisode;

	public String getCurrentEpisode() {
		return currentEpisode;
	}

	public void setCurrentEpisode(String currentEpisode) {
		this.currentEpisode = currentEpisode;
	}

	public int getVideoset_id() {
		return videoset_id;
	}

	public void setVideoset_id(int videoset_id) {
		this.videoset_id = videoset_id;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getAlbumId() {
		return albumId;
	}

	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getMovieName() {
		return movieName;
	}

	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}

	public long getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(long recordTime) {
		this.recordTime = recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = Long.valueOf(recordTime);
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = DateWithRecordUtils.getCurrentDate(Long
                .valueOf(formatTime));
	}

	public void setFormatTime(long formatTime) {
		this.formatTime = DateWithRecordUtils.getCurrentDate(formatTime);
	}

	public Integer getVideoset_type() {
		return videoset_type;
	}

	public void setVideoset_type(Integer videoset_type) {
		this.videoset_type = videoset_type;
	}

	public String getVrsAlbumId() {
		return vrsAlbumId;
	}

	public void setVrsAlbumId(String vrsAlbumId) {
		this.vrsAlbumId = vrsAlbumId;
	}

	public String getVrsTvId() {
		return vrsTvId;
	}

	public void setVrsTvId(String vrsTvId) {
		this.vrsTvId = vrsTvId;
	}

	public Integer getCpId() {
		return cpId;
	}

	public void setCpId(Integer cpId) {
		this.cpId = cpId;
	}

	@Override
	public String toString() {
		return "VideoRecordEntity [source=" + source + ", albumId=" + albumId + ", picUrl=" + picUrl + ", movieName=" + movieName + ", recordTime="
				+ recordTime + ", formatTime=" + formatTime + ", videoset_type=" + videoset_type + ", vrsAlbumId=" + vrsAlbumId + ", vrsTvId="
				+ vrsTvId + ", cpId=" + cpId + "]";
	}
	
	
}
