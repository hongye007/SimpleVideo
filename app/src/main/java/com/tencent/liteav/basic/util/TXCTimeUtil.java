package com.tencent.liteav.basic.util;

public class TXCTimeUtil
{
    public static long generatePtsMS() {
        return nativeGeneratePtsMS();
    }
    
    public static long getTimeTick() {
        return nativeGetTimeTick();
    }
    
    public static long getUtcTimeTick() {
        return nativeGetUtcTimeTick();
    }
    
    public static void initAppStartTime() {
        nativeInitAppStartTime();
    }
    
    public static long getClockTickInHz() {
        return nativeGetClockTickInHz();
    }
    
    private static native long nativeGetClockTickInHz();
    
    private static native long nativeGetTimeTick();
    
    private static native long nativeGetUtcTimeTick();
    
    private static native void nativeInitAppStartTime();
    
    private static native long nativeGeneratePtsMS();
    
    static {
        f.f();
    }
}
