package com.vaptcha;

/**
 * Created by Administrator on 2018/5/7/007.
 */


public interface vaptchaListener {
    void onError();
    void onExection();
    void onSuccess(String token);
}
