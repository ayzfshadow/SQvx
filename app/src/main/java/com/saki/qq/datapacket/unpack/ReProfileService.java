package com.saki.qq.datapacket.unpack;

import android.util.Log;
import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;
import com.saki.tool.ByteReader;
import com.saki.tool.HexTool;
import java.util.ArrayList;
import com.saki.loger.ViewLoger;

public class ReProfileService
{

    public static class Pb
    {

        /* renamed from: com.saki.e.c.b.classj$a$a reason: collision with other inner class name */
        public static class ReqSystemMsgNew
        {

            /* renamed from: com.saki.e.c.b.classj$a$a$a reason: collision with other inner class name */
            public static class Group extends RecviePack
            {
                public long a;
                public ArrayList<SysMsg> list = new ArrayList<>();

                /* renamed from: com.saki.e.c.b.classj$a$a$a$a reason: collision with other inner class name */
                public class SysMsg
                {
                    public long requstId;
                    public long group;
                    public long uin;
                    public long invitor;
                    public String msg;
                    public String uinName;
                    public String groupName;
                    public String invitorName;
                    public long time;
                    public int type;

					public long sub_type;

					public long group_msg_type;

                    public SysMsg()
                    {
                    }

                    public String toString()
                    {
                        return "[系统消息" + this.type + "]:" + this.requstId + " " + this.group + " " + this.uin;
                    }
                }

                public Group(byte[] bArr)
                {
                    super(bArr);
					//ViewLoger.info("group_new\n"+HexTool.a(bArr));
                    ByteReader unpack = new ByteReader(bArr);
                    if (unpack.readUnsignedInt() == unpack.length())
                    {
                        ProtoBuff classa = new ProtoBuff(unpack.readRestBytes());
                        this.a = classa.readVarint(5);
                        while (true)
                        {
                            byte[] a2 = classa.readLengthDelimited(10);
                            if (a2 != null)
                            {
                                classa.update(a2);
                                SysMsg aVar = new SysMsg();
                                aVar.requstId = classa.readVarint(3);
                                aVar.time = classa.readVarint(4);
                                aVar.uin = classa.readVarint(5);
								
								
                                byte[] a3 = classa.readLengthDelimited(50);
                                if (a3 != null)
                                {
                                    classa.update(a3);
                                    aVar.type = (int) classa.readVarint(1);
                                    aVar.msg = new String(classa.readLengthDelimited(2));
									aVar.sub_type=classa.readVarint(7);
                                    aVar.group = classa.readVarint(10);
                                    aVar.invitor = classa.readVarint(11);
									aVar.group_msg_type=classa.readVarint(12);
                                    byte[] a4 = classa.readLengthDelimited(51);
                                    if (a4 != null)
                                    {
                                        aVar.uinName = new String(a4);
                                    }
                                    byte[] a5 = classa.readLengthDelimited(52);
                                    if (a5 != null)
                                    {
                                        aVar.groupName = new String(a5);
                                    }
                                    byte[] a6 = classa.readLengthDelimited(53);
                                    if (a6 != null)
                                    {
                                        aVar.invitorName = new String(a6);
                                    }
                                }
                                this.list.add(aVar);
                            }
                            else
                            {
                                return;
                            }
                        }
                    }
                    unpack.destroy();
                }
            }
        }
    }
}
