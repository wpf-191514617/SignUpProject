package com.beitone.signup.ui.account;

import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.aip.FaceSDKManager;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.entity.WebEntity;
import com.beitone.signup.entity.response.SessionResponse;
import com.beitone.signup.helper.WebHelper;
import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.ui.WebActivity;
import com.beitone.signup.view.CheckingDialog;
import com.beitone.signup.widget.AppButton;
import com.beitone.signup.widget.CountDownButton;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;
import cn.betatown.mobile.beitonelibrary.util.Trace;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.etAccount)
    EditText etAccount;
    @BindView(R.id.ivClearAccount)
    ImageView ivClearAccount;
    @BindView(R.id.etAuthCode)
    EditText etAuthCode;
    @BindView(R.id.tvSendAuthCode)
    CountDownButton tvSendAuthCode;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.ivShowPassword)
    ImageView ivShowPassword;
    @BindView(R.id.btnNext)
    AppButton btnNext;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.tvAgree)
    TextView tvAgree;
    @BindView(R.id.cbCheckAgree)
    CheckBox cbCheckAgree;

    private CheckingDialog mCheckingDialog;

    @Override
    protected int getContentViewLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initViewAndData() {
        ButterKnife.bind(this);
        setTitle("注册");
        initAgreeText();
        cbCheckAgree.setChecked(true);
        etAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtil.isEmpty(charSequence.toString())) {
                    ivClearAccount.setVisibility(View.INVISIBLE);
                } else {
                    ivClearAccount.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (StringUtil.isEmpty(charSequence.toString())) {
                    ivShowPassword.setVisibility(View.INVISIBLE);
                } else {
                    ivShowPassword.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        initFace();
    }

    private void initFace() {
        // 如果图片中的人脸小于200*200个像素，将不能检测出人脸，可以根据需求在100-400间调节大小
        FaceSDKManager.getInstance().getFaceTracker(this).set_min_face_size(120);
        FaceSDKManager.getInstance().getFaceTracker(this).set_isCheckQuality(true);
        // 该角度为商学，左右，偏头的角度的阀值，大于将无法检测出人脸，为了在1：n的时候分数高，注册尽量使用比较正的人脸，可自行条件角度
        FaceSDKManager.getInstance().getFaceTracker(this).set_eulur_angle_thr(45, 45, 45);
        FaceSDKManager.getInstance().getFaceTracker(this).set_isVerifyLive(true);
    }

    private void initAgreeText() {
        SpannableString spannableString = new SpannableString(getString(R.string.register_login));
        AgreeClickSpan agreeClickSpan = new AgreeClickSpan("login");
        spannableString.setSpan(agreeClickSpan, 5, spannableString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());
        tvLogin.setHighlightColor(getResources().getColor(android.R.color.transparent));
        tvLogin.setText(spannableString);


        spannableString = new SpannableString(getString(R.string.register_agree));
        agreeClickSpan = new AgreeClickSpan("agree1");
        spannableString.setSpan(agreeClickSpan, 4, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        agreeClickSpan = new AgreeClickSpan("agree2");
        spannableString.setSpan(agreeClickSpan, 11, spannableString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvAgree.setMovementMethod(LinkMovementMethod.getInstance());
        tvAgree.setHighlightColor(getResources().getColor(android.R.color.transparent));
        tvAgree.setText(spannableString);
    }


    @OnClick({R.id.ivClearAccount, R.id.tvSendAuthCode, R.id.ivShowPassword, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClearAccount:
                etAccount.getText().clear();
                break;
            case R.id.tvSendAuthCode:
                String phone = etAccount.getText().toString();
                if (!StringUtil.isMobileNO(phone)) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                showCheckingDialog();
                break;
            case R.id.ivShowPassword:
                ivShowPassword.setSelected(!ivShowPassword.isSelected());
                if (ivShowPassword.isSelected()) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                etPassword.setSelection(etPassword.getText().length());
                break;
            case R.id.btnNext:
                String phone1 = etAccount.getText().toString();
                String authCode = etAuthCode.getText().toString();
                String password = etPassword.getText().toString();
                if (!StringUtil.isMobileNO(phone1)) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (StringUtil.isEmpty(authCode)) {
                    showToast("请输入验证码");
                    return;
                }
                if (StringUtil.isEmpty(password)) {
                    showToast("请输入密码");
                    return;
                }


                if (!cbCheckAgree.isChecked()) {
                    showToast("请勾选协议");
                    return;
                }

                register(phone1, authCode, password);
                //jumpToThenKill(ImproveInformationActivity.class);
                break;
        }
    }

    private void register(String phone1, String authCode, String password) {
        AccountProvider.doRegister(this, phone1, authCode, password,
                new OnJsonCallBack<SessionResponse>() {
                    @Override
                    public void onResult(SessionResponse data) {
                        Trace.d("data---" + data);
                        if (data != null) {
                            SignUpApplication.setSession(data.getSessionId());
                            Bundle bundle = new Bundle();
                            bundle.putString("phone", phone1);
                            bundle.putString("userId", data.getId());
                            jumpToThenKill(ImproveInformationActivity.class, bundle);
                        }
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

    private void showCheckingDialog() {
        if (mCheckingDialog == null) {
            mCheckingDialog = new CheckingDialog(this);
            mCheckingDialog.setOnCheckingCallback(new CheckingDialog.OnCheckingCallback() {
                @Override
                public void onCheckingPass(String token) {
                    sendSMSCode(token);
                }

                @Override
                public void onCheckingCancel() {

                }

                @Override
                public void onCheckingFailed(String signal) {

                }
            });
        }
        mCheckingDialog.show();
    }

    private void sendSMSCode(String token) {
        AccountProvider.sendRegisterSMSCode(this, etAccount.getText().toString(), token,
                new OnJsonCallBack() {
                    @Override
                    public void onResult(Object data) {
                        tvSendAuthCode.start();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvSendAuthCode.stopConut();
    }


    class AgreeClickSpan extends ClickableSpan {

        private String action;

        public AgreeClickSpan(String action) {
            this.action = action;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(getResources().getColor(R.color.colorAccent));//设置颜色
            ds.setUnderlineText(false);//去掉下划线
        }

        @Override
        public void onClick(@NonNull View view) {
            switch (action)
            {
                case "agree1":
                    WebEntity webEntity = WebHelper.getUserProtocol();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(WebActivity.KEY_WEB, webEntity);
                    jumpTo(WebActivity.class, bundle);
                    break;
                case "agree2":
                    WebEntity webEntity1 = WebHelper.getPrivacyPolicy();
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelable(WebActivity.KEY_WEB, webEntity1);
                    jumpTo(WebActivity.class, bundle1);
                    break;
                case "login":
                    finish();
                    break;
            }
        }
    }

}
