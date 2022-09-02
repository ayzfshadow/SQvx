package com.saki.ui;

import android.app.*;
import android.content.*;
import android.os.*;
import android.support.design.widget.*;
import android.support.v4.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import com.ayzf.sqvx.*;
import com.mcsqnxa.common.*;
import com.saki.*;
import com.saki.client.*;
import com.saki.qq.userinfo.*;
import com.saki.service.*;
import com.saki.ui.LoginListener.*;
import com.saki.ui.c.*;
import com.saki.util.*;
import com.setqq.script.*;
import com.setqq.script.sdk.*;
import java.io.*;
import java.util.*;

public class PagerActivity extends AppCompatActivity implements  ServiceListenner,LocalServer.IReceive
{
    public com.saki.ui.GroupListView groupListView;
    public com.saki.ui.PluginListView pluginlistview;
    public com.saki.ui.LogerView logerview;
	/* access modifiers changed from: private */
    public final LinkedList<View> viewlist = new LinkedList<>();
    private SQServiceConnection sqserviceconnection;
    /* access modifiers changed from: private */
    public final String[] g = {"群列表", "插件列表", "运行日志"};
    private long lastKeyDown;

    private AnnounceView announceview;

    private ViewPager viewpager;

    private com.saki.ui.ToastHandler toast ;

    private NewService service;

    private SQNotifiCation notification;
    public static String key = "";
    class ViewPagerAdapter extends PagerAdapter
    {
        ViewPagerAdapter()
        {
        }

        public CharSequence getPageTitle(int i)
        {
            return PagerActivity.this.g[i];
        }

        public void destroyItem(ViewGroup viewGroup,int i,Object obj)
        {
            viewGroup.removeView(PagerActivity.this.viewlist.get(i));
        }

        public int getCount()
        {
            return PagerActivity.this.viewlist.size();
        }

        public Object instantiateItem(ViewGroup viewGroup,int i)
        {
            View view = PagerActivity.this.viewlist.get(i);
            viewGroup.addView(view);
            return view;
        }

        public boolean isViewFromObject(View view,Object obj)
        {
            return view == obj;
        }
    }

    /* access modifiers changed from: private */
    //加密脚本应该是
    public void encyptScript(String str)
    {
        if (str.endsWith(".sl") || str.endsWith(".sj"))
        {
            this.toast.toastInfo("正在打包脚本，请稍后...");
            ScriptUtil classf = new ScriptUtil(str);
            if (classf.c())
            {
                byte[] b2 = classf.b(classf.b(),classf.a() + "/");
                if (b2 == null || b2.length == 0)
                {
                    this.toast.toastInfo("脚本打包失败");
                    return;
                }
                File file = new File(classf.b().getParentFile() + "/" + classf.a() + ".zip");
                FileUtil.writeFile(file,b2);
                this.toast.toastInfo("脚本打包完成" + (importScript(file) ? "且导入完毕" : "但导入失败"));
                return;
            }
            this.toast.toastInfo("无法识别该脚本");
        }
		else if (str.endsWith(".zip"))
        {
            this.toast.toastInfo("正在导入脚本，请稍后...");
            this.toast.toastInfo("脚本导入:" + (importScript(new File(str)) ? "完毕" : "失败"));
        }
		else
        {
            this.toast.toastInfo("未能识别的脚本文件类型");
        }
    }

    //导入脚本
    private boolean importScript(File file)
    {
        try
        {
            ScriptUtil.a(file,FileUtil.c);
            return true;
        }
		catch (IOException e2)
        {
            e2.printStackTrace();
            return false;
        }
    }

    //传入服务，在这时候开启状态栏通知
    public void onCall(NewService newService)
    {
        this.service=newService;
        this.notification=new SQNotifiCation(this.service);
        this.notification.start();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i2,int i3,Intent intent)
    {
        if (i2 == 3026 && i3 == -1)
        {
            final String a2 = FileUtil.getFilePathByUri((Context) this,intent.getData());
            if (a2 == null)
            {
                Snackbar.make(viewpager,"返回文件为空，请选择其他文件选择器",1000).show();
                return;
            }
            new Thread(new Runnable() {
                    public void run()
					{
                        PagerActivity.this.encyptScript(a2);
					}
				}).start();
        }
        super.onActivityResult(i2,i3,intent);
    }



    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);

        this.groupListView=new GroupListView(this);
        MessageCenter._this().addListener((MessageListener) this.groupListView);
        this.pluginlistview=new PluginListView(this);
        this.logerview=new LogerView(this);
        this.viewlist.add(this.groupListView);
        this.viewlist.add(this.pluginlistview);
        this.viewlist.add(this.logerview);
        setContentView(R.layout.layout_activity_pager);
        Toolbar toolbar = findViewById(R.id.toolbar_pager);
        setSupportActionBar(toolbar);
		this.viewpager=((Activity)this).findViewById(R.id.pagerview_viewpager);
        viewpager.setAdapter(new ViewPagerAdapter());
		TabLayout tablayout = ((Activity)this).findViewById(R.id.pagerview_tablayout);
		tablayout.setupWithViewPager(viewpager);
		StatusBarUtil.setTransparent(this);
        Intent intent = new Intent(this,NewService.class);
        SQServiceConnection classb = new SQServiceConnection((ServiceListenner) this);
        this.sqserviceconnection=classb;
        bindService(intent,classb,1);
        this.toast=new com.saki.ui.ToastHandler(viewpager,this);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

		if (SQApplication.getServer() == null)
        {
			new Thread(new Runnable(){
                    @Override
                    public void run()
					{
                        try
						{
                            SQApplication.setServer(LocalServer.bind(29930));
                            SQApplication.getServer().startListener(PagerActivity.this);

                            System.out.println("服务端初始化ok");
						}
                        catch (Exception e)
						{
                            e.printStackTrace();
						}
					}
				}).start();
        }
    }



	@Override
	public void onReceive(LocalServer server,final Object data)
    {
		try
        {
			if (data instanceof Integer)
            {
				if ((int)data == 0x00)//插件与主程序握手
                {
					server.send(0x01);//获取插件信息
                }
				else if ((int)data == 0xFFFF)//心跳包,不需要处理
                {
                    server.send(0x0000);
					return;
                }
            }
			else if (data instanceof String[])//插件推送插件信息到主程序
            {
				new ScriptItem((String[])data);return;
            }
			else if (data instanceof Msg)
            {
				new PluginApi(null).send((Msg)data);
            }

			/*new Handler ( Looper.getMainLooper ( ) ).post ( new Runnable ( ){
			 @Override
			 public void run ( )
			 {
			 Toast.makeText ( PagerActivity.this, data.toString ( ), 0 ).show ( );
			 }
			 } );*/
        }
		catch (Exception e)
        {
			ShowToast.show(this,e.toString());
			e.printStackTrace();
        }
    }






    /* access modifiers changed from: protected */
    public void onDestroy()
    {
        this.groupListView.onDestroy();
        this.pluginlistview.onDestroy();
        this.logerview.closeLoger();
        unbindService(this.sqserviceconnection);
        this.notification.stop();
        super.onDestroy();
    }

    public boolean onKeyDown(int i2,KeyEvent keyEvent)
    {
        if (i2 != 4)
        {
            return super.onKeyDown(i2,keyEvent);
        }
        if (System.currentTimeMillis() - this.lastKeyDown > 2000)
        {
            Snackbar.make(viewpager,"再按一次，退出程序",1000).show();
            this.lastKeyDown=System.currentTimeMillis();
        }
		else
        {
            stopService(new Intent(this,NewService.class));
            finish();
        }
        return true;
    }

    @Override public boolean  onCreateOptionsMenu(Menu menu)
    {
        // 这条表示加载菜单文件，第一个参数表示通过那个资源文件来创建菜单 // 第二个表示将菜单传入那个对象中。这里我们用Menu传入menu // 这条语句一般系统帮我们创建好 
        getMenuInflater().inflate(R.menu.toolbar_menu,menu); 
        return true;
    }
	// 菜单的监听方法 @Override public boolean  onOptionsItemSelected(MenuItem item) { switch (item.getItemId()) { case R.id.fist: Toast.makeText(this, "点击了第一个菜单", 0).show(); break; case R.id.second: Toast.makeText(this, "点击了第二个菜单", 0).show(); break; case R.id.third: Toast.makeText(this, "点击了第三个菜单", 0).show(); break; default: break; } return true; }}
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id)
        {
            case R.id.menu_item_encryptscript:
            case R.id.menu_item_importscript:
				{
                    FileUtil.chooseFile(viewpager,(Activity) this,3026);
				}break;
            case R.id.menu_item_announce:
                {
                    if (this.announceview != null)
					{
                        this.announceview.dismiss();
                        this.announceview=null;
                    }
                    this.announceview=new AnnounceView(this,this.getWindow().getDecorView());
                }break;

            default:{

				}
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

}
