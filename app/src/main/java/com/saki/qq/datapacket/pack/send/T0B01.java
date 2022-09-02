package com.saki.qq.datapacket.pack.send;

import com.saki.qq.userinfo.Key;

public abstract class T0B01 extends SendPack {
    public T0B01(String str) {
        super(str);
        super.setcmdtype(11);
    }

	

    /* access modifiers changed from: protected */
   @Override public void packToken4c(byte[] bArr) {
       
    }

    /* access modifiers changed from: protected */
   @Override public byte[] d() {
        return Key.c;
    }
}
