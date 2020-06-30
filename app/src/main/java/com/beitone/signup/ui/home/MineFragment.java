package com.beitone.signup.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.helper.WebHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.ui.WebActivity;
import com.beitone.signup.ui.account.LoginActivity;
import com.beitone.signup.ui.account.UserInfoActivity;
import com.beitone.signup.ui.setting.FeedbackActivity;
import com.beitone.signup.ui.setting.SettingActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class MineFragment extends BaseHomeFragment {
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
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;

    private View lineSign, lineChangeWorkPoint;

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
        if (isVisibleToUser){
            if(activity!=null){
                StateAppBar.translucentStatusBar(activity, true);
            }
        }//展示
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initStatusBar() {
        fakeStatusBar.setBackgroundColor(Color.parseColor("#00000000"));
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
        switch (userInfoResponse.getType()) {
            case "1":
            case "2":
                layoutSign.setVisibility(View.GONE);
                layoutChangeWorkPoint.setVisibility(View.GONE);
                lineSign.setVisibility(View.GONE);
                lineChangeWorkPoint.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick({R.id.tvUserInformation, R.id.layoutSign, R.id.layoutChangeWorkPoint,
            R.id.layoutFeedback,
            R.id.layoutSetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvUserInformation:
                jumpTo(UserInfoActivity.class);
                break;
            case R.id.layoutSign:
                Bundle bundle = new Bundle();
                bundle.putParcelable(WebActivity.KEY_WEB, WebHelper.getCalendar());
                bundle.putBoolean("isSign", true);
                jumpTo(WebActivity.class, bundle);
                break;
            case R.id.layoutChangeWorkPoint:
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable(WebActivity.KEY_WEB, WebHelper.getProject());
                jumpTo(WebActivity.class, bundle1);
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
