package com.tencent.liteav.k;

import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.beauty.*;
import android.util.*;

public class e
{
    private h b;
    private static String c;
    n.f a;
    private d d;
    private int e;
    private int f;
    
    public e() {
        this.b = null;
        this.a = null;
        this.d = null;
        this.e = 0;
        this.f = 0;
    }
    
    public boolean a(final int e, final int f) {
        this.e = e;
        this.f = f;
        return this.c(e, f);
    }
    
    private boolean c(final int n, final int n2) {
        if (null == this.b) {
            (this.b = new h()).a(true);
            if (!this.b.c()) {
                Log.e(com.tencent.liteav.k.e.c, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (null != this.b) {
            this.b.a(n, n2);
        }
        if (null == this.d) {
            this.d = new d();
            if (!this.d.a(n, n2)) {
                Log.e(com.tencent.liteav.k.e.c, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (null != this.d) {
            this.d.b(n, n2);
        }
        return true;
    }
    
    private void b() {
        if (null != this.b) {
            this.b.e();
            this.b = null;
        }
        if (null != this.d) {
            this.d.b();
            this.d = null;
        }
    }
    
    public void a(final n.f a) {
        this.a = a;
        if (null == a) {
            Log.i(com.tencent.liteav.k.e.c, "GhostShadowParam is null, reset list");
            if (null != this.d) {
                this.d.a();
            }
        }
    }
    
    public int a(int c) {
        if (!this.a(this.a, this.e, this.f)) {
            return c;
        }
        int n = c;
        if (null != this.d) {
            final int a = this.d.a(c);
            if (a > 0) {
                n = a;
            }
        }
        if (null != this.b) {
            c = this.b.c(c, n);
        }
        return c;
    }
    
    private boolean a(final n.f f, final int n, final int n2) {
        if (null == f) {
            return false;
        }
        if (null != this.d) {
            this.d.b(f.a);
        }
        return true;
    }
    
    public void b(final int n, final int n2) {
        this.c(n, n2);
    }
    
    public void a() {
        this.b();
    }
    
    static {
        e.c = "GhostShadow";
    }
}
