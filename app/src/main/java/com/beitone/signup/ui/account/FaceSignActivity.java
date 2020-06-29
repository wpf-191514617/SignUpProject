package com.beitone.signup.ui.account;

import android.content.DialogInterface;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baidu.aip.face.FaceFilter;
import com.beitone.face.APIService;
import com.beitone.face.exception.FaceError;
import com.beitone.face.model.RegResult;
import com.beitone.face.utils.ImageSaveUtil;
import com.beitone.face.utils.OnResultListener;
import com.beitone.face.utils.PreferencesUtil;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.entity.request.SignRequest;
import com.beitone.signup.entity.response.UploadFileResponse;
import com.beitone.signup.helper.LocationHelper;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.provider.UserProvider;
import com.beitone.signup.view.AppDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class FaceSignActivity extends FaceDetectActivity {

    private FaceFilter.TrackedModel trackedModel;

    private String facePath, baiduFacePath;

    @Override
    protected void onFaceAuthSuccess(FaceFilter.TrackedModel trackedModel) {
        // 人脸识别成功
        this.trackedModel = trackedModel;
        facePath = ImageSaveUtil.loadCameraBitmapPath(this, "head_tmp.jpg");
        baiduFacePath = facePath;
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
                    signUpBaidu(data.getId());
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

    private void signUpBaidu(String fileId) {
        final File file = new File(baiduFacePath);
        if (!file.exists()) {
            Toast.makeText(this, "人脸图片不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        APIService.getInstance().verify(new OnResultListener<RegResult>() {
            @Override
            public void onResult(RegResult result) {
                //   signUp(fileId);

                displayData(result, fileId);
            }

            @Override
            public void onError(FaceError error) {
                error.printStackTrace();
                showToast(error.getErrorMessage());
            }
        }, file, UserHelper.getInstance().getCurrentInfo().getId());
    }


    private void displayData(RegResult result, String fileId) {

        String res = result.getJsonRes();
        double maxScore = 0;
        String userId = "";
        String userInfo = "";
        if (TextUtils.isEmpty(res)) {
            return;
        }
        JSONObject obj = null;
        try {
            obj = new JSONObject(res);
            JSONObject resObj = obj.optJSONObject("result");
            if (resObj != null) {
                JSONArray resArray = resObj.optJSONArray("user_list");
                int size = resArray.length();


                for (int i = 0; i < size; i++) {
                    JSONObject s = (JSONObject) resArray.get(i);
                    if (s != null) {
                        double score = s.getDouble("score");
                        if (score > maxScore) {
                            maxScore = score;
                            userId = s.getString("user_id");
                            userInfo = s.getString("user_info");
                        }

                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        if (maxScore < 90) {
            showToast("人脸校验不通过");
            finish();
            /*resultTv.setText("识别失败");
            scoreTv.setText("人脸识别分数过低：" + maxScore);*/
        } else {
            faceDetectManager.stop();
            signUp(fileId);
            // 分数可以根据安全级别调整，
           /* resultTv.setText("识别成功");
            if (!TextUtils.isEmpty(userInfo)) {
                uidTv.setText(userInfo);
            } else {
                uidTv.setText(userId);
            }

            scoreTv.setText("人脸识别分数:" + maxScore);
            String username = usernameEt.getText().toString().trim();
            PreferencesUtil.putString("username", username);*/
        }
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
                showSuccessDialog();
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


}
