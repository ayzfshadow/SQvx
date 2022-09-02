package com.setqq.script;

import android.content.*;
import android.content.SharedPreferences.*;
import android.graphics.drawable.*;
import android.widget.*;
import com.saki.qq.userinfo.*;
import com.saki.service.*;
import com.saki.ui.*;
import com.saki.util.*;
import java.io.*;
import com.saki.client.SQApplication;

public class ScriptItem implements CompoundButton.OnCheckedChangeListener
{
    public String name;
    public String id;
    public String author;
    public String version;
    public String info;
    public Drawable icon;
    public com.setqq.script.sdk.PluginInterface plugin;
    public boolean enabled = false;
    public boolean hasUi = false;
    public int status =0;//1的时候是还没下载，2的时候是可以更新，0应该就是正常状态
    public int plugintype =0;//0的时候是脚本，1的时候是插件，2的时候是内置词库插件
    public String downloadUrl;
    private NewService newservice;


	public String[] sinfo;


	public ScriptItem(String[] info)
    {
		this.sinfo=info;
		newservice=NewService.getService();

		this.name=info[0];
		this.id=info[4];
		this.version=info[1];
		this.author=info[2];
		this.info=info[3];

		this.enabled=getSharedPref((Context) NewService.getService()).getBoolean(this.id,false);

		PluginCenter.getInstence().addScript(this);
    }



    public ScriptItem(NewService newService,String id,String name,String author,String version,String info,boolean z,Drawable drawable,com.setqq.script.sdk.PluginInterface plugin)
    {
        this.newservice=newService;
        this.id=id;
        this.name=name;
        this.author=author;
        this.version=version;
        this.info=info;
        this.icon=drawable;
        this.plugin=plugin;
        this.hasUi=z;
        this.enabled=getSharedPref((Context) newService).getBoolean(this.id,false);
        PluginCenter.getInstence().addScript(this);
        loadScript(newService);
    }

    public Intent getimpActivity()
    {
        return this.plugin.getimpClass();
    }


    public void setType(int p0)
    {
        this.plugintype=p0;
    }

    public static SharedPreferences getSharedPref(Context context)
    {
        return context.getSharedPreferences("app_config",0);
    }

    public String getUiUrl()
    {
        if (this.plugin != null)
        {
            return this.plugin.getUiUrl();
        }
        return null;
    }

    public void loadScript(NewService newService)
    {
        if (this.plugin != null)
        {
            try
            {
                this.plugin.onload(newService);
            }
            catch (Throwable e2)
            {
                e2.printStackTrace();
                com.saki.loger.ViewLoger.warnning("插件初始化异常:" + e2.toString());
            }
        }
    }

    public void onCheckedChanged(CompoundButton switchButton,boolean z)
    {
        this.enabled=z;
        Editor edit = getSharedPref((Context) this.newservice).edit();
        edit.putBoolean(this.id,this.enabled);
        edit.commit();
    }

    public ScriptItem setFromMacket(String str)
    {//说明这个插件是从服务器来的，还没下载
        this.status=1;
        this.downloadUrl=str;
        return this;
    }

    public boolean onMessage(Msg msg)
    {//消息通知插件处理


		if (this.sinfo != null)
        {
			if (SQApplication.getServer() != null)
            {
				SQApplication.getServer().send(msg);
            }

			return true;
        }

        try
        {
            if (this.plugin != null)
            {
                this.plugin.process(msg);
                return true;
            }
        }
        catch (Throwable e2)
        {
            e2.printStackTrace();
            com.saki.loger.ViewLoger.erro((Object) "[" + this.name + "]脚本发生异常:" + e2.getMessage());
        }
        return false;
    }

    public void stop()
    {
	  	if (this.sinfo != null)
        {
			return;
        }

        try
        {
            this.plugin.onstop();
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
            com.saki.loger.ViewLoger.erro((Object) "脚本停止时异常:" + e2.toString());
        }
    }

    public void deletScript(Context context)
    {
	  	if (this.sinfo != null)
        {
			return;
        }

        if (this.id != null)
        {
            FileUtil.deleteFile(new File(FileUtil.externalpath + "Script/" + this.id));
        }
    }

    public boolean equals(Object obj)
    {
        return obj instanceof ScriptItem ? this.id.equals(((ScriptItem) obj).id) : super.equals(obj);
    }
}
