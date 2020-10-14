package com.tencent.liteav.beauty;

import com.tencent.liteav.basic.module.*;
import android.content.*;
import android.app.*;
import com.tencent.liteav.basic.log.*;

import android.content.pm.*;
import android.graphics.*;
import android.os.*;
import android.annotation.*;
import java.lang.ref.*;
import java.util.*;

public class e extends BaseObj implements com.tencent.liteav.beauty.f
{
    protected Context a;
    protected boolean b;
    protected boolean c;
    protected int d;
    protected int e;
    protected int f;
    protected com.tencent.liteav.basic.c.a g;
    protected com.tencent.liteav.beauty.c h;
    protected b i;
    protected c j;
    private d l;
    private long m;
    private long n;
    private long o;
    private Object p;
    com.tencent.liteav.beauty.g k;
    private a q;
    
    @Override
    public int willAddWatermark(final int a, final int e, final int f) {
        if (this.k != null) {
            final com.tencent.liteav.basic.structs.b b = new com.tencent.liteav.basic.structs.b();
            b.e = e;
            b.f = f;
            b.j = 0;
            b.i = (this.j != null && this.j.e);
            b.a = a;
            return this.k.a(b);
        }
        return 0;
    }
    
    @Override
    public void didProcessFrame(final int a, final int e, final int f, final long n) {
        this.c();
        if (this.k != null) {
            final com.tencent.liteav.basic.structs.b b = new com.tencent.liteav.basic.structs.b();
            b.e = e;
            b.f = f;
            b.j = 0;
            b.i = (this.j != null && this.j.e);
            b.a = a;
            this.k.a(b, n);
        }
    }
    
    @Override
    public void didProcessFrame(final byte[] array, final int n, final int n2, final int n3, final long n4) {
        if (this.k != null) {
            this.k.b(array, n, n2, n3, n4);
        }
    }
    
    private void c() {
        if (this.m != 0L) {
            this.setStatusValue(3002, System.currentTimeMillis() - this.m);
        }
        ++this.n;
        final long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > this.o + 2000L) {
            this.setStatusValue(3003, this.n * 1000.0 / (currentTimeMillis - this.o));
            this.n = 0L;
            this.o = currentTimeMillis;
        }
    }
    
    public e(final Context a, final boolean b) {
        this.b = true;
        this.c = false;
        this.d = 0;
        this.e = 0;
        this.f = 1;
        this.g = null;
        this.i = new b();
        this.j = null;
        this.l = com.tencent.liteav.beauty.e.d.c;
        this.m = 0L;
        this.n = 0L;
        this.o = 0L;
        this.q = new a(this);
        final ActivityManager activityManager = (ActivityManager)a.getSystemService("activity");
        TXCLog.i("TXCVideoPreprocessor", "TXCVideoPreprocessor version: VideoPreprocessor-v1.1");
        final ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();
        if (null != deviceConfigurationInfo) {
            TXCLog.i("TXCVideoPreprocessor", "opengl es version " + deviceConfigurationInfo.reqGlEsVersion);
            TXCLog.i("TXCVideoPreprocessor", "set GLContext " + b);
            if (deviceConfigurationInfo.reqGlEsVersion > 131072) {
                TXCLog.i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_3");
                com.tencent.liteav.basic.c.j.a(3);
            }
            else {
                TXCLog.i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_2");
                com.tencent.liteav.basic.c.j.a(2);
            }
        }
        else {
            TXCLog.e("TXCVideoPreprocessor", "getDeviceConfigurationInfo opengl Info failed!");
        }
        this.a = a;
        this.b = b;
        this.h = new com.tencent.liteav.beauty.c(this.a, this.b);
        com.tencent.liteav.beauty.a.a().a(a);
    }
    
    public void a(final float[] array) {
        if (this.h != null) {
            this.h.a(array);
        }
    }
    
    public void a(final boolean b) {
        if (this.h != null) {
            this.h.a(b);
        }
    }
    
    public synchronized int a(final byte[] array, final int n, final int n2, final int n3, final int n4, final int n5) {
        this.a(n, n2, this.z(n3), n4, n5);
        this.h.b(this.i);
        return this.h.a(array, n4);
    }
    
    public synchronized int a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return this.a(n, n2, n3, n4, n5, n6, 0L);
    }
    
    public synchronized int a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final long n7) {
        this.a(n2, n3, this.z(n4), n5, n6);
        this.h.b(this.i);
        return this.h.a(n, n5, n7);
    }
    
    public synchronized int a(final com.tencent.liteav.basic.structs.b b, final int n, final int n2, final long n3) {
        this.m = System.currentTimeMillis();
        this.a(b.l);
        this.a(b.g, b.h);
        this.b(b.i);
        this.a(b.c);
        this.a(b.d);
        if (b.m != null && b.a == -1) {
            return this.a(b.m, b.e, b.f, b.j, n, n2);
        }
        return this.a(b.a, b.e, b.f, b.j, n, n2, n3);
    }
    
    public synchronized void a(final d l) {
        this.l = l;
        TXCLog.i("TXCVideoPreprocessor", "set Process SDK performance " + l);
    }
    
    public synchronized void a(final com.tencent.liteav.basic.c.a g) {
        this.g = g;
    }
    
    public synchronized void a(final int d, final int e) {
        this.d = d;
        this.e = e;
    }
    
    public synchronized void a(final Bitmap bitmap, final float n, final float n2, final float n3) {
        if (n < 0.0f || n2 < 0.0f || n3 < 0.0) {
            TXCLog.e("TXCVideoPreprocessor", "WaterMark param is Error!");
            return;
        }
        if (null != this.h) {
            this.h.a(bitmap, n, n2, n3);
        }
    }
    
    public synchronized void a(final List<f> list) {
        if (null != this.h) {
            com.tencent.liteav.beauty.a.a().g();
            this.h.a(list);
        }
    }
    
    public synchronized void b(final boolean c) {
        this.c = c;
    }
    
    public synchronized void a(final Object p) {
        this.p = p;
    }
    
    public synchronized Object a() {
        return this.p;
    }
    
    public synchronized void a(final com.tencent.liteav.beauty.f f) {
        if (null == this.h) {
            TXCLog.e("TXCVideoPreprocessor", "setListener mDrawer is null!");
            return;
        }
        this.h.a(f);
    }
    
    public synchronized void a(final com.tencent.liteav.beauty.g k) {
        if (null == this.h) {
            TXCLog.e("TXCVideoPreprocessor", "setListener mDrawer is null!");
            return;
        }
        if ((this.k = k) == null) {
            this.h.a((com.tencent.liteav.beauty.f)null);
        }
        else {
            this.h.a(this);
        }
    }
    
    public synchronized void a(final com.tencent.liteav.basic.b.b b) {
        if (null == this.h) {
            TXCLog.e("TXCVideoPreprocessor", "setListener mDrawer is null!");
            return;
        }
        this.h.a(b);
    }
    
    private int z(final int n) {
        int n2 = n;
        switch (n) {
            case 1: {
                n2 = 90;
                break;
            }
            case 2: {
                n2 = 180;
                break;
            }
            case 3: {
                n2 = 270;
                break;
            }
        }
        return n2;
    }
    
    private boolean a(final int b, final int c, final int d, final int k, final int l) {
        if (null == this.j) {
            this.j = new c();
            this.n = 0L;
            this.o = System.currentTimeMillis();
        }
        if (b != this.j.b || c != this.j.c || d != this.j.d || (this.d > 0 && this.d != this.j.f) || (this.e > 0 && this.e != this.j.g) || (null != this.g && ((this.g.c > 0 && (null == this.j.j || this.g.c != this.j.j.c)) || (this.g.d > 0 && (null == this.j.j || this.g.d != this.j.j.d)) || (this.g.a >= 0 && (null == this.j.j || this.g.a != this.j.j.a)) || (this.g.b >= 0 && (null == this.j.j || this.g.b != this.j.j.b)))) || this.c != this.j.e || this.j.h != k) {
            TXCLog.i("TXCVideoPreprocessor", "Init sdk");
            TXCLog.i("TXCVideoPreprocessor", "Input widht " + b + " height " + c);
            this.j.b = b;
            this.j.c = c;
            int c3;
            int d3;
            if (null != this.g && this.g.a >= 0 && this.g.b >= 0 && this.g.c > 0 && this.g.d > 0) {
                TXCLog.i("TXCVideoPreprocessor", "set Crop Rect; init ");
                final int c2 = (b - this.g.a > this.g.c) ? this.g.c : (b - this.g.a);
                final int d2 = (c - this.g.b > this.g.d) ? this.g.d : (c - this.g.b);
                this.g.c = c2;
                this.g.d = d2;
                c3 = this.g.c;
                d3 = this.g.d;
            }
            else {
                c3 = b;
                d3 = c;
            }
            this.j.j = this.g;
            this.j.d = d;
            this.j.a = this.b;
            this.j.h = k;
            this.j.i = l;
            this.j.f = this.d;
            this.j.g = this.e;
            if (this.j.f <= 0 || this.j.g <= 0) {
                if (90 == this.j.d || 270 == this.j.d) {
                    this.j.f = d3;
                    this.j.g = c3;
                }
                else {
                    this.j.f = c3;
                    this.j.g = d3;
                }
            }
            int n;
            int n2;
            if (this.l == com.tencent.liteav.beauty.e.d.a) {
                if (90 == this.j.d || 270 == this.j.d) {
                    n = this.j.g;
                    n2 = this.j.f;
                }
                else {
                    n = this.j.f;
                    n2 = this.j.g;
                }
            }
            else if (this.l == com.tencent.liteav.beauty.e.d.b) {
                n = c3;
                n2 = d3;
            }
            else {
                final com.tencent.liteav.basic.util.d b2 = this.b(c3, d3, this.j.d, this.j.f, this.j.g);
                n = (b2.a + 7) / 8 * 8;
                n2 = (b2.b + 7) / 8 * 8;
            }
            this.j.e = this.c;
            if (!this.a(this.j, n, n2)) {
                TXCLog.e("TXCVideoPreprocessor", "init failed!");
                return false;
            }
        }
        else if (k != this.j.h || l != this.j.i) {
            this.j.h = k;
            this.i.k = k;
            this.j.i = l;
            this.i.l = l;
            this.h.b(l);
        }
        return true;
    }
    
    private com.tencent.liteav.basic.util.d b(final int n, final int n2, final int n3, int n4, int n5) {
        if (n3 == 90 || n3 == 270) {
            final int n6 = n5;
            n5 = n4;
            n4 = n6;
        }
        final int min = Math.min(n4, n5);
        final int min2 = Math.min(n, n2);
        for (final int n7 : new int[] { 720, 1080, 1280 }) {
            if (min <= n7 && min2 >= n7) {
                final float n8 = 1.0f * n7 / min;
                return new com.tencent.liteav.basic.util.d((int)(n8 * n4), (int)(n8 * n5));
            }
        }
        return new com.tencent.liteav.basic.util.d(n, n2);
    }
    
    private boolean a(final c c, final int g, final int f) {
        this.i.d = c.b;
        this.i.e = c.c;
        this.i.m = c.j;
        this.i.g = g;
        this.i.f = f;
        this.i.h = (c.d + 360) % 360;
        this.i.b = c.f;
        this.i.c = c.g;
        this.i.a = 0;
        this.i.j = c.a;
        this.i.i = c.e;
        this.i.k = c.h;
        this.i.l = c.i;
        if (null == this.h) {
            (this.h = new com.tencent.liteav.beauty.c(this.a, c.a)).a(this.f);
        }
        return this.h.a(this.i);
    }
    
    public void a(final int f) {
        if (f != this.f) {
            this.f = f;
            if (this.h != null) {
                this.h.a(this.f);
            }
        }
    }
    
    public synchronized void b() {
        if (null != this.h) {
            this.h.a();
        }
        this.j = null;
    }
    
    public synchronized void b(final int n) {
        if (this.h != null) {
            this.h.d(n);
        }
        this.q.a("beautyStyle", n);
    }
    
    public synchronized void c(final int n) {
        int n2 = n;
        if (n2 > 9) {
            n2 = 9;
            TXCLog.e("TXCVideoPreprocessor", "Beauty value too large! set max value 9");
        }
        else if (n2 < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Beauty < 0; set 0");
            n2 = 0;
        }
        if (this.h != null) {
            this.h.c(n2);
        }
        this.q.a("beautyLevel", n2);
    }
    
    public synchronized void d(final int n) {
        int n2 = n;
        if (n2 > 9) {
            n2 = 9;
            TXCLog.e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
        }
        else if (n2 < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness < 0; set 0");
            n2 = 0;
        }
        if (this.h != null) {
            this.h.e(n2);
        }
        this.q.a("whiteLevel", n2);
    }
    
    public synchronized void e(final int n) {
        int n2 = n;
        if (n2 > 9) {
            n2 = 9;
            TXCLog.e("TXCVideoPreprocessor", "Ruddy value too large! set max value 9");
        }
        else if (n2 < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Ruddy < 0; set 0");
            n2 = 0;
        }
        if (this.h != null) {
            this.h.g(n2);
        }
        this.q.a("ruddyLevel", n2);
    }
    
    public void f(final int n) {
        int n2 = n;
        if (n2 > 9) {
            n2 = 9;
            TXCLog.e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
        }
        else if (n2 < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness < 0; set 0");
            n2 = 0;
        }
        if (this.h != null) {
            this.h.f(n2);
        }
    }
    
    public synchronized void a(final String s) {
        if (this.h != null) {
            this.h.a(s);
        }
    }
    
    public synchronized void c(final boolean b) {
        if (this.h != null) {
            this.h.b(b);
        }
    }
    
    @TargetApi(18)
    public boolean a(final String s, final boolean b) {
        if (Build.VERSION.SDK_INT < 18) {
            return false;
        }
        if (this.h != null) {
            this.h.a(s, b);
        }
        return true;
    }
    
    public synchronized void g(final int n) {
        if (this.h != null) {
            this.h.h(n);
        }
        this.q.a("eyeBigScale", n);
    }
    
    public synchronized void h(final int n) {
        if (this.h != null) {
            this.h.i(n);
        }
        this.q.a("faceSlimLevel", n);
    }
    
    public void i(final int n) {
        if (this.h != null) {
            this.h.j(n);
        }
        this.q.a("faceVLevel", n);
    }
    
    public void j(final int n) {
        if (this.h != null) {
            this.h.k(n);
        }
        this.q.a("faceShortLevel", n);
    }
    
    public void k(final int n) {
        if (this.h != null) {
            this.h.l(n);
        }
        this.q.a("chinLevel", n);
    }
    
    public void l(final int n) {
        if (this.h != null) {
            this.h.m(n);
        }
        this.q.a("noseSlimLevel", n);
    }
    
    public void m(final int n) {
        if (this.h != null) {
            this.h.n(n);
        }
        this.q.a("eyeLightenLevel", n);
    }
    
    public void n(final int n) {
        if (this.h != null) {
            this.h.o(n);
        }
        this.q.a("toothWhitenLevel", n);
    }
    
    public void o(final int n) {
        if (this.h != null) {
            this.h.p(n);
        }
        this.q.a("wrinkleRemoveLevel", n);
    }
    
    public void p(final int n) {
        if (this.h != null) {
            this.h.q(n);
        }
        this.q.a("pounchRemoveLevel", n);
    }
    
    public void q(final int n) {
        if (this.h != null) {
            this.h.r(n);
        }
        this.q.a("smileLinesRemoveLevel", n);
    }
    
    public void r(final int n) {
        if (this.h != null) {
            this.h.s(n);
        }
        this.q.a("foreheadLevel", n);
    }
    
    public void s(final int n) {
        if (this.h != null) {
            this.h.t(n);
        }
        this.q.a("eyeDistanceLevel", n);
    }
    
    public void t(final int n) {
        if (this.h != null) {
            this.h.u(n);
        }
        this.q.a("eyeAngleLevel", n);
    }
    
    public void u(final int n) {
        if (this.h != null) {
            this.h.v(n);
        }
        this.q.a("mouthShapeLevel", n);
    }
    
    public void v(final int n) {
        if (this.h != null) {
            this.h.w(n);
        }
        this.q.a("noseWingLevel", n);
    }
    
    public void w(final int n) {
        if (this.h != null) {
            this.h.x(n);
        }
        this.q.a("nosePositionLevel", n);
    }
    
    public void x(final int n) {
        if (this.h != null) {
            this.h.y(n);
        }
        this.q.a("lipsThicknessLevel", n);
    }
    
    public void y(final int n) {
        if (this.h != null) {
            this.h.z(n);
        }
        this.q.a("faceBeautyLevel", n);
    }
    
    public synchronized void a(final float n) {
        if (this.h != null) {
            this.h.a(n);
        }
    }
    
    public synchronized void a(final Bitmap bitmap) {
        if (this.h != null) {
            this.h.a(bitmap);
        }
    }
    
    public void a(final float n, final Bitmap bitmap, final float n2, final Bitmap bitmap2, final float n3) {
        if (this.h != null) {
            this.h.a(n, bitmap, n2, bitmap2, n3);
        }
    }
    
    public synchronized void b(final float n) {
        if (this.h != null) {
            this.h.b(n);
        }
    }
    
    @Override
    public void setID(final String id) {
        super.setID(id);
        this.setStatusValue(3001, this.q.a());
    }
    
    public enum d
    {
        a, 
        b, 
        c;
    }
    
    public static class f
    {
        public Bitmap a;
        public float b;
        public float c;
        public float d;
        public int e;
        public int f;
        public int g;
    }
    
    public enum g
    {
        a, 
        b;
    }
    
    public static class e
    {
        public g a;
        public boolean b;
        
        public e() {
            this.a = g.b;
            this.b = false;
        }
    }
    
    private static class c
    {
        public boolean a;
        public int b;
        public int c;
        public int d;
        public boolean e;
        public int f;
        public int g;
        public int h;
        public int i;
        public com.tencent.liteav.basic.c.a j;
        
        private c() {
            this.e = false;
            this.h = 5;
            this.i = 0;
            this.j = null;
        }
    }
    
    static class b
    {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h;
        boolean i;
        boolean j;
        public int k;
        public int l;
        com.tencent.liteav.basic.c.a m;
        
        b() {
            this.k = 5;
            this.l = 0;
            this.m = null;
        }
    }
    
    protected static class a
    {
        private HashMap<String, String> b;
        WeakReference<e> a;
        
        public a(final e e) {
            this.b = new HashMap<String, String>();
            this.a = new WeakReference<e>(e);
        }
        
        public void a(final String s, final int n) {
            this.b.put(s, String.valueOf(n));
            final e e = this.a.get();
            if (e != null) {
                final String id = e.getID();
                if (id != null && id.length() > 0) {
                    e.setStatusValue(3001, this.a());
                }
            }
        }
        
        public String a() {
            String string = "";
            for (final String s : this.b.keySet()) {
                string = string + s + ":" + this.b.get(s) + " ";
            }
            return "{" + string + "}";
        }
    }
}
