package com.beitone.signup.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.beitone.signup.R;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.SessionResponse;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.provider.UserProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.widget.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

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

    private final int REQUEST_IMPROVE = 89;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        if (UserHelper.getInstance().isLogin()){
            jumpToThenKill(MainActivity.class);
            return;
        }
        mToolbar.setNavigationIcon(null);
        setTitle("登录");

    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);
        if (eventData.CODE == EventCode.CODE_REGISTER_SUCCESS){
            String phone = (String) eventData.data;
            setText(etAccount , phone);
            etAccount.setSelection(phone.length());
        }
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
                doLogin(phone, password);
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
        AccountProvider.doLogin(this, phone, password, new OnJsonCallBack<SessionResponse>() {
            @Override
            public void onResult(SessionResponse data) {
                if (data != null) {
                    SignUpApplication.setSession(data.getSessionId());
                    loadUserInfo();
                }
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

    private void loadUserInfo() {
        UserProvider.loadUserInfo(this, new OnJsonCallBack<UserInfoResponse>() {
            @Override
            public void onResult(UserInfoResponse data) {
                if (data != null) {
                    if (!data.getRegist_step().equals("2")){
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isImprove" , true);
                        bundle.putString("phone", data.getPhone());
                        bundle.putString("userId", data.getId());
                        jumpToThenKill(ImproveInformationActivity.class ,bundle);
                        return;
                    }
                    UserHelper.getInstance().saveCurrentUserInfo(data);
                    jumpToThenKill(MainActivity.class);
                }
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }
        });
    }
}
