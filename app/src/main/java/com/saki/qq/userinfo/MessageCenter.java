package com.saki.qq.userinfo;

import com.saki.qq.datapacket.unpack.ReMessageSvc.PbGetMsg;
import com.saki.qq.datapacket.unpack.ReProfileService.Pb.ReqSystemMsgNew.Group.SysMsg;
import com.saki.ui.c.MessageListener;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.saki.qq.datapacket.unpack.*;
import com.setqq.script.Msg;

public class MessageCenter implements MessageListener
{

	@Override
	public void onGenericEventMessage(Msg aVar)
	{
		for (MessageListener a2 : this.listeners) {
            a2.onGenericEventMessage(aVar);
        }
	}


	@Override
	public void onGenericGroupEventMessage(Msg aVar)
	{
		for (MessageListener a2 : this.listeners) {
            a2.onGenericGroupEventMessage(aVar);
        }
	}
	

	
	@Override
	public void ontimerMsg(Msg h)
	{
		for (MessageListener a2 : this.listeners) {
            a2.ontimerMsg(h);
        }
	}
	

    private static final MessageCenter _this = new MessageCenter();
    private List<MessageListener> listeners = new CopyOnWriteArrayList<MessageListener>();

    public MessageCenter() {
        addListener((MessageListener) PluginCenter.getInstence());
    }

    public static MessageCenter _this() {
        return _this;
    }

    public void onPrivateMessage(PbGetMsg aVar) {
        for (MessageListener a2 : this.listeners) {
            a2.onPrivateMessage(aVar);
        }
    }

    public void onDiscussMessage(ReOnlinePush.PbPushDisMsg aVar) {
        for (MessageListener a2 : this.listeners) {
            a2.onDiscussMessage(aVar);
        }
        com.saki.loger.DebugLoger.log(this, "来自讨论组:[" + aVar.groupName + "](" + aVar.groupId + ")[【" + aVar.sendTitle + "】" + aVar.sendName + "](" + aVar.fromUin + "):" + aVar.msg);
    }

    public void onGroupMessage(ReOnlinePush.PbPushGroupMsg bVar) {
        for (MessageListener a2 : this.listeners) {
            a2.onGroupMessage(bVar);
        }
        com.saki.loger.DebugLoger.log(this, "来自群:[" + bVar.groupName + "](" + bVar.groupId + ")[【" + bVar.sendTitle + "】" + bVar.sendName + "](" + bVar.fromUin + "):" + bVar.msg);
    }

    public void onTransMessage(ReOnlinePush.PbPushTransMsg cVar) {
        for (MessageListener a2 : this.listeners) {
            a2.onTransMessage(cVar);
        }
    }

    public void onSysMessage(SysMsg aVar) {
        for (MessageListener a2 : this.listeners) {
            a2.onSysMessage(aVar);
        }
    }

    public void addListener(MessageListener classa) {
        if (!this.listeners.contains(classa)) {
            this.listeners.add(classa);
        }
    }
}
