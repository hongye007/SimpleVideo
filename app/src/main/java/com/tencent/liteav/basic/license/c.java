package com.tencent.liteav.basic.license;

import android.content.*;
import android.text.*;
import java.net.*;
import java.io.*;

public class c extends a
{
    private Context b;
    private String c;
    private String d;
    private String e;
    private b f;
    private long g;
    private long h;
    private boolean i;
    private String j;
    
    public c(final Context b, final String c, final String d, final String e, final b f, final boolean i, final String j) {
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.i = i;
        this.j = j;
    }
    
    @Override
    public void run() {
        if (!com.tencent.liteav.basic.util.c.a(this.b) || TextUtils.isEmpty((CharSequence)this.c) || TextUtils.isEmpty((CharSequence)this.d) || TextUtils.isEmpty((CharSequence)this.e) || !this.c.startsWith("http")) {
            this.a(null, 0);
            return;
        }
        final File file = new File(this.d);
        if (!file.exists()) {
            file.mkdirs();
        }
        else if (file.isFile() && this.f != null) {
            this.f.a(file, (Exception)null);
            return;
        }
        final File file2 = new File(this.d + File.separator + this.e);
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        boolean b = false;
        Exception ex = null;
        try {
            if (file2.exists()) {
                file2.delete();
            }
            file2.createNewFile();
            httpURLConnection = (HttpURLConnection)new URL(this.c).openConnection();
            if (!TextUtils.isEmpty((CharSequence)this.j)) {
                httpURLConnection.addRequestProperty("If-Modified-Since", this.j);
            }
            httpURLConnection.setConnectTimeout(30000);
            httpURLConnection.setReadTimeout(30000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            final int responseCode = httpURLConnection.getResponseCode();
            b = (httpURLConnection.getResponseCode() == 200);
            if (b) {
                final String headerField = httpURLConnection.getHeaderField("Last-Modified");
                this.j = headerField;
                if (this.i) {
                    this.g = httpURLConnection.getContentLength();
                    if (this.g <= 0L) {
                        if (this.f != null) {
                            this.f.a(file2, (Exception)null);
                        }
                        return;
                    }
                    if (!com.tencent.liteav.basic.util.c.a(this.g)) {
                        if (this.f != null) {
                            this.f.a(file2, (Exception)null);
                        }
                        return;
                    }
                }
                inputStream = httpURLConnection.getInputStream();
                final byte[] array = new byte[8192];
                fileOutputStream = new FileOutputStream(file2);
                this.h = 0L;
                int read;
                while ((read = inputStream.read(array)) != -1) {
                    fileOutputStream.write(array, 0, read);
                    if (this.i) {
                        final int n = (int)(this.h * 100L / this.g);
                        this.h += read;
                        final int n2 = (int)(this.h * 100L / this.g);
                        if (n == n2 || this.f == null) {
                            continue;
                        }
                        this.f.a(n2);
                    }
                }
                fileOutputStream.flush();
                if (this.f != null) {
                    this.f.a(100);
                    this.f.a(file2, headerField);
                }
            }
            else if (responseCode == 304) {
                if (this.f != null) {
                    this.f.a(100);
                    this.f.a(null, this.j);
                }
            }
            else {
                ex = new d("http status got exception. code = " + responseCode);
            }
        }
        catch (Exception ex2) {
            ex = ex2;
        }
        finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                if (this.f != null) {
                    this.f.a();
                }
            }
            catch (IOException ex3) {}
        }
        if ((!b || null != ex) && this.f != null) {
            this.f.a(file2, (Exception)null);
        }
    }
    
    private void a(final Exception ex, final int n) {
        if (this.f != null) {
            this.f.a(null, ex);
        }
        this.f = null;
    }
}
