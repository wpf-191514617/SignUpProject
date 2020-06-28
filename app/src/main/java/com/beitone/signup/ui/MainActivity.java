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

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.widget.MainNavigateTabBar;

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

    }


}