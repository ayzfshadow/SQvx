package com.saki.qq.datapacket.pack.send.data;

import com.saki.qq.datapacket.pack.ByteWriter;

public class XmlData extends MsgData {
    private byte[] a;

    public XmlData(String str) {
        String replaceAll = str.replaceAll("(?s)(?i).*?service[iI][dD]=['|\"]([0-9]+).*", "$1");
        int i = 0;
        if (replaceAll.matches("[0-9]+")) {
            i = Integer.parseInt(replaceAll);
        }
        this.a = com.saki.util.Zip.Deflater(str.getBytes());
        ByteWriter classa = new ByteWriter();
        classa.writeByte((byte)1);
        classa.writeBytes(this.a);
        this.a = classa.getData();
        classa.reCreate();
        classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, this.a));
        classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, (long) i));
        this.a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(12, classa.getData());
        this.a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, this.a);
    }

    public byte[] getBytes() {
        return this.a;
    }
}
