package com.tencent.liteav.editer;

import java.util.concurrent.atomic.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;

import android.graphics.*;
import android.os.*;

public class s
{
    private final String a = "PicDec";
    private int b;
    private Handler c;
    private HandlerThread d;
    private List<Bitmap> e;
    private l f;
    private int g;
    private long h;
    private List<Long> i;
    private long j;
    private long k;
    private long l;
    private long m;
    private boolean n;
    private long o;
    private long p;
    private long q;
    private long r;
    private long s;
    private long t;
    private e u;
    private AtomicBoolean v;
    private AtomicBoolean w;
    private long x;
    private int y;
    private int z;
    private int A;
    private int B;
    
    public s(final String s) {
        this.b = 1;
        this.g = 20;
        this.j = 1000L;
        this.k = 500L;
        this.q = -1L;
        this.r = -1L;
        this.s = -1L;
        this.t = -1L;
        this.y = 0;
        this.B = -1;
        (this.d = new HandlerThread("picDec" + s)).start();
        this.c = new a(this.d.getLooper());
        this.e = new ArrayList<Bitmap>();
        this.i = new ArrayList<Long>();
        this.v = new AtomicBoolean(false);
        this.w = new AtomicBoolean(false);
    }
    
    public void a(final boolean n) {
        this.n = n;
    }
    
    public void a(final List<Bitmap> list, final int g) {
        if (list == null || list.size() == 0) {
            TXCLog.e("PicDec", "setBitmapList, bitmapList is empty");
            return;
        }
        if (g <= 0) {
            this.g = 20;
        }
        else {
            this.g = g;
        }
        this.a(list);
        this.h = 1000 / this.g;
    }
    
    public long a(final int b) {
        synchronized (this) {
            if (this.B != b) {
                this.i.clear();
            }
        }
        this.B = b;
        this.j = com.tencent.liteav.j.a.a(b);
        this.k = com.tencent.liteav.j.a.b(b);
        if (b == 5 || b == 4) {
            this.l = this.e.size() * (this.j + this.k);
        }
        else {
            this.l = this.e.size() * (this.j + this.k) - this.k;
        }
        return this.m = this.h * ((int)(this.l / 1000L * this.g) - 1);
    }
    
    public void a(final l f) {
        this.f = f;
    }
    
    private void j() {
        this.q = -1L;
        this.r = -1L;
        this.s = -1L;
        this.t = -1L;
        this.v.compareAndSet(true, false);
    }
    
    public void a(final long o, final long p2) {
        this.o = o;
        this.p = p2;
    }
    
    public int a() {
        if (this.e.size() == 0) {
            return 0;
        }
        return this.z = 720;
    }
    
    public int b() {
        if (this.e.size() == 0) {
            return 0;
        }
        return this.A = 1280;
    }
    
    public boolean c() {
        return this.w.get();
    }
    
    public synchronized void d() {
        if (this.b == 2) {
            TXCLog.e("PicDec", "start(), mState is play, ignore");
            return;
        }
        this.b = 2;
        this.w.compareAndSet(true, false);
        this.j();
        this.c.sendEmptyMessage(1);
    }
    
    public synchronized void e() {
        if (this.b == 1) {
            TXCLog.e("PicDec", "stop(), mState is init, ignore");
            return;
        }
        this.b = 1;
        this.c.sendEmptyMessage(4);
    }
    
    public void f() {
        if (this.b == 1 || this.b == 3) {
            TXCLog.e("PicDec", "pause(), mState = " + this.b + ", ignore");
            return;
        }
        this.b = 3;
        this.c.sendEmptyMessage(3);
    }
    
    public void g() {
        if (this.b == 1 || this.b == 2) {
            TXCLog.e("PicDec", "resume(), mState = " + this.b + ", ignore");
            return;
        }
        this.b = 2;
        this.c.sendEmptyMessage(2);
    }
    
    public void a(final long x) {
        this.b = 4;
        this.x = x;
        this.c.sendEmptyMessage(5);
    }
    
    public synchronized void h() {
        if (this.b == 1) {
            TXCLog.e("PicDec", "getNextBitmapFrame, current state is init, ignore");
            return;
        }
        this.c.sendEmptyMessage(2);
    }
    
    private void k() {
        this.c.removeMessages(2);
        this.j();
    }
    
    private void l() {
        this.c.removeMessages(2);
        this.j();
    }
    
    private void m() {
        if (this.n && this.s >= 0L) {
            if (this.v.get()) {
                this.b(this.u);
            }
            else {
                if (!this.n()) {
                    this.c.sendEmptyMessageDelayed(2, 5L);
                    return;
                }
                this.b(this.u);
            }
        }
        this.u = new e();
        long longValue;
        synchronized (this) {
            if (this.i.size() <= 0) {
                return;
            }
            longValue = this.i.get(this.y);
            if (longValue > this.p * 1000L) {
                longValue = -1L;
            }
        }
        if (longValue == -1L) {
            this.u.c(4);
            this.u.a(0L);
            this.u.j(this.a());
            this.u.k(this.b());
            this.u.m(0);
            this.b(this.u);
            this.c.sendEmptyMessage(4);
            this.w.set(true);
            return;
        }
        synchronized (this) {
            ++this.y;
        }
        this.s = longValue / 1000L;
        this.u.a(longValue);
        this.u.b(longValue);
        this.u.f(this.g);
        this.u.m(0);
        this.a(this.u);
        if (!this.n) {
            this.b(this.u);
            return;
        }
        if (this.q < 0L) {
            this.q = this.s;
            this.v.set(true);
            this.s = longValue;
            this.r = System.currentTimeMillis();
            this.c.sendEmptyMessage(2);
            return;
        }
        this.v.compareAndSet(true, false);
        this.c.sendEmptyMessageDelayed(2, 5L);
    }
    
    private void b(final long n) {
        this.c(n);
        this.u = new e();
        final long longValue;
        synchronized (this) {
            if (this.i.size() <= 0) {
                return;
            }
            longValue = this.i.get(this.y);
        }
        this.u.a(longValue);
        this.u.m(0);
        this.a(this.u);
        this.b(this.u);
    }
    
    private void a(final e e) {
        final long n = e.e() / 1000L;
        final int n2 = (int)(n / (this.j + this.k));
        TXCLog.d("PicDec", "setBitmapsAndDisplayRatio, frameTimeMs = " + n + ", picIndex = " + n2 + ", loopTime = " + (this.j + this.k));
        Bitmap bitmap;
        if (n2 >= this.e.size()) {
            bitmap = this.e.get(this.e.size() - 1);
        }
        else {
            bitmap = this.e.get(n2);
        }
        Bitmap bitmap2 = null;
        if (n2 < this.e.size() - 1) {
            bitmap2 = this.e.get(n2 + 1);
        }
        final ArrayList<Bitmap> list = new ArrayList<Bitmap>();
        list.add(bitmap);
        if (bitmap2 != null) {
            list.add(bitmap2);
        }
        e.a(list);
        e.j(this.a());
        e.k(this.b());
    }
    
    private boolean n() {
        this.t = System.currentTimeMillis();
        this.s = this.u.e() / 1000L;
        return Math.abs(this.s - this.q) < this.t - this.r;
    }
    
    private void b(final e e) {
        if (this.f != null) {
            this.f.a(e);
        }
    }
    
    private synchronized void c(final long n) {
        if (this.i.size() <= 0) {
            return;
        }
        for (int i = 0; i < this.i.size(); ++i) {
            if (this.i.get(i) / 1000L >= n) {
                this.y = i;
                return;
            }
        }
    }
    
    private void a(final List<Bitmap> list) {
        for (int i = 0; i < list.size(); ++i) {
            this.e.add(a(list.get(i), 720, 1280));
        }
    }
    
    public static Bitmap a(final Bitmap bitmap, final int n, final int n2) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        float n3;
        if (width / (float)height >= n / (float)n2) {
            n3 = n / (float)width;
        }
        else {
            n3 = n2 / (float)height;
        }
        final Matrix matrix = new Matrix();
        matrix.postScale(n3, n3);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }
    
    private synchronized void o() {
        if (this.i.size() > 0) {
            return;
        }
        for (int n = (int)(this.l / 1000L * this.g), i = 0; i < n; ++i) {
            if (i == n - 1) {
                this.i.add(i * this.h * 1000L);
                this.i.add(-1L);
            }
            else {
                this.i.add(i * this.h * 1000L);
            }
        }
    }
    
    public void i() {
        for (int i = 0; i < this.e.size(); ++i) {
            this.e.get(i).recycle();
        }
        this.e.clear();
        if (this.d != null) {
            this.d.quit();
        }
    }
    
    class a extends Handler
    {
        public a(final Looper looper) {
            super(looper);
        }
        
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 1: {
                    com.tencent.liteav.editer.s.this.o();
                    com.tencent.liteav.editer.s.this.c(com.tencent.liteav.editer.s.this.o);
                    com.tencent.liteav.editer.s.this.c.sendEmptyMessage(2);
                    break;
                }
                case 2: {
                    com.tencent.liteav.editer.s.this.m();
                    break;
                }
                case 5: {
                    com.tencent.liteav.editer.s.this.b(com.tencent.liteav.editer.s.this.x);
                    break;
                }
                case 3: {
                    com.tencent.liteav.editer.s.this.k();
                    break;
                }
                case 4: {
                    TXCLog.i("PicDec", "stopDecode");
                    com.tencent.liteav.editer.s.this.l();
                    break;
                }
            }
        }
    }
}
