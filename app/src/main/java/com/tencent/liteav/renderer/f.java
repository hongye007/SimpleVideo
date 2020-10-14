package com.tencent.liteav.renderer;

import android.view.*;
import java.lang.ref.*;

import android.graphics.*;
import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.module.*;
import android.os.*;

public class f extends BaseObj implements TextureView.SurfaceTextureListener
{
    private static final float[] a;
    private SurfaceTexture b;
    protected TextureView d;
    protected e e;
    protected int f;
    protected int g;
    protected int h;
    protected int i;
    protected int j;
    private int c;
    private com.tencent.liteav.basic.c.e q;
    private i r;
    private Surface s;
    private int t;
    protected int k;
    private int u;
    protected volatile int l;
    protected int m;
    protected int n;
    private int[] v;
    private int w;
    private long x;
    private long y;
    private long z;
    private long A;
    private boolean B;
    private boolean C;
    private boolean D;
    protected g o;
    WeakReference<b> p;
    private boolean E;
    private a F;
    
    public void a(final int j) {
        this.j = j;
    }
    
    private Bitmap a(final Bitmap bitmap, final int n, final int n2) {
        float n3;
        if (n2 / (float)n > bitmap.getHeight() / (float)bitmap.getWidth()) {
            n3 = n / (float)bitmap.getWidth();
        }
        else {
            n3 = n2 / (float)bitmap.getHeight();
        }
        final Matrix matrix = new Matrix();
        matrix.preScale(n3, n3);
        final Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        bitmap.recycle();
        return bitmap2;
    }
    
    private Bitmap a(final Matrix matrix, final Bitmap bitmap, final int n, final int n2) {
        final int n3 = 360 - (this.t + this.k) % 360;
        final Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        Bitmap bitmap3 = bitmap2;
        if (n3 != 0) {
            final Matrix matrix2 = new Matrix();
            matrix2.setRotate((float)n3);
            bitmap3 = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), matrix2, false);
            bitmap2.recycle();
        }
        if (this.u == 0) {
            final int width = bitmap3.getWidth();
            final int height = bitmap3.getHeight();
            final boolean b = n < n2;
            if (b != width < height) {
                if (b) {
                    final float n4 = height * (float)n / n2;
                    final float n5 = 0.5f * (width - n4);
                    final Matrix matrix3 = new Matrix();
                    matrix3.preScale(n / n4, n / n4);
                    final Bitmap bitmap4 = bitmap3;
                    bitmap3 = Bitmap.createBitmap(bitmap4, (int)n5, 0, (int)n4, height, matrix3, false);
                    bitmap4.recycle();
                }
                else {
                    final float n6 = width / (float)n * n2;
                    final float n7 = 0.5f * (height - n6);
                    final Matrix matrix4 = new Matrix();
                    matrix4.preScale(n2 / n6, n2 / n6);
                    final Bitmap bitmap5 = Bitmap.createBitmap(bitmap3, 0, (int)n7, width, (int)n6, (Matrix)null, false);
                    bitmap3 = Bitmap.createBitmap(bitmap5, 0, 0, bitmap5.getWidth(), bitmap5.getHeight(), matrix4, false);
                    bitmap5.recycle();
                }
            }
            else if (n != bitmap3.getWidth() && n2 != bitmap3.getHeight()) {
                bitmap3 = this.a(bitmap3, n, n2);
            }
        }
        else if (n != bitmap3.getWidth() && n2 != bitmap3.getHeight()) {
            bitmap3 = this.a(bitmap3, n, n2);
        }
        return bitmap3;
    }
    
    public void a(final o o) {
        final TextureView d = this.d;
        if (d != null) {
            Bitmap bitmap;
            try {
                bitmap = d.getBitmap();
            }
            catch (OutOfMemoryError outOfMemoryError) {
                bitmap = null;
            }
            final Bitmap bitmap2 = bitmap;
            if (null != bitmap2) {
                AsyncTask.execute((Runnable)new Runnable() {
                    final /* synthetic */ Matrix a = d.getTransform((Matrix)null);
                    
                    @Override
                    public void run() {
                        Bitmap a;
                        try {
                            a = com.tencent.liteav.renderer.f.this.a(this.a, bitmap2, d.getWidth(), d.getHeight());
                        }
                        catch (Exception ex) {
                            TXCLog.w("TXCVideoRender", "takePhoto error " + ex);
                            a = null;
                        }
                        catch (Error error) {
                            TXCLog.w("TXCVideoRender", "takePhoto error " + error);
                            a = null;
                        }
                        if (o != null) {
                            o.onTakePhotoComplete(a);
                        }
                    }
                });
            }
        }
        else if (this.q != null) {
            this.q.a(new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.renderer.f.this.q != null) {
                        com.tencent.liteav.renderer.f.this.q.a(o);
                    }
                }
            });
        }
        else if (o != null) {
            o.onTakePhotoComplete(null);
        }
    }
    
    public void b(final int c) {
        if (c > 0) {
            this.c = c;
        }
    }
    
    public f() {
        this.f = 0;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.c = 800;
        this.t = 0;
        this.k = 0;
        this.l = -1;
        this.m = 0;
        this.n = 0;
        this.v = new int[5];
        this.w = 500;
        this.x = 0L;
        this.y = 0L;
        this.z = 0L;
        this.A = 0L;
        this.B = false;
        this.C = false;
        this.E = false;
        this.F = new a();
        this.D = false;
    }
    
    public void a(final g o) {
        this.o = o;
    }
    
    public void a(final b b) {
        this.p = new WeakReference<b>(b);
    }
    
    public void a(final TextureView textureView) {
        this.b(textureView);
    }
    
    public void a(final Surface surface) {
        this.b(surface);
    }
    
    public void a(final TXSVideoFrame txsVideoFrame, final int n, final int n2, final int k) {
        if (k != this.k) {
            this.k = k;
            this.d(this.t);
        }
        this.a(n, n2);
        this.b();
    }
    
    public void a(final int n, final int n2, final int n3, final boolean b, final int n4) {
        this.a(n2, n3);
    }
    
    public void f() {
        Monitor.a(2, String.format("Remote-VideoRender[%d]: Start [tinyID:%s] [streamType:%d]", this.hashCode(), this.getID(), this.j), "streamType: 2-big, 3-small, 7-sub", 0);
        this.C = true;
        if (Build.VERSION.SDK_INT >= 21) {
            this.E = true;
        }
        else {
            this.E = false;
        }
        this.D = false;
        this.n();
    }
    
    public void a(final boolean b) {
        if (this.C) {
            Monitor.a(2, String.format("Remote-VideoRender[%d]: Stop [tinyID:%s][streamType:%d][stopRendThread:%s]", this.hashCode(), this.getID(), this.j, b ? "true" : "false"), "streamType: 2-big, 3-small, 7-sub", 0);
        }
        this.C = false;
        this.D = false;
        this.E = false;
        if (b && this.l == 1) {
            this.l = -1;
            TXCLog.w("TXCVideoRender", "play:vrender: quit render thread when stop");
            this.e();
            synchronized (this) {
                if (this.q != null) {
                    TXCLog.i("TXCVideoRender", "surface-render:stop render thread " + this.q);
                    this.q.a();
                    this.q = null;
                }
            }
        }
    }
    
    public void b(final int n, final int n2) {
        this.a(n, n2);
    }
    
    public void c(final int u) {
        this.u = u;
        if (this.e != null) {
            this.e.a(u);
        }
    }
    
    public void d(final int t) {
        this.t = t;
        if (this.e != null) {
            this.e.c((t + this.k) % 360);
        }
    }
    
    public SurfaceTexture a() {
        return null;
    }
    
    public int g() {
        if (this.d != null) {
            return this.d.getWidth();
        }
        if (this.s != null) {
            return this.m;
        }
        return 0;
    }
    
    public int h() {
        if (this.d != null) {
            return this.d.getHeight();
        }
        if (this.s != null) {
            return this.n;
        }
        return 0;
    }
    
    public int i() {
        return this.h;
    }
    
    public int j() {
        return this.i;
    }
    
    protected void a(final SurfaceTexture surfaceTexture) {
        this.B = true;
    }
    
    protected void b(final SurfaceTexture surfaceTexture) {
        this.B = false;
    }
    
    public void c(final Object o) {
    }
    
    public void e() {
    }
    
    public void e(final int w) {
        this.w = w;
    }
    
    private void b(final TextureView d) {
        if (d != null) {
            this.l = 0;
        }
        boolean b = false;
        if ((this.d == null && d != null) || (this.d != null && !this.d.equals(d))) {
            b = true;
        }
        TXCLog.w("TXCVideoRender", "play:vrender: set video view @old=" + this.d + ",new=" + d + "id " + this.getID() + "_" + this.j);
        if (b) {
            if (this.d != null && this.b == null) {
                this.b(this.d.getSurfaceTexture());
                this.d.setSurfaceTextureListener((TextureView.SurfaceTextureListener)null);
            }
            this.d = d;
            if (this.d != null) {
                if (this.d.getWidth() != 0) {
                    this.f = this.d.getWidth();
                }
                if (this.d.getHeight() != 0) {
                    this.g = this.d.getHeight();
                }
                (this.e = new e(this.d)).b(this.h, this.i);
                this.e.a(this.f, this.g);
                this.e.a(this.u);
                this.e.c((this.t + this.k) % 360);
                this.d.setSurfaceTextureListener((TextureView.SurfaceTextureListener)this);
                if (this.b != null) {
                    if (Build.VERSION.SDK_INT >= 16 && this.d.getSurfaceTexture() != this.b) {
                        TXCLog.w("TXCVideoRender", "play:vrender: setSurfaceTexture " + this.d + ", surfaceTexture " + this.b);
                        this.d.setSurfaceTexture(this.b);
                    }
                    else {
                        TXCLog.w("TXCVideoRender", "play:vrender: not setSurfaceTexture old surfaceTexture " + this.d.getSurfaceTexture() + ", new surfaceTexture " + this.b);
                    }
                }
                else if (this.d.isAvailable()) {
                    this.a(this.d.getSurfaceTexture());
                }
            }
        }
    }
    
    protected void a(final int h, final int i) {
        if ((this.h != h || this.i != i) && (this.h != h || this.i != i)) {
            this.h = h;
            this.i = i;
            if (this.e != null) {
                this.e.b(this.h, this.i);
            }
        }
    }
    
    public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture, final int f, final int g) {
        TXCLog.w("TXCVideoRender", "play:vrender: texture available @" + surfaceTexture + "id " + this.getID() + "_" + this.j);
        this.f = f;
        this.g = g;
        if (this.e != null) {
            this.e.a(this.f, this.g);
        }
        if (this.b != null) {
            if (Build.VERSION.SDK_INT >= 16 && this.d.getSurfaceTexture() != this.b) {
                this.d.setSurfaceTexture(this.b);
            }
            this.b = null;
        }
        else {
            this.a(surfaceTexture);
        }
        this.B = true;
    }
    
    public void onSurfaceTextureSizeChanged(final SurfaceTexture surfaceTexture, final int f, final int g) {
        TXCLog.w("TXCVideoRender", "play:vrender: texture size change new:" + f + "," + g + " old:" + this.f + "," + this.g);
        if (!this.B) {
            TXCLog.w("TXCVideoRender", "play:vrender: onSurfaceCreate on onSurfaceTextureSizeChanged when onSurfaceTextureAvailable is not trigger");
            this.B = true;
            this.a(surfaceTexture);
        }
        this.f = f;
        this.g = g;
        if (this.e != null) {
            this.e.a(this.f, this.g);
        }
    }
    
    public boolean onSurfaceTextureDestroyed(final SurfaceTexture b) {
        try {
            this.B = false;
            TXCLog.w("TXCVideoRender", "play:vrender:  onSurfaceTextureDestroyed when need save texture : " + this.E + "id " + this.getID() + "_" + this.j);
            if (this.E) {
                this.b = b;
            }
            else {
                this.F.a = 0L;
                this.b(b);
                if (b == this.b) {
                    this.b = null;
                }
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCVideoRender", "onSurfaceTextureDestroyed failed.", ex);
        }
        return this.b == null;
    }
    
    public void onSurfaceTextureUpdated(final SurfaceTexture surfaceTexture) {
    }
    
    private void b(final Surface s) {
        TXCLog.i("TXCVideoRender", "surface-render: set surface " + s);
        if (this.s == s) {
            TXCLog.i("TXCVideoRender", "surface-render: set the same surface, ignore ");
            return;
        }
        this.s = s;
        this.l = 1;
        if (s != null) {
            TXCLog.i("TXCVideoRender", "surface-render: set surface start render thread " + s);
            this.c(null);
        }
        else {
            synchronized (this) {
                if (this.q != null) {
                    TXCLog.i("TXCVideoRender", "surface-render: set surface stop render thread " + this.q);
                    this.q.a();
                    this.q = null;
                }
            }
        }
    }
    
    protected void k() {
    }
    
    protected void l() {
        synchronized (this) {
            if (this.q != null) {
                TXCLog.i("TXCVideoRender", "surface-render: onRenderThreadEGLDestroy stop render thread " + this.q);
                this.q.a();
                this.q = null;
            }
        }
        if (this.r != null) {
            this.r.c();
            this.r = null;
        }
    }
    
    protected void a(final Object o, final int n, final float[] array, final boolean b) {
        if (this.l == 1) {
            final int[] a = this.a(n, this.h, this.i, array, b);
            final int n2 = a[0];
            final int n3 = a[1];
            final int n4 = a[2];
            System.arraycopy(a, 0, this.v, 0, 3);
            if (b) {
                this.v[3] = 1;
                this.v[4] = 180;
            }
            else {
                this.v[3] = 0;
                this.v[4] = 0;
            }
            synchronized (this) {
                final Surface s = this.s;
                if (s != null) {
                    if (this.q != null) {
                        final Surface b2 = this.q.b();
                        if (b2 != s || (b2 != null && !b2.isValid())) {
                            TXCLog.i("TXCVideoRender", "surface-render: onDrawTextureToSurface surface change stop render thread " + this.q + ", " + b2 + ", " + s);
                            this.q.a();
                            this.q = null;
                        }
                    }
                    if (this.q == null && this.l == 1 && s.isValid()) {
                        this.q = new com.tencent.liteav.basic.c.e();
                        TXCLog.i("TXCVideoRender", "surface-render: onDrawTextureToSurface start render thread " + this.q + "," + s);
                        this.q.a(o, s);
                    }
                    if (this.q != null && this.l == 1) {
                        if (b) {
                            this.q.a(n2, true, 180, this.m, this.n, n3, n4, false, false);
                        }
                        else {
                            this.q.a(n2, false, 0, this.m, this.n, n3, n4, false, false);
                        }
                    }
                }
                else if (this.q != null) {
                    TXCLog.i("TXCVideoRender", "surface-render: onDrawTextureToSurface stop render thread " + this.q);
                    this.q.a();
                    this.q = null;
                }
            }
        }
    }
    
    private int[] a(final int n, final int n2, final int n3, final float[] array, final boolean b) {
        if (this.r != null && this.r.a() != b) {
            this.r.c();
            this.r = null;
        }
        if (this.r == null) {
            (this.r = new i(b)).b();
        }
        if (array != null) {
            this.r.a(array);
        }
        else {
            this.r.a(com.tencent.liteav.renderer.f.a);
        }
        final int m = this.m;
        final int n4 = this.n;
        if (this.u == 0) {
            this.r.a(com.tencent.liteav.renderer.i.a);
        }
        else {
            this.r.a(com.tencent.liteav.renderer.i.b);
        }
        int n5 = (this.t + this.k) % 360;
        if (b && (this.t == 90 || this.t == 270)) {
            n5 = (this.t + this.k + 180) % 360;
        }
        this.r.b(n5);
        this.r.b(n2, n3);
        this.r.a(m, n4);
        return new int[] { this.r.d(n), m, n4 };
    }
    
    public void c(final int m, final int n) {
        TXCLog.i("TXCVideoRender", "surface-render: set setSurfaceSize " + m + "*" + n);
        if (m != this.m || n != this.n) {
            if (this.q != null && this.l == 1 && this.v != null) {
                this.q.a(new Runnable() {
                    @Override
                    public void run() {
                        com.tencent.liteav.renderer.f.this.m = m;
                        com.tencent.liteav.renderer.f.this.n = n;
                        if (com.tencent.liteav.renderer.f.this.q != null) {
                            com.tencent.liteav.renderer.f.this.q.a(com.tencent.liteav.renderer.f.this.v[0], com.tencent.liteav.renderer.f.this.v[3] == 1, com.tencent.liteav.renderer.f.this.v[4], com.tencent.liteav.renderer.f.this.m, com.tencent.liteav.renderer.f.this.n, com.tencent.liteav.renderer.f.this.v[1], com.tencent.liteav.renderer.f.this.v[2], true, false);
                        }
                    }
                });
            }
            else {
                this.m = m;
                this.n = n;
            }
        }
    }
    
    public a m() {
        return this.F;
    }
    
    public void n() {
        this.F.a = 0L;
        this.F.b = 0L;
        this.F.c = 0L;
        this.F.d = 0L;
        this.F.e = 0L;
        this.F.f = 0L;
        this.F.g = 0L;
        this.F.h = 0L;
        this.F.i = 0L;
        this.F.k = 0;
        this.F.l = 0;
        this.setStatusValue(6001, this.j, 0L);
        this.setStatusValue(6002, this.j, 0.0);
        this.setStatusValue(6003, this.j, 0L);
        this.setStatusValue(6005, this.j, 0L);
        this.setStatusValue(6006, this.j, 0L);
        this.setStatusValue(6004, this.j, 0L);
    }
    
    private long a(final long n) {
        final long timeTick = TXCTimeUtil.getTimeTick();
        if (n > timeTick) {
            return 0L;
        }
        return timeTick - n;
    }
    
    public void o() {
        if (this.F.a == 0L) {
            this.F.a = TXCTimeUtil.getTimeTick();
        }
        else {
            final long n = TXCTimeUtil.getTimeTick() - this.F.a;
            if (n >= 1000L) {
                final double doubleValue = (this.F.c - this.F.b) * 1000.0 / n;
                this.setStatusValue(6002, this.j, doubleValue);
                TXCKeyPointReportProxy.a(this.getID(), 40001, (int)doubleValue, this.j);
                this.F.b = this.F.c;
                final a f = this.F;
                f.a += n;
            }
        }
    }
    
    private void b() {
        if (!this.D) {
            final Bundle bundle = new Bundle();
            bundle.putString("EVT_USERID", this.getID());
            bundle.putInt("EVT_ID", 2003);
            bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            bundle.putCharSequence("EVT_MSG", (CharSequence)"Render the first video frame(IDR)");
            bundle.putInt("EVT_PARAM1", this.h);
            bundle.putInt("EVT_PARAM2", this.i);
            com.tencent.liteav.basic.util.f.a(this.p, 2003, bundle);
            this.setStatusValue(6001, this.j, TXCTimeUtil.getTimeTick());
            this.setStatusValue(6015, this.j, this.h);
            this.setStatusValue(6016, this.j, this.i);
            TXCLog.i("TXCVideoRender", "trtc_render render first frame " + this.getID() + ", " + this.j);
            this.D = true;
            Monitor.a(2, String.format("Remote-VideoRender[%d]: Render first frame [tinyID:%s][streamType:%d]", this.hashCode(), this.getID(), this.j), "streamType: 2-big, 3-small, 7-sub", 0);
            TXCKeyPointReportProxy.a(this.getID(), 40022, 0L, this.j);
        }
        final a f = this.F;
        ++f.c;
        this.o();
        if (this.F.d != 0L) {
            this.F.i = this.a(this.F.d);
            if (this.F.i > this.w) {
                final a f2 = this.F;
                ++f2.e;
                this.setStatusValue(6003, this.j, this.F.e);
                if (this.F.i > this.F.h) {
                    this.F.h = this.F.i;
                    this.setStatusValue(6005, this.j, this.F.h);
                }
                final a f3 = this.F;
                f3.g += this.F.i;
                this.setStatusValue(6006, this.j, this.F.g);
                TXCLog.w("TXCVideoRender", "render frame count:" + this.F.c + " block time:" + this.F.i + "> 500");
            }
            if (this.F.i > this.c) {
                ++this.x;
                this.z += this.F.i;
                TXCLog.w("TXCVideoRender", "render frame count:" + this.F.c + " block time:" + this.F.i + "> " + this.c);
                com.tencent.liteav.basic.util.f.a(this.p, this.getID(), 2105, "Current video playback stuck for " + this.F.i + "ms");
            }
            if (this.F.i > 1000L) {
                final a f4 = this.F;
                ++f4.f;
                this.setStatusValue(6004, this.j, this.F.f);
                TXCLog.w("TXCVideoRender", "render frame count:" + this.F.c + " block time:" + this.F.i + "> 1000");
            }
        }
        final long timeTick = TXCTimeUtil.getTimeTick();
        if (this.y == 0L) {
            this.y = timeTick;
        }
        else if (timeTick - this.y >= 2000L) {
            this.setStatusValue(17015, this.j, this.x);
            this.setStatusValue(17016, this.j, this.z);
            if (this.A != 0L) {
                TXCKeyPointReportProxy.a(this.getID(), 40005, (int)this.z, this.j);
                TXCKeyPointReportProxy.a(this.getID(), 40006, (int)(timeTick - this.y), this.j);
            }
            this.x = 0L;
            this.y = timeTick;
            this.z = 0L;
        }
        this.F.d = TXCTimeUtil.getTimeTick();
        if (this.A == 0L) {
            this.A = this.F.d;
        }
        this.F.l = this.i;
        this.F.k = this.h;
    }
    
    static {
        a = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f };
    }
    
    public static class a
    {
        public long a;
        public long b;
        public long c;
        public long d;
        public long e;
        public long f;
        public long g;
        public long h;
        public long i;
        public long j;
        public int k;
        public int l;
    }
}
