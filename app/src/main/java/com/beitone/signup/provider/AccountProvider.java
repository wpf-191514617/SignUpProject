package com.beitone.signup.provider;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;

public class AccountProvider extends BaseProvider {

    public static void sendSMSCode(Context context, String phone, String token,
                                   OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("token", token);
        map.put("type", "regist");
        post(context, "/worker/doSendPhoneCode.htm", map, onJsonCallBack);
    }


    public static void doRegister(Context context, String phone, String authCode, String password,
                                  OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        map.put("phone_code", authCode);
        post(context, "/worker/doRegist1.htm", map, onJsonCallBack);
    }


    public static void uploadIdCardImage(Context context, String file,
                                         OnJsonCallBack onJsonCallBack) {
        List<File> files = new ArrayList<>();
        files.add(new File(file));
        AppProvider.uploadImage(context, files, "b_worker", "card_photo", onJsonCallBack);
    }


    public static void doImproveInfo(){

    }


    public static void doLogin(Context context, String phone, String password,
                               OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        post(context, "/worker/doLoginOn.htm", map, onJsonCallBack);
    }


}
