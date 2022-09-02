package com.saki.qq.userinfo;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.LruCache;
import com.saki.client.classd;
import com.saki.tool.Code;
import com.saki.tool.HexTool;
import com.saki.util.FileLoader;
import java.io.File;

@SuppressLint({"NewApi"})
public class imgstore {
    static LruCache<String, imgstore> h = new LruCache<String, imgstore>(4194304) {
        /* access modifiers changed from: protected */
        /* renamed from: a */
        public int sizeOf(String str, imgstore classc) {
            return classc.b;
        }
    };
    public byte[] a;
    public int b;
    public byte[] c;
    public int d;
    public int e;
    public String f;
    public long g;
    public int mark;

    private static int id;

    public classd.a v;
    
    public imgstore(byte[] bArr, int i, int i2, String str) {
        this.a = bArr;
        this.d = i;
        this.e = i2;
        this.f = str;
        this.b = bArr.length;
        this.c = Code.md5(bArr);
        this.mark=imgstore.id;
        imgstore.id+=1;
        com.saki.client.classd.cache.put((int)mark,this);
    }

    public static final imgstore a(String str) {
        imgstore classc = (imgstore) h.get(str);
        if (classc == null) {
            classc = a(com.saki.util.FileUtil.c(new File(str)));
            if (classc != null) {
                h.put(str, classc);
            }
        }
        return classc;
    }

    private static final imgstore a(byte[] bArr) {
        Options options = new Options();
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        if (decodeByteArray == null) {
            return null;
        }
        String str = options.outMimeType == null ? "image/jpg" : options.outMimeType;
        return new imgstore(bArr, decodeByteArray.getWidth(), decodeByteArray.getHeight(), str.substring(str.indexOf("/") + 1));
    }

    public static final imgstore b(String str) {
        imgstore classc = (imgstore) h.get(str);
        if (classc == null) {
            classc = a(FileLoader.load(str));
            if (classc != null) {
                h.put(str, classc);
            }
        }
        return classc;
    }

    

    public String toString() {
        return HexTool.Bytes2Hex(this.c, "") + "." + this.f;
    }
}
