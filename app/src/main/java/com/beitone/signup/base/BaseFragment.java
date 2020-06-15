package com.beitone.signup.base;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDialog;
import com.beitone.signup.R;
import com.beitone.signup.model.EventData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import androidx.annotation.Nullable;
import cn.betatown.mobile.beitonelibrary.base.BToneFragment;
import cn.betatown.mobile.beitonelibrary.base.BaseApplication;
import cn.betatown.mobile.beitonelibrary.viewcontroller.callback.BaseView;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseFragment extends BToneFragment implements BaseView, CustomAdapt {

    private LoadingDialog mLoadingDailog;

    protected void showLoadingDialog() {
        if (mLoadingDailog == null) {
            LoadingDialog.Builder loadingBuilder = new LoadingDialog.Builder(getActivity())
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
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
    public void showLoading() {
        switchGifView(BaseApplication.getContext().getString(R.string.loading),
                R.drawable.ic_loding,
                v -> onClickReload());
    }

    @Override
    public void showError(String msg) {
        switchView(msg, R.drawable.ic_error, v -> onClickReload());
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void showNormal() {
        switchView(BaseApplication.getContext().getString(R.string.empty), R.drawable.bg_empty, v -> onClickReload());
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


    @Nullable
    @Override
    public Context getContext() {
        return getActivity();
    }
}
