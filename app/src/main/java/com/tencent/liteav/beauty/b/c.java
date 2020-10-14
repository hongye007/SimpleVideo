package com.tencent.liteav.beauty.b;

import android.util.*;
import com.tencent.liteav.beauty.*;
import android.opengl.*;

public class c extends b
{
    private static final String r;
    private i s;
    private a t;
    private ab u;
    private int v;
    private int w;
    private float x;
    private float y;
    private float z;
    private float A;
    
    public c() {
        this.u = null;
        this.v = -1;
        this.w = -1;
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.A = 0.0f;
    }
    
    @Override
    public int a(final int n) {
        int n2 = n;
        int a = n;
        if (this.x > 0.0f || this.y > 0.0f || this.z > 0.0f) {
            if (this.x != 0.0f) {
                a = this.s.a(n);
            }
            n2 = this.t.a(a, n, n);
        }
        if (this.A > 0.0f) {
            n2 = this.u.a(n2);
        }
        return n2;
    }
    
    @Override
    public void a(final int v, final int w) {
        if (this.v == v && this.w == w) {
            return;
        }
        Log.i(c.r, "onOutputSizeChanged mFrameWidth = " + v + "  mFrameHeight = " + w);
        this.v = v;
        this.w = w;
        this.c(this.v, this.w);
    }
    
    @Override
    public boolean c(final int v, final int w) {
        this.v = v;
        this.w = w;
        Log.i(c.r, "init mFrameWidth = " + v + "  mFrameHeight = " + w);
        if (null == this.s) {
            (this.s = new i()).a(true);
            if (!this.s.c()) {
                Log.e(c.r, "mNewFaceFilter init Failed");
                return false;
            }
        }
        this.s.a(this.v, this.w);
        if (null == this.t) {
            (this.t = new a()).a(true);
            if (!this.t.c()) {
                Log.e(c.r, "mBeautyCoreFilter init Failed");
                return false;
            }
        }
        this.t.a(this.v, this.w);
        if (null == this.u) {
            (this.u = new ab()).a(true);
            if (!this.u.c()) {
                Log.e(c.r, "mSharpenessFilter init Failed");
                return false;
            }
        }
        this.u.a(this.v, this.w);
        return true;
    }
    
    @Override
    public void b() {
        if (this.t != null) {
            this.t.e();
            this.t = null;
        }
        if (this.s != null) {
            this.s.e();
            this.s = null;
        }
        if (null != this.u) {
            this.u.e();
            this.u = null;
        }
    }
    
    @Override
    public void c(final int n) {
        this.x = (float)n;
        if (this.t != null) {
            this.t.a((float)n);
        }
    }
    
    @Override
    public void d(final int n) {
        this.y = (float)n;
        if (this.t != null) {
            this.t.b((float)n);
        }
    }
    
    @Override
    public void e(final int n) {
        this.z = (float)n;
        if (this.t != null) {
            this.t.c((float)n);
        }
    }
    
    @Override
    public void f(final int n) {
        final float a = n / 15.0f;
        if (Math.abs(this.A - a) < 0.001) {
            return;
        }
        this.A = a;
        if (null != this.u) {
            this.u.a(this.A);
        }
    }
    
    private static float b(final float n) {
        float n2 = n;
        if (n2 > 1.0f) {
            if (n2 < 2.5) {
                n2 = a((n2 - 1.0f) / 1.5f, 1.0f, 4.1f);
            }
            else if (n2 < 4.0f) {
                n2 = a((n2 - 2.5f) / 1.5f, 4.1f, 5.6f);
            }
            else if (n2 < 5.5) {
                n2 = a((n2 - 4.0f) / 1.5f, 5.6f, 6.8f);
            }
            else if (n2 <= 7.0) {
                n2 = a((n2 - 5.5f) / 1.5f, 6.8f, 7.0f);
            }
            return n2 / 10.0f;
        }
        return 0.1f;
    }
    
    private static float a(final float n, final float n2, final float n3) {
        return n2 + (n3 - n2) * n;
    }
    
    static {
        r = c.class.getSimpleName();
    }
    
    public static class a extends ad
    {
        private int x;
        private int y;
        private int z;
        
        public a() {
            super("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\nattribute vec4 inputTextureCoordinate2;\nattribute vec4 inputTextureCoordinate3;\n \nvarying vec2 textureCoordinate;\nvarying vec2 textureCoordinate2;\nvarying vec2 textureCoordinate3;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n    textureCoordinate2 = inputTextureCoordinate2.xy;\n    textureCoordinate3 = inputTextureCoordinate3.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}");
            this.x = -1;
            this.y = -1;
            this.z = -1;
        }
        
        @Override
        public boolean c() {
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(1);
            if (this.a != 0 && this.a()) {
                this.g = true;
            }
            else {
                this.g = false;
            }
            this.d();
            return this.g;
        }
        
        @Override
        public void a(final int n, final int n2) {
            if (this.f == n2 && this.e == n) {
                return;
            }
            super.a(n, n2);
            this.x = GLES20.glGetUniformLocation(this.q(), "smoothDegree");
            this.y = GLES20.glGetUniformLocation(this.q(), "brightDegree");
            this.z = GLES20.glGetUniformLocation(this.q(), "ruddyDegree");
        }
        
        @Override
        public boolean a() {
            final boolean a = super.a();
            if (a) {}
            return a;
        }
        
        public void a(final float n) {
            this.a(this.x, b(n));
        }
        
        public void b(final float n) {
            this.a(this.y, n / 3.0f);
        }
        
        public void c(final float n) {
            this.a(this.z, n / 10.0f / 2.0f);
        }
    }
}
