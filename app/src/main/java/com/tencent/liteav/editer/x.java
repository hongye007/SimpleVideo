package com.tencent.liteav.editer;

import android.content.*;
import android.os.*;
import android.graphics.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.basic.log.*;

public class x extends e
{
    private b.e n;
    private b.a o;
    private Handler p;
    private v q;
    
    public x(final Context context) {
        super(context, "avethumb");
        this.p = new Handler(Looper.getMainLooper());
        this.q = new v() {
            @Override
            public void a(final int n, final long n2, final Bitmap bitmap) {
                x.this.c.p();
                if (x.this.o != null) {
                    x.this.o.a(n, n2 / 1000L, bitmap);
                }
                final int c = CameraProxy.h.a().c();
                if (c == 0) {
                    x.this.c();
                    if (x.this.n != null) {
                        x.this.i();
                    }
                    return;
                }
                final float n3 = (n + 1) * 1.0f / c;
                TXCLog.i("VideoAverageThumbnailGenerate", "index:" + n + ",count= " + c + ",progress:" + n3);
                x.this.p.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (n3 >= 1.0f) {
                            x.this.c();
                        }
                        if (x.this.n != null) {
                            x.this.n.a(n3);
                            if (n3 >= 1.0f) {
                                x.this.i();
                                TXCLog.i("VideoAverageThumbnailGenerate", "===onProcessComplete===");
                            }
                        }
                    }
                });
            }
        };
        this.c = new ai("avethumb");
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
        TXCLog.i("VideoAverageThumbnailGenerate", "start");
        this.a(CameraProxy.k.a().a);
        this.h();
        this.b();
        super.a();
    }
    
    @Override
    protected void e() {
        TXCLog.i("VideoAverageThumbnailGenerate", "onGenerateComplete");
    }
    
    @Override
    protected void a(final long n) {
    }
    
    protected void h() {
        CameraProxy.h.a().a(this.c.c());
        this.c.a(CameraProxy.h.a().b());
    }
    
    private void i() {
        CameraProxy.i.a().n = true;
        final a.c c = new a.c();
        c.a = 0;
        c.b = "Generate Complete";
        this.n.a(c);
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
    }
    
    @Override
    public void d() {
        TXCLog.i("VideoAverageThumbnailGenerate", "release");
        if (this.f != null) {
            this.f.a((v)null);
        }
        this.q = null;
        super.d();
    }
    
    public void c(final boolean b) {
        if (this.c != null) {
            this.c.a(b);
        }
    }
}
