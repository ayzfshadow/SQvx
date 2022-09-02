package com.saki.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class ImageUtil {
    private static Bitmap getCachedFace(String str) {
        return b(FileUtil.b + "face/" + str + ".png");
    }

    public static Bitmap getCachedFace(String str, boolean z) {
        Bitmap bitmap = null;
        if (z) {
            bitmap = getCachedFace(str);
        }
        return bitmap == null ? loadFace(str) : bitmap;
    }

    public static void getHeader(final String str, final int i, final BitmapCallback classa) {
        new Thread(new Runnable() {
            public void run() {
                if (i == 0) {
                    classa.callBack(ImageUtil.getCachedHeader(str, true));
                } else {
                    classa.callBack(ImageUtil.getCachedFace(str, true));
                }
            }
        }).start();
    }

    public static void loadBitmap(final String str, final BitmapCallback classa, final Bitmap bitmap) {
        if (classa == null) {
            return;
        }
        if (str == null) {
            classa.callBack(bitmap);
        } else {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Bitmap decodeStream = BitmapFactory.decodeStream(new URL(str).openStream());
                        if (decodeStream != null) {
                            classa.callBack(decodeStream);
                            return;
                        }
                    } catch (IOException e) {
                    }
                    classa.callBack(bitmap);
                }
            }).start();
        }
    }

    private static void a(String str, byte[] bArr) {
        b("face/" + str + ".png", bArr);
    }

    private static Bitmap b(String str) {
        File file = new File(FileUtil.b + str);
        if (file.exists()) {
            try {
                return BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Bitmap getCachedHeader(String str, boolean z) {
        Bitmap bitmap = null;
        if (z) {
            bitmap = getCachedHeader(str);
        }
        return bitmap == null ? loadLogo(str) : bitmap;
    }

    private static void b(String str, byte[] bArr) {
        File file = new File(FileUtil.b + str);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                return;
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private static Bitmap getCachedHeader(String str) {
        return b(FileUtil.b + "logo/" + str + ".png");
    }

    private static void c(String str, byte[] bArr) {
        b("logo/" + str + ".png", bArr);
    }

    private static Bitmap loadFace(String str) {
        byte[] a = FileLoader.load("http://q2.qlogo.cn/headimg_dl?dst_uin=" + str + "&spec=100");
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(a, 0, a.length);
        if (decodeByteArray == null) {
            return null;
        }
        a(str, a);
        return decodeByteArray;
    }

    private static Bitmap loadLogo(String str) {
        byte[] a = FileLoader.load("http://p.qlogo.cn/gh/" + str + "/" + str + "/40?t=" + System.currentTimeMillis());
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(a, 0, a.length);
        if (decodeByteArray == null) {
            return null;
        }
        c(str, a);
        return decodeByteArray;
    }
}
