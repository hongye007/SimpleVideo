package com.tencent.liteav.editer;

import android.content.*;

import com.tencent.liteav.basic.log.*;
import android.graphics.*;
import android.opengl.*;
import java.util.*;

public class f
{
    private final String a = "BitmapCombineRender";
    private com.tencent.liteav.l.a b;
    private int c;
    private int d;
    private int e;
    private a f;
    private a g;
    private int h;
    
    public f(final Context context, final int c, final int d) {
        this.h = -1;
        this.b = new com.tencent.liteav.l.a(context);
        this.c = c;
        this.d = d;
    }
    
    public int a(final e e, final int n, final boolean b) {
        if (b) {
            return this.h;
        }
        final List w = e.w();
        if (w == null || w.size() == 0) {
            TXCLog.e("BitmapCombineRender", "bitmapList is empty");
            return -1;
        }
        final Bitmap bitmap = w.get(0);
        if (this.f == null) {
            this.f = new a();
            this.f.a = j.a(bitmap, -1, false);
            this.f.b = bitmap.getWidth();
            this.f.c = bitmap.getHeight();
        }
        else if (bitmap.getWidth() != this.f.b || bitmap.getHeight() != this.f.c) {
            GLES20.glDeleteTextures(1, new int[] { this.f.a }, 0);
            this.f.a = j.a(bitmap, -1, false);
            this.f.b = bitmap.getWidth();
            this.f.c = bitmap.getHeight();
        }
        else {
            j.a(bitmap, this.f.a, false);
        }
        final com.tencent.liteav.basic.c.a a = this.a(bitmap.getWidth(), bitmap.getHeight());
        com.tencent.liteav.basic.c.a a2 = new com.tencent.liteav.basic.c.a(0, 0, 0, 0);
        int a3 = -1;
        if (w.size() > 1) {
            final Bitmap bitmap2 = w.get(1);
            if (this.g == null) {
                this.g = new a();
                this.g.a = j.a(bitmap2, -1, false);
                this.g.b = bitmap2.getWidth();
                this.g.c = bitmap2.getHeight();
            }
            else if (bitmap2.getWidth() != this.g.b || bitmap2.getHeight() != this.g.c) {
                GLES20.glDeleteTextures(1, new int[] { this.g.a }, 0);
                this.g.a = j.a(bitmap2, -1, false);
                this.g.b = bitmap2.getWidth();
                this.g.c = bitmap2.getHeight();
            }
            else {
                j.a(bitmap2, this.g.a, false);
            }
            a2 = this.a(bitmap2.getWidth(), bitmap2.getHeight(), n);
            a3 = this.g.a;
        }
        switch (n) {
            case 1: {
                return this.h = this.a(this.f.a, a3, e.e(), n, a, a2);
            }
            case 2: {
                return this.h = this.b(this.f.a, a3, e.e(), n, a, a2);
            }
            case 4:
            case 5: {
                return this.h = this.c(this.f.a, a3, e.e(), n, a, a2);
            }
            case 3: {
                return this.h = this.e(this.f.a, a3, e.e(), n, a, a2);
            }
            case 6: {
                return this.h = this.d(this.f.a, a3, e.e(), n, a, a2);
            }
            default: {
                return -1;
            }
        }
    }
    
    private com.tencent.liteav.basic.c.a a(final int n, final int n2) {
        final com.tencent.liteav.basic.c.a a = new com.tencent.liteav.basic.c.a(0, 0, this.c, this.d);
        if (n / (float)n2 >= this.c / (float)this.d) {
            final float n3 = (float)this.c;
            final float n4 = this.c * n2 / (float)n;
            a.a = 0;
            a.b = (int)(this.d - n4) / 2;
            a.c = (int)n3;
            a.d = (int)n4;
        }
        else {
            final float n5 = this.d * n / (float)n2;
            final float n6 = (float)this.d;
            a.a = (int)(this.c - n5) / 2;
            a.b = 0;
            a.c = (int)n5;
            a.d = (int)n6;
        }
        return a;
    }
    
    private com.tencent.liteav.basic.c.a a(final int n, final int n2, final int n3) {
        final com.tencent.liteav.basic.c.a a = new com.tencent.liteav.basic.c.a(0, 0, this.c, this.d);
        if (n / (float)n2 >= this.c / (float)this.d) {
            final float n4 = (float)this.c;
            final float n5 = this.c * n2 / (float)n;
            if (n3 == 1) {
                a.a = this.c;
            }
            else {
                a.a = 0;
            }
            if (n3 == 2) {
                a.b = this.d + (int)(this.d - n5) / 2;
            }
            else {
                a.b = (int)(this.d - n5) / 2;
            }
            a.c = (int)n4;
            a.d = (int)n5;
        }
        else {
            final float n6 = this.d * n / (float)n2;
            final float n7 = (float)this.d;
            if (n3 == 1) {
                a.a = this.c + (int)(this.c - n6) / 2;
            }
            else {
                a.a = (int)(this.c - n6) / 2;
            }
            if (n3 == 2) {
                a.b = this.d;
            }
            else {
                a.b = 0;
            }
            a.c = (int)n6;
            a.d = (int)n7;
        }
        return a;
    }
    
    private int a(final int a, final int a2, final long n, final int n2, final com.tencent.liteav.basic.c.a g, final com.tencent.liteav.basic.c.a g2) {
        final float a3 = com.tencent.liteav.j.a.a(n2, n / 1000L);
        final com.tencent.liteav.basic.e.a[] array = new com.tencent.liteav.basic.e.a[2];
        final com.tencent.liteav.basic.e.a a4 = new com.tencent.liteav.basic.e.a();
        a4.a = a;
        a4.b = 0;
        a4.c = g.c;
        a4.d = g.d;
        a4.g = g;
        array[0] = a4;
        final com.tencent.liteav.basic.e.a a5 = new com.tencent.liteav.basic.e.a();
        a5.a = a2;
        a5.b = 0;
        a5.c = g2.c;
        a5.d = g2.d;
        a5.g = g2;
        array[1] = a5;
        final int n3 = (int)(a3 * this.c);
        TXCLog.d("BitmapCombineRender", "processTwoPicLeftRightCombine, cropOffsetRatio = " + a3 + ", cropOffset = " + n3);
        final com.tencent.liteav.basic.c.a a6 = new com.tencent.liteav.basic.c.a(n3, 0, this.c, this.d);
        this.b.a(this.c * 2 + this.e, this.d);
        this.b.a(a6);
        return this.b.a(array, 0);
    }
    
    private int b(final int a, final int a2, final long n, final int n2, final com.tencent.liteav.basic.c.a g, final com.tencent.liteav.basic.c.a g2) {
        final float a3 = com.tencent.liteav.j.a.a(n2, n / 1000L);
        final com.tencent.liteav.basic.e.a[] array = new com.tencent.liteav.basic.e.a[2];
        final com.tencent.liteav.basic.e.a a4 = new com.tencent.liteav.basic.e.a();
        a4.a = a;
        a4.b = 0;
        a4.c = g.c;
        a4.d = g.d;
        a4.g = g;
        array[0] = a4;
        final com.tencent.liteav.basic.e.a a5 = new com.tencent.liteav.basic.e.a();
        a5.a = a2;
        a5.b = 0;
        a5.c = g2.c;
        a5.d = g2.d;
        a5.g = g2;
        array[1] = a5;
        final int n3 = (int)(a3 * this.d);
        TXCLog.d("BitmapCombineRender", "processTwoPicUpDownCombine, cropOffsetRatio = " + a3 + ", cropOffset = " + n3);
        final com.tencent.liteav.basic.c.a a6 = new com.tencent.liteav.basic.c.a(0, n3, this.c, this.d);
        this.b.a(this.c, this.d * 2 + this.e);
        this.b.a(a6);
        return this.b.a(array, 0);
    }
    
    private int c(final int a, final int a2, final long n, final int n2, final com.tencent.liteav.basic.c.a g, final com.tencent.liteav.basic.c.a g2) {
        final long n3 = n / 1000L;
        final float b = com.tencent.liteav.j.a.b(n2, n3);
        final float c = com.tencent.liteav.j.a.c(n2, n3);
        TXCLog.d("BitmapCombineRender", "processTwoPicZoom, scaleRatio = " + b + ", alpha = " + c);
        final com.tencent.liteav.basic.e.a[] array = new com.tencent.liteav.basic.e.a[2];
        final com.tencent.liteav.basic.e.a a3 = new com.tencent.liteav.basic.e.a();
        a3.a = a;
        a3.b = 0;
        a3.c = g.c;
        a3.d = g.d;
        a3.g = g;
        array[0] = a3;
        final com.tencent.liteav.basic.e.a a4 = new com.tencent.liteav.basic.e.a();
        a4.a = a2;
        a4.b = 0;
        a4.c = g2.c;
        a4.d = g2.d;
        a4.g = g2;
        array[1] = a4;
        if (null == a3.e) {
            a3.e = new com.tencent.liteav.basic.e.a.a();
        }
        a3.e.a = b;
        a3.e.c = c;
        if (a2 >= 0) {
            a4.e = new com.tencent.liteav.basic.e.a.a();
            if (n2 == 5) {
                a4.e.a = 1.1f;
            }
            a4.e.c = 1.0f - c;
        }
        this.b.a(this.c, this.d);
        this.b.a((com.tencent.liteav.basic.c.a)null);
        return this.b.a(array, 0);
    }
    
    private int d(final int a, final int a2, final long n, final int n2, final com.tencent.liteav.basic.c.a g, final com.tencent.liteav.basic.c.a g2) {
        final float c = com.tencent.liteav.j.a.c(n2, n / 1000L);
        TXCLog.d("BitmapCombineRender", "processTwoPicFaceInOut, alpha = " + c);
        final com.tencent.liteav.basic.e.a[] array = new com.tencent.liteav.basic.e.a[2];
        final com.tencent.liteav.basic.e.a a3 = new com.tencent.liteav.basic.e.a();
        a3.a = a;
        a3.b = 0;
        a3.c = g.c;
        a3.d = g.d;
        a3.g = g;
        array[0] = a3;
        final com.tencent.liteav.basic.e.a a4 = new com.tencent.liteav.basic.e.a();
        a4.a = a2;
        a4.b = 0;
        a4.c = g2.c;
        a4.d = g2.d;
        a4.g = g2;
        array[1] = a4;
        a3.e = new com.tencent.liteav.basic.e.a.a();
        a3.e.c = c;
        if (a2 >= 0) {
            a4.e = new com.tencent.liteav.basic.e.a.a();
            a4.e.c = 1.0f - c;
        }
        this.b.a(this.c, this.d);
        this.b.a((com.tencent.liteav.basic.c.a)null);
        return this.b.a(array, 0);
    }
    
    private int e(final int a, final int a2, final long n, final int n2, final com.tencent.liteav.basic.c.a g, final com.tencent.liteav.basic.c.a g2) {
        final long n3 = n / 1000L;
        final int d = com.tencent.liteav.j.a.d(n2, n3);
        final float b = com.tencent.liteav.j.a.b(n2, n3);
        TXCLog.d("BitmapCombineRender", "processTwoPicRotation, rotation = " + d + ", scale = " + b);
        final com.tencent.liteav.basic.e.a[] array = new com.tencent.liteav.basic.e.a[2];
        final com.tencent.liteav.basic.e.a a3 = new com.tencent.liteav.basic.e.a();
        a3.a = a;
        a3.b = 0;
        a3.c = g.c;
        a3.d = g.d;
        a3.g = g;
        array[0] = a3;
        final com.tencent.liteav.basic.e.a a4 = new com.tencent.liteav.basic.e.a();
        a4.a = a2;
        a4.b = 0;
        a4.c = g2.c;
        a4.d = g2.d;
        a4.g = g2;
        array[1] = a4;
        a3.e = new com.tencent.liteav.basic.e.a.a();
        a3.e.b = d;
        a3.e.a = b;
        a3.e.d = true;
        if (a2 >= 0) {
            a4.e = new com.tencent.liteav.basic.e.a.a();
        }
        if (d != 0) {
            a3.e.d = true;
            if (a4.e != null) {
                a4.e.d = true;
            }
        }
        else {
            a3.e.c = 1.0f;
            if (a4.e != null) {
                a4.e.c = 0.0f;
            }
        }
        this.b.a(this.c, this.d);
        this.b.a((com.tencent.liteav.basic.c.a)null);
        return this.b.a(array, 0);
    }
    
    public void a() {
        final int[] array = new int[2];
        int n = 0;
        if (this.f != null) {
            array[0] = this.f.a;
            ++n;
        }
        if (this.g != null) {
            array[1] = this.g.a;
            ++n;
        }
        GLES20.glDeleteTextures(n, array, 0);
        this.f = null;
        this.g = null;
        this.b.a();
    }
    
    protected class a
    {
        public int a;
        public int b;
        public int c;
    }
}
