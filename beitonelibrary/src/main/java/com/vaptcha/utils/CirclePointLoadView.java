package com.vaptcha.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import cn.betatown.mobile.beitonelibrary.R;


/**
 * Created by zhouguizhijxhz on 2018/5/29.
 */
public class CirclePointLoadView extends RelativeLayout {
    private int defaultLeftColor = Color.parseColor("#FF8247");
    private int defaultMiddleColor = Color.parseColor("#EE4000");
    private int defaultRightColor = Color.parseColor("#CD2626");
    private int leftColor;
    private int middleColor;
    private int rightColor;
    private int defaultCircleRadius = 10;
    private int defaultTranslationDistance = 36;
    private int translationDistance;
    private static final long ANIMATION_DURATION = 400;
    private int radius;
    private CircleItemPointView leftView;
    private CircleItemPointView middleView;
    private CircleItemPointView rightView;
    private AnimatorSet spreadAnimation;
    private AnimatorSet closedAnimation;
    public CirclePointLoadView(Context context) {
        this(context,null);
    }
    public CirclePointLoadView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public CirclePointLoadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttrs(context,attrs);
        addViewToLayout(context);
    }
    private void addViewToLayout(Context context) {
        if(null==context){
            return;
        }
        leftView = createView(context);
        leftView.setColor(leftColor);
        middleView = createView(context);
        middleView.setColor(middleColor);
        rightView = createView(context);
        rightView.setColor(rightColor);
        addView(leftView);
        addView(rightView);
        addView(middleView);
    }
    /**
     * 展开动画
     */
    private void spreadAnimation() {
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(leftView,"translationX",0,-translationDistance);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(rightView,"translationX",0,translationDistance);
        spreadAnimation = new AnimatorSet();
        spreadAnimation.setDuration(ANIMATION_DURATION);
        spreadAnimation.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        spreadAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                closedAnimation();
            }
        });
        spreadAnimation.start();
    }

    private void closedAnimation() {
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(leftView,"translationX",-translationDistance,0);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(rightView,"translationX",translationDistance,0);
        closedAnimation = new AnimatorSet();
        closedAnimation.setInterpolator(new AccelerateInterpolator());
        closedAnimation.setDuration(ANIMATION_DURATION);
        closedAnimation.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        closedAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                int leftColor = leftView.getColor();
                int rightColor = rightView.getColor();
                int middleColor = middleView.getColor();
                middleView.changeColor(leftColor);
                rightView.changeColor(middleColor);
                leftView.changeColor(rightColor);
                spreadAnimation();
            }
        });
        closedAnimation.start();
    }
    public CircleItemPointView createView(Context context) {
        CircleItemPointView itemPointView = new CircleItemPointView(context);
        LayoutParams params = new LayoutParams(dp2px(context,radius),dp2px(context,radius));
        params.addRule(CENTER_IN_PARENT);
        itemPointView.setLayoutParams(params);
        return itemPointView;
    }
    private void parseAttrs(Context context, AttributeSet attrs) {
        if(null==context||null==attrs){
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CirclePointLoadView);
        leftColor =  typedArray.getColor(R.styleable.CirclePointLoadView_leftPointColor,defaultLeftColor);
        middleColor =  typedArray.getColor(R.styleable.CirclePointLoadView_middlePointColor,defaultMiddleColor);
        rightColor =  typedArray.getColor(R.styleable.CirclePointLoadView_rightPointColor,defaultRightColor);
        radius = typedArray.getInteger(R.styleable.CirclePointLoadView_radius,defaultCircleRadius);
        translationDistance = (int) typedArray.getDimension(R.styleable.CirclePointLoadView_translationDistance,defaultTranslationDistance);
        translationDistance = dp2px(context,translationDistance);
        typedArray.recycle();
    }
    /**
     * 开启动画
     */
    public void startLoad(){
        if(spreadAnimation==null){
            spreadAnimation();
        }
        leftView.setVisibility(View.VISIBLE);
        middleView.setVisibility(View.VISIBLE);
        rightView.setVisibility(View.VISIBLE);
    }
    /**
     * 停止动画
     */
    public void stopLoad(){
        clearAnimation();
        leftView.setVisibility(View.GONE);
        middleView.setVisibility(View.GONE);
        rightView.setVisibility(View.GONE);
    }

    /**
     * 设置左侧颜色值
     * @param leftColor
     */
    public void setLeftColor(int leftColor) {
        this.leftColor = leftColor;
    }
    /**
     * 设置中间颜色值
     * @param middleColor
     */
    public void setMiddleColor(int middleColor) {
        this.middleColor = middleColor;
    }
    /**
     * 设置右侧颜色值
     * @param rightColor
     */
    public void setRightColor(int rightColor) {
        this.rightColor = rightColor;
    }
    /**
     * 设置圆的半径
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    /**
     * 设置动画x轴方向移动的距离
     * @param translationDistance
     */
    public void setTranslationDistance(int translationDistance) {
        translationDistance = translationDistance;
    }
    private int dp2px(Context context, int dpValue){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.getResources().getDisplayMetrics());
    }

}