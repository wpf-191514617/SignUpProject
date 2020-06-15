package cn.betatown.mobile.beitonelibrary.base;

import android.app.Application;
import android.content.Context;

import cn.betatown.mobile.beitonelibrary.util.PreferencesUtils;

public class BaseApplication extends Application {


    private static Context context;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static String getSession() {
        return PreferencesUtils.getString(getContext(), "btSession");
    }

    public static void setSession(String session) {
        PreferencesUtils.putString(getContext(), "btSession", session);
    }
}
