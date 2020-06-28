package com.beitone.signup.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.widget.InputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {
    @BindView(R.id.inputPhone)
    InputLayout inputPhone;
    @BindView(R.id.inputPassword)
    InputLayout inputPassword;
    @BindView(R.id.inputAboutUs)
    InputLayout inputAboutUs;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

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
                jumpTo(AboutUsActivity.class );
            }
        });
    }

    @OnClick(R.id.tvLogout)
    public void onViewClicked() {

    }
}
