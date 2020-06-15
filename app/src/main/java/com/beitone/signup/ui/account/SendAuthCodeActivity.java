package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.widget.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendAuthCodeActivity extends BaseActivity {
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.ivClearAccount)
    ImageView ivClearAccount;
    @BindView(R.id.etAuthCode)
    EditText etAuthCode;
    @BindView(R.id.tvSendAuthCode)
    TextView tvSendAuthCode;
    @BindView(R.id.btnNext)
    AppButton btnNext;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_send_authcode;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("短信验证");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation

    }

    @OnClick({R.id.ivClearAccount, R.id.tvSendAuthCode, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClearAccount:
                break;
            case R.id.tvSendAuthCode:
                break;
            case R.id.btnNext:
                jumpToThenKill(ResetPasswordActivity.class);
                break;
        }
    }
}
