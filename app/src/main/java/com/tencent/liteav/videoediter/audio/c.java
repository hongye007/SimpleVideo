package com.tencent.liteav.videoediter.audio;

import android.annotation.*;
import android.media.*;

import com.tencent.liteav.CaptureAndEnc;

import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;
import android.os.*;
import android.text.*;
import java.io.*;
import com.tencent.liteav.basic.log.*;

import java.util.*;

@TargetApi(18)
public class c
{
    public static String[] a;
    private AtomicInteger b;
    private MediaFormat c;
    private e d;
    private MediaFormat e;
    private String f;
    private volatile long g;
    private volatile long h;
    private int i;
    private int j;
    private int k;
    private int l;
    private List<CaptureAndEnc.e> m;
    private a n;
    private f o;
    private AtomicBoolean p;
    private AtomicBoolean q;
    private ReentrantLock r;
    private Condition s;
    private Condition t;
    private com.tencent.liteav.videoediter.audio.e u;
    private TXSkpResample v;
    private com.tencent.liteav.videoediter.audio.f w;
    private CaptureAndEnc.e x;
    private Handler y;
    private boolean z;
    
    public c() {
        this.g = -1L;
        this.h = -1L;
        this.y = new Handler(Looper.getMainLooper());
        this.z = true;
        this.p = new AtomicBoolean(false);
        this.q = new AtomicBoolean(false);
        this.b = new AtomicInteger(-1);
        this.r = new ReentrantLock();
        this.s = this.r.newCondition();
        this.t = this.r.newCondition();
        (this.w = new f()).a(1.0f);
    }
    
    public void a(final float n) {
        this.w.a(n);
    }
    
    public int a(final String f) throws IOException {
        if (this.f != null && !this.f.equals(f)) {
            this.g = -1L;
            this.h = -1L;
        }
        if (TextUtils.isEmpty((CharSequence)f)) {
            this.d();
            this.e = null;
            return 0;
        }
        if (this.b.get() == 0 || this.b.get() == 1) {
            this.d();
        }
        this.f = f;
        return this.f();
    }
    
    public void a(final long n, final long n2) {
        this.g = n * 1000L;
        this.h = n2 * 1000L;
        if (this.d != null) {
            this.d.c(this.g);
        }
        TXCLog.i("TXAudioMixer", "bgm startTime :" + this.g + ",bgm endTime:" + this.h);
    }
    
    public void b(final float n) {
        this.w.b(n);
    }
    
    public void a(final MediaFormat c) {
        if (c == null) {
            TXCLog.e("TXAudioMixer", "target media format can't be null");
            return;
        }
        this.c = c;
        this.j = this.c.getInteger("channel-count");
        this.i = this.c.getInteger("sample-rate");
        this.n();
    }
    
    private int f() throws IOException {
        this.b.getAndSet(0);
        this.o();
        final String string = this.e.getString("mime");
        boolean b = false;
        for (final String s : com.tencent.liteav.videoediter.audio.c.a) {
            if (string != null && string.equals(s)) {
                b = true;
                break;
            }
        }
        if (!b) {
            this.b.getAndSet(2);
            return -1;
        }
        this.g();
        this.p();
        this.n();
        return 0;
    }
    
    private void g() throws IOException {
        this.o = new f();
        if (this.d != null) {
            this.o.a(this.d.m());
            this.o.a(this.d.m(), null);
            this.o.a();
        }
    }
    
    private void h() {
        try {
            this.a(this.f);
            this.a();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void a() {
        if (this.b.get() == -1 || this.b.get() == 2) {
            TXCLog.e("TXAudioMixer", "you should set bgm info first");
            return;
        }
        if (this.b.get() == 1) {
            TXCLog.e("TXAudioMixer", "decode have been started");
            return;
        }
        this.b.getAndSet(1);
        this.i();
    }
    
    public short[] a(final short[] array) {
        if (this.b.get() != 1) {
            TXCLog.e("TXAudioMixer", "bgm decode not start yet");
            this.w.a(array, null);
            return array;
        }
        return this.w.a(array, this.a(array.length));
    }
    
    private short[] a(final int n) {
        final CaptureAndEnc.e c = this.c();
        if (c == null) {
            return null;
        }
        final short[] a = this.a(c);
        if (a == null) {
            return null;
        }
        short[] array = Arrays.copyOf(a, n);
        int i = a.length;
        if (i < n) {
            while (i < n) {
                final CaptureAndEnc.e c2 = this.c();
                if (c2 == null) {
                    return null;
                }
                final short[] a2 = this.a(c2);
                if (a2.length + i > n) {
                    final short[] a3 = this.a(array, i, a2);
                    if (a3 == null) {
                        continue;
                    }
                    i += a2.length - a3.length;
                    this.x = this.b(a3);
                }
                else {
                    this.a(array, i, a2);
                    i += a2.length;
                    this.x = null;
                }
            }
        }
        else if (i > n) {
            this.x = this.b(Arrays.copyOfRange(a, n, a.length));
        }
        else if (i == n) {
            array = this.a(c);
            this.x = null;
        }
        return array;
    }
    
    private short[] a(final CaptureAndEnc.e e) {
        if (e instanceof com.tencent.liteav.videoediter.audio.a) {
            return ((com.tencent.liteav.videoediter.audio.a)e).z();
        }
        return com.tencent.liteav.videoediter.audio.b.a(e.b(), e.g());
    }
    
    private short[] a(final short[] array, final int n, final short[] array2) {
        int n2 = 0;
        for (int n3 = n; n2 < array2.length && n3 < array.length; ++n3, ++n2) {
            array[n3] = array2[n2];
        }
        if (array2.length - n2 + 1 > 0) {
            return Arrays.copyOfRange(array2, n2, array2.length);
        }
        return null;
    }
    
    public boolean b() {
        return !this.z && this.q.get();
    }
    
    public CaptureAndEnc.e c() {
        if (this.x != null) {
            final CaptureAndEnc.e x = this.x;
            this.x = null;
            return x;
        }
        if (!this.z && this.q.get()) {
            return null;
        }
        while (this.m != null && this.m.size() == 0) {
            this.r.lock();
            try {
                this.t.await();
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            finally {
                this.r.unlock();
            }
        }
        if (this.m != null && this.m.size() <= 10) {
            this.r.lock();
            this.s.signal();
            this.r.unlock();
        }
        CaptureAndEnc.e e;
        for (e = null; (e == null || e.g() == 0) && this.m != null && this.m.size() != 0; e = this.m.remove(0)) {}
        return e;
    }
    
    public void d() {
        if (this.b.get() == -1) {
            return;
        }
        this.b.getAndSet(2);
        TXCLog.i("TXAudioMixer", "============================start cancel mix task=============================");
        this.t();
        this.r();
        this.q();
        this.s();
        this.y.removeCallbacksAndMessages((Object)null);
        TXCLog.i("TXAudioMixer", "============================cancel finish =============================");
    }
    
    private void i() {
        this.j();
        (this.n = new a()).start();
    }
    
    private void j() {
        if (this.n != null && this.n.isAlive() && !this.n.isInterrupted()) {
            this.n.interrupt();
            this.n = null;
        }
        this.q();
        this.p();
        this.p.getAndSet(false);
        this.q.getAndSet(false);
    }
    
    public MediaFormat e() {
        return this.e;
    }
    
    public void a(final boolean z) {
        this.z = z;
    }
    
    private void k() throws Exception {
        TXCLog.i("TXAudioMixer", "================= start decode===================");
        while (this.b.get() == 1 && !Thread.currentThread().isInterrupted()) {
            if (this.q.get()) {
                TXCLog.i("TXAudioMixer", "=================Decoded end===================");
                break;
            }
            CaptureAndEnc.e m;
            try {
                this.l();
                m = this.m();
            }
            catch (Exception ex2) {
                m = null;
            }
            if (m == null) {
                continue;
            }
            final CaptureAndEnc.e b = this.b(m);
            if (b == null) {
                continue;
            }
            if (this.m != null && this.m.size() == 20) {
                this.r.lock();
                try {
                    this.s.await();
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                finally {
                    this.r.unlock();
                }
            }
            if (this.m != null && this.m.size() == 0) {
                if (b != null) {
                    this.m.add(b);
                }
                this.r.lock();
                this.t.signal();
                this.r.unlock();
            }
            else {
                if (this.m == null || b == null) {
                    continue;
                }
                this.m.add(b);
            }
        }
        TXCLog.i("TXAudioMixer", "=================decode finish===================");
    }
    
    private void l() throws InterruptedException {
        if (this.p.get()) {
            return;
        }
        final CaptureAndEnc.e c = this.o.c();
        if (c == null) {
            return;
        }
        final CaptureAndEnc.e b = this.d.b(c);
        if (b != null) {
            if (this.d.d(b)) {
                this.p.getAndSet(true);
                TXCLog.d("TXAudioMixer", "audio endOfFile:" + this.p.get());
                TXCLog.i("TXAudioMixer", "read audio end");
            }
            this.o.a(b);
        }
    }
    
    private CaptureAndEnc.e m() {
        if (this.b.get() != 1) {
            return null;
        }
        final CaptureAndEnc.e d = this.o.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        if (d.e() < this.g && (d.o().flags & 0x4) == 0x0) {
            return null;
        }
        if (d.e() > this.h) {
            this.q.getAndSet(true);
            return null;
        }
        if ((d.o().flags & 0x4) != 0x0) {
            TXCLog.i("TXAudioMixer", "==================generate decode Audio END==========================");
            this.q.getAndSet(true);
            return d;
        }
        return d;
    }
    
    private CaptureAndEnc.e b(final CaptureAndEnc.e e) throws InterruptedException {
        if (e.o().flags == 2) {
            return e;
        }
        if (this.j == this.l && this.i == this.k) {
            return e;
        }
        short[] array = com.tencent.liteav.videoediter.audio.b.a(e.b(), e.g());
        if (array == null || array.length == 0) {
            return e;
        }
        if (this.u == null || this.v == null) {
            return e;
        }
        if (this.j != this.l) {
            array = this.u.a(array);
        }
        if (this.i != this.k) {
            array = this.v.doResample(array);
            if (array == null || array.length == 0) {
                return null;
            }
        }
        return this.b(array);
    }
    
    private CaptureAndEnc.e b(final short[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        final com.tencent.liteav.videoediter.audio.a a = new com.tencent.liteav.videoediter.audio.a();
        a.a(array);
        a.d(array.length * 2);
        a.h(this.j);
        a.g(this.i);
        return a;
    }
    
    @TargetApi(16)
    private void n() {
        if (this.e == null || this.c == null) {
            return;
        }
        if (this.u == null) {
            this.u = new e();
        }
        this.u.a(this.l, this.j);
        if (this.v == null) {
            this.v = new TXSkpResample();
        }
        this.v.init(this.k, this.i);
        TXCLog.i("TXAudioMixer", "TXChannelResample and TXSkpResample have been created!!!");
    }
    
    private void o() throws IOException {
        TXCLog.i("TXAudioMixer", "initMediaExtractor -> bgmPath = " + this.f);
        (this.d = new e(true)).a(this.f);
        this.e = this.d.m();
        this.l = this.e.getInteger("channel-count");
        this.k = this.e.getInteger("sample-rate");
        if (this.g == -1L && this.h == -1L) {
            this.g = 0L;
            this.h = this.e.getLong("durationUs") * 1000L;
        }
        this.d.c(this.g);
    }
    
    private void p() {
        this.m = new LinkedList<CaptureAndEnc.e>();
        this.m = Collections.synchronizedList(this.m);
    }
    
    private void q() {
        if (this.m != null) {
            TXCLog.i("TXAudioMixer", "clean audio frame queue");
            this.m.clear();
            this.m = null;
        }
    }
    
    private void r() {
        if (this.d != null) {
            TXCLog.i("TXAudioMixer", "release media extractor");
            this.d.o();
            this.d = null;
        }
    }
    
    private void s() {
        if (this.u != null) {
            this.u = null;
            TXCLog.i("TXAudioMixer", "release chanel resample ");
        }
        if (this.v != null) {
            TXCLog.i("TXAudioMixer", "release skp resample ");
            this.v.destroy();
            this.v = null;
        }
    }
    
    private void t() {
        if (this.n != null && this.n.isAlive() && !this.n.isInterrupted()) {
            TXCLog.i("TXAudioMixer", "interrupt the decode thread");
            this.n.interrupt();
            this.n = null;
        }
        if (this.o != null) {
            TXCLog.i("TXAudioMixer", "stop audio decode");
            this.o.b();
            this.o = null;
        }
    }
    
    static {
        c.a = new String[] { "audio/mp4a-latm", "audio/mpeg" };
    }
    
    private class a extends Thread
    {
        public a() {
            super("Mixer-BGM-Decoder-Thread");
        }
        
        @Override
        public void run() {
            super.run();
            TXCLog.i("TXAudioMixer", "================= start thread===================");
            try {
                com.tencent.liteav.videoediter.audio.c.this.k();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            if (com.tencent.liteav.videoediter.audio.c.this.z && com.tencent.liteav.videoediter.audio.c.this.b.get() == 1) {
                com.tencent.liteav.videoediter.audio.c.this.y.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        com.tencent.liteav.videoediter.audio.c.this.h();
                    }
                });
            }
            TXCLog.i("TXAudioMixer", "================= finish thread===================");
        }
    }
}
