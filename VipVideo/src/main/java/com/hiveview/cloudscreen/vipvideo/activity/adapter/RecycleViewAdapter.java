package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.DetailRecommendListEntity;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.MarqueeTextView;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RecycleViewAdapter extends
        RecyclerView.Adapter<RecycleViewAdapter.RecycleViewViewHolder> {

    RecycleViewViewHolder holder;
    RecyclerView view;
    List<DetailRecommendListEntity> listEntity = new ArrayList<>();

    List<View> focusView = new ArrayList<>();
    private RecommentOnItemListener itemListener = null;

    public interface RecommentOnItemListener {
        public void onItemClick(View view, int postion);
    }

    public void setData(List<DetailRecommendListEntity> listEntity) {
        if (listEntity != null) {
            this.listEntity = listEntity;
            notifyDataSetChanged();
        }
    }

    /**
     * 设置Item点击监听
     *
     * @param listener
     */
    public void setOnItemClickListener(RecommentOnItemListener listener) {
        this.itemListener = listener;
    }

    public RecycleViewAdapter(RecyclerView view) {
        super();
        this.view = view;
    }

    @Override
    public int getItemCount() {
        return listEntity.size();
    }

    @Override
    public void onBindViewHolder(RecycleViewViewHolder viewHolder, int position) {
        DetailRecommendListEntity entity = listEntity.get(position);

        String url = "";
        if (!TextUtils.isEmpty(entity.getPicUrl())) {
            url = entity.getPicUrl();
        }
        String urlSource = Uri.parse(url).getQueryParameter("source");
        if (!TextUtils.isEmpty(urlSource)) {
            if (urlSource.equals("Qiyi")) {
                url = DisplayImageUtil.createImgUrl(entity.getPicUrl(), true);
            }
        }

        Log.i("test", "url-->" + url + "-----urlSource---->" + urlSource);
        new DisplayImageUtil.Builder().setRound(17).setEmptyUriImage(ResourceProvider.getInstance().getDefaultCoverListPortrait())
                .setErrorImage(ResourceProvider.getInstance().getDefaultCoverListPortrait()).setLoadingImage(ResourceProvider.getInstance().getDefaultCoverListPortrait()).build()
                .displayImage(url, viewHolder.bg);


        if (listEntity.get(position).getName() != null && listEntity.get(position).getName() != "") {
            viewHolder.name.setText(listEntity.get(position).getName());
        } else {
            viewHolder.name.setVisibility(View.INVISIBLE);
        }
        int result = listEntity.get(position).getScore().compareTo(BigDecimal.ZERO);
        if (result > 0) {
            viewHolder.star_number.setText(listEntity.get(position).getScore() + "");
        } else {
            viewHolder.star_number.setText(CloudScreenApplication.getInstance().getResources().getString(R.string.view_film_recommend));
        }

        if (viewHolder.name.length() > 6) {
            viewHolder.name.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        } else {
            viewHolder.name.setGravity(Gravity.CENTER);
        }

        if (null != listEntity.get(position).getCornerPic()) {
            DisplayImageUtil.getInstance().displayImage(listEntity.get(position).getCornerPic(), viewHolder.mark);
        }
    }

    @Override
    public RecycleViewViewHolder onCreateViewHolder(ViewGroup viewGroup,
                                                    int position) {
        View convertView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.fragment_right_item_layout, null);
        // 创建一个ViewHolder
        holder = new RecycleViewViewHolder(convertView, itemListener);
        resetFocusColor(UserStateUtil.getInstance().getUserStatus());
        return holder;
    }

    public class RecycleViewViewHolder extends ViewHolder implements
            OnFocusChangeListener, View.OnClickListener {

        public View view_recomment;
        public ImageView bg;
        public ImageView mark;
        public MarqueeTextView name;
        public View nopress;
        public View press;
        public TypeFaceTextView star_number;
        public RecommentOnItemListener mListener;

        public RecycleViewViewHolder(View itemView, RecommentOnItemListener onItemListener) {
            super(itemView);
            this.mListener = onItemListener;
            itemView.setFocusable(true);
            itemView.setOnFocusChangeListener(this);
            itemView.setOnClickListener(this);

            view_recomment = itemView.findViewById(R.id.view_recommend);
            focusView.add(view_recomment);
            bg = (ImageView) itemView.findViewById(R.id.bg);
            mark = (ImageView) itemView.findViewById(R.id.mark);
            nopress = itemView.findViewById(R.id.nopress);
            press = itemView.findViewById(R.id.press);
            name = (MarqueeTextView) itemView.findViewById(R.id.name);
            star_number = (TypeFaceTextView) itemView
                    .findViewById(R.id.tv_score_star_bottom);
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                setFocusItem(v);
            } else {
                setLostFocusItem(v);
            }
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }
        }

    }


    public void resetFocusColor(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case VIPUSER:
                for (View v : focusView) {
                    v.setBackgroundResource(R.drawable.focused_view_vip_selector);
                }
                break;
            default:
                for (View v : focusView) {
                    v.setBackgroundResource(R.drawable.focused_selector);
                }
                break;
        }
    }


    @SuppressLint("NewApi")
    private void setFocusItem(View view) {
        Log.d("fragment", "setFocusOneColumnItemAnimation");
        View nopress = view.findViewById(R.id.nopress);
        View press = view.findViewById(R.id.press);
        MarqueeTextView name = (MarqueeTextView) view.findViewById(R.id.name);
        View star_number = view.findViewById(R.id.tv_score_star_bottom);

        nopress.setVisibility(View.INVISIBLE);
        press.setVisibility(View.VISIBLE);
        ((MarqueeTextView) name).setTextColor(Color.WHITE);
        ((MarqueeTextView) name).setIsInFocusView(true);
        ((MarqueeTextView) name).setTextSize(24f);
        ((MarqueeTextView) name).setEllipsize(TextUtils.TruncateAt
                .valueOf("MARQUEE"));

        nopress.animate().alpha(0f).setDuration(300).start();
        press.animate().alpha(1f).setDuration(300).start();
        name.animate().translationY(-55).setDuration(300).start();
        star_number.animate().translationY(0).alpha(1f).setDuration(300)
                .setInterpolator(new DecelerateInterpolator()).start();
    }

    @SuppressLint("NewApi")
    private void setLostFocusItem(View view) {
        View nopress = view.findViewById(R.id.nopress);
        View press = view.findViewById(R.id.press);
        MarqueeTextView name = (MarqueeTextView) view.findViewById(R.id.name);
        View star_number = view.findViewById(R.id.tv_score_star_bottom);

        nopress.setVisibility(View.VISIBLE);
        press.setVisibility(View.INVISIBLE);
        ((MarqueeTextView) name).setTextColor(name.getResources().getColor(R.color.recomment_gray));
        ((MarqueeTextView) name).setIsInFocusView(false);
        ((MarqueeTextView) name).setTextSize(20f);
        ((MarqueeTextView) name).setEllipsize(TextUtils.TruncateAt
                .valueOf("END"));

        nopress.animate().alpha(1f).setDuration(300).start();
        press.animate().alpha(0f).setDuration(300).start();
        name.animate().translationY(0).setDuration(300).start();
        star_number.animate().translationY(55).alpha(0f).setDuration(300)
                .setInterpolator(new DecelerateInterpolator()).start();

    }

}
