package com.tencent.liteav.f;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.videoediter.audio.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import com.tencent.liteav.basic.log.*;
import android.media.*;
import android.text.*;
import java.io.*;
import java.nio.*;
import android.os.*;

public class b
{
    private final String b = "AudioPreprocessChain";
    private d c;
    private e d;
    private TXJNIAudioResampler e;
    private c f;
    private j g;
    private int h;
    private int i;
    private LinkedList<Long> j;
    private long k;
    private long l;
    private int m;
    private CameraProxy.b n;
    private CaptureAndEnc.b o;
    private float p;
    private g q;
    private boolean r;
    private HandlerThread s;
    private a t;
    private AtomicBoolean u;
    private final AtomicBoolean v;
    public CaptureAndEnc.e a;
    private Object w;
    private String x;
    private long y;
    private double z;
    private double A;
    
    public b(final String s) {
        this.k = -1L;
        this.l = -1L;
        this.m = 0;
        this.w = new Object();
        this.j = new LinkedList<Long>();
        this.n = CameraProxy.b.a();
        this.q = com.tencent.liteav.f.g.a();
        this.u = new AtomicBoolean(false);
        this.v = new AtomicBoolean(false);
        this.r = true;
        this.o = new CaptureAndEnc.b();
        this.x = s + "Bgm";
    }
    
    public void a() {
        TXCLog.i("AudioPreprocessChain", "initFilter");
        this.e = new TXJNIAudioResampler();
        this.d = new e();
        synchronized (this.w) {
            this.f = new c();
        }
        this.c = new d();
        this.p = 1.0f;
        this.e.setSpeed(this.p);
    }
    
    public void b() {
        TXCLog.i("AudioPreprocessChain", "destroyFilter");
        this.k = -1L;
        this.l = -1L;
        this.m = 0;
        if (this.e != null) {
            this.e.destroy();
            this.e = null;
        }
        synchronized (this.w) {
            if (this.f != null) {
                this.f.d();
                this.f = null;
            }
        }
        if (this.d != null) {
            this.d = null;
        }
        if (this.j != null) {
            this.j.clear();
        }
    }
    
    public void a(final MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.e("AudioPreprocessChain", "setAudioFormat audioFormat is null");
            return;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            this.o.b = mediaFormat.getInteger("sample-rate");
            this.o.a = mediaFormat.getInteger("channel-count");
            TXCLog.i("AudioPreprocessChain", "setAudioFormat sampleRate:" + this.o.b);
            TXCLog.i("AudioPreprocessChain", "setAudioFormat channelCount:" + this.o.a);
        }
        if (this.h != 0 && this.i != 0) {
            this.e.setChannelCount(this.o.a);
            this.d.a(this.h, this.o.a);
            this.e.setSampleRate(this.i, this.o.b);
        }
        if (this.f != null) {
            this.f.a(mediaFormat);
        }
    }
    
    public void c() {
        TXCLog.i("AudioPreprocessChain", "start");
        if (TextUtils.isEmpty((CharSequence)this.n.a)) {
            this.n.h = false;
            return;
        }
        this.n.h = true;
        this.u.set(true);
        this.a(this.n.a);
        if (this.n.b != -1L && this.n.c != -1L) {
            this.a(this.n.b, this.n.c);
        }
        this.a(this.n.e);
        this.a(this.n.f);
        this.b(this.n.g);
        this.a(this.n.d);
        this.z = 0.0;
        if (this.n.i) {
            this.k();
        }
    }
    
    private void k() {
        if (this.n.k <= 0L) {
            return;
        }
        if (this.n.e) {
            this.A = this.y / 1000000.0;
            TXCLog.i("AudioPreprocessChain", "getBgmEndTimePts, is loop, mBgmEndTimeSec = " + this.A);
        }
        else {
            final long n = this.n.c * 1000L - this.n.b * 1000L;
            this.A = ((this.y > n) ? n : this.y) / 1000000.0;
            TXCLog.i("AudioPreprocessChain", "getBgmEndTimePts, not loop, mVideoDurationUs = " + this.y + ", bgmDurationUs = " + n + ", so mBgmEndTimeSec = " + this.A);
        }
    }
    
    public void d() {
        TXCLog.i("AudioPreprocessChain", "stop");
        if (!this.r) {
            if (this.t != null) {
                this.t.removeCallbacksAndMessages((Object)null);
                this.s.quit();
            }
            this.v.set(true);
            this.s = null;
            this.t = null;
        }
    }
    
    public int e() {
        if (!this.r) {
            if (this.s == null) {
                (this.s = new HandlerThread(this.x)).start();
                this.t = new a(this.s.getLooper());
            }
            this.v.set(false);
            this.t.sendEmptyMessage(10000);
            return 0;
        }
        TXCLog.w("AudioPreprocessChain", "tryStartAddBgmForNoAudioTrack, this has audio track, ignore!");
        return -1;
    }
    
    public void f() {
        TXCLog.i("AudioPreprocessChain", "pause");
        this.u.set(false);
    }
    
    public void g() {
        TXCLog.i("AudioPreprocessChain", "resume");
        this.u.set(true);
    }
    
    public int a(final String s) {
        int a;
        try {
            a = this.f.a(s);
        }
        catch (IOException ex) {
            TXCLog.e("AudioPreprocessChain", "set bgm failed.", ex);
            a = -1;
        }
        this.f.a();
        if (!TextUtils.isEmpty((CharSequence)s)) {
            this.n.h = true;
        }
        else {
            this.n.h = false;
        }
        return a;
    }
    
    public void a(final long n, final long n2) {
        if (this.f != null) {
            this.f.a(n, n2);
        }
    }
    
    public void a(final boolean b) {
        if (this.f != null) {
            this.f.a(b);
        }
    }
    
    public void a(final long d) {
        this.n.d = d;
    }
    
    public void a(final float n) {
        if (this.f != null) {
            this.f.b(n);
        }
    }
    
    public void b(final float n) {
        if (this.f != null) {
            this.f.a(n);
        }
    }
    
    public void b(final long y) {
        this.y = y;
    }
    
    public MediaFormat h() {
        return this.f.e();
    }
    
    public void a(final j g) {
        this.g = g;
    }
    
    public void a(final CaptureAndEnc.e e) {
        if (e == null) {
            TXCLog.d("AudioPreprocessChain", "processFrame, frame is null");
            return;
        }
        if (e.q() || e.r()) {
            TXCLog.d("AudioPreprocessChain", "processFrame, frame is isUnNormallFrame");
            if (this.g != null) {
                this.g.a(e);
            }
            return;
        }
        if (e.p()) {
            TXCLog.i("AudioPreprocessChain", "processFrame, frame is end");
            if (this.g != null) {
                this.g.a(e);
            }
            return;
        }
        if (!this.q.c() && this.i == this.o.b) {
            this.j.add(e.e());
        }
        else {
            this.p = this.q.a(e.e());
            this.e.setSpeed(this.p);
            if (this.k == -1L) {
                this.k = e.e();
            }
            this.j.add(this.l());
        }
        final CaptureAndEnc.e a = this.a(e.b(), this.b(e));
        if (this.g != null) {
            this.g.a(a);
        }
    }
    
    public void i() {
        if (this.e != null) {
            short[] array = this.e.flushBuffer();
            if (this.i != this.o.b && this.o.a == 2 && array != null) {
                this.d.a(1, 2);
                array = this.d.a(array);
            }
            CaptureAndEnc.e a = null;
            if (array != null && array.length > 0) {
                this.j.add((long)this.l());
                a = this.a(null, array);
            }
            if (a != null && this.g != null) {
                this.g.a(a);
            }
        }
    }
    
    private Long l() {
        long k = 0L;
        if (this.m == 0) {
            k = this.k;
        }
        else if (this.o != null && this.o.b != 0) {
            k = this.k + 1024000000L * this.m / this.o.b;
        }
        ++this.m;
        return k;
    }
    
    private long a(final int n) {
        long n2;
        if (this.l == -1L) {
            n2 = this.k;
        }
        else {
            n2 = this.l;
        }
        this.l = n2 + 1000000L * n / (this.o.b * this.o.a * 2);
        return n2;
    }
    
    private CaptureAndEnc.e a(final ByteBuffer byteBuffer, final short[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        if (this.j == null || this.j.size() == 0) {
            TXCLog.i("AudioPreprocessChain", "doMixer mTimeQueue:" + this.j);
            return null;
        }
        final long longValue = this.j.pollFirst();
        if (this.n.h) {
            short[] array2;
            if (longValue >= this.n.d) {
                this.f.a(this.b(array.length * 2));
                array2 = this.f.a(array);
            }
            else {
                this.c.a(this.n.f);
                array2 = this.c.a(array);
            }
            return this.a(byteBuffer, array2, longValue);
        }
        this.c.a(this.n.f);
        return this.a(byteBuffer, this.c.a(array), longValue);
    }
    
    private float b(final int n) {
        if (!this.n.i) {
            return this.n.g;
        }
        this.z += n / (this.o.b * this.o.a * 2.0);
        final float n2 = this.n.j / 1000.0f;
        final float n3 = this.n.k / 1000.0f;
        double n4 = 1.0;
        if (this.n.j > 0L && this.z <= n2) {
            n4 = Math.log10(1.0 + this.z) / Math.log10(Math.pow(10.0, Math.log10(n2 + 1.0f) / 1.0));
        }
        else if (this.n.k > 0L && this.z >= this.A - n3) {
            n4 = Math.log10(1.0 + this.A - this.z) / Math.log10(Math.pow(10.0, Math.log10(n3 + 1.0f) / 1.0));
        }
        if (n4 < 0.0) {
            n4 = 0.0;
        }
        else if (n4 > 1.0) {
            n4 = 1.0;
        }
        return (float)(this.n.g * n4);
    }
    
    private CaptureAndEnc.e a(final ByteBuffer byteBuffer, final short[] array, final long n) {
        final int n2 = array.length * 2;
        final ByteBuffer a = com.tencent.liteav.videoediter.audio.b.a(byteBuffer, array);
        final CaptureAndEnc.e e = new CaptureAndEnc.e();
        e.d(n2);
        e.a(a);
        e.h(this.o.a);
        e.g(this.o.b);
        e.b(n);
        e.a(n);
        return e;
    }
    
    private short[] b(final CaptureAndEnc.e e) {
        this.c(e);
        short[] array = com.tencent.liteav.videoediter.audio.b.a(e.b(), e.g());
        if (this.i != this.o.b && this.o.a == 2) {
            if (this.h == 2) {
                this.d.a(2, 1);
                if (array != null) {
                    array = this.d.a(array);
                }
            }
            if (this.p != 1.0f || this.i != this.o.b) {
                array = this.e.resample(array);
            }
            if (array != null) {
                this.d.a(1, 2);
                array = this.d.a(array);
            }
            return array;
        }
        if (this.h != this.o.a) {
            array = this.d.a(array);
        }
        if (this.p != 1.0f || this.i != this.o.b) {
            array = this.e.resample(array);
        }
        return array;
    }
    
    private void c(final CaptureAndEnc.e e) {
        if (this.h != e.k()) {
            this.h = e.k();
            TXCLog.i("AudioPreprocessChain", "setAudioFormat initResampler setChannelCount");
            this.e.setChannelCount(this.o.a);
            this.d.a(this.h, this.o.a);
        }
        if (this.i != e.j()) {
            this.i = e.j();
            TXCLog.i("AudioPreprocessChain", "setAudioFormat initResampler setSampleRate");
            this.e.setSampleRate(this.i, this.o.b);
        }
    }
    
    public void b(final boolean r) {
        this.r = r;
    }
    
    public void c(final boolean b) {
        this.u.set(b);
    }
    
    public boolean j() {
        return this.v.get();
    }
    
    class a extends Handler
    {
        public a(final Looper looper) {
            super(looper);
        }
        
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 10000: {
                    this.a();
                    break;
                }
            }
        }
        
        private void a() {
            if (com.tencent.liteav.f.b.this.v.get()) {
                return;
            }
            if (!com.tencent.liteav.f.b.this.u.get()) {
                this.sendEmptyMessageDelayed(10000, 10L);
                return;
            }
            CaptureAndEnc.e e = null;
            boolean b = false;
            synchronized (com.tencent.liteav.f.b.this.w) {
                e = com.tencent.liteav.f.b.this.f.c();
                b = com.tencent.liteav.f.b.this.f.b();
            }
            if (e == null && b) {
                com.tencent.liteav.f.b.this.v.set(true);
                final CaptureAndEnc.e b2 = this.b();
                if (com.tencent.liteav.f.b.this.g != null) {
                    com.tencent.liteav.f.b.this.g.a(b2);
                    return;
                }
            }
            if (e != null && e.b() != null) {
                final float a = com.tencent.liteav.f.b.this.b(e.g());
                TXCLog.d("AudioPreprocessChain", "BgmHandler, bgmVolume = " + a);
                if (a != 1.0f) {
                    com.tencent.liteav.f.b.this.c.a(a);
                    e = com.tencent.liteav.f.b.this.a(e.b(), com.tencent.liteav.f.b.this.c.a(com.tencent.liteav.videoediter.audio.b.a(e.b(), e.g())), e.e());
                }
                long b3 = com.tencent.liteav.f.b.this.a(e.g());
                if (b3 == -1L) {
                    b3 = 0L;
                }
                e.a(b3);
                TXCLog.d("AudioPreprocessChain", "BgmHandler pts:" + b3 + ", duration:" + com.tencent.liteav.f.b.this.y);
                if (com.tencent.liteav.f.b.this.y == 0L && com.tencent.liteav.f.b.this.a != null) {
                    com.tencent.liteav.f.b.this.y = com.tencent.liteav.f.b.this.a.e();
                }
                if (b3 >= com.tencent.liteav.f.b.this.y) {
                    com.tencent.liteav.f.b.this.v.set(true);
                    final CaptureAndEnc.e b4 = this.b();
                    if (com.tencent.liteav.f.b.this.g != null) {
                        com.tencent.liteav.f.b.this.g.a(b4);
                        return;
                    }
                }
                e.a(b3);
                if (com.tencent.liteav.f.b.this.g != null) {
                    com.tencent.liteav.f.b.this.g.a(e);
                }
            }
            this.sendEmptyMessageDelayed(10000, 10L);
        }
        
        private CaptureAndEnc.e b() {
            final CaptureAndEnc.e e = new CaptureAndEnc.e();
            e.d(0);
            e.a(0L);
            e.c(4);
            return e;
        }
    }
}
