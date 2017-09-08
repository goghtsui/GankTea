package com.hiveview.cloudscreen.vipvideo.service.entity;

import com.hiveview.cloudscreen.vipvideo.util.DateWithRecordUtils;


/**
 * 
 * @ClassName AppStoreEntity
 * @Description 记录应用游戏市场的实体
 * @author xieyi
 * @date 2014-9-24 下午4:42:13
 * 
 */
public class AppStoreEntity extends HiveviewBaseEntity {

	/**
	 * @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String appName;// 应用名称
	private String iconUri;// 应用图标的uri
	private int iconId = -1;// 如果是外接存储应用，那么此项有值
	private String packageName;// 包名
	private long launchTime;// 应用启动时间
	private String formatTime;// 按yyyy-MM-dd格式化的时间

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getIconUri() {
		return iconUri;
	}

	public void setIconUri(String iconUri) {
		this.iconUri = iconUri;
	}

	public int getIconId() {
		return iconId;
	}

	public void setIconId(int iconId) {
		this.iconId = iconId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public long getLaunchTime() {
		return launchTime;
	}

	public void setLaunchTime(long launchTime) {
		this.launchTime = launchTime;
	}

	public String getFormatTime() {
		return formatTime;
	}

	public void setFormatTime(String formatTime) {
		this.formatTime = formatTime;
	}

	public void setFormatTime(long formatTime) {
		this.formatTime = DateWithRecordUtils.getCurrentDate(formatTime);
	}

	@Override
	public String toString() {
		return "AppStoreEntity{" +
				"appName='" + appName + '\'' +
				", iconUri='" + iconUri + '\'' +
				", iconId=" + iconId +
				", packageName='" + packageName + '\'' +
				", launchTime=" + launchTime +
				", formatTime='" + formatTime + '\'' +
				'}';
	}
}
