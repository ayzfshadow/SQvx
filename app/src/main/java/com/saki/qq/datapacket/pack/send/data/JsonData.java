package com.saki.qq.datapacket.pack.send.data;

import com.saki.util.Zip;
import com.saki.qq.datapacket.pack.*;

public class JsonData extends MsgData {
    private byte[] a;

    public JsonData(String str) {
        this.a = Zip.Deflater(str.getBytes());
        ByteWriter classa = new ByteWriter();
        classa.writeByte((byte)1);
        classa.writeBytes(this.a);
        this.a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, classa.getData());
        this.a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(51, this.a);
        this.a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, this.a);
    }

    public byte[] getBytes() {
        return this.a;
    }
}
