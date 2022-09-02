package com.setqq.script.sdk;

import android.webkit.JavascriptInterface;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import java.io.Serializable;
import java.io.*;
import android.content.Intent;

public interface PluginInterface extends Serializable
{

    Intent getimpClass();

    void onstop();

    void onload(NewService newService) throws Throwable;

    void process(Msg msg) throws Throwable;

    String getUiUrl();

    @JavascriptInterface
    String onAction(String str) throws Throwable;
}
