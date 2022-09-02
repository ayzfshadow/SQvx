package com.saki.qq.datapacket.unpack;

import android.util.Log;
import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;
import com.saki.qq.userinfo.User;
import com.saki.tool.ByteReader;
import com.saki.tool.HexTool;
import java.util.ArrayList;
import com.saki.loger.DebugLoger;

public class ReMessageSvc
{

    public static class PbGetMsg extends RecviePack
	{
        public ArrayList<Msg> a = new ArrayList<>();

        /* renamed from: com.saki.e.c.b.classg$a$a reason: collision with other inner class name */
        public class Msg
		{
            public long sendTime;
            public long unknown;
            public long code;
            public long fromUin;
            public long toUin;
            public String f;
            public String msg;
            public int type;
            public long applyUin;
            public long groupId;
            public String applyName;

			public long msgId =0;

            public Msg()
			{
            }

            public String toString()
			{
                return "[" + this.sendTime + "](" + this.type + ")来自:[" + this.fromUin + "(" + this.code + ")][" + this.toUin + "]" + this.applyName + "(" + this.applyUin + "):" + this.msg;
            }
        }

        public PbGetMsg(byte[] bArr)
		{
			
            super(bArr);
			DebugLoger.log(HexTool.a(bArr),"");
            ByteReader classd = new ByteReader(bArr);
            if (classd.readInt() == classd.length())
			{
                ProtoBuff classa = new ProtoBuff(checkZip(classd.readRestBytes()));
                //User.d = classa.readLengthDelimited(3);
                while (true)
				{
                    byte[] a2 = classa.readLengthDelimited(5);
                    if (a2 != null)
					{
                        readContent(a2);
                    }
					else
					{
                        return;
                    }
                }
            }
        }

        private void a(byte[] bArr, Msg aVar)
		{
            if (bArr != null)
			{
				ProtoBuff h  = new ProtoBuff(bArr);
				aVar.msgId = h.readVarint(3);
                aVar.f = new String(h.readLengthDelimited(9));
				h.destroy();
            }
        }

        private void readMsg(byte[] bArr, Msg aVar)
		{
            if (bArr != null)
			{
                ProtoBuff classa = new ProtoBuff(bArr);
                a(classa.readLengthDelimited(1), aVar);
                byte[] a2 = classa.readLengthDelimited(2);
                classa.destroy();
                if (a2 != null)
				{
                    ProtoBuff classa2 = new ProtoBuff(a2);
                    byte[] a3 = classa2.readLengthDelimited(1);
                    if (a3 == null)
					{
                        a3 = classa2.readLengthDelimited(12);
                    }
                    if (a3 != null)
					{
						ProtoBuff bg  =new ProtoBuff(a3);
                        aVar.msg = new String(checkZip(bg.readLengthDelimited(1)));
						bg.destroy();
                    }
                }
            }
        }

        private void readContent(byte[] bArr)
		{
			
            ProtoBuff classa = new ProtoBuff(bArr);
            while (true)
			{
				//DebugLoger.log("4","4");
                byte[] a2 = classa.readLengthDelimited(4);
                if (a2 != null)
				{
                    Msg aVar = new Msg();
                    ProtoBuff classa2 = new ProtoBuff(a2);
                    readUin(classa2.readLengthDelimited(1), aVar);
                    byte[] a3 = classa2.readLengthDelimited(3);
					classa2.destroy();
                    if (a3 != null)
					{
                        ProtoBuff classa3 = new ProtoBuff(a3);
                        readMsg(classa3.readLengthDelimited(1), aVar);
                        byte[] a4 = classa3.readLengthDelimited(2);
                        if (a4 != null)
						{

                            ByteReader classd = new ByteReader(a4);
                            aVar.unknown = com.saki.tool.HexTool.Bytes2UnsignedInt32(classd.readBytes(4));
                            try
							{
                                classd.readByte();
                                classd.readBytes(4);
                                classd.readByte();
                                aVar.groupId = com.saki.tool.HexTool.Bytes2UnsignedInt32(classd.readBytesAndDestroy(4));
                            }
							catch (Exception e)
							{
                                e.printStackTrace();
                            }
							
                        }
                    }
                    this.a.add(aVar);
                }
				else
				{
					classa.destroy();
                    return;
                }
            }
        }

        private void readUin(byte[] bArr, Msg aVar)
		{
            ProtoBuff classa = new ProtoBuff(bArr);
            aVar.fromUin = classa.readVarint(1);
            aVar.toUin = classa.readVarint(2);
            aVar.type = (int) classa.readVarint(3);
            aVar.sendTime = classa.readVarint(6);
            byte[] a2 = classa.readLengthDelimited(8);
            if (a2 != null)
			{
                classa.update(a2);
                aVar.code = classa.readVarint(3);
                aVar.unknown = classa.readVarint(4);

            }
            classa.update(bArr);
            aVar.applyUin = classa.readVarint(15);
            byte[] a3 = classa.readLengthDelimited(16);
            if (a3 != null)
			{
                aVar.applyName = new String(a3);
            }
			classa.destroy();
        }
    }

    public static class PbshNotify extends RecviePack
	{
        public PbshNotify(byte[] bArr)
		{
            super(bArr);
        }
    }

    public static class PushForceOffline extends RecviePack
	{
        public PushForceOffline(byte[] bArr)
		{
            super(bArr);
        }
    }
}
