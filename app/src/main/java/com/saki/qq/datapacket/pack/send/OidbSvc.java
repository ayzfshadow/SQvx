package com.saki.qq.datapacket.pack.send;

import com.saki.qq.userinfo.Token;
import com.saki.qq.userinfo.User;
import com.saki.tool.HexTool;

public class OidbSvc
{

    public static class Ox496 extends T0B01
    {
        public Ox496(int i)
        {
            super("OidbSvc.0x496");
            setSeq(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            return HexTool.hextobytes("08 96 09 10 00 22 04 20 01 28 01");
        }
    }

    public static class Ox570_8 extends T0B01
    {
        private long a;
        private long b;
        private int c;

        public Ox570_8(int i, long j, long j2, int i2)
        {
            super("OidbSvc.0x570_8");
            setSeq(i);
            this.a = j;
            this.b = j2;
            this.c = i2;
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            com.saki.qq.datapacket.pack.ByteWriter classa = new com.saki.qq.datapacket.pack.ByteWriter();
            classa.writeInt(this.a);
            classa.writeByte((byte)32);
            classa.writeShort(1);
            classa.writeInt(this.b);
            classa.writeInt(this.c);
            byte[] b2 = classa.getData();
            classa.reCreate();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 1392));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 8));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, b2));
            return classa.getData();
        }
    }

    public static class Ox59f extends SendPack
    {
        public Ox59f(int i)
        {
            super("OidbSvc.0x59f");
            setSeq((int)i);
            setseqid((int)i);
            setToken0044(Token._2c);
            setToken004c(Token._4c);
            this.setappid((long)537053443);//537053443
            if (User.imei != null && !User.imei.isEmpty())
            {
                setImei(User.imei);
            }
            else
            {
                setImei("000000000000000");
            }
            setVersion("|460020597543391|A7.2.5.310885");
        }

        /* access modifiers changed from: protected */
        @Override public byte[] getContent()
        {
            return com.saki.tool.HexTool.hextobytes("08 9F 0B 10 01 18 00 22 00 32 0D 61 6E 64 72 6F 69 64 20 36 2E 35 2E 30");
        }

        /* access modifiers changed from: protected */
        @Override public byte[] d()
        {
            return com.saki.qq.userinfo.Key.c;
        }
    }

    public static class Ox7a2_0 extends T0B01
    {
        public Ox7a2_0(int i)
        {
            super("OidbSvc.0x7a2_0");
            setSeq(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            com.saki.qq.datapacket.pack.ByteWriter classa = new com.saki.qq.datapacket.pack.ByteWriter();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 1954));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 0)));
            return classa.getDataAndDestroy();
        }
    }

    public static class Ox7c4_0 extends T0B01
    {
        public Ox7c4_0(int i)
        {
            super("OidbSvc.0x7c4_0");
            setSeq(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            com.saki.qq.datapacket.pack.ByteWriter classa = new com.saki.qq.datapacket.pack.ByteWriter();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, User.uin));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(4, 100));
            byte[] b = classa.getDataAndDestroy();
            classa.reCreate();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, b));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 2));
            byte[] b2 = classa.getDataAndDestroy();
            classa.reCreate();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 1988));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, b2));
            return classa.getDataAndDestroy();
        }
    }

    public static class Ox89a_0 extends T0B01
    {
        private long a;
        private boolean b;

        public Ox89a_0(int i, long j, boolean z)
        {
            super("OidbSvc.0x89a_0");
            setSeq(i);
            this.a = j;
            this.b = z;
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            com.saki.qq.datapacket.pack.ByteWriter classa = new com.saki.qq.datapacket.pack.ByteWriter();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.a));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(17, this.b ? 268435455 : 0)));
            byte[] b2 = classa.getData();
            classa.reCreate();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 2202));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, b2));
            return classa.getData();
        }
    }

    public static class Ox8a0_0 extends T0B01
    {
        private long a;
        private long b;

        public Ox8a0_0(int i, long j, long j2)
        {
            super("OidbSvc.0x8a0_0");
            setSeq(i);
            this.a = j2;
            this.b = j;
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            com.saki.qq.datapacket.pack.ByteWriter classa = new com.saki.qq.datapacket.pack.ByteWriter();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 5));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, this.a));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            byte[] b2 = classa.getData();
            classa.reCreate();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.b));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, b2));
            byte[] b3 = classa.getData();
            classa.reCreate();
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 2208));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
            classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, b3));
            return classa.getData();
        }
    }
}
