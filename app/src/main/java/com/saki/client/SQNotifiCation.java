package com.saki.client;

import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import com.saki.ui.PagerActivity;
import com.ayzf.sqvx.R;


public class SQNotifiCation {
    Notification a;
    Service b;

    public SQNotifiCation(Service service) {
        Builder builder;
        this.b = service;
        Intent intent = new Intent(service, PagerActivity.class);
        intent.setFlags(536870912);
        PendingIntent activity = PendingIntent.getActivity(service, 0, intent, 0);
        if (VERSION.SDK_INT >= 26) {
            String str = "setqq.com.saki.com";
            String string = service.getString(R.string.AppName);
            Builder builder2 = new Builder(service, str);
            NotificationChannel notificationChannel = new NotificationChannel(str, string, 4);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(SupportMenu.CATEGORY_MASK);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(1);
            ((NotificationManager) service.getSystemService("notification")).createNotificationChannel(notificationChannel);
            builder = builder2;
        } else {
            builder = new Builder(service);
        }
        builder.setContentIntent(activity);
        builder.setSmallIcon(R.mipmap.mipmap0001);
        builder.setContentTitle(service.getResources().getString(R.string.AppName));
        this.a = builder.setContentText("后台运行中").build();
        this.a.flags |= 32;
    }

    public void start() {
        this.b.startForeground(3028, this.a);
    }

    public void stop() {
        this.b.stopForeground(true);
    }
}
