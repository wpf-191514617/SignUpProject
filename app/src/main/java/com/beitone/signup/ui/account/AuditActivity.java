package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.widget.AppButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuditActivity extends BaseActivity {
    @BindView(R.id.layoutWait)
    LinearLayout layoutWait;
    @BindView(R.id.btnPerfectInfo)
    AppButton btnPerfectInfo;
    @BindView(R.id.layoutFailed)
    LinearLayout layoutFailed;
    @BindView(R.id.tvMemo)
    TextView tvMemo;

    private String status, phone, userId, join_audit_memo;
    private boolean isImprove;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_audit;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        status = extras.getString("status", "2");
        isImprove = extras.getBoolean("isImprove", false);
        userId = extras.getString("userId");
        phone = extras.getString("phone");
        join_audit_memo = extras.getString("join_audit_memo");
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        mToolbar.setNavigationIcon(null);
        setTitle("审核");
        if (status.equals("1")) {
            layoutWait.setVisibility(View.VISIBLE);
            layoutFailed.setVisibility(View.GONE);
        } else {
            layoutWait.setVisibility(View.GONE);
            layoutFailed.setVisibility(View.VISIBLE);
            setText(tvMemo , "审核失败原因：" + join_audit_memo);
        }
    }


    @OnClick(R.id.btnPerfectInfo)
    public void onViewClicked() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("fromAudit", true);
        bundle.putString("phone", phone);
        bundle.putString("userId", userId);
        bundle.putBoolean("isImprove", isImprove);
        jumpToThenKill(ImproveInformationActivity.class, bundle);
    }

}
