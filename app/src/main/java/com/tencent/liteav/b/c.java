package com.tencent.liteav.b;

import android.content.*;
import java.util.concurrent.atomic.*;
import java.util.*;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.basic.log.*;

import android.view.*;
import com.tencent.liteav.editer.*;
import android.opengl.*;
import java.nio.*;

public class c
{
    private final String a = "CombineDecAndRender";
    private Context b;
    private n c;
    private ArrayList<b> d;
    private a e;
    private j f;
    private com.tencent.liteav.b.a g;
    private int h;
    private int i;
    private int j;
    private int k;
    private h l;
    private AtomicBoolean m;
    private AtomicBoolean n;
    private AtomicBoolean o;
    private AtomicBoolean p;
    private e q;
    
    public c(final Context b) {
        this.m = new AtomicBoolean(false);
        this.n = new AtomicBoolean(false);
        this.o = new AtomicBoolean(false);
        this.p = new AtomicBoolean(false);
        this.b = b;
        this.d = new ArrayList<b>();
        this.g = new com.tencent.liteav.b.a();
        this.c = new n();
        this.f = new j(this.b);
    }
    
    public void a(final h l) {
        this.l = l;
    }
    
    public int a(final List<String> list) {
        if (list == null || list.size() < 2) {
            return -1;
        }
        this.j = 0;
        int n = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            final b b = new b();
            b.a.a(list.get(i));
            final int d = b.a.d();
            if (n > d) {
                this.j = i;
                n = d;
            }
            this.d.add(b);
            this.g.a(b.a.a(), i);
        }
        TXCLog.i("CombineDecAndRender", "mFpsSmallIndex = " + this.j);
        if (this.f == null) {
            this.f = new j(this.b);
        }
        this.g.a(this.a(), this.k);
        this.g.a();
        return 0;
    }
    
    public void b(final List<Float> list) {
        if (this.g != null) {
            this.g.a(list);
        }
    }
    
    public void a(final List<com.tencent.liteav.i.a.a> list, final int h, final int i) {
        if (this.f == null) {
            this.f = new j(this.b);
        }
        this.f.a(list, h, i);
        this.h = h;
        this.i = i;
    }
    
    public int a() {
        int n = -1;
        for (int i = 0; i < this.d.size(); ++i) {
            final int e = this.d.get(i).a.e();
            if (n < e) {
                n = e;
                this.k = i;
            }
        }
        if (n > 0) {
            return n;
        }
        this.k = 0;
        return 48000;
    }
    
    public int b() {
        int n = -1;
        for (int i = 0; i < this.d.size(); ++i) {
            final int f = this.d.get(i).a.f();
            if (n < f) {
                n = f;
            }
        }
        if (n >= 0) {
            return n;
        }
        return 10000;
    }
    
    public void c() {
        TXCLog.i("CombineDecAndRender", "start");
        for (int i = 0; i < this.d.size(); ++i) {
            final b b = this.d.get(i);
            final m a = b.a;
            final g g = new g();
            g.a = a.b();
            g.b = a.c();
            this.c.a(g, i);
            this.c.a(new k() {
                @Override
                public void a(final Surface surface, final int n) {
                    a.a(surface);
                    b.d = new d(com.tencent.liteav.b.c.this.b);
                }
                
                @Override
                public void b(final Surface surface, final int n) {
                    if (b != null && b.d != null) {
                        b.d.a();
                    }
                    if (com.tencent.liteav.b.c.this.f != null) {
                        com.tencent.liteav.b.c.this.f.a();
                        com.tencent.liteav.b.c.this.f = null;
                    }
                }
                
                @Override
                public int a(final int n, final float[] array, final e e, final int n2) {
                    if (com.tencent.liteav.b.c.this.n.get()) {
                        TXCLog.i("CombineDecAndRender", "mVideoRenderCallback mDecodeVideoEnd, ignore");
                        return 0;
                    }
                    if (com.tencent.liteav.b.c.this.a(e, false)) {
                        TXCLog.i("CombineDecAndRender", "mVideoRenderCallback onTextureProcess, end frame");
                        return 0;
                    }
                    if (com.tencent.liteav.b.c.this.d == null || com.tencent.liteav.b.c.this.d.size() <= n2 || n2 < 0) {
                        TXCLog.e("CombineDecAndRender", "onSurfaceTextureAvailable index is error:" + n2);
                        return 0;
                    }
                    final b b = com.tencent.liteav.b.c.this.d.get(n2);
                    if (b == null) {
                        TXCLog.w("CombineDecAndRender", "struct is null " + n2);
                        return 0;
                    }
                    if (b.d != null) {
                        b.d.a(array);
                        b.g = b.d.a(n, e);
                    }
                    com.tencent.liteav.b.c.this.e();
                    return 0;
                }
            }, i);
        }
        this.c.a(new r() {
            @Override
            public void a(final EGLContext eglContext) {
                if (com.tencent.liteav.b.c.this.l != null) {
                    com.tencent.liteav.b.c.this.l.a(eglContext);
                }
                for (int i = 0; i < com.tencent.liteav.b.c.this.d.size(); ++i) {
                    final b b = com.tencent.liteav.b.c.this.d.get(i);
                    if (b != null) {
                        final int n = i;
                        b.a.g();
                        b.a.a(new com.tencent.liteav.b.e() {
                            @Override
                            public void a(final CaptureAndEnc.e e) {
                                if (com.tencent.liteav.b.c.this.p.get() || com.tencent.liteav.b.c.this.o.get()) {
                                    return;
                                }
                                if (b.e) {
                                    b.e = false;
                                    com.tencent.liteav.b.c.this.g.b(e.j(), n);
                                }
                                TXCLog.d("CombineDecAndRender", "Audio1 frame put one:" + e.e() + ", flag = " + e.f() + ", AudioBlockingQueue size:" + b.c.size());
                                try {
                                    b.c.put(e);
                                }
                                catch (InterruptedException ex) {
                                    TXCLog.w("CombineDecAndRender", ex.toString());
                                }
                                com.tencent.liteav.b.c.this.f();
                            }
                            
                            @Override
                            public void b(final CaptureAndEnc.e e) {
                                if (com.tencent.liteav.b.c.this.p.get()) {
                                    return;
                                }
                                TXCLog.d("CombineDecAndRender", "Video1 frame put one:" + e.e() + ",VideoBlockingQueue size:" + b.b.size());
                                try {
                                    b.b.put(e);
                                }
                                catch (InterruptedException ex) {
                                    TXCLog.w("CombineDecAndRender", ex.toString());
                                }
                                if (com.tencent.liteav.b.c.this.c != null) {
                                    com.tencent.liteav.b.c.this.c.a(e, n);
                                }
                            }
                        });
                    }
                }
                com.tencent.liteav.b.c.this.e.start();
            }
            
            @Override
            public void b(final EGLContext eglContext) {
            }
        });
        this.c.a();
        this.p.compareAndSet(true, false);
        this.m.compareAndSet(true, false);
        this.n.compareAndSet(true, false);
        this.o.compareAndSet(true, false);
        this.e = new a();
    }
    
    public void d() {
        TXCLog.i("CombineDecAndRender", "stop");
        if (this.p.get()) {
            return;
        }
        this.p.set(true);
        if (this.e != null && this.e.isAlive()) {
            try {
                this.e.join(500L);
            }
            catch (InterruptedException ex) {}
        }
        for (int i = 0; i < this.d.size(); ++i) {
            final b b = this.d.get(i);
            final m a = b.a;
            if (a != null) {
                a.h();
            }
            b.c.clear();
            if (b.g != null) {
                b.g.b = null;
                b.g.a = -1;
            }
        }
        this.d.clear();
        if (this.g != null) {
            this.g.b();
        }
        if (this.c != null) {
            this.c.b();
        }
    }
    
    private void e() {
        final b b = this.d.get(this.j);
        if (b == null || b.g == null) {
            return;
        }
        final e b2 = b.g.b;
        if (b2 == null) {
            return;
        }
        final ArrayList<f> list = new ArrayList<f>();
        boolean b3 = false;
        for (int i = 0; i < this.d.size(); ++i) {
            final b b4 = this.d.get(i);
            list.add(b4.g);
            if (i != this.j) {
                if (b4 != null) {
                    final e b5 = b4.g.b;
                    if (b5 != null) {
                        if (b2.e() > b5.e()) {
                            b4.b.remove();
                            b4.g.b = null;
                            b4.g.a = -1;
                            b3 = true;
                        }
                    }
                    else {
                        b3 = true;
                    }
                }
            }
        }
        if (b3) {
            TXCLog.d("CombineDecAndRender", "not all video ready to compine");
        }
        else {
            final int a = this.f.a(list);
            if (this.l != null) {
                this.l.a(a, this.h, this.i, b2);
            }
            for (int j = 0; j < this.d.size(); ++j) {
                final b b6 = this.d.get(j);
                if (b6 != null) {
                    final f g = b6.g;
                    b6.b.remove();
                    if (g != null) {
                        b6.g.b = null;
                        b6.g.a = -1;
                    }
                }
            }
        }
    }
    
    private void f() {
        final ArrayList<e> list = new ArrayList<e>();
        boolean b = true;
        boolean b2 = true;
        e e = null;
        for (int i = 0; i < this.d.size(); ++i) {
            final b b3 = this.d.get(i);
            if (b3.c.isEmpty()) {
                if (!b3.f) {
                    TXCLog.d("CombineDecAndRender", "combineAudioFrame audio queue is empty but not end:" + i);
                    return;
                }
                list.add(i, null);
            }
            else {
                final e e2 = b3.c.peek();
                if (this.a(e2, true)) {
                    TXCLog.i("CombineDecAndRender", "combineAudioFrame, frame is end " + i);
                    b3.c.clear();
                    b3.f = true;
                    list.add(i, null);
                    e = e2;
                }
                else {
                    list.add(i, e2);
                    b = false;
                    b2 = false;
                }
            }
        }
        for (int j = 0; j < this.d.size(); ++j) {
            final b b4 = this.d.get(j);
            if (!b4.c.isEmpty()) {
                try {
                    b4.c.take();
                }
                catch (InterruptedException ex) {}
            }
        }
        if (b) {
            this.o.set(true);
            TXCLog.i("CombineDecAndRender", "combineAudioFrame, audio both end");
            this.l.c(e);
            if (this.n.get()) {
                TXCLog.i("CombineDecAndRender", "combineAudioFrame, video and audio both end");
                this.m.set(true);
                this.d();
            }
            return;
        }
        if (!b2) {
            final e a = this.g.a(list);
            if (a != null) {
                if (this.l != null) {
                    this.l.a(a);
                }
                this.q = a;
            }
            else {
                this.o.set(true);
                TXCLog.i("CombineDecAndRender", "combineAudioFrame, audio one is end");
                this.q.c(4);
                this.q.a((ByteBuffer)null);
                this.q.d(0);
                this.l.c(this.q);
                if (this.n.get()) {
                    TXCLog.i("CombineDecAndRender", "combineAudioFrame, video and audio both end");
                    this.m.set(true);
                    this.d();
                }
            }
        }
    }
    
    private boolean a(final e e, final boolean b) {
        if (!e.p()) {
            return false;
        }
        if (this.l != null) {
            if (b) {
                TXCLog.i("CombineDecAndRender", "===judgeDecodeComplete=== audio end");
            }
            else {
                TXCLog.i("CombineDecAndRender", "===judgeDecodeComplete=== video end");
                if (!this.n.get()) {
                    this.n.set(true);
                    this.l.b(e);
                }
            }
            if (this.o.get() && this.n.get()) {
                TXCLog.i("CombineDecAndRender", "judgeDecodeComplete, video and audio both end");
                this.m.set(true);
                this.d();
            }
        }
        return true;
    }
    
    class a extends Thread
    {
        @Override
        public void run() {
            this.setName("DecodeThread");
            try {
                TXCLog.i("CombineDecAndRender", "===DecodeThread Start===");
                while (!com.tencent.liteav.b.c.this.m.get() && !com.tencent.liteav.b.c.this.p.get()) {
                    for (int i = 0; i < com.tencent.liteav.b.c.this.d.size(); ++i) {
                        ((b)com.tencent.liteav.b.c.this.d.get(i)).a.i();
                    }
                }
                for (int j = 0; j < com.tencent.liteav.b.c.this.d.size(); ++j) {
                    ((b)com.tencent.liteav.b.c.this.d.get(j)).b.clear();
                }
                TXCLog.i("CombineDecAndRender", "===DecodeThread Exit===");
            }
            catch (InterruptedException ex) {}
        }
    }
}
