package com.saki.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import com.saki.service.NewService.a;
import com.saki.ui.LoginListener.LoginResultListener;
import com.saki.ui.LoginListener.ServiceListenner;

public class SQServiceConnection implements ServiceConnection {
    private NewService a;
    private LoginResultListener b;
    private ServiceListenner c;

    public SQServiceConnection(ServiceListenner classa) {
        this.c = classa;
    }

    public SQServiceConnection(LoginResultListener classc) {
        this.b = classc;
    }

    public NewService getService() {
        return this.a;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
       Log.d("serviceconnected","");
        this.a = ((a) iBinder).a();
        this.a.a(this.b);
        if (this.c != null) {
            this.c.onCall(getService());
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.a.a((LoginResultListener) null);
        this.b = null;
        this.a = null;
    }
}
