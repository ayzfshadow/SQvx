package com.saki.qq.datapacket.pack.send;

import com.saki.qq.userinfo.User;
import com.saki.qq.datapacket.pack.jce.RequestPacket;
import com.saki.qq.datapacket.pack.jce.WriteJceStruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.qq.taf.jce.*;
import com.saki.qq.datapacket.unpack.*;

public class OnlinePush {

    public static class RespPush extends T0B01 {
        ArrayList<W> a = new ArrayList<>();
        private long b;

        /* renamed from: com.saki.e.c.a.c.classe$a$a reason: collision with other inner class name */
        public class W extends WriteJceStruct
		{

			@Override
			public void readFrom(JceInputStream paramJceInputStream)
			{
				// TODO: Implement this method
			}

            private long b;
            private int c;
            private byte[] d;

            public W(long j, int i, byte[] bArr) {
                this.b = j;
                this.c = i;
                this.d = bArr;
            }

            public void writeTo(JceOutputStream classe) {
                classe.write(this.b, 0);
                classe.write(0, 1);
                classe.write(this.c, 2);
                classe.write(this.d, 3);
            }
        }

        public RespPush(int i, long j, ArrayList<ReOnlinePush.ReqPush.MsgInfo> arrayList) {
            super("OnlinePush.RespPush");
            setSeq(i);
            this.b = j;
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    ReOnlinePush.ReqPush.MsgInfo bVar =  (ReOnlinePush.ReqPush.MsgInfo) it.next();
                    this.a.add(new W(bVar.id, bVar.unknow, bVar.cookie));
                }
            }
        }

        /* access modifiers changed from: protected */
        public byte[] getContent() {
           JceOutputStream classe = new JceOutputStream();
            classe.write((JceStruct) new WriteJceStruct() {

							 @Override
							 public void readFrom(JceInputStream paramJceInputStream)
							 {
								 // TODO: Implement this method
							 }
							 
                public void writeTo(JceOutputStream classe) {
                    classe.write(User.uin, 0);
                    classe.write(RespPush.this.a, 1);
                    classe.write((int) User.disreq, 2);
                }
            }, 0);
            HashMap hashMap = new HashMap();
            hashMap.put("resp", classe.toByteArray());
            JceOutputStream classe2 = new JceOutputStream();
            classe2.write(hashMap, 0);
            return new RequestPacket((short)3, (byte)0, 0, (int) this.b, "OnlinePush", "SvcRespPushMsg", classe2.toByteArray(), 0, null, null).toByteArray();
        }
    }
}
