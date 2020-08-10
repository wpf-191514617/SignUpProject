package com.beitone.signup.helper;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.beitone.signup.provider.AccountProvider;

import cn.betatown.mobile.beitonelibrary.http.callback.OnJsonCallBack;
import cn.betatown.mobile.beitonelibrary.util.Trace;

public class UploadHlepr {

    private static UploadHlepr mUploadHlepr;

    private Handler mHandler;

    private long mIntervalTime = 0;

    private Context mContext;

    private UploadHlepr(Context context){
        mContext = context;
        mHandler = new Handler();
    }

    public static UploadHlepr getInstance(Context context){
        if (mUploadHlepr == null){
            synchronized (UploadHlepr.class){
                if (mUploadHlepr == null){
                    mUploadHlepr = new UploadHlepr(context);
                }
            }
        }
        return mUploadHlepr;
    }

    public void startUploadLocation(int minute){
        if (minute <= 0){
            minute = 1;
        }
        mIntervalTime = minute * 60 * 1000;
        mHandler.postDelayed(mRunnable , 10);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            uploadLocation();
            mHandler.postDelayed(this , mIntervalTime);
        }
    };

    private void uploadLocation() {
        AccountProvider.uploadLocation(mContext, new OnJsonCallBack() {
            @Override
            public void onResult(Object data) {
                Trace.d("uploadLoc----" + data);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                Trace.d("");
            }
        });
    }

    public void onStop(){
        mHandler.removeCallbacks(mRunnable);
    }

}
