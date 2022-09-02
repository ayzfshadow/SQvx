package com.saki.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.saki.service.NewService;
import com.saki.service.SQServiceConnection;

public abstract class classa extends RelativeLayout implements com.saki.ui.LoginListener.ServiceListenner {
    protected LayoutInflater a;
    private ListView c;
    private SQServiceConnection d;

    public classa(Context context, int i) {
        super(context);
        this.a = LayoutInflater.from(context);
        this.a.inflate(i, this);
  
        this.c = b(context);
		
        Intent intent = new Intent(context, NewService.class);
        SQServiceConnection classb = new SQServiceConnection((com.saki.ui.LoginListener.ServiceListenner) this);
        this.d = classb;
        context.bindService(intent, classb, 1);
    }

    /* access modifiers changed from: protected */
    
    public void onCall(NewService newService) {
    }

    /* access modifiers changed from: protected */
    public abstract ListView b(Context context);

    public void onDestroy() {
        this.c = null;
    
        this.a = null;
        getContext().unbindService(this.d);
    }

    public ListView getListView() {
        return this.c;
    }

    

    public NewService getService() {
        return this.d.getService();
    }
}
