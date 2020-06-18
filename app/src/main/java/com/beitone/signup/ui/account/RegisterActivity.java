package com.beitone.signup.ui.account;

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

import com.beitone.signup.provider.AccountProvider;
import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.view.CheckingDialog;
import com.beitone.signup.widget.AppButton;
import com.beitone.signup.widget.CountDownButton;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

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
        AccountProvider.doRegister(this, phone1, authCode, password, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                jumpToThenKill(ImproveInformationActivity.class);
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
        AccountProvider.sendSMSCode(this, etAccount.getText().toString(), token,
                new OnJsonCallBack() {
                    @Override
                    public void onResult(Object data) {
                        tvSendAuthCode.start();
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
            if (action.equals("login")) {
                finish();
            } else {

            }
        }
    }

}
