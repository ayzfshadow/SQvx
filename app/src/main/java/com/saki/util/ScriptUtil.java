package com.saki.util;

import com.eclipsesource.v8.V8;
import com.saki.client.Client;
import com.saki.http.HTTP;
import com.saki.tool.HexTool;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

public class ScriptUtil {
    public static final String[] a = {".sl", ".sj", ".bak", ".zip"};
    File b;
    String c;

    public ScriptUtil(String str) {
        this.b = new File(str);
    }

    public static String a(File file, String str) throws FileNotFoundException, IOException {
        return a((InputStream) new FileInputStream(file), str);
    }

    public static String a(InputStream inputStream, String str) throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        String str2 = null;
        while (true) {
            ZipEntry nextEntry = zipInputStream.getNextEntry();
            if (nextEntry == null) {
                zipInputStream.close();
                return str2;
            } else if (nextEntry.isDirectory()) {
                if (str2 == null) {
                    str2 = nextEntry.getName();
                }
                File file = new File(str + nextEntry.getName() + "/");
                if (!file.exists()) {
                    file.mkdirs();
                }
            } else {
                File file2 = new File(str + nextEntry.getName());
                if (!file2.exists()) {
                    file2.getParentFile().mkdirs();
                    file2.createNewFile();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                byte[] bArr = new byte[65536];
                while (true) {
                    int read = zipInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    fileOutputStream.write(bArr, 0, read);
                }
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
    }

    private static void a(File file) throws IOException {
        String absolutePath = file.getAbsolutePath();
        File file2 = new File(absolutePath.substring(0, absolutePath.lastIndexOf(".")) + b(file));
        if (!file2.exists()) {
            file2.createNewFile();
        }
        FileUtil.a(file, file2);
    }

    public static void a(ZipOutputStream zipOutputStream, File[] fileArr, String str, String... strArr) throws IOException {
        boolean z;
        if (fileArr != null) {
            for (File file : fileArr) {
                if (file != null && file.exists()) {
                    if (strArr != null) {
                        int i = 0;
                        while (true) {
                            if (i >= strArr.length) {
                                z = false;
                                break;
                            } else if (file.getName().endsWith(strArr[i])) {
                                z = true;
                                break;
                            } else {
                                i++;
                            }
                        }
                        if (z) {
                        }
                    }
                    if (file.isDirectory()) {
                        String str2 = str + file.getName() + "/";
                        zipOutputStream.putNextEntry(new ZipEntry(str2));
                        a(zipOutputStream, file.listFiles(), str2, new String[0]);
                    } else {
                        zipOutputStream.putNextEntry(new ZipEntry(str + file.getName()));
                        FileInputStream fileInputStream = new FileInputStream(file);
                        byte[] bArr = new byte[65536];
                        while (true) {
                            int read = fileInputStream.read(bArr);
                            if (read == -1) {
                                break;
                            }
                            zipOutputStream.write(bArr, 0, read);
                        }
                        fileInputStream.close();
                    }
                }
            }
        }
    }

    private static String b(File file) {
        return file.getName().endsWith(".sl") ? ".ecl" : file.getName().endsWith(".sj") ? ".ecj" : ".ec";
    }

    public static byte[] b(File file, String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            a(file);
            ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
            a(zipOutputStream, file.getParentFile().listFiles(), str, a);
            zipOutputStream.flush();
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArrayOutputStream.toByteArray();
    }

//    public static void updateLibrary() {
//        new Thread(new Runnable() {
//            private boolean a(HTTP classb, String str, File file) {
//                com.saki.loger.ViewLoger.info("更新库文件:" + str);
//                try {
//                    return FileUtil.writeFile(file, classb.httpGet(str, true, 3000, 10000, new String[0]));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    return false;
//                }
//            }
//
//            public void run() {
//                String str = Client.MAINSEEVERURL+"?cmd=scriptlib";
//                try {
//                    HTTP classb = new HTTP();
//                    JSONObject jSONObject = new JSONObject(new String(classb.httpGet(str, true, 3000, 3000, new String[0])));
//                    if (jSONObject.getInt("code") == 0) {
//                        JSONArray jSONArray = jSONObject.getJSONArray("files");
//                        for (int i = 0; i < jSONArray.length(); i++) {
//                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
//                            String string = jSONObject2.getString("name");
//                            String string2 = jSONObject2.getString("md5");
//                            String string3 = jSONObject2.getString("url");
//                            File file = new File(FileUtil.d + string);
//                            if (!file.exists()) {
//                                a(classb, string3, file);
//                            } else if (!HexTool.a(FileUtil.a(file), "").equals(string2)) {
//                                a(classb, string3, file);
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    public String a() {
        return this.c;
    }

    public File b() {
        return this.b;
    }

    public boolean c() {
        try {
            if (this.b.getName().endsWith(".sl")) {
                LuaState newLuaState = LuaStateFactory.newLuaState();
                newLuaState.openLibs();
                newLuaState.LdoString(new String(FileUtil.c(this.b)));
                this.c = newLuaState.getLuaObject("id").toString();
                newLuaState.getLuaObject("ui").toString();
                return !this.c.equals("nil");
            } else if (!this.b.getName().endsWith(".sj")) {
                return false;
            } else {
                V8 createV8Runtime = V8.createV8Runtime();
                createV8Runtime.executeScript(new String(FileUtil.c(this.b)));
                this.c = createV8Runtime.getObject("Script").getString("id");
                return this.c != null && !this.c.equals("");
            }
        } catch (Exception e) {
            StringWriter stringWriter = new StringWriter();
            e.printStackTrace(new PrintWriter(stringWriter));
            com.saki.loger.ViewLoger.erro((Object) "识别脚本异常:" + stringWriter.toString());
            return false;
        }
    }
}
