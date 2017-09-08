package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.hiveview.cloudscreen.vipvideo.activity.component.BaseFilmDetailEpisode.DataHolder;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;

public class EpisodeTvGroupAdapter extends BaseEpisodeGroupAdapter {

    public EpisodeTvGroupAdapter(Context context) {
        super(context);
    }

    @Override
    protected List<DataHolder> formatData(List<EpisodeListEntity> data) {
        List<DataHolder> list = new ArrayList<DataHolder>();
        if (data.size() == 0) {
            return null;
        } else if (data.get(data.size() - 1).getIsEffective() == 1) {
            int size = data.size();

            // 获取一共有多少组
            int step = (int) Math.ceil(size / 20);
            if (size % 20 == 0) { // 如果对20取模为0，表示已经分完一组
                step--;
            }
            String episodeString = "";
            if (step == 0) { // 如果只有一组数据执行以下逻辑
                if (data.size() < 10) { // 小于十条，显示格式如下(格式:01-0x)
                    episodeString = "01-0" + data.size();
                } else { // 大于十条显示格式如下(01-xx)
                    episodeString = "01-" + data.size();
                }
                // 将剧集分组数据保存在Holder中
                DataHolder holder = new DataHolder(0, episodeString);
                list.add(holder);
            } else { // 如果有多组数据执行以下逻辑
                for (int i = 0; i <= step; i++) {
                    int current = i * 20 + 1;// 当前第一条数据是第几集
                    DataHolder holder = null;
                    if (i == 0) { // 如果是第一组数据，显示逻辑如下(格式:0x-xx)
                        holder = new DataHolder(i, "0" + current + "-" + (current + 19));
                    } else if (i == step) {// 如果是最后一组数据，显示逻辑如下(格式:xx-data.size)
                        holder = new DataHolder(i, current + "-" + data.size());
                    } else {// 其他组数据，显示逻辑如下(格式:xx-xx)
                        holder = new DataHolder(i, current + "-" + (current + 19));
                    }
                    list.add(holder);
                }
            }
            return list;
        } else {
            // 获取数据总数
            int size = data.get(data.size() - 1).getPhase();

            // 获取一共有多少组
            int step = (int) Math.ceil(size / 20);
            if (size % 20 == 0) { // 如果对20取模为0，表示已经分完一组
                step--;
            }
            String episodeString = "";
            if (step == 0) { // 如果只有一组数据执行以下逻辑
                if (data.get(data.size() - 1).getPhase() < 10) { // 小于十条，显示格式如下(格式:01-0x)
                    episodeString = "01-0" + data.get(data.size() - 1).getPhase();
                } else { // 大于十条显示格式如下(01-xx)
                    episodeString = "01-" + data.get(data.size() - 1).getPhase();
                }
                // 将剧集分组数据保存在Holder中
                DataHolder holder = new DataHolder(0, episodeString);
                list.add(holder);
            } else { // 如果有多组数据执行以下逻辑
                for (int i = 0; i <= step; i++) {
                    int current = i * 20 + 1;// 当前第一条数据是第几集
                    DataHolder holder = null;
                    if (i == 0) { // 如果是第一组数据，显示逻辑如下(格式:0x-xx)
                        holder = new DataHolder(i, "0" + current + "-" + (current + 19));
                    } else if (i == step) {// 如果是最后一组数据，显示逻辑如下(格式:xx-data.size)
                        holder = new DataHolder(i, current + "-" + data.get(data.size() - 1).getPhase());
                    } else {// 其他组数据，显示逻辑如下(格式:xx-xx)
                        holder = new DataHolder(i, current + "-" + (current + 19));
                    }
                    list.add(holder);
                }
            }
            return list;
        }
    }
}
