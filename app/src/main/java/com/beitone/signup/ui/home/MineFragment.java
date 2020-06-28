package com.beitone.signup.ui.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseFragment;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.ui.account.LoginActivity;
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

    private View lineSign, lineChangeWorkPoint;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViewAndData() {
        lineSign = getView().findViewById(R.id.lineSign);
        lineChangeWorkPoint = getView().findViewById(R.id.lineChangeWorkPoint);
        refreshUserData();
    }

    private void refreshUserData() {
        UserInfoResponse userInfoResponse = UserHelper.getInstance().getCurrentInfo();
        setText(tvUserNickName, userInfoResponse.getName());
        setText(tvUserTeam,
                userInfoResponse.getB_project_name() + " - " + userInfoResponse.getB_project_team_name());
        switch (userInfoResponse.getType()){
            case "1":
            case "2":
                layoutSign.setVisibility(View.GONE);
                layoutChangeWorkPoint.setVisibility(View.GONE);
                lineSign.setVisibility(View.GONE);
                lineChangeWorkPoint.setVisibility(View.GONE);
                break;
        }
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

    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);
        switch (eventData.CODE) {
            case EventCode
                    .CODE_USERINFO_SUCCESS:
                refreshUserData();
                break;
            case EventCode.CODE_USERINFO_FAILED:
                showToast((String) eventData.data);
                UserHelper.getInstance().saveCurrentUserInfo(null);
                jumpToThenKill(LoginActivity.class);
                break;
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }
}
