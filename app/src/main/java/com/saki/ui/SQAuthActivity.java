package com.saki.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.ayzf.sqvx.R;
import com.ayzf_shadow.tool.Tea;
import com.saki.http.HTTP;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import org.json.JSONObject;
import android.app.AlertDialog;

public class SQAuthActivity extends AppCompatActivity implements View.OnClickListener
{

    public static final String server2 = "http://www.ayzfshadow.com/SQvx/authorize.php?";
    public static final String teaKey = "04215866666652000013145229932901";
    EditText lookauthqq;
    EditText lookkeykey;
    EditText usekeyqq;
    EditText usekeykey;
    EditText transferoriginqq;
    EditText transfertargetqq;
    EditText transferorigkey;
    Button lookqq;
    Button lookkey;
    Button usekey;
    Button transfer;

    private Handler handler = new Handler() {
        @Override public void handleMessage(Message message)
        {
            if (message.what == 0)
                Snackbar.make(lookauthqq,(String)message.obj,3000).show();
            else if (message.what == 1)
                generalDialog(SQAuthActivity.this,"查询授权",(String)message.obj);
        }
    };

    public void onCreate(Bundle bundle)
    {
        super. onCreate(bundle);

        this. setContentView(R.layout.layout_activity_sqauthactivity);

        this. lookauthqq=this.findViewById(R.id.layoutactivitysqauthactivityEditTextLookAuth);
        this. lookkeykey=this.findViewById(R.id.layoutactivitysqauthactivityEditTextLookKey);
        this. usekeyqq=this.findViewById(R.id.layoutactivitysqauthactivityEditTextUseKeyQQ);
        this. usekeykey=this.findViewById(R.id.layoutactivitysqauthactivityEditTextUseKeyKey);
        this. transferoriginqq=this.findViewById(R.id.layoutactivitysqauthactivityEditTextTransferOriginQQ);
        this. transfertargetqq=this.findViewById(R.id.layoutactivitysqauthactivityEditTextTransferTargetQQ);
        this. transferorigkey=this.findViewById(R.id.layoutactivitysqauthactivityEditTextTransferOtiginKey);
        this. lookqq=this.findViewById(R.id.layoutactivitysqauthactivityAppCompatButtonLookAuth);
        this. lookkey=this.findViewById(R.id.layoutactivitysqauthactivityAppCompatButtonLookKey);
        this. usekey=this.findViewById(R.id.layoutactivitysqauthactivityAppCompatButtonUseKey);
        this. transfer=this.findViewById(R.id.layoutactivitysqauthactivityAppCompatButtonTransfer);
        this.lookqq.setOnClickListener(this);
        this.lookkey.setOnClickListener(this);
        this.usekey.setOnClickListener(this);
        this.transfer.setOnClickListener(this);
    }
    @Override
    public void onClick(View p1)
    {
        switch (p1.getId())
        {
            case R.id.layoutactivitysqauthactivityAppCompatButtonLookAuth:{
                    if (lookauthqq.getText().toString().isEmpty())
                    {
                        Snackbar.make(lookauthqq,"qq不可以为空",3000).show();
                        return;
                    }
                    //request("http://106.12.4.207:9999/SQv8/AuthServer?cmd=queryauth&qq=" + lookauthqq.getText().toString());
                    request(server2 + "id=" + Tea.encrypt(teaKey,appMd5(SQAuthActivity.this)) + "&type=1&qq=" + Tea.encrypt(teaKey,lookauthqq.getText().toString()),R.id.layoutactivitysqauthactivityAppCompatButtonLookAuth);

                }break;
            case R.id.layoutactivitysqauthactivityAppCompatButtonLookKey:{
                    if (lookkeykey.getText().toString().isEmpty())
                    {
                        Snackbar.make(lookauthqq,"key不可以为空",3000).show();
                        return;
                    }
                    //request("http://106.12.4.207:9999/SQv8/AuthServer?cmd=querykey&key=" + lookkeykey.getText().toString());
                    request(server2 + "id=" + Tea.encrypt(teaKey,appMd5(SQAuthActivity.this)) + "&type=2&key=" + Tea.encrypt(teaKey,lookkeykey.getText().toString()),R.id.layoutactivitysqauthactivityAppCompatButtonLookKey);

                }break;
            case R.id.layoutactivitysqauthactivityAppCompatButtonUseKey:{
//                    if (usekeykey.getText().toString().isEmpty())
//                    {
//                        Snackbar.make(lookauthqq, "key不可以为空", 3000).show();
//                        return;
//                    }
//                    if (usekeyqq.getText().toString().isEmpty())
//                    {
//                        Snackbar.make(lookauthqq, "qq不可以为空", 3000).show();
//                        return;
//                    }
//                    request("http://106.12.4.207:9999/SQv8/AuthServer?cmd=usekey&key="+usekeykey.getText().toString()+"&qq="+usekeyqq.getText().toString());
                    Snackbar.make(lookauthqq,"请使用\"影殇机器人\"程序执行此操作",3000).show();
                }break;
            case R.id.layoutactivitysqauthactivityAppCompatButtonTransfer:{
//                    if (transferorigkey.getText().toString().isEmpty())
//                    {
//                        Snackbar.make(lookauthqq, "key不可以为空", 3000).show();
//                        return;
//                    }
//                    if (transferoriginqq.getText().toString().isEmpty())
//                    {
//                        Snackbar.make(lookauthqq, "源qq不可以为空", 3000).show();
//                        return;
//                    }
//                    if (transfertargetqq.getText().toString().isEmpty())
//                    {
//                        Snackbar.make(lookauthqq, "新qq不可以为空", 3000).show();
//                        return;
//                    }
//                    request("http://106.12.4.207:9999/SQv8/AuthServer?cmd=transferauth&key="+transferorigkey.getText().toString()+"&originqq="+transferoriginqq.getText().toString()+"&targetqq="+transfertargetqq.getText().toString());
                    Snackbar.make(lookauthqq,"请使用\"影殇机器人\"程序执行此操作",3000).show();
                }break;


        }
    }

    public static final String appMd5(Context a)
    {
        return getFileMd5(a.getPackageResourcePath());
    }
    public static String getFileMd5(String path)
    {
        try
        {
            // 获取一个文件的特征信息，签名信息。
            File file = new File(path);
            // md5
            MessageDigest digest = MessageDigest.getInstance("md5");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = fis.read(buffer)) != -1)
            {
                digest.update(buffer,0,len);
            }
            byte[] result = digest.digest();
            StringBuffer sb  = new StringBuffer();
            for (byte b : result)
            {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                // System.out.println(str);
                if (str.length() == 1)
                {
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }

    private void request(final String url,final int id)
    {
        new Thread() {
            public void run()
            {
                try
                {
                    String result;
                    if (com.ayzf_shadow.tool.jiance.vpn.֏֏֏֏֏֏() == false || com.ayzf_shadow.tool.jiance.xposed.֏֏֏֏֏֏() == false)
                    {
                        result="网络环境异常";
                        Message h = new Message();
                        h.what=0;
                        h.obj=result;
                        SQAuthActivity.this.handler.sendMessage(h);
                    }
                    else
                    {
                        switch (id)
                        {
                            case R.id.layoutactivitysqauthactivityAppCompatButtonLookAuth:{
                                    result=Tea.decrypt(teaKey,new String(new HTTP().httpGet(url,true,3000,3000,new String[0])));
                                    Message h = new Message();
                                    h.what=1;
                                    h.obj=result;
                                    SQAuthActivity.this.handler.sendMessage(h);
                                }break;
                            case R.id.layoutactivitysqauthactivityAppCompatButtonLookKey:{
                                    result=Tea.decrypt(teaKey,new String(new HTTP().httpGet(url,true,3000,3000,new String[0])));
                                    if (result.equals("5699"))
                                    {
                                        result = "卡密不存在";
                                    }
                                    else
                                    {
                                        JSONObject json = new JSONObject(result);
                                        String code = json.get("code").toString();
                                        if (code.matches("5644"))
                                        {
                                            result="卡密已被使用，已增加授权时长：" + json.get("time").toString() + "\n卡密类型：" + json.get("type").toString();
                                        }
                                        else if (code.matches("5692"))
                                        {
                                            result="卡密正常，可增加授权时长：" + json.get("time").toString() + "\n卡密类型：" + json.get("type").toString();
                                        }
                                        else
                                        {
                                            result="未知错误";
                                        }
                                    }
                                    Message h = new Message();
                                    h.what=0;
                                    h.obj=result;
                                    SQAuthActivity.this.handler.sendMessage(h);
                                }break;
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static void generalDialog(Context context,String title,String message)
    {
        AlertDialog dialog=new AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .create();
        dialog.show();
    }
}
