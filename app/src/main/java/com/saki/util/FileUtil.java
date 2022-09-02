package com.saki.util;

import android.app.*;
import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.design.widget.*;
import android.util.*;
import android.view.*;
import com.saki.loger.*;
import java.io.*;
import java.util.*;
import com.saki.tool.TeaCryptor;

public class FileUtil {
    public static String externalpath;
    public static String b;
    public static String c;
    public static String d;

	
	public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    if (columnIndex > -1) {
                        path = cursor.getString(columnIndex);
                    }
                }
                cursor.close();
            }
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
																	  Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }
                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};
                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
	
	
	
    public static String getFile(Context context, Uri uri) {
        String str = null;
        if (uri == null) {
            return null;
        }
		
        String scheme = uri.getScheme();
		Log.d("jjjjj",scheme);
        if (scheme == null) {
            return uri.getPath();
        }
        if ("file".equals(scheme)) {
            return uri.getPath();
        }
        if (!"content".equals(scheme)) {
            return null;
        }
        Cursor query = context.getContentResolver().query(uri, new String[]{"_data"}, null, null, null);
        if (query == null) {
            return null;
        }
        if (query.moveToFirst()) {
            int columnIndex = query.getColumnIndex("_data");
            if (columnIndex > -1) {
                str = query.getString(columnIndex);
            }
        }
        query.close();
		
        return str;
    }

    public static String readStringFromFile(String str) {
        return new String(readByteArrayFromFile(new File(str)));
    }

    public static void chooseFile(View view,Activity activity, int i) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("*/*");
        intent.addCategory("android.intent.category.OPENABLE");
        try {
            activity.startActivityForResult(Intent.createChooser(intent, "选择文件"), i);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(view, "请安装一个文件管理器", 1000).show();
        }
    }

    public static void a(Context context) {
        System.out.println("----------------------InitPath-------------------------");
        externalpath = context.getApplicationContext().getExternalFilesDir(null).getAbsolutePath() + "/";
        b = context.getApplicationContext().getExternalCacheDir().getAbsolutePath() + "/";
        System.out.println("BASE_PATH:" + externalpath);
        System.out.println("CACHE_PATH:" + b);
        c = externalpath + "Script/";
        d = externalpath + "ScriptLib/";
        System.out.println("----------------------InitPath-------------------------");
    }

    public static boolean a(File file, File file2) {
        boolean z = false;
        if (!file.exists()) {
            return z;
        }
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[65536];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read == -1) {
                    return a(byteArrayOutputStream.toByteArray(), file2);
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return z;
        }
    }
	
	public static final boolean writeFile(File file, InputStream bArr) {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
			byte[] b = new byte[1024];
			while ((bArr.read(b)) != -1) {
				fileOutputStream.write(b);// 写入数据
			}
			bArr.close();
			
         
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            ViewLoger.erro("文件写入失败:" + e);
            return false;
        }
    }

    public static final boolean writeFile(File file, byte[] bArr) {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bArr);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e) {
            ViewLoger.erro("文件写入失败:" + e);
            return false;
        }
    }

    public static boolean a(byte[] bArr, File file) {
        byte[] bArr2 = new byte[16];
        new Random().nextBytes(bArr2);
        byte[] b2 = new TeaCryptor().encrypt(bArr, com.saki.tool.Code.md5(bArr2));
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(b2.length + 16);
            byteArrayOutputStream.write(bArr2);
            byteArrayOutputStream.write(b2);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return writeFile(file, byteArray);
        } catch (IOException e) {
            return false;
        }
    }

    public static byte[] a(File file) {
        return com.saki.tool.Code.md5(c(file));
    }

    public static byte[] readByteArrayFromFile(File file) {
        if (file.exists()) {
            try {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[65536];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        fileInputStream.close();
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        byte[] bArr2 = new byte[16];
                        System.arraycopy(byteArray, 0, bArr2, 0, 16);
                        byte[] bArr3 = new byte[(byteArray.length - 16)];
                        System.arraycopy(byteArray, 16, bArr3, 0, bArr3.length);
                        TeaCryptor cry =  new TeaCryptor();
                        byte[]o= cry.decrypt(bArr3, com.saki.tool.Code.md5(bArr2));
                        cry.destroy();
                        return o;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static final byte[] c(File file) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] bArr = new byte[65536];
            while (true) {
                int read = fileInputStream.read(bArr);
                if (read != -1) {
                    byteArrayOutputStream.write(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            ViewLoger.erro("文件读取失败:" + e);
            return new byte[0];
        }
    }

    public static void deleteFile(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                File[] listFiles = file.listFiles();
                if (listFiles != null) {
                    for (File d2 : listFiles) {
                        deleteFile(d2);
                    }
                }
            }
            file.delete();
        }
    }
}
