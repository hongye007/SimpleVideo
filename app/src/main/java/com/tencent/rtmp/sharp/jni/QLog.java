package com.tencent.rtmp.sharp.jni;

import android.content.*;
import android.util.*;
import com.tencent.liteav.basic.log.*;

public class QLog
{
    public static final String ERR_KEY = "qq_error|";
    public static final int DEV = 4;
    public static final int CLR = 2;
    public static final int USR = 1;
    public static final int LOG_ITEM_MAX_CACHE_SIZE = 50;
    public static String sBuildNumber;
    public static final String TAG_REPORTLEVEL_DEVELOPER = "D";
    public static final String TAG_REPORTLEVEL_COLORUSER = "W";
    public static final String TAG_REPORTLEVEL_USER = "E";
    
    public static void init(final Context context) {
    }
    
    public static boolean isColorLevel() {
        return true;
    }
    
    public static boolean isDevelopLevel() {
        return true;
    }
    
    public static void p(final String s, final String s2) {
    }
    
    public static String getStackTraceString(final Throwable t) {
        return Log.getStackTraceString(t);
    }
    
    public static void e(final String s, final int n, final String s2) {
        TXCLog.e(s, "[" + getReportLevel(n) + "]" + s2);
    }
    
    public static void e(final String s, final int n, final String s2, final Throwable t) {
        e(s, n, s2);
    }
    
    public static void w(final String s, final int n, final String s2) {
        TXCLog.w(s, "[" + getReportLevel(n) + "]" + s2);
    }
    
    public static void w(final String s, final int n, final String s2, final Throwable t) {
        TXCLog.w(s, "[" + getReportLevel(n) + "]" + s2);
    }
    
    public static void i(final String s, final int n, final String s2) {
        TXCLog.i(s, "[" + getReportLevel(n) + "]" + s2);
    }
    
    public static void i(final String s, final int n, final String s2, final Throwable t) {
        TXCLog.i(s, "[" + getReportLevel(n) + "]" + s2);
    }
    
    public static void d(final String s, final int n, final String s2) {
        TXCLog.d(s, "[" + getReportLevel(n) + "]" + s2);
    }
    
    public static void d(final String s, final int n, final String s2, final Throwable t) {
        TXCLog.d(s, "[" + getReportLevel(n) + "]" + s2);
    }
    
    public static String getReportLevel(final int n) {
        switch (n) {
            case 2: {
                return "W";
            }
            case 4: {
                return "D";
            }
            case 1: {
                return "E";
            }
            default: {
                return "E";
            }
        }
    }
    
    public static void dumpCacheToFile() {
    }
    
    static {
        QLog.sBuildNumber = "";
    }
}
