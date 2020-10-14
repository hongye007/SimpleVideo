package com.tencent.liteav.videoencoder;

import java.lang.ref.*;

import com.tencent.liteav.basic.log.*;

import java.util.*;
import android.opengl.*;
import android.os.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.module.*;

public class b extends BaseObj
{
    private c a;
    private d b;
    private WeakReference<com.tencent.liteav.basic.b.b> c;
    private int d;
    private int e;
    private int f;
    private Timer g;
    private TimerTask h;
    private LinkedList<Runnable> i;
    private TXSVideoEncoderParam j;
    private float k;
    private float l;
    private float m;
    private int n;
    private int o;
    private com.tencent.liteav.basic.c.b p;
    private g q;
    private static Integer r;
    private boolean s;
    private p t;
    private static final String u;
    private static int v;
    
    public b(final int e) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = 0;
        this.e = 2;
        this.f = 1;
        this.g = null;
        this.h = null;
        this.i = new LinkedList<Runnable>();
        this.k = 0.0f;
        this.l = 0.0f;
        this.m = 0.0f;
        this.n = 0;
        this.o = 0;
        this.e = e;
    }
    
    public int a(final TXSVideoEncoderParam j) {
        this.j = j;
        int start = 10000002;
        int c;
        if (j.enableBlackList) {
            c = com.tencent.liteav.basic.d.b.a().c();
        }
        else {
            c = 2;
        }
        if (this.e == 1 && c != 0) {
            this.a = new com.tencent.liteav.videoencoder.a();
            this.a(1008, "Enables hardware encoding", this.f = 1);
        }
        else if (this.e == 3 && j.width == 720 && j.height == 1280 && c != 0) {
            this.a = new com.tencent.liteav.videoencoder.a();
            this.a(1008, "Enables hardware encoding", this.f = 1);
        }
        else {
            this.a = new TXCSWVideoEncoder();
            this.a(1008, "Enables software encoding", this.f = 2);
        }
        this.setStatusValue(4007, (long)this.f);
        if (this.a != null) {
            if (this.b != null) {
                this.a.setListener(this.b);
            }
            if (this.d != 0) {
                this.a.setBitrate(this.d);
            }
            this.a.setID(this.getID());
            start = this.a.start(j);
            if (start != 0) {
                TXCLog.i(com.tencent.liteav.videoencoder.b.u, "start video encode " + ((this.f == 1) ? "hw" : "sw"));
                return start;
            }
        }
        if (this.e == 3) {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 0;
            this.o = com.tencent.liteav.basic.d.b.a().g();
            this.g();
        }
        return start;
    }
    
    @Override
    public void setID(final String s) {
        super.setID(s);
        if (this.a != null) {
            this.a.setID(s);
        }
        this.setStatusValue(4007, (long)this.f);
    }
    
    public EGLContext a(final int n, final int n2) {
        if (this.s) {
            return (this.p != null) ? this.p.d() : null;
        }
        this.s = true;
        synchronized (com.tencent.liteav.videoencoder.b.r) {
            final StringBuilder append = new StringBuilder().append("CVGLThread");
            final Integer r = com.tencent.liteav.videoencoder.b.r;
            ++com.tencent.liteav.videoencoder.b.r;
            this.q = new g(append.append(r).toString());
        }
        final boolean[] array = { false };
        this.q.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.b.this.p = com.tencent.liteav.basic.c.b.a(null, null, null, n, n2);
                array[0] = (com.tencent.liteav.videoencoder.b.this.p != null);
            }
        });
        if (array[0]) {
            return this.p.d();
        }
        return null;
    }
    
    protected void a(final Runnable runnable) {
        synchronized (this.i) {
            this.i.add(runnable);
        }
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
    
    public long a(final byte[] array, final int n, final int n2, final int n3, final long n4) {
        if (this.p == null) {
            return -1L;
        }
        this.q.b(new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.videoencoder.b.this.t == null || com.tencent.liteav.videoencoder.b.this.t.o() != n2 || com.tencent.liteav.videoencoder.b.this.t.p() != n3) {
                    if (com.tencent.liteav.videoencoder.b.this.t != null) {
                        com.tencent.liteav.videoencoder.b.this.t.e();
                        com.tencent.liteav.videoencoder.b.this.t = null;
                    }
                    com.tencent.liteav.videoencoder.b.this.t = new p(n);
                    if (!com.tencent.liteav.videoencoder.b.this.t.c()) {
                        if (com.tencent.liteav.videoencoder.b.this.p != null) {
                            com.tencent.liteav.videoencoder.b.this.p.c();
                            com.tencent.liteav.videoencoder.b.this.p = null;
                        }
                        com.tencent.liteav.videoencoder.b.this.t = null;
                        return;
                    }
                    com.tencent.liteav.videoencoder.b.this.t.a(true);
                    com.tencent.liteav.videoencoder.b.this.t.a(n2, n3);
                }
                com.tencent.liteav.videoencoder.b.this.t.a(array);
                GLES20.glViewport(0, 0, n2, n3);
                final int r = com.tencent.liteav.videoencoder.b.this.t.r();
                GLES20.glFlush();
                com.tencent.liteav.videoencoder.b.this.a(r, n2, n3, n4);
            }
        });
        return 0L;
    }
    
    public void a() {
        if (this.q != null) {
            this.q.b(new Runnable() {
                final /* synthetic */ com.tencent.liteav.basic.c.b a = com.tencent.liteav.videoencoder.b.this.p;
                
                @Override
                public void run() {
                    com.tencent.liteav.videoencoder.b.this.i.clear();
                    if (com.tencent.liteav.videoencoder.b.this.a != null) {
                        com.tencent.liteav.videoencoder.b.this.a.stop();
                    }
                    if (com.tencent.liteav.videoencoder.b.this.t != null) {
                        com.tencent.liteav.videoencoder.b.this.t.e();
                        com.tencent.liteav.videoencoder.b.this.t = null;
                    }
                    if (this.a != null) {
                        this.a.c();
                    }
                }
            });
            this.q = null;
            this.p = null;
        }
        else {
            this.i.clear();
            if (this.a != null) {
                this.a.stop();
            }
        }
        if (this.e == 3) {
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = 0.0f;
            this.n = 0;
            this.h();
        }
        this.b = null;
        this.d = 0;
    }
    
    public void a(final int rotation) {
        if (this.a != null) {
            this.a.setRotation(rotation);
        }
    }
    
    public void a(final boolean xMirror) {
        if (this.a != null) {
            this.a.setXMirror(xMirror);
        }
    }
    
    public long a(final int n, final int n2, final int n3, final long n4) {
        long pushVideoFrame = 10000002L;
        while (this.a(this.i)) {}
        if (this.a != null) {
            this.setStatusValue(4002, this.e());
            this.setStatusValue(4001, this.j.streamType, this.d());
            if (this.f == 1) {
                this.setStatusValue(8002, this.j.streamType, this.f());
            }
            pushVideoFrame = this.a.pushVideoFrame(n, n2, n3, n4);
        }
        return pushVideoFrame;
    }
    
    public long b(final int n, final int n2, final int n3, final long n4) {
        long pushVideoFrameAsync = 10000002L;
        while (this.a(this.i)) {}
        if (this.a != null) {
            this.setStatusValue(4002, this.e());
            this.setStatusValue(4001, this.j.streamType, this.d());
            if (this.f == 1) {
                this.setStatusValue(8002, this.j.streamType, this.f());
            }
            pushVideoFrameAsync = this.a.pushVideoFrameAsync(n, n2, n3, n4);
        }
        return pushVideoFrameAsync;
    }
    
    public long c(final int n, final int n2, final int n3, final long n4) {
        long pushVideoFrameSync = 10000002L;
        while (this.a(this.i)) {}
        if (this.a != null) {
            this.setStatusValue(4002, this.e());
            this.setStatusValue(4001, this.j.streamType, this.d());
            pushVideoFrameSync = this.a.pushVideoFrameSync(n, n2, n3, n4);
        }
        return pushVideoFrameSync;
    }
    
    public boolean b(final int fps) {
        if (this.a != null) {
            this.a.setFPS(fps);
            return true;
        }
        return false;
    }
    
    public void b() {
        if (this.a != null) {
            this.a.restartIDR();
        }
    }
    
    public void c() {
        if (this.a != null) {
            this.a.signalEOSAndFlush();
        }
    }
    
    public void a(final com.tencent.liteav.basic.b.b b) {
        this.c = new WeakReference<com.tencent.liteav.basic.b.b>(b);
    }
    
    public void a(final d b) {
        this.b = b;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.videoencoder.b.this.a != null) {
                    com.tencent.liteav.videoencoder.b.this.a.setListener(com.tencent.liteav.videoencoder.b.this.b);
                }
            }
        });
    }
    
    public void c(final int d) {
        this.d = d;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.videoencoder.b.this.a != null) {
                    com.tencent.liteav.videoencoder.b.this.a.setBitrate(com.tencent.liteav.videoencoder.b.this.d);
                }
            }
        });
    }
    
    public boolean b(final int d, final int n) {
        if (this.a != null) {
            this.d = d;
            this.a.setBitrateFromQos(d, n);
            return true;
        }
        return false;
    }
    
    public boolean d(final int encodeIdrFpsFromQos) {
        if (this.a != null) {
            this.a.setEncodeIdrFpsFromQos(encodeIdrFpsFromQos);
            return true;
        }
        return false;
    }
    
    public void e(final int n) {
        this.a(new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.videoencoder.b.this.a != null) {
                    com.tencent.liteav.videoencoder.b.this.a.enableNearestRPS(n);
                }
            }
        });
    }
    
    public double d() {
        double realFPS = 0.0;
        if (this.a != null) {
            realFPS = this.a.getRealFPS();
        }
        return realFPS;
    }
    
    public long e() {
        long realBitrate = 0L;
        if (this.a != null) {
            realBitrate = this.a.getRealBitrate();
        }
        return realBitrate;
    }
    
    public int f() {
        int encodeCost = 0;
        if (this.a != null) {
            encodeCost = this.a.getEncodeCost();
        }
        return encodeCost;
    }
    
    private void g() {
        if (this.h == null) {
            this.h = new a(this);
        }
        (this.g = new Timer()).schedule(this.h, 1000L, 1000L);
    }
    
    private void h() {
        if (this.g != null) {
            this.g.cancel();
            this.g = null;
        }
        if (this.h != null) {
            this.h = null;
        }
    }
    
    private void a(final int n, final String s) {
        if (this.c == null) {
            return;
        }
        final com.tencent.liteav.basic.b.b b = this.c.get();
        if (b == null) {
            return;
        }
        final Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", n);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putCharSequence("EVT_MSG", (CharSequence)s);
        b.onNotifyEvent(n, bundle);
    }
    
    private void a(final int n, final String s, final int n2) {
        if (this.c == null) {
            return;
        }
        final com.tencent.liteav.basic.b.b b = this.c.get();
        if (b == null) {
            return;
        }
        final Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", n);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putCharSequence("EVT_MSG", (CharSequence)s);
        bundle.putInt("EVT_PARAM1", n2);
        b.onNotifyEvent(n, bundle);
    }
    
    private void i() {
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.b.this.a(1107, "Switches from software encoding to hardware encoding");
                if (com.tencent.liteav.videoencoder.b.this.a != null) {
                    com.tencent.liteav.videoencoder.b.this.a.setListener(null);
                    com.tencent.liteav.videoencoder.b.this.a.stop();
                }
                com.tencent.liteav.videoencoder.b.this.a = new com.tencent.liteav.videoencoder.a();
                com.tencent.liteav.videoencoder.b.this.f = 1;
                com.tencent.liteav.videoencoder.b.this.setStatusValue(4007, (long)com.tencent.liteav.videoencoder.b.this.f);
                com.tencent.liteav.videoencoder.b.this.a.start(com.tencent.liteav.videoencoder.b.this.j);
                if (com.tencent.liteav.videoencoder.b.this.b != null) {
                    com.tencent.liteav.videoencoder.b.this.a.setListener(com.tencent.liteav.videoencoder.b.this.b);
                }
                if (com.tencent.liteav.videoencoder.b.this.d != 0) {
                    com.tencent.liteav.videoencoder.b.this.a.setBitrate(com.tencent.liteav.videoencoder.b.this.d);
                }
                com.tencent.liteav.videoencoder.b.this.a.setID(com.tencent.liteav.videoencoder.b.this.getID());
            }
        });
        TXCLog.w("TXCVideoEncoder", "switchSWToHW");
    }
    
    static {
        b.r = 1;
        u = b.class.getSimpleName();
        b.v = 0;
    }
    
    static class a extends TimerTask
    {
        private WeakReference<b> a;
        
        public a(final b b) {
            this.a = new WeakReference<b>(b);
        }
        
        @Override
        public void run() {
            if (this.a == null) {
                return;
            }
            final b b = this.a.get();
            if (b == null) {
                return;
            }
            if (b.n < b.o) {
                final int[] a = com.tencent.liteav.basic.util.f.a();
                ++b.n;
                final b b2 = b;
                b2.k += a[0] / 10;
                final b b3 = b;
                b3.l += a[1] / 10;
                final b b4 = b;
                b4.m += b.d() * 100.0 / b.j.fps;
                return;
            }
            if (com.tencent.liteav.basic.d.b.a().a(b.k / b.o, b.l / b.o, b.m / b.o) && com.tencent.liteav.basic.d.b.a().c() != 0) {
                Monitor.a(2, "VideoEncoder: Insufficient performance, switching software encoding to hardware encoding [appCPU:" + b.k + "][sysCPU:" + b.l + "][fps:" + b.m + "][checkCount:" + b.o + "]", "", 0);
                b.i();
            }
            b.h();
        }
    }
}
