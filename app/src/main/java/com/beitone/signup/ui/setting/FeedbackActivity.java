package com.beitone.signup.ui.setting;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.beitone.signup.R;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.response.UploadFileResponse;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.view.AppDialog;
import com.beitone.signup.widget.AppButton;
import com.donkingliang.imageselector.utils.ImageSelector;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.adapter.AdapterUtil;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.permission.Acp;
import cn.betatown.mobile.beitonelibrary.permission.AcpListener;
import cn.betatown.mobile.beitonelibrary.permission.AcpOptions;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.betatown.mobile.beitonelibrary.widget.SelectImageLayout;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.btnCommitFeedback)
    AppButton btnCommitFeedback;
    @BindView(R.id.selImageLayout)
    SelectImageLayout selImageLayout;

    private final int REQUEST_CODE = 10;
    @BindView(R.id.etFeedBackContent)
    EditText etFeedBackContent;

    private int mPhotoIndex = 0;

    private List<String> selImages = new ArrayList<>();

    private List<File> compressImages = new ArrayList<>();

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        ImageSelector.preload(this);
        setTitle("意见反馈");
        selImageLayout.setMargin(getResources().getDimensionPixelSize(R.dimen.dimen_16dp),
                getResources().getDimensionPixelSize(R.dimen.dimen_16dp));
        selImageLayout.initData(SignUpApplication.getContext());
        selImageLayout.setOnSelectImageListener(new SelectImageLayout.OnSelectImageListener() {
            @Override
            public void onSelectImage() {
                checkpermission();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageSelector.clearCache(this);
    }

    private void checkpermission() {
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        ).build(), new AcpListener() {
            @Override
            public void onGranted() {
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(selImageLayout.getMaxSelectCount()) // 图片的最大选择数量，小于等于0
                        // 时，不限数量。
                        .start(FeedbackActivity.this, REQUEST_CODE); // 打开相册
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelector.SELECT_RESULT);
            selImageLayout.addImages(images);
        }
    }

    @OnClick(R.id.btnCommitFeedback)
    public void onViewClicked() {
        String content = etFeedBackContent.getText().toString();
        if (StringUtil.isEmpty(content)) {
            showToast("请填写反馈内容");
            return;
        }
        selImages = selImageLayout.getImageFiles();
        if (AdapterUtil.isListNotEmpty(selImages)) {
            showLoadingDialog();
            mPhotoIndex = 0;
            compressImages = new ArrayList<>();
            compressImage();
        } else {
            showLoadingDialog();
            commitDesc();
        }
    }

    private void compressImage() {
        if (compressImages.size() == selImages.size()) {
            mPhotoIndex = 0;
            selImages.clear();
            selImages = new ArrayList<>();
            uploadPhotos();
        } else {
            Luban.with(this).load(selImages.get(mPhotoIndex))
                    .ignoreBy(100)
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onSuccess(File file) {
                            compressImages.add(file);
                            mPhotoIndex++;
                            compressImage();
                        }

                        @Override
                        public void onError(Throwable e) {
                            onDismissLoading();
                            showToast("上传数据失败");
                        }
                    }).launch();
        }
    }

    private void uploadPhotos() {
        if (selImages.size() == compressImages.size()) {
            commitDesc();
        } else {
            AccountProvider.uploadFeedBackImage(this, compressImages.get(mPhotoIndex),
                    new OnJsonCallBack<UploadFileResponse>() {
                        @Override
                        public void onResult(UploadFileResponse data) {
                            if (data != null && !StringUtil.isEmpty(data.getId())) {
                                mPhotoIndex++;
                                selImages.add(data.getId());
                                uploadPhotos();
                            } else {
                                onDismissLoading();
                                showToast("上传图像失败");
                            }
                        }

                        @Override
                        public void onError(String msg) {
                            super.onError(msg);
                            onDismissLoading();
                            showToast(msg);
                        }

                        @Override
                        public void onFailed(String msg) {
                            super.onFailed(msg);
                            onDismissLoading();
                            showToast(msg);
                        }
                    });
        }
    }

    private void commitDesc() {
        String file_ids = "";
        StringBuffer fileIds = new StringBuffer();
        if (AdapterUtil.isListNotEmpty(selImages)) {
            for (String selImage : selImages) {
                fileIds.append(selImage).append(",");
            }
            file_ids = fileIds.substring(0, fileIds.length() - 1);
        }
        AccountProvider.onFeedBack(this, etFeedBackContent.getText().toString(),
                file_ids, new OnJsonCallBack() {
                    @Override
                    public void onResult(Object data) {
                        onDismissLoading();
                        showSuccess();
                    }

                    @Override
                    public void onFailed(String msg) {
                        super.onFailed(msg);
                        showToast(msg);
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        showToast(msg);
                    }
                });
    }

    private void showSuccess() {
        AppDialog appDialog = new AppDialog(this, "感谢您的反馈", "好的", AppDialog.DialogType.THANKS);
        appDialog.setCancelable(false);
        appDialog.setCanceledOnTouchOutside(false);
        appDialog.show();
        appDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
    }

}
