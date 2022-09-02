package com.saki.qq.datapacket.unpack;

import com.saki.qq.datapacket.pack.jce.MemberList;
import com.saki.qq.datapacket.pack.jce.TroopList;
import java.util.HashMap;
import com.qq.taf.jce.*;
import android.util.Log;

public class ReFriendList
{

    public static class GetFriendGroupList extends RecviePack
	{
        public GetFriendGroupList(byte[] bArr)
		{
            super(bArr);
        }
    }

    public static class GetTroopListReqV2 extends RecviePack
	{
        public TroopList a = new TroopList();

        public GetTroopListReqV2(byte[] bArr)
		{
            super(bArr);
            
            com.saki.tool.ByteReader classd = new com.saki.tool.ByteReader(bArr);
            int length = (int) classd.readUnsignedInt();
            //Log.d("length",length+"    "+classd.length());
            
            if (length == classd.length())
			{
                JceInputStream classd2 = new JceInputStream(checkZip(classd.readRestBytes()));
                com.saki.qq.datapacket.pack.jce.RequestPacket classd3 = new com.saki.qq.datapacket.pack.jce.RequestPacket();
                classd3.readFrom(classd2);
                JceInputStream classd4 = new JceInputStream(classd3.sBuffer);
                HashMap<String,byte[]> hashMap = new HashMap<String,byte[]>();
                hashMap.put("", new byte[0]);
                HashMap<String,byte[]> hashMap2 = classd4.readMap(hashMap, 0, true);
				TroopList u =null;
                for (String str : hashMap2.keySet())
				{
                    this.a = (TroopList) new JceInputStream(hashMap2.get(str)).read((JceStruct) this.a, 0, true);
                }
            }
            classd.destroy();
        }
    }

    public static class GetTroopMemberList extends RecviePack
	{
        public MemberList a = new MemberList();

        public GetTroopMemberList(byte[] bArr)
		{
            super(bArr);
			System.out.println("GetTroopMemberList 1");
            com.saki.tool.ByteReader unpack = new com.saki.tool.ByteReader(bArr);
            if (unpack.readUnsignedInt() == unpack.length())
			{
                JceInputStream classd2 = new JceInputStream(checkZip(unpack.readRestBytes()));
                com.saki.qq.datapacket.pack.jce.RequestPacket classd3 = new com.saki.qq.datapacket.pack.jce.RequestPacket();
                classd3.readFrom(classd2);
                JceInputStream classd4 = new JceInputStream(classd3.sBuffer);
                HashMap<String,byte[]> hashMap = new HashMap<String,byte[]>();
                hashMap.put("", new byte[0]);
                HashMap<String,byte[]> hashMap2 = classd4.readMap(hashMap, 0, true);
                for (String str : hashMap2.keySet())
				{
                    this.a = (MemberList) new JceInputStream(hashMap2.get(str)).read((JceStruct) this.a, 0, true);
                }
            }
			System.out.println("GetTroopMemberList 2");
            unpack.destroy();
        }
    }
}
