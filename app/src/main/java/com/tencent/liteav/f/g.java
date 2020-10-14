package com.tencent.liteav.f;

import com.tencent.liteav.i.*;
import java.util.*;

public class g
{
    private static g a;
    private List<a.j> b;
    
    public static g a() {
        if (g.a == null) {
            g.a = new g();
        }
        return g.a;
    }
    
    private g() {
    }
    
    public void a(final List<a.j> b) {
        this.b = b;
    }
    
    public List<a.j> b() {
        return this.b;
    }
    
    public boolean c() {
        if (this.b == null || this.b.size() == 0) {
            return false;
        }
        final Iterator<a.j> iterator = this.b.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().a != 2) {
                return true;
            }
        }
        return false;
    }
    
    public float a(final long n) {
        float a = 1.0f;
        if (this.b == null || this.b.size() == 0) {
            return a;
        }
        for (final a.j j : this.b) {
            if (n > j.b * 1000L && n < j.c * 1000L) {
                a = this.a(j.a);
                break;
            }
        }
        return a;
    }
    
    public float a(final int n) {
        switch (n) {
            case 0: {
                return 0.25f;
            }
            case 1: {
                return 0.5f;
            }
            case 2: {
                return 1.0f;
            }
            case 3: {
                return 1.5f;
            }
            case 4: {
                return 2.0f;
            }
            default: {
                return 1.0f;
            }
        }
    }
    
    public void d() {
        if (this.b != null) {
            this.b.clear();
        }
        this.b = null;
    }
    
    public long b(long n) {
        final List<a.j> b = a().b();
        for (int i = 0; i < b.size(); ++i) {
            final a.j j = b.get(i);
            final float a = this.a(j.a);
            final long n2 = (j.c - j.b) * 1000L;
            n = n + (long)(n2 / a) - n2;
        }
        return n;
    }
}
