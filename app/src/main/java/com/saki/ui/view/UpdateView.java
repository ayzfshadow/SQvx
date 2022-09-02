package com.saki.ui.view;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.internal.view.SupportMenu;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import com.saki.util.FileLoader;
import com.saki.util.ApkUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;
import org.json.JSONObject;
import com.ayzf.sqvx.R;
import android.util.Log;
import android.support.v4.content.ContextCompat;

public class UpdateView extends View implements OnClickListener {
    
    
    File c = new File(Environment.getExternalStorageDirectory() + "/SQ/update/temp.apk");
    private Paint d;
    private RectF e;
    /* access modifiers changed from: private */
    public int f = 100;
    /* access modifiers changed from: private */
    public int g = 100;
    /* access modifiers changed from: private */
    public String h = "";
    private String i;
    /* access modifiers changed from: private */
    public String UpdateMessage = "检查更新中...";
    /* access modifiers changed from: private */
    public boolean k = false;
    /* access modifiers changed from: private */
    public boolean l = false;
    /* access modifiers changed from: private */
    public boolean m = false;
    private int o;
    /* access modifiers changed from: private */
    public Handler handler = new Handler() {
        public void handleMessage(Message message) {
            if (message.what == 0) {
                UpdateView.this.invalidate();
            } else if (message.what == 2) {
                UpdateView.this.UpdateMessage = (String) message.obj;
                UpdateView.this.invalidate();
            }
        }
    };

    public UpdateView(Activity activity) {
        super(activity);
        a();
    }

    public UpdateView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public UpdateView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a();
    }

    private void a() {
        this.e = new RectF();
        this.e.top = 0.0f;
        this.e.left = 0.0f;
        this.d = new Paint();
        this.d.setAntiAlias(true);
        this.d.setDither(true);
        setOnClickListener(this);
        Context context = getContext();
        try {
            this.o = context.getPackageManager().getPackageInfo(context.getPackageName(), 16384).versionCode;
        } catch (NameNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2, String str, String str2) {
        getContext();
        if (this.o < i2) {
            this.k = true;
            this.i = "发现新版本[点击更新]:" + str;
            this.h = str2;
            this.c = new File(Environment.getExternalStorageDirectory() + "/SQ/update/" + i2 + ".apk");
            this.handler.sendMessage(Message.obtain(this.handler, 2, this.i));
            return;
        }
        this.handler.sendMessage(Message.obtain(this.handler, 2, "当前已经是最新版本"));
    }

    public void a(final String str) {
        if (com.ayzf_shadow.tool.jiance.vpn.֏֏֏֏֏֏() == false || com.ayzf_shadow.tool.jiance.xposed.֏֏֏֏֏֏() == false)
        {
            UpdateView.this.handler.sendMessage(Message.obtain(UpdateView.this.handler, 2, "网络环境异常"));
            return;
        }
        new Thread(new Runnable() {
                public void run() {
                    try {
                        JSONObject jSONObject = new JSONObject(new String(FileLoader.load(str)));
                        UpdateView.this.a(jSONObject.getInt("versionCode"), jSONObject.optString("info"), jSONObject.optString("down"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        UpdateView.this.handler.sendMessage(Message.obtain(UpdateView.this.handler, 2, "更新检测失败"));
                    }
                }
            }).start();
    }

    public InputStream b(String str) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
        httpURLConnection.connect();
        if (httpURLConnection.getResponseCode() == 302) {
            return b(httpURLConnection.getHeaderField("Location"));
        }
        this.f = httpURLConnection.getContentLength();
        return httpURLConnection.getInputStream();
    }

    public int getCode() {
        return this.o;
    }

    public void onClick(View view) {
        if (this.l) {
            ApkUtil.installApk(getContext(), this.c);
        } else if (this.m) {
            this.m = false;
            this.handler.sendMessage(Message.obtain(this.handler, 2, "更新已取消"));
        } else if (this.k && !this.m) {
            this.handler.sendMessage(Message.obtain(this.handler, 2, "正在下载更新"));
            new Thread(new Runnable() {
                    public void run() {
                        if (!UpdateView.this.c.exists()) {
                            try {
                                File parentFile = UpdateView.this.c.getParentFile();
                                if (!parentFile.exists()) {
                                    parentFile.mkdirs();
                                } else {
                                    for (File delete : parentFile.listFiles()) {
                                        delete.delete();
                                    }
                                }
                                UpdateView.this.c.createNewFile();
                                UpdateView.this.g = 0;
                                BufferedInputStream bufferedInputStream = new BufferedInputStream(UpdateView.this.b(UpdateView.this.h));
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(UpdateView.this.c));
                                UpdateView.this.m = true;
                                while (UpdateView.this.m) {
                                    int read = bufferedInputStream.read();
                                    if (read == -1) {
                                        break;
                                    }
                                    bufferedOutputStream.write(read);
                                    UpdateView.this.g = UpdateView.this.g + 1;
                                    if (UpdateView.this.g % (UpdateView.this.f / 100) == 0) {
                                        UpdateView.this.handler.sendEmptyMessage(0);
                                    }
                                }
                                if (UpdateView.this.m) {
                                    UpdateView.this.k = false;
                                    bufferedOutputStream.flush();
                                    bufferedOutputStream.close();
                                    bufferedOutputStream.close();
                                    bufferedInputStream.close();
                                    UpdateView.this.handler.sendMessage(Message.obtain(UpdateView.this.handler, 2, "下载完成!点击安装!"));
                                    UpdateView.this.l = true;
                                    return;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            UpdateView.this.c.delete();
                            UpdateView.this.handler.sendMessage(Message.obtain(UpdateView.this.handler, 2, "下载失败"));
                            return;
                        }
                        UpdateView.this.handler.sendMessage(Message.obtain(UpdateView.this.handler, 2, "下载完成!点击安装!"));
                        UpdateView.this.l = true;
                    }
                }).start();
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.saveLayer(this.e, null, 31);
        this.d.setStyle(Style.FILL);
        
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), new int[]{ContextCompat.getColor(this.getContext(),R.color.colorPrimary), -1}, null, TileMode.CLAMP);
        float f2 = (this.e.right / ((float) this.f)) * ((float) this.g);
        this.d.setShader(linearGradient);
        canvas.drawRect(this.e.left, this.e.top, f2, this.e.bottom, this.d);
        this.d.setShader(null);
        this.d.setStyle(Style.STROKE);
        this.d.setColor(ContextCompat.getColor(this.getContext(),R.color.colorAccent));
        this.d.setStrokeWidth(1.0f);
        canvas.drawRoundRect(this.e, 0.0f, 0.0f, this.d);
        this.d.setStyle(Style.FILL_AND_STROKE);
        this.d.setStrokeWidth(1.0f);
        this.d.setColor(ContextCompat.getColor(this.getContext(),R.color.colorAccent));
        this.d.setTypeface(Typeface.MONOSPACE);
        this.d.setTextSize(25.0f);
        Rect rect = new Rect();
        this.d.getTextBounds(this.UpdateMessage, 0, this.UpdateMessage.length(), rect);
        canvas.drawText(this.UpdateMessage, (this.e.width() / 2.0f) - ((float) (rect.width() / 2)), ((float) (rect.height() / 2)) + (this.e.height() / 2.0f), this.d);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        this.e.bottom = this.e.top + ((float) i3);
        this.e.right = this.e.left + ((float) i2);
        super.onSizeChanged(i2, i3, i4, i5);
    }
}

