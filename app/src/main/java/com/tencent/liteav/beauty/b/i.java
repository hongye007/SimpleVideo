package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import java.nio.*;
import com.tencent.liteav.beauty.*;

public class i extends h
{
    private d u;
    private h v;
    private c w;
    private a x;
    private b y;
    private int[] z;
    private int[] A;
    private float B;
    int r;
    int s;
    boolean t;
    
    public i() {
        this.B = 4.0f;
    }
    
    @Override
    public boolean a() {
        boolean b = super.a();
        if (b) {
            this.u = new d();
            if (b) {
                b = this.u.c();
            }
            this.w = new c();
            if (b) {
                b = this.w.c();
            }
            this.x = new a("precision highp float;  \nuniform sampler2D inputImageTexture;  \nuniform sampler2D inputImageTexture2;  \nvarying vec2 textureCoordinate;  \nvarying vec2 textureCoordinate2;  \nvoid main()  \n{  \n\tgl_FragColor = texture2D(inputImageTexture2, textureCoordinate2) - texture2D(inputImageTexture, textureCoordinate) * texture2D(inputImageTexture2, textureCoordinate2);  \n}  \n");
            if (b) {
                b = this.x.c();
            }
            this.y = new b("precision highp float;   \nuniform sampler2D inputImageTexture;   \nuniform sampler2D inputImageTexture2;  \nuniform sampler2D inputImageTexture3;   \nvarying vec2 textureCoordinate;   \nvarying vec2 textureCoordinate2;  \nvarying vec2 textureCoordinate3;    \nvoid main()   \n{   \n\tgl_FragColor = texture2D(inputImageTexture, textureCoordinate) * texture2D(inputImageTexture3, textureCoordinate3) + texture2D(inputImageTexture2, textureCoordinate2);   \n}   \n");
            if (b) {
                b = this.y.c();
            }
            (this.v = new h()).a(true);
            if (b) {
                b = this.v.c();
            }
            if (b) {
                return true;
            }
        }
        this.e();
        return false;
    }
    
    @Override
    public void a(final int n, final int n2) {
        if (this.f == n2 && this.e == n) {
            return;
        }
        super.a(n, n2);
        if (!this.t) {
            if (n < n2) {
                if (n < 540) {
                    this.B = 1.0f;
                }
                else {
                    this.B = 4.0f;
                }
            }
            else if (n2 < 540) {
                this.B = 1.0f;
            }
            else {
                this.B = 4.0f;
            }
        }
        this.r = (int)(n / this.B);
        this.s = (int)(n2 / this.B);
        this.v.a(this.r, this.s);
        this.w.a(this.r, this.s);
        this.x.a(this.r, this.s);
        this.y.a(n, n2);
        this.u.a(this.r, this.s);
        if (this.z != null) {
            GLES20.glDeleteFramebuffers(this.z.length, this.z, 0);
            GLES20.glDeleteTextures(this.z.length, this.A, 0);
            this.z = null;
            this.A = null;
        }
        this.z = new int[8];
        this.A = new int[this.z.length];
        GLES20.glGenFramebuffers(this.z.length, this.z, 0);
        GLES20.glGenTextures(this.z.length, this.A, 0);
        for (int i = 0; i < this.z.length; ++i) {
            GLES20.glBindTexture(3553, this.A[i]);
            if (i >= 5) {
                GLES20.glTexImage2D(3553, 0, 6408, n, n2, 0, 6408, 5121, (Buffer)null);
            }
            else {
                GLES20.glTexImage2D(3553, 0, 6408, this.r, this.s, 0, 6408, 5121, (Buffer)null);
            }
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLES20.glBindFramebuffer(36160, this.z[i]);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.A[i], 0);
            GLES20.glBindTexture(3553, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }
    
    @Override
    public int a(final int n) {
        int a = n;
        if (this.B != 1.0f) {
            GLES20.glViewport(0, 0, this.r, this.s);
            a = this.v.a(n);
        }
        final int a2 = this.u.a(a, this.z[4], this.A[4]);
        final int a3 = this.w.a(a, a2, this.z[0], this.A[0]);
        final int a4 = this.x.a(a3, a2, this.z[1], this.A[1]);
        int n2 = this.u.a(a3, this.z[2], this.A[2]);
        int n3 = this.u.a(a4, this.z[3], this.A[3]);
        if (this.B != 1.0f) {
            GLES20.glViewport(0, 0, this.e, this.f);
            n2 = this.v.a(n2, this.z[5], this.A[5]);
            n3 = this.v.a(n3, this.z[6], this.A[6]);
        }
        return this.y.a(n2, n3, n, this.z[7], this.A[7]);
    }
    
    @Override
    public void b() {
        if (this.g) {
            super.b();
            this.u.e();
            this.w.e();
            this.x.e();
            this.y.e();
            this.v.e();
            if (null != this.z) {
                GLES20.glDeleteFramebuffers(this.z.length, this.z, 0);
                GLES20.glDeleteTextures(this.z.length, this.A, 0);
                this.z = null;
            }
            this.A = null;
        }
    }
    
    private static class c extends ae
    {
        int r;
        int s;
        float t;
        
        public c() {
            super(null, null);
            this.t = 1.5f;
        }
        
        @Override
        public boolean c() {
            NativeLoad.getInstance();
            this.a = NativeLoad.nativeLoadGLProgram(2);
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
        public boolean a() {
            if (super.a()) {
                this.r = GLES20.glGetUniformLocation(this.a, "texelWidthOffset");
                this.s = GLES20.glGetUniformLocation(this.a, "texelHeightOffset");
                return true;
            }
            return false;
        }
        
        void a(final float t) {
            this.t = t;
            this.a(this.r, this.t / this.e);
            this.a(this.s, this.t / this.f);
        }
        
        @Override
        public void a(final int n, final int n2) {
            super.a(n, n2);
            this.a(this.t);
        }
    }
    
    private static class a extends ae
    {
        public a(final String s) {
            super(s);
        }
        
        @Override
        public boolean a() {
            return super.a();
        }
    }
    
    private static class b extends ad
    {
        public b(final String s) {
            super(s);
        }
        
        @Override
        public boolean a() {
            return super.a();
        }
    }
}
