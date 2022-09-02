package com.saki.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import com.ayzf.sqvx.R;

public class DownloadButton extends View {
    private Paint a;
    private Paint b;
    private String c;
    /* access modifiers changed from: private */
    public int d = 0;
    /* access modifiers changed from: private */
    public InputStream e;
    /* access modifiers changed from: private */
    public ByteArrayOutputStream f;
    /* access modifiers changed from: private */
    public DownloadCompleteListener g;
    private a h;

    private class a extends Thread {
        private String b;
        private float c = 0.0f;
        private int d = 0;
        private DecimalFormat e;

        public a(String str) {
            this.b = str;
            this.e = new DecimalFormat("#.00");
        }

        private void d() throws IOException {
            byte[] bArr = new byte[65536];
            do {
                int read = DownloadButton.this.e.read(bArr);
                if (read != -1) {
                    DownloadButton.this.f.write(bArr, 0, read);
                    this.c = ((float) DownloadButton.this.f.size()) / ((float) this.d);
                    if (this.c > 1.0f) {
                        this.c = 1.0f;
                    }
                    DownloadButton.this.postInvalidate();
                } else {
                    try
                    {
                        DownloadButton.this.g.onDownloadComplete(DownloadButton.this.f.toByteArray());
                    }
                    catch (Exception e)
                    {e.printStackTrace();}
                    return;
                }
            } while (!interrupted());
        }

        public float a() {
            return this.c;
        }

        public String b() {
            return this.e.format((double) (this.c * 100.0f)) + "%";
        }

        public void c() {
            interrupt();
        }

        public void run() {
            DownloadButton.this.postInvalidate();
            try {
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.b).openConnection();
                httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
                httpURLConnection.connect();
                this.d = httpURLConnection.getContentLength();
                DownloadButton.this.f = new ByteArrayOutputStream(this.d);
                DownloadButton.this.e = httpURLConnection.getInputStream();
                d();
            } catch (IOException e2) {
                DownloadButton.this.d = -1;
                DownloadButton.this.postInvalidate();
            }
        }
    }

    public interface DownloadCompleteListener {
        void onDownloadComplete(byte[] bArr) throws Exception;
    }

    public DownloadButton(Context context) {
        super(context);
        a();
    }

    public DownloadButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public DownloadButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        this.a = new Paint(1);
        this.a.setColor(-1);
        this.b = new Paint();
        this.b.setColor(this.getResources().getColor(R.color.colorPrimary));
        this.c = "下载";
    }

    public void a(String str, DownloadCompleteListener bVar) {
        this.g = bVar;
        if (this.h == null) {
            this.h = new a(str);
            this.h.start();
            this.d = 1;
            return;
        }
        this.h.c();
        this.h = null;
        this.d = 0;
        postInvalidate();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.b.setStyle(Style.FILL);
        canvas.drawRect(0.0f, 0.0f, this.h != null ? ((float) getWidth()) * this.h.a() : (float) getWidth(), (float) getHeight(), this.b);
        this.b.setStyle(Style.STROKE);
        canvas.drawRect(0.0f, 0.0f, (float) getWidth(), (float) getHeight(), this.b);
        FontMetrics fontMetrics = this.a.getFontMetrics();
        float height = (((float) (getHeight() / 2)) + ((fontMetrics.descent - fontMetrics.ascent) / 2.0f)) - fontMetrics.descent;
        String str = this.c;
        if (this.d == 1) {
            str = this.h.b();
        } else if (this.d == -1) {
            str = "下载失败";
        }
        canvas.drawText(str, ((float) (getWidth() / 2)) - (this.a.measureText(str) / 2.0f), height, this.a);
    }

    public void setTitle(String str) {
        this.c = str;
    }
}
