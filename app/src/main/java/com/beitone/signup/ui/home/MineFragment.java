package com.beitone.signup.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.entity.response.UploadFileResponse;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.helper.WebHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.provider.UserProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.ui.WebActivity;
import com.beitone.signup.ui.WebActivity1;
import com.beitone.signup.ui.account.ImproveInformationActivity;
import com.beitone.signup.ui.account.LoginActivity;
import com.beitone.signup.ui.account.UserInfoActivity;
import com.beitone.signup.ui.setting.FeedbackActivity;
import com.beitone.signup.ui.setting.SettingActivity;
import com.beitone.signup.view.HnitDialog;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.permission.Acp;
import cn.betatown.mobile.beitonelibrary.permission.AcpListener;
import cn.betatown.mobile.beitonelibrary.permission.AcpOptions;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;
import de.hdodenhof.circleimageview.CircleImageView;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class MineFragment extends BaseHomeFragment {
    private static final int REQUEST_SELECT_HEAD = 6;
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
    /* @BindView(R.id.fake_status_bar)
     View fakeStatusBar;*/
    @BindView(R.id.civUserHead)
    CircleImageView civUserHead;

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
        if (isVisibleToUser) {
            if (activity != null) {
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
        // fakeStatusBar.setBackgroundColor(Color.parseColor("#00000000"));
    }

    @Override
    public void onRefresh() {
        UserHelper.getInstance().refreshUserInfo(getActivity());
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

        Picasso.get().load(BaseProvider.BaseUrl + userInfoResponse.getHead_photo()).into(civUserHead);
        String line = "";
        switch (userInfoResponse.getType()) {
            case "1":
            case "2":
                layoutSign.setVisibility(View.GONE);
                layoutChangeWorkPoint.setVisibility(View.GONE);
                lineSign.setVisibility(View.GONE);
                lineChangeWorkPoint.setVisibility(View.GONE);
                break;
            default:
                line = " - ";
                break;
        }

        if (userInfoResponse.getStatus().equals("8")){
            showChangeProjectDialog();
        }

        setText(tvUserTeam,
                userInfoResponse.getB_project_name() + line + userInfoResponse.getB_project_team_name());

    }

    private HnitDialog mHnitDialog;

    private void showChangeProjectDialog() {
        if (mHnitDialog == null){
            mHnitDialog = new HnitDialog.Builder(getActivity())
                    .setTitle("当前账户处于挂起状态，您可以切换新工地或退场")
                    .setNative("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mHnitDialog.dismiss();
                        }
                    })
                    .setPositive("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mHnitDialog.dismiss();
                            Bundle bundle1 = new Bundle();
                            bundle1.putParcelable(WebActivity.KEY_WEB, WebHelper.getProject());
                            jumpTo(WebActivity.class, bundle1);
                        }
                    })
                    .build();
        }
        mHnitDialog.show();
    }

    @OnClick({R.id.civUserHead, R.id.tvUserInformation, R.id.layoutSign, R.id.layoutChangeWorkPoint,
            R.id.layoutFeedback, R.id.layoutSetting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.civUserHead:
                // onSelectImageHead();
                activity.selectHead();
                break;
            case R.id.tvUserInformation:
                jumpTo(UserInfoActivity.class);
                break;
            case R.id.layoutSign:
                Bundle bundle = new Bundle();
                bundle.putParcelable(WebActivity.KEY_WEB, WebHelper.getCalendar());
                bundle.putBoolean("isSign", true);
                jumpTo(WebActivity.class, bundle);
                //  jumpTo(WebActivity1.class);
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

    private void commpressImage(String path) {
        showLoadingDialog();
        Luban.with(getActivity()).load(path)
                .ignoreBy(100)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        uploadUserHead(file);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDismissLoading();
                        showToast("上传数据失败");
                    }
                }).launch();
    }

    private void uploadUserHead(File file) {
        AccountProvider.uploadUserHead(getActivity(), file,
                new OnJsonCallBack<UploadFileResponse>() {
                    @Override
                    public void onResult(UploadFileResponse data) {
                        if (data != null && !StringUtil.isEmpty(data.getId())) {
                            updateUserHead(data.getId(), file);
                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        super.onFailed(msg);
                        onDismissLoading();
                        showToast(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        onDismissLoading();
                        showToast(msg);
                    }
                });
    }

    private void updateUserHead(String id, File file) {
        AccountProvider.updateUserHead(getActivity(), id, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                onDismissLoading();
                Picasso.get().load("file://" + file.getPath()).into(civUserHead);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                onDismissLoading();
                showToast(msg);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                onDismissLoading();
                showToast(msg);
            }

        });
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
            case EventCode.CODE_CHANGE_USERHEAD:
                String image = (String) eventData.data;
                commpressImage(image);
                break;
        }
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }


}
