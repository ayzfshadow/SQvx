package com.saki.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.qq.taf.jce.dynamic.ByteArrayField;
import com.qq.taf.jce.dynamic.DynamicInputStream;
import com.qq.taf.jce.dynamic.JceField;
import com.qq.taf.jce.dynamic.NumberField;
import com.saki.loger.DebugLoger;
import com.saki.loger.ViewLoger;
import com.saki.qq.datapacket.pack.protobuff.ProtoBuff;
import com.saki.qq.datapacket.pack.send.GrayUinPro;
import com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg;
import com.saki.qq.datapacket.pack.send.OidbSvc.Ox7c4_0;
import com.saki.qq.datapacket.pack.send.ProfileService;
import com.saki.qq.datapacket.unpack.QQUnPacket;
import com.saki.qq.datapacket.unpack.ReConfigPushSvc;
import com.saki.qq.datapacket.unpack.ReFriendList;
import com.saki.qq.datapacket.unpack.ReGrayUinPro;
import com.saki.qq.datapacket.unpack.ReImgStore;
import com.saki.qq.datapacket.unpack.ReMessageSvc;
import com.saki.qq.datapacket.unpack.ReMessageSvc.PushForceOffline;
import com.saki.qq.datapacket.unpack.ReOidSvc;
import com.saki.qq.datapacket.unpack.ReOnlinePush;
import com.saki.qq.datapacket.unpack.ReOnlinePush.ReqPush;
import com.saki.qq.datapacket.unpack.ReProfileService;
import com.saki.qq.datapacket.unpack.ReProfileService.Pb.ReqSystemMsgNew.Group.SysMsg;
import com.saki.qq.datapacket.unpack.RePttStore;
import com.saki.qq.datapacket.unpack.ReServer;
import com.saki.qq.datapacket.unpack.ReStatSvc;
import com.saki.qq.datapacket.unpack.ReUnknown;
import com.saki.qq.datapacket.unpack.RecviePack;
import com.saki.qq.userinfo.Device;
import com.saki.qq.userinfo.Key;
import com.saki.qq.userinfo.MessageCenter;
import com.saki.qq.userinfo.Token;
import com.saki.qq.userinfo.TroopDataCenter;
import com.saki.qq.userinfo.User;
import com.saki.service.HeartBeatThread;
import com.saki.service.NewService;
import com.saki.service.SQSocket;
import com.saki.tool.ByteReader;
import com.saki.tool.Code;
import com.saki.tool.HexTool;
import com.saki.tool.TeaCryptor;
import com.saki.ui.c.TroopDataListener;
import com.setqq.script.Msg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;
import com.saki.util.FileUtil;
import java.io.File;

public class Client implements com.saki.client.PacketSender
{
	
    
	com.saki.http.HTTP http;
    /* access modifiers changed from: private */
    public com.saki.client.classd b;
    /* access modifiers changed from: private */
    public com.saki.ui.c.MessageListener c;
    /* access modifiers changed from: private */
    public TroopDataListener d;
    /* access modifiers changed from: private */
    private int f;
    /* access modifiers changed from: private */
    public SQSocket g;
    /* access modifiers changed from: private */
    public NewService newService;

	public ExecutorService executor;
	
    private boolean newthread;

    private boolean islogined =false;

	public boolean uientered =false;

	public boolean heartbeatstarted=false;

    public static String DOMAIN= "http://www.ayzfshadow.com/SQvx/";
	private static String PROTOCOLSEVERURL= DOMAIN+"ServerVx.php";




    public class ClientSocket extends SQSocket
	{


        public ClientSocket()
		{
            super("msfwifi.3g.qq.com", 8080);
        }

        @Override public void channelActive(ChannelHandlerContext ctx) throws Exception
        {
            super.channelActive(ctx);
            if (islogined)
            {
             //  send(new com.saki.qq.datapacket.pack.send.OidbSvc.Ox59f(Client.this.b()).toByteArray());
				//send(Key.rigister);
				Key.a(Key.a);
				sendPacket(new GrayUinPro.Check(Client.this.b()).toByteArray());
				
		    }
        }

        @Override
        public void unpack(final ByteBuf bArr)
        {
            Runnable h = new Runnable(){
                @Override public void run()
                {
                    unpack1(bArr);
                }

            };
            if (newthread)
            {
                super.ctx.executor().execute(h);
            }
            else
            {
                h.run();
            }
        }

		private String getCRC1(Context c)
		{
			try
			{
				Class zip =Class.forName("java.util.zip.ZipFile");
				Constructor<?> construct = zip.getConstructor(Class.forName("java.lang.String"));

				Class Context = Class.forName("android.content.Context");
				Method getPackageCodePath= Context.getDeclaredMethod("getPackageCodePath");
				String path = (String)getPackageCodePath.invoke(c);
				Object zipobj= construct.newInstance(path);

				Method getEntry = zip.getDeclaredMethod("getEntry", Class.forName("java.lang.String"));
				Object zipentryobj = getEntry.invoke(zipobj, "classes.dex");

				Class ZipEntry = Class.forName("java.util.zip.ZipEntry");
				Method getCrc = ZipEntry.getDeclaredMethod("getCrc");
				long crc = (long)getCrc.invoke(zipentryobj);


				return crc+"";

			}
			catch (Exception e)
			{
				e.printStackTrace();
				return "";
			}
		}

        public void unpack1(final ByteBuf bArr)
		{
            try
            {
                RecviePack recivepack = QQUnPacket.unPacket(bArr);
                if (recivepack instanceof ReUnknown)
                {
					Log.d("未处理包",((ReUnknown)recivepack).cmd);
                    //com.saki.loger.DebugLoger.log(this, com.saki.tool.HexTool.a(recivepack.getData()));
                }
                else if (recivepack instanceof ReServer)
                {
                    Client.this.processServerRespone(Client.this.unpackLogin(recivepack.getData()));
                }
                else if (recivepack instanceof RePttStore.GroupPttUp)
                {
                    RePttStore.GroupPttUp aVar = (RePttStore.GroupPttUp) recivepack;
                    com.saki.client.classf.voiceinfo a3 = classf.a(aVar.l.seq);
                    classf.uploadvoice(a3, aVar.ukey);
                    PbSendMsg bVar = new PbSendMsg(Client.this.b(), -1, a3.f, -1);
                    bVar.addMsg(new com.saki.qq.datapacket.pack.send.data.PttMsg(a3.a, a3.b, a3.d, aVar.un4, 2033389267, aVar.key2));
                    send(bVar.toByteArray());
                    classf.b(aVar.l.seq);
                }
                else if (recivepack instanceof ReImgStore.GroupPicUp)
                {
                    Client.this.b.ProcessPicup(recivepack.l.seq, (ReImgStore.GroupPicUp) recivepack);
                }
                else if (recivepack instanceof PushForceOffline)
                {
                    System.exit(0);
                }
                else if (recivepack instanceof ReMessageSvc.PbGetMsg)
                {

                    Client.this.c.onPrivateMessage((ReMessageSvc.PbGetMsg) recivepack);
                }
                else if (recivepack instanceof ReProfileService.Pb.ReqSystemMsgNew.Group)
                {
                    com.saki.loger.DebugLoger.log(this, "系统消息");
                    ReProfileService.Pb.ReqSystemMsgNew.Group aVar3 = (ReProfileService.Pb.ReqSystemMsgNew.Group) recivepack;
                    send(new ProfileService.Pb.ReqSystemMsgRead.Group(Client.this.b(), aVar3.a).toByteArray());
                    Iterator it2 = aVar3.list.iterator();
                    while (it2.hasNext())
                    {
                        SysMsg aVar4 = (SysMsg) it2.next();
                        //com.saki.loger.DebugLoger.log(this, aVar4.toString());
						Boolean ggg = User.passreqcache.get(aVar4.time);
                        if (ggg != null)
                        {
							if (ggg.booleanValue())
							{
								send(new ProfileService.Pb.ReqSystemMsgAction.Group(Client.this.b(), aVar4.requstId, aVar4.uin, aVar4.group,aVar4.sub_type,aVar4.group_msg_type, true).toByteArray());
							}
							else
							{
								send(new ProfileService.Pb.ReqSystemMsgAction.Group(Client.this.b(), aVar4.requstId, aVar4.uin, aVar4.group, aVar4.sub_type,aVar4.group_msg_type, false).toByteArray());
							}
                            User.passreqcache.remove(aVar4.time);
                        }
                    }
                }
                else if (recivepack instanceof ReMessageSvc.PbshNotify)
                {
                    send(new com.saki.qq.datapacket.pack.send.MessageSvc.PbGetMsg(Client.this.b()).toByteArray());
                }
                else if (recivepack instanceof ReConfigPushSvc.PushDomain)
                {

                }
                else if (recivepack instanceof ReOnlinePush.PbPushTransMsg)
                {
                    send(new com.saki.qq.datapacket.pack.send.OnlinePush.RespPush(Client.this.b(), ((ReOnlinePush.PbPushTransMsg) recivepack).requstId, null).toByteArray());
                    Client.this.c.onTransMessage((ReOnlinePush.PbPushTransMsg) recivepack);
                }
                else if (recivepack instanceof ReqPush)
                {
                    ReqPush dVar = (ReqPush) recivepack;

                    for (ReqPush.listPack h  : dVar.a)
					{
						send(new com.saki.qq.datapacket.pack.send.OnlinePush.RespPush(Client.this.b(), dVar.b, h.a).toByteArray());

						if (h.pckgname.equals("OnlinePushPack.SvcReqPushMsg"))
						{
							this.processReqPush(h);
						}

					}
                }
                else if (recivepack instanceof ReOnlinePush.PbPushDisMsg)
                {
                    send(new com.saki.qq.datapacket.pack.send.OnlinePush.RespPush(Client.this.b(), (long) recivepack.l.seq, null).toByteArray());
                    Client.this.c.onDiscussMessage((ReOnlinePush.PbPushDisMsg) recivepack);
                }
                else if (recivepack instanceof ReOnlinePush.PbPushGroupMsg)
                {
                    Client.this.c.onGroupMessage((ReOnlinePush.PbPushGroupMsg) recivepack);
                }
                else if (recivepack instanceof ReFriendList.GetTroopListReqV2)
                {
                    Client.this.d.onGroupDataUpdate(((ReFriendList.GetTroopListReqV2)recivepack).a.list);
                }
                else if (recivepack instanceof ReFriendList.GetFriendGroupList)
                {
                }
                else if (recivepack instanceof ReStatSvc.register)
                {
                    com.saki.loger.ViewLoger.info("上线成功");
					SharedPreferences.Editor edit = Client.this.newService.getApplicationContext().getSharedPreferences("login", 0).edit();
					edit.putString("token002c",HexTool.a(new TeaCryptor().encrypt(Token._2c,Code.md5((this.getCRC1(Client.this.newService)+"").getBytes()))));
					edit.putString("token004c",HexTool.a(new TeaCryptor().encrypt(Token._4c,Code.md5((this.getCRC1(Client.this.newService)+"").getBytes()))));
					//edit.putString("rigister",HexTool.a(new TeaCryptor().encrypt(Key.rigister,Code.md5((this.getCRC1(Client.this.newService)+"").getBytes()))));
					edit.putLong("generatet",new Date().getTime());
					edit.putString("sessionkey",HexTool.a(Key.c));
					edit.commit();
					islogined = true;
					if(!Client.this.heartbeatstarted){
						g.ctx.executor().scheduleAtFixedRate(new HeartBeatThread(Client.this), 0, 100, TimeUnit.SECONDS);
						Client.this.heartbeatstarted=true;
					}
					if(!Client.this.uientered){
					    Client.this.newService.a(0, (Object) null);
						Client.this.uientered=true;
					}
                }

                else
                {
                    if (recivepack instanceof ReFriendList.GetTroopMemberList)
                    {
                        ReFriendList.GetTroopMemberList cVar = (ReFriendList.GetTroopMemberList) recivepack;
                        Client.this.d.onGroupMemberDataUpdate(cVar.a.code, cVar.a.list);
                    }
                    else if (recivepack instanceof ReOidSvc.Ox59f)
                    {
                        send(new com.saki.qq.datapacket.pack.send.OidbSvc.Ox496(Client.this.b()).toByteArray());
                    }
                    else if (recivepack instanceof ReOidSvc.Ox496)
                    {
                        send(new Ox7c4_0(Client.this.b()).toByteArray());
                    }
                    else if (recivepack instanceof ReOidSvc.Ox7c4_0)
                    {
                        send(new com.saki.qq.datapacket.pack.send.OidbSvc.Ox7a2_0(Client.this.b()).toByteArray());
                    }
                    else if (recivepack instanceof ReOidSvc.Ox7a2_0)
                    {
                        send(new com.saki.qq.datapacket.pack.send.StatSvc.Register(Client.this.b()).toByteArray());
                    }
                    else if (recivepack instanceof ReGrayUinPro)
                    {
                        com.saki.qq.userinfo.Key.a(com.saki.qq.userinfo.Key.c);
                        send(new com.saki.qq.datapacket.pack.send.OidbSvc.Ox59f(Client.this.b()).toByteArray());
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }

		private void processReqPush(ReOnlinePush.ReqPush.listPack h) throws Exception
		{
			for (ReOnlinePush.ReqPush.MsgInfo d  :h.a)
			{
				DebugLoger.log("type", d.type + "");
                com.saki.loger.DebugLoger.log(this, HexTool.a(d.data));
			    switch (d.type)
				{
					case 732:{//群里的
							ByteReader factory = new ByteReader(d.data);
							factory.readBytes(4);
							byte cmd =factory.readBytes(1)[0];
							if (cmd == (byte)0x11)
							{
								//群消息撤回
								factory.readBytes(2);
								ProtoBuff buff = new ProtoBuff(factory.readRestBytes());
								Msg msg = new Msg();
								msg.type = 29;
								msg.groupid = buff.readVarint(4);
								buff.update(buff.readLengthDelimited(11));
							    msg.operator = buff.readVarint(1);
								buff.update(buff.readLengthDelimited(3));
								msg.isgroupmessage = buff.readVarint(1);
								msg.time = buff.readVarint(2);
							    msg.isfromgroup = buff.readVarint(3);
								msg.uin = buff.readVarint(6);
								msg.addMsg("msg", "消息撤回");
								buff.destroy();
								buff = null;
								Client.this.c.onGenericGroupEventMessage(msg);
							}
							else if (cmd == (byte)0x0c)
							{
								//某人被禁言
								factory.readBytes(1);
								Msg msg = new Msg();
								msg.type = 30;
								msg.operator = factory.readUnsignedInt();
								msg.time = factory.readUnsignedInt();
								factory.readBytes(2);
								msg.uin = factory.readUnsignedInt();
								msg.value = (int) factory.readUnsignedInt();
								msg.groupid = d.id;
								msg.addMsg("msg", "群内禁言");
								Client.this.c.onGenericGroupEventMessage(msg);
							}
							factory.destroy();
						}break;
					case 528:{
							DynamicInputStream di1 = new DynamicInputStream(d.data);
							JceField field = di1.read();
							short cmd =((NumberField)field).shortValue();
							//Util.log("cmd" + cmd);
							while (field.getTag() != 10)//读到10
							{
								field = di1.read();
							}

							if (cmd == 126)
							{//收到转账
								ProtoBuff pb = new ProtoBuff(((ByteArrayField)field).get());
								Msg msg  = new Msg();
								pb.update(pb.readLengthDelimited(2));
								//1不知道是啥
								msg.time = pb.readVarint(2);
								String g = pb.readString(3);
								JSONObject js = new JSONObject(g);
								msg.uinName = js.getString("from_name");
								msg.uin = Long.valueOf(js.getString("from_uin"));
								msg.title = js.getString("pay_msg");
								msg.value = Integer.valueOf(js.getString("pay_status"));
								msg.groupName = js.getString("type");
								msg.type = 31;
								msg.addMsg("msg", "收到转账");
								Client.this.c.onGenericEventMessage(msg);
								pb.destroy();
							}else {
								DebugLoger.log("type","unknown");
							}



						}break;


					default:{
							DebugLoger.log("未知类型", d.type + "");
						}break;

				}

			}
		}


    }





    public Client(NewService newService)
	{
        this.newService = newService;
        this.newthread =  User.newthread;

		http = new com.saki.http.HTTP();
		b = new com.saki.client.classd(this);
		c = MessageCenter._this();
		d = TroopDataCenter.getListener();
		executor = Executors.newCachedThreadPool();
        Client.this.g = new ClientSocket();
		Timer timer = new Timer();
		Date g = new Date();
		int hours = g.getHours();
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hours + 1);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		timer.scheduleAtFixedRate(new TimerTask(){

				@Override
				public void run()
				{
					Log.d("定时","启动");
					ViewLoger.info("定时任务");
					Msg h = new Msg();
					h.addMsg("msg","整点消息");
					h.type=33;
					Client.this.c.ontimerMsg(h);
				
				}
			}, calendar.getTime(), 1000*60*60);
    }


    /* access modifiers changed from: private */
    public byte[] unpackLogin(byte[] bArr)
	{
        try
		{
            return this.http.httpPost(Client.PROTOCOLSEVERURL, bArr, 2000, 3000, new String[0]);
        }
		catch (Exception e2)
		{
            e2.printStackTrace();
            return null;
        }
    }

    public com.saki.client.classd a()
	{
        return this.b;
    }

    public void getVerifyPacket(final String str)
	{
        new Thread(new Runnable() {
				public void run()
				{
					try
					{
						Client.this.processServerRespone(Client.this.http.httpGet(Client.PROTOCOLSEVERURL + "?verifycode=" + str, true, 3000, 3000, new String[0]));
					}
					catch (Exception e)
					{
						Client.this.newService.a(1, (Object) "网络异常");
					}
				}
			}).start();
    }

	public void getUnlockPacket(final String str)
	{
        new Thread(new Runnable() {
				public void run()
				{
					try
					{
						Client.this.processServerRespone(Client.this.http.httpGet(Client.PROTOCOLSEVERURL + "?unlockcode=" + str, true, 3000, 3000, new String[0]));
					}
					catch (Exception e)
					{
						Client.this.newService.a(1, (Object) "网络异常");
					}
				}
			}).start();
    }
	
//	public void getLoginPacket(final String str, final String str2, final int i)
//	{
//        new Thread(new Runnable() {
//				public void run()
//				{
//					try
//					{
//
//						NewService d2 = Client.this.newService;
//
//						long j;
//						Context context = d2.getApplicationContext();
//						String packageCodePath = context.getPackageCodePath();
//						
//							PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 1);
//							if (Build.VERSION.SDK_INT >= 28) {
//								j = packageInfo.getLongVersionCode();
//							} else {
//								j = (long) packageInfo.versionCode;
//							}
//						
//						String Bytes2Hex = HexTool.a((new TeaCryptor().encrypt(str2.getBytes(), Code.md5(FileUtil.a(new File(packageCodePath))))), "");
//						Client.this.processServerRespone(Client.this.http.httpGet("http://www.dicq.online/Server.php?ver=" + j + "&id=" + str + "&password=" + Bytes2Hex, true, 3000, 3000, new String[0]));
////					
//						//handleServerReturn(client, http2.call("GET", "http://www.dicq.online/Server.php?ver=" + j + "&id=" + client.getUser().id + "&password=" + Bytes2Hex, (byte[]) null, new String[0]));
//						
//					}
//					catch (Exception e)
//					{
//						e.printStackTrace();
//						Client.this.newService.a(1, "网络异常");
//					}
//				}
//
//
//			}).start();
//    }
	
	
	
    public void getLoginPacket(final String str, final String str2, final int i)
	{
        this.executor.execute(new Runnable() {
				public void run()
				{
					try
					{
						NewService d2 = Client.this.newService;
						String path = d2.getPackageCodePath();
						byte[] key = FileUtil.a(new File(path));
						
						//byte[] key = HexTool.hextobytes("00000000000000000000000000000000");
						//classa.this.newService.a(1, HexTool.Bytes2Hex(key,""));
						byte[] psdData = new TeaCryptor().encrypt(str2.getBytes(), key);
						Client.this.processServerRespone(Client.this.http.httpGet(PROTOCOLSEVERURL + "?ver=" + i + "&id=" + str + "&password=" + com.saki.tool.HexTool.Bytes2Hex(psdData,"") + "&deviceInfo=" + getdiviceInfo(), true, 3000, 3000, new String[0]));
					}
					catch (Exception e)
					{
						e.printStackTrace();
						Client.this.newService.a(1, "网络异常");
					}
				}
			});
    }
//
	
	
	
	private String getdiviceInfo() throws JSONException
	{
		JSONObject js = new JSONObject();
		js.put("display", Device.display);
		js.put("product", Device.product);
		js.put("device", Device.device);
		js.put("board", Device.board);
		js.put("brand", Device.brand);
		js.put("model", Device.model);
		js.put("bootloader", Device.bootloader);
		js.put("fingerprint", Device.fingerprint);
		js.put("bootId", Device.bootId);
		js.put("procVersion", Device.procVersion);
		js.put("baseBand", Device.baseBand);
		js.put("simInfo", Device.simInfo);
		js.put("osType", Device.osType);
		js.put("macAddress", Device.macAddress);
		js.put("wifiBSSID", Device.wifiBSSID);
		js.put("wifiSSID", Device.wifiSSID);
		js.put("imsiMd5", HexTool.Bytes2Hex(Device.imsiMd5, ""));
		js.put("imei", Device.imei);
		js.put("apn", Device.apn);
		js.put("androidId", Device.androidId);
		js.put("codename", Device.codename);
		js.put("incremental", Device.incremental);
		js.put("innerVersion", Device.innerVersion);
		js.put("osVersion", Device.osVersion);
		return HexTool.Bytes2Hex(js.toString().getBytes(), "");
	}

	
	
    private byte[] getkey(Context applicationContext)
    {

        long h =getCRC1(applicationContext.getApplicationContext());

        byte[] i =Code.md5(String.valueOf(h).getBytes());


        return i;
    }

//    private long getCRC(Context c) {
//        try {
//            Class zip =Class.forName(new String(new byte[]{106,97,118,97,46,117,116,105,108,46,122,105,112,46,90,105,112,70,105,108,101}));
//            Constructor<?> construct = zip.getConstructor(Class.forName(new String(new byte[]{106,97,118,97,46,108,97,110,103,46,83,116,114,105,110,103})));
//
//            Class Context = Class.forName(new String(new byte[]{97,110,100,114,111,105,100,46,99,111,110,116,101,110,116,46,67,111,110,116,101,120,116}));
//            Method getPackageCodePath= Context.getDeclaredMethod(new String(new byte[]{103,101,116,80,97,99,107,97,103,101,67,111,100,101,80,97,116,104}));
//            String path = (String)getPackageCodePath.invoke(c);
//            Object zipobj= construct.newInstance(path);
//
//            Method getEntry = zip.getDeclaredMethod(new String(new byte[]{103,101,116,69,110,116,114,121}),Class.forName(new String(new byte[]{106,97,118,97,46,108,97,110,103,46,83,116,114,105,110,103})));
//            Object zipentryobj = getEntry.invoke(zipobj,new String(new byte[]{99,108,97,115,115,101,115,46,100,101,120}));
//
//            Class ZipEntry = Class.forName(new String(new byte[]{106,97,118,97,46,117,116,105,108,46,122,105,112,46,90,105,112,69,110,116,114,121}));
//            Method getCrc = ZipEntry.getDeclaredMethod(new String(new byte[]{103,101,116,67,114,99}));
//            long crc = (long)getCrc.invoke(zipentryobj);
//
//            
////            return crc;
//            
//            return crc;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
    private long getCRC1(Context c)
	{
        try
		{
            Class zip =Class.forName("java.util.zip.ZipFile");
            Constructor<?> construct = zip.getConstructor(Class.forName("java.lang.String"));

            Class Context = Class.forName("android.content.Context");
            Method getPackageCodePath= Context.getDeclaredMethod("getPackageCodePath");
            String path = (String)getPackageCodePath.invoke(c);
            Object zipobj= construct.newInstance(path);

            Method getEntry = zip.getDeclaredMethod("getEntry", Class.forName("java.lang.String"));
            Object zipentryobj = getEntry.invoke(zipobj, "classes.dex");

            Class ZipEntry = Class.forName("java.util.zip.ZipEntry");
            Method getCrc = ZipEntry.getDeclaredMethod("getCrc");
            long crc = (long)getCrc.invoke(zipentryobj);


            return crc;

        }
		catch (Exception e)
		{
            e.printStackTrace();
            return 0;
        }
    }

	public String getIMEI(Context c)
    {
        TelephonyManager telephonyManager = (TelephonyManager)c.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public void processServerRespone(byte[] bArr)
	{
        if (bArr == null || bArr.length == 0)
		{
            this.newService.a(1, (Object) "服务器解包失败");
            return;
        }
        com.saki.service.Tlvparse classa = new com.saki.service.Tlvparse(bArr);
        while (true)
		{
            com.saki.service.Tlvparse.tlvdata a2 = classa.a();
            if (a2 == null)
			{
                classa.destroy();
                return;
            }
            if (a2.a == 1)
			{
                this.newService.a(2, (Object) a2.b);
            }
			else if (a2.a == -1)
			{
                this.newService.a(1, (Object) new String(a2.b).replaceAll("[^ -龥]", ""));
            }
			else if (a2.a == 4)
			{
                if (a2.b.length == 64)
				{
                    com.saki.qq.userinfo.Token._2c = a2.b;
                }
				else if (a2.b.length == 72)
				{
					//sendPacket(new com.saki.qq.datapacket.pack.send.OidbSvc.Ox59f(0).d());
                    com.saki.qq.userinfo.Token._4c = a2.b;
					sendPacket(new GrayUinPro.Check(Client.this.b()).toByteArray());
                }
				
            }
			else if (a2.a == 3)
			{
                this.f = com.saki.tool.HexTool.b(a2.b);
            }
			else if (a2.a == 2)
			{
				//Log.d("sessionkey",HexTool.a(a2.b));
                com.saki.qq.userinfo.Key.c = a2.b;
				com.saki.qq.userinfo.Key.a(a2.b);
            }
			else if (a2.a == 6)
			{
                sendPacket(a2.b);
            }
			else if (a2.a == 100)
			{
                sendPacket(a2.b);
				//Key.rigister=a2.b;
            }
			if (a2.a == 11)
			{
                this.newService.a(11, (Object) a2.b);
            }
			if (a2.a == 12)
			{
                this.newService.a(12, (Object) a2.b);
            }
			if (a2.a == 13)
			{
                this.newService.a(13, (Object) a2.b);
            }
			else if (a2.a == 5)
			{
                this.newService.a(4, (Object) new String(a2.b));
            }
			else if (a2.a == 7)
			{
                this.newService.a(5, (Object) a2.b);
            }
			else if (a2.a == -3)
			{
                this.newService.a(6, (Object) new String(a2.b));
            }
			else if (a2.a == -4)
			{
                this.newService.a(7, (Object) new String(a2.b));
            }
			else if (a2.a == 9)
			{
                com.saki.qq.userinfo.Token.lockinfo = new String(a2.b);
            }
        }
    }

    public int b()
	{
        int i = this.f;
        this.f = i + 1;
        return i;
    }

    @Override public void sendPacket(final byte[] bArr)
	{
        Client.this.g.send(bArr);
    }
}
