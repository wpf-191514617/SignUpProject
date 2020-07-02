package com.beitone.signup.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.ui.home.BaseHomeFragment;
import com.beitone.signup.ui.home.HomeFragment;
import com.beitone.signup.ui.home.MineFragment;
import com.beitone.signup.ui.home.StatisticsFragment;
import com.beitone.signup.ui.home.WorkFragment;
import com.beitone.signup.view.HomePager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.util.StatusBarUtil;
import cn.betatown.mobile.beitonelibrary.widget.MainNavigateTabBar;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class HomeActivity extends BaseActivity {

    @BindView(R.id.homePage)
    HomePager homePage;
    @BindView(R.id.ivHome)
    ImageView ivHome;
    @BindView(R.id.tvHome)
    TextView tvHome;
    @BindView(R.id.layoutTabHome)
    RelativeLayout layoutTabHome;
    @BindView(R.id.ivWork)
    ImageView ivWork;
    @BindView(R.id.tvWork)
    TextView tvWork;
    @BindView(R.id.layoutTabWork)
    RelativeLayout layoutTabWork;
    @BindView(R.id.ivStatistics)
    ImageView ivStatistics;
    @BindView(R.id.tvStatistics)
    TextView tvStatistics;
    @BindView(R.id.layoutTabStatistics)
    RelativeLayout layoutTabStatistics;
    @BindView(R.id.ivMine)
    ImageView ivMine;
    @BindView(R.id.tvMine)
    TextView tvMine;
    @BindView(R.id.layoutTabMine)
    RelativeLayout layoutTabMine;

    private List<BaseHomeFragment> mHomePageFragments;

    private UserInfoResponse mUserInfoResponse;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
//        StateAppBar.translucentStatusBar(this,true);
//        //状态栏亮色模式，设置状态栏黑色文字、图标
//        StatusBarUtils.StatusBarLightMode(this);

        StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color
                .white));
        //状态栏亮色模式，设置状态栏黑色文字、图标
        //注意：如果是设置白色状态栏，则需要添加下面这句话。如果是设置其他的颜色，则可以不添加，状态栏大都默认是白色字体和图标
        StatusBarUtils.StatusBarLightMode(this);

        mHomePageFragments = new ArrayList<>();
        mHomePageFragments.add(new HomeFragment());
        mUserInfoResponse= UserHelper.getInstance().getCurrentInfo();
        switch (mUserInfoResponse.getType()) {
            case "3":
            case "4":
                mHomePageFragments.add(new WorkFragment());
                break;
            default:
                layoutTabWork.setVisibility(View.GONE);
                break;
        }
        if (!mUserInfoResponse.getType().equals("4")) {
            mHomePageFragments.add(new StatisticsFragment());
        } else {
            layoutTabStatistics.setVisibility(View.GONE);
        }
        mHomePageFragments.add(new MineFragment());
        homePage.setOffscreenPageLimit(mHomePageFragments.size());
        homePage.setAdapter(new HomePageAdapter(getSupportFragmentManager()));
        homePage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ivHome.setSelected(true);
        tvHome.setSelected(true);
    }

    @OnClick({R.id.layoutTabHome, R.id.layoutTabWork, R.id.layoutTabStatistics, R.id.layoutTabMine})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutTabHome:
                if (ivHome.isSelected()){
                    return;
                }
                ivHome.setSelected(true);
                tvHome.setSelected(true);
                ivWork.setSelected(false);
                tvWork.setSelected(false);
                ivStatistics.setSelected(false);
                tvStatistics.setSelected(false);
                ivMine.setSelected(false);
                tvMine.setSelected(false);
                homePage.setCurrentItem(0);
                break;
            case R.id.layoutTabWork:
                if (ivWork.isSelected()){
                    return;
                }
                ivHome.setSelected(false);
                tvHome.setSelected(false);
                ivWork.setSelected(true);
                tvWork.setSelected(true);
                ivStatistics.setSelected(false);
                tvStatistics.setSelected(false);
                ivMine.setSelected(false);
                tvMine.setSelected(false);
                homePage.setCurrentItem(1);
                break;
            case R.id.layoutTabStatistics:
                if (ivStatistics.isSelected()){
                    return;
                }
                ivHome.setSelected(false);
                tvHome.setSelected(false);
                ivWork.setSelected(false);
                tvWork.setSelected(false);
                ivStatistics.setSelected(true);
                tvStatistics.setSelected(true);
                ivMine.setSelected(false);
                tvMine.setSelected(false);

                switch (mUserInfoResponse.getType()) {
                    case "3":
                    case "4":
                        homePage.setCurrentItem(2);
                        break;
                    default:
                        homePage.setCurrentItem(1);
                        break;
                }

                break;
            case R.id.layoutTabMine:
                if (ivMine.isSelected()){
                    return;
                }
                ivHome.setSelected(false);
                tvHome.setSelected(false);
                ivWork.setSelected(false);
                tvWork.setSelected(false);
                ivStatistics.setSelected(false);
                tvStatistics.setSelected(false);
                ivMine.setSelected(true);
                tvMine.setSelected(true);
                homePage.setCurrentItem(mHomePageFragments.size()-1);
                break;
        }
    }


    class HomePageAdapter extends FragmentPagerAdapter{

        public HomePageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mHomePageFragments.get(position);
        }

        @Override
        public int getCount() {
            return mHomePageFragments.size();
        }
    }

}
