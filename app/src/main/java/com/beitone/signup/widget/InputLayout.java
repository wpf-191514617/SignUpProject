package com.beitone.signup.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beitone.signup.R;

import androidx.annotation.DrawableRes;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;


public class InputLayout extends LinearLayout {

    private String inputLable;
    private int inputGravity;
    private boolean inputIsSelect;
    private String inputHnit;
    private String inputFooter;
    private int inputType;

    private TextView tvInputLable;
    private TextView tvInputFooter;
    private EditText etInput;
    private ImageView ivNext;

    private char[] numDigts = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
    private char[] idCardDigts = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', 'x', 'X'};
    private char[] licenseDigts = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public InputLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.InputLayout);
        inputLable = array.getString(R.styleable.InputLayout_inputLable);
        inputFooter = array.getString(R.styleable.InputLayout_inputFooter);
        inputGravity = array.getInt(R.styleable.InputLayout_inputGravity, 0);
        inputIsSelect = array.getBoolean(R.styleable.InputLayout_inputSelect, false);
        inputHnit = array.getString(R.styleable.InputLayout_inputHnit);
        inputType = array.getInt(R.styleable.InputLayout_inputType, 0);
        array.recycle();

        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_input, this);
        tvInputLable = contentView.findViewById(R.id.tvInputLable);
        etInput = contentView.findViewById(R.id.etInput);
        ivNext = contentView.findViewById(R.id.ivNext);
        tvInputFooter = contentView.findViewById(R.id.tvInputFooter);
        setText(tvInputLable, inputLable);
        if (inputGravity == 0) {
            etInput.setGravity(Gravity.LEFT);
        } else {
            etInput.setGravity(Gravity.RIGHT);
        }
        if (!StringUtil.isEmpty(inputHnit)) {
            etInput.setHint(inputHnit);
        }
        initInputType();
        if (!StringUtil.isEmpty(inputFooter)) {
            tvInputFooter.setText(inputFooter);
        }

        if (inputIsSelect) {
            /*etInput.setFocusable(false);
            etInput.setEnabled(false);*/
            etInput.setClickable(true);
            etInput.setFocusableInTouchMode(false);
            etInput.requestFocus();
            etInput.requestFocusFromTouch();

            ivNext.setVisibility(View.VISIBLE);
        } else {
            etInput.setFocusable(true);
            etInput.setEnabled(true);
            ivNext.setVisibility(View.GONE);
        }
    }

    private void initInputType() {
        switch (inputType) {
            case 1: //电话
                etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11) {
                }});
                etInput.setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return numDigts;
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_CLASS_PHONE;
                    }
                });
                break;
            case 2://身份证号码
                etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18) {
                }});
                etInput.setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return idCardDigts;
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_NUMBER_FLAG_SIGNED;
                    }
                });
                break;
            case 3: // 营业执照
                etInput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18) {
                }});
                etInput.setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return licenseDigts;
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_NUMBER_FLAG_SIGNED;
                    }
                });
                break;
            case 4: // 验证码
                etInput.setKeyListener(new NumberKeyListener() {
                    @Override
                    protected char[] getAcceptedChars() {
                        return numDigts;
                    }

                    @Override
                    public int getInputType() {
                        return InputType.TYPE_CLASS_NUMBER;
                    }
                });
                break;
            default:
                etInput.setFilters(new InputFilter[]{new EmojiExcludeFilter()});
                break;
        }
    }

    private class EmojiExcludeFilter implements InputFilter
    {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE) {
                    return "";
                }
            }
            return null;

//            for (int i = start; i < end; i++) {
//                int type = Character.getType(source.charAt(i));
//                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
//                    return "";
//                }
//            }
//            String speChat = "[`~!@#$%^&*()+=|{}':;'\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘”“’？]";
//            Pattern pattern = Pattern.compile(speChat);
//            Matcher matcher = pattern.matcher(source.toString());
//            if (matcher.find()) {
//                return "";
//            } else {
//                return null;
//            }
        }
    }



    public void setInputLable(String inputLable) {
        setText(tvInputLable, inputLable);
    }

    public EditText getEtInput() {
        return etInput;
    }

    public void setEditble(boolean isEdit) {
        getEtInput().setEnabled(isEdit);
        getEtInput().setFocusable(isEdit);
    }

    private void setText(TextView textView, String value) {
        if (StringUtil.isEmpty(value)) {
            value = "";
        }

        textView.setText(value);
    }

    public void inputContent(String content) {
        setText(getEtInput(), content);
    }

    public void inputContentNoEdit(String content) {
        setEditble(false);
        setText(getEtInput(), content);
    }

    public String getText() {
        return getEtInput().getText().toString().trim();
    }

    public void setSelResource(@DrawableRes int resourceId) {
        ivNext.setImageResource(resourceId);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        etInput.setOnClickListener(l);
    }

    public boolean checkInputContent() {
        switch (inputType) {
            case 1:
                return StringUtil.isMobileNO(getText());
            case 2:
                return StringUtil.checkIdentification(getText());
            case 3:
                return StringUtil.checkLicenseCode(getText());
            case 4:
                return getText().length() == 4 || getText().length() == 6;
        }
        return !StringUtil.isEmpty(getText());
    }

}
