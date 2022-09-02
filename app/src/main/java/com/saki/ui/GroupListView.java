package com.saki.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import com.saki.qq.datapacket.pack.jce.Troop;
import com.saki.qq.datapacket.unpack.ReMessageSvc.PbGetMsg;
import com.saki.qq.datapacket.unpack.ReOnlinePush;
import com.saki.qq.datapacket.unpack.ReProfileService.Pb.ReqSystemMsgNew.Group.SysMsg;
import com.saki.qq.userinfo.User;
import com.saki.service.NewService;
import com.ayzf.sqvx.R;
import com.saki.ui.Adapter.GroupListViewAdapter;
import com.saki.ui.c.MessageListener;
import com.saki.ui.c.TroopDataListener;
import com.setqq.script.Msg;
import java.util.ArrayList;

public class GroupListView extends com.saki.ui.classa implements OnRefreshListener, OnItemClickListener, MessageListener, TroopDataListener,android.widget.CompoundButton.OnCheckedChangeListener
{

	@Override
	public void ontimerMsg(Msg h)
	{
	}
	

	

	@Override
	public void onGenericEventMessage(Msg aVar)
	{
		// TODO: Implement this method
	}


	@Override
	public void onGenericGroupEventMessage(Msg aVar)
	{
		// TODO: Implement this method
	}
	
    public GroupListViewAdapter gropviewadapter;
    public SwipeRefreshLayout swiperefreshlayout;
	public SwitchCompat allgroupswitch;
    private String[] d = {"请打开开关后重试^_^", "开关在最右边呢^_^", "不打开开关的话，你再怎么点也没有反应哦！", "你是笨蛋吗？再戳咱可报警了哦。"};
    private int e = 0;

    public GroupListView(Context context) {
        super(context, R.layout.layout_view_grouplist);
		
    }

    /* access modifiers changed from: protected */
   

    public void onMessageIncrease(long j) {
        this.gropviewadapter.onMessageIncrease(j);
    }

    public void onGroupMemberDataUpdate(long j, ArrayList<com.saki.qq.datapacket.pack.jce.Member> arrayList) {
    }

    public void onPrivateMessage(PbGetMsg aVar) {
    }

    public void onDiscussMessage(ReOnlinePush.PbPushDisMsg aVar) {
    }

    public void onGroupMessage(ReOnlinePush.PbPushGroupMsg bVar) {
        onMessageIncrease(bVar.groupId);
    }

    public void onTransMessage(ReOnlinePush.PbPushTransMsg cVar) {
    }

    public void onSysMessage(SysMsg aVar) {
    }

    public void onCall(NewService newService) {
        newService.updateGroupList();
    }

    public void onGroupDataUpdate(ArrayList<Troop> arrayList) {
        this.swiperefreshlayout.post(new Runnable() {
            public void run() {
                GroupListView.this.swiperefreshlayout.setRefreshing(false);
            }
        });
        
        this.gropviewadapter.updateGroupList();
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"InlinedApi"})
    public ListView b(Context context) {
        ListView listView = findViewById(R.id.grouplistview_list);
        this.gropviewadapter = new GroupListViewAdapter(context);
        this.swiperefreshlayout = (SwipeRefreshLayout) findViewById(R.id.id009f);
        this.swiperefreshlayout.setColorSchemeResources(17170450, 17170454, 17170456, 17170452);
        this.swiperefreshlayout.setOnRefreshListener(this);
		this.allgroupswitch=this.findViewById(R.id.grouplistview_list_switch);
		this.allgroupswitch.setOnCheckedChangeListener(this);
        listView.setAdapter(this.gropviewadapter);
        listView.setOnItemClickListener(this);
        com.saki.qq.userinfo.TroopDataCenter.getListener().addListener((TroopDataListener) this);
        return listView;
    }

	@Override
	public void onCheckedChanged(CompoundButton p1, boolean p2)
	{
		this.gropviewadapter.setallgroupenable(p2);
	}
	
	
    public void onDestroy() {
        if (this.gropviewadapter != null) {
            com.saki.qq.userinfo.TroopDataCenter.getListener().removeListener((TroopDataListener) this);
        }
        super.onDestroy();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        if (com.saki.qq.userinfo.TroopDataCenter.getListener().isGroupEnabled(j)) {
            Msg msg = new Msg();
            msg.addMsg("msg", "[DB]:此消息仅为测试使用，软件正常运行中...");
            getService().a(-1, j, -1, msg.getData());
            Snackbar.make(swiperefreshlayout, "测试消息已经发送了哦\n如果没有收到，那就重新登录吧，", 1000).show();
            //Snackbar.make(swiperefreshlayout, "点我没有任何作用", 1000).show();
            return;
        }
        if (this.e >= this.d.length) {
            this.e = 0;
        }
        Snackbar.make(swiperefreshlayout, this.d[this.e], 1000).show();
        this.e++;
    }

    public void onRefresh() {
        getService().updateGroupList();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                GroupListView.this.swiperefreshlayout.setRefreshing(false);
            }
        }, 15000);
    }
}
