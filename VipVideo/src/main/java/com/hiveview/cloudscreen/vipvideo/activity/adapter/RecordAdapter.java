package com.hiveview.cloudscreen.vipvideo.activity.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.SizeConstant;
import com.hiveview.cloudscreen.vipvideo.service.entity.AppStoreEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.RecordEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoRecordEntity;
import com.hiveview.cloudscreen.vipvideo.util.AppStoreUtils;
import com.hiveview.cloudscreen.vipvideo.util.QIYIUtils;
import com.hiveview.cloudscreen.vipvideo.view.HistoryView;
import com.hiveview.cloudscreen.vipvideo.view.HorizontalListView;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater inflater;
    private HistoryView historyView;
    private List<RecordEntity> records;
    private boolean isDelete;
    // # start by Spr_ypt 单双行高度计算用值
    private float density;// 屏幕像素数
    private final int ONE_LINE_HEIGH;// 记录单行高度（dip），需乘以密度，获取像素数
    private final int TWO_LINE_HEIGH;// 记录双行高度

    private SizeConstant sizeConstant;
    // # end by Spr_ypt

    public RecordAdapter(Context context, HistoryView hisView, ClickToActivity backToActivity, SizeConstant sizeConstant) {
        this.ctx = context;
        this.historyView = hisView;
        this.backToActivity = backToActivity;
        this.sizeConstant = sizeConstant;
        inflater = LayoutInflater.from(ctx);
        density = getDensity(context);// add by Spr_ypt
//        ONE_LINE_HEIGH = 368;
        ONE_LINE_HEIGH = (int) context.getResources().getDimension(R.dimen.recordAdapter_one_line_heigh);
        Log.i("RecordAdapter", "单行的高度：" + ONE_LINE_HEIGH);
//        TWO_LINE_HEIGH = 593;
        TWO_LINE_HEIGH = (int) context.getResources().getDimension(R.dimen.recordAdapter_two_line_heigh);
        Log.i("RecordAdapter", "双行的高度：" + TWO_LINE_HEIGH);
    }

    /**
     * @param context 必须是activity
     * @return
     * @Title: RecordAdapter
     * @author:yupengtong
     * @Description: 获取屏幕密度
     */
    private float getDensity(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }

    public void setData(List<RecordEntity> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @SuppressLint("NewApi")
    public void setMode(boolean isDelete) {
        this.isDelete = isDelete;
        notifyDataSetInvalidated();
    }

    public boolean getMode() {
        return isDelete;
    }

    @Override
    public int getCount() {
        return records.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return records.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // init
        View view = null;
        RecordHolder holder = null;
        if (convertView == null) {
            holder = new RecordHolder();
            if (position == 0) {
                view = inflater.inflate(R.layout.record_header_layout, null);
                holder.title_text2 = (TypeFaceTextView) view.findViewById(R.id.title_text2);
                holder.position = position;
//                holder.placeFlag = 0;
                view.setTag(holder);
            } else {
                view = inflater.inflate(R.layout.record_item_layout, null);
                holder.itemLayout = (RelativeLayout) view.findViewById(R.id.rl_record_item);
                holder.year = (TypeFaceTextView) view.findViewById(R.id.history_year);
                holder.month = (TypeFaceTextView) view.findViewById(R.id.history_month);
                holder.colorLine1 = (ImageView) view.findViewById(R.id.line_color1);
                holder.movieTextView = (TypeFaceTextView) view.findViewById(R.id.movie);
                holder.history_list1 = (HorizontalListView) view.findViewById(R.id.history_list1);
                holder.colorLine2 = (ImageView) view.findViewById(R.id.line_color2);
                holder.appTextView = (TypeFaceTextView) view.findViewById(R.id.game);
                holder.history_list2 = (HorizontalListView) view.findViewById(R.id.history_list2);
//                holder.placeFlag = 1;
                view.setTag(holder);
            }
        } else {
            view = convertView;
            holder = (RecordHolder) view.getTag();
            if (position != 0) {
                view = inflater.inflate(R.layout.record_item_layout, null);
                holder.itemLayout = (RelativeLayout) view.findViewById(R.id.rl_record_item);
                holder.year = (TypeFaceTextView) view.findViewById(R.id.history_year);
                holder.month = (TypeFaceTextView) view.findViewById(R.id.history_month);
                holder.colorLine1 = (ImageView) view.findViewById(R.id.line_color1);
                holder.movieTextView = (TypeFaceTextView) view.findViewById(R.id.movie);
                holder.history_list1 = (HorizontalListView) view.findViewById(R.id.history_list1);
                holder.colorLine2 = (ImageView) view.findViewById(R.id.line_color2);
                holder.appTextView = (TypeFaceTextView) view.findViewById(R.id.game);
                holder.history_list2 = (HorizontalListView) view.findViewById(R.id.history_list2);
//                holder.placeFlag = 1;
                view.setTag(holder);
            } else if (position == 0) {
                view = inflater.inflate(R.layout.record_header_layout, null);
                holder.title_text2 = (TypeFaceTextView) view.findViewById(R.id.title_text2);
//                holder.placeFlag = 0;
                holder.position = position;
                view.setTag(holder);
            }
        }
        holder = (RecordHolder) view.getTag();
        // 加载数据
        if (position != 0) {
            RecordEntity entity = records.get(position - 1);
            holder.position = position;
            holder.year.setText(entity.getYear());
            holder.month.setText(entity.getMonthAndDay());
            RecordUpAdapter upAdapter;
            RecordDownAdapter downAdapter;
            boolean isHasUpview = true;// 是否存在电影数据
            // 加载电影部分
            if (entity.getMovies() != null && entity.getMovies().size() > 0) {// 有电影数据
                isHasUpview = true;
                upAdapter = new RecordUpAdapter(ctx, historyView, holder.history_list1, position, isDelete, callBack, entity, sizeConstant);
                upAdapter.setData(entity.getMovies());
                holder.colorLine1.setVisibility(View.VISIBLE);
                holder.movieTextView.setVisibility(View.VISIBLE);
                holder.history_list1.setVisibility(View.VISIBLE);
            } else {// 无电影数据
                holder.colorLine1.setVisibility(View.GONE);
                holder.movieTextView.setVisibility(View.GONE);
                holder.history_list1.setVisibility(View.GONE);
                isHasUpview = false;
                LayoutParams layoutParameters = holder.itemLayout.getLayoutParams();
                if (layoutParameters.height != (int) ONE_LINE_HEIGH * density) {
                    layoutParameters.height = (int) (ONE_LINE_HEIGH * density);
                    holder.itemLayout.setLayoutParams(layoutParameters);
                }
                List<VideoRecordEntity> videoEntitys = new ArrayList<VideoRecordEntity>();
                VideoRecordEntity videoEntity = new VideoRecordEntity();
                videoEntity.setSource(2);
                videoEntitys.add(videoEntity);
                upAdapter = new RecordUpAdapter(ctx, historyView, holder.history_list1, position, isDelete, callBack, entity, sizeConstant);
                upAdapter.setData(videoEntitys);
            }
            // 加载应用部分
            if (entity.getAppStores() != null && entity.getAppStores().size() > 0) {// 存在app数据
                downAdapter = new RecordDownAdapter(ctx, historyView, holder.history_list2, position, isDelete, callBack, entity, sizeConstant);
                downAdapter.setData(entity.getAppStores());
                holder.colorLine2.setVisibility(View.VISIBLE);
                holder.appTextView.setVisibility(View.VISIBLE);
                holder.history_list2.setVisibility(View.VISIBLE);
                if (isHasUpview) {
                    LayoutParams layoutParameters = holder.itemLayout.getLayoutParams();
                    if (layoutParameters.height != (int) (TWO_LINE_HEIGH * density)) {
                        layoutParameters.height = (int) (TWO_LINE_HEIGH * density);
                        holder.itemLayout.setLayoutParams(layoutParameters);
                    }
                }
                holder.history_list1.setAdapter(upAdapter);
                downAdapter.setHasUpView(isHasUpview);
                holder.history_list2.setAdapter(downAdapter);
            } else {// 无app数据
                if (!isHasUpview) {
                    // add by Spr_ypt
                    // 用于处理通过删除后无电影也无app的情况，但是由于数据刷新延后弃用
                    // replace by callBack.removeEmptyRecord
                    records.remove(entity);
                    return null;
                } else {

                    holder.colorLine2.setVisibility(View.GONE);
                    holder.appTextView.setVisibility(View.GONE);
                    holder.history_list2.setVisibility(View.GONE);
                    upAdapter.setHasDownView(false);
                    LayoutParams layoutParameters = holder.itemLayout.getLayoutParams();
                    if (layoutParameters.height != (int) (ONE_LINE_HEIGH * density)) {
                        layoutParameters.height = (int) (ONE_LINE_HEIGH * density);
                        holder.itemLayout.setLayoutParams(layoutParameters);
                    }
                    List<AppStoreEntity> appEntitys = new ArrayList<AppStoreEntity>();
                    AppStoreEntity appEntity = new AppStoreEntity();
                    appEntity.setAppName(ctx.getString(R.string.no_record));
                    appEntitys.add(appEntity);
                    downAdapter = new RecordDownAdapter(ctx, historyView, holder.history_list2, position, isDelete, callBack, entity, sizeConstant);
                    downAdapter.setData(appEntitys);
                    holder.history_list1.setAdapter(upAdapter);
                    downAdapter.setHasUpView(isHasUpview);
                    holder.history_list2.setAdapter(downAdapter);
                }
            }

        } else {
            holder.title_text2.setText(String.format(ctx.getString(R.string.total_day_record), "" + records.size()));
        }
        return view;
    }

    public class RecordHolder {
        RelativeLayout itemLayout;
        TypeFaceTextView title_text2;
        TypeFaceTextView year;
        TypeFaceTextView month;
        ImageView colorLine1;
        TypeFaceTextView movieTextView;
        HorizontalListView history_list1;
        ImageView colorLine2;
        TypeFaceTextView appTextView;
        HorizontalListView history_list2;
        public int position;
//        int placeFlag;
    }

    private RecordUpAdapter.ClickCallBack callBack = new RecordUpAdapter.ClickCallBack() {

        @Override
        public void qiyi(VideoRecordEntity entity) {
            backToActivity.qiyi(entity);
        }


        @Override
        public void appGame(AppStoreEntity entity) {
            backToActivity.appGame(entity);
        }


        @Override
        public void deleteQiyi(VideoRecordEntity entity, RecordEntity recordEntity) {
            QIYIUtils.delete(ctx, entity);
            removeEmptyRecord(recordEntity);
            notifyDataSetChanged();
            backToActivity.delete();
            // TODO remove已经在对应adapter内处理过了，现在需要删除数据库内数据

        }

        @Override
        public void deleteApp(AppStoreEntity entity, RecordEntity recordEntity) {
            AppStoreUtils.delete(ctx, entity);
            removeEmptyRecord(recordEntity);
            notifyDataSetChanged();
            backToActivity.delete();
            // TODO remove已经在对应adapter内处理过了，现在需要删除数据库内数据

        }

        /**
         * @Title:
         * @author:yupengtong
         * @Description: 判断recordEntity是否已经没有内容了，如果没有了就将其移除
         * @param recordEntity
         */
        private void removeEmptyRecord(RecordEntity recordEntity) {
            if ((null == recordEntity.getAppStores() || recordEntity.getAppStores().size() == 0)
                    && (null == recordEntity.getMovies() || recordEntity.getMovies().size() == 0)) {
                records.remove(recordEntity);
            }
        }
    };

    public interface CollectCallBack {
        void animation(int div);

        void delete(Object obj, int div);

        boolean deleteAll(boolean isDeleteAll);// 由于单条删除完全部后也会调用该程序判断是否全删除了，所以这里需要一个标记位来表示是否真的通过“全部删除”功能进行全部删除的。
    }

    public interface ClickToActivity {
        void qiyi(VideoRecordEntity entity);

        void appGame(AppStoreEntity entity);

        void delete();

    }

    private ClickToActivity backToActivity;
}
