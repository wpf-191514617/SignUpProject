package com.beitone.signup.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.WebEntity;
import com.beitone.signup.entity.response.AboutUsResponse;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.helper.UploadHlepr;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.helper.WebHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.ui.home.HomeFragment;
import com.beitone.signup.ui.home.MineFragment;
import com.beitone.signup.ui.home.StatisticsFragment;
import com.beitone.signup.ui.home.WorkFragment;
import com.beitone.signup.ui.setting.AboutUsActivity;
import com.beitone.signup.view.AppDialog;
import com.beitone.signup.view.HnitDialog;
import com.beitone.signup.view.UpdateVersionDialog;
import com.donkingliang.imageselector.utils.ImageSelector;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.permission.Acp;
import cn.betatown.mobile.beitonelibrary.permission.AcpListener;
import cn.betatown.mobile.beitonelibrary.permission.AcpOptions;
import cn.betatown.mobile.beitonelibrary.util.AppUtil;
import cn.betatown.mobile.beitonelibrary.util.PreferencesUtils;
import cn.betatown.mobile.beitonelibrary.widget.MainNavigateTabBar;
import cn.ycbjie.ycstatusbarlib.StatusBarUtils;
import cn.ycbjie.ycstatusbarlib.bar.StateAppBar;

public class MainActivity extends HomeActivity {
    private int REQUEST_SELECT_HEAD = 18;
    private HnitDialog mHomeHnitDialog;

    @Override
    protected void initViewAndData() {
        super.initViewAndData();

        boolean isShow = PreferencesUtils.getBoolean(this, "isFirstShowHnitAgree", false);
        if (!isShow) {
            showHnitDialog();
        } else {
            checkIsUpdate();
        }
    }

    private void showHnitDialog() {
        mHomeHnitDialog = new HnitDialog.Builder(this)
                .setTitle(getMsg())
                .setNative("不同意", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHomeHnitDialog.dismiss();
                        System.exit(0);
                    }
                })
                .setPositive("同意并使用", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHomeHnitDialog.dismiss();
                        PreferencesUtils.putBoolean(MainActivity.this, "isFirstShowHnitAgree", true);
                        checkIsUpdate();
                    }
                })
                .build();

        mHomeHnitDialog.setCancelable(false);
        mHomeHnitDialog.setCanceledOnTouchOutside(false);
        mHomeHnitDialog.show();
    }

    private SpannableString getMsg() {
        SpannableString string =
                new SpannableString(getApplicationContext().getResources().getString(R.string.home_hnit));
        string.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
                ds.setAntiAlias(true);
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View view) {
                WebEntity webEntity = WebHelper.getUserProtocol();
                Bundle bundle = new Bundle();
                bundle.putParcelable(WebActivity.KEY_WEB, webEntity);
                jumpTo(WebActivity.class, bundle);
            }
            // 49，65
        }, 40, 46, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        string.setSpan(new ClickableSpan() {

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getApplicationContext().getResources().getColor(R.color.colorAccent));
                ds.setAntiAlias(true);
                ds.setUnderlineText(false);
            }

            @Override
            public void onClick(View view) {
                WebEntity webEntity = WebHelper.getPrivacyPolicy();
                Bundle bundle = new Bundle();
                bundle.putParcelable(WebActivity.KEY_WEB, webEntity);
                jumpTo(WebActivity.class, bundle);
            }
            // 49，65
        }, 47, 53, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }




    private void checkIsUpdate() {
        AppProvider.loadAboutUs(this, new OnJsonCallBack<AboutUsResponse>() {
            @Override
            public void onResult(AboutUsResponse data) {
                if (data != null) {
                    UploadHlepr.getInstance(MainActivity.this).startUploadLocation(data.getReport_locus_minute());
                    if (data.getVersion() != null) {
                        if (data.getVersion().getVersionCode() > AppUtil.getVersionCode(MainActivity.this)) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showUpdateDialog(data.getVersion());
                                }
                            }, 1500);
                        }
                    }
                }
            }
        });
    }

    private void showUpdateDialog(AboutUsResponse.VersionBean version) {
        UpdateVersionDialog mUpdateVersionDialog = new UpdateVersionDialog(this);
        mUpdateVersionDialog.setAppUpdateResponse(version);
        mUpdateVersionDialog.show();
    }


    public void selectHead(){
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(true)  //设置是否单选
                                .setCrop(true)
                                .start(MainActivity.this, REQUEST_SELECT_HEAD); //
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        showToast("权限拒绝");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SELECT_HEAD && resultCode == Activity.RESULT_OK){
            if (data != null) {
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelector.SELECT_RESULT);
                if (AdapterUtil.isListNotEmpty(images)) {
                    EventData<String> eventData = new EventData(EventCode.CODE_CHANGE_USERHEAD ,
                            images.get(0));
                    EventBus.getDefault().post(eventData);
                }
            }
        }
    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }





    private static final double EARTH_RADIUS = 6371393;

    public static double getDistance(Double lat1,Double lng1,Double lat2,Double lng2) {
        // 经纬度（角度）转弧度。弧度用作参数，以调用Math.cos和Math.sin
        double radiansAX = Math.toRadians(lng1); // A经弧度
        double radiansAY = Math.toRadians(lat1); // A纬弧度
        double radiansBX = Math.toRadians(lng2); // B经弧度
        double radiansBY = Math.toRadians(lat2); // B纬弧度

        // 公式中“cosβ1cosβ2cos（α1-α2）+sinβ1sinβ2”的部分，得到∠AOB的cos值
        double cos = Math.cos(radiansAY) * Math.cos(radiansBY) * Math.cos(radiansAX - radiansBX)
                + Math.sin(radiansAY) * Math.sin(radiansBY);
//        System.out.println("cos = " + cos); // 值域[-1,1]
        double acos = Math.acos(cos); // 反余弦值
//        System.out.println("acos = " + acos); // 值域[0,π]
//        System.out.println("∠AOB = " + Math.toDegrees(acos)); // 球心角 值域[0,180]
        return EARTH_RADIUS * acos; // 最终结果
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UploadHlepr.getInstance(this).onStop();
    }
}