package com.tencent.liteav.basic.c;

import java.util.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;
import java.nio.*;
import android.graphics.*;

public class h
{
    private final LinkedList<Runnable> r;
    private final String s;
    private final String t;
    protected int a;
    protected int b;
    protected int c;
    protected int d;
    protected int e;
    protected int f;
    protected boolean g;
    protected FloatBuffer h;
    protected FloatBuffer i;
    protected float[] j;
    protected float[] k;
    private boolean u;
    protected a l;
    private int v;
    private float[] w;
    protected int m;
    protected int n;
    protected boolean o;
    protected boolean p;
    protected boolean q;
    
    public h() {
        this("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = inputTextureCoordinate.xy;\n}", "varying lowp vec2 textureCoordinate;\n \nuniform sampler2D inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", false);
    }
    
    public h(final String s, final String s2) {
        this(s, s2, false);
    }
    
    public h(final String s, final String t, final boolean q) {
        this.u = false;
        this.v = -1;
        this.w = null;
        this.m = -1;
        this.n = -1;
        this.o = false;
        this.p = false;
        this.q = false;
        this.r = new LinkedList<Runnable>();
        this.s = s;
        this.t = t;
        this.q = q;
        if (q) {
            TXCLog.i("TXCGPUFilter", "set Oes fileter");
        }
        this.h = ByteBuffer.allocateDirect(com.tencent.liteav.basic.c.l.e.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.j = com.tencent.liteav.basic.c.l.e;
        this.h.put(this.j).position(0);
        this.i = ByteBuffer.allocateDirect(com.tencent.liteav.basic.c.l.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.k = com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, true);
        this.i.put(this.k).position(0);
    }
    
    public boolean c() {
        this.a = com.tencent.liteav.basic.c.j.a(this.s, this.t);
        if (this.a != 0 && this.a()) {
            this.g = true;
        }
        else {
            this.g = false;
        }
        this.d();
        return this.g;
    }
    
    public void a(final boolean o) {
        this.o = o;
    }
    
    public void b(final boolean p) {
        this.p = p;
        TXCLog.i("TXCGPUFilter", "set Nearest model " + p);
    }
    
    public void a(final a l) {
        this.u = (l != null);
        this.l = l;
    }
    
    public boolean a() {
        this.b = GLES20.glGetAttribLocation(this.a, "position");
        this.c = GLES20.glGetUniformLocation(this.a, "inputImageTexture");
        this.v = GLES20.glGetUniformLocation(this.a, "textureTransform");
        this.d = GLES20.glGetAttribLocation(this.a, "inputTextureCoordinate");
        return true;
    }
    
    public void d() {
    }
    
    public void e() {
        GLES20.glDeleteProgram(this.a);
        this.b();
        this.g = false;
    }
    
    public void b() {
        this.f();
        this.f = -1;
        this.e = -1;
    }
    
    private static float[] a(final FloatBuffer floatBuffer) {
        if (floatBuffer.limit() <= 0) {
            return null;
        }
        final float[] array = new float[floatBuffer.limit()];
        for (int i = 0; i < floatBuffer.limit(); ++i) {
            array[i] = floatBuffer.get(i);
        }
        return array;
    }
    
    public void f() {
        if (this.m != -1) {
            GLES20.glDeleteFramebuffers(1, new int[] { this.m }, 0);
            this.m = -1;
        }
        if (this.n != -1) {
            GLES20.glDeleteTextures(1, new int[] { this.n }, 0);
            this.n = -1;
        }
    }
    
    public void a(final int e, final int f) {
        if (this.f == f && this.e == e) {
            return;
        }
        this.e = e;
        this.f = f;
        if (this.o) {
            if (this.m != -1) {
                this.f();
            }
            final int[] array = { 0 };
            GLES20.glGenFramebuffers(1, array, 0);
            this.m = array[0];
            this.n = com.tencent.liteav.basic.c.j.a(e, f, 6408, 6408);
            GLES20.glBindFramebuffer(36160, this.m);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.n, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }
    
    public void a(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        GLES20.glUseProgram(this.a);
        this.k();
        if (!this.g) {
            return;
        }
        floatBuffer.position(0);
        GLES20.glVertexAttribPointer(this.b, 2, 5126, false, 0, (Buffer)floatBuffer);
        GLES20.glEnableVertexAttribArray(this.b);
        floatBuffer2.position(0);
        GLES20.glVertexAttribPointer(this.d, 2, 5126, false, 0, (Buffer)floatBuffer2);
        GLES20.glEnableVertexAttribArray(this.d);
        if (this.v >= 0 && null != this.w) {
            GLES20.glUniformMatrix4fv(this.v, 1, false, this.w, 0);
        }
        if (n != -1) {
            GLES20.glActiveTexture(33984);
            if (this.q) {
                GLES20.glBindTexture(36197, n);
            }
            else {
                GLES20.glBindTexture(3553, n);
            }
            GLES20.glUniform1i(this.c, 0);
        }
        this.i();
        GLES20.glDrawArrays(5, 0, 4);
        GLES20.glDisableVertexAttribArray(this.b);
        GLES20.glDisableVertexAttribArray(this.d);
        this.j();
        if (this.q) {
            GLES20.glBindTexture(36197, 0);
        }
        else {
            GLES20.glBindTexture(3553, 0);
        }
    }
    
    public void a(final float[] w) {
        this.w = w;
    }
    
    public void g() {
        if (this.k != null) {
            for (int i = 0; i < 8; i += 2) {
                this.k[i] = 1.0f - this.k[i];
            }
            this.a(this.j, this.k);
        }
    }
    
    public void h() {
        if (this.k != null) {
            for (int i = 1; i < 8; i += 2) {
                this.k[i] = 1.0f - this.k[i];
            }
            this.a(this.j, this.k);
        }
    }
    
    public int b(final int n, final FloatBuffer floatBuffer, final FloatBuffer floatBuffer2) {
        if (!this.g) {
            return -1;
        }
        this.a(n, floatBuffer, floatBuffer2);
        if (this.l instanceof a) {
            this.l.a(n);
        }
        return 1;
    }
    
    public int b(final int n) {
        return this.b(n, this.h, this.i);
    }
    
    protected void i() {
    }
    
    protected void j() {
    }
    
    protected void k() {
        while (!this.r.isEmpty()) {
            this.r.removeFirst().run();
        }
    }
    
    public int a(final int n, final int n2, final int n3) {
        if (!this.g) {
            return -1;
        }
        GLES20.glBindFramebuffer(36160, n2);
        this.a(n, this.h, this.i);
        if (this.l instanceof a) {
            this.l.a(n3);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return n3;
    }
    
    public int a(final int n) {
        return this.a(n, this.m, this.n);
    }
    
    public int l() {
        return this.n;
    }
    
    public int m() {
        return this.m;
    }
    
    public void a(final float[] j, final float[] k) {
        this.j = j;
        this.h = ByteBuffer.allocateDirect(com.tencent.liteav.basic.c.l.e.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.h.put(j).position(0);
        this.k = k;
        this.i = ByteBuffer.allocateDirect(com.tencent.liteav.basic.c.l.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.i.put(k).position(0);
    }
    
    public float[] a(final int n, final int n2, final FloatBuffer floatBuffer, final com.tencent.liteav.basic.c.a a, final int n3) {
        float[] array;
        if (floatBuffer == null) {
            if (4 == n3) {
                array = com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, false);
            }
            else {
                array = com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, true);
            }
        }
        else {
            array = a(floatBuffer);
        }
        if (null != a) {
            final float n4 = a.a / (n * 1.0f);
            final float n5 = (n - a.a - a.c) / (n * 1.0f);
            final float n6 = a.b / (n2 * 1.0f);
            final float n7 = (n2 - a.b - a.d) / (n2 * 1.0f);
            for (int i = 0; i < array.length / 2; ++i) {
                if (array[2 * i] < 0.5f) {
                    final float[] array2 = array;
                    final int n8 = 2 * i;
                    array2[n8] += n4;
                }
                else {
                    final float[] array3 = array;
                    final int n9 = 2 * i;
                    array3[n9] -= n5;
                }
                if (array[2 * i + 1] < 0.5f) {
                    final float[] array4 = array;
                    final int n10 = 2 * i + 1;
                    array4[n10] += n6;
                }
                else {
                    final float[] array5 = array;
                    final int n11 = 2 * i + 1;
                    array5[n11] -= n7;
                }
            }
        }
        return array;
    }
    
    public void a(final int n, final int n2, final int n3, final float[] array, final float n4, final boolean b, final boolean b2) {
        float[] a;
        if (array == null) {
            a = com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, true);
        }
        else {
            a = array;
        }
        int n5 = n;
        int n6 = n2;
        if (n / (float)n2 > n4) {
            n6 = n2;
            n5 = (int)(n6 * n4);
        }
        else if (n / (float)n2 < n4) {
            n5 = n;
            n6 = (int)(n5 / n4);
        }
        final float n7 = n5 / (float)n;
        final float n8 = n6 / (float)n2;
        final float n9 = (1.0f - n7) / 2.0f;
        final float n10 = (1.0f - n8) / 2.0f;
        for (int i = 0; i < a.length / 2; ++i) {
            if (a[2 * i] < 0.5f) {
                final float[] array2 = a;
                final int n11 = 2 * i;
                array2[n11] += n9;
            }
            else {
                final float[] array3 = a;
                final int n12 = 2 * i;
                array3[n12] -= n9;
            }
            if (a[2 * i + 1] < 0.5f) {
                final float[] array4 = a;
                final int n13 = 2 * i + 1;
                array4[n13] += n10;
            }
            else {
                final float[] array5 = a;
                final int n14 = 2 * i + 1;
                array5[n14] -= n10;
            }
        }
        final int n15 = n3 / 90;
        for (int j = 0; j < n15; ++j) {
            final float n16 = a[0];
            final float n17 = a[1];
            a[0] = a[2];
            a[1] = a[3];
            a[2] = a[6];
            a[3] = a[7];
            a[6] = a[4];
            a[7] = a[5];
            a[4] = n16;
            a[5] = n17;
        }
        if (n15 == 0 || n15 == 2) {
            if (b) {
                a[0] = 1.0f - a[0];
                a[2] = 1.0f - a[2];
                a[4] = 1.0f - a[4];
                a[6] = 1.0f - a[6];
            }
            if (b2) {
                a[1] = 1.0f - a[1];
                a[3] = 1.0f - a[3];
                a[5] = 1.0f - a[5];
                a[7] = 1.0f - a[7];
            }
        }
        else {
            if (b2) {
                a[0] = 1.0f - a[0];
                a[2] = 1.0f - a[2];
                a[4] = 1.0f - a[4];
                a[6] = 1.0f - a[6];
            }
            if (b) {
                a[1] = 1.0f - a[1];
                a[3] = 1.0f - a[3];
                a[5] = 1.0f - a[5];
                a[7] = 1.0f - a[7];
            }
        }
        this.a(com.tencent.liteav.basic.c.l.e.clone(), a);
    }
    
    public void a(final int n, final FloatBuffer floatBuffer) {
        float[] array;
        if (floatBuffer == null) {
            array = com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, true);
        }
        else {
            array = a(floatBuffer);
        }
        for (int n2 = n / 90, i = 0; i < n2; ++i) {
            final float n3 = array[0];
            final float n4 = array[1];
            array[0] = array[2];
            array[1] = array[3];
            array[2] = array[6];
            array[3] = array[7];
            array[6] = array[4];
            array[7] = array[5];
            array[4] = n3;
            array[5] = n4;
        }
        this.a(com.tencent.liteav.basic.c.l.e.clone(), array);
    }
    
    public boolean n() {
        return this.g;
    }
    
    public int o() {
        return this.e;
    }
    
    public int p() {
        return this.f;
    }
    
    public int q() {
        return this.a;
    }
    
    public void b(final int n, final int n2) {
        this.a(new Runnable() {
            @Override
            public void run() {
                GLES20.glUniform1i(n, n2);
            }
        });
    }
    
    public void a(final int n, final float n2) {
        this.a(new Runnable() {
            @Override
            public void run() {
                GLES20.glUniform1f(n, n2);
            }
        });
    }
    
    public void a(final int n, final float[] array) {
        this.a(new Runnable() {
            @Override
            public void run() {
                GLES20.glUniform2fv(n, 1, FloatBuffer.wrap(array));
            }
        });
    }
    
    public void b(final int n, final float[] array) {
        this.a(new Runnable() {
            @Override
            public void run() {
                GLES20.glUniform3fv(n, 1, FloatBuffer.wrap(array));
            }
        });
    }
    
    public void c(final int n, final float[] array) {
        this.a(new Runnable() {
            @Override
            public void run() {
                GLES20.glUniform4fv(n, 1, FloatBuffer.wrap(array));
            }
        });
    }
    
    public void a(final int n, final PointF pointF) {
        this.a(new Runnable() {
            @Override
            public void run() {
                GLES20.glUniform2fv(n, 1, new float[] { pointF.x, pointF.y }, 0);
            }
        });
    }
    
    public void d(final int n, final float[] array) {
        this.a(new Runnable() {
            @Override
            public void run() {
                GLES20.glUniformMatrix4fv(n, 1, false, array, 0);
            }
        });
    }
    
    public void a(final Runnable runnable) {
        synchronized (this.r) {
            this.r.addLast(runnable);
        }
    }
    
    public interface a
    {
        void a(final int p0);
    }
}
