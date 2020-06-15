package cn.betatown.mobile.beitonelibrary.widget;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

public interface OnWebViewClientListener {

    boolean shouldOverrideUrlLoading(WebView view, String url);

    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error);

    void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error);

}
