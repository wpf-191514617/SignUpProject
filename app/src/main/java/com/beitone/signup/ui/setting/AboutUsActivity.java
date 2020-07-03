package com.beitone.signup.ui.setting;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.AboutUsResponse;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.view.UpdateVersionDialog;
import com.beitone.signup.widget.InputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.AppUtil;

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.inputWechat)
    InputLayout inputWechat;
    @BindView(R.id.inputWeb)
    InputLayout inputWeb;
    @BindView(R.id.inputService)
    InputLayout inputService;
    @BindView(R.id.inputEmail)
    InputLayout inputEmail;
    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.layoutVersion)
    LinearLayout layoutVersion;
    @BindView(R.id.updateHintView)
    View updateHintView;
    @BindView(R.id.tvCopyright)
    TextView tvCopyright;

    private AboutUsResponse.VersionBean mVersionBean;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("关于我们");
        inputWechat.setEditble(false);
        inputWeb.setEditble(false);
        inputService.setEditble(false);
        inputEmail.setEditble(false);
        setText(tvVersion, AppUtil.getVersionName(this));
        AppProvider.loadAboutUs(this, new OnJsonCallBack<AboutUsResponse>() {
            @Override
            public void onResult(AboutUsResponse data) {
                if (data != null) {
                    inputWechat.inputHnit(data.getWeixin());
                    inputWeb.inputHnit(data.getWeb());
                    inputService.inputHnit(data.getPhone());
                    inputEmail.inputHnit(data.getEmail());
                    setText(tvCopyright , data.getCn_copyright() + "\n" + data.getEn_copyright());
                    if (data.getVersion() != null) {
                        mVersionBean = data.getVersion();
                        if (data.getVersion().getVersionCode() == AppUtil.getVersionCode(AboutUsActivity.this)) {
                            updateHintView.setVisibility(View.INVISIBLE);
                        } else {
                            updateHintView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });

    }


    @OnClick(R.id.layoutVersion)
    public void onViewClicked() {
        if (mVersionBean == null){
            return;
        }
        if (mVersionBean.getVersionCode() == AppUtil.getVersionCode(this)) {
            showToast("已经是最新版本了！");
        } else {
            showUpdateDialog(mVersionBean);
        }
    }

    private void showUpdateDialog(AboutUsResponse.VersionBean version) {
        UpdateVersionDialog mUpdateVersionDialog = new UpdateVersionDialog(this);
        mUpdateVersionDialog.setAppUpdateResponse(version);
        mUpdateVersionDialog.show();
    }

}
