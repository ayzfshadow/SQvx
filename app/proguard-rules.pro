
#不要压缩(这个必须，因为开启混淆的时候 默认 会把没有被调用的代码 全都排除掉)
#-dontshrink

#保护泛型 如果混淆报错建议关掉
#-keepattributes Signature
-keepattributes LineNumberTable



-dontwarn *.**
-keep class com.mcsqnxa.** {<init>(...);}
-keep class org.** {*;}
-keep class java.util.zip.ZipFile {*;}
-keep class com.zly.media.silk.** {*;}
-keep class com.eclipsesource.** {*;}
-keep class com.setqq.script.Msg {*;}
-keep class com.setqq.script.sdk.ApiAdapter {*;}
-keep class com.setqq.script.sdk.IPlugin {*;}
-keep class com.setqq.script.sdk.PluginApiInterface {*;}
