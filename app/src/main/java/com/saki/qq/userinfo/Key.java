package com.saki.qq.userinfo;

public class Key {
    public static final byte[] a = new byte[16];
    
    public static byte[] c = a;
    private static byte[] d = a;

	//public static byte[] rigister;

    public static void a(byte[] bArr) {
        d = bArr;
    }

    public static byte[] a() {
        return d;
    }
}
