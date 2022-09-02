package com.saki.qq.datapacket.unpack;

public class ReStatSvc {

    public static class register extends RecviePack {
        public register(byte[] bArr) {
            super(bArr);
        }
    }

    public static class SimpleGet extends RecviePack {
        public SimpleGet(byte[] bArr) {
            super(bArr);
        }
    }
}
