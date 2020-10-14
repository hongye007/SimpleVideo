package com.tencent.liteav.beauty.a.a;

import android.view.*;

public class c extends b
{
    private Surface b;
    private boolean c;
    
    public c(final a a, final int n, final int n2, final boolean c) {
        super(a);
        this.a(n, n2);
        this.c = c;
    }
    
    public void c() {
        this.a();
        if (this.b != null) {
            if (this.c) {
                this.b.release();
            }
            this.b = null;
        }
    }
}
