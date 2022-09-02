package com.saki.qq.datapacket.unpack;

import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;

public class RePttStore {

    public static class GroupPttUp extends RecviePack {
        public long id;
        public long ip1;
        public long ip2;
        public long un3;
        public byte[] ukey;
        public long un4;
        public byte[] key2;

        public GroupPttUp(byte[] bArr) {
            super(bArr);
            ProtoBuff protoBuff = new ProtoBuff(bArr);
            this.id = protoBuff.readVarint(1);
            byte[] a2 = protoBuff.readLengthDelimited(5);
            if (a2 != null) {
                protoBuff.update(a2);
				this.ip1 = protoBuff.readVarint(5);
				this.ip2 = protoBuff.readVarint(5);
				this.un3 = protoBuff.readVarint(5);
				this.ukey = protoBuff.readLengthDelimited(7);
				this.un4 = protoBuff.readVarint(8);
				this.key2 = protoBuff.readLengthDelimited(11);
            }
            protoBuff.destroy();
        }
    }
}
