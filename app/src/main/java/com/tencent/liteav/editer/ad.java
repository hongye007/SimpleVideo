package com.tencent.liteav.editer;

import android.graphics.*;
import android.view.*;

import com.tencent.liteav.basic.log.*;
import android.os.*;
import android.opengl.*;

public class ad
{
    private final String a = "VideoGLGenerate";
    private float[] b;
    private Handler c;
    private HandlerThread d;
    private int e;
    private int f;
    private c g;
    private com.tencent.liteav.renderer.c h;
    private com.tencent.liteav.renderer.c i;
    private r j;
    private o k;
    private SurfaceTexture l;
    private Surface m;
    private boolean n;
    private boolean o;
    private e p;
    private SurfaceTexture.OnFrameAvailableListener q;
    
    public ad(final String s) {
        this.q = (SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                ad.this.n = true;
                if (ad.this.p != null) {
                    ad.this.c(ad.this.p);
                    ad.this.p = null;
                }
            }
        };
        this.b = new float[16];
        (this.d = new HandlerThread(s + "glGene")).start();
        this.c = new Handler(this.d.getLooper());
    }
    
    public void a(final g g) {
        this.e = g.a;
        this.f = g.b;
    }
    
    public void a(final o k) {
        this.k = k;
    }
    
    public void a(final r j) {
        this.j = j;
    }
    
    public void a() {
        TXCLog.i("VideoGLGenerate", "start");
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    ad.this.f();
                    ad.this.d();
                }
            });
        }
    }
    
    public void b() {
        TXCLog.i("VideoGLGenerate", "stop");
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (ad.this.j != null && ad.this.g != null) {
                        ad.this.j.b(ad.this.g.e());
                    }
                    ad.this.e();
                    ad.this.g();
                }
            });
        }
    }
    
    public void c() {
        if (this.c != null) {
            if (this.d != null) {
                if (Build.VERSION.SDK_INT >= 18) {
                    this.d.quitSafely();
                }
                else {
                    this.d.quit();
                }
                this.d = null;
            }
            this.k = null;
            this.j = null;
            this.q = null;
            this.c = null;
        }
    }
    
    private void d() {
        TXCLog.i("VideoGLGenerate", "initTextureRender");
        (this.h = new com.tencent.liteav.renderer.c(true)).b();
        (this.i = new com.tencent.liteav.renderer.c(false)).b();
        this.l = new SurfaceTexture(this.h.a());
        this.m = new Surface(this.l);
        this.l.setOnFrameAvailableListener(this.q);
        this.o = true;
        if (this.k != null) {
            this.k.a(this.m);
        }
        if (this.j != null && this.g != null) {
            this.j.a(this.g.e());
        }
    }
    
    private void e() {
        TXCLog.i("VideoGLGenerate", "destroyTextureRender");
        this.o = false;
        if (this.h != null) {
            this.h.c();
        }
        this.h = null;
        if (this.i != null) {
            this.i.c();
        }
        this.i = null;
    }
    
    private void f() {
        TXCLog.i("VideoGLGenerate", "initEGL");
        this.g = com.tencent.liteav.basic.c.c.a(null, null, null, this.e, this.f);
    }
    
    private void g() {
        TXCLog.i("VideoGLGenerate", "destroyEGL");
        if (this.k != null) {
            this.k.b(this.m);
        }
        if (this.g != null) {
            this.g.c();
            this.g = null;
        }
    }
    
    public synchronized void a(final e e) {
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    ad.this.c(e);
                }
            });
        }
    }
    
    public void b(final e e) {
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    ad.this.n = true;
                    ad.this.c(e);
                }
            });
        }
    }
    
    private boolean c(final e p) {
        if (!this.o) {
            return false;
        }
        if (p.p() || p.r()) {
            if (this.k != null) {
                if (p.y() == 0) {
                    this.k.a(p.x(), this.b, p);
                }
                else {
                    this.k.a(this.h.a(), this.b, p);
                }
            }
            return false;
        }
        boolean n = false;
        synchronized (this) {
            if (!this.n) {
                this.p = p;
                return false;
            }
            n = this.n;
            this.n = false;
        }
        GLES20.glViewport(0, 0, this.e, this.f);
        if (n) {
            try {
                if (this.l != null) {
                    this.l.updateTexImage();
                    this.l.getTransformMatrix(this.b);
                }
            }
            catch (Exception ex) {}
            if (this.k != null) {
                if (p.y() == 0) {
                    this.k.a(p.x(), this.b, p);
                }
                else {
                    this.k.a(this.h.a(), this.b, p);
                }
            }
            else if (this.i != null) {
                this.i.a(this.l);
            }
        }
        return true;
    }
}
