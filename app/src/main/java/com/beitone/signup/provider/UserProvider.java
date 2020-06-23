package com.beitone.signup.provider;

import android.content.Context;

import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;

public class UserProvider extends BaseProvider {


    public static void loadUserInfo(Context context, OnJsonCallBack onJsonCallBack){
        post(context, "/worker/getWorkerInfo.htm", onJsonCallBack);
    }

}
