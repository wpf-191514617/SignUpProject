package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.widget.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.ivClearAccount)
    ImageView ivClearAccount;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivShowPassword)
    ImageView ivShowPassword;
    @BindView(R.id.btnLogin)
    AppButton btnLogin;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.tvForgetPassword)
    TextView tvForgetPassword;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        mToolbar.setNavigationIcon(null);
        setTitle("登录");
    }

    @OnClick({R.id.ivClearAccount, R.id.ivShowPassword, R.id.btnLogin, R.id.tvRegister,
            R.id.tvForgetPassword})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClearAccount:
                etAccount.getText().clear();
                break;
            case R.id.ivShowPassword:
                break;
            case R.id.btnLogin:
                //jumpToThenKill(MainActivity.class);
                String phone = etAccount.getText().toString();
                String password = etPassword.getText().toString();
                if (!StringUtil.isMobileNO(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                if (StringUtil.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }
                doLogin(phone , password);
                break;
            case R.id.tvRegister:
                jumpTo(RegisterActivity.class);
                break;
            case R.id.tvForgetPassword:
                jumpTo(SendAuthCodeActivity.class);
                break;
        }
    }

    private void doLogin(String phone, String password) {
        AccountProvider.doLogin(this, phone, password, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                jumpToThenKill(MainActivity.class);
            }
        });
    }
}
