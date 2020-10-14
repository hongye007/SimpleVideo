package com.tencent.liteav.g;

import android.content.*;

import com.tencent.liteav.CaptureAndEnc;

public class q
{
    private Context a;
    private e b;
    private l c;
    private l d;
    private n e;
    private g f;
    
    public q(final Context a) {
        this.a = a;
    }
    
    public void a() {
        this.b = new e(this.a, true);
    }
    
    public void b() {
        (this.c = new l(false)).a();
        (this.d = new l(true)).a();
    }
    
    public void c() {
        if (this.c != null) {
            this.c.b();
            this.c = null;
        }
        if (this.d != null) {
            this.d.b();
            this.d = null;
        }
    }
    
    public void d() {
        if (this.b != null) {
            this.b.b();
            this.b = null;
        }
    }
    
    public void a(final n e) {
        this.e = e;
    }
    
    public void a(final g f) {
        this.f = f;
    }
    
    public void a(final float[] array) {
        if (this.b != null) {
            this.b.a(array);
        }
        if (this.d != null) {
            this.d.a(array);
        }
    }
    
    public void a(final int n, CaptureAndEnc.e a) {
        if (this.b == null) {
            return;
        }
        if (a == null) {
            return;
        }
        final int c = this.c(n, a);
        a = this.a(a);
        int n2 = this.b.a(c, a.m(), a.n(), 0, 0, 0);
        if (this.e != null) {
            n2 = this.e.b(n2, a);
        }
        final int b = this.b(n2, a);
        if (this.e != null) {
            this.e.a(b, a);
        }
    }
    
    private CaptureAndEnc.e a(final CaptureAndEnc.e e) {
        final int n = 360 - e.h();
        if (n == 90 || n == 270) {
            final int n2 = e.n();
            e.k(e.m());
            e.j(n2);
        }
        return e;
    }
    
    private int b(final int n, final CaptureAndEnc.e e) {
        if (this.c == null) {
            return n;
        }
        if (e.m() == 0 || e.n() == 0) {
            return n;
        }
        this.c.a(s.r().u);
        this.c.b(e.m(), e.n());
        this.c.a(this.f.a, this.f.b);
        return this.c.d(n);
    }
    
    private int c(final int n, final CaptureAndEnc.e e) {
        if (this.d == null) {
            return n;
        }
        if (e.m() == 0 || e.n() == 0) {
            return n;
        }
        this.d.a(s.r().u);
        final int n2 = 360 - e.h();
        this.d.b(n2);
        this.d.b(e.m(), e.n());
        if (n2 == 90 || n2 == 270) {
            this.d.a(e.n(), e.m());
        }
        else {
            this.d.a(e.m(), e.n());
        }
        return this.d.d(n);
    }
}
