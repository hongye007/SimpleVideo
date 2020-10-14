package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;

public class x extends h
{
    private a r;
    
    @Override
    public int a(final int n) {
        if (null == this.r) {
            this.r = new a();
            this.r.a = 0;
            this.r.b = 0;
            this.r.c = this.e;
            this.r.d = this.f;
        }
        GLES20.glViewport(this.r.a, this.r.b, this.r.c, this.r.d);
        return super.a(n);
    }
}
