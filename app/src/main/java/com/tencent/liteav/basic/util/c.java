package com.tencent.liteav.basic.util;

import android.content.*;
import android.net.*;
import com.tencent.liteav.basic.log.*;
import android.content.res.*;
import android.text.*;
import java.io.*;

public class c
{
    public static boolean a(final Context context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    
    public static boolean a(final long n) {
        return true;
    }
    
    public static long a(final File file) {
        return a(file, 5);
    }
    
    public static long a(final File file, final int n) {
        long n2 = 0L;
        if (n <= 0) {
            return n2;
        }
        try {
            for (final File file2 : file.listFiles()) {
                if (file2.isDirectory()) {
                    n2 += a(file2, n - 1);
                }
                else {
                    n2 += file2.length();
                }
            }
        }
        catch (Exception ex) {
            TXCLog.i("FileUtil", "getFolderSize exception " + ex.toString());
        }
        return n2;
    }
    
    public static void a(final Context context, final String s, final String s2) {
        final AssetManager assets = context.getAssets();
        InputStream open = null;
        OutputStream outputStream = null;
        try {
            open = assets.open(s);
            outputStream = new FileOutputStream(s2);
            a(open, outputStream);
        }
        catch (IOException ex) {
            TXCLog.e("FileUtil", "copy asset file failed.", ex);
        }
        finally {
            a(open);
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                }
                catch (IOException ex2) {}
            }
        }
    }
    
    public static void a(final InputStream inputStream, final OutputStream outputStream) throws IOException {
        final byte[] array = new byte[1024];
        int read;
        while ((read = inputStream.read(array)) != -1) {
            outputStream.write(array, 0, read);
        }
    }
    
    public static boolean a(final String s) {
        if (TextUtils.isEmpty((CharSequence)s)) {
            return false;
        }
        final File file = new File(s);
        return file.exists() && file.isFile();
    }
    
    public static String b(final String s) {
        final File file = new File(s);
        final StringBuilder sb = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            return sb.toString();
        }
        catch (IOException ex) {
            throw new RuntimeException("IOException occurred. ", ex);
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException ex2) {
                    throw new RuntimeException("IOException occurred. ", ex2);
                }
            }
        }
    }
    
    public static void a(final String s, final byte[] array) {
        FilterOutputStream filterOutputStream = null;
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(new File(s));
            filterOutputStream = new BufferedOutputStream(outputStream);
            filterOutputStream.write(array);
        }
        catch (Exception ex) {
            TXCLog.e("FileUtil", "write file failed.", ex);
        }
        finally {
            try {
                if (filterOutputStream != null) {
                    filterOutputStream.close();
                }
                if (outputStream != null) {
                    ((FileOutputStream)outputStream).close();
                }
            }
            catch (Exception ex2) {}
        }
    }
    
    public static boolean a(final Context context, final String s) {
        final AssetManager assets = context.getAssets();
        try {
            final String[] list = assets.list("");
            for (int i = 0; i < list.length; ++i) {
                if (list[i].equals(s.trim())) {
                    TXCLog.i("isAssetFileExists", s + " exist");
                    return true;
                }
            }
        }
        catch (IOException ex) {
            TXCLog.i("isAssetFileExists", s + " not exist");
            return false;
        }
        TXCLog.i("isAssetFileExists", s + " not exist");
        return false;
    }
    
    public static String b(final Context context, final String s) {
        InputStream open = null;
        try {
            open = context.getAssets().open(s);
            final byte[] array = new byte[open.available()];
            if (open.read(array) <= 0) {
                return "";
            }
            open.close();
            return new String(array, "utf-8");
        }
        catch (IOException ex) {
            TXCLog.e("FileUtil", "read asset file failed.", ex);
        }
        finally {
            if (open != null) {
                try {
                    open.close();
                }
                catch (IOException ex2) {}
            }
        }
        return "";
    }
    
    public static void a(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        }
        catch (IOException ex) {}
    }
}
