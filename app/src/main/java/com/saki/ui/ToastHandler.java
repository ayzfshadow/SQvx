package com.saki.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import android.view.View;
import android.support.design.widget.Snackbar;

public class ToastHandler extends Handler {
    private Context a;

    private View view;

    public ToastHandler(View view,Context context) {
        this.a = context;
        this.view=view;
    }

    public void toastInfo(String str) {
        Message obtainMessage = obtainMessage();
        obtainMessage.obj = str;
        sendMessage(obtainMessage);
    }

    public void handleMessage(Message message) {
        Snackbar.make(view, message.obj.toString(), 1000).show();
    }
}
