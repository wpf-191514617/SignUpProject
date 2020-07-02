package com.beitone.signup.view;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.beitone.signup.R;
import com.beitone.signup.entity.response.AboutUsResponse;
import com.beitone.signup.update.UpdateAppHttpUtil;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.service.DownloadService;
import com.vector.update_app.utils.AppUpdateUtils;

import java.io.File;
import java.util.List;

import cn.betatown.mobile.beitonelibrary.permission.Acp;
import cn.betatown.mobile.beitonelibrary.permission.AcpListener;
import cn.betatown.mobile.beitonelibrary.permission.AcpOptions;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class UpdateVersionDialog extends Dialog {

    TextView tvVersionName;
    TextView tvUpdateContent;
    TextView btnUpdate;
    LinearLayout layoutClose;
    ProgressBar progress;
    LinearLayout layoutProgress;

    private AboutUsResponse.VersionBean mAppUpdateResponse;

    public UpdateVersionDialog(Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        init(context);
    }

    private void init(Context context) {
        View contentView =
                LayoutInflater.from(context).inflate(R.layout.layout_version_dialog, null);
        setContentView(contentView);
        tvVersionName = contentView.findViewById(R.id.tvVersionName);
        tvUpdateContent = contentView.findViewById(R.id.tvUpdateContent);
        btnUpdate = contentView.findViewById(R.id.btnUpdate);
        btnUpdate.setSelected(true);
        layoutClose = contentView.findViewById(R.id.layoutClose);
        progress = contentView.findViewById(R.id.progress);
        layoutProgress = contentView.findViewById(R.id.layoutProgress);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Acp.getInstance(getContext()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE).build(),
                        new AcpListener() {
                            @Override
                            public void onGranted() {
                                downloadAPK();
                            }

                            @Override
                            public void onDenied(List<String> permissions) {

                            }
                        });
            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getWindow().setGravity(Gravity.CENTER);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width =
                context.getResources().getDisplayMetrics().widthPixels - context.getResources().getDimensionPixelSize(R.dimen.dimen_78dp);
        layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.dimen_355dp);
        contentView.setLayoutParams(layoutParams);
        //getWindow().setWindowAnimations(R.style.CenterDialog_Animation);
    }

    public void setAppUpdateResponse(AboutUsResponse.VersionBean appUpdateResponse) {
        this.mAppUpdateResponse = appUpdateResponse;
        initData();
    }

    private void initData() {
        if (mAppUpdateResponse.getIsUpgrade() != null && mAppUpdateResponse.getIsUpgrade().equals(
                "1")) {
            layoutClose.setVisibility(View.GONE);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        } else {
            layoutClose.setVisibility(View.VISIBLE);
            setCancelable(true);
            setCanceledOnTouchOutside(true);
        }
        setText(tvVersionName, mAppUpdateResponse.getVersionName());
        setText(tvUpdateContent, mAppUpdateResponse.getUpdateDesc());
    }

    private void downloadAPK() {
        UpdateAppBean updateAppBean = new UpdateAppBean();
        //设置 apk 的下载地址
        updateAppBean.setApkFileUrl(mAppUpdateResponse.getUpdateUrl());

        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {
            try {
                path = getContext().getExternalCacheDir().getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        } else {
            path = getContext().getCacheDir().getAbsolutePath();
        }

        //设置apk 的保存路径
        updateAppBean.setTargetPath(path);
        //实现网络接口，只实现下载就可以
        updateAppBean.setHttpManager(new UpdateAppHttpUtil());
        UpdateAppManager.download(getContext(), updateAppBean,
                new DownloadService.DownloadCallback() {
                    @Override
                    public void onStart() {
                        layoutProgress.setVisibility(View.VISIBLE);
                        btnUpdate.setVisibility(View.GONE);
                    }

                    @Override
                    public void onProgress(float gress, long totalSize) {
                        progress.setProgress(Math.round(gress * 100));
                    }

                    @Override
                    public void setMax(long totalSize) {

                    }

                    @Override
                    public boolean onFinish(File file) {
                        if (isShowing() && !mAppUpdateResponse.getIsUpgrade().equals("1")) {
                            dismiss();
                        }
                        return true;
                    }

                    @Override
                    public void onError(String msg) {
                        btnUpdate.setVisibility(View.VISIBLE);
                        layoutProgress.setVisibility(View.GONE);
                    }

                    @Override
                    public boolean onInstallAppAndAppOnForeground(File file) {
                        AppUpdateUtils.installApp(getContext(), file);
                        return false;
                    }
                });
    }


    private void setText(TextView textView, String value) {
        if (StringUtil.isEmpty(value)) {
            value = "";
        }
        textView.setText(value);
    }

}
