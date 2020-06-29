package com.beitone.signup.ui;

import android.widget.FrameLayout;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.ui.home.HomeFragment;
import com.beitone.signup.ui.home.MineFragment;
import com.beitone.signup.ui.home.StatisticsFragment;
import com.beitone.signup.ui.home.WorkFragment;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.widget.MainNavigateTabBar;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class MainActivity extends BaseActivity {

    @BindView(R.id.flMainContent)
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
        /*StateAppBar.setStatusBarColor(StatusBarFragmentActivity.this,
                ContextCompat.getColor(StatusBarFragmentActivity.this,
                        R.color.white));*/

        StateAppBar.translucentStatusBar(this,true);
        //状态栏亮色模式，设置状态栏黑色文字、图标
        StatusBarUtils.StatusBarLightMode(this);

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

        /*mainTab.setTabSelectListener(new MainNavigateTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(MainNavigateTabBar.ViewHolder holder) {
                switch (holder.tabIndex){
                    case 1:
                        switch (infoResponse.getType()) {
                            case "3":
                            case "4":
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
        });*/


    }


}