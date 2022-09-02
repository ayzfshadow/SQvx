package saki.ui;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import com.example.vxdemo.R;
import com.setqq.script.Msg;
import com.setqq.script.sdk.IPlugin;
import com.setqq.script.sdk.PluginApiInterface;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Demo implements IPlugin
{
    private HashMap<Long,String>msgcache =new HashMap<>();

    @Override
    public Intent getActivity(Context p1)
    {
        return new Intent(p1, UI.class);
    }

    @Override
    public Drawable getIcon(Context p1)
    {
        return p1.getDrawable(R.drawable.ic_launcher);
    }

	PluginApiInterface api;
	long loadtime;
	/**
	 * 主程序加载插件时调用
	 * @param context 主程序上下文句柄
	 * @param api 接口类
	 */
	@Override
	public void onLoad(Context context, PluginApiInterface api)
	{
		this.api = api;
		this.loadtime = new Date().getTime();
	}

	/**
	 * 插件被打开或关闭时调用
	 * @param o 打开为true 关闭为false
	 */
	public void onSet(boolean o)
	{

	}
	/**
	 * 发送消息
	 * @param msg 消息包
	 * @return 返回结果包
	 */
	private Msg send(Msg msg)
	{
		if (api != null)
		{
			return api.send(msg);
		}
		return null;
	}
	/**
	 * 由主程序接收消息时调用
	 * @param 消息包体
	 */
	@Override
	public void onMessageHandler(Msg msg)
	{
		/*
         ///监听部分
		 * TYPE_GROUP_MSG = 0;// 群消息
		 * TYPE_BUDDY_MSG = 1;// 好友消息
		 * TYPE_DIS_MSG = 2;// 讨论组消息
		 * TYPE_SESS_MSG = 4;// 临时消息
		 * TYPE_SYS_MSG = 3;// 系统消息
         * TYPE_ADMINTRANSE_MSG = 25;// 管理变更
         * TYPE_MEMBERTRANSE_MSG = 26;// 有人退群/被踢
         * TYPE_MEMBERENTER_MSG = 27;// 有人入群
         * TYPE_MEMBERREQUEST_MSG = 28;// 有人请求入群
         * TYPE_MSG_CANSEL 29;//有人撤回消息
         * TYPE_MEMBER_SSHUTUP = 30;// 有人被禁言
         * TYPE_TRANSFER_MSG = 31;// 转账


         ////发送部分
         * TYPE_ADMINTRANSE_MSG = 25;// 管理变更
         * TYPE_MEMBERTRANSE_MSG = 26;// 有人退群/被踢
         * TYPE_MEMBERENTER_MSG = 27;// 有人入群
         * TYPE_MEMBERREQUEST_MSG = 28;// 有人请求入群
         *25-28会被当作群消息发送发送到群里，0-4按照监听部分发送原目标

		 * TYPE_GET_GROUP_LIST = 5;// 群列表，发送后返回的包getMsg("troop")为群列表
		 * TYPE_GET_GROUP_INFO = 6;// 群信息，发送后返回的包getMsg("member")为成员列表
		 * TYPE_GET_GROUP_MEBMER = 7;// 群成员
		 * TYPE_FAVORITE = 8;// 点赞
		 * TYPE_SET_MEMBER_CARD = 9;// 设置群名片
		 * TYPE_SET_MEMBER_SHUTUP = 10;// 成员禁言
		 * TYPE_SET_GROUP_SHUTUP = 11;// 群禁言
		 * TYPE_DELETE_MEMBER = 12;// 删除群成员
		 * TYPE_REQUEST_DEAL = 13;// 处理入群请求
		 * TYPE_GET_LOGIN_ACCOUNT = 15;// 获取机器人QQ,发送后返回包的Uin为机器人QQ
		 * TYPE_RECALL_MSG = 16;// 撤回消息
         * TYPE_UPDATE_GROUPLIST = 17;// 更新群列表
         * TYPE_ENABLE_ALL_GROUP = 18;// 开启全部群
         * TYPE_EXIT_GROUP =19；//退群
		 */
		//对消息类型进行判断
		//this.api.log(0, msg.getTextMsg() + msg.getMsg("at"));
		switch (msg.type)
		{

			case Msg.TYPE_GROUP_MSG:{// 0 群消息
			        //发送消息测试
                    this.msgcache.put(msg.isfromgroup, msg.getTextMsg());
					if (msg.getTextMsg().equals("文本"))
					{
						msg.clearMsg();
						msg.addMsg("msg", "你要的文本");
						this.api.send(msg);

					}
					else if (msg.getTextMsg().equals("xml"))
					{
						msg.clearMsg();
						msg.addMsg("xml", "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?><msg serviceID=\"2\" templateID=\"1\" action=\"web\" brief=\"应用分享\" sourceMsgId=\"0\" url=\"\" flag=\"0\" adverSign=\"0\" multiMsgFlag=\"0\"><item layout=\"2\"><audio cover=\"https://qlogo3.store.qq.com/qzone/352404041/352404041/100\" src=\"http://www.you168.cn/mp3/09_00.mp3\" /><title>2020年1月29日9时0分2秒</title><summary>Only超级自定义</summary></item><source name=\"\" icon=\"\" action=\"\" appid=\"-1\" /></msg>]");
						this.api.send(msg);
					}
					else if (msg.getTextMsg().equals("json"))
					{
						msg.clearMsg();
						msg.addMsg("json", "{\"app\":\"com.tencent.structmsg\",\"desc\":\"音乐\",\"view\":\"music\",\"ver\":\"0.0.0.1\",\"prompt\":\"[分享]芒种\",\"meta\":{\"music\":{\"sourceMsgId\":\"0\",\"title\":\"芒种\",\"desc\":\"音阙诗听;赵方婧\",\"preview\":\"https:\\/\\/y.gtimg.cn\\/music\\/photo_new\\/T002R300x300M000001QvT9r1lhLUr.jpg\",\"tag\":\"QQ音乐\",\"musicUrl\":\"http:\\/\\/ws.stream.qqmusic.qq.com\\/C4000020VnHM0U9uNh.m4a?guid=9154824960&vkey=FFD974C8D70EAE296074633835513EAC9BEC57ECB89004D5C9E1F88E3E439090EF0EE18B69B75BC554A5E72CE06CE4790B9EDDFA6B43136D&uin=0&fromtag=66\",\"jumpUrl\":\"https:\\/\\/i.y.qq.com\\/v8\\/playsong.html?_wv=1&songid=233060208\",\"appid\":100495085,\"app_type\":1,\"action\":\"\",\"source_url\":\"\",\"source_icon\":\"\",\"android_pkg_name\":\"\"}},\"config\":{\"forward\":true,\"type\":\"normal\",\"autosize\":true}}");
						this.api.send(msg);
					}
					else if (msg.getTextMsg().equals("语音"))
					{
						msg.clearMsg();
						msg.addMsg("ptt", "https://tts.baidu.com/text2audio?tex=这是你要的语音&cuid=baike&rate=192&ctp=1&pdt=301&vol=9&lan=ZH&per=0");
						this.api.send(msg);
					}
					else if (msg.getTextMsg().equals("图片"))
					{
						msg.clearMsg();
						msg.addMsg("img", "https://i.loli.net/2020/01/29/3AwEcYFXCgGpjbk.jpg");
						this.api.send(msg);
					}
					//群管功能测试
					else if (msg.getTextMsg().equals("全体禁言"))
					{
						msg.type = Msg.TYPE_SET_GROUP_SHUTUP;//11
						msg.value = 0;
						this.api.send(msg);
					}
					else if (msg.getTextMsg().equals("全体解禁"))
					{
						msg.type = Msg.TYPE_SET_GROUP_SHUTUP;//11
						msg.value = 1;
						this.api.send(msg);
					}
					else if (msg.getTextMsg().matches("禁言@.*"))
					{
						List<String> g  = msg.getMsg("at");
						if (g != null && g.size() > 0)
						{
							msg.type = Msg.TYPE_SET_MEMBER_SHUTUP;//10
							msg.value = 60;
							msg.uin = Long.parseLong(g.get(0).split("@")[0]);
							this.api.send(msg);
						}
					}
					else if (msg.getTextMsg().matches("解禁@.*"))
					{
						List<String> g  = msg.getMsg("at");
						if (g != null && g.size() > 0)
						{
							msg.type = Msg.TYPE_SET_MEMBER_SHUTUP;//10
							msg.value = 0;
							msg.uin = Long.parseLong(g.get(0).split("@")[0]);
							this.api.send(msg);
						}
					}
					else if (msg.getTextMsg().matches("踢出@.*"))//未测试
					{
						List<String> g  = msg.getMsg("at");
						if (g != null && g.size() > 0)
						{
							msg.type = Msg.TYPE_DELETE_MEMBER;//12
							msg.uin = Long.parseLong(g.get(0).split("@")[0]);
							this.api.send(msg);
						}
					}
				}break;

			case Msg.TYPE_ADMINTRANSE_MSG:{//25
					msg.clearMsg();
					if (msg.value == 1)
					{
						msg.addMsg("msg", msg.uin + "成为管理");
					}
					else if (msg.value == 0)
					{
						msg.addMsg("msg", msg.uin + "被除名出管理");
					}
					this.api.send(msg);
				}break;
			case Msg.TYPE_MEMBERTRANSE_MSG:{//26 //未测试
					msg.clearMsg();
					if (msg.value == 1)
					{
						msg.addMsg("msg", msg.uin + "退出群聊");
					}
					else if (msg.value == 0)
					{
						msg.addMsg("msg", msg.uin + "被踢出群聊");
					}
					this.api.send(msg);
				}break;
			case Msg.TYPE_MEMBERENTER_MSG:{//27 //未测试
					msg.clearMsg();
					msg.addMsg("msg", msg.uin + "进入群聊");
					this.api.send(msg);
				}break;
			case Msg.TYPE_MEMBERREQUEST_MSG:{//28 //未测试
			        msg.type = Msg.TYPE_REQUEST_DEAL;
					msg.value = 0;//写1就是拒绝
					msg.clearMsg();
					this.api.send(msg);
					msg.type = 0;
					msg.addMsg("msg", msg.uin + "申请加入本群");
					this.api.send(msg);
				}break;
			case Msg.TYPE_MSG_CANSEL:{//29
                    String j = this.msgcache.get(msg.isfromgroup);
                    if (j == null)
                    {
                        return;
                    }
                    msg.clearMsg();
                    msg.type=0;
                    msg.addMsg("msg", msg.operator +"撤回了"+msg.uin+"的消息："+j);
					this.api.send(msg);
                }break;
            case Msg.TYPE_MEMBER_SSHUTUP:{//30
                    msg.clearMsg();
                    msg.type=0;
                    msg.addMsg("msg", msg.operator +"把"+msg.uin+"禁言"+msg.value+"秒");
					this.api.send(msg);
            
            
            }

		}




	}

}
