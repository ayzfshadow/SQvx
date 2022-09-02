package com.saki.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.PopupWindow;
import com.saki.client.Client;
import com.saki.http.HTTP;
import com.ayzf.sqvx.R;

public class AnnounceView extends PopupWindow implements OnClickListener {
    Context a;
    /* access modifiers changed from: private */
    public Button button;
    private WebView webview;

    class SQwebClient extends WebViewClient {
        SQwebClient() {
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            AnnounceView.this.button.setEnabled(true);
        }

        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            webView.loadUrl("file:///android_asset/404.html");
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
            Uri url = webResourceRequest.getUrl();
            String scheme = url.getScheme();
            if (scheme.equals("http") || scheme.equals("https")) {
                return super.shouldOverrideUrlLoading(webView, webResourceRequest);
            }
            Intent intent = new Intent("android.intent.action.VIEW", url);
            intent.setFlags(268435456);
            AnnounceView.this.a.startActivity(intent);
            return true;
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Uri parse = Uri.parse(str);
            String scheme = parse.getScheme();
            if (scheme.equals("http") || scheme.equals("https")) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            AnnounceView.this.a.startActivity(new Intent("android.intent.action.VIEW", parse));
            return true;
        }
    }

	
	
	
    public AnnounceView(Context context, View view) {
        super(context);
        this.a = context;
		
        setContentView(LayoutInflater.from(context).inflate(R.layout.layout_view_announce, null));
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
		setHeight(WindowManager.LayoutParams.MATCH_PARENT);
		button = ((Button) getContentView().findViewById(R.id.announceview_button));
		webview = ((WebView) getContentView().findViewById(R.id.announceview_webview));
        this.webview.removeJavascriptInterface("searchBoxJavaBridge_");
        this.webview.removeJavascriptInterface("accessibility");
        this.webview.removeJavascriptInterface("accessibilityTraversal");
        this.webview.getSettings().setDefaultTextEncodingName("utf-8");
        this.webview.requestFocusFromTouch();
        this.webview.setWebViewClient(new SQwebClient());
        this.webview.setScrollBarStyle(0);
        WebSettings settings = this.webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setNeedInitialFocus(false);
        settings.setSupportZoom(true);
        settings.setUseWideViewPort(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setLoadsImagesAutomatically(true);
        this.button.setOnClickListener(this);
        this.button.setEnabled(false);
        setOutsideTouchable(false);
        setAnimationStyle(R.style.Animation_AppCompat_Dialog);
        showAtLocation(view, 17, 0, 50);
        int i = -1;
        try {
            i = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        if (!com.ayzf_shadow.tool.jiance.vpn.֏֏֏֏֏֏() || !com.ayzf_shadow.tool.jiance.xposed.֏֏֏֏֏֏())
        {
            webview.loadUrl("file:///android_asset/404.html");
            return;
        }
        this.webview.loadUrl("http://www.ayzfshadow.com/SQvx/NoticeVx.php");
    }

    @SuppressLint({"NewApi"})
    public void onClick(View view) {
        if (view == this.button) {
            dismiss();
        }
    }
}
