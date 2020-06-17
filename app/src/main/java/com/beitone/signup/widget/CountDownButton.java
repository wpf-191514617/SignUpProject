package com.beitone.signup.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;

import com.beitone.signup.R;

import androidx.appcompat.widget.AppCompatTextView;


/**
 * 倒计时控件
 */

public class CountDownButton extends AppCompatTextView implements Handler.Callback {

    //设置倒计时的时间
    private long totalTime;
    //记录倒计时的时间
    private long firstTime;
    //默认的时间
    private int conutTime = 60;
    //设置开始的时候的文字
    private String startText;
    //设置倒计时结束的文字
    private String endText;
    //
    private int startBack;
    private int endBack;

    private Handler mHandler;

    private int startColor;
    private int loadColor;

    public CountDownButton(Context context) {
        this(context, null);
    }

    public CountDownButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton,
                defStyleAttr, 0);
        totalTime = array.getInteger(R.styleable.CountDownButton_count_time, conutTime);
        startText =
                array.getString(R.styleable.CountDownButton_start_text) == null || "".equals(array.getString(R.styleable.CountDownButton_start_text)) ? "获取验证码" : array.getString(R.styleable.CountDownButton_start_text);
        endText =
                array.getString(R.styleable.CountDownButton_end_text) == null || "".equals(array.getString(R.styleable.CountDownButton_end_text)) ? "重新获取" : array.getString(R.styleable.CountDownButton_end_text);
        startColor = array.getColor(R.styleable.CountDownButton_start_text_color, Color.RED);
        loadColor = array.getColor(R.styleable.CountDownButton_load_text_color, Color.RED);
        startBack = array.getResourceId(R.styleable.CountDownButton_start_back, -1);
        endBack = array.getResourceId(R.styleable.CountDownButton_load_back, -1);
        array.recycle();
        init(context);

    }

    private void init(Context context) {
        mHandler = new Handler(this);
        firstTime = totalTime;
        setText(startText);
        setTextColor(startColor);
        if (startBack != -1)
            setBackgroundResource(startBack);
    }

    public void start() {
        setClickable(false);
        setTextColor(loadColor);
        if (endBack != -1)
            setBackgroundResource(endBack);
        mHandler.sendEmptyMessage(1);
    }

    public void stopConut() {
        mHandler.removeCallbacksAndMessages(null);
    }

    public CountDownButton setCountTime(long totalTime) {
        this.totalTime = totalTime;
        firstTime = totalTime;
        return this;
    }

    public CountDownButton setStartText(String startText) {
        setText(startText);
        this.startText = startText;
        return this;
    }

    public CountDownButton setEndText(String endText) {
        this.endText = endText;
        return this;
    }

    public void reset() {
        mHandler.sendEmptyMessage(-1);
        /*mHandler = new Handler(this);
        firstTime = totalTime;
        setText(startText);
        setTextColor(startColor);
        setBackgroundResource(startBack);*/
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                totalTime--;
                if (totalTime == 0) {
                    setClickable(true);
                    totalTime = firstTime;
                    setText(endText);
                    setBackgroundResource(startBack);
                    mHandler.sendEmptyMessage(2);
                } else {
                    setText(totalTime + "s后重新发送");
                    mHandler.sendEmptyMessageDelayed(1, 1000);
                }

                break;
            case 2:
                setTextColor(startColor);
                mHandler.removeCallbacksAndMessages(null);
                break;
            case -1:
                firstTime = totalTime;
                setText(startText);
                setTextColor(startColor);
                setBackgroundResource(startBack);
                break;
        }
        return false;
    }
}
