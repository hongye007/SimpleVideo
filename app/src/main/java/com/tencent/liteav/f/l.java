package com.tencent.liteav.f;

import com.tencent.liteav.basic.log.*;
import android.opengl.*;
import java.nio.*;

public class l
{
    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private boolean g;
    private float[] h;
    private float[] i;
    private float j;
    private float k;
    private boolean l;
    private boolean m;
    private final float[] n;
    private final float[] o;
    private FloatBuffer p;
    private float[] q;
    private float[] r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    
    public void a(final int a, final int b) {
        if (a == this.a && b == this.b) {
            return;
        }
        TXCLog.i("VideoScaleFilter", "Output resolution change: " + this.a + "*" + this.b + " -> " + a + "*" + b);
        this.a = a;
        this.b = b;
        final float n = 1.0f;
        if (a > b) {
            Matrix.orthoM(this.h, 0, -n, n, -1.0f, 1.0f, -1.0f, 1.0f);
            this.j = n;
            this.k = 1.0f;
        }
        else {
            Matrix.orthoM(this.h, 0, -1.0f, 1.0f, -n, n, -1.0f, 1.0f);
            this.j = 1.0f;
            this.k = n;
        }
        this.l = true;
    }
    
    public void b(final int c, final int d) {
        if (c == this.c && d == this.d) {
            return;
        }
        TXCLog.i("VideoScaleFilter", "Input resolution change: " + this.c + "*" + this.d + " -> " + c + "*" + d);
        this.c = c;
        this.d = d;
    }
    
    public void a(final int e) {
        this.e = e;
    }
    
    public void b(final int f) {
        this.f = f;
    }
    
    private void b(final float[] array) {
        if (this.b == 0 || this.a == 0) {
            return;
        }
        int n = this.c;
        int n2 = this.d;
        if (this.f == 270 || this.f == 90) {
            n = this.d;
            n2 = this.c;
        }
        final float n3 = this.a * 1.0f / n;
        final float n4 = this.b * 1.0f / n2;
        float n5;
        if (this.e == 1) {
            if (n3 * n2 > this.b) {
                n5 = n3;
            }
            else {
                n5 = n4;
            }
        }
        else if (n3 * n2 > this.b) {
            n5 = n4;
        }
        else {
            n5 = n3;
        }
        Matrix.setIdentityM(this.i, 0);
        if (this.g) {
            if (this.f % 180 == 0) {
                Matrix.scaleM(this.i, 0, -1.0f, 1.0f, 1.0f);
            }
            else {
                Matrix.scaleM(this.i, 0, 1.0f, -1.0f, 1.0f);
            }
        }
        Matrix.scaleM(this.i, 0, n * n5 / this.a * 1.0f, n2 * n5 / this.b * 1.0f, 1.0f);
        Matrix.rotateM(this.i, 0, (float)this.f, 0.0f, 0.0f, -1.0f);
        Matrix.multiplyMM(array, 0, this.h, 0, this.i, 0);
    }
    
    public l(final Boolean b) {
        this.a = 0;
        this.b = 0;
        this.c = 0;
        this.d = 0;
        this.e = 2;
        this.f = 0;
        this.g = false;
        this.h = new float[16];
        this.i = new float[16];
        this.j = 1.0f;
        this.k = 1.0f;
        this.l = false;
        this.m = true;
        this.n = new float[] { -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
        this.o = new float[] { -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f };
        this.q = new float[16];
        this.r = new float[16];
        this.t = -12345;
        this.u = -12345;
        this.m = b;
        this.p = ByteBuffer.allocateDirect(this.n.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.p.put(b ? this.o : this.n).position(0);
        Matrix.setIdentityM(this.r, 0);
    }
    
    public void a(final float[] r) {
        this.r = r;
    }
    
    public void c(final int n) {
        GLES20.glViewport(0, 0, this.a, this.b);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glUseProgram(this.s);
        this.a("glUseProgram");
        if (this.m) {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(36197, n);
        }
        else {
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, n);
        }
        this.p.position(0);
        GLES20.glVertexAttribPointer(this.x, 3, 5126, false, 20, (Buffer)this.p);
        this.a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.x);
        this.a("glEnableVertexAttribArray maPositionHandle");
        this.p.position(3);
        GLES20.glVertexAttribPointer(this.y, 2, 5126, false, 20, (Buffer)this.p);
        this.a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.y);
        this.a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.q, 0);
        this.b(this.q);
        GLES20.glUniformMatrix4fv(this.v, 1, false, this.q, 0);
        GLES20.glUniformMatrix4fv(this.w, 1, false, this.r, 0);
        this.a("glDrawArrays");
        GLES20.glDrawArrays(5, 0, 4);
        this.a("glDrawArrays");
        if (this.m) {
            GLES20.glBindTexture(36197, 0);
        }
        else {
            GLES20.glBindTexture(3553, 0);
        }
    }
    
    public int d(final int n) {
        this.c();
        if (this.u == -12345) {
            TXCLog.e("VideoScaleFilter", "invalid frame buffer id");
            return n;
        }
        GLES20.glBindFramebuffer(36160, this.u);
        this.c(n);
        GLES20.glBindFramebuffer(36160, 0);
        return this.t;
    }
    
    public void a() {
        if (this.m) {
            this.s = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        }
        else {
            this.s = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        if (this.s == 0) {
            throw new RuntimeException("failed creating program");
        }
        this.x = GLES20.glGetAttribLocation(this.s, "aPosition");
        this.a("glGetAttribLocation aPosition");
        if (this.x == -1) {
            throw new RuntimeException("Could not get attrib location for aPosition");
        }
        this.y = GLES20.glGetAttribLocation(this.s, "aTextureCoord");
        this.a("glGetAttribLocation aTextureCoord");
        if (this.y == -1) {
            throw new RuntimeException("Could not get attrib location for aTextureCoord");
        }
        this.v = GLES20.glGetUniformLocation(this.s, "uMVPMatrix");
        this.a("glGetUniformLocation uMVPMatrix");
        if (this.v == -1) {
            throw new RuntimeException("Could not get attrib location for uMVPMatrix");
        }
        this.w = GLES20.glGetUniformLocation(this.s, "uSTMatrix");
        this.a("glGetUniformLocation uSTMatrix");
        if (this.w == -1) {
            throw new RuntimeException("Could not get attrib location for uSTMatrix");
        }
    }
    
    private void c() {
        if (!this.l) {
            return;
        }
        TXCLog.i("VideoScaleFilter", "reloadFrameBuffer. size = " + this.a + "*" + this.b);
        this.d();
        final int[] array = { 0 };
        final int[] array2 = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glGenFramebuffers(1, array2, 0);
        this.t = array[0];
        this.u = array2[0];
        TXCLog.i("VideoScaleFilter", "frameBuffer id = " + this.u + ", texture id = " + this.t);
        GLES20.glBindTexture(3553, this.t);
        this.a("glBindTexture mFrameBufferTextureID");
        GLES20.glTexImage2D(3553, 0, 6408, this.a, this.b, 0, 6408, 5121, (Buffer)null);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        this.a("glTexParameter");
        GLES20.glBindFramebuffer(36160, this.u);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.t, 0);
        GLES20.glBindTexture(3553, 0);
        GLES20.glBindFramebuffer(36160, 0);
        this.l = false;
    }
    
    public void b() {
        GLES20.glDeleteProgram(this.s);
        this.d();
    }
    
    private void d() {
        if (this.u != -12345) {
            GLES20.glDeleteFramebuffers(1, new int[] { this.u }, 0);
            this.u = -12345;
        }
        if (this.t != -12345) {
            GLES20.glDeleteTextures(1, new int[] { this.t }, 0);
            this.t = -12345;
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
            TXCLog.e("VideoScaleFilter", "Could not compile shader " + n + ":");
            TXCLog.e("VideoScaleFilter", " " + GLES20.glGetShaderInfoLog(glCreateShader));
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
            TXCLog.e("VideoScaleFilter", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, a);
        this.a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, a2);
        this.a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        final int[] array = { 0 };
        GLES20.glGetProgramiv(glCreateProgram, 35714, array, 0);
        if (array[0] != 1) {
            TXCLog.e("VideoScaleFilter", "Could not link program: ");
            TXCLog.e("VideoScaleFilter", GLES20.glGetProgramInfoLog(glCreateProgram));
            GLES20.glDeleteProgram(glCreateProgram);
            glCreateProgram = 0;
        }
        return glCreateProgram;
    }
    
    private void a(final String s) {
        int glGetError;
        while ((glGetError = GLES20.glGetError()) != 0) {
            TXCLog.e("VideoScaleFilter", s + ": glError " + glGetError);
        }
    }
}
