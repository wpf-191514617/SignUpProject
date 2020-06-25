package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.widget.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

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

    private String phone;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_reset_password;
    }


    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        phone = extras.getString("findPhone");
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
                String password = etPassword.getText().toString();
                String password1 = etPassword1.getText().toString();
                if (StringUtil.checkPassword(password)
                        && StringUtil.checkPassword(password1)){
                    if (!password.equals(password1)){
                        showToast("俩次输入的密码不一致");
                    } else {
                        resetPassword(password);
                    }
                } else {
                    showToast("请输入至少6位数密码");
                }
                break;
        }
    }

    private void resetPassword(String password) {
        AccountProvider.doFindPwd2(this, phone, password, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                showToast("操作成功");
                finish();
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
