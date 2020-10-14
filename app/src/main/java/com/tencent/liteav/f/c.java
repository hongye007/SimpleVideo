package com.tencent.liteav.f;

public class c
{
    protected int a;
    protected int b;
    protected e c;
    
    public void a(final g g) {
        this.a = g.a;
        this.b = g.b;
    }
    
    protected g b(final e e) {
        final g g = new g();
        final float n = this.a * 1.0f / e.m();
        final float n2 = this.b * 1.0f / e.n();
        float n3;
        if (i.a().s == 2) {
            n3 = ((n > n2) ? n2 : n);
        }
        else {
            n3 = ((n < n2) ? n2 : n);
        }
        g.a = (int)(e.m() * n3);
        g.b = (int)(e.n() * n3);
        return g;
    }
    
    protected a.h a(final a.h h, final g g) {
        final a.h h2 = new a.h();
        h2.a = (h.a - (this.a - g.a) / 2) / g.a;
        h2.b = (h.b - (this.b - g.b) / 2) / g.b;
        h2.c = h.c / g.a;
        return h2;
    }
    
    public void c(final e c) {
        this.c = c;
    }
    
    protected void c() {
        this.a = 0;
        this.b = 0;
        this.c = null;
    }
}
