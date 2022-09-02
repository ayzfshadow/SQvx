package com.saki.qq.datapacket.pack.send.data;

import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;

public class TextData extends MsgData {
    private byte[] a;

    public TextData(String str) {
        this.a = ProtoBuff.writeLengthDelimit(1, str.getBytes());
        this.a = ProtoBuff.writeLengthDelimit(1, this.a);
        this.a = ProtoBuff.writeLengthDelimit(2, this.a);
    }

    public byte[] getBytes() {
        return this.a;
    }
}
