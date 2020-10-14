package com.tencent.liteav.basic.c;

import java.nio.*;
import android.graphics.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;

public class j
{
    private static float[] f;
    private static float[] g;
    private static float[] h;
    private static float[] i;
    private static float[] j;
    public static FloatBuffer a;
    public static FloatBuffer b;
    public static FloatBuffer c;
    public static FloatBuffer d;
    public static FloatBuffer e;
    private static int k;
    
    public static void a(final int k) {
        com.tencent.liteav.basic.c.j.k = k;
    }
    
    public static final int a() {
        return com.tencent.liteav.basic.c.j.k;
    }
    
    public static void a(final a[] array) {
        if (null != array) {
            for (final a a : array) {
                if (null != a) {
                    a(a);
                }
            }
        }
    }
    
    public static a[] a(a[] array, final int n, final int n2, final int n3) {
        if (null == array) {
            array = new a[n];
        }
        for (int i = 0; i < array.length; ++i) {
            array[i] = a(array[i], n2, n3);
        }
        return array;
    }
    
    public static a a(a a, final int c, final int d) {
        if (null == a) {
            a = new a();
        }
        if (null == a.a) {
            a.a = new int[1];
        }
        if (null == a.b) {
            a.b = new int[1];
        }
        a.c = c;
        a.d = d;
        a(a.a, a.b, a.c, a.d);
        return a;
    }
    
    public static a a(a a) {
        if (null != a) {
            if (null != a.a) {
                GLES20.glDeleteFramebuffers(1, a.a, 0);
                a.a = null;
            }
            if (null != a.b) {
                GLES20.glDeleteTextures(1, a.b, 0);
                a.b = null;
            }
            a = null;
        }
        return a;
    }
    
    public static int a(final int n, final int n2, final int n3, final int n4, final int[] array) {
        GLES20.glGenTextures(1, array, 0);
        GLES20.glBindTexture(3553, array[0]);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, 10241, 9728);
        GLES20.glTexParameteri(3553, 10240, 9729);
        GLES20.glTexImage2D(3553, 0, n3, n, n2, 0, n4, 5121, (Buffer)null);
        return array[0];
    }
    
    public static FloatBuffer a(final float[] array) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(array.length * 4);
        allocateDirect.order(ByteOrder.nativeOrder());
        final FloatBuffer floatBuffer = allocateDirect.asFloatBuffer();
        floatBuffer.put(array);
        floatBuffer.position(0);
        return floatBuffer;
    }
    
    public static int a(final int n, final int n2, final int n3, final int n4, final IntBuffer intBuffer) {
        final int c = c();
        GLES20.glBindTexture(3553, c);
        GLES20.glTexImage2D(3553, 0, n3, n, n2, 0, n4, 5121, (Buffer)intBuffer);
        GLES20.glBindTexture(3553, 0);
        return c;
    }
    
    public static int a(final int n, final int n2, final int n3, final int n4) {
        return a(n, n2, n3, n4, (IntBuffer)null);
    }
    
    public static int a(final Bitmap bitmap, final int n, final boolean b) {
        final int[] array = { 0 };
        if (n == -1) {
            GLES20.glGenTextures(1, array, 0);
            GLES20.glBindTexture(3553, array[0]);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10242, 33071.0f);
            GLES20.glTexParameterf(3553, 10243, 33071.0f);
            GLUtils.texImage2D(3553, 0, bitmap, 0);
        }
        else {
            GLES20.glBindTexture(3553, n);
            GLUtils.texSubImage2D(3553, 0, 0, 0, bitmap);
            array[0] = n;
        }
        if (b) {
            bitmap.recycle();
        }
        return array[0];
    }
    
    public static int a(final int n, final int n2, final int[] array) {
        final int n3 = n * n2 * 4;
        GLES30.glGenBuffers(1, array, 0);
        GLES30.glBindBuffer(35051, array[0]);
        GLES30.glBufferData(35051, n3, (Buffer)null, 35049);
        GLES30.glBindBuffer(35051, 0);
        return array[0];
    }
    
    public static int b() {
        final int[] array = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glBindTexture(36197, array[0]);
        GLES20.glTexParameterf(36197, 10241, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, 10242, 33071);
        GLES20.glTexParameteri(36197, 10243, 33071);
        return array[0];
    }
    
    public static void a(final int[] array, final int[] array2, final int n, final int n2) {
        GLES20.glGenFramebuffers(1, array, 0);
        array2[0] = a(n, n2, 6408, 6408, array2);
        GLES20.glBindFramebuffer(36160, array[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, array2[0], 0);
        GLES20.glBindFramebuffer(36160, 0);
    }
    
    public static int c() {
        final int[] array = { 0 };
        GLES20.glGenTextures(1, array, 0);
        GLES20.glBindTexture(3553, array[0]);
        GLES20.glTexParameterf(3553, 10241, 9729.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glBindTexture(3553, 0);
        return array[0];
    }
    
    public static int a(final String s, final int n) {
        final int[] array = { 0 };
        final int glCreateShader = GLES20.glCreateShader(n);
        GLES20.glShaderSource(glCreateShader, s);
        GLES20.glCompileShader(glCreateShader);
        GLES20.glGetShaderiv(glCreateShader, 35713, array, 0);
        if (array[0] == 0) {
            TXCLog.w("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(glCreateShader));
            return 0;
        }
        return glCreateShader;
    }
    
    public static int a(final String s, final String s2) {
        final int[] array = { 0 };
        final int a = a(s, 35633);
        if (a == 0) {
            TXCLog.w("Load Program", "Vertex Shader Failed");
            return 0;
        }
        final int a2 = a(s2, 35632);
        if (a2 == 0) {
            TXCLog.w("Load Program", "Fragment Shader Failed");
            return 0;
        }
        final int glCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(glCreateProgram, a);
        GLES20.glAttachShader(glCreateProgram, a2);
        GLES20.glLinkProgram(glCreateProgram);
        GLES20.glGetProgramiv(glCreateProgram, 35714, array, 0);
        if (array[0] <= 0) {
            TXCLog.w("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(a);
        GLES20.glDeleteShader(a2);
        return glCreateProgram;
    }
    
    static {
        com.tencent.liteav.basic.c.j.f = new float[] { -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f };
        com.tencent.liteav.basic.c.j.g = new float[] { 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f };
        com.tencent.liteav.basic.c.j.h = new float[] { 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f };
        com.tencent.liteav.basic.c.j.i = new float[] { 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f };
        com.tencent.liteav.basic.c.j.j = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f };
        com.tencent.liteav.basic.c.j.a = a(com.tencent.liteav.basic.c.j.f);
        com.tencent.liteav.basic.c.j.b = a(com.tencent.liteav.basic.c.j.g);
        com.tencent.liteav.basic.c.j.c = a(com.tencent.liteav.basic.c.j.h);
        com.tencent.liteav.basic.c.j.d = a(com.tencent.liteav.basic.c.j.i);
        com.tencent.liteav.basic.c.j.e = a(com.tencent.liteav.basic.c.j.j);
        com.tencent.liteav.basic.c.j.k = 2;
    }
    
    public static class a
    {
        public int[] a;
        public int[] b;
        public int c;
        public int d;
        
        public a() {
            this.a = null;
            this.b = null;
            this.c = -1;
            this.d = -1;
        }
    }
    
    public interface b
    {
    }
}
