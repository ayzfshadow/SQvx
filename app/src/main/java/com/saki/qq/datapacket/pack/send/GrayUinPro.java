package com.saki.qq.datapacket.pack.send;

import com.qq.taf.jce.JceInputStream;
import com.qq.taf.jce.JceOutputStream;
import com.qq.taf.jce.JceStruct;
import com.saki.qq.datapacket.pack.jce.RequestPacket;
import com.saki.qq.userinfo.Device;
import com.saki.qq.userinfo.Token;
import java.util.HashMap;
import com.saki.qq.userinfo.User;

public class GrayUinPro
{
	public static class Check extends T0A02 {

        private static class JceData extends JceStruct {
            @Override
            public void readFrom(JceInputStream in) {

            }

            @Override
            public void writeTo(JceOutputStream out) {
                HashMap<String, byte[]> m = new HashMap<String, byte[]>();
                JceOutputStream o = new JceOutputStream();
                o.write(new JceStruct() {
						@Override
						public void readFrom(JceInputStream in) {
						}

						@Override
						public void writeTo(JceOutputStream out) {
							out.write(537053443, 1);
							out.write(String.valueOf(User.uin), 2);
							out.write("", 3);
							out.write("", 4);
						}
					}, 0);
                m.put("req", o.toByteArray());
                out.write(m, 0);
            }
        }

        public Check(int seq) {
            super("GrayUinPro.Check");
            setSeq((int)seq);
            setseqid((int)seq);
            setappid(537053443);
            setImei(Device.imei);
            setVersion("|310260000000000|A6.5.0.215311");
            setToken0044(Token._2c);
        }

        @Override
        public byte[] getContent() {
            return new RequestPacket((short) 3, (byte) 0, 0,
									 MessageSvc.PbSendMsg.b_(),
									 "KQQ.ConfigService.ConfigServantObj", "ClientReq",
									 new JceData().toByteArray(), 0, null, null).toByteArray();
        }
    }
}
