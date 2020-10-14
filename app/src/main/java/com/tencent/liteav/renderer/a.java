package com.tencent.liteav.renderer;

import android.graphics.*;
import com.tencent.liteav.basic.structs.*;
import java.util.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;
import java.lang.ref.*;

public class a extends f implements SurfaceTexture.OnFrameAvailableListener
{
    private final int q = 0;
    private final int r = 0;
    private final int s = 0;
    private final int t = 0;
    private Object u;
    private b v;
    private SurfaceTexture w;
    private c x;
    private boolean y;
    private float[] z;
    private c A;
    private TXSVideoFrame B;
    private TXCYuvTextureRender C;
    private Object D;
    private Object E;
    private i F;
    private TXCYuvTextureRender G;
    h a;
    a b;
    a c;
    private final Queue<Runnable> H;
    
    public a() {
        this.u = new Object();
        this.D = null;
        this.E = new Object();
        this.H = new LinkedList<Runnable>();
        this.z = new float[16];
    }
    
    public void a(final h a) {
        this.a = a;
    }
    
    public void a(final a b) {
        this.b = b;
        if (b != null && this.C != null) {
            this.C.setHasFrameBuffer(this.h, this.i);
        }
    }
    
    public void b(final a c) {
        this.c = c;
        if (c != null && this.C != null) {
            this.C.setHasFrameBuffer(this.h, this.i);
        }
    }
    
    @Override
    public void a(final TXSVideoFrame b, final int n, final int n2, final int n3) {
        synchronized (this) {
            if (this.B != null) {
                this.B.release();
            }
            this.B = b;
        }
        super.a(b, n, n2, n3);
        synchronized (this.u) {
            if (this.v != null) {
                this.v.c();
            }
        }
    }
    
    @Override
    public void a(final int n, final int n2, final int n3, final boolean b, final int n4) {
        GLES20.glViewport(0, 0, this.g(), this.h());
        if (this.A != null) {
            this.A.a(n, b, n4);
        }
        super.a(n, n2, n3, b, n4);
        synchronized (this.u) {
            if (this.v != null) {
                this.v.c();
            }
        }
    }
    
    @Override
    public SurfaceTexture a() {
        return this.w;
    }
    
    public Object b() {
        synchronized (this.u) {
            return (this.v != null) ? this.v.a() : null;
        }
    }
    
    @Override
    protected void a(final SurfaceTexture surfaceTexture) {
        super.a(surfaceTexture);
        TXCLog.w("TXCVideoRender", "play:vrender: create render thread when onSurfaceCreate");
        this.c(null);
    }
    
    @Override
    protected void b(final SurfaceTexture surfaceTexture) {
        super.b(surfaceTexture);
        TXCLog.w("TXCVideoRender", "play:vrender: quit render thread when onSurfaceRelease");
        this.e();
    }
    
    @Override
    protected void a(final int n, final int n2) {
        super.a(n, n2);
        if (this.C != null) {
            this.C.setVideoSize(n, n2);
        }
        if (this.x != null) {
            this.x.a(n, n2);
        }
    }
    
    void a(final Object d) {
        synchronized (this.E) {
            this.D = d;
            TXCLog.w("TXCVideoRender", "play:vrender: TXCGLRender initTextureRender " + this);
            this.p();
            if (this.e != null) {
                this.e.a(this.f, this.g);
                this.e.b(this.h, this.i);
            }
            if (this.x != null) {
                this.x.b();
                (this.w = new SurfaceTexture(this.x.a())).setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)this);
            }
            if (this.C != null) {
                this.C.createTexture();
            }
            if (this.b != null && this.C != null) {
                this.C.setHasFrameBuffer(this.h, this.i);
            }
            if (this.A != null) {
                this.A.b();
            }
            if (this.o != null) {
                this.o.onSurfaceTextureAvailable(this.w);
            }
        }
    }
    
    private void p() {
        this.x = new c(true);
        this.C = new TXCYuvTextureRender();
        this.A = new c(false);
    }
    
    void b(final Object o) {
        synchronized (this.E) {
            if (this.D != o) {
                TXCLog.w("TXCVideoRender", "play:vrender: TXCGLRender destroyTextureRender ignore when not the same gl thread " + this);
                return;
            }
            this.D = null;
            TXCLog.w("TXCVideoRender", "play:vrender: TXCGLRender destroyTextureRender " + this);
            try {
                if (this.o != null) {
                    this.o.onSurfaceTextureDestroy(this.w);
                }
            }
            catch (Exception ex) {
                TXCLog.e("TXCVideoRender", "callback failed.", ex);
            }
            if (this.x != null) {
                this.x.c();
                this.x = null;
            }
            if (this.C != null) {
                this.C.onSurfaceDestroy();
                this.C = null;
            }
            if (this.A != null) {
                this.A.c();
                this.A = null;
            }
            this.w = null;
            if (this.F != null) {
                this.F.c();
                this.F = null;
            }
            if (this.G != null) {
                this.G.onSurfaceDestroy();
                this.G = null;
            }
        }
    }
    
    boolean c() {
        while (this.a(this.H)) {}
        return this.q();
    }
    
    SurfaceTexture d() {
        return (this.d != null) ? this.d.getSurfaceTexture() : null;
    }
    
    @Override
    public void onSurfaceTextureUpdated(final SurfaceTexture surfaceTexture) {
    }
    
    @Override
    public void c(final Object o) {
        synchronized (this.u) {
            if (this.v == null) {
                (this.v = new b(new WeakReference<a>(this))).a(o);
                this.v.start();
                this.v.c();
                TXCLog.w("TXCVideoRender", "play:vrender: start render thread id " + this.getID() + ", glContext " + o + ", " + this);
            }
            else {
                TXCLog.w("TXCVideoRender", "play:vrender: start render thread when running " + this.getID() + ", " + this);
            }
        }
    }
    
    @Override
    public void e() {
        synchronized (this.u) {
            if (this.v != null) {
                this.v.b();
                this.v.c();
                this.v = null;
                TXCLog.w("TXCVideoRender", "play:vrender: quit render thread id" + this.getID() + ", " + this);
            }
        }
    }
    
    private boolean q() {
        boolean y = false;
        TXSVideoFrame b = null;
        synchronized (this) {
            if (this.y) {
                y = this.y;
                this.y = false;
            }
            else {
                if (this.B == null) {
                    return false;
                }
                b = this.B;
                this.B = null;
            }
        }
        GLES20.glViewport(0, 0, this.g(), this.h());
        Object b2 = null;
        if (this.l == 1) {
            b2 = this.b();
        }
        final a c = this.c;
        if (y) {
            if (this.w != null) {
                this.w.updateTexImage();
                this.w.getTransformMatrix(this.z);
            }
            if (this.a != null) {
                if (this.x != null) {
                    this.a.a(this.x.a(), this.z);
                }
            }
            else if (this.x != null) {
                GLES20.glBindFramebuffer(36160, 0);
                this.x.a(this.w);
            }
            if (c != null) {
                final int a = this.x.a();
                if (this.F == null) {
                    (this.F = new i(true)).b();
                    this.F.a(true);
                    this.F.b(180);
                    this.F.a(com.tencent.liteav.renderer.i.a);
                }
                this.F.a(this.z);
                this.F.b(this.h, this.i);
                this.F.a(this.h, this.i);
                c.onTextureProcess(this.F.d(a), this.i(), this.j(), this.k);
            }
            if (this.l == 1 && this.x != null) {
                this.a(b2, this.x.a(), this.z, true);
            }
        }
        else if (b != null && this.C != null) {
            int n = -1;
            if (this.b != null) {
                this.C.setHasFrameBuffer(this.h, this.i);
                n = this.C.drawToTexture(b);
                this.b.onTextureProcess(n, this.i(), this.j(), this.k);
            }
            else if (this.l == 0) {
                GLES20.glBindFramebuffer(36160, 0);
                this.C.drawFrame(b);
            }
            if (this.l == 1) {
                if (n == -1) {
                    this.C.setHasFrameBuffer(this.h, this.i);
                    n = this.C.drawToTexture(b);
                }
                this.a(b2, n, null, false);
            }
            if (c != null) {
                if (this.G == null) {
                    (this.G = new TXCYuvTextureRender()).createTexture();
                    this.G.flipVertical(false);
                }
                this.G.setHasFrameBuffer(this.h, this.i);
                c.onTextureProcess(this.G.drawToTexture(b), this.i(), this.j(), this.k);
            }
        }
        return true;
    }
    
    private boolean a(final Queue<Runnable> queue) {
        final Runnable runnable;
        synchronized (queue) {
            if (queue.isEmpty()) {
                return false;
            }
            runnable = queue.poll();
        }
        if (runnable == null) {
            return false;
        }
        runnable.run();
        return true;
    }
    
    public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
        synchronized (this) {
            this.y = true;
        }
        synchronized (this.u) {
            if (this.v != null) {
                this.v.c();
            }
        }
    }
    
    public void finalize() throws Throwable {
        super.finalize();
        TXCLog.w("TXCVideoRender", "play:vrender: quit render thread when finalize");
        try {
            this.e();
        }
        catch (Exception ex) {
            TXCLog.e("TXCVideoRender", "quit render thread failed.", ex);
        }
    }
    
    public interface a
    {
        void onTextureProcess(final int p0, final int p1, final int p2, final int p3);
    }
}
