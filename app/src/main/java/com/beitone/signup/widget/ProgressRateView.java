package com.beitone.signup.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beitone.signup.R;

import androidx.annotation.Nullable;

public class ProgressRateView extends LinearLayout {

    private String lable = "";

    private ProgressBar progressRate;

    private TextView tvProgressRate;

    public ProgressRateView(Context context) {
        this(context, null);
    }

    public ProgressRateView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressRateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InputLayout);
        lable = array.getString(R.styleable.InputLayout_inputLable);
        array.recycle();
        initView(context);
    }

    private void initView(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_progress_rate,
                this);
        TextView tvRateLable = contentView.findViewById(R.id.tvRateLable);
        tvRateLable.setText(lable);
        progressRate = contentView.findViewById(R.id.progressRate);
        tvProgressRate = contentView.findViewById(R.id.tvProgressRate);
    }

    public void setRate(int rate){
        progressRate.setProgress(rate);
        tvProgressRate.setText(rate+"%");
    }
}
