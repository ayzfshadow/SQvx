package com.setqq.script.lua;

import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.saki.loger.ViewLoger;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import com.setqq.script.sdk.ApiAdapter;
import com.setqq.script.sdk.ScriptPlugin;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class LuaScriptPlugin extends ScriptPlugin
{

    @Override
    public Intent getimpClass()
    {
        return null;
    }

    private LuaState a = LuaStateFactory.newLuaState();
    private NewService b;

    public LuaScriptPlugin(NewService newService, String str) throws com.setqq.script.NullIdException {
        this.a.openLibs();
        this.a.LdoString(com.saki.util.FileUtil.readStringFromFile(str));
        this.filePath = str;
        this.b = newService;
        this.id = this.a.getLuaObject("id").toString();
        if (this.id.equals("nil")) {
            throw new com.setqq.script.NullIdException(this.filePath);
        }
        this.name = this.a.getLuaObject("name").toString();
        this.author = this.a.getLuaObject("author").toString();
        this.version = this.a.getLuaObject("version").toString();
        this.info = this.a.getLuaObject("info").toString();
        this.icon = this.a.getLuaObject("icon").toString();
        this.ui = this.a.getLuaObject("ui").toString();
        initPluginItem(newService);
        com.saki.loger.ViewLoger.info("lua插件" + name + "加载完成!");
    }

    private String a(String str, Object... objArr) {
        this.a.getField(LuaState.LUA_GLOBALSINDEX.intValue(), str);
        for (Object pushJavaObject : objArr) {
            this.a.pushJavaObject(pushJavaObject);
        }
        int pcall = this.a.pcall(objArr.length, 1, 0);
        if (pcall != 0) {
            ViewLoger.erro((Object) "lua脚本[" + this.name + "]发生异常,错误代码:(" + pcall + ")\n" + this.a.getLuaObject(1));
            return null;
        }
        this.a.setField(LuaState.LUA_GLOBALSINDEX.intValue(), "result");
        return this.a.getLuaObject("result").toString();
    }

    private void b(String str, Object... objArr) {
        this.a.getField(LuaState.LUA_GLOBALSINDEX.intValue(), str);
        for (Object pushJavaObject : objArr) {
            this.a.pushJavaObject(pushJavaObject);
        }
        int pcall = this.a.pcall(objArr.length, 0, 0);
        if (pcall != 0) {
            ViewLoger.erro((Object) "lua脚本[" + this.name + "]发生异常,错误代码:(" + pcall + ")\n" + this.a.getLuaObject(1));
        }
    }

    public void onstop() {
    }

    public void onload(NewService newService) {
        process(new Msg(19));
    }

    public void process(Msg msg) {
        b("handleMessage", new ApiAdapter(this.b, msg));
        if (msg.type == 18) {
            onstop();
        }
    }

    public String getIcon() {
        if (!this.icon.equals("nil")) {
            return this.icon;
        }
        return null;
    }

    public boolean hasUi() {
        return this.ui != null &&!this.ui.equals("nil");
    }

    @JavascriptInterface
    public String onAction(String str) {
        Msg msg = new Msg();
        msg.addMsg("msg", str);
        return a("onAction", new ApiAdapter(this.b, msg));
    }
}
