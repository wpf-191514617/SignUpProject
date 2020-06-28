package com.beitone.signup.ui.setting;

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
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class UpdatePasswordActivity extends BaseActivity {
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
        return R.layout.activity_update_password;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("修改密码");
    }


    @OnClick({R.id.ivShowPassword, R.id.ivShowPassword1, R.id.btnCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivShowPassword:
                break;
            case R.id.ivShowPassword1:
                break;
            case R.id.btnCommit:
                String password = etPassword.getText().toString();
                String password1 = etPassword1.getText().toString();
                if (!StringUtil.checkPassword(password)){
                    showToast("请输入不小于6位数的密码");
                    return;
                }

                if (!password.equals(password1)){
                    showToast("俩次输入的密码不一致");
                    return;
                }
                commit(password);
                break;
        }
    }

    private void commit(String password) {

    }
}
