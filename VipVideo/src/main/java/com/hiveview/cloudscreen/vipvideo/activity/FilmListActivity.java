package com.hiveview.cloudscreen.vipvideo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.BaseFilmListAdapter;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.FilmHotwordListAdapter;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.FilmListAdapter;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.FilmListFromHotAdapter;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.AlbumEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.HotWordEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.HotWordsContentEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.ListBrowseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.ListViewActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.BitmapBlurUtil;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.FeedbackTranslationAnimatorUtil;
import com.hiveview.cloudscreen.vipvideo.util.Logger;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;
import com.hiveview.cloudscreen.vipvideo.view.HotWordListView;
import com.hiveview.cloudscreen.vipvideo.view.LoadingView;
import com.hiveview.cloudscreen.vipvideo.view.pageItemView.FilmPageItemView;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalPagerAdapter;
import com.hiveview.cloudscreen.vipvideo.view.verticalViewPager.VerticalViewPager;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Title CloudScreenVIPVideo
 * @Auther Spr_ypt
 * @Date 2015/11/9
 * @TODO
 */
public class FilmListActivity extends BaseActivity implements VerticalViewPager.OnPageChangeListener, FilmPageItemView.OnItemKeyListener, FilmPageItemView.OnItemSelectedListener, FilmListAdapter.OnActionListener, UserStateUtil.UserStatusWatcher {
    private static final String TAG = FilmListActivity.class.getSimpleName();
    /**
     * 最外层布局
     */
    private RelativeLayout container;
    /**
     * 热词列表ListView
     */
    private HotWordListView hotwordListView;
    /**
     * 热词列表ListView适配器
     */
    private FilmHotwordListAdapter hotwordAdapter;
    /**
     * 搜索按钮布局
     */
    private LinearLayout searchLayout;
    /**
     * viewPager
     */
    private VerticalViewPager verticalViewPager;
    /**
     * viewPager适配器
     */
    private FilmListAdapter filmListAdapter;
    /**
     * 对应热词结果的viewPager适配器
     */
    private FilmListFromHotAdapter filmListFromHotAdapter;
    /**
     * 用于记录当前的viewPager适配器;
     */
    private BaseFilmListAdapter currentFilmAdapter;
    /**
     * 参数，意图传入，cid
     */
    private int channelId;
    /**
     * 参数，意图传入，ctype
     */
    private int channelType;
    /**
     * 参数，意图传入，频道标题
     */
    private String title;
    /**
     * 每次请求的数据条数
     */
    private static final int PAGE_SIZE = 120;
    /**
     * 当前加载的页数
     */
    private int currentPage = 1;
    /**
     * 是否加载中
     */
    private boolean isLoading = false;
    /**
     * By Spr_ypt 加载框
     */
    private LoadingView loadingView;
    /**
     * By Spr_ypt 蒙版
     */
    private View filterMask;
    /**
     * 加载页面延时出现handler
     */
    private Handler filmListHandler = new FilmListHandler(this);
    /**
     * 热词列表选中监听，替代焦点监听
     */
    final HotwordOnItemSelectedListener hotwordOnItemSelectedListener = new HotwordOnItemSelectedListener(this);
    /**
     * 热词id,意图传入
     */
    private int hotWordId;
    /**
     * 热词对应的影片集类型
     */
    private Integer hotWordHsTypeId;
    /**
     * 头布局
     */
    private FrameLayout headerContainer;
    /**
     * 头View
     */
    private View headerView;
    /**
     * 头标题
     */
    private TextView titleView;
    /**
     * 分类文本
     */
    private TextView categoryView;
    /**
     * 分类文本布局
     */
    private FrameLayout categoryLayout;// By Spr_ypt
    /**
     * 数量文本
     */
    private TextView countView;
    /**
     * 数量文本布局
     */
    private FrameLayout countLayout;// By Spr_ypt
    /**
     * 当前页数文本
     */
    private TextView pageCountView;
    /**
     * 用于记录总页数
     */
    private int pageCount;
    /**
     * 分类筛选字段
     */
    private String selectIds;
    /**
     * 触底反弹动画
     */
    private AnimatorSet feedbackAnimator;

    /**
     * 统计埋点
     * 页面跳出标识页面跳出标识
     * 1：直接跳出 0：做了其他操作
     */
    private String noAction = "1";
    /**
     * 记录热词被选中的次数，首次进入默认选中时算作noAction
     */
    private int hotWordTimes = 0;
    /**
     * 统计埋点
     * 打开页面的时间，用于结束的时候计算持续时间
     */
    private long openTime;
    /**
     * 用于记录热词的selector ID
     */
    private int filmHotwordSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_list);
        //加载布局
        init();
        //创建菜单
        createMenu();
        //处理vip变色
        dealVipColor();
        //处理参数
        dealExtra();
        //初始化触底反弹动画器
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            feedbackAnimator = FeedbackTranslationAnimatorUtil.getInstance().getAnimationSet(verticalViewPager, FeedbackTranslationAnimatorUtil.Orientation.VERTICAL, -50f);
        }
        //设置不依赖数据的监听
        setPreListeners();
        //请求热词列表
        CloudScreenService.getInstance().getHotWordsList(new HotWordListListener(this), channelId);
        //请求电影列表，设置热词的时候会默认情况，为了避免重复请求，此处弃用
//        CloudScreenService.getInstance().getFilm(new GetFilmListener(this), firstClass, PAGE_SIZE, 1);
        //开始加载动画
        startLoading();
    }

    private void setPreListeners() {
        //搜索按键监听
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearch = new Intent(Invoker.ACTION_START_GLOBAL_SEARCH);
                intentSearch.putExtra("resultShowType", "vertical");// 默认 影视（竖图）优先
                intentSearch.addCategory(Intent.CATEGORY_DEFAULT);
                intentSearch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentSearch);
            }
        });
        searchLayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (null != hotwordAdapter && hotwordAdapter.getCount() > 0) {
                    return false;
                } else {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {//当没有数据的时候不响应搜索按钮的右键，防止焦点丢失
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        });
        //ViewPager翻页监听
        verticalViewPager.setOnPageChangeListener(this);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/26
     * @Description 添加需要的menu选项
     */
    private void createMenu() {
        List<CloundMenuWindow.MenuItemEntity> menuItems = new ArrayList<CloundMenuWindow.MenuItemEntity>();
        CloundMenuWindow.MenuItemEntity menuCategoryFilter = null;

        menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.my_collect));
        menuCategoryFilter.setItemPosition(101);
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_collect_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_collect_focus);
        menuItems.add(menuCategoryFilter);

        menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.history_record));
        menuCategoryFilter.setItemPosition(102);
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_history_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_history_focus);
        menuItems.add(menuCategoryFilter);

        mWindow = new CloundMenuWindow(this, menuItems);
        mWindow.setItemSelectedListener(new CloundMenuWindow.OnSelectedItemClickListener() {

            @Override
            public void selectedItemClick(CloundMenuWindow.MenuItemEntity entity) {
                if (entity.getItemPosition() == 101) {// 我的收藏
                    startActivity(new Intent(Invoker.ACTION_START_COLLECT));
                } else if (entity.getItemPosition() == 102) {// 历史记录
                    startActivity(new Intent(Invoker.ACTION_START_RECORD));
                }
            }
        });
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/20
     * @Description 处理Vip颜色
     */
    private void dealVipColor() {
        switch (UserStateUtil.getInstance().getUserStatus()) {
            case VIPUSER:
                filmHotwordSelector = R.drawable.filmlist_hotword_vip_selector;
                searchLayout.setBackgroundResource(R.drawable.filmlist_hotword_vip_selector);
                if (null != currentFilmAdapter && null != currentFilmAdapter.getPrimaryItem()) {
                    ((FilmPageItemView) currentFilmAdapter.getPrimaryItem()).resetUserColor();
                }
                break;
            default:
                filmHotwordSelector = R.drawable.filmlist_hotword_selector;
                searchLayout.setBackgroundResource(R.drawable.filmlist_hotword_selector);
                if (null != currentFilmAdapter && null != currentFilmAdapter.getPrimaryItem()) {
                    ((FilmPageItemView) currentFilmAdapter.getPrimaryItem()).resetUserColor();
                }
                break;
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/20
     * @Description 处理传入的参数
     */
    private void dealExtra() {
        //频道ID
        int extraChannelId = getIntent().getIntExtra(AppConstants.LIST_CHANNEL_ID, -1);
        int extraChannelType = getIntent().getIntExtra(AppConstants.LIST_CHANNEL_TYPE, -1);
        //频道名
        title = getIntent().getStringExtra(AppConstants.LIST_TITLE);
        if (null == title) {
            title = "";
        }
        if (extraChannelId == -1 || extraChannelType == -1) {
            finish();
        } else {
            channelId = extraChannelId;
            channelType = extraChannelType;
        }
        titleView.setText(title);
        //热词
        hotWordId = getIntent().getIntExtra(AppConstants.LIST_HOTWORD_ID, -1);

    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/11
     * @Description 加载热词部分数据事件
     */
    private void initHotword(ResultEntity result) {

        hotwordAdapter = new FilmHotwordListAdapter(this);
        hotwordAdapter.setData(result.getList());
        hotwordListView.setItemsCanFocus(true);
        hotwordOnItemSelectedListener.focusPosition = hotwordAdapter.getPositionFromHotWordId(hotWordId);
        hotwordListView.setAdapter(hotwordAdapter);
        hotwordListView.setOnItemSelectedListener(hotwordOnItemSelectedListener);
        hotwordListView.post(new Runnable() {
            @Override
            public void run() {
                if (null != hotwordListView) {
                    hotwordListView.requestFocus();
                }
            }
        });
        //用于处理热词列表从那一条离开，焦点返回时回到那一条
        hotwordListView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hotwordListView.resetLoseFocusFromTop();
                    hotwordListView.post(new Runnable() {
                        @Override
                        public void run() {
                            hotwordListView.setSelector(filmHotwordSelector);
                            hotwordListView.setSelection(hotwordOnItemSelectedListener.focusPosition);
                            if (null != hotwordOnItemSelectedListener.lastSelectedView) {
                                hotwordOnItemSelectedListener.lastSelectedView.setBackground(null);
                            }
                        }
                    });
                } else {
                    hotwordListView.setSelector(R.drawable.none_selector);
                    if (!hotwordListView.isLoseFocusFromTop()) {
                        filmListHandler.removeMessages(FilmListHandler.HOTWORD_SELECTED);
                        if (null != hotwordOnItemSelectedListener.lastSelectedView) {
                            hotwordOnItemSelectedListener.lastSelectedView.setBackgroundResource(R.drawable.filmlist_hotword_selected);
                        }
                    } else {
                        if (null != hotwordOnItemSelectedListener.selectedView) {
                            hotwordOnItemSelectedListener.selectedView.setBackgroundResource(R.drawable.filmlist_hotword_selected);
                        }
                    }
                }
            }
        });
        hotwordListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
                        if (null != currentFilmAdapter && null != currentFilmAdapter.getFirstItem()) {
                            //这里是为了实现当焦点从热词列表移动到影片列表时第一个影片获取焦点的变态焦点需求
                            return currentFilmAdapter.getFirstItem().requestFocus();
                        } else {
                            return true;
                        }
                    }
                }
                return false;
            }
        });
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/10
     * @Description 加载布局
     */
    private void init() {
        container = (RelativeLayout) findViewById(R.id.container);
        hotwordListView = (HotWordListView) findViewById(R.id.lv_filmlist_hotwords);
        searchLayout = (LinearLayout) findViewById(R.id.ll_filmList_search);
        verticalViewPager = (VerticalViewPager) findViewById(R.id.grid_container);
        loadingView = (LoadingView) findViewById(R.id.lv_onload);
        filterMask = findViewById(R.id.view_filter_mask);
        headerContainer = (FrameLayout) findViewById(R.id.fl_header);
        //加载头布局
        headerView = LayoutInflater.from(this).inflate(R.layout.view_film_grid_header, null);
        titleView = (TextView) headerView.findViewById(R.id.tv_title);
        categoryView = (TextView) headerView.findViewById(R.id.tv_category);
        categoryLayout = (FrameLayout) headerView.findViewById(R.id.fl_category);
        countView = (TextView) headerView.findViewById(R.id.tv_film_count);
        countLayout = (FrameLayout) headerView.findViewById(R.id.fl_film_count);
        pageCountView = (TextView) headerView.findViewById(R.id.tv_page);
        titleView.setText(title);
        headerContainer.addView(headerView);

    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/10
     * @Description FilmPageItemView.OnItemKeyListener接口回调
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        FilmPageItemView currentView = (FilmPageItemView) currentFilmAdapter.getPrimaryItem();
        int index = 0;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_UP:
                    if (null != currentView) {
                        index = currentView.getViewIndex(v);
                        if (verticalViewPager.getCurrentItem() == 0) {
                            if (index <= 4) {
                                return true;
                            }
                        }
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (null != currentView) {
                        index = currentView.getViewIndex(v);
                        if (index == 0 || index == 5) {
                            hotwordListView.requestFocus();
                        }
                    }
            }
        }
        return false;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/10
     * @Description VerticalViewPager.OnPageChangeListener接口回调
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {//sdk版本低于4.4
            container.invalidate();
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/10
     * @Description VerticalViewPager.OnPageChangeListener接口回调
     */
    @Override
    public void onPageSelected(int position) {
        if (null != currentFilmAdapter) {
            if (null != currentFilmAdapter && null != currentFilmAdapter.getPrimaryItem()) {
                ((FilmPageItemView) currentFilmAdapter.getPrimaryItem()).resetUserColor();
            }
            int lastPage = currentFilmAdapter.getCount() - 1;
            if (pageCount > 0) {
                pageCountView.setText(String.format(getResources().getString(R.string.current_page), (position + 1) + "", pageCount + ""));
            }
            if (position == lastPage - 1) {//数据拉取有点慢，提前一页开始请求后面的数据
                Logger.i(TAG, "append data");

                if (hotwordAdapter.getDataFromWordId(hotWordId).getName().equals(HotWordEntity.FILM_ALL)) {
                    CloudScreenService.getInstance().getFilm(new AppendFilmListByTagTask(this), channelId, channelType, PAGE_SIZE, ++currentPage);
                } else {
                    Logger.d(TAG, "currentPage=" + currentPage);
                    CloudScreenService.getInstance().getHotWordsContent(new AppendFilmListFromHotWord(this), channelId, hotWordId, ++currentPage, PAGE_SIZE);
                }
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/10
     * @Description VerticalViewPager.OnPageChangeListener接口回调
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            DisplayImageUtil.getInstance().resume();
        } else {
            DisplayImageUtil.getInstance().pause();
        }
    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/26
     * @Description 分类筛选文字切换动画，暂时不上等媒资库
     */
    private void categoryAnim(final String txt) {
        categoryView.animate().translationX(categoryView.getWidth() - 1).start();
        categoryLayout.animate().translationX(-categoryLayout.getWidth()).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                categoryView.animate().translationX(0).start();
                categoryLayout.animate().translationX(0).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        categoryView.setText(txt);
                    }
                }).start();
            }
        }).start();
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/27
     * @Description FilmPageItemView.OnItemSelectedListener接口回调
     */
    @Override
    public void onItemSelected(View v) {
//        filmListHandler.removeMessages(FilmListHandler.RESET_BACKGROUND);
//        Message msg = new Message();
//        msg.what = FilmListHandler.RESET_BACKGROUND;
//        msg.obj = v;
//        filmListHandler.sendMessageDelayed(msg, FilmListHandler.RESET_BACKGROUND_DELAYE_TIME);

    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/27
     * @Description 重置背景的方法，弃用
     */
    private void resetBackground(View v) {
        String imageUrl = null;
        if (v.getTag(R.id.tag_listview_entity) instanceof AlbumEntity) {
            AlbumEntity entity = (AlbumEntity) v.getTag(R.id.tag_listview_entity);
            Logger.d(TAG, "BlueRayImg=" + entity.getAlbumHbPic());
            imageUrl = entity.getAlbumHbPic();
        } else if (v.getTag(R.id.tag_listview_entity) instanceof HotWordsContentEntity) {
            HotWordsContentEntity entity = (HotWordsContentEntity) v.getTag(R.id.tag_listview_entity);
            imageUrl = entity.getBigBackPic();
            Logger.d(TAG, "imageUrl=" + imageUrl);
        }
        if (null != imageUrl) {
            CloudScreenApplication.getInstance().imageLoader.loadImage(imageUrl, new SimpleImageLoadingListener() {
                @SuppressLint("NewApi")
                @Override
                public void onLoadingComplete(String imageUri, View view, final Bitmap loadedImage) {
                    super.onLoadingComplete(imageUri, view, loadedImage);
                    //通过异步任务设置高斯模糊图
                    new AsyncTask<Context, String, Bitmap>() {
                        @Override
                        protected Bitmap doInBackground(Context... params) {
                            Bitmap blurBitmap = BitmapBlurUtil.getInstance().blurBitmap(loadedImage, params[0]);
                            return blurBitmap;
                        }

                        @Override
                        protected void onPostExecute(final Bitmap bitmap) {
                            if (null != bitmap) {
                                container.animate().alpha(0.8f).setDuration(200).setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        super.onAnimationEnd(animation);
                                        container.setBackground(new BitmapDrawable(null, bitmap));
                                        container.animate().alpha(1).setDuration(200).start();
                                    }
                                }).start();
                            }
                            super.onPostExecute(bitmap);
                        }
                    }.execute(FilmListActivity.this);
                }
            });
        }

    }


    /**
     * @Author Spr_ypt
     * @Date 2016/6/24
     * @Description UserStateUtil.UserStatusWatcher回调接口
     */
    @Override
    public void userStatusChanged() {
        dealVipColor();
    }

    @Override
    public void userStatusNoChanged() {

    }


    /**
     * @Author Spr_ypt
     * @Date 2015/11/19
     * @Description 热词选中监听，替代焦点监听
     */
    private static class HotwordOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private View lastSelectedView;
        private View selectedView;
        private int position = 0;//选择的position，即onItemSelected返回的id
        private int focusPosition = 0;//当前选中的position，选中会有延迟所以与position有区别
        private WeakReference<FilmListActivity> reference;

        private HotwordOnItemSelectedListener(FilmListActivity activity) {
            reference = new WeakReference<FilmListActivity>(activity);
        }


        public void setLastSelectedView(View lastSelectedView) {
            this.lastSelectedView = lastSelectedView;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Logger.d(TAG, "onItemSelected position=" + position);
            FilmListActivity activty = reference.get();

            selectedView = view;
            this.position = position;
            activty.filmListHandler.removeMessages(FilmListHandler.HOTWORD_SELECTED);
            activty.filmListHandler.sendEmptyMessageDelayed(FilmListHandler.HOTWORD_SELECTED, FilmListHandler.HOTWORD_SELECTED_DELAYE_TIME);

        }


        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Logger.d(TAG, "onNothingSelected");
            FilmListActivity activty = reference.get();
            activty.filmListHandler.removeMessages(FilmListHandler.HOTWORD_SELECTED);
        }

        /**
         * @Author Spr_ypt
         * @Date 2015/11/19
         * @Description 处理某个标签被选中的逻辑
         */
        @SuppressLint("NewApi")
        public void setHotwordSelected() {
            FilmListActivity activty = reference.get();
            //如果选中的热词和之前的是同一个则不做变化
            if (selectedView != lastSelectedView) {
                activty.startLoading();
                focusPosition = position;
                setLastSelectedView(selectedView);
                activty.setHotwordData(activty.hotwordAdapter.getData(position), 1, PAGE_SIZE);

                //统计埋点
                if (!activty.hotwordAdapter.getData(position).getName().equals(HotWordEntity.FILM_ALL)) {
                    BaseActionInfo info = new ListViewActionInfo("03", "sde0302", "" + activty.channelId, "4", "" + activty.hotwordAdapter.getData(position).getId(), null);
                    new Statistics(activty, info).send();
                }
            }
        }


    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/25
     * @Description 设置热词数据
     */
    private void setHotwordData(HotWordEntity hotWordEntity, int pageNo, int pageSize) {
        hotWordId = hotWordEntity.getId();//记录当前热词id
        hotWordHsTypeId = hotWordEntity.getIsEffective();//记录当前热词对应的影片集类型
        currentPage = 1;//切换标签后需要重置当前页数
        Logger.d(TAG, "setHotwordData.hotWordId=" + hotWordId);
        //不是全部内容的话要根据热词id请求数据，否则请求全部数据
        isLoading = true;
        //首次进入默认选中热词不算有操作，之后的肯定是用户操作导致
        if (hotWordTimes > 0) {
            noAction = "0";
        }
        hotWordTimes++;
        if (!hotWordEntity.getName().equals(HotWordEntity.FILM_ALL)) {
            CloudScreenService.getInstance().getHotWordsContent(new HotWordContentListener(this), channelId, hotWordId, pageNo, pageSize);
        } else {
            CloudScreenService.getInstance().getFilm(new GetFilmListener(this), channelId, channelType, PAGE_SIZE, 1);
        }

    }

    private static class HotWordContentListener implements OnRequestResultListener<ResultEntity<HotWordsContentEntity>> {
        private WeakReference<FilmListActivity> reference;

        private HotWordContentListener(FilmListActivity activity) {
            reference = new WeakReference<FilmListActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity<HotWordsContentEntity> resultEntity) {
            Logger.i(TAG, "HotWordContentListener.onSucess  " + resultEntity.getList().size());
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                activity.countView.setText(String.format(activity.getResources().getString(R.string.total_film), resultEntity.arg1 + ""));
                activity.pageCount = (int) Math.ceil((double) resultEntity.arg1 / 10);
                if (activity.pageCount > 0) {
                    activity.pageCountView.setText(String.format(activity.getResources().getString(R.string.current_page), 1 + "", activity.pageCount + ""));
                } else {
                    activity.pageCountView.setText(" ");
                }
                //如果该热词有特殊设定的频道类型就取特设值，没有的话默认当前频道值
                Integer mCid = null != activity.hotWordHsTypeId ? activity.hotWordHsTypeId : activity.channelId;
                activity.filmListFromHotAdapter = new FilmListFromHotAdapter(activity, activity, activity);
                activity.filmListFromHotAdapter.setData(resultEntity.getList());
                activity.filmListFromHotAdapter.setOnActionListener(activity);
                activity.verticalViewPager.setAdapter(activity.filmListFromHotAdapter);
                activity.currentFilmAdapter = activity.filmListFromHotAdapter;
                if ("1".equals(activity.noAction)) {//首次进入第一部影片获取焦点
                    if (null != activity.currentFilmAdapter.getFirstItem()) {
                        activity.currentFilmAdapter.getFirstItem().requestFocus();
                    }
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            Log.i(TAG, "HotWordContentListener.onFail");
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
            }

        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.i(TAG, "HotWordContentListener.onParseFail");
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/17
     * @Description 获取热词列表监听
     */
    private static class HotWordListListener implements OnRequestResultListener<ResultEntity<AlbumEntity>> {
        private WeakReference<FilmListActivity> reference;

        private HotWordListListener(FilmListActivity activity) {
            reference = new WeakReference<FilmListActivity>(activity);
        }


        @Override
        public void onSucess(ResultEntity entity) {
            FilmListActivity activity = reference.get();
            if (null != entity && null != entity.getList() && entity.getList().size() > 0) {
                activity.initHotword(entity);
            } else {
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
            }

        }

        @Override
        public void onFail(Exception e) {
            Logger.d(TAG, "hotword request onFail");
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Logger.d(TAG, "hotword request onParseFail");
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/19
     * @Description 获取电影列表监听
     */
    public static class GetFilmListener implements OnRequestResultListener<ResultEntity> {


        private WeakReference<FilmListActivity> reference = null;

        public GetFilmListener(FilmListActivity activity) {
            reference = new WeakReference<FilmListActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            Log.d(TAG, "onSuccess");
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                activity.countView.setText(String.format(activity.getResources().getString(R.string.total_film), resultEntity.arg1 + ""));
                activity.pageCount = (int) Math.ceil((double) resultEntity.arg1 / 10);
                if (activity.pageCount > 0) {
                    activity.pageCountView.setText(String.format(activity.getResources().getString(R.string.current_page), 1 + "", activity.pageCount + ""));
                } else {
                    activity.pageCountView.setText(" ");
                }
                activity.filmListAdapter = new FilmListAdapter(activity, activity, activity);
                activity.filmListAdapter.setData(resultEntity.getList());
                activity.filmListAdapter.setOnActionListener(activity);
                activity.verticalViewPager.setAdapter(activity.filmListAdapter);
                activity.currentFilmAdapter = activity.filmListAdapter;
                if ("1".equals(activity.noAction)) {
                    if (null != activity.currentFilmAdapter.getFirstItem()) {
                        activity.currentFilmAdapter.getFirstItem().requestFocus();
                    }
                }
                //初始化分类筛选控件
//                activity.filmFilter = New.newListFilter(activity, activity.verticalViewPager, activity.firstClass + "", activity);
            }
        }

        @Override
        public void onFail(Exception e) {
            Log.d(TAG, "onParseJsonFail");
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.d(TAG, "onFail");
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.finishLoading();
                Toast.makeText(activity.getApplicationContext(), activity.getString(R.string.load_data_failed_please_try_again), Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/20
     * @Description 补充列表数据方法监听
     */
    private static class AppendFilmListByTagTask implements OnRequestResultListener<ResultEntity> {
        private WeakReference<FilmListActivity> ref;

        public AppendFilmListByTagTask(FilmListActivity activity) {
            ref = new WeakReference<FilmListActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            Log.i(TAG, "AppendFilmListByTagTask.onSucess result=" + resultEntity.getList().size());
            FilmListActivity activity = ref.get();
            if (null != activity) {
                activity.filmListAdapter.setItemPosition(VerticalPagerAdapter.POSITION_UNCHANGED);
                activity.filmListAdapter.addData(resultEntity.getList()); // 追加数据
                activity.isLoading = false;
            }
        }

        @Override
        public void onFail(Exception e) {
            Log.e(TAG, "AppendFilmListByTagTask.onFail e=" + e.toString());
            FilmListActivity activity = ref.get();
            if (null != activity) {
                activity.isLoading = false;
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.e(TAG, "AppendFilmListByTagTask.onParseFail e=" + e.toString());
            FilmListActivity activity = ref.get();
            if (null != activity) {
                activity.isLoading = false;
            }
        }
    }

    private static class AppendFilmListFromHotWord implements OnRequestResultListener<ResultEntity<HotWordsContentEntity>> {
        private WeakReference<FilmListActivity> reference;

        private AppendFilmListFromHotWord(FilmListActivity activity) {
            reference = new WeakReference<FilmListActivity>(activity);
        }


        @Override
        public void onSucess(ResultEntity<HotWordsContentEntity> resultEntity) {
            Log.i(TAG, "AppendFilmListFromHotWord.onSucess result=" + resultEntity.getList().size());
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.filmListFromHotAdapter.setItemPosition(VerticalPagerAdapter.POSITION_UNCHANGED);
                activity.filmListFromHotAdapter.addData(resultEntity.getList()); // 追加数据
                activity.isLoading = false;
            }
        }

        @Override
        public void onFail(Exception e) {
            Log.e(TAG, "AppendFilmListFromHotWord.onFail e=" + e.toString());
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.isLoading = false;
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.e(TAG, "AppendFilmListFromHotWord.onParseFail e=" + e.toString());
            FilmListActivity activity = reference.get();
            if (null != activity) {
                activity.isLoading = false;
            }
        }
    }

    /**
     * @Title: SixColumnListActivity
     * @author:yupengtong
     * @Description: 开始加载动画
     */
    private void startLoading() {
        filmListHandler.sendEmptyMessageDelayed(FilmListHandler.NEED_LOADING_VIEW, FilmListHandler.LOADING_DELAYE_TIME);
        isLoading = true;
    }


    /**
     * @Title: SixColumnListActivity
     * @author:yupengtong
     * @Description: 结束加载
     */
    private void finishLoading() {
        filmListHandler.removeMessages(FilmListHandler.NEED_LOADING_VIEW);
        if (loadingView.getVisibility() == View.VISIBLE) {
            loadingView.setVisibility(View.GONE);
        }
        if (filterMask.getAlpha() != 0) {
            filterMask.animate().alpha(0).setDuration(600).start();
        }
        isLoading = false;
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/11/19
     * @Description 用于处理吧本activity的所有handler消息
     */
    public static class FilmListHandler extends Handler {
        private WeakReference<FilmListActivity> reference;
        /**
         * loading延时消息标记位
         */
        public static final int NEED_LOADING_VIEW = 0x0001;
        /**
         * loading画面出现延迟 By Spr_ypt
         */
        public static final int LOADING_DELAYE_TIME = 200;
        /**
         * 热词选中延时消息
         */
        public static final int HOTWORD_SELECTED = 0x0002;
        /**
         * 热词选中延迟时间
         */
        public static final int HOTWORD_SELECTED_DELAYE_TIME = 500;
        /**
         * 重置背景
         */
        public static final int RESET_BACKGROUND = 0x0003;
        /**
         * 重置背景延时
         */
        public static final int RESET_BACKGROUND_DELAYE_TIME = 2000;

        public FilmListHandler(FilmListActivity activity) {
            reference = new WeakReference<FilmListActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FilmListActivity activity = reference.get();
            if (null != activity) {
                switch (msg.what) {
                    case NEED_LOADING_VIEW:
                        if (activity.isLoading) {
                            activity.loadingView.setVisibility(View.VISIBLE);
                            activity.filterMask.setAlpha(1);
                        }
                        break;
                    case HOTWORD_SELECTED:
                        activity.hotwordOnItemSelectedListener.setHotwordSelected();
                        break;
                    case RESET_BACKGROUND:
                        activity.resetBackground((View) msg.obj);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if (isLoading && KeyEvent.KEYCODE_BACK != event.getKeyCode()) {
            return true;
        }
        if (KeyEvent.ACTION_DOWN == event.getAction()) {
            if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (null != verticalViewPager && currentFilmAdapter != null) {
                    if (verticalViewPager.getCurrentItem() == currentFilmAdapter.getCount() - 1) {
                        FilmPageItemView view = (FilmPageItemView) currentFilmAdapter.getPrimaryItem();
                        if (view.hasFocus()) {
                            if (!view.hasNextDownView(view.getCurrentIndex())) {
                                if (null != feedbackAnimator && !feedbackAnimator.isRunning()) {
                                    feedbackAnimator.start();
                                }
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * @Author Spr_ypt
     * @Date 2015/12/8
     * @Description FilmListAdapter.OnActionListener接口回调
     */
    @Override
    public void effectiveAction() {
        noAction = "0";
    }


    @Override
    protected void onStart() {
        openTime = System.currentTimeMillis();
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserStateUtil.getInstance().addWatcher(this);
        UserStateUtil.getInstance().notifyReFreshUserState(this);
        dealVipColor();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UserStateUtil.getInstance().removeWatcher(this);
    }

    @Override
    protected void onStop() {
        filmListHandler.removeMessages(FilmListHandler.RESET_BACKGROUND);
        BaseActionInfo info = new ListBrowseActionInfo("03", "vip03016", noAction, channelId + "", Long.toString(System.currentTimeMillis() - openTime));//统计埋点
        new Statistics(this, info).send();//统计埋点
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != loadingView) {
            loadingView.recycle();
        }
        if (null != verticalViewPager) {
            verticalViewPager.setOnPageChangeListener(null);
        }
        FeedbackTranslationAnimatorUtil.getInstance().recycle();
    }
}
