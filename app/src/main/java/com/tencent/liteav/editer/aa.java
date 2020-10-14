package com.tencent.liteav.editer;

import android.annotation.*;
import android.util.*;

import com.tencent.liteav.CameraProxy;

import java.util.concurrent.atomic.*;
import com.tencent.liteav.basic.log.*;

import android.os.*;

@TargetApi(16)
public class aa extends c
{
    private final String k = "VideoDecAndDemuxPreview";
    private LongSparseArray<e> l;
    private LongSparseArray<e> m;
    private b n;
    private HandlerThread o;
    private a p;
    private HandlerThread q;
    private AtomicBoolean r;
    private AtomicBoolean s;
    private AtomicBoolean t;
    private AtomicBoolean u;
    private AtomicInteger v;
    private e w;
    private e x;
    private volatile boolean y;
    private long z;
    private int A;
    private long B;
    private long C;
    private int D;
    private boolean E;
    private long F;
    private int G;
    private long H;
    private long I;
    private long J;
    private long K;
    private long L;
    private AtomicBoolean M;
    private e N;
    private long O;
    private AtomicBoolean P;
    private long Q;
    private AtomicBoolean R;
    
    public aa() {
        this.y = true;
        this.z = -1L;
        this.B = 0L;
        this.C = 0L;
        this.E = false;
        this.H = -1L;
        this.I = -1L;
        this.J = -1L;
        this.K = -1L;
        this.L = -1L;
        this.O = -1L;
        this.m = (LongSparseArray<e>)new LongSparseArray();
        this.l = (LongSparseArray<e>)new LongSparseArray();
        this.g = new AtomicLong(0L);
        this.h = new AtomicLong(0L);
        this.v = new AtomicInteger(1);
        this.r = new AtomicBoolean(false);
        this.s = new AtomicBoolean(false);
        this.R = new AtomicBoolean(false);
        this.t = new AtomicBoolean(false);
        this.u = new AtomicBoolean(false);
        this.M = new AtomicBoolean(false);
        this.P = new AtomicBoolean(false);
        (this.o = new HandlerThread("previewVDec")).start();
        this.n = new b(this.o.getLooper());
        (this.q = new HandlerThread("previewADec")).start();
        this.p = new a(this.q.getLooper());
    }
    
    public synchronized void l() {
        TXCLog.i("VideoDecAndDemuxPreview", "start(), mCurrentState = " + this.v);
        if (this.v.get() == 2) {
            TXCLog.e("VideoDecAndDemuxPreview", "start ignore, mCurrentState = " + this.v.get());
            return;
        }
        this.l.clear();
        this.M.set(false);
        this.R.set(true);
        this.P.getAndSet(false);
        this.r.getAndSet(false);
        this.s.getAndSet(false);
        this.t.getAndSet(false);
        this.u.getAndSet(false);
        this.N = null;
        this.y = true;
        this.H = -1L;
        if (this.v.get() == 3) {
            TXCLog.i("VideoDecAndDemuxPreview", "start(), state pause, seek then send MSG_VIDEO_DECODE_START and MSG_AUDIO_DECODE_START");
            this.v.set(2);
            this.b(this.g.get());
            this.n.sendEmptyMessage(102);
            if (this.h()) {
                this.p.sendEmptyMessage(202);
            }
        }
        else if (this.v.get() == 4) {
            TXCLog.i("VideoDecAndDemuxPreview", "start(), state preview at time, stop then start");
            this.m();
            this.l();
        }
        else {
            TXCLog.i("VideoDecAndDemuxPreview", "start(), state init, seek then send MSG_VIDEO_DECODE_CONFIG and MSG_AUDIO_DECODE_CONFIG");
            this.v.set(2);
            this.b(this.g.get());
            this.n.sendEmptyMessage(101);
            if (this.h()) {
                this.p.sendEmptyMessage(201);
            }
        }
    }
    
    public void m() {
        if (this.v.get() == 1) {
            TXCLog.e("VideoDecAndDemuxPreview", "stop(), mCurrentState in stop, ignore");
            return;
        }
        this.v.set(1);
        TXCLog.i("VideoDecAndDemuxPreview", "stop(), send MSG_VIDEO_DECODE_STOP");
        this.n.sendEmptyMessage(103);
        if (this.h()) {
            TXCLog.i("VideoDecAndDemuxPreview", "stop(), send MSG_AUDIO_DECODE_STOP");
            this.p.sendEmptyMessage(203);
        }
    }
    
    public synchronized void n() {
        final int value = this.v.get();
        if (value == 3 || value == 1) {
            TXCLog.e("VideoDecAndDemuxPreview", "pause ignore, current state = " + value);
            return;
        }
        this.v.set(3);
        TXCLog.i("VideoDecAndDemuxPreview", "pause(), send MSG_VIDEO_DECODE_PAUSE");
        this.n.sendEmptyMessage(104);
        if (this.h()) {
            TXCLog.i("VideoDecAndDemuxPreview", "pause(), send MSG_AUDIO_DECODE_PAUSE");
            this.p.sendEmptyMessage(204);
        }
    }
    
    public synchronized void o() {
        final int value = this.v.get();
        if (value == 1 || value == 2 || value == 4) {
            TXCLog.e("VideoDecAndDemuxPreview", "resume ignore, state = " + value);
            return;
        }
        this.v.set(2);
        TXCLog.i("VideoDecAndDemuxPreview", "resume(), send MSG_VIDEO_DECODE_START");
        this.n.sendEmptyMessage(102);
        if (this.h()) {
            TXCLog.i("VideoDecAndDemuxPreview", "resume(), send MSG_AUDIO_DECODE_START");
            this.p.sendEmptyMessage(202);
        }
    }
    
    public void a(final long n) {
        this.Q = n * 1000L;
        if (this.v.get() == 3 || this.v.get() == 4) {
            TXCLog.d("VideoDecAndDemuxPreview", "previewAtTime, state = " + this.v.get() + ", send MSG_VIDEO_DECODE_PREVIEW_START");
            this.v.set(4);
            this.n.removeMessages(5);
            this.n.sendEmptyMessage(5);
        }
        else {
            TXCLog.d("VideoDecAndDemuxPreview", "previewAtTime, state = " + this.v.get() + ", send MSG_VIDEO_DECODE_PREVIEW_CONFIG");
            this.v.set(4);
            synchronized (this) {
                this.r();
            }
            this.n.sendEmptyMessage(6);
        }
    }
    
    public void a(final boolean y) {
        this.y = y;
    }
    
    public synchronized void a(final long n, final long n2) {
        this.g.getAndSet(n);
        this.h.getAndSet(n2);
        this.r();
    }
    
    private void r() {
        this.N = null;
        this.x = null;
        this.K = -1L;
        this.L = -1L;
        this.I = -1L;
        this.J = -1L;
        this.M.set(false);
        TXCLog.i("VideoDecAndDemuxPreview", "avsync video frame reset first systime " + this.J);
        this.b(this.B, this.C);
    }
    
    public void b(final long b, final long c) {
        if (b == 0L && c == 0L) {
            this.D = 0;
        }
        else {
            this.D = 3;
        }
        this.B = b;
        this.C = c;
        this.E = false;
    }
    
    public int p() {
        return this.a.f();
    }
    
    @TargetApi(18)
    @Override
    public void k() {
        this.m();
        if (this.o != null) {
            this.o.quitSafely();
        }
        if (this.q != null) {
            this.q.quitSafely();
        }
        if (this.l != null) {
            this.l.clear();
        }
        if (this.m != null) {
            this.m.clear();
        }
        this.w = null;
        this.x = null;
    }
    
    private e b(final e e) {
        if (!com.tencent.liteav.f.g.a().c()) {
            e.b(e.e());
            return e;
        }
        if (this.x == null) {
            TXCLog.i("VideoDecAndDemuxPreview", "processSpeedFrame, mLastVideoFrame is null, this is first frame, not to speed");
            return e;
        }
        if (e.p()) {
            TXCLog.i("VideoDecAndDemuxPreview", "processSpeedFrame, this frame is end frame, not to speed");
            return e;
        }
        final long n = this.x.t() + (long)((e.e() - this.x.e()) / com.tencent.liteav.f.g.a().a(e.e()));
        e.b(n);
        this.K = n / 1000L;
        return e;
    }
    
    private synchronized void b(final long n) {
        if (this.P.get()) {
            TXCLog.e("VideoDecAndDemuxPreview", "seekPosition, had seeked");
            return;
        }
        TXCLog.d("VideoDecAndDemuxPreview", "======================start seek video and audio starting point=====================mStartTime = " + this.g);
        this.a.a(n);
        final long p = this.a.p();
        this.a.c(p);
        final long q = this.a.q();
        TXCLog.d("VideoDecAndDemuxPreview", "======================seek end=====================");
        TXCLog.d("VideoDecAndDemuxPreview", "==============seekTime==========" + this.g);
        TXCLog.d("VideoDecAndDemuxPreview", "==============startVdts==========" + p);
        TXCLog.d("VideoDecAndDemuxPreview", "==============startAdts==========" + q);
        this.P.getAndSet(true);
    }
    
    private void c(final e x) {
        if (this.f != null) {
            this.f.a(x);
        }
        this.x = x;
    }
    
    private e s() {
        final e d = this.b.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        e n = d;
        if (n == null) {
            return null;
        }
        final e e = (e)this.l.get(d.e());
        if (e != null) {
            n = this.b.a(e, d);
            if (CameraProxy.g.a().b()) {
                n.a(e.v());
            }
        }
        else {
            n.j(this.d());
            n.k(this.e());
        }
        if (n.e() < this.g.get() && !n.p()) {
            TXCLog.d("VideoDecAndDemuxPreview", "VideoFrame pts :" + n.e() + " before  startTime (" + this.g + ")");
            return null;
        }
        if (n.e() > this.h.get()) {
            TXCLog.d("VideoDecAndDemuxPreview", "VideoFrame pts :" + n.e() + " after  duration (" + this.h + ")");
            if (CameraProxy.g.a().b()) {
                return null;
            }
            n = this.b.b(n);
        }
        if (n.p()) {
            this.t.getAndSet(true);
            TXCLog.d("VideoDecAndDemuxPreview", "==================preview decode Video END==========================");
            if (!this.u.get()) {
                TXCLog.d("VideoDecAndDemuxPreview", "-------------- preview Audio NOT END ----------------");
                return n;
            }
            TXCLog.d("VideoDecAndDemuxPreview", "================== VIDEO SEND END OF FILE ==========================" + n.toString());
        }
        this.N = n;
        this.K = this.N.e() / 1000L;
        return n;
    }
    
    private boolean t() {
        this.L = System.currentTimeMillis();
        this.K = this.N.t() / 1000L;
        return Math.abs(this.K - this.I) < this.L - this.J;
    }
    
    private synchronized void u() throws InterruptedException {
        if (this.r.get()) {
            TXCLog.d("VideoDecAndDemuxPreview", "readVideoFrame, read video end of file, ignore");
            return;
        }
        final e c = this.b.c();
        if (c == null) {
            return;
        }
        if ((this.D == 3 || this.D == 2) && this.g.get() <= this.B && this.a.r() >= this.C) {
            this.a.a(this.B);
            --this.D;
            this.E = true;
        }
        final e a = this.a.a(c);
        if (this.A <= 0) {
            this.A = this.j();
            if (this.A != 0) {
                this.G = 1000 / this.A * 1000;
            }
        }
        if (a != null) {
            if (this.E) {
                a.a(this.F + this.G);
            }
            this.F = a.e();
            if (this.H < 0L) {
                this.H = this.F;
            }
            boolean b;
            if (CameraProxy.g.a().b()) {
                if (a.p()) {
                    this.F = this.a(a);
                    this.H = this.F;
                }
                b = this.a(this.F, this.G, a);
                if (!b) {
                    final long abs = Math.abs(this.H - this.F);
                    TXCLog.d("VideoDecAndDemuxPreview", "reverse newVPts = " + abs + ", mFirstVideoReadPTS = " + this.H + ", curFixFrame.getSampleTime() = " + this.F);
                    a.a(abs);
                    a.c(abs);
                    a.d(this.F);
                }
            }
            else {
                b = this.a.c(a);
            }
            if (b) {
                this.r.set(true);
                TXCLog.i("VideoDecAndDemuxPreview", "read video end");
            }
            this.l.put(a.e(), (Object)a);
            this.b.a(a);
        }
    }
    
    private synchronized void v() {
        if (this.s.get()) {
            return;
        }
        final e c = this.c.c();
        if (c == null) {
            return;
        }
        final e b = this.a.b(c);
        if (b != null) {
            if (this.a.d(b)) {
                this.s.set(true);
                TXCLog.d("VideoDecAndDemuxPreview", "audio endOfFile:" + this.s.get());
                TXCLog.i("VideoDecAndDemuxPreview", "read audio end");
            }
            this.m.put(b.e(), (Object)b);
            this.c.a(b);
        }
    }
    
    private synchronized void w() {
        final e d = this.c.d();
        if (d == null) {
            return;
        }
        if (d.o() == null) {
            return;
        }
        e w = d;
        final e e = (e)this.m.get(d.e());
        if (e != null) {
            w = this.c.a(e, d);
        }
        if (w == null) {
            return;
        }
        if (w.e() < this.g.get() && !w.p()) {
            TXCLog.d("VideoDecAndDemuxPreview", "AudioFrame pts :" + w.e() + " before  startTime (" + this.g + ")");
            return;
        }
        if (w.e() > this.h.get()) {
            TXCLog.d("VideoDecAndDemuxPreview", "AudioFrame pts :" + w.e() + " after  duration (" + this.h + ")");
            w = this.c.b(w);
        }
        if (w.p()) {
            this.u.set(true);
            TXCLog.d("VideoDecAndDemuxPreview", "==================preview decode Audio END==========================");
            if (!this.t.get()) {
                TXCLog.d("VideoDecAndDemuxPreview", "-------------- preview VIDEO NOT END ----------------");
                return;
            }
            TXCLog.d("VideoDecAndDemuxPreview", "================== AUDIO SEND END OF FILE ==========================" + w.toString());
        }
        if (this.w == null) {
            this.w = d;
            this.O = System.currentTimeMillis();
            TXCLog.d("VideoDecAndDemuxPreview", "avsync first audio frame ts : " + this.w.e() + ", first systime : " + this.O);
        }
        if (this.z == -1L) {
            this.z = System.currentTimeMillis();
        }
        if (this.e != null) {
            this.e.a(w);
        }
        this.w = w;
        this.z = System.currentTimeMillis();
    }
    
    public boolean q() {
        return this.t.get();
    }
    
    class b extends Handler
    {
        public b(final Looper looper) {
            super(looper);
        }
        
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 101: {
                    TXCLog.i("VideoDecAndDemuxPreview", "normal : configureVideo()");
                    aa.this.a();
                    if (CameraProxy.g.a().b()) {
                        aa.this.a.a(aa.this.h.get());
                        TXCLog.i("VideoDecAndDemuxPreview", "VideoDecodeHandler, reverse, seekVideo time = " + aa.this.h);
                    }
                    aa.this.n.sendEmptyMessage(102);
                    break;
                }
                case 102: {
                    try {
                        if (aa.this.K >= 0L) {
                            if (aa.this.N.p()) {
                                TXCLog.i("VideoDecAndDemuxPreview", "is end video frame, to stop decode");
                                aa.this.c(aa.this.N);
                                aa.this.n.sendEmptyMessage(103);
                                return;
                            }
                            if (aa.this.M.get()) {
                                aa.this.c(aa.this.N);
                            }
                            else {
                                if (!aa.this.t()) {
                                    aa.this.n.sendEmptyMessageDelayed(102, 5L);
                                    return;
                                }
                                aa.this.c(aa.this.N);
                            }
                        }
                        aa.this.u();
                        final e g = aa.this.s();
                        if (g == null) {
                            aa.this.n.sendEmptyMessage(102);
                            return;
                        }
                        aa.this.N = aa.this.b(g);
                        if (aa.this.I < 0L) {
                            aa.this.I = aa.this.K;
                            if (aa.this.O > 0L) {
                                aa.this.J = aa.this.O;
                            }
                            else {
                                aa.this.J = System.currentTimeMillis();
                            }
                            TXCLog.d("VideoDecAndDemuxPreview", "avsync first video frame ts : " + aa.this.I + ", first systime : " + aa.this.J + ", current systime " + System.currentTimeMillis());
                            aa.this.M.set(true);
                            aa.this.n.sendEmptyMessage(102);
                        }
                        else {
                            aa.this.M.compareAndSet(true, false);
                            aa.this.n.sendEmptyMessageDelayed(102, 5L);
                        }
                    }
                    catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    break;
                }
                case 6: {
                    TXCLog.i("VideoDecAndDemuxPreview", "preview at time : configureVideo()");
                    aa.this.a();
                    aa.this.a.a(aa.this.Q);
                    aa.this.n.sendEmptyMessage(5);
                    break;
                }
                case 5: {
                    try {
                        if (aa.this.b != null) {
                            aa.this.b.e();
                        }
                        aa.this.a.a(aa.this.Q);
                        int n = 0;
                        e d;
                        do {
                            ++n;
                            e e = aa.this.b.c();
                            if (e != null) {
                                e = aa.this.a.a(e);
                                if (e != null) {
                                    aa.this.b.a(e);
                                }
                            }
                            d = aa.this.b.d();
                            if (d == null) {
                                if (e == null) {
                                    continue;
                                }
                                aa.this.a.c(e);
                            }
                            else {
                                d.j(aa.this.d());
                                d.k(aa.this.e());
                                d.e(aa.this.p());
                                if (!CameraProxy.g.a().b() || d.e() > aa.this.g.get()) {
                                    continue;
                                }
                                aa.this.b.b(d);
                            }
                        } while (d == null && n < 100);
                        if (d != null) {
                            aa.this.c(d);
                        }
                    }
                    catch (Exception ex2) {
                        ex2.printStackTrace();
                    }
                    break;
                }
                case 104: {
                    TXCLog.i("VideoDecAndDemuxPreview", "video decode pause");
                    aa.this.n.removeMessages(102);
                    synchronized (aa.this) {
                        aa.this.r();
                    }
                    break;
                }
                case 103: {
                    TXCLog.i("VideoDecAndDemuxPreview", "VideoDecodeHandler, video decode stop!");
                    if (aa.this.t.get() && aa.this.u.get()) {
                        aa.this.v.set(1);
                    }
                    aa.this.n.removeMessages(102);
                    synchronized (aa.this) {
                        aa.this.r();
                    }
                    if (aa.this.b != null) {
                        aa.this.b.b();
                        aa.this.b = null;
                        break;
                    }
                    break;
                }
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
                    aa.this.b();
                    aa.this.p.sendEmptyMessage(202);
                    break;
                }
                case 202: {
                    TXCLog.i("VideoDecAndDemuxPreview", "avsync audio frame start AudioDecodeHandler, mCurrentState = " + aa.this.v + ", mAudioDecodeEOF = " + aa.this.u);
                    while (aa.this.v.get() != 1 && !aa.this.u.get()) {
                        try {
                            if (aa.this.v.get() == 3) {
                                aa.this.w = null;
                                aa.this.O = -1L;
                                Thread.sleep(10L);
                            }
                            else {
                                if (!aa.this.y) {
                                    continue;
                                }
                                aa.this.v();
                                aa.this.w();
                            }
                        }
                        catch (InterruptedException ex) {
                            ex.printStackTrace();
                            if (aa.this.v.get() == 1) {
                                break;
                            }
                            continue;
                        }
                    }
                    aa.this.w = null;
                    aa.this.O = -1L;
                    if (aa.this.v.get() == 1) {
                        TXCLog.d("VideoDecAndDemuxPreview", "AudioDecodeHandler, loop decode end state is init, ignore to stop");
                        return;
                    }
                    TXCLog.i("VideoDecAndDemuxPreview", "AudioDecodeHandler, in MSG_AUDIO_DECODE_START, send MSG_AUDIO_DECODE_STOP");
                    aa.this.p.sendEmptyMessage(203);
                    break;
                }
                case 204: {
                    aa.this.w = null;
                    aa.this.O = -1L;
                    aa.this.p.removeMessages(202);
                    break;
                }
                case 203: {
                    TXCLog.i("VideoDecAndDemuxPreview", "AudioDecodeHandler, audio decode stop!");
                    aa.this.p.removeMessages(202);
                    if (aa.this.c != null) {
                        aa.this.c.b();
                        aa.this.c = null;
                        break;
                    }
                    break;
                }
            }
        }
    }
}
