//
// Decompiled by Jadx - 938ms
//
package com.saki.plugin.Dictionary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import com.saki.loger.DebugLoger;
import com.saki.qq.userinfo.TroopDataCenter;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import com.setqq.script.sdk.PluginApi;
import com.setqq.script.sdk.PluginInterface;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DicPlugin implements PluginInterface
{

    @Override
    public Intent getimpClass()
    {
        return null;
    }
    
	public Set<String> a;
	public DicFileLoader dicfileloader;
	public DicFileLoader dicfileloader_extra;
	private NewService service = null;
	private PluginApi d;

	@SuppressLint({"NewApi"})
	private boolean a(long j)
	{
		if (this.a == null)
		{
			SharedPreferences sharedPreferences = this.service.getSharedPreferences("dictionary", 0);
			this.a = new HashSet();
			sharedPreferences.getStringSet("black", this.a);
		}
		return this.a.contains(String.valueOf(j));
	}

	private boolean a(String str)
	{
		return this.service.getSharedPreferences("dictionary", 0).getBoolean(str, false);
	}

	private long[] a(ArrayList<String> arrayList)
	{
		if (arrayList == null || arrayList.size() == 0)
		{
			return new long[0];
		}
		long[] jArr = new long[arrayList.size()];
		for (int i = 0; i < jArr.length; i++)
		{
			String str = (String) arrayList.get(i);
			jArr[i] = Long.parseLong(str.substring(0, str.indexOf(64)));
		}
		return jArr;
	}

	private String getcharset()
	{
		return this.service.getSharedPreferences("dictionary", 0).getString("charset", "UTF-8");
	}

	private String getmaster()
	{
		return this.service.getSharedPreferences("dictionary", 0).getString("master", "0");
	}

	public void onstop()
	{
	}

	public void onload(NewService newService)
	{
		this.service = newService;
		this.dicfileloader = new DicFileLoader(DicStream.dicfile);
		this.dicfileloader.existorcreate();
		this.dicfileloader_extra = new DicFileLoader(DicStream.dicfile_extra);
		this.dicfileloader_extra.existorcreate();
		this.d = new PluginApi(newService);
	}

	private long getfirstcache()
	{
		for (long key : DicExecutor.msgcache.keySet()){
            return key;
        }
		return 0;
	}
	
	public void process(Msg msg)
	{
		DebugLoger.log("process",msg.getTextMsg());
		if (msg != null)
		{
			try
			{
				if(msg.type == 0 && TroopDataCenter.getListener().isGroupEnabled(msg.groupid)){
					DicExecutor.msgcache.put(msg.isfromgroup,msg.getTextMsg());
					DebugLoger.log("put",msg.isfromgroup+" "+msg.getTextMsg());
					if(DicExecutor.msgcache.size()>1000){
						
						DicExecutor.msgcache.remove(getfirstcache());
					}
					
				}
				DicFileLoader loader  =null;
				switch (msg.type)
				{
					case 0:
				    case 1:
				    case 2:
					case 4:{
							loader=dicfileloader;
						}break;
					case 25:
					case 26:
					case 27:
					case 28:
					case 29:
					case 30:
					case 31:{
							loader=dicfileloader_extra;
						}break;
			        default:{
						throw new Exception();
					}
				}
				String textMsg = msg.getTextMsg();
				if ((msg.type==27&& msg.uin == com.saki.qq.userinfo.User.uin) ||(this.service != null && !textMsg.equals("") && msg.uin != com.saki.qq.userinfo.User.uin))
				{
					if (msg.type != 0 || TroopDataCenter.getListener().isGroupEnabled(msg.groupid))
					{
						if (!a(msg.groupid) && !a(msg.uin))
						{
							String[] a;
							String[] strArr = null;
							if (loader.b())
							{
								strArr = loader.getDicIntentByMsg(textMsg);
							}
							if (strArr == null)
							{
								BufferedReader a2 = loader.a(textMsg, msg.type, getcharset());
								if (a2 != null)
								{
									a = loader.a(a2, true);
									if (a == null || a.length == 0)
									{
										a = loader.a(a2, false);
									}
									if (a != null && a.length > 0)
									{
										String a3=new DicExecutorIml(this.service, msg, getmaster(), textMsg, a(msg.getMsg("at"))).a(a);
										
										//Log.d("process", "");
										if (!a3.equals(""))
										{
											if (a3.matches("(?i)(?s)card:[0-9]+.*"))
											{
												a3 = a3.replaceAll("(?i)(?s)card:[0-9]+(.*)", "$1");
												msg.clearMsg();
												msg.addMsg("xml", a3);
												this.d.send(msg);
												return;
											}
											else if (a3.matches("(?i)(?s)json:.*"))
											{
												a3 = a3.substring(5);
												msg.clearMsg();
												msg.addMsg("json", a3);
												this.d.send(msg);
												return;
											}
											else if (a3.matches("(?i)(?s)ptt:.*"))
											{
												a3 = a3.replaceAll("(?i)(?s)ptt:(.*)", "$1");
												msg.clearMsg();
												msg.addMsg("ptt", a3);
												this.d.send(msg);
												return;
											}
											else
											{
												a3 = a3.replaceAll("\\\\r", "\r");
												msg.clearMsg();
												for (String str : a3.split("±"))
												{
													if (str.indexOf("img:") == 0)
													{
														msg.addMsg("img", str.substring(4));
													}
													else if (str.indexOf("at:") == 0)
													{
														msg.addMsg("at", str.substring(3));
													}
													else if (str.indexOf("reply:") == 0)
													{
														msg.reply(str.substring(6), msg);
													}
													else if (str.indexOf("mojo") == 0)
													{
														msg.addMsg("mojo", str.substring(5));
													}
													else if (!str.equals(""))
													{
														msg.addMsg("msg", str);
													}
												}

												this.d.send(msg);
												return;
											}
										}
										return;
									}
								}
							}
						}
					}
				}

			}
			catch (Exception e)
			{
				Writer stringWriter = new StringWriter();
				e.printStackTrace(new PrintWriter(stringWriter));
				com.saki.loger.ViewLoger.erro("高级词库发生异常:" + e.toString() + "\r\n" + stringWriter.toString());
			}
		}
	}

	public String getUiUrl()
	{
		return null;
	}

	public String onAction(String str)
	{
		return str;
	}
}
