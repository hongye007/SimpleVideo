package com.tencent.liteav.f;

import android.content.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.editer.*;
import java.util.*;
import android.graphics.*;

public class k
{
    private Context a;
    private j b;
    private e c;
    private v d;
    private n e;
    private g f;
    private i g;
    private l h;
    private com.tencent.liteav.f.e i;
    private h j;
    private f k;
    private a l;
    private com.tencent.liteav.f.j m;
    private ArrayList<e.f> n;
    private CaptureAndEnc.e o;
    private int p;
    private int q;
    private l r;
    private boolean s;
    private int t;
    private CaptureAndEnc.e u;
    private Object v;
    
    public k(final Context a) {
        this.s = false;
        this.v = new Object();
        this.a = a;
    }
    
    public void a() {
        this.b = CameraProxy.j.a();
        this.c = new e(this.a, true);
        this.i = new com.tencent.liteav.f.e(this.a);
        this.j = com.tencent.liteav.f.h.a();
        this.k = com.tencent.liteav.f.f.a();
        this.l = com.tencent.liteav.f.a.a();
        this.m = com.tencent.liteav.f.j.a();
    }
    
    public void b() {
        if (!CameraProxy.h.a().e()) {
            (this.g = new i(false)).a();
        }
        (this.h = new l(false)).a();
        (this.r = new l(true)).a();
    }
    
    public void c() {
        if (this.g != null) {
            this.g.b();
            this.g = null;
        }
        if (this.h != null) {
            this.h.b();
            this.h = null;
        }
        if (this.r != null) {
            this.r.b();
            this.r = null;
        }
    }
    
    public void d() {
        if (this.i != null) {
            this.i.a();
        }
        if (this.c != null) {
            this.c.b();
            this.c = null;
        }
        if (this.n != null) {
            this.n.clear();
        }
        this.o = null;
    }
    
    public void a(final g f) {
        this.f = f;
    }
    
    public void a(final n e) {
        this.e = e;
    }
    
    public void a(final v d) {
        synchronized (this.v) {
            this.d = d;
        }
    }
    
    public void a(final float[] array) {
        if (this.c != null) {
            this.c.a(array);
        }
        if (this.r != null) {
            this.r.a(array);
        }
    }
    
    public void a(final int p) {
        this.p = p;
        if (p == 1) {
            if (this.o != null) {
                this.b(this.o);
            }
            this.a(this.q, this.o);
        }
        else if (p == 2) {
            if (this.o != null) {
                this.b(this.o);
            }
            final int e = CameraProxy.j.a().e();
            final int f = CameraProxy.j.a().f();
            int n;
            if (this.t != 0) {
                n = Math.abs(e - this.t);
                this.t = 0;
            }
            else {
                n = Math.abs(e - f);
            }
            if (n == 90 || n == 270) {
                this.c(this.o);
            }
            this.a(this.q, this.o);
            CameraProxy.j.a().b(e);
        }
    }
    
    public void a(final int n, CaptureAndEnc.e u) {
        if (this.c == null) {
            return;
        }
        if (u == null) {
            return;
        }
        int n2 = n;
        if (this.s) {
            final int c = this.c(n2, u);
            u = this.b(u);
            this.e(c, u);
            this.o = u;
            this.q = n;
            return;
        }
        this.n = new ArrayList<e.f>();
        if (CameraProxy.k.a().d() == 1) {
            n2 = this.c(n2, u);
            u = this.b(u);
        }
        this.l.c(u);
        this.k.c(u);
        this.j.c(u);
        if (this.e != null) {
            n2 = this.e.b(n2, u);
        }
        this.e();
        this.g();
        this.f();
        if (this.p != 1) {
            this.f(u);
            this.e(u);
            this.d(u);
        }
        this.a(u);
        this.c.a(this.n);
        this.c.b(u.s());
        final int b = this.b(this.d(this.c.a(n2, u.m(), u.n(), 0, 0, 0), u), u);
        if (this.e != null) {
            this.e.a(b, u);
        }
        this.f(b, u);
        this.o = u;
        this.u = u;
        this.q = n;
    }
    
    private void a(final CaptureAndEnc.e e) {
        if (!this.m.b()) {
            return;
        }
        final List<com.tencent.liteav.i.a.k> h = this.m.h();
        if (h == null || h.size() == 0) {
            return;
        }
        final long n = com.tencent.liteav.j.e.a(e) / 1000L;
        for (final com.tencent.liteav.i.a.k k : h) {
            if (n <= k.c) {
                break;
            }
            if (n <= k.c || n > k.d) {
                continue;
            }
            this.n.add(this.a(k.a, k.b));
        }
    }
    
    private CaptureAndEnc.e b(final CaptureAndEnc.e e) {
        if (e.r()) {
            final int n = 360 - e.h();
            if (n == 90 || n == 270) {
                this.c(e);
            }
            return e;
        }
        final int e2 = CameraProxy.j.a().e();
        final int n2 = 360 - e.h() - e2;
        if (n2 == 90 || n2 == 270) {
            this.c(e);
        }
        if (CameraProxy.i.a().t.get() == 2) {
            this.t = e2;
        }
        return e;
    }
    
    private CaptureAndEnc.e c(final CaptureAndEnc.e e) {
        final int n = e.n();
        e.k(e.m());
        e.j(n);
        return e;
    }
    
    private int b(final int n, final CaptureAndEnc.e e) {
        if (this.h == null) {
            return n;
        }
        if (e.m() == 0 || e.n() == 0) {
            return n;
        }
        this.h.a(CameraProxy.i.a().s);
        this.h.b(e.m(), e.n());
        this.h.a(this.f.a, this.f.b);
        return this.h.d(n);
    }
    
    private int c(final int n, final CaptureAndEnc.e e) {
        if (this.r == null) {
            return n;
        }
        if (e.m() == 0 || e.n() == 0) {
            return n;
        }
        this.r.a(CameraProxy.i.a().s);
        final int n2 = 360 - e.h() - CameraProxy.j.a().e();
        this.r.b(n2);
        this.r.b(e.m(), e.n());
        if (n2 == 90 || n2 == 270) {
            this.r.a(e.n(), e.m());
        }
        else {
            this.r.a(e.m(), e.n());
        }
        return this.r.d(n);
    }
    
    private int d(final int n, final CaptureAndEnc.e e) {
        if (this.i != null) {
            this.i.a(e);
            return this.i.a(e, n);
        }
        return n;
    }
    
    private void d(final CaptureAndEnc.e e) {
        List<CaptureAndEnc.a> list = this.l.b();
        if (list == null || list.size() == 0) {
            this.l.a(this.f);
            this.l.a(e);
            list = this.l.b();
        }
        for (final CaptureAndEnc.a a : list) {
            final long n = e.e() / 1000L;
            if (n >= a.c && n <= a.d) {
                final Bitmap decodeFile = BitmapFactory.decodeFile(a.a);
                if (decodeFile == null) {
                    continue;
                }
                if (a.e == 0.0f) {
                    this.n.add(this.a(decodeFile, a.b));
                }
                else {
                    this.n.add(this.a(com.tencent.liteav.j.a.a(a.e, decodeFile), a.b));
                }
            }
        }
    }
    
    private void e(final CaptureAndEnc.e e) {
        List<com.tencent.liteav.i.a.e> list = this.k.b();
        if (list == null || list.size() == 0) {
            this.k.a(this.f);
            this.k.a(e);
            list = this.k.b();
        }
        for (final com.tencent.liteav.i.a.e e2 : list) {
            final long n = e.e() / 1000L;
            if (n >= e2.c && n <= e2.d) {
                this.n.add(this.a(e2.a, e2.b));
            }
        }
    }
    
    private void f(final CaptureAndEnc.e e) {
        List<com.tencent.liteav.i.a.k> list = this.j.b();
        if (list == null || list.size() == 0) {
            this.j.a(this.f);
            this.j.a(e);
            list = this.j.b();
        }
        for (final com.tencent.liteav.i.a.k k : list) {
            final long n = e.e() / 1000L;
            if (n >= k.c && n <= k.d) {
                this.n.add(this.a(k.a, k.b));
            }
        }
    }
    
    private void e(final int n, final CaptureAndEnc.e e) {
        v d = null;
        synchronized (this.v) {
            d = this.d;
        }
        if (d == null) {
            return;
        }
        final CameraProxy.h a = CameraProxy.h.a();
        if (a.e()) {
            return;
        }
        if (e.p()) {
            do {
                final int h = a.h();
                a.g();
                if (this.o != null) {
                    final long e2 = this.o.e();
                    final g d2 = a.d();
                    if (this.g != null) {
                        this.g.b(this.o.m(), this.o.n());
                        this.g.a(d2.a, d2.b);
                        final Bitmap a2 = com.tencent.liteav.j.d.a(this.g.b(n), d2.a, d2.b);
                        if (d != null) {
                            d.a(h, e2, a2);
                        }
                    }
                }
            } while (!a.e());
            return;
        }
        final int h2 = a.h();
        final long g = a.g();
        final g d3 = a.d();
        if (this.g != null) {
            this.g.b(e.m(), e.n());
            this.g.a(d3.a, d3.b);
            final Bitmap a3 = com.tencent.liteav.j.d.a(this.g.b(n), d3.a, d3.b);
            if (d != null) {
                d.a(h2, g, a3);
            }
        }
    }
    
    private void f(final int n, final CaptureAndEnc.e e) {
        final v d;
        synchronized (this.v) {
            d = this.d;
        }
        if (d == null) {
            return;
        }
        final CameraProxy.h a = CameraProxy.h.a();
        if (a.e()) {
            return;
        }
        if (e.p()) {
            do {
                final int h = a.h();
                a.g();
                if (this.o != null) {
                    final long e2 = this.o.e();
                    final g d2 = a.d();
                    if (this.g != null) {
                        this.g.b(this.o.m(), this.o.n());
                        this.g.a(d2.a, d2.b);
                        final Bitmap a2 = com.tencent.liteav.j.d.a(this.g.b(n), d2.a, d2.b);
                        if (d != null) {
                            d.a(h, e2, a2);
                        }
                    }
                }
            } while (!a.e());
            return;
        }
        final long e3 = e.e();
        if (!CameraProxy.i.a().r && !a.k() && e3 < a.f()) {
            return;
        }
        final int h2 = a.h();
        final long g = a.g();
        final g d3 = a.d();
        if (this.g != null) {
            this.g.b(e.m(), e.n());
            this.g.a(d3.a, d3.b);
            final Bitmap a3 = com.tencent.liteav.j.d.a(this.g.b(n), d3.a, d3.b);
            if (d != null) {
                d.a(h2, g, a3);
            }
        }
    }
    
    private void e() {
        final c c = this.b.c();
        if (c != null && c.a()) {
            this.c.c(c.a);
            this.c.d(c.b);
        }
    }
    
    private void f() {
        final CaptureAndEnc.d d = this.b.d();
        if (d != null) {
            this.c.a(d.d(), d.e(), d.b(), d.f(), d.c());
        }
    }
    
    private void g() {
        final CaptureAndEnc.j b = this.b.b();
        if (b != null) {
            this.n.add(this.a(b.c(), b.d()));
        }
    }
    
    private e.f a(final Bitmap a, final com.tencent.liteav.i.a.h h) {
        final e.f f = new e.f();
        f.a = a;
        f.b = h.a;
        f.c = h.b;
        f.d = h.c;
        return f;
    }
    
    public void a(final boolean s) {
        this.s = s;
    }
}
