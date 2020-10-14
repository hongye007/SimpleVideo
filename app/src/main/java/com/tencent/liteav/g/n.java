package com.tencent.liteav.g;

import android.view.*;

import com.tencent.liteav.basic.log.*;
import android.os.*;
import android.graphics.*;
import java.util.*;

import android.opengl.*;

public class n
{
    private final String a = "VideoJoinGLGenerate";
    private ArrayList<Surface> b;
    private Handler c;
    private HandlerThread d;
    private int e;
    private int f;
    private c g;
    private com.tencent.liteav.renderer.c h;
    private r i;
    private d j;
    private boolean k;
    private j l;
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        try {
            this.c();
        }
        catch (Exception ex) {}
        catch (Error error) {}
    }
    
    public n() {
        this.b = new ArrayList<Surface>();
        (this.d = new HandlerThread("GLGeneJoin")).start();
        this.c = new Handler(this.d.getLooper());
    }
    
    public void a(final g g) {
        this.e = g.a;
        this.f = g.b;
    }
    
    public void a(final j l) {
        this.l = l;
    }
    
    public void a(final d j) {
        this.j = j;
    }
    
    public void a(final r i) {
        this.i = i;
    }
    
    public void a() {
        TXCLog.i("VideoJoinGLGenerate", "start");
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    n.this.f();
                    n.this.d();
                }
            });
        }
    }
    
    public void b() {
        TXCLog.i("VideoJoinGLGenerate", "stop");
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (n.this.i != null) {
                        n.this.i.b(n.this.g.e());
                    }
                    n.this.e();
                    n.this.g();
                }
            });
        }
    }
    
    public void c() {
        TXCLog.i("VideoJoinGLGenerate", "release");
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
            this.j = null;
            this.i = null;
            this.c = null;
        }
        this.b.clear();
    }
    
    public void a(final Runnable runnable) {
        if (Looper.myLooper() == ((this.c != null) ? this.c.getLooper() : null)) {
            runnable.run();
        }
        else if (this.c != null) {
            this.c.post(runnable);
        }
    }
    
    private void d() {
        TXCLog.i("VideoJoinGLGenerate", "initTextureRender");
        (this.h = new com.tencent.liteav.renderer.c(false)).b();
        final List<i> a = this.l.a();
        for (int i = 0; i < a.size(); ++i) {
            final i j = a.get(i);
            final k b = new k();
            b.e = new float[16];
            (b.a = new com.tencent.liteav.renderer.c(true)).b();
            b.b = new SurfaceTexture(b.a.a());
            b.c = new Surface(b.b);
            b.b.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
                public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                    b.d = true;
                    if (b.f != null) {
                        n.this.b(b.f, j);
                        b.f = null;
                    }
                }
            });
            j.b = b;
            this.b.add(b.c);
        }
        this.k = true;
        if (this.j != null) {
            this.j.a(this.b);
        }
        if (this.i != null) {
            this.i.a(this.g.e());
        }
    }
    
    private void e() {
        TXCLog.i("VideoJoinGLGenerate", "destroyTextureRender mVideoExtractListConfig:" + this.l);
        this.k = false;
        if (this.l != null) {
            final List<i> a = this.l.a();
            for (int i = 0; i < a.size(); ++i) {
                final k b = a.get(i).b;
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
        }
        if (this.h != null) {
            this.h.c();
        }
        this.h = null;
    }
    
    private void f() {
        TXCLog.i("VideoJoinGLGenerate", "initEGL");
        this.g = com.tencent.liteav.basic.c.c.a(null, null, null, this.e, this.f);
    }
    
    private void g() {
        TXCLog.i("VideoJoinGLGenerate", "destroyEGL");
        if (this.j != null) {
            this.j.b(this.b);
        }
        if (this.g != null) {
            this.g.c();
            this.g = null;
        }
    }
    
    public synchronized void a(final e e, final i i) {
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    n.this.b(e, i);
                }
            });
        }
    }
    
    private boolean b(final e f, final i i) {
        if (!this.k) {
            return false;
        }
        final k b = i.b;
        if (f.p()) {
            if (this.j != null) {
                if (f.y() == 0) {
                    this.j.a(f.x(), b.e, f);
                }
                else {
                    this.j.a(b.a.a(), b.e, f);
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
        GLES20.glViewport(0, 0, this.e, this.f);
        if (d) {
            try {
                if (b.b != null) {
                    b.b.updateTexImage();
                    b.b.getTransformMatrix(b.e);
                }
            }
            catch (Exception ex) {}
            if (this.j != null) {
                if (f.y() == 0) {
                    this.j.a(f.x(), b.e, f);
                }
                else {
                    this.j.a(b.a.a(), b.e, f);
                }
            }
            else if (this.h != null) {
                this.h.a(b.b);
            }
        }
        return true;
    }
}
