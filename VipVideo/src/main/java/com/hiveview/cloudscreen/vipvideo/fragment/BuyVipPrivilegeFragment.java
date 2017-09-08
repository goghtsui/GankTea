package com.hiveview.cloudscreen.vipvideo.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.hiveview.cloudscreen.vipvideo.R;
import com.hiveview.cloudscreen.vipvideo.activity.adapter.BuyVipPrivilegeInfoAdapter;
import com.hiveview.cloudscreen.vipvideo.service.CloudScreenService;
import com.hiveview.cloudscreen.vipvideo.service.OnRequestResultListener;
import com.hiveview.cloudscreen.vipvideo.service.entity.PrerogativeListEntity;
import com.hiveview.cloudscreen.vipvideo.service.entity.ResultEntity;
import com.hiveview.cloudscreen.vipvideo.service.exception.HiveviewException;
import com.hiveview.cloudscreen.vipvideo.view.wheelview.WheelView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 大麦VIP特权信息
 */
public class BuyVipPrivilegeFragment extends Fragment implements View.OnKeyListener{

    private View view;
    private TextView tv_list_title;
    private ListView list;
    private BuyVipPrivilegeInfoAdapter adapter;
    private List<PrerogativeListEntity> entityList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vip_privilege, container,
                false);
        tv_list_title = (TextView) view.findViewById(R.id.tv_list_title);
        list = (ListView) view.findViewById(R.id.listview_privilege_info);
        list.setFocusable(true);
        list.setFocusableInTouchMode(true);
        list.setOnKeyListener(this);
        list.requestLayout();

        CloudScreenService.getInstance().getPrerogativeList(new PrerogativeListListener(BuyVipPrivilegeFragment.this));

        return view;
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                if (entityList.size() - adapter.index * adapter.VIEW_COUNT <= adapter.VIEW_COUNT) {
                    return false;
                } else {
                    adapter.index++;
                    adapter.notifyDataSetChanged();
                    return true;
                }
            }

            if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_UP) {
                if (adapter.index <= 0) {
                    return false;
                } else {
                    adapter.index--;
                    adapter.notifyDataSetChanged();
                    return true;
                }
            }
        }
        return false;
    }


    private static class PrerogativeListListener implements OnRequestResultListener<ResultEntity> {

        private WeakReference<BuyVipPrivilegeFragment> ref;

        public PrerogativeListListener(BuyVipPrivilegeFragment fragment) {
            ref = new WeakReference<BuyVipPrivilegeFragment>(fragment);
        }

        @Override
        public void onSucess(ResultEntity resultEntity) {
            BuyVipPrivilegeFragment fragment = ref.get();
            if (fragment != null) {
                fragment.entityList = resultEntity.getList();
                if (fragment.entityList != null) {
                    Log.i("fragment", "entityList:" + fragment.entityList.toString());
                    fragment.adapter = new BuyVipPrivilegeInfoAdapter(fragment.getActivity());
                    fragment.adapter.setData(fragment.entityList);
                    fragment.list.setAdapter(fragment.adapter);
                }
            }

        }

        @Override
        public void onFail(Exception e) {
            Log.i("fragment", "onFail");
        }

        @Override
        public void onParseFail(HiveviewException e) {
            Log.i("fragment", "onParseFail");
        }
    }


}
