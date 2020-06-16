package com.beitone.signup;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.beitone.signup.base.BaseActivity;

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
    }



}