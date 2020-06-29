package com.beitone.signup.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.beitone.signup.R;
import com.beitone.signup.widget.AppButton;

import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class AppDialog extends Dialog {


    public AppDialog(@NonNull Context context, String message, String btnText) {
        this(context , message , btnText , DialogType.SUCCESS);
    }

    public AppDialog(@NonNull Context context, String message, String btnText, DialogType type) {
        super(context, R.style.ActionSheetDialogStyle);
        initView(context,message,btnText,type);
    }

    private void initView(Context context, String message, String btnText, DialogType type) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_dialog , null);
        setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width =
                context.getResources().getDisplayMetrics().widthPixels -
                        context.getResources().getDimensionPixelSize(R.dimen.dimen_98dp);
        layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.dimen_294dp);
        contentView.setLayoutParams(layoutParams);
        getWindow().setGravity(Gravity.CENTER);
        getWindow().setWindowAnimations(R.style.CenterDialog_Animation);
        TextView tvDialogMessage = contentView.findViewById(R.id.tvDialogMessage);
        ImageView ivDialog = contentView.findViewById(R.id.ivDialog);
        AppButton appButton = contentView.findViewById(R.id.btnDialog);
        setText(tvDialogMessage , message);
        setText(appButton , btnText);
        switch (type){
            case WAIT:
                ivDialog.setImageResource(R.drawable.ic_waiting);
                break;
            case SUCCESS:
                ivDialog.setImageResource(R.drawable.ic_success);
                break;
            case THANKS:
                ivDialog.setImageResource(R.drawable.ic_thanks);
                break;
            case SIGN_SUCCESS:
                ivDialog.setImageResource(R.drawable.sign_success);
                break;
            case ERROR:
                break;
        }
        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


    private void setText(TextView textView , String text){
        if (StringUtil.isEmpty(text)){
            text = "";
        }
        textView.setText(text);
    }


    public enum DialogType {
        SUCCESS, ERROR, WAIT,THANKS,SIGN_SUCCESS
    }

}
