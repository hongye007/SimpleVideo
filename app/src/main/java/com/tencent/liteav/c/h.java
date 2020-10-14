package com.tencent.liteav.c;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.basic.log.*;
import java.util.*;

public class h
{
    private static h a;
    private ArrayList<Long> b;
    private int c;
    private a.l d;
    private int e;
    private int f;
    private boolean g;
    
    public static h a() {
        if (h.a == null) {
            h.a = new h();
        }
        return h.a;
    }
    
    public void a(final a.l d) {
        this.d = d;
    }
    
    public void a(final ArrayList<Long> b) {
        this.b = b;
    }
    
    public void a(final long n) {
        if (n < 0L) {
            return;
        }
        this.b = new ArrayList<Long>();
        if (this.d == null || this.d.a <= 0) {
            return;
        }
        final c a = CameraProxy.c.a();
        final long f = a.f();
        final long g = a.g();
        TXCLog.i("ThumbnailConfig", "calculateThumbnailList startTimeUs : " + f + ", endTimeUs : " + g);
        long n2;
        if (g - f > 0L) {
            n2 = g - f;
        }
        else {
            n2 = n;
        }
        final long n3 = n2 / this.d.a;
        for (int i = 0; i < this.d.a; ++i) {
            long n4 = n3 * i + f;
            if (g > 0L && g < n) {
                if (n4 > g) {
                    n4 = g;
                }
            }
            else if (n4 > n) {
                n4 = n;
            }
            TXCLog.i("ThumbnailConfig", "calculateThumbnailList frameTime : " + n4);
            this.b.add(n4);
        }
    }
    
    public List<Long> b() {
        return this.b;
    }
    
    public int c() {
        if (this.d == null) {
            return 0;
        }
        return this.d.a;
    }
    
    public g d() {
        final g g = new g();
        if (this.d != null) {
            g.a = this.d.b;
            g.b = this.d.c;
        }
        else if (this.f != 0 && this.e != 0) {
            g.b = this.f;
            g.a = this.e;
        }
        return g;
    }
    
    public boolean e() {
        return this.b == null || this.b.size() <= 0;
    }
    
    public long f() {
        return this.b.get(0);
    }
    
    public long g() {
        ++this.c;
        return this.b.remove(0);
    }
    
    public int h() {
        return this.c;
    }
    
    public void i() {
        this.c = 0;
        this.b = null;
        this.g = false;
    }
    
    public void j() {
        this.i();
        this.d = null;
    }
    
    public void a(final int n) {
        this.e = n;
        if (this.d != null) {
            this.d.b = n;
        }
    }
    
    public void b(final int n) {
        this.f = n;
        if (this.d != null) {
            this.d.c = n;
        }
    }
    
    public boolean k() {
        return this.g;
    }
    
    public void a(final boolean g) {
        this.g = g;
    }
}
