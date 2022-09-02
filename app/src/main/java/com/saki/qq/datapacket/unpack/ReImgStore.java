package com.saki.qq.datapacket.unpack;

import com.saki.loger.LogcatLoger;
import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;
import com.saki.tool.ByteReader;
import com.saki.tool.HexTool;

public class ReImgStore {

    public static class GroupPicUp extends RecviePack {
        public long requestid;
        public byte[] unknowData;
        public long id;
        public byte[] md5;

        public GroupPicUp(byte[] bArr) {
            super(bArr);
			LogcatLoger.d("picccccccc",HexTool.a(bArr));
            ByteReader unpack = new ByteReader(bArr);
            if (unpack.readUnsignedInt() == unpack.length()) {
                ProtoBuff pb = new ProtoBuff(unpack.readRestBytes());
                this.requestid = pb.readVarint(1);
                byte[] a2 = pb.readLengthDelimited(3);
                if (a2 != null) {
                    pb.update(a2);
                    if (pb.readLengthDelimited(5) != null) {
                        this.md5 = pb.readLengthDelimited(1);
                    }
                    this.unknowData = pb.readLengthDelimited(8);
                    this.id = pb.readVarint(9);
                    
                }
                pb.destroy();
            }
        }
    }
}
