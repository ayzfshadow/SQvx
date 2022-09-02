package com.saki.qq.datapacket.unpack;

public class ReConfigPushSvc {

    public static class PushDomain extends RecviePack {
        public PushDomain(byte[] bArr) {
            super(bArr);
        }
    }

    public static class PushReq extends RecviePack {
        public PushReq(byte[] bArr) {
            super(bArr);
        }
    }
}
