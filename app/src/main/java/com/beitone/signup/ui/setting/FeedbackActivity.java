package com.beitone.signup.ui.setting;

import android.os.Bundle;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.widget.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.btnCommitFeedback)
    AppButton btnCommitFeedback;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("意见反馈");
    }

    @OnClick(R.id.btnCommitFeedback)
    public void onViewClicked() {
    }
}
