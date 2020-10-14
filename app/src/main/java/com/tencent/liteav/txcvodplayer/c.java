package com.tencent.liteav.txcvodplayer;

import java.lang.ref.*;
import android.view.*;

public final class c
{
    private WeakReference<View> a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;
    private int i;
    
    public c(final View view) {
        this.i = 0;
        this.a = new WeakReference<View>(view);
    }
    
    public void a(final int b, final int c) {
        this.b = b;
        this.c = c;
    }
    
    public void b(final int d, final int e) {
        this.d = d;
        this.e = e;
    }
    
    public void a(final int f) {
        this.f = f;
    }
    
    public void c(int n, int n2) {
        if (this.f == 90 || this.f == 270) {
            final int n3 = n;
            n = n2;
            n2 = n3;
        }
        int g = View.getDefaultSize(this.b, n);
        int h = View.getDefaultSize(this.c, n2);
        if (this.i == 3) {
            g = n;
            h = n2;
        }
        else if (this.b > 0 && this.c > 0) {
            final int mode = View.MeasureSpec.getMode(n);
            final int size = View.MeasureSpec.getSize(n);
            final int mode2 = View.MeasureSpec.getMode(n2);
            final int size2 = View.MeasureSpec.getSize(n2);
            if (mode == Integer.MIN_VALUE && mode2 == Integer.MIN_VALUE) {
                final float n4 = size / (float)size2;
                float n5 = 0.0f;
                switch (this.i) {
                    case 4: {
                        n5 = 1.7777778f;
                        if (this.f == 90 || this.f == 270) {
                            n5 = 1.0f / n5;
                            break;
                        }
                        break;
                    }
                    case 5: {
                        n5 = 1.3333334f;
                        if (this.f == 90 || this.f == 270) {
                            n5 = 1.0f / n5;
                            break;
                        }
                        break;
                    }
                    default: {
                        n5 = this.b / (float)this.c;
                        if (this.d > 0 && this.e > 0) {
                            n5 = n5 * this.d / this.e;
                            break;
                        }
                        break;
                    }
                }
                final boolean b = n5 > n4;
                switch (this.i) {
                    case 0:
                    case 4:
                    case 5: {
                        if (b) {
                            g = size;
                            h = (int)(g / n5);
                            break;
                        }
                        h = size2;
                        g = (int)(h * n5);
                        break;
                    }
                    case 1: {
                        if (b) {
                            h = size2;
                            g = (int)(h * n5);
                            break;
                        }
                        g = size;
                        h = (int)(g / n5);
                        break;
                    }
                    default: {
                        if (b) {
                            g = Math.min(this.b, size);
                            h = (int)(g / n5);
                            break;
                        }
                        h = Math.min(this.c, size2);
                        g = (int)(h * n5);
                        break;
                    }
                }
            }
            else if (mode == 1073741824 && mode2 == 1073741824) {
                g = size;
                h = size2;
                if (this.b * h < g * this.c) {
                    g = h * this.b / this.c;
                }
                else if (this.b * h > g * this.c) {
                    h = g * this.c / this.b;
                }
            }
            else if (mode == 1073741824) {
                g = size;
                h = g * this.c / this.b;
                if (mode2 == Integer.MIN_VALUE && h > size2) {
                    h = size2;
                }
            }
            else if (mode2 == 1073741824) {
                h = size2;
                g = h * this.b / this.c;
                if (mode == Integer.MIN_VALUE && g > size) {
                    g = size;
                }
            }
            else {
                g = this.b;
                h = this.c;
                if (mode2 == Integer.MIN_VALUE && h > size2) {
                    h = size2;
                    g = h * this.b / this.c;
                }
                if (mode == Integer.MIN_VALUE && g > size) {
                    g = size;
                    h = g * this.c / this.b;
                }
            }
        }
        this.g = g;
        this.h = h;
    }
    
    public int a() {
        return this.g;
    }
    
    public int b() {
        return this.h;
    }
    
    public void b(final int i) {
        this.i = i;
    }
}
