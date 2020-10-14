package com.tencent.liteav.renderer;

import android.view.*;
import com.tencent.liteav.basic.log.*;
import android.graphics.*;
import android.os.*;

public class e
{
    private TextureView a;
    private Handler b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    private int j;
    private float k;
    private int l;
    
    public e(final TextureView a) {
        this.c = 0;
        this.d = 0;
        this.e = 640;
        this.f = 480;
        this.g = 0;
        this.h = 0;
        this.i = 1;
        this.j = 0;
        this.k = 1.0f;
        this.l = 0;
        this.a = a;
        this.c = a.getWidth();
        this.d = a.getHeight();
        this.b = new Handler(a.getContext().getMainLooper());
    }
    
    public void a(final int n) {
        try {
            this.b.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.renderer.e.this.b(n);
                }
            });
        }
        catch (Exception ex) {
            TXCLog.e("TXCTextureViewWrapper", "set render mode failed", ex);
        }
    }
    
    public void b(final int i) {
        this.i = i;
        if (this.a != null) {
            float n = 1.0f;
            if (i == 1) {
                if (this.j == 0 || this.j == 180) {
                    n = 1.0f;
                }
                else if (this.j == 270 || this.j == 90) {
                    if (this.g == 0 || this.h == 0) {
                        return;
                    }
                    final float n2 = this.d / (float)this.g;
                    final float n3 = this.c / (float)this.h;
                    n = ((n2 > n3) ? n3 : n2);
                }
            }
            else if (i == 0) {
                if (this.g == 0 || this.h == 0) {
                    return;
                }
                if (this.j == 0 || this.j == 180) {
                    final float n4 = this.d / (float)this.h;
                    final float n5 = this.c / (float)this.g;
                    n = ((n4 < n5) ? n5 : n4);
                }
                else if (this.j == 270 || this.j == 90) {
                    final float n6 = this.d / (float)this.g;
                    final float n7 = this.c / (float)this.h;
                    n = ((n6 < n7) ? n7 : n6);
                }
            }
            if (this.k < 0.0f) {
                n = -n;
            }
            this.a.setScaleX(n);
            this.a.setScaleY(Math.abs(n));
            this.k = n;
        }
    }
    
    public void c(final int n) {
        try {
            this.b.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.renderer.e.this.d(n);
                }
            });
        }
        catch (Exception ex) {
            TXCLog.e("TXCTextureViewWrapper", "set render rotation failed.", ex);
        }
    }
    
    public void d(int j) {
        j %= 360;
        this.j = j;
        if (this.a != null) {
            float n = 1.0f;
            if (j == 0 || j == 180) {
                this.a.setRotation((float)(360 - j));
                if (this.i == 1) {
                    n = 1.0f;
                }
                else if (this.i == 0) {
                    if (this.g == 0 || this.h == 0) {
                        return;
                    }
                    final float n2 = this.d / (float)this.h;
                    final float n3 = this.c / (float)this.g;
                    n = ((n2 < n3) ? n3 : n2);
                }
            }
            else if (j == 270 || j == 90) {
                if (this.g == 0 || this.h == 0) {
                    return;
                }
                this.a.setRotation((float)(360 - j));
                final float n4 = this.d / (float)this.g;
                final float n5 = this.c / (float)this.h;
                if (this.i == 1) {
                    n = ((n4 > n5) ? n5 : n4);
                }
                else if (this.i == 0) {
                    n = ((n4 < n5) ? n5 : n4);
                }
            }
            if (this.k < 0.0f) {
                n = -n;
            }
            this.a.setScaleX(n);
            this.a.setScaleY(Math.abs(n));
            this.k = n;
        }
    }
    
    private void a() {
        try {
            this.a(new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.renderer.e.this.c(com.tencent.liteav.renderer.e.this.e, com.tencent.liteav.renderer.e.this.f);
                    com.tencent.liteav.renderer.e.this.b(com.tencent.liteav.renderer.e.this.i);
                    com.tencent.liteav.renderer.e.this.d(com.tencent.liteav.renderer.e.this.j);
                }
            });
        }
        catch (Exception ex) {
            TXCLog.e("TXCTextureViewWrapper", "adjust video size failed.", ex);
        }
    }
    
    private void c(final int n, final int n2) {
        if (this.a == null || n == 0 || n2 == 0) {
            return;
        }
        if (this.c == 0 || this.d == 0) {
            return;
        }
        final double n3 = n2 / (double)n;
        if (this.d > (int)(this.c * n3)) {
            this.g = this.c;
            this.h = (int)(this.c * n3);
        }
        else {
            this.g = (int)(this.d / n3);
            this.h = this.d;
        }
        final float n4 = (this.c - this.g) / 2.0f;
        final float n5 = (this.d - this.h) / 2.0f;
        final float n6 = this.g / (float)this.c;
        final float n7 = this.h / (float)this.d;
        final Matrix transform = new Matrix();
        this.a.getTransform(transform);
        transform.setScale(n6, n7);
        transform.postTranslate(n4, n5);
        this.a.setTransform(transform);
        this.a.requestLayout();
    }
    
    public void a(final int c, final int d) {
        TXCLog.w("TXCTextureViewWrapper", "vrender: set view size:" + c + "," + d);
        this.c = c;
        this.d = d;
        this.a();
    }
    
    public void b(final int e, final int f) {
        TXCLog.w("TXCTextureViewWrapper", "vrender: set video size:" + e + "," + f);
        this.e = e;
        this.f = f;
        this.a();
    }
    
    private void a(final Runnable runnable) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            runnable.run();
        }
        else {
            this.b.post(runnable);
        }
    }
}
