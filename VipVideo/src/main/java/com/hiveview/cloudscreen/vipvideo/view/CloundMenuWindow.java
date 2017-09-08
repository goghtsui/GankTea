package com.hiveview.cloudscreen.vipvideo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressLint("NewApi")
public class CloundMenuWindow {

    /**
     * 显示菜单View的PopuWindow
     */
    private MeunPopuWindow meunWindow = null;

    /**
     * 用户自定义菜单项的集合
     */
    private List<MenuItemEntity> items = null;

    /**
     * 菜单View的总容器
     */
    private RelativeLayout container = null;

    /**
     * 菜单项的View的容器
     */
    private LinearLayout itemContainer = null;

    private LauncherFocusView launcherFocusView;

    /**
     * 回调外部响应菜单项选中事件的监听接口对象
     */
    private OnSelectedItemClickListener itemSelectedListener = null;

    /**
     * 菜单项title的标示
     */
    public static final int MENU_TYPE_TITLE = 1000;

    /**
     * 菜单项搜索的标示
     */
    public static final int MENU_TYPE_SEARCH = 1001;

    /**
     * 菜单项个人中心的标示
     */
    public static final int MENU_TYPE_PERSON = 1002;

    /**
     * 菜单项设置的标示
     */
    public static final int MENU_TYPE_SETTING = 1003;

    private AtomicBoolean isOnAnim = new AtomicBoolean(false);

    private final String TAG = "CloundMenuWindow";

    /**
     * 调用系统设置的包命
     */
    private final String settingPackageName = "com.hiveview.cloudtv.settings";

    /**
     * mContext
     *
     * @author Liulifeng
     * @date 17:25 2014/12/11 星期四
     */
    private Context mContext;

    /**
     * 菜单构造函数
     *
     * @param @param context
     * @param @param menuItems
     *               调用的自定义的菜单项的项数据实体集合，如果不需要自定义菜单项，使用默认菜单项（搜索，个人中心，设置），就传null
     * @date
     */
    public CloundMenuWindow(Context context, List<MenuItemEntity> menuItems) {
        mContext = context;
        if (null != menuItems) {
            // 用户自定义的菜单项集合
            items = menuItems;
        } else {
            items = new ArrayList<MenuItemEntity>();
        }

        // 菜单的标题头
        MenuItemEntity titleEntity = new MenuItemEntity();
        titleEntity.setItemName(context.getString(R.string.menu_title));
        titleEntity.setItemIconResId(R.drawable.menu_);
        titleEntity.setItemIconFocusResId(R.drawable.menu_);
        titleEntity.setItemPosition(MENU_TYPE_TITLE);

        // 菜单中的搜索项
        MenuItemEntity searchEntity = new MenuItemEntity();
        searchEntity.setItemName(context.getString(R.string.search_title));
        searchEntity.setItemIconResId(R.drawable.search_focus);
        searchEntity.setItemIconFocusResId(R.drawable.search_focus);
        searchEntity.setItemPosition(MENU_TYPE_SEARCH);

        // 菜单中的个人中心项
        MenuItemEntity personEntity = new MenuItemEntity();
        personEntity.setItemName(context.getString(R.string.person_title));
        personEntity.setItemIconResId(R.drawable.person_focus);
        personEntity.setItemIconFocusResId(R.drawable.person_focus);
        personEntity.setItemPosition(MENU_TYPE_PERSON);

        // 菜单中的设置项
        MenuItemEntity settingEntity = new MenuItemEntity();
        settingEntity.setItemName(context.getString(R.string.setting_title));
        settingEntity.setItemIconResId(R.drawable.setting_focus);
        settingEntity.setItemIconFocusResId(R.drawable.setting_focus);
        settingEntity.setItemPosition(MENU_TYPE_SETTING);

        items.add(0, titleEntity);
        items.add(searchEntity);
        items.add(personEntity);
        items.add(settingEntity);

        if (null != items) {
            itemContainer = new LinearLayout(context);// 初始化菜单容器

            itemContainer.setOrientation(LinearLayout.VERTICAL);// 菜单项竖直显示

            MeunItemClickListener clickListener = new MeunItemClickListener();
            MeunItemOnFocusChangeListener onFocusChangeListener = new MeunItemOnFocusChangeListener();
            MeunItemOnKeyListener keyListener = new MeunItemOnKeyListener();

            for (int i = 0; i < items.size(); i++) {
                ItemView itemView = new ItemView(context);

                MenuItemEntity entity = items.get(i);// 获取菜单项内容
                if (i == 0) {// 菜单的title是不能获取焦点
                    itemView.setFocusable(false);
                    itemView.setMeunTitleStyle();
                } else {// 其他菜单项能获取焦点
                    itemView.setFocusable(true);
                    if (entity.getItemPosition() == 0) {
                        entity.setItemPosition(i - 1);// 排除菜单title项
                    }
                }

                itemView.setDisplayItemName(entity.getItemName());// 设置菜单项显示文本
                itemView.setDisplayItemIcon(entity.getItemIconFocusResId());// 设置菜单项显示的Icon
                itemView.setOnClickListener(clickListener);
                itemView.setOnFocusChangeListener(onFocusChangeListener);
                itemView.setOnKeyListener(keyListener);
                itemView.setTag(entity);

                if (i == 0) {
                    itemView.setNameTextSize((int) context.getResources().getDimension(R.dimen.cloundmenuwindow_itemView_setNameTextSize));// title字体偏小
                    // 21
                } else {
                    ImageView imageLine = new ImageView(context);
                    imageLine.setImageResource(R.drawable.menu_line);// 添加菜单项顶部的先
                    itemContainer.addView(imageLine);
                }

                // 添加菜单项到菜单容器
                LinearLayout.LayoutParams params = new LayoutParams((int) context.getResources().getDimension(
                        R.dimen.cloundmenuwindow_LinearLayout_LayoutParams_width), (int) context.getResources().getDimension(
                        R.dimen.cloundmenuwindow_LinearLayout_LayoutParams_heigh));// 344,130
                itemContainer.addView(itemView, params);
            }

            // 焦点框的View
            launcherFocusView = new LauncherFocusView(context, false);

            container = new RelativeLayout(context);// 最外层的父View容器

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            // 添加菜单项容器到总容器
            params.leftMargin = (int) context.getResources().getDimension(R.dimen.cloundmenuwindow_params_leftMargin);// 54
            params.topMargin = (int) context.getResources().getDimension(R.dimen.cloundmenuwindow_params_topMargin);// 110
            container.addView(itemContainer, params);

            // 添加到最顶层的View
            container.addView(launcherFocusView);

            // 初始化显示菜单的popuWindow
            meunWindow = new MeunPopuWindow(container, (int) context.getResources().getDimension(R.dimen.cloundmenuwindow_MeunPopuWindow_width),
                    (int) context.getResources().getDimension(R.dimen.cloundmenuwindow_MeunPopuWindow_heigh));// 750
            // 1080
            meunWindow.setFocusable(true);// 能够夺取屏幕内的焦点
            meunWindow.setAnimationStyle(R.style.menu_popu_in_out_style);// 定义PopuWindow进出动画
            // 设置popuWindow的背景
//			meunWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), DisplayImageUtil.getInstance().decodeResource(context.getResources(), R.drawable.menu_window_bg)));
            meunWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.menu_background));

        }
    }

    /**
     * 菜单项的Item显示信息的类
     *
     * @ClassName: MenuItemEntity
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-9-20 下午3:29:16
     */
    public static class MenuItemEntity {

        /**
         * 菜单项对应的位置
         */
        private int itemPosition;

        /**
         * 菜单项显示的文本内容
         */
        private String itemName;

        /**
         * 显示菜单的项的图片Icon
         */
        private int itemIconResId;

        /**
         * 显示菜单的项获取焦点后的图片Icon
         */
        private int itemIconFocusResId;

        public int getItemPosition() {
            return itemPosition;
        }

        /**
         * 设置菜单项在菜单项中的位置
         *
         * @param position
         * @Title: MenuItemEntity
         * @author:陈丽晓
         * @Description: TODO
         */
        public void setItemPosition(int position) {
            itemPosition = position;
        }

        public String getItemName() {
            return itemName;
        }

        /**
         * 设置菜单项在菜单中的显示的名称
         *
         * @param itemName
         * @Title: MenuItemEntity
         * @author:陈丽晓
         * @Description: TODO
         */
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public int getItemIconResId() {
            return itemIconResId;
        }

        /**
         * 设置菜单项正常显示的图片资源
         *
         * @param itemIconResId
         * @Title: MenuItemEntity
         * @author:陈丽晓
         * @Description: TODO
         */
        public void setItemIconResId(int itemIconResId) {
            this.itemIconResId = itemIconResId;
        }

        public int getItemIconFocusResId() {
            return itemIconFocusResId;
        }

        /**
         * 设置菜单项获取焦点后显示的图片资源
         *
         * @Title: MenuItemEntity
         * @author:陈丽晓
         * @Description: TODO
         */
        public void setItemIconFocusResId(int itemIconFocusResId) {
            this.itemIconFocusResId = itemIconFocusResId;
        }

    }

    /**
     * 自定义菜单项的View
     *
     * @ClassName: ItemView
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-9-20 下午10:02:41
     */
    private class ItemView extends LinearLayout {

        /**
         * 显示菜单项的ICON
         */
        private ImageView icon = null;

        /**
         * 显示菜单项的文本
         */
        private TextView tvName = null;

        public ItemView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public ItemView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public ItemView(Context context) {
            super(context);
            setOrientation(HORIZONTAL);

            setGravity(Gravity.CENTER_VERTICAL);

            icon = new ImageView(getContext());
            icon.setAlpha(0.3f);
            tvName = new TextView(getContext());

            // 设置菜单文本显示属性
            tvName.setTextSize(context.getResources().getDimension(R.dimen.cloundmenuwindow_tvName_setTextSize));// 24
            tvName.setTextColor(0xffffffff);
            tvName.setAlpha(0.3f);
            tvName.animate().setDuration(350);

            // 设置距离属性
            LinearLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = (int) context.getResources().getDimension(R.dimen.cloundmenuwindow_LinearLayout_params_leftMargin);// 20

            addView(icon, params);
            addView(tvName, params);
        }

        /**
         * 设置字体属性显示
         *
         * @param textSize
         * @Title: ItemView
         * @author:陈丽晓
         * @Description: TODO
         */
        public void setNameTextSize(int textSize) {
            tvName.setTextSize(textSize);
        }

        /**
         * 设置显示的菜单文本显示
         *
         * @param name
         * @Title: ItemView
         * @author:陈丽晓
         * @Description: TODO
         */
        public void setDisplayItemName(String name) {
            tvName.setText(name);
        }

        /**
         * 设置显示的菜单Icon
         *
         * @param resId
         * @Title: ItemView
         * @author:陈丽晓
         * @Description: TODO
         */
        public void setDisplayItemIcon(int resId) {
            icon.setImageResource(resId);
        }

        public void setFoucsTextColor() {
            tvName.animate().alpha(1f);
            icon.animate().alpha(1f);
        }

        public void setNormalTextColor() {
            tvName.animate().alpha(0.3f);
            icon.animate().alpha(0.3f);
        }

        public void setMeunTitleStyle() {
            tvName.setAlpha(1f);
            icon.setAlpha(1f);
        }

    }

    /**
     * 显示菜单
     *
     * @Title: CloundMenuWindow
     * @author:陈丽晓
     * @Description: TODO
     */
    /**
     * @Title: CloundMenuWindow
     * @author:alfred_bin
     * @Description
     * @date 2015-3-20
     */
    /**
     * @Title: CloundMenuWindow
     * @author:alfred_bin
     * @Description
     * @date 2015-3-20
     */
    public void show() {
        if (!meunWindow.isShowing()) {
            isOnAnim.set(true);
            launcherFocusView.setX((0 - mContext.getResources().getDimension(R.dimen.cloundmenuwindow_show_launcherFocusView_344)) * 2);
            meunWindow.showAtLocation(container, Gravity.LEFT, 0, 0);// 左侧显示

            // 默认获得焦点的位置
            int tmpFocusPosition = ((itemContainer.getChildCount() - 1) / 2);
            final int focusPosition = tmpFocusPosition % 2 == 0 ? tmpFocusPosition : tmpFocusPosition + 1;


            Collection<Animator> animators = new ArrayList<>();
            // 设置甩尾动画
            for (int i = 0; i < itemContainer.getChildCount(); i++) {
                View view = itemContainer.getChildAt(i);
                view.setX((view.getX() - mContext.getResources().getDimension(R.dimen.cloundmenuwindow_show_view_setX_344)) * 2);
                Animator animator = ObjectAnimator.ofFloat(view, "x", view.getX(), 0);
                animator.setDuration(250);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setStartDelay(i * 60);

                if (i == focusPosition) {
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            launcherFocusView.initFocusView(itemContainer.getChildAt(focusPosition), 250);
                        }
                    });
                }
                // start by liulifeng 2015年3月20日 15:22:48
                if (i == itemContainer.getChildCount() - 1) {
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isOnAnim.set(false);
                            Log.d(TAG, "itemContainer.getChildCount()-1---->isOnAnim : " + isOnAnim + " --> i :"
                                    + (itemContainer.getChildCount() - 1));
                        }
                    });
                }
                animators.add(animator);
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(animators);
            animatorSet.start();
        }
    }

    /**
     * 自定义菜单的PopupWindow,为了响应用户按返回键指定菜单项的动画
     *
     * @ClassName: MeunPopuWindow
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-9-20 下午10:12:44
     */
    private class MeunPopuWindow extends PopupWindow {

        public MeunPopuWindow(View contentView, int width, int height) {
            super(contentView, width, height);
        }

        @Override
        public void dismiss() {
            // 当用户按了返回键，菜单消失动画
            if (!isOnAnim.get()) {
                super.dismiss();
            }

        }


    }

    /**
     * 响应菜单项确定事件，外部回到接口
     *
     * @ClassName: OnSelectedItemClickListener
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-9-20 下午10:25:35
     */
    public interface OnSelectedItemClickListener {
        public void selectedItemClick(MenuItemEntity entity);
    }

    public void setItemSelectedListener(OnSelectedItemClickListener itemSelectedListener) {
        this.itemSelectedListener = itemSelectedListener;
    }

    /**
     * 响应菜单项的按键事件监听
     *
     * @ClassName: MeunItemClickListener
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-9-20 下午10:31:01
     */
    private class MeunItemClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {

            MenuItemEntity entity = (MenuItemEntity) v.getTag();
            if (entity.getItemPosition() == MENU_TYPE_SEARCH) {
                Log.d(TAG, "search is selected");
                Intent intentSearch = new Intent(Invoker.ACTION_START_GLOBAL_SEARCH);
                if (isVertical) {
                    intentSearch.putExtra("resultShowType", "vertical");// 默认 影视（竖图）优先
                } else {
                    intentSearch.putExtra("resultShowType", "horizontal");// 否则
                    // 设置为横图模式
                    isVertical = true;
                }
                intentSearch.addCategory(Intent.CATEGORY_DEFAULT);
                intentSearch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentSearch);
            } else if (entity.getItemPosition() == MENU_TYPE_PERSON) {
                Log.d(TAG, "person is selected");
                Intent intentPerson = new Intent(Invoker.ACTION_START_PERSON);
                intentPerson.addCategory(Intent.CATEGORY_DEFAULT);
                intentPerson.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intentPerson);
            } else if (entity.getItemPosition() == MENU_TYPE_SETTING) {
                try {
                    Intent intent = mContext.getPackageManager().getLaunchIntentForPackage(Invoker.PACKAGE_START_SETTING.trim());// 防止包名前后多了空格
                    if (intent != null) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {// 响应用户自动定义的菜单项
                if (null != itemSelectedListener) {
                    itemSelectedListener.selectedItemClick(entity);
                }
            }

            meunWindow.dismiss();
        }
    }

    public boolean isVertical = true; // 搜索默认为竖图显示

    public void setSearchType(boolean isVertical) { // 设置搜索模块的类型样式
        this.isVertical = isVertical;
    }

    /**
     * 响应菜单项的焦点改变事件监听
     *
     * @ClassName: MeunItemClickListener
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-9-20 下午10:31:01
     */
    private class MeunItemOnFocusChangeListener implements OnFocusChangeListener {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            ItemView itemView = (ItemView) v;
            // start by liulifeng 2015年3月20日 15:24:27
            Log.d(TAG, "onFocusChange()-------> isOnAnim : " + isOnAnim);
            if (hasFocus) {
                Log.d(TAG, "onFocusChange()-------> hasFocus");
                launcherFocusView.moveTo(v);
                itemView.setFoucsTextColor();
            } else {
                Log.d(TAG, "onFocusChange()-------> ");
                itemView.setNormalTextColor();
            }
            // end by liulifeng
        }

    }

    /**
     * 菜单项的按键事件监听
     *
     * @ClassName: MeunItemOnKeyListener
     * @Description: TODO
     * @author: 陈丽晓
     * @date 2014-9-20 下午10:44:32
     */
    private class MeunItemOnKeyListener implements OnKeyListener {

        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            // start by liulifeng 2015年3月20日 15:23:42
            Log.d(TAG, "onKey()---->isOnAnim : " + isOnAnim.get());
            if (isOnAnim.get()) {
                return true;
            }
            // end by liulifeng
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {// 向右按键标示选中此项
                v.performClick();
                meunWindow.dismiss();
                return true;
            }
            if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_MENU) {
                meunWindow.dismiss();
                return true;
            }

            return false;
        }

    }

}