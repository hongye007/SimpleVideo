package com.tencent.liteav.basic.module;

public class TXCStatus
{
    public static void a(final String s) {
        nativeStatusStartRecord(s);
    }
    
    public static void b(final String s) {
        nativeStatusStopRecord(s);
    }
    
    public static boolean a(final String s, final int n, final Object o) {
        return a(s, n, 0, o);
    }
    
    public static boolean a(final String s, final int n, final int n2, final Object o) {
        if (s == null || s.length() == 0) {
            return false;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof Double) {
            return nativeStatusSetDoubleValue(s, n, n2, (double)o);
        }
        if (o instanceof String) {
            return nativeStatusSetStrValue(s, n, n2, (String)o);
        }
        if (o instanceof Long) {
            return nativeStatusSetIntValue(s, n, n2, (long)o);
        }
        return nativeStatusSetIntValue(s, n, n2, (int)o);
    }
    
    public static long a(final String s, final int n) {
        return a(s, n, 0);
    }
    
    public static String b(final String s, final int n) {
        return b(s, n, 0);
    }
    
    public static int c(final String s, final int n) {
        return c(s, n, 0);
    }
    
    public static double d(final String s, final int n) {
        return d(s, n, 0);
    }
    
    public static long a(final String s, final int n, final int n2) {
        return nativeStatusGetIntValue(s, n, n2);
    }
    
    public static String b(final String s, final int n, final int n2) {
        return nativeStatusGetStrValue(s, n, n2);
    }
    
    public static int c(final String s, final int n, final int n2) {
        return (int)nativeStatusGetIntValue(s, n, n2);
    }
    
    public static double d(final String s, final int n, final int n2) {
        return nativeStatusGetDoubleValue(s, n, n2);
    }
    
    private static native void nativeStatusStartRecord(final String p0);
    
    private static native void nativeStatusStopRecord(final String p0);
    
    private static native boolean nativeStatusSetIntValue(final String p0, final int p1, final int p2, final long p3);
    
    private static native boolean nativeStatusSetDoubleValue(final String p0, final int p1, final int p2, final double p3);
    
    private static native boolean nativeStatusSetStrValue(final String p0, final int p1, final int p2, final String p3);
    
    private static native long nativeStatusGetIntValue(final String p0, final int p1, final int p2);
    
    private static native double nativeStatusGetDoubleValue(final String p0, final int p1, final int p2);
    
    private static native String nativeStatusGetStrValue(final String p0, final int p1, final int p2);
}
