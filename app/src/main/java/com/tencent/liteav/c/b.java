package com.tencent.liteav.c;

import android.text.*;

public class b
{
    private static b l;
    public String a;
    public long b;
    public long c;
    public long d;
    public boolean e;
    public float f;
    public float g;
    public boolean h;
    public boolean i;
    public long j;
    public long k;
    
    public static b a() {
        if (b.l == null) {
            b.l = new b();
        }
        return b.l;
    }
    
    private b() {
        this.b();
    }
    
    public void a(final String a) {
        if (TextUtils.isEmpty((CharSequence)a)) {
            this.b();
            return;
        }
        if (this.a == null || !this.a.equals(a)) {
            this.a = a;
        }
    }
    
    public void b() {
        this.c();
        this.f = 1.0f;
        this.i = false;
        this.j = 0L;
        this.k = 0L;
    }
    
    private void c() {
        this.a = null;
        this.b = -1L;
        this.c = -1L;
        this.h = false;
        this.e = false;
    }
}
