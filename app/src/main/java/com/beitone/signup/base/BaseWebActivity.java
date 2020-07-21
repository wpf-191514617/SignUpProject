package com.beitone.signup.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.widget.Toast;

import com.beitone.signup.R;
import com.beitone.signup.SignUpApplication;
import com.beitone.signup.model.EventData;
import com.beitone.signup.util.AndroidInterface;
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
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.ColorInt;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipeBackLayout;
import cn.betatown.mobile.beitonelibrary.base.swipeback.BmSwipebackUtils;
import cn.betatown.mobile.beitonelibrary.base.swipeback.app.BmSwipeBackActivityBase;
import cn.betatown.mobile.beitonelibrary.base.swipeback.app.BmSwipeBackActivityHelper;
import cn.betatown.mobile.beitonelibrary.http.BaseProvider;

public abstract class BaseWebActivity extends AppCompatActivity implements BmSwipeBackActivityBase {

    protected AgentWeb mAgentWeb;
    private AgentWebUIControllerImplBase mAgentWebUIController;
    private ErrorLayoutEntity mErrorLayoutEntity;
    private MiddlewareWebChromeBase mMiddleWareWebChrome;
    private MiddlewareWebClientBase mMiddleWareWebClient;

    private Handler handler = new Handler();

    private BmSwipeBackActivityHelper mHelper;

    protected AgentWeb.PreAgentWeb preAgentWeb;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHelper = new BmSwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
        if (isRegisterEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View view = super.findViewById(id);
        if (view == null && mHelper != null) {
            return mHelper.findViewById(id);
        }
        return view;
    }

    @Override
    public BmSwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        BmSwipebackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }

    protected abstract boolean isRegisterEventBus();

    protected void onEventComming(EventData eventData) {

    }

    @Subscribe
    public void onEventMainThread(EventData eventData) {
        if (null != eventData) {
            onEventComming(eventData);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        buildAgentWeb();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        buildAgentWeb();
    }

    public void syncCookie(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除旧的[可以省略]
        cookieManager.setCookie(url, "JSESSIONID=" + SignUpApplication.getSession()+ ";Domain" +
                "=home.tx06.com;Path=/");
        CookieSyncManager.getInstance().sync();// To get instant sync instead of waiting for the timer to trigger, the host can call this.
    }

    protected void buildAgentWeb() {
        ErrorLayoutEntity mErrorLayoutEntity = getErrorLayoutEntity();
        preAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(getAgentWebParent(), new ViewGroup.LayoutParams(-1, -1))
                .useDefaultIndicator(getIndicatorColor(), getIndicatorHeight())
                .setWebChromeClient(getWebChromeClient())
                .setWebViewClient(getWebViewClient())
                .setWebView(getWebView())
                .setPermissionInterceptor(getPermissionInterceptor())
                .setWebLayout(getWebLayout())
                .setAgentWebUIController(getAgentWebUIController())
                .interceptUnkownUrl()
                .setOpenOtherPageWays(getOpenOtherAppWay())
                .useMiddlewareWebChrome(getMiddleWareWebChrome())
                .useMiddlewareWebClient(getMiddleWareWebClient())
                .setAgentWebWebSettings(getAgentWebSettings())
                .setMainFrameErrorView(mErrorLayoutEntity.layoutRes, mErrorLayoutEntity.reloadId)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .additionalHttpHeader(getUrlString(), getHeader())
                .createAgentWeb()
                .ready();
        mAgentWeb = preAgentWeb.go(getUrl());
        mAgentWeb.getJsInterfaceHolder().addJavaObject("AndroidWebView",
                new AndroidInterface(this));
    }

    protected abstract String getUrlString();

    protected Map<String, String> getHeader() {
        Map<String, String> head = new HashMap<>();
        head.put("Referer", BaseProvider.BaseUrl);
        String jsessionid = SignUpApplication.getSession();
        head.put("Cookie", "JSESSIONID=" + jsessionid + ";Domain=home.tx06.com;Path=/");
        return head;
    }


    protected @NonNull
    ErrorLayoutEntity getErrorLayoutEntity() {
        if (this.mErrorLayoutEntity == null) {
            this.mErrorLayoutEntity = new ErrorLayoutEntity();
        }
        return mErrorLayoutEntity;
    }

    protected AgentWeb getAgentWeb() {
        return this.mAgentWeb;
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onPause();
        }
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        if (mAgentWeb != null) {
            mAgentWeb.getWebLifeCycle().onResume();
        }
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        if (mAgentWeb != null) {
            mAgentWeb.clearWebCache();
            mAgentWeb.getWebLifeCycle().onDestroy();
            mAgentWeb.destroy();
            AgentWebConfig.clearDiskCache(this);
        }
        if (isRegisterEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }


    protected
    @Nullable
    String getUrl() {
        return null;
    }

    public @Nullable
    IAgentWebSettings getAgentWebSettings() {
        return AgentWebSettingsImpl.getInstance();
    }

    protected abstract @NonNull
    ViewGroup getAgentWebParent();

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

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(view, title);
            }

        };
    }

    protected void showContentView() {
    }

    protected @ColorInt
    int getIndicatorColor() {
        return -1;
    }

    protected int getIndicatorHeight() {
        return -1;
    }

    protected @Nullable
    WebViewClient getWebViewClient() {
        return new WebViewClient() {


            @Override
            public void onLoadResource(WebView view, String url) {

                enableCookies(view);
                CookieManager mCookieManager = CookieManager.getInstance();
                if (mCookieManager != null) {
                    mCookieManager.setAcceptCookie(true);
                    AgentWebConfig.syncCookie(url,
                            "JSESSIONID=" + SignUpApplication.getSession()+";Domain=home.tx06" +
                                    ".com;Path=/");
                }
                super.onLoadResource(view, url);
            }
        };
    }

    protected void showloadingview() {

    }


    protected @Nullable
    WebView getWebView() {
        return new WebView(this);
    }

    protected @Nullable
    IWebLayout getWebLayout() {
        return null;
    }

    protected @Nullable
    PermissionInterceptor getPermissionInterceptor() {
        return null;
    }

    public @Nullable
    AgentWebUIControllerImplBase getAgentWebUIController() {
        return null;
    }

    public @Nullable
    DefaultWebClient.OpenOtherPageWays getOpenOtherAppWay() {
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

    protected void setTitle(WebView view, String title) {

    }

    protected @NonNull
    MiddlewareWebClientBase getMiddleWareWebClient() {
        return this.mMiddleWareWebClient = new MiddlewareWebClientBase() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setTitle(view, view.getTitle());

                    }
                }, 3000);

            }
        };
    }

    protected void showToast(int resourceId) {
        showToast(getString(resourceId));
    }

    protected void showToast(String text) {
        if (!TextUtils.isEmpty(text))
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }


}
