package com.beitone.signup.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.beitone.signup.R;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import me.jessyan.autosize.utils.ScreenUtils;

public class CheckingDialog extends Dialog {

    public static final String PASS = "pass";//通过
    public static final String CANCEL = "cancel";//取消

//5e0456544c6b8b41680b8b67
//5e096eee4c6b8b41680b8d30
    private final String checkingUrl =
            "https://v.vaptcha.com/app/android.html?" +
            "vid=5ee854a11850112466713183&scene=1&lang=zh-CN&" +
            "offline_server=https://www.vaptchadowntime.com/dometime";

    private WebView webChecking;

    private OnCheckingCallback mOnCheckingCallback;

    public void setOnCheckingCallback(OnCheckingCallback mOnCheckingCallback) {
        this.mOnCheckingCallback = mOnCheckingCallback;
    }

    public CheckingDialog(@NonNull Context context) {
        super(context, R.style.ActionSheetDialogStyle);
        init(context);
    }


    @Override
    public void show() {
        super.show();
        webChecking.loadUrl(checkingUrl);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_checking, null);
        setContentView(view);
        webChecking = view.findViewById(R.id.webChecking);
        webChecking.setBackgroundColor(Color.TRANSPARENT);
        webChecking.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        webChecking.getSettings().setUseWideViewPort(true);
        webChecking.getSettings().setLoadWithOverviewMode(true);
        webChecking.getSettings().setTextZoom(80);
        // 禁止缓存加载，以确保可获取最新的验证图片。
        webChecking.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置不使用默认浏览器，而直接使用WebView组件加载页面。
        webChecking.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 设置WebView组件支持加载JavaScript。
        webChecking.getSettings().setJavaScriptEnabled(true);
        // 建立JavaScript调用Java接口的桥梁。
        webChecking.addJavascriptInterface(new VaptchaInterface(), "vaptchaInterface");
        int[] sizeArray = ScreenUtils.getScreenSize(context);
        if (sizeArray != null && sizeArray.length > 1) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            layoutParams.width = sizeArray[0];
           // layoutParams.height = sizeArray[1];
            layoutParams.height = context.getResources().getDimensionPixelSize(R.dimen.dimen_365dp);
            view.setLayoutParams(layoutParams);
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
        webChecking.loadUrl("about:blank");
    }

    private class VaptchaInterface {

        @JavascriptInterface
        public void signal(String json) {
            //json格式{signal:"",data:""}
            //signal: pass (通过) ； cancel（取消）
            try {
                JSONObject jsonObject = new JSONObject(json);
                String signal = jsonObject.getString("signal");
                String data = jsonObject.getString("data");
                if (PASS.equals(signal)) {//通过
                    if (mOnCheckingCallback != null) {
                        mOnCheckingCallback.onCheckingPass(data);
                    }
                } else if (CANCEL.equals(signal)) {//取消
                    if (mOnCheckingCallback != null) {
                        mOnCheckingCallback.onCheckingCancel();
                    }
                } else {//其他html页面返回的状态参数
                    if (mOnCheckingCallback != null) {
                        mOnCheckingCallback.onCheckingFailed(signal);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            dismiss();
        }
    }

    public interface OnCheckingCallback {
        void onCheckingPass(String token);

        void onCheckingCancel();

        void onCheckingFailed(String signal);
    }

}
