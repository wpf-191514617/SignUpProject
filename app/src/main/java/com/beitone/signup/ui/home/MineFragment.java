package com.beitone.signup.ui.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;
import com.beitone.signup.ui.setting.FeedbackActivity;
import com.beitone.signup.ui.setting.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment {
    @BindView(R.id.tvUserInformation)
    TextView tvUserInformation;
    @BindView(R.id.tvUserNickName)
    TextView tvUserNickName;
    @BindView(R.id.tvUserTeam)
    TextView tvUserTeam;
    @BindView(R.id.layoutSign)
    LinearLayout layoutSign;
    @BindView(R.id.layoutChangeWorkPoint)
    LinearLayout layoutChangeWorkPoint;
    @BindView(R.id.layoutFeedback)
    LinearLayout layoutFeedback;
    @BindView(R.id.layoutSetting)
    LinearLayout layoutSetting;

    private View lineSign , lineChangeWorkPoint;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViewAndData() {
        lineSign = getView().findViewById(R.id.lineSign);
        lineChangeWorkPoint = getView().findViewById(R.id.lineChangeWorkPoint);
    }

    @OnClick({R.id.layoutSign, R.id.layoutChangeWorkPoint, R.id.layoutFeedback, R.id.layoutSetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutSign:
                break;
            case R.id.layoutChangeWorkPoint:
                break;
            case R.id.layoutFeedback:
                jumpTo(FeedbackActivity.class);
                break;
            case R.id.layoutSetting:
                jumpTo(SettingActivity.class);
                break;
        }
    }
}
