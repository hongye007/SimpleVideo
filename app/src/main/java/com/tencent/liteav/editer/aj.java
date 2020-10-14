package com.tencent.liteav.editer;

import com.tencent.liteav.CameraProxy;

import android.content.*;
import android.graphics.*;

import java.util.*;

public class aj extends e
{
    private b.a n;
    private ArrayList<Long> o;
    private v p;
    
    public aj(final Context context) {
        super(context, "timelistThumb");
        this.p = new v() {
            @Override
            public void a(final int n, final long n2, final Bitmap bitmap) {
                if (aj.this.n != null) {
                    aj.this.n.a(n, n2 / 1000L, bitmap);
                }
            }
        };
        this.o = new ArrayList<Long>();
        this.c = new ai("timelistThumb");
        this.f.a(this.p);
    }
    
    public void a(final b.a n) {
        this.n = n;
    }
    
    @Override
    public void a() {
        this.a(CameraProxy.k.a().a);
        this.h();
        this.b();
        super.a();
    }
    
    @Override
    protected void e() {
    }
    
    @Override
    protected void a(final long n) {
    }
    
    protected void h() {
        CameraProxy.h.a().a(this.o);
        this.c.a(CameraProxy.h.a().b());
    }
    
    @Override
    protected int a(final int n, final int n2, final int n3, final long n4) {
        return n;
    }
    
    @Override
    protected void f() {
    }
    
    @Override
    protected void g() {
    }
    
    public void a(final int n) {
        CameraProxy.h.a().a(n);
    }
    
    public void b(final int n) {
        CameraProxy.h.a().b(n);
    }
    
    public void a(final List<Long> list) {
        this.o.clear();
        for (int i = 0; i < list.size(); ++i) {
            this.o.add(list.get(i) * 1000L);
        }
    }
    
    @Override
    public void d() {
        super.d();
        this.o.clear();
        this.p = null;
    }
    
    public void c(final boolean b) {
        if (this.c != null) {
            this.c.a(b);
        }
    }
}
