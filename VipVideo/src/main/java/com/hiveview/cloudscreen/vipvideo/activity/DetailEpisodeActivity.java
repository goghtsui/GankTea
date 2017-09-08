/**
 * @Title CartoonActivity.java
 * @Package com.hiveview.cloudscreen.vipvideo.activity
 * @author haozening
 * @date 2014年9月10日 下午7:57:47
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity;


import com.hiveview.cloudscreen.vipvideo.activity.component.BaseFilmDetailEpisode;
import com.hiveview.cloudscreen.vipvideo.activity.component.TvDetailEpisode;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;

/**
 * @ClassName CartoonActivity
 * @Description 
 * @author haozening
 * @date 2014年9月10日 下午7:57:47
 * 
 */
public class DetailEpisodeActivity extends VipVideoDetailActivity {

	
	@Override
	public BaseFilmDetailEpisode getEpisodeComponent(AlbumEntity albumEntity) {
		return new TvDetailEpisode(this, albumEntity);
	}

}
