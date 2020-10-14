package com.tencent.liteav.basic.log;

import com.tencent.liteav.basic.util.*;
import android.text.*;
import android.util.*;
import android.content.*;
import java.io.*;

public class TXCLog
{
    public static final int LOG_VERBOSE = 0;
    public static final int LOG_DEBUG = 1;
    public static final int LOG_INFO = 2;
    public static final int LOG_WARNING = 3;
    public static final int LOG_ERROR = 4;
    public static final int LOG_FATAL = 5;
    public static final int LOG_NONE = 6;
    public static final int LOG_ASYNC = 0;
    public static final int LOG_SYNC = 1;
    public static String mLogDir;
    private static String mLogCacheDir;
    private static boolean mEnableCompress;
    private static final Object mLogLock;
    private static boolean mHasInit;
    private static a mListener;
    private static int mLogLevel;
    private static boolean mEnableConsole;
    private static boolean mEnableCallback;
    
    public static boolean init() {
        if (TXCLog.mHasInit) {
            return true;
        }
        synchronized (TXCLog.mLogLock) {
            if (TXCLog.mHasInit) {
                return true;
            }
            final boolean f = com.tencent.liteav.basic.util.f.f();
            final Context appContext = TXCCommonUtil.getAppContext();
            if (f && appContext != null) {
                if (TextUtils.isEmpty((CharSequence)TXCLog.mLogDir)) {
                    final File externalFilesDir = appContext.getExternalFilesDir((String)null);
                    if (externalFilesDir != null) {
                        TXCLog.mLogDir = externalFilesDir.getAbsolutePath() + "/log/tencent/liteav";
                    }
                }
                TXCLog.mLogCacheDir = appContext.getFilesDir().getAbsolutePath() + "/log/tencent/liteav";
                Log.i("TXCLog", "TXCLog init log file path : " + TXCLog.mLogDir + ", cache : " + TXCLog.mLogCacheDir);
                nativeLogInit();
                nativeLogSetLevel(TXCLog.mLogLevel);
                nativeLogSetConsole(TXCLog.mEnableConsole);
                nativeLogOpen(0, TXCLog.mLogDir, TXCLog.mLogCacheDir, "LiteAV", TXCLog.mEnableCompress);
                nativeEnableCallback(TXCLog.mEnableCallback);
                TXCLog.mHasInit = true;
            }
        }
        return TXCLog.mHasInit;
    }
    
    public static void log(final int n, final String s, final String s2) {
        if (init()) {
            nativeLog(n, s, "", 0, "", s2);
        }
        log_callback(n, s, s2);
    }
    
    public static void setLevel(final int mLogLevel) {
        TXCLog.mLogLevel = mLogLevel;
        if (TXCLog.mHasInit) {
            nativeLogSetLevel(TXCLog.mLogLevel);
        }
    }
    
    public static void setLogDirPath(final String mLogDir) {
        if (TextUtils.isEmpty((CharSequence)mLogDir)) {
            return;
        }
        if (!mLogDir.equalsIgnoreCase(TXCLog.mLogDir)) {
            TXCLog.mLogDir = mLogDir;
            if (TXCLog.mHasInit) {
                Log.i("TXCLog", "TXCLog setLogDirPath " + mLogDir);
                nativeLogClose();
                nativeLogOpen(0, TXCLog.mLogDir, TXCLog.mLogCacheDir, "LiteAV", TXCLog.mEnableCompress);
            }
        }
    }
    
    public static void sliceLogFile() {
        if (TXCLog.mHasInit) {
            Log.i("TXCLog", "TXCLog sliceLogFile");
            nativeLogOpen(0, TXCLog.mLogDir, TXCLog.mLogCacheDir, "LiteAV", TXCLog.mEnableCompress);
        }
    }
    
    public static void setLogCompressEnabled(final boolean mEnableCompress) {
        if (TXCLog.mEnableCompress != mEnableCompress) {
            TXCLog.mEnableCompress = mEnableCompress;
            if (TXCLog.mHasInit) {
                nativeLogClose();
                nativeLogOpen(0, TXCLog.mLogDir, TXCLog.mLogCacheDir, "LiteAV", TXCLog.mEnableCompress);
            }
        }
    }
    
    public static void setConsoleEnabled(final boolean mEnableConsole) {
        TXCLog.mEnableConsole = mEnableConsole;
        if (TXCLog.mHasInit) {
            nativeLogSetConsole(TXCLog.mEnableConsole);
        }
    }
    
    public static void setListener(final a mListener) {
        TXCLog.mListener = mListener;
        TXCLog.mEnableCallback = (TXCLog.mListener != null);
        if (TXCLog.mHasInit) {
            nativeEnableCallback(TXCLog.mEnableCallback);
        }
    }
    
    public static void v(final String s, final String s2, final Object... array) {
        log(0, s, String.format(s2, array));
    }
    
    public static void d(final String s, final String s2, final Object... array) {
        log(1, s, String.format(s2, array));
    }
    
    public static void i(final String s, final String s2, final Object... array) {
        log(2, s, String.format(s2, array));
    }
    
    public static void w(final String s, final String s2, final Object... array) {
        log(3, s, String.format(s2, array));
    }
    
    public static void e(final String s, final String s2, final Object... array) {
        log(4, s, String.format(s2, array));
    }
    
    public static void v(final String s, final String s2) {
        log(0, s, s2);
    }
    
    public static void d(final String s, final String s2) {
        log(1, s, s2);
    }
    
    public static void i(final String s, final String s2) {
        log(2, s, s2);
    }
    
    public static void w(final String s, final String s2) {
        log(3, s, s2);
    }
    
    public static void e(final String s, final String s2) {
        log(4, s, s2);
    }
    
    public static void e(final String s, final String s2, final Throwable t) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter);
        t.printStackTrace(printWriter);
        for (Throwable t2 = t.getCause(); t2 != null; t2 = t2.getCause()) {
            t2.printStackTrace(printWriter);
        }
        printWriter.close();
        e(s, s2 + "\n" + stringWriter.toString());
    }
    
    private static void log_callback(final int n, final String s, final String s2) {
        if (TXCLog.mListener != null) {
            TXCLog.mListener.a(n, s, s2);
        }
    }
    
    private static native void nativeLogInit();
    
    private static native void nativeLog(final int p0, final String p1, final String p2, final int p3, final String p4, final String p5);
    
    private static native void nativeLogSetLevel(final int p0);
    
    private static native void nativeLogSetConsole(final boolean p0);
    
    private static native void nativeLogOpen(final int p0, final String p1, final String p2, final String p3, final boolean p4);
    
    private static native void nativeLogClose();
    
    private static native void nativeEnableCallback(final boolean p0);
    
    static {
        TXCLog.mLogDir = "";
        TXCLog.mLogCacheDir = "";
        TXCLog.mEnableCompress = true;
        mLogLock = new Object();
        TXCLog.mHasInit = false;
        TXCLog.mListener = null;
        TXCLog.mLogLevel = 0;
        TXCLog.mEnableConsole = true;
        TXCLog.mEnableCallback = false;
    }
    
    public interface a
    {
        void a(final int p0, final String p1, final String p2);
    }
}
