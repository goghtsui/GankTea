package com.hiveview.cloudscreen.vipvideo.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.SubjectDetailActivity;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.VideoDetailInvoker;
import com.hiveview.cloudscreen.vipvideo.common.VideoListInvoker;
import com.hiveview.cloudscreen.vipvideo.service.entity.VideoChannelEntity;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.LauncherActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.DeviceInfoUtil;
import com.hiveview.cloudscreen.vipvideo.util.StringUtils;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.TypefaceUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xieyi
 * @ClassName ChannelView
 * @Description 点播首页底部频道分类视图组件
 * @date 2014-8-30 下午3:14:58
 */
public class ChannelView extends RelativeLayout {

    private static final String TAG = "ChannelView";


    public ChannelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ChannelView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChannelView(Context context) {
        super(context);
    }

    /*
     * 用来存放每排最后一个分类视图
     */
    private List<RelativeLayout> cacheView;


    public List<RelativeLayout> getCacheView() {
        return cacheView;
    }

    private List<RelativeLayout> firstRow;

    List<View> recommendList = new ArrayList<View>();

    List<VideoChannelEntity> channelData;

    /**
     * @param result 分类的数组
     * @Title setData
     * @author xieyi
     * @Description 该组件的数据填充渲染方法       组件布局为:所有子元素都为一级子元素，处于同一个页面层级，方便焦点处理，每排都是先
     * 布局一个ImageView的背景图，每排内容第一个左间距依赖第一排"频道分类"，后续的相继依赖前一个布局
     * ID号段1000起的是行背景图，2000起的是分类内容，3000起的是分割线
     */
    public void setData(List<VideoChannelEntity> result) {
        Log.d(TAG, "result : " + result.size());
        if (cacheView == null) {
            cacheView = new ArrayList<RelativeLayout>();
            firstRow = new ArrayList<RelativeLayout>();
        }
        channelData = result;
        //设置文本大小
        for (int i = 0; i < result.size(); i++) {
            float len = new TypeFaceTextView(getContext()).getPaint().measureText(result.get(i).getChannelName());
//            int len = StringUtils.parseString(result.get(i).getChannelName()).length();
            if (len > 56) {//56为4个文字的长度
                result.get(i).setSize((int) Math.ceil((double) len / 56));
            }
        }
        //计算各频道所在行列
        int sizeSum = 0;//用于计算当前行长度
        int row = 1;//行
        int col = 0;//对应行数下的位置（列）
        for (int i = 0; i < result.size(); i++) {
            col++;
            sizeSum += result.get(i).getSize();
            if (sizeSum > 8 && col != 1) {//大于8且不是第一条说明最后增加的这个应该在下一行
                row++;
                col = 1;
                sizeSum = result.get(i).getSize();
                result.get(i).setRow(row);
                result.get(i).setCol(1);
                result.get(i - 1).setLast(true);
            } else if (sizeSum == 8 || (sizeSum > 8 && col == 1)) {//等于8或者第一条就大于8说明最后增加的还是当前行，但是下一个需要换行
                result.get(i).setRow(row);
                result.get(i).setCol(col);
                result.get(i).setLast(true);
                if (i != result.size() - 1) {//存在下一个的时候才需要增加行数
                    row++;
                }
                col = 0;
                sizeSum = 0;
            } else {
                result.get(i).setRow(row);
                result.get(i).setCol(col);
            }

        }

        for (VideoChannelEntity e : result) {
        }

//        int lineSize = resultSize / 8; //数据总行数
//        int remainder = resultSize % 8; //如果不为0，则为最后一行数据个数
//        if (remainder > 0) lineSize++;
        int index = 0; //记录数据索引
        for (int i = 0; i < row; i++) { //根据数据长度循环布局每行，每行最多8个分类位置
            ImageView back = new ImageView(getContext());
            back.setId(1000 + i);
            addView(back);
            LayoutParams backParams = (LayoutParams) back.getLayoutParams();
            backParams.width = LayoutParams.MATCH_PARENT;
            backParams.height = (int) getResources().getDimension(R.dimen.channerview_backParams_height);//110

            if (i == 0) {//第一行，因为第一行多了"频道分类"和颜色条的视图，所以要单独布局
                back.setBackgroundResource(R.drawable.bg_launcher_channel);
                backParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                ImageView channelColor = new ImageView(getContext());
                channelColor.setBackgroundResource(R.drawable.channel_color);
                channelColor.setId(9999);
                addView(channelColor);
                LayoutParams colorParams = (LayoutParams) channelColor.getLayoutParams();
                colorParams.width = LayoutParams.WRAP_CONTENT;
                colorParams.height = LayoutParams.WRAP_CONTENT;
                colorParams.leftMargin = (int) getResources().getDimension(R.dimen.channerview_colorParams_leftMargin);//100
                colorParams.bottomMargin = (int) getResources().getDimension(R.dimen.channerview_colorParams_bottomMargin);//30
                colorParams.addRule(RelativeLayout.ALIGN_LEFT, back.getId());
                colorParams.addRule(RelativeLayout.ALIGN_BOTTOM, back.getId());

                TypeFaceTextView title = new TypeFaceTextView(getContext());
                title.setId(9998);
                title.setTextSize(getResources().getDimension(R.dimen.channerview_title_setTextSize));//32
                title.setTextColor(Color.WHITE);
                title.setText(R.string.cloud_single_bottomtxt);
                if (StringUtils.isZh(getContext())) {
                    title.setTypeface(TypefaceUtil.TypefaceFile.SIMHEI);
                } else {
                    title.getPaint().setFakeBoldText(true);
                }
                title.getPaint().setFakeBoldText(true);
                addView(title);
                LayoutParams titleParams = (LayoutParams) title.getLayoutParams();
                titleParams.width = LayoutParams.WRAP_CONTENT;
                titleParams.height = LayoutParams.WRAP_CONTENT;
                titleParams.leftMargin = (int) getResources().getDimension(R.dimen.channerview_titleParams_leftMargin);//20
                titleParams.addRule(RelativeLayout.RIGHT_OF, 9999);
                titleParams.addRule(RelativeLayout.ALIGN_BOTTOM, 9999);
            } else {
                back.setBackgroundResource(R.drawable.bg_launcher_channel);
                backParams.addRule(RelativeLayout.BELOW, back.getId() - 1);
                backParams.topMargin = (int) getResources().getDimension(R.dimen.channerview_backParams_topMargin);// 34
            }
            //设置单个频道布局数据
            for (VideoChannelEntity e : result) {
                if (e.getRow() - 1 == i) {
                    fillContent(e.getRow() - 1, e.getCol() - 1, e.isLast(), index, back, e);
                    index++;
                }
            }

        }

        for (int i = 0; i < cacheView.size(); i++) {
            if (i == cacheView.size() - 1) { //最后一排最后一个就设置向右焦点不动，否则焦点到下一排第一个
                cacheView.get(i).setOnKeyListener(keyListener);
            } else
                cacheView.get(i).setNextFocusRightId(cacheView.get(i).getId() + 1);
        }
    }

    //start by liulifeng 2015-1-27 16:50:06
    private OnFocusChangeListener channelFocusListener = new OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                for (View rv : recommendList) {
                    rv.setNextFocusDownId(v.getId());
                }
            }

        }
    };


    public void setRecommendView(List<View> recommendView) {
        recommendList = recommendView;
    }
    //end liulifeng

    /**
     * @param row      行数
     * @param index    该分类位置在行里的索引，每行的索引都是从0开始，最高到7
     * @param isLast   是否该行结尾
     * @param position 该位置数据在总数据集里的索引
     * @param view     该分类位置所在行的背景视图
     * @param entity   该分类位置的内容
     * @Title fillContent
     * @author xieyi
     * @Description 频道分类位置内容填充
     */

    private void fillContent(int row, int index, boolean isLast, int position, ImageView view, VideoChannelEntity entity) {
        //设置焦点布局
        RelativeLayout focus = new RelativeLayout(getContext());
        focus.setId(2000 + position);
        focus.setFocusable(true);
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            focus.setBackgroundResource(R.drawable.focused_view_vip_selector);
        } else {
            focus.setBackgroundResource(R.drawable.focused_view_selector);
        }
        focus.setOnClickListener(clickListener);
        focus.setOnFocusChangeListener(channelFocusListener); //记忆频道分类焦点监听器 by liulifeng 2015-1-27 16:49:18
        addView(focus);
        cacheView.add(focus);
        LayoutParams focusParams = (LayoutParams) focus.getLayoutParams();
//        focusParams.width = (int) getResources().getDimension(R.dimen.channerview_focusParams_width);
//        focusParams.height = (int) getResources().getDimension(R.dimen.channerview_focusParams_height);
        focusParams.width = LayoutParams.WRAP_CONTENT;
        focusParams.height = LayoutParams.WRAP_CONTENT;
        TypeFaceTextView content = new TypeFaceTextView(getContext());
        content.setTag(entity);
        //默认 26px . 中文版为17sp  海外版为12sp  已通过dimen.xml 适配  start by liulifeng
        content.setTextSize(getResources().getDimension(R.dimen.channerview_content_setTextSize));
        //end by liulifeng
        content.setTextColor(Color.WHITE);
        content.setGravity(Gravity.CENTER);
        content.setFocusable(false);
        content.setText(StringUtils.parseString(entity.getChannelName()));

        content.setWidth((int) getResources().getDimension(R.dimen.channerview_contentParams_width) + (entity.getSize() - 1) * (int) getResources().getDimension(R.dimen.channerview_contentParams_double_width));
        content.setHeight((int) getResources().getDimension(R.dimen.channerview_contentParams_height));
        focus.addView(content);
        if (row == 0) {
            firstRow.add(focus);
            focus.setOnKeyListener(keyListener);
        }
        Log.d(TAG, "index : " + index);
        if (index == 0) {//该行第一个分类位置.
            focusParams.topMargin = -(int) getResources().getDimension(R.dimen.channerview_focusParams_topMargin);//-22
            focusParams.addRule(RelativeLayout.RIGHT_OF, 9998);
            focusParams.addRule(RelativeLayout.ALIGN_TOP, view.getId());
            if (position == 0)
                first = focus;
            else
                focus.setNextFocusLeftId(focus.getId() - 1); //不是第一排第一个就设置向左焦点到上一排最后一个
        } else {
            focusParams.leftMargin = -(int) getResources().getDimension(R.dimen.channerview_focusParams_leftMargin);//-20
            if (row == 0 && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                focusParams.topMargin = -(int) getResources().getDimension(R.dimen.channerview_focusParams_topMargin);//-22

            focusParams.addRule(RelativeLayout.RIGHT_OF, focus.getId() - 2000 - 1 + 3000);
            focusParams.addRule(RelativeLayout.ALIGN_TOP, focus.getId() - 1);
        }
        /*
         * 分割线
		 */
        if (!isLast) {//每排最后一个位置不画分割线
            ImageView halvingLine = new ImageView(getContext());
            halvingLine.setId(3000 + position);
            halvingLine.setBackgroundResource(R.drawable.halving_line);
            addView(halvingLine);
            LayoutParams lineParams = (LayoutParams) halvingLine.getLayoutParams();
            lineParams.width = LayoutParams.WRAP_CONTENT;
            lineParams.height = LayoutParams.WRAP_CONTENT;
            lineParams.leftMargin = -(int) getResources().getDimension(R.dimen.channerview_lineParams_leftMargin);//-20
            lineParams.bottomMargin = (int) getResources().getDimension(R.dimen.channerview_lineParams_bottomMargin);//17
            lineParams.addRule(RelativeLayout.RIGHT_OF, focus.getId());
            lineParams.addRule(RelativeLayout.ALIGN_BOTTOM, view.getId());
        }
    }

    /*
     * 设置第一行向上焦点
     */
    public void setFirstRowUpId(int id) {
        for (int i = 0; i < firstRow.size(); i++) {
            firstRow.get(i).setNextFocusUpId(id);
        }
    }

    /*
     * 第一个分类视图
     */
    private RelativeLayout first;

    public RelativeLayout getFirstTextView() {
        return first;
    }

    private UpFocusCallBack callBack;//焦点回到推荐位视图时的接口回调

    public void setUpFocusCallBack(UpFocusCallBack callBack) {
        this.callBack = callBack;
    }

    public interface UpFocusCallBack {
        void upFocused();
    }

    private OnKeyListener keyListener = new OnKeyListener() {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (KeyEvent.ACTION_DOWN == event.getAction()) {
                switch (event.getKeyCode()) {
                    case KeyEvent.KEYCODE_DPAD_LEFT:
                        if (v.getId() == first.getId())
                            return true;
                        else
                            break;
                    case KeyEvent.KEYCODE_DPAD_RIGHT:
                        if (v.getId() == cacheView.get(cacheView.size() - 1).getId())
                            return true;
                        else
                            break;
                    case KeyEvent.KEYCODE_DPAD_UP:
                        RelativeLayout parentView = (RelativeLayout) v;
                        View childView = parentView.getChildAt(0);
                        VideoChannelEntity entity = (VideoChannelEntity) childView.getTag();
                        if (entity.getRow() == 1) {
                            callBack.upFocused();
                        }
                        break;
                }
            }
            return false;
        }
    };

    OnClickListener clickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {


            RelativeLayout parentView = (RelativeLayout) v;
            View childView = parentView.getChildAt(0);
            VideoChannelEntity entity = (VideoChannelEntity) childView.getTag();

            switch (entity.getChannelType()) {
                case 0:
                    //单个专题
                    Intent intent0 = new Intent(getContext(), SubjectDetailActivity.class);
                    intent0.putExtra(AppConstants.EXTRA_SUBJECT_ID, entity.getId());
                    getContext().startActivity(intent0);
                    break;
                case 1:
                    //打开id对应的专辑,暂未考虑单片单点
                    String action1 = VideoDetailInvoker.getInstance().getDetailActivityAction(1);//这里应该传对应的cid
                    //BlurBackground(); TODO 需要调用主页的模糊背景方法
                    Intent intentDetail = new Intent(action1);
                    intentDetail.putExtra(AppConstants.EXTRA_VIDEOSET_ID, entity.getId());
                    intentDetail.putExtra(AppConstants.EXTRA_CID, 1);//这里应该传对应的cid
                    intentDetail.putExtra(AppConstants.EXTRA_SOURCE, "1");//TODO 需要确认这里的值
                    getContext().startActivity(intentDetail);
                    break;
                case 3:
                    //热词
                    String action3 = VideoDetailInvoker.ACTION_HOT_WORD;
                    Intent intent3 = new Intent(action3);
                    intent3.putExtra(AppConstants.LIST_CHANNEL_ID, entity.getParentCid());
                    intent3.putExtra(AppConstants.LIST_CHANNEL_TYPE, entity.getParentCtype());
                    intent3.putExtra(AppConstants.LIST_TITLE, getChannelName(entity.getParentCid()));
                    intent3.putExtra(AppConstants.LIST_HOTWORD_ID, entity.getId());
                    getContext().startActivity(intent3);
                    break;
                case 8:
                    //TODO 标签（需要筛选功能上线）
                    ToastUtil.showToast(getContext(), "筛选功能还未上线，敬请期待！", Toast.LENGTH_SHORT);
                    break;
                case 9:
                    //个性化频道
                    if (entity.getIsSpecific() == 2) {//商品类
                        String action7 = VideoDetailInvoker.ACTION_GOODS_LIST;
                        Intent intent7 = new Intent(action7);
                        intent7.putExtra("userId", UserStateUtil.getInstance().getUserInfo().userId);
                        intent7.putExtra("channelId", entity.getId() + "");//频道id
//                        intent7.putExtra("channelType", entity.getChannelType());//频道类型
                        intent7.putExtra("packageName", "com.hiveview.cloudscreen.vipvideo");//包名
                        intent7.putExtra("templateId", DeviceInfoUtil.getInstance().getDeviceInfo(getContext()).templetId + "");//模板id
                        getContext().startActivity(intent7);
                    } else if (entity.getIsSpecific() == 4 || entity.getIsSpecific() == 5) {
                        //TODO 直轮播（后期需求此处预留）
                        ToastUtil.showToast(getContext(), "直轮播跳转还未上线，敬请期待！", Toast.LENGTH_SHORT);
                    } else {
                        int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getId());
                        String action9 = VideoListInvoker.getInstance().getListActivityAction(showType);
                        Intent intent9 = new Intent(action9);
                        intent9.putExtra(AppConstants.LIST_CHANNEL_ID, entity.getId());
                        intent9.putExtra(AppConstants.LIST_CHANNEL_TYPE, entity.getChannelType());
                        intent9.putExtra(AppConstants.LIST_TITLE, entity.getChannelName());
                        getContext().startActivity(intent9);
                    }
                    break;
                case 10:
                    //基础频道
                    int showType = CloudScreenApplication.getInstance().getShowTypeByVideoTypeId(entity.getId());
                    String action10 = VideoListInvoker.getInstance().getListActivityAction(showType);
                    Intent intent10 = new Intent(action10);
                    intent10.putExtra(AppConstants.LIST_CHANNEL_ID, entity.getId());
                    intent10.putExtra(AppConstants.LIST_CHANNEL_TYPE, entity.getChannelType());
                    intent10.putExtra(AppConstants.LIST_TITLE, entity.getChannelName());
                    getContext().startActivity(intent10);
                    break;
            }

            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
                BaseActionInfo info = new LauncherActionInfo("03", "sde0301", "2", "1", null, entity.getId() + "", null);//统计埋点
                new Statistics(getContext(), info).send();//统计埋点
            } else {
                BaseActionInfo info = new LauncherActionInfo("03", "sde0301", "2", "2", null, entity.getId() + "", null);//统计埋点
                new Statistics(getContext(), info).send();//统计埋点
            }
        }
    };

    /**
     * @Author Spr_ypt
     * @Date 2016/12/15
     * @Description 根据cid获取频道名称
     */
    private String getChannelName(Integer cid) {
        String parentName = "";
        for (VideoChannelEntity e : channelData
                ) {
            if (e.getId() == cid) {
                parentName = e.getChannelName();
            }
        }
        return parentName;
    }

    public void setChannelViewBg(int res) {
        if (null != cacheView && cacheView.size() > 0) {
            for (RelativeLayout r : cacheView) {
                r.setBackgroundResource(res);
            }
        }
    }
}
