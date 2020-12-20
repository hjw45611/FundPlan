-optimizationpasses 5          # 指定代码的压缩级别
-dontpreverify           # 混淆时是否做预校验
-verbose                # 混淆时是否记录日志

-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*  # 混淆时所采用的算法

-keep public class * extends android.app.Activity      # 保持哪些类不被混淆
-keep public class * extends android.app.Application   # 保持哪些类不被混淆
-keep public class * extends android.app.Service       # 保持哪些类不被混淆
-keep public class * extends android.content.BroadcastReceiver  # 保持哪些类不被混淆
-keep public class * extends android.content.ContentProvider    # 保持哪些类不被混淆
-keep public class * extends android.app.backup.BackupAgentHelper # 保持哪些类不被混淆
-keep public class * extends android.preference.Preference        # 保持哪些类不被混淆
-keep public class com.android.vending.licensing.ILicensingService    # 保持哪些类不被混淆

-ignorewarnings
-keepattributes Signature
#保留源文件名及行号
-keepattributes SourceFile,LineNumberTable

# bugly
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}

# gson
-keep class com.google.gson.** { *; }
-keep class com.google.gson.stream.** { *; }

# okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}

# okio
-dontwarn okio.**
-keep class okio.**{*;}

# bdmap
-dontwarn com.baidu.location.**
-keep class com.baidu.**{*;}
-keep class com.baidu.mapapi.**{*;}
-keep class vi.com.gdi.bgl.android.**{*;}
-keep class com.baidubce.**{*;}

# glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

# for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# eventbus
-keepattributes *Annotation*
-keepclassmembers class ** {
    @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }
# Only required if you use AsyncExecutor
-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
    <init>(Java.lang.Throwable);
}

# mdm
#-ignorewarning
-keepattributes Signature
-keep class * implements android.os.IInterface {*;}
-keep enum com.android.mobileSec.DevCtrl.DevType{*;}
-keep class com.android.mobileSec.**{*;}
-keep class com.android.mobileSec.control.**{*;}
-keep class com.tdtech.devicemanager.**{*;}
-keep class com.huawei.**{*;}
-keep class com.thundersoft.**{*;}
-keep class com.pekall.enterprise.**{*;}
-keep class com.tdtech.devicemanager.**{*;}
-keep class lte.trunk.devicemanager.**{*;}
-dontwarn com.huawei.updatesdk.**
-dontwarn com.pekall.enterprise.**
-dontwarn com.tdtech.devicemanager.**
-dontwarn lte.trunk.devicemanager.**
-dontwarn com.skr.library.network.**
-dontwarn com.tdtech.devicemanager.**
-dontwarn com.android.mdmservice.**

-keep class com.mdm.online.event.**{*;}
-keep class com.mdm.online.message.event.**{*;}
-keep class com.mdm.online.message.bean.**{*;}
-keep class com.mdm.online.model.bean.**{*;}
-keep class com.mdm.online.model.local.db.bean.**{*;}
-keep class com.mdm.online.model.local.sp.**{*;}
-keep class com.mdm.online.model.remote.http.resp.**{*;}
-keep class com.mdm.online.model.remote.callback.SimpleCallback{*;}
-keep class com.skr.library.bean.**{*;}
-keep class com.skr.activation.bean.**{*;}
# 保持证书库不被混淆。
-keep class com.mdm.online.model.local.crypto.**{*;}
# sqlcipher
-keep class com.skr.room.**{*;}
-keep class com.mdm.online.model.**{*;}
-keep class net.sqlcipher.**{*;}
#保持某个类不被混淆
-keep class com.mdm.online.accessibilty.DpmOwnerHelper{*;}
-keep class com.mdm.online.utils.MiddleWare{*;}
-keep class com.mdm.online.ctrl.impl.hw.**{*;}
-keep class com.secspace.mdmengine.api.**{*;}
-keep class android.content.pm.**{*;}
#update-excludePush（App升级相关）
-keep class com.skr.downloader.**{*;}
#双系统接口ga.jar
-keep class ga.mdm.**{*;}
-keep class com.oppo.**{*;}

# retrofit
-keep class retrofit2.**{*;}
-keepattributes Signature
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**

-keepclasseswithmembernames class * {   # 保持 native 方法不被混淆
    native <methods>;
}
-keepclasseswithmembers class * {       # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {       # 保持自定义控件类不被混淆
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
    public void *(android.view.View);
}
-keepclassmembers enum * {              # 保持枚举 enum 类不被混淆
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable { # 保持 Parcelable 不被混淆
    public static final android.os.Parcelable$Creator *;
}

#-assumenosideeffects class android.util.Log {#去掉log日志
#    public static boolean isLoggable(java.lang.String,int);
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#}

#阿里热修复
#基线包使用，生成mapping.txt
#-printmapping mapping.txt
#生成的mapping.txt在app/build/outputs/mapping/release路径下，移动到/app路径下
#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
#hotfix
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
-dontwarn com.alibaba.sdk.android.utils.**
#防止inline
-dontoptimize

-keepclassmembers class com.mdm.online.App {
    public <init>();
}
# 如果不使用android.support.annotation.Keep则需加上此行
 -keep class com.mdm.online.SophixStubApplication$RealApplicationStub
#router混淆
-keep public class com.alibaba.android.arouter.routes.**{*;}
-keep public class com.alibaba.android.arouter.facade.**{*;}
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}

# 如果使用了 byType 的方式获取 Service，需添加下面规则，保护接口
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider

# 如果使用了 单类注入，即不定义接口实现 IProvider，需添加下面规则，保护实现
# -keep class * implements com.alibaba.android.arouter.facade.template.IProvider

#华为push
-keepattributes *Annotation*
-keepattributes Exceptions
-keepattributes InnerClasses
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keep class com.hianalytics.android.**{*;}
-keep class com.huawei.updatesdk.**{*;}
-keep class com.huawei.hms.**{*;}

# keep annotated by NotProguard 混淆配置文件中声明
-keep @com.mdm.online.anno.NotProguard class * {*;}
-keep class * {
@com.mdm.online.anno.NotProguard <fields>;
}
-keepclassmembers class * {
@com.mdm.online.anno.NotProguard <methods>;
}

# 高德定位
-keep class com.amap.api.location.**{*;}
-keep class com.amap.api.fence.**{*;}
-keep class com.loc.**{*;}
-keep class com.autonavi.aps.amapapi.model.**{*;}