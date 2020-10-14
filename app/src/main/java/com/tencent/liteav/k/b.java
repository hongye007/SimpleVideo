package com.tencent.liteav.k;

import com.tencent.liteav.beauty.*;
import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.basic.c.*;
import android.util.*;
import android.opengl.*;

public class b
{
    private d a;
    private o b;
    private m c;
    private m d;
    private n e;
    private h f;
    private f g;
    private int h;
    private int i;
    private static String j;
    private int k;
    private com.tencent.liteav.k.n.c l;
    private com.tencent.liteav.k.n.c.a m;
    private com.tencent.liteav.k.n.c.a n;
    private int[] o;
    private j.a p;
    private j.a q;
    private float r;
    
    public b() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = 0;
        this.i = 0;
        this.k = 1;
        this.l = null;
        this.m = com.tencent.liteav.k.n.c.a.a;
        this.n = com.tencent.liteav.k.n.c.a.a;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = 0.0f;
    }
    
    public boolean a(final int h, final int i) {
        this.h = h;
        this.i = i;
        if (null == this.p || h != this.p.c || i != this.p.d) {
            this.p = com.tencent.liteav.basic.c.j.a(this.p);
            this.p = com.tencent.liteav.basic.c.j.a(this.p, h, i);
        }
        if (null == this.q || h != this.q.c || i != this.q.d) {
            this.q = com.tencent.liteav.basic.c.j.a(this.q);
            this.q = com.tencent.liteav.basic.c.j.a(this.q, h, i);
        }
        return this.c(h, i);
    }
    
    private boolean c(final int n, final int n2) {
        if (null == this.a) {
            this.a = new d();
            if (!this.a.a(n, n2)) {
                Log.e(com.tencent.liteav.k.b.j, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (null != this.a) {
            this.a.b(this.k);
            this.a.b(n, n2);
        }
        if (null == this.b) {
            this.b = new o();
            if (!this.b.a(n, n2)) {
                Log.e(com.tencent.liteav.k.b.j, "mGridShapeFilter init Failed!");
                return false;
            }
        }
        if (null != this.b) {
            this.b.b(n, n2);
        }
        if (null == this.c) {
            (this.c = new m()).a(true);
            if (!this.c.c()) {
                Log.e(com.tencent.liteav.k.b.j, "mScaleFilter init Failed!");
                return false;
            }
        }
        if (null != this.c) {
            this.c.a(n, n2);
        }
        if (null == this.d) {
            (this.d = new m()).a(true);
            if (!this.d.c()) {
                Log.e(com.tencent.liteav.k.b.j, "mScaleFilter2 init Failed!");
                return false;
            }
        }
        if (null != this.d) {
            this.d.a(n, n2);
        }
        if (null == this.e) {
            (this.e = new n()).a(true);
            if (!this.e.c()) {
                Log.e(com.tencent.liteav.k.b.j, "mGridShapeFilter init Failed!");
                return false;
            }
        }
        if (null != this.e) {
            this.e.a(n, n2);
        }
        if (null == this.f) {
            this.f = new h();
            if (!this.f.c()) {
                Log.e(com.tencent.liteav.k.b.j, "mDrawFilter init Failed!");
                return false;
            }
        }
        if (null != this.f) {
            this.f.a(n, n2);
        }
        if (null == this.g) {
            this.g = new f();
            if (!this.g.c()) {
                Log.e(com.tencent.liteav.k.b.j, "mColorBrushFilter init Failed!");
                return false;
            }
        }
        if (null != this.g) {
            this.g.a(n, n2);
        }
        return true;
    }
    
    public void b(final int n, final int n2) {
        if (n == this.h && n2 == this.i) {
            return;
        }
        this.a(n, n2);
    }
    
    private void b() {
        if (null != this.a) {
            this.a.b();
            this.a = null;
        }
        if (null != this.b) {
            this.b.a();
            this.b = null;
        }
        if (null != this.c) {
            this.c.e();
            this.c = null;
        }
        if (null != this.e) {
            this.e.e();
            this.e = null;
        }
        if (null != this.f) {
            this.f.e();
            this.f = null;
        }
        if (null != this.g) {
            this.g.e();
            this.g = null;
        }
    }
    
    private void c() {
        if (null != this.q) {
            com.tencent.liteav.basic.c.j.a(this.q);
            this.q = null;
        }
        if (null != this.p) {
            com.tencent.liteav.basic.c.j.a(this.p);
            this.p = null;
        }
        if (null != this.o) {
            GLES20.glDeleteTextures(1, this.o, 0);
            this.o = null;
        }
    }
    
    public void a(final com.tencent.liteav.k.n.c l) {
        this.l = l;
        if (null == this.l) {
            return;
        }
        if (null != this.b) {
            this.b.a(this.l);
        }
        if (null != this.c) {
            this.c.a(this.l.j);
        }
        if (com.tencent.liteav.k.n.c.a.b == l.g) {
            this.r = l.j;
        }
    }
    
    public int a(final int n) {
        if (null == this.l) {
            return n;
        }
        int a = n;
        int a2 = n;
        int n2 = n;
        if (null == this.o) {
            (this.o = new int[1])[0] = com.tencent.liteav.basic.c.j.a(this.h, this.i, 6408, 6408, this.o);
        }
        if (null != this.b) {
            a = this.b.a(a);
        }
        if (null != this.c) {
            a2 = this.c.a(a2);
        }
        if (null != this.a) {
            if (com.tencent.liteav.k.n.c.a.c == this.l.g) {
                this.d.a(this.r);
                final int a3 = this.d.a(n);
                final int a4 = this.a.a(a3);
                if (a4 > 0) {
                    n2 = a4;
                }
                if (com.tencent.liteav.k.n.c.a.b == this.m) {
                    n2 = a3;
                }
            }
            else {
                final int a5 = this.a.a(n);
                if (a5 > 0) {
                    n2 = a5;
                }
                if (com.tencent.liteav.k.n.c.a.c == this.m) {
                    n2 = n;
                }
            }
            this.m = this.l.g;
        }
        if (null != this.q) {
            GLES20.glBindFramebuffer(36160, this.q.a[0]);
            if (this.l.k) {
                this.g.d(this.o[0], a);
            }
            else {
                this.g.d(this.q.b[0], a);
            }
            GLES20.glBindFramebuffer(36160, 0);
        }
        GLES20.glBindFramebuffer(36160, this.p.a[0]);
        if (null != this.e && null != this.q) {
            this.e.b(this.q.b[0], n2, a2);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return this.p.b[0];
    }
    
    public void a() {
        this.b();
        this.c();
    }
    
    static {
        b.j = "Diffuse";
    }
}
