package com.setqq.script;

import android.content.*;
import android.content.pm.*;
import android.os.*;
import com.saki.loger.*;
import com.saki.service.*;
import com.saki.ui.*;
import com.setqq.script.lua.*;
import com.setqq.script.sdk.*;
import dalvik.system.*;
import java.io.*;
import java.util.*;
import com.saki.client.SQApplication;

public class PluginUtil
{
    /* access modifiers changed from: private */
    public static NewService newservice;

	private static List<String> j  = new ArrayList<>();

	private static HashMap<String,PathClassLoader> i  = new HashMap<String,PathClassLoader>();
    public static void loadJavaPlugin(NewService context) throws InstantiationException, IllegalAccessException
    {

        PackageManager packageManager = context.getPackageManager();
        for (PackageInfo packageInfo : packageManager.getInstalledPackages(8320))
		{
			
            Bundle bundle = packageInfo.applicationInfo.metaData;
            if (bundle != null)
			{
                String string = bundle.getString("vxpluginclass");
                if (string != null)
				{
                    String author = bundle.getString("author", "佚名");
                    String version = packageInfo.versionName;
                    String info = bundle.getString("info", "暂无说明");
                    String name = (String) packageInfo.applicationInfo.loadLabel(packageManager);
                    boolean z = bundle.getBoolean("jump", false);
                    try
					{
//						if (!j.contains(packageInfo.applicationInfo.sourceDir))
//						{
//							AssetManager.class.getDeclaredMethod("addAssetPath", String.class).invoke(context.getAssets(), packageInfo.applicationInfo.sourceDir);
//							j.add(packageInfo.applicationInfo.sourceDir);
//						}
						IPlugin a = loadPluginClass(context, packageInfo.applicationInfo.nativeLibraryDir, packageInfo.applicationInfo.sourceDir, string);
                        if (a != null)
						{
                            new com.setqq.script.java.JavaPlugin(context, packageInfo.packageName, author, version, info, name, z, a,packageInfo.applicationInfo.sourceDir);
                        }

                    }
					catch (Exception e)
					{
                        StringWriter g = new StringWriter();
                        PrintWriter y = new PrintWriter(g);
                        e.printStackTrace(y);
                        com.saki.loger.ViewLoger.erro("插件" + name + "加载失败!" + g.toString());
                    }
                }
            }
        }
		
		
		if(SQApplication.getServer()!=null){
		  SQApplication.getServer().send(0x0001);
		}
		
		
    }





    private static IPlugin loadPluginClass(Context context, String nativeDir, String sourceDir, String string)
    {
        try
        {
			PathClassLoader u  = i.get(sourceDir);
			if (u == null)
			{
				u = new PathClassLoader(sourceDir, nativeDir, context.getClassLoader());
				i.put(sourceDir, u);
			}
            return (IPlugin) u.loadClass(string).newInstance();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return null;
        }

    }




//    public static void getScrpitsFromMarket()
//	{
//        new Thread(new Runnable() {
//				public void run()
//				{
//					try
//					{
//
//						JSONArray jSONArray = new JSONArray(new String(FileLoader.load(Client.MAINSEEVERURL + "?cmd=pluginshop")));
//						for (int i = 0; i < jSONArray.length(); i++)
//						{
//							new UndownloadedScriptPlugin(PluginUtil.newservice, jSONArray.getJSONObject(i));
//						}
//						com.saki.qq.userinfo.PluginCenter.getInstence().updateUi();
//					}
//					catch (JSONException e)
//					{
//						e.printStackTrace();
//					}
//				}
//			}).start();
//    }

    public static void setService(NewService newService)
	{
        newservice = newService;
    }

    private static void filtfileAndPlug(File file)
	{
        File[] listFiles = file.listFiles(new FilenameFilter() {
				public boolean accept(File file, String str)
				{
					return str.endsWith(".ecl") || str.endsWith(".ecj");
				}
			});
        if (listFiles != null)
		{
            for (File b : listFiles)
			{
                plug(b);
            }
        }
    }



    public static void plugPluginByFileName(String str, String str2)
	{
        File[] listFiles = new File(str).listFiles();
        if (listFiles != null)
		{
            for (File file : listFiles)
			{
                if (file.isDirectory())
				{
                    if (str2 == null)
					{
                        filtfileAndPlug(file);
                    }
					else if (str2.equals(file.getName()))
					{
                        filtfileAndPlug(file);
                        return;
                    }
					else
					{
                        return;
                    }
                }
            }
        }
    }



    private static void plug(final File file)
	{
        new Thread(new Runnable() {
				public void run()
				{
					try
					{
						if (file.getName().endsWith(".ecl"))
						{
							new LuaScriptPlugin(PluginUtil.newservice, file.getAbsolutePath());
						}
						else if (file.getName().endsWith(".ecj"))
						{
							new com.setqq.script.js.JsScriptPlugin(PluginUtil.newservice, file.getAbsolutePath());
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
						ViewLoger.erro((Object) "脚本加载异常:" + e.getMessage());
					}
				}
			}).start();
    }






}
