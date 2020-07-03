package com.beitone.signup.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.entity.response.StatisticsResponse;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.helper.WebHelper;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.ui.WebActivity;
import com.beitone.signup.widget.ProgressRateView;

import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class StatisticsFragment extends BaseHomeFragment {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.prvPersonTraining)
    ProgressRateView prvPersonTraining;
    @BindView(R.id.prvPersonSign)
    ProgressRateView prvPersonSign;
    @BindView(R.id.prvProjectTraining)
    ProgressRateView prvProjectTraining;
    @BindView(R.id.prvProjectSign)
    ProgressRateView prvProjectSign;
    @BindView(R.id.layoutPersonStatistics)
    LinearLayout layoutPersonStatistics;
    @BindView(R.id.layoutProjectStatistics)
    LinearLayout layoutProjectStatistics;
    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.tvUserWorkType)
    TextView tvUserWorkType;
    @BindView(R.id.tvProjectName)
    TextView tvProjectName;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.lineTitle)
    View lineTitle;
    @BindView(R.id.layoutStatisticsContent)
    LinearLayout layoutStatisticsContent;
    @BindView(R.id.tvErrorPeopleCount)
    TextView tvErrorPeopleCount;
    @BindView(R.id.tvErrorPeopleRate)
    TextView tvErrorPeopleRate;
    @BindView(R.id.layoutErrorContent)
    LinearLayout layoutErrorContent;

    private StatisticsResponse mStatisticsResponse;


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

    //判断是否展示—与ViewPager连用，进行左右切换
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (activity != null) {
                StateAppBar.setStatusBarColor(activity, ContextCompat.getColor(activity, R.color
                        .white));
                //状态栏亮色模式，设置状态栏黑色文字、图标
                //注意：如果是设置白色状态栏，则需要添加下面这句话。如果是设置其他的颜色，则可以不添加，状态栏大都默认是白色字体和图标
                StatusBarUtils.StatusBarLightMode(activity);
            }
        }//展示
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);

    }

    @Override
    public void initStatusBar() {
        tvTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        fakeStatusBar.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initViewAndData() {
        setText(tvTitle, "统计");
        loadHomeData();
    }

    private void loadHomeData() {
        AppProvider.loadStatisticsAppIndexData(getActivity(),
                new OnJsonCallBack<StatisticsResponse>() {
                    @Override
                    public void onResult(StatisticsResponse data) {
                        if (data != null) {
                            mStatisticsResponse = data;
                            reStoreView();

                            layoutProjectStatistics.setVisibility(View.VISIBLE);
                            if (data.getWorker() != null) {
                                setRate(prvProjectTraining, data.getWorker().getStudy_rate());
                                setRate(prvProjectSign, data.getWorker().getSign_rate());
                            } else {
                                setRate(prvProjectTraining, "0");
                                setRate(prvProjectSign, "0");
                            }


                            layoutPersonStatistics.setVisibility(View.VISIBLE);
                            if (data.getSum_rate() != null) {
                                setRate(prvPersonTraining, data.getSum_rate().getStudy_rate());
                                setRate(prvPersonSign, data.getSum_rate().getSign_rate());
                            } else {
                                setRate(prvPersonTraining, "0");
                                setRate(prvPersonSign, "0");
                            }

                            layoutErrorContent.setVisibility(View.VISIBLE);
                            if (data.getFail_rate() != null){
                                setText(tvErrorPeopleCount , data.getFail_rate().getNum());
                                setText(tvErrorPeopleRate , data.getFail_rate().getRate());
                            } else {
                                setText(tvErrorPeopleCount , "0");
                                setText(tvErrorPeopleRate , "0");
                            }

                            setUserData();
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        showError(msg);
                    }

                    @Override
                    public void onFailed(String msg) {
                        super.onFailed(msg);
                        showError(msg);
                    }
                });
    }

    private void setUserData() {
        UserInfoResponse infoResponse = UserHelper.getInstance().getCurrentInfo();
        setText(tvUserName, infoResponse.getName());
        setText(tvUserWorkType, infoResponse.getType_of_work_name());
        setText(tvProjectName, infoResponse.getB_project_name());

        switch (infoResponse.getType()) {
            case "1":
            case "2":
                layoutPersonStatistics.setVisibility(View.GONE);
                break;

        }

    }


    private void setRate(ProgressRateView progressRateView, String rate) {
        if (StringUtil.isEmpty(rate)) {
            return;
        }
        if (rate.contains("%")) {
            rate = rate.replace("%", "");
        }
        progressRateView.setRate((int) Double.parseDouble(rate));
    }

    @Override
    protected View getLoadingTargetView() {
        return getView().findViewById(R.id.layoutStatisticsContent);
    }

    @Override
    protected void onClickReload() {
        super.onClickReload();
        loadHomeData();
    }

    @OnClick({R.id.layoutProjectStatistics, R.id.layoutErrorContent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutProjectStatistics:
                Bundle bundle = new Bundle();
                if (UserHelper.getInstance().getCurrentInfo().getType().equals("3")) {
                    bundle.putParcelable(WebActivity.KEY_WEB,
                            WebHelper.getItemAnalysisDetail(mStatisticsResponse.getWorker().getB_project_id()));
                } else {
                    bundle.putParcelable(WebActivity.KEY_WEB, WebHelper.getItemAnalysis());
                }
                jumpTo(WebActivity.class, bundle);
                break;
            case R.id.layoutErrorContent:
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable(WebActivity.KEY_WEB, WebHelper.getItemAnalysis2());
                jumpTo(WebActivity.class, bundle1);
                break;
        }
    }
}
