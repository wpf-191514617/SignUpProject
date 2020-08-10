package com.beitone.signup.ui.account;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beitone.face.APIService;
import com.beitone.face.exception.FaceError;
import com.beitone.face.model.RegResult;
import com.beitone.face.utils.ImageSaveUtil;
import com.beitone.face.utils.OnResultListener;
import com.beitone.signup.R;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.entity.request.ImproveInforRequest;
import com.beitone.signup.entity.response.EngineeringResponse;
import com.beitone.signup.entity.response.FaceAuthResponse;
import com.beitone.signup.entity.response.FaceRegisterResponse;
import com.beitone.signup.entity.response.TeamResponse;
import com.beitone.signup.entity.response.UploadFileResponse;
import com.beitone.signup.entity.response.WorkTypeResponse;
import com.beitone.signup.helper.UserHelper;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.provider.AppProvider;
import com.beitone.signup.ui.MainActivity;
import com.beitone.signup.view.AppDialog;
import com.beitone.signup.view.SingleSelectDialog;
import com.beitone.signup.widget.AppButton;
import com.beitone.signup.widget.InputLayout;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

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
import cn.betatown.mobile.beitonelibrary.util.GsonUtil;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class ImproveInformationActivity extends BaseActivity {

    @BindView(R.id.inputName)
    protected InputLayout inputName;
    @BindView(R.id.inputIDCard)
    protected InputLayout inputIDCard;
    @BindView(R.id.inputPhone)
    protected InputLayout inputPhone;
    @BindView(R.id.inputProject)
    protected InputLayout inputProject;
    @BindView(R.id.inputConstructionTeam)
    protected InputLayout inputConstructionTeam;
    @BindView(R.id.inputTypeOfWork)
    protected InputLayout inputTypeOfWork;
    @BindView(R.id.ivIdCard)
    protected ImageView ivIdCard;
    @BindView(R.id.layoutIDCard)
    protected LinearLayout layoutIDCard;
    @BindView(R.id.ivFaceAuth)
    protected ImageView ivFaceAuth;
    @BindView(R.id.layoutFace)
    protected LinearLayout layoutFace;
    @BindView(R.id.btnSubmit)
    protected AppButton btnSubmit;

    private EngineeringResponse mCurrentEngineering;
    private TeamResponse mCurrentTeamResponse;
    private WorkTypeResponse mCurrentWorkTypeResponse;

    private SingleSelectDialog mSelectProjectDialog, mSelectTeamDialog, mSelectWorkTypeDialog;

    private static final int REQUEST_CODE_DETECT_FACE = 1000;

    private String facePath, facePathId;
    private String idCardPath;
    private Bitmap mHeadBmp;
    private String mUserId;
    private String phone;
    private static final int REQUEST_SELECT_IMAGE = 1001;

    private boolean isImprove, fromAudit;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_improve_information;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        super.getBundleExtras(extras);
        mUserId = extras.getString("userId");
        phone = extras.getString("phone");
        isImprove = extras.getBoolean("isImprove", false);
        fromAudit = extras.getBoolean("fromAudit", false);

    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        SignUpApplication.getApplication().initToken();
        SignUpApplication.getApplication().initRegister();
        setTitle("完善个人资料");
        inputProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadProject();
            }
        });
        inputConstructionTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadConstructionTeam();
            }
        });
        inputTypeOfWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadWorkTypeList();
            }
        });
        initUserInfoData();
    }

    protected void initUserInfoData() {
        inputPhone.inputContent(phone);
        inputPhone.setEditble(false);
    }

    protected void loadWorkTypeList() {
        AppProvider.getWorkTypeList(this, new OnJsonCallBack<List<WorkTypeResponse>>() {
            @Override
            public void onResult(List<WorkTypeResponse> data) {
                if (AdapterUtil.isListNotEmpty(data)) {
                    showWorkTypeListDialog(data);
                } else {
                    showToast("未查找到工种");
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }

        });
    }


    protected void loadConstructionTeam() {
        if (mCurrentEngineering == null) {
            showToast("请先选择施工项目");
            return;
        }
        AppProvider.getProjectTeams(this, mCurrentEngineering.getId(),
                new OnJsonCallBack<List<TeamResponse>>() {
                    @Override
                    public void onResult(List<TeamResponse> data) {
                        if (AdapterUtil.isListNotEmpty(data)) {
                            showSelectTeamDialog(data);
                        } else {
                            showToast("当前项目无施工队");
                        }
                    }

                    @Override
                    public void onError(String msg) {
                        super.onError(msg);
                        showToast(msg);
                    }

                    @Override
                    public void onFailed(String msg) {
                        super.onFailed(msg);
                        showToast(msg);
                    }

                });
    }

    protected void loadProject() {
        AppProvider.loadProject(this, new OnJsonCallBack<List<EngineeringResponse>>() {
            @Override
            public void onResult(List<EngineeringResponse> data) {
                if (AdapterUtil.isListNotEmpty(data)) {
                    showSelectProjectDialog(data);
                } else {
                    showToast("当前无施工项目");
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                showToast(msg);
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                showToast(msg);
            }
        });
    }

    private void showSelectTeamDialog(List<TeamResponse> responseList) {
        List<String> data = new ArrayList<>();
        for (TeamResponse response : responseList) {
            data.add(response.getName());
        }
        if (mSelectTeamDialog == null) {
            mSelectTeamDialog = new SingleSelectDialog(this,
                    new SingleSelectDialog.OnSingleSelectListener() {
                        @Override
                        public void onSingleSelect(String text, int position) {
                            if (responseList != null){
                                mCurrentTeamResponse = responseList.get(position);
                                inputConstructionTeam.inputContent(text);
                            }
                        }
                    });
        }
        String name = "";
        if (mCurrentTeamResponse != null) {
            name = mCurrentTeamResponse.getName();
        }
        mSelectTeamDialog.setData(data, name);
        mSelectTeamDialog.show();
    }

    private void showSelectProjectDialog(List<EngineeringResponse> responseList) {
        List<String> data = new ArrayList<>();
        for (EngineeringResponse response : responseList) {
            data.add(response.getName());
        }
        if (mSelectProjectDialog == null) {
            mSelectProjectDialog = new SingleSelectDialog(this,
                    new SingleSelectDialog.OnSingleSelectListener() {
                        @Override
                        public void onSingleSelect(String text, int position) {
                            EngineeringResponse selectEngineering = responseList.get(position);
                            if (mCurrentEngineering != null) {
                                if (!selectEngineering.equals(mCurrentEngineering)) {
                                    mCurrentTeamResponse = null;
                                    inputConstructionTeam.getEtInput().getText().clear();
                                }
                            }
                            mCurrentEngineering = selectEngineering;
                            inputProject.inputContent(text);
                        }
                    });
        }

        String name = "";
        if (mCurrentEngineering != null) {
            name = mCurrentEngineering.getName();
        }
        mSelectProjectDialog.setData(data, name);
        mSelectProjectDialog.show();
    }

    private void showWorkTypeListDialog(List<WorkTypeResponse> responseList) {
        List<String> data = new ArrayList<>();
        for (WorkTypeResponse response : responseList) {
            data.add(response.getMname());
        }
        if (mSelectWorkTypeDialog == null) {
            mSelectWorkTypeDialog = new SingleSelectDialog(this,
                    new SingleSelectDialog.OnSingleSelectListener() {
                        @Override
                        public void onSingleSelect(String text, int position) {
                            mCurrentWorkTypeResponse = responseList.get(position);
                            inputTypeOfWork.inputContent(text);
                        }
                    });
        }

        String name = "";
        if (mCurrentWorkTypeResponse != null) {
            name = mCurrentWorkTypeResponse.getMname();
        }
        mSelectWorkTypeDialog.setData(data, name);
        mSelectWorkTypeDialog.show();
    }

    @OnClick({R.id.layoutIDCard, R.id.layoutFace, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layoutIDCard:
                selectImage();
                break;
            case R.id.layoutFace:
                onVerifyFace();
                break;
            case R.id.btnSubmit:
                if (!inputName.checkInputContent()) {
                    showToast("请输入姓名");
                    return;
                }
                if (!inputIDCard.checkInputContent()) {
                    showToast("请输入正确的身份证号码");
                    return;
                }
                if (!inputPhone.checkInputContent()) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (mCurrentEngineering == null) {
                    showToast("请选择施工项目");
                    return;
                }
                if (mCurrentTeamResponse == null) {
                    showToast("请选择施工队");
                    return;
                }
                if (mCurrentWorkTypeResponse == null) {
                    showToast("请选择工种");
                    return;
                }
                if (StringUtil.isEmpty(idCardPath)) {
                    showToast("请选择身份证照片");
                    return;
                }
                if (StringUtil.isEmpty(facePath)) {
                    showToast("请进行人脸识别");
                    return;
                }

                showLoadingDialog();
                compressImage(idCardPath, true);

                String name = inputName.getText();
                String idCard = inputIDCard.getText();
                String phone = inputPhone.getText();

                break;
        }
    }

    private void commitUserDec(String faceToken) {
        ImproveInforRequest request = new ImproveInforRequest();
        request.name = inputName.getText();
        request.phone = inputPhone.getText();
        request.card_num = inputIDCard.getText();
        request.b_project_id = mCurrentEngineering.getId();
        request.b_project_team_id = mCurrentTeamResponse.getId();
        //request.type_of_work = mCurrentWorkTypeResponse.get
        request.type_of_work = mCurrentWorkTypeResponse.getValue();
        request.card_photo_fileid = idCardPath;
        request.face_photo_fileid = facePathId;
        request.face_token = faceToken;
        AccountProvider.doImproveInfo(this, request, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                // TODO   提交数据    上传百度
                onDismissLoading();
                showSuccessDialog();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                onDismissLoading();
                showToast(msg);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                onDismissLoading();
                showToast(msg);
            }
        });
    }

    private void registerUserToBaidu() {
        APIService.getInstance().reg(new OnResultListener<RegResult>() {
            @Override
            public void onResult(RegResult result) {
                Trace.d("data++");
                if (result != null && !StringUtil.isEmpty(result.getJsonRes())){
                    FaceRegisterResponse response = GsonUtil.GsonToBean(result.getJsonRes() ,
                            FaceRegisterResponse.class);
                    if (response != null&& response.getError_code()==0){
                        if (response.getResult() != null){
                            commitUserDec(response.getResult().getFace_token());
                        }
                    }
                }
                /*if (result.getError_code() != 0) {
                    onDismissLoading();
                    showToast(result.getError_msg());
                    return;
                }
                */
               // if (isImprove) {
                    //UserHelper.getInstance().refreshUserInfo(ImproveInformationActivity.this);
                    //return;
                //}

                /*showToast("操作成功");
                finish();*/
            }

            @Override
            public void onError(FaceError error) {
                onDismissLoading();
                showToast(error.getErrorMessage());
            }
        }, new File(facePath), mUserId, inputPhone.getText());
    }

    private void showSuccessDialog() {
        AppDialog appDialog = new AppDialog(this, "您已注册成功", "确定", AppDialog.DialogType.SUCCESS);
        appDialog.setCanceledOnTouchOutside(false);
        appDialog.setCancelable(false);
        appDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

                EventData<String> eventData = new EventData<String>(EventCode.CODE_REGISTER_SUCCESS,
                        phone);
                EventBus.getDefault().post(eventData);
                if (isImprove) {
                    Bundle bundle = new Bundle();
                    bundle.putString("status", "1");
                    jumpToThenKill(AuditActivity.class, bundle);
                    return;
                }
                finish();
            }
        });
        appDialog.show();
    }


    @Override
    protected boolean isRegisterEventBus() {
        return true;
    }


    @Override
    protected void onEventComming(EventData eventData) {
        super.onEventComming(eventData);
        switch (eventData.CODE) {
            case EventCode
                    .CODE_USERINFO_SUCCESS:
                /*onDismissLoading();
                showToast("操作成功");
                jumpToThenKill(MainActivity.class);*/
                break;
            case EventCode.CODE_USERINFO_FAILED:
                onDismissLoading();
                showToast((String) eventData.data);
                break;
        }
    }

    private void uploadFacePhoto() {
        AccountProvider.uploadFacePhoto(this, facePath, new OnJsonCallBack<UploadFileResponse>() {
            @Override
            public void onResult(UploadFileResponse data) {
                if (data != null && !StringUtil.isEmpty(data.getId())) {
                    facePathId = data.getId();
                    //commitUserDec();
                    registerUserToBaidu();
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


    private void uploadIdCard() {
        AccountProvider.uploadIdCardImage(this, idCardPath,
                new OnJsonCallBack<UploadFileResponse>() {
                    @Override
                    public void onResult(UploadFileResponse data) {
                        if (data != null && !StringUtil.isEmpty(data.getId())) {
                            idCardPath = data.getId();
                            uploadFacePhoto();
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

    protected void selectImage() {
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA).build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                        ImageSelector.builder()
                                .useCamera(true) // 设置是否使用拍照
                                .setSingle(false)  //设置是否单选
                                .setMaxSelectCount(1)
                                .start(ImproveInformationActivity.this, REQUEST_SELECT_IMAGE); //
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        showToast("权限拒绝");
                    }
                });
    }

    protected void onVerifyFace() {
        Acp.getInstance(this).request(new AcpOptions.Builder().setPermissions(Manifest.permission.CAMERA).build(), new AcpListener() {
            @Override
            public void onGranted() {
                jumpToForResult(FaceDetectActivity.class, REQUEST_CODE_DETECT_FACE);
            }

            @Override
            public void onDenied(List<String> permissions) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DETECT_FACE && resultCode == Activity.RESULT_OK) {
            //faceToken = data.getStringExtra("faceId");
            facePath = ImageSaveUtil.loadCameraBitmapPath(this, "head_tmp.jpg");
            if (mHeadBmp != null) {
                mHeadBmp.recycle();
            }
            mHeadBmp = ImageSaveUtil.loadBitmapFromPath(this, facePath);
            if (mHeadBmp != null) {
                ivFaceAuth.setImageBitmap(mHeadBmp);
            }
        } else if (requestCode == REQUEST_SELECT_IMAGE && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> images = data.getStringArrayListExtra(
                        ImageSelector.SELECT_RESULT);
                if (AdapterUtil.isListNotEmpty(images)) {
                    idCardPath = images.get(0);
                    Picasso.get().load(new File(idCardPath)).into(ivIdCard);
                    // Glide.with(this).load(idCardPath).centerCrop().into(ivIdCard);
                }
            }
        }
    }

    private void compressImage(String path, boolean isIdCard) {
        Luban.with(this).load(path)
                .ignoreBy(100)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onSuccess(File file) {
                        if (isIdCard) {
                            idCardPath = file.getPath();
                            compressImage(facePath, false);
                        } else {
                            facePath = file.getPath();
                            uploadIdCard();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        onDismissLoading();
                        showToast("上传数据失败");
                    }
                }).launch();
    }

}
