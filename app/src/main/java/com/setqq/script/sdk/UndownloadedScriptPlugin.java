package com.setqq.script.sdk;

import com.saki.service.NewService;
import com.setqq.script.Msg;
import com.setqq.script.ScriptItem;
import org.json.JSONObject;
import org.json.*;
import com.saki.util.ImageUtil;
import com.saki.util.BitmapCallback;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Bitmap;
import com.ayzf.sqvx.R;
import android.content.Intent;

public class UndownloadedScriptPlugin extends ScriptPlugin
{

    @Override
    public Intent getimpClass()
    {
        return null;
    }

    public int plugintype=0;//0的时候是脚本,1的时候是apk
    public UndownloadedScriptPlugin(NewService newService, JSONObject jSONObject) throws JSONException {
        this.id = jSONObject.getString("id");
        this.version = jSONObject.getString("version");
        ScriptItem a = com.saki.qq.userinfo.PluginCenter.getInstence().getScriptById(this.id);
        if (a == null) {
            this.name = jSONObject.getString("name");
            this.author = jSONObject.getString("author");
            this.info = jSONObject.getString("info");
            String string = jSONObject.getString("download");
            this.ui = jSONObject.optString("synopsis");
            this.icon = jSONObject.optString("icon");
            this.plugintype=jSONObject.optInt("plugintype");
            initPluginItem(newService, string);
        } else if (!a.version.equals(this.version)) {
            a.status = 2;
            a.downloadUrl = jSONObject.getString("download");
        }
    }
    
    public void initPluginItem(final NewService newService, final String url) {
        ImageUtil.loadBitmap(getIcon(), (BitmapCallback) new BitmapCallback() {
                                 public void callBack(Bitmap bitmap) {
                                     new com.setqq.script.ScriptItem(newService, id, name, author, version, info, hasUi(), new BitmapDrawable(newService.getResources(), bitmap), UndownloadedScriptPlugin.this).setFromMacket(url).setType(UndownloadedScriptPlugin.this.plugintype);
                                 }
                             }, BitmapFactory.decodeResource(newService.getResources(), R.drawable.drawable0064));
    }

    public void onstop() {
    }

    public void onload(NewService newService) {
    }

    public void process(Msg msg) {
    }

    public String getIcon() {
        if (this.icon == null || this.icon.equals("")) {
            return null;
        }
        return this.icon;
    }

    public boolean hasUi() {
        return this.ui != null && !this.ui.equals("");
    }

    public String onAction(String str) {
        return null;
    }
}
