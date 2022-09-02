package com.saki.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import com.saki.client.Client;
import com.saki.loger.ViewLoger;
import com.saki.qq.datapacket.pack.jce.Troop;
import com.saki.qq.datapacket.pack.send.FriendList.GetTroopMemberList;
import com.saki.qq.datapacket.pack.send.FriendList.ModifyGroupCardReq;
import com.saki.qq.datapacket.pack.send.MessageSvc;
import com.saki.qq.datapacket.pack.send.OidbSvc;
import com.saki.qq.datapacket.pack.send.OidbSvc.Ox89a_0;
import com.saki.qq.datapacket.pack.send.OidbSvc.Ox8a0_0;
import com.saki.qq.datapacket.pack.send.PbMessageSvc;
import com.saki.qq.datapacket.pack.send.ProfileService;
import com.saki.qq.datapacket.pack.send.ProfileService.Pb.ReqSystemMsgAction.Group;
import com.saki.qq.datapacket.pack.send.data.ReplyData;
import com.saki.qq.userinfo.PluginCenter;
import com.saki.qq.userinfo.TroopDataCenter;
import com.saki.qq.userinfo.User;
import com.ayzf.sqvx.R;
import com.saki.ui.LoginListener.LoginResultListener;
import com.setqq.script.Msg;
import com.setqq.script.PluginUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import com.ayzf.sqvx.BuildConfig;
import com.saki.qq.userinfo.Key;

public class NewService extends Service implements Cloneable
{
    /* access modifiers changed from: private */
    public Client client;
    private LoginResultListener b;

    private PowerManager.WakeLock wake;

    public IBinder onBind(Intent intent)
    {
        if(this.getString(R.string.string0085).equals(this.getCRC1(this))){

        }else{
			if(!BuildConfig.DEBUG){
				//this.onDestroy();
			}
            
        }
        return new a();
    }
    
	
	public void setallgroupclosed()
	{
		for(Troop troop:TroopDataCenter.getListener().troopList){
            troop.isEnabled=false;
        }
	}

	public void setgroupclosed(long groupid)
	{
		for(Troop troop:TroopDataCenter.getListener().troopList){
            if(troop.id==groupid){
				troop.isEnabled=false;
			}
        }
	}
	
	
	
	public void setgroupenabled(long groupid)
	{
		for(Troop troop:TroopDataCenter.getListener().troopList){
            if(troop.id==groupid){
				troop.isEnabled=true;
			}
        }
	}
	

    public void setallgroupenabled()
    {
        for(Troop troop:TroopDataCenter.getListener().troopList){
            troop.isEnabled=true;
        }
    }

	@Override
    public Object clone(){
        NewService bean = null;
        try{
            bean = (NewService) super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return bean;
    }

	public void unpassreq(long j)
	{
		
		User.passreqcache.put(j, new Boolean(false));
		this.client.sendPacket(new ProfileService.Pb.ReqSystemMsgNew.Group(client.b()).toByteArray());
	}

	public void passreq(long j)
	{
		User.passreqcache.put(j, new Boolean(true));
		this.client.sendPacket(new ProfileService.Pb.ReqSystemMsgNew.Group(client.b()).toByteArray());
	}

    public void exit(long groupid)
    {
        this.client.sendPacket(new ProfileService.GroupMngReq(groupid,client.b()).toByteArray());
    }
    
    public class a extends Binder
    {
        public a()
        {
        }

        public NewService a()
        {
            return NewService.this;
        }
    }

    public void updateGroupList()
    {
        this.client.sendPacket(new com.saki.qq.datapacket.pack.send.FriendList.GetTroopListReqV2(this.client.b()).toByteArray());
    }

    public void a(int i, Object obj)
    {
        if (this.b != null)
        {
            this.b.onCall(i, obj);
        }
    }

    public void a(long j)
    {
        this.client.sendPacket(new GetTroopMemberList(this.client.b(), j).toByteArray());
    }

    public void a(long j, long j2)
    {
        this.client.sendPacket(new Ox8a0_0(this.client.b(), j, j2).toByteArray());
    }

    public void withDraw(long j, long j2, long j3)
    {
        this.client.sendPacket(new PbMessageSvc.PbMsgWithDraw(j, j2, j3).toByteArray());
    }

    public void a(long j, long j2, int i)
    {
        this.client.sendPacket(new OidbSvc.Ox570_8(this.client.b(), j, j2, i).toByteArray());
    }

    public void a(long j, long j2, long j3, final Msg msg)
    {

        final HashMap<String, ArrayList> hashMap2 = msg.getData();
        final long j4 = j;
        final long j5 = j2;
        final long j6 = j3;
        new Thread(new Runnable() {
                public void run()
                {
					try
					{
						if (hashMap2 != null)
						{
							ArrayList<String> arrayList = hashMap2.get("index");
							//ViewLoger.info(arrayList.size());
							//ViewLoger.info(arrayList.get(0));
							if (arrayList.size() == 1 && arrayList.get(0).equals("msg"))
							{
								ArrayList<String> msg = hashMap2.get("msg");
								String textmsg = msg.get(0);
								int msglength=textmsg.length();
								if (textmsg.length() >= 347 * 10 + 9)
								{
									ViewLoger.erro("消息超过3479长度不能发送");
									//消息超过3479长度不能发送
									return;
								}
								if (textmsg.length() >= 358)
								{
									int index=0;
									int packageindex=0;
									int wasted = msglength % 186;
									long packagelength = 0;
									long msgid=com.saki.tool.Code.randomLong();
									if (wasted == 0)
									{
										packagelength = msglength / 186;
									}
									else
									{
										packagelength = msglength / 186 + 1;
									}
									while (index < msglength)
									{
										MessageSvc.PbSendMsg bVar = new MessageSvc.PbSendMsg(NewService.this.client.b(), j4, j5, j6);
										bVar.setpackageindex(packageindex);
										bVar.setpackagelength(packagelength);
										bVar.setmsgid(msgid);
										String textmessage="";
										if (index + 186 >= msglength)
										{
											textmessage = textmsg.substring(index, msglength);
										}
										else
										{
											textmessage = textmsg.substring(index, index + 186);
										}
										com.saki.qq.datapacket.pack.send.data.MsgData.addPart((com.saki.client.PacketSender) NewService.this.client, NewService.this.client.a(), bVar, 1, 0, "msg", textmessage);
										index += 186;
										packageindex += 1;
									}
									return;
								}

							}
							com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg bVar = new com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg(NewService.this.client.b(), j4, j5, j6);
							int totalsize = arrayList.size();
							if (msg.reply != null)
							{
								totalsize += 1;
								bVar.addMsg(new ReplyData(msg.replytext, msg.uin, msg.isgroupmessage, msg.time));

							}
							// Log.d("ccc", "ccc");
							if (arrayList != null)
							{
								for (int i = 0; i < arrayList.size(); i++)
								{
									String messageType = (String) arrayList.get(i);
									ArrayList arrayList2 = hashMap2.get(messageType);
									if (arrayList2 != null && arrayList2.size() > 0)
									{
										com.saki.qq.datapacket.pack.send.data.MsgData.addPart((com.saki.client.PacketSender) NewService.this.client, NewService.this.client.a(), bVar, totalsize, i, messageType, (String) arrayList2.remove(0));
									}
								}

							}
						}
					}
					catch (Exception e)
					{
						final Writer result = new StringWriter();
						PrintWriter g = new PrintWriter(result);
						e.printStackTrace(g);
						ViewLoger.erro("消息发送错误"+result.toString());
					}
                }
            }).start();
    }



    public void a(long j, long j2, long j3, HashMap<String, ArrayList> hashMap)
    {
        final HashMap<String, ArrayList> hashMap2 = hashMap;
        final long j4 = j;
        final long j5 = j2;
        final long j6 = j3;
        new Thread(new Runnable() {
                public void run()
                {
					try
					{
						if (hashMap2 != null)
						{
							ArrayList<String> arrayList = hashMap2.get("index");
							//ViewLoger.info(arrayList.size());
							//ViewLoger.info(arrayList.get(0));
							if (arrayList.size() == 1 && arrayList.get(0).equals("msg"))
							{
								ArrayList<String> msg = hashMap2.get("msg");
								String textmsg = msg.get(0);
								int msglength=textmsg.length();
								if (textmsg.length() >= 347 * 10 + 9)
								{
									ViewLoger.erro("消息超过3479长度不能发送");
									//消息超过3479长度不能发送
									return;
								}
								if (textmsg.length() >= 358)
								{
									int index=0;
									int packageindex=0;
									int wasted = msglength % 186;
									long packagelength = 0;
									long msgid=com.saki.tool.Code.randomLong();
									if (wasted == 0)
									{
										packagelength = msglength / 186;
									}
									else
									{
										packagelength = msglength / 186 + 1;
									}
									while (index < msglength)
									{
										MessageSvc.PbSendMsg bVar = new MessageSvc.PbSendMsg(NewService.this.client.b(), j4, j5, j6);
										bVar.setpackageindex(packageindex);
										bVar.setpackagelength(packagelength);
										bVar.setmsgid(msgid);
										String textmessage="";
										if (index + 186 >= msglength)
										{
											textmessage = textmsg.substring(index, msglength);
										}
										else
										{
											textmessage = textmsg.substring(index, index + 186);
										}
										com.saki.qq.datapacket.pack.send.data.MsgData.addPart((com.saki.client.PacketSender) NewService.this.client, NewService.this.client.a(), bVar, 1, 0, "msg", textmessage);
										index += 186;
										packageindex += 1;
									}
									return;
								}

							}
							com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg bVar = new com.saki.qq.datapacket.pack.send.MessageSvc.PbSendMsg(NewService.this.client.b(), j4, j5, j6);
							if (arrayList != null)
							{
								for (int i = 0; i < arrayList.size(); i++)
								{
									String messageType = (String) arrayList.get(i);
									ArrayList arrayList2 = hashMap2.get(messageType);
									if (arrayList2 != null && arrayList2.size() > 0)
									{
										com.saki.qq.datapacket.pack.send.data.MsgData.addPart((com.saki.client.PacketSender) NewService.this.client, NewService.this.client.a(), bVar, arrayList.size(), i, messageType, (String) arrayList2.remove(0));
									}
								}
							}
						}
					}
					catch (Exception e)
					{
						final Writer result = new StringWriter();
						PrintWriter g = new PrintWriter(result);
						e.printStackTrace(g);
						ViewLoger.erro("消息发送错误"+result.toString());
					}
                }
            }).start();
    }

    
    public void a(long j, long j2, String str)
    {
        this.client.sendPacket(new ModifyGroupCardReq(this.client.b(), j, j2, str).toByteArray());
    }

    public void a(long j, boolean z)
    {
        this.client.sendPacket(new Ox89a_0(this.client.b(), j, z).toByteArray());
    }

    public void a(LoginResultListener classc)
    {
        this.b = classc;
    }

    public void Verify(String code)
    {
        this.client.getVerifyPacket(code);
    }

	public void sendLock(byte[] code)
    {
        this.client.sendPacket(code);
    }
	
	public void sendLockVerify(String code)
	{
		this.client.getUnlockPacket(code);
	}
	
	
    public void a(String str, String str2, int i)
    {
        this.client.getLoginPacket(str, str2, i);
    }
	
	public void relogin()
    {
		Key.a(Key.a);
        this.client.sendPacket(new com.saki.qq.datapacket.pack.send.GrayUinPro.Check(this.client.b()).toByteArray());
    }

    public void loadScripts() 
    {
        PluginCenter.getInstence().removeAllScript();
      //  PluginCenter.getInstence().initDicPlugin(this);
		PluginCenter.getInstence().initSettings(this);
        PluginCenter.getInstence().loadPlugins();
        PluginCenter.getInstence().loadJavaPlugins(this);
        //PluginUtil.getScrpitsFromMarket();
    }

    public void b(long j)
    {
        this.client.sendPacket(new com.saki.qq.datapacket.pack.send.VisitorSvc.ReqFavorite(this.client.b(), j).toByteArray());
    }

    
	
	private static NewService n;
	

	public static NewService getService(){
	  return n;
	}
	
    public void onCreate()
    {
        super.onCreate();
		this.n = this;
		
        com.setqq.script.PluginUtil.setService(this);
        this.client = new Client(this);
        this.acquireWakeLock();
    }

    private void acquireWakeLock()
    {
        if (this.wake == null)
        {
            this.wake = ((PowerManager) getSystemService("power")).newWakeLock(1, "sqvx:wakelock");
            WakeLock wakeLock = this.wake;
            if (wakeLock != null)
            {
                wakeLock.acquire();
            }
        }
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
    private void releaseWakeLock()
    {
        WakeLock wakeLock = this.wake;
        if (wakeLock != null)
        {
            wakeLock.release();
        }
        this.wake = null;
    }

    public void onDestroy()
    {
        //com.saki.loger.DebugLoger.log(this, "Destroy");
        super.onDestroy();
        System.exit(0);
    }
}
