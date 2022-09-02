package com.saki.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.saki.service.NewService;
import com.setqq.script.ScriptItem;
import com.ayzf.sqvx.R;
import android.support.design.widget.Snackbar;
import android.net.Uri;


public class PluginListView extends classa implements OnRefreshListener, OnItemClickListener, OnItemLongClickListener
{
    public com.saki.ui.Adapter.PluginListViewAdapter pluginlistviewadapter;
    public SwipeRefreshLayout swipefreshlayout;

    public PluginListView(Context context)
    {
        super(context, R.layout.layout_view_pluginlist);
    }

    private void startScriptWebview(ScriptItem classb)
    {
        if (classb.hasUi && classb.id != null && !classb.id.equals("") && classb.plugin != null)
        {
            Intent intent = new Intent();
            intent.setClass(getContext(), ScriptWebView.class);
            intent.putExtra("script", classb.id);
            getContext().startActivity(intent);
        }
    }

    /* access modifiers changed from: protected */

    public void onCall(NewService newService)
    {
        newService.loadScripts();
    }

    /* access modifiers changed from: protected */
    @SuppressLint({"InlinedApi"})
    public ListView b(Context context)
    {
        ListView listView = findViewById(R.id.listview_pluginlistview);
        this.pluginlistviewadapter = new com.saki.ui.Adapter.PluginListViewAdapter(context, this);

        this.swipefreshlayout = findViewById(R.id.listview_swiperefreshlayout);
        this.swipefreshlayout.setColorSchemeResources(17170450, 17170454, 17170456, 17170452);
        this.swipefreshlayout.setOnRefreshListener(this);
        listView.setAdapter(this.pluginlistviewadapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        com.saki.qq.userinfo.PluginCenter.getInstence().setBridge((com.saki.ui.c.PluginItemToUiBridge) this.pluginlistviewadapter);
        return listView;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j)
    {
        ScriptItem item =   this.pluginlistviewadapter.getItem(i);
		
	  if ( item.sinfo != null )
		{
		  Intent intent = getContext ( ).getPackageManager ( ).getLaunchIntentForPackage ( item.id );
		  intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK );

		  try
			{
			  getContext ( ).startActivity ( intent );
			}
		  catch (Exception noFound)
			{
			  Toast.makeText ( getContext ( ), "Package not found!", Toast.LENGTH_SHORT ).show ( );
			}

		  return;
		}
		
		
        if (item.plugintype == 0)
        {
            startScriptWebview(item);
        }
        else if (item.plugintype == 1)
        {
            try
            {
                if (item.hasUi)
                {
                   Intent intent = item.getimpActivity();
                    intent.setPackage(item.id);
                    getContext().startActivity(intent);

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Snackbar.make(swipefreshlayout, "此插件没有主界面", 1000).show();
            }
        }
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j)
    {
        final ScriptItem sscriptitem = this.pluginlistviewadapter.getItem(i);
		
	  if ( sscriptitem.sinfo != null )
		{
		  Uri uri = Uri.fromParts ( "package", sscriptitem.id, null );
		  Intent intent = new Intent ( Intent.ACTION_DELETE, uri );
		  getContext ( ).startActivity ( intent );

		  return true;
		}
		
		
		
        if (sscriptitem.plugintype == 2)
        {
            Snackbar.make(this, "此插件无法卸载", 1000).show();
            return false;
        }
        else if (sscriptitem.plugintype == 0)
        {
            Builder builder = new Builder(getContext());
            builder.setTitle("卸载提示");
            builder.setMessage("确定要卸载[" + sscriptitem.name + "]吗？");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("卸载/删除", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        sscriptitem.deletScript(PluginListView.this.getContext());
                        PluginListView.this.getService().loadScripts();
                    }
                });
            builder.show();
        }
        else if (sscriptitem.plugintype == 1)
        {
            Builder builder = new Builder(getContext());
            builder.setTitle("卸载提示");
            builder.setMessage("确定要卸载[" + sscriptitem.name + "]吗？\n卸载后请手动刷新列表。");
            builder.setNegativeButton("取消", null);
            builder.setPositiveButton("卸载/删除", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        Intent intent = new Intent("android.intent.action.DELETE");
                        intent.setData(Uri.parse("package:" + sscriptitem.id));
                        getContext().startActivity(intent);
                    }
                });
            builder.show();
        }
        return true;
    }

    public void onRefresh()
    {
        getService().loadScripts();
    }
}
