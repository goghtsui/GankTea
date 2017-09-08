package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.activity.component.BaseFilmDetailEpisode.DataHolder;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;

import java.util.ArrayList;
import java.util.List;

public  class EpisodeVarietyGroupAdapter extends BaseEpisodeGroupAdapter {

	private static final String TAG = "EpisodeVarietyRightAdapter";
	private List<DataHolder> result = new ArrayList<DataHolder>();
	private List<String> temp = new ArrayList<String>();
	public EpisodeVarietyGroupAdapter(Context context) {
		super(context);
	}

	@Override
	protected List<DataHolder> formatData(List<EpisodeListEntity> data) {
		for (EpisodeListEntity entity : data) {
			String year = getYear(entity.getYear());
			if (temp.contains(year)) {
				continue;
			} else {
				temp.add(year);
			}
		}
		int index = 0;
		for (String s : temp) {
			DataHolder holder = new DataHolder(index, s);
			result.add(holder);
			index++;
		}
		for (DataHolder holder : result) {
//			Log.d(TAG, "des : " + holder.description);
		}
		return result;
	}


	public static String getYear(String str) {
		if (str.length() > 4) {
			return str.substring(0, 4);
		} else {
			return str;
		}
	}

}
