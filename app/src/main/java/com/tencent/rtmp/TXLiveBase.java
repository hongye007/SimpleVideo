package com.tencent.rtmp;

import android.content.*;
import com.tencent.liteav.basic.license.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.datareport.*;
import com.tencent.liteav.basic.util.*;
import android.util.*;
import com.tencent.liteav.*;
import java.security.*;
import java.io.*;
import java.math.*;

public class TXLiveBase
{
    private static final String TAG = "TXLiveBase";
    private static ITXLiveBaseListener listener;
    private static TXLiveBase instance;
    private static final String FILE_MD5_LITEAV_ARM = "___md5_libliteavsdk_arm_md5_____";
    private static final String FILE_MD5_SATURN_ARM = "___md5_libsaturn_arm_md5________";
    private static final String FILE_MD5_FFMPEG_ARM = "___md5_libtxffmpeg_arm_md5______";
    private static final String FILE_MD5_TRAE_ARM = "___md5_libtraeimp_arm_md5_______";
    private static final String FILE_MD5_LITEAV_V7A = "___md5_libliteavsdk_v7a_md5_____";
    private static final String FILE_MD5_SATURN_V7A = "___md5_libsaturn_v7a_md5________";
    private static final String FILE_MD5_FFMPEG_V7A = "___md5_libtxffmpeg_v7a_md5______";
    private static final String FILE_MD5_TRAE_V7A = "___md5_libtraeimp_v7a_md5_______";
    private static final String FILE_MD5_LITEAV_V64 = "___md5_libliteavsdk_v64_md5_____";
    private static final String FILE_MD5_SATURN_V64 = "___md5_libsaturn_v64_md5________";
    private static final String FILE_MD5_FFMPEG_V64 = "___md5_libtxffmpeg_v64_md5______";
    private static final String FILE_MD5_TRAE_V64 = "___md5_libtraeimp_v64_md5_______";
    
    private TXLiveBase() {
    }
    
    public static TXLiveBase getInstance() {
        return TXLiveBase.instance;
    }
    
    public void setLicence(final Context context, final String s, final String s2) {
        LicenceCheck.a().b(context, s, s2);
    }
    
    public String getLicenceInfo(final Context context) {
        final f f = new f();
        LicenceCheck.a().b(f, context);
        return f.a;
    }
    
    public static void setListener(final ITXLiveBaseListener listener) {
        TXCLog.setListener(new a());
        TXLiveBase.listener = listener;
    }
    
    public static void setLogLevel(final int level) {
        TXCLog.setLevel(level);
    }
    
    public static void setConsoleEnabled(final boolean consoleEnabled) {
        TXCLog.setConsoleEnabled(consoleEnabled);
    }
    
    public static void setAppVersion(final String appVersion) {
        TXCDRApi.txSetAppVersion(appVersion);
        TXCCommonUtil.setAppVersion(appVersion);
    }
    
    public static void setLibraryPath(final String s) {
        Log.i("TXLiveBase", "setLibraryPath " + s);
        com.tencent.liteav.basic.util.f.b(s);
    }
    
    public static boolean isLibraryPathValid(final String s) {
        final String fileMD5 = getFileMD5(s, "libliteavsdk.so");
        final String fileMD6 = getFileMD5(s, "libsaturn.so");
        final String fileMD7 = getFileMD5(s, "libtxffmpeg.so");
        final String fileMD8 = getFileMD5(s, "libtraeimp-rtmp.so");
        Log.e("TXLiveBase", "MD5-CHECK-V64 libliteavsdk = " + fileMD5 + " FILE_MD5_LITEAV_V64 = " + "___md5_libliteavsdk_v64_md5_____");
        Log.e("TXLiveBase", "MD5-CHECK-V64 libsaturn    = " + fileMD6 + " FILE_MD5_SATURN_V64 = " + "___md5_libsaturn_v64_md5________");
        Log.e("TXLiveBase", "MD5-CHECK-V64 libtxffmpeg  = " + fileMD7 + " FILE_MD5_FFMPEG_V64 = " + "___md5_libtxffmpeg_v64_md5______");
        Log.e("TXLiveBase", "MD5-CHECK-V64 libtraeimpl  = " + fileMD8 + " FILE_MD5_TRAE_V64   = " + "___md5_libtraeimp_v64_md5_______");
        if (fileMD5 != null && fileMD5.equalsIgnoreCase("___md5_libliteavsdk_v64_md5_____") && fileMD6 != null && fileMD6.equalsIgnoreCase("___md5_libsaturn_v64_md5________") && fileMD7 != null && fileMD7.equalsIgnoreCase("___md5_libtxffmpeg_v64_md5______") && fileMD8 != null && fileMD8.equalsIgnoreCase("___md5_libtraeimp_v64_md5_______")) {
            return true;
        }
        Log.e("TXLiveBase", "MD5-CHECK-V7A libliteavsdk = " + fileMD5 + " FILE_MD5_LITEAV_V7A = " + "___md5_libliteavsdk_v7a_md5_____");
        Log.e("TXLiveBase", "MD5-CHECK-V7A libsaturn    = " + fileMD6 + " FILE_MD5_SATURN_V7A = " + "___md5_libsaturn_v7a_md5________");
        Log.e("TXLiveBase", "MD5-CHECK-V7A libtxffmpeg  = " + fileMD7 + " FILE_MD5_FFMPEG_V7A = " + "___md5_libtxffmpeg_v7a_md5______");
        Log.e("TXLiveBase", "MD5-CHECK-V7A libtraeimpl  = " + fileMD8 + " FILE_MD5_TRAE_V7A   = " + "___md5_libtraeimp_v7a_md5_______");
        if (fileMD5 != null && fileMD5.equalsIgnoreCase("___md5_libliteavsdk_v7a_md5_____") && fileMD6 != null && fileMD6.equalsIgnoreCase("___md5_libsaturn_v7a_md5________") && fileMD7 != null && fileMD7.equalsIgnoreCase("___md5_libtxffmpeg_v7a_md5______") && fileMD8 != null && fileMD8.equalsIgnoreCase("___md5_libtraeimp_v7a_md5_______")) {
            return true;
        }
        Log.e("TXLiveBase", "MD5-CHECK-ARM libliteavsdk = " + fileMD5 + " FILE_MD5_LITEAV_ARM = " + "___md5_libliteavsdk_arm_md5_____");
        Log.e("TXLiveBase", "MD5-CHECK-ARM libsaturn    = " + fileMD6 + " FILE_MD5_SATURN_ARM = " + "___md5_libsaturn_arm_md5________");
        Log.e("TXLiveBase", "MD5-CHECK-ARM libtxffmpeg  = " + fileMD7 + " FILE_MD5_FFMPEG_ARM = " + "___md5_libtxffmpeg_arm_md5______");
        Log.e("TXLiveBase", "MD5-CHECK-ARM libtraeimpl  = " + fileMD8 + " FILE_MD5_TRAE_ARM   = " + "___md5_libtraeimp_arm_md5_______");
        return fileMD5 != null && fileMD5.equalsIgnoreCase("___md5_libliteavsdk_arm_md5_____") && fileMD6 != null && fileMD6.equalsIgnoreCase("___md5_libsaturn_arm_md5________") && fileMD7 != null && fileMD7.equalsIgnoreCase("___md5_libtxffmpeg_arm_md5______") && fileMD8 != null && fileMD8.equalsIgnoreCase("___md5_libtraeimp_arm_md5_______");
    }
    
    public static String getSDKVersionStr() {
        return TXCCommonUtil.getSDKVersionStr();
    }
    
    public static void setPituLicencePath(final String pituLicencePath) {
        TXCCommonUtil.setPituLicencePath(pituLicencePath);
    }
    
    public static String getPituSDKVersion() {
        return u.a();
    }
    
    public static void setAppID(final String appID) {
        TXCCommonUtil.setAppID(appID);
    }
    
    private static String getFileMD5(final String s, final String s2) {
        final File file = new File(s, s2);
        if (!file.exists() || !file.isFile()) {
            return null;
        }
        MessageDigest instance = null;
        final byte[] array = new byte[1024];
        try {
            instance = MessageDigest.getInstance("MD5");
            final FileInputStream fileInputStream = new FileInputStream(file);
            int read;
            while ((read = fileInputStream.read(array, 0, 1024)) != -1) {
                instance.update(array, 0, read);
            }
            fileInputStream.close();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        catch (FileNotFoundException ex2) {
            ex2.printStackTrace();
        }
        catch (IOException ex3) {
            ex3.printStackTrace();
        }
        return new BigInteger(1, instance.digest()).toString(16);
    }
    
    public static void setUserId(final String userId) {
        TXCCommonUtil.setUserId(userId);
    }
    
    static {
        TXLiveBase.listener = null;
        TXLiveBase.instance = new TXLiveBase();
    }
    
    private static class a implements TXCLog.a
    {
        @Override
        public void a(final int n, final String s, final String s2) {
            if (TXLiveBase.listener != null) {
                TXLiveBase.listener.OnLog(n, s, s2);
            }
        }
    }
}
