package com.beitone.signup.ui;

import android.Manifest;
import android.os.Handler;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.LocationHelper;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.UserProvider;
import com.beitone.signup.ui.account.LoginActivity;

import java.util.List;

import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.permission.Acp;
import cn.betatown.mobile.beitonelibrary.permission.AcpListener;
import cn.betatown.mobile.beitonelibrary.permission.AcpOptions;

public class SplashActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViewAndData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startLocation();
            }
        }, 500);

    }

    private void startLocation() {
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(
                Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        LocationHelper.getInstance().startLocation();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (UserHelper.getInstance().isLogin()) {
                                    refreshUserInfo();
                                } else {
                                    jumpToThenKill(LoginActivity.class);
                                }
                            }
                        }, 1500);
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        //showToast("权限拒绝");
                        finish();
                    }
                });
    }

    private void refreshUserInfo() {
        UserHelper.getInstance().refreshUserInfo(this);
    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);
        switch (eventData.CODE){
            case EventCode
                    .CODE_USERINFO_SUCCESS:
                jumpToThenKill(MainActivity.class);
                break;
            case EventCode.CODE_USERINFO_FAILED:
                showToast((String) eventData.data);
                UserHelper.getInstance().saveCurrentUserInfo(null);
                jumpToThenKill(LoginActivity.class);
                break;
        }
    }
}
