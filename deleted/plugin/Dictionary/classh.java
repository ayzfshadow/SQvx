package com.saki.plugin.Dictionary;

import android.os.Environment;
import com.saki.http.HTTP;
import java.io.File;
import java.io.FileOutputStream;

public class classh extends Thread {
    private String a;
    private String b;

    public classh(String str, String str2) {
        this.a = str;
        this.b = str2;
        start();
    }

    public void run() {
        try {
            File file = new File(Environment.getExternalStorageDirectory() + this.a);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(new HTTP().httpGet(this.b, true, 1000, 10000, new String[0]));
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
        }
    }
}
