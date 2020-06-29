package com.beitone.signup.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.beitone.signup.R;
import com.beitone.signup.SignUpApplication;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.AgentWebConfig;
import com.just.agentweb.AgentWebSettingsImpl;
import com.just.agentweb.AgentWebUIControllerImplBase;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.IAgentWebSettings;
import com.just.agentweb.IWebLayout;
import com.just.agentweb.MiddlewareWebChromeBase;
import com.just.agentweb.MiddlewareWebClientBase;
import com.just.agentweb.PermissionInterceptor;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import cn.betatown.mobile.beitonelibrary.http.BaseProvider;

public abstract class BaseWebFragment extends Fragment {

    protected AgentWeb mAgentWeb;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;
    private ErrorLayoutEntity mErrorLayoutEntity;
    private AgentWebUIControllerImplBase mAgentWebUIController;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ErrorLayoutEntity mErrorLayoutEntity = getErrorLayoutEntity();
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) getAgentWebParent(), -1,
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
                /*  .setWebView(getWebView())
                  .setWebLayout(getWebLayout())*/
                .setAgentWebWebSettings(getAgentWebSettings())
                .setWebViewClient(getWebViewClient())
                .setPermissionInterceptor(getPermissionInterceptor())
                .setWebChromeClient(getWebChromeClient())
                .interceptUnkownUrl()
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setAgentWebUIController(getAgentWebUIController())
                .setMainFrameErrorView(mErrorLayoutEntity.layoutRes, mErrorLayoutEntity.reloadId)
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())
                .additionalHttpHeader(getUrl() , getHeader())
                .createAgentWeb()//
                .ready()//
                .go(getUrl());
    }

    protected Map<String, String> getHeader() {
        Map<String, String> head = new HashMap<>();
        head.put("Referer" , BaseProvider.BaseUrl);
        head.put("Set-Cookie" , "JSESSIONID=" + SignUpApplication.getSession()+ ";Domain=www.beitone.com;" +
                "Path=/");
        return head;
    }

    public final void enableCookies(WebView webView) {
        // enable javascript (has security implications!)
        webView.getSettings().setJavaScriptEnabled(true);

        // enable cookies.
        CookieManager.getInstance().setAcceptCookie(true);

        // enable fileScheme cookies (has security implications!)
        // it is recommended to comment this out should you find it not necessary
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            CookieManager.getInstance().setAcceptFileSchemeCookies(true);
        }

        // enable third party cookies
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
    }


    protected void setTitle(WebView view, String title) {

    }

    protected @NonNull
    ErrorLayoutEntity getErrorLayoutEntity() {
        if (this.mErrorLayoutEntity == null) {
            this.mErrorLayoutEntity = new ErrorLayoutEntity();
        }
        return mErrorLayoutEntity;
    }

    protected @Nullable
    AgentWebUIControllerImplBase getAgentWebUIController() {
        return mAgentWebUIController;
    }

    protected static class ErrorLayoutEntity {
        private int layoutRes = R.layout.error_ui;
        private int reloadId;

        public void setLayoutRes(int layoutRes) {
            this.layoutRes = layoutRes;
            if (layoutRes <= 0) {
                layoutRes = -1;
            }
        }

        public void setReloadId(int reloadId) {
            this.reloadId = reloadId;
            if (reloadId <= 0) {
                reloadId = -1;
            }
        }
    }

    @Override
    public void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();

    }

    @Override
    public void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    protected @Nullable
    String getUrl() {
        return "";
    }

    @Override
    public void onDestroy() {
        if (mAgentWeb != null) {
            AgentWebConfig.clearDiskCache(this.getContext());
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        if (mAgentWeb != null) {
            AgentWebConfig.clearDiskCache(this.getContext());
            mAgentWeb.getWebLifeCycle().onDestroy();
        }
        super.onDestroyView();
    }

    protected @Nullable
    IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance();
    }

    protected @Nullable
    WebChromeClient getWebChromeClient() {
        return new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress >= 100) {
                    showContentView();
                }
                super.onProgressChanged(view, newProgress);
            }



        };
    }

    protected void showContentView() {
    }

    protected abstract @NonNull
    ViewGroup getAgentWebParent();

    protected @ColorInt
    int getIndicatorColor() {
        return -1;
    }

    protected int getIndicatorHeight() {
        return -1;
    }

    protected @Nullable
    WebViewClient getWebViewClient() {
        return new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                showloadingview();
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                enableCookies(view);
                CookieManager mCookieManager = CookieManager.getInstance();
                if (mCookieManager != null) {
                    mCookieManager.setAcceptCookie(true);
                    AgentWebConfig.syncCookie(url, "JSESSIONID=" + SignUpApplication.getSession()+";Domain=www" +
                            ".beitone.com;Path=/");
                }
            }
        };
    }

    /**
     * 同步一下cookie
     */
    public void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
//        cookieManager.removeSessionCookie();//移除
        cookieManager.setCookie(url, "JSESSIONID=" + SignUpApplication.getSession()+ ";Domain=www.beitone.com;Path=/");
        CookieSyncManager.getInstance().sync();
    }


    protected void showloadingview() {

    }

    protected @Nullable
    WebView getWebView() {
        return null;
    }

    protected @Nullable
    IWebLayout getWebLayout() {
        return null;
    }

    protected @Nullable
    PermissionInterceptor getPermissionInterceptor() {
        return null;
    }

    protected @NonNull
    MiddlewareWebChromeBase getMiddleWareWebChrome() {
        return this.mMiddleWareWebChrome = new MiddlewareWebChromeBase() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(view, title);
            }
        };
    }

    protected @NonNull
    MiddlewareWebClientBase getMiddleWareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebClientBase() {
        };
    }

}
