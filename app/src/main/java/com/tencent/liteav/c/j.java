package com.tencent.liteav.c;

import com.tencent.liteav.CaptureAndEnc;

public class j
{
    private static j b;
    private CaptureAndEnc.j c;
    private h d;
    private c e;
    private d f;
    public float a;
    private int g;
    private int h;
    
    public static j a() {
        if (j.b == null) {
            j.b = new j();
        }
        return j.b;
    }
    
    private j() {
        this.h();
    }
    
    private void h() {
        this.g();
    }
    
    public void a(final CaptureAndEnc.j c) {
        this.c = c;
    }
    
    public CaptureAndEnc.j b() {
        return this.c;
    }
    
    public void a(final c e) {
        this.e = e;
    }
    
    public c c() {
        return this.e;
    }
    
    public d d() {
        return this.f;
    }
    
    public void a(final d f) {
        this.f = f;
    }
    
    public int e() {
        return this.g;
    }
    
    public void a(final int g) {
        this.g = g;
    }
    
    public int f() {
        return this.h;
    }
    
    public void b(final int h) {
        this.h = h;
    }
    
    public void g() {
        this.a = 1.0f;
        if (this.c != null) {
            this.c.b();
        }
        this.c = null;
        if (this.d != null) {
            this.d.a();
        }
        if (this.f != null) {
            this.f.a();
        }
        this.d = null;
        this.e = null;
        this.g = 0;
    }
}
