//
// Decompiled by Procyon - 2468ms
//
package com.saki.plugin.Dictionary;

import com.saki.util.FileUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.io.*;

public class DicStream {
	public static File dicfile = new File(FileUtil.externalpath + "DictionaryV8/dic.txt");
	
	public static File dicfile_extra =new File(FileUtil.externalpath + "DictionaryV8/dic_extra.txt");
	
	LinkedHashMap<String, String[]> b;

	

	public DicStream() {
		this.b = new LinkedHashMap<String, String[]>();
	}

	public void a() {
		final LinkedHashMap<String, String[]> b = new LinkedHashMap<String, String[]>();
		for (final String s : this.b.keySet()) {
			final String[] array = this.b.get(s);
			String string = "";
			for (int i = 0; i < array.length; ++i) {
				final StringBuilder append = new StringBuilder().append(string).append(array[i]);
				String s2;
				if (i == array.length - 1) {
					s2 = "";
				}
				else {
					s2 = "\n";
				}
				string = append.append(s2).toString();
			}
			b.put(s, new String[] { DicFileLoader.b(string).replace(" ", "") });
		}
		this.b = b;
	}

	public void loadFile(final File file, final String s) throws IOException {
		this.loaddic(new FileInputStream(file), s);
	} 

	public void loaddic(final InputStream inputStream, final String s) throws IOException {
		final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, s));
		while (true) {
			final String line = bufferedReader.readLine();
			if (line == null) {
				break;
			}
			if (line.indexOf("##") == 0 || line.equals("")) {
				continue;
			}
			final ArrayList<String> list = new ArrayList<String>();
			while (true) {
				final String line2 = bufferedReader.readLine();
				if (line2 == null || line2.equals("")) {
					break;
				}
				list.add(line2);
			}
			this.b.put(line, list.toArray(new String[0]));
		}
		bufferedReader.close();
	}

	public void loadFile(final String s) throws IOException {
		this.loadFile(DicStream.dicfile, s);
	}
	
	public void loadFile_extra(final String s) throws IOException {
		this.loadFile(DicStream.dicfile_extra, s);
	}

	public boolean a(final String s, final String[] array) {
		boolean b;
		if (this.b.containsKey(s)) {
			b = false;
		}
		else {
			this.b.put(s, array);
			b = true;
		}
		return b;
	}

	public HashMap<String, String[]> b() {
		return this.b;
	}

	public void b(final File file, final String s) throws IOException {
		if (this.b.size() != 0) {
			final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), s));
			bufferedWriter.write("");
			for (final String s2 : this.b.keySet()) {
				bufferedWriter.append((CharSequence)s2);
				bufferedWriter.newLine();
				final String[] array = this.b.get(s2);
				for (int length = array.length, i = 0; i < length; ++i) {
					bufferedWriter.append((CharSequence)array[i]);
					bufferedWriter.newLine();
				}
				bufferedWriter.newLine();
			}
			bufferedWriter.flush();
			bufferedWriter.close();
		}
	}

	public int c() {
		return this.b.size();
	}
}

