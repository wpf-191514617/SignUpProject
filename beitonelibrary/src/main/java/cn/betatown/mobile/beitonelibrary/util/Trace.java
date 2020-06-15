package cn.betatown.mobile.beitonelibrary.util;

import android.util.Log;

public class Trace {

    private static final boolean ISDEBUG = true;

    private static final String TAG = "btone";

    private Trace(){}

    public static void d(String msg){
        d(TAG , msg);
    }

    public static void d(String tag, String msg){
        if (ISDEBUG){
            Log.d(tag , msg);
        }
    }

    public static void e(String msg){
        e(TAG , msg);
    }

    public static void e(String tag, String msg){
        if (ISDEBUG){
            Log.e(tag , msg);
        }
    }


    public static void i(String msg){
        i(TAG , msg);
    }

    public static void i(String tag, String msg){
        if (ISDEBUG){
            Log.i(tag , msg);
        }
    }

}
