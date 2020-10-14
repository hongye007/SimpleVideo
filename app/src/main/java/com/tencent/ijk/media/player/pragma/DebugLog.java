package com.tencent.ijk.media.player.pragma;

import android.util.*;
import java.util.*;

public class DebugLog
{
    public static final boolean ENABLE_ERROR = true;
    public static final boolean ENABLE_INFO = true;
    public static final boolean ENABLE_WARN = true;
    public static final boolean ENABLE_DEBUG = true;
    public static final boolean ENABLE_VERBOSE = true;
    
    public static void e(final String s, final String s2) {
        Log.e(s, s2);
    }
    
    public static void e(final String s, final String s2, final Throwable t) {
        Log.e(s, s2, t);
    }
    
    public static void efmt(final String s, final String s2, final Object... array) {
        Log.e(s, String.format(Locale.US, s2, array));
    }
    
    public static void i(final String s, final String s2) {
        Log.i(s, s2);
    }
    
    public static void i(final String s, final String s2, final Throwable t) {
        Log.i(s, s2, t);
    }
    
    public static void ifmt(final String s, final String s2, final Object... array) {
        Log.i(s, String.format(Locale.US, s2, array));
    }
    
    public static void w(final String s, final String s2) {
        Log.w(s, s2);
    }
    
    public static void w(final String s, final String s2, final Throwable t) {
        Log.w(s, s2, t);
    }
    
    public static void wfmt(final String s, final String s2, final Object... array) {
        Log.w(s, String.format(Locale.US, s2, array));
    }
    
    public static void d(final String s, final String s2) {
        Log.d(s, s2);
    }
    
    public static void d(final String s, final String s2, final Throwable t) {
        Log.d(s, s2, t);
    }
    
    public static void dfmt(final String s, final String s2, final Object... array) {
        Log.d(s, String.format(Locale.US, s2, array));
    }
    
    public static void v(final String s, final String s2) {
        Log.v(s, s2);
    }
    
    public static void v(final String s, final String s2, final Throwable t) {
        Log.v(s, s2, t);
    }
    
    public static void vfmt(final String s, final String s2, final Object... array) {
        Log.v(s, String.format(Locale.US, s2, array));
    }
    
    public static void printStackTrace(final Throwable t) {
        t.printStackTrace();
    }
    
    public static void printCause(Throwable t) {
        final Throwable cause = t.getCause();
        if (cause != null) {
            t = cause;
        }
        printStackTrace(t);
    }
}
