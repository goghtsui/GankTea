package com.hiveview.cloudscreen.vipvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.common.AppConstants;
import com.hiveview.cloudscreen.vipvideo.common.CloudScreenApplication;
import com.hiveview.cloudscreen.vipvideo.common.Invoker;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.ActivityListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.statistic.BaseActionInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.Statistics;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.ActivityBrowseInfo;
import com.hiveview.cloudscreen.vipvideo.statistic.imp.LoginDialogActionInfo;
import com.hiveview.cloudscreen.vipvideo.util.DisplayImageUtil;
import com.hiveview.cloudscreen.vipvideo.util.ToastUtil;
import com.hiveview.cloudscreen.vipvideo.util.UserStateUtil;
import com.hiveview.cloudscreen.vipvideo.view.CloundMenuWindow;
import com.hiveview.cloudscreen.vipvideo.view.TypeFaceTextView;
import com.hiveview.cloudscreen.vipvideo.view.VipVideoDialog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


public class VipActivityDetailsActivity extends BaseActivity implements UserStateUtil.UserStatusWatcher {

    private ImageView background;
    private View view_action_film;
    private TypeFaceTextView tv_open_vip;
    VipVideoDialog vipVideoDialog;

    private Context context;
    /**
     * 标记点击了登录，用于在回调中判断是否登录成功
     */
    private boolean isLogining = false;

    private boolean isAction = false;

    private int isVip = -1;

    /**
     * 活动id
     */
    int actionId = -1;
    List<View> focusView = new ArrayList<>();
    private boolean backgroundDown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_detail);
        context = this;
        background = (ImageView) findViewById(R.id.activity_background);
        view_action_film = findViewById(R.id.view_action_film);
        tv_open_vip = (TypeFaceTextView) findViewById(R.id.tv_film_detail_open_vip);
        view_action_film.setOnClickListener(new UserOpenVipOnClickListener(this));
        focusView.add(view_action_film);

        //获取 actionId 来展示不同的活动
        actionId = getIntent().getIntExtra(AppConstants.EXTRA_ACTION_ID, -1);
        Log.i("test", "actionId:::" + actionId);
        //添加菜单显示
        initMenu();
        //注册成为用户状态变化观察者
        UserStateUtil.getInstance().addWatcher(this);

        //判断是否是vip,改变焦点颜色
        resetFocusColor(UserStateUtil.getInstance().getUserStatus());

        //判断用户是否是vip进行用户请求背景图参数
        if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.VIPUSER) {
            isVip = 1;
        } else {
            isVip = 0;
        }

        //获取活动页背景图
        CloudScreenService.getInstance().getActivityList(new ActivityListListener(this),isVip, actionId, 1, 10);
    }

    private void initMenu() {
        List<CloundMenuWindow.MenuItemEntity> menuItems = new ArrayList<CloundMenuWindow.MenuItemEntity>();
        CloundMenuWindow.MenuItemEntity menuCategoryFilter = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter.setItemName(getString(R.string.my_collect));
        menuCategoryFilter.setItemIconResId(R.drawable.ic_menu_category_collect_nomal);
        menuCategoryFilter.setItemIconFocusResId(R.drawable.ic_menu_category_collect_focus);

        CloundMenuWindow.MenuItemEntity menuCategoryFilter2 = new CloundMenuWindow.MenuItemEntity();
        menuCategoryFilter2.setItemName(getString(R.string.history_record));
        menuCategoryFilter2.setItemIconResId(R.drawable.ic_menu_category_history_nomal);
        menuCategoryFilter2.setItemIconFocusResId(R.drawable.ic_menu_category_history_focus);

        menuItems.add(menuCategoryFilter);
        menuItems.add(menuCategoryFilter2);
        mWindow = new CloundMenuWindow(this, menuItems);
        mWindow.setItemSelectedListener(new CloundMenuWindow.OnSelectedItemClickListener() {

            @Override
            public void selectedItemClick(CloundMenuWindow.MenuItemEntity entity) {
                if (entity.getItemPosition() == 0) { // 我的收藏
                    startActivity(new Intent(Invoker.ACTION_START_COLLECT));
                } else if (entity.getItemPosition() == 1) { // 历史记录
                    startActivity(new Intent(Invoker.ACTION_START_RECORD));
                }
            }
        });
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


    /**
     * 活动页接口
     */
    private static class ActivityListListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<VipActivityDetailsActivity> ref;

        public ActivityListListener(VipActivityDetailsActivity activity) {
            ref = new WeakReference<VipActivityDetailsActivity>(activity);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            VipActivityDetailsActivity activity = ref.get();
            if (activity != null) {
                List<ActivityListEntity> activityListEntity = resultEntity.getList();
                if (activityListEntity != null && activityListEntity.size() > 0) {
                    //下载背景图
                    Log.i("test", "entity:" + activityListEntity.toString());
                    if (null != activityListEntity.get(0).getActivityImg()) {
                        DisplayImageUtil.getInstance().displayImage(activityListEntity.get(0).getActivityImg(), activity.background);
                        activity.backgroundDown = true;
                    }else{
                        activity.background.setImageResource(R.drawable.bg_activity);
                    }
                }else{
                    activity.background.setImageResource(R.drawable.bg_activity);
                }
            }
        }

        @Override
        public void onFail(Exception e) {
            VipActivityDetailsActivity activity = ref.get();
            if (null != activity) {
                Toast.makeText(activity.getApplicationContext(), R.string.load_data_failed_please_try_again, Toast.LENGTH_SHORT)
                        .show();
                activity.background.setImageResource(R.drawable.bg_activity);
                activity = null;
            }
        }

        @Override
        public void onParseFail(HiveviewException e) {
            VipActivityDetailsActivity activity = ref.get();
            if (null != activity) {
                Toast.makeText(activity.getApplicationContext(), R.string.load_data_failed_please_try_again, Toast.LENGTH_SHORT)
                        .show();
                activity.background.setImageResource(R.drawable.bg_activity);
                activity = null;
            }
        }
    }


    /**
     * 点击事件
     */
    private static class UserOpenVipOnClickListener implements OnClickListener {
        private WeakReference<VipActivityDetailsActivity> reference;

        private UserOpenVipOnClickListener(VipActivityDetailsActivity activity) {
            reference = new WeakReference<VipActivityDetailsActivity>(activity);
        }

        @Override
        public void onClick(View view) {
            VipActivityDetailsActivity activity = reference.get();
            if (activity.backgroundDown) {
                //获取当前用户状态
                activity.setUserStatus(UserStateUtil.getInstance().getUserStatus());
                activity.isAction = true;
            } else {
                ToastUtil.showToast(activity, activity.getString(R.string.load_data_failed_please_try_again), 0);
            }

        }
    }


    /**
     * 设置用户状态
     *
     * @param userStatus 用户状态值
     */
    private void setUserStatus(UserStateUtil.UserStatus userStatus) {
        switch (userStatus) {
            case NOLOGIN:
                vipVideoDialog = new VipVideoDialog(this, getString(R.string.dialog_activity_login), getString(R.string.dialog_login_ok), getString(R.string.dialog_login_cancel), VipVideoDialog.DialogBg.D, false);
                vipVideoDialog.setOnBtnClicklistener(new VipVideoDialog.OnBtnClickListener() {
                    @Override
                    public void onPositiveBtnClick() {
                        isLogining = true;
                        UserStateUtil.getInstance().dealLogin(context);
                        vipVideoDialog.dismiss();
                    }

                    @Override
                    public void onNegativeBtnClick() {
                        vipVideoDialog.dismiss();
                        // //VIP登录弹出框埋点（点击行为  取消登录）
                        BaseActionInfo loginInfo = new LoginDialogActionInfo("03", "vip0306", "9", "3", null);
                        new Statistics(getApplicationContext(), loginInfo).send();
                    }

                    @Override
                    public void onDismissWithoutPressBtn() {
                        BaseActionInfo loginInfo = new LoginDialogActionInfo("03", "vip0306", "9", "3", null);
                        new Statistics(getApplicationContext(), loginInfo).send();
                    }
                });
                vipVideoDialog.showWindow();
                break;
            case NOMALUSER:
                UserStateUtil.getInstance().becomeVip(context, "9");
                break;
            case VIPUSER:
                tv_open_vip.setTextColor(Color.GRAY);
                Toast.makeText(this, R.string.becomevip_and_already_vip, Toast.LENGTH_SHORT).show();
                break;


        }
    }

    @Override
    public void userStatusChanged() {
        resetFocusColor(UserStateUtil.getInstance().getUserStatus());
        //统计登陆成功失败的埋点
        if (isLogining) {
            isLogining = false;
            //登录失败
            if (UserStateUtil.getInstance().getUserStatus() == UserStateUtil.UserStatus.NOLOGIN) {
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "9", "2", null);
                new Statistics(context, info).send();//统计埋点
            } else {
                //登录成功
                BaseActionInfo info = new LoginDialogActionInfo("03", "vip0306", "9", "1", null);
                new Statistics(context, info).send();//统计埋点
            }
        }
    }

    @Override
    public void userStatusNoChanged() {

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserStateUtil.getInstance().removeWatcher(this);//反注册监听
        if (!isAction) {
            BaseActionInfo info = new ActivityBrowseInfo("03", "vip03015", actionId + "");
            new Statistics(context, info).send();
            isAction = false;
        }
    }
}

