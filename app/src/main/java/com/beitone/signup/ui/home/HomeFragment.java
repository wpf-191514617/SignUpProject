package com.beitone.signup.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beitone.signup.BannerData;
import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;
import com.beitone.signup.entity.response.AppIndexDataResponse;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.util.TestUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.stx.xhb.xbanner.XBanner;

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
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class HomeFragment extends BaseFragment {


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
        initBanner();
        loadAppIndexData();
        homePager.setOffscreenPageLimit(2);
        homePager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        tabHome.setupWithViewPager(homePager);
    }




    //判断是否展示—与ViewPager连用，进行左右切换
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if(activity!=null){
                StateAppBar.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color.white));
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


            }
        });

        homeBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                AppIndexDataResponse.BannerBean bannerData =
                        (AppIndexDataResponse.BannerBean) model;
                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(10);
//通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                RequestOptions options =
                        RequestOptions.bitmapTransform(roundedCorners).override(300, 300);
                Glide.with(HomeFragment.this).load(bannerData.getXBannerUrl()).apply(options).into((ImageView) view);
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
