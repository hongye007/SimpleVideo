package com.tencent.liteav.g;

import android.content.*;
import android.widget.*;
import android.graphics.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;
import android.view.*;
import java.util.*;
import android.opengl.*;

public class o
{
    private Context a;
    private d b;
    private com.tencent.liteav.g.d c;
    private FrameLayout d;
    private TextureView e;
    private int f;
    private int g;
    private Handler h;
    private HandlerThread i;
    private c j;
    private SurfaceTexture k;
    private boolean l;
    private boolean m;
    private ArrayList<Surface> n;
    private TextureView.SurfaceTextureListener o;
    
    public o(final Context a) {
        this.o = (TextureView.SurfaceTextureListener)new TextureView.SurfaceTextureListener() {
            public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, final int n, final int n2) {
                TXCLog.i("VideoJoinGLRender", "onSurfaceTextureAvailable surface:" + surfaceTexture + ",width:" + n + ",height:" + n2 + ", mSaveSurfaceTexture = " + com.tencent.liteav.g.o.this.k);
                com.tencent.liteav.g.o.this.f = n;
                com.tencent.liteav.g.o.this.g = n2;
                if (com.tencent.liteav.g.o.this.k != null) {
                    if (Build.VERSION.SDK_INT >= 16) {
                        com.tencent.liteav.g.o.this.e.setSurfaceTexture(com.tencent.liteav.g.o.this.k);
                    }
                }
                else {
                    com.tencent.liteav.g.o.this.k = surfaceTexture;
                    com.tencent.liteav.g.o.this.a(surfaceTexture);
                }
            }
            
            public void onSurfaceTextureSizeChanged(final SurfaceTexture surfaceTexture, final int n, final int n2) {
                TXCLog.i("VideoJoinGLRender", "onSurfaceTextureSizeChanged surface:" + surfaceTexture + ",width:" + n + ",height:" + n2);
                com.tencent.liteav.g.o.this.f = n;
                com.tencent.liteav.g.o.this.g = n2;
                if (com.tencent.liteav.g.o.this.c != null) {
                    com.tencent.liteav.g.o.this.c.a(n, n2);
                }
            }
            
            public boolean onSurfaceTextureDestroyed(final SurfaceTexture surfaceTexture) {
                TXCLog.i("VideoJoinGLRender", "onSurfaceTextureDestroyed surface:" + surfaceTexture);
                if (!com.tencent.liteav.g.o.this.l) {
                    com.tencent.liteav.g.o.this.k = null;
                    com.tencent.liteav.g.o.this.a(false);
                }
                return false;
            }
            
            public void onSurfaceTextureUpdated(final SurfaceTexture surfaceTexture) {
            }
        };
        this.a = a;
        this.n = new ArrayList<Surface>();
        this.b = new d();
        (this.i = new HandlerThread("VideoJoinGLRender")).start();
        this.h = new Handler(this.i.getLooper());
    }
    
    public void a(final com.tencent.liteav.g.d c) {
        this.c = c;
    }
    
    public void a(final a.g g) {
        if (this.d != null) {
            this.d.removeAllViews();
        }
        final FrameLayout a = g.a;
        if (a == null) {
            TXCLog.e("VideoJoinGLRender", "initWithPreview param.videoView is NULL!!!");
            return;
        }
        if (this.d == null || !a.equals(this.d)) {
            (this.e = new TextureView(this.a)).setSurfaceTextureListener(this.o);
        }
        (this.d = a).addView((View)this.e);
    }
    
    public int a() {
        return this.f;
    }
    
    public int b() {
        return this.g;
    }
    
    public void c() {
        this.l = true;
    }
    
    public void d() {
        this.l = false;
    }
    
    private void a(final SurfaceTexture surfaceTexture) {
        if (this.h != null) {
            this.h.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.g.o.this.b.a(surfaceTexture);
                    com.tencent.liteav.g.o.this.e();
                    if (com.tencent.liteav.g.o.this.c != null) {
                        com.tencent.liteav.g.o.this.c.a(com.tencent.liteav.g.o.this.n);
                    }
                }
            });
        }
    }
    
    private void a(final boolean b) {
        if (this.h != null) {
            this.h.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    try {
                        if (com.tencent.liteav.g.o.this.h == null) {
                            return;
                        }
                        if (com.tencent.liteav.g.o.this.c != null) {
                            com.tencent.liteav.g.o.this.c.b(com.tencent.liteav.g.o.this.n);
                        }
                        com.tencent.liteav.g.o.this.f();
                        com.tencent.liteav.g.o.this.b.a();
                        if (b) {
                            com.tencent.liteav.g.o.this.h = null;
                            if (com.tencent.liteav.g.o.this.i != null) {
                                com.tencent.liteav.g.o.this.i.quit();
                                com.tencent.liteav.g.o.this.i = null;
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
    
    private void e() {
        TXCLog.i("VideoJoinGLRender", "initTextureRender");
        (this.j = new c(false)).b();
        final List<i> d = t.a().d();
        for (int i = 0; i < d.size(); ++i) {
            final i j = d.get(i);
            final k b = new k();
            b.e = new float[16];
            (b.a = new c(true)).b();
            b.b = new SurfaceTexture(b.a.a());
            b.c = new Surface(b.b);
            b.b.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
                public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                    b.d = true;
                    if (b.f != null && com.tencent.liteav.g.o.this.b(b.f, j)) {
                        b.f = null;
                        com.tencent.liteav.g.o.this.b.b();
                    }
                }
            });
            j.b = b;
            this.n.add(b.c);
        }
        this.m = true;
    }
    
    private void f() {
        TXCLog.i("VideoJoinGLRender", "destroyTextureRender");
        this.m = false;
        final List<i> d = t.a().d();
        for (int i = 0; i < d.size(); ++i) {
            final k b = d.get(i).b;
            if (b.a != null) {
                b.a.c();
            }
            b.a = null;
            if (b.b != null) {
                b.b.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
                b.b.release();
            }
            b.b = null;
            if (b.c != null) {
                b.c.release();
            }
            b.c = null;
        }
        if (this.j != null) {
            this.j.c();
        }
        this.j = null;
    }
    
    public void a(final int n, final int n2, final int n3) {
        GLES20.glViewport(0, 0, n2, n3);
        if (this.j != null) {
            this.j.a(n, false, 0);
        }
    }
    
    public void a(final e e, final i i) {
        if (this.h != null) {
            this.h.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.g.o.this.b(e, i)) {
                        com.tencent.liteav.g.o.this.b.b();
                    }
                }
            });
        }
    }
    
    private boolean b(final e f, final i i) {
        if (!this.m) {
            return false;
        }
        final k b = i.b;
        if (f.p()) {
            TXCLog.i("VideoJoinGLRender", "onDrawFrame, frame isEndFrame");
            if (this.c != null) {
                if (f.y() == 0) {
                    this.c.a(f.x(), b.e, f);
                }
                else {
                    this.c.a(b.a.a(), b.e, f);
                }
            }
            return false;
        }
        boolean d = false;
        synchronized (this) {
            if (!b.d) {
                b.f = f;
                return false;
            }
            d = b.d;
            b.d = false;
        }
        GLES20.glViewport(0, 0, this.f, this.g);
        if (d) {
            if (b.b != null) {
                b.b.updateTexImage();
                b.b.getTransformMatrix(b.e);
            }
            if (this.c != null) {
                if (f.y() == 0) {
                    this.c.a(f.x(), b.e, f);
                }
                else {
                    this.c.a(b.a.a(), b.e, f);
                }
            }
            else if (this.j != null) {
                this.j.a(b.b);
            }
        }
        return true;
    }
}
