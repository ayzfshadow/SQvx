package com.saki.ui.Adapter;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v7.widget.*;
import android.text.method.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import com.ayzf.sqvx.*;
import com.saki.ui.c.*;
import com.saki.ui.view.*;
import com.saki.util.*;
import com.setqq.script.*;
import java.io.*;
import java.util.*;

public class PluginListViewAdapter extends BaseAdapter implements PluginItemToUiBridge {
    /* access modifiers changed from: private */
    public final ArrayList<com.setqq.script.ScriptItem> scripts = new ArrayList<>();
    private final LayoutInflater b;
    private final PluginListViewAdapterHendler handler = new PluginListViewAdapterHendler();
    private final com.saki.ui.PluginListView d;

    class PluginViewItemStore {
        public ImageView logo;
        public TextView name;
        public TextView author;
        public TextView version;
        public TextView info;
        public SwitchCompat switchcompat;
        public DownloadButton download;

        PluginViewItemStore() {
        }
    }

    class PluginListViewAdapterHendler extends Handler {
        PluginListViewAdapterHendler() {
        }

        private void addScriptItem(com.setqq.script.ScriptItem classb) {
            Iterator it = PluginListViewAdapter.this.scripts.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (((com.setqq.script.ScriptItem) it.next()).id.equals(classb.id)) {
                        it.remove();
                        break;
                    }
                } else {
                    break;
                }
            }
            PluginListViewAdapter.this.scripts.add(classb);
        }

        public void updateUi() {
            Message obtain = Message.obtain();
            obtain.what = 1;
            sendMessage(obtain);
        }

        public void addToScriptListInUi(com.setqq.script.ScriptItem classb) {
            Message obtain = Message.obtain();
            obtain.obj = classb;
            obtain.what = 0;
            sendMessage(obtain);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 0) {
                addScriptItem((com.setqq.script.ScriptItem) message.obj);
                PluginListViewAdapter.this.notifyDataSetChanged();
            } else if (message.what == 1) {
                PluginListViewAdapter.this.notifyDataSetChanged();
            } else {
                com.setqq.script.ScriptItem classb = (com.setqq.script.ScriptItem) message.obj;
                Iterator it = PluginListViewAdapter.this.scripts.iterator();
                while (it.hasNext()) {
                    if (((com.setqq.script.ScriptItem) it.next()).equals(classb)) {
                        it.remove();
                        PluginListViewAdapter.this.notifyDataSetChanged();
                        return;
                    }
                }
            }
        }
    }

    public PluginListViewAdapter(Context context, com.saki.ui.PluginListView classf) {
        this.b = LayoutInflater.from(context);
        this.d = classf;
    }

    /* renamed from: a */
    public com.setqq.script.ScriptItem getItem(int i) {
        return (com.setqq.script.ScriptItem) this.scripts.get(i);
    }

    public void removeAllScript() {
        this.scripts.clear();
        this.handler.updateUi();
    }

    public void addScript(com.setqq.script.ScriptItem classb) {
        this.d.swipefreshlayout.setRefreshing(false);
        this.handler.addToScriptListInUi(classb);
    }

    public void updateUi() {
        this.handler.updateUi();
    }

    public void addScriptToUi(com.setqq.script.ScriptItem classb) {
        addScript(classb);
    }

    public int getCount() {
        return this.scripts.size();
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        final PluginViewItemStore aVar;
		final com.setqq.script.ScriptItem a2 = getItem(i);

		
        if (view == null) {
            PluginViewItemStore aVar2 = new PluginViewItemStore();
            view = this.b.inflate(R.layout.layout_item_plugin, null);
            aVar2.logo = view.findViewById(R.id.imageview_pluginlist_item_logo);
            aVar2.name = view.findViewById(R.id.imageview_pluginlist_item_name);
            aVar2.author = view.findViewById(R.id.imageview_pluginlist_item_author);
            aVar2.version = view.findViewById(R.id.imageview_pluginlist_item_version);
			
			aVar2.version.setBackgroundResource ( a2.sinfo == null ?R.drawable.bg_red_boll: R.drawable.bg_green_boll );
			
			
            aVar2.info = view.findViewById(R.id.imageview_pluginlist_item_info);
            aVar2.info.setMovementMethod(ScrollingMovementMethod.getInstance());
            aVar2.switchcompat = view.findViewById(R.id.imageview_pluginlist_item_switch);
            aVar2.download = view.findViewById(R.id.imageview_pluginlist_item_download);
            view.setTag(aVar2);
            aVar = aVar2;
        } else {
            aVar = (PluginViewItemStore) view.getTag();
        }
        
		if(a2.sinfo==null){
		if (a2.icon == null) {
            aVar.logo.setImageResource(R.drawable.drawable0064);
        } else {
            aVar.logo.setImageDrawable(a2.icon);
        }
		}else{
			try
			  {
				PackageManager packageManager = view.getContext ( ).getPackageManager ( );  
				ApplicationInfo applicationInfo = packageManager.getApplicationInfo ( a2.sinfo [ 4 ], 0 );
				aVar.logo.setImageDrawable ( packageManager.getApplicationIcon ( applicationInfo ) );
			  }
			catch (Exception e)
			  {
				e.printStackTrace ( );
				aVar.logo.setImageResource ( R.drawable.drawable0064 );
			  }
		}
		
		
        aVar.name.setText(a2.name);
        aVar.author.setText(a2.author);
        aVar.version.setText(a2.version);
        aVar.info.setText(a2.info);
        aVar.switchcompat.setChecked(a2.enabled);
        aVar.switchcompat.setOnCheckedChangeListener(a2);
        if (a2.status == 0) {
            aVar.download.setVisibility(8);
            aVar.switchcompat.setVisibility(0);
        } else if (a2.status == 1) {
            aVar.download.setVisibility(0);
            aVar.switchcompat.setVisibility(8);
        } else if (a2.status == 2) {
            aVar.download.setVisibility(0);
            aVar.download.setTitle("更新");
        }
        aVar.download.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                aVar.download.a(a2.downloadUrl,  new com.saki.ui.view.DownloadButton.DownloadCompleteListener() {
                    public void onDownloadComplete(byte[] bArr) throws Exception {
                        try {
                            if(a2.plugintype==0){
                            PluginUtil.plugPluginByFileName(com.saki.util.FileUtil.c, com.saki.util.ScriptUtil.a((InputStream) new ByteArrayInputStream(bArr), com.saki.util.FileUtil.c));
                            }else if(a2.plugintype==1){
                                File file = new File(Environment.getExternalStorageDirectory() + "/SQ/update/plugin.apk");
                                Snackbar.make(PluginListViewAdapter.this.d.swipefreshlayout,"安装完成后请手动刷新列表",1500).show();
                                Thread.currentThread().sleep(1500);
                                FileUtil.writeFile(file,bArr);
                                ApkUtil.installApk(PluginListViewAdapter.this.d.getContext(),file );
                                
                                //file.delete();
                            }
                        } catch (IOException e) {
                            com.saki.loger.ViewLoger.warnning("下载失败:" + a2.downloadUrl);
                        }
                    }
                });
            }
        });
        return view;
    }
}
