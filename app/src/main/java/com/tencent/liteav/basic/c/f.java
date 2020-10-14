package com.tencent.liteav.basic.c;

import android.graphics.*;
import javax.microedition.khronos.egl.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;
import android.opengl.*;

public class f implements g.a, m
{
    private volatile HandlerThread b;
    private volatile g c;
    private n d;
    private int[] e;
    private SurfaceTexture f;
    private boolean g;
    public int a;
    private long h;
    private long i;
    private float[] j;
    
    public f() {
        this.b = null;
        this.c = null;
        this.e = null;
        this.f = null;
        this.g = false;
        this.a = 25;
        this.h = 0L;
        this.i = 0L;
        this.j = new float[16];
    }
    
    @Override
    public void a(final int a, final boolean b) {
        this.a = a;
        this.b();
    }
    
    @Override
    public void a() {
        this.f();
    }
    
    @Override
    public void setSurfaceTextureListener(final n d) {
        this.d = d;
    }
    
    @Override
    public SurfaceTexture getSurfaceTexture() {
        return this.f;
    }
    
    @Override
    public EGLContext getGLContext() {
        synchronized (this) {
            return (this.c != null) ? this.c.a() : null;
        }
    }
    
    @Override
    public void a(final Runnable runnable) {
        synchronized (this) {
            if (this.c != null) {
                this.c.post(runnable);
            }
        }
    }
    
    @Override
    public void a(final boolean b) {
        synchronized (this) {
            try {
                if (this.c != null) {
                    this.c.removeCallbacksAndMessages((Object)null);
                }
                this.g = false;
                if (this.f == null || this.e == null) {
                    return;
                }
                this.f.updateTexImage();
            }
            catch (Exception ex) {
                TXCLog.e("TXGLSurfaceTextureThread", "updateTexImage failed." + ex.getMessage());
            }
            this.f.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
                public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                    com.tencent.liteav.basic.c.f.this.a(103, new Runnable() {
                        @Override
                        public void run() {
                            com.tencent.liteav.basic.c.f.this.g = true;
                            com.tencent.liteav.basic.c.f.this.a(102);
                        }
                    });
                    surfaceTexture.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
                }
            });
        }
    }
    
    @Override
    public void a(final int n, final boolean b, final int n2, final int n3, final int n4, final boolean b2) {
    }
    
    @Override
    public void a(final byte[] array) {
    }
    
    @Override
    public void setRendMode(final int n) {
    }
    
    @Override
    public void setRendMirror(final int n) {
    }
    
    @Override
    public void c() {
        this.h();
    }
    
    @Override
    public void d() {
        this.a(102, 5L);
        if (!this.i()) {
            return;
        }
        if (this.f == null || this.e == null) {
            return;
        }
        try {
            this.f.updateTexImage();
            this.f.getTransformMatrix(this.j);
        }
        catch (Exception ex) {
            TXCLog.e("TXGLSurfaceTextureThread", "onMsgRend Exception " + ex.getMessage());
        }
        final n d = this.d;
        if (d != null) {
            d.onTextureProcess(this.e[0], this.j);
        }
    }
    
    @Override
    public void e() {
        this.g();
    }
    
    private void b() {
        this.f();
        synchronized (this) {
            (this.b = new HandlerThread("TXGLSurfaceTextureThread")).start();
            (this.c = new g(this.b.getLooper())).a(this);
            this.c.a = 1280;
            this.c.b = 720;
            TXCLog.w("TXGLSurfaceTextureThread", "create gl thread " + this.b.getName());
        }
        this.a(100);
    }
    
    private void f() {
        synchronized (this) {
            if (this.c != null) {
                com.tencent.liteav.basic.c.g.a(this.c, this.b);
                TXCLog.w("TXGLSurfaceTextureThread", "destroy gl thread");
            }
            this.c = null;
            this.b = null;
        }
    }
    
    private void a(final int n, final long n2) {
        synchronized (this) {
            if (this.c != null) {
                this.c.sendEmptyMessageDelayed(n, n2);
            }
        }
    }
    
    private void a(final int n) {
        synchronized (this) {
            if (this.c != null) {
                this.c.sendEmptyMessage(n);
            }
        }
    }
    
    private void a(final int what, final Runnable obj) {
        synchronized (this) {
            if (this.c != null) {
                final Message message = new Message();
                message.what = what;
                message.obj = obj;
                this.c.sendMessage(message);
            }
        }
    }
    
    private void g() {
        TXCLog.w("TXGLSurfaceTextureThread", "destroy surface texture ");
        final n d = this.d;
        if (d != null) {
            d.onSurfaceTextureDestroy(this.f);
        }
        if (this.f != null) {
            this.f.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
            this.f.release();
            this.g = false;
            this.f = null;
        }
        if (this.e != null) {
            GLES20.glDeleteTextures(1, this.e, 0);
            this.e = null;
        }
    }
    
    private void h() {
        TXCLog.w("TXGLSurfaceTextureThread", "init surface texture ");
        (this.e = new int[1])[0] = com.tencent.liteav.basic.c.j.b();
        if (this.e[0] <= 0) {
            this.e = null;
            return;
        }
        (this.f = new SurfaceTexture(this.e[0])).setDefaultBufferSize(1280, 720);
        this.f.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                com.tencent.liteav.basic.c.f.this.a(103, new Runnable() {
                    @Override
                    public void run() {
                        com.tencent.liteav.basic.c.f.this.g = true;
                        com.tencent.liteav.basic.c.f.this.a(102);
                    }
                });
                surfaceTexture.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
            }
        });
        final n d = this.d;
        if (d != null) {
            d.onSurfaceTextureAvailable(this.f);
        }
    }
    
    private boolean i() {
        if (!this.g) {
            this.h = 0L;
            this.i = System.nanoTime();
            return false;
        }
        final long nanoTime = System.nanoTime();
        if (nanoTime < this.i + this.h * 1000L * 1000L * 1000L / this.a) {
            return false;
        }
        if (this.i == 0L) {
            this.i = nanoTime;
        }
        else if (nanoTime > this.i + 1000000000L) {
            this.h = 0L;
            this.i = nanoTime;
        }
        ++this.h;
        return true;
    }
}
