package com.tencent.liteav.g;

import java.util.concurrent.atomic.*;

import com.tencent.liteav.basic.log.*;
import java.util.*;
import java.nio.*;
import android.media.*;
import android.os.*;

public class l
{
    private final String a = "VideoJoinDecAndDemuxGenerate";
    private c b;
    private com.tencent.liteav.g.a c;
    private j d;
    private AtomicInteger e;
    private HandlerThread f;
    private HandlerThread g;
    private b h;
    private a i;
    private i j;
    private i k;
    private e l;
    private long m;
    private long n;
    private boolean o;
    private e p;
    private long q;
    private long r;
    
    public l() {
        this.o = true;
        this.e = new AtomicInteger(1);
        (this.f = new HandlerThread("JoinVDecGene")).start();
        this.h = new b(this.f.getLooper());
        (this.g = new HandlerThread("JoinADecGene")).start();
        this.i = new a(this.g.getLooper());
    }
    
    public void a(final j d) {
        this.d = d;
    }
    
    public void a(final c b) {
        this.b = b;
    }
    
    public void a(final com.tencent.liteav.g.a c) {
        this.c = c;
    }
    
    public synchronized void a() {
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "start");
        if (this.e.get() == 2) {
            TXCLog.e("VideoJoinDecAndDemuxGenerate", "start ignore, mCurrentState = " + this.e.get());
            return;
        }
        this.q = 0L;
        this.r = 0L;
        this.m = 0L;
        this.n = 0L;
        this.p = null;
        this.l = null;
        this.d.h();
        this.e.set(2);
        this.h.sendEmptyMessage(101);
        this.i.sendEmptyMessage(201);
    }
    
    public synchronized void b() {
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "stop");
        if (this.e.get() == 1) {
            TXCLog.e("VideoJoinDecAndDemuxGenerate", "stop(), mCurrentState in stop, ignore");
            return;
        }
        this.e.set(1);
        this.h.sendEmptyMessage(103);
        this.i.sendEmptyMessage(203);
    }
    
    public synchronized void c() {
        if (this.e.get() == 1) {
            TXCLog.e("VideoJoinDecAndDemuxGenerate", "getNextVideoFrame, current state is init, ignore");
            return;
        }
        this.h.sendEmptyMessage(102);
    }
    
    public void a(final boolean o) {
        this.o = o;
    }
    
    private void d() {
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "startAudioDecoder");
        final List<i> a = this.d.a();
        for (int i = 0; i < a.size(); ++i) {
            final i j = a.get(i);
            j.d();
            j.m();
        }
        this.k = this.d.c();
        this.r = this.k.j();
    }
    
    private void e() {
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "stopAudioDecoder");
        final List<i> a = this.d.a();
        for (int i = 0; i < a.size(); ++i) {
            a.get(i).n();
        }
    }
    
    private void f() {
        if (!this.o) {
            this.i.sendEmptyMessageDelayed(202, 10L);
            return;
        }
        e p = null;
        if (this.k.f() == null) {
            if (Build.VERSION.SDK_INT >= 16) {
                p = this.g();
            }
        }
        else {
            this.k.r();
            p = this.k.t();
            if (p == null) {
                this.i.sendEmptyMessage(202);
                return;
            }
            p.a(p.e() + this.n);
        }
        if (p == null) {
            return;
        }
        if (p.p()) {
            if (this.d.g()) {
                if (this.d.f() && this.j.o()) {
                    TXCLog.i("VideoJoinDecAndDemuxGenerate", "throw last audio");
                    this.a(p);
                }
                this.i.sendEmptyMessage(203);
            }
            else {
                final long n = 1024000000L / this.p.j();
                this.n = this.p.e() + n;
                TXCLog.i("VideoJoinDecAndDemuxGenerate", "mEOFAudioFrameUs:" + this.n + ",mCurrentAudioDuration:" + this.r);
                if (this.n < this.r) {
                    final int n2 = (int)((this.r - this.n) / n);
                    TXCLog.i("VideoJoinDecAndDemuxGenerate", "count:" + n2);
                    for (int i = 0; i < n2; ++i) {
                        this.a(n);
                    }
                    this.n = this.r;
                }
                this.i();
                this.i.sendEmptyMessage(202);
            }
        }
        else {
            this.a(this.p = p);
            this.i.sendEmptyMessage(202);
        }
    }
    
    private e g() {
        if (Build.VERSION.SDK_INT < 16) {
            return null;
        }
        final MediaFormat i = t.a().i();
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
        if (this.p != null) {
            n2 = this.p.e() + n;
        }
        if (n2 >= this.r) {
            e.c(4);
        }
        e.a(n2);
        return e;
    }
    
    public void a(final long n) {
        final ByteBuffer allocate = ByteBuffer.allocate(this.p.g());
        TXCLog.d("VideoJoinDecAndDemuxGenerate", "mCurrentAudioFrame.getLength():" + this.p.g());
        this.p.a(allocate);
        final e p = new e(this.p.a(), this.p.b(), this.p.o());
        p.a(this.p.c());
        p.b(this.p.d());
        p.g(this.p.j());
        final long n2 = this.p.e() + n;
        p.a(n2);
        p.b(n2);
        p.c(n2);
        p.c(this.p.f());
        this.a(this.p = p);
    }
    
    private void h() {
        this.j.q();
        final e s = this.j.s();
        if (s == null) {
            this.h.sendEmptyMessage(102);
            return;
        }
        if (this.m != 0L) {
            TXCLog.d("VideoJoinDecAndDemuxGenerate", "before:" + s.e() + ",after:" + (s.e() + this.m));
        }
        s.a(s.e() + this.m);
        if (s.p()) {
            if (this.d.f()) {
                if (this.d.g() && this.k.p()) {
                    TXCLog.i("VideoJoinDecAndDemuxGenerate", "throw last video");
                    this.b(s);
                }
                this.h.sendEmptyMessage(103);
            }
            else {
                this.m = this.l.e();
                TXCLog.i("VideoJoinDecAndDemuxGenerate", "mEOFVideoFrameUs:" + this.m + ",mCurrentVideoDuration:" + this.q);
                if (this.m != this.q) {
                    this.m = this.q;
                }
                this.j();
                this.h.sendEmptyMessage(102);
            }
        }
        else {
            if (s.e() > this.q) {
                TXCLog.d("VideoJoinDecAndDemuxGenerate", "dropOne");
                this.c();
                return;
            }
            this.b(this.l = s);
        }
    }
    
    private void i() {
        if (!this.d.e()) {
            TXCLog.i("VideoJoinDecAndDemuxGenerate", "nextAudioExtractorConfig isAllReadEOF");
            return;
        }
        this.k = this.d.c();
        this.r += this.k.j();
    }
    
    private void j() {
        if (!this.d.d()) {
            TXCLog.i("VideoJoinDecAndDemuxGenerate", "nextVideoExtractorConfig isAllReadEOF");
            return;
        }
        this.j = this.d.b();
        this.q += this.j.j();
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "Duration :" + this.k.j());
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "AudioDuration :" + this.j.i() + ", VideoDuration:" + this.j.h());
    }
    
    private void k() {
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "startVideoDecoder");
        final List<i> a = this.d.a();
        for (int i = 0; i < a.size(); ++i) {
            final i j = a.get(i);
            j.c();
            j.k();
        }
        this.j = this.d.b();
        this.q = this.j.j();
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "Duration :" + this.j.j());
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "AudioDuration :" + this.j.i() + ", VideoDuration:" + this.j.h());
    }
    
    private void l() {
        TXCLog.i("VideoJoinDecAndDemuxGenerate", "stopVideoDecoder");
        final List<i> a = this.d.a();
        for (int i = 0; i < a.size(); ++i) {
            a.get(i).l();
        }
    }
    
    private void a(final e e) {
        if (this.c != null) {
            this.c.a(e, this.k);
        }
    }
    
    private void b(final e e) {
        if (this.b != null) {
            this.b.a(e, this.j);
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
                    com.tencent.liteav.g.l.this.d();
                    com.tencent.liteav.g.l.this.i.sendEmptyMessage(202);
                    break;
                }
                case 202: {
                    com.tencent.liteav.g.l.this.f();
                }
                case 203: {
                    com.tencent.liteav.g.l.this.i.removeMessages(202);
                    com.tencent.liteav.g.l.this.e();
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
                    com.tencent.liteav.g.l.this.k();
                    com.tencent.liteav.g.l.this.h.sendEmptyMessage(102);
                    break;
                }
                case 102: {
                    com.tencent.liteav.g.l.this.h();
                    break;
                }
                case 104: {
                    com.tencent.liteav.g.l.this.h.removeMessages(102);
                    break;
                }
                case 103: {
                    com.tencent.liteav.g.l.this.h.removeMessages(102);
                    com.tencent.liteav.g.l.this.l();
                    break;
                }
            }
        }
    }
}
