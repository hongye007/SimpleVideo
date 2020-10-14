package com.tencent.liteav.f;

import com.tencent.liteav.basic.log.*;
import android.opengl.*;
import java.nio.*;

public class i
{
    private int a;
    private int b;
    private int c;
    private int d;
    private float[] e;
    private float[] f;
    private boolean g;
    private boolean h;
    private boolean i;
    private final float[] j;
    private FloatBuffer k;
    private float[] l;
    private float[] m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    
    public void a(final int a, final int b) {
        if (a == this.a && b == this.b) {
            return;
        }
        TXCLog.i("TXScaleFilter", "Output resolution change: " + this.a + "*" + this.b + " -> " + a + "*" + b);
        this.a = a;
        this.b = b;
        final float n = 1.0f;
        if (a > b) {
            Matrix.orthoM(this.e, 0, -n, n, -1.0f, 1.0f, -1.0f, 1.0f);
        }
        else {
            Matrix.orthoM(this.e, 0, -1.0f, 1.0f, -n, n, -1.0f, 1.0f);
        }
        this.g = true;
    }
    
    public void b(final int c, final int d) {
        if (c == this.c && d == this.d) {
            return;
        }
        TXCLog.i("TXScaleFilter", "Input resolution change: " + this.c + "*" + this.d + " -> " + c + "*" + d);
        this.c = c;
        this.d = d;
    }
    
    private void a(final float[] array) {
        if (this.b == 0 || this.a == 0) {
            return;
        }
        final int c = this.c;
        final int d = this.d;
        final float n = this.a * 1.0f / c;
        final float n2 = this.b * 1.0f / d;
        float n3;
        if (n * d > this.b) {
            n3 = n;
        }
        else {
            n3 = n2;
        }
        Matrix.setIdentityM(this.f, 0);
        if (this.i) {
            Matrix.scaleM(this.f, 0, c * n3 / this.a * 1.0f, d * n3 / this.b * 1.0f, 1.0f);
        }
        else {
            Matrix.scaleM(this.f, 0, c * n3 / this.a * 1.0f, d * n3 / this.b * 1.0f, 1.0f);
        }
        Matrix.multiplyMM(array, 0, this.e, 0, this.f, 0);
    }
    
    public i(final Boolean b) {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = new float[16];
        this.f = new float[16];
        this.g = false;
        this.h = true;
        this.i = true;
        this.j = new float[] { -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
        this.l = new float[16];
        this.m = new float[16];
        this.o = -12345;
        this.p = -12345;
        this.h = b;
        this.k = ByteBuffer.allocateDirect(this.j.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.k.put(this.j).position(0);
        Matrix.setIdentityM(this.m, 0);
    }
    
    public void a(final int n) {
        GLES20.glViewport(0, 0, this.a, this.b);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.n);
        this.a("glUseProgram");
        if (this.h) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, n);
        }
        else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, n);
        }
        this.k.position(0);
        GLES20.glVertexAttribPointer(this.s, 3, 5126, false, 20, (Buffer)this.k);
        this.a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.s);
        this.a("glEnableVertexAttribArray maPositionHandle");
        this.k.position(3);
        GLES20.glVertexAttribPointer(this.t, 2, 5126, false, 20, (Buffer)this.k);
        this.a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.t);
        this.a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.l, 0);
        this.a(this.l);
        GLES20.glUniformMatrix4fv(this.q, 1, false, this.l, 0);
        GLES20.glUniformMatrix4fv(this.r, 1, false, this.m, 0);
        this.a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        this.a("glDrawArrays");
        if (this.h) {
            GLES20.glBindTexture(36197, 0);
        }
        else {
            GLES20.glBindTexture(3553, 0);
        }
    }
    
    public int b(final int n) {
        this.c();
        if (this.p == -12345) {
            TXCLog.e("TXScaleFilter", "invalid frame buffer id");
            return n;
        }
        GLES20.glBindFramebuffer(36160, this.p);
        this.a(n);
        GLES20.glBindFramebuffer(36160, 0);
        return this.o;
    }
    
    public void a() {
        if (this.h) {
            this.n = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        }
        else {
            this.n = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        if (this.n == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.s = GLES20.glGetAttribLocation(this.n, "aPosition");
        this.a("glGetAttribLocation aPosition");
        if (this.s == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.t = GLES20.glGetAttribLocation(this.n, "aTextureCoord");
        this.a("glGetAttribLocation aTextureCoord");
        if (this.t == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.q = GLES20.glGetUniformLocation(this.n, "uMVPMatrix");
        this.a("glGetUniformLocation uMVPMatrix");
        if (this.q == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.r = GLES20.glGetUniformLocation(this.n, "uSTMatrix");
        this.a("glGetUniformLocation uSTMatrix");
        if (this.r == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
    }
    
    private void c() {
        if (!this.g) {
            return;
        }
        TXCLog.i("TXScaleFilter", "reloadFrameBuffer. size = " + this.a + "*" + this.b);
        this.d();
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glGenFramebuffers(1, array2, 0);
        this.o = array[0];
        this.p = array2[0];
        TXCLog.i("TXScaleFilter", "frameBuffer id = " + this.p + ", texture id = " + this.o);
        GLES20.glBindTexture(3553, this.o);
        this.a("glBindTexture mFrameBufferTextureID");
        GLES20.glTexImage2D(3553, 0, 6408, this.a, this.b, 0, 6408, 5121, (Buffer)null);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        this.a("glTexParameter");
        GLES20.glBindFramebuffer(36160, this.p);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.o, 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.g = false;
    }
    
    public void b() {
        GLES20.glDeleteProgram(this.n);
        this.d();
    }
    
    private void d() {
        if (this.p != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[] { this.p }, 0);
            this.p = -12345;
        }
        if (this.o != -12345) {
            GLES20.glDeleteTextures(1, new int[] { this.o }, 0);
            this.o = -12345;
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
            TXCLog.e("TXScaleFilter", "Could not compile shader " + n + ":");
            TXCLog.e("TXScaleFilter", " " + GLES20.glGetShaderInfoLog(glCreateShader));
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
            TXCLog.e("TXScaleFilter", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, a);
        this.a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, a2);
        this.a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        final int[] array = { 0 };
        GLES20.glGetProgramiv(glCreateProgram, 35714, array, 0);
        if (array[0] != 1) {
            TXCLog.e("TXScaleFilter", "Could not link program: ");
            TXCLog.e("TXScaleFilter", GLES20.glGetProgramInfoLog(glCreateProgram));
            GLES20.glDeleteProgram(glCreateProgram);
            glCreateProgram = 0;
        }
        return glCreateProgram;
    }
    
    private void a(final String s) {
        final int glGetError;
        if ((glGetError = GLES20.glGetError()) != 0) {
            TXCLog.e("TXScaleFilter", s + ": glError " + glGetError);
            throw new RuntimeException(s + ": glError " + glGetError);
        }
    }
}
