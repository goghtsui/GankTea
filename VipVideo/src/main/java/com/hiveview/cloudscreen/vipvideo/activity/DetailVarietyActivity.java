/**
 * @Title VarietyActivity.java
 * @Package com.hiveview.cloudscreen.video.activity
 * @author haozening
 * @date 2014年9月10日 下午3:08:12
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity;


import com.hiveview.cloudscreen.vipvideo.activity.component.BaseFilmDetailEpisode;
import com.hiveview.cloudscreen.vipvideo.activity.component.VarietyDetailEpisode;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;

/**
 * @ClassName VarietyActivity
 * @Description 
 * @author haozening
 * @date 2014年9月10日 下午3:08:12
 * 
 */
public class DetailVarietyActivity extends VipVideoDetailActivity {

	@Override
	public BaseFilmDetailEpisode getEpisodeComponent(AlbumEntity albumEntity) {
		return new VarietyDetailEpisode(this, albumEntity);
	}

}
