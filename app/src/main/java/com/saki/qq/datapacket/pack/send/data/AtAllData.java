package com.saki.qq.datapacket.pack.send.data;

public class AtAllData extends MsgData {
    private byte[] a;

    public AtAllData(String str) {
        com.saki.qq.datapacket.pack.ByteWriter classa = new com.saki.qq.datapacket.pack.ByteWriter();
        classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, str.getBytes()));
        com.saki.qq.datapacket.pack.ByteWriter classa2 = new com.saki.qq.datapacket.pack.ByteWriter();
        classa2.writeShort(1);
        classa2.writeInt(str.length());
        classa2.writeByte((byte)1);
        classa2.writeInt(0);
        classa2.writeShort(0);
        classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(3, classa2.getData()));
        this.a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, classa.getData()));
    }

    public byte[] getBytes() {
        return this.a;
    }
}