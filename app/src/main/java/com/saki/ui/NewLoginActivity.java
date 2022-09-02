package com.saki.ui;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.saki.qq.userinfo.Device;
import com.saki.qq.userinfo.Key;
import com.saki.qq.userinfo.Token;
import com.saki.qq.userinfo.User;
import com.saki.service.NewService;
import com.saki.service.SQServiceConnection;
import com.ayzf.sqvx.R;
import com.saki.tool.Code;
import com.saki.tool.HexTool;
import com.saki.tool.TeaCryptor;
import com.saki.ui.Adapter.NavigationItemSelectedListener;
import com.saki.ui.LoginListener.LoginResultListener;
import com.saki.ui.mask.PasswordMask;
import com.saki.ui.view.UpdateView;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Random;
import com.saki.client.Client;

public class NewLoginActivity extends AppCompatActivity implements OnClickListener, LoginResultListener
{
    /* access modifiers changed from: private */
    public LinearLayout loginInput;
    /* access modifiers changed from: private */
    public android.support.design.widget.TextInputLayout linearlayout_login_account;
    /* access modifiers changed from: private */
    public android.support.design.widget.TextInputLayout linearlayout_login_password;
    /* access modifiers changed from: private */
    public LinearLayout linearlayout_login_roundimage;
    /* access modifiers changed from: private */
    public Button button_login;
	public Button button_relogin;
    private EditText edittext_account_input;
    private EditText edittext_password_input;
    /* access modifiers changed from: private */
    public SQServiceConnection sqerviceconnection;
    /* access modifiers changed from: private */
    public CircleImageView login_user_header;
    private UpdateView login_update_view;
    
    //登陆结果回调
    private Handler loginhandler = new Handler() {
       @Override public void handleMessage(Message message)
        {
			System.out.println(message.what+"");
            if (message.what == 1)
            {
                NewLoginActivity.this.resetInputStatus();
                String obj = message.obj.toString();
                if (obj.replace(" ", "").equals(""))
                {
                    obj = "登录失败:请关闭QQ设备锁";
                }
                Snackbar.make(button_login, obj, 1000).show();
            }
            else if (message.what == 3)
            {
                NewLoginActivity.this.login_user_header.setImageBitmap((Bitmap) message.obj);
            }
            else if (message.what == 2)//验证码
            {
			   byte[] bArr = (byte[]) message.obj;
			   new VerifyDialog(NewLoginActivity.this, new String(bArr), new com.saki.ui.LoginListener.VerifyCodeRecall() {
					   public void onRecall(String str)
					   {
						   if (str == null || str.equals(""))
						   {
							   NewLoginActivity.this.resetInputStatus();
						   }
						   else
						   {
							   NewLoginActivity.this.sqerviceconnection.getService().Verify(str);
						   }
					   }
				   });
            }
		   else if (message.what == 11)//设备锁
		   {
			   final byte[] bArr = (byte[]) message.obj;
			   final AlertDialog.Builder normalDialog = 
				   new AlertDialog.Builder(NewLoginActivity.this);
			   normalDialog.setTitle("需要验证登陆保护");
			   normalDialog.setMessage(com.saki.qq.userinfo.Token.lockinfo);
			   normalDialog.setPositiveButton("确定", 
				   new DialogInterface.OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which) {
						   NewLoginActivity.this.sqerviceconnection.getService().sendLock(bArr);
					   }
				   });
			   normalDialog.setNegativeButton("关闭", 
				   new DialogInterface.OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which) {
						   NewLoginActivity.this.resetInputStatus();
					   }
				   });
			   // 显示
			   normalDialog.show();
		   }
		   else if (message.what == 12)
		   {
			   final EditText edit = new EditText(NewLoginActivity.this);
			   final AlertDialog.Builder normalDialog = 
				   new AlertDialog.Builder(NewLoginActivity.this);
			   normalDialog.setView(edit);
			   normalDialog.setTitle("需要验证登录保护");

			   normalDialog.setMessage("输入密保手机收到的短信验证码");


			   normalDialog.setPositiveButton("确定", 
				   new DialogInterface.OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which)
					   {
						   if (edit.getText().toString().isEmpty())
						   {
							   //NewLoginActivity.this.resetInputStatus();
						   }
						   else
						   {
							   sqerviceconnection.getService().sendLockVerify(edit.getText().toString());
						   }
					   }
				   });
			   normalDialog.setNegativeButton("关闭", 
				   new DialogInterface.OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which)
					   {
						   //NewLoginActivity.this.resetInputStatus();
					   }
				   });
			   // 显示
			   normalDialog.show();
		   }else if (message.what == 13)//设备锁验证失败
		   {

			   final EditText edit = new EditText(NewLoginActivity.this);
			   final AlertDialog.Builder normalDialog = 
				   new AlertDialog.Builder(NewLoginActivity.this);
			   normalDialog.setView(edit);
			   normalDialog.setTitle("验证码错误");

			   normalDialog.setMessage("重新输入密保手机收到的短信验证码");


			   normalDialog.setPositiveButton("确定", 
				   new DialogInterface.OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which)
					   {
						   if (edit.getText().toString().isEmpty())
						   {
							   //NewLoginActivity.this.resetInputStatus();
						   }
						   else
						   {
							   sqerviceconnection.getService().sendLockVerify(edit.getText().toString());
						   }
					   }
				   });
			   normalDialog.setNegativeButton("关闭", 
				   new DialogInterface.OnClickListener() {
					   @Override
					   public void onClick(DialogInterface dialog, int which)
					   {
						   //NewLoginActivity.this.resetInputStatus();
					   }
				   });
			   // 显示
			   normalDialog.show();
		   }
            else if (message.what == 0)
            {
                Snackbar.make(button_login, "请稍等，正在获取必要key", 1000).show();
               new Thread
               (new Runnable()//创建子线程对象
                   {
                       @Override//超类
                       public void run()//必要方法
                       {
                           try
                           {
                               PagerActivity.key = com.ayzf_shadow.tool.Tea.decrypt(com.saki.ui.SQAuthActivity.teaKey,new String(new com.saki.http.HTTP().httpGet(Client.DOMAIN + "Server2.php",true,3000,3000,new String[0])));
                           }
                           catch (Exception e)
                           {}
                       }
                   }
               ).start();
                NewLoginActivity.this.startActivity(new Intent(NewLoginActivity.this, PagerActivity.class));
                NewLoginActivity.this.finish();
            }
            else
            {
                Snackbar.make(button_login, message.obj.toString(), 1000).show();
            }
        }
    };
    private ObjectAnimator objectAnimator;

    //最后点击返回时间
    private long lastkeydown;

    //公告
    private AnnounceView announceView;

	private DrawerLayout drawer;

    public class a extends LinearInterpolator
    {
        private float b = 0.15f;

        public a()
        {
        }

        public float getInterpolation(float f)
        {
            return (float) ((Math.pow(2.0d, (double) (-10.0f * f)) * Math.sin((((double) (f - (this.b / 4.0f))) * 6.283185307179586d) / ((double) this.b))) + 1.0d);
        }
    }

    //动画效果之类的
    private class InputAnimatorListener implements AnimatorListener
    {
        private boolean b;

        private InputAnimatorListener()
        {
            this.b = false;
        }

        @SuppressLint("WrongConstant")
        public void onAnimationCancel(Animator animator)
        {
            this.b = true;
            NewLoginActivity.this.linearlayout_login_account.setVisibility(0);
            NewLoginActivity.this.linearlayout_login_password.setVisibility(0);
            NewLoginActivity.this.button_login.setEnabled(true);
            NewLoginActivity.this.button_login.setText("登录");
            NewLoginActivity.this.loginInput.setScaleX(1.0f);
        }

        @SuppressLint("WrongConstant")
        public void onAnimationEnd(Animator animator)
        {
            if (!this.b)
            {
                NewLoginActivity.this.linearlayout_login_roundimage.setVisibility(0);
                NewLoginActivity.this.loginInput.setVisibility(4);
                NewLoginActivity.this.a();
            }
        }

        public void onAnimationRepeat(Animator animator)
        {
        }

        @SuppressLint("WrongConstant")
        public void onAnimationStart(Animator animator)
        {
            NewLoginActivity.this.button_login.setEnabled(false);
            NewLoginActivity.this.button_login.setText("登录中...");
			NewLoginActivity.this.button_relogin.setEnabled(false);
            NewLoginActivity.this.button_relogin.setText("登录中...");
            NewLoginActivity.this.linearlayout_login_account.setVisibility(4);
            NewLoginActivity.this.linearlayout_login_password.setVisibility(4);
        }
    }

    /* access modifiers changed from: private */
	//不知道这个什么鬼
    public void a()
    {
        PropertyValuesHolder ofFloat = PropertyValuesHolder.ofFloat("scaleX", new float[]{0.5f, 1.0f});
        PropertyValuesHolder ofFloat2 = PropertyValuesHolder.ofFloat("scaleY", new float[]{0.5f, 1.0f});
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.linearlayout_login_roundimage, new PropertyValuesHolder[]{ofFloat, ofFloat2});
        ofPropertyValuesHolder.setDuration(1000);
        ofPropertyValuesHolder.setInterpolator(new a());
        ofPropertyValuesHolder.start();
    }

	//开始登陆
    private void startLogin(final String str)
    {
        new Thread() {
            public void run()
            {
                com.saki.qq.userinfo.User.uin = Long.parseLong(str);
                NewLoginActivity.this.onCall(3, (Object) com.saki.util.ImageUtil.getCachedFace(str, false));
            }
        }.start();
    }

	//记录账号密码
    private void recordAccoundAndPassword(String str, String str2)
    {
        Editor edit = getSharedPreferences("login", 0).edit();
        edit.putString("account", str);
        edit.putString("passWord", str2);
        edit.commit();
    }

	//检查服务器连接并开启服务
    @SuppressLint("WrongConstant")
    private void startService()
    {
        boolean z;
        @SuppressLint("WrongConstant") NetworkInfo[] allNetworkInfo = ((ConnectivityManager) getApplicationContext().getSystemService("connectivity")).getAllNetworkInfo();
        int i2 = 0;
        while (true)
        {
            if (i2 >= allNetworkInfo.length)
            {
                z = false;
                break;
            }
            else if (allNetworkInfo[i2].getState() == State.CONNECTED)
            {
                z = true;
                break;
            }
            else
            {
                i2++;
            }
        }
        if (!z)
        {
            Snackbar.make(this.button_login, "无网络", 1000).show();
            startService(new Intent(this, NewService.class));
            Intent intent = new Intent(this, NewService.class);
            SQServiceConnection classb = new SQServiceConnection((LoginResultListener) this);
            this.sqerviceconnection = classb;
            bindService(intent, classb, 1);
        }
        else
        {
            this.login_update_view.a("http://www.ayzfshadow.com/SQvx/UpdateVx.php");
            startService(new Intent(this, NewService.class));
            Intent intent = new Intent(this, NewService.class);
            SQServiceConnection classb = new SQServiceConnection((LoginResultListener) this);
            this.sqerviceconnection = classb;
            bindService(intent, classb, 1);
        }
        initLoginInput();
    }
	
	
	

//    private long getCRC(Context c) {
//        try {
//            Class zip =Class.forName(new String(new byte[]{106,97,118,97,46,117,116,105,108,46,122,105,112,46,90,105,112,70,105,108,101}));
//            Constructor<?> construct = zip.getConstructor(Class.forName(new String(new byte[]{106,97,118,97,46,108,97,110,103,46,83,116,114,105,110,103})));
//
//            Class Context = Class.forName(new String(new byte[]{97,110,100,114,111,105,100,46,99,111,110,116,101,110,116,46,67,111,110,116,101,120,116}));
//            Method getPackageCodePath= Context.getDeclaredMethod(new String(new byte[]{103,101,116,80,97,99,107,97,103,101,67,111,100,101,80,97,116,104}));
//            String path = (String)getPackageCodePath.invoke(c);
//            Object zipobj= construct.newInstance(path);
//
//            Method getEntry = zip.getDeclaredMethod(new String(new byte[]{103,101,116,69,110,116,114,121}),Class.forName(new String(new byte[]{106,97,118,97,46,108,97,110,103,46,83,116,114,105,110,103})));
//            Object zipentryobj = getEntry.invoke(zipobj,new String(new byte[]{99,108,97,115,115,101,115,46,100,101,120}));
//
//            Class ZipEntry = Class.forName(new String(new byte[]{106,97,118,97,46,117,116,105,108,46,122,105,112,46,90,105,112,69,110,116,114,121}));
//            Method getCrc = ZipEntry.getDeclaredMethod(new String(new byte[]{103,101,116,67,114,99}));
//            long crc = (long)getCrc.invoke(zipentryobj);
//
//            
////            return crc;
//            
//            return crc;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return 0;
//        }
//    }
    private String getCRC1(Context c)
	{
        try
		{
            Class zip =Class.forName("java.util.zip.ZipFile");
            Constructor<?> construct = zip.getConstructor(Class.forName("java.lang.String"));

            Class Context = Class.forName("android.content.Context");
            Method getPackageCodePath= Context.getDeclaredMethod("getPackageCodePath");
            String path = (String)getPackageCodePath.invoke(c);
            Object zipobj= construct.newInstance(path);

            Method getEntry = zip.getDeclaredMethod("getEntry", Class.forName("java.lang.String"));
            Object zipentryobj = getEntry.invoke(zipobj, "classes.dex");

            Class ZipEntry = Class.forName("java.util.zip.ZipEntry");
            Method getCrc = ZipEntry.getDeclaredMethod("getCrc");
            long crc = (long)getCrc.invoke(zipentryobj);

            return crc+"";

        }
		catch (Exception e)
		{
            e.printStackTrace();
            return "";
        }
    }

	//读取账号
    private String readAcconutRecord()
    {
        return getSharedPreferences("login", 0).getString("account", "");
    }

	//读取密码
    private String readPasswordRecord()
    {
        return getSharedPreferences("login", 0).getString("passWord", "");
    }

	//初始化输入元素
    private void initLoginInput()
    {
        this.edittext_account_input.setText(readAcconutRecord());
        this.edittext_password_input.setText(readPasswordRecord());
        this.edittext_password_input.setTransformationMethod(new PasswordMask());
    }

    /* access modifiers changed from: private */
	//登录失败重置界面输入元素
    @SuppressLint("WrongConstant")
    public void resetInputStatus()
    {
        this.linearlayout_login_roundimage.setVisibility(8);
        this.loginInput.setVisibility(0);
        if (this.objectAnimator == null || !this.objectAnimator.isRunning())
        {
            this.linearlayout_login_account.setVisibility(0);
            this.linearlayout_login_password.setVisibility(0);
            this.button_login.setEnabled(true);
			this.button_relogin.setEnabled(true);
            this.button_login.setText("登录");
			this.button_relogin.setText("Relogin");
            this.loginInput.setScaleX(1.0f);
            return;
        }
        this.objectAnimator.cancel();
    }

	//应该是监听登录结果
    public void onCall(int i2, Object obj)
    {
        Message obtain = Message.obtain();
        obtain.what = i2;
        obtain.obj = obj;
        this.loginhandler.sendMessage(obtain);
    }

    public void onClick(View view)
    {
        boolean z;
        @SuppressLint("WrongConstant") NetworkInfo[] allNetworkInfo = ((ConnectivityManager) getApplicationContext().getSystemService("connectivity")).getAllNetworkInfo();
        int i2 = 0;
        while (true)
        {
            if (i2 >= allNetworkInfo.length)
            {
                z = false;
                break;
            }
            else if (allNetworkInfo[i2].getState() == State.CONNECTED)
            {
                z = true;
                break;
            }
            else
            {
                i2++;
            }
        }
        if (!z)
        {
            Snackbar.make(this.button_login,"无网络",1000).show();
            return;
        }
        if (com.ayzf_shadow.tool.jiance.vpn.֏֏֏֏֏֏() == false || com.ayzf_shadow.tool.jiance.xposed.֏֏֏֏֏֏() == false)
        {
            Snackbar.make(this.button_login,"网络环境异常",1000).show();
            return;
        }
        switch(view.getId()){
			case R.id.button_login:{
					String obj = this.edittext_account_input.getText().toString();
					String obj2 = this.edittext_password_input.getText().toString();
					if (obj == null || obj2 == null || obj.equals("") || obj2.equals(""))
					{
						Snackbar.make(this.button_login, "请输入账号密码", 1000).show();
						return;
					}
					recordAccoundAndPassword(obj, obj2);
					startLogin(obj);
					this.sqerviceconnection.getService().a(obj, obj2, this.login_update_view.getCode());
					this.objectAnimator = ObjectAnimator.ofFloat(this.loginInput, "scaleX", new float[]{1.0f, 0.5f});
					this.objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
					this.objectAnimator.setDuration(500);
					this.objectAnimator.addListener(new InputAnimatorListener());
					this.objectAnimator.start();
			}break;
			case R.id.button_super_login:{
					SharedPreferences sp=getSharedPreferences("login", 0);
					String _4c=sp.getString("token004c", null);
					if (_4c == null)
{
						Snackbar.make(this.button_login, "请登陆成功后再试。", 1900).show();
						return;
					}
					Long t = getSharedPreferences("login", 0).getLong("generatet",0);
					if(new Date().getTime()>t+(1000*60*60*24*10)){
						Snackbar.make(this.button_login, "已过期，请重新登陆。", 1900).show();
						return;
					}
					String obj = this.edittext_account_input.getText().toString();
					//String obj2 = this.edittext_password_input.getText().toString();
					startLogin(obj);
					
					Token._2c=new TeaCryptor().decrypt(HexTool.hextobytes(sp.getString("token002c","")),Code.md5((this.getCRC1(this)+"").getBytes()));
					Token._4c=new TeaCryptor().decrypt(HexTool.hextobytes(sp.getString("token004c","")),Code.md5((this.getCRC1(this)+"").getBytes()));
					//Key.rigister=new TeaCryptor().decrypt(HexTool.hextobytes(sp.getString("rigister","")),Code.md5((this.getCRC1(this)+"").getBytes()));
					
					Key.c=HexTool.hextobytes(sp.getString("sessionkey",""));
					com.saki.qq.userinfo.Key.a(com.saki.qq.userinfo.Key.c);
					this.sqerviceconnection.getService().relogin();
					
					//Key.c=Key.c;
					this.objectAnimator = ObjectAnimator.ofFloat(this.loginInput, "scaleX", new float[]{1.0f, 0.5f});
					this.objectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
					this.objectAnimator.setDuration(500);
					this.objectAnimator.addListener(new InputAnimatorListener());
					this.objectAnimator.start();
					
			}
		}
    }

    /* access modifiers changed from: protected */
    @SuppressLint("WrongConstant")
    public void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.layout_activity_login);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login);
        this.setSupportActionBar(toolbar);
		StatusBarUtil.setTransparent(this);
        this.loginInput = (LinearLayout) findViewById(R.id.linearlayout_login_input);
        this.linearlayout_login_account = (android.support.design.widget.TextInputLayout) (LinearLayout) findViewById(R.id.linearlayout_login_account);
        this.linearlayout_login_password = (android.support.design.widget.TextInputLayout) (LinearLayout) findViewById(R.id.linearlayout_login_password);
        this.linearlayout_login_roundimage = (LinearLayout) findViewById(R.id.linearlayout_login_roundimage);
        this.button_login = (Button) findViewById(R.id.button_login);
		this.button_relogin=findViewById(R.id.button_super_login);
        this.edittext_account_input = (EditText) findViewById(R.id.edittext_account_input);
        this.edittext_password_input = (EditText) findViewById(R.id.edittext_password_input);
        this.login_user_header = (CircleImageView) findViewById(R.id.login_user_header);
        this.login_update_view = (UpdateView) findViewById(R.id.login_update_view);
        this.button_login.setOnClickListener(this);
		this.button_relogin.setOnClickListener(this);
		drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //在布局文件中生命DrawerLayout后，即可从边缘滑出抽屉了

        //ActionBarDrawerToggle作用是在toolbar上创建一个点击弹出drawer的按钮而已
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
			this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        //不写这句话，是没有按钮显示的
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationItemSelectedListener(this));
        if (VERSION.SDK_INT >= 23)
        {
            String[] strArr = {"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_PHONE_STATE"};
            for (String checkSelfPermission : strArr)
            {
                if (checkSelfPermission(checkSelfPermission) != 0)
                {
                    requestPermissions(strArr, 302);
                }
            }
        }
		
		User.imei=getSharedPreferences("device", 0).getString("imei", "");
		if(User.imei==null||User.imei.isEmpty()){
			getSharedPreferences("device", 0).edit().putString("imei", randomimei()).commit();
		}
        User.devicecompany=getSharedPreferences("device", 0).getString("manufacture", "");
		if(User.devicecompany==null||User.devicecompany.isEmpty()){
			getSharedPreferences("device", 0).edit().putString("manufacture", "8848").commit();
		}
        User.devicemode=getSharedPreferences("device", 0).getString("mode", "");
		if(User.devicemode==null||User.devicemode.isEmpty()){
			getSharedPreferences("device", 0).edit().putString("mode", "8848 Μ5").commit();
		}
		User.imei=getSharedPreferences("device", 0).getString("imei", "");
        User.devicecompany=getSharedPreferences("device", 0).getString("manufacture", "");
        User.devicemode=getSharedPreferences("device", 0).getString("mode", "");
		
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        
        User.enablesilkencode=sp.getBoolean("switch_silkencode", false);
        User.switch_enableallgroupautomatically=sp.getBoolean("switch_enableallgroupautomatically", false);
        User.newthread=sp.getBoolean("switch_newthread", false);
        User.switch_enableprivatereply=sp.getBoolean("switch_enableprivatereply", false);
		User.enablediscuss=sp.getBoolean("switch_enablediscussreply", false);
       // Client.PROTOCOLSEVERURL=sp.getString("authserver", "http://"+HTTP.host+"/SQv8/ProtocolServer");
        
        Device.display = sp.getString("edittext_display",  "DAMIMI.2018.003 test-key");
		Device.product = sp.getString("edittext_product",  "8848Μ6");
		Device.device = sp.getString("edittext_device", "8848Μ6");
		Device.board = sp.getString("edittext_board",  "ocean");
		Device.brand = sp.getString("edittext_brand",  "8848");
		Device.model = sp.getString("edittext_model",  "8848 Μ6");
		Device.bootloader = sp.getString("edittext_bootloader",  "unknown");
		Device.fingerprint = sp.getString("edittext_fingerprint", "8848/Μ6/Μ6/DAMIMI.2018.003/547250:Device/release-keys");
		Device.bootId = sp.getString("edittext_bootId", "20fe4dfe-54e1-8a1e-a445-061ea2ef662a");
		Device.procVersion = sp.getString("edittext_procVersion",  "Linux version 4.14.117-UnrealEngine-fe40de4a122e0 (mahuateng@ubuntu) (Android (6031699 based on r253063e4) clang version 9.0.3 (https://android.googlesource.com/toolchain/clang 745b335211bb9eadfa6aa6301f84715cee4b37c5) (https://android.googlesource.com/toolchain/llvm 31c3f8c4ae6cc980405a3b90e7e88db00249eba5) (based on LLVM 9.0.3svn)) #1 Thu Dec 02:09:57 CST 2018");
		Device.baseBand = sp.getString("edittext_baseBand", "MPSS.HE.1.0.c11.1-00007-SM8150_GEN_PACK-2.233059.1.235876.1");
		Device.simInfo = sp.getString("edittext_simInfo", "cmcc");
		Device.osType = sp.getString("edittext_osType", "android");
		Device.macAddress = sp.getString("edittext_macAddress", "02:00:00:00:00:00");
		Device.wifiBSSID = sp.getString("edittext_wifiBSSID", "02:00:00:00:00:00");
		Device.wifiSSID = sp.getString("edittext_wifiSSID", "<unknown ssid>");
		Device.imsiMd5 = HexTool.hextobytes(sp.getString("edittext_imsiMd5", "2ef1a7b23265dacb272ef1a7b23265da"));
		Device.imei = sp.getString("edittext_imei", "868209161547921");
		Device.apn = sp.getString("edittext_apn", "wifi");
		Device.androidId = sp.getString("edittext_androidId",  "DAMIMI.2018.003");
		Device.codename = sp.getString("edittext_codename",  "Raphaere");
		Device.incremental = sp.getString("edittext_incremental",  "super.angelways.20190426.145685");
		Device.innerVersion = sp.getString("edittext_innerVersion",  "DaJiJi");
		Device.osVersion = sp.getString("edittext_osVersion",  "10.0.1");
        startService();
		
    }

	private String randomimei()
	{
	String str="0123456789";
		Random random=new Random();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<14;i++){
			int number=random.nextInt(10);
			sb.append(str.charAt(number));
		}
		return "8"+sb.toString();
	}
	

    /* access modifiers changed from: protected */
    public void onDestroy()
    {
        if (this.sqerviceconnection != null)
        {
            unbindService(this.sqerviceconnection);
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int i2, KeyEvent keyEvent)
    {
        if (i2 != 4)
        {
            return super.onKeyDown(i2, keyEvent);
        }
        if (System.currentTimeMillis() - this.lastkeydown > 2000)
        {
            Snackbar.make(this.button_login, "再按一次，退出程序",1000).show();
  
            this.lastkeydown = System.currentTimeMillis();
        }
        else
        {
            stopService(new Intent(this, NewService.class));
            finish();
        }
        return true;
    }

    public void onRequestPermissionsResult(int i2, String[] strArr, int[] iArr)
    {
        switch (i2)
        {
            case 302:
                if (iArr.length > 0 && iArr[0] == -1)
                {
                    if (this.sqerviceconnection != null)
                    {
                        stopService(new Intent(this, NewService.class));
                    }
                    finish();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onWindowFocusChanged(boolean z)
    {
        if (z && this.announceView == null)
        {
            this.announceView = new AnnounceView(this, getWindow().getDecorView());
        }
        super.onWindowFocusChanged(z);
    }
}
