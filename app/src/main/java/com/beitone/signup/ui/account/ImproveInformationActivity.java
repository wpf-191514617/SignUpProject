package com.beitone.signup.ui.account;

import android.os.Bundle;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.widget.InputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImproveInformationActivity extends BaseActivity {

    @BindView(R.id.inputName)
    InputLayout inputName;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_improve_information;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("完善个人资料");
    }

}
