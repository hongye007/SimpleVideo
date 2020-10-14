package com.tencent.liteav.basic.c;

import android.view.*;
import com.tencent.liteav.basic.log.*;
import android.opengl.*;
import javax.microedition.khronos.egl.*;
import android.os.*;
import java.nio.*;
import android.graphics.*;

public class e implements g.a
{
    private volatile HandlerThread a;
    private volatile g b;
    private h c;
    private int d;
    private boolean e;
    private float f;
    private float g;
    private int h;
    private int i;
    private int j;
    private int k;
    private boolean l;
    private o m;
    private boolean n;
    
    public e() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = 0;
        this.e = false;
        this.f = 1.0f;
        this.g = 1.0f;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = false;
        this.m = null;
        this.n = false;
    }
    
    public void a(final Object o, final Surface surface) {
        TXCLog.i("TXGLSurfaceRenderThread", "surface-render: surface render start " + surface + ", " + this);
        this.b(o, surface);
    }
    
    public void a() {
        TXCLog.i("TXGLSurfaceRenderThread", "surface-render: surface render stop " + this);
        this.f();
    }
    
    public Surface b() {
        synchronized (this) {
            return (this.b != null) ? this.b.b() : null;
        }
    }
    
    public void a(final int n) {
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.basic.c.e.this.h = n;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(16640);
            }
        });
    }
    
    public void b(final int n) {
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.basic.c.e.this.i = n;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(16640);
            }
        });
    }
    
    public void a(final Runnable runnable) {
        synchronized (this) {
            if (this.b != null) {
                this.b.post(runnable);
            }
        }
    }
    
    public void a(final int n, final boolean b, final int n2, final int n3, final int n4, final int n5, final int n6, final boolean b2, final boolean b3) {
        GLES20.glFinish();
        synchronized (this) {
            if (this.b != null) {
                this.b.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        try {
                            com.tencent.liteav.basic.c.e.this.b(n, b, n2, n3, n4, n5, n6, b2, b3);
                        }
                        catch (Exception ex) {
                            TXCLog.e("TXGLSurfaceRenderThread", "surface-render: render texture error occurred!" + ex.getMessage());
                        }
                    }
                });
            }
        }
    }
    
    public void a(final o m) {
        this.m = m;
        this.l = true;
    }
    
    @Override
    public void c() {
        this.c = new h();
        if (!this.c.c()) {
            return;
        }
        this.c.a(com.tencent.liteav.basic.c.l.e, com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, false));
    }
    
    @Override
    public void d() {
    }
    
    @Override
    public void e() {
        if (this.c != null) {
            this.c.e();
            this.c = null;
        }
    }
    
    private void b(final Object o, final Surface c) {
        this.f();
        synchronized (this) {
            (this.a = new HandlerThread("TXGLSurfaceRenderThread")).start();
            (this.b = new g(this.a.getLooper())).a(this);
            if (o == null || o instanceof EGLContext) {
                this.b.d = false;
                this.b.h = (EGLContext)o;
            }
            else {
                this.b.d = true;
                this.b.f = (android.opengl.EGLContext)o;
            }
            this.b.c = c;
            TXCLog.w("TXGLSurfaceRenderThread", "surface-render: create gl thread " + this.a.getName());
        }
        this.c(100);
    }
    
    private void f() {
        synchronized (this) {
            if (this.b != null) {
                com.tencent.liteav.basic.c.g.a(this.b, this.a);
                TXCLog.w("TXGLSurfaceRenderThread", "surface-render: destroy gl thread");
            }
            this.b = null;
            this.a = null;
        }
    }
    
    private void c(final int n) {
        synchronized (this) {
            if (this.b != null) {
                this.b.sendEmptyMessage(n);
            }
        }
    }
    
    private void b(final int n, final boolean b, final int d, final int n2, final int n3, final int n4, final int n5, final boolean b2, final boolean b3) {
        if (n4 == 0 || n5 == 0 || this.c == null) {
            return;
        }
        if (this.n) {
            this.n = false;
            return;
        }
        if (b2) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16640);
            GLES20.glBindFramebuffer(36160, 0);
            if (this.b != null) {
                this.b.c();
            }
            this.n = true;
        }
        int j = (n2 != 0) ? n2 : n4;
        int k = (n3 != 0) ? n3 : n5;
        int n6 = 0;
        int n7 = 0;
        if (this.h == 0) {
            n6 = 0;
            n7 = 0;
        }
        else if (this.h == 1) {
            final int n8 = (720 - this.d) % 360;
            final boolean b4 = n8 == 90 || n8 == 270;
            final int[] a = this.a(j, k, b4 ? n5 : n4, b4 ? n4 : n5);
            j = a[0];
            k = a[1];
            n6 = a[2];
            n7 = a[3];
        }
        GLES20.glViewport(n6, n7, this.j = j, this.k = k);
        boolean e = b;
        if (this.i == 1) {
            if (!b3) {
                e = !b;
            }
        }
        else if (this.i == 2 && b3) {
            e = !b;
        }
        final float f = (k != 0) ? (j / (float)k) : 1.0f;
        final float g = (n5 != 0) ? (n4 / (float)n5) : 1.0f;
        if (this.e != e || this.d != d || this.f != f || this.g != g) {
            this.e = e;
            this.d = d;
            this.f = f;
            this.g = g;
            final int n9 = (720 - this.d) % 360;
            final boolean b5 = n9 == 90 || n9 == 270;
            this.c.a(n4, n5, n9, com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, true), (b5 ? k : j) / (float)(b5 ? j : k), !b5 && this.e, b5 && this.e);
            if (b5) {
                this.c.g();
            }
            else {
                this.c.h();
            }
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        GLES20.glBindFramebuffer(36160, 0);
        this.c.b(n);
        this.a(n6, n7);
        if (this.b != null) {
            this.b.c();
        }
    }
    
    private int[] a(final int n, final int n2, final int n3, final int n4) {
        final int[] array = new int[4];
        int n5 = 0;
        int n6 = 0;
        final float n7 = n2 / (float)n;
        final float n8 = n4 / (float)n3;
        int n9;
        int n10;
        if (n7 > n8) {
            n9 = n;
            n10 = (int)(n * n8);
            n6 = (n2 - n10) / 2;
        }
        else {
            n10 = n2;
            n9 = (int)(n2 / n8);
            n5 = (n - n9) / 2;
        }
        array[0] = n9;
        array[1] = n10;
        array[2] = n5;
        array[3] = n6;
        return array;
    }
    
    private void a(final int n, final int n2) {
        if (this.l) {
            if (this.j != 0 && this.k != 0) {
                final boolean b = this.j <= this.k;
                final int n3 = (this.k >= this.j) ? this.k : this.j;
                final int n4 = (this.k >= this.j) ? this.j : this.k;
                int n5 = n3;
                int n6 = n4;
                if (b) {
                    n5 = n4;
                    n6 = n3;
                }
                final ByteBuffer allocate = ByteBuffer.allocate(n5 * n6 * 4);
                final Bitmap bitmap = Bitmap.createBitmap(n5, n6, Bitmap.Config.ARGB_8888);
                allocate.position(0);
                GLES20.glReadPixels(n, n2, n5, n6, 6408, 5121, (Buffer)allocate);
                final int n7 = n5;
                final int n8 = n6;
                final o m = this.m;
                if (m != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            allocate.position(0);
                            bitmap.copyPixelsFromBuffer((Buffer)allocate);
                            final Matrix matrix = new Matrix();
                            matrix.setScale(1.0f, -1.0f);
                            m.onTakePhotoComplete(Bitmap.createBitmap(bitmap, 0, 0, n7, n8, matrix, false));
                            bitmap.recycle();
                        }
                    }).start();
                }
            }
            this.m = null;
            this.l = false;
        }
    }
}
