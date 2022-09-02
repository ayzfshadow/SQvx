package com.saki.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import java.io.File;

public class ApkUtil {
    private static void a(Context context) {
        context.startActivity(new Intent("android.settings.MANAGE_UNKNOWN_APP_SOURCES", Uri.parse("package:" + context.getPackageName())));
    }

    public static void installApk(Context context, File file) {
        Intent intent = new Intent("android.intent.action.VIEW");
        if (VERSION.SDK_INT >= 24) {
            intent.setFlags(1);
            intent.setDataAndType(FileProvider.getUriForFile(context, "com.ayzf.sqvx.fileprovider", file), "application/vnd.android.package-archive");
            if (VERSION.SDK_INT >= 26 && !context.getPackageManager().canRequestPackageInstalls()) {
                a(context);
                return;
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(268435456);
        }
        if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
            context.startActivity(intent);
        }
    }
}
