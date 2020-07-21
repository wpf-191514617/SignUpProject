package cn.betatown.mobile.beitonelibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cn.betatown.mobile.beitonelibrary.base.BaseApplication;
import cn.betatown.mobile.beitonelibrary.http.BaseProvider;
import cn.betatown.mobile.beitonelibrary.util.Trace;

public class MyWebView extends WebView {

    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebSetting();


        setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100){
                    setVisibility(View.VISIBLE);
                } else {
                    setVisibility(View.GONE);
                }
            }


        });

        setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);

            }

        });


    }

    private void initWebSetting() {
        getSettings().setJavaScriptEnabled(true);
        setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        getSettings().setDefaultTextEncodingName("UTF-8");
        getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        getSettings().setBuiltInZoomControls(false);
        getSettings().setSupportMultipleWindows(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setSupportZoom(false);
        getSettings().setPluginState(WebSettings.PluginState.ON);
        getSettings().setDomStorageEnabled(true);
        getSettings().setLoadsImagesAutomatically(true);
    }


    public void onLoadUrl(String url) {
        synCookies(getContext() , url);
        loadUrl(url,getHeader());
    }

    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, "JSESSIONID=" + BaseApplication.getSession()+ ";Domain=home" +
                ".tx06.com;Path=/");
        CookieSyncManager.getInstance().sync();
    }


    protected Map<String, String> getHeader() {
        Map<String, String> head = new HashMap<>();
        head.put("Referer", BaseProvider.BaseUrl);
        String jsessionid = BaseApplication.getSession();
        head.put("Cookie", "JSESSIONID=" + jsessionid + ";Domain=home.tx06.com;Path=/");
        return head;
    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int mExpandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
//        super.onMeasure(widthMeasureSpec, mExpandSpec);
//    }

}
