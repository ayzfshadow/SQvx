package com.saki.qq.datapacket.pack.send;

import com.saki.qq.datapacket.pack.ByteWriter;
import com.saki.qq.datapacket.pack.send.data.MsgData;
import com.saki.qq.userinfo.User;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

public class MessageSvc {

    public static class PbGetMsg extends T0B01 {
        public static byte[] a = new byte[0];

        public PbGetMsg(int i) {
            super("MessageSvc.PbGetMsg");
            setSeq(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent() {
			long time = getGreenwichTime()+30000;
			return new  ByteWriter().writePb(2,new ByteWriter()
								  .writePb(1,time)
								  .writePb(2,time)
								  .writePb(5,1795394314)
								  .writePb(9,3548060570l)
								  .writePb(11,1546087334)
								  .writePb(13,time)
								  .getDataAndDestroy())
				.writePb(3,0)
				.writePb(4,20)
				.writePb(5,3)
				.writePb(6,1)
				.writePb(7,1)
				
             .getDataAndDestroy();
        }

        /* access modifiers changed from: protected */
        public void b() {
            packContent();
        }

        
	
		public static long getGreenwichTime()
		{
			Date nowTime = new Date(); // 要转换的时间

			Calendar cal = Calendar.getInstance();

			cal.setTimeInMillis(nowTime.getTime());

			cal.add(Calendar.HOUR, -8);

			return cal.getTime().getTime() / 1000;
		}
    }

    public static class PbSendMsg extends T0B01 {
        private static int i = 10000;
        public long a = -1;
        public long b = -1;
        public long c = -1;
        public int d = 1;
        public ArrayList<MsgData> e = new ArrayList<>();

        private long packagelength=1;

        private int packageindex=0;

        private long msgid;

        public PbSendMsg(int i2, long j, long j2, long j3) {
            super("MessageSvc.PbSendMsg");
            setSeq(i2);
            this.a = j;
            this.c = j2;
            this.b = j3;
            if (j3 != -1) {
                if (j2 != -1) {
                    this.d = 3;
                } else {
                    this.d = 1;
                }
            } else if (j == -1) {
                this.d = 2;
            } else {
                this.d = 4;
            }
        }

        public void setpackagelength(long packagelength)
        {
            this.packagelength=packagelength;
        }

        public void setpackageindex(int packageindex)
        {
            this.packageindex=packageindex;
        }

        public void setmsgid(long msgid)
        {
            this.msgid=msgid;
        }
        
        public static final int b_() {
            int i2 = i;
            i = i2 + 1;
            return i2;
        }

        private byte[] getStyle() {
            ByteWriter classa = new ByteWriter();
            classa.writePb(1, this.packagelength);
            classa.writePb(2, this.packageindex);
            classa.writePb(3, this.msgid);
            return com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, classa.getData());
        }

        private byte[] getTarget() {
            if (this.d != 3) {
                return this.d == 1 ? com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.b))) : this.d == 2 ? com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(2, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.c))) : com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeVarint(1, this.a)));
            }
            ByteWriter classa = new ByteWriter();
            classa.writePb(1, this.c);
            classa.writePb(2, this.b);
            return com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(3, classa.getData()));
        }

        public void addMsg(MsgData classd) {
            if (classd != null) {
                this.e.add(classd);
            }
        }

		
		public static long getGreenwichTime()
		{
			Date nowTime = new Date(); // 要转换的时间

			Calendar cal = Calendar.getInstance();

			cal.setTimeInMillis(nowTime.getTime());

			cal.add(Calendar.HOUR, -8);

			return cal.getTime().getTime() / 1000;
		}
		
		
        /* access modifiers changed from: protected */
        public byte[] getContent() {
          //  Log.d("ccc","ccccc1111");
            ByteWriter classa = new ByteWriter();
            classa.writeBytes(getTarget());
            classa.writeBytes(getStyle());
            ByteWriter classa2 = new ByteWriter();
            Iterator it = this.e.iterator();
            while (it.hasNext()) {
                classa2.writeBytes(((MsgData) it.next()).getBytes());
            }
            classa.writePb(3, com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(1, classa2.getDataAndDestroy()));
            classa.writePb(4, (long) b_());
            classa.writePb(5, (long) Math.abs(new Random().nextInt()));
			long time = getGreenwichTime()+30000;
            if (this.d == 3 || this.d == 1) {
                classa.writePb(6, new ByteWriter()
							   .writePb(1,time)
							   .writePb(2,time)
							   .writePb(5,1795394314)
							   .writePb(9,3548060570l)
							   .writePb(11,1546087334)
							   .writePb(13,time)
							   .getDataAndDestroy());
            }
            int i2 = 3 - this.d;
            if (i2 < 0) {
                i2 = 0;
            }
            classa.writePb(8, (long) i2);
            byte[]ggg =  classa.getDataAndDestroy();
           //ViewLoger.info(HexTool.a(ggg));
            return ggg;
        }
    }
}
