package com.beitone.signup.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.widget.FrameLayout;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.ui.home.HomeFragment;
import com.beitone.signup.ui.home.MineFragment;
import com.beitone.signup.ui.home.StatisticsFragment;
import com.beitone.signup.ui.home.WorkFragment;
import com.donkingliang.imageselector.utils.ImageSelector;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.permission.Acp;
import cn.betatown.mobile.beitonelibrary.permission.AcpListener;
import cn.betatown.mobile.beitonelibrary.permission.AcpOptions;
import cn.betatown.mobile.beitonelibrary.widget.MainNavigateTabBar;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class MainActivity extends HomeActivity {
    private int REQUEST_SELECT_HEAD = 18;

    /*@BindView(R.id.flMainContent)
    FrameLayout flMainContent;
    @BindView(R.id.mainTab)
    MainNavigateTabBar mainTab;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);


        mainTab.addTab(HomeFragment.class,
                new MainNavigateTabBar.TabParam(R.drawable.tab_home_nor,
                        R.drawable.tab_home_sel, "首页"));
        UserInfoResponse infoResponse = UserHelper.getInstance().getCurrentInfo();
        switch (infoResponse.getType()) {
            case "3":
            case "4":
                mainTab.addTab(WorkFragment.class,
                        new MainNavigateTabBar.TabParam(R.drawable.tab_work_nor,
                                R.drawable.tab_work_sel, "工作"));
                break;
        }
        if (!infoResponse.getType().equals("4")) {
            mainTab.addTab(StatisticsFragment.class,
                    new MainNavigateTabBar.TabParam(R.drawable.tab_statistics_nor,
                            R.drawable.tab_statistics_sel, "统计"));
        }
        mainTab.addTab(MineFragment.class,
                new MainNavigateTabBar.TabParam(R.drawable.tab_mine_nor,
                        R.drawable.tab_mine_sel, "我的"));

        mainTab.setTabSelectListener(new MainNavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(MainNavigateTabBar.ViewHolder holder) {
                switch (holder.tabIndex){
                    case 1:
                        switch (infoResponse.getType()) {
                            case "3":
                            case "4":
                                *//*StateAppBar.translucentStatusBar(MainActivity.this,
                                        true);*//*
                                EventBus.getDefault().post(EventCode.CODE_CHANGE_STATUS1);
                                break;
                            default:
                                EventBus.getDefault().post(EventCode.CODE_CHANGE_STATUS0);
                                *//*StateAppBar.setStatusBarColor(MainActivity.this,
                                        ContextCompat.getColor(MainActivity.this,
                                                R.color.white));
                                //状态栏亮色模式，设置状态栏黑色文字、图标
                                StatusBarUtils.StatusBarLightMode(MainActivity.this);*//*
                                break;
                        }
                        break;
                    case 3:
                        StateAppBar.translucentStatusBar(MainActivity.this,
                                true);
                        break;
                    default:
                        StateAppBar.setStatusBarColor(MainActivity.this,
                                ContextCompat.getColor(MainActivity.this,
                                        R.color.white));
                        //状态栏亮色模式，设置状态栏黑色文字、图标
                        StatusBarUtils.StatusBarLightMode(MainActivity.this);
                        break;
                }
            }
        });


    }*/


    public void selectHead(){
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(true)  //设置是否单选
                                .setCrop(true)
                                .start(MainActivity.this, REQUEST_SELECT_HEAD); //
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        showToast("权限拒绝");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_HEAD && resultCode == Activity.RESULT_OK){
            if (data != null) {
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelector.SELECT_RESULT);
                if (AdapterUtil.isListNotEmpty(images)) {
                    EventData<String> eventData = new EventData(EventCode.CODE_CHANGE_USERHEAD ,
                            images.get(0));
                    EventBus.getDefault().post(eventData);
                }
            }
        }
    }
}