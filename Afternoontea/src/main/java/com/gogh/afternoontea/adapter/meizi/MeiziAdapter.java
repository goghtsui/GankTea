package com.gogh.afternoontea.adapter.meizi;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.gogh.afternoontea.R;
import com.gogh.afternoontea.entity.meizi.MeiziBean;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by huxq17 on 2016/4/12.
 */
public class MeiziAdapter extends BaseCardAdapter {

    private List<MeiziBean> datas;
    private Context context;

    public MeiziAdapter(List<MeiziBean> datas, Context context) {
        this.datas = datas;
        this.context = context;
    }

    public void setData(List<MeiziBean> datas){
        this.datas = datas;
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public int getCardLayoutId() {
        return R.layout.meizi_card_item;
    }

    @Override
    public void onBindData(int position, @NonNull View cardview) {
        if (datas == null || datas.size() == 0) {
            return;
        }
        ImageView imageView = (ImageView) cardview.findViewById(R.id.meizi_card_item_imageview);
        MeiziBean meizi = datas.get(position);
        Picasso.with(context).load(meizi.getImageurl()).config(Bitmap.Config.ARGB_8888).into(imageView);
    }

    /**
     * 如果可见的卡片数是3，则可以不用实现这个方法
     * @return
     */
    @Override
    public int getVisibleCardCount() {
        return 3;
    }
}
