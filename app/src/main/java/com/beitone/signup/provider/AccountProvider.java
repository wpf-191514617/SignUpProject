package com.beitone.signup.provider;

import android.content.Context;

import com.beitone.signup.entity.request.ImproveInforRequest;
import com.beitone.signup.helper.LocationHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.GsonUtil;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class AccountProvider extends BaseProvider {

    public static void sendFindPasswordSMSCode(Context context, String phone, String token,
                                               OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("token", token);
        map.put("type", "findpwd");
        post(context, "/worker/doSendPhoneCodeFindPwd.htm", map, onJsonCallBack);
    }


    public static void sendRegisterSMSCode(Context context, String phone, String token,
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

    public static void updateUserHead(Context context, String fileId,
                                  OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("fileid", fileId);
        post(context, "/worker/doUpdateHeadPhoto.htm", map, onJsonCallBack);
    }



    public static void uploadFeedBackImage(Context context, File file,
                                           OnJsonCallBack onJsonCallBack) {
        List<File> files = new ArrayList<>();
        files.add(file);
        AppProvider.uploadImage(context, files, "b_feedback", "附件图片", onJsonCallBack);
    }

    public static void uploadUserHead(Context context, File file,
                                         OnJsonCallBack onJsonCallBack) {
        List<File> files = new ArrayList<>();
        files.add(file);
        AppProvider.uploadImage(context, files, "b_worker", "head_photo", onJsonCallBack);
    }


    public static void uploadIdCardImage(Context context, String file,
                                         OnJsonCallBack onJsonCallBack) {
        List<File> files = new ArrayList<>();
        files.add(new File(file));
        AppProvider.uploadImage(context, files, "b_worker", "card_photo", onJsonCallBack);
    }

    public static void uploadFacePhoto(Context context, String file,
                                       OnJsonCallBack onJsonCallBack) {
        List<File> files = new ArrayList<>();
        files.add(new File(file));
        AppProvider.uploadImage(context, files, "b_worker", "face_photo", onJsonCallBack);
    }


    public static void doImproveInfo(Context context, ImproveInforRequest request,
                                     OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = GsonUtil.GsonToMaps(GsonUtil.GsonString(request));
        post(context, "/worker/doRegist2.htm", map, onJsonCallBack);
    }


    public static void doLogin(Context context, String phone, String password,
                               OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        post(context, "/worker/doLoginOn.htm", map, onJsonCallBack);
    }


    public static void doFindPwd1(Context context, String phone, String phone_code,
                                  OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("phone_code", phone_code);
        post(context, "/worker/doFindPwd1.htm", map, onJsonCallBack);
    }


    public static void doFindPwd2(Context context, String phone, String password,
                                  OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        post(context, "/worker/doFindPwd2.htm", map, onJsonCallBack);
    }


    public static void onFeedBack(Context context, String content, String file_ids,
                                  OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("content", content);
        if (!StringUtil.isEmpty(file_ids)) {
            map.put("file_ids", file_ids);
        }
        post(context, "/feedback/doSave.htm", map, onJsonCallBack);
    }


    public static void sendUpdateSMSCode(Context context, OnJsonCallBack onJsonCallBack) {
        post(context, "/worker/doSendPhoneCodeChangePhone.htm", onJsonCallBack);
    }

    public static void doSendPhoneCodeResetPwd(Context context, OnJsonCallBack onJsonCallBack) {
        post(context, "/worker/doSendPhoneCodeResetPwd.htm", onJsonCallBack);
    }


    public static void doSendPhoneCodeChangePhoneNew(Context context,
                                                     String phone, OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        post(context, "/worker/doSendPhoneCodeChangePhoneNew.htm", map,onJsonCallBack);
    }

    public static void doChangePhone(Context context,
                                                     String phone,
                                                     String code, OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("code" , code);
        post(context, "/worker/doChangePhone.htm", map,onJsonCallBack);
    }


    public static void checkResetPwdCode(Context context, String code,
                                         OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        post(context, "/worker/checkResetPwdCode.htm", map, onJsonCallBack);
    }

    public static void checkUpdatePhoneCode(Context context, String code,
                                         OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        post(context, "/worker/checkChangePhoneCode.htm", map, onJsonCallBack);
    }


    public static void doResetPwd(Context context, String password, String confirmPwd,
                                  OnJsonCallBack onJsonCallBack) {
        Map<String, String> map = new HashMap<>();
        map.put("password", password);
        map.put("confirmPwd", confirmPwd);
        post(context, "/worker/doResetPwd.htm", map, onJsonCallBack);
    }

    public static void uploadLocation(Context context,OnJsonCallBack onJsonCallBack){
        Map<String, String> map = new HashMap<>();
        map.put("lng", String.valueOf(LocationHelper.getInstance().getLongitude()));
        map.put("lat", String.valueOf(LocationHelper.getInstance().getLatitude()));
        post(context, "/workerLocus/doSave.htm", map, onJsonCallBack);
    }

}
