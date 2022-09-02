package com.saki.qq.userinfo;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
    public static long uin;
    public static String b = "";
    public static a c = new a();
    public static HashMap<Long,Boolean> passreqcache = new HashMap<Long,Boolean>();
    public static long disreq;
    public static String imei ="";
    public static String devicecompany ="";
    public static String devicemode="";

    public static boolean enablesilkencode=false;

    public static boolean switch_enableallgroupautomatically;

    public static boolean newthread =false;

    public static boolean switch_enableprivatereply;

    public static boolean enablediscuss;
    

    public static class a {
        byte[] a;

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (byte valueOf : this.a) {
                sb.append(String.format("%02x", new Object[]{Byte.valueOf(valueOf)}));
            }
            return sb.toString();
        }
    }
}
