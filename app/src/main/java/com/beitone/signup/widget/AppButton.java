package com.beitone.signup.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;


import com.beitone.signup.R;

import androidx.appcompat.widget.AppCompatButton;


public class AppButton extends AppCompatButton {


    public AppButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AppButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AppButton);
        String btnText = array.getString(R.styleable.AppButton_btnText);
        boolean btnEnable = array.getBoolean(R.styleable.AppButton_btnEnable, true);
        int style = array.getInteger(R.styleable.AppButton_btnStyle, 0);
        array.recycle();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStateListAnimator(null);
        }
       /* switch (style) {
            case 1:
                setBackgroundResource(R.drawable.bg_button_blue);
                break;
            default:*/
                setBackgroundResource(R.drawable.bg_button);
                /*break;
        }*/

        setTextColor(getResources().getColor(R.color.white));
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        if (!TextUtils.isEmpty(btnText)) {
            setText(btnText);
        }
        setSelected(btnEnable);
        setFocusable(btnEnable);
        setClickable(btnEnable);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        setEnabled(selected);
        setFocusable(selected);
        setClickable(selected);
    }


}
