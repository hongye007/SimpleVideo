package com.tencent.liteav.g;

import java.util.*;
import java.util.concurrent.atomic.*;

import com.tencent.liteav.basic.log.*;
import java.nio.*;
import android.media.*;
import android.os.*;

public class m
{
    private final String a = "VideoJoinDecAndDemuxPreview";
    private t b;
    private c c;
    private com.tencent.liteav.g.a d;
    private List<i> e;
    private AtomicInteger f;
    private HandlerThread g;
    private HandlerThread h;
    private b i;
    private a j;
    private i k;
    private i l;
    private long m;
    private long n;
    private long o;
    private long p;
    private boolean q;
    private e r;
    private long s;
    private long t;
    private boolean u;
    private e v;
    private long w;
    private long x;
    private Object y;
    private long z;
    
    public m() {
        this.m = -1L;
        this.n = -1L;
        this.o = -1L;
        this.u = true;
        this.y = new Object();
        this.f = new AtomicInteger(1);
        this.b = com.tencent.liteav.g.t.a();
        (this.g = new HandlerThread("joinVDec")).start();
        this.i = new b(this.g.getLooper());
        (this.h = new HandlerThread("joinADec")).start();
        this.j = new a(this.h.getLooper());
    }
    
    public void a(final List<i> e) {
        this.e = e;
    }
    
    public void a(final c c) {
        synchronized (this.y) {
            this.c = c;
        }
    }
    
    public void a(final com.tencent.liteav.g.a d) {
        synchronized (this.y) {
            this.d = d;
        }
    }
    
    public synchronized void a() {
        TXCLog.i("VideoJoinDecAndDemuxPreview", "start");
        if (this.f.get() == 2) {
            TXCLog.e("VideoJoinDecAndDemuxPreview", "start ignore, mCurrentState = " + this.f.get());
            return;
        }
        this.u = true;
        this.b.n();
        this.f.set(2);
        this.i.sendEmptyMessage(101);
        this.j.sendEmptyMessage(201);
    }
    
    public synchronized void b() {
        TXCLog.i("VideoJoinDecAndDemuxPreview", "stop");
        if (this.f.get() == 1) {
            TXCLog.e("VideoJoinDecAndDemuxPreview", "stop(), mCurrentState in stop, ignore");
            return;
        }
        this.f.set(1);
        this.i.sendEmptyMessage(103);
        this.j.sendEmptyMessage(203);
    }
    
    public synchronized void c() {
        final int value = this.f.get();
        if (value == 3 || value == 1) {
            TXCLog.e("VideoJoinDecAndDemuxPreview", "pause ignore, current state = " + value);
            return;
        }
        this.f.set(3);
        this.i.sendEmptyMessage(104);
        this.j.sendEmptyMessage(204);
    }
    
    public synchronized void d() {
        final int value = this.f.get();
        if (value == 1 || value == 2) {
            TXCLog.e("VideoJoinDecAndDemuxPreview", "resume ignore, state = " + value);
            return;
        }
        this.f.set(2);
        this.i.sendEmptyMessage(102);
        this.j.sendEmptyMessage(202);
    }
    
    public void a(final boolean u) {
        this.u = u;
    }
    
    private void e() {
        if (!this.u) {
            this.j.sendEmptyMessageDelayed(202, 10L);
            return;
        }
        e v = null;
        if (this.l.f() == null) {
            if (Build.VERSION.SDK_INT >= 16) {
                v = this.f();
            }
        }
        else {
            this.l.r();
            v = this.l.t();
            if (v == null) {
                this.j.sendEmptyMessage(202);
                return;
            }
            TXCLog.d("VideoJoinDecAndDemuxPreview", "before:" + v.e() + ",after:" + (v.e() + this.t));
            v.a(v.e() + this.t);
            if (v != null) {
                TXCLog.d("VideoJoinDecAndDemuxPreview", "====:" + v.e() + ",len:" + v.g() + ",mEOFAudioFrameUs:" + this.t + ",flag:" + v.f());
            }
        }
        if (v == null) {
            return;
        }
        if (v.p()) {
            if (this.b.m()) {
                if (this.b.l() && this.k.o()) {
                    TXCLog.i("VideoJoinDecAndDemuxPreview", "throw last audio");
                    this.a(v);
                    synchronized (this) {
                        this.f.set(1);
                    }
                }
                this.j.sendEmptyMessage(203);
            }
            else {
                final long n = 1024000000L / this.v.j();
                this.t = this.v.e() + n;
                TXCLog.i("VideoJoinDecAndDemuxPreview", "mEOFAudioFrameUs:" + this.t + ",mCurrentAudioDuration:" + this.x);
                if (this.t < this.x) {
                    final int n2 = (int)((this.x - this.t) / n);
                    TXCLog.i("VideoJoinDecAndDemuxPreview", "count:" + n2);
                    for (int i = 0; i < n2; ++i) {
                        this.a(n);
                    }
                    this.t = this.x;
                }
                this.i();
                this.j.sendEmptyMessage(202);
            }
        }
        else {
            if (this.v == null) {
                this.o = System.currentTimeMillis();
            }
            this.a(this.v = v);
            this.j.sendEmptyMessage(202);
        }
    }
    
    private e f() {
        if (Build.VERSION.SDK_INT < 16) {
            return null;
        }
        final MediaFormat i = com.tencent.liteav.g.t.a().i();
        if (i == null) {
            return null;
        }
        final int integer = i.getInteger("sample-rate");
        final int integer2 = i.getInteger("channel-count");
        final long n = 1024000000L / integer;
        final e e = new e("audio/mp4a-latm", ByteBuffer.allocate(2048 * integer2), new MediaCodec.BufferInfo());
        e.g(integer);
        e.h(integer2);
        e.d(2048 * integer2);
        long n2 = 0L;
        if (this.v != null) {
            n2 = this.v.e() + n;
        }
        if (n2 >= this.x) {
            e.c(4);
        }
        e.a(n2);
        return e;
    }
    
    public void a(final long n) {
        this.v.a(ByteBuffer.allocate(this.v.g()));
        final e v = new e(this.v.a(), this.v.b(), this.v.o());
        v.a(this.v.c());
        v.b(this.v.d());
        v.g(this.v.j());
        v.h(this.v.k());
        v.a(this.v.e() + n);
        this.v = v;
        TXCLog.d("VideoJoinDecAndDemuxPreview", "------insertEmptyAudioFrame--------");
        this.a(v);
    }
    
    private void g() {
        if (this.m >= 0L) {
            if (this.q) {
                this.b(this.r);
            }
            else {
                if (!this.h()) {
                    this.i.sendEmptyMessageDelayed(102, 5L);
                    return;
                }
                this.b(this.r);
            }
        }
        this.k.q();
        final e s = this.k.s();
        if (s == null) {
            this.i.sendEmptyMessage(102);
            return;
        }
        s.a(s.e() + this.s);
        if (s.p()) {
            if (this.b.l()) {
                if (this.b.m() && this.l.p()) {
                    TXCLog.i("VideoJoinDecAndDemuxPreview", "throw last video");
                    this.b(s);
                    this.f.set(1);
                    this.w = 0L;
                }
                this.i.sendEmptyMessage(103);
            }
            else {
                this.s = this.r.e();
                TXCLog.i("VideoJoinDecAndDemuxPreview", "mEOFVideoFrameUs:" + this.s + ",mCurrentVideoDuration:" + this.w);
                if (this.s != this.w) {
                    this.s = this.w;
                }
                this.j();
                this.i.sendEmptyMessage(102);
            }
        }
        else {
            if (s.e() > this.w) {
                TXCLog.d("VideoJoinDecAndDemuxPreview", "dropOne");
                this.i.sendEmptyMessage(102);
                return;
            }
            this.r = s;
            this.m = s.e() / 1000L;
            if (this.n < 0L) {
                this.n = this.m;
                if (this.o > 0L) {
                    this.p = this.o;
                    TXCLog.i("VideoJoinDecAndDemuxPreview", "mTimelineMs get AudioFrame:" + this.o);
                }
                else {
                    this.p = System.currentTimeMillis();
                    TXCLog.i("VideoJoinDecAndDemuxPreview", "mTimelineMs get SystemTime:" + this.p);
                }
                this.q = true;
                this.i.sendEmptyMessage(102);
            }
            else {
                this.q = false;
                this.i.sendEmptyMessageDelayed(102, 5L);
            }
        }
    }
    
    private boolean h() {
        final long currentTimeMillis = System.currentTimeMillis();
        this.m = this.r.e() / 1000L;
        return Math.abs(this.m - this.n) < currentTimeMillis - this.p;
    }
    
    private void i() {
        if (!this.b.k()) {
            TXCLog.i("VideoJoinDecAndDemuxPreview", "isAllReadEOF");
            this.j.sendEmptyMessage(203);
            return;
        }
        this.l = this.b.f();
        this.x += this.l.j();
        this.j.sendEmptyMessage(202);
    }
    
    private void j() {
        if (!this.b.g()) {
            TXCLog.i("VideoJoinDecAndDemuxPreview", "isAllReadEOF");
            this.i.sendEmptyMessage(103);
            return;
        }
        this.k = this.b.e();
        this.w += this.k.j();
        TXCLog.i("VideoJoinDecAndDemuxPreview", "Duration :" + this.k.j());
        this.i.sendEmptyMessage(102);
    }
    
    private void k() {
        TXCLog.i("VideoJoinDecAndDemuxPreview", "startAudioDecoder");
        final List<i> d = com.tencent.liteav.g.t.a().d();
        for (int i = 0; i < d.size(); ++i) {
            final i j = d.get(i);
            j.d();
            j.m();
        }
        this.l = this.b.f();
        this.x = this.l.j();
    }
    
    private void l() {
        TXCLog.i("VideoJoinDecAndDemuxPreview", "stopAudioDecoder");
        final List<i> d = com.tencent.liteav.g.t.a().d();
        for (int i = 0; i < d.size(); ++i) {
            d.get(i).n();
        }
    }
    
    private void m() {
        TXCLog.i("VideoJoinDecAndDemuxPreview", "startVideoDecoder");
        final List<i> d = com.tencent.liteav.g.t.a().d();
        for (int i = 0; i < d.size(); ++i) {
            final i j = d.get(i);
            j.c();
            j.k();
        }
        this.k = this.b.e();
        this.w = this.k.j();
        TXCLog.i("VideoJoinDecAndDemuxPreview", "Duration :" + this.k.j());
    }
    
    private void n() {
        TXCLog.i("VideoJoinDecAndDemuxPreview", "stopVideoDecoder");
        final List<i> d = com.tencent.liteav.g.t.a().d();
        for (int i = 0; i < d.size(); ++i) {
            d.get(i).l();
        }
    }
    
    private void a(final e e) {
        synchronized (this.y) {
            if (this.d != null) {
                TXCLog.d("VideoJoinDecAndDemuxPreview", "source:" + this.l.a + ",throwOutAudioFrame: " + e.e());
                this.d.a(e, this.l);
            }
        }
    }
    
    private void b(final e e) {
        synchronized (this.y) {
            if (this.c != null && e.e() != this.z) {
                TXCLog.d("VideoJoinDecAndDemuxPreview", "source:" + this.k.a + ",throwOutVideoFrame: " + e.e());
                this.c.a(e, this.k);
                this.z = e.e();
            }
        }
    }
    
    class a extends Handler
    {
        public a(final Looper looper) {
            super(looper);
        }
        
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 201: {
                    com.tencent.liteav.g.m.this.k();
                    com.tencent.liteav.g.m.this.j.sendEmptyMessage(202);
                    break;
                }
                case 202: {
                    com.tencent.liteav.g.m.this.e();
                    break;
                }
                case 204: {
                    com.tencent.liteav.g.m.this.j.removeMessages(202);
                    com.tencent.liteav.g.m.this.o = -1L;
                    com.tencent.liteav.g.m.this.v = null;
                    break;
                }
                case 203: {
                    com.tencent.liteav.g.m.this.j.removeMessages(202);
                    com.tencent.liteav.g.m.this.l();
                    com.tencent.liteav.g.m.this.o = -1L;
                    com.tencent.liteav.g.m.this.v = null;
                    com.tencent.liteav.g.m.this.t = 0L;
                    break;
                }
            }
        }
    }
    
    class b extends Handler
    {
        public b(final Looper looper) {
            super(looper);
        }
        
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 101: {
                    com.tencent.liteav.g.m.this.m();
                    com.tencent.liteav.g.m.this.i.sendEmptyMessage(102);
                    break;
                }
                case 102: {
                    com.tencent.liteav.g.m.this.g();
                    break;
                }
                case 104: {
                    com.tencent.liteav.g.m.this.i.removeMessages(102);
                    com.tencent.liteav.g.m.this.r = null;
                    com.tencent.liteav.g.m.this.m = -1L;
                    com.tencent.liteav.g.m.this.n = -1L;
                    com.tencent.liteav.g.m.this.p = -1L;
                    break;
                }
                case 103: {
                    com.tencent.liteav.g.m.this.i.removeMessages(102);
                    com.tencent.liteav.g.m.this.n();
                    com.tencent.liteav.g.m.this.r = null;
                    com.tencent.liteav.g.m.this.m = -1L;
                    com.tencent.liteav.g.m.this.n = -1L;
                    com.tencent.liteav.g.m.this.p = -1L;
                    com.tencent.liteav.g.m.this.s = 0L;
                    break;
                }
            }
        }
    }
}
