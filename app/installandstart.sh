#!/bin/bash
adb install build/outputs/apk/$1/app-$1.apk
adb shell am start -n com.ayzf.sqvx/com.saki.ui.NewLoginActivity
adb logcat  --pid=$(adb shell ps -o PID,name|grep com.ayzf.sqvx|head -n 1|grep -o '[0-9]*')

