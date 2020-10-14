package com.tencent.liteav.basic.module;

public class TXCEventRecorderProxy
{
    private long a;
    
    @Override
    protected void finalize() throws Throwable {
        nativeRelease(this.a);
        this.a = 0L;
        super.finalize();
    }
    
    public static void a(final String s, final int n, final long n2, final long n3, final String s2, final int n4) {
        if (s == null || s2 == null) {
            return;
        }
        nativeAddEventMsg(s, n, n2, n3, s2, n4);
    }
    
    private static native void nativeRelease(final long p0);
    
    private static native void nativeAddEventMsg(final String p0, final int p1, final long p2, final long p3, final String p4, final int p5);
}
