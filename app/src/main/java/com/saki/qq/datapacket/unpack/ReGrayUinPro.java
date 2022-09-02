package com.saki.qq.datapacket.unpack;

import com.saki.qq.datapacket.pack.jce.RequestPacket;
import com.qq.taf.jce.*;
import android.util.*;

public class ReGrayUinPro extends RecviePack {
	public com.saki.qq.datapacket.pack.jce.RequestPacket a = new com.saki.qq.datapacket.pack.jce.RequestPacket();

	public ReGrayUinPro(byte[] bArr) {
		super(bArr);
		com.saki.tool.ByteReader com_saki_a_classd = new com.saki.tool.ByteReader(bArr);
		com_saki_a_classd.readUnsignedInt();
		if (com_saki_a_classd.readUnsignedInt() == com_saki_a_classd.length()) {
			byte[] a = com_saki_a_classd.readRestBytes();
			
			this.a.readFrom(new JceInputStream(a));
		}
        com_saki_a_classd.destroy();
	}
	
	public static String byteArrayToHexString(byte[] bytes)
	{
        String hex= "";
        if (bytes != null)
		{
            for (Byte b : bytes)
			{
                hex += String.format("%02X", b.intValue() & 0xFF) + " ";
            }
        }
        return hex;

    }
	
}
