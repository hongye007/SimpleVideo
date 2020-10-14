package com.tencent.liteav.b;

import android.os.*;

import java.util.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.editer.*;
import android.graphics.*;
import android.view.*;
import android.opengl.*;

public class n
{
    private final String b = "VideoGLMultiGenerate";
    private Handler c;
    private HandlerThread d;
    private int e;
    private int f;
    private c g;
    private com.tencent.liteav.renderer.c h;
    private r i;
    private boolean j;
    public List<a> a;
    
    public n() {
        this.a = new ArrayList<a>();
        (this.d = new HandlerThread("GLMultiGene")).start();
        this.c = new Handler(this.d.getLooper());
    }
    
    public void a(final g g, final int n) {
        final a a = new a();
        a.b = n;
        a.c = new float[16];
        this.a.add(a);
        a.d = g.a;
        a.e = g.b;
        this.e = ((g.a > this.e) ? g.a : this.e);
        this.f = ((g.b > this.f) ? g.b : this.f);
        TXCLog.i("VideoGLMultiGenerate", "setRenderResolution, mSurfaceWidth = " + this.e + ", mSurfaceHeight = " + this.f);
    }
    
    public void a(final k k, final int n) {
        if (this.a == null || this.a.size() == 0 || n >= this.a.size()) {
            TXCLog.e("VideoGLMultiGenerate", "setListener, mVideoGLInfoList is empty or mIndex is larger than size");
            return;
        }
        this.a.get(n).g = k;
    }
    
    public void a(final r i) {
        this.i = i;
    }
    
    public void a() {
        TXCLog.i("VideoGLMultiGenerate", "start");
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    n.this.e();
                    n.this.c();
                }
            });
        }
    }
    
    public void b() {
        TXCLog.i("VideoGLMultiGenerate", "stop");
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (n.this.i != null && n.this.g != null) {
                        n.this.i.b(n.this.g.e());
                    }
                    n.this.d();
                    n.this.f();
                }
            });
        }
    }
    
    private void c() {
        TXCLog.i("VideoGLMultiGenerate", "initTextureRender");
        (this.h = new com.tencent.liteav.renderer.c(false)).b();
        for (int i = 0; i < this.a.size(); ++i) {
            final a a = this.a.get(i);
            a.f = new com.tencent.liteav.renderer.c(true);
            a.f.b();
            a.h = new SurfaceTexture(a.f.a());
            a.i = new Surface(a.h);
            a.h.setOnFrameAvailableListener(a.l);
            if (a.g != null) {
                a.g.a(a.i, i);
            }
            if (i == this.a.size() - 1) {
                this.j = true;
            }
        }
        if (this.i != null) {
            this.i.a(this.g.e());
        }
    }
    
    private void d() {
        TXCLog.i("VideoGLMultiGenerate", "destroyTextureRender");
        this.j = false;
        for (int i = 0; i < this.a.size(); ++i) {
            final a a = this.a.get(i);
            if (a.f != null) {
                a.f.c();
                a.f = null;
                if (a.h != null) {
                    a.h.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
                    a.h.release();
                    a.h = null;
                }
                if (a.i != null) {
                    a.i.release();
                    a.i = null;
                }
                a.h = null;
                a.k = null;
                a.j = false;
                a.c = new float[16];
            }
        }
        if (this.h != null) {
            this.h.c();
        }
        this.h = null;
    }
    
    private void e() {
        TXCLog.i("VideoGLMultiGenerate", "initEGL");
        this.g = com.tencent.liteav.basic.c.c.a(null, null, null, this.e, this.f);
    }
    
    private void f() {
        TXCLog.i("VideoGLMultiGenerate", "destroyEGL");
        for (int i = 0; i < this.a.size(); ++i) {
            final a a = this.a.get(i);
            if (a.g != null) {
                a.g.b(a.i, i);
            }
        }
        if (this.g != null) {
            this.g.c();
            this.g = null;
        }
    }
    
    public synchronized void a(final e e, final int n) {
        if (this.a == null || this.a.size() == 0 || n >= this.a.size()) {
            TXCLog.e("VideoGLMultiGenerate", "setListener, mVideoGLInfoList is empty or mIndex is larger than size");
            return;
        }
        if (this.c != null) {
            this.c.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    n.this.b(e, n);
                }
            });
        }
    }
    
    private boolean b(final e e, final int n) {
        if (!this.j) {
            return false;
        }
        final a a = this.a.get(n);
        if (e.p() || e.r()) {
            if (a.g != null) {
                if (e.y() == 0) {
                    a.g.a(e.x(), a.c, e, n);
                }
                else {
                    a.g.a(a.f.a(), a.c, e, n);
                }
            }
            return false;
        }
        boolean i = false;
        synchronized (this) {
            if (!a.j) {
                a.k = e;
                return false;
            }
            i = a.j;
            a.j = false;
        }
        GLES20.glViewport(0, 0, a.d, a.e);
        if (i) {
            try {
                if (a.h != null) {
                    a.h.updateTexImage();
                    a.h.getTransformMatrix(a.c);
                }
            }
            catch (Exception ex) {}
            if (a.g != null) {
                if (e.y() == 0) {
                    a.g.a(e.x(), a.c, e, n);
                }
                else {
                    a.g.a(a.f.a(), a.c, e, n);
                }
            }
            else if (this.h != null) {
                this.h.a(a.h);
            }
        }
        return true;
    }
    
    public class a
    {
        private int b;
        private float[] c;
        private int d;
        private int e;
        private com.tencent.liteav.renderer.c f;
        private k g;
        private SurfaceTexture h;
        private Surface i;
        private boolean j;
        private e k;
        private SurfaceTexture.OnFrameAvailableListener l;
        
        public a() {
            this.l = (SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
                public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                    n.a.this.j = true;
                    if (n.a.this.k != null) {
                        n.this.b(n.a.this.k, n.a.this.b);
                        n.a.this.k = null;
                    }
                }
            };
        }
    }
}
