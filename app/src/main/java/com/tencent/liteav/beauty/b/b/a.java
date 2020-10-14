package com.tencent.liteav.beauty.b.b;

import com.tencent.liteav.beauty.b.*;
import android.util.*;

public class a extends b
{
    private com.tencent.liteav.beauty.b.b.b r;
    private ab s;
    private String t;
    private float u;
    private float v;
    private float w;
    private float x;
    
    public a() {
        this.r = null;
        this.s = null;
        this.t = "TXCBeauty3Filter";
        this.u = 0.0f;
        this.v = 0.0f;
        this.w = 0.0f;
        this.x = 0.0f;
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
    
    private boolean d(final int n, final int n2) {
        if (null == this.r) {
            (this.r = new b()).a(true);
            if (!this.r.c()) {
                Log.e(this.t, "m_verticalFilter init failed!!, break init");
                return false;
            }
        }
        this.r.a(n, n2);
        if (null == this.s) {
            (this.s = new ab()).a(true);
            if (!this.s.c()) {
                Log.e(this.t, "mSharpnessFilter init failed!!, break init");
                return false;
            }
        }
        this.s.a(n, n2);
        return true;
    }
    
    @Override
    public int a(final int n) {
        int n2 = n;
        if (this.u > 0.0f || this.v > 0.0f || this.w > 0.0f) {
            n2 = this.r.a(n2);
        }
        if (this.x > 0.0f) {
            n2 = this.s.a(n2);
        }
        return n2;
    }
    
    @Override
    public void c(final int n) {
        this.u = n / 10.0f;
        if (null != this.r) {
            this.r.a(this.u);
        }
    }
    
    @Override
    public void d(final int n) {
        this.v = n / 10.0f;
        if (null != this.r) {
            this.r.b(this.v);
        }
    }
    
    @Override
    public void e(final int n) {
        this.w = n / 10.0f;
        if (null != this.r) {
            this.r.c(this.w);
        }
    }
    
    @Override
    public void f(final int n) {
        final float x = n / 20.0f;
        if (Math.abs(this.x - x) < 0.001) {
            return;
        }
        this.x = x;
        if (null != this.s) {
            this.s.a(this.x);
        }
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
    }
    
    @Override
    public void b() {
        super.b();
        this.r();
    }
}
