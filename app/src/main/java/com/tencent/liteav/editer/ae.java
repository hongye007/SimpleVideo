package com.tencent.liteav.editer;

import android.content.*;
import android.widget.*;
import android.graphics.*;

import com.tencent.liteav.basic.log.*;
import android.os.*;
import android.view.*;
import android.opengl.*;

public class ae
{
    private final Context a;
    private float[] b;
    private d c;
    private o d;
    private FrameLayout e;
    private TextureView f;
    private int g;
    private int h;
    private Handler i;
    private HandlerThread j;
    private c k;
    private c l;
    private SurfaceTexture m;
    private SurfaceTexture n;
    private Surface o;
    private boolean p;
    private e q;
    private boolean r;
    private boolean s;
    private TextureView.SurfaceTextureListener t;
    private SurfaceTexture.OnFrameAvailableListener u;
    
    public ae(final Context a) {
        this.t = (TextureView.SurfaceTextureListener)new TextureView.SurfaceTextureListener() {
            public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, final int n, final int n2) {
                TXCLog.i("VideoGLRender", "onSurfaceTextureAvailable surface:" + surfaceTexture + ",width:" + n + ",height:" + n2 + ", mSaveSurfaceTexture = " + ae.this.n);
                ae.this.g = n;
                ae.this.h = n2;
                if (ae.this.n != null) {
                    if (Build.VERSION.SDK_INT >= 16) {
                        ae.this.f.setSurfaceTexture(ae.this.n);
                    }
                }
                else {
                    ae.this.n = surfaceTexture;
                    ae.this.a(surfaceTexture);
                }
            }
            
            public void onSurfaceTextureSizeChanged(final SurfaceTexture surfaceTexture, final int n, final int n2) {
                TXCLog.i("VideoGLRender", "wonSurfaceTextureSizeChanged surface:" + surfaceTexture + ",width:" + n + ",height:" + n2);
                ae.this.g = n;
                ae.this.h = n2;
                if (ae.this.d != null) {
                    ae.this.d.a(n, n2);
                }
            }
            
            public boolean onSurfaceTextureDestroyed(final SurfaceTexture surfaceTexture) {
                TXCLog.i("VideoGLRender", "onSurfaceTextureDestroyed surface:" + surfaceTexture);
                if (!ae.this.r) {
                    ae.this.n = null;
                    ae.this.a(false);
                }
                return false;
            }
            
            public void onSurfaceTextureUpdated(final SurfaceTexture surfaceTexture) {
            }
        };
        this.u = (SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
            public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                synchronized (this) {
                    ae.this.p = true;
                }
            }
        };
        this.a = a;
        this.b = new float[16];
        this.c = new d();
        (this.j = new HandlerThread("videoGLRender")).start();
        this.i = new Handler(this.j.getLooper());
    }
    
    public void a(final o d) {
        this.d = d;
    }
    
    public void a(final a.g g) {
        if (this.e != null) {
            this.e.removeAllViews();
        }
        final FrameLayout a = g.a;
        if (a == null) {
            TXCLog.e("VideoGLRender", "initWithPreview param.videoView is NULL!!!");
            return;
        }
        if (this.e == null || !a.equals(this.e)) {
            (this.f = new TextureView(this.a)).setSurfaceTextureListener(this.t);
        }
        (this.e = a).addView((View)this.f);
    }
    
    public int a() {
        return this.g;
    }
    
    public int b() {
        return this.h;
    }
    
    public void c() {
        this.r = true;
    }
    
    public void d() {
        this.r = false;
    }
    
    public void e() {
        this.r = false;
        this.a(true);
        if (this.f != null) {
            this.f.setSurfaceTextureListener((TextureView.SurfaceTextureListener)null);
            this.f = null;
        }
        if (this.e != null) {
            this.e.removeAllViews();
            this.e = null;
        }
        if (this.d != null) {
            this.d = null;
        }
        this.t = null;
        this.u = null;
    }
    
    private void a(final SurfaceTexture surfaceTexture) {
        if (this.i != null) {
            this.i.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    ae.this.c.a(surfaceTexture);
                    ae.this.f();
                    if (ae.this.d != null) {
                        ae.this.d.a(ae.this.o);
                    }
                }
            });
        }
    }
    
    private void a(final boolean b) {
        if (this.i != null) {
            this.i.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    try {
                        if (ae.this.i == null) {
                            return;
                        }
                        if (ae.this.d != null) {
                            ae.this.d.b(ae.this.o);
                        }
                        ae.this.g();
                        ae.this.c.a();
                        if (b) {
                            ae.this.i = null;
                            if (ae.this.j != null) {
                                ae.this.j.quit();
                                ae.this.j = null;
                            }
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }
    }
    
    private void f() {
        (this.k = new c(true)).b();
        (this.l = new c(false)).b();
        this.m = new SurfaceTexture(this.k.a());
        this.o = new Surface(this.m);
        this.m.setOnFrameAvailableListener(this.u);
        this.s = true;
    }
    
    private void g() {
        this.s = false;
        if (this.k != null) {
            this.k.c();
        }
        this.k = null;
        if (this.l != null) {
            this.l.c();
        }
        this.l = null;
        if (this.m != null) {
            this.m.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
            this.m.release();
            this.m = null;
        }
        if (this.o != null) {
            this.o.release();
            this.o = null;
        }
    }
    
    public void a(final int n, final int n2, final int n3) {
        GLES20.glViewport(0, 0, n2, n3);
        if (this.l != null) {
            this.l.a(n, false, 0);
        }
    }
    
    public void a(final e e) {
        if (this.i != null) {
            this.i.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (ae.this.c(e)) {
                        ae.this.c.b();
                    }
                }
            });
        }
    }
    
    public void a(final int n) {
        if (this.i != null) {
            this.i.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (ae.this.d != null) {
                        ae.this.d.a(n);
                        ae.this.c.b();
                    }
                }
            });
        }
    }
    
    public void b(final e e) {
        if (this.i != null) {
            this.i.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    ae.this.p = true;
                    ae.this.c(e);
                    ae.this.c.b();
                }
            });
        }
    }
    
    private boolean c(final e q) {
        if (!this.s) {
            return false;
        }
        if (q.p()) {
            TXCLog.i("VideoGLRender", "onDrawFrame, frame isEndFrame");
            if (this.d != null) {
                if (q.y() == 0) {
                    this.d.a(q.x(), this.b, q);
                }
                else {
                    this.d.a(this.k.a(), this.b, q);
                }
            }
            return false;
        }
        this.q = q;
        boolean p = false;
        synchronized (this) {
            if (!this.p) {
                return false;
            }
            p = this.p;
            this.p = false;
        }
        GLES20.glViewport(0, 0, this.g, this.h);
        if (p) {
            if (this.m != null) {
                this.m.updateTexImage();
                this.m.getTransformMatrix(this.b);
            }
            if (this.d != null) {
                if (q.y() == 0) {
                    this.d.a(q.x(), this.b, q);
                }
                else {
                    this.d.a(this.k.a(), this.b, q);
                }
            }
            else if (this.l != null) {
                this.l.a(this.m);
            }
        }
        return true;
    }
}
