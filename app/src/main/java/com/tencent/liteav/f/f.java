package com.tencent.liteav.f;

import java.util.concurrent.*;
import java.util.*;

public class f extends c
{
    private static f d;
    private List<a.e> e;
    private CopyOnWriteArrayList<a.e> f;
    
    public static f a() {
        if (f.d == null) {
            f.d = new f();
        }
        return f.d;
    }
    
    private f() {
        this.f = new CopyOnWriteArrayList<a.e>();
    }
    
    public void a(final List<a.e> e) {
        this.e = e;
        this.b(this.f);
        if (this.c != null) {
            this.a(this.c);
        }
    }
    
    public List<a.e> b() {
        return this.f;
    }
    
    public void a(final e e) {
        if (this.a == 0 || this.b == 0) {
            return;
        }
        if (this.e == null || this.e.size() == 0) {
            return;
        }
        final g b = this.b(e);
        for (final a.e e2 : this.e) {
            if (e2 == null) {
                continue;
            }
            this.f.add(this.a(e2, this.a(e2.b, b)));
        }
    }
    
    private a.e a(final a.e e, final a.h b) {
        final a.e e2 = new a.e();
        e2.b = b;
        e2.a = e.a;
        e2.c = e.c;
        e2.d = e.d;
        return e2;
    }
    
    public void c() {
        super.c();
        this.b(this.f);
        this.b(this.e);
        this.e = null;
    }
    
    protected void b(final List<a.e> list) {
        if (list != null) {
            for (final a.e e : list) {
                if (e != null && e.a != null && !e.a.isRecycled()) {
                    e.a.recycle();
                    e.a = null;
                }
            }
            list.clear();
        }
    }
}
