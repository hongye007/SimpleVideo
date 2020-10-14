package com.tencent.liteav.editer;

import android.annotation.*;
import android.view.*;
import java.util.concurrent.atomic.*;
import java.io.*;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.basic.log.*;

import android.media.*;

@TargetApi(16)
public class c
{
    private final String k = "BasicVideoDecDemux";
    protected e a;
    protected af b;
    protected f c;
    protected Surface d;
    protected h e;
    protected m f;
    protected AtomicLong g;
    protected AtomicLong h;
    protected int i;
    private int l;
    protected String j;
    
    public int a(final String j) throws IOException {
        this.j = j;
        this.a = new e();
        final int a = this.a.a(j);
        if (a != -1002 && a != 0) {
            return a;
        }
        this.l = 0;
        return a;
    }
    
    public void a(final m f) {
        this.f = f;
    }
    
    public void a(final h e) {
        this.e = e;
    }
    
    protected void a() {
        TXCLog.i("BasicVideoDecDemux", "configureVideo");
        this.b = new af();
        final MediaFormat l = this.a.l();
        this.b.a(new b.a() {
            @Override
            public void a(final String s) {
                final m f = com.tencent.liteav.editer.c.this.f;
                if (f != null) {
                    f.a(s);
                }
            }
        });
        this.b.a(l);
        this.b.a(this.a.l(), this.d);
        this.b.a();
    }
    
    protected void b() {
        TXCLog.i("BasicVideoDecDemux", "configureAudio");
        this.c = new f();
        final MediaFormat m = this.a.m();
        this.c.a(m);
        this.c.a(m, null);
        this.c.a();
    }
    
    public synchronized void a(final Surface d) {
        this.d = d;
    }
    
    public long c() {
        return this.a.a();
    }
    
    public int d() {
        return this.a.b();
    }
    
    public int e() {
        return this.a.c();
    }
    
    public MediaFormat f() {
        return this.a.m();
    }
    
    public MediaFormat g() {
        return this.a.l();
    }
    
    public boolean h() {
        return this.a != null && this.a.m() != null;
    }
    
    public boolean i() {
        return this.a.l() != null;
    }
    
    protected long a(final CaptureAndEnc.e e) {
        TXCLog.i("BasicVideoDecDemux", "seekFinalVideo, read is end frame, try to find final video frame(not end frame)");
        int i = 1;
        long e2 = 0L;
        final long n = 1000 / this.j() * 1000;
        long n2 = this.a.j();
        if (n2 <= 0L) {
            n2 = this.h.get();
        }
        while (i <= 3) {
            long n3 = n2 - n * i;
            if (n3 < 0L) {
                n3 = n2;
            }
            this.a.a(n3);
            this.a.a(e);
            TXCLog.d("BasicVideoDecDemux", "seekReversePTS, seek End PTS = " + n3 + ", flags = " + e.f() + ", seekEndCount = " + i);
            if (!e.p()) {
                e2 = e.e();
                break;
            }
            ++i;
        }
        return e2;
    }
    
    protected boolean a(final long n, final long n2, final CaptureAndEnc.e e) {
        if (n <= this.g.get()) {
            TXCLog.i("BasicVideoDecDemux", "seekReversePTS, lastReadPTS <= mStartTime");
            this.a.a(n);
            ++this.i;
            if (this.i >= 2) {
                this.b.b(e);
                return true;
            }
            return false;
        }
        else {
            final long n3 = n - 1000L;
            this.a.a(n3);
            final long p3 = this.a.p();
            if (p3 < n) {
                TXCLog.i("BasicVideoDecDemux", "seekReversePTS, seekPTS = " + n3 + ", find previous pts = " + p3);
                return false;
            }
            int n4 = 1;
            do {
                long n5 = n - (n2 * n4 + 1000L);
                if (n5 < 0L) {
                    n5 = 0L;
                }
                this.a.a(n5);
                final long p4 = this.a.p();
                TXCLog.i("BasicVideoDecDemux", "seekReversePTS, may be SEEK_TO_PREVIOUS_SYNC same to NEXT_SYNC, seekPTS = " + n5 + ", find previous pts = " + p4 + ", count = " + n4);
                if (p4 < n) {
                    return false;
                }
            } while (++n4 <= 10);
            this.b.b(e);
            return true;
        }
    }
    
    public int j() {
        if (this.l != 0) {
            return this.l;
        }
        final MediaFormat l = this.a.l();
        try {
            if (l != null) {
                this.l = l.getInteger("frame-rate");
            }
        }
        catch (NullPointerException ex) {
            this.l = 20;
        }
        return this.l;
    }
    
    public void k() {
        if (this.a != null) {
            this.a.o();
        }
    }
}
