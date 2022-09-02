//
// Decompiled by Jadx - 934ms
//
package com.saki.plugin.Dictionary;

import com.saki.util.FileUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class classb {
	public static String a(String str, String str2, String str3) {
		Properties properties = new Properties();
		try {
			InputStream fileInputStream = new FileInputStream(new File(FileUtil.externalpath + str));
			properties.load(fileInputStream);
			fileInputStream.close();
			str3 = properties.getProperty(str2, str3);
		} catch (IOException e) {
		}
		return str3;
	}

	
	
	public static void b(String str, String str2, String str3) {
		Properties properties = new Properties();
		try {
			File file = new File(FileUtil.externalpath + str);
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			InputStream fileInputStream = new FileInputStream(file);
			properties.load(fileInputStream);
			fileInputStream.close();
			properties.put(str2, str3);
			OutputStream fileOutputStream = new FileOutputStream(file);
			properties.store(fileOutputStream, null);
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
