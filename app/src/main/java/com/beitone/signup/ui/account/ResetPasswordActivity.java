package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.widget.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {

    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivShowPassword)
    ImageView ivShowPassword;
    @BindView(R.id.etPassword1)
    EditText etPassword1;
    @BindView(R.id.ivShowPassword1)
    ImageView ivShowPassword1;
    @BindView(R.id.btnCommit)
    AppButton btnCommit;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("重置密码");
    }


    @OnClick({R.id.ivShowPassword, R.id.btnCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivShowPassword:
                break;
            case R.id.btnCommit:
                break;
        }
    }
}
