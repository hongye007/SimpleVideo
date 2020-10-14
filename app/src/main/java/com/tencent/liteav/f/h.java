package com.tencent.liteav.f;

import java.util.concurrent.*;
import java.util.*;

public class h extends c
{
    private static h d;
    private List<a.k> e;
    private CopyOnWriteArrayList<a.k> f;
    
    public static h a() {
        if (h.d == null) {
            h.d = new h();
        }
        return h.d;
    }
    
    private h() {
        this.f = new CopyOnWriteArrayList<a.k>();
    }
    
    public void a(final List<a.k> e) {
        this.e = e;
        this.f.clear();
        if (this.c != null) {
            this.a(this.c);
        }
    }
    
    public List<a.k> b() {
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
        for (final a.k k : this.e) {
            if (k == null) {
                continue;
            }
            this.f.add(this.a(k, this.a(k.b, b)));
        }
    }
    
    private a.k a(final a.k k, final a.h b) {
        final a.k i = new a.k();
        i.b = b;
        i.a = k.a;
        i.c = k.c;
        i.d = k.d;
        return i;
    }
    
    public void c() {
        super.c();
        this.b(this.f);
        this.b(this.e);
        this.e = null;
    }
    
    protected void b(final List<a.k> list) {
        if (list != null) {
            for (final a.k k : list) {
                if (k != null && k.a != null && !k.a.isRecycled()) {
                    k.a.recycle();
                    k.a = null;
                }
            }
            list.clear();
        }
    }
}
