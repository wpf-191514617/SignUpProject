package com.beitone.signup.helper;

import com.beitone.signup.SignUpApplication;

public class UserHelper {

    private static UserHelper mInstance = null;

    private UserHelper(){}

    public static UserHelper getInstance(){
        if (mInstance == null){
            synchronized (UserHelper.class){
                if (mInstance == null){
                    mInstance = new UserHelper();
                }
            }
        }
        return mInstance;
    }



    public void logOut(){
        SignUpApplication.setSession("");
    }

}
