package com.setqq.script.sdk;

import com.setqq.script.Msg;

public interface PluginApiInterface {
    Msg send(Msg msg);
    
    void log(int tag,String data);
}
