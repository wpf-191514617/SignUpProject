package com.beitone.signup.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beitone.signup.R;


public class HnitDialog extends Dialog {

    private Builder mBuilder;

    private HnitDialog(Context context, Builder builder) {
        super(context, R.style.ActionSheetDialogStyle);
        mBuilder = builder;
        initView(context);
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_appdialog, null);
        setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width =
                context.getResources().getDisplayMetrics().widthPixels -
                        context.getResources().getDimensionPixelSize(R.dimen.dimen_78dp);
        contentView.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.CENTER);
        TextView tvMsg = contentView.findViewById(R.id.tvMessage);
        if (!TextUtils.isEmpty(mBuilder.message)) {
            tvMsg.setText(mBuilder.message);
        }

        if (mBuilder.messageSpan != null && !TextUtils.isEmpty(mBuilder.messageSpan)) {
            tvMsg.setText(mBuilder.messageSpan);
            tvMsg.setMovementMethod(LinkMovementMethod.getInstance());
            tvMsg.setHighlightColor(Color.TRANSPARENT);
        }

        TextView tvNative = contentView.findViewById(R.id.tvNative);
        if (!TextUtils.isEmpty(mBuilder.nativeText)) {
            tvNative.setText(mBuilder.nativeText);
        }

        TextView tvPositive = contentView.findViewById(R.id.tvPositive);
        if (!TextUtils.isEmpty(mBuilder.positiveText)) {
            tvPositive.setText(mBuilder.positiveText);
        }
        if (mBuilder.onNativeListener != null)
            tvNative.setOnClickListener(mBuilder.onNativeListener);
        if (mBuilder.onPositiveListener != null) {
            tvPositive.setOnClickListener(mBuilder.onPositiveListener);
        }
    }

    public static class Builder {

        private Context context;

        private String message;

        private String positiveText;

        private String nativeText;

        private Spannable messageSpan;

        private View.OnClickListener onPositiveListener, onNativeListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder(Context context, String message) {
            this.context = context;
            this.message = message;
        }

        public Builder setTitle(String message) {
            this.message = message;
            return this;
        }

        public Builder setTitle(Spannable message) {
            this.messageSpan = message;
            return this;
        }

        public Builder setPositive(String positiveText, View.OnClickListener onClickListener) {
            this.positiveText = positiveText;
            this.onPositiveListener = onClickListener;
            return this;
        }

        public Builder setNative(String nativeText, View.OnClickListener onClickListener) {
            this.nativeText = nativeText;
            this.onNativeListener = onClickListener;
            return this;
        }

        public HnitDialog build() {
            return new HnitDialog(context, this);
        }
    }

}
