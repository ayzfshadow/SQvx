package com.saki.qq.datapacket.pack.jce;

import java.util.HashMap;
import java.util.Map;
import com.qq.taf.jce.*;
import android.util.*;

public final class RequestPacket extends JceStruct {
    static Map<String, String> a;
    static byte[] b;
    public byte cPacketType = 0;
    public Map<String, String> context;
    public int iMessageType = 0;
    public int iRequestId = 0;
    public int iTimeout = 0;
    public short iversion = 0;
    public byte[] sBuffer;
    public String sFuncName = null;
    public String sServantName = null;
    public Map<String, String> status;
	public RequestPacket(){
		
	}

    public RequestPacket(short iversion, byte cpackettype, int imessagetype, int irequestid, String sservantName, String sfuncName, byte[] bArr, int i4, Map<String, String> map, Map<String, String> map2) {
        this.iversion = iversion;
        this.cPacketType = cpackettype;
        this.iMessageType = imessagetype;
        this.iRequestId = irequestid;
        this.sServantName = sservantName;
        this.sFuncName = sfuncName;
        this.sBuffer = bArr;
        this.iTimeout = i4;
        if (map == null) {
            map = new HashMap<>();
        }
        this.context = map;
        if (map2 == null) {
            map2 = new HashMap<>();
        }
        this.status = map2;
    }

    public void readFrom(JceInputStream classd) {
        try {
            this.iversion = classd.read(this.iversion, 1, true);
            this.cPacketType = classd.read(this.cPacketType, 2, true);
            this.iMessageType = classd.read(this.iMessageType, 3, true);
            this.iRequestId = classd.read(this.iRequestId, 4, true);
            this.sServantName = classd.readString(5, true);
			
			
            this.sFuncName = classd.readString(6, true);
			
            if (this.b == null) {
                this.b = new byte[]{0};
            }
            this.sBuffer = classd.read(this.b, 7, true);
            this.iTimeout = classd.read(this.iTimeout, 8, true);
            if (a == null) {
                a = new HashMap<String,String>();
                a.put("", "");
            }
            this.context = classd.readMap(a, 9, true);
            if (a == null) {
                a = new HashMap<String,String>();
                a.put("", "");
            }
            this.status = classd.readMap(a, 10, true);
        } catch (Exception e2) {
            e2.printStackTrace();
            System.out.println("RequestPacket decode error " + HexUtil.bytes2HexStr(this.sBuffer));
            throw new RuntimeException(e2);
        }
    }

    public void writeTo(JceOutputStream classe) {
        classe.write(this.iversion, 1);
        classe.write(this.cPacketType, 2);
        classe.write(this.iMessageType, 3);
        classe.write(this.iRequestId, 4);
        classe.write(this.sServantName, 5);
        classe.write(this.sFuncName, 6);
        classe.write(this.sBuffer, 7);
        classe.write((byte)this.iTimeout, 8);
        classe.write(this.context, 9);
        classe.write(this.status, 10);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e2) {
            throw new AssertionError();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("sFuncName:" + this.sFuncName + "\r\n");
        sb.append("sServantName:" + this.sServantName + "\r\n");
        sb.append("cPacketType:" + this.cPacketType + "\r\n");
        sb.append("iMessageType:" + this.iMessageType + "\r\n");
        sb.append("iRequestId:" + this.iRequestId + "\r\n");
        sb.append("iTimeout:" + this.iTimeout + "\r\n");
        sb.append("iVersion:" + this.iversion + "\r\n");
        sb.append("cPacketType:" + this.cPacketType + "\r\n");
        sb.append("sBuffer:" + com.saki.tool.HexTool.a(this.sBuffer) + "\r\n");
        return sb.toString();
    }
}
