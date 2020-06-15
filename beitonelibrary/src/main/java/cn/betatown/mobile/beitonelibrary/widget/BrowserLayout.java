package cn.betatown.mobile.beitonelibrary.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.util.TypedValue;
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

import cn.betatown.mobile.beitonelibrary.R;
import cn.betatown.mobile.beitonelibrary.util.Trace;

/**
 * WebView
 */

public class BrowserLayout extends LinearLayout {


    /*private OnLoadUrlCallBackListener mOnLoadUrlCallBackListener;

    public void setmOnLoadUrlCallBackListener(OnLoadUrlCallBackListener
    mOnLoadUrlCallBackListener) {
        this.mOnLoadUrlCallBackListener = mOnLoadUrlCallBackListener;
    }*/

    private OnWebViewClientListener mOnWebViewClientListener;

    public void setOnWebViewClientListener(OnWebViewClientListener mOnWebViewClientListener) {
        this.mOnWebViewClientListener = mOnWebViewClientListener;
    }

    protected Context mContext = null;
    protected WebView mWebView = null;
    protected WebSettings mWebSettings;
  /*  private View mBrowserControllerView = null;
    private ImageButton mGoBackBtn = null;
    private ImageButton mGoForwardBtn = null;
    private ImageButton mGoBrowserBtn = null;
    private ImageButton mRefreshBtn = null;*/

    private int mBarHeight = 5;
    protected ProgressBar mProgressBar = null;

    protected String mLoadUrl;

    public BrowserLayout(Context context) {
        super(context);
        init(context);
    }

    public BrowserLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_webview , this);
        mProgressBar = contentView.findViewById(R.id.my_profile_tracker);
        mWebView = contentView.findViewById(R.id.webView);
       /* mProgressBar =
                (ProgressBar) LayoutInflater.from(context).inflate(R.layout.progress_horizontal,
                        null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mBarHeight,
                        getResources().getDisplayMetrics()));

        mWebView = new WebView(context);*/
        mWebSettings = mWebView.getSettings();
        initWebSetting();

      /*  LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mWebView, lps);*/

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {
                    /*if(mOnLoadUrlCallBackListener != null){
                        mOnLoadUrlCallBackListener.onFinish(mLoadUrl);
                    }*/
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }


        });

        mWebView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoadUrl = url;
                if (mOnWebViewClientListener != null) {
                    mOnWebViewClientListener.onPageFinished(view, url);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                Trace.i("current Url ", "url " + url);
                view.loadUrl(url);
                if (mOnWebViewClientListener != null) {
                    mOnWebViewClientListener.shouldOverrideUrlLoading(view, url);
                }
                /*if(!url.startsWith("http")&&!url.startsWith("https")){
                    Intent intent = new Intent(Intent.ACTION_VIEW , Uri.parse(url));
                    getContext().startActivity(intent);
                    return true;
                }*/

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (mOnWebViewClientListener != null) {
                    mOnWebViewClientListener.onPageStarted(view, url, favicon);
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                handler.proceed();
                if (mOnWebViewClientListener != null) {
                    mOnWebViewClientListener.onReceivedSslError(view, handler, error);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                if (mOnWebViewClientListener != null) {
                    mOnWebViewClientListener.onReceivedError(view, request, error);
                }
            }

        });

       /* mBrowserControllerView = LayoutInflater.from(context).inflate(R.layout
       .browser_controller, null);
        mGoBackBtn = (ImageButton) mBrowserControllerView.findViewById(R.id
        .browser_controller_back);
        mGoForwardBtn = (ImageButton) mBrowserControllerView.findViewById(R.id
        .browser_controller_forward);
        mGoBrowserBtn = (ImageButton) mBrowserControllerView.findViewById(R.id
        .browser_controller_go);
        mRefreshBtn = (ImageButton) mBrowserControllerView.findViewById(R.id
        .browser_controller_refresh);

        mGoBackBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (canGoBack()) {
                    goBack();
                }
            }
        });

        mGoForwardBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (canGoForward()) {
                    goForward();
                }
            }
        });

        mRefreshBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                loadUrl(mLoadUrl);
            }
        });

        mGoBrowserBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!CommonUtils.isEmpty(mLoadUrl)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mLoadUrl));
                    mContext.startActivity(intent);
                }
            }
        });*/

        //addView(mBrowserControllerView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    protected void initWebSetting() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public void loadUrl(String url, Map<String, String> head){
        mWebView.loadUrl(url , head);
    }

    public boolean canGoBack() {
        return null != mWebView ? mWebView.canGoBack() : false;
    }

    public boolean canGoForward() {
        return null != mWebView ? mWebView.canGoForward() : false;
    }

    public void goBack() {
        if (null != mWebView) {
            mWebView.goBack();
        }
    }

    public void goForward() {
        if (null != mWebView) {
            mWebView.goForward();
        }
    }

    public WebView getWebView() {
        return mWebView != null ? mWebView : null;
    }



}
