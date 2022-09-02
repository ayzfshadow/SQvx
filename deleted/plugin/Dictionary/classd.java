package com.saki.plugin.Dictionary;

import android.os.Handler;
import android.os.Message;

public class classd extends Handler {
    public void a() {
        sendEmptyMessage(1);
    }

    public void a(int i, boolean z) {
        a("第[" + i + "]条:" + (z ? "完成" : "抛弃"));
    }

    public void a(String str) {
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.obj = str;
        sendMessage(obtain);
    }
}
