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

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.ivClearAccount)
    ImageView ivClearAccount;
    @BindView(R.id.etAuthCode)
    EditText etAuthCode;
    @BindView(R.id.tvSendAuthCode)
    TextView tvSendAuthCode;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivShowPassword)
    ImageView ivShowPassword;
    @BindView(R.id.btnNext)
    AppButton btnNext;
    @BindView(R.id.tvLogin)
    TextView tvLogin;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("注册");
    }


    @OnClick({R.id.ivClearAccount, R.id.tvSendAuthCode, R.id.ivShowPassword, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClearAccount:
                break;
            case R.id.tvSendAuthCode:
                break;
            case R.id.ivShowPassword:
                break;
            case R.id.btnNext:
                break;
        }
    }
}
