package com.tencent.liteav.beauty.b.a;

import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;

public class a extends b
{
    private c r;
    private d s;
    private e t;
    private k u;
    private aa v;
    private b w;
    private String x;
    private int y;
    private int z;
    private int A;
    private float B;
    private final float C = 0.7f;
    private float D;
    private float E;
    private int F;
    private int G;
    private int H;
    private int I;
    
    public a() {
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = "TXCBeauty2Filter";
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = 1.0f;
        this.D = 0.8f;
        this.E = 2.0f;
        this.F = 0;
        this.G = 0;
        this.H = 0;
        this.I = 0;
    }
    
    @Override
    public boolean c(final int n, final int n2) {
        return this.d(n, n2);
    }
    
    @Override
    public void a(final int e, final int f) {
        if (this.e == e && this.f == f) {
            return;
        }
        this.d(this.e = e, this.f = f);
    }
    
    @Override
    public void c(final int y) {
        if (this.t != null) {
            this.t.a(y / 10.0f);
        }
        this.g(this.y = y);
    }
    
    @Override
    public void d(final int z) {
        if (this.w != null) {
            this.w.a(z / 10.0f);
        }
        this.z = z;
    }
    
    @Override
    public void e(final int a) {
        if (null != this.w) {
            this.w.b(a / 10.0f);
        }
        this.A = a;
    }
    
    private void g(final int n) {
        this.B = 1.0f - n / 50.0f;
        if (this.u != null) {
            this.u.a(this.B);
        }
    }
    
    @Override
    public void f(final int n) {
        final float d = 0.7f + n / 12.0f;
        if (Math.abs(this.D - d) < 0.001) {
            return;
        }
        this.D = d;
        TXCLog.i(this.x, "set mSharpenLevel " + n);
        if (null != this.v) {
            this.v.a(this.D);
        }
    }
    
    @Override
    public int a(final int n) {
        if (1.0f != this.E) {
            GLES20.glViewport(0, 0, this.H, this.I);
        }
        int n2 = this.t.c(this.s.a(n), n);
        if (1.0f != this.E) {
            GLES20.glViewport(0, 0, this.F, this.G);
        }
        if (this.D > 0.7f) {
            n2 = this.v.a(n2);
        }
        return this.w.c(n2, n);
    }
    
    private boolean d(final int n, final int n2) {
        this.F = n;
        this.G = n2;
        this.H = n;
        this.I = n2;
        if (1.0f != this.E) {
            this.H /= (int)this.E;
            this.I /= (int)this.E;
        }
        TXCLog.i(this.x, "mResampleRatio " + this.E + " mResampleWidth " + this.H + " mResampleHeight " + this.I);
        if (null == this.w) {
            (this.w = new b()).a(true);
            if (!this.w.c()) {
                TXCLog.e(this.x, "mBeautyBlendFilter init failed!!, break init");
                return false;
            }
        }
        this.w.a(n, n2);
        if (null == this.s) {
            (this.s = new d()).a(true);
            if (!this.s.c()) {
                TXCLog.e(this.x, "m_horizontalFilter init failed!!, break init");
                return false;
            }
        }
        this.s.a(this.H, this.I);
        if (null == this.t) {
            (this.t = new e()).a(true);
            this.t.b(1.0f != this.E);
            if (!this.t.c()) {
                TXCLog.e(this.x, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.t.a(this.H, this.I);
        if (null == this.u) {
            (this.u = new k(1.0f)).a(true);
            if (!this.u.c()) {
                TXCLog.e(this.x, "m_gammaFilter init failed!!, break init");
                return false;
            }
        }
        this.u.a(this.H, this.I);
        if (null == this.v) {
            (this.v = new aa()).a(true);
            if (!this.v.c()) {
                TXCLog.e(this.x, "mSharpenFilter init failed!!, break init");
                return false;
            }
        }
        this.v.a(n, n2);
        return true;
    }
    
    void r() {
        if (null != this.w) {
            this.w.e();
            this.w = null;
        }
        if (null != this.s) {
            this.s.e();
            this.s = null;
        }
        if (null != this.t) {
            this.t.e();
            this.t = null;
        }
        if (null != this.u) {
            this.u.e();
            this.u = null;
        }
        if (null != this.v) {
            this.v.e();
            this.v = null;
        }
    }
    
    @Override
    public void b() {
        super.b();
        this.r();
    }
}
