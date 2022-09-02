package com.saki.qq.datapacket.pack.send;

import com.saki.qq.userinfo.User;

import java.util.HashMap;
import java.util.Map;
import com.qq.taf.jce.*;

public class StatSvc
{

    public static class Register extends T0B01
    {

        /* renamed from: com.saki.e.c.a.c.classi$a$a reason: collision with other inner class name */
        private static class JceData extends JceStruct
        {

            @Override
            public void readFrom(JceInputStream paramJceInputStream)
            {
                // TODO: Implement this method
            }

            private JceData()
            {
            }


            public void writeTo(JceOutputStream classe)
            {
                HashMap<String,byte[]> hashMap = new HashMap<String,byte[]>();
                JceOutputStream classe2 = new JceOutputStream();
                classe2.write((JceStruct) new JceStruct() {

                                  @Override
                                  public void readFrom(JceInputStream paramJceInputStream)
                                  {
                                      // TODO: Implement this method
                                  }



                                  public void writeTo(JceOutputStream classe)
                                  {
                                      classe.write(User.uin, 0);
                                      classe.write(7, 1);
                                      classe.write(0, 2);
                                      classe.write("", 3);
                                      classe.write(11, 4);
                                      classe.write(0, 5);
                                      classe.write(0, 6);
                                      classe.write(0, 7);
                                      classe.write(0, 8);
                                      classe.write(0, 9);
                                      classe.write(0, 10);
                                      classe.write(15, 11);
                                      classe.write(1, 12);
                                      classe.write("", 13);
                                      classe.write(0, 14);
                                      if (User.imei != null && !User.imei.isEmpty())
                                      {
                                          classe.write(User.imei.getBytes(), 16);
                                          
                                      }
                                      else
                                      {
                                          classe.write(new byte[]{-113, -91, 126, -78, 44, 45, -30, -75, -10, -71, 27, 85, -66, 85, -28, 127}, 16);
                                      }
                                      classe.write(2052, 17);
                                      classe.write(0, 18);
                                      if (User.devicemode != null && !User.devicemode.isEmpty())
                                      {
                                          classe.write(User.devicemode, 19);
                                          classe.write(User.devicemode, 20);
                                      }
                                      else
                                      {
                                          classe.write("老年机", 19);
                                          classe.write("老年机", 20);
                                      }
                                      classe.write("7.1.2", 21);
                                      
                                  }
                              }, 0);
                hashMap.put("SvcReqRegister", classe2.toByteArray());
                classe.write(hashMap, 0);
            }
        }

        public Register(int i)
        {
            super("StatSvc.register");
            setSeq(i);
			//setseqid(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            return new com.saki.qq.datapacket.pack.jce.RequestPacket((short)3, (byte) 0, 1819559151, 0, "PushService", "SvcReqRegister", new JceData().toByteArray(), 0, null, null).toByteArray();
        }
    }

    public static class SimpleGet extends T0B01
    {
		
		private static class JceData extends JceStruct
        {

            @Override
            public void readFrom(JceInputStream paramJceInputStream)
            {
                // TODO: Implement this method
            }

            private JceData()
            {
            }


            public void writeTo(JceOutputStream classe)
            {
                HashMap<String,byte[]> hashMap = new HashMap<String,byte[]>();
                JceOutputStream classe2 = new JceOutputStream();
                classe2.write((JceStruct) new JceStruct() {

                                  @Override
                                  public void readFrom(JceInputStream paramJceInputStream)
                                  {
                                      // TODO: Implement this method
                                  }



                                  public void writeTo(JceOutputStream out)
                                  {
                                      out.write ((int)User.uin, 0);
									  out.write((byte)7, 1);
									  out.write("", 2);
									  out.write ((byte)11, 3);
									  out.write ((byte)0, 4);
									  out.write ((byte)0, 5);
									  out.write ((byte)0, 6);
									  out.write((byte)0, 7);
									  out.write((byte)0, 8);
									  out.write ((byte)0, 9);
									  out.write ((byte)0, 10);
									  out.write ((byte)0, 11);

                                  }
                              }, 0);
                hashMap.put("SvcReqGet", classe2.toByteArray());
                classe.write(hashMap, 0);
            }
        }
		
		
        public SimpleGet(int i)
        {
            super("StatSvc.get");
            setSeq(i);
        }

        /* access modifiers changed from: protected */
        public byte[] getContent()
        {
            return new com.saki.qq.datapacket.pack.jce.RequestPacket((short)3, (byte) 0, 1819559151, 0, "PushService", "SvcReqGet", new JceData().toByteArray(), 0, null, null).toByteArray();
        }
    }
}

