package com.tencent.liteav.editer;

import android.content.*;
import android.os.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.basic.log.*;

public class ab extends e
{
    private b.c n;
    private b.b o;
    private Handler p;
    
    public ab(final Context context) {
        super(context, "gene");
        this.p = new Handler(Looper.getMainLooper());
    }
    
    public void a(final b.c n) {
        this.n = n;
    }
    
    public void a(final b.b o) {
        this.o = o;
    }
    
    @Override
    public void a() {
        TXCLog.i("VideoEditGenerate", "start");
        if (CameraProxy.k.a().d() == 1) {
            this.a(CameraProxy.k.a().a);
            if (CameraProxy.k.a().e() != 0) {
                if (this.n != null) {
                    final a.c c = new a.c();
                    c.a = 0;
                    c.b = "Generate Fail,Cause: Video Source Path illegal : " + CameraProxy.k.a().a;
                    TXCLog.i("VideoEditGenerate", "onGenerateComplete");
                    this.n.a(c);
                }
                return;
            }
        }
        else if (CameraProxy.k.a().d() == 2) {
            this.a(CameraProxy.k.a().b(), CameraProxy.k.a().c());
        }
        if (this.l.b()) {
            return;
        }
        this.l.g();
        this.b();
        this.h = new com.tencent.liteav.e.a(2, false);
        (this.i = new c(this.a, 0)).a(this.l.i);
        this.k.a(this.m);
        super.a();
    }
    
    @Override
    public void c() {
        TXCLog.i("VideoEditGenerate", "stop");
        super.c();
        this.k.a((w)null);
    }
    
    @Override
    protected void e() {
        TXCLog.i("VideoEditGenerate", "onGenerateComplete");
        if (this.k != null) {
            this.k.i();
        }
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ab.this.n != null) {
                    final a.c c = new a.c();
                    c.a = 0;
                    c.b = "Generate Complete";
                    TXCLog.i("VideoEditGenerate", "===onGenerateComplete===");
                    ab.this.n.a(c);
                }
            }
        });
    }
    
    @Override
    protected void a(final long n) {
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ab.this.n != null) {
                    final long k = ab.this.l.k;
                    if (k > 0L) {
                        long f;
                        if (CameraProxy.g.a().b()) {
                            f = 0L;
                        }
                        else {
                            f = CameraProxy.c.a().f();
                        }
                        final float n = (n - f) * 1.0f / k;
                        TXCLog.d("VideoEditGenerate", "onGenerateProgress timestamp:" + n + ",progress:" + n + ",duration:" + k);
                        ab.this.n.a(n);
                    }
                }
            }
        });
    }
    
    @Override
    protected int a(final int n, final int n2, final int n3, final long n4) {
        if (this.o != null) {
            return this.o.a(n, n2, n3, n4);
        }
        return n;
    }
    
    @Override
    protected void f() {
        if (this.o != null) {
            this.o.a();
        }
    }
    
    @Override
    protected void g() {
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ab.this.n != null) {
                    final a.c c = new a.c();
                    c.a = -1;
                    c.b = "Generate Fail: decode video frame fail.";
                    TXCLog.e("VideoEditGenerate", "===onDecoderError===");
                    ab.this.n.a(c);
                }
            }
        });
    }
}
