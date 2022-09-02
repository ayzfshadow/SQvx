package com.saki.loger;

import java.util.ArrayList;

public class ViewLoger {
    public static ArrayList<String> a = new ArrayList<>();
    private static com.saki.ui.LogHandler handler;

    public static void closeLoger() {
        handler = null;
    }

    private static void log(int type, Object data) {
        if (handler != null) {
            handler.call(type, data);
        }
    }

    public static void setHandler(com.saki.ui.LogHandler classb) {
        handler = classb;
    }

    public static void erro(Object obj) {
        log(2, obj);
    }

    public static void info(Object obj) {
        log(0, obj);
    }

    public static void warnning(Object obj) {
        log(1, obj);
    }
}
