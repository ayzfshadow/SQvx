package com.saki.qq.datapacket.unpack;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.saki.loger.ViewLoger;
import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;
import com.saki.qq.userinfo.User;
import com.saki.tool.ByteReader;
import com.saki.tool.HexTool;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.saki.loger.DebugLoger;

public class ReOnlinePush {

    public static class PbPushDisMsg extends PbPushGroupMsg {
        public PbPushDisMsg(byte[] bArr) throws Exception {
            super(bArr);
        }

        /* access modifiers changed from: protected */
        public int getGroupDateIndex() {
            return 13;
        }

        /* access modifiers changed from: protected */
        public int getGroupNameIndex() {
            return 5;
        }
    }

    public static class PbPushGroupMsg extends RecviePack {
        public long fromUin;
        public long toUin;
        public long groupId;
        public String groupName;
        public String sendName;
        public String fontName;
        public String sendTitle;
        public long messageId;
        public long sendTime;
        
        public HashMap<String, ArrayList> msg = new HashMap<>();

        public long messageIndex;

        public PbPushGroupMsg(byte[] bArr) throws Exception {
            super(bArr);
            ByteReader classd = new ByteReader(bArr);
            if (classd.readUnsignedInt() == classd.length()) {
                ProtoBuff pb = new ProtoBuff(classd.readRestBytes());
                byte[] a = pb.readLengthDelimited(1);
                pb.update(a);
                readUinInfo(pb.readLengthDelimited(1));
                readFontStyleInfo(pb.readLengthDelimited(2));
                readContent(pb.readLengthDelimited(3));
                pb.destroy();
            }
            classd.destroy();
        }

        private void addMsg(String str, String str2) {
            ArrayList<String> arrayList = this.msg.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList<String>();
                this.msg.put(str, arrayList);
            }
            arrayList.add(str2);
        }

        private void readContent(byte[] bArr) {
            ProtoBuff classa = new ProtoBuff(bArr);
            classa.update(classa.readLengthDelimited(1));
            readOther(classa.readLengthDelimited(1));
            while (true) {
                byte[] a2 = classa.readLengthDelimited(2);
                if (a2 != null) {
                    readMsg(a2);
                    readTitle(a2);
                } else {
                    classa.destroy();
                    return;
                }
            }
        }

        private void readFontStyleInfo(byte[] bArr) throws Exception {
          //  ViewLoger.info(HexTool.a(bArr));
            ProtoBuff classa = new ProtoBuff(bArr);
            int count =(int) classa.readVarint(1);
            classa.destroy();
            if(count>1){
                throw new Exception("长消息过滤");
            }
        }

        private void readMsg(byte[] bArr) {
            //Log.d("msg",HexTool.a(bArr));
            ProtoBuff pb =new ProtoBuff(new byte[0]);
            byte[] a2 = pb.update(bArr).readLengthDelimited(1);
            if (a2 != null) {
                String str = "";
                byte[] a3 = pb.update(a2).readLengthDelimited(1);
                if (a3 != null) {
                    str = new String(a3);
                    addMsg("msg", str);
                }
                byte[] a4 = pb.update(a2).readLengthDelimited(3);
                if (a4 != null) {
                    ByteReader classd = new ByteReader(a4);
                    classd.readUnsignedShort();
                    classd.readUnsignedInt();
                    classd.readByte();
                    addMsg("at", com.saki.tool.HexTool.Bytes2UnsignedInt32(classd.readBytes(4)) + str);
                    classd.destroy();
                }
            }
            byte[] a5 = pb.update(bArr).readLengthDelimited(8);
            if (a5 != null) {
                addMsg("img", new String(pb.update(a5).readLengthDelimited(2)));
            }
            byte[] a6 = pb.update(bArr).readLengthDelimited(12);
            if (a6 != null) {
                addMsg("xml", new String(checkZip(pb.update(a6).readLengthDelimited(1))));
            }
            byte[] a7 = pb.update(bArr).readLengthDelimited(51);
            if (a7 != null) {
                addMsg("json", new String(checkZip(pb.update(a7).readLengthDelimited(1))));
            }
            pb.destroy();
        }

        private void readOther(byte[] bArr) {
            ProtoBuff pb =new ProtoBuff(new byte[0]);
            pb.update(bArr);
            this.sendTime = pb.readVarint(2);
            this.messageId = pb.readVarint(3);
            this.fontName = new String(pb.readLengthDelimited(9));
            pb.destroy();
        }

        private void readTitle(byte[] bArr) {
          ProtoBuff pb=  new ProtoBuff(bArr);
            byte[] a2 = pb.readLengthDelimited(16);
            if (a2 != null) {
                byte[] a3 = pb.update(a2).readLengthDelimited(7);
                if (a3 != null) {
                    this.sendTitle = new String(a3);
                }
            }
            pb.destroy();
        }

        private void readTroopInfo(byte[] bArr) {
            ProtoBuff classa = new ProtoBuff(bArr);
            this.groupId = classa.readVarint(1);
            this.sendName = new String(classa.readLengthDelimited(4));
            this.groupName = new String(classa.readLengthDelimited(getGroupNameIndex()));
            classa.destroy();
        }

        private void readUinInfo(byte[] bArr) {
            ProtoBuff classa = new ProtoBuff(bArr);
            this.fromUin = classa.readVarint(1);
            this.toUin = classa.readVarint(2);
            this.messageIndex = classa.readVarint(5);
            readTroopInfo(classa.readLengthDelimited(getGroupDateIndex()));
            classa.destroy();
        }

        /* access modifiers changed from: protected */
        public int getGroupDateIndex() {
            return 9;
        }

        /* access modifiers changed from: protected */
        public int getGroupNameIndex() {
            return 8;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PbPushGroupMsg)) {
                return false;
            }
            PbPushGroupMsg bVar = (PbPushGroupMsg) obj;
            return this.fromUin == bVar.fromUin && this.toUin == bVar.toUin && this.groupId == bVar.groupId && this.sendTime == bVar.sendTime && this.messageId == bVar.messageId;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("sendUin:" + this.fromUin + "\r\n");
            sb.append("toUin:" + this.toUin + "\r\n");
            sb.append("groupId:" + this.groupId + "\r\n");
            sb.append("groupName:" + this.groupName + "\r\n");
            sb.append("sendName:" + this.sendName + "\r\n");
            sb.append("fontName:" + this.fontName + "\r\n");
            sb.append("sendTitle:" + this.sendTitle + "\r\n");
            sb.append("requstId:" + this.messageId + "\r\n");
            sb.append("sendTime:" + this.sendTime + "\r\n");
            sb.append("msg:" + this.msg + "");
            return sb.toString();
        }
    }

    public static class PbPushTransMsg extends RecviePack {
        public long code =0;
        public long groupId=0;
        public long requstId=0;
        public long uin=0;
        public long operator=0;
        public int type=0;
        public long time=0;
       
        private static List<Long> reduplicatemsg = new ArrayList<Long>();
        public PbPushTransMsg(byte[] bArr) throws Exception {
            super(bArr);
			//ViewLoger.info(HexTool.a(bArr));
			boolean z = true;
            ProtoBuff pb =new ProtoBuff(new byte[0]);
            ByteReader classd = new ByteReader(bArr);
            if (classd.readUnsignedInt() == classd.length()) {
                ProtoBuff classa = pb.update(classd.readRestBytes());
				
				
				long groupCode = classa.readVarint(1);
				int type = (int) classa.readVarint(3);
				int requestId = (int) classa.readVarint(5);
				if (this.reduplicatemsg.contains((long)requestId))
				{
					this.reduplicatemsg.remove((long)requestId);
					pb.destroy();
					throw new Exception();
				}
				this.reduplicatemsg.add((long)requestId);
				long time = classa.readVarint(7);
				ByteReader reader = new ByteReader(classa.readLengthDelimited(10));
				long groupUin = reader.readUnsignedInt();
				switch (type)
				{
					case 44:{//有人被升级为管理员/降级为成员
							reader.readBytes(1);
							int upordown = reader.readByte();
							switch (upordown)
							{
								case 1:{
										long honorUin=reader.readUnsignedInt();
										
										this.code = groupCode;
										this.time = time;
										this.groupId = groupUin;
										this.uin = honorUin;
										this.type=1;
									}break;
								case 0:{
										long dishonorUin=reader.readUnsignedInt();
										
										
										this.code = groupCode;
										this.time = time;
										this.groupId = groupUin;
										this.uin = dishonorUin;
										this.type=0;
										//this.robot.call(event);
									}break;
								default:{
										System.out.println("未知的成员等级变更");
									}
							}
						}break;
					case 34:{//主动退群/被踢出
							int o =reader.readByte();
							if (o != 1)
							{
								System.out.println("意料之外的类型: "+o);
							}
							long leavedUin=reader.readUnsignedInt();
							int outType = reader.readUnsignedByte();
							switch (outType)
							{
								case 130:{//主动退出
										
										this.code = groupCode;
										this.time = time;
										this.groupId = groupUin;
										this.uin = leavedUin;
										this.type=130;
									}break;
								case 131:{//被踢出
										
										this.code = groupCode;
										this.time = time;
										this.groupId = groupUin;
										this.uin = leavedUin;
										this.operator = reader.readUnsignedInt();
										//this.robot.call(event);
										this.type=131;
									}break;
								default:{
										System.out.println(outType);
										System.out.println("未知的成员变更");
									}
							}
						}break;
				}
				reader.destroy();
				
                
                User.disreq = classa.readVarint(11);
            }
            classd.destroy();
            pb.destroy();
        }

        public boolean equals(Object obj) {
            return obj instanceof PbPushTransMsg ? ((PbPushTransMsg) obj).requstId == this.requstId : super.equals(obj);
        }
    }

    public static class ReqPush extends RecviePack {
        public List<listPack> a = new ArrayList<listPack>();
        public long b;

        public static class listPack extends JceStruct
		{

			public String pckgname;

			public listPack(String pckgname){
				this.pckgname=pckgname;
			}
			
			@Override
			public void writeTo(JceOutputStream paramJceOutputStream)
			{
				// TODO: Implement this method
			}
			
            public ArrayList<MsgInfo> a = new ArrayList<>();

            public void readFrom(JceInputStream classd) {
                this.a.add(new MsgInfo());
				
                this.a = (ArrayList) classd.readobj(this.a, 2, true);
                User.disreq = classd.read(Long.MAX_VALUE, 3, true);
            }
        }

        public static class MsgInfo extends JceStruct
		{

			

			@Override
			public void writeTo(JceOutputStream paramJceOutputStream)
			{
				// TODO: Implement this method
			}

            public long id;
            public int unknow;
            public byte[] cookie;
			public int type;
			public byte[] data;
			
            public void readFrom(JceInputStream classd) {
                this.id = classd.read(Long.MAX_VALUE, 0, true);
				this.type =classd.read(Short.MAX_VALUE, 2, true);
                this.unknow = classd.read(Short.MAX_VALUE, 3, true);
				this.data = classd.read(new byte[0], 6, true);
                this.cookie = classd.read(new byte[0], 8, true);
            }
        }

        public ReqPush(byte[] bArr) {
            super(bArr);
			
			DebugLoger.log("reqpush",HexTool.a(bArr));
            ByteReader classd = new ByteReader(bArr);
            if (classd.readUnsignedInt() == classd.length()) {
                com.saki.qq.datapacket.pack.jce.RequestPacket classd2 = new com.saki.qq.datapacket.pack.jce.RequestPacket();
                classd2.readFrom(new JceInputStream(classd.readRestBytes()));
                this.b = (long) classd2.iRequestId;
                JceInputStream classd3 = new JceInputStream(classd2.sBuffer);
                HashMap<String,HashMap<String,byte[]>> hashMap = new HashMap<String,HashMap<String,byte[]>>();
                HashMap<String,byte[]> hashMap2 = new HashMap<String,byte[]>();
                hashMap2.put("", new byte[0]);
                hashMap.put("", hashMap2);
                HashMap<String,HashMap<String,byte[]>> hashMap3 = (HashMap<String,HashMap<String,byte[]>>) classd3.readobj(hashMap, 0, true);
                for (String str : hashMap3.keySet()) {
                    HashMap<String,byte[]> hashMap4 = hashMap3.get(str);
                    for (String str2 : hashMap4.keySet()) {
						//DebugLoger.log("key",str2);
                        this.a.add( (listPack) new JceInputStream(hashMap4.get(str2)).readunnew((JceStruct) new listPack(str2), 0, true));
                    }
                }
            }
            classd.destroy();
        }
    }
}

