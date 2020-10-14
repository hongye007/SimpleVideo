package com.tencent.liteav.beauty.b.c;

import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.basic.log.*;

public class a extends b
{
    private d r;
    private d s;
    private b t;
    private c u;
    private d v;
    private d w;
    private float x;
    private float y;
    private float z;
    
    public a() {
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = 0.2f;
        this.y = 0.2f;
        this.z = 0.2f;
    }
    
    @Override
    public boolean c(final int e, final int f) {
        this.e = e;
        this.f = f;
        return this.d(e, f);
    }
    
    @Override
    public void a(final int e, final int f) {
        super.a(e, f);
        this.e = e;
        this.f = f;
        this.r.a(e, f);
        this.s.a(e, f);
        this.t.a(e, f);
        this.v.a(e, f);
        this.w.a(e, f);
        this.u.a(e, f);
    }
    
    private boolean d(final int n, final int n2) {
        if (null == this.r) {
            (this.r = new d(true)).a(true);
            if (!this.r.c()) {
                TXCLog.e("TXCBeauty4Filter", "mSkinBlurFilterVertical init failed!!, break init");
                return false;
            }
        }
        if (null == this.s) {
            (this.s = new d(false)).a(true);
            if (!this.s.c()) {
                TXCLog.e("TXCBeauty4Filter", "mSkinBlurFilterHorizontal init failed!!, break init");
                return false;
            }
        }
        if (null == this.t) {
            (this.t = new b()).a(true);
            if (!this.t.c()) {
                TXCLog.e("TXCBeauty4Filter", "mBorderFilter init failed!!, break init");
                return false;
            }
        }
        if (null == this.v) {
            (this.v = new d(true)).a(true);
            if (!this.v.c()) {
                TXCLog.e("TXCBeauty4Filter", "mBorderBlurFilterVertical init failed!!, break init");
                return false;
            }
        }
        if (null == this.w) {
            (this.w = new d(false)).a(true);
            if (!this.w.c()) {
                TXCLog.e("TXCBeauty4Filter", "mBorderBlurFilterHorizontal init failed!!, break init");
                return false;
            }
        }
        if (null == this.u) {
            (this.u = new c()).a(true);
            if (!this.u.c()) {
                TXCLog.e("TXCBeauty4Filter", "mSmoothFilter init failed!!, break init");
                return false;
            }
        }
        this.u.a(360.0f, 640.0f);
        this.u.a(this.x);
        this.u.b(this.y);
        this.u.c(this.z);
        this.a(n, n2);
        return true;
    }
    
    @Override
    public int a(final int n) {
        int a = n;
        if (this.x > 0.0f || this.y > 0.0f || this.z > 0.0f) {
            final int a2 = this.s.a(this.r.a(n));
            a = this.u.a(n, a2, this.w.a(this.v.a(this.t.c(n, a2))));
        }
        return a;
    }
    
    @Override
    public void c(final int n) {
        this.x = n / 10.0f;
        if (null != this.u) {
            this.u.a(this.x);
        }
    }
    
    @Override
    public void d(final int n) {
        this.y = n / 10.0f;
        if (null != this.u) {
            this.u.b(this.y);
        }
    }
    
    @Override
    public void e(final int n) {
        this.z = n / 10.0f;
        if (null != this.u) {
            this.u.c(this.z);
        }
    }
    
    @Override
    public void f(final int n) {
        this.u.d(n / 10.0f);
    }
    
    void r() {
        if (null != this.r) {
            this.r.b();
            this.r = null;
        }
        if (null != this.s) {
            this.s.b();
            this.s = null;
        }
        if (null != this.t) {
            this.t.b();
            this.t = null;
        }
        if (null != this.u) {
            this.u.b();
            this.u = null;
        }
        if (null != this.v) {
            this.v.b();
            this.v = null;
        }
        if (null != this.w) {
            this.w.b();
            this.w = null;
        }
    }
    
    @Override
    public void b() {
        super.b();
        this.r();
    }
}
