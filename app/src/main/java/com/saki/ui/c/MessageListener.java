package com.saki.ui.c;

import com.saki.qq.datapacket.unpack.ReMessageSvc.PbGetMsg;
import com.saki.qq.datapacket.unpack.ReOnlinePush;
import com.saki.qq.datapacket.unpack.ReOnlinePush.PbPushGroupMsg;
import com.saki.qq.datapacket.unpack.ReOnlinePush.PbPushTransMsg;
import com.saki.qq.datapacket.unpack.ReProfileService.Pb.ReqSystemMsgNew.Group.SysMsg;
import com.setqq.script.Msg;

public interface MessageListener
{

    void ontimerMsg(Msg h);

    void onPrivateMessage(PbGetMsg aVar);

    void onDiscussMessage(ReOnlinePush.PbPushDisMsg aVar);

    void onGroupMessage(PbPushGroupMsg bVar);

    void onTransMessage(PbPushTransMsg cVar);

    void onSysMessage(SysMsg aVar);
	
	void onGenericGroupEventMessage(Msg aVar);
	
    void onGenericEventMessage(Msg aVar);
}
