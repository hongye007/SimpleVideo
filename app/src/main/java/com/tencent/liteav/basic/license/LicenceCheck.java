package com.tencent.liteav.basic.license;

import com.tencent.liteav.basic.datareport.*;
import com.tencent.liteav.basic.util.*;
import java.io.*;
import com.tencent.liteav.basic.log.*;
import android.text.*;
import android.content.*;
import java.text.*;
import android.util.*;
import javax.crypto.spec.*;
import javax.crypto.*;
import java.security.spec.*;
import org.json.*;
import android.os.*;
import android.app.*;
import java.util.*;
import java.security.*;

public class LicenceCheck
{
    private Context a;
    private String b;
    private String c;
    private static LicenceCheck d;
    private a e;
    private a f;
    
    public static LicenceCheck a() {
        if (LicenceCheck.d == null) {
            LicenceCheck.d = new LicenceCheck();
        }
        return LicenceCheck.d;
    }
    
    private LicenceCheck() {
        this.b = "YTFaceSDK.licence";
        this.c = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4teqkW/TUruU89ElNVd\nKrpSL+HCITruyb6BS9mW6M4mqmxDhazDmQgMKNfsA0d2kxFucCsXTyesFNajaisk\nrAzVJpNGO75bQFap4jYzJYskIuas6fgIS7zSmGXgRcp6i0ZBH3pkVCXcgfLfsVCO\n+sN01jFhFgOC0LY2f1pJ+3jqktAlMIxy8Q9t7XwwL5/n8/Sledp7TwuRdnl2OPl3\nycCTRkXtOIoRNB9vgd9XooTKiEdCXC7W9ryvtwCiAB82vEfHWXXgzhsPC13URuFy\n1JqbWJtTCCcfsCVxuBplhVJAQ7JsF5SMntdJDkp7rJLhprgsaim2CRjcVseNmw97\nbwIDAQAB";
        this.e = new a("TXUgcSDK.licence");
        this.f = new a("TXLiveSDK.licence");
    }
    
    public void a(final Context context, final String s, final String s2) {
        this.a(this.e, context, s, s2);
        this.a(context);
    }
    
    private void a(final Context context) {
        try {
            if (Class.forName("com.tencent.qcloud.ugckit.UGCKit") != null) {
                TXCDRApi.txReportDAU(context, com.tencent.liteav.basic.datareport.a.br);
            }
        }
        catch (Exception ex) {}
    }
    
    public void b(final Context context, final String s, final String s2) {
        this.a(this.f, context, s, s2);
    }
    
    private void a(final a a, final Context appContext, final String e, final String d) {
        if (appContext != null) {
            this.a = appContext.getApplicationContext();
            TXCCommonUtil.setAppContext(appContext);
        }
        a.d = d;
        a.e = e;
        if (this.a != null && this.d()) {
            final File externalFilesDir = this.a.getExternalFilesDir((String)null);
            if (externalFilesDir != null) {
                a.c = externalFilesDir.getAbsolutePath();
            }
            if (!this.b(a.c + File.separator + a.a)) {
                TXCLog.i("LicenceCheck", "setLicense, sdcard file not exist, to download");
                this.b(a, "");
            }
            this.a(a);
        }
    }
    
    public void a(final a a) {
        if (TextUtils.isEmpty((CharSequence)a.e)) {
            TXCLog.e("LicenceCheck", "downloadLicense, mUrl is empty, ignore!");
            return;
        }
        if (a.f) {
            TXCLog.i("LicenceCheck", "downloadLicense, in downloading, ignore");
            return;
        }
        final b b = new b() {
            @Override
            public void a(final File file, final String s) {
                if (file == null) {
                    TXCLog.i("LicenceCheck", "downloadLicense, license not modified");
                    return;
                }
                LicenceCheck.this.b(a, s);
                TXCLog.i("LicenceCheck", "downloadLicense, onSaveSuccess");
                final String a = LicenceCheck.this.h(a);
                if (TextUtils.isEmpty((CharSequence)a)) {
                    TXCLog.e("LicenceCheck", "downloadLicense, readDownloadTempLicence is empty!");
                    a.f = false;
                    return;
                }
                if (LicenceCheck.this.d(a, a) == 0) {
                    LicenceCheck.this.f(a);
                }
            }
            
            @Override
            public void a(final File file, final Exception ex) {
                TXCLog.i("LicenceCheck", "downloadLicense, onSaveFailed");
            }
            
            @Override
            public void a(final int n) {
                TXCLog.i("LicenceCheck", "downloadLicense, onProgressUpdate");
            }
            
            @Override
            public void a() {
                TXCLog.i("LicenceCheck", "downloadLicense, onProcessEnd");
                a.f = false;
            }
        };
        if (this.a == null) {
            TXCLog.e("LicenceCheck", "context is NULL !!! Please set context in method:setLicense(Context context, String url, String key)");
            return;
        }
        final File externalFilesDir = this.a.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            TXCLog.e("LicenceCheck", "Please check permission WRITE_EXTERNAL_STORAGE permission has been set !!!");
            return;
        }
        final String b2 = this.b(a);
        a.c = externalFilesDir.getAbsolutePath();
        new Thread(new c(this.a, a.e, a.c, a.b, b, false, b2)).start();
        a.f = true;
    }
    
    public int a(final f f, final Context context) {
        return this.a(this.e, f, context);
    }
    
    public int b(final f f, final Context context) {
        return this.a(this.f, f, context);
    }
    
    private String b(final a a) {
        if (this.a == null) {
            return null;
        }
        return this.a.getSharedPreferences("LicenceCheck.lastModified", 0).getString(a.a + ".lastModified", (String)null);
    }
    
    private void b(final a a, final String s) {
        if (this.a == null) {
            return;
        }
        final SharedPreferences.Editor edit = this.a.getSharedPreferences("LicenceCheck.lastModified", 0).edit();
        edit.putString(a.a + ".lastModified", s);
        edit.commit();
    }
    
    private int a(final a a, final f f, final Context context) {
        final int a2 = this.a(a, context);
        if (a2 != 0) {
            this.a(a);
        }
        if (f != null) {
            f.a = a.i;
        }
        return a2;
    }
    
    private int a(final a a, final Context a2) {
        if (a.g) {
            return 0;
        }
        if (this.a == null) {
            this.a = a2;
        }
        if (this.d(a) == 0) {
            a.g = true;
            return 0;
        }
        final int c = this.c(a);
        if (c == 0) {
            a.g = true;
            return 0;
        }
        return c;
    }
    
    private int c(final a a) {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            TXCLog.e("LicenceCheck", "checkSdcardLicence, sdcard not mounted yet!");
            return -10;
        }
        final File externalFilesDir = this.a.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            TXCLog.e("LicenceCheck", "checkSdcardLicence, mContext.getExternalFilesDir is null!");
            return -10;
        }
        final String string = externalFilesDir.getAbsolutePath() + File.separator + a.a;
        if (!this.b(string)) {
            return -7;
        }
        final String b = com.tencent.liteav.basic.util.c.b(string);
        if (TextUtils.isEmpty((CharSequence)b)) {
            TXCLog.e("LicenceCheck", "checkSdcardLicence, licenceSdcardStr is empty");
            return -8;
        }
        return this.a(a, b);
    }
    
    private int d(final a a) {
        if (!this.e(a)) {
            return -6;
        }
        final String b = com.tencent.liteav.basic.util.c.b(this.a, a.a);
        if (TextUtils.isEmpty((CharSequence)b)) {
            TXCLog.e("LicenceCheck", "checkAssetLicence, licenceSdcardStr is empty");
            return -8;
        }
        return this.a(a, b);
    }
    
    public int a(final a a, final String s) {
        try {
            final JSONObject jsonObject = new JSONObject(s);
            return this.d(a, s);
        }
        catch (JSONException ex) {
            if (a == this.f) {
                return -1;
            }
            return this.e(a, s);
        }
    }
    
    private boolean d() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            TXCLog.e("LicenceCheck", "checkSdcardLicence, sdcard not mounted yet!");
            return false;
        }
        if (this.a.getExternalFilesDir((String)null) == null) {
            TXCLog.e("LicenceCheck", "checkSdcardLicence, mContext.getExternalFilesDir is null!");
            return false;
        }
        return true;
    }
    
    private boolean e(final a a) {
        return com.tencent.liteav.basic.util.c.a(this.a, a.a);
    }
    
    private boolean b(final String s) {
        return com.tencent.liteav.basic.util.c.a(s);
    }
    
    private void f(final a a) {
        final File externalFilesDir = this.a.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            TXCLog.i("LicenceCheck", "saveTempLocal sdcardDir is null");
            return;
        }
        final File file = new File(externalFilesDir.getAbsolutePath() + File.separator + a.a);
        if (file.exists()) {
            TXCLog.i("LicenceCheck", "delete dst file:" + file.delete());
        }
        final File file2 = new File(a.c + File.separator + a.b);
        if (file2.exists()) {
            TXCLog.i("LicenceCheck", "rename file:" + file2.renameTo(file));
        }
        a.g = true;
    }
    
    private static long c(final String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s).getTime();
        }
        catch (Exception ex) {
            TXCLog.e("LicenceCheck", "time str to millsecond failed.", ex);
            return -1L;
        }
    }
    
    public PublicKey a(final String s) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(s, 0)));
    }
    
    private String c(final a a, final String s) {
        if (TextUtils.isEmpty((CharSequence)a.d)) {
            TXCLog.e("LicenceCheck", "decodeLicence, mKey is empty!!!");
            return "";
        }
        final byte[] bytes = a.d.getBytes();
        final SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");
        final IvParameterSpec ivParameterSpec = new IvParameterSpec(this.nativeIvParameterSpec(bytes));
        final byte[] decode = Base64.decode(s, 0);
        String s2;
        try {
            final Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, ivParameterSpec);
            s2 = new String(instance.doFinal(decode), "UTF-8");
            TXCLog.i("LicenceCheck", "decodeLicence : " + s2);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            s2 = null;
        }
        return s2;
    }
    
    private native byte[] nativeIvParameterSpec(final byte[] p0);
    
    private int d(final a a, final String s) {
        String string;
        String string2;
        try {
            final JSONObject jsonObject = new JSONObject(s);
            final int optInt = jsonObject.optInt("appId");
            string = jsonObject.getString("encryptedLicense");
            string2 = jsonObject.getString("signature");
            TXCLog.i("LicenceCheck", "appid:" + optInt);
            TXCLog.i("LicenceCheck", "encryptedLicense:" + string);
            TXCLog.i("LicenceCheck", "signature:" + string2);
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            this.a(-1);
            return -1;
        }
        return this.a(a, string, string2);
    }
    
    private int a(final a a, final String s, final String s2) {
        boolean a2 = false;
        try {
            a2 = a(Base64.decode(s, 0), Base64.decode(s2, 0), this.a(this.c));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("LicenceCheck", "verifyLicence, exception is : " + ex);
        }
        if (!a2) {
            this.a(-2);
            TXCLog.e("LicenceCheck", "verifyLicence, signature not pass!");
            return -2;
        }
        final String c = this.c(a, s);
        if (TextUtils.isEmpty((CharSequence)c)) {
            this.a(-3);
            TXCLog.e("LicenceCheck", "verifyLicence, decodeValue is empty!");
            return -3;
        }
        a.i = c;
        try {
            final JSONObject jsonObject = new JSONObject(c);
            final String string = jsonObject.getString("pituLicense");
            final JSONArray optJSONArray = jsonObject.optJSONArray("appData");
            if (optJSONArray == null) {
                TXCLog.e("LicenceCheck", "verifyLicence, appDataArray is null!");
                this.a(-1);
                return -1;
            }
            boolean b = false;
            boolean b2 = false;
            int a3 = 0;
            for (int i = 0; i < optJSONArray.length(); ++i) {
                final JSONObject jsonObject2 = optJSONArray.getJSONObject(i);
                final String optString = jsonObject2.optString("packageName");
                TXCLog.i("LicenceCheck", "verifyLicence, packageName:" + optString);
                if (!optString.equals(this.a.getPackageName())) {
                    TXCLog.e("LicenceCheck", "verifyLicence, packageName not match!");
                }
                else {
                    b = true;
                    if (!this.d(jsonObject2.optString("endDate"))) {
                        b2 = true;
                        a3 = (this.a(a, jsonObject2, string) ? 1 : 0);
                        if (a3 != 0) {
                            a3 = 1;
                            break;
                        }
                    }
                }
            }
            if (!b) {
                this.a(-4);
                return -4;
            }
            if (!b2) {
                this.a(-5);
                return -5;
            }
            if (a3 == 0) {
                this.a(-11);
                return -11;
            }
            if (!TextUtils.isEmpty((CharSequence)string)) {
                try {
                    final byte[] decode = Base64.decode(string, 0);
                    final File externalFilesDir = this.a.getExternalFilesDir((String)null);
                    if (externalFilesDir == null) {
                        return -10;
                    }
                    final File file = new File(externalFilesDir.getAbsolutePath() + File.separator + this.b);
                    com.tencent.liteav.basic.util.c.a(file.getAbsolutePath(), decode);
                    TXCCommonUtil.setPituLicencePath(file.getAbsolutePath());
                }
                catch (Exception ex2) {
                    TXCLog.e("LicenceCheck", "decode pitu license error:" + ex2);
                }
            }
            TXCDRApi.txReportDAU(this.a, a.aI);
        }
        catch (JSONException ex3) {
            ex3.printStackTrace();
            TXCLog.e("LicenceCheck", "verifyLicence, json format error ! exception = " + ex3);
            this.a(-1);
            return -1;
        }
        return 0;
    }
    
    private boolean a(final a a, final JSONObject jsonObject, final String s) {
        boolean b = false;
        final int optInt = jsonObject.optInt("feature");
        if (a == this.f) {
            final int h = optInt >> 4 & 0xF;
            if (h >= 1) {
                a.h = h;
                b = true;
            }
            TXCLog.i("LicenceCheck", "live parseVersionType, mLicenceVersionType = " + a.h);
        }
        else if (a == this.e) {
            final int n = optInt;
            final int h2 = optInt & 0xF;
            if (h2 <= 1) {
                if (h2 == 1 || n == 0) {
                    if (!TextUtils.isEmpty((CharSequence)s)) {
                        a.h = 5;
                    }
                    else {
                        a.h = 3;
                    }
                    b = true;
                }
            }
            else {
                a.h = h2;
                b = true;
            }
            TXCLog.i("LicenceCheck", "ugc parseVersionType, mLicenceVersionType = " + a.h);
        }
        return b;
    }
    
    public int b() {
        return this.g(this.e);
    }
    
    public int c() {
        return this.g(this.f);
    }
    
    private int g(final a a) {
        return a.h;
    }
    
    private void a(final int n) {
        TXCDRApi.txReportDAU(this.a, com.tencent.liteav.basic.datareport.a.aJ, n, "");
    }
    
    private boolean d(final String s) {
        final long c = c(s);
        if (c < 0L) {
            TXCLog.e("LicenceCheck", "checkEndDate, end date millis < 0!");
            return true;
        }
        if (c < System.currentTimeMillis()) {
            TXCLog.e("LicenceCheck", "checkEndDate, end date expire!");
            return true;
        }
        return false;
    }
    
    private int e(final a a, final String s) {
        final String e = this.e(s);
        if (TextUtils.isEmpty((CharSequence)e)) {
            TXCLog.e("LicenceCheck", "verifyOldLicence, decryptStr is empty");
            return -3;
        }
        a.i = e;
        try {
            final JSONObject jsonObject = new JSONObject(e);
            if (!jsonObject.getString("packagename").equals(b(this.a))) {
                TXCLog.e("LicenceCheck", "packagename not match!");
                this.a(-4);
                return -4;
            }
            if (this.d(jsonObject.getString("enddate"))) {
                return -5;
            }
        }
        catch (JSONException ex) {
            ex.printStackTrace();
            TXCLog.e("LicenceCheck", "verifyOldLicence, json format error !");
            this.a(-1);
            return -1;
        }
        a.h = 5;
        TXCDRApi.txReportDAU(this.a, a.aI);
        return 0;
    }
    
    private static String b(final Context context) {
        final int myPid = Process.myPid();
        for (final ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager)context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }
    
    private String e(final String s) {
        String s2 = null;
        try {
            s2 = new String(h.b(Base64.decode(s, 0), Base64.decode("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKfMXaF6wx9lev2U\nIzkk6ydI2sdaSQAD2ZvDBLq+5Fm6nGwSSWawl03D4vHcWIUa3wnz6f19/y8wzrj4\nnTfcEnT94SPdB6GhGsqPwbwRp9MHAqd/2gWZxSb005il2yiOZafk6X4NGKCn2tGd\nyNaCF+m9rLykuLdZHB0Z53ivgseNAgMBAAECgYAvXI2pAH+Goxwd6uwuOu9svTGT\nRzaHnI6VWmxBUZQeh3+TOW4iYAG03291GN6bY0RFCOWouSGH7lzK9NFbbPCAQ/hx\ncO48PqioHoq7K8sqzd3XaYBv39HrRnM8JvZsqv0PLJwX/LGm2y/MRaKAC6bcHtse\npgh+NNmUxXNRcTMRAQJBANezmenBcR8HTcY5YaEk3SQRzOo+QhIXuuD4T/FESpVJ\nmVQGxJjLsEBua1j38WG2QuepE5JiVbkQ0jQSvhUiZK0CQQDHJa+vWu6l72lQAvIx\nwmRISorvLb/tnu5bH0Ele42oX+w4p/tm03awdVjhVANnpDjYS2H6EzrF/pfis7k9\nV2phAkB4E4gz47bYYhV+qsTZkw70HGCpab0YG1OyFylRkwW983nCl/3rXUChrZZe\nsbATCAZYtfuqOsmju2R5DpH4a+wFAkBmHlcWbmSNxlSUaM5U4b+WqlLQDv+qE6Na\nKo63b8HWI0n4S3tI4QqttZ7b/L66OKXFk/Ir0AyFVuX/o/VLFTZBAkAdSTEkGwE5\nGQmhxu95sKxmdlUY6Q0Gwwpi06C1BPBrj2VkGXpBP0twhPVAq/3xVjjb+2KXVTUW\nIpRLc06M4vhv", 0)));
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("LicenceCheck", "decryptLicenceStr, exception is : " + ex);
        }
        return s2;
    }
    
    private String h(final a a) {
        return com.tencent.liteav.basic.util.c.b(new File(a.c + File.separator + a.b).getAbsolutePath());
    }
    
    public static boolean a(final byte[] array, final byte[] array2, final PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        final Signature instance = Signature.getInstance("SHA256WithRSA");
        instance.initVerify(publicKey);
        instance.update(array);
        return instance.verify(array2);
    }
    
    private class a
    {
        String a;
        String b;
        String c;
        String d;
        String e;
        boolean f;
        boolean g;
        int h;
        String i;
        
        public a(final String a) {
            this.a = a;
            this.b = a + ".tmp";
            this.c = "";
            this.d = "";
            this.e = "";
            this.f = false;
            this.g = false;
            this.h = -1;
            this.i = "";
        }
    }
}
