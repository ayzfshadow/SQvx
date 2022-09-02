package com.saki.qq.datapacket.pack.send;

import com.saki.qq.userinfo.Key;

public abstract class T0A02 extends SendPack
 {

    public T0A02(String command) {
        super(command);
        setType(2);
    }

 
	@Override public byte[] d() {
        return Key.a;
    }


}
