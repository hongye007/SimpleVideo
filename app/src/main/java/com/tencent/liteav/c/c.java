package com.tencent.liteav.c;

import com.tencent.liteav.basic.log.*;

public class c
{
    private final String a = "CutTimeConfig";
    private static c b;
    private long c;
    private long d;
    private long e;
    private long f;
    
    public c() {
        this.c = -1L;
        this.d = -1L;
        this.e = -1L;
        this.f = -1L;
    }
    
    public static c a() {
        if (c.b == null) {
            synchronized (c.class) {
                if (c.b == null) {
                    c.b = new c();
                }
            }
        }
        return c.b;
    }
    
    public void a(final long c, final long d) {
        if (c < 0L || d < 0L) {
            TXCLog.e("CutTimeConfig", "setCutTimeUs, startTimeUs or endTimeUs < 0");
            return;
        }
        if (c >= d) {
            TXCLog.e("CutTimeConfig", "setCutTimeUs, start time >= end time, ignore");
            return;
        }
        this.c = c;
        this.d = d;
    }
    
    public void b(final long e, final long f) {
        if (e < 0L || f < 0L) {
            TXCLog.e("CutTimeConfig", "setPlayTimeUs, startTimeUs or endTimeUs < 0");
            return;
        }
        if (e >= f) {
            TXCLog.e("CutTimeConfig", "setPlayTimeUs, start time >= end time, ignore");
            return;
        }
        this.e = e;
        this.f = f;
    }
    
    public long b() {
        return this.c;
    }
    
    public long c() {
        return this.d;
    }
    
    public long d() {
        return this.e;
    }
    
    public long e() {
        return this.f;
    }
    
    public long f() {
        if (this.c < 0L) {
            this.c = 0L;
        }
        return this.c;
    }
    
    public long g() {
        if (this.d < 0L) {
            this.d = 0L;
        }
        return this.d;
    }
    
    public void h() {
        this.c = -1L;
        this.d = -1L;
        this.e = -1L;
        this.f = -1L;
    }
}
