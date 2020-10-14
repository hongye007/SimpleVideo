package com.tencent.liteav.f;

import java.util.concurrent.*;

import com.tencent.liteav.CameraProxy;

import java.util.*;
import android.text.*;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.basic.log.*;
import org.json.*;
import java.io.*;

public class a extends c
{
    private static a d;
    private List<com.tencent.liteav.i.a.b> e;
    private CopyOnWriteArrayList<CaptureAndEnc.a> f;
    
    public static a a() {
        if (a.d == null) {
            a.d = new a();
        }
        return a.d;
    }
    
    private a() {
        this.f = new CopyOnWriteArrayList<CaptureAndEnc.a>();
    }
    
    public void a(final List<com.tencent.liteav.i.a.b> e) {
        this.e = e;
        this.f.clear();
        if (this.c != null) {
            this.a(this.c);
        }
    }
    
    public List<CaptureAndEnc.a> b() {
        return this.f;
    }
    
    public void a(final e e) {
        if (e == null) {
            return;
        }
        if (this.a == 0 || this.b == 0) {
            return;
        }
        if (this.e == null || this.e.size() == 0) {
            return;
        }
        final g b = this.b(e);
        for (final com.tencent.liteav.i.a.b b2 : this.e) {
            if (b2 == null) {
                continue;
            }
            final com.tencent.liteav.i.a.b a = this.a(b2, this.a(b2.b, b));
            final CameraProxy.a a2 = this.a(a.a);
            if (a2 == null) {
                continue;
            }
            if (a2.c <= 0) {
                continue;
            }
            long c = a.c;
            final long n = a.d - c;
            final int n2 = a2.b / a2.c;
            int n3 = (int)(n / a2.b);
            if (n % a2.b > 0L) {
                ++n3;
            }
            for (int i = 0; i < n3; ++i) {
                CaptureAndEnc.a a4;
                for (int n4 = 0; n4 < a2.c && c + n2 <= a.d; c = a4.d, ++n4) {
                    final CameraProxy.a.a a3 = a2.g.get(n4);
                    a4 = new CaptureAndEnc.a();
                    a4.a = a.a + a3.a + ".png";
                    a4.b = a.b;
                    a4.c = c;
                    a4.d = c + n2;
                    a4.e = a.e;
                    this.f.add(a4);
                }
            }
        }
    }
    
    private CameraProxy.a a(final String s) {
        final String b = this.b(s + "config.json");
        if (TextUtils.isEmpty((CharSequence)b)) {
            return null;
        }
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(b);
        }
        catch (JSONException ex) {
            TXCLog.e("AnimatedPasterFilterChain", "parse invalid json string", (Throwable)ex);
            return null;
        }
        final CameraProxy.a a = new CameraProxy.a();
        try {
            a.a = jsonObject.getString("name");
            a.c = jsonObject.getInt("count");
            a.b = jsonObject.getInt("period");
            a.d = jsonObject.getInt("width");
            a.e = jsonObject.getInt("height");
            a.f = jsonObject.getInt("keyframe");
            final JSONArray jsonArray = jsonObject.getJSONArray("frameArray");
            for (int i = 0; i < a.c; ++i) {
                final JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                final CameraProxy.a.a a2 = new CameraProxy.a.a();
                a2.a = jsonObject2.getString("picture");
                a.g.add(a2);
            }
        }
        catch (JSONException ex2) {
            TXCLog.e("AnimatedPasterFilterChain", "failed to get value from json.", (Throwable)ex2);
        }
        return a;
    }
    
    private String b(final String s) {
        String string = "";
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(s)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                string += line;
            }
            bufferedReader.close();
        }
        catch (IOException ex) {
            TXCLog.e("AnimatedPasterFilterChain", "read file failed.", ex);
        }
        finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                }
                catch (IOException ex2) {}
            }
        }
        return string;
    }
    
    private com.tencent.liteav.i.a.b a(final com.tencent.liteav.i.a.b b, final com.tencent.liteav.i.a.h b2) {
        final com.tencent.liteav.i.a.b b3 = new com.tencent.liteav.i.a.b();
        b3.b = b2;
        b3.a = b.a;
        b3.c = b.c;
        b3.d = b.d;
        b3.e = b.e;
        return b3;
    }
    
    public void c() {
        super.c();
        this.f.clear();
        if (this.e != null) {
            this.e.clear();
        }
        this.e = null;
    }
}
