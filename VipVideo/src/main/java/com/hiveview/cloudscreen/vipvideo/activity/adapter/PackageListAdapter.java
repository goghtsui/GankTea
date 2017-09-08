package com.hiveview.cloudscreen.vipvideo.activity.adapter;


import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.service.entity.VIpGoodsContentApiVo;
import com.hiveview.cloudscreen.vipvideo.service.entity.VipGoodsApiVo;
import com.hiveview.cloudscreen.vipvideo.service.entity.VipPackageListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VipProductInfoEntity;
import com.hiveview.cloudscreen.vipvideo.util.ExpiryTimeCountUtil;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;

import java.util.List;

public class PackageListAdapter extends BaseAdapter {

    private static final String TAG = PackageListAdapter.class.getSimpleName();

    LayoutInflater mInflater;
    List<VIpGoodsContentApiVo> packageEntityList;
    ViewHolder holder;

    public PackageListAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<VIpGoodsContentApiVo> packageEntityList) {
        if (null != packageEntityList) {
            this.packageEntityList = packageEntityList;
        }
    }

    @Override
    public int getCount() {
        return packageEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return packageEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_buy_vip_item,
                    null);
            holder.by_vip_background = convertView.findViewById(R.id.btn_buy_vip);
            holder.tvVipTime = (TypeFaceTextView) convertView
                    .findViewById(R.id.tv_vip_time);
            holder.tvVipPrice = (TypeFaceTextView) convertView
                    .findViewById(R.id.tv_vip_price);
            holder.tvCurrentPrice = (TypeFaceTextView) convertView
                    .findViewById(R.id.tv_current_price);
            holder.tvCoin = (TextView) convertView.findViewById(R.id.tv_vip_coin);
            holder.tvDiscount = (TypeFaceTextView) convertView
                    .findViewById(R.id.tv_discount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        fillData(holder, position);
        return convertView;
    }


    private void fillData(ViewHolder holder, int position) {

        VIpGoodsContentApiVo entity = packageEntityList.get(position);
        if (null == entity) {
            return;
        }

        //显示当前价格，且原价和优惠策略都符合条件，为黄钻背景
        if (null != entity.getPresentPrice()) {
            holder.tvCurrentPrice.setText(entity.getPresentPrice());
            Log.i(TAG, "1");
            if (null != entity.getPrice()) {
                holder.tvVipPrice.setText(" 原价 " + entity.getPrice() + " 麦币");
            }
            if (null != entity.getStrategyId() && entity.getStrategyId() != 1) {
                holder.tvDiscount.setVisibility(View.VISIBLE);
                holder.tvDiscount.setText(entity.getStrategyName());
                holder.by_vip_background.setBackgroundResource(R.drawable.by_vip_background);
            } else {
                holder.tvDiscount.setVisibility(View.GONE);
                holder.by_vip_background.setBackgroundResource(R.drawable.default_bg_vip);
            }
        } else {
            //只有原价的价格，但当前价格没有，默认为普通背景且无优惠
            holder.by_vip_background.setBackgroundResource(R.drawable.default_bg_vip);
            if (null != entity.getPrice()) {
                holder.tvVipPrice.setText(" 原价 " + entity.getPrice() + " 麦币");
                holder.tvCurrentPrice.setText(entity.getPrice());
                holder.tvDiscount.setVisibility(View.GONE);
            } else {
                holder.tvCurrentPrice.setText("此包出现金额问题");
            }
            Log.i(TAG, "2");
        }


        //开通时间
        if (null != entity.getDuration() && null != entity.getGiveDuration()) {
            holder.tvVipTime.setText(entity.getDuration() + " + " + entity.getGiveDuration() + " 天");
        } else {
            holder.tvVipTime.setText(entity.getDuration() + " 天");
        }
    }


    private class ViewHolder {
        private View by_vip_background;
        private TypeFaceTextView tvVipTime;//套餐时间
        private TypeFaceTextView tvDiscount;//  套餐优惠策略
        private TypeFaceTextView tvCurrentPrice;// 套餐当前价格显示
        private TypeFaceTextView tvVipPrice;//套餐原价显示
        private TextView tvCoin;
    }


    public void clear() {
        holder.tvDiscount = null;
        holder.tvVipTime = null;
        holder.tvCurrentPrice = null;
        holder.tvVipPrice = null;
    }
}
