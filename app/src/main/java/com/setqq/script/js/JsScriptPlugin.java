package com.setqq.script.js;

import android.content.Intent;
import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;
import com.eclipsesource.v8.V8Object;
import com.saki.service.NewService;
import com.setqq.script.Msg;
import com.setqq.script.sdk.ApiAdapter;
import com.setqq.script.sdk.ScriptPlugin;
import java.io.IOException;
import java.lang.reflect.Method;

public class JsScriptPlugin extends ScriptPlugin
{

    @Override
    public Intent getimpClass()
    {
        return null;
    }

    NewService newservice;
    V8Object v8obj;
    V8 v8 = V8.createV8Runtime("window");

    static {
        V8.setFlags("-harmony-dynamic-import");
        V8.setFlags("-harmony-import-meta");
    }

    public JsScriptPlugin(NewService newService, String str) throws com.setqq.script.NullIdException {
        this.newservice = newService;
        this.v8.executeVoidScript(com.saki.util.FileUtil.readStringFromFile(str));
        this.v8obj = this.v8.getObject("Script");
        this.id = this.v8obj.getString("id");
        if (this.id == null || this.id.equals("")) {
            throw new com.setqq.script.NullIdException(str);
        }
        this.name = this.v8obj.getString("name");
        this.version = this.v8obj.getString("version");
        this.author = this.v8obj.getString("author");
        this.info = this.v8obj.getString("info");
        this.icon = this.v8obj.getString("icon");
        this.ui = this.v8obj.getString("ui");
        this.filePath = str;
        initPluginItem(newService);
        this.v8.getLocker().release();
        com.saki.loger.ViewLoger.info("js插件" + name + "加载完成!");
    }

    public void onstop() {
        try {
            this.v8.getLocker().acquire();
            this.v8obj.release();
            this.v8.release();
        } finally {
            if (!(this.v8 == null || this.v8.getLocker() == null || !this.v8.getLocker().hasLock())) {
                this.v8.getLocker().release();
            }
            this.v8 = null;
        }
    }

    public void onload(NewService newService) throws Throwable {
        process(new Msg(19));
    }

    public void process(Msg msg) throws Throwable {
        callFunction("handleMessage", new ApiAdapter(this.newservice, msg));
    }

	void callFunction(String str, com.setqq.script.sdk.classa com_setqq_script_sdk_classa) throws Throwable {
		Throwable th;
		V8Array v8Array = null;
		V8Object v8Object;
		try {
			this.v8.getLocker().acquire();
			v8Object = new V8Object(this.v8);
			try {
				for (Method method : com_setqq_script_sdk_classa.getClass().getMethods()) {
					String name = method.getName();
					v8Object.registerJavaMethod(com_setqq_script_sdk_classa, name, name, method.getParameterTypes());
				}
				v8Array = new V8Array(this.v8).push(v8Object);
				this.v8obj.executeFunction(str, v8Array);
				if (v8Object != null) {
					v8Object.release();
				}
				if (v8Array != null) {
					v8Array.release();
				}
				if (this.v8 != null && this.v8.getLocker() != null && this.v8.getLocker().hasLock()) {
					this.v8.getLocker().release();
				}
			} catch (Throwable th2) {
				th = th2;
				if (v8Object != null) {
					v8Object.release();
				}
				if (v8Array != null) {
					v8Array.release();
				}
				if (!(this.v8 == null || this.v8.getLocker() == null || !this.v8.getLocker().hasLock())) {
					this.v8.getLocker().release();
				}
				throw th;
			}
		} catch (Throwable th3) {
			th = th3;
			v8Object = v8Array;
			if (v8Object != null) {
				v8Object.release();
			}
			if (v8Array != null) {
				v8Array.release();
			}
			this.v8.getLocker().release();
			throw th;
		}
	}

    public String getIcon() {
        if (this.icon == null || !this.icon.equals("")) {
            return null;
        }
        return this.icon;
    }

    public boolean hasUi() {
        return this.ui != null && !this.ui.equals("");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0087  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public String onAction(String str) throws Throwable {
		V8Object v8Object;
		Throwable th;
		V8Array v8Array = null;
		try {
			this.v8.getLocker().acquire();
			v8Object = new V8Object(this.v8);
			try {
				Msg msg = new Msg(-1);
				msg.addMsg("msg", str);
				ApiAdapter apiAdapter = new ApiAdapter(this.newservice, msg);
				for (Method method : apiAdapter.getClass().getMethods()) {
					String name = method.getName();
					v8Object.registerJavaMethod(apiAdapter, name, name, method.getParameterTypes());
				}
				v8Array = new V8Array(this.v8).push(v8Object);
				String executeStringFunction = this.v8obj.executeStringFunction("onAction", v8Array);
				if (v8Object != null) {
					v8Object.release();
				}
				if (v8Array != null) {
					v8Array.release();
				}
				if (!(this.v8 == null || this.v8.getLocker() == null || !this.v8.getLocker().hasLock())) {
					this.v8.getLocker().release();
				}
				return executeStringFunction;
			} catch (Throwable th2) {
				th = th2;
				if (v8Object != null) {
					v8Object.release();
				}
				if (v8Array != null) {
					v8Array.release();
				}
				if (!(this.v8 == null || this.v8.getLocker() == null || !this.v8.getLocker().hasLock())) {
					this.v8.getLocker().release();
				}
				throw th;
			}
		} catch (IOException th3) {
			th = th3;
			v8Object = v8Array;
			if (v8Object != null) {
				v8Object.release();
			}
			if (v8Array != null) {
				v8Array.release();
			}
			this.v8.getLocker().release();
			throw th;
		}
	}
}
