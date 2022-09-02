//
// Decompiled by Jadx - 1139ms
//
package com.saki.plugin.Dictionary;

import com.saki.tool.HexTool;
import com.saki.tool.Code;
import com.saki.util.FileUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Random;
import java.util.*;
import android.util.*;
import com.saki.tool.TeaCryptor;

public class DicFileLoader {
	private File dicfile ;
	
	
	private boolean isEnabled = false;
	private String c;
	private HashMap<String, String[]> d = new HashMap<String, String[]> ();

	
	public DicFileLoader(File file){
		this.dicfile=file;
	}
	public static String a(String var0) {
		byte[] var4 = HexTool.hextobytes(var0);
		byte[] var1 = new byte[16];
		byte[] var2 = new byte[4];
		System.arraycopy(var4, 0, var2, 0, 4);
		byte[] var3 = new byte[var4.length - 4];
		System.arraycopy(var4, 4, var3, 0, var3.length);
		(new Random(HexTool.Bytes2UnsignedInt32(var2))).nextBytes(var1);
        TeaCryptor cry=new TeaCryptor();
		byte[] k =cry.decrypt(var3, var1);
        return new String(k);
	}
	

	public static String b(String str) {
		byte[] bytes = str.getBytes();
		byte[] bArr = new byte[16];
		long a = HexTool.a(new Random().nextLong());
		new Random(a).nextBytes(bArr);
		return HexTool.a(HexTool.a(a, 4), "") + HexTool.a(new TeaCryptor().encrypt(bytes, bArr), "");
	}

	public BufferedReader a(String str, int i, String str2) {
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(dicfile), str2));
			if (bufferedReader != null) {
				while (true) {
					String readLine = bufferedReader.readLine();
					if (readLine == null) {
						break;
					} else if (!(readLine.equals("") || readLine.indexOf("##") == 0)) {
						if (readLine.indexOf("[友]") == 0 && i != 1) {
							a(bufferedReader, false);
						} else if (readLine.indexOf("[群]") == 0 && i != 0) {
							a(bufferedReader, false);
						} else if (str.matches(readLine.replace("[友]", "").replace("[群]", ""))) {
							this.c = readLine;
							return bufferedReader;
						} else {
							a(bufferedReader, false);
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			com.saki.loger.ViewLoger.erro("无法打开并读取dic:" + e.toString());
		}
		return null;
	}

	public void setEnabled(boolean z) {
		this.isEnabled = z;
	}

	public boolean existorcreate() {
		if (dicfile.exists()) {
			return true;
		}
		if (!dicfile.getParentFile().exists()) {
			dicfile.getParentFile().mkdirs();
		}
		try {
			return dicfile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/* JADX WARNING: inconsistent code. */
	/* Code decompiled incorrectly, please refer to instructions dump. */
	public String[] a(BufferedReader arrstring, boolean bl) {
		List<String> arrayList = new ArrayList<String>();
		try {
			String[] datatoreturn = null;
			String arrstring3 ;
			while ((arrstring3 = arrstring.readLine()) != null && !arrstring3.equals("")) {
				
				if (arrstring3.indexOf("##") == 0 || arrstring3.indexOf("//") == 0) continue;
				if (arrstring3.matches("^[0-9a-fA-F]+$")) {
					arrstring = null;
					if (!bl) return datatoreturn;
					return a(arrstring3).split("\n");
				}
				arrayList.add(arrstring3);
			}
			datatoreturn =  arrayList.toArray(new String[arrayList.size()]);
			if (!this.isEnabled) return datatoreturn;
			if (this.c == null) return datatoreturn;
			this.d.put(this.c, datatoreturn);
			return datatoreturn;
		}
		catch (IOException iOException) {
			iOException.printStackTrace();
			return new String[0];
		}
	}
	

	public boolean b() {
		return this.isEnabled;
	}

	public String[] getDicIntentByMsg(String str) {
		for (String str2 : this.d.keySet()) {
			if (str2 != null && str.matches(str2)) {
				return (String[]) this.d.get(str2);
			}
		}
		return null;
	}
}
