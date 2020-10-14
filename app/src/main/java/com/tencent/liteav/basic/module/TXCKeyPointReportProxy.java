package com.tencent.liteav.basic.module;

import android.content.*;
import java.io.*;
import com.tencent.liteav.basic.log.*;

public class TXCKeyPointReportProxy
{
    public static void a(final Context context) {
        if (context == null) {
            return;
        }
        final File externalFilesDir = context.getApplicationContext().getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            return;
        }
        final String string = externalFilesDir.getAbsolutePath() + "/txrtmp/ssoreport.txt";
        final File file = new File(string);
        if (!file.exists()) {
            try {
                if (!file.mkdirs()) {
                    TXCLog.e("TXCKeyPointReportProxy", "can not create sso file path");
                    return;
                }
            }
            catch (Exception ex) {
                TXCLog.e("TXCKeyPointReportProxy", "create sso file exception:" + ex.toString());
            }
        }
        nativeInit(string);
    }
    
    public static void a() {
        nativeSendCacheReport();
    }
    
    public static void a(final a a) {
        nativeSetDeviceInfo(a.a, a.b, a.c, a.d, a.e, a.f, a.g, a.h);
    }
    
    public static void a(final int n, final int n2) {
        nativeSetCpu(n, n2);
    }
    
    public static void a(final int n) {
        nativeTagKeyPointStart(n);
    }
    
    public static void b(final int n, final int n2) {
        nativeTagKeyPointEnd(n, n2);
    }
    
    public static void a(final String s, final int n, final long n2, final int n3) {
        nativesetRemoteQuality(s, n, n2, n3);
    }
    
    public static void a(final int n, final int n2, final int n3) {
        nativeSetLocalQuality(n, n2, n3);
    }
    
    public static void c(final int n, final int n2) {
        nativeSetBasicInfo(n, n2);
    }
    
    public static void b(final int n) {
        nativeSetErrorCode(n);
    }
    
    private static native void nativeInit(final String p0);
    
    private static native void nativeSendCacheReport();
    
    private static native void nativeSetDeviceInfo(final int p0, final int p1, final int p2, final int p3, final String p4, final String p5, final String p6, final String p7);
    
    private static native void nativeSetCpu(final int p0, final int p1);
    
    private static native void nativeTagKeyPointStart(final int p0);
    
    private static native void nativeTagKeyPointEnd(final int p0, final int p1);
    
    private static native void nativesetRemoteQuality(final String p0, final int p1, final long p2, final int p3);
    
    private static native void nativeSetLocalQuality(final int p0, final int p1, final int p2);
    
    private static native void nativeSetBasicInfo(final int p0, final int p1);
    
    private static native void nativeSetErrorCode(final int p0);
    
    public static class a
    {
        public int a;
        public int b;
        public int c;
        public int d;
        public String e;
        public String f;
        public String g;
        public String h;
    }
}
