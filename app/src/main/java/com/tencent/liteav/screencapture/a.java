package com.tencent.liteav.screencapture;

import android.content.*;
import java.lang.ref.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import android.view.*;
import android.graphics.*;
import android.os.*;
import android.opengl.*;
import javax.microedition.khronos.egl.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.c.*;

public class a
{
    protected final Handler a;
    private final boolean j;
    private final Context k;
    protected volatile HandlerThread b;
    protected volatile b c;
    protected volatile WeakReference<com.tencent.liteav.screencapture.b> d;
    protected volatile int e;
    protected int f;
    protected int g;
    protected int h;
    protected boolean i;
    private Object l;
    private int m;
    private int n;
    private WeakReference<com.tencent.liteav.basic.b.b> o;
    private WeakReference<a> p;
    private c.b q;
    
    public a(final Context context, final boolean j, final a a) {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = 0;
        this.f = 720;
        this.g = 1280;
        this.h = 20;
        this.i = true;
        this.l = null;
        this.m = this.f;
        this.n = this.g;
        this.o = null;
        this.q = new c.b() {
            @Override
            public void a(final boolean b, final boolean b2) {
                if (b) {
                    com.tencent.liteav.screencapture.a.this.b(106);
                }
                else {
                    com.tencent.liteav.screencapture.a.this.p = null;
                    f.a(com.tencent.liteav.screencapture.a.this.o, -1308, "Failed to share screen");
                }
            }
            
            @Override
            public void a() {
                f.a(com.tencent.liteav.screencapture.a.this.o, -7001, "Screen recording stopped. It may be preempted by other apps");
                final a b = com.tencent.liteav.screencapture.a.this.d();
                com.tencent.liteav.screencapture.a.this.p = null;
                if (b != null) {
                    b.onScreenCaptureStopped(1);
                }
            }
            
            @Override
            public void a(final boolean b) {
                if (com.tencent.liteav.screencapture.a.this.j) {
                    com.tencent.liteav.screencapture.a.this.b(b);
                    com.tencent.liteav.screencapture.a.this.b(105, com.tencent.liteav.screencapture.a.this.m, com.tencent.liteav.screencapture.a.this.n);
                }
            }
        };
        this.p = new WeakReference<a>(a);
        this.k = context.getApplicationContext();
        this.a = new Handler(Looper.getMainLooper());
        this.j = j;
    }
    
    public int a(final int n, final int n2, final int h) {
        this.h = h;
        if (Build.VERSION.SDK_INT < 21) {
            this.c(20000004);
            return 20000004;
        }
        this.c(n, n2);
        this.a();
        TXCLog.i("TXCScreenCapture", "start screen capture");
        return 0;
    }
    
    public void a(final Object l) {
        TXCLog.i("TXCScreenCapture", "stop encode: " + l);
        this.l = l;
        this.b();
    }
    
    public void a(final boolean b) {
        synchronized (this) {
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    final a b = com.tencent.liteav.screencapture.a.this.d();
                    if (com.tencent.liteav.screencapture.a.this.i != b && b != null) {
                        if (b) {
                            b.onScreenCaptureResumed();
                        }
                        else {
                            b.onScreenCapturePaused();
                        }
                    }
                    com.tencent.liteav.screencapture.a.this.i = b;
                }
            };
            if (this.c != null) {
                this.c.post((Runnable)runnable);
            }
            else {
                runnable.run();
            }
        }
    }
    
    public void a(final com.tencent.liteav.screencapture.b b) {
        this.d = new WeakReference<com.tencent.liteav.screencapture.b>(b);
    }
    
    public void a(final com.tencent.liteav.basic.b.b b) {
        this.o = new WeakReference<com.tencent.liteav.basic.b.b>(b);
    }
    
    public void a(final int h) {
        this.b(103, this.h = h);
    }
    
    public void a(final int n, final int n2) {
        this.c(n, n2);
        this.b(105, n, n2);
    }
    
    private void c(final int f, final int g) {
        if (this.j) {
            final int rotation = ((WindowManager)this.k.getSystemService("window")).getDefaultDisplay().getRotation();
            if (rotation == 0 || rotation == 2) {
                if (f > g) {
                    this.f = g;
                    this.g = f;
                }
                else {
                    this.f = f;
                    this.g = g;
                }
            }
            else if (f < g) {
                this.f = g;
                this.g = f;
            }
            else {
                this.f = f;
                this.g = g;
            }
        }
        else {
            this.f = f;
            this.g = g;
        }
        this.m = this.f;
        this.n = this.g;
    }
    
    public synchronized void a(final Runnable runnable) {
        if (this.c != null) {
            this.c.post(runnable);
        }
    }
    
    protected void a() {
        this.b();
        synchronized (this) {
            (this.b = new HandlerThread("ScreenCaptureGLThread")).start();
            this.c = new b(this.b.getLooper(), this);
            ++this.e;
            this.c.a = this.e;
            this.c.e = this.m;
            this.c.f = this.n;
            this.c.g = ((this.h < 1) ? 1 : this.h);
        }
        this.b(100);
    }
    
    protected void b() {
        synchronized (this) {
            ++this.e;
            if (this.c != null) {
                this.a(101, new Runnable() {
                    final /* synthetic */ Handler a = com.tencent.liteav.screencapture.a.this.c;
                    final /* synthetic */ HandlerThread b = com.tencent.liteav.screencapture.a.this.b;
                    
                    @Override
                    public void run() {
                        com.tencent.liteav.screencapture.a.this.a.post((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                if (Runnable.this.a != null) {
                                    Runnable.this.a.removeCallbacksAndMessages((Object)null);
                                }
                                if (Runnable.this.b != null) {
                                    if (Build.VERSION.SDK_INT >= 18) {
                                        Runnable.this.b.quitSafely();
                                    }
                                    else {
                                        Runnable.this.b.quit();
                                    }
                                }
                            }
                        });
                    }
                });
            }
            this.c = null;
            this.b = null;
        }
    }
    
    protected com.tencent.liteav.screencapture.b c() {
        return (this.d == null) ? null : this.d.get();
    }
    
    protected void a(final int n, final long n2) {
        synchronized (this) {
            if (this.c != null) {
                this.c.sendEmptyMessageDelayed(n, n2);
            }
        }
    }
    
    protected void b(final int n) {
        synchronized (this) {
            if (this.c != null) {
                this.c.sendEmptyMessage(n);
            }
        }
    }
    
    protected void b(final int what, final int arg1) {
        synchronized (this) {
            if (this.c != null) {
                final Message message = new Message();
                message.what = what;
                message.arg1 = arg1;
                this.c.sendMessage(message);
            }
        }
    }
    
    protected void b(final int what, final int arg1, final int arg2) {
        synchronized (this) {
            if (this.c != null) {
                final Message message = new Message();
                message.what = what;
                message.arg1 = arg1;
                message.arg2 = arg2;
                this.c.sendMessage(message);
            }
        }
    }
    
    protected void a(final int what, final Runnable obj) {
        synchronized (this) {
            if (this.c != null) {
                final Message message = new Message();
                message.what = what;
                message.obj = obj;
                this.c.sendMessage(message);
            }
        }
    }
    
    protected void c(final int n) {
        final a d = this.d();
        if (d != null && n == 0) {
            d.onScreenCaptureStarted();
        }
    }
    
    protected void a(final int n, final EGLContext eglContext, final int n2, final int n3, final int n4, final long n5) {
        final com.tencent.liteav.screencapture.b c = this.c();
        if (c != null) {
            c.a(n, eglContext, n2, n3, n4, n5);
        }
    }
    
    protected void b(final boolean b) {
        if (b) {
            this.m = ((this.f < this.g) ? this.f : this.g);
            this.n = ((this.f < this.g) ? this.g : this.f);
        }
        else {
            this.m = ((this.f < this.g) ? this.g : this.f);
            this.n = ((this.f < this.g) ? this.f : this.g);
        }
        TXCLog.i("TXCScreenCapture", String.format(Locale.ENGLISH, "reset screen capture isPortrait[%b] output size[%d/%d]", b, this.m, this.n));
    }
    
    private a d() {
        final WeakReference<a> p = this.p;
        return (p != null) ? ((a)p.get()) : null;
    }
    
    protected class b extends Handler
    {
        public int a;
        public int[] b;
        public Surface c;
        public SurfaceTexture d;
        public int e;
        public int f;
        public int g;
        protected boolean h;
        protected long i;
        protected long j;
        protected com.tencent.liteav.basic.c.b k;
        protected i l;
        float[] m;
        private boolean o;
        
        public b(final Looper looper, final a a) {
            super(looper);
            this.a = 0;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = 720;
            this.f = 1280;
            this.g = 25;
            this.h = false;
            this.i = 0L;
            this.j = 0L;
            this.k = null;
            this.l = null;
            this.m = new float[16];
            this.o = true;
            TXCLog.i("TXCScreenCapture", "TXCScreenCaptureGLThreadHandler inited. hashCode: %d", this.hashCode());
        }
        
        public void handleMessage(final Message message) {
            if (message == null) {
                return;
            }
            if (this.a != com.tencent.liteav.screencapture.a.this.e && 101 != message.what) {
                return;
            }
            switch (message.what) {
                case 100: {
                    this.a(message);
                    break;
                }
                case 102: {
                    try {
                        this.c(message);
                    }
                    catch (Exception ex) {
                        TXCLog.e("TXCScreenCapture", "render failed.", ex);
                    }
                    break;
                }
                case 101: {
                    this.b(message);
                    break;
                }
                case 103: {
                    this.d(message);
                    break;
                }
                case 105: {
                    this.e(message);
                    break;
                }
                case 106: {
                    this.a();
                    break;
                }
            }
            if (message != null && message.obj != null) {
                ((Runnable)message.obj).run();
            }
        }
        
        protected void a(final Message message) {
            this.i = 0L;
            this.j = 0L;
            if (!this.b()) {
                this.c();
                com.tencent.liteav.screencapture.a.this.b();
                com.tencent.liteav.screencapture.a.this.c(20000003);
            }
        }
        
        protected void a() {
            if (this.o && this.k != null) {
                final Bundle bundle = new Bundle();
                bundle.putString("EVT_MSG", "Screen recording started successfully");
                com.tencent.liteav.basic.util.f.a(com.tencent.liteav.screencapture.a.this.o, 1004, bundle);
                com.tencent.liteav.screencapture.a.this.c(0);
            }
            this.o = false;
        }
        
        protected void b(final Message message) {
            com.tencent.liteav.screencapture.a.this.i = false;
            final a b = com.tencent.liteav.screencapture.a.this.d();
            if (b != null) {
                b.onScreenCaptureStopped(0);
            }
            final com.tencent.liteav.screencapture.b c = com.tencent.liteav.screencapture.a.this.c();
            if (c != null) {
                c.a(com.tencent.liteav.screencapture.a.this.l);
            }
            this.c();
        }
        
        protected void c(final Message message) {
            com.tencent.liteav.screencapture.a.this.a(102, 5L);
            if (!com.tencent.liteav.screencapture.a.this.i) {
                return;
            }
            if (this.k == null) {
                TXCLog.e("TXCScreenCapture", "eglhelper is null");
                return;
            }
            if (!this.h) {
                this.i = 0L;
                this.j = System.nanoTime();
                return;
            }
            final long nanoTime = System.nanoTime();
            if (nanoTime < this.j + this.i * 1000L * 1000L * 1000L / this.g) {
                return;
            }
            if (this.j == 0L) {
                this.j = nanoTime;
            }
            else if (nanoTime > this.j + 1000000000L) {
                this.i = 0L;
                this.j = nanoTime;
            }
            ++this.i;
            if (this.d == null || this.b == null) {
                return;
            }
            this.d.getTransformMatrix(this.m);
            try {
                this.d.updateTexImage();
            }
            catch (Exception ex) {
                TXCLog.e("TXCScreenCapture", "onMsgRend Exception " + ex.getMessage());
            }
            this.l.a(this.m);
            GLES20.glViewport(0, 0, this.e, this.f);
            com.tencent.liteav.screencapture.a.this.a(0, this.k.d(), this.l.a(this.b[0]), this.e, this.f, TXCTimeUtil.getTimeTick());
        }
        
        protected void d(final Message message) {
            if (message == null) {
                return;
            }
            this.g = ((message.arg1 < 1) ? 1 : message.arg1);
            this.i = 0L;
            this.j = 0L;
        }
        
        protected void e(final Message message) {
            if (message == null) {
                return;
            }
            this.e = message.arg1;
            this.f = message.arg2;
            this.d();
            this.l.a(this.e, this.f);
            this.e();
            TXCLog.i("TXCScreenCapture", String.format("set screen capture size[%d/%d]", com.tencent.liteav.screencapture.a.this.m, com.tencent.liteav.screencapture.a.this.n));
        }
        
        protected boolean b() {
            TXCLog.i("TXCScreenCapture", String.format("init egl size[%d/%d]", this.e, this.f));
            this.k = com.tencent.liteav.basic.c.b.a(null, null, null, this.e, this.f);
            if (this.k == null) {
                return false;
            }
            this.l = new i();
            if (!this.l.c()) {
                return false;
            }
            this.l.a(true);
            this.l.a(this.e, this.f);
            this.l.a(com.tencent.liteav.basic.c.l.e, com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, false));
            this.e();
            return true;
        }
        
        protected void c() {
            this.d();
            if (this.l != null) {
                this.l.e();
                this.l = null;
            }
            if (this.k != null) {
                this.k.c();
                this.k = null;
            }
        }
        
        protected void d() {
            new e(Looper.getMainLooper()).a(new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.screencapture.c.a(com.tencent.liteav.screencapture.a.this.k).a(com.tencent.liteav.screencapture.a.b.this.c);
                }
            });
            if (this.c != null) {
                this.c.release();
                this.c = null;
            }
            if (this.d != null) {
                this.d.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
                this.d.release();
                this.h = false;
                this.d = null;
            }
            if (this.b != null) {
                GLES20.glDeleteTextures(1, this.b, 0);
                this.b = null;
            }
        }
        
        protected void e() {
            (this.b = new int[1])[0] = com.tencent.liteav.basic.c.j.b();
            if (this.b[0] <= 0) {
                this.b = null;
                return;
            }
            this.d = new SurfaceTexture(this.b[0]);
            this.c = new Surface(this.d);
            this.d.setDefaultBufferSize(this.e, this.f);
            this.d.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)new SurfaceTexture.OnFrameAvailableListener() {
                public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
                    com.tencent.liteav.screencapture.a.this.a(104, new Runnable() {
                        @Override
                        public void run() {
                            com.tencent.liteav.screencapture.a.b.this.h = true;
                            com.tencent.liteav.screencapture.a.this.b(102);
                        }
                    });
                    surfaceTexture.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
                }
            });
            new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.screencapture.c.a(com.tencent.liteav.screencapture.a.this.k).a(com.tencent.liteav.screencapture.a.b.this.c, com.tencent.liteav.screencapture.a.b.this.e, com.tencent.liteav.screencapture.a.b.this.f, com.tencent.liteav.screencapture.a.this.q);
                }
            });
        }
    }
    
    public interface a
    {
        void onScreenCaptureStarted();
        
        void onScreenCaptureResumed();
        
        void onScreenCapturePaused();
        
        void onScreenCaptureStopped(final int p0);
    }
}
