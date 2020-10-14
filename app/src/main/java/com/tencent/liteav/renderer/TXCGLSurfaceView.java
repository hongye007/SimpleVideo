package com.tencent.liteav.renderer;

import java.lang.ref.*;
import com.tencent.liteav.basic.b.*;
import android.content.*;
import java.util.*;
import android.util.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;
import javax.microedition.khronos.opengles.*;
import javax.microedition.khronos.egl.*;
import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.util.*;
import java.nio.*;
import android.graphics.*;
import android.os.*;

public class TXCGLSurfaceView extends TXCGLSurfaceViewBase implements SurfaceTexture.OnFrameAvailableListener, GLSurfaceView.Renderer, m
{
    private SurfaceTexture g;
    private EGLContext h;
    private com.tencent.liteav.basic.c.h i;
    private int[] j;
    private float[] k;
    private int l;
    private boolean m;
    private float n;
    private float o;
    private int p;
    private long q;
    private long r;
    private int s;
    private boolean t;
    private boolean u;
    private Object v;
    private Handler w;
    private int x;
    private int y;
    private boolean z;
    private o A;
    private int B;
    private int C;
    private boolean D;
    private boolean E;
    private n F;
    private long G;
    WeakReference<com.tencent.liteav.basic.b.b> a;
    private byte[] H;
    private long I;
    private int J;
    private int K;
    private final Queue<Runnable> L;
    
    public TXCGLSurfaceView(final Context context) {
        super(context);
        this.k = new float[16];
        this.l = 0;
        this.m = false;
        this.n = 1.0f;
        this.o = 1.0f;
        this.p = 20;
        this.q = 0L;
        this.r = 0L;
        this.s = 12288;
        this.t = true;
        this.u = false;
        this.v = new Object();
        this.x = 0;
        this.y = 0;
        this.z = true;
        this.A = null;
        this.B = 0;
        this.C = 0;
        this.D = true;
        this.E = true;
        this.H = null;
        this.I = 0L;
        this.J = 0;
        this.K = 0;
        this.L = new LinkedList<Runnable>();
        this.setEGLContextClientVersion(2);
        this.a(8, 8, 8, 8, 16, 0);
        this.setRenderer((GLSurfaceView.Renderer)this);
    }
    
    public TXCGLSurfaceView(final Context context, final AttributeSet set) {
        super(context, set);
        this.k = new float[16];
        this.l = 0;
        this.m = false;
        this.n = 1.0f;
        this.o = 1.0f;
        this.p = 20;
        this.q = 0L;
        this.r = 0L;
        this.s = 12288;
        this.t = true;
        this.u = false;
        this.v = new Object();
        this.x = 0;
        this.y = 0;
        this.z = true;
        this.A = null;
        this.B = 0;
        this.C = 0;
        this.D = true;
        this.E = true;
        this.H = null;
        this.I = 0L;
        this.J = 0;
        this.K = 0;
        this.L = new LinkedList<Runnable>();
        this.setEGLContextClientVersion(2);
        this.a(8, 8, 8, 8, 16, 0);
        this.setRenderer((GLSurfaceView.Renderer)this);
    }
    
    public void setFPS(final int n) {
        this.b(new Runnable() {
            @Override
            public void run() {
                TXCGLSurfaceView.this.p = n;
                if (TXCGLSurfaceView.this.p <= 0) {
                    TXCGLSurfaceView.this.p = 1;
                }
                else if (TXCGLSurfaceView.this.p > 60) {
                    TXCGLSurfaceView.this.p = 60;
                }
                TXCGLSurfaceView.this.r = 0L;
                TXCGLSurfaceView.this.q = 0L;
            }
        });
    }
    
    public void setRendMode(final int n) {
        this.b(new Runnable() {
            @Override
            public void run() {
                TXCGLSurfaceView.this.B = n;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(16640);
            }
        });
    }
    
    public void setRendMirror(final int n) {
        this.b(new Runnable() {
            @Override
            public void run() {
                TXCGLSurfaceView.this.C = n;
                GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                GLES20.glClear(16640);
            }
        });
    }
    
    @Override
    protected void b() {
        TXCLog.i("TXCGLSurfaceView", "surfaceDestroyed-->enter with mSurfaceTextureListener:" + this.F);
        if (this.F != null) {
            this.F.onSurfaceTextureDestroy(this.g);
        }
        if (this.g != null) {
            this.g.release();
            this.g = null;
        }
    }
    
    @Override
    protected void setRunInBackground(final boolean b) {
        if (b) {
            synchronized (this) {
                TXCLog.i("TXCGLSurfaceView", "background capture enter background");
                this.c = true;
            }
        }
        else {
            this.b(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        TXCLog.i("TXCGLSurfaceView", "background capture exit background");
                        TXCGLSurfaceView.this.c = false;
                    }
                }
            });
        }
    }
    
    public void setNotifyListener(final com.tencent.liteav.basic.b.b b) {
        this.a = new WeakReference<com.tencent.liteav.basic.b.b>(b);
    }
    
    public void a(final o a) {
        this.A = a;
        this.z = true;
    }
    
    public void a(final int n, final boolean b, final int l, final int n2, final int n3, final boolean d) {
        if (this.i == null) {
            return;
        }
        synchronized (this) {
            if (this.c) {
                return;
            }
        }
        GLES20.glClear(16640);
        int n4 = 0;
        int n5 = 0;
        int width = this.getWidth();
        int height = this.getHeight();
        if (this.B == 0) {
            n4 = 0;
            n5 = 0;
        }
        else if (this.B == 1) {
            final int n6 = (720 - l) % 360;
            final boolean b2 = n6 == 90 || n6 == 270;
            final int[] a = this.a(width, height, b2 ? n3 : n2, b2 ? n2 : n3);
            width = a[0];
            height = a[1];
            n4 = a[2];
            n5 = a[3];
        }
        GLES20.glViewport(n4, n5, this.x = width, this.y = height);
        boolean m = b;
        if (this.C == 1) {
            if (!d) {
                m = !b;
            }
        }
        else if (this.C == 2 && d) {
            m = !b;
        }
        final float n7 = (height != 0) ? (width / (float)height) : 1.0f;
        final float o = (n3 != 0) ? (n2 / (float)n3) : 1.0f;
        if (this.m != m || this.l != l || this.n != n7 || this.o != o || this.D != d) {
            this.m = m;
            this.l = l;
            this.n = n7;
            this.o = o;
            this.D = d;
            final int n8 = (720 - this.l) % 360;
            final boolean b3 = n8 == 90 || n8 == 270;
            this.i.a(n2, n3, n8, l.a(com.tencent.liteav.basic.c.k.a, false, true), (b3 ? height : width) / (float)(b3 ? width : height), !b3 && this.m, b3 && this.m);
            if (b3) {
                this.i.g();
            }
            else {
                this.i.h();
            }
        }
        GLES20.glBindFramebuffer(36160, 0);
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16640);
        this.i.b(n);
        this.a(n4, n5);
    }
    
    public void a(final byte[] h) {
        synchronized (this) {
            this.H = h;
            this.t = false;
            this.u = true;
        }
    }
    
    private int[] a(final int n, final int n2, final int n3, final int n4) {
        final int[] array = new int[4];
        int n5 = 0;
        int n6 = 0;
        final float n7 = n2 / (float)n;
        final float n8 = n4 / (float)n3;
        int n9;
        int n10;
        if (n7 > n8) {
            n9 = n;
            n10 = (int)(n * n8);
            n6 = (n2 - n10) / 2;
        }
        else {
            n10 = n2;
            n9 = (int)(n2 / n8);
            n5 = (n - n9) / 2;
        }
        array[0] = n9;
        array[1] = n10;
        array[2] = n5;
        array[3] = n6;
        return array;
    }
    
    public SurfaceTexture getSurfaceTexture() {
        return this.g;
    }
    
    public void a(final int p2, final boolean e) {
        this.p = p2;
        if (this.p <= 0) {
            this.p = 1;
        }
        else if (this.p > 60) {
            this.p = 60;
        }
        this.A = null;
        this.z = false;
        this.J = 0;
        this.I = 0L;
        this.K = 0;
        this.c(true);
        this.E = e;
        this.G = 0L;
        this.g();
    }
    
    public void a() {
        this.c(false);
    }
    
    public void setSurfaceTextureListener(final n f) {
        this.F = f;
    }
    
    public EGLContext getGLContext() {
        return this.h;
    }
    
    public void a(final Runnable runnable) {
        synchronized (this.L) {
            this.L.add(runnable);
        }
    }
    
    @Override
    protected int c() {
        if (this.s != 12288) {
            TXCLog.e("TXCGLSurfaceView", "background capture swapbuffer error : " + this.s);
        }
        return this.s;
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (Build.VERSION.SDK_INT >= 21 && this.w != null) {
            this.w.getLooper().quitSafely();
            this.w = null;
        }
    }
    
    public void b(final Runnable runnable) {
        synchronized (this.L) {
            this.L.add(runnable);
        }
    }
    
    private boolean a(final Queue<Runnable> queue) {
        final Runnable runnable;
        synchronized (queue) {
            if (queue.isEmpty()) {
                return false;
            }
            runnable = queue.poll();
        }
        if (runnable == null) {
            return false;
        }
        runnable.run();
        return true;
    }
    
    public void onSurfaceCreated(final GL10 gl10, final EGLConfig eglConfig) {
        this.h = ((EGL10)EGLContext.getEGL()).eglGetCurrentContext();
        (this.j = new int[1])[0] = com.tencent.liteav.basic.c.j.b();
        if (this.j[0] <= 0) {
            this.j = null;
            TXCLog.e("TXCGLSurfaceView", "create oes texture error!! at glsurfaceview");
            return;
        }
        this.g = new SurfaceTexture(this.j[0]);
        this.g();
        this.i = new com.tencent.liteav.basic.c.h();
        if (!this.i.c()) {
            return;
        }
        this.i.a(com.tencent.liteav.basic.c.l.e, com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, false));
        if (this.F != null) {
            this.F.onSurfaceTextureAvailable(this.g);
        }
    }
    
    public void onSurfaceChanged(final GL10 gl10, final int n, final int n2) {
    }
    
    public void onDrawFrame(final GL10 gl10) {
        this.a(this.L);
        boolean b = false;
        boolean b2 = true;
        long currentTimeMillis;
        while (true) {
            currentTimeMillis = System.currentTimeMillis();
            if (this.r == 0L || currentTimeMillis < this.r) {
                this.r = currentTimeMillis;
            }
            if (currentTimeMillis - this.r >= this.q * 1000L / this.p) {
                break;
            }
            this.a(15L);
            b2 = false;
        }
        if (currentTimeMillis - this.r > 1000L) {
            this.q = 1L;
            b = true;
            this.r = System.currentTimeMillis();
        }
        if (this.t) {
            return;
        }
        try {
            byte[] h = null;
            boolean b3 = false;
            synchronized (this) {
                if (!this.u) {
                    b3 = true;
                }
                else {
                    b2 = false;
                    if (this.H != null) {
                        h = this.H;
                        this.H = null;
                        if (this.g != null) {
                            this.g.updateTexImage();
                            this.g.getTransformMatrix(this.k);
                        }
                    }
                    else if (this.g != null) {
                        this.g.updateTexImage();
                        this.g.getTransformMatrix(this.k);
                    }
                    if (!b) {
                        ++this.q;
                    }
                    else {
                        this.q = 1L;
                    }
                    this.u = false;
                }
            }
            if (b3) {
                if (b2) {
                    this.a(5L);
                }
                return;
            }
            final long currentTimeMillis2 = System.currentTimeMillis();
            if (currentTimeMillis2 > this.I + 1000.0) {
                this.J = (int)(this.K * 1000.0 / (currentTimeMillis2 - this.I)) + 1;
                this.I = currentTimeMillis2;
                this.K = 0;
            }
            ++this.K;
            if (this.F != null) {
                if (h != null) {
                    this.F.onBufferProcess(h, this.k);
                }
                else {
                    this.F.onTextureProcess(this.j[0], this.k);
                }
            }
            int n = 1;
            synchronized (this) {
                n = (this.c ? 0 : 1);
            }
            if (n != 0) {
                final int e = this.e();
                if (e != 12288 && System.currentTimeMillis() - this.G > 2000L) {
                    TXCLog.w("TXCGLSurfaceView", "background capture swapBuffer error : " + e);
                    this.G = System.currentTimeMillis();
                    final Bundle bundle = new Bundle();
                    bundle.putInt("EVT_PARAM1", e);
                    bundle.putInt("EVT_ID", 2110);
                    bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                    bundle.putCharSequence("EVT_MSG", (CharSequence)"Failed to render video");
                    com.tencent.liteav.basic.util.f.a(this.a, 2110, bundle);
                }
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCGLSurfaceView", "onDrawFrame failed", ex);
        }
    }
    
    private void a(final int n, final int n2) {
        if (this.z) {
            if (this.x != 0 && this.y != 0) {
                final boolean b = this.x <= this.y;
                final int n3 = (this.y >= this.x) ? this.y : this.x;
                final int n4 = (this.y >= this.x) ? this.x : this.y;
                int n5 = n3;
                int n6 = n4;
                if (b) {
                    n5 = n4;
                    n6 = n3;
                }
                final ByteBuffer allocate = ByteBuffer.allocate(n5 * n6 * 4);
                final Bitmap bitmap = Bitmap.createBitmap(n5, n6, Bitmap.Config.ARGB_8888);
                allocate.position(0);
                GLES20.glReadPixels(n, n2, n5, n6, 6408, 5121, (Buffer)allocate);
                final int n7 = n5;
                final int n8 = n6;
                final o a = this.A;
                if (a != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap;
                            try {
                                allocate.position(0);
                                bitmap.copyPixelsFromBuffer((Buffer)allocate);
                                final Matrix matrix = new Matrix();
                                matrix.setScale(1.0f, -1.0f);
                                bitmap = Bitmap.createBitmap(bitmap, 0, 0, n7, n8, matrix, false);
                            }
                            catch (Exception ex) {
                                TXCLog.w("TXCGLSurfaceView", "takePhoto error " + ex);
                                bitmap = null;
                            }
                            catch (Error error) {
                                TXCLog.w("TXCGLSurfaceView", "takePhoto error " + error);
                                bitmap = null;
                            }
                            a.onTakePhotoComplete(bitmap);
                            bitmap.recycle();
                        }
                    }).start();
                }
            }
            this.A = null;
            this.z = false;
        }
    }
    
    public void onFrameAvailable(final SurfaceTexture surfaceTexture) {
        this.t = false;
        synchronized (this) {
            this.u = true;
        }
    }
    
    public void a(final boolean b) {
        this.t = true;
        if (b) {
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glClear(16384);
            this.s = this.e();
        }
        synchronized (this) {
            if (this.u) {
                this.u = false;
                if (this.g != null) {
                    this.g.updateTexImage();
                }
            }
        }
    }
    
    public void b(final boolean b) {
        synchronized (this.v) {
            this.b(new Runnable() {
                @Override
                public void run() {
                    synchronized (TXCGLSurfaceView.this.v) {
                        TXCGLSurfaceView.this.a(b);
                        TXCGLSurfaceView.this.v.notifyAll();
                    }
                }
            });
            try {
                this.v.wait(1000L);
            }
            catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void a(final long n) {
        try {
            Thread.sleep(n);
        }
        catch (Exception ex) {}
    }
    
    private void g() {
        if (this.E) {
            if (this.g != null) {
                if (Build.VERSION.SDK_INT >= 21) {
                    if (this.w == null) {
                        final HandlerThread handlerThread = new HandlerThread("VideoCaptureThread");
                        handlerThread.start();
                        this.w = new Handler(handlerThread.getLooper());
                    }
                    this.g.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)this, this.w);
                }
                else {
                    this.g.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)this);
                }
            }
        }
        else if (this.g != null) {
            this.g.setOnFrameAvailableListener((SurfaceTexture.OnFrameAvailableListener)null);
        }
    }
}
