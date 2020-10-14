package com.tencent.liteav.k;

import android.content.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;

public class n
{
    private com.tencent.liteav.k.j a;
    private com.tencent.liteav.k.k b;
    private com.tencent.liteav.k.c c;
    private com.tencent.liteav.k.a d;
    private com.tencent.liteav.k.h e;
    private com.tencent.liteav.k.e f;
    private com.tencent.liteav.k.i g;
    private com.tencent.liteav.k.d h;
    private com.tencent.liteav.k.g i;
    private com.tencent.liteav.k.b j;
    private com.tencent.liteav.f k;
    private com.tencent.liteav.g l;
    private l m;
    private m n;
    private d o;
    private a p;
    private i q;
    private f r;
    private k s;
    private e t;
    private h u;
    private c v;
    private g w;
    private j x;
    private final Queue<Runnable> y;
    private final String z = "VideoEffect";
    private Context A;
    
    public n(final Context a) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = null;
        this.m = null;
        this.n = null;
        this.o = null;
        this.p = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = null;
        this.y = new LinkedList<Runnable>();
        this.A = null;
        this.A = a;
    }
    
    public void a(final int n, final n n2) {
        this.a(new Runnable() {
            @Override
            public void run() {
                switch (n) {
                    case 0: {
                        com.tencent.liteav.k.n.this.p = (a)n2;
                        break;
                    }
                    case 1: {
                        com.tencent.liteav.k.n.this.o = (d)n2;
                        break;
                    }
                    case 2: {
                        com.tencent.liteav.k.n.this.m = (l)n2;
                        break;
                    }
                    case 3: {
                        com.tencent.liteav.k.n.this.n = (m)n2;
                        break;
                    }
                    case 4: {
                        com.tencent.liteav.k.n.this.q = (i)n2;
                        break;
                    }
                    case 5: {
                        com.tencent.liteav.k.n.this.r = (f)n2;
                        break;
                    }
                    case 6: {
                        com.tencent.liteav.k.n.this.s = (k)n2;
                        break;
                    }
                    case 7: {
                        com.tencent.liteav.k.n.this.t = (e)n2;
                        break;
                    }
                    case 8: {
                        com.tencent.liteav.k.n.this.u = (h)n2;
                        break;
                    }
                    case 9: {
                        com.tencent.liteav.k.n.this.v = (c)n2;
                        break;
                    }
                    case 10: {
                        com.tencent.liteav.k.n.this.w = (g)n2;
                        break;
                    }
                    case 11: {
                        com.tencent.liteav.k.n.this.x = (j)n2;
                        break;
                    }
                }
            }
        });
    }
    
    public int a(final b b) {
        this.a(this.y);
        int n = b.a;
        if (null != this.p) {
            this.d(b.b, b.c);
            if (null != this.d) {
                this.d.a(this.p);
                n = this.d.a(n);
            }
        }
        if (null != this.o) {
            this.c(b.b, b.c);
            if (null != this.c) {
                this.c.a(this.o);
                n = this.c.a(n);
            }
        }
        if (null != this.m) {
            this.a(b.b, b.c);
            if (null != this.a) {
                this.a.a(this.m);
                n = this.a.a(n);
            }
        }
        if (null != this.n) {
            this.b(b.b, b.c);
            if (null != this.b) {
                this.b.a(this.n);
                n = this.b.a(n);
            }
        }
        if (null != this.q) {
            this.e(b.b, b.c);
            if (null != this.e) {
                this.e.a(this.q);
                n = this.e.a(n);
            }
        }
        if (null != this.r) {
            this.f(b.b, b.c);
            if (null != this.f) {
                this.f.a(this.r);
                n = this.f.a(n);
            }
        }
        if (null != this.s) {
            this.g(b.b, b.c);
            if (null != this.g) {
                this.g.a(this.s);
                n = this.g.a(n);
            }
        }
        if (null != this.t) {
            this.h(b.b, b.c);
            if (null != this.h) {
                this.h.a(this.t);
                n = this.h.a(n);
            }
        }
        if (null != this.u) {
            this.i(b.b, b.c);
            if (null != this.i) {
                this.i.a(this.u);
                n = this.i.a(n);
            }
        }
        if (null != this.v) {
            this.j(b.b, b.c);
            if (null != this.j) {
                this.j.a(this.v);
                n = this.j.a(n);
            }
        }
        if (null != this.w) {
            this.k(b.b, b.c);
            if (null != this.k) {
                n = this.k.a(n);
            }
        }
        if (null != this.x) {
            this.l(b.b, b.c);
            if (null != this.l) {
                this.l.a(this.x);
                n = this.l.setVideoEncRotation(n);
            }
        }
        this.b();
        return n;
    }
    
    private void a(final int n, final int n2) {
        if (null == this.a) {
            this.a = new com.tencent.liteav.k.j();
            if (!this.a.a(n, n2)) {
                TXCLog.e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.a.b(n, n2);
    }
    
    private void b(final int n, final int n2) {
        if (null == this.b) {
            (this.b = new com.tencent.liteav.k.k()).a(true);
            if (!this.b.c()) {
                TXCLog.e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.b.a(n, n2);
    }
    
    private void c(final int n, final int n2) {
        if (null == this.c) {
            (this.c = new com.tencent.liteav.k.c()).a(true);
            if (!this.c.c()) {
                TXCLog.e("VideoEffect", "mSpiritOutFilter.init failed");
                return;
            }
        }
        this.c.a(n, n2);
    }
    
    private void d(final int n, final int n2) {
        if (null == this.d) {
            (this.d = new com.tencent.liteav.k.a()).a(true);
            if (!this.d.c()) {
                TXCLog.e("VideoEffect", "mAnHeiFilter.init failed");
                return;
            }
        }
        this.d.a(n, n2);
    }
    
    private void e(final int n, final int n2) {
        if (null == this.e) {
            (this.e = new com.tencent.liteav.k.h()).a(true);
            if (!this.e.c()) {
                TXCLog.e("VideoEffect", "mShadowFilter.init failed");
                return;
            }
        }
        this.e.a(n, n2);
    }
    
    private void f(final int n, final int n2) {
        if (null == this.f) {
            this.f = new com.tencent.liteav.k.e();
            if (!this.f.a(n, n2)) {
                TXCLog.e("VideoEffect", "mGhostShadowFilter.init failed");
                return;
            }
        }
        this.f.b(n, n2);
    }
    
    private void g(final int n, final int n2) {
        if (null == this.g) {
            this.g = new com.tencent.liteav.k.i();
            if (!this.g.a(n, n2)) {
                TXCLog.e("VideoEffect", "createPhontomFilter.init failed");
                return;
            }
        }
        this.g.b(n, n2);
    }
    
    private void h(final int n, final int n2) {
        if (null == this.h) {
            (this.h = new com.tencent.liteav.k.d()).a(true);
            if (!this.h.c()) {
                TXCLog.e("VideoEffect", "createGhostFilter.init failed");
                return;
            }
        }
        this.h.a(n, n2);
    }
    
    private void i(final int n, final int n2) {
        if (null == this.i) {
            this.i = new com.tencent.liteav.k.g(this.A);
            if (!this.i.a(n, n2)) {
                TXCLog.e("VideoEffect", "createGhostFilter.init failed");
                return;
            }
        }
        this.i.b(n, n2);
    }
    
    private void j(final int n, final int n2) {
        if (null == this.j) {
            this.j = new com.tencent.liteav.k.b();
            if (!this.j.a(n, n2)) {
                TXCLog.e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.j.b(n, n2);
    }
    
    private void k(final int n, final int n2) {
        if (null == this.k) {
            (this.k = new com.tencent.liteav.f()).a(true);
            if (!this.k.c()) {
                TXCLog.e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.k.a(n, n2);
    }
    
    private void l(final int n, final int n2) {
        if (null == this.l) {
            (this.l = new com.tencent.liteav.g()).a(true);
            if (!this.l.c()) {
                TXCLog.e("VideoEffect", "mDiffuseFilter.init failed");
                return;
            }
        }
        this.l.a(n, n2);
    }
    
    private void b() {
        this.p = null;
        this.o = null;
        this.m = null;
        this.n = null;
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.w = null;
        this.x = null;
    }
    
    private void a(final Runnable runnable) {
        synchronized (this.y) {
            this.y.add(runnable);
        }
    }
    
    private void a(final Queue<Runnable> queue) {
        while (true) {
            Runnable runnable = null;
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    runnable = queue.poll();
                }
            }
            if (runnable == null) {
                break;
            }
            runnable.run();
        }
    }
    
    public void a() {
        this.c();
        this.b();
    }
    
    private void c() {
        if (null != this.a) {
            this.a.a();
            this.a = null;
        }
        if (null != this.b) {
            this.b.e();
            this.b = null;
        }
        if (null != this.c) {
            this.c.e();
            this.c = null;
        }
        if (null != this.d) {
            this.d.e();
            this.d = null;
        }
        if (null != this.e) {
            this.e.e();
            this.e = null;
        }
        if (null != this.f) {
            this.f.a();
            this.f = null;
        }
        if (null != this.g) {
            this.g.a();
            this.g = null;
        }
        if (null != this.h) {
            this.h.e();
            this.h = null;
        }
        if (null != this.i) {
            this.i.b();
            this.i = null;
        }
        if (null != this.j) {
            this.j.a();
            this.j = null;
        }
        if (null != this.k) {
            this.k.e();
            this.k = null;
        }
        if (null != this.l) {
            this.l.startWithoutAudio();
            this.l = null;
        }
    }
    
    public static class n
    {
    }
    
    public static class b
    {
        public int a;
        public int b;
        public int c;
    }
    
    public static class m extends n
    {
        public int a;
    }
    
    public static class l extends n
    {
        public float d;
        public float e;
        public int f;
        public int g;
        public float h;
        
        public l() {
            this.d = 0.5f;
            this.e = 0.5f;
            this.f = 1;
            this.g = 1;
            this.h = 0.5f;
        }
    }
    
    public static class d extends n
    {
        public float a;
        public float b;
        public float[] c;
        public float d;
        public float e;
        public float[] f;
        public float[] g;
        public float[] h;
        
        public d() {
            this.a = 0.0f;
            this.b = 0.4f;
            this.c = new float[] { 0.5f, 0.5f };
            this.d = 0.0f;
            this.e = 10.0f;
            this.f = new float[] { 0.0f, 0.0f };
            this.g = new float[] { 0.0f, 0.0f };
            this.h = new float[] { 0.0f, 0.0f };
        }
    }
    
    public static class a extends n
    {
    }
    
    public static class f extends n
    {
        public int a;
        public int b;
        public float c;
        
        public f() {
            this.a = 5;
            this.b = 1;
            this.c = 0.5f;
        }
    }
    
    public static class k extends l
    {
        public float[] a;
        public float[] b;
        public float[] c;
        
        public k() {
            this.a = new float[] { 0.0f, 0.0f };
            this.b = new float[] { 0.0f, 0.1f };
            this.c = new float[] { 0.0f, 0.0f };
        }
    }
    
    public static class h extends n
    {
        public float a;
        
        public h() {
            this.a = 0.0f;
        }
    }
    
    public static class c extends n
    {
        public float a;
        public float b;
        public float c;
        public float d;
        public float e;
        public float f;
        public a g;
        public float h;
        public float i;
        public float j;
        public boolean k;
        
        public c() {
            this.a = 0.01f;
            this.b = 0.02f;
            this.c = 0.05f;
            this.d = 30.0f;
            this.e = 0.6f;
            this.f = 0.0f;
            this.g = a.b;
            this.h = 0.3f;
            this.i = 0.5f;
            this.j = 1.5f;
            this.k = false;
        }
        
        public enum a
        {
            a(-1), 
            b(0), 
            c(1);
            
            public int value;
            
            private a(final int value) {
                this.value = value;
            }
        }
    }
    
    public static class g extends n
    {
    }
    
    public static class j extends n
    {
        public float a;
        
        public j() {
            this.a = 0.0f;
        }
    }
    
    public static class i extends n
    {
        public float a;
        public float b;
        public float c;
        public float d;
        public float e;
        
        public i() {
            this.a = 0.0f;
            this.b = 0.0f;
            this.c = 0.0f;
            this.d = 0.0f;
            this.e = 0.05f;
        }
    }
    
    public static class e extends n
    {
        public float a;
        public float b;
        public float c;
        
        public e() {
            this.a = 0.0f;
            this.b = 0.0f;
            this.c = 0.0f;
        }
    }
}
