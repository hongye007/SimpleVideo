package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.graphics.*;
import android.opengl.*;

public class ac extends h
{
    private float r;
    private int s;
    private float t;
    private int u;
    private PointF v;
    private int w;
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.s = GLES20.glGetUniformLocation(this.q(), "angle");
        this.u = GLES20.glGetUniformLocation(this.q(), "radius");
        this.w = GLES20.glGetUniformLocation(this.q(), "center");
        return a;
    }
    
    @Override
    public void d() {
        super.d();
        this.a(this.t);
        this.b(this.r);
        this.a(this.v);
    }
    
    public void a(final float t) {
        this.t = t;
        this.a(this.u, t);
    }
    
    public void b(final float r) {
        this.r = r;
        this.a(this.s, r);
    }
    
    public void a(final PointF v) {
        this.v = v;
        this.a(this.w, v);
    }
}
