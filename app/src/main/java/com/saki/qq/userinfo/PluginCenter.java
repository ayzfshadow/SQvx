package com.saki.qq.userinfo;

import android.annotation.SuppressLint;
import com.saki.loger.DebugLoger;
import com.saki.loger.LogcatLoger;
import com.saki.qq.datapacket.unpack.ReMessageSvc.PbGetMsg;
import com.saki.qq.datapacket.unpack.ReOnlinePush;
import com.saki.qq.datapacket.unpack.ReProfileService.Pb.ReqSystemMsgNew.Group.SysMsg;
import com.saki.service.NewService;
import com.ayzf.sqvx.R;
import com.saki.tool.Code;
import com.saki.ui.c.MessageListener;
import com.setqq.script.Msg;
import com.setqq.script.PluginUtil;
import com.setqq.script.ScriptItem;
import java.util.ArrayList;
import java.util.Iterator;

@SuppressLint({"NewApi"})
public class PluginCenter implements MessageListener
{

	
    private static final PluginCenter self = new PluginCenter();
    private boolean enableprivate = false;
    private boolean enablediscuss = false;
    
    private ArrayList<ScriptItem> plugins = new ArrayList<>();
	private ArrayList<Long> PbGetMsgcache = new ArrayList<>();

    private com.saki.ui.c.PluginItemToUiBridge uiBridge;

	public void initSettings(NewService p0)
	{
		this.enableprivate = User.switch_enableprivatereply;
		this.enablediscuss = User.enablediscuss;
	}




    public static PluginCenter getInstence()
	{
        return self;
    }

    public ScriptItem getScriptById(String str)
	{
        if (str != null)
		{
            Iterator it = this.plugins.iterator();
            while (it.hasNext())
			{
                ScriptItem classb = (ScriptItem) it.next();
                if (str.equals(classb.id))
				{
                    return classb;
                }
            }
        }
        return null;
    }
	
	@Override
	public void ontimerMsg(Msg h)
	{
		Iterator it = this.plugins.iterator();
		while (it.hasNext())
		{

			ScriptItem classb = (ScriptItem) it.next();

            if(classb.enabled){
				classb.onMessage(h);
            }

		}
	}
	

	@Override
	public void onGenericGroupEventMessage(Msg aVar)
	{
		long b2 = TroopDataCenter.getListener().getGroupCodeById(aVar.groupid);
		Iterator it = this.plugins.iterator();
		while (it.hasNext())
		{
			ScriptItem classb = (ScriptItem) it.next();
			if (classb.enabled && TroopDataCenter.getListener().isGroupEnabled(aVar.groupid))
			{
				aVar.code = b2;
				classb.onMessage(aVar);
			}
		}

	}

	@Override
	public void onGenericEventMessage(Msg aVar)
	{
		Iterator it = this.plugins.iterator();
		while (it.hasNext())
		{
            
			ScriptItem classb = (ScriptItem) it.next();
            
            if((classb.enabled && TroopDataCenter.getListener().isGroupEnabled(aVar.groupid))||aVar.type==31){
               classb.onMessage(aVar);
            }
			
		}
	}



    public void onPrivateMessage(PbGetMsg aVar)
	{
		DebugLoger.log(aVar.a.size() + "", "hhh");
        if (aVar.a.size() > 0)
		{
			for (PbGetMsg.Msg aVar2 :aVar.a)
			{
				if (this.PbGetMsgcache.contains(aVar2.sendTime))
				{
					DebugLoger.log("重复", "过滤");
					continue;
				}
				this.PbGetMsgcache.add(aVar2.sendTime);
				if (this.PbGetMsgcache.size() > 1000)
				{
					this.PbGetMsgcache.remove(0);
				}
				LogcatLoger.d("primsg", aVar2.toString());
				if (this.enableprivate && aVar2.fromUin != com.saki.qq.userinfo.User.uin && aVar2.type == 166)
				{
					//com.saki.loger.DebugLoger.log(this, aVar2.toString());
					Iterator it = this.plugins.iterator();
					while (it.hasNext())
					{
						ScriptItem classb = (ScriptItem) it.next();
						if (classb.enabled)
						{
							Msg msg = new Msg();
							msg.type = 1;
							msg.groupid = aVar2.groupId;
							msg.uin = aVar2.fromUin;
							msg.code = aVar2.code;
							msg.uinName = aVar2.applyName;
							msg.addMsg("msg", aVar2.msg);
							msg.time = aVar2.sendTime;
							classb.onMessage(msg);
						}
					}
				}
				else if (this.enableprivate && aVar2.fromUin != com.saki.qq.userinfo.User.uin && aVar2.type == 141)
				{
					//com.saki.loger.DebugLoger.log(this, aVar2.toString());
					Iterator it = this.plugins.iterator();
					while (it.hasNext())
					{
						ScriptItem classb = (ScriptItem) it.next();
						if (classb.enabled)
						{
							Msg msg = new Msg();
							msg.type = 4;
							msg.groupid = aVar2.groupId;
							msg.uin = aVar2.fromUin;
							msg.code = aVar2.code;
							msg.uinName = aVar2.applyName;
							msg.addMsg("msg", aVar2.msg);
							msg.time = aVar2.sendTime;
							classb.onMessage(msg);
						}
					}
				}
				else if (aVar2.type == 33)
				{
					//com.saki.loger.DebugLoger.log(this, aVar2.toString());
					Iterator it = this.plugins.iterator();
					while (it.hasNext())
					{
						ScriptItem classb = (ScriptItem) it.next();
						if (classb.enabled && (TroopDataCenter.getListener().isGroupEnabled(Code.getGroupUinByGroupCode(aVar2.fromUin))))
						{
							Msg msg = new Msg();
							msg.type = 27;
							msg.groupid = Code.getGroupUinByGroupCode(aVar2.fromUin);
							msg.uin = aVar2.applyUin;
							msg.code = aVar2.fromUin;
							msg.uinName = aVar2.applyName;
							msg.addMsg("msg", "新人入群");
							msg.time = aVar2.sendTime;
							classb.onMessage(msg);
						}
					}
				}
				else if (aVar2.type == 84)
				{
					Iterator it = this.plugins.iterator();
					while (it.hasNext())
					{
						ScriptItem classb = (ScriptItem) it.next();
						if (classb.enabled && TroopDataCenter.getListener().isGroupEnabled(Code.getGroupUinByGroupCode(aVar2.fromUin)))
						{
							Msg msg = new Msg();
							msg.type = 28;
							msg.groupid = Code.getGroupUinByGroupCode(aVar2.fromUin);
							msg.uin = aVar2.applyUin;
							msg.code = aVar2.fromUin;
							msg.uinName = aVar2.applyName;
							msg.addMsg("msg", "入群申请");
							msg.time = aVar2.sendTime;
							classb.onMessage(msg);
						}
					}
				}
				else if (aVar2.type == 87)
				{
					Iterator it = this.plugins.iterator();
					while (it.hasNext())
					{
						ScriptItem classb = (ScriptItem) it.next();
						if (classb.enabled)
						{
							Msg msg = new Msg();
							msg.type = 32;
							msg.groupid = Code.getGroupUinByGroupCode(aVar2.fromUin);
							msg.uin = aVar2.applyUin;
							msg.code = aVar2.fromUin;
							msg.uinName = aVar2.applyName;
							msg.addMsg("msg", "入群邀请");
							msg.time = aVar2.sendTime;
							classb.onMessage(msg);
						}
					}
				}
			}
		}
    }

    public void onDiscussMessage(ReOnlinePush.PbPushDisMsg aVar)
	{
        if (this.enablediscuss && aVar.fromUin != 1000000)
		{
            Iterator it = this.plugins.iterator();
            while (it.hasNext())
			{
                ScriptItem classb = (ScriptItem) it.next();
                if (classb.enabled && aVar.fromUin != com.saki.qq.userinfo.User.uin)
				{
                    Msg msg = new Msg();
                    msg.type = 2;
                    msg.groupid = aVar.groupId;
                    msg.groupName = aVar.groupName;
                    msg.uin = aVar.fromUin;
                    msg.uinName = aVar.sendName;
                    msg.setData(aVar.msg);
                    msg.time = aVar.sendTime;
                    msg.title = aVar.sendTitle;
                    classb.onMessage(msg);
                }
            }
        }
    }

    public void onGroupMessage(ReOnlinePush.PbPushGroupMsg bVar)
	{
        if (bVar.fromUin != 1000000)
		{
            long b2 = TroopDataCenter.getListener().getGroupCodeById(bVar.groupId);
            Iterator it = this.plugins.iterator();
            while (it.hasNext())
			{
                ScriptItem classb = (ScriptItem) it.next();
                if (classb.enabled && TroopDataCenter.getListener().isGroupEnabled(bVar.groupId) && bVar.fromUin != com.saki.qq.userinfo.User.uin)
				{
                    Msg msg = new Msg();
                    msg.type = 0;
                    msg.code = b2;
                    msg.groupid = bVar.groupId;
                    msg.isfromgroup = bVar.messageId;
                    msg.isgroupmessage = bVar.messageIndex;
                    msg.groupName = bVar.groupName;
                    msg.uin = bVar.fromUin;
                    msg.uinName = bVar.sendName;
                    msg.setData(bVar.msg);
                    msg.time = bVar.sendTime;
                    msg.title = bVar.sendTitle;
                    classb.onMessage(msg);
                }
            }
        }
    }

    public void onTransMessage(ReOnlinePush.PbPushTransMsg cVar)
	{
        com.saki.loger.DebugLoger.log(this, "变换消息:" + cVar.groupId + " " + cVar.uin + " " + cVar.type + "  " + cVar.uin + " " + cVar.operator);
        Iterator it = this.plugins.iterator();
        while (it.hasNext())
		{
            ScriptItem classb = (ScriptItem) it.next();
            if (classb.enabled && TroopDataCenter.getListener().isGroupEnabled(cVar.groupId))
			{
                Msg msg = new Msg();
                if (cVar.type == 1)
				{
					msg.value = 1;
                    msg.type = 25;
					msg.addMsg("msg", "成为管理");
                }
				else if (cVar.type == 0)
				{
                    msg.value = 0;
                    msg.type = 25;
					msg.addMsg("msg", "开除管理");
                }
				else if (cVar.type == 130)
				{
                    msg.value = 1;
                    msg.type = 26;
					msg.addMsg("msg", "退出群聊");
                }
				else if (cVar.type == 131)
				{
                    msg.value = 0;
                    msg.type = 26;
					msg.addMsg("msg", "踢出群聊");
                    msg.operator = cVar.operator;
                }
                msg.code = cVar.code;
                msg.groupid = cVar.groupId;
                msg.uin = cVar.uin;
                msg.time = cVar.time;
                classb.onMessage(msg);
            }
        }
    }

    public void onSysMessage(SysMsg aVar)
	{
        com.saki.loger.DebugLoger.log(this, "系统消息:" + aVar.group + ":" + aVar.msg);
        Iterator it = this.plugins.iterator();
        while (it.hasNext())
		{
            ScriptItem classb = (ScriptItem) it.next();
            if (classb.enabled)
			{
                Msg msg = new Msg();
                msg.type = 3;
                msg.groupid = aVar.group;
                msg.uin = aVar.uin;
                msg.groupName = aVar.groupName;
                msg.time = aVar.time;
                msg.uinName = aVar.uinName;
                msg.code = aVar.requstId;
                msg.addMsg("msg", aVar.msg);
                if (!(aVar.invitor == 0 || aVar.invitorName == null))
				{
                    msg.addMsg("invitor", aVar.invitor + "@" + aVar.invitorName);
                }
                classb.onMessage(msg);
            }
        }
    }

    public void initDicPlugin(NewService newService)
	{
//        this.dic = new com.saki.plugin.Dictionary.DicPlugin();
//        this.dic.onload(newService);
//        new ScriptItem(newService, "", "高级词库", "小社", "1.0", "使用dic前请打开此插件", false, newService.getResources().getDrawable(R.drawable.drawable0063), this.dic).setType(2);
//
    }

    public void setBridge(com.saki.ui.c.PluginItemToUiBridge classb)
	{
        this.uiBridge = classb;
    }

    public void addScript(ScriptItem classb)
	{
        this.plugins.remove(classb);
        if (this.uiBridge != null)
		{
            this.uiBridge.addScriptToUi(classb);
        }
        this.plugins.add(classb);
    }

    public void loadPlugins()
	{
        //com.saki.util.ScriptUtil.updateLibrary();
        PluginUtil.plugPluginByFileName(com.saki.util.FileUtil.c, null);

    }

    public void loadJavaPlugins(NewService applicationContext) 
    {
        try
		{
			PluginUtil.loadJavaPlugin(applicationContext);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
    }

    public void updateUi()
	{
        if (this.uiBridge != null)
		{
            this.uiBridge.updateUi();
        }
    }

    public void removeAllScript()
	{
        Iterator it = this.plugins.iterator();
        while (it.hasNext())
		{
            ((ScriptItem) it.next()).stop();
        }
        this.plugins.clear();
        if (this.uiBridge != null)
		{
            this.uiBridge.removeAllScript();
        }
    }

    
}
