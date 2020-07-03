package com.beitone.signup.ui.setting;

import com.beitone.signup.SignUpApplication;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.provider.AccountProvider;

import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class ChangePhoneActivity extends CheckUserActivity {

    @Override
    protected void initData() {
        setTitle("手机绑定");
    }

    @Override
    protected void sendSMSCode() {
        String phone = etPhone.getText().toString();
        if (!StringUtil.isMobileNO2(phone)){
            showToast("请输入正确的手机号");
            return;
        }
        AccountProvider.doSendPhoneCodeChangePhoneNew(this, phone, new OnJsonCallBack() {
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

    @Override
    protected void checkAuthCode(String authCode) {
        String phone = etPhone.getText().toString();
        if (!StringUtil.isMobileNO2(phone)){
            showToast("请输入正确的手机号");
            return;
        }
        AccountProvider.doChangePhone(this, phone, authCode, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                UserHelper.getInstance().refreshUserInfo(SignUpApplication.getContext());
                showToast("修改手机号成功");
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
