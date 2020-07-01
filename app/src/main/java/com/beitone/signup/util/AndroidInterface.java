package com.beitone.signup.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.webkit.JavascriptInterface;

import com.beitone.signup.model.EventCode;
import com.beitone.signup.model.EventData;

import org.greenrobot.eventbus.EventBus;

import cn.betatown.mobile.beitonelibrary.util.Trace;

public class AndroidInterface {

    private Handler deliver = new Handler(Looper.getMainLooper());

    private Activity activity;

    public AndroidInterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void getImgSrc(){
        EventData eventData = new EventData<>(EventCode.CODE_GET_IMGSRC);
        EventBus.getDefault().post(eventData);
    }
















    // 分享
    @JavascriptInterface
    public void shareWeb(final String title, final String url, final String imgUrl,
                         final String desc) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
               /* ShareWebEntity webEntity = new ShareWebEntity();
                webEntity.desc = desc;
                webEntity.imgUrl = imgUrl;
                webEntity.title = title;
                webEntity.url = url;
                EventData<ShareWebEntity> eventData = new EventData<>(EventCode.CODE_SHARE,
                        webEntity);
                EventBus.getDefault().post(eventData);*/
               // UMengHelper.getInstance().share(activity, title, url, imgUrl, desc);
            }
        });
    }

    @JavascriptInterface
    public void toOpenBankAccountPage(String type){
        deliver.post(new Runnable() {
            @Override
            public void run() {
               /* EventData eventData = new EventData<>(EventCode.CODE_OPENBANKACCOUNTPAGE);
                EventBus.getDefault().post(eventData);*/
            }
        });
    }

    @JavascriptInterface
    public void Bank_CallBack(final String page, final String flag, final String businessSeqNo,
                              final String msg) {
        deliver.post(new Runnable() {
            @Override
            public void run() {
                /*WebResponse webResponse = new WebResponse(flag, msg);
                webResponse.businessSeqNo = businessSeqNo;
                EventData<WebResponse> eventData = new EventData<WebResponse>(EventCode.CODE_WEB,
                        webResponse);
                EventBus.getDefault().post(eventData);*/
            }
        });
    }

    @JavascriptInterface
    public void showSource(String title) {
        Trace.d("onReceiveTitle", title);
    }

}
