package com.beitone.signup.ui.home;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.widget.ProgressRateView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.Trace;

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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initViewAndData() {
        setText(tvTitle , "统计");
        loadHomeData();
    }

    private void loadHomeData() {
        AppProvider.loadStatisticsAppIndexData(getActivity(), new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                Trace.d("data");
            }
        });
    }

    @OnClick(R.id.layoutProjectStatistics)
    public void onViewClicked() {

    }
}
