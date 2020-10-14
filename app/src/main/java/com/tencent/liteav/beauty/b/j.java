package com.tencent.liteav.beauty.b;

import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import java.nio.*;
import java.util.*;

public class j extends h
{
    protected List<h> r;
    protected List<h> s;
    private int[] t;
    private int[] u;
    private h v;
    private final FloatBuffer w;
    private final FloatBuffer x;
    private final FloatBuffer y;
    
    public j() {
        this(null);
        this.o = true;
    }
    
    public j(final List<h> r) {
        this.s = new ArrayList<h>();
        this.v = new h();
        this.o = true;
        this.r = r;
        if (this.r == null) {
            this.r = new ArrayList<h>();
        }
        else {
            this.s();
        }
        this.w = ByteBuffer.allocateDirect(com.tencent.liteav.beauty.b.y.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.w.put(com.tencent.liteav.beauty.b.y.a).position(0);
        this.x = ByteBuffer.allocateDirect(com.tencent.liteav.basic.c.l.a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.x.put(com.tencent.liteav.basic.c.l.a).position(0);
        final float[] a = com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, true);
        this.y = ByteBuffer.allocateDirect(a.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.y.put(a).position(0);
    }
    
    public void a(final h h) {
        if (h == null) {
            return;
        }
        this.r.add(h);
        this.s();
    }
    
    @Override
    public boolean a() {
        boolean b = super.a();
        if (b) {
            for (final h h : this.r) {
                h.c();
                if (!h.n()) {
                    break;
                }
            }
            b = this.v.c();
        }
        return b && GLES20.glGetError() == 0;
    }
    
    @Override
    public void b() {
        super.b();
        final Iterator<h> iterator = this.r.iterator();
        while (iterator.hasNext()) {
            iterator.next().e();
        }
    }
    
    @Override
    public void f() {
        super.f();
        if (this.u != null) {
            GLES20.glDeleteTextures(2, this.u, 0);
            this.u = null;
        }
        if (this.t != null) {
            GLES20.glDeleteFramebuffers(2, this.t, 0);
            this.t = null;
        }
    }
    
    @Override
    public void a(final int n, final int n2) {
        if (this.e == n && this.f == n2) {
            return;
        }
        if (this.t != null) {
            this.f();
        }
        super.a(n, n2);
        for (int size = this.s.size(), i = 0; i < size; ++i) {
            this.s.get(i).a(n, n2);
        }
        this.v.a(n, n2);
        if (this.s != null && this.s.size() > 0) {
            this.s.size();
            this.t = new int[2];
            this.u = new int[2];
            for (int j = 0; j < 2; ++j) {
                GLES20.glGenFramebuffers(1, this.t, j);
                GLES20.glGenTextures(1, this.u, j);
                GLES20.glBindTexture(3553, this.u[j]);
                GLES20.glTexImage2D(3553, 0, 6408, n, n2, 0, 6408, 5121, (Buffer)null);
                GLES20.glTexParameterf(3553, 10240, 9729.0f);
                GLES20.glTexParameterf(3553, 10241, 9729.0f);
                GLES20.glTexParameterf(3553, 10242, 33071.0f);
                GLES20.glTexParameterf(3553, 10243, 33071.0f);
                GLES20.glBindFramebuffer(36160, this.t[j]);
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, this.u[j], 0);
                GLES20.glBindTexture(3553, 0);
                GLES20.glBindFramebuffer(36160, 0);
            }
        }
    }
    
    @Override
    public int a(final int n, final int n2, final int n3) {
        final int size = this.s.size();
        int n4 = n;
        boolean b = false;
        this.k();
        for (int i = 0; i < size; ++i) {
            final h h = this.s.get(i);
            if (b) {
                n4 = h.a(n4, n2, n3);
            }
            else {
                n4 = h.a(n4, this.t[0], this.u[0]);
            }
            b = !b;
        }
        if (b) {
            this.v.a(n4, n2, n3);
        }
        return n3;
    }
    
    public List<h> r() {
        return this.s;
    }
    
    public void s() {
        if (this.r == null) {
            return;
        }
        this.s.clear();
        for (final h h : this.r) {
            if (h instanceof j) {
                ((j)h).s();
                final List<h> r = ((j)h).r();
                if (r == null) {
                    continue;
                }
                if (r.isEmpty()) {
                    continue;
                }
                this.s.addAll(r);
            }
            else {
                this.s.add(h);
            }
        }
    }
}
