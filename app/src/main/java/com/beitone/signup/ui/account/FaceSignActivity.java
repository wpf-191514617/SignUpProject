package com.beitone.signup.ui.account;

import com.baidu.aip.face.FaceFilter;
import com.beitone.face.utils.ImageSaveUtil;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.entity.request.SignRequest;
import com.beitone.signup.entity.response.UploadFileResponse;
import com.beitone.signup.helper.LocationHelper;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.provider.UserProvider;

import java.io.File;

import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class FaceSignActivity extends FaceDetectActivity {

    private FaceFilter.TrackedModel trackedModel;

    private String facePath;

    @Override
    protected void onFaceAuthSuccess(FaceFilter.TrackedModel trackedModel) {
        // 人脸识别成功
        this.trackedModel = trackedModel;
        facePath = ImageSaveUtil.loadCameraBitmapPath(this, "head_tmp.jpg");
        compressImage();
    }


    private void compressImage() {
        Luban.with(this).load(facePath)
                .ignoreBy(100)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        facePath = file.getPath();
                        uploadFacePhoto();
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDismissLoading();
                        showToast("上传数据失败");
                    }
                }).launch();
    }


    private void uploadFacePhoto() {
        AccountProvider.uploadFacePhoto(this, facePath, new OnJsonCallBack<UploadFileResponse>() {
            @Override
            public void onResult(UploadFileResponse data) {
                if (data != null && !StringUtil.isEmpty(data.getId())) {
                    signUp(data.getId());
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

    private void signUp(String id) {
        SignRequest request = new SignRequest();
        request.address = LocationHelper.getInstance().getAddress();
        request.fileid = id;
        request.lat = String.valueOf(LocationHelper.getInstance().getLatitude());
        request.lng = String.valueOf(LocationHelper.getInstance().getLongitude());
        request.similarity = String.valueOf(trackedModel.getInfo().mConf);
        UserProvider.onSignUp(this, request, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                UserHelper.getInstance().refreshUserInfo(SignUpApplication.getContext());
                setResult(RESULT_OK);
                finish();
            }
        });
    }


}
