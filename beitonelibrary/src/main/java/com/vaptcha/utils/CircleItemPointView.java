package com.vaptcha.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by zhouguizhijxhz on 2018/5/29.
 */
public class CircleItemPointView extends View {
    private Paint mPaint;
    private int mColor;

    public CircleItemPointView(Context context) {
        this(context, null);
    }

    public CircleItemPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleItemPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        canvas.drawCircle(cx, cy, cx, mPaint);
    }

    /**
     * 切换颜色
     */
    public void changeColor(int color) {
        mColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    public void setColor(int color) {
        mColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 获取当前的颜色
     */
    public int getColor() {
        return mColor;
    }
}