package com.x.module_webview.customview;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private final String TAG = getClass().getSimpleName();

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.setEnabled(true);
        Log.e(TAG, "shouldOverrideUrlLoading:" + url);
        if (url.startsWith("http:") || url.startsWith("https:")) {
            //不需要处理
            return false;
        } else {
            Log.e(TAG, "shouldOverrideUrlLoading");
            //自己处理
            return true;
        }
    }
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //接受证书
        handler.cancel();// 接受所有网站的证书
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
    }

    @TargetApi(android.os.Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
        onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
    }
}
