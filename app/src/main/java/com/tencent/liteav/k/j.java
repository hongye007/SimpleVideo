package com.tencent.liteav.k;

import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.beauty.*;
import com.tencent.liteav.basic.log.*;

public class j
{
    protected al a;
    private l d;
    protected n.l b;
    e.f[] c;
    private static String e;
    private int f;
    private int g;
    
    public j() {
        this.a = null;
        this.d = null;
        this.b = null;
        this.c = null;
        this.f = -1;
        this.g = -1;
    }
    
    public boolean a(final int n, final int n2) {
        return this.c(n, n2);
    }
    
    private boolean c(final int f, final int g) {
        if (f == this.f && g == this.g) {
            return true;
        }
        this.f = f;
        this.g = g;
        if (null == this.a) {
            (this.a = new al()).a(true);
            if (!this.a.c()) {
                TXCLog.e(j.e, "mZoomInOutFilter init error!");
                return false;
            }
        }
        this.a.a(f, g);
        if (null == this.d) {
            (this.d = new l()).a(true);
            if (!this.d.c()) {
                TXCLog.e(j.e, "mTextureWaterMarkFilter init error!");
                return false;
            }
        }
        this.d.a(f, g);
        return true;
    }
    
    private void b() {
        if (null != this.a) {
            this.a.e();
            this.a = null;
        }
        if (null != this.d) {
            this.d.e();
            this.d = null;
        }
    }
    
    public void a(final n.l b) {
        this.b = b;
    }
    
    public int a(final int n) {
        int a = n;
        if (null == this.b || null == this.a) {
            return a;
        }
        this.a.a(0.96f, this.b.g);
        this.a.a(this.b.h);
        for (int i = 0; i < this.b.f; ++i) {
            if (i >= 1) {
                this.a.a(0.9f, this.b.g + i);
            }
            final int a2 = this.a.a(n);
            final e.f[] array = { new e.f() };
            array[0].e = a2;
            array[0].f = this.f;
            array[0].g = this.g;
            array[0].b = 0.0f;
            array[0].c = 0.0f;
            array[0].d = 1.0f;
            if (null != this.d) {
                this.d.a(array);
                a = this.d.a(a);
            }
        }
        return a;
    }
    
    public void b(final int n, final int n2) {
        this.c(n, n2);
    }
    
    public void a() {
        this.b();
    }
    
    static {
        j.e = "SpiritOut";
    }
}
