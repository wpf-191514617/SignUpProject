package com.beitone.signup.ui.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;
import com.beitone.signup.entity.response.StatisticsResponse;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.widget.ProgressRateView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class StatisticsFragment extends BaseFragment {

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
                    reStoreView();
                    setUserData();
                    layoutProjectStatistics.setVisibility(View.VISIBLE);
                    setRate(prvProjectTraining, data.getWorker().getStudy_rate());
                    setRate(prvProjectSign, data.getWorker().getSign_rate());
                    if (data.getSum_rate() != null) {
                        layoutPersonStatistics.setVisibility(View.VISIBLE);
                        setRate(prvPersonTraining, data.getSum_rate().getStudy_rate());
                        setRate(prvPersonSign, data.getSum_rate().getSign_rate());
                    }
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
        setText(tvUserName , infoResponse.getName());
        setText(tvUserWorkType ,infoResponse.getType_of_work_name());
        setText(tvProjectName , infoResponse.getB_project_name());
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

    @OnClick(R.id.layoutProjectStatistics)
    public void onViewClicked() {

    }
}
