
// Decompiled by Jadx - 1000ms
//
package com.saki.qq.datapacket.pack.send.data;

import com.saki.qq.userinfo.User;
import com.saki.qq.datapacket.pack.ByteWriter;
import com.saki.tool.HexTool;

public class PttMsg extends MsgData {
	private byte[] a;
	private String b;
	private long c;
	private long d;
	private long e;
	private byte[] f;

	public PttMsg(String str, byte[] bArr, long j, long j2, long j3, byte[] bArr2) {
		this.b = str.replaceAll("\\.[a-z0-9]*$","\\.amr");
        //com.saki.loger.ViewLoger.info(this.b);
		this.a = bArr;
		this.c = j;
		this.d = j2;
		this.e = j3;
		this.f = bArr2;
	}

	public byte[] getBytes() {
		ByteWriter com_saki_e_c_a_classa = new ByteWriter();
		com_saki_e_c_a_classa.writePb(1, 4);
		com_saki_e_c_a_classa.writePb(2, User.uin);
		com_saki_e_c_a_classa.writePb(4, this.a);
		com_saki_e_c_a_classa.writePb(5, (HexTool.Bytes2Hex(this.a,"")+".amr").getBytes());
		com_saki_e_c_a_classa.writePb(6, this.c);
		com_saki_e_c_a_classa.writePb(8, this.d);
		com_saki_e_c_a_classa.writePb(9, this.e);
		com_saki_e_c_a_classa.writePb(10, 80);
		com_saki_e_c_a_classa.writePb(11, 1);
		com_saki_e_c_a_classa.writePb(18, this.f);
		com_saki_e_c_a_classa.writePb(19, 0);//时长
		com_saki_e_c_a_classa.writePb(29, 1);
		com_saki_e_c_a_classa.writePb(30, new byte[]{(byte) 8, (byte) 0});
		//byte[] a = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.writeLengthDelimit(4, com_saki_e_c_a_classa.getDataAndDestroy());
		ByteWriter com_saki_e_c_a_classa2 = new ByteWriter();
		com_saki_e_c_a_classa2.writePb(9, new byte[]{(byte) 40, (byte) 0});
		//byte[] a2 = com.saki.qq.datapacket.pack.protobuff.ProtoBuff.;
		ByteWriter com_saki_e_c_a_classa3 = new ByteWriter();
		com_saki_e_c_a_classa3.writePb(2, com_saki_e_c_a_classa2.getDataAndDestroy());
		com_saki_e_c_a_classa3.writePb(4, com_saki_e_c_a_classa.getDataAndDestroy());
		return com_saki_e_c_a_classa3.getDataAndDestroy();
	}
}
