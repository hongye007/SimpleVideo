package com.tencent.liteav.f;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.CaptureAndEnc;

import android.content.*;
import java.util.*;

public class e
{
    private g a;
    private d b;
    private n c;
    private f d;
    private boolean e;
    private long f;
    private long g;
    private long h;
    private long i;
    private long j;
    private long k;
    private long l;
    private long m;
    private n.l n;
    private n.m o;
    private n.a p;
    private n.d q;
    private n.i r;
    private n.f s;
    private n.k t;
    private n.e u;
    private n.h v;
    private n.g w;
    private n.j x;
    private final int y = 120000;
    private final int z = 230000;
    private final int A = 274000;
    private final int B = 318000;
    private final int C = 362000;
    private final int D = 406000;
    private final int E = 450000;
    private final int F = 494000;
    private final int G = 538000;
    private final int H = 582000;
    private final int I = 560000;
    private final int J = 1120000;
    private final int K = 1000000;
    private final int L = 120000;
    private final int M = 70000;
    private final int N = 300000;
    private final int O = 350000;
    private final int P = 400000;
    private final int Q = 500000;
    private final int R = 600000;
    private final int S = 650000;
    private final int T = 700000;
    private final int U = 800000;
    private final int V = 900000;
    private final int W = 1000000;
    private final int X = 1050000;
    private final int Y = 1100000;
    private final int Z = 1200000;
    private final int aa = 1500000;
    private final int ab = 2500000;
    private final int ac = 120000;
    private final int ad = 240000;
    private final int ae = 360000;
    private final int af = 480000;
    private final int ag = 600000;
    private final int ah = 720000;
    private final int ai = 840000;
    private final int aj = 960000;
    private final int ak = 1080000;
    private final int al = 1200000;
    private final int am = 1320000;
    private final int an = 1440000;
    private final int ao = 1560000;
    private final int ap = 1680000;
    private final int aq = 1800000;
    private final int ar = 100000;
    private final int as = 200000;
    private final int at = 300000;
    private final int au = 400000;
    private final int av = 500000;
    private final int aw = 600000;
    private final int ax = 700000;
    private final int ay = 800000;
    private final int az = 850000;
    private final int aA = 50000;
    private final int aB = 150000;
    private final int aC = 250000;
    private final int aD = 300000;
    private final int aE = 400000;
    private final int aF = 580000;
    private final int aG = 1000000;
    private final int aH = 2000000;
    
    public e(final Context context) {
        this.f = -1L;
        this.g = -1L;
        this.h = -1L;
        this.i = -1L;
        this.j = -1L;
        this.k = -1L;
        this.l = -1L;
        this.m = -1L;
        this.b = CameraProxy.d.a();
        this.c = new n(context);
        this.a = CameraProxy.g.a();
    }
    
    public void a(final CaptureAndEnc.e e) {
        final long e2 = e.e();
        this.d = null;
        if (!this.b()) {
            return;
        }
        final int a = this.a(e2);
        if (a == -1 || this.d == null) {
            return;
        }
        if (!this.b(e2)) {
            return;
        }
        this.a(a, e2);
    }
    
    public int a(final CaptureAndEnc.e e, final int a) {
        if (this.d == null) {
            return a;
        }
        final n.b b = new n.b();
        b.a = a;
        b.b = e.m();
        b.c = e.n();
        switch (this.d.a) {
            case 2: {
                this.c.a(2, this.n);
                break;
            }
            case 3: {
                this.c.a(3, this.o);
                break;
            }
            case 0: {
                this.c.a(0, this.p);
                break;
            }
            case 1: {
                this.c.a(1, this.q);
                break;
            }
            case 4: {
                this.c.a(4, this.r);
                break;
            }
            case 5: {
                this.c.a(5, this.s);
                break;
            }
            case 6: {
                this.c.a(6, this.t);
                break;
            }
            case 7: {
                this.c.a(7, this.u);
                break;
            }
            case 8: {
                this.c.a(8, this.v);
                break;
            }
            case 10: {
                this.c.a(10, this.w);
                break;
            }
            case 11: {
                this.c.a(11, this.x);
                break;
            }
        }
        return this.c.a(b);
    }
    
    private boolean b() {
        final List<f> d = this.b.d();
        return d != null && d.size() != 0;
    }
    
    private int a(final long n) {
        int n2 = -1;
        final List<f> d = this.b.d();
        if (d == null || d.size() == 0) {
            return n2;
        }
        for (int i = d.size() - 1; i >= 0; --i) {
            final f d2 = d.get(i);
            if (n >= d2.b && n <= d2.c) {
                n2 = d2.a;
                this.d = d2;
                break;
            }
        }
        final f b = this.b.b();
        if (b.c == -1L || b.c == b.b) {
            n2 = b.a;
            this.d = b;
        }
        return n2;
    }
    
    private boolean b(final long n) {
        boolean b = false;
        final long b2 = this.d.b;
        final long c = this.d.c;
        if (b2 != -1L && c != -1L && n > b2 && n < c) {
            b = true;
        }
        if (b2 != -1L && n > b2) {
            b = true;
        }
        if (c != -1L && n < c) {
            b = true;
        }
        return b;
    }
    
    private void a(final int n, final long n2) {
        switch (n) {
            case 2: {
                this.l(n2);
                break;
            }
            case 3: {
                this.k(n2);
                break;
            }
            case 0: {
                if (null == this.p) {
                    this.p = new n.a();
                }
                this.c();
                break;
            }
            case 1: {
                this.j(n2);
                break;
            }
            case 4: {
                this.i(n2);
                break;
            }
            case 5: {
                this.h(n2);
                break;
            }
            case 6: {
                this.g(n2);
                break;
            }
            case 7: {
                this.f(n2);
                break;
            }
            case 8: {
                this.e(n2);
                break;
            }
            case 10: {
                this.d(n2);
                break;
            }
            case 11: {
                this.c(n2);
                break;
            }
        }
    }
    
    private void c(final long m) {
        if (null == this.x) {
            this.x = new n.j();
        }
        if (this.m == -1L) {
            this.m = m;
        }
        final long abs = Math.abs(m - this.m);
        if (abs < 1000000L) {
            this.x.a = 0.0f;
        }
        else if (abs < 2000000L) {
            this.x.a = 1.0f;
        }
        else {
            this.m = -1L;
        }
    }
    
    private void d(final long n) {
        if (null == this.w) {
            this.w = new n.g();
        }
    }
    
    private void e(final long l) {
        if (this.l == -1L) {
            this.l = l;
        }
        if (null == this.v) {
            this.v = new n.h();
            this.v.a = 0.0f;
        }
        final long abs = Math.abs(l - this.l);
        if (abs < 50000L) {
            this.v.a = 0.7f;
        }
        else if (abs < 150000L) {
            this.v.a = 0.5f;
        }
        else if (abs < 250000L) {
            this.v.a = 0.4f;
        }
        else if (abs < 300000L) {
            this.v.a = 1.0f;
        }
        else if (abs < 400000L) {
            this.v.a = 0.3f;
        }
        else if (abs < 580000L) {
            this.v.a = 0.0f;
        }
        else {
            this.l = -1L;
        }
    }
    
    private void f(final long k) {
        if (this.k == -1L) {
            this.k = k;
        }
        if (null == this.u) {
            this.u = new n.e();
            this.u.b = 0.0f;
            this.u.a = 0.0f;
            this.u.c = 0.0f;
        }
        final long abs = Math.abs(k - this.k);
        if (abs < 100000L) {
            this.u.b = 10.0f;
            this.u.a = 0.01f;
            this.u.c = 0.0f;
        }
        else if (abs < 200000L) {
            this.u.b = 20.0f;
            this.u.a = -0.02f;
            this.u.c = 0.0f;
        }
        else if (abs < 300000L) {
            this.u.b = 30.0f;
            this.u.a = 0.02f;
            this.u.c = 0.0f;
        }
        else if (abs < 400000L) {
            this.u.b = 20.0f;
            this.u.a = -0.03f;
            this.u.c = 0.0f;
        }
        else if (abs < 500000L) {
            this.u.b = 10.0f;
            this.u.a = 0.01f;
            this.u.c = 0.0f;
        }
        else if (abs < 600000L) {
            this.u.b = 20.0f;
            this.u.a = -0.02f;
            this.u.c = 0.0f;
        }
        else if (abs < 700000L) {
            this.u.b = 30.0f;
            this.u.a = -0.03f;
            this.u.c = 0.0f;
        }
        else if (abs < 800000L) {
            this.u.b = 20.0f;
            this.u.a = 0.02f;
            this.u.c = 0.0f;
        }
        else if (abs < 850000L) {
            this.u.b = 0.0f;
            this.u.a = 0.0f;
            this.u.c = 0.0f;
        }
        else {
            this.k = -1L;
        }
    }
    
    private void g(final long j) {
        if (this.j == -1L) {
            this.j = j;
        }
        if (null == this.t) {
            this.t = new n.k();
            this.t.f = 1;
            this.t.h = 0.3f;
        }
        this.t.a = new float[] { 0.0f, 0.0f };
        this.t.b = new float[] { 0.0f, 0.1f };
        final long abs = Math.abs(j - this.j);
        if (abs < 120000L) {
            this.t.g = 0;
            this.t.a = new float[] { 0.0f, 0.0f };
            this.t.b = new float[] { 0.0f, 0.0f };
        }
        else if (abs < 120000L) {
            this.t.g = 1;
        }
        else if (abs < 240000L) {
            this.t.g = 2;
        }
        else if (abs < 360000L) {
            this.t.g = 3;
        }
        else if (abs < 480000L) {
            this.t.g = 4;
        }
        else if (abs < 600000L) {
            this.t.g = 5;
        }
        else if (abs < 720000L) {
            this.t.g = 6;
        }
        else if (abs < 840000L) {
            this.t.g = 7;
        }
        else if (abs < 960000L) {
            this.t.g = 8;
        }
        else if (abs < 1080000L) {
            this.t.g = 9;
        }
        else if (abs < 1200000L) {
            this.t.g = 10;
        }
        else if (abs < 1320000L) {
            this.t.g = 11;
        }
        else if (abs < 1440000L) {
            this.t.g = 12;
        }
        else if (abs < 1560000L) {
            this.t.g = 13;
        }
        else if (abs < 1680000L) {
            this.t.g = 14;
        }
        else if (abs < 1800000L) {
            this.t.g = 15;
        }
        else {
            this.j = -1L;
        }
    }
    
    private void h(final long n) {
        if (null == this.s) {
            this.s = new n.f();
        }
        this.s.a = 5;
        this.s.b = 1;
        this.s.c = 0.5f;
    }
    
    private void i(final long i) {
        if (this.i == -1L) {
            if (this.e) {
                this.i = this.d.b;
            }
            else {
                this.i = i;
            }
        }
        if (null == this.r) {
            this.r = new n.i();
        }
        final long abs = Math.abs(i - this.i);
        if (abs < 300000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.003f;
        }
        else if (abs < 350000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        }
        else if (abs < 400000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.024f;
        }
        else if (abs < 500000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        }
        else if (abs < 600000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.003f;
        }
        else if (abs < 650000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.03f;
        }
        else if (abs < 700000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        }
        else if (abs < 800000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.024f;
        }
        else if (abs < 900000L) {
            this.r.b = 1.0f;
        }
        else if (abs < 1000000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        }
        else if (abs < 1050000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.024f;
        }
        else if (abs < 1100000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.015f;
        }
        else if (abs < 1200000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.009f;
        }
        else if (abs < 1500000L) {
            this.r.b = 0.0f;
            this.r.e = 0.03f;
            this.r.d = 0.003f;
        }
        else if (abs < 2500000L) {
            this.r.b = 1.0f;
        }
        else {
            this.i = -1L;
        }
    }
    
    private void j(final long h) {
        if (this.h == -1L) {
            if (this.e) {
                this.h = this.d.b;
            }
            else {
                this.h = h;
            }
        }
        if (this.q == null) {
            this.q = new n.d();
            this.q.e = 8.0f;
            this.q.c = new float[] { 0.5f, 0.5f };
            this.q.a = 0.0f;
            this.q.b = 0.2f;
        }
        final long abs = Math.abs(h - this.h);
        if (abs < 120000L) {
            this.q.d = 0.0f;
            this.q.e = 8.0f;
            this.q.c = new float[] { 0.0f, 0.0f };
            this.q.a = 0.0f;
            this.q.b = 0.0f;
            this.q.f = new float[] { 0.0f, 0.0f };
            this.q.g = new float[] { 0.0f, 0.0f };
        }
        else {
            int i = 1;
            while (i <= 8) {
                if (abs < 120000 + 70000 * i) {
                    this.q.d = (float)i;
                    this.q.e = 8.0f;
                    this.q.c = new float[] { 0.5f, 0.5f };
                    this.q.a = 0.0f;
                    this.q.b = 0.3f;
                    if (i >= 3) {
                        this.q.f = new float[] { -0.1f, 0.0f };
                        this.q.g = new float[] { 0.0f, 0.1f };
                        break;
                    }
                    this.q.f = new float[] { 0.0f, 0.0f };
                    this.q.g = new float[] { 0.0f, 0.0f };
                    break;
                }
                else {
                    ++i;
                }
            }
            if (abs > 680000L) {
                if (abs <= 1080000L) {
                    this.q.d = 0.0f;
                    this.q.e = 8.0f;
                    this.q.c = new float[] { 0.0f, 0.0f };
                    this.q.a = 0.0f;
                    this.q.b = 0.0f;
                    this.q.f = new float[] { 0.0f, 0.0f };
                    this.q.g = new float[] { 0.0f, 0.0f };
                }
                else {
                    this.h = -1L;
                }
            }
        }
    }
    
    private void c() {
        if (null == this.p) {
            this.p = new n.a();
        }
    }
    
    private void k(final long g) {
        if (this.g == -1L) {
            if (this.e) {
                this.g = this.d.b;
            }
            else {
                this.g = g;
            }
        }
        if (null == this.o) {
            this.o = new n.m();
        }
        final long abs = Math.abs(g - this.g);
        if (abs <= 1000000L) {
            this.o.a = 4;
        }
        else if (abs <= 2000000L) {
            this.o.a = 9;
        }
        else {
            this.g = -1L;
        }
    }
    
    private void l(final long f) {
        if (this.f == -1L) {
            if (this.e) {
                this.f = this.d.b;
            }
            else {
                this.f = f;
            }
        }
        if (null == this.n) {
            this.n = new n.l();
            this.n.f = 1;
            this.n.h = 0.3f;
        }
        final long abs = Math.abs(f - this.f);
        if (abs < 120000L) {
            this.n.g = 0;
        }
        else if (abs < 230000L) {
            this.n.g = 1;
        }
        else if (abs < 274000L) {
            this.n.g = 2;
        }
        else if (abs < 318000L) {
            this.n.g = 3;
        }
        else if (abs < 362000L) {
            this.n.g = 4;
        }
        else if (abs < 406000L) {
            this.n.g = 5;
        }
        else if (abs < 450000L) {
            this.n.g = 6;
        }
        else if (abs < 494000L) {
            this.n.g = 7;
        }
        else if (abs < 538000L) {
            this.n.g = 8;
        }
        else if (abs < 582000L) {
            this.n.g = 9;
        }
        else if (abs < 1120000L) {
            this.n.g = 0;
        }
        else {
            this.f = -1L;
        }
    }
    
    public void a() {
        if (this.c != null) {
            this.c.a();
        }
    }
}
