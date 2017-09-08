package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.SizeConstant;
import com.hiveview.cloudscreen.vipvideo.common.factory.resource.ResourceProvider;
import com.hiveview.cloudscreen.vipvideo.service.entity.AppStoreEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecordEntity;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.view.EffectiveMarqueeTextView;
import com.hiveview.cloudscreen.vipvideo.view.HistoryView;
import com.hiveview.cloudscreen.vipvideo.view.HorizontalListView;

import java.util.List;


/**
 * @author xieyi
 * @ClassName RecordDownAdapter
 * @Description 历史界面item下层滚动视图
 * @date 2014-9-26 下午3:58:19
 */
public class RecordDownAdapter extends BaseAdapter {

    private HistoryView historyView;
    private HorizontalListView listView;
    private Context ctx;
    private LayoutInflater inflater;
    private int parentPosition;
    private List<AppStoreEntity> entitys;
    private boolean isDelete;
    private RecordEntity recordEntity;
    private int startX;
    private SizeConstant sizeConstant;

    public RecordDownAdapter(Context context, HistoryView historyView, HorizontalListView listView, int parentPosition, boolean isDelete,
                             RecordUpAdapter.ClickCallBack callBack, RecordEntity recordEntity, SizeConstant sizeConstant) {
        this.ctx = context;
        this.historyView = historyView;
        this.listView = listView;
        this.parentPosition = parentPosition;
        this.isDelete = isDelete;
        this.callBack = callBack;
        this.recordEntity = recordEntity;
        this.sizeConstant = sizeConstant;
        inflater = LayoutInflater.from(ctx);
        startX = (int) ctx.getResources().getDimensionPixelSize(R.dimen.recordDownAdapter_startX);// 209
    }

    public void setData(List<AppStoreEntity> entitys) {
        this.entitys = entitys;
    }

    @Override
    public int getCount() {
        return entitys.size();
    }

    @Override
    public Object getItem(int position) {
        return entitys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        AppRecordHolder holder = null;
        if (null == convertView) {
            holder = new AppRecordHolder();
            view = inflater.inflate(R.layout.record_downlist_item_layout, null);
            holder.list2_bg = (ImageView) view.findViewById(R.id.list2_bg);
            holder.list2_bg.setBackgroundResource(ResourceProvider.getInstance().getD280160());
            holder.delete = (ImageView) view.findViewById(R.id.delete);
            holder.list2_focus = (ImageView) view.findViewById(R.id.list2_focus);
            holder.list2_txt = (EffectiveMarqueeTextView) view.findViewById(R.id.list2_txt);
            view.setTag(holder);
        } else {
            view = convertView;
        }
        holder = (AppRecordHolder) view.getTag();
        holder.position = position;
        AppStoreEntity entity = entitys.get(position);
        if (isDelete && !entity.getAppName().equals(ctx.getText(R.string.no_record))) {
            holder.delete.setVisibility(View.VISIBLE);
        }
        //获取应用内的icon
        Drawable appicon;
        PackageManager pm = ctx.getPackageManager();
        holder.list2_bg.setImageDrawable(null);// 先清空图片防止复用造成的图片重复By Spr_ypt
        try {
            ApplicationInfo info = pm.getApplicationInfo(entity.getPackageName(), 0);
//            appicon = pm.getDrawable(entity.getPackageName(), entity.getIconId(), info);
            appicon = info.loadIcon(pm);
        } catch (NameNotFoundException e) {
            appicon = new BitmapDrawable();
            e.printStackTrace();
        }
        //判断是否有运营图片
        if (null != entity.getIconUri() && "" != entity.getIconUri()) {
            DisplayImageUtil.getInstance().setRound((int) ctx.getResources().getDimension(R.dimen.recommendview_cover_round)).setDuration(200).setLoadingDrawable(appicon).setErrorDrawable(appicon).setEmptyUriDrawable(appicon)
                    .displayImage(entity.getIconUri(), holder.list2_bg);
        } else {
            holder.list2_bg.setImageDrawable(appicon);
        }
        holder.list2_txt.setText(TextUtils.isEmpty(entity.getAppName()) ? ctx.getText(R.string.server_no_data) : entity.getAppName());
        view.setOnFocusChangeListener(focusChange);
        view.setOnKeyListener(keyListener);
        view.setOnClickListener(onClick);
        return view;
    }

    private OnFocusChangeListener focusChange = new OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            AppRecordHolder holder = (AppRecordHolder) v.getTag();
            if (hasFocus) {
                focusChanged(holder, hasFocus);
                int[] location = new int[2];
                v.getLocationInWindow(location);
                // 屏幕最右边左移
                if (location[0] + v.getWidth() >= sizeConstant.getBoxWidth()) {
                    listView.scrollTo(location[0] + v.getWidth() - sizeConstant.getBoxWidth()
                            + (int) ctx.getResources().getDimension(R.dimen.recordDownAdapter_onFocusListener_scrollTo) + listView.getmNextX());
                }
                // 屏幕最左边右移
                else if (location[0] <= startX) {
                    listView.scrollTo(location[0] - startX
                            - (int) ctx.getResources().getDimension(R.dimen.recordDownAdapter_onFocusListener_scrollTo) + listView.getmNextX());
                }
            } else {
                focusChanged(holder, hasFocus);
            }
        }
    };

    private void focusChanged(AppRecordHolder holder, boolean hasFocus) {
        if (hasFocus) {
            holder.list2_focus.setVisibility(View.VISIBLE);
            holder.list2_txt.setTextColor(Color.WHITE);
            if (holder.list2_txt.isOverFlowed()) {
                holder.list2_txt.setGravity(Gravity.LEFT);
            } else {
                holder.list2_txt.setGravity(Gravity.CENTER);
            }
            holder.list2_txt.setIsInFocusView(true);

        } else {
            holder.list2_focus.setVisibility(View.INVISIBLE);
            holder.list2_txt.setTextColor(Color.rgb(100, 100, 100));
            holder.list2_txt.setGravity(Gravity.CENTER);
            holder.list2_txt.setIsInFocusView(false);

        }
    }

    private OnKeyListener keyListener = new OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (!listView.getmScroller().isFinished() || !historyView.getmScroller().isFinished()) {
                return true;
            }
            AppRecordHolder holder = (AppRecordHolder) v.getTag();
            if (KeyEvent.ACTION_DOWN == event.getAction()) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_UP:
                        View parentView = historyView.getChildAt(0);
                        RecordAdapter.RecordHolder holder1 = (RecordAdapter.RecordHolder) parentView.getTag();
                        if (!isHasUpView) {// 不存在上半部分

                            if (holder1.position != parentPosition) {// 只有列表在顶部和底部时会出现这种情况，大部分时候焦点就在列表第一个item
                                int[] location = new int[2];
                                parentView.getLocationInWindow(location);
                                if (holder1.position != 1) {
                                    historyView.scrollTo(location[1] + historyView.getmNextY(), 0, parentPosition);
                                } else {// 第一项向上移动时会显示出头信息
                                    historyView.scrollTo(location[1] + parentView.getHeight() - sizeConstant.getBoxHeight() + historyView.getmNextY(), 0,
                                            parentPosition);

                                }
                            } else {
                                if (parentPosition == 2) {
                                    historyView.scrollTo(-sizeConstant.getBoxHeight() + historyView.getmNextY(), 1, parentPosition);
                                } else {
                                    RecordEntity entity = (RecordEntity) historyView.getItemAtPosition(parentPosition - 1);
                                    if (null != entity.getMovies() && null != entity.getAppStores() && entity.getMovies().size() != 0
                                            && entity.getAppStores().size() != 0) {
                                        // 影片与游戏数据都存在的时候Item高度为888px
                                        historyView.scrollTo(
                                                -(int) ctx.getResources().getDimension(R.dimen.history_view_scroll_item_all) + historyView.getmNextY(),
                                                1, parentPosition);
                                    } else {
                                        // 只存在1项的时候Item高度为552px
                                        historyView.scrollTo(
                                                -(int) ctx.getResources().getDimension(R.dimen.history_view_scroll_item_one) + historyView.getmNextY(),
                                                1, parentPosition);
                                    }
                                }
                            }
                        } else {
                            if (parentPosition == 1 && holder1.position == parentPosition) {
                                historyView.scrollTo(
                                        -(int) ctx.getResources().getDimension(R.dimen.history_view_scroll_from_title) + historyView.getmNextY(), 0,
                                        parentPosition);// 顶部Item高度为192px
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_DOWN:
                        View parentView1 = historyView.getChildAt(0);
                        RecordAdapter.RecordHolder holder2 = (RecordAdapter.RecordHolder) parentView1.getTag();
                        if (holder2.position == parentPosition) {
                            // 目前焦点在下半部分点下键的时候仅存在焦点为屏幕中第一项的情况，并且有没有上半部分逻辑相同
                            historyView.scrollTo(parentView1.getHeight() + historyView.getmNextY(), 0, parentPosition);
                        } else {
                            if (parentPosition == 1) {
                                historyView.scrollTo(historyView.getChildAt(1).getHeight() + parentView1.getHeight() + historyView.getmNextY(), 0,
                                        parentPosition);
                            }
                        }
                        break;
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (holder.position == 0)
                            return true;
                        break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        if (holder.position == entitys.size() - 1)
                            return true;
                        break;
                }
            }
            return false;
        }
    };

    private OnClickListener onClick = new OnClickListener() {

        @Override
        public void onClick(View v) {
            AppRecordHolder holder = (AppRecordHolder) v.getTag();
            AppStoreEntity entity = entitys.get(holder.position);
            if (!isDelete) {
                callBack.appGame(entity);
            } else {
                entitys.remove(entity);
                recordEntity.getAppStores().remove(entity);
                notifyDataSetChanged();
                callBack.deleteApp(entity, recordEntity);
            }
        }
    };

    public class AppRecordHolder {
        ImageView list2_focus;
        ImageView list2_bg;
        ImageView delete;
        EffectiveMarqueeTextView list2_txt;
        int position;
    }

    private RecordUpAdapter.ClickCallBack callBack;
    private boolean isHasUpView = true;

    public void setHasUpView(boolean b) {
        this.isHasUpView = b;
    }
}
