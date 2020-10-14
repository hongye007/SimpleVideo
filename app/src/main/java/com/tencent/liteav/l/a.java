package com.tencent.liteav.l;

import com.tencent.liteav.beauty.b.*;
import android.content.*;
import com.tencent.liteav.beauty.*;
import java.util.*;
import android.util.*;
import android.opengl.*;
import com.tencent.liteav.basic.c.*;
import java.nio.*;
import com.tencent.liteav.basic.log.*;

public class a
{
    private z[] a;
    private h[] b;
    private b c;
    private com.tencent.liteav.basic.e.a[] d;
    private float[] e;
    private int f;
    private int g;
    private Context h;
    private final Queue<Runnable> i;
    private String j;
    private f k;
    
    public a(final Context context) {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = 0;
        this.g = 0;
        this.h = null;
        this.i = new LinkedList<Runnable>();
        this.j = "CombineProcessor";
        this.k = new f() {
            @Override
            public int willAddWatermark(final int n, final int n2, final int n3) {
                return 0;
            }
            
            @Override
            public void didProcessFrame(final int a, final int c, final int d, final long n) {
                a.this.d[a.this.g].b = 0;
                a.this.d[a.this.g].a = a;
                a.this.d[a.this.g].c = c;
                a.this.d[a.this.g].d = d;
            }
            
            @Override
            public void didProcessFrame(final byte[] array, final int n, final int n2, final int n3, final long n4) {
            }
        };
        this.h = context.getApplicationContext();
    }
    
    public void a(final int n, final int n2) {
        this.a(new Runnable() {
            @Override
            public void run() {
                if (null != com.tencent.liteav.l.a.this.c) {
                    com.tencent.liteav.l.a.this.c.a(n, n2);
                }
            }
        });
    }
    
    public void b(final int n, final int n2) {
        this.a(new Runnable() {
            @Override
            public void run() {
                if (null != com.tencent.liteav.l.a.this.c) {
                    com.tencent.liteav.l.a.this.c.b(n, n2);
                }
            }
        });
    }
    
    public void a(final com.tencent.liteav.basic.c.a a) {
        this.a(new Runnable() {
            @Override
            public void run() {
                if (null != com.tencent.liteav.l.a.this.c) {
                    com.tencent.liteav.l.a.this.c.a(a);
                }
            }
        });
    }
    
    public int a(final com.tencent.liteav.basic.e.a[] array, final int n) {
        if (null == array || array.length <= 0) {
            Log.e(this.j, "frames is null or no frames!");
            return -1;
        }
        if (this.f < array.length) {
            this.f = array.length;
            this.b();
        }
        this.a(array);
        this.a(this.i);
        this.d = array.clone();
        for (int i = 0; i < array.length; ++i) {
            if (null != this.a[i] && null != array[i].e) {
                this.a[i].a((float)array[i].e.b, array[i].e.a);
                this.a[i].b(array[i].e.c);
                GLES20.glViewport(0, 0, array[i].g.c, array[i].g.d);
                this.d[i].a = this.a[i].a(this.d[i].a);
            }
            if (null != this.b[i] && array[i].f != null) {
                this.b[i].a(l.e, this.b[i].a(array[i].c, array[i].d, null, array[i].f, 0));
                GLES20.glViewport(0, 0, array[i].g.c, array[i].g.d);
                this.d[i].a = this.b[i].a(this.d[i].a);
            }
        }
        return this.c.a(this.d, n);
    }
    
    private void a(final Runnable runnable) {
        synchronized (this.i) {
            this.i.add(runnable);
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
    
    private void a(final com.tencent.liteav.basic.e.a[] array) {
        if (null == this.a) {
            this.a = new z[array.length];
            for (int i = 0; i < array.length; ++i) {
                (this.a[i] = new z()).a(true);
                if (!this.a[i].c()) {
                    TXCLog.e(this.j, "mRotateScaleFilter[i] failed!");
                    return;
                }
            }
        }
        if (null != this.a) {
            for (int j = 0; j < array.length; ++j) {
                if (null != this.a[j]) {
                    this.a[j].a(array[j].g.c, array[j].g.d);
                }
            }
        }
        if (null == this.b) {
            this.b = new h[array.length];
            for (int k = 0; k < array.length; ++k) {
                (this.b[k] = new h()).a(true);
                if (!this.b[k].c()) {
                    TXCLog.e(this.j, "mCropFilter.init failed!");
                    return;
                }
                if (null != this.b[k]) {
                    this.b[k].a(array[k].g.c, array[k].g.d);
                }
            }
        }
        if (null == this.c) {
            this.c = new b();
        }
    }
    
    private void b() {
        if (null != this.a) {
            for (int i = 0; i < this.a.length; ++i) {
                if (null != this.a[i]) {
                    this.a[i].e();
                    this.a[i] = null;
                }
            }
            this.a = null;
        }
        if (null != this.b) {
            for (int j = 0; j < this.b.length; ++j) {
                if (null != this.b[j]) {
                    this.b[j].e();
                    this.b[j] = null;
                }
            }
            this.b = null;
        }
        if (null != this.c) {
            this.c.a();
            this.c = null;
        }
    }
    
    public void a() {
        this.b();
    }
}
