package com.tencent.liteav.g;

import java.util.*;
import android.view.*;
import android.content.*;
import com.tencent.liteav.basic.log.*;

import android.os.*;
import android.media.*;

public class r
{
    private final String a = "VideoJoinPreview";
    private o b;
    private m c;
    private q d;
    private b e;
    private com.tencent.liteav.f.b f;
    private boolean g;
    private List<Surface> h;
    private c.b i;
    private com.tencent.liteav.g.c j;
    private a k;
    private d l;
    private n m;
    private j n;
    private b.a o;
    private Handler p;
    
    public r(final Context context) {
        this.j = new com.tencent.liteav.g.c() {
            @Override
            public void a(final e e, final i i) {
                if (r.this.b != null) {
                    r.this.b.a(e, i);
                }
            }
        };
        this.k = new a() {
            @Override
            public void a(final e e, final i i) {
                if (r.this.f != null) {
                    r.this.f.a(e);
                }
            }
        };
        this.l = new d() {
            @Override
            public void a(final List<Surface> list) {
                TXCLog.i("VideoJoinPreview", "onSurfaceTextureAvailable, mStartPlay = " + r.this.g);
                r.this.h = list;
                if (r.this.d != null) {
                    r.this.d.a();
                    r.this.d.b();
                    r.this.d.a(r.this.m);
                }
                if (r.this.g) {
                    r.this.e();
                }
            }
            
            @Override
            public void a(final int a, final int b) {
                if (r.this.d != null) {
                    final g g = new g();
                    g.a = a;
                    g.b = b;
                    r.this.d.a(g);
                }
            }
            
            @Override
            public void b(final List<Surface> list) {
                TXCLog.i("VideoJoinPreview", "onSurfaceTextureDestroy");
                r.this.h = null;
                if (r.this.d != null) {
                    r.this.d.c();
                    r.this.d.d();
                    r.this.d.a((n)null);
                }
            }
            
            @Override
            public int a(final int n, final float[] array, final e e) {
                if (e.p()) {
                    r.this.f();
                    return 0;
                }
                if (r.this.d != null) {
                    r.this.d.a(array);
                    r.this.d.a(n, e);
                    r.this.a(e.e());
                }
                return 0;
            }
        };
        this.m = new n() {
            @Override
            public void a(final int n, final e e) {
                if (r.this.b != null) {
                    r.this.b.a(n, r.this.b.a(), r.this.b.b());
                }
            }
            
            @Override
            public int b(final int n, final e e) {
                return n;
            }
        };
        this.n = new j() {
            @Override
            public void a(final e e) {
                if (e == null || e.b() == null) {
                    return;
                }
                if (e.p()) {
                    r.this.f();
                    return;
                }
                if (r.this.e != null) {
                    r.this.e.a(e);
                }
                if (r.this.f != null) {
                    r.this.f.i();
                }
            }
        };
        this.o = new b.a() {
            @Override
            public void a(final int n) {
                r.this.c.a(n <= 5);
            }
        };
        this.p = new Handler(Looper.getMainLooper());
        (this.b = new o(context)).a(this.l);
        this.d = new q(context);
        this.c = new m();
        this.e = new b();
    }
    
    public void a(final c.b i) {
        this.i = i;
    }
    
    public void a(final com.tencent.liteav.i.a.g g) {
        this.b.a(g);
    }
    
    public void a() {
        this.g = true;
        TXCLog.i("VideoJoinPreview", "startPlay mStartPlay:" + this.g);
        if (this.h != null) {
            this.e();
        }
    }
    
    public void b() {
        this.g = false;
        TXCLog.i("VideoJoinPreview", "stopPlay mStartPlay false");
        if (this.c != null) {
            this.c.b();
            this.c.a((com.tencent.liteav.g.c)null);
            this.c.a((a)null);
        }
        if (this.f != null) {
            this.f.d();
            this.f.a((j)null);
            this.f.b();
            this.f = null;
        }
        if (this.e != null) {
            this.e.a((b.a)null);
            this.e.d();
        }
        if (this.b != null) {
            this.b.d();
        }
    }
    
    public void c() {
        this.g = false;
        if (this.c != null) {
            this.c.c();
        }
        if (this.e != null) {
            this.e.a();
        }
    }
    
    public void d() {
        this.g = true;
        if (this.c != null) {
            this.c.d();
        }
        if (this.e != null) {
            this.e.b();
        }
    }
    
    private void e() {
        TXCLog.i("VideoJoinPreview", "startPlayInternal");
        final g g = new g();
        g.a = this.b.a();
        g.b = this.b.b();
        this.d.a(g);
        (this.f = new com.tencent.liteav.f.b("join")).a();
        this.f.a(this.n);
        final MediaFormat i = t.a().i();
        this.f.a(i);
        this.e.a(i);
        this.e.a(this.o);
        this.e.c();
        this.c.a(t.a().d());
        this.c.a(this.j);
        this.c.a(this.k);
        this.c.a();
        this.b.c();
    }
    
    private void f() {
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (r.this.i != null) {
                    r.this.i.a();
                }
            }
        });
    }
    
    private void a(final long n) {
        this.p.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (r.this.i != null) {
                    r.this.i.a((int)n);
                }
            }
        });
    }
}
