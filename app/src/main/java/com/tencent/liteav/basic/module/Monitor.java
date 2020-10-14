package com.tencent.liteav.basic.module;

public class Monitor
{
    public static void a(final String s, final int n, final String s2) {
        nativeInit(s, n, s2);
    }
    
    public static void a(final int n, final String s, final String s2, final int n2) {
        nativeOnlineLog(n, s, s2, n2);
    }
    
    public static void a(final int n, final int n2, final String s, final String s2, final int n3, final int n4) {
        nativeOnlineLogWithLimit(n, n2, s, s2, n3, n4);
    }
    
    public static void a() {
        nativeUnInit();
    }
    
    private static native void nativeInit(final String p0, final int p1, final String p2);
    
    private static native void nativeOnlineLog(final int p0, final String p1, final String p2, final int p3);
    
    private static native void nativeOnlineLogWithLimit(final int p0, final int p1, final String p2, final String p3, final int p4, final int p5);
    
    private static native void nativeUnInit();
}
