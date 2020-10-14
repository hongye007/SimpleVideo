package com.tencent.liteav;

import javax.microedition.khronos.egl.*;
import java.lang.ref.*;
import android.content.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;

import java.util.concurrent.*;
import com.tencent.liteav.basic.module.*;

public class l implements p, b
{
    private final a a;
    private q b;
    private EGLContext c;
    private WeakReference<com.tencent.liteav.basic.b.b> d;
    private int e;
    private CaptureAndEnc f;
    private int g;
    private int h;
    private String i;
    private int j;
    private long k;
    private long l;
    private long m;
    private boolean n;
    private final Queue<Runnable> o;
    
    public l(final Context context, final i i, final a.a a) {
        this.c = null;
        this.d = null;
        this.i = "";
        this.j = 0;
        this.o = new LinkedList<Runnable>();
        (this.a = new a(context, i.V, a)).a(this);
        i.a();
        this.f = this.c(i.a, i.b);
        this.e = i.h;
        this.g = i.a;
        this.h = i.b;
        TXCLog.i("TXCScreenCaptureSource", "capture size: %s, encode size: %dx%d", this.f, this.g, this.h);
    }
    
    private CaptureAndEnc c(final int n, final int n2) {
        final boolean b = n > n2;
        final CaptureAndEnc d = new CaptureAndEnc();
        if (n > 1280 || n2 > 1280) {
            d.a = (b ? Math.max(n, n2) : Math.min(n, n2));
            d.b = (b ? Math.min(n, n2) : Math.max(n, n2));
        }
        else {
            d.a = (b ? 1280 : 720);
            d.b = (b ? 720 : 1280);
        }
        return d;
    }
    
    @Override
    public void a() {
        Monitor.a(2, String.format("VideoCapture[%d]: start screen", this.hashCode()), "", 0);
        this.k = 0L;
        this.l = 0L;
        this.m = 0L;
        this.n = true;
        this.a.a(this.f.a, this.f.b, this.e);
    }
    
    @Override
    public void a(final boolean b) {
        Monitor.a(2, String.format("VideoCapture[%d]: stop screen", this.hashCode()), "", 0);
        this.a.a((Object)null);
    }
    
    @Override
    public void a(final String i) {
        this.i = i;
    }
    
    @Override
    public void c() {
        this.a.a(false);
    }
    
    @Override
    public void b() {
        this.a.a(true);
    }
    
    @Override
    public void b(final boolean b) {
        final CaptureAndEnc c = this.c(this.g, this.h);
        if (!c.equals(this.f)) {
            this.f = c;
            this.a.a(c.a, c.b);
            TXCLog.i("TXCScreenCaptureSource", "capture size: %s, encode size: %dx%d", this.f, this.g, this.h);
        }
    }
    
    @Override
    public boolean d() {
        return true;
    }
    
    @Override
    public int e() {
        return 0;
    }
    
    @Override
    public EGLContext getEGLContext() {
        return this.c;
    }
    
    @Override
    public boolean a(final int n) {
        return false;
    }
    
    @Override
    public void a(final int n, final int n2) {
    }
    
    @Override
    public void a(final float n) {
    }
    
    @Override
    public void b(final int n) {
    }
    
    @Override
    public void c(final int n) {
    }
    
    @Override
    public void d(final int n) {
    }
    
    @Override
    public void a(final q b) {
        this.b = b;
    }
    
    @Override
    public void c(final boolean b) {
    }
    
    @Override
    public boolean d(final boolean b) {
        return false;
    }
    
    @Override
    public void a(final com.tencent.liteav.basic.structs.b b) {
    }
    
    @Override
    public void a(final Runnable runnable) {
        if (this.a != null) {
            this.a.a(runnable);
        }
    }
    
    @Override
    public void a(final com.tencent.liteav.basic.b.b b) {
        this.d = new WeakReference<com.tencent.liteav.basic.b.b>(b);
        if (this.a != null) {
            this.a.a(b);
        }
    }
    
    @Override
    public void e(final int n) {
    }
    
    @Override
    public void a(final CameraProxy c) {
    }
    
    @Override
    public void b(final int g, final int h) {
        this.g = g;
        this.h = h;
    }
    
    @Override
    public void f(final int e) {
        this.e = e;
        this.a.a(e);
    }
    
    @Override
    public void a(final float n, final float n2) {
    }
    
    @Override
    public int g() {
        return this.e;
    }
    
    @Override
    public boolean h() {
        return false;
    }
    
    @Override
    public boolean i() {
        return false;
    }
    
    @Override
    public boolean j() {
        return false;
    }
    
    @Override
    public boolean k() {
        return false;
    }
    
    @Override
    public boolean l() {
        return false;
    }
    
    @Override
    public void g(final int j) {
        this.j = j;
    }
    
    @Override
    public void e(final boolean b) {
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
    
    @Override
    public void a(final int n, final EGLContext c, final int a, final int e, final int f, final long n2) {
        this.c = c;
        while (this.a(this.o)) {}
        if (n != 0) {
            TXCLog.e("TXCScreenCaptureSource", "onScreenCaptureFrame failed");
            return;
        }
        if (this.n) {
            this.n = false;
            Monitor.a(2, String.format("VideoCapture[%d]: capture first frame", this.hashCode()), "", 0);
            f.a(this.d, 1007, "First frame capture completed");
            TXCLog.i("TXCScreenCaptureSource", "on Got first frame");
        }
        ++this.k;
        final long n3 = System.currentTimeMillis() - this.l;
        if (n3 >= TimeUnit.SECONDS.toMillis(1L)) {
            final double n4 = (this.k - this.m) * 1000.0 / n3;
            this.m = this.k;
            this.l = System.currentTimeMillis();
            TXCStatus.a(this.i, 1001, this.j, n4);
        }
        if (this.b != null) {
            this.f(e < f);
            final com.tencent.liteav.basic.structs.b b = new com.tencent.liteav.basic.structs.b();
            b.e = e;
            b.f = f;
            b.g = this.g;
            b.h = this.h;
            b.a = a;
            b.b = 0;
            b.j = 0;
            b.l = f.a(b.e, b.f, this.g, this.h);
            this.b.b(b);
        }
    }
    
    @Override
    public void a(final Object o) {
        while (this.a(this.o)) {}
        if (this.b != null) {
            this.b.s();
        }
    }
    
    private void f(final boolean b) {
        if (b) {
            if (this.g > this.h) {
                this.b(this.h, this.g);
            }
        }
        else if (this.g < this.h) {
            this.b(this.h, this.g);
        }
    }
}
