package com.tencent.liteav.b;

import android.annotation.*;
import android.util.*;

import java.util.concurrent.atomic.*;
import java.util.*;
import android.view.*;

import java.util.concurrent.*;
import android.media.*;
import com.tencent.liteav.basic.log.*;

@TargetApi(16)
public class m
{
    private LongSparseArray<e> a;
    private LongSparseArray<e> b;
    private AtomicBoolean c;
    private AtomicBoolean d;
    private LinkedList<e> e;
    private Surface f;
    private com.tencent.liteav.b.e g;
    private com.tencent.liteav.g.e h;
    private l i;
    private f j;
    private e k;
    private e l;
    private ArrayBlockingQueue<e> m;
    private ArrayBlockingQueue<e> n;
    
    public m() {
        this.h = new com.tencent.liteav.g.e();
        this.a = (LongSparseArray<e>)new LongSparseArray();
        this.b = (LongSparseArray<e>)new LongSparseArray();
        this.c = new AtomicBoolean(false);
        this.d = new AtomicBoolean(false);
        (this.e = new LinkedList<e>()).clear();
    }
    
    public int a(final String s) {
        final int a = this.h.a(s);
        if (a < 0) {
            return a;
        }
        return 0;
    }
    
    public void a(final Surface f) {
        this.f = f;
    }
    
    public MediaFormat a() {
        return this.h.m();
    }
    
    public int b() {
        return this.h.b();
    }
    
    public int c() {
        return this.h.c();
    }
    
    public int d() {
        return this.h.e();
    }
    
    public int e() {
        final MediaFormat m = this.h.m();
        if (m.containsKey("sample-rate")) {
            return m.getInteger("sample-rate");
        }
        return 0;
    }
    
    public int f() {
        final MediaFormat m = this.h.m();
        if (m.containsKey("max-input-size")) {
            return m.getInteger("max-input-size");
        }
        return 0;
    }
    
    public void a(final com.tencent.liteav.b.e g) {
        this.g = g;
    }
    
    public void g() {
        this.i = new l();
        this.j = new f();
        final MediaFormat m = this.h.m();
        this.j.a(m);
        this.j.a(m, null);
        this.j.a();
        this.i.a(this.h.l());
        this.i.a(this.h.l(), this.f);
        this.i.a();
    }
    
    public void h() {
        if (this.j != null) {
            this.j.b();
        }
        if (this.i != null) {
            this.i.b();
        }
        if (this.e != null) {
            this.e.clear();
        }
        if (this.b != null) {
            this.b.clear();
        }
        if (this.a != null) {
            this.a.clear();
        }
        this.h.o();
        this.c.compareAndSet(true, false);
        this.d.compareAndSet(true, false);
    }
    
    public void i() throws InterruptedException {
        this.j();
        this.k();
        this.l();
        this.m();
    }
    
    private void j() throws InterruptedException {
        if (this.c.get()) {
            TXCLog.d("TXReaderLone", "mReadVideoEOF, ignore");
            return;
        }
        final e c = this.i.c();
        if (c == null) {
            return;
        }
        final e a = this.h.a(c);
        if (a != null) {
            if (this.h.c(a)) {
                this.c.compareAndSet(false, true);
                TXCLog.i("TXReaderLone", "==TXReaderLone Read Video End===");
            }
            this.b.put(a.e(), (Object)a);
            this.i.a(a);
        }
    }
    
    private void k() throws InterruptedException {
        if (this.d.get()) {
            return;
        }
        final e c = this.j.c();
        if (c == null) {
            return;
        }
        final e b = this.h.b(c);
        if (b != null) {
            if (this.h.d(b)) {
                this.d.compareAndSet(false, true);
                TXCLog.i("TXReaderLone", "==TXReaderLone Read Audio End===");
            }
            this.a.put(b.e(), (Object)b);
            this.j.a(b);
        }
    }
    
    private void l() {
        if (this.e.size() == 0) {
            if (this.m != null && this.m.size() > 0) {
                TXCLog.d("TXReaderLone", "decodeVideoFrame, ignore because mVideoBlockingQueue.size() = " + this.m.size());
                return;
            }
            final e d = this.i.d();
            if (d == null) {
                return;
            }
            if (d.o() == null) {
                return;
            }
            if (this.k == null) {
                this.k = d;
            }
            e a = d;
            final e e = (e)this.b.get(d.e());
            if (e != null) {
                a = this.i.a(e, d);
            }
            if ((a.o().flags & 0x4) != 0x0) {
                TXCLog.i("TXReaderLone", "==TXReaderLone Decode Video End===");
            }
            this.e.add(a);
        }
        if (this.e.size() <= 0) {
            return;
        }
        final e k = this.e.get(0);
        if (this.k == null) {
            this.k = k;
        }
        final e i = k;
        if (this.g != null) {
            this.g.b(i);
        }
        if (!this.e.isEmpty() && this.e.size() > 0) {
            this.e.remove(0);
        }
        this.k = i;
    }
    
    private void m() {
        if (this.n != null && this.n.size() > 9) {
            TXCLog.d("TXReaderLone", "decodeAudioFrame, ignore because mAudioBlockingQueue size = " + this.n.size());
            return;
        }
        final e d = this.j.d();
        if (d == null) {
            return;
        }
        if (d.o() == null) {
            return;
        }
        e a = d;
        final e e = (e)this.a.get(d.e());
        if (e != null) {
            a = this.j.a(e, d);
        }
        if (a == null) {
            TXCLog.e("TXReaderLone", "decodeAudioFrame, fixFrame is null, sampleTime = " + d.e());
            return;
        }
        if ((a.o().flags & 0x4) != 0x0) {
            TXCLog.i("TXReaderLone", "==TXReaderLone Decode Audio End===");
        }
        if (this.l == null) {
            this.l = d;
        }
        final e l = a;
        if (this.g != null) {
            this.g.a(l);
        }
        this.l = l;
    }
    
    public void a(final ArrayBlockingQueue<e> m) {
        this.m = m;
    }
    
    public void b(final ArrayBlockingQueue<e> n) {
        this.n = n;
    }
}
