package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.view.CheckingDialog;
import com.beitone.signup.widget.AppButton;
import com.beitone.signup.widget.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;

public class SendAuthCodeActivity extends BaseActivity {
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.ivClearAccount)
    ImageView ivClearAccount;
    @BindView(R.id.etAuthCode)
    EditText etAuthCode;
    @BindView(R.id.tvSendAuthCode)
    CountDownButton tvSendAuthCode;
    @BindView(R.id.btnNext)
    AppButton btnNext;
    private CheckingDialog mCheckingDialog;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_send_authcode;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("短信验证");
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtil.isEmpty(charSequence.toString())) {
                    ivClearAccount.setVisibility(View.INVISIBLE);
                } else {
                    ivClearAccount.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick({R.id.ivClearAccount, R.id.tvSendAuthCode, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClearAccount:
                etAccount.getText().clear();
                break;
            case R.id.tvSendAuthCode:
                String phone = etAccount.getText().toString();
                if (!StringUtil.isMobileNO(phone)) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                showCheckingDialog();
                break;
            case R.id.btnNext:
                String phone1 = etAccount.getText().toString();
                String authCode = etAuthCode.getText().toString();
                if (!StringUtil.isMobileNO(phone1)) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (StringUtil.isEmpty(authCode)) {
                    showToast("请输入验证码");
                    return;
                }
                checkAuth(phone1, authCode);
                break;
        }
    }

    private void checkAuth(String phone1, String authCode) {
        AccountProvider.doFindPwd1(this, phone1, authCode, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                Bundle bundle = new Bundle();
                bundle.putString("findPhone", phone1);
                jumpToThenKill(ResetPasswordActivity.class, bundle);
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


    private void showCheckingDialog() {
        if (mCheckingDialog == null) {
            mCheckingDialog = new CheckingDialog(this);
            mCheckingDialog.setOnCheckingCallback(new CheckingDialog.OnCheckingCallback() {
                @Override
                public void onCheckingPass(String token) {
                    sendSMSCode(token);
                }

                @Override
                public void onCheckingCancel() {

                }

                @Override
                public void onCheckingFailed(String signal) {

                }
            });
        }
        mCheckingDialog.show();
    }


    private void sendSMSCode(String token) {
        AccountProvider.sendFindPasswordSMSCode(this, etAccount.getText().toString(), token,
                new OnJsonCallBack() {
                    @Override
                    public void onResult(Object data) {
                        tvSendAuthCode.start();
                    }
                });
    }

}
