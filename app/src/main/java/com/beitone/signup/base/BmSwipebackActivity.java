package com.beitone.signup.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDialog;
import com.beitone.signup.R;
import com.beitone.signup.model.EventData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.base.BaseSwipebackActivity;
import cn.betatown.mobile.beitonelibrary.viewcontroller.callback.BaseView;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BmSwipebackActivity extends BaseSwipebackActivity implements BaseView, CustomAdapt {


    protected Toolbar mToolbar;

    protected TextView tvTilte;

    private LoadingDialog mLoadingDailog;

    protected void showLoadingDialog() {
        if (mLoadingDailog == null) {
            LoadingDialog.Builder loadingBuilder = new LoadingDialog.Builder(this)
                    .setShowMessage(false)
                    .setCancelable(false);
            mLoadingDailog = loadingBuilder.create();
        }
        mLoadingDailog.show();
    }

    protected void onDismissLoading() {
        if (mLoadingDailog != null && mLoadingDailog.isShowing()) {
            mLoadingDailog.dismiss();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*BMStatusBarUtil.transparencyBar(this); //设置状态栏全透明
        BMStatusBarUtil.StatusBarLightMode(this); //设置白底黑字*/

        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        mToolbar = ButterKnife.findById(this, R.id.common_toolbar);
        if (null != mToolbar) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
            mToolbar.setNavigationIcon(R.drawable.ic_back);
            tvTilte = (TextView) findViewById(R.id.tvTitle);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        if (tvTilte != null && title != null && !TextUtils.isEmpty(title.toString())) {
            tvTilte.setText(title);
        }
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
        if (tvTilte != null && titleId != 0) {
            tvTilte.setText(titleId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Override
    public void showLoading() {
        //switchView(getString(R.string.loading), R.drawable.bg_empty, v -> onClickReload());
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showNormal() {
        // switchView(getString(R.string.empty), R.drawable.bg_empty, v -> onClickReload());
    }

    @Subscribe
    public void onEventMainThread(EventData eventData) {
        if (null != eventData) {
            onEventComming(eventData);
        }
    }

    protected void onEventComming(EventData eventData) {
    }

    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public boolean isBaseOnWidth() {
        return false;
    }

    @Override
    public float getSizeInDp() {
        return 667;
    }

    protected void onClickReload() {

    }


    public void setText(TextView tv, String value) {
        if (TextUtils.isEmpty(value)) {
            value = "";
        }
        tv.setText(value);
    }

    public void setText(TextView tv, CharSequence text) {
        if (text == null) {
            text = "";
        }
        tv.setText(text);
    }

    protected abstract int getContentViewLayoutId();

    protected abstract void initViewAndData();
    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return TransitionMode.RIGHT;
    }
}
