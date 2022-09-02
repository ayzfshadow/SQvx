package com.setqq.script.sdk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import com.saki.util.BitmapCallback;
import com.saki.util.ImageUtil;
import com.saki.service.NewService;
import java.io.File;
import java.io.*;
import com.ayzf.sqvx.R;

public abstract class ScriptPlugin implements com.setqq.script.sdk.PluginInterface {
    public String name;
    public String version;
    public String info;
    public String author;
    public String id;
    public String icon;
    public String ui;
    public String filePath;

    public void initPluginItem(final NewService newService, final String url) {
        ImageUtil.loadBitmap(getIcon(), (BitmapCallback) new BitmapCallback() {
            public void callBack(Bitmap bitmap) {
                new com.setqq.script.ScriptItem(newService, ScriptPlugin.this.id, ScriptPlugin.this.name, ScriptPlugin.this.author, ScriptPlugin.this.version, ScriptPlugin.this.info, ScriptPlugin.this.hasUi(), new BitmapDrawable(newService.getResources(), bitmap), ScriptPlugin.this).setFromMacket(url);
            }
        }, BitmapFactory.decodeResource(newService.getResources(), R.drawable.drawable0064));
    }

    public String getUiUrl() {
        return this.ui.indexOf("http") == 0 ? this.ui : "file://" + new File(this.filePath).getParentFile() + "/" + this.ui;
    }

    public void initPluginItem(final NewService newService) {
        ImageUtil.loadBitmap(getIcon(), (BitmapCallback) new BitmapCallback() {
            public void callBack(Bitmap bitmap) {
                new com.setqq.script.ScriptItem(newService, ScriptPlugin.this.id, ScriptPlugin.this.name, ScriptPlugin.this.author, ScriptPlugin.this.version, ScriptPlugin.this.info, ScriptPlugin.this.hasUi(), new BitmapDrawable(newService.getResources(), bitmap), ScriptPlugin.this);
            }
        }, BitmapFactory.decodeResource(newService.getResources(), R.drawable.drawable0064));
    }

    public abstract String getIcon();

    public abstract boolean hasUi();
}
