package com.setqq.script.sdk;

import android.os.Environment;
import com.saki.http.HTTP;
import com.saki.util.FileUtil;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class ApiAdapter implements classa {
    protected NewService context;
    public Msg msg;

    public ApiAdapter(NewService newService, Msg msg2) {
        this.context = newService;
        this.msg = msg2;
    }

    public void add(String str, String str2) {
        this.msg.addMsg(str, str2);
    }

    public String at(int i) {
        ArrayList msg2 = this.msg.getMsg("at");
        if (msg2 == null || i < 0 || i >= msg2.size()) {
            return null;
        }
        return (String) msg2.get(i);
    }

    public int atCnt() {
        if (this.msg.getMsg("at") == null) {
            return 0;
        }
        return this.msg.getMsg("at").size();
    }

    public void clearMsg() {
        this.msg.clearMsg();
    }

    public String get(String str) {
        try {
            return new String(new HTTP().httpGet(str, true, 1000, 3000, new String[0]));
        } catch (Exception e) {
            com.saki.loger.ViewLoger.erro((Object) "网页GET异常:" + e.toString());
            return null;
        }
    }

    public double getCode() {
        return (double) this.msg.code;
    }

    public double getGroupId() {
        return (double) this.msg.groupid;
    }

    public String getGroupName() {
        return this.msg.groupName;
    }

    public String getLibPath() {
        return FileUtil.d;
    }

    public String getRootPath() {
        return Environment.getExternalStorageDirectory() + "/";
    }

    public String getScriptPath() {
        return FileUtil.c;
    }

    public String getTextMsg() {
        return this.msg.getTextMsg();
    }

    public double getTime() {
        return (double) this.msg.time;
    }

    public String getTitle() {
        return this.msg.title;
    }

    public int getType() {
        return this.msg.type;
    }

    public double getUin() {
        return (double) this.msg.uin;
    }

    public String getUinName() {
        return this.msg.uinName;
    }

    public int getValue() {
        return this.msg.value;
    }

    public String httpCall(String str, String str2, String str3, String str4) {
        byte[] bArr = null;
        if (str3 != null && !str3.equals("")) {
            bArr = str3.getBytes();
        }
        return new String(new HTTP().a(str, str2, bArr, str4));
    }

    public String load(String str, String str2, String str3) {
        File file = new File(str);
        if (!file.exists()) {
            return str3;
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
            return properties.getProperty(str2);
        } catch (IOException e) {
            com.saki.loger.ViewLoger.erro((Object) "读取错误:" + e.toString());
            return str3;
        }
    }

    public void logE(String str) {
        com.saki.loger.ViewLoger.erro((Object) str);
    }

    public void logI(String str) {
        com.saki.loger.ViewLoger.info(str);
    }

    public void logW(String str) {
        com.saki.loger.ViewLoger.warnning(str);
    }

    public String post(String str, String str2) {
        try {
            return new String(new HTTP().httpPost(str, str2.getBytes(), 1000, 3000, new String[0]));
        } catch (Exception e) {
            com.saki.loger.ViewLoger.erro((Object) "网页POST异常:" + e.toString());
            return null;
        }
    }

    public String read(String str) {
        return new String(FileUtil.c(new File(str)));
    }

    public double robot() {
        return (double) com.saki.qq.userinfo.User.uin;
    }

    public void save(String str, String str2, String str3) {
        File file = new File(str);
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            Properties properties = new Properties();
            properties.load(new FileInputStream(file));
            properties.setProperty(str2, str3);
            properties.store(new FileOutputStream(file), null);
        } catch (IOException e) {
            com.saki.loger.ViewLoger.erro((Object) "保存错误:" + e.toString());
        }
    }

    public classa send() {
        Msg a = new PluginApi(this.context).send(this.msg);
        if (a == null) {
            return null;
        }
        this.msg = a;
        return this;
    }

    public void setCode(double d) {
        this.msg.code = Double.valueOf(d).longValue();
    }

    public void setGroupId(double d) {
        this.msg.groupid = Double.valueOf(d).longValue();
    }

    public void setGroupName(String str) {
        this.msg.groupName = str;
    }

    public void setTime(double d) {
        this.msg.time = Double.valueOf(d).longValue();
    }

    public void setTitle(String str) {
        this.msg.title = str;
    }

    public void setType(int i) {
        this.msg.type = i;
    }

    public void setUin(double d) {
        this.msg.uin = Double.valueOf(d).longValue();
    }

    public void setUinName(String str) {
        this.msg.uinName = str;
    }

    public void setValue(int i) {
        this.msg.value = i;
    }

    public void write(String str, String str2) {
        FileUtil.writeFile(new File(str), str2.getBytes());
    }
}
