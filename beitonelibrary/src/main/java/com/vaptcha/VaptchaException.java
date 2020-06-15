package com.vaptcha;

import android.util.Log;

/**
 * Created by tyl
 * 2018/10/29/029
 * Describe:
 */

public class VaptchaException extends NullPointerException {
    /**
     * 自定义异常类
     */
    public VaptchaException(String msg){
        super(msg);
        Log.e("vaptcha",msg);
    }
}
