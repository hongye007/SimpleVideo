package com.tencent.liteav.beauty.b;

import android.opengl.*;
import com.tencent.liteav.basic.c.*;

public class ag extends af
{
    protected float u;
    
    public ag(final String s, final String s2, final String s3, final String s4) {
        super(s, s2, s3, s4);
        this.u = 4.0f;
    }
    
    @Override
    public boolean a() {
        return super.a() && GLES20.glGetError() == 0;
    }
    
    protected void v() {
        final float u = this.u();
        final h h = this.r.get(0);
        final int glGetUniformLocation = GLES20.glGetUniformLocation(h.q(), "texelWidthOffset");
        final int glGetUniformLocation2 = GLES20.glGetUniformLocation(h.q(), "texelHeightOffset");
        h.a(glGetUniformLocation, u / this.e);
        h.a(glGetUniformLocation2, 0.0f);
        final float t = this.t();
        final h h2 = this.r.get(1);
        final int glGetUniformLocation3 = GLES20.glGetUniformLocation(h2.q(), "texelWidthOffset");
        final int glGetUniformLocation4 = GLES20.glGetUniformLocation(h2.q(), "texelHeightOffset");
        h2.a(glGetUniformLocation3, 0.0f);
        h2.a(glGetUniformLocation4, t / this.f);
    }
    
    @Override
    public void a(final int n, final int n2) {
        super.a(n, n2);
        this.v();
    }
    
    public float t() {
        return this.u;
    }
    
    public float u() {
        return this.u;
    }
}
