# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

  # 过滤R文件的混淆：
-keep class **.R$* {*;}
###-----------基本指令--------------------
###-----------指定代码的压缩级别------------
-optimizationpasses 5
###-----------是否使用大小写混合------------
-dontusemixedcaseclassnames
###-----------混淆时是否做预校验------------
-dontpreverify
###-----------混淆时是否记录日志------------
-verbose
###-----------忽略警告------------
-ignorewarnings
-keepattributes EnclosingMethod

###-----------保证异常时显示行号------------
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

 #dump文件列出apk包内所有class的内部结构
-dump class_files.txt

#seeds.txt文件列出未混淆的类和成员
-printseeds seeds.txt

#usage.txt文件列出从apk中删除的代码
-printusage unused.txt

#mapping文件列出混淆前后的映射
-printmapping mapping.txt

###-----------注解------------
-keepattributes *Annotation*

###-----------泛型------------
-keepattributes Signature

###-----------异常------------
-keepattributes Exceptions

###-----------混淆时所采用的算法------------
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
###-----------避免混淆Android基本组件的配置--------------------
###-----------保持Activity类不被混淆------------
-keep public class * extends android.app.Activity
###-----------保持AppCompatActivity类不被混淆------------
-keep public class * extends android.support.v7.app.AppCompatActivity
###-----------保持DialogFragment类不被混淆------------
-keep public class * extends android.app.DialogFragment
###-----------保持Application类不被混淆------------
-keep public class * extends android.app.Application
###-----------保持Service类不被混淆------------
-keep public class * extends android.app.Service
###-----------保持BroadcastReceiver类不被混淆------------
-keep public class * extends android.content.BroadcastReceiver
###-----------保持ContentProvider类不被混淆------------
-keep public class * extends android.content.ContentProvider
###-----------保持BackupAgentHelper类不被混淆------------
-keep public class * extends android.app.backup.BackupAgentHelper
###-----------保持Preference类不被混淆------------
-keep public class * extends android.preference.Preference
###-----------保持ILicensingService类不被混淆------------
-keep public class com.android.vending.licensing.ILicensingService

###-----------保持 native 方法不被混淆------------
-keepclasseswithmembernames class * {
    native <methods>;
}

###-----------保持自定义控件类不被混淆------------
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

###-----------保持自定义控件类不被混淆------------
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

###-----------保持自定义控件类不被混淆------------
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

###-----------保持枚举 enum 类不被混淆------------
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

###-----------# 保持 Parcelable 不被混淆------------
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}


-keepclasseswithmembernames class * {                                           # 保持 native 方法不被混淆
    native <methods>;
}

-keepclasseswithmembers class * {                                               # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);     # 保持自定义控件类不被混淆
}

-keepclassmembers class * extends android.app.Activity {                        # 保持自定义控件类不被混淆
   public void *(android.view.View);
}

-keepclassmembers enum * {                                                      # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep public class com.dh.clickevent.type.** {*;}
-keep public class com.dh.clickevent.bean.* {*;}
-keep public class com.dh.clickevent.impl.* {*;}
-keep public class com.dh.clickevent.factory.ClickEventsFactory {*;}
-keep public class com.dh.clickevent.ClickEventsManager {*;}

