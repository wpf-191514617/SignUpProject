package com.beitone.signup.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.library.YLCircleImageView;
import com.beitone.face.utils.DeviceUtil;
import com.beitone.face.widget.DensityUtils;
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

import java.lang.reflect.Field;
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
import me.jessyan.autosize.utils.ScreenUtils;

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
        initData();
    }

    private void initData() {
        initBanner();
        loadAppIndexData();
        homePager.setOffscreenPageLimit(2);
        homePager.setAdapter(new HomePagerAdapter(getChildFragmentManager()));
        tabHome.setupWithViewPager(homePager);
        setIndicator(tabHome , 16 ,16);
    }

    @Override
    public void initStatusBar() {
        tvTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        fakeStatusBar.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onRefresh() {
        //initData();
        initBanner();
        loadAppIndexData();
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

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        if (fakeStatusBar != null) {
            fakeStatusBar.setVisibility(View.VISIBLE);
        }
        StateAppBar.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color
                .white));
        //状态栏亮色模式，设置状态栏黑色文字、图标
        //注意：如果是设置白色状态栏，则需要添加下面这句话。如果是设置其他的颜色，则可以不添加，状态栏大都默认是白色字体和图标
        StatusBarUtils.StatusBarLightMode(activity);
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
                if (StringUtil.isUrl(bannerData.getJumpurl())){
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

    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }


    }



    public static void reflex(final TabLayout tabLayout){
        tabLayout.post(() -> {
            try {
                //拿到tabLayout的slidingTabIndicator属性
                Field tabIndicator = tabLayout.getClass().getDeclaredField("slidingTabIndicator");
                //API28以下为mTabStrip
//              Field tabIndicator = tabLayout.getClass().getDeclaredField("mTabStrip");
                tabIndicator.setAccessible(true);
                LinearLayout mTabStrip = (LinearLayout) tabIndicator.get(tabLayout);

                int dp10 = DensityUtils.dip2px(tabLayout.getContext(), 15);

                for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                    View tabView = mTabStrip.getChildAt(i);

                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                    Field mTextViewField = tabView.getClass().getDeclaredField("textView");
                    //API28以下为mTextView
//                  Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                    mTextViewField.setAccessible(true);
                    TextView mTextView = (TextView) mTextViewField.get(tabView);
                    tabView.setPadding(0, 0, 0, 0);

                    //字多宽线就多宽，需要测量mTextView的宽度
                    int width = 0;
                    width = mTextView.getWidth();
                    if (width == 0) {
                        mTextView.measure(0, 0);
                        width = mTextView.getMeasuredWidth();
                    }

                    //设置tab左右间距为10dp 这个间距可根据自己需求更改
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                    params.width = width ;
                    params.leftMargin = dp10;
                    params.rightMargin = dp10;
                    tabView.setLayoutParams(params);
                    tabView.invalidate();
                }

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
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
