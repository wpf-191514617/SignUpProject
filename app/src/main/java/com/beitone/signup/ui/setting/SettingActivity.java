package com.beitone.signup.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.WebEntity;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.helper.WebHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.ui.WebActivity;
import com.beitone.signup.ui.account.LoginActivity;
import com.beitone.signup.view.HnitDialog;
import com.beitone.signup.widget.InputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.util.BaseAppManager;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.inputPhone)
    InputLayout inputPhone;
    @BindView(R.id.inputPassword)
    InputLayout inputPassword;
    @BindView(R.id.inputAboutUs)
    InputLayout inputAboutUs;
    @BindView(R.id.tvLogout)
    TextView tvLogout;
    @BindView(R.id.inputUserProtocol)
    InputLayout inputUserProtocol;
    @BindView(R.id.inputPrivacyPolicy)
    InputLayout inputPrivacyPolicy;

    private HnitDialog mHnitDialog;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("设置");
        inputAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpTo(AboutUsActivity.class);
            }
        });
        inputPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpTo(CheckUserActivity.class);
            }
        });
       refreshUserData();

        inputPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isUpdatePhone" , true);
                jumpTo(CheckUserActivity.class , bundle);
            }
        });

        inputUserProtocol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebEntity webEntity = WebHelper.getUserProtocol();
                Bundle bundle = new Bundle();
                bundle.putParcelable(WebActivity.KEY_WEB , webEntity);
                jumpTo(WebActivity.class , bundle);
            }
        });

        inputPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebEntity webEntity = WebHelper.getPrivacyPolicy();
                Bundle bundle = new Bundle();
                bundle.putParcelable(WebActivity.KEY_WEB , webEntity);
                jumpTo(WebActivity.class , bundle);
            }
        });

    }

    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }

    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);
        switch (eventData.CODE) {
            case EventCode
                    .CODE_USERINFO_SUCCESS:
                refreshUserData();
                break;
        }
    }

    private void refreshUserData() {
        String phone = UserHelper.getInstance().getCurrentInfo().getPhone();
        inputPhone.inputHnit(StringUtil.mobileEncrypt(phone));
    }

    @OnClick(R.id.tvLogout)
    public void onViewClicked() {
        if (mHnitDialog == null) {
            mHnitDialog = new HnitDialog.Builder(this)
                    .setTitle("是否退出当前账户？")
                    .setNative("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mHnitDialog.dismiss();
                        }
                    })
                    .setPositive("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mHnitDialog.dismiss();
                            UserHelper.getInstance().logOut();
                            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //将DengLuActivity至于栈顶
                            startActivity(intent);
                            BaseAppManager.getInstance().clear();
                        }
                    })
                    .build();
        }
        mHnitDialog.show();
    }

}
