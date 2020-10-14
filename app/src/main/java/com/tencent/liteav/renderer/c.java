package com.tencent.liteav.renderer;

import android.graphics.*;
import android.opengl.*;
import java.nio.*;
import com.tencent.liteav.basic.log.*;

public class c
{
    private final float[] a;
    private final float[] b;
    private FloatBuffer c;
    private float[] d;
    private float[] e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private boolean m;
    private boolean n;
    private int o;
    private int p;
    private int q;
    
    public c(final boolean m) {
        this.a = new float[] { -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f };
        this.b = new float[] { 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 0.0f, 0.0f };
        this.d = new float[16];
        this.e = new float[16];
        this.g = -12345;
        this.l = false;
        this.m = true;
        this.n = false;
        this.o = -1;
        this.p = 0;
        this.q = 0;
        this.m = m;
        if (this.m) {
            this.c = ByteBuffer.allocateDirect(this.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.c.put(this.a).position(0);
        }
        else {
            this.c = ByteBuffer.allocateDirect(this.b.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            this.c.put(this.b).position(0);
        }
        Matrix.setIdentityM(this.e, 0);
    }
    
    public int a() {
        return this.g;
    }
    
    public void a(final SurfaceTexture surfaceTexture) {
        if (surfaceTexture == null) {
            return;
        }
        this.a("onDrawFrame start");
        surfaceTexture.getTransformMatrix(this.e);
        this.b(36197, this.g);
    }
    
    public void a(final int p2, final int q) {
        this.p = p2;
        this.q = q;
    }
    
    public void a(final int n, final boolean n2, final int o) {
        if (this.n != n2 || this.o != o) {
            this.n = n2;
            this.o = o;
            final float[] array = new float[20];
            for (int i = 0; i < 20; ++i) {
                array[i] = this.b[i];
            }
            if (this.n) {
                array[0] = -array[0];
                array[5] = -array[5];
                array[10] = -array[10];
                array[15] = -array[15];
            }
            for (int n3 = o / 90, j = 0; j < n3; ++j) {
                final float n4 = array[3];
                final float n5 = array[4];
                array[3] = array[8];
                array[4] = array[9];
                array[8] = array[18];
                array[9] = array[19];
                array[18] = array[13];
                array[19] = array[14];
                array[13] = n4;
                array[14] = n5;
            }
            this.c.clear();
            this.c.put(array).position(0);
        }
        this.b(3553, n);
    }
    
    private void b(final int n, final int n2) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        if (this.l) {
            this.l = false;
            return;
        }
        GLES20.glUseProgram(this.f);
        this.a("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(n, n2);
        this.c.position(0);
        GLES20.glVertexAttribPointer(this.j, 3, 5126, false, 20, (Buffer)this.c);
        this.a("glVertexAttribPointer maPosition");
        GLES20.glEnableVertexAttribArray(this.j);
        this.a("glEnableVertexAttribArray maPositionHandle");
        this.c.position(3);
        GLES20.glVertexAttribPointer(this.k, 2, 5126, false, 20, (Buffer)this.c);
        this.a("glVertexAttribPointer maTextureHandle");
        GLES20.glEnableVertexAttribArray(this.k);
        this.a("glEnableVertexAttribArray maTextureHandle");
        Matrix.setIdentityM(this.d, 0);
        GLES20.glUniformMatrix4fv(this.h, 1, false, this.d, 0);
        if (this.p % 8 != 0) {
            Matrix.scaleM(this.e, 0, (this.p - 1) * 1.0f / ((this.p + 7) / 8 * 8), 1.0f, 1.0f);
        }
        if (this.q % 8 != 0) {
            Matrix.scaleM(this.e, 0, 1.0f, (this.q - 1) * 1.0f / ((this.q + 7) / 8 * 8), 1.0f);
        }
        GLES20.glUniformMatrix4fv(this.i, 1, false, this.e, 0);
        GLES20.glDrawArrays(5, 0, 4);
        this.a("glDrawArrays");
        GLES20.glFinish();
    }
    
    public void b() {
        if (this.m) {
            this.f = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n  gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n");
        }
        else {
            this.f = this.a("uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n", "varying highp vec2 vTextureCoord;\n \nuniform sampler2D sTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(sTexture, vTextureCoord);\n}");
        }
        if (this.f == 0) {
            TXCLog.e("TXCOesTextureRender", "failed creating program");
            return;
        }
        this.j = GLES20.glGetAttribLocation(this.f, "aPosition");
        this.a("glGetAttribLocation aPosition");
        if (this.j == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for aPosition");
            return;
        }
        this.k = GLES20.glGetAttribLocation(this.f, "aTextureCoord");
        this.a("glGetAttribLocation aTextureCoord");
        if (this.k == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for aTextureCoord");
            return;
        }
        this.h = GLES20.glGetUniformLocation(this.f, "uMVPMatrix");
        this.a("glGetUniformLocation uMVPMatrix");
        if (this.h == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for uMVPMatrix");
            return;
        }
        this.i = GLES20.glGetUniformLocation(this.f, "uSTMatrix");
        this.a("glGetUniformLocation uSTMatrix");
        if (this.i == -1) {
            TXCLog.e("TXCOesTextureRender", "Could not get attrib location for uSTMatrix");
            return;
        }
        if (this.m) {
            this.d();
        }
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        this.a("glTexParameter");
    }
    
    public void c() {
        if (this.f != 0) {
            GLES20.glDeleteProgram(this.f);
        }
        GLES20.glDeleteTextures(1, new int[] { this.g }, 0);
        this.g = -1;
    }
    
    private void d() {
        final int[] array = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glBindTexture(36197, this.g = array[0]);
        this.a("glBindTexture mTextureID");
    }
    
    private int a(final int n, final String s) {
        int glCreateShader = GLES20.glCreateShader(n);
        this.a("glCreateShader type=" + n);
        GLES20.glShaderSource(glCreateShader, s);
        GLES20.glCompileShader(glCreateShader);
        final int[] array = { 0 };
        GLES20.glGetShaderiv(glCreateShader, 35713, array, 0);
        if (array[0] == 0) {
            TXCLog.e("TXCOesTextureRender", "Could not compile shader " + n + ":");
            TXCLog.e("TXCOesTextureRender", " " + GLES20.glGetShaderInfoLog(glCreateShader));
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
            TXCLog.e("TXCOesTextureRender", "Could not create program");
        }
        GLES20.glAttachShader(glCreateProgram, a);
        this.a("glAttachShader");
        GLES20.glAttachShader(glCreateProgram, a2);
        this.a("glAttachShader");
        GLES20.glLinkProgram(glCreateProgram);
        final int[] array = { 0 };
        GLES20.glGetProgramiv(glCreateProgram, 35714, array, 0);
        if (array[0] != 1) {
            TXCLog.e("TXCOesTextureRender", "Could not link program: ");
            TXCLog.e("TXCOesTextureRender", GLES20.glGetProgramInfoLog(glCreateProgram));
            GLES20.glDeleteProgram(glCreateProgram);
            glCreateProgram = 0;
        }
        return glCreateProgram;
    }
    
    public void a(final String s) {
        final int glGetError;
        if ((glGetError = GLES20.glGetError()) != 0) {
            TXCLog.e("TXCOesTextureRender", s + ": glError " + glGetError);
        }
    }
}
