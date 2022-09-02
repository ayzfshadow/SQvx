package com.saki.util;

import com.saki.http.HTTP;
import com.saki.loger.ViewLoger;

public class FileLoader {
    public static byte[] load(String str) {
        try {
           return new HTTP().httpGet(str, true, 3000, 10000, new String[0]);
        } catch (Exception e) {
            ViewLoger.erro("加载网络文件失败: "+e.getMessage());
            e.printStackTrace();
            return new byte[0];
        }
    }
}
