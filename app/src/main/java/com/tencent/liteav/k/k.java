package com.tencent.liteav.k;

import com.tencent.liteav.basic.c.*;
import java.nio.*;
import android.opengl.*;

public class k extends h
{
    private int r;
    private int[] s;
    private a[] t;
    
    public k() {
        this.r = 0;
        this.s = new int[] { 1, 4, 9 };
        this.t = null;
    }
    
    public void a(final n.m m) {
        if (m.a == this.r || (m.a != this.s[0] && m.a != this.s[1] && m.a != this.s[2])) {
            return;
        }
        this.r = m.a;
        this.t = new a[this.r];
        for (int i = 0; i < this.r; ++i) {
            this.t[i] = new a();
        }
        if (m.a == this.s[0]) {
            this.t[0].a = 0;
            this.t[0].b = 0;
            this.t[0].c = this.e;
            this.t[0].d = this.f;
        }
        else if (m.a == this.s[1]) {
            for (int j = 0; j < this.s[1]; ++j) {
                this.t[j].a = j % 2 * this.e / 2;
                this.t[j].b = j / 2 * this.f / 2;
                this.t[j].c = this.e / 2;
                this.t[j].d = this.f / 2;
            }
        }
        else if (m.a == this.s[2]) {
            for (int k = 0; k < this.s[2]; ++k) {
                this.t[k].a = k % 3 * this.e / 3;
                this.t[k].b = k / 3 * this.f / 3;
                this.t[k].c = this.e / 3;
                this.t[k].d = this.f / 3;
            }
        }
    }
    
    @Override
    public void a(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        if (null != this.t) {
            for (int i = 0; i < this.t.length; ++i) {
                if (null != this.t[i]) {
                    GLES20.glViewport(this.t[i].a, this.t[i].b, this.t[i].c, this.t[i].d);
                }
                super.a(n, floatBuffer, floatBuffer2);
            }
        }
        else {
            super.a(n, floatBuffer, floatBuffer2);
        }
    }
    
    private static class a
    {
        int a;
        public int b;
        public int c;
        public int d;
        
        private a() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.d = 0;
        }
    }
}
