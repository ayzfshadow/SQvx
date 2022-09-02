package com.saki.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.saki.loger.ViewLoger;
import java.util.ArrayList;
import java.util.Iterator;
import com.ayzf.sqvx.R;
import android.support.design.widget.Snackbar;
import android.widget.ExpandableListView;
import android.widget.BaseExpandableListAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

public class LogerView extends RelativeLayout implements SwipeRefreshLayout.OnRefreshListener
{
    PagerActivity pagerActivity;
    LogerViewAdapter logerViewAdapter;
    AnnounceView announceview;
    SwipeRefreshLayout swiperefreshlayout;
    com.saki.ui.LogHandler handler = new com.saki.ui.LogHandler() {
        public void call(int i, Object obj) {
            sendMessage(Message.obtain(this, 0, new LogStore(this.gettag(i), obj.toString())));
        }

        public void handleMessage(Message message) {
			com.saki.ui.LogerView.this.logerViewAdapter.addLog((LogStore) message.obj);
        }
        
        public String gettag(int i){
            return (new String[]{"信息","警告","错误"})[i];
        }
    };
    private ExpandableListView loglistview;

    private static class LogStore {
        String tag;
        String value;

        public LogStore(String i, String str) {
            this.tag = i;
            this.value = str;
        }
    }

    class LogerViewAdapter extends BaseExpandableListAdapter implements android.widget.ExpandableListView.OnChildClickListener
    {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v,
                                    int groupPosition, int childPosition, long id) {
                                        
            ((ClipboardManager) LogerView.this.pagerActivity.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("msg", getItem(groupPosition).value));
            Snackbar.make(LogerView.this, "复制完成", 1000).show();
            return false;     
                                   
            // TODO: Implement this method
       
        }

      
        
        @Override
        public int getGroupCount()
        {
            // TODO: Implement this method
            return this.logs.size();
        }

        @Override
        public int getChildrenCount(int p1)
        {
            // TODO: Implement this method
            return 1;
        }

        @Override
        public Object getGroup(int p1)
        {
            // TODO: Implement this method
            return this.logs.get(p1).tag;
        }

        @Override
        public Object getChild(int p1, int p2)
        {
            // TODO: Implement this method
            return this.logs.get(p1).value;
        }

        @Override
        public long getGroupId(int p1)
        {
            // TODO: Implement this method
            return p1;
        }

        @Override
        public long getChildId(int p1, int p2)
        {
            // TODO: Implement this method
            return p2;
        }

        @Override
        public boolean hasStableIds()
        {
            // TODO: Implement this method
            return true;
        }

        @Override
        public View getGroupView(int i, boolean p2, View view, ViewGroup viewGroup)
        {
            if (view == null) {
                view = c.inflate(R.layout.layout_item_log, viewGroup,false);
            }
            // set category name as tag so view can be found view later
            view.setTag(getGroup(i).toString());

            TextView textView = view.findViewById(R.id.logitem_tag);

            //"i" is the position of the parent/group in the list
            textView.setText(getGroup(i).toString());

            TextView sub = view.findViewById(R.id.logitem_value);        

           
                sub.setText(this.logs.get(i).value.toString());
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean p3, View view, ViewGroup viewGroup)
        {
            if (view == null) {
                view = c.inflate(R.layout.layout_item_log_expand, viewGroup,false);
            }


            TextView textView = view.findViewById(R.id.logitem_fullvalue);

            //"i" is the position of the parent/group in the list and 
            //"i1" is the position of the child
            textView.setText(this.logs.get(i).value);        

            // set checked if parent category selection contains child category

            //return the entire view
            return view;
        }

        @Override
        public boolean isChildSelectable(int p1, int p2)
        {
            // TODO: Implement this method
            return true;
        }
        
        private ArrayList<com.saki.ui.LogerView.LogStore> logs = new ArrayList<>();
        private final LayoutInflater c;

        class LogItemView {
            TextView tagview;
            TextView valueview;

            LogItemView() {
            }
        }

        public LogerViewAdapter(Context context) {
            this.c = LayoutInflater.from(context);
        }

        /* renamed from: a */
        public com.saki.ui.LogerView.LogStore getItem(int i) {
            return  this.logs.get(i);
        }

        public void cleanLoger() {
            this.logs.clear();
            notifyDataSetChanged();
        }

        /* access modifiers changed from: 0000 */
        
        public void addLog(com.saki.ui.LogerView.LogStore aVar) {
            this.logs.add(aVar);
            notifyDataSetChanged();
        }

        

    

     
    }

    public LogerView(PagerActivity pagerActivity) {
        super(pagerActivity);
        this.pagerActivity = pagerActivity;
        this.logerViewAdapter = new LogerViewAdapter(pagerActivity);
        LayoutInflater.from(pagerActivity).inflate(R.layout.layout_view_log, this);
        this.loglistview = findViewById(R.id.logview_loglistview);
        swiperefreshlayout= this.findViewById(R.id.swiperefreshlayout_logview);
        swiperefreshlayout.setOnRefreshListener(this);
        this.loglistview.setAdapter(this.logerViewAdapter);
        this.loglistview.setOnChildClickListener(this.logerViewAdapter);
        
        Iterator it = ViewLoger.a.iterator();
        while (it.hasNext()) {
            this.handler.sendMessage(Message.obtain(this.handler, 0, (String) it.next()));
        }
        ViewLoger.setHandler(this.handler);
    }

    public void closeLoger() {
        ViewLoger.closeLoger();
    }
    
    
    @Override
    public void onRefresh()
    {
        logerViewAdapter.cleanLoger();
        this.swiperefreshlayout.setRefreshing(false);
    }
}
