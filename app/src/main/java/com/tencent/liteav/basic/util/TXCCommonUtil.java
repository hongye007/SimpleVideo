package com.tencent.liteav.basic.util;

import com.tencent.liteav.basic.log.*;
import java.security.*;
import android.content.*;
import java.util.zip.*;
import java.io.*;
import java.util.*;
import android.net.wifi.*;

public class TXCCommonUtil
{
    private static final String TAG = "TXCCommonUtil";
    private static String mAppID;
    private static String mStrAppVersion;
    public static String pituLicencePath;
    private static Context sApplicationContext;
    private static String mUserId;
    
    public static int[] getSDKVersion() {
        final String[] split = nativeGetSDKVersion().split("\\.");
        final int[] array = new int[split.length];
        for (int i = 0; i < split.length; ++i) {
            try {
                array[i] = Integer.parseInt(split[i]);
            }
            catch (NumberFormatException ex) {
                TXCLog.e("TXCCommonUtil", "parse version failed.", ex);
                array[i] = 0;
            }
        }
        return array;
    }
    
    public static String getSDKVersionStr() {
        return nativeGetSDKVersion();
    }
    
    public static int getSDKID() {
        return nativeGetSDKID();
    }
    
    public static String getConfigCenterKey() {
        return nativeGetConfigCenterKey();
    }
    
    public static String getFileExtension(final String s) {
        String substring = null;
        if (s != null && s.length() > 0) {
            final int lastIndex = s.lastIndexOf(46);
            if (lastIndex > -1 && lastIndex < s.length() - 1) {
                substring = s.substring(lastIndex + 1);
            }
        }
        return substring;
    }
    
    public static void setAppContext(final Context context) {
        if (context == null) {
            return;
        }
        TXCCommonUtil.sApplicationContext = context.getApplicationContext();
    }
    
    public static Context getAppContext() {
        return TXCCommonUtil.sApplicationContext;
    }
    
    public static void sleep(final int n) {
        try {
            Thread.sleep(n);
        }
        catch (InterruptedException ex) {}
    }
    
    public static String getStreamIDByStreamUrl(final String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        String s2 = s;
        final int index = s2.indexOf("?");
        if (index != -1) {
            s2 = s2.substring(0, index);
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        final int lastIndex = s2.lastIndexOf("/");
        if (lastIndex != -1) {
            s2 = s2.substring(lastIndex + 1);
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        final int index2 = s2.indexOf(".");
        if (index2 != -1) {
            s2 = s2.substring(0, index2);
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        return s2;
    }
    
    public static String getAppNameByStreamUrl(final String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        String s2 = s;
        final int index = s2.indexOf("?");
        if (index != -1) {
            s2 = s2.substring(0, index);
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        final int lastIndex = s2.lastIndexOf("/");
        if (lastIndex != -1) {
            s2 = s2.substring(0, lastIndex);
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        final int lastIndex2 = s2.lastIndexOf("/");
        if (lastIndex2 != -1) {
            s2 = s2.substring(lastIndex2 + 1);
        }
        if (s2 == null || s2.length() == 0) {
            return null;
        }
        return s2;
    }
    
    public static void setAppVersion(final String mStrAppVersion) {
        TXCCommonUtil.mStrAppVersion = mStrAppVersion;
    }
    
    public static void setPituLicencePath(final String pituLicencePath) {
        TXCCommonUtil.pituLicencePath = pituLicencePath;
    }
    
    public static String getAppVersion() {
        return TXCCommonUtil.mStrAppVersion;
    }
    
    public static void setAppID(final String mAppID) {
        TXCCommonUtil.mAppID = mAppID;
    }
    
    public static String getAppID() {
        return TXCCommonUtil.mAppID;
    }
    
    public static String getUserId() {
        return TXCCommonUtil.mUserId;
    }
    
    public static void setUserId(final String mUserId) {
        TXCCommonUtil.mUserId = mUserId;
    }
    
    public static String getMD5(final String s) {
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(s.getBytes());
            final byte[] digest = instance.digest();
            final StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < digest.length; ++i) {
                int n = digest[i];
                if (n < 0) {
                    n += 256;
                }
                if (n < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(n));
            }
            return sb.toString();
        }
        catch (Exception ex) {
            return s;
        }
    }
    
    public static byte[] getMD5(final byte[] array) {
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(array);
            return instance.digest();
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static void saveString(final String s, final String s2) {
        final Context sApplicationContext = TXCCommonUtil.sApplicationContext;
        if (sApplicationContext == null) {
            return;
        }
        try {
            final SharedPreferences.Editor edit = sApplicationContext.getSharedPreferences("TXCCommonConfig", 0).edit();
            edit.putString(s, s2);
            edit.commit();
        }
        catch (Exception ex) {
            TXCLog.e("TXCCommonUtil", "save string failed", ex);
        }
    }
    
    public static String loadString(final String s) {
        final Context sApplicationContext = TXCCommonUtil.sApplicationContext;
        if (sApplicationContext == null) {
            return "";
        }
        String string;
        try {
            string = sApplicationContext.getSharedPreferences("TXCCommonConfig", 0).getString(s, "");
        }
        catch (Exception ex) {
            string = "";
            TXCLog.e("TXCCommonUtil", "load string failed.", ex);
        }
        return string;
    }
    
    public static void saveUInt64(final String s, final long n) {
        final Context sApplicationContext = TXCCommonUtil.sApplicationContext;
        if (sApplicationContext == null) {
            return;
        }
        try {
            final SharedPreferences.Editor edit = sApplicationContext.getSharedPreferences("TXCCommonConfig", 0).edit();
            edit.putLong(s, n);
            edit.commit();
        }
        catch (Exception ex) {
            TXCLog.e("TXCCommonUtil", "save uint64 failed.", ex);
        }
    }
    
    public static long loadUInt64(final String s) {
        final Context sApplicationContext = TXCCommonUtil.sApplicationContext;
        if (sApplicationContext == null) {
            return 0L;
        }
        long long1;
        try {
            long1 = sApplicationContext.getSharedPreferences("TXCCommonConfig", 0).getLong(s, 0L);
        }
        catch (Exception ex) {
            long1 = 0L;
            TXCLog.e("TXCCommonUtil", "load uint64 failed.", ex);
        }
        return long1;
    }
    
    public static void zip(final ArrayList<String> list, final String s) {
        final File file = new File(s);
        InputStream inputStream = null;
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(file));
            zipOutputStream.setComment("LiteAV log");
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                final File file2 = new File(iterator.next());
                try {
                    Label_0111: {
                        if (file2.length() != 0L) {
                            if (file2.length() <= 8388608L) {
                                break Label_0111;
                            }
                        }
                    }
                    inputStream = new FileInputStream(file2);
                    zipOutputStream.putNextEntry(new ZipEntry(file2.getName()));
                    final byte[] array = new byte[8192];
                    int read;
                    while ((read = inputStream.read(array)) != -1) {
                        zipOutputStream.write(array, 0, read);
                    }
                }
                catch (Exception ex) {
                    TXCLog.e("TXCCommonUtil", "zip failed.", ex);
                }
                finally {
                    try {
                        inputStream.close();
                    }
                    catch (Exception ex2) {}
                }
            }
        }
        catch (FileNotFoundException ex3) {
            TXCLog.w("TXCCommonUtil", "zip log error");
        }
        finally {
            try {
                zipOutputStream.close();
            }
            catch (Exception ex4) {}
        }
    }
    
    public static String getLogUploadPath() {
        if (TXCCommonUtil.sApplicationContext == null) {
            return "";
        }
        final File externalFilesDir = TXCCommonUtil.sApplicationContext.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            return "";
        }
        return externalFilesDir.getAbsolutePath() + "/log/tencent/liteav";
    }
    
    public static int getGateway() {
        if (TXCCommonUtil.sApplicationContext == null) {
            return 0;
        }
        int gateway;
        try {
            gateway = ((WifiManager)TXCCommonUtil.sApplicationContext.getSystemService("wifi")).getDhcpInfo().gateway;
        }
        catch (Exception ex) {
            TXCLog.e("TXCCommonUtil", "getGateway error ", ex);
            gateway = 0;
        }
        return gateway;
    }
    
    public static boolean equals(final Object o, final Object o2) {
        return o == o2 || (o != null && o.equals(o2));
    }
    
    public static String getAppPackageName() {
        return f.c(TXCCommonUtil.sApplicationContext);
    }
    
    public static String getAppFilePath() {
        String absolutePath = "/sdcard/liteav";
        if (TXCCommonUtil.sApplicationContext != null) {
            absolutePath = TXCCommonUtil.sApplicationContext.getFilesDir().getAbsolutePath();
        }
        final File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return absolutePath;
    }
    
    private static native String nativeGetSDKVersion();
    
    private static native int nativeGetSDKID();
    
    private static native String nativeGetConfigCenterKey();
    
    static {
        TXCCommonUtil.mAppID = "";
        TXCCommonUtil.mStrAppVersion = "";
        TXCCommonUtil.pituLicencePath = "YTFaceSDK.licence";
        TXCCommonUtil.sApplicationContext = null;
        TXCCommonUtil.mUserId = "";
        f.f();
    }
}
