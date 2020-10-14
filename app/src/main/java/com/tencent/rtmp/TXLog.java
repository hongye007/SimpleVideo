package com.tencent.rtmp;

import com.tencent.liteav.basic.log.*;

public class TXLog
{
    public static void d(final String s, final String s2) {
        wrietLogMessage(1, s, s2);
    }
    
    public static void i(final String s, final String s2) {
        wrietLogMessage(2, s, s2);
    }
    
    public static void w(final String s, final String s2) {
        wrietLogMessage(3, s, s2);
    }
    
    public static void e(final String s, final String s2) {
        wrietLogMessage(4, s, s2);
    }
    
    private static void wrietLogMessage(final int n, final String s, final String s2) {
        TXCLog.log(n, s, "thread ID:" + Thread.currentThread().getId() + "|line:" + -1 + "|" + s2);
    }
}
