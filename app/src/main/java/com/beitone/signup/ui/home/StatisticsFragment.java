package com.beitone.signup.ui.home;

import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;
import com.beitone.signup.widget.ProgressRateView;

import butterknife.BindView;

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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_statistics;
    }

    @Override
    protected void initViewAndData() {

    }
}
