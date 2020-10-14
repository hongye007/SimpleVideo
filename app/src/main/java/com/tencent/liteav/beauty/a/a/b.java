package com.tencent.liteav.beauty.a.a;

import javax.microedition.khronos.egl.*;

public class b
{
    protected a a;
    private EGLSurface b;
    private int c;
    private int d;
    
    protected b(final a a) {
        this.b = EGL11.EGL_NO_SURFACE;
        this.c = -1;
        this.d = -1;
        this.a = a;
    }
    
    public void a(final int c, final int d) {
        if (this.b != EGL11.EGL_NO_SURFACE) {
            throw new IllegalStateException("surface already created");
        }
        this.b = this.a.a(c, d);
        this.c = c;
        this.d = d;
    }
    
    public void a() {
        this.a.a(this.b);
        this.b = EGL11.EGL_NO_SURFACE;
        final int n = -1;
        this.d = n;
        this.c = n;
    }
    
    public void b() {
        this.a.b(this.b);
    }
}
