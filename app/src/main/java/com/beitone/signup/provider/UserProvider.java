package com.beitone.signup.provider;

import android.content.Context;

import com.beitone.signup.entity.request.SignRequest;

import java.util.Map;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.GsonUtil;

public class UserProvider extends BaseProvider {


    public static void loadUserInfo(Context context, OnJsonCallBack onJsonCallBack){
        post(context, "/worker/getWorkerInfo.htm", onJsonCallBack);
    }

    public static void onSignUp(Context context, SignRequest request , OnJsonCallBack onJsonCallBack){
       Map<String , String> map =  GsonUtil.GsonToMaps(GsonUtil.GsonString(request));
       post(context , "/sign/doSign.htm" , map , onJsonCallBack);
    }

    public static void getWorkInfo(Context context, OnJsonCallBack onJsonCallBack){
        post(context, "/worker/getWorkbenchInfo.htm", onJsonCallBack);
    }

}
