package com.tencent.liteav.editer;

import com.tencent.liteav.CameraProxy;

import android.content.*;
import android.os.*;
import android.graphics.*;
import com.tencent.liteav.basic.log.*;

public class ag extends e
{
    private b.e n;
    private b.a o;
    private Handler p;
    private v q;
    
    public ag(final Context context, final String s) {
        super(context, s);
        this.p = new Handler(Looper.getMainLooper());
        this.q = new v() {
            @Override
            public void a(final int n, final long n2, final Bitmap bitmap) {
                if (ag.this.o != null) {
                    ag.this.o.a(n, n2 / 1000L, bitmap);
                }
                if (CameraProxy.i.a().r) {
                    final int c = CameraProxy.h.a().c();
                    if (c == 0) {
                        ag.this.c();
                        if (ag.this.n != null) {
                            ag.this.i();
                        }
                        return;
                    }
                    final float n3 = (n + 1) * 1.0f / c;
                    TXCLog.i("VideoProcessGenerate", "index:" + n + ",count= " + c + ",progress:" + n3);
                    ag.this.p.post((Runnable)new Runnable() {
                        @Override
                        public void run() {
                            if (ag.this.n != null) {
                                ag.this.n.a(n3);
                                if (n3 >= 1.0f) {
                                    ag.this.i();
                                    ag.this.c();
                                }
                            }
                        }
                    });
                }
            }
        };
        this.c = new y(s);
        this.f.a(this.q);
    }
    
    public void a(final b.e n) {
        this.n = n;
    }
    
    public void a(final b.a o) {
        this.o = o;
    }
    
    @Override
    public void a() {
        this.a(CameraProxy.k.a().a);
        this.h();
        this.b();
        com.tencent.liteav.basic.d.b.a().a(this.a);
        this.b = com.tencent.liteav.basic.d.b.a().f();
        this.h = new a(2, this.b);
        this.i = new c(this.a, 0);
        if (!this.l.r) {
            this.l.f();
            this.i.a(this.l.o);
        }
        super.a();
    }
    
    @Override
    protected void e() {
        CameraProxy.k.a().a = CameraProxy.i.a().o;
        CameraProxy.j.a().a(0);
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ag.this.n != null) {
                    final com.tencent.liteav.i.a.c c = new com.tencent.liteav.i.a.c();
                    c.a = 0;
                    c.b = "Generate Complete";
                    ag.this.n.a(c);
                }
            }
        });
    }
    
    @Override
    protected void a(final long n) {
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ag.this.n != null) {
                    final long k = ag.this.l.k;
                    if (k > 0L) {
                        ag.this.n.a((n - CameraProxy.c.a().f()) * 1.0f / k);
                    }
                }
            }
        });
    }
    
    protected void h() {
        long c = CameraProxy.c.a().g() - CameraProxy.c.a().f();
        if (c <= 0L) {
            c = this.c.c();
        }
        CameraProxy.h.a().a(c);
    }
    
    private void i() {
        final com.tencent.liteav.i.a.c c = new com.tencent.liteav.i.a.c();
        c.a = 0;
        c.b = "Generate Complete";
        this.n.a(c);
        TXCLog.i("VideoProcessGenerate", "===onProcessComplete===");
    }
    
    @Override
    protected int a(final int n, final int n2, final int n3, final long n4) {
        return n;
    }
    
    @Override
    protected void f() {
    }
    
    @Override
    protected void g() {
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ag.this.n != null) {
                    final com.tencent.liteav.i.a.c c = new com.tencent.liteav.i.a.c();
                    c.a = -1;
                    c.b = "Generate Fail: decode video frame fail.";
                    TXCLog.e("VideoProcessGenerate", "===onDecoderError===");
                    ag.this.n.a(c);
                }
            }
        });
    }
    
    @Override
    public void d() {
        super.d();
        this.q = null;
    }
}
