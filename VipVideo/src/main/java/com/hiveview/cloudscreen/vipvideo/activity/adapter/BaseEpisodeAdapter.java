package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.EpisodeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;
import com.hiveview.cloudscreen.vipvideo.view.wheelview.BaseWheelAdapter;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public abstract class BaseEpisodeAdapter extends BaseWheelAdapter {

    public Context context;
    private AlbumEntity albumEntity;
    protected List<EpisodeListEntity> list = new ArrayList<EpisodeListEntity>();
    private LayoutInflater inflater;
    private ProductInfoEntity productInfoEntity;

    public BaseEpisodeAdapter(Context context) {
        super();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void setProductInfo(ProductInfoEntity productInfoEntity) {
        this.productInfoEntity = productInfoEntity;
    }

    public void setData(List<EpisodeListEntity> list, AlbumEntity albumEntity) {
        if (null != list) {
            this.list = list;
        }
        this.albumEntity = albumEntity;
//        albumEntity.setTrySeeEp(5);
//        albumEntity.setIsVip(0);
//        albumEntity.setChargingType(1);
    }

    public List<EpisodeListEntity> getData() {
        return list;
    }

    public EpisodeListEntity getEntity(int position) {
        if (null != list) {
            return list.get(position);
        } else {
            return null;
        }
    }

    public abstract String getText(EpisodeListEntity entity);

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.episode_item, null);
        TypeFaceTextView textView = (TypeFaceTextView) view.findViewById(R.id.epTextView);
        ImageView imageView = (ImageView) view.findViewById(R.id.ep_icon);
        textView.setText(getText(list.get(position)));
        view.setTag(list.get(position).getPhase());


        if (null != productInfoEntity) {
            //只有720收费中有免费试看时打角标
            if ((albumEntity.getIsVip() == 0 && albumEntity.getChargingType() == 1)) {
                if (productInfoEntity.getMultiSetNumbers() >= ++position) {
                    imageView.setVisibility(View.VISIBLE);
                } else {
                    imageView.setVisibility(View.INVISIBLE);
                }
            } else if (albumEntity.getIsVip() == 1 && albumEntity.getChargingType() == 1) {
                if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                } else {
                    if (productInfoEntity.getMultiSetNumbers() >= ++position) {
                        imageView.setVisibility(View.VISIBLE);
                    } else {
                        imageView.setVisibility(View.INVISIBLE);
                    }
                }
            }
        }


        return view;
    }

    private void showFreeIcon() {
    }

    @Override
    public void nextView(View nextView) {
        if (null != nextView) {
            TypeFaceTextView textView = (TypeFaceTextView) nextView.findViewById(R.id.epTextView);
            ImageView imageView = (ImageView) nextView.findViewById(R.id.ep_icon);
            if (null != textView) {
                textView.setTextColor(Color.parseColor("#9a9a9a"));
                nextView.animate().scaleX(1f).scaleY(1f).start();
            }
        }
    }

    @Override
    public void currentView(View currentView) {
        if (null != currentView) {
            TypeFaceTextView textView = (TypeFaceTextView) currentView.findViewById(R.id.epTextView);
            ImageView imageView = (ImageView) currentView.findViewById(R.id.ep_icon);
            if (null != textView) {
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                currentView.animate().scaleX(1.5f).scaleY(1.5f).start();
            }
        }
    }

    @Override
    public void preView(View preView) {
        if (null != preView) {
            TypeFaceTextView textView = (TypeFaceTextView) preView.findViewById(R.id.epTextView);
            ImageView imageView = (ImageView) preView.findViewById(R.id.ep_icon);
            if (null != textView) {
                textView.setTextColor(Color.parseColor("#9a9a9a"));
                preView.animate().scaleX(1f).scaleY(1f).start();
            }
        }
    }
}
