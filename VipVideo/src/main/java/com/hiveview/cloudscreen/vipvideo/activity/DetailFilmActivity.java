/**
 * @Title FilmDetailActivity.java
 * @Package com.hiveview.cloudscreen.vipvideo
 * @author haozening
 * @Description
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity;


import com.hiveview.cloudscreen.vipvideo.activity.component.BaseFilmDetailEpisode;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;

/**
 * @ClassName FilmDetailActivity
 * @Description 
 * @author haozening
 *
 */
public class DetailFilmActivity extends VipVideoDetailActivity {

	@Override
	public BaseFilmDetailEpisode getEpisodeComponent(AlbumEntity albumEntity) {
		return null;
	}
	
	@Override
	protected boolean isEpisodeDetail() {
		return false;
	}

}
