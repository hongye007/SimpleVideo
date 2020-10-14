package com.tencent.liteav.editer;

import android.annotation.*;

import com.tencent.liteav.CameraProxy;

import java.util.concurrent.atomic.*;
import com.tencent.liteav.basic.log.*;

import java.util.*;
import android.os.*;

@TargetApi(16)
public abstract class d extends c
{
    private final String Y = "BasicVideoDecDemuxGenerater";
    protected final int k = 5;
    protected final int l = 6;
    protected final int m = 7;
    protected final int n = 8;
    protected final int o = 9;
    protected final int p = 10;
    protected final int q = 11;
    protected final int r = 12;
    protected final int s = 13;
    protected final int t = 14;
    protected Vector<e> u;
    protected Vector<e> v;
    protected AtomicBoolean w;
    protected AtomicBoolean x;
    protected AtomicBoolean y;
    protected AtomicBoolean z;
    protected AtomicInteger A;
    protected b B;
    protected HandlerThread C;
    protected a D;
    protected HandlerThread E;
    protected volatile boolean F;
    protected e G;
    protected e H;
    protected int I;
    protected long J;
    protected long K;
    protected int L;
    protected boolean M;
    protected long N;
    protected int O;
    protected long P;
    protected long Q;
    protected long R;
    protected AtomicBoolean S;
    protected AtomicBoolean T;
    protected List<Long> U;
    protected int V;
    protected int W;
    private long Z;
    protected AtomicBoolean X;
    private com.tencent.liteav.g.e aa;
    private long ab;
    private long ac;
    
    public d() {
        this.F = true;
        this.J = 0L;
        this.K = 0L;
        this.M = false;
        this.P = -1L;
        this.Q = -1L;
        this.R = -1L;
        this.Z = -1L;
        this.v = new Vector<e>();
        this.u = new Vector<e>();
        this.g = new AtomicLong(0L);
        this.h = new AtomicLong(0L);
        this.A = new AtomicInteger(1);
        this.w = new AtomicBoolean(false);
        this.x = new AtomicBoolean(false);
        this.y = new AtomicBoolean(false);
        this.z = new AtomicBoolean(false);
        this.S = new AtomicBoolean(false);
        this.T = new AtomicBoolean(false);
    }
    
    protected abstract void l();
    
    protected abstract void m();
    
    public int n() {
        return this.a.f();
    }
    
    public abstract void a(final boolean p0);
    
    private void q() {
        (this.aa = new com.tencent.liteav.g.e()).a(this.j);
    }
    
    private e b(final e e) {
        if (!CameraProxy.g.a().b()) {
            return e;
        }
        if (this.R < 0L) {
            this.R = e.e();
        }
        this.P = e.e();
        final long n = this.P - this.R;
        TXCLog.i("BasicVideoDecDemuxGenerater", "processReverseAudioFrame newVPts = " + n + ", mFirstAudioFramePTS = " + this.R + ", curAudioFrame pts = " + this.P);
        e.a(n);
        return e;
    }
    
    private void c(final e h) {
        if (this.f != null) {
            this.f.a(h);
        }
        this.H = h;
    }
    
    private void d(final e e) {
        if (this.e != null) {
            this.e.a(e);
        }
    }
    
    private e e(final e e) {
        if (this.H == null) {
            TXCLog.i("BasicVideoDecDemuxGenerater", "processSpeedFrame, mLastVideoFrame is null");
            return e;
        }
        if (e.p()) {
            return e;
        }
        if (!com.tencent.liteav.f.g.a().c()) {
            return e;
        }
        e.b(this.H.t() + (long)((e.e() - this.H.e()) / com.tencent.liteav.f.g.a().a(e.e())));
        return e;
    }
    
    protected void a(final long n) {
        if (this.T.get()) {
            TXCLog.e("BasicVideoDecDemuxGenerater", "seekPosition, had seeked");
            return;
        }
        TXCLog.d("BasicVideoDecDemuxGenerater", "======================prepare to seek video & audio starting point=====================mStartTime = " + this.g);
        this.a.a(n);
        final long p = this.a.p();
        this.a.c(p);
        final long q = this.a.q();
        TXCLog.d("BasicVideoDecDemuxGenerater", "==============startVdts==========" + p);
        TXCLog.d("BasicVideoDecDemuxGenerater", "==============startAdts==========" + q);
        this.T.getAndSet(true);
    }
    
    private e r() {
        final e d = this.b.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        if (d.p()) {
            TXCLog.i("BasicVideoDecDemuxGenerater", "getDecodeVideoFrame, is end frame");
            d.j(this.d());
            d.k(this.e());
            this.y.getAndSet(true);
            return d;
        }
        if (this.u.size() == 0) {
            return d;
        }
        final e e = this.u.remove(0);
        e e2;
        if (e != null) {
            e2 = this.b.a(e, d);
        }
        else {
            e2 = CameraProxy.k.a().h();
        }
        if (e2 == null) {
            return null;
        }
        long n;
        if (CameraProxy.g.a().b()) {
            e2.a(e.v());
            e2.c(e.u());
            n = e.v();
        }
        else {
            n = e2.e();
        }
        if (n < this.g.get()) {
            TXCLog.d("BasicVideoDecDemuxGenerater", "VideoFrame pts :" + n + " before  startTime (" + this.g + ")");
            return null;
        }
        if (n > this.h.get()) {
            TXCLog.d("BasicVideoDecDemuxGenerater", "VideoFrame pts :" + n + " after  duration (" + this.h + ")");
            if (CameraProxy.g.a().b()) {
                return null;
            }
            e2 = this.b.b(e2);
        }
        if (e2.p()) {
            this.y.getAndSet(true);
            TXCLog.d("BasicVideoDecDemuxGenerater", "==================generate decode Video END==========================");
            if (!this.z.get()) {
                TXCLog.d("BasicVideoDecDemuxGenerater", "-------------- generate Audio NOT END ----------------");
                return e2;
            }
            TXCLog.d("BasicVideoDecDemuxGenerater", "================== VIDEO SEND END OF FILE ==========================" + e2.toString());
        }
        return e2;
    }
    
    private void s() {
        if (this.w.get()) {
            TXCLog.e("BasicVideoDecDemuxGenerater", "readVideoFrame, read video end of file, ignore");
            return;
        }
        final e c = this.b.c();
        if (c == null) {
            return;
        }
        if ((this.L == 3 || this.L == 2) && this.a.r() >= this.K) {
            this.a.a(this.J);
            --this.L;
            this.M = true;
        }
        final e a = this.a.a(c);
        if (this.I <= 0) {
            this.I = this.j();
            if (this.I != 0) {
                this.O = 1000 / this.I * 1000;
            }
        }
        if (a != null) {
            if (this.M) {
                a.a(this.N + this.O);
            }
            this.N = a.e();
            if (this.Q < 0L) {
                this.Q = this.N;
            }
            boolean b;
            if (CameraProxy.g.a().b()) {
                if (a.p()) {
                    this.N = this.a(a);
                    this.Q = this.N;
                }
                b = this.a(this.N, this.O, a);
                if (!b) {
                    final long abs = Math.abs(this.Q - this.N);
                    TXCLog.d("BasicVideoDecDemuxGenerater", "reverse newVPts = " + abs + ", mFirstVideoFramePTS = " + this.Q + ", curFixFrame.getSampleTime() = " + this.N);
                    a.a(abs);
                    a.c(abs);
                    a.d(this.N);
                }
            }
            else {
                b = this.a.c(a);
            }
            if (b) {
                this.w.set(true);
                TXCLog.i("BasicVideoDecDemuxGenerater", "read video end");
            }
            this.u.add(a);
            this.b.a(a);
        }
    }
    
    private void t() {
        if (this.x.get()) {
            return;
        }
        final e c = this.c.c();
        if (c == null) {
            return;
        }
        final e b = this.a.b(c);
        if (b != null) {
            if (this.a.d(b)) {
                this.x.set(true);
                TXCLog.d("BasicVideoDecDemuxGenerater", "audio endOfFile:" + this.x.get());
                TXCLog.i("BasicVideoDecDemuxGenerater", "read audio end");
            }
            this.v.add(b);
            this.c.a(b);
        }
    }
    
    private e u() {
        final e d = this.c.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        if (this.v.size() == 0) {
            return d;
        }
        e g = d;
        final e e = this.v.remove(0);
        if (e != null) {
            g = this.c.a(e, d);
        }
        if (g == null) {
            return null;
        }
        if (g.e() < this.g.get() && !g.p()) {
            TXCLog.d("BasicVideoDecDemuxGenerater", "AudioFrame pts :" + g.e() + " before  startTime (" + this.g + ")");
            return null;
        }
        if (g.e() > this.h.get()) {
            TXCLog.d("BasicVideoDecDemuxGenerater", "AudioFrame pts :" + g.e() + " after  duration (" + this.h + ")");
            g = this.c.b(g);
        }
        if (g.p()) {
            this.z.set(true);
            TXCLog.d("BasicVideoDecDemuxGenerater", "==================generate decode Audio END==========================");
            if (!this.y.get()) {
                TXCLog.d("BasicVideoDecDemuxGenerater", "-------------- generate VIDEO NOT END ----------------");
                return g;
            }
            TXCLog.d("BasicVideoDecDemuxGenerater", "================== AUDIO SEND END OF FILE ==========================" + g.toString());
        }
        if (this.G == null) {
            this.G = d;
            TXCLog.d("BasicVideoDecDemuxGenerater", "first AUDIO pts:" + this.G.e());
        }
        return this.G = g;
    }
    
    public void a(final long n, final long n2) {
        this.g.getAndSet(n);
        this.h.getAndSet(n2);
    }
    
    public void b(final long j, final long k) {
        if (j == 0L && k == 0L) {
            this.L = 0;
            this.M = false;
        }
        else {
            this.L = 3;
        }
        this.J = j;
        this.K = k;
        this.N = 0L;
    }
    
    public void a(final List<Long> list) {
        TXCLog.i("BasicVideoDecDemuxGenerater", "setVideoGivenPtsList :" + list.size());
        if (this.U != null) {
            this.U.clear();
            this.U.addAll(list);
        }
    }
    
    public boolean o() {
        return this.y.get();
    }
    
    public void p() {
        if (this.A.get() == 1) {
            TXCLog.e("BasicVideoDecDemuxGenerater", "getNextVideoFrame, current state is init, ignore");
            return;
        }
        this.B.sendEmptyMessage(102);
    }
    
    public void b(final boolean f) {
        this.F = f;
    }
    
    protected class b extends Handler
    {
        public b(final Looper looper) {
            super(looper);
        }
        
        public void handleMessage(final Message message) {
            switch (message.what) {
                case 101: {
                    d.this.a();
                    if (CameraProxy.g.a().b()) {
                        d.this.a.a(d.this.h.get());
                        TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, reverse, seekVideo time = " + d.this.h);
                    }
                    d.this.B.sendEmptyMessage(102);
                    break;
                }
                case 102: {
                    try {
                        if (d.this.b == null) {
                            return;
                        }
                        d.this.s();
                        final e b = d.this.r();
                        if (b == null) {
                            d.this.B.sendEmptyMessage(102);
                            return;
                        }
                        final e a = d.this.e(b);
                        if (a.p()) {
                            TXCLog.i("BasicVideoDecDemuxGenerater", "is end video frame, to stop decode, mAudioDecodeEOF = " + d.this.z);
                            if (d.this.h()) {
                                if (d.this.z.get()) {
                                    d.this.c(a);
                                }
                                else {
                                    d.this.H = a;
                                }
                            }
                            else {
                                d.this.c(a);
                            }
                            d.this.B.sendEmptyMessage(103);
                        }
                        else {
                            d.this.c(a);
                        }
                    }
                    catch (Exception ex) {
                        TXCLog.e("BasicVideoDecDemuxGenerater", "video decode failed.", ex);
                    }
                    break;
                }
                case 103: {
                    TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, video decode stop!");
                    this.a(14);
                    break;
                }
                case 105: {
                    this.a(message.arg1);
                    this.getLooper().quit();
                    break;
                }
                case 8: {
                    if (d.this.U == null || d.this.U.size() == 0) {
                        return;
                    }
                    d.this.q();
                    d.this.a();
                    d.this.V = 0;
                    d.this.W = 0;
                    d.this.ab = 0L;
                    d.this.ac = 0L;
                    this.sendEmptyMessage(9);
                    break;
                }
                case 9: {
                    if (d.this.W >= d.this.U.size()) {
                        return;
                    }
                    if (d.this.V >= d.this.U.size()) {
                        this.sendEmptyMessage(11);
                        break;
                    }
                    d.this.Z = d.this.U.get(d.this.V);
                    if (d.this.ab >= d.this.Z) {
                        TXCLog.i("BasicVideoDecDemuxGenerater", "seek lastSyncTime:" + d.this.ac + ",index:" + d.this.V);
                        d.this.a.b(d.this.ac);
                        this.sendEmptyMessage(10);
                        d.this.ab = d.this.ac;
                        break;
                    }
                    d.this.ac = d.this.ab;
                    d.this.aa.b(d.this.ac + 1L);
                    d.this.ab = d.this.aa.p();
                    TXCLog.i("BasicVideoDecDemuxGenerater", "nextSyncTime:" + d.this.ab + ",lastSyncTime" + d.this.ac + ",mGivenPts:" + d.this.Z);
                    if (d.this.ab == -1L || d.this.ab == d.this.ac) {
                        d.this.ab = d.this.ac;
                        TXCLog.i("BasicVideoDecDemuxGenerater", "seek lastSyncTime:" + d.this.ac + ",index:" + d.this.V);
                        this.sendEmptyMessage(10);
                        return;
                    }
                    this.sendEmptyMessage(9);
                    break;
                }
                case 10: {
                    try {
                        if (d.this.b == null) {
                            return;
                        }
                        d.this.s();
                        final e b2 = d.this.r();
                        if (b2 == null) {
                            this.sendEmptyMessageDelayed(10, 5L);
                            return;
                        }
                        b2.j(d.this.d());
                        b2.k(d.this.e());
                        if (b2.p()) {
                            d.this.c(b2);
                            this.sendEmptyMessage(11);
                        }
                        else if (b2.e() >= d.this.Z) {
                            d.this.c(b2);
                            final d a2 = d.this;
                            ++a2.V;
                            this.sendEmptyMessage(9);
                        }
                        else {
                            this.sendEmptyMessage(10);
                        }
                    }
                    catch (Exception ex2) {
                        TXCLog.e("BasicVideoDecDemuxGenerater", "decode thumbnail failed.", ex2);
                    }
                    break;
                }
                case 11: {
                    d.this.B.removeMessages(10);
                    if (d.this.b != null) {
                        d.this.b.b();
                        d.this.b = null;
                    }
                    d.this.A.set(1);
                    break;
                }
                case 5: {
                    if (d.this.U == null || d.this.U.size() == 0) {
                        return;
                    }
                    d.this.a();
                    d.this.V = 0;
                    d.this.W = 0;
                    d.this.B.sendEmptyMessage(6);
                    break;
                }
                case 6: {
                    if (d.this.W >= d.this.U.size()) {
                        return;
                    }
                    if (d.this.Z < 0L) {
                        if (d.this.V < d.this.U.size()) {
                            d.this.Z = d.this.U.get(d.this.V);
                            d.this.a.a(d.this.Z);
                            TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, get pts = " + d.this.Z + ", mVideoGivenPtsInputIndex = " + d.this.V);
                        }
                        else {
                            d.this.Z = 0L;
                        }
                    }
                    if (d.this.Z >= 0L) {
                        final e c = d.this.b.c();
                        if (c != null) {
                            if (d.this.V < d.this.U.size()) {
                                final d a3 = d.this;
                                ++a3.V;
                                final e a4 = d.this.a.a(c);
                                if (a4 != null) {
                                    d.this.b.a(a4);
                                }
                                TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, freeFrame exist");
                            }
                            else {
                                TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, isReadGivenTimeEnd, set end flag");
                                c.a(0L);
                                c.c(4);
                                d.this.b.a(c);
                            }
                            d.this.Z = -1L;
                        }
                    }
                    if (!d.this.X.get()) {
                        d.this.B.sendEmptyMessageDelayed(6, 5L);
                        return;
                    }
                    final e d = com.tencent.liteav.editer.d.this.b.d();
                    if (d == null) {
                        com.tencent.liteav.editer.d.this.B.sendEmptyMessageDelayed(6, 5L);
                        return;
                    }
                    d.e(com.tencent.liteav.editer.d.this.n());
                    d.j(com.tencent.liteav.editer.d.this.d());
                    d.k(com.tencent.liteav.editer.d.this.e());
                    com.tencent.liteav.editer.d.this.c(d);
                    final d a5 = com.tencent.liteav.editer.d.this;
                    ++a5.W;
                    com.tencent.liteav.editer.d.this.X.set(false);
                    if (com.tencent.liteav.editer.d.this.W >= com.tencent.liteav.editer.d.this.U.size()) {
                        TXCLog.i("BasicVideoDecDemuxGenerater", "VideoDecodeHandler, decode end");
                        com.tencent.liteav.editer.d.this.B.sendEmptyMessage(7);
                        break;
                    }
                    com.tencent.liteav.editer.d.this.B.sendEmptyMessageDelayed(6, 5L);
                    break;
                }
                case 7: {
                    d.this.B.removeMessages(6);
                    if (d.this.b != null) {
                        d.this.b.b();
                        d.this.b = null;
                    }
                    d.this.A.set(1);
                    break;
                }
            }
        }
        
        private void a(final int n) {
            if (14 == n) {
                d.this.B.removeMessages(102);
                d.this.Q = -1L;
                d.this.S.set(false);
            }
            if (13 == n) {
                d.this.B.removeMessages(6);
                d.this.A.set(1);
            }
            if (12 == n) {
                d.this.B.removeMessages(10);
                d.this.A.set(1);
            }
            if (d.this.b != null) {
                d.this.b.b();
                d.this.b = null;
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
                    d.this.b();
                    d.this.D.sendEmptyMessage(202);
                    break;
                }
                case 202: {
                    while (d.this.A.get() != 1 && !d.this.z.get()) {
                        if (!d.this.F) {
                            continue;
                        }
                        d.this.t();
                        final e i = d.this.u();
                        if (i == null) {
                            continue;
                        }
                        final e c = d.this.b(i);
                        if (c.p()) {
                            TXCLog.i("BasicVideoDecDemuxGenerater", "is end audio frame, to stop decode, mVideoDecodeEOF = " + d.this.y);
                            if (d.this.y.get()) {
                                d.this.d(c);
                                if (d.this.H.p()) {
                                    d.this.c(d.this.H);
                                }
                            }
                            d.this.D.sendEmptyMessage(203);
                            break;
                        }
                        d.this.d(c);
                    }
                    break;
                }
                case 203: {
                    TXCLog.i("BasicVideoDecDemuxGenerater", "AudioDecodeHandler, audio decode stop!");
                    d.this.D.removeMessages(202);
                    if (d.this.c != null) {
                        d.this.c.b();
                        d.this.c = null;
                        break;
                    }
                    break;
                }
                case 205: {
                    d.this.D.removeMessages(202);
                    if (d.this.c != null) {
                        d.this.c.b();
                        d.this.c = null;
                    }
                    this.getLooper().quit();
                    break;
                }
            }
        }
    }
}
