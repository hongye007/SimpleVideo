package com.tencent.liteav.c;

public class g
{
    private static g a;
    private boolean b;
    
    public g() {
        this.b = false;
    }
    
    public static g a() {
        if (g.a == null) {
            synchronized (g.class) {
                if (g.a == null) {
                    g.a = new g();
                }
            }
        }
        return g.a;
    }
    
    public void a(final boolean b) {
        this.b = b;
    }
    
    public boolean b() {
        return this.b;
    }
    
    public void c() {
        this.b = false;
    }
}
