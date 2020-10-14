package com.tencent.liteav;

import android.content.*;

import java.lang.ref.*;
import com.tencent.liteav.basic.log.*;
import javax.microedition.khronos.egl.*;
import android.os.*;

import com.tencent.liteav.renderer.*;
import android.graphics.*;
import com.tencent.liteav.basic.module.*;
import android.app.*;
import java.util.*;

public class CameraProxy implements b, n, com.tencent.liteav.capturer.b, p
{
    private Context b;
    private final a c;
    private q d;
    private boolean e;
    private i f;
    private int g;
    private m h;
    private boolean i;
    private long j;
    private long k;
    private long l;
    private int m;
    private Object n;
    private HandlerThread o;
    private Handler p;
    private String q;
    private boolean r;
    WeakReference<b> a;
    
    public CameraProxy(final Context b, final i i, final m h, final boolean w) {
        this.g = 0;
        this.h = null;
        this.i = false;
        this.j = 0L;
        this.l = 0L;
        this.m = 0;
        this.n = new Object();
        this.o = null;
        this.p = null;
        this.q = "";
        this.r = true;
        this.c = new a();
        try {
            this.f = (i)i.clone();
        }
        catch (CloneNotSupportedException ex) {
            this.f = new i();
            ex.printStackTrace();
        }
        this.b = b;
        (this.h = h).setSurfaceTextureListener(this);
        this.f.W = w;
        this.c.b(this.f.U);
    }
    
    @Override
    public void a() {
        Monitor.a(2, String.format("VideoCapture[%d]: start camera", this.hashCode()), "", 0);
        TXCLog.i("CameraCapture", "start->enter with getSurfaceTexture:" + this.h.getSurfaceTexture());
        this.h.a(this.f.h, !this.f.W);
        this.a(this.h.getSurfaceTexture());
    }
    
    @Override
    public void a(final boolean b) {
        Monitor.a(2, String.format("VideoCapture[%d]: stop camera", this.hashCode()), "", 0);
        this.c();
        this.h.a();
        synchronized (this.n) {
            if (this.p != null) {
                this.p.removeCallbacksAndMessages((Object)null);
            }
            if (this.o != null) {
                TXCLog.w("CameraCapture", "stop camera monitor ");
                this.o.quit();
                this.o = null;
                this.p = null;
            }
        }
    }
    
    @Override
    public void a(final String q) {
        this.q = q;
    }
    
    @Override
    public void b() {
        TXCLog.i("CameraCapture", "startCapture->enter with getSurfaceTexture:" + this.h.getSurfaceTexture());
        this.a(this.h.getSurfaceTexture());
    }
    
    @Override
    public void c() {
        TXCLog.i("CameraCapture", "stopCapture->enter with null");
        this.c.a((com.tencent.liteav.capturer.b)null);
        this.c.g();
        this.e = false;
    }
    
    @Override
    public void b(final boolean b) {
        if (this.e && this.c != null) {
            this.f.m = (b ? (!this.f.m) : this.f.m);
            this.c.g();
            this.h.a(false);
            this.c.a(this.f.h);
            this.c.c(this.f.l);
            this.c.a(this.n());
            this.c.a(this.f.W, this.f.a, this.f.b);
            this.c.a(this);
            this.c.a(this.h.getSurfaceTexture());
            TXCLog.i("CameraCapture", String.format("vsize refreshCapture w*h:%d*%d orientation:%d", this.f.a, this.f.b, this.f.l));
            if (this.c.d(this.f.m) == 0) {
                this.e = true;
                Monitor.a(2, String.format("VideoCapture[%d]: start %s camera successfully", this.hashCode(), this.f.m ? "front" : "back"), "", 0);
                this.a(1003, "Enabled camera successfully");
            }
            else {
                this.e = false;
                this.a(-1301, "Failed to open the camera, please confirm whether the camera permission is turned on");
            }
            this.i = false;
        }
    }
    
    @Override
    public boolean d() {
        return this.e;
    }
    
    @Override
    public int e() {
        return this.c.f();
    }
    
    @Override
    public boolean a(final int n) {
        return this.c.b(n);
    }
    
    @Override
    public void a(final int n, final int n2) {
        this.c.a((float)n, (float)n2);
    }
    
    @Override
    public void a(final float n) {
        this.c.a(n);
    }
    
    @Override
    public void b(final int g) {
        this.g = g;
    }
    
    @Override
    public void c(final int rendMode) {
        if (this.h != null) {
            this.h.setRendMode(rendMode);
        }
    }
    
    @Override
    public void d(final int rendMirror) {
        if (this.h != null) {
            this.h.setRendMirror(rendMirror);
        }
    }
    
    @Override
    public void a(final q d) {
        this.d = d;
    }
    
    @Override
    public void c(final boolean b) {
        this.a(new Runnable() {
            @Override
            public void run() {
                CameraProxy.this.f.S = b;
            }
        });
    }
    
    @Override
    public boolean d(final boolean b) {
        return this.c.a(b);
    }
    
    @Override
    public void a(final com.tencent.liteav.basic.structs.b b) {
        if (this.h != null) {
            this.h.a(b.a, b.i, this.g, b.e, b.f, this.c.i());
        }
    }
    
    @Override
    public void a(final Runnable runnable) {
        this.h.a(runnable);
    }
    
    @Override
    public EGLContext getEGLContext() {
        return this.h.getGLContext();
    }
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
        com.tencent.liteav.basic.util.f.a(this.a, n, bundle);
    }
    
    @Override
    public void a(final b b) {
        this.a = new WeakReference<b>(b);
    }
    
    @Override
    public void e(final int l) {
        this.f.l = l;
        this.c.c(this.f.l);
        this.r = true;
        TXCLog.i("CameraCapture", String.format("vsize setCaptureOrientation w*h:%d*%d orientation:%d", this.f.a, this.f.b, this.f.l));
    }
    
    @Override
    public void a(final com.tencent.liteav.basic.a.c k) {
        this.f.k = k;
        this.r = true;
    }
    
    @Override
    public void b(final int a, final int b) {
        this.f.a = a;
        this.f.b = b;
        this.r = true;
        TXCLog.i("CameraCapture", String.format("vsize setVideoEncSize w*h:%d*%d orientation:%d", this.f.a, this.f.b, this.f.l));
    }
    
    @Override
    public void f(final int n) {
        this.f.h = n;
        if (this.c != null) {
            this.c.a(n);
        }
        if (this.h != null && this.h instanceof TXCGLSurfaceView) {
            ((TXCGLSurfaceView)this.h).setFPS(n);
        }
    }
    
    @Override
    public void a(final float n, final float n2) {
        if (this.c != null && this.f.K) {
            this.c.a(n, n2);
        }
    }
    
    @Override
    public int g() {
        return this.f.h;
    }
    
    @Override
    public boolean h() {
        return this.c != null && this.c.b();
    }
    
    @Override
    public boolean i() {
        return this.c != null && this.c.c();
    }
    
    @Override
    public boolean j() {
        return this.c != null && this.c.d();
    }
    
    @Override
    public boolean k() {
        return this.c != null && this.c.e();
    }
    
    @Override
    public boolean l() {
        return this.c != null && this.c.i();
    }
    
    private void a(final SurfaceTexture surfaceTexture) {
        if (surfaceTexture != null && !this.e && this.c != null) {
            this.c.a(this);
            this.c.a(surfaceTexture);
            this.c.a(this.f.h);
            this.c.c(this.f.l);
            this.c.c(this.f.K);
            this.c.a(this.n());
            this.c.a(this.f.W, this.f.a, this.f.b);
            TXCLog.i("CameraCapture", String.format("vsize startCapture w*h:%d*%d orientation:%d", this.f.a, this.f.b, this.f.l));
            if (this.c.d(this.f.m) == 0) {
                this.e = true;
                this.k = System.currentTimeMillis();
                Monitor.a(2, String.format("VideoCapture[%d]: start %s camera successfully", this.hashCode(), this.f.m ? "front" : "back"), "", 0);
                this.a(1003, "Enabled camera successfully");
                this.i = false;
            }
            else {
                this.e = false;
                this.a(-1301, "Failed to open camera, please confirm whether the camera permission is turned on");
            }
        }
    }
    
    private void a(final int n, final String s) {
        com.tencent.liteav.basic.util.f.a(this.a, n, s);
    }
    
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture) {
        TXCLog.i("CameraCapture", "onSurfaceTextureAvailable->enter with mListener:" + this.d);
        this.a(surfaceTexture);
        if (this.d != null) {
            this.d.a(surfaceTexture);
        }
    }
    
    @Override
    public void onSurfaceTextureDestroy(final SurfaceTexture surfaceTexture) {
        this.c();
        TXCLog.i("CameraCapture", "onSurfaceTextureDestroy->enter with mListener:" + this.d);
        if (this.d != null) {
            this.d.s();
        }
    }
    
    @Override
    public int onTextureProcess(final int n, final float[] array) {
        this.a(n, null, array, 4);
        return 0;
    }
    
    @Override
    public void onBufferProcess(final byte[] array, final float[] array2) {
        this.a(-1, array, array2, 3);
    }
    
    private void a(final int a, final byte[] m, final float[] c, final int b) {
        if (!this.e) {
            return;
        }
        if (!this.i) {
            Monitor.a(2, String.format("VideoCapture[%d]: capture first frame", this.hashCode()), "", 0);
            com.tencent.liteav.basic.util.f.a(this.a, 1007, "First frame capture completed");
            this.i = true;
            this.r = true;
            TXCLog.i("CameraCapture", "trtc_render: render first frame");
        }
        final com.tencent.liteav.basic.structs.b b2 = new com.tencent.liteav.basic.structs.b();
        b2.e = this.c.j();
        b2.f = this.c.k();
        b2.g = this.f.a;
        b2.h = this.f.b;
        b2.j = this.c.h();
        b2.i = (this.c.i() ? (!this.f.S) : this.f.S);
        b2.a = a;
        b2.c = c;
        b2.d = this.f.W;
        b2.m = m;
        b2.b = b;
        if (b2.j == 0 || b2.j == 180) {
            b2.g = this.f.b;
            b2.h = this.f.a;
        }
        else {
            b2.g = this.f.a;
            b2.h = this.f.b;
        }
        b2.l = com.tencent.liteav.basic.util.f.a(b2.e, b2.f, this.f.b, this.f.a);
        if (this.d != null) {
            this.d.b(b2);
        }
        if (this.r) {
            this.r = false;
            TXCLog.i("CameraCapture", String.format("vsize onCaptureFrame w*h:%d*%d angle:%d", b2.g, b2.h, b2.j));
        }
        ++this.j;
        final long n = System.currentTimeMillis() - this.k;
        if (n >= 1000L) {
            TXCStatus.a(this.q, 1001, this.m, (this.j - this.l) * 1000.0 / n);
            this.l = this.j;
            this.k += n;
        }
    }
    
    private a.a n() {
        if (this.f.T) {
            return com.tencent.liteav.capturer.a.a.i;
        }
        switch (c$3.a[this.f.k.ordinal()]) {
            case 1: {
                return com.tencent.liteav.capturer.a.a.a;
            }
            case 2: {
                return com.tencent.liteav.capturer.a.a.e;
            }
            case 3: {
                return com.tencent.liteav.capturer.a.a.f;
            }
            case 4: {
                return com.tencent.liteav.capturer.a.a.h;
            }
            case 5: {
                return com.tencent.liteav.capturer.a.a.d;
            }
            default: {
                return com.tencent.liteav.capturer.a.a.g;
            }
        }
    }
    
    @Override
    public void a(final byte[] array) {
        if (this.h != null) {
            this.h.a(array);
        }
    }
    
    @Override
    public void m() {
        if (this.c.l() != null) {
            this.c.g();
        }
        synchronized (this.n) {
            if (this.o == null) {
                (this.o = new HandlerThread("cameraMonitorThread")).start();
                this.p = new Handler(this.o.getLooper());
                TXCLog.w("CameraCapture", "start camera monitor ");
            }
            if (this.p != null) {
                this.p.postDelayed((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (CameraProxy.this.d() && CameraProxy.this.o() && CameraProxy.this.c.l() == null) {
                                TXCLog.w("CameraCapture", "camera monitor restart capture");
                                CameraProxy.this.c.g();
                                CameraProxy.this.h.a(false);
                                CameraProxy.this.c.a(CameraProxy.this.f.h);
                                CameraProxy.this.c.a(CameraProxy.this.f.W, CameraProxy.this.f.a, CameraProxy.this.f.b);
                                CameraProxy.this.c.a(CameraProxy.this.h.getSurfaceTexture());
                                CameraProxy.this.c.d(CameraProxy.this.f.m);
                            }
                            else if (CameraProxy.this.p != null) {
                                CameraProxy.this.p.postDelayed((Runnable)this, 2000L);
                            }
                        }
                        catch (Exception ex) {
                            TXCLog.w("CameraCapture", "camera monitor exception ");
                        }
                    }
                }, 2000L);
            }
        }
    }
    
    @Override
    public void g(final int m) {
        this.m = m;
    }
    
    @Override
    public void e(final boolean u) {
        this.f.U = u;
        this.c.b(u);
        this.r = true;
    }
    
    private boolean o() {
        try {
            if (this.b != null) {
                final List runningAppProcesses = ((ActivityManager)this.b.getSystemService("activity")).getRunningAppProcesses();
                if (runningAppProcesses == null) {
                    TXCLog.w("CameraCapture", "List of RunningAppProcessInfo is null");
                    return false;
                }
                for (int i = 0; i < runningAppProcesses.size(); ++i) {
                    final ActivityManager.RunningAppProcessInfo runningAppProcessInfo = runningAppProcesses.get(i);
                    if (runningAppProcessInfo == null) {
                        TXCLog.w("CameraCapture", "ActivityManager.RunningAppProcessInfo is null");
                    }
                    else if (runningAppProcessInfo.processName.equals(this.b.getPackageName()) && runningAppProcessInfo.importance == 100) {
                        return true;
                    }
                }
            }
        }
        catch (Exception ex) {}
        return false;
    }
}
