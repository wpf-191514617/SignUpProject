package com.beitone.signup.helper;

import com.beitone.signup.SignUpApplication;
import com.beitone.signup.entity.response.UserInfoResponse;

import cn.betatown.mobile.beitonelibrary.util.GsonUtil;
import cn.betatown.mobile.beitonelibrary.util.StringUtil;

public class UserHelper {

    private static UserHelper mInstance = null;

    private final String KEY_USERINFO = "USER.INFO";

    private UserHelper() {
    }

    public static UserHelper getInstance() {
        if (mInstance == null) {
            synchronized (UserHelper.class) {
                if (mInstance == null) {
                    mInstance = new UserHelper();
                }
            }
        }
        return mInstance;
    }


    public void logOut() {
        SignUpApplication.setSession("");
    }


    public void saveCurrentUserInfo(UserInfoResponse infoResponse) {
        CacheHelper.getInstance().putValue(KEY_USERINFO, GsonUtil.GsonString(infoResponse));
    }

    public UserInfoResponse getCurrentInfo() {
        String s = CacheHelper.getInstance().getValueByKey(KEY_USERINFO);
        if (StringUtil.isEmpty(s)) {
            return null;
        }
        return GsonUtil.GsonToBean(s, UserInfoResponse.class);
    }


    public boolean isLogin() {
        if (getCurrentInfo() != null && !StringUtil.isEmpty(SignUpApplication.getSession())) {
            return true;
        }
        return false;
    }





}
