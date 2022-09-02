package com.setqq.script;

import dalvik.system.PathClassLoader;

public class NewClassLoader extends PathClassLoader
{

	private ClassLoader parent;

	private ClassLoader host;
	public NewClassLoader(String dexPath, String librarySearchPath, ClassLoader parent,ClassLoader host)
	{
        super(dexPath, librarySearchPath, parent);
		this.parent=parent;
		this.host=host;
    }

	@Override
    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException
	{
        // 插件自己的Class。从自己开始一直到BootClassLoader，采用正常的双亲委派模型流程，读到了就直接返回
        Class<?> pc = null;
        ClassNotFoundException cnfException = null;
        try
		{
            pc = super.loadClass(className, resolve);
            if (pc != null)
			{
                // 只有开启“详细日志”才会输出，防止“刷屏”现象
                return pc;
            }
        }
		catch (ClassNotFoundException e)
		{
			System.out.println("class "+className+" not found in plugin");
			//e.printStackTrace();
            // Do not throw "e" now
            cnfException = e;
			try
			{
				//当插件里无法找到类再从主程序里找
				return host.loadClass(className);
			}
			catch (ClassNotFoundException e1)
			{
				System.out.println("class "+className+" not found in host");

				// Do not throw "e1" now
				cnfException = e1;
			}

        }

        // 若插件里没有此类，则会从宿主ClassLoader中找，找到了则直接返回
        // 注意：需要读取isUseHostClassIfNotFound开关。默认为关闭的。可参见该开关的说明

        // At this point we can throw the previous exception
        if (cnfException != null)
		{
            throw cnfException;
        }
        return null;
    }

}




