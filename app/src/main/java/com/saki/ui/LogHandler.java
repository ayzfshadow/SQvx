package com.saki.ui;

import android.os.Handler;

public abstract class LogHandler extends Handler {
    public abstract void call(int type, Object data);
}
