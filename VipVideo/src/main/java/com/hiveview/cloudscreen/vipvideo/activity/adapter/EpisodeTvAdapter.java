package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;
import android.util.Log;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoEntity;

import java.util.List;


public class EpisodeTvAdapter extends BaseEpisodeAdapter {

    public EpisodeTvAdapter(Context context) {
        super(context);
    }

    @Override
    public String getText(EpisodeListEntity entity) {
        return String.format(context.getString(R.string.first_set), "" + entity.getPhase());
    }

}
