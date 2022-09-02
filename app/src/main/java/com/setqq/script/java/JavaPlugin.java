package com.setqq.script.java;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import com.setqq.script.sdk.IPlugin;
import com.setqq.script.sdk.ScriptPlugin;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class JavaPlugin extends ScriptPlugin
{

    private Context plugincontext;

    @Override
    public Intent getimpClass()
    {
        return this._class.getActivity(this.plugincontext);
    }


    private NewService newService;

    private Drawable icon;

    private IPlugin _class;

    private boolean hasUi;

	private String path;

    public JavaPlugin(NewService context, String packageName, String author, String version, String info, String name, boolean z, IPlugin a, String path) throws  Exception
    {
        this.newService = context;
        this.id = packageName;
        this.name = name;
        this.author = author;
        this.version = version;
        this.info = info;
        this.path = path;
        this._class = a;


		this.plugincontext  = context.createPackageContext(this.id, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
		AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(this.plugincontext.getAssets(), this.path);
		Class<?> clazz=Class.forName("android.app.ContextImpl");
		Method method=clazz.getDeclaredMethod("getImpl", Context.class);
		method.setAccessible(true);
		Object mContextImpl=method.invoke(null, plugincontext);
		//获取ContextImpl的实例
		//Log.d("csdn","mContextImpl="+mContextImpl);
		Field mPreferencesDir=clazz.getDeclaredField("mPreferencesDir");
		mPreferencesDir.setAccessible(true);
		// 获取mBase变量
		mPreferencesDir.set(mContextImpl, new File(plugincontext.getFilesDir(), "../shared_prefs"));

        this.icon = _class.getIcon(this.plugincontext);
        this.ui = "";
        this.hasUi = z;

        overridenInitPluginItem(context);
        com.saki.loger.ViewLoger.info("java插件" + name + "加载完成!");
    }

    private void overridenInitPluginItem(NewService context)
    {
        new com.setqq.script.ScriptItem(newService, id, name, author, version, info, hasUi(), this.icon, this).setType(1);
    }

    @Override
    public String getIcon()
    {
        return null;
    }

    @Override
    public boolean hasUi()
    {
        // TODO: Implement this method
        return this.hasUi;
    }

    @Override
    public void onstop()
    {
        this._class=null;
		System.gc();
    }

    @Override
    public void onload(NewService context) throws Throwable
    {

        this._class.onLoad(plugincontext, new com.setqq.script.sdk.PluginApi(context));
    }

    @Override
    public void process(Msg msg) throws Throwable
    {
        try
		{
			this._class.onMessageHandler(msg);
			if (msg.type == 18)
			{
				onstop();
			}
        }
		catch (Exception e)
		{
            com.saki.loger.ViewLoger.erro(this.name + "发生异常:" + e.getMessage());
        }
    }

    @Override
    public String onAction(String str) throws Throwable
    {
        // TODO: Implement this method
        return null;
    }


}
