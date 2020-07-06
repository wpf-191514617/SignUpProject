package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

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

    private String status;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_audit;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        status = extras.getString("status" , "2");
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        mToolbar.setNavigationIcon(null);
        if (status.equals("1")){
            layoutWait.setVisibility(View.VISIBLE);
            layoutFailed.setVisibility(View.GONE);
        } else {
            layoutWait.setVisibility(View.GONE);
            layoutFailed.setVisibility(View.VISIBLE);
        }
    }


    @OnClick(R.id.btnPerfectInfo)
    public void onViewClicked() {
        jumpToThenKill(ImproveInformationActivity.class);
    }
}
