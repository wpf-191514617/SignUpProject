package com.beitone.signup.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.widget.AppButton;
import com.beitone.signup.widget.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class CheckUserActivity extends BaseActivity {
    @BindView(R.id.etPhone)
    protected EditText etPhone;
    @BindView(R.id.ivShowPassword)
    protected ImageView ivShowPassword;
    @BindView(R.id.etAuthCode)
    protected EditText etAuthCode;
    @BindView(R.id.tvSendAuthCode)
    protected CountDownButton tvSendAuthCode;
    @BindView(R.id.btnNext)
    protected AppButton btnNext;

    private boolean isUpdatePhone;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_check_user;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        isUpdatePhone = extras.getBoolean("isUpdatePhone", false);
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        initData();
    }

    protected void initData() {
        setTitle("身份验证");
        etPhone.setEnabled(false);
        etPhone.setFocusable(false);
        UserInfoResponse infoResponse = UserHelper.getInstance().getCurrentInfo();
        setText(etPhone, StringUtil.mobileEncrypt(infoResponse.getPhone()));
        etPhone.setHint(StringUtil.mobileEncrypt(infoResponse.getPhone()));
    }

    @OnClick({R.id.tvSendAuthCode, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvSendAuthCode:
                if (isUpdatePhone) {
                    sendUpdateSMSCode();
                } else {
                    sendSMSCode();
                }
                break;
            case R.id.btnNext:
                String authCode = etAuthCode.getText().toString();
                if (StringUtil.isEmpty(authCode)) {
                    showToast("请输入验证码");
                    return;
                }
                if (!isUpdatePhone) {
                    checkAuthCode(authCode);
                } else {
                    checkUpdateAuthCode(authCode);
                }
                break;
        }
    }

    private void checkUpdateAuthCode(String authCode) {
        AccountProvider.checkUpdatePhoneCode(this, authCode, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                jumpToThenKill(ChangePhoneActivity.class);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }

        });
    }

    private void sendUpdateSMSCode() {
        AccountProvider.sendUpdateSMSCode(this, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                tvSendAuthCode.start();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }
        });
    }

    protected void checkAuthCode(String authCode) {
        AccountProvider.checkResetPwdCode(this, authCode, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                jumpToThenKill(UpdatePasswordActivity.class);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvSendAuthCode.stopConut();
    }

    protected void sendSMSCode() {
        AccountProvider.doSendPhoneCodeResetPwd(this, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                tvSendAuthCode.start();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }
        });
    }
}
