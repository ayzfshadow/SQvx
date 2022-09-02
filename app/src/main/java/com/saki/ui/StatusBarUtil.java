//
// Decompiled by Jadx - 725ms
//
package com.saki.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;

public class StatusBarUtil {
	public static void setTransparent(Activity activity) {
		Activity activity2 = activity;
		if (VERSION.SDK_INT >= 19) {
			transparentStatusBar(activity2);
			setRootView(activity2);
		}
	}

	@TargetApi(19)
	private static void transparentStatusBar(Activity activity) {
		Activity activity2 = activity;
		if (VERSION.SDK_INT >= 21) {
			activity2.getWindow().addFlags(Integer.MIN_VALUE);
			activity2.getWindow().clearFlags(0x04000000);
			activity2.getWindow().addFlags(0x08000000);
			activity2.getWindow().setStatusBarColor(0);
			return;
		}
		activity2.getWindow().addFlags(0x04000000);
	}

	private static void setRootView(Activity activity) {
		ViewGroup viewGroup = (ViewGroup) activity.findViewById(0x01020002);
		int childCount = viewGroup.getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childAt = viewGroup.getChildAt(i);
			if (childAt instanceof ViewGroup) {
				childAt.setFitsSystemWindows(true);
				((ViewGroup) childAt).setClipToPadding(true);
			}
		}
	}

	public StatusBarUtil() {
		StatusBarUtil statusBarUtil = this;
	}
}
