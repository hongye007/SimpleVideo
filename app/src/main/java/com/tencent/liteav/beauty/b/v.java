package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;

public class v extends h
{
    private int r;
    
    public v() {
        this.r = -1;
    }
    
    @Override
    public boolean a() {
        final boolean a = super.a();
        this.c(this.r = GLES20.glGetUniformLocation(this.q(), "purlColor"), new float[] { 0.0f, 0.0f, 0.0f, 1.0f });
        return a;
    }
    
    public void b(final float[] array) {
        this.c(this.r, array);
    }
}
