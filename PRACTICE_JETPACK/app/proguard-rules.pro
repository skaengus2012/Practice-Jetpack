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

-keep class * extends androidx.lifecycle.ViewModel { *; }
-dontwarn com.google.auto.factory.**
-dontwarn com.google.googlejavaformat.**
-dontwarn com.google.common.**
-dontwarn Njava.util.**
-dontwarn hu.akarnokd.rxjava2.**
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn org.objenesis.**

# Slidinguppanel
-dontwarn com.sothree.**
-keep class com.sothree.**
-keep interface com.sothree.**

# Test
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.hamcrest.**
-dontwarn android.test.**
-dontwarn android.support.test.**