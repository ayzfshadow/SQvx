//
// Decompiled by Jadx - 802ms
//
package com.saki.qq.datapacket.unpack;

import android.util.Log;
import com.saki.loger.DebugLoger;
import com.saki.qq.datapacket.unpack.ReOidSvc.Ox496;
import com.saki.qq.datapacket.unpack.ReOidSvc.Ox59f;
import com.saki.qq.datapacket.unpack.ReOidSvc.Ox7a2_0;
import com.saki.qq.datapacket.unpack.ReOidSvc.Ox7c4_0;
import com.saki.tool.ByteReader;
import com.saki.tool.Code;
import com.saki.tool.TeaCryptor;
import io.netty.buffer.ByteBuf;
import com.saki.tool.HexTool;

public class QQUnPacket
{


	public static com.saki.qq.datapacket.unpack.Info getInfo(ByteReader reader, byte[] key)
	{

        reader.readBytes(8);
        int type = reader.readBytes(1)[0];
//      int index = buf.readerIndex();
//      buf.readerIndex(0);
//      byte[] g = new byte[buf.readableBytes()];
//      buf.readBytes(g);
//      System.out.println(Util.byte2HexString(g));
//      buf.readerIndex(index);

        reader.readBytes(1);//0
        reader.readBytes((int)reader.readInt() - 4);//qq长度+4+qq
        
        byte[] data = reader.readRestBytes();
        if (data.length % 8 != 0)
        {
            return null;//包体长度不是8的解密不出来的，丢弃这个包
		}
        TeaCryptor cry =  new TeaCryptor();
        byte[] data_decrypted = cry.decrypt(data, key);
        if (data_decrypted == null || data_decrypted.length == 0)
        {
            data_decrypted = cry.decrypt(data, new byte[16]);
        }
        if (data_decrypted == null || data_decrypted.length == 0)
        {
			cry.destroy();
            return null;
        }
        cry.destroy();
        reader.update(data_decrypted);
		//Log.d("decrypted",HexTool.a(data_decrypted));
        return new Info(reader);
	}

	private static RecviePack cmdUnpacket(String str, byte[] bArr)
	{
		
		Log.d( "收包",str);
		try
		{
			if (str.equals("wtlogin.login"))
			{
				return new ReServer(bArr);
			}
			else if (str.equals("GrayUinPro.Check"))
			{
				return new ReGrayUinPro(bArr);
			}
			else if (str.equals("OidbSvc.0x59f"))
			{
				return new Ox59f(bArr);
			}
			else if (str.equals("OidbSvc.0x496"))
			{
				return new Ox496(bArr);
			}
			else if (str.equals("OidbSvc.0x7c4_0"))
			{
				return new Ox7c4_0(bArr);
			}
			else if (str.equals("OidbSvc.0x7a2_0"))
			{
				return new Ox7a2_0(bArr);
			}
			else if (str.equals("StatSvc.SimpleGet"))
			{
				return new ReStatSvc.SimpleGet(bArr);
			}
			else if (str.equals("StatSvc.register"))
			{
				return new ReStatSvc.register(bArr);
			}
			else if (str.equals("ConfigPushSvc.PushReq"))
			{
				return new ReConfigPushSvc.PushReq(bArr);
			}
			else if (str.equals("ConfigPushSvc.PushDomain"))
			{
				return new ReConfigPushSvc.PushDomain(bArr);
			}
			else if (str.equals("friendlist.GetTroopListReqV2"))
			{
				return new ReFriendList.GetTroopListReqV2(bArr);
			}
			else if (str.equals("friendlist.getFriendGroupList"))
			{
				return new ReFriendList.GetFriendGroupList(bArr);
			}
			else if (str.equals("friendlist.getTroopMemberList"))
			{
				return new ReFriendList.GetTroopMemberList(bArr);
			}
			else if (str.equals("OnlinePush.PbPushTransMsg"))
			{
				return new ReOnlinePush.PbPushTransMsg(bArr);
			}
			else if (str.equals("OnlinePush.PbPushGroupMsg"))
			{
				return new ReOnlinePush.PbPushGroupMsg(bArr);
			}
			else if (str.equals("OnlinePush.PbPushDisMsg"))
			{
				return new ReOnlinePush.PbPushDisMsg(bArr);
			}
			else if (str.equals("OnlinePush.ReqPush"))
			{
				return new ReOnlinePush.ReqPush(bArr);
			}
			else if (str.equals("MessageSvc.PushNotify"))
			{
				return new ReMessageSvc.PbshNotify(bArr);
			}
			else if (str.equals("MessageSvc.PushForceOffline"))
			{
				return new ReMessageSvc.PushForceOffline(bArr);
			}
			else if (str.equals("MessageSvc.PbGetMsg"))
			{
				return new ReMessageSvc.PbGetMsg(bArr);
			}
			else if (str.equals("ProfileService.Pb.ReqSystemMsgNew.Group"))
			{
				return new ReProfileService.Pb.ReqSystemMsgNew.Group(bArr);
			}
			else if (str.equals("ImgStore.GroupPicUp"))
			{
				return new ReImgStore.GroupPicUp(bArr);
			}
			else if (str.equals("PttStore.GroupPttUp"))
			{
				return new RePttStore.GroupPttUp(bArr);
			}
			return new ReUnknown(bArr,str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static RecviePack unPacket(ByteBuf bArr) throws UnknownPacketException
	{
		ByteReader com_saki_a_classd = new ByteReader(bArr);
        byte[] com_saki_a_classd1 = com_saki_a_classd.readRestBytes();
		//Log.d("recv",HexTool.a(com_saki_a_classd1));
        com_saki_a_classd.readerIndex(0);
		com.saki.qq.datapacket.unpack.Info a = getInfo(com_saki_a_classd, com.saki.qq.userinfo.Key.a());
		if (a == null)
		{
			throw new UnknownPacketException();
		}
//		DebugLoger.log(a, "---------------" + a.cmd + "---------------");
//		//DebugLoger.log(a, com.saki.tool.HexTool.a(com_saki_a_classd.readRestBytes()));
//		DebugLoger.log(a, "---------------------end--------------------");
//		 
            RecviePack receivepack = cmdUnpacket(a.cmd, com_saki_a_classd.readRestBytesAndDestroy()).setInfo(a);
        receivepack.setData(com_saki_a_classd1);
        return receivepack;
	}
}
