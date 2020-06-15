package com.beitone.signup.base;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.tu.loadingdialog.LoadingDialog;
import com.beitone.signup.R;
import com.beitone.signup.model.EventData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.base.BaseAppcomtActivity;
import cn.betatown.mobile.beitonelibrary.viewcontroller.callback.BaseView;
import me.jessyan.autosize.internal.CustomAdapt;

public abstract class BaseActivity extends BaseAppcomtActivity implements BaseView, CustomAdapt {

    protected Toolbar mToolbar;

    protected TextView tvTilte;


    private LoadingDialog mLoadingDailog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow()
                .getDecorView()
                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        MIUISetStatusBarLightMode(this.getWindow(), true);
        FlymeSetStatusBarLightMode(this.getWindow(), true);
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
       /* StateAppBar.setStatusBarColor(this, ContextCompat.getColor(this, R.color.white));
        //状态栏亮色模式，设置状态栏黑色文字、图标
        //注意：如果是设置白色状态栏，则需要添加下面这句话。如果是设置其他的颜色，则可以不添加，状态栏大都默认是白色字体和图标
        StatusBarUtils.StatusBarLightMode(this);*/
    }

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


    public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }


    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        mToolbar = findViewById(R.id.common_toolbar);
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
        switchGifView(getString(R.string.loading), R.drawable.ic_loding, v -> onClickReload());
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
        switchView(getString(R.string.empty), R.drawable.bg_empty, v -> onClickReload());
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
    protected void onStop() {
        super.onStop();
        onDismissLoading();
    }


}
