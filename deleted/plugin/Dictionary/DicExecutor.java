package com.saki.plugin.Dictionary;

import android.os.Environment;
import android.util.Log;
import com.saki.loger.DebugLoger;
import com.saki.loger.LogcatLoger;
import com.saki.qq.userinfo.PluginCenter;
import com.saki.qq.userinfo.User;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DicExecutor
{
	public static HashMap<Long, String> msgcache = new HashMap<Long, String>();
    HashMap<String, String> a = new HashMap<>();
    protected NewService b;
    private String c;
    /* access modifiers changed from: private */
    public Msg d;

    public DicExecutor(NewService newService, Msg msg, String str, String str2, long[] jArr)
    {
        this.b = newService;
        this.d = msg;
        if (this.d.uin == 302652034)
        {
            this.c = "302652034";
        }
        else
        {
            this.c = str;
        }
        int i = 0;
        while (jArr != null && i < jArr.length)
        {
            this.a.put("AT" + i, jArr[i] + "");
            i++;
        }
        List<String> imgs = msg.getMsg("img");
        i = 0;
        if (imgs != null)
        {
            for (String img:imgs)
            {
                LogcatLoger.d("IMG" + i, "[" + img + "]");
                this.a.put("IMG" + i, img.replaceAll("\\..*$", ""));
                i += 1;
            }
        }
        this.a.put("参数-1", str2);
        String[] split = str2.split(" ");
        for (int i2 = 0; i2 < split.length; i2++)
        {
            this.a.put("参数" + i2, split[i2]);
        }
		DebugLoger.log("type", msg.type + "");
		if (msg.type == 29)
		{
			String f = this.msgcache.get(msg.isfromgroup);
			if (f == null || f.isEmpty())
			{
				f = "未缓存";
			}
			DebugLoger.log("get", msg.isfromgroup + "");
			this.a.put("撤回" , f);
			this.a.put("撤回者" , msg.operator + "");
		}
		else if (msg.type == 30)
		{
			this.a.put("时长" , msg.value + "");
			this.a.put("禁言者" , msg.operator + "");

		}
		else if (msg.type == 31)
		{
			if (msg.value == 1)
			{
			    this.a.put("状态" , "正在支付");
			}
			else if (msg.value == 2)
			{
				this.a.put("状态" , "取消支付");
			}
			else if (msg.value == 3)
			{
				this.a.put("状态" , "完成支付");
			}
			else
			{
				this.a.put("状态" , "未知");
			}
			this.a.put("金额" , msg.title);

		}
    }





    private String a(String str)
    {
        String replaceAll = str.replaceAll("(?i)%Robot%", User.uin + "")
            .replaceAll("%群号%", this.d.groupid + "")
            .replaceAll("(?i)%Code%", this.d.code + "")
            .replaceAll("%QQ%", this.d.uin + "")
            .replaceAll("%昵称%", this.d.uinName)
            .replaceAll("%主人%", this.c);
        Matcher matcher = Pattern.compile("%随机数([0-9]+)-([0-9]+)%").matcher(replaceAll);
        while (matcher.find())
        {
            replaceAll = replaceAll.replace(matcher.group(), getRandom(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))) + "");
        }
        Matcher matcher2 = Pattern.compile("%时间(.*?)%").matcher(replaceAll);
        while (matcher2.find())
        {
            replaceAll = replaceAll.replace(matcher2.group(), new SimpleDateFormat(matcher2.group(1)).format(new Date(System.currentTimeMillis())));
        }
		//Log.d("xxxxx",replaceAll);
        return replaceAll;
    }


	public static int getRandom(int start, int end)
	{
		return (int)(Math.random() * (end - start + 1) + start);
	}

    /* access modifiers changed from: protected */
    public abstract String a(String str, String str2);

    public String a(String... strArr)
    {

        String finalmsg = "";
        int index = 0;
        while (index < strArr.length)
        {
            String a2 = a(strArr[index]);
			//Log.d("y",strArr[index]);
            a2 = processinsertvalue(a2);
            Matcher matcher2 = Pattern.compile("\\[(-?[0-9]+)(\\+|-|\\*|/|%)(-?[0-9]+)\\]").matcher(a2);
            while (matcher2.find())
            {
                long parseLong = Long.parseLong(matcher2.group(1));
                long parseLong2 = Long.parseLong(matcher2.group(3));
                if (matcher2.group(2).equals("+"))
                {
                    a2 = a2.replace(matcher2.group(), (parseLong + parseLong2) + "");
                }
                if (matcher2.group(2).equals("-"))
                {
                    a2 = a2.replace(matcher2.group(), (parseLong - parseLong2) + "");
                }
                if (matcher2.group(2).equals("*"))
                {
                    a2 = a2.replace(matcher2.group(), (parseLong * parseLong2) + "");
                }
                if (matcher2.group(2).equals("/"))
                {
                    a2 = a2.replace(matcher2.group(), (parseLong / parseLong2) + "");
                }
                if (matcher2.group(2).equals("%"))
                {
                    a2 = a2.replace(matcher2.group(), (parseLong % parseLong2) + "");
                }
            }

            String tmpmsg = a2;
            int tempindex = index;
			tmpmsg =   executecmd(a2);
			LogcatLoger.d("executed", tmpmsg);
            
            if (tmpmsg.indexOf("如果:") == 0)
            {
				HashMap<String,Object> u =  processif(tmpmsg, strArr, tempindex);

                tempindex = u.get("index");
				tmpmsg = (String) u.get("msg");
				if ((boolean)u.get(("break")))
				{
					finalmsg =  finalmsg + tmpmsg;
					break;
				}
            }

            index = tempindex + 1;
            finalmsg =  finalmsg + tmpmsg;
        }
        return finalmsg;
    }

	private String processinsertvalue(String a2)
	{
		if (a2.indexOf(":") == 1)
		{
			this.a.put(a2.substring(0, 1), a2.substring(2));
			LogcatLoger.d("mapput", a2.substring(0, 1) + " " + a2.substring(2));
			a2 = "";
		}
		Pattern compile = Pattern.compile("%([^%]+?)%");
		Matcher matcher = compile.matcher(a2);
		while (matcher.find())
		{

			a2 = a2.replace(matcher.group(), ((String) this.a.get(matcher.group(1))) + "");
			matcher = compile.matcher(a2);
		}
		return a2;
	}

	private String executecmd(String a2)
	{
		Pattern compile2 = Pattern.compile("(?s)\\$([^\\$]+)\\$");
		Matcher matcher3 = compile2.matcher(a2);
		String tmpmsg="";
		String msg2return=a2;
		while (matcher3.find())
		{
			final String[] split = matcher3.group(1).split(" ");
			if (split.length <= 0)
			{
				tmpmsg = tmpmsg.replace(matcher3.group(), "");
			}
			else
			{
				if (split[0].equals("jump"))
				{
//					if (split.length > 1 && split[1].matches("-?[0-9]+"))
//					{
//						int parseInt = Integer.parseInt(split[1]) + tempindex;
//						if (parseInt < 0)
//						{
//							parseInt = 0;
//						}
//						tempindex = parseInt >= strArr.length ? strArr.length - 1 : parseInt;
//					}
//					replace = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("调用") && split.length > 2)
				{
					final long parseLong3 = Long.parseLong(split[1]);
					new Thread(new Runnable() {
							public void run()
							{
								if (parseLong3 > 0)
								{
									try
									{
										Thread.sleep(parseLong3);
									}
									catch (Exception e)
									{
										e.printStackTrace();
										return;
									}
								}
								//DicExecutor.this.d.clearMsg();
								String str = "";
								for (int i = 2; i < split.length; i++)
								{
									str = str + split[i];
									if (i != split.length - 1)
									{
										str = str + " ";
									}
								}
								Msg g= new Msg();
								g.code = DicExecutor.this.d.code;
								g.groupid = DicExecutor.this.d.groupid;
								g.groupName = DicExecutor.this.d.groupName;
								g.isfromgroup = DicExecutor.this.d.isfromgroup;
								g.isgroupmessage = DicExecutor.this.d.isgroupmessage;
								g.operator = DicExecutor.this.d.operator;
								g.reply = DicExecutor.this.d.reply;
								g.replytext = DicExecutor.this.d.replytext;
								g.time = DicExecutor.this.d.time;
								g.title = DicExecutor.this.d.title;
								g.type = DicExecutor.this.d.type;
								g.uin = DicExecutor.this.d.uin;
								g.uinName = DicExecutor.this.d.uinName;
								g.value = DicExecutor.this.d.value;
								g.addMsg("msg", str);
								//DicExecutor.this.d.addMsg("msg", str);
								PluginCenter.getInstence().getDicPlugin().process(g);
							}
						}).start();
					tmpmsg = msg2return.replace(matcher3.group(), "");
					LogcatLoger.d("调用后", tmpmsg);
				}
				else if (split[0].equals("删除") && split.length == 2)
				{
					File file = new File(Environment.getExternalStorageDirectory() + split[1]);
					if (file.exists())
					{
						file.delete();
					}
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("下载") && split.length == 3)
				{
					new classh(split[2], split[1]);
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("读") && split.length == 4)
				{
					tmpmsg = msg2return.replace(matcher3.group(), com.saki.plugin.Dictionary.classb.a(split[1], split[2], split[3]));
				}
				else if (split[0].equals("写") && split.length == 4)
				{
					com.saki.plugin.Dictionary.classb.b(split[1], split[2], split[3]);
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("撤回"))
				{
					withDraw(this.d.groupid, this.d.isfromgroup, this.d.isgroupmessage);
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("退群"))
				{
					exit(this.d.groupid);
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("禁") && split.length == 4)
				{
					membershutup(Long.parseLong(split[1]), Long.parseLong(split[2]), Integer.parseInt(split[3]));
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("全禁") && split.length == 2)
				{
					groupshutup(Long.parseLong(split[1]), true);
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("通过"))
				{
					if (this.d.type == 28)
					{
						passreq(this.d.time);
					}

					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("更新群列表"))
				{
					DebugLoger.log("ffff", "fccc");
					this.b.updateGroupList();
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("火力全开"))
				{
					this.b.setallgroupenabled();
					tmpmsg = tmpmsg.replace(matcher3.group(), "");
				}
				else if (split[0].equals("拒绝"))
				{
					if (this.d.type == 28)
					{
						unpassreq(this.d.time);
					}

					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("全解") && split.length == 2)
				{
					groupshutup(Long.parseLong(split[1]), false);
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("改") && split.length == 4)
				{
					a(Long.parseLong(split[1]), Long.parseLong(split[2]), split[3]);
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("踢") && split.length == 3)
				{
					kick(Long.parseLong(split[1]), Long.parseLong(split[2]));
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}

				else if (split[0].equals("发送") && split.length >= 5)
				{
					String str4 = "";
					int i4 = 5;
					while (i4 < split.length)
					{
						DebugLoger.log("shit", "shit");
						str4 = i4 == split.length + -1 ? str4 + split[i4] : str4 + split[i4] + " ";
						i4++;
					}
					String replaceAll = str4.replaceAll("\\\\r", "\r");
					if (split[1].equals("群"))
					{
						String msg = "";
						for (int ii1=0;ii1 < split.length;ii1++)
						{
							if (ii1 > 3)
							{
								msg += split[ii1];
							}
						}
						b(split[2], (long) Long.parseLong(split[3]), msg.replaceAll("\\\\r", "\r"));
					}
					else if (split[1].equals("好友") && split.length > 5)
					{
						a(split[2], (long) Integer.parseInt(split[3]), replaceAll);
					}
					else if (split[1].equals("临时") && split.length > 5)
					{
						a(split[2], Long.parseLong(split[3]), Long.parseLong(split[4]), replaceAll);
					}
					tmpmsg = tmpmsg.replace(matcher3.group(), "");
				}
				else if (split[0].equals("点赞"))
				{
					if (split.length >= 2)
					{
						this.b.b(Long.parseLong(split[1]));
					}
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}

				else if (split[0].equals("访问"))
				{
					tmpmsg = split.length == 2 ? msg2return.replace(matcher3.group(), a("GET", split[1])) : split.length == 3 ? msg2return.replace(matcher3.group(), a(split[1], split[2])) : msg2return.replace(matcher3.group(), "");
				}
				else if (split[0].equals("替换") && split.length > 2)
				{
					String[] split2 = matcher3.group(1).substring(split[1].length() + 4).split(split[1]);
					String str5 = split2.length >= 2 ? split2.length > 2 ? split2[0].replace(split2[1], split2[2]) : split2[0].replace(split2[1], "") : "";
					tmpmsg = msg2return.replace(matcher3.group(), str5);
				}
				else if (!split[0].equals("取中间") || split.length <= 2)
				{
					tmpmsg = msg2return.replace(matcher3.group(), "");
				}
				else
				{
					String[] split3 = matcher3.group(1).substring(split[1].length() + 5).split(split[1]);
					String str;
					if (split3.length >= 2)
					{
						int i5 = 0;
						if (!split3[1].equals(""))
						{
							int indexOf = split3[0].indexOf(split3[1]);
							i5 = indexOf != -1 ? indexOf + split3[1].length() : 0;
						}
						int length = split3[0].length();
						if (split3.length > 2)
						{
							length = split3[0].indexOf(split3[2], i5);
							if (length == -1)
							{
								length = split3[0].length();
							}
						}
						str = i5 < length ? split3[0].substring(i5, length) : "";
					}
					else
					{
						str = "";
					}
					tmpmsg = msg2return.replace(matcher3.group(), str);
				}

				matcher3 = compile2.matcher(tmpmsg);
				msg2return = tmpmsg;

			}
		}
		return msg2return;
	}

	private HashMap<String,Object> processif(String tmpmsg, String[] strArr, int tempindex)
	{
		LogcatLoger.d("processif", "");
		String msg2return="";
		LogcatLoger.d("z", tmpmsg);
		int i =0;
		String substring = tmpmsg.substring(3);
		String[] split4 = substring.split("\\||&");
		boolean z = processcompare(split4, substring);
		LogcatLoger.d("判断结果", z + "");
		HashMap<String,Object> h = new HashMap<String,Object>();
		h.put("break",false);
		if (z)
		{
			i = tempindex + 1;
			boolean reached=false;
			while (i < strArr.length)
			{

				strArr[i] = strArr[i].replaceAll("^[\\s]+", "");
				
				strArr[i] = executecmd(processinsertvalue(a(strArr[i])));
			    LogcatLoger.d("vvvv", strArr[i]);
				LogcatLoger.d("reached", reached + "");

				if (strArr[i].indexOf("如果:") == 0)
				{
					if (reached)
					{
						HashMap<String,Object> u =  processif(strArr[i], strArr, i);
						i = u.get("index");
				    }
					else
					{
						HashMap<String,Object> u =  processif(strArr[i], strArr, i);
						strArr[i] = "";
						msg2return += u.get("msg");
						i = u.get("index");
						if ((boolean)u.get(("break")))
						{
							u.put("msg", msg2return);
							return u;
						}
					}
				}
				if (strArr[i].indexOf("否则 如果:") == 0)
				{
					strArr[i] = "";
					reached = true;
				
					}
				if (strArr[i].equals("否则"))
				{
					strArr[i] = "";
					reached = true;
				}
				if (strArr[i].equals("结束"))
				{
					strArr[i] = "";
					break;
				}
				if (strArr[i].equals("返回"))
				{
					strArr[i] = "";
					if (reached)
					{
						h.put("break", false);
					}
					else
					{
						h.put("break", true);
						reached=true;
						
					}
				}
				if (reached)
				{

				}
				else
				{
				    msg2return += strArr[i];
				}

				i++;
			}
		}
		else
		{
			i = tempindex + 1;
			boolean reached=false;
			while (i < strArr.length)
			{

				strArr[i] = strArr[i].replaceAll("^[\\s]+", "");
				strArr[i] = executecmd(processinsertvalue(a(strArr[i])));
				LogcatLoger.d("vvvv", strArr[i]);
				LogcatLoger.d("reached", reached + "");
				if (strArr[i].indexOf("如果:") == 0)
				{
					if (reached)
					{
						HashMap<String,Object> u =  processif(strArr[i], strArr, i);
						strArr[i] = "";
						msg2return += u.get("msg");
						i = u.get("index");
						if ((boolean)u.get(("break")))
						{
							u.put("msg", msg2return);
							return u;
						}
					}
					else
					{
						HashMap<String,Object> u =  processif(strArr[i], strArr, i);
						i = u.get("index");
					}
				}
				if (strArr[i].indexOf("否则 如果:") == 0)
				{
					String substring1 = strArr[i].substring(6);
					String[] split41 = substring1.split("\\||&");
					strArr[i] = "";
					reached = processcompare(split41, substring1);
				}
				if (strArr[i].equals("否则"))
				{
					strArr[i] = "";
					if (reached)
					{
						reached = false;
					}
					else
					{
						reached = true;
					}
				}
				if (strArr[i].equals("返回"))
				{
					
					strArr[i] = "";
					if (reached)
					{
						h.put("break", true);
						reached=false;
						
					}
					else
					{
						h.put("break", false);
					}

					
				}
				if (strArr[i].equals("结束"))
				{
					strArr[i] = "";
					break;
				}
				if (reached)
				{
					msg2return += strArr[i];
				}
				else
				{

				}

				i++;
			}
		}
		
		h.put("msg", msg2return);
		h.put("index", i);
		return h;
	}

	private boolean processcompare(String[] split4, String substring)
	{
		boolean[] zArr = new boolean[split4.length];
		for (int i7 = 0; i7 < split4.length; i7++)
		{
			Matcher matcher4 = Pattern.compile("(.*?)([==|!=|>|<|>=|<=]+)(.*)").matcher(split4[i7]);
			while (matcher4.find())
			{
				if (!matcher4.group(1).matches("-?[0-9]+") || !matcher4.group(3).matches("-?[0-9]+"))
				{
					zArr[i7] = matcher4.group(1).equals(matcher4.group(3));
				}
				else
				{
					long parseLong4 = Long.parseLong(matcher4.group(1));
					long parseLong5 = Long.parseLong(matcher4.group(3));
					if (matcher4.group(2).equals("=="))
					{
						zArr[i7] = parseLong4 == parseLong5;
					}
					if (matcher4.group(2).equals("!="))
					{
						zArr[i7] = parseLong4 != parseLong5;
					}
					if (matcher4.group(2).equals(">="))
					{
						zArr[i7] = parseLong4 >= parseLong5;
					}
					if (matcher4.group(2).equals("<="))
					{
						zArr[i7] = parseLong4 <= parseLong5;
					}
					if (matcher4.group(2).equals(">"))
					{
						zArr[i7] = parseLong4 > parseLong5;
					}
					if (matcher4.group(2).equals("<"))
					{
						zArr[i7] = parseLong4 < parseLong5;
					}
				}
				substring = substring.replace(matcher4.group(), "");
			}
		}
		boolean z = zArr[0];
		for (int i8 = 1; i8 < zArr.length; i8++)
		{
			z = substring.charAt(i8 + -1) == '|' ? z | zArr[i8] : z & zArr[i8];
		}
		return z;
	}

    public abstract void exit(long groupid);

    public abstract void withDraw(long groupid, long messageCurrency, long messageInside);

    /* access modifiers changed from: protected */
    public abstract void kick(long j, long j2);

    public abstract void groupshutup(long j, boolean j2);

	public abstract void passreq(long j);

	public abstract void unpassreq(long j);

    /* access modifiers changed from: protected */
    public abstract void membershutup(long j, long j2, int i);

    /* access modifiers changed from: protected */
    public abstract void a(long j, long j2, String str);

    /* access modifiers changed from: protected */
    public abstract void a(String str, long j, long j2, String str2);

    /* access modifiers changed from: protected */
    public abstract void a(String str, long j, String str2);

    /* access modifiers changed from: protected */
    public abstract void b(String str, long j, String str2);
}
