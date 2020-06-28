package com.beitone.signup.helper;

import android.content.Context;
import android.os.Handler;

import com.beitone.signup.SignUpApplication;
import com.beitone.signup.entity.response.UserInfoResponse;
import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;
import com.beitone.signup.provider.UserProvider;
import com.beitone.signup.ui.MainActivity;

import org.greenrobot.eventbus.EventBus;

import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
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
        if (infoResponse == null){
            CacheHelper.getInstance().putValue(KEY_USERINFO , "");
            return;
        }
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

    public void refreshUserInfo(Context context){
        UserProvider.loadUserInfo(context, new OnJsonCallBack<UserInfoResponse>() {
            @Override
            public void onResult(UserInfoResponse data) {
                if (data != null) {
                    UserHelper.getInstance().saveCurrentUserInfo(data);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new EventData(EventCode.CODE_USERINFO_SUCCESS));
                        }
                    }, 100);
                }
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                EventBus.getDefault().post(new EventData(EventCode.CODE_USERINFO_FAILED,msg));
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                EventBus.getDefault().post(new EventData(EventCode.CODE_USERINFO_FAILED,msg));
            }
        });
    }



}
