package com.saki.qq.datapacket.pack.send;

import com.saki.qq.userinfo.User;
import com.saki.qq.datapacket.pack.jce.RequestPacket;
import com.saki.qq.datapacket.pack.jce.WriteJceStruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.qq.taf.jce.*;

public class FriendList
{

    public static class GetTroopListReqV2 extends T0B01
	{
        public GetTroopListReqV2(int i)
		{
            super("friendlist.GetTroopListReqV2");
            setSeq(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
		{
            HashMap<String,byte[]> hashMap = new HashMap<String,byte[]>();
            JceOutputStream classe = new JceOutputStream();
            classe.write((JceStruct) new WriteJceStruct() {
							 @Override public void readFrom(JceInputStream paramJceInputStream)
							 {
								 // TODO: Implement this method
							 }
							 public void writeTo(JceOutputStream classe)
							 {
								 classe.write(User.uin, 0);
								 classe.write(0, 1);
								 classe.write(1, 4);
								 classe.write(5, 5);
							 }
						 }, 0);
            hashMap.put("GetTroopListReqV2", classe.toByteArray());
            JceOutputStream classe2 = new JceOutputStream();
            classe2.write(hashMap, 0);
            return new RequestPacket((short)3, (byte)0, (int)0, (int)com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg.b_(), "mqq.IMService.FriendListServiceServantObj", "GetTroopListReqV2", classe2.toByteArray(), 0, null, null).toByteArray();
        }
		
		
    }

    public static class GetTroopMemberList extends T0B01
	{
        /* access modifiers changed from: private */
        public long a;

        private class Troop extends WriteJceStruct
		{

			@Override
			public void readFrom(JceInputStream paramJceInputStream)
			{
				// TODO: Implement this method
			}

            private Troop()
			{
            }

            public void writeTo(JceOutputStream classe)
			{
                classe.write(User.uin, 0);
                classe.write(GetTroopMemberList.this.a, 1);
                classe.write(0, 2);
                classe.write(GetTroopMemberList.this.a, 3);
                classe.write(2, 4);
            }
        }

        public GetTroopMemberList(int i, long j)
		{
            super("friendlist.getTroopMemberList");
            setSeq(i);
            this.a = j;
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
		{
            HashMap hashMap = new HashMap();
            JceOutputStream classe = new JceOutputStream();
            classe.write((JceStruct) new Troop(), 0);
            hashMap.put("GTML", classe.toByteArray());
            JceOutputStream classe2 = new JceOutputStream();
            classe2.write(hashMap, 0);
            return new RequestPacket((short)3, (byte)0, 0, com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg.b_(), "mqq.IMService.FriendListServiceServantObj", "GetTroopMemberListReq", classe2.toByteArray(), 0, null, null).toByteArray();
        }
    }

    public static class ModifyGroupCardReq extends T0B01
	{
        private G a;

        class G extends WriteJceStruct
		{

			@Override
			public void readFrom(JceInputStream paramJceInputStream)
			{
				// TODO: Implement this method
			}

            private ArrayList<Mc> b = new ArrayList<>();
            private long c;

            public G(long j, long j2, String str)
			{
                this.c = j;
                this.b.add(new Mc(j2, str));
            }

            public void writeTo(JceOutputStream classe)
			{
                classe.write(0, 0);
                classe.write(this.c, 1);
                classe.write(0, 2);
                classe.write(this.b, 3);
            }
        }

        class Mc extends WriteJceStruct
		{

			@Override
			public void readFrom(JceInputStream paramJceInputStream)
			{
				// TODO: Implement this method
			}

            ArrayList<ProfileService> a = new ArrayList<>();
            private long c;
            private String d;

            public Mc(long j, String str)
			{
                this.c = j;
                this.d = str;
            }

            public void writeTo(JceOutputStream classe)
			{
                classe.write(this.c, 0);
                classe.write(1, 1);
                classe.write(this.d, 2);
                classe.write(-1, 3);
                classe.write("", 4);
                classe.write("", 5);
                classe.write("", 6);
            }
        }

        public ModifyGroupCardReq(int i, long j, long j2, String str)
		{
            super("friendlist.ModifyGroupCardReq");
            this.a = new G(j, j2, str);
            setSeq(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
		{
            JceOutputStream classe = new JceOutputStream();
            classe.write((JceStruct) this.a, 0);
            HashMap hashMap = new HashMap();
            hashMap.put("MGCREQ", classe.toByteArray());
            JceOutputStream classe2 = new JceOutputStream();
            classe2.write(hashMap, 0);
            return new RequestPacket((short)3, (byte)0, 0, com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg.b_(), "mqq.IMService.FriendListServiceServantObj", "ModifyGroupCardReq", classe2.toByteArray(), 0, null, null).toByteArray();
        }
    }
}
