package com.tencent.liteav.d;

import android.graphics.*;

public class d
{
    private float a;
    private Bitmap b;
    private Bitmap c;
    private float d;
    private float e;
    
    public d() {
    }
    
    public d(final float a, final Bitmap b, final float d, final Bitmap c, final float e) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
    }
    
    public void a() {
        if (this.b != null && !this.b.isRecycled()) {
            this.b.recycle();
            this.b = null;
        }
        if (this.c != null && !this.c.isRecycled()) {
            this.c.recycle();
            this.c = null;
        }
    }
    
    public float b() {
        return this.d;
    }
    
    public float c() {
        return this.e;
    }
    
    public float d() {
        return this.a;
    }
    
    public Bitmap e() {
        return this.b;
    }
    
    public Bitmap f() {
        return this.c;
    }
    
    public void a(final float d) {
        this.d = d;
    }
    
    public void b(final float e) {
        this.e = e;
    }
}
