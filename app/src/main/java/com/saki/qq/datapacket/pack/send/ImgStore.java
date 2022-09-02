package com.saki.qq.datapacket.pack.send;

import android.support.v4.media.session.PlaybackStateCompat;
import com.saki.qq.datapacket.pack.ByteWriter;
import com.saki.qq.userinfo.imgstore;

public class ImgStore {

    public static class GroupPicUp extends T0B01 {
        private long a;
        private imgstore b;

        
        
        public GroupPicUp(int mark, long j, imgstore classc) {
            super("ImgStore.GroupPicUp");
            super.setSeq((int)mark);
            this.a = j;
            this.b = classc;
            
        }

        /* access modifiers changed from: protected */
        public byte[] getContent() {
            ByteWriter classa = new ByteWriter();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 3));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 1));
            ByteWriter classa2 = new ByteWriter();
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.a));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, com.saki.qq.userinfo.User.uin));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, this.b.c));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(5, (long) this.b.a.length));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(6, this.b.toString().getBytes()));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(7, 5));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(8, 9));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(9, 1));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(10, (long) this.b.d));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(11, (long) this.b.e));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(12, 1000));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(13, "6.5.0.390".getBytes()));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(14, 1007));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(3, classa2.getData()));
            return classa.getData();
        }
    }

    public static class UP {
        byte[] a;
        byte[] b;
        byte[] c;
        int d;

        public UP(byte[] bArr, byte[] bArr2) {
            this.a = bArr;
            this.d = bArr.length;
            this.b = com.saki.tool.Code.md5(bArr);
            this.c = bArr2;
        }

        public byte[] a() {
            ByteWriter classa = new ByteWriter();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 1));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, String.valueOf(com.saki.qq.userinfo.User.uin).getBytes()));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(3, "PicUp.DataUp".getBytes()));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(4, (long) com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg.b_()));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(5, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(6, 4660));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(7, (long) PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(8, 2));
            ByteWriter classa2 = new ByteWriter();
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, classa.getData()));
            classa.reCreate();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, (long) this.d));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(4, (long) this.d));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(6, this.c));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(8, this.b));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(9, this.b));
            classa2.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, classa.getData()));
            classa.reCreate();
            classa.writeByte((byte)40);
            classa.writeInt(classa2.length());
            classa.writeInt(this.d);
            classa.writeBytes(classa2.getData());
            classa.writeBytes(this.a);
            classa.writeByte((byte)41);
            return classa.getData();
        }
    }
}
