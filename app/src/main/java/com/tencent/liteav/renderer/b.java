package com.tencent.liteav.renderer;

import java.lang.ref.*;
import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.log.*;
import android.view.*;
import javax.microedition.khronos.egl.*;
import android.graphics.*;

class b extends Thread
{
    private WeakReference<a> a;
    private boolean b;
    private int c;
    private int d;
    private Object e;
    private c f;
    private com.tencent.liteav.basic.c.b g;
    private Object h;
    
    b(final WeakReference<a> a) {
        this.b = false;
        this.c = 1280;
        this.d = 720;
        this.e = new Object();
        this.f = null;
        this.g = null;
        this.h = null;
        this.a = a;
    }
    
    @Override
    public void run() {
        this.setName("VRender-" + this.getId());
        try {
            this.b = true;
            this.k();
            this.f();
            this.i();
            while (this.b) {
                if (this.h()) {
                    final a a = (this.a == null) ? null : this.a.get();
                    if (a != null && a.d() != null) {
                        this.d();
                    }
                }
                synchronized (this.e) {
                    try {
                        this.e.wait();
                    }
                    catch (InterruptedException ex2) {}
                }
            }
            this.j();
            this.g();
            this.l();
        }
        catch (Exception ex) {
            TXCLog.e("TXCVideoRenderThread", "render failed.", ex);
        }
    }
    
    public void a(final Object h) {
        this.h = h;
    }
    
    public Object a() {
        if (this.g != null) {
            return this.g.d();
        }
        if (this.f != null) {
            return this.f.e();
        }
        return null;
    }
    
    public void b() {
        this.b = false;
        this.c();
    }
    
    public void c() {
        synchronized (this.e) {
            this.e.notifyAll();
        }
    }
    
    public void d() {
        if (this.g != null) {
            this.g.a();
        }
        if (this.f != null) {
            this.f.d();
        }
    }
    
    public void e() {
        if (this.g != null) {
            this.g.b();
        }
        if (this.f != null) {
            this.f.b();
        }
    }
    
    private void f() {
        try {
            final a a = this.a.get();
            if (a != null) {
                a.a(this);
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCVideoRenderThread", "init texture render failed.", ex);
        }
    }
    
    private void g() {
        try {
            final a a = this.a.get();
            if (a != null) {
                a.b(this);
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCVideoRenderThread", "destroy texture render failed", ex);
        }
    }
    
    private boolean h() {
        try {
            if (this.a != null) {
                final a a = this.a.get();
                if (a != null) {
                    return a.c();
                }
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCVideoRenderThread", "drawFrame failed." + ex.getMessage());
        }
        return false;
    }
    
    private void i() {
        if (this.a != null) {
            final a a = this.a.get();
            if (a != null) {
                a.k();
            }
        }
    }
    
    private void j() {
        if (this.a != null) {
            final a a = this.a.get();
            if (a != null) {
                a.l();
            }
        }
    }
    
    private void k() {
        final a a = this.a.get();
        if (a == null) {
            return;
        }
        final SurfaceTexture d = a.d();
        Surface surface = null;
        if (d != null) {
            surface = new Surface(d);
        }
        if (this.h == null || this.h instanceof EGLContext) {
            this.g = com.tencent.liteav.basic.c.b.a(null, (EGLContext)this.h, surface, this.c, this.d);
        }
        else {
            this.f = com.tencent.liteav.basic.c.c.a(null, (android.opengl.EGLContext)this.h, surface, this.c, this.d);
        }
        TXCLog.w("TXCVideoRenderThread", "vrender: init egl share context " + this.h + ", create context" + this.a());
        this.e();
    }
    
    private void l() {
        TXCLog.w("TXCVideoRenderThread", "vrender: uninit egl " + this.a());
        if (this.g != null) {
            this.g.c();
            this.g = null;
        }
        if (this.f != null) {
            this.f.c();
            this.f = null;
        }
    }
}
