package com.saki.plugin.Dictionary;

import android.content.Context;
import com.a.a.classu;
import com.a.a.classw;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;

public class DicFileTool {
    public static void a(Context context, classd classd, DicStream classe, DicStream classe2, boolean z) {
        HashMap<String, String[]> b = classe2.b();
        int i = 0;
        for (String str : b.keySet()) {
            int i2 = i + 1;
            if (z) {
                classe.b().put(str, b.get(str));
                classd.a(i2, true);
            } else {
                classd.a(i2, classe.a(str, (String[]) b.get(str)));
            }
            i = i2;
        }
        try {
            classe.b(classe.dicfile, b(context));
        } catch (IOException e) {
            classd.a("合并完成！保存失败!错误信息:" + e.toString());
        }
        classd.a();
    }

	public static void a_extra(Context context, classd classd, DicStream classe, DicStream classe2, boolean z) {
        HashMap<String, String[]> b = classe2.b();
        int i = 0;
        for (String str : b.keySet()) {
            int i2 = i + 1;
            if (z) {
                classe.b().put(str, b.get(str));
                classd.a(i2, true);
            } else {
                classd.a(i2, classe.a(str, (String[]) b.get(str)));
            }
            i = i2;
        }
        try {
            classe.b(classe.dicfile_extra, b(context));
        } catch (IOException e) {
            classd.a("合并完成！保存失败!错误信息:" + e.toString());
        }
        classd.a();
    }
	
    public static void importDic(Context context, classd classd, String str) {
        classd.a("正在删除dic:" + new File(com.saki.util.FileUtil.externalpath + "DictionaryV8/dic.txt").delete());
        importDic(context, classd, str, true);
    }

	public static void importExtraDic(Context context, classd classd, String str) {
        classd.a("正在删除dic:" + new File(com.saki.util.FileUtil.externalpath + "DictionaryV8/dic_extra.txt").delete());
        importDic(context, classd, str, true);
    }
	
    public static void importDic(final Context context, final classd classd, final String str, final boolean z) {
        new Thread(new Runnable() {
				public void run() {
					String iOException;
					StackTraceElement[] stackTrace;
					int i = 0;
					File file = new File(str);
					if (file.exists()) {
						int read;
						classd.a("正在加载旧dic....");
						DicStream classe = new DicStream();
						try {
							classe.loadFile(DicFileTool.b(context));
						} catch (IOException e) {
							//iOException = e.toString();
							//for (StackTraceElement stackTraceElement : e.getStackTrace()) {
								//iOException = iOException + "\n" + stackTraceElement.toString();
							//}
							//classd.a("加载失败!错误信息:" + iOException);
						}
						classd.a("加载完成:共加载" + classe.c() + "条词库!");
						if (file.getName().endsWith(".txt")) {
							classd.a("开始读取新词库...");
							DicStream classe2 = new DicStream();
							try {
								classe2.loadFile(file, DicFileTool.b(context));
								classd.a("加载成功:共加载" + classe2.c() + "条词库!\n——————开始合并——————");
								DicFileTool.a(context, classd, classe, classe2, z);
								return;
							} catch (IOException e2) {
								iOException = e2.toString();
								stackTrace = e2.getStackTrace();
								String str = iOException;
								while (i < stackTrace.length) {
									str = str + "\n" + stackTrace[i].toString();
									i++;
								}
								classd.a("加载失败!错误信息:" + str);
								return;
							}
						}
						classd.a("正在解压...");
						try {
							classw com_a_a_classw = new classw(file, "GBK");
							Enumeration b = com_a_a_classw.b();
							while (b.hasMoreElements()) {
								classu com_a_a_classu = (classu) b.nextElement();
								if (com_a_a_classu.getName().contains("SakiDictionary")) {
									if (com_a_a_classu.isDirectory()) {
										File file2 = new File(com.saki.util.FileUtil.externalpath + com_a_a_classu.getName());
										if (file2.exists()) {
											continue;
										} else {
											classd.a("创建目录:" + file2.getAbsolutePath() + (file2.mkdirs() ? "[成功]" : "[失败]"));
										}
									} else if (com_a_a_classu.getName().contains("/dic.txt")) {
										classd.a("发现压缩dic文件:" + com_a_a_classu.getName() + "\n开始载入...");
										InputStream r0 = com_a_a_classw.a(com_a_a_classu);
										DicStream classe3 = new DicStream();
										classe3.loaddic(r0,DicFileTool.b(context));
										classd.a("载入新dic:" + classe3.c() + "条,正在合并...");
										DicFileTool.a(context, classd, classe, classe3, z);
									} else {
										classd.a("正在解压文件:" + com_a_a_classu.getName());
										File file3 = new File(com.saki.util.FileUtil.externalpath + com_a_a_classu.getName());
										if (!file3.exists()) {
											classd.a("创建文件:" + (file3.createNewFile() ? "[成功]" : "[失败]"));
										}
										FileOutputStream fileOutputStream = new FileOutputStream(file3);
										InputStream	r0 = com_a_a_classw.a(com_a_a_classu);
										while (true) {
											read = r0.read();
											if (read == -1) {
												break;
											}
											fileOutputStream.write(read);
										}
										fileOutputStream.flush();
										fileOutputStream.close();
										r0.close();
										classd.a("解压完成!");
									}
								}
							}
							return;
						} catch (IOException e22) {
							iOException = e22.toString();
							stackTrace = e22.getStackTrace();
							String str2 = iOException;
							for (StackTraceElement stackTraceElement2 : stackTrace) {
								str2 = str2 + "\n" + stackTraceElement2.toString();
							}
							classd.a("解压失败!错误信息:" + str2);
							return;
						}
					}
					classd.a("需要合并的词库文件不存在:" + str);
				}
			
			
			
			
            public void fakerun() {
                File file = new File(str);
                if (!file.exists()) {
                    classd.a("需要合并的词库文件不存在:" + str);
                    return;
                }
			
                classd.a("正在加载旧dic....");
                DicStream classe = new DicStream();
                try {
                    classe.loadFile(DicFileTool.b(context));
                } catch (IOException e) {
                    String iOException = e.toString();
                    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                        iOException = iOException + "\n" + stackTraceElement.toString();
                    }
                    classd.a("加载失败!错误信息:" + iOException);
                }
                classd.a("加载完成:共加载" + classe.c() + "条词库!");
				
                if (file.getName().endsWith(".txt")) {
                    classd.a("开始读取新词库...");
                    DicStream classe2 = new DicStream();
                    try {
                        classe2.loadFile(file, DicFileTool.b(context));
                        classd.a("加载成功:共加载" + classe2.c() + "条词库!\n——————开始合并——————");
                        DicFileTool.a(context, classd, classe, classe2, z);
                    } catch (IOException e2) {
                        String iOException2 = e2.toString();
                        StackTraceElement[] stackTrace = e2.getStackTrace();
                        String str = iOException2;
                        for (StackTraceElement stackTraceElement2 : stackTrace) {
                            str = str + "\n" + stackTraceElement2.toString();
                        }
                        classd.a("加载失败!错误信息:" + str);
                    }
                } else {
                    classd.a("正在解压...");
                    try {
                        classw classw = new classw(file, "GBK");
                        Enumeration b2 = classw.b();
                        while (b2.hasMoreElements()) {
                            classu classu = (classu) b2.nextElement();
                            if (classu.getName().contains("SakiDictionary")) {
                                if (classu.isDirectory()) {
                                    File file2 = new File(com.saki.util.FileUtil.externalpath + classu.getName());
                                    if (!file2.exists()) {
                                        classd.a("创建目录:" + file2.getAbsolutePath() + (file2.mkdirs() ? "[成功]" : "[失败]"));
                                    }
                                } else if (classu.getName().contains("/dic.txt")) {
                                    classd.a("发现压缩dic文件:" + classu.getName() + "\n开始载入...");
                                    InputStream a2 = classw.a(classu);
                                    DicStream classe3 = new DicStream();
                                    classe3.loaddic(a2, DicFileTool.b(context));
                                    classd.a("载入新dic:" + classe3.c() + "条,正在合并...");
                                    DicFileTool.a(context, classd, classe, classe3, z);
                                } else {
                                    classd.a("正在解压文件:" + classu.getName());
                                    File file3 = new File(com.saki.util.FileUtil.externalpath + classu.getName());
                                    if (!file3.exists()) {
                                        classd.a("创建文件:" + (file3.createNewFile() ? "[成功]" : "[失败]"));
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                                    InputStream a3 = classw.a(classu);
                                    while (true) {
                                        int read = a3.read();
                                        if (read == -1) {
                                            break;
                                        }
                                        fileOutputStream.write(read);
                                    }
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    a3.close();
                                    classd.a("解压完成!");
                                }
                            }
                        }
                    } catch (IOException e3) {
                        String iOException3 = e3.toString();
                        StackTraceElement[] stackTrace2 = e3.getStackTrace();
                        String str2 = iOException3;
                        for (StackTraceElement stackTraceElement3 : stackTrace2) {
                            str2 = str2 + "\n" + stackTraceElement3.toString();
                        }
                        classd.a("解压失败!错误信息:" + str2);
                    }
                }
            }
        }).start();
    }

	
	
	public static void importExtraDic(final Context context, final classd classd, final String str, final boolean z) {
        new Thread(new Runnable() {
				public void run() {
					String iOException;
					StackTraceElement[] stackTrace;
					int i = 0;
					File file = new File(str);
					if (file.exists()) {
						int read;
						classd.a("正在加载旧dic....");
						DicStream classe = new DicStream();
						try {
							classe.loadFile_extra(DicFileTool.b(context));
						} catch (IOException e) {
							//iOException = e.toString();
							//for (StackTraceElement stackTraceElement : e.getStackTrace()) {
							//iOException = iOException + "\n" + stackTraceElement.toString();
							//}
							//classd.a("加载失败!错误信息:" + iOException);
						}
						classd.a("加载完成:共加载" + classe.c() + "条词库!");
						if (file.getName().endsWith(".txt")) {
							classd.a("开始读取新词库...");
							DicStream classe2 = new DicStream();
							try {
								classe2.loadFile(file, DicFileTool.b(context));
								classd.a("加载成功:共加载" + classe2.c() + "条词库!\n——————开始合并——————");
								DicFileTool.a_extra(context, classd, classe, classe2, z);
								return;
							} catch (IOException e2) {
								iOException = e2.toString();
								stackTrace = e2.getStackTrace();
								String str = iOException;
								while (i < stackTrace.length) {
									str = str + "\n" + stackTrace[i].toString();
									i++;
								}
								classd.a("加载失败!错误信息:" + str);
								return;
							}
						}
						classd.a("正在解压...");
						try {
							classw com_a_a_classw = new classw(file, "GBK");
							Enumeration b = com_a_a_classw.b();
							while (b.hasMoreElements()) {
								classu com_a_a_classu = (classu) b.nextElement();
								if (com_a_a_classu.getName().contains("SakiDictionary")) {
									if (com_a_a_classu.isDirectory()) {
										File file2 = new File(com.saki.util.FileUtil.externalpath + com_a_a_classu.getName());
										if (file2.exists()) {
											continue;
										} else {
											classd.a("创建目录:" + file2.getAbsolutePath() + (file2.mkdirs() ? "[成功]" : "[失败]"));
										}
									} else if (com_a_a_classu.getName().contains("/dic.txt")) {
										classd.a("发现压缩dic文件:" + com_a_a_classu.getName() + "\n开始载入...");
										InputStream r0 = com_a_a_classw.a(com_a_a_classu);
										DicStream classe3 = new DicStream();
										classe3.loaddic(r0,DicFileTool.b(context));
										classd.a("载入新dic:" + classe3.c() + "条,正在合并...");
										DicFileTool.a_extra(context, classd, classe, classe3, z);
									} else {
										classd.a("正在解压文件:" + com_a_a_classu.getName());
										File file3 = new File(com.saki.util.FileUtil.externalpath + com_a_a_classu.getName());
										if (!file3.exists()) {
											classd.a("创建文件:" + (file3.createNewFile() ? "[成功]" : "[失败]"));
										}
										FileOutputStream fileOutputStream = new FileOutputStream(file3);
										InputStream	r0 = com_a_a_classw.a(com_a_a_classu);
										while (true) {
											read = r0.read();
											if (read == -1) {
												break;
											}
											fileOutputStream.write(read);
										}
										fileOutputStream.flush();
										fileOutputStream.close();
										r0.close();
										classd.a("解压完成!");
									}
								}
							}
							return;
						} catch (IOException e22) {
							iOException = e22.toString();
							stackTrace = e22.getStackTrace();
							String str2 = iOException;
							for (StackTraceElement stackTraceElement2 : stackTrace) {
								str2 = str2 + "\n" + stackTraceElement2.toString();
							}
							classd.a("解压失败!错误信息:" + str2);
							return;
						}
					}
					classd.a("需要合并的词库文件不存在:" + str);
				}




				public void fakerun() {
					File file = new File(str);
					if (!file.exists()) {
						classd.a("需要合并的词库文件不存在:" + str);
						return;
					}

					classd.a("正在加载旧dic....");
					DicStream classe = new DicStream();
					try {
						classe.loadFile(DicFileTool.b(context));
					} catch (IOException e) {
						String iOException = e.toString();
						for (StackTraceElement stackTraceElement : e.getStackTrace()) {
							iOException = iOException + "\n" + stackTraceElement.toString();
						}
						classd.a("加载失败!错误信息:" + iOException);
					}
					classd.a("加载完成:共加载" + classe.c() + "条词库!");

					if (file.getName().endsWith(".txt")) {
						classd.a("开始读取新词库...");
						DicStream classe2 = new DicStream();
						try {
							classe2.loadFile(file, DicFileTool.b(context));
							classd.a("加载成功:共加载" + classe2.c() + "条词库!\n——————开始合并——————");
							DicFileTool.a_extra(context, classd, classe, classe2, z);
						} catch (IOException e2) {
							String iOException2 = e2.toString();
							StackTraceElement[] stackTrace = e2.getStackTrace();
							String str = iOException2;
							for (StackTraceElement stackTraceElement2 : stackTrace) {
								str = str + "\n" + stackTraceElement2.toString();
							}
							classd.a("加载失败!错误信息:" + str);
						}
					} else {
						classd.a("正在解压...");
						try {
							classw classw = new classw(file, "GBK");
							Enumeration b2 = classw.b();
							while (b2.hasMoreElements()) {
								classu classu = (classu) b2.nextElement();
								if (classu.getName().contains("SakiDictionary")) {
									if (classu.isDirectory()) {
										File file2 = new File(com.saki.util.FileUtil.externalpath + classu.getName());
										if (!file2.exists()) {
											classd.a("创建目录:" + file2.getAbsolutePath() + (file2.mkdirs() ? "[成功]" : "[失败]"));
										}
									} else if (classu.getName().contains("/dic.txt")) {
										classd.a("发现压缩dic文件:" + classu.getName() + "\n开始载入...");
										InputStream a2 = classw.a(classu);
										DicStream classe3 = new DicStream();
										classe3.loaddic(a2, DicFileTool.b(context));
										classd.a("载入新dic:" + classe3.c() + "条,正在合并...");
										DicFileTool.a_extra(context, classd, classe, classe3, z);
									} else {
										classd.a("正在解压文件:" + classu.getName());
										File file3 = new File(com.saki.util.FileUtil.externalpath + classu.getName());
										if (!file3.exists()) {
											classd.a("创建文件:" + (file3.createNewFile() ? "[成功]" : "[失败]"));
										}
										FileOutputStream fileOutputStream = new FileOutputStream(file3);
										InputStream a3 = classw.a(classu);
										while (true) {
											int read = a3.read();
											if (read == -1) {
												break;
											}
											fileOutputStream.write(read);
										}
										fileOutputStream.flush();
										fileOutputStream.close();
										a3.close();
										classd.a("解压完成!");
									}
								}
							}
						} catch (IOException e3) {
							String iOException3 = e3.toString();
							StackTraceElement[] stackTrace2 = e3.getStackTrace();
							String str2 = iOException3;
							for (StackTraceElement stackTraceElement3 : stackTrace2) {
								str2 = str2 + "\n" + stackTraceElement3.toString();
							}
							classd.a("解压失败!错误信息:" + str2);
						}
					}
				}
			}).start();
    }
	
	
    /* access modifiers changed from: private */
    public static String b(Context context) {
        return context.getSharedPreferences("dictionary", 0).getString("charset", "UTF-8");
    }
}
