package com.tencent.liteav.l;

import com.tencent.liteav.beauty.b.*;
import android.util.*;
import android.opengl.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.c.*;
import java.nio.*;

public class b
{
    private ai f;
    h a;
    h b;
    private v g;
    private final int h = 2;
    private final int i = 3;
    private int j;
    private int k;
    private int l;
    private int m;
    private a n;
    private float[] o;
    protected j.a[] c;
    protected j.a d;
    protected int[] e;
    private f p;
    private String q;
    
    public b() {
        this.f = null;
        this.a = null;
        this.b = null;
        this.g = null;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = null;
        this.o = new float[] { 0.0f, 0.0f, 0.0f, 0.0f };
        this.c = null;
        this.d = null;
        this.e = null;
        this.p = null;
        this.q = "CombineVideoFilter";
    }
    
    public void a(final int j, final int k) {
        if (j > 0 && k > 0 && (j != this.j || k != this.k)) {
            j.a(this.c);
            this.c = null;
        }
        this.j = j;
        this.k = k;
    }
    
    public void b(final int l, final int m) {
        if (l > 0 && m > 0 && (l != this.l || m != this.m)) {
            this.b();
        }
        this.l = l;
        this.m = m;
    }
    
    public void a(final a n) {
        this.n = n;
    }
    
    public int a(final com.tencent.liteav.basic.e.a[] array, final int n) {
        if (null == array || this.j <= 0 || this.k <= 0) {
            Log.e(this.q, "frames or canvaceSize if null!");
            return -1;
        }
        this.a(array);
        int n2 = 0;
        if (null != this.f) {
            for (int i = 0; i < array.length; ++i) {
                GLES20.glBindFramebuffer(36160, this.c[i].a[0]);
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
                GLES20.glClear(16640);
                final e.f[] array2 = { new e.f() };
                array2[0].e = array[i].a;
                array2[0].f = array[i].g.c;
                array2[0].g = array[i].g.d;
                array2[0].b = array[i].g.a * 1.0f / this.j;
                array2[0].c = array[i].g.b * 1.0f / this.k;
                array2[0].d = array[i].g.c * 1.0f / this.j;
                if (null != array[i].e) {
                    this.f.a(array[i].e.c);
                    this.f.c(array[i].e.d);
                }
                this.f.a(array2);
                GLES20.glViewport(0, 0, this.j, this.k);
                if (i == 0) {
                    this.f.b(this.d.b[0]);
                }
                else {
                    this.f.b(this.c[i - 1].b[0]);
                }
                GLES20.glBindFramebuffer(36160, 0);
                n2 = i;
            }
        }
        int n3 = this.c[n2].b[0];
        int n4 = this.j;
        int n5 = this.k;
        if (null != this.b && null != this.n) {
            GLES20.glViewport(0, 0, this.n.c, this.n.d);
            n3 = this.b.a(n3);
        }
        if (null != this.a) {
            GLES20.glViewport(0, 0, this.l, this.m);
            n3 = this.a.a(n3);
            n4 = this.l;
            n5 = this.m;
        }
        if (null != this.p) {
            this.p.didProcessFrame(n3, n4, n5, n);
        }
        return n3;
    }
    
    public void a() {
        com.tencent.liteav.basic.c.j.a(this.c);
        this.c = null;
        this.d();
        if (null != this.d) {
            com.tencent.liteav.basic.c.j.a(this.d);
            this.d = null;
        }
    }
    
    private void a(final int n, final int n2, final int n3) {
        if (n <= 0 || n2 <= 0 || (null != this.c && n3 == this.c.length)) {
            return;
        }
        com.tencent.liteav.basic.c.j.a(this.c);
        this.c = null;
        this.c = com.tencent.liteav.basic.c.j.a(this.c, n3, n, n2);
        if (null == this.e) {
            (this.e = new int[1])[0] = com.tencent.liteav.basic.c.j.a(n, n2, 6408, 6408, this.e);
        }
        if (null != this.d) {
            com.tencent.liteav.basic.c.j.a(this.d);
            this.d = null;
        }
        if (null == this.d) {
            this.d = com.tencent.liteav.basic.c.j.a(this.d, n, n2);
        }
        if (null != this.g) {
            GLES20.glBindFramebuffer(36160, this.d.a[0]);
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glClear(16640);
            this.g.b(this.o);
            this.g.b(-1);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }
    
    private void c(final int n, final int n2) {
        if (null == this.a) {
            (this.a = new h()).a(true);
            if (!this.a.c()) {
                TXCLog.e(this.q, "mOutputFilter.init failed!");
                return;
            }
        }
        if (null != this.a) {
            this.a.a(n, n2);
        }
    }
    
    private void d(final int n, final int n2) {
        if (null == this.f) {
            (this.f = new ai()).a(true);
            if (!this.f.c()) {
                TXCLog.e(this.q, "TXCGPUWatermarkTextureFilter.init failed!");
                return;
            }
        }
        if (null != this.f) {
            this.f.a(n, n2);
        }
    }
    
    private boolean e(final int n, final int n2) {
        if (null == this.b) {
            (this.b = new h()).a(true);
            if (!this.b.c()) {
                TXCLog.e(this.q, "mCropFilter.init failed!");
                return false;
            }
        }
        if (null != this.b) {
            this.b.a(n, n2);
        }
        return true;
    }
    
    private void b() {
        if (null != this.a) {
            this.a.e();
            this.a = null;
        }
    }
    
    private void c() {
        if (null != this.b) {
            this.b.e();
            this.b = null;
        }
    }
    
    private void a(final com.tencent.liteav.basic.e.a[] array) {
        this.d(this.j, this.k);
        if (null == this.g) {
            this.g = new v();
            if (!this.g.c()) {
                TXCLog.e(this.q, "mCropFilter.init failed!");
                return;
            }
        }
        if (null != this.g) {
            this.g.a(this.j, this.k);
        }
        this.a(this.j, this.k, array.length);
        if (null != this.n) {
            this.e(this.n.c, this.n.d);
            if (null != this.b) {
                this.b.a(com.tencent.liteav.basic.c.l.e, this.b.a(this.j, this.k, null, this.n, 0));
            }
        }
        else {
            this.c();
        }
        if (this.l > 0 && this.m > 0) {
            this.c(this.l, this.m);
        }
    }
    
    private void d() {
        if (null != this.f) {
            this.f.e();
            this.f = null;
        }
        this.b();
        if (null != this.b) {
            this.b.e();
            this.b = null;
        }
    }
}
