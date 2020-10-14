package com.tencent.liteav.basic.util;

import android.content.*;
import android.app.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.datareport.*;
import android.net.*;
import android.telephony.*;
import java.util.*;
import java.lang.ref.*;
import android.os.*;
import android.util.*;
import android.text.*;
import java.nio.*;
import android.annotation.*;
import android.media.*;
import android.graphics.*;
import java.io.*;

public class f
{
    private static boolean b;
    private static String c;
    public static long a;
    private static int d;
    private static long e;
    private static boolean f;
    private static a<b> g;
    private static final Object h;
    private static boolean i;
    private static int[] j;
    
    public static int[] a() {
        if (com.tencent.liteav.basic.util.f.b) {
            com.tencent.liteav.basic.util.f.b = false;
            com.tencent.liteav.basic.util.f.g.a().a();
            return new int[] { 0, 0 };
        }
        return com.tencent.liteav.basic.util.f.g.a().a();
    }
    
    public static int b() {
        if (com.tencent.liteav.basic.util.f.f || (com.tencent.liteav.basic.util.f.e != 0L && TXCTimeUtil.getTimeTick() - com.tencent.liteav.basic.util.f.e < 15000L)) {
            return com.tencent.liteav.basic.util.f.d;
        }
        com.tencent.liteav.basic.util.f.f = true;
        AsyncTask.execute((Runnable)new Runnable() {
            @Override
            public void run() {
                System.currentTimeMillis();
                com.tencent.liteav.basic.util.f.f = false;
                try {
                    final Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
                    Debug.getMemoryInfo(memoryInfo);
                    final int totalPss = memoryInfo.getTotalPss();
                    com.tencent.liteav.basic.util.f.e = TXCTimeUtil.getTimeTick();
                    com.tencent.liteav.basic.util.f.d = totalPss / 1024;
                }
                catch (Exception ex) {}
            }
        });
        return com.tencent.liteav.basic.util.f.d;
    }
    
    public static boolean a(final Context context) {
        if (context == null) {
            return false;
        }
        try {
            final ActivityManager activityManager = (ActivityManager)context.getSystemService("activity");
            if (activityManager.getRunningTasks(1) == null) {
                TXCLog.e("TXCSystemUtil", "running task is null, ams is abnormal!!!");
                return false;
            }
            final ActivityManager.RunningTaskInfo runningTaskInfo = activityManager.getRunningTasks(1).get(0);
            if (runningTaskInfo == null || runningTaskInfo.topActivity == null) {
                TXCLog.e("TXCSystemUtil", "failed to get RunningTaskInfo");
                return false;
            }
            return !runningTaskInfo.topActivity.getPackageName().equals(context.getPackageName());
        }
        catch (Exception ex) {
            return false;
        }
    }
    
    public static String b(final Context context) {
        return TXCDRApi.getSimulateIDFA(context);
    }
    
    public static String c(final Context context) {
        String packageName = "";
        if (context != null) {
            try {
                packageName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
            }
            catch (Exception ex) {}
        }
        return packageName;
    }
    
    public static boolean d(final Context context) {
        try {
            System.currentTimeMillis();
            if (context != null) {
                final NetworkInfo activeNetworkInfo = ((ConnectivityManager)context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    return activeNetworkInfo.isAvailable();
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
    
    public static int e(final Context context) {
        if (context == null) {
            return 0;
        }
        final ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        final TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return 0;
        }
        if (!activeNetworkInfo.isConnected()) {
            return 0;
        }
        if (activeNetworkInfo.getType() == 9) {
            return 5;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 0;
        }
        switch (telephonyManager.getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11: {
                return 4;
            }
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15: {
                return 3;
            }
            case 13: {
                return 2;
            }
            default: {
                return 2;
            }
        }
    }
    
    public static String c() {
        return Build.MODEL;
    }
    
    public static String d() {
        return Build.VERSION.RELEASE;
    }
    
    public static String e() {
        return UUID.randomUUID().toString();
    }
    
    public static String f(final Context context) {
        return TXCDRApi.getDevUUID(context, TXCDRApi.getSimulateIDFA(context));
    }
    
    public static void a(final WeakReference<com.tencent.liteav.basic.b.b> weakReference, final String s, final int n, final String s2) {
        final Bundle bundle = new Bundle();
        bundle.putString("EVT_USERID", s);
        bundle.putInt("EVT_ID", n);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (s2 != null) {
            bundle.putCharSequence("EVT_MSG", (CharSequence)s2);
        }
        a(weakReference, n, bundle);
    }
    
    public static void a(final WeakReference<com.tencent.liteav.basic.b.b> weakReference, final int n, final String s) {
        final Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", n);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (s != null) {
            bundle.putCharSequence("EVT_MSG", (CharSequence)s);
        }
        a(weakReference, n, bundle);
    }
    
    public static void a(final WeakReference<com.tencent.liteav.basic.b.b> weakReference, final int n, final Bundle bundle) {
        if (weakReference == null) {
            return;
        }
        final com.tencent.liteav.basic.b.b b = weakReference.get();
        if (b != null) {
            b.onNotifyEvent(n, bundle);
        }
    }
    
    public static void a(final WeakReference<com.tencent.liteav.basic.b.b> weakReference, final String s, final int n, final Bundle bundle) {
        if (weakReference == null) {
            return;
        }
        final com.tencent.liteav.basic.b.b b = weakReference.get();
        if (b != null) {
            bundle.putString("EVT_USERID", s);
            b.onNotifyEvent(n, bundle);
        }
    }
    
    public static com.tencent.liteav.basic.c.a a(final int n, final int n2, final int n3, final int n4) {
        int n5;
        int n6;
        if (n * n4 >= n2 * n3) {
            n5 = n2;
            n6 = n5 * n3 / n4;
        }
        else {
            n6 = n;
            n5 = n6 * n4 / n3;
        }
        return new com.tencent.liteav.basic.c.a((n > n6) ? (n - n6 >> 1) : 0, (n2 > n5) ? (n2 - n5 >> 1) : 0, n6, n5);
    }
    
    public static boolean f() {
        synchronized (com.tencent.liteav.basic.util.f.h) {
            if (!com.tencent.liteav.basic.util.f.i) {
                Log.w("Native-LiteAV", "load library txffmpeg " + a("txffmpeg"));
                Log.w("Native-LiteAV", "load library traeimp-rtmp " + a("traeimp-rtmp"));
                com.tencent.liteav.basic.util.f.i = a("liteavsdk");
                Log.w("Native-LiteAV", "load library liteavsdk " + com.tencent.liteav.basic.util.f.i);
            }
            return com.tencent.liteav.basic.util.f.i;
        }
    }
    
    public static boolean a(final String s) {
        try {
            Log.w("Native-LiteAV", "load library " + s + " from system path ");
            System.loadLibrary(s);
            return true;
        }
        catch (Error error) {
            Log.w("Native-LiteAV", "load library : " + error.toString());
            return b(com.tencent.liteav.basic.util.f.c, s);
        }
        catch (Exception ex) {
            Log.w("Native-LiteAV", "load library : " + ex.toString());
            return b(com.tencent.liteav.basic.util.f.c, s);
        }
    }
    
    private static boolean b(final String s, final String s2) {
        boolean b = false;
        try {
            if (!TextUtils.isEmpty((CharSequence)s)) {
                Log.w("Native-LiteAV", "load library " + s2 + " from path " + s);
                System.load(s + "/lib" + s2 + ".so");
                b = true;
            }
        }
        catch (Error error) {
            Log.w("Native-LiteAV", "load library : " + error.toString());
        }
        catch (Exception ex) {
            Log.w("Native-LiteAV", "load library : " + ex.toString());
        }
        return b;
    }
    
    public static void b(final String c) {
        Log.w("Native-LiteAV", "setLibraryPath " + c);
        com.tencent.liteav.basic.util.f.c = c;
    }
    
    public static String g() {
        return com.tencent.liteav.basic.util.f.c;
    }
    
    public static int a(final int n) {
        int n2;
        for (n2 = 0; n2 < com.tencent.liteav.basic.util.f.j.length && com.tencent.liteav.basic.util.f.j[n2] != n; ++n2) {}
        if (n2 >= com.tencent.liteav.basic.util.f.j.length) {
            n2 = -1;
        }
        return n2;
    }
    
    @TargetApi(16)
    public static MediaFormat a(final int n, final int n2, final int n3) {
        final int a = a(n);
        final ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.put(0, (byte)(n3 << 3 | a >> 1));
        allocate.put(1, (byte)((a & 0x1) << 7 | n2 << 3));
        final MediaFormat audioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", n, n2);
        audioFormat.setInteger("channel-count", n2);
        audioFormat.setInteger("sample-rate", n);
        audioFormat.setByteBuffer("csd-0", allocate);
        return audioFormat;
    }
    
    public static boolean a(final String dataSource, final String s) {
        FileOutputStream fileOutputStream = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try {
            if (dataSource == null || s == null) {
                return false;
            }
            if (!new File(dataSource).exists()) {
                return false;
            }
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(dataSource);
            final Bitmap frameAtTime = mediaMetadataRetriever.getFrameAtTime();
            final File file = new File(s);
            if (file.exists()) {
                file.delete();
            }
            fileOutputStream = new FileOutputStream(file);
            frameAtTime.compress(Bitmap.CompressFormat.JPEG, 100, (OutputStream)fileOutputStream);
            fileOutputStream.flush();
        }
        catch (Exception ex) {
            TXCLog.e("TXCSystemUtil", "get video thumb failed.", ex);
            return false;
        }
        finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException ex2) {}
            }
            if (mediaMetadataRetriever != null) {
                mediaMetadataRetriever.release();
            }
        }
        return true;
    }
    
    public static boolean h() {
        return (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("NEM-L22")) || (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi") && Build.MODEL.equalsIgnoreCase("MIX 2S"));
    }
    
    private static void a(final String s, final MediaFormat mediaFormat, final byte[] array, final int n, final int n2) {
        final ByteBuffer allocate = ByteBuffer.allocate(n2 - n);
        allocate.put(array, n, n2 - n);
        allocate.position(0);
        mediaFormat.setByteBuffer(s, allocate);
    }
    
    @TargetApi(16)
    public static MediaFormat a(final byte[] array, final int n, final int n2) {
        final MediaFormat videoFormat = MediaFormat.createVideoFormat("video/avc", n, n2);
        boolean b = false;
        boolean b2 = false;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        for (int n6 = 0; n6 + 3 < array.length; ++n6) {
            if (array[n6] == 0 && array[n6 + 1] == 0 && array[n6 + 2] == 1) {
                n3 = 3;
            }
            if (array[n6] == 0 && array[n6 + 1] == 0 && array[n6 + 2] == 0 && array[n6 + 3] == 1) {
                n3 = 4;
            }
            if (n3 > 0) {
                if (n4 == 0) {
                    n4 = n3;
                    n6 += n3;
                    n3 = 0;
                }
                else {
                    n5 = n6;
                    final int n7 = array[n4] & 0x1F;
                    if (n7 == 7) {
                        a("csd-0", videoFormat, array, n4, n5);
                        b = true;
                    }
                    else if (n7 == 8) {
                        a("csd-1", videoFormat, array, n4, n5);
                        b2 = true;
                    }
                    n6 = (n4 = n6 + n3);
                    n3 = 0;
                    if (b && b2) {
                        return videoFormat;
                    }
                }
            }
            else {
                n3 = 0;
            }
        }
        final int n8 = array[n4] & 0x1F;
        if (b && n8 == 8) {
            a("csd-1", videoFormat, array, n4, n5);
            return videoFormat;
        }
        if (b2 && n8 == 7) {
            a("csd-0", videoFormat, array, n4, n5);
            return videoFormat;
        }
        return null;
    }
    
    static {
        com.tencent.liteav.basic.util.f.b = true;
        com.tencent.liteav.basic.util.f.c = "";
        com.tencent.liteav.basic.util.f.a = 0L;
        com.tencent.liteav.basic.util.f.d = 0;
        com.tencent.liteav.basic.util.f.e = 0L;
        com.tencent.liteav.basic.util.f.f = false;
        com.tencent.liteav.basic.util.f.g = new a<b>(new a.a<b>() {
            public b b() {
                return new b();
            }
        });
        h = new Object();
        com.tencent.liteav.basic.util.f.i = false;
        com.tencent.liteav.basic.util.f.j = new int[] { 96000, 88200, 64000, 48000, 44100, 32000, 24000, 22050, 16000, 12000, 11025, 8000, 7350 };
    }
}
