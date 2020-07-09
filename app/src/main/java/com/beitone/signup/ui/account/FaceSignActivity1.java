package com.beitone.signup.ui.account;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Handler;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.baidu.idl.face.platform.utils.Base64Utils;
import com.beitone.face.APIService;
import com.beitone.face.exception.FaceError;
import com.beitone.face.model.RegResult;
import com.beitone.face.utils.ImageSaveUtil;
import com.beitone.face.utils.ImageUtil;
import com.beitone.face.utils.OnResultListener;
import com.beitone.faceui.FaceLivenessActivity;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.entity.request.SignRequest;
import com.beitone.signup.entity.response.FaceVerifyResponse;
import com.beitone.signup.entity.response.UploadFileResponse;
import com.beitone.signup.helper.LocationHelper;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.provider.UserProvider;
import com.beitone.signup.util.BitmapUtils;
import com.beitone.signup.view.AppDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.GsonUtil;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class FaceSignActivity1 extends FaceLivenessActivity {

    private List<String> headList;

    private int mHeadIndex;
    private String facePath = "";
    private double mSimilarity = 0;

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String,
            String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            //showMessageDialog("活体检测", "检测成功");
            // TODO
            mHeadIndex = 0;
            headList = new ArrayList<>();
            Set<Map.Entry<String, String>> sets = base64ImageMap.entrySet();
            for (Map.Entry<String, String> entry : sets) {
                headList.add(entry.getValue());
            }
            identify();
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            showMessageDialog("采集超时");
        }
    }

    private void identify() {
        if (mHeadIndex == headList.size()) {
            showMessageDialog("人脸校验不通过");
        } else {

            APIService.getInstance().doFaceVerify(new OnResultListener<RegResult>() {
                @Override
                public void onResult(RegResult result) {
                    if (result != null) {
                        String json = result.getJsonRes();
                        FaceVerifyResponse faceVerifyResponse = GsonUtil.GsonToBean(json,
                                FaceVerifyResponse.class);
                        if (faceVerifyResponse != null && faceVerifyResponse.getError_code() == 0) {
                            if (faceVerifyResponse.getResult() != null && faceVerifyResponse.getResult().getScore() > 85) {
                                mSimilarity = faceVerifyResponse.getResult().getScore();
                                saveBitmap(headList.get(mHeadIndex));
                                return;
                            }
                        }
                    }
                    mHeadIndex++;
                    identify();
                }

                @Override
                public void onError(FaceError error) {
                    mHeadIndex++;
                    identify();
                }
            }, headList.get(mHeadIndex));
        }
    }

    private void saveBitmap(String baseImage) {
        final Bitmap face = BitmapUtils.base64ToBitmap(baseImage);
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString() + "", ".jpg");

            ImageUtil.resize(face, file, 200, 200);
            ImageSaveUtil.saveCameraBitmap(FaceSignActivity1.this, face, "head_tmp.jpg");
            File finalFile = file;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    facePath = finalFile.getPath();
                    compressImage();
                }
            }, 1000);
        } catch (IOException e) {
            e.printStackTrace();
            showMessageDialog("采集失败");
        }
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
                    //signUpBaidu(data.getId());
                    signUp(data.getId());
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                onDismissLoading();
                showToast(msg);
                finish();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                onDismissLoading();
                showToast(msg);
                finish();
            }
        });
    }


    private void signUp(String id) {
        SignRequest request = new SignRequest();
        request.address = LocationHelper.getInstance().getAddress();
        request.fileid = id;
        request.lat = String.valueOf(LocationHelper.getInstance().getLatitude());
        request.lng = String.valueOf(LocationHelper.getInstance().getLongitude());
        request.similarity = String.valueOf(mSimilarity);
        UserProvider.onSignUp(this, request, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                UserHelper.getInstance().refreshUserInfo(SignUpApplication.getContext());
                showSuccessDialog();
            }


            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
                finish();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
                finish();
            }

        });
    }

    private void showSuccessDialog() {
        AppDialog appDialog = new AppDialog(this, "打卡成功", "我知道了",
                AppDialog.DialogType.SIGN_SUCCESS);
        appDialog.setCancelable(false);
        appDialog.setCanceledOnTouchOutside(false);
        appDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                setResult(RESULT_OK);
                finish();
            }
        });
        appDialog.show();
    }
















    private void showMessageDialog(String msg) {
        AppDialog appDialog = new AppDialog(this, msg, "确定", AppDialog.DialogType.ERROR);
        appDialog.setCancelable(false);
        appDialog.setCanceledOnTouchOutside(false);
        appDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        appDialog.show();
    }


}
