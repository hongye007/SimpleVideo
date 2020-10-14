package com.tencent.liteav.editer;

import android.content.*;
import android.view.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.basic.log.*;

import android.os.*;
import java.io.*;
import java.util.*;
import android.graphics.*;
import android.media.*;

public class ac
{
    private final String a = "VideoEditerPreview";
    private Context b;
    private aa c;
    private ae d;
    private b e;
    private k f;
    private i g;
    private com.tencent.liteav.f.k h;
    private com.tencent.liteav.f.b i;
    private Surface j;
    private boolean k;
    private com.tencent.liteav.i.b.d l;
    private com.tencent.liteav.i.b.b m;
    private s n;
    private f o;
    private o p;
    private n q;
    private j r;
    private m s;
    private h t;
    private l u;
    private Handler v;
    private b.a w;
    
    public ac(final Context b) {
        this.p = new o() {
            @Override
            public void a(final Surface surface) {
                TXCLog.i("VideoEditerPreview", "onSurfaceTextureAvailable surface:" + surface + ", mNeedPlay = " + ac.this.k);
                if (ac.this.h != null) {
                    ac.this.h.a();
                    ac.this.h.b();
                    ac.this.h.a(ac.this.q);
                }
                synchronized (ac.this) {
                    ac.this.j = surface;
                    if (ac.this.k) {
                        ac.this.h();
                    }
                }
            }
            
            @Override
            public void a(final int a, final int b) {
                if (ac.this.h != null) {
                    final g g = new g();
                    g.a = a;
                    g.b = b;
                    ac.this.h.a(g);
                }
            }
            
            @Override
            public void b(final Surface surface) {
                TXCLog.i("VideoEditerPreview", "onSurfaceTextureDestroy surface:" + surface);
                synchronized (ac.this) {
                    if (ac.this.j == surface) {
                        ac.this.j = null;
                    }
                }
                if (ac.this.h != null) {
                    ac.this.h.c();
                    ac.this.h.d();
                    ac.this.h.a((n)null);
                }
                if (ac.this.o != null) {
                    ac.this.o.a();
                }
            }
            
            @Override
            public int a(int a, final float[] array, final e e) {
                if (e.p()) {
                    ac.this.j();
                    return 0;
                }
                if (ac.this.o != null) {
                    a = ac.this.o.a(e, CameraProxy.e.a().b(), false);
                    e.l(a);
                    e.m(0);
                }
                if (ac.this.h != null) {
                    ac.this.h.a(array);
                    ac.this.h.a(a, e);
                    ac.this.c(e.e());
                }
                return 0;
            }
            
            @Override
            public void a(final int n) {
                if (ac.this.h != null) {
                    ac.this.h.a(n);
                }
            }
        };
        this.q = new n() {
            @Override
            public void a(final int n, final e e) {
                com.tencent.liteav.j.b.c();
                if (ac.this.d != null) {
                    ac.this.d.a(n, ac.this.d.a(), ac.this.d.b());
                }
            }
            
            @Override
            public int b(final int n, final e e) {
                return ac.this.a(n, e.m(), e.n(), e.e());
            }
        };
        this.r = new j() {
            @Override
            public void a(final e e) {
                if (e == null || e.b() == null) {
                    return;
                }
                com.tencent.liteav.j.b.d();
                if (e.p() && ((CameraProxy.k.a().d() == 2 && ac.this.n.c()) || (CameraProxy.k.a().d() == 1 && ac.this.c.q()))) {
                    ac.this.j();
                    return;
                }
                if (ac.this.e != null) {
                    ac.this.e.a(e);
                }
                synchronized (ac.this) {
                    if (ac.this.i != null) {
                        ac.this.i.i();
                    }
                }
            }
        };
        this.s = new m() {
            @Override
            public void a(final e e) {
                com.tencent.liteav.j.b.a();
                if (ac.this.d != null) {
                    ac.this.d.a(e);
                }
            }
            
            @Override
            public void a(final String s) {
                ac.this.v.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        TXCLog.e("VideoEditerPreview", "onDecodeVideoError ->  msg = " + s);
                        final a.f f = new a.f();
                        f.a = -1;
                        f.b = "decode video frame fail.";
                        if (ac.this.l != null) {
                            ac.this.l.a(f);
                        }
                        TXCLog.e("VideoEditerPreview", "onDecodeVideoError -> onPreviewError: decode video fail.");
                    }
                });
            }
        };
        this.t = new h() {
            @Override
            public void a(final e e) {
                com.tencent.liteav.j.b.b();
                synchronized (ac.this) {
                    if (ac.this.i != null) {
                        ac.this.i.a(e);
                    }
                }
            }
        };
        this.u = new l() {
            @Override
            public void a(final e e) {
                if (ac.this.d != null) {
                    ac.this.d.b(e);
                }
            }
        };
        this.v = new Handler(Looper.getMainLooper());
        this.w = new b.a() {
            @Override
            public void a(final int n) {
                if (CameraProxy.k.a().d() == 1) {
                    if (ac.this.c != null && ac.this.c.h()) {
                        ac.this.c.a(n <= 5);
                    }
                }
                else {
                    synchronized (ac.this) {
                        if (ac.this.i != null) {
                            ac.this.i.c(n <= 5);
                        }
                    }
                }
            }
        };
        this.b = b;
        (this.d = new ae(b)).a(this.p);
        this.e = new b();
        this.g = CameraProxy.i.a();
        this.h = new com.tencent.liteav.f.k(b);
        this.f = CameraProxy.k.a();
    }
    
    public void a(final String s) {
        TXCLog.i("VideoEditerPreview", "setVideoPath videoPath:" + s);
        if (this.c == null) {
            this.c = new aa();
        }
        try {
            this.c.a(s);
            if (this.c.h()) {
                this.g.a(this.c.f());
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void a(final List<Bitmap> list, final int n) {
        (this.n = new s("preview")).a(true);
        this.n.a(list, n);
        this.o = new f(this.b, this.n.a(), this.n.b());
    }
    
    public long a(final int n) {
        CameraProxy.e.a().a(n);
        long a = 0L;
        if (this.n != null) {
            a = this.n.a(n);
        }
        return a;
    }
    
    public synchronized void b(final String s) {
        if (this.i == null) {
            (this.i = new com.tencent.liteav.f.b("preview")).a();
        }
        this.i.a(s);
        this.g.c(this.i.h());
        this.i.a(this.g.n());
        boolean b = false;
        if (CameraProxy.k.a().d() == 1) {
            if (this.c != null) {
                b = this.c.h();
            }
            else {
                b = this.g.l();
            }
        }
        if (!b) {
            this.i.b(b);
            this.i.c();
        }
    }
    
    public synchronized void a(final long n, final long n2) {
        TXCLog.i("VideoEditerPreview", "setBGMStartTime startTime:" + n + ",endTime:" + n2);
        if (this.i != null) {
            this.i.a(n, n2);
        }
    }
    
    public synchronized void a(final float n) {
        TXCLog.i("VideoEditerPreview", "setVideoVolume volume:" + n);
        if (this.i != null) {
            this.i.a(n);
        }
    }
    
    public synchronized void b(final float n) {
        TXCLog.i("VideoEditerPreview", "setBGMVolume volume:" + n);
        if (this.i != null) {
            this.i.b(n);
        }
    }
    
    public synchronized void a(final boolean b) {
        TXCLog.i("VideoEditerPreview", "setBGMLoop looping:" + b);
        if (this.i != null) {
            this.i.a(b);
        }
    }
    
    public synchronized void a(final long n) {
        TXCLog.i("VideoEditerPreview", "setBGMAtVideoTime videoStartTime:" + n);
        if (this.i != null) {
            this.i.a(n);
        }
    }
    
    public void a() {
        if (this.d != null) {
            this.d.a(1);
        }
    }
    
    public void b() {
        if (this.f.d() == 1 && this.d != null) {
            this.d.a(2);
        }
    }
    
    public void b(final long n, final long n2) {
        if (this.f.d() == 1 && this.c != null) {
            this.c.a(n * 1000L, n2 * 1000L);
        }
        else if (this.f.d() == 2 && this.n != null) {
            this.n.a(n, n2);
        }
    }
    
    public void c(final long n, final long n2) {
        if (this.f.d() == 2) {
            TXCLog.e("VideoEditerPreview", "setRepeateFromTimeToTime, source is picture, not support yet!");
            return;
        }
        if (this.c != null) {
            this.c.b(n, n2);
        }
    }
    
    public void a(final com.tencent.liteav.i.b.d l) {
        this.l = l;
    }
    
    public void a(final com.tencent.liteav.i.b.b m) {
        this.m = m;
    }
    
    public void a(final a.g g) {
        synchronized (this) {
            this.j = null;
        }
        if (this.f.d() == 1) {
            this.a(this.f.a);
            if (CameraProxy.k.a().e() != 0) {
                TXCLog.e("VideoEditerPreview", "initWithPreview Video Source illegal : " + this.f.a);
                return;
            }
        }
        if (this.d != null) {
            this.d.a(g);
        }
    }
    
    public void b(final long n) {
        if (this.f.d() == 1 && this.c != null) {
            this.c.a(this.j);
            this.c.a(this.s);
            this.c.a(n);
        }
        else if (this.f.d() == 2 && this.n != null) {
            this.n.a(n);
        }
    }
    
    public void c() {
        synchronized (this) {
            this.k = true;
            TXCLog.i("VideoEditerPreview", "startPlay mNeedPlay true, mSurface:" + this.j);
            if (this.j != null) {
                this.h();
            }
        }
    }
    
    public void d() {
        synchronized (this) {
            this.k = false;
        }
        TXCLog.i("VideoEditerPreview", "stopPlay mNeedPlay false");
        if (this.f.d() == 1 && this.c != null) {
            this.c.a((m)null);
            this.c.a((h)null);
            this.c.m();
        }
        else if (this.f.d() == 2 && this.n != null) {
            this.n.e();
            this.n.a((l)null);
        }
        if (this.e != null) {
            this.e.a((b.a)null);
            this.e.d();
        }
        synchronized (this) {
            if (this.i != null) {
                this.i.d();
                this.i.a((j)null);
                this.i.b();
                this.i = null;
            }
        }
        if (this.d != null) {
            this.d.d();
        }
    }
    
    public void e() {
        synchronized (this) {
            this.k = true;
        }
        if (this.d != null) {
            this.d.a(0);
        }
        synchronized (this) {
            if (this.j == null) {
                TXCLog.i("VideoEditerPreview", "resumePlay, mSurface is null, ignore!");
                return;
            }
        }
        if (this.f.d() == 1 && this.c != null) {
            this.c.o();
        }
        else if (this.f.d() == 2 && this.n != null) {
            this.n.g();
        }
        if (this.e != null) {
            this.e.b();
        }
        if (this.c != null && !this.c.h()) {
            synchronized (this) {
                if (this.i != null) {
                    this.i.g();
                }
            }
        }
    }
    
    public void f() {
        synchronized (this) {
            this.k = false;
        }
        if (this.f.d() == 1 && this.c != null) {
            this.c.n();
        }
        else if (this.f.d() == 2 && this.n != null) {
            this.n.f();
        }
        if (this.e != null) {
            this.e.a();
        }
        if (this.c != null && !this.c.h()) {
            synchronized (this) {
                if (this.i != null) {
                    this.i.f();
                }
            }
        }
    }
    
    public void g() {
        TXCLog.i("VideoEditerPreview", "release");
        if (this.c != null) {
            this.c.k();
        }
        if (this.n != null) {
            this.n.i();
        }
        if (this.d != null) {
            this.d.a((o)null);
            this.d.e();
        }
        this.d = null;
        this.h = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.w = null;
        this.j = null;
    }
    
    private void h() {
        TXCLog.i("VideoEditerPreview", "startPlayInternal");
        if (this.i == null) {
            (this.i = new com.tencent.liteav.f.b("preview")).a();
        }
        this.i.a(this.r);
        this.i.b(this.i());
        if (this.g.l()) {
            final MediaFormat n = this.g.n();
            this.i.a(n);
            if (this.f.d() == 1) {
                if (this.c != null) {
                    this.i.b(this.c.h());
                }
            }
            else {
                this.i.b(false);
            }
            this.i.c();
            this.i.e();
            this.e.a(n);
        }
        final g g = new g();
        if (this.d != null) {
            g.a = this.d.a();
            g.b = this.d.b();
        }
        this.h.a(g);
        if (this.f.d() == 1 && this.c != null) {
            this.c.a(this.j);
            this.c.a(this.s);
            this.c.a(this.t);
            this.c.l();
        }
        else if (this.f.d() == 2 && this.n != null) {
            this.n.a(this.u);
            this.n.d();
        }
        if (this.g.l()) {
            this.e.a(this.w);
            this.e.c();
        }
        if (this.d != null) {
            this.d.a(0);
            this.d.c();
        }
        com.tencent.liteav.j.b.h();
    }
    
    private long i() {
        final c a = CameraProxy.c.a();
        long b = a.e() - a.d();
        TXCLog.i("VideoEditerPreview", "calculatePlayDuration playDurationUs : " + b);
        if (com.tencent.liteav.f.g.a().c()) {
            b = com.tencent.liteav.f.g.a().b(b);
            TXCLog.i("VideoEditerPreview", "calculatePlayDuration after Speed playDurationUs : " + b);
        }
        return b;
    }
    
    private int a(final int n, final int n2, final int n3, final long n4) {
        if (this.m != null) {
            return this.m.a(n, n2, n3, n4);
        }
        return n;
    }
    
    private void j() {
        this.v.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ac.this.l != null) {
                    ac.this.l.a();
                }
            }
        });
    }
    
    private void c(final long n) {
        this.v.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (ac.this.l != null) {
                    ac.this.l.a((int)n);
                }
            }
        });
    }
}
