package com.tencent.liteav.f;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.editer.*;
import android.graphics.*;
import com.tencent.liteav.basic.log.*;
import java.nio.*;
import java.util.*;

public class j
{
    private static j c;
    private i d;
    private float e;
    private List<a.k> f;
    public e a;
    public e b;
    private w g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private boolean m;
    private boolean n;
    private boolean o;
    
    public static j a() {
        if (j.c == null) {
            j.c = new j();
        }
        return j.c;
    }
    
    private j() {
        this.i();
    }
    
    public void a(final i d) {
        this.d = d;
    }
    
    public boolean b() {
        return this.d != null;
    }
    
    public void a(final w g) {
        this.g = g;
    }
    
    public long c() {
        return this.d.a() * 1000 * 1000;
    }
    
    public void d() {
        this.o = CameraProxy.i.a().l();
        if (this.d == null) {
            return;
        }
        if (this.a == null) {
            return;
        }
        final int a = this.d.a();
        if (a == 0) {
            return;
        }
        this.k = a * this.a.i();
        this.l = 0;
        this.e = 0.0f;
        this.e();
        if (this.o) {
            if (this.b == null) {
                return;
            }
            this.i = 1000 * this.b.g() / (2 * this.b.k() * this.b.j());
            this.h = a * 1000 / this.i;
            this.j = 0;
            for (int i = 0; i < this.h; ++i) {
                this.g();
            }
        }
        this.f();
    }
    
    public void e() {
        final Bitmap c = this.d.c();
        final a.h d = this.d.d();
        final int a = this.d.a();
        if (c == null || c.isRecycled()) {
            return;
        }
        if (d == null) {
            return;
        }
        if (a == 0) {
            return;
        }
        final ArrayList<a.k> f = new ArrayList<a.k>();
        final int n = a * this.a.i();
        long d2 = com.tencent.liteav.j.e.a(this.a) / 1000L;
        final int n2 = 255 / n;
        int n3 = 100;
        for (int i = 0; i < n; ++i) {
            n3 += n2;
            if (n3 >= 255) {
                n3 = 255;
            }
            final Bitmap a2 = com.tencent.liteav.j.a.a(c, n3);
            final a.k k = new a.k();
            k.b = d;
            k.a = a2;
            k.c = d2;
            k.d = d2 + 1000 / this.a.i();
            f.add(k);
            d2 = k.d;
        }
        this.f = f;
    }
    
    public void f() {
        if (this.n) {
            return;
        }
        if (this.l < this.k - 1) {
            final e l = this.l();
            l.c(this.a.f());
            l.m(this.a.y());
            l.a(this.a.w());
            ++this.l;
            TXCLog.d("TailWaterMarkChain", "===insertTailVideoFrame===mVideoIndex:" + this.l + ",time:" + l.t());
            if (this.g != null) {
                this.g.b(l);
            }
            return;
        }
        this.n = true;
        TXCLog.d("TailWaterMarkChain", "===insertTailVideoFrame===mEndAudio:" + this.m + ",mHasAudioTrack:" + this.o);
        if (this.o) {
            if (this.m) {
                this.n();
            }
            return;
        }
        this.k();
    }
    
    private void k() {
        TXCLog.d("TailWaterMarkChain", "===insertVideoTailVFrame===, lastVideoFrame = " + this.a);
        if (this.a == null) {
            return;
        }
        final e l = this.l();
        l.c(4);
        ++this.l;
        TXCLog.d("TailWaterMarkChain", "===insertVideoTailVFrame===mVideoIndex:" + this.l + ",time:" + l.t() + ",flag:" + l.f());
        if (this.g != null) {
            this.g.b(l);
        }
    }
    
    private e l() {
        final e e = new e(this.a.a(), this.a.b(), this.a.o());
        e.a(this.a.c());
        e.b(this.a.d());
        e.e(this.a.h());
        e.f(this.a.i());
        e.g(this.a.j());
        if (e.h() == 90 || e.h() == 270) {
            e.j(this.a.n());
            e.k(this.a.m());
        }
        else {
            e.j(this.a.m());
            e.k(this.a.n());
        }
        final long p = this.p();
        e.a(p);
        e.b(p);
        e.c(p);
        e.a(true);
        e.m(this.a.y());
        e.a(this.e += 10.0f / this.k);
        return e;
    }
    
    public void g() {
        if (this.m) {
            return;
        }
        if (this.j >= this.h - 1) {
            this.m = true;
            if (this.n) {
                this.n();
            }
            return;
        }
        this.b.a(ByteBuffer.allocate(this.b.g()));
        final e o = this.o();
        o.c(this.b.f());
        ++this.j;
        TXCLog.d("TailWaterMarkChain", "===insertTailAudioFrame===mAudioIndex:" + this.j + ",time:" + o.e());
        if (this.g != null) {
            this.g.a(o);
        }
    }
    
    private long m() {
        return this.b.e() + this.i * (this.j + 1) * 1000;
    }
    
    private void n() {
        TXCLog.d("TailWaterMarkChain", "===insertAudioTailFrame===");
        this.b.a(ByteBuffer.allocate(this.b.g()));
        final e o = this.o();
        o.c(4);
        ++this.j;
        if (this.g != null) {
            this.g.a(o);
        }
    }
    
    private e o() {
        final e e = new e(this.b.a(), this.b.b(), this.b.o());
        e.a(this.b.c());
        e.b(this.b.d());
        e.g(this.b.j());
        e.h(this.b.k());
        final long m = this.m();
        e.a(m);
        e.b(m);
        e.c(m);
        e.a(true);
        return e;
    }
    
    private long p() {
        long n;
        if (CameraProxy.g.a().b()) {
            n = this.a.u() + (this.l + 1) * 1000 / this.a.i() * 1000;
        }
        else if (com.tencent.liteav.f.g.a().c()) {
            n = this.a.t() + (this.l + 1) * 1000 / this.a.i() * 1000;
        }
        else {
            n = this.a.e() + (this.l + 1) * 1000 / this.a.i() * 1000;
        }
        return n;
    }
    
    public List<a.k> h() {
        return this.f;
    }
    
    public void i() {
        if (this.f != null) {
            for (final a.k k : this.f) {
                if (k != null && k.a != null && !k.a.isRecycled()) {
                    k.a.recycle();
                    k.a = null;
                }
            }
            this.f.clear();
        }
        this.f = null;
        if (this.d != null) {
            this.d.b();
        }
        this.d = null;
        this.a = null;
        this.b = null;
        this.e = 0.0f;
        this.j = 0;
        this.l = 0;
        this.h = 0;
        this.k = 0;
        this.m = false;
        this.n = false;
    }
    
    public boolean j() {
        if (this.o) {
            return this.n && this.m;
        }
        return this.n;
    }
}
