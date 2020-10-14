package com.tencent.liteav.beauty.b;

import com.tencent.liteav.k.*;
import android.util.*;

public class o
{
    private f a;
    private e b;
    private z c;
    private ac d;
    private static String e;
    private int f;
    private int g;
    private n.c h;
    
    public o() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.f = 0;
        this.g = 0;
        this.h = null;
    }
    
    public boolean a(final int n, final int n2) {
        return this.c(n, n2);
    }
    
    private boolean c(final int n, final int n2) {
        if (null == this.a) {
            (this.a = new f()).a(true);
            if (!this.a.c()) {
                Log.e(o.e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (null != this.a) {
            this.a.a(n, n2);
        }
        if (null == this.b) {
            (this.b = new e()).a(true);
            if (!this.b.c()) {
                Log.e(o.e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (null != this.b) {
            this.b.a(n, n2);
        }
        if (null == this.c) {
            (this.c = new z()).a(true);
            if (!this.c.c()) {
                Log.e(o.e, "mRotateScaleFilter init Failed!");
                return false;
            }
        }
        if (null != this.c) {
            this.c.a(n, n2);
        }
        return true;
    }
    
    private void b() {
        if (null != this.a) {
            this.a.e();
            this.a = null;
        }
        if (null != this.b) {
            this.b.e();
            this.b = null;
        }
        if (null != this.c) {
            this.c.e();
            this.c = null;
        }
    }
    
    public void a(final n.c h) {
        this.h = h;
        if (null != this.a) {
            this.a.a(this.h);
        }
        if (null != this.c) {
            this.c.a(this.h.d);
        }
        if (null != this.b) {
            this.b.a(this.h.i);
            this.b.b(this.h.h);
        }
    }
    
    public void b(final int n, final int n2) {
        if (n == this.f && n2 == this.g) {
            return;
        }
        this.c(n, n2);
    }
    
    public int a(final int n) {
        if (null == this.h) {
            return n;
        }
        int n2 = n;
        if (null != this.a) {
            n2 = this.a.a(n2);
        }
        if (null != this.c) {
            n2 = this.c.a(n2);
        }
        if (null != this.b) {
            n2 = this.b.a(n2);
        }
        return n2;
    }
    
    public void a() {
        this.b();
    }
    
    static {
        o.e = "GridShape";
    }
}
