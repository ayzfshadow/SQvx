package com.saki.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.saki.qq.userinfo.PluginCenter;
import com.setqq.script.ScriptItem;
import com.ayzf.sqvx.R;

public class ScriptWebView extends Activity implements OnClickListener {
    private WebView webview;

    class ScriptWebClient extends WebViewClient {
        ScriptWebClient() {
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            Uri url = webResourceRequest.getUrl();
            String scheme = url.getScheme();
            if (scheme.equals("http") || scheme.equals("https") || scheme.equals("file")) {
                return super.shouldOverrideUrlLoading(webView, webResourceRequest);
            }
            Intent intent = new Intent("android.intent.action.VIEW", url);
            intent.setFlags(268435456);
            ScriptWebView.this.startActivity(intent);
            ScriptWebView.this.finish();
            return true;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Uri parse = Uri.parse(str);
            String scheme = parse.getScheme();
            if (scheme.equals("http") || scheme.equals("https") || scheme.equals("file")) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            ScriptWebView.this.startActivity(new Intent("android.intent.action.VIEW", parse));
            ScriptWebView.this.finish();
            return true;
        }
    }

    public void onClick(View view) {
        this.webview.stopLoading();
        this.webview.removeAllViews();
        this.webview.destroy();
        this.webview = null;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.layout0029);
        String stringExtra = getIntent().getStringExtra("script");
        this.webview = findViewById(R.id.id0083);
        ScriptItem a2 = PluginCenter.getInstence().getScriptById(stringExtra);
        this.webview.addJavascriptInterface(a2.plugin, "script");
        this.webview.removeJavascriptInterface("searchBoxJavaBridge_");
        this.webview.removeJavascriptInterface("accessibility");
        this.webview.removeJavascriptInterface("accessibilityTraversal");
        this.webview.getSettings().setDefaultTextEncodingName("utf-8");
        this.webview.requestFocusFromTouch();
        this.webview.setWebViewClient(new ScriptWebClient());
        this.webview.setScrollBarStyle(0);
        WebSettings settings = this.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setNeedInitialFocus(false);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadsImagesAutomatically(true);
        try{
            this.webview.loadUrl(a2.getUiUrl());
        }catch(Exception e){
            e.printStackTrace();
            this.onDestroy();
            return;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 4 || this.webview == null || !this.webview.canGoBack()) {
            return super.onKeyDown(i, keyEvent);
        }
        this.webview.goBack();
        return true;
    }
}
