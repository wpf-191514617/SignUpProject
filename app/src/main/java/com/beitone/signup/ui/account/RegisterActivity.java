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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.beitone.signup.R;
import com.beitone.signup.base.BaseActivity;
import com.beitone.signup.view.CheckingDialog;
import com.beitone.signup.widget.AppButton;
import com.beitone.signup.widget.CountDownButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
                if (StringUtil.isEmpty(charSequence.toString())){
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
                if (StringUtil.isEmpty(charSequence.toString())){
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
        spannableString.setSpan(agreeClickSpan, 5, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());
        tvLogin.setHighlightColor(getResources().getColor(android.R.color.transparent));
        tvLogin.setText(spannableString);


        spannableString = new SpannableString(getString(R.string.register_agree));
        agreeClickSpan = new AgreeClickSpan("agree1");
        spannableString.setSpan(agreeClickSpan, 4, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);

        agreeClickSpan = new AgreeClickSpan("agree2");
        spannableString.setSpan(agreeClickSpan, 11, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
                showCheckingDialog();
                break;
            case R.id.ivShowPassword:
                ivShowPassword.setSelected(!ivShowPassword.isSelected());
                if (ivShowPassword.isSelected()) {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                etPassword.setSelection(etPassword.getText().length());
                break;
            case R.id.btnNext:
                jumpToThenKill(ImproveInformationActivity.class);
                break;
        }
    }

    private void showCheckingDialog() {
        if (mCheckingDialog == null){
            mCheckingDialog = new CheckingDialog(this);
            mCheckingDialog.setOnCheckingCallback(new CheckingDialog.OnCheckingCallback() {
                @Override
                public void onCheckingPass(String token) {

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
