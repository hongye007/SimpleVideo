package com.tencent.liteav.basic.datareport;

import android.os.*;
import java.util.*;
import java.security.*;
import com.tencent.liteav.basic.log.*;
import android.provider.*;
import java.io.*;
import android.content.*;
import android.telephony.*;
import android.net.*;
import android.content.pm.*;
import com.tencent.liteav.basic.util.*;

public class TXCDRApi
{
    private static final String TAG = "TXCDRApi";
    private static String mDevType;
    private static String mNetType;
    private static String mDevId;
    private static String mDevUUID;
    private static String mAppName;
    private static String mSysVersion;
    private static String g_simulate_idfa;
    private static final char[] DIGITS_LOWER;
    static final int NETWORK_TYPE_UNKNOWN = 255;
    static final int NETWORK_TYPE_WIFI = 1;
    static final int NETWORK_TYPE_4G = 2;
    static final int NETWORK_TYPE_3G = 3;
    static final int NETWORK_TYPE_2G = 4;
    static boolean initRpt;
    
    public static void InitEvent(final Context commonInfo, final String s, final int n, final int n2, final TXCDRExtInfo txcdrExtInfo) {
        setCommonInfo(commonInfo);
        if (s == null) {
            return;
        }
        nativeInitEventInternal(s, n, n2, txcdrExtInfo);
    }
    
    public static void txSetEventValue(final String s, final int n, final String s2, final String s3) {
        nativeSetEventValueInterval(s, n, s2, s3);
    }
    
    public static void txSetEventIntValue(final String s, final int n, final String s2, final long n2) {
        nativeSetEventValueInterval(s, n, s2, "" + n2);
    }
    
    public static void txReportDAU(final Context commonInfo, final int n) {
        if (commonInfo != null) {
            setCommonInfo(commonInfo);
        }
        nativeReportDAUInterval(n, 0, "");
    }
    
    public static void txReportDAU(final Context commonInfo, final int n, final int n2, final String s) {
        if (commonInfo != null) {
            setCommonInfo(commonInfo);
        }
        nativeReportDAUInterval(n, n2, s);
    }
    
    public static void reportEvent40003(final String s, final int n, final int n2, final String s2, final String s3) {
        nativeReportEvent40003(s, n, n2, s2, s3);
    }
    
    public static void reportAVRoomEvent(final int n, final long n2, final String s, final int n3, final int n4, final String s2, final String s3) {
        nativeReportAVRoomEvent(n, n2, s, n3, n4, s2, s3);
    }
    
    public static int getStatusReportInterval() {
        return nativeGetStatusReportInterval();
    }
    
    public static void setCommonInfo(final Context context) {
        TXCDRApi.mDevType = Build.MODEL;
        TXCDRApi.mNetType = Integer.toString(getNetworkType(context));
        if (TXCDRApi.mDevId.isEmpty()) {
            TXCDRApi.mDevId = getSimulateIDFA(context);
        }
        if (TXCDRApi.mDevUUID.isEmpty()) {
            TXCDRApi.mDevUUID = getDevUUID(context, TXCDRApi.mDevId);
        }
        final String packageName = getPackageName(context);
        TXCDRApi.mAppName = getApplicationNameByPackageName(context, packageName) + ":" + packageName;
        TXCDRApi.mSysVersion = String.valueOf(Build.VERSION.SDK_INT);
        txSetCommonInfo();
    }
    
    public static void txSetCommonInfo() {
        if (TXCDRApi.mDevType != null) {
            nativeSetCommonValue(a.f, TXCDRApi.mDevType);
        }
        if (TXCDRApi.mNetType != null) {
            nativeSetCommonValue(a.g, TXCDRApi.mNetType);
        }
        if (TXCDRApi.mDevId != null) {
            nativeSetCommonValue(a.h, TXCDRApi.mDevId);
        }
        if (TXCDRApi.mDevUUID != null) {
            nativeSetCommonValue(a.i, TXCDRApi.mDevUUID);
        }
        if (TXCDRApi.mAppName != null) {
            nativeSetCommonValue(a.j, TXCDRApi.mAppName);
        }
        if (TXCDRApi.mSysVersion != null) {
            nativeSetCommonValue(a.l, TXCDRApi.mSysVersion);
        }
    }
    
    public static void txSetAppVersion(final String s) {
        if (s != null) {
            nativeSetCommonValue(a.k, s);
        }
    }
    
    public static String txCreateToken() {
        return UUID.randomUUID().toString();
    }
    
    private static String byteArrayToHexString(final byte[] array) {
        final char[] array2 = new char[array.length << 1];
        int i = 0;
        int n = 0;
        while (i < array.length) {
            array2[n++] = TXCDRApi.DIGITS_LOWER[(0xF0 & array[i]) >>> 4];
            array2[n++] = TXCDRApi.DIGITS_LOWER[0xF & array[i]];
            ++i;
        }
        return new String(array2);
    }
    
    public static String string2Md5(final String s) {
        String byteArrayToHexString = "";
        if (null == s) {
            return byteArrayToHexString;
        }
        try {
            byteArrayToHexString = byteArrayToHexString(MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8")));
        }
        catch (Exception ex) {
            TXCLog.e("TXCDRApi", "string2Md5 failed.", ex);
        }
        if (byteArrayToHexString == null) {
            byteArrayToHexString = "";
        }
        return byteArrayToHexString;
    }
    
    public static String getOrigAndroidID(final Context context) {
        String string = "";
        try {
            string = Settings.Secure.getString(context.getContentResolver(), "android_id");
        }
        catch (Throwable t) {}
        return string2Md5(string);
    }
    
    public static String getSimulateIDFA(final Context context) {
        if (TXCDRApi.g_simulate_idfa != null && TXCDRApi.g_simulate_idfa.length() > 0) {
            return TXCDRApi.g_simulate_idfa;
        }
        String string = null;
        String s = null;
        final File externalFilesDir = context.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            TXCLog.e("TXCDRApi", "getSimulateIDFA sdcardDir is null");
            return TXCDRApi.g_simulate_idfa;
        }
        final SharedPreferences sharedPreferences = context.getSharedPreferences("com.tencent.liteav.dev_uuid", 0);
        final String string2 = sharedPreferences.getString("com.tencent.liteav.key_dev_uuid", "");
        try {
            final File file = new File(externalFilesDir.getAbsolutePath() + "/txrtmp/spuid");
            if (file.exists()) {
                final FileInputStream fileInputStream = new FileInputStream(file);
                final int available = fileInputStream.available();
                if (available > 0) {
                    final byte[] array = new byte[available];
                    fileInputStream.read(array);
                    s = new String(array, "UTF-8");
                }
                fileInputStream.close();
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCDRApi", "read UUID from file failed! reason: " + ex.getMessage());
        }
        if (string2 != null && string2.length() > 0) {
            string = string2;
        }
        else if (s != null && s.length() > 0) {
            string = s;
        }
        if (string == null || string.length() == 0) {
            String s2 = "";
            final long currentTimeMillis = System.currentTimeMillis();
            final long timeTick = TXCTimeUtil.getTimeTick();
            final String c = f.c(context);
            for (int i = 5; i >= 0; --i) {
                s2 += String.format("%02x", (byte)(currentTimeMillis >> i * 8 & 0xFFL));
            }
            for (int j = 3; j >= 0; --j) {
                s2 += String.format("%02x", (byte)(timeTick >> j * 8 & 0xFFL));
            }
            string = s2 + string2Md5(c + UUID.randomUUID().toString());
        }
        TXCDRApi.g_simulate_idfa = string;
        TXCLog.i("TXCDRApi", "UUID:" + TXCDRApi.g_simulate_idfa);
        Label_0630: {
            if (s != null) {
                if (s.equals(string)) {
                    break Label_0630;
                }
            }
            try {
                final File file2 = new File(externalFilesDir.getAbsolutePath() + "/txrtmp");
                if (!file2.exists()) {
                    file2.mkdir();
                }
                final File file3 = new File(externalFilesDir.getAbsolutePath() + "/txrtmp/spuid");
                if (!file3.exists()) {
                    file3.createNewFile();
                }
                final FileOutputStream fileOutputStream = new FileOutputStream(file3);
                fileOutputStream.write(string.getBytes());
                fileOutputStream.close();
            }
            catch (Exception ex2) {
                TXCLog.e("TXCDRApi", "write UUID to file failed! reason: " + ex2.getMessage());
            }
        }
        if (string2 == null || !string2.equals(string)) {
            final SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString("com.tencent.liteav.key_dev_uuid", string);
            edit.commit();
        }
        return TXCDRApi.g_simulate_idfa;
    }
    
    public static String getDevUUID(final Context context, final String s) {
        return getSimulateIDFA(context);
    }
    
    public static int getNetworkType(final Context context) {
        if (context == null) {
            return 255;
        }
        final ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        final TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService("phone");
        NetworkInfo activeNetworkInfo;
        try {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        catch (Exception ex) {
            TXCLog.e("TXCDRApi", "getActiveNetworkInfo exception:", ex);
            return 255;
        }
        if (activeNetworkInfo == null) {
            return 255;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 255;
        }
        int networkType;
        try {
            networkType = telephonyManager.getNetworkType();
        }
        catch (Exception ex2) {
            TXCLog.e("TXCDRApi", "TXCDRApi: get network type fail, exception occurred.", ex2);
            return 2;
        }
        switch (networkType) {
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
    
    private static String getPackageName(final Context context) {
        String packageName = "";
        try {
            packageName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        }
        catch (Exception ex) {
            TXCLog.e("TXCDRApi", "get package name failed.", ex);
        }
        return packageName;
    }
    
    public static String getApplicationNameByPackageName(final Context context, final String s) {
        final PackageManager packageManager = context.getPackageManager();
        String string;
        try {
            string = packageManager.getApplicationLabel(packageManager.getApplicationInfo(s, 128)).toString();
        }
        catch (Exception ex) {
            string = "";
        }
        return string;
    }
    
    public static void initCrashReport(final Context context) {
        try {
            synchronized (TXCDRApi.class) {
                if (!TXCDRApi.initRpt && context != null) {
                    final String sdkVersionStr = TXCCommonUtil.getSDKVersionStr();
                    if (sdkVersionStr != null) {
                        final SharedPreferences.Editor edit = context.getSharedPreferences("BuglySdkInfos", 0).edit();
                        edit.putString("8e50744bf0", sdkVersionStr);
                        edit.commit();
                        TXCDRApi.initRpt = true;
                    }
                }
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCDRApi", "init crash report failed.", ex);
        }
    }
    
    private static native void nativeInitDataReport();
    
    private static native void nativeUninitDataReport();
    
    private static native void nativeInitEventInternal(final String p0, final int p1, final int p2, final TXCDRExtInfo p3);
    
    private static native void nativeReportDAUInterval(final int p0, final int p1, final String p2);
    
    private static native void nativeSetEventValueInterval(final String p0, final int p1, final String p2, final String p3);
    
    public static native void nativeSetCommonValue(final String p0, final String p1);
    
    public static native void nativeReportEvent(final String p0, final int p1);
    
    public static native int nativeGetStatusReportInterval();
    
    public static native void nativeReportEvent40003(final String p0, final int p1, final int p2, final String p3, final String p4);
    
    public static native void nativeReportAVRoomEvent(final int p0, final long p1, final String p2, final int p3, final int p4, final String p5, final String p6);
    
    static {
        TXCDRApi.mDevType = "";
        TXCDRApi.mNetType = "";
        TXCDRApi.mDevId = "";
        TXCDRApi.mDevUUID = "";
        TXCDRApi.mAppName = "";
        TXCDRApi.mSysVersion = "";
        TXCDRApi.g_simulate_idfa = "";
        DIGITS_LOWER = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        TXCDRApi.initRpt = false;
        f.f();
        nativeInitDataReport();
    }
}
