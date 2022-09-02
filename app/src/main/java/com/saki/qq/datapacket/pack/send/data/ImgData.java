package com.saki.qq.datapacket.pack.send.data;

import com.saki.qq.userinfo.imgstore;
import com.saki.qq.datapacket.pack.ByteWriter;

public class ImgData extends MsgData {
    private byte[] a;

    public ImgData(imgstore classc) {
        ByteWriter classa = new ByteWriter();
        classa.writePb(2, classc.toString().getBytes());
        classa.writePb(7, classc.g);
        classa.writePb(8, 3082863821L);
        classa.writePb(9, 8080);
        classa.writePb(10, 66);
        classa.writePb(11, "eXyEdhfrFa5KfBK7".getBytes());
        classa.writePb(12, 1);
        classa.writePb(13, classc.c);
        classa.writePb(17, 3);
        classa.writePb(20, 1000);
        classa.writePb(22, (long) classc.d);
        classa.writePb(23, (long) classc.e);
        classa.writePb(24, 101);
        classa.writePb(25, (long) classc.b);
        classa.writePb(26, 0);
        classa.writePb(29, 0);
        classa.writePb(30, 0);
        this.a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(8, classa.getDataAndDestroy()));
    }

    public byte[] getBytes() {
        return this.a;
    }
}
