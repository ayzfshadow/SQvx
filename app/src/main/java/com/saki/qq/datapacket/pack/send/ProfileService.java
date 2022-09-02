package com.saki.qq.datapacket.pack.send;


import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.saki.qq.datapacket.pack.ByteWriter;
import com.saki.qq.datapacket.pack.jce.WriteJceStruct;
import com.saki.qq.userinfo.User;
import java.util.HashMap;
import com.saki.qq.datapacket.pack.jce.RequestPacket;

public class ProfileService {

    public static class GroupMngReq extends T0B01
    {

        private long groupid;
        public GroupMngReq(long groupid,int i){
            super("ProfileService.GroupMngReq");
            this.groupid=groupid;
            setSeq(i);
        }

        @Override
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
                                 classe.write(2, 0);
                                 classe.write(User.uin, 1);
                                 classe.write(new ByteWriter().writeInt(User.uin).writeInt(groupid).getDataAndDestroy(), 2);
                                 classe.write(0, 3);
                                 classe.write("", 4);
                                 classe.write(0, 5);
                                 classe.write(0, 6);
                                 classe.write(0, 7);
                                 classe.write(0, 8);
                                 classe.write(0, 9);
                                 classe.write(0, 10);
                                 classe.write("", 11);
                                 classe.write("", 12);
                             }
                         }, 0);
            hashMap.put("GroupMngReq", classe.toByteArray());
            JceOutputStream classe2 = new JceOutputStream();
            classe2.write(hashMap, 0);
            return new RequestPacket((short)3, (byte)0, (int)0, (int)com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg.b_(), "KQQ.ProfileService.ProfileServantObj", "GroupMngReq", classe2.toByteArray(), 0, null, null).toByteArray();
            
        }
        
        
        
    }
    
    public static class Pb {

        /* renamed from: com.saki.e.c.a.c.classf$a$a reason: collision with other inner class name */
        public static class ReqSystemMsgAction {

            /* renamed from: com.saki.e.c.a.c.classf$a$a$a reason: collision with other inner class name */
            public static class Group extends T0B01 {
                private long a;
                private long b;
                private long c;
                private boolean d;

				private long group_msg_type;

				private long sub_type;

                public Group(int seq, long requestId, long uin, long group, long sub_type,long group_msg_type,boolean z) {
                    super("ProfileService.Pb.ReqSystemMsgAction.Group");
                    setSeq(seq);
                    this.a = requestId;
                    this.b = uin;
                    this.c = group;
                    this.d = z;
					this.sub_type=sub_type;
					this.group_msg_type=group_msg_type;
                }

                /* access modifiers changed from: protected */
                public byte[] getContent() {
                   return new ByteWriter()
						.writePb(1, 2)
						.writePb(2, this.a)
						.writePb(3, this.b)
						.writePb(4, 1)
						.writePb(5, sub_type)
						.writePb(6, 0)
						.writePb(7, group_msg_type)
						.writePb(8, new ByteWriter()
								 .writePb(1, (long) (this.d ? 11 : 12))
								 .writePb(2, this.c).getDataAndDestroy())
						.writePb(9, 1000).getDataAndDestroy();
                }
            }
        }

        public static class ReqSystemMsgNew {

            /* renamed from: com.saki.e.c.a.c.classf$a$b$a reason: collision with other inner class name */
            public static class Group extends T0B01 {
                public Group(int i) {
                    super("ProfileService.Pb.ReqSystemMsgNew.Group");
                    setSeq(i);
                }

                /* access modifiers changed from: protected */
                public byte[] getContent() {
                    ByteWriter classa = new ByteWriter();
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, 20));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 0));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(3, 0));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(4, 1000));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(5, 3));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(6, com.saki.tool.HexTool.hextobytes("08 01 10 01 18 01 28 01 30 01 38 01")));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(8, 0));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(9, 0));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(10, 1));
                    return classa.getData();
                }
            }
        }

        public static class ReqSystemMsgRead {

            /* renamed from: com.saki.e.c.a.c.classf$a$c$a reason: collision with other inner class name */
            public static class Group extends T0B01 {
                private long a;

                public Group(int i, long j) {
                    super("ProfileService.Pb.ReqSystemMsgRead.Group");
                    setSeq(i);
                    this.a = j;
                }

                /* access modifiers changed from: protected */
                public byte[] getContent() {
                    ByteWriter classa = new ByteWriter();
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.a));
                    classa.writeBytes(com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(2, 3));
                    return classa.getDataAndDestroy();
                }
            }
        }
    }
}
