package com.saki.qq.datapacket.pack.send;

import com.saki.qq.userinfo.User;
import com.saki.qq.datapacket.pack.ByteWriter;

public class PttStore {
    public static int id = 1000;
    public static class GroupPttUp extends T0B01 {
        private long a;
        private byte[] b;
        private int c;
        private String d;
        
        private String type;

		private String hexmd5;

        public GroupPttUp(long mark,long j, int i, String hexmd5,String str, byte[] bArr,String type) {
            super("PttStore.GroupPttUp");
            //com.saki.loger.ViewLoger.info(str);
            this.a = j;
            this.c = i;
            this.d = str;
            this.b = bArr;
            this.type =type;
            super.setSeq((int)mark);
			this.hexmd5=hexmd5;
        }

        /* access modifiers changed from: protected */
        public byte[] getContent() {
            ByteWriter classa = new ByteWriter();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 3));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 3));
            ByteWriter classa2 = new ByteWriter();
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.a));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, User.uin));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, this.b));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(5, (long) this.c));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(6, (this.hexmd5+".amr").getBytes()));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(7, 5));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(8, 9));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(9, 3));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(10, "6.5.0.390".getBytes()));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(12, (type.equals(".amr")) ?2: 6));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(13, 1));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(14, 1));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(15, 1));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(5, classa2.getData()));
            return classa.getData();
        }
    }
}
