package com.tencent.liteav.j;

import com.tencent.liteav.CaptureAndEnc;

import java.util.concurrent.atomic.*;

public class e
{
    private static e a;
    private AtomicLong b;
    private AtomicLong c;
    private AtomicLong d;
    private AtomicLong e;
    private AtomicLong f;
    private AtomicLong g;
    
    public static e a() {
        if (e.a == null) {
            e.a = new e();
        }
        return e.a;
    }
    
    private e() {
        this.b = new AtomicLong(0L);
        this.c = new AtomicLong(0L);
        this.d = new AtomicLong(0L);
        this.e = new AtomicLong(0L);
        this.f = new AtomicLong(0L);
        this.g = new AtomicLong(0L);
    }
    
    public static long a(final CaptureAndEnc.e e) {
        long n;
        if (g.a().b()) {
            n = e.u();
        }
        else if (com.tencent.liteav.f.g.a().c()) {
            n = e.t();
        }
        else {
            n = e.e();
        }
        return n;
    }
    
    public void a(final long n) {
        this.b.set(n);
    }
    
    public void b(final long n) {
        this.c.set(n);
    }
    
    public void c(final long n) {
        this.d.set(n);
    }
    
    public void d(final long n) {
        this.e.set(n);
    }
    
    public void e(final long n) {
        this.f.set(n);
    }
    
    public void f(final long n) {
        this.g.set(n);
    }
    
    public void b() {
        this.b.set(0L);
        this.c.set(0L);
        this.d.set(0L);
        this.e.set(0L);
        this.f.set(0L);
        this.g.set(0L);
    }
}
