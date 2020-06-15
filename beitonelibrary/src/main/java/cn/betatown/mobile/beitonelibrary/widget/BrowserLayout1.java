package cn.betatown.mobile.beitonelibrary.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Map;

import androidx.annotation.Nullable;
import cn.betatown.mobile.beitonelibrary.R;
import cn.betatown.mobile.beitonelibrary.util.Trace;

/**
 * WebView
 */

public class BrowserLayout1 extends LinearLayout {

    private MyWebView myWebView;


    public BrowserLayout1(Context context) {
        this(context , null);
    }

    public BrowserLayout1(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BrowserLayout1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_noscrollwebview , this);
        myWebView = view.findViewById(R.id.layoutWebView);
    }

    public void loadUrl(Activity activity , final String url){
        if (TextUtils.isEmpty(url)){
            return;
        }

        final WebSettings settings = myWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setDomStorageEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1) {
            new Object() {
                void setLoadWithOverviewMode(boolean overview) {
                    settings.setLoadWithOverviewMode(overview);
                }
            }.setLoadWithOverviewMode(true);
        }

        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        activity.getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                myWebView.loadUrl(url);
            }
        });
    }

    public WebView getWebView(){
        return myWebView;
    }

}
