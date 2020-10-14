package com.tencent.liteav;

import java.text.*;
import java.util.*;
import com.tencent.liteav.basic.util.*;
import android.text.*;
import java.net.*;
import java.io.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;

public class m
{
    private String a;
    private String b;
    private int c;
    private String d;
    private String e;
    private long f;
    
    public m() {
        this.a = "";
        this.b = "";
        this.c = 0;
        this.d = "";
        this.e = "";
        this.f = 0L;
    }
    
    public long a() {
        return System.currentTimeMillis() - this.f;
    }
    
    public String a(final long n) {
        final String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(this.f + n * 1000L));
        String s;
        if (this.c < 0) {
            s = String.format("http://%s/timeshift/%s/%s/timeshift.m3u8?delay=%d", this.a, this.e, this.b, (System.currentTimeMillis() - this.f - n) / 1000L);
        }
        else {
            s = String.format("http://%s/%s/%s/timeshift.m3u8?starttime=%s&appid=%s&txKbps=0", this.a, this.c, this.b, format, this.d);
        }
        return s;
    }
    
    public int a(final String s, final String s2, final int n, final a a) {
        if (s == null || s.isEmpty()) {
            return -1;
        }
        this.d = TXCCommonUtil.getAppID();
        if (TextUtils.isEmpty((CharSequence)this.d)) {
            return -2;
        }
        AsyncTask.execute((Runnable)new Runnable() {
            @Override
            public void run() {
                m.this.f = System.currentTimeMillis();
                m.this.b = "";
                m.this.c = n;
                m.this.a = s2;
                m.this.b = TXCCommonUtil.getStreamIDByStreamUrl(s);
                m.this.e = TXCCommonUtil.getAppNameByStreamUrl(s);
                if (m.this.e == null) {
                    m.this.e = "live";
                }
                String s;
                if (m.this.c < 0) {
                    s = String.format("http://%s/timeshift/%s/%s/timeshift.m3u8?delay=0", m.this.a, m.this.e, m.this.b);
                }
                else {
                    s = String.format("http://%s/%s/%s/timeshift.m3u8?delay=0&appid=%s&txKbps=0", m.this.a, m.this.c, m.this.b, m.this.d);
                }
                try {
                    final HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(s).openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Charsert", "UTF-8");
                    httpURLConnection.setRequestProperty("Content-Type", "text/plain;");
                    String string = "";
                    String line;
                    while ((line = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream())).readLine()) != null) {
                        string += line;
                    }
                    TXCLog.i("TXCTimeShiftUtil", "prepareSeekTime: receive response, strResponse = " + string);
                    final String d = m.this.a(string);
                    if (d != null) {
                        m.this.f = Long.parseLong(d) * 1000L;
                    }
                }
                catch (Exception ex) {
                    m.this.f = System.currentTimeMillis();
                    TXCLog.e("TXCTimeShiftUtil", "prepareSeekTime error " + ex.toString());
                }
                final long currentTimeMillis = System.currentTimeMillis();
                TXCLog.i("TXCTimeShiftUtil", "live start time:" + m.this.f + ",currentTime:" + currentTimeMillis + ",diff:" + (currentTimeMillis - m.this.f));
                final long n = currentTimeMillis - m.this.f;
                if (a != null) {
                    new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            a.a(n);
                        }
                    });
                }
            }
        });
        return 0;
    }
    
    private String a(final String s) {
        if (s.contains("#EXT-TX-TS-START-TIME")) {
            final int n = s.indexOf("#EXT-TX-TS-START-TIME:") + 22;
            if (n > 0) {
                final String substring = s.substring(n);
                final int index = substring.indexOf("#");
                if (index > 0) {
                    return substring.substring(0, index).replaceAll("\r\n", "");
                }
            }
        }
        return null;
    }
    
    public interface a
    {
        void a(final long p0);
    }
}
