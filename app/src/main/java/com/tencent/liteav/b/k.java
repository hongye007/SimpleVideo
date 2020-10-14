package com.tencent.liteav.b;

import android.content.*;
import android.os.*;
import android.opengl.*;

import com.tencent.liteav.basic.log.*;

import java.util.*;
import java.io.*;

public class k
{
    private t a;
    private Context b;
    private c.a c;
    private i d;
    private com.tencent.liteav.b.c e;
    private h f;
    private g g;
    private List<String> h;
    private List<Float> i;
    private Handler j;
    
    public k(final Context b) {
        this.j = new Handler(Looper.getMainLooper());
        this.b = b;
        this.e = new com.tencent.liteav.b.c(this.b);
        this.d = new i(this.b);
        this.a = t.a();
        this.c();
    }
    
    private void c() {
        this.f = new h() {
            @Override
            public void a(final EGLContext eglContext) {
                k.this.d.a(k.this.e.b());
                k.this.d.b(k.this.e.a());
                k.this.d.a(eglContext);
            }
            
            @Override
            public void a(final int n, final int n2, final int n3, final e e) {
                k.this.d.a(n, n2, n3, e);
            }
            
            @Override
            public void a(final e e) {
                k.this.d.a(e);
            }
            
            @Override
            public void b(final e e) {
                k.this.d.c(e);
            }
            
            @Override
            public void c(final e e) {
                k.this.d.b(e);
            }
        };
        this.g = new g() {
            @Override
            public void a(final float n) {
                k.this.j.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (k.this.c != null) {
                            k.this.c.a(n);
                        }
                    }
                });
            }
            
            @Override
            public void a(final int n, final String s) {
                TXCLog.i("TXCombineVideo", "===onEncodedComplete===");
                k.this.j.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (k.this.c != null) {
                            final a.d d = new a.d();
                            d.a = n;
                            d.b = s;
                            if (n == 0) {
                                k.this.c.a(1.0f);
                            }
                            k.this.c.a(d);
                        }
                    }
                });
            }
        };
        this.e.a(this.f);
        this.d.a(this.g);
    }
    
    public void a(final c.a c) {
        this.c = c;
    }
    
    public void a(final List<String> h) {
        TXCLog.i("TXCombineVideo", "setVideoList");
        this.h = h;
        if (this.i == null) {
            this.i = new ArrayList<Float>();
            for (int i = 0; i < h.size(); ++i) {
                this.i.add(1.0f);
            }
        }
    }
    
    public void a(final String s) {
        TXCLog.i("TXCombineVideo", "setTargetPath " + s);
        try {
            final File file = new File(s);
            if (!file.exists()) {
                file.createNewFile();
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        this.d.a(s);
    }
    
    public void b(final List<Float> i) {
        TXCLog.i("TXCombineVideo", "setVideoVolumes ");
        this.i = i;
    }
    
    public void a() {
        TXCLog.i("TXCombineVideo", "start");
        this.e.a(this.h);
        this.e.b(this.i);
        this.d.a(this.a.p());
        this.d.b();
        this.e.c();
    }
    
    public void b() {
        TXCLog.i("TXCombineVideo", "stop");
        this.e.d();
        this.d.a();
    }
    
    public void a(final List<a.a> list, int n, int n2) {
        TXCLog.i("TXCombineVideo", "setPictureList");
        n = (n + 15) / 16 * 16;
        n2 = (n2 + 15) / 16 * 16;
        this.d.a(n, n2);
        this.e.a(list, n, n2);
    }
    
    static {
        f.f();
    }
}
