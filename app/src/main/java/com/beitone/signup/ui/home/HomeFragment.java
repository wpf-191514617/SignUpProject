package com.beitone.signup.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.library.YLCircleImageView;
import com.beitone.signup.R;
import com.beitone.signup.entity.WebEntity;
import com.beitone.signup.entity.response.AppIndexDataResponse;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.ui.WebActivity;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.stx.xhb.xbanner.XBanner;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class HomeFragment extends BaseHomeFragment {


    @BindView(R.id.homeBanner)
    XBanner homeBanner;
    @BindView(R.id.tabHome)
    TabLayout tabHome;
    @BindView(R.id.app_barlayout)
    AppBarLayout appBarlayout;
    @BindView(R.id.homePager)
    ViewPager homePager;
    @BindView(R.id.containerHome)
    CoordinatorLayout containerHome;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.lineTitle)
    View lineTitle;

    private MainActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewAndData() {
        setText(tvTitle, "首页");
        fakeStatusBar.setVisibility(View.GONE);
        initBanner();
        loadAppIndexData();
        homePager.setOffscreenPageLimit(2);
        homePager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        tabHome.setupWithViewPager(homePager);
    }

    @Override
    public void initStatusBar() {
        tvTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        fakeStatusBar.setBackgroundColor(Color.parseColor("#ffffff"));
    }


    //判断是否展示—与ViewPager连用，进行左右切换
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (activity != null) {
                if (fakeStatusBar != null) {
                    fakeStatusBar.setVisibility(View.VISIBLE);
                }
                StateAppBar.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color
                        .white));
                //状态栏亮色模式，设置状态栏黑色文字、图标
                //注意：如果是设置白色状态栏，则需要添加下面这句话。如果是设置其他的颜色，则可以不添加，状态栏大都默认是白色字体和图标
                StatusBarUtils.StatusBarLightMode(activity);
            }
        }//展示
    }


    private void loadAppIndexData() {
        AppProvider.loadAppIndexData(getActivity(), new OnJsonCallBack<AppIndexDataResponse>() {
            @Override
            public void onResult(AppIndexDataResponse data) {
                if (data != null && AdapterUtil.isListNotEmpty(data.getBanner())) {
                    initBannnerData(data.getBanner());
                }
            }
        });
    }

    private void initBannnerData(List<AppIndexDataResponse.BannerBean> banner) {
        homeBanner.setAutoPlayAble(banner.size() > 1);
        homeBanner.setIsClipChildrenMode(true);
        //老方法，不推荐使用
        homeBanner.setBannerData(R.layout.item_banner, banner);
    }

    private void initBanner() {
        homeBanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                AppIndexDataResponse.BannerBean bannerData =
                        (AppIndexDataResponse.BannerBean) model;
                if (!StringUtil.isEmpty(bannerData.getJumpurl())){
                    WebEntity webEntity = new WebEntity();
                    webEntity.head = new HashMap<>();
                    webEntity.url = bannerData.getJumpurl();
                    webEntity.title = bannerData.getTitle();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(WebActivity.KEY_WEB , webEntity);
                    jumpTo(WebActivity.class , bundle);
                }
            }
        });

        homeBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                AppIndexDataResponse.BannerBean bannerData =
                        (AppIndexDataResponse.BannerBean) model;
                //设置图片圆角角度
                Picasso.get().load(BaseProvider.BaseUrl + bannerData.getUrl()).into((YLCircleImageView)view);
            }
        });
    }


    class HomePagerAdapter extends FragmentPagerAdapter {

        private String[] title = {"安全教育", "学习资料"};

        public HomePagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new HomeListFragment(String.valueOf(position + 1));
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }

}
