package com.tencent.liteav.beauty;

import java.util.*;
import com.tencent.liteav.basic.c.*;
import android.util.*;
import android.opengl.*;

public class d
{
    private ArrayList<j.a> a;
    private ArrayList<j.a> b;
    private int c;
    private h d;
    private static String e;
    private j.a[] f;
    private int g;
    private int h;
    
    public d() {
        this.a = new ArrayList<j.a>();
        this.b = new ArrayList<j.a>();
        this.c = 1;
        this.d = null;
        this.f = null;
        this.g = -1;
        this.h = -1;
    }
    
    public boolean a(final int n, final int n2) {
        return this.c(n, n2);
    }
    
    private boolean c(final int g, final int h) {
        if (null == this.d) {
            (this.d = new h()).a(true);
            if (!this.d.c()) {
                Log.e(com.tencent.liteav.beauty.d.e, "mDissolveBlendFilter init Failed!");
                return false;
            }
        }
        if (null != this.d) {
            this.d.a(g, h);
        }
        this.g = g;
        this.h = h;
        return true;
    }
    
    public void b(final int n, final int n2) {
        this.c(n, n2);
    }
    
    public int a(final int n) {
        j.a a = null;
        int a2 = -1;
        if (this.a.size() >= this.c) {
            if (this.a.size() > 0) {
                a = this.a.get(0);
            }
            if (null != a) {
                if (null != this.d) {
                    a2 = this.d.a(a.b[0]);
                }
                this.b.add(a);
                this.a.remove(0);
            }
        }
        j.a a3 = null;
        if (this.b.size() > 0) {
            a3 = this.b.get(0);
        }
        if (null != a3) {
            GLES20.glBindFramebuffer(36160, a3.a[0]);
            if (null != this.d) {
                this.d.b(n);
            }
            GLES20.glBindFramebuffer(36160, 0);
            this.a.add(a3);
            this.b.remove(0);
        }
        return a2;
    }
    
    public void b(final int c) {
        this.c = c;
        if (null == this.f || this.f.length != this.c) {
            j.a(this.f);
            this.a();
            this.f = j.a(this.f, this.c, this.g, this.h);
            for (int i = 0; i < this.f.length; ++i) {
                this.b.add(this.f[i]);
            }
        }
    }
    
    public void a() {
        this.b.clear();
        this.a.clear();
    }
    
    public void b() {
        if (null != this.d) {
            this.d.e();
            this.d = null;
        }
        j.a(this.f);
        this.f = null;
        this.a();
    }
    
    static {
        d.e = "avePreFrame";
    }
}
