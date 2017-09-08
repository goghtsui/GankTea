/**
 * @Title EpisodeVarietyLeftAdapter.java
 * @Package com.hiveview.cloudscreen.video.activity.adapter
 * @author haozening
 * @date 2014年9月10日 下午3:10:59
 * @Description 
 * @version V1.0
 */
package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;

import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoEntity;


/**
 * 综艺左侧剧集列表
 * @ClassName EpisodeVarietyLeftAdapter
 * @Description 
 * @author haozening
 * @date 2014年9月10日 下午3:10:59
 * 
 */
public class EpisodeVarietyAdapter extends BaseEpisodeAdapter {

	public EpisodeVarietyAdapter(Context context) {
		super(context);
	}

	@Override
	public String getText(EpisodeListEntity entity) {
		return entity.getYear();

	}
	
}