package com.tencent.liteav.renderer;

import com.tencent.liteav.basic.log.*;
import android.opengl.*;
import java.nio.*;

public class i
{
    public static int a;
    public static int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private boolean i;
    private float[] j;
    private float[] k;
    private float l;
    private float m;
    private boolean n;
    private boolean o;
    private final float[] p;
    private FloatBuffer q;
    private float[] r;
    private float[] s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;
    
    public void a(final int c, final int d) {
        if (c == this.c && d == this.d) {
            return;
        }
        TXCLog.i("TXTweenFilter", "Output resolution change: " + this.c + "*" + this.d + " -> " + c + "*" + d);
        this.c = c;
        this.d = d;
        final float n = 1.0f;
        if (c > d) {
            Matrix.orthoM(this.j, 0, -n, n, -1.0f, 1.0f, -1.0f, 1.0f);
            this.l = n;
            this.m = 1.0f;
        }
        else {
            Matrix.orthoM(this.j, 0, -1.0f, 1.0f, -n, n, -1.0f, 1.0f);
            this.l = 1.0f;
            this.m = n;
        }
        this.n = true;
    }
    
    public void b(final int e, final int f) {
        if (e == this.e && f == this.f) {
            return;
        }
        TXCLog.i("TXTweenFilter", "Input resolution change: " + this.e + "*" + this.f + " -> " + e + "*" + f);
        this.e = e;
        this.f = f;
    }
    
    public boolean a() {
        return this.o;
    }
    
    public void a(final int g) {
        this.g = g;
    }
    
    public void b(final int h) {
        this.h = h;
    }
    
    private void b(final float[] array) {
        if (this.d == 0 || this.c == 0) {
            return;
        }
        int n = this.e;
        int n2 = this.f;
        if (this.h == 270 || this.h == 90) {
            n = this.f;
            n2 = this.e;
        }
        final float n3 = this.c * 1.0f / n;
        final float n4 = this.d * 1.0f / n2;
        float n5;
        if (this.g == com.tencent.liteav.renderer.i.a) {
            if (n3 * n2 > this.d) {
                n5 = n3;
            }
            else {
                n5 = n4;
            }
        }
        else if (n3 * n2 > this.d) {
            n5 = n4;
        }
        else {
            n5 = n3;
        }
        Matrix.setIdentityM(this.k, 0);
        if (this.i) {
            if (this.h % 180 == 0) {
                Matrix.scaleM(this.k, 0, -1.0f, 1.0f, 1.0f);
            }
            else {
                Matrix.scaleM(this.k, 0, 1.0f, -1.0f, 1.0f);
            }
        }
        Matrix.scaleM(this.k, 0, n * n5 / this.c * 1.0f, n2 * n5 / this.d * 1.0f, 1.0f);
        Matrix.rotateM(this.k, 0, (float)this.h, 0.0f, 0.0f, -1.0f);
        Matrix.multiplyMM(array, 0, this.j, 0, this.k, 0);
    }
    
    public i(final Boolean b) {
        this.c = 0;
        this.d = 0;
        this.e = 0;
        this.f = 0;
        this.g = com.tencent.liteav.renderer.i.b;
        this.h = 0;
        this.i = false;
        this.j = new float[16];
        this.k = new float[16];
        this.l = 1.0f;
        this.m = 1.0f;
        this.n = false;
        this.o = true;
        this.p = new float[] { -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
        this.r = new float[16];
        this.s = new float[16];
        this.u = -12345;
        this.v = -12345;
        this.o = b;
        this.q = ByteBuffer.allocateDirect(this.p.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.q.put(this.p).position(0);
        Matrix.setIdentityM(this.s, 0);
    }
    
    public void a(final boolean i) {
        this.i = i;
    }
    
    public void a(final float[] s) {
        this.s = s;
    }
    
    public void c(final int n) {
        GLES20.glViewport(0, 0, this.c, this.d);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.t);
        this.a("glUseProgram");
        if (this.o) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, n);
        }
        else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, n);
        }
        this.q.position(0);
        GLES20.glVertexAttribPointer(this.y, 3, 5126, false, 20, (Buffer)this.q);
        this.a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.y);
        this.a("glEnableVertexAttribArray maPositionHandle");
        this.q.position(3);
        GLES20.glVertexAttribPointer(this.z, 2, 5126, false, 20, (Buffer)this.q);
        this.a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.z);
        this.a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.r, 0);
        this.b(this.r);
        GLES20.glUniformMatrix4fv(this.w, 1, false, this.r, 0);
        GLES20.glUniformMatrix4fv(this.x, 1, false, this.s, 0);
        this.a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        this.a("glDrawArrays");
        if (this.o) {
            GLES20.glBindTexture(36197, 0);
        }
        else {
            GLES20.glBindTexture(3553, 0);
        }
    }
    
    public int d(final int n) {
        this.d();
        if (this.v == -12345) {
            TXCLog.d("TXTweenFilter", "invalid frame buffer id");
            return n;
        }
        GLES20.glBindFramebuffer(36160, this.v);
        this.c(n);
        GLES20.glBindFramebuffer(36160, 0);
        return this.u;
    }
    
    public void b() {
        if (this.o) {
            this.t = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        }
        else {
            this.t = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        if (this.t == 0) {
            TXCLog.e("TXTweenFilter", "failed creating program");
            return;
        }
        this.y = GLES20.glGetAttribLocation(this.t, "aPosition");
        this.a("glGetAttribLocation aPosition");
        if (this.y == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for aPosition");
            return;
        }
        this.z = GLES20.glGetAttribLocation(this.t, "aTextureCoord");
        this.a("glGetAttribLocation aTextureCoord");
        if (this.z == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for aTextureCoord");
            return;
        }
        this.w = GLES20.glGetUniformLocation(this.t, "uMVPMatrix");
        this.a("glGetUniformLocation uMVPMatrix");
        if (this.w == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for uMVPMatrix");
            return;
        }
        this.x = GLES20.glGetUniformLocation(this.t, "uSTMatrix");
        this.a("glGetUniformLocation uSTMatrix");
        if (this.x == -1) {
            TXCLog.e("TXTweenFilter", "Could not get attrib location for uSTMatrix");
        }
    }
    
    private void d() {
        if (!this.n) {
            return;
        }
        TXCLog.i("TXTweenFilter", "reloadFrameBuffer. size = " + this.c + "*" + this.d);
        this.e();
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glGenFramebuffers(1, array2, 0);
        this.u = array[0];
        this.v = array2[0];
        TXCLog.d("TXTweenFilter", "frameBuffer id = " + this.v + ", texture id = " + this.u);
        GLES20.glBindTexture(3553, this.u);
        this.a("glBindTexture mFrameBufferTextureID");
        GLES20.glTexImage2D(3553, 0, 6408, this.c, this.d, 0, 6408, 5121, (Buffer)null);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        this.a("glTexParameter");
        GLES20.glBindFramebuffer(36160, this.v);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.u, 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.n = false;
    }
    
    public void c() {
        GLES20.glDeleteProgram(this.t);
        this.e();
    }
    
    private void e() {
        if (this.v != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[] { this.v }, 0);
            this.v = -12345;
        }
        if (this.u != -12345) {
            GLES20.glDeleteTextures(1, new int[] { this.u }, 0);
            this.u = -12345;
        }
    }
    
    private int a(final int n, final String s) {
        int glCreateShader = GLES20.glCreateShader(n);
        this.a("glCreateShader type=" + n);
        GLES20.glShaderSource(glCreateShader, s);
        GLES20.glCompileShader(glCreateShader);
        final int[] array = { 0 };
        GLES20.glGetShaderiv(glCreateShader, 35713, array, 0);
        if (array[0] == 0) {
            TXCLog.e("TXTweenFilter", "Could not compile shader " + n + ":");
            TXCLog.e("TXTweenFilter", " " + GLES20.glGetShaderInfoLog(glCreateShader));
            GLES20.glDeleteShader(glCreateShader);
            glCreateShader = 0;
        }
        return glCreateShader;
    }
    
    private int a(final String s, final String s2) {
        final int a = this.a(35633, s);
        if (a == 0) {
            return 0;
        }
        final int a2 = this.a(35632, s2);
        if (a2 == 0) {
            return 0;
        }
        int glCreateProgram = GLES20.glCreateProgram();
        this.a("glCreateProgram");
        if (glCreateProgram == 0) {
            TXCLog.e("TXTweenFilter", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, a);
        this.a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, a2);
        this.a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        final int[] array = { 0 };
        GLES20.glGetProgramiv(glCreateProgram, 35714, array, 0);
        if (array[0] != 1) {
            TXCLog.e("TXTweenFilter", "Could not link program: ");
            TXCLog.e("TXTweenFilter", GLES20.glGetProgramInfoLog(glCreateProgram));
            GLES20.glDeleteProgram(glCreateProgram);
            glCreateProgram = 0;
        }
        return glCreateProgram;
    }
    
    private void a(final String s) {
        int glGetError;
        while ((glGetError = GLES20.glGetError()) != 0) {
            TXCLog.e("TXTweenFilter", s + ": glError " + glGetError);
        }
    }
    
    static {
        i.a = 1;
        i.b = 2;
    }
}
