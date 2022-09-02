package com.saki.qq.datapacket.pack.send;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.saki.loger.ViewLoger;
import com.saki.qq.datapacket.pack.jce.RequestPacket;
import com.saki.qq.datapacket.pack.jce.WriteJceStruct;
import com.saki.qq.userinfo.User;
import com.saki.tool.HexTool;
import java.util.HashMap;
import java.util.Map;

public class VisitorSvc
{

    public static class ReqFavorite extends T0B01
    {
        /* access modifiers changed from: private */
        public long a;
        /* access modifiers changed from: private */
        public int b = com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg.b_();

        /* renamed from: com.saki.e.c.a.c.classk$a$a reason: collision with other inner class name */
        private class C0006a extends WriteJceStruct
        {

            @Override
            public void readFrom(JceInputStream paramJceInputStream)
            {
                // TODO: Implement this method
            }

            private C0006a()
            {
            }

            public void writeTo(JceOutputStream classe)
            {
                classe.write((JceStruct) new This(), 0);
                classe.write(ReqFavorite.this.a, 1);
                classe.write(0, 2);
                classe.write(new byte[0], 3);
            }
        }

        private class This extends WriteJceStruct
        {

            @Override public void readFrom(JceInputStream paramJceInputStream)
            {
                // TODO: Implement this method
            }

            private This()
            {
            }

            public void writeTo(JceOutputStream classe)
            {
                classe.write(1, 0);
                classe.write(ReqFavorite.this.b, 1);
                classe.write(User.uin, 2);
                classe.write(0, 3);
                classe.write("", 4);
            }
        }

        public ReqFavorite(int i, long j)
        {
            super("VisitorSvc.ReqFavorite");
            //ViewLoger.info(i+" "+j);
            setSeq(i);
            this.a = j;
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            Map hashMap = new HashMap();
            HashMap hashMap2 = new HashMap();
            JceOutputStream com_saki_e_b_a_classe = new JceOutputStream();
            com_saki_e_b_a_classe.write(new C0006a(), 0);
            hashMap2.put("QQService.RespFavorite", com_saki_e_b_a_classe.toByteArray());
            hashMap.put("RespFavorite", hashMap2);
            com_saki_e_b_a_classe = new JceOutputStream();
            com_saki_e_b_a_classe.write(hashMap, 0);
            byte[] a = com_saki_e_b_a_classe.toByteArray();
            byte[] a2 = new RequestPacket((short) 2, (byte) 0, 0, 0, "VisitorSvc", "RespFavorite", a, 0, null, null).toByteArray();
            System.out.println(HexTool.a(a2));
            return a2;
        }
    }
}

