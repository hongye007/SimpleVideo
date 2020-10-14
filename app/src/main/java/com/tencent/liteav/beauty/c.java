package com.tencent.liteav.beauty;

import android.content.*;
import android.graphics.*;
import com.tencent.liteav.beauty.b.*;
import java.lang.ref.*;
import com.tencent.liteav.basic.c.*;
import java.util.*;
import com.tencent.liteav.basic.log.*;
import android.util.*;
import com.tencent.liteav.basic.util.*;
import android.opengl.*;
import java.nio.*;
import android.os.*;

class c extends HandlerThread
{
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;
    private int A;
    private int B;
    private int C;
    private int D;
    private Context E;
    private boolean F;
    private boolean G;
    private e.e H;
    private e.f I;
    private int J;
    private int K;
    private int L;
    private int M;
    private int N;
    private int O;
    private float P;
    private int Q;
    private int R;
    private int S;
    private boolean T;
    private float[] U;
    private boolean V;
    private int W;
    private int X;
    private com.tencent.liteav.basic.c.a Y;
    private Bitmap Z;
    private p aa;
    private w ab;
    private b ac;
    private com.tencent.liteav.beauty.b.a.a ad;
    private com.tencent.liteav.beauty.b.b.a ae;
    private com.tencent.liteav.beauty.b.c af;
    private com.tencent.liteav.beauty.b.c.a ag;
    private Bitmap ah;
    private Bitmap ai;
    private float aj;
    private float ak;
    private float al;
    private s am;
    private u an;
    private aj ao;
    private m ap;
    private l aq;
    private h ar;
    private r as;
    private i at;
    private h au;
    private final Queue<Runnable> av;
    private boolean aw;
    boolean a;
    private Object ax;
    private Object ay;
    private Handler az;
    private a aA;
    private float aB;
    private int aC;
    private int aD;
    private int aE;
    private int aF;
    private int aG;
    private boolean aH;
    private com.tencent.liteav.beauty.a.a.c aI;
    private com.tencent.liteav.beauty.a.a.a aJ;
    private Bitmap aK;
    private List<e.f> aL;
    private long aM;
    private int aN;
    private final int aO = 100;
    private final float aP = 1000.0f;
    private byte[] aQ;
    private int[] aR;
    private boolean aS;
    private byte[] aT;
    protected int[] b;
    protected int[] c;
    private int aU;
    private int aV;
    private int aW;
    private int aX;
    private f aY;
    private WeakReference<com.tencent.liteav.basic.b.b> aZ;
    com.tencent.liteav.beauty.b.a d;
    com.tencent.liteav.beauty.b.a e;
    com.tencent.liteav.beauty.b.a f;
    private j.b ba;
    
    c(final Context e, final boolean aw) {
        super("TXCFilterDrawer");
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = 0;
        this.m = 0;
        this.n = 0;
        this.o = 0;
        this.p = 0;
        this.q = 0;
        this.r = 0;
        this.s = 0;
        this.t = 0;
        this.u = 0;
        this.v = 0;
        this.w = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.A = 0;
        this.B = 0;
        this.C = 0;
        this.D = 0;
        this.E = null;
        this.F = true;
        this.G = false;
        this.H = new e.e();
        this.I = null;
        this.J = -1;
        this.K = -1;
        this.L = -1;
        this.M = -1;
        this.N = -1;
        this.O = -1;
        this.P = 1.0f;
        this.Q = -1;
        this.R = -1;
        this.S = 1;
        this.T = false;
        this.U = null;
        this.V = false;
        this.W = 0;
        this.X = 0;
        this.Y = null;
        this.Z = null;
        this.aa = null;
        this.ab = null;
        this.ac = null;
        this.ad = null;
        this.ae = null;
        this.af = null;
        this.ag = null;
        this.an = null;
        this.ao = null;
        this.ap = null;
        this.aq = null;
        this.ar = null;
        this.as = null;
        this.at = null;
        this.au = null;
        this.av = new LinkedList<Runnable>();
        this.a = false;
        this.ax = new Object();
        this.ay = new Object();
        this.aB = 0.5f;
        this.aC = 0;
        this.aD = 0;
        this.aE = 0;
        this.aF = 0;
        this.aG = 0;
        this.aH = false;
        this.aI = null;
        this.aJ = null;
        this.aK = null;
        this.aL = null;
        this.aM = 0L;
        this.aN = 0;
        this.aQ = null;
        this.aR = null;
        this.aS = false;
        this.aT = null;
        this.b = null;
        this.c = null;
        this.aU = -1;
        this.aV = 0;
        this.aW = 1;
        this.aX = this.aU;
        this.aY = null;
        this.aZ = new WeakReference<com.tencent.liteav.basic.b.b>(null);
        this.d = new b.a();
        this.e = new b.a();
        this.f = new b.a();
        this.ba = new j.b() {};
        this.E = e;
        this.az = new Handler(this.E.getMainLooper());
        this.aw = aw;
    }
    
    public synchronized boolean a(final e.b b) {
        boolean c = true;
        if (!b.j) {
            if (null == this.aA) {
                this.start();
                this.aA = new a(this.getLooper(), this.E);
            }
            this.aA.obtainMessage(0, (Object)b).sendToTarget();
            this.aA.b();
        }
        else {
            c = this.c(b);
        }
        return c;
    }
    
    public int a(final int n, final int n2, final long n3) {
        this.a(this.av);
        int n4 = n;
        boolean b = this.P != 1.0f;
        GLES20.glViewport(0, 0, this.N, this.O);
        if (null != this.as) {
            if (4 == n2 || this.V) {
                this.as.a(this.U);
                this.as.c(this.V);
            }
            n4 = this.as.a(n4);
        }
        if (this.ac != null) {
            int af;
            if (Math.min(this.Q, this.R) < 540) {
                af = 0;
            }
            else {
                af = this.aF;
            }
            this.ac.f(af);
            if (this.aD > 0 || this.aE > 0 || this.aG > 0 || af > 0) {
                n4 = this.ac.a(n4);
            }
        }
        if (this.am != null) {
            n4 = this.am.a(n4);
        }
        GLES20.glViewport(0, 0, this.L, this.M);
        if (null != this.ap) {
            n4 = this.ap.a(n4);
            b = false;
        }
        if (null != this.aq) {
            n4 = this.aq.a(n4);
            b = false;
        }
        if (b) {
            this.b(this.L, this.M);
            if (null != this.au) {
                GLES20.glViewport(0, 0, this.L, this.M);
                n4 = this.au.a(n4);
            }
        }
        if (null != this.aY) {
            final int willAddWatermark = this.aY.willAddWatermark(n4, this.L, this.M);
            if (willAddWatermark > 0) {
                n4 = willAddWatermark;
            }
        }
        GLES20.glViewport(0, 0, this.L, this.M);
        if (null != this.ao) {
            n4 = this.ao.a(n4);
        }
        if (this.ar != null) {
            GLES20.glViewport(0, 0, this.Q, this.R);
            n4 = this.ar.a(n4);
        }
        this.a(n4, n3);
        return n4;
    }
    
    public int a(final byte[] array, final int n) {
        int a = -1;
        this.a(array);
        if (!this.aw) {
            final byte[] array2 = array.clone();
            this.aA.obtainMessage(2, (Object)array2).sendToTarget();
            if (!this.aS) {
                TXCLog.i("TXCFilterDrawer", "First Frame, clear queue");
                NativeLoad.getInstance();
                NativeLoad.nativeClearQueue();
            }
            this.aA.obtainMessage(3, n, 0).sendToTarget();
            this.a(array2, this.aS);
            this.aS = true;
        }
        else {
            this.b(array);
            a = this.A(n);
        }
        return a;
    }
    
    public void a(final float ab) {
        this.aB = ab;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.beauty.c.this.am != null) {
                    com.tencent.liteav.beauty.c.this.am.a(ab);
                }
            }
        });
    }
    
    public void a(final float[] array) {
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.beauty.c.this.U = array;
            }
        });
    }
    
    public void a(final boolean b) {
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.beauty.c.this.V = b;
            }
        });
    }
    
    public void a(final int n) {
    }
    
    private void a(final com.tencent.liteav.basic.c.a a, final int n, final int n2, final int n3, final int n4, final boolean b, int n5, final int n6) {
        if (null == this.as) {
            TXCLog.i("TXCFilterDrawer", "Create CropFilter");
            if (4 == n6) {
                this.as = new r("attribute vec4 position;\nattribute vec4 inputTextureCoordinate;\n \nuniform mat4 textureTransform;\nvarying vec2 textureCoordinate;\n \nvoid main()\n{\n    gl_Position = position;\n    textureCoordinate = (textureTransform * inputTextureCoordinate).xy;\n}", "#extension GL_OES_EGL_image_external : require\n\nvarying lowp vec2 textureCoordinate;\n \nuniform samplerExternalOES inputImageTexture;\n \nvoid main()\n{\n     gl_FragColor = texture2D(inputImageTexture, textureCoordinate);\n}", true);
            }
            else {
                this.as = new r();
            }
            if (this.as.c()) {
                this.as.a(true);
            }
            else {
                TXCLog.e("TXCFilterDrawer", "mInputCropFilter init failed!");
            }
        }
        this.as.a(n3, n4);
        final float[] a2 = this.as.a(this.J, this.K, null, a, n6);
        n5 = (720 - n5) % 360;
        this.as.a(n, n2, n5, a2, ((n5 == 90 || n5 == 270) ? n4 : n3) / (float)((n5 == 90 || n5 == 270) ? n3 : n4), b, false);
    }
    
    private void a(final int n, final int n2, final int n3, final int n4, final int n5) {
        synchronized (this.ay) {
            final int n6 = (n5 + 360) % 360;
            TXCLog.i("TXCFilterDrawer", "real outputAngle " + n6);
            if (null == this.ar) {
                if (n == n3 && n2 == n4 && n6 == 0) {
                    TXCLog.i("TXCFilterDrawer", "Don't need change output Image, don't create out filter!");
                    return;
                }
                this.ar = new h();
                if (this.ar.c()) {
                    this.ar.a(true);
                }
                else {
                    TXCLog.e("TXCFilterDrawer", "mOutputZoomFilter init failed!");
                }
            }
            this.ar.a(n3, n4);
            this.ar.a((720 - n6) % 360, (FloatBuffer)null);
        }
    }
    
    public void a(final Bitmap a, final float b, final float c, final float d) {
        if (null == this.I) {
            this.I = new e.f();
        }
        if (TXCCommonUtil.equals(this.I.a, a) && b == this.I.b && c == this.I.c && d == this.I.d && this.ao != null) {
            Log.d("TXCFilterDrawer", "Same Water Mark; don't set again");
            return;
        }
        this.I.a = a;
        this.I.b = b;
        this.I.c = c;
        this.I.d = d;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (null != a) {
                    com.tencent.liteav.beauty.a.a().g();
                }
                if (null == a) {
                    if (null != com.tencent.liteav.beauty.c.this.ao) {
                        com.tencent.liteav.beauty.c.this.ao.e();
                        com.tencent.liteav.beauty.c.this.ao = null;
                    }
                    return;
                }
                if (null == com.tencent.liteav.beauty.c.this.ao) {
                    if (com.tencent.liteav.beauty.c.this.L <= 0 || com.tencent.liteav.beauty.c.this.M <= 0) {
                        TXCLog.e("TXCFilterDrawer", "output Width and Height is error!");
                        return;
                    }
                    com.tencent.liteav.beauty.c.this.ao = new aj();
                    com.tencent.liteav.beauty.c.this.ao.a(true);
                    if (!com.tencent.liteav.beauty.c.this.ao.c()) {
                        TXCLog.e("TXCFilterDrawer", "mWatermarkFilter.init failed!");
                        com.tencent.liteav.beauty.c.this.ao.e();
                        com.tencent.liteav.beauty.c.this.ao = null;
                        return;
                    }
                    com.tencent.liteav.beauty.c.this.ao.a(com.tencent.liteav.beauty.c.this.L, com.tencent.liteav.beauty.c.this.M);
                }
                com.tencent.liteav.beauty.c.this.ao.d(true);
                com.tencent.liteav.beauty.c.this.ao.a(a, b, c, d);
            }
        });
    }
    
    public void a(final List<e.f> list) {
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.beauty.c.this.aL = list;
                if ((null == list || 0 == list.size()) && null == com.tencent.liteav.beauty.c.this.aK && null != com.tencent.liteav.beauty.c.this.ao) {
                    com.tencent.liteav.beauty.c.this.ao.e();
                    com.tencent.liteav.beauty.c.this.ao = null;
                    return;
                }
                if (list != null && list.size() != 0) {
                    if (null == com.tencent.liteav.beauty.c.this.ao) {
                        if (com.tencent.liteav.beauty.c.this.L <= 0 || com.tencent.liteav.beauty.c.this.M <= 0) {
                            Log.e("TXCFilterDrawer", "output Width and Height is error!");
                            return;
                        }
                        com.tencent.liteav.beauty.c.this.ao = new aj();
                        com.tencent.liteav.beauty.c.this.ao.a(true);
                        if (!com.tencent.liteav.beauty.c.this.ao.c()) {
                            Log.e("TXCFilterDrawer", "mWatermarkFilter.init failed!");
                            com.tencent.liteav.beauty.c.this.ao.e();
                            com.tencent.liteav.beauty.c.this.ao = null;
                            return;
                        }
                        com.tencent.liteav.beauty.c.this.ao.a(com.tencent.liteav.beauty.c.this.L, com.tencent.liteav.beauty.c.this.M);
                    }
                    com.tencent.liteav.beauty.c.this.ao.d(true);
                    com.tencent.liteav.beauty.c.this.ao.a(list);
                }
            }
        });
    }
    
    void a(final f ay) {
        TXCLog.i("TXCFilterDrawer", "set listener");
        this.aY = ay;
    }
    
    void a(final com.tencent.liteav.basic.b.b b) {
        TXCLog.i("TXCFilterDrawer", "set notify");
        this.aZ = new WeakReference<com.tencent.liteav.basic.b.b>(b);
        if (null != this.ap) {
            this.ap.a(this.aZ.get());
        }
    }
    
    private int a(final int n, final long n2) {
        if (0 == this.X) {
            if (null != this.aY) {
                long generatePtsMS = n2;
                if (generatePtsMS == 0L) {
                    generatePtsMS = TXCTimeUtil.generatePtsMS();
                }
                this.aY.didProcessFrame(n, this.Q, this.R, generatePtsMS);
            }
            return n;
        }
        if (1 != this.X && 3 != this.X && 2 != this.X) {
            TXCLog.e("TXCFilterDrawer", "Don't support format!");
            return -1;
        }
        GLES20.glViewport(0, 0, this.Q, this.R);
        if (null == this.ab) {
            TXCLog.e("TXCFilterDrawer", "mRGBA2I420Filter is null!");
            return n;
        }
        GLES20.glBindFramebuffer(36160, this.b[0]);
        this.ab.b(n);
        if (2 == this.X) {
            this.a(this.Q, this.R);
        }
        else {
            this.a(this.Q, this.R * 3 / 8);
        }
        GLES20.glBindFramebuffer(36160, 0);
        return n;
    }
    
    private int a(final int n, final int n2) {
        if (this.aw) {
            if (null != this.aY) {
                NativeLoad.getInstance();
                NativeLoad.nativeGlReadPixs(n, n2, this.aQ);
                this.aY.didProcessFrame(this.aQ, this.Q, this.R, this.X, TXCTimeUtil.generatePtsMS());
            }
            else if (null != this.aT) {
                NativeLoad.getInstance();
                NativeLoad.nativeGlReadPixs(n, n2, this.aT);
            }
        }
        else if (3 == com.tencent.liteav.basic.c.j.a()) {
            if (0L == this.aM) {
                this.aM = TXCTimeUtil.getTimeTick();
            }
            if (++this.aN >= 100) {
                TXCLog.i("TXCFilterDrawer", "Real fps " + 100.0f / ((TXCTimeUtil.getTimeTick() - this.aM) / 1000.0f));
                this.aN = 0;
                this.aM = TXCTimeUtil.getTimeTick();
            }
            GLES30.glPixelStorei(3333, 1);
            if (Build.VERSION.SDK_INT >= 18) {
                GLES30.glReadBuffer(1029);
            }
            GLES30.glBindBuffer(35051, this.aR[0]);
            NativeLoad.getInstance();
            NativeLoad.nativeGlReadPixs(n, n2, null);
            ByteBuffer byteBuffer = null;
            if (Build.VERSION.SDK_INT >= 18) {
                byteBuffer = (ByteBuffer)GLES30.glMapBufferRange(35051, 0, n * n2 * 4, 1);
                if (null == byteBuffer) {
                    TXCLog.e("TXCFilterDrawer", "glMapBufferRange is null");
                    return -1;
                }
            }
            NativeLoad.getInstance();
            NativeLoad.nativeGlMapBufferToQueue(n, n2, byteBuffer);
            if (Build.VERSION.SDK_INT >= 18) {
                GLES30.glUnmapBuffer(35051);
            }
            GLES30.glBindBuffer(35051, 0);
        }
        else {
            NativeLoad.getInstance();
            NativeLoad.nativeGlReadPixsToQueue(n, n2);
        }
        return 0;
    }
    
    public void b(final int n) {
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.beauty.c.this.X = n;
            }
        });
    }
    
    private void a(final byte[] array, final boolean b) {
        if (!b) {
            if (null != this.aY) {
                this.aY.didProcessFrame(array, this.Q, this.R, this.X, TXCTimeUtil.generatePtsMS());
            }
            else {
                TXCLog.i("TXCFilterDrawer", "First Frame, don't process!");
            }
        }
        else {
            int r = this.R * 3 / 8;
            if (2 == this.X) {
                r = this.R;
            }
            if (null != this.aY) {
                NativeLoad.getInstance();
                if (NativeLoad.nativeGlReadPixsFromQueue(this.Q, r, this.aQ)) {
                    this.aY.didProcessFrame(this.aQ, this.Q, this.R, this.X, TXCTimeUtil.generatePtsMS());
                }
                else {
                    TXCLog.d("TXCFilterDrawer", "nativeGlReadPixsFromQueue Failed");
                    this.aY.didProcessFrame(array, this.Q, this.R, this.X, TXCTimeUtil.generatePtsMS());
                }
            }
            else {
                NativeLoad.getInstance();
                if (!NativeLoad.nativeGlReadPixsFromQueue(this.Q, r, this.aT)) {
                    TXCLog.d("TXCFilterDrawer", "nativeGlReadPixsFromQueue Failed");
                }
            }
        }
    }
    
    private int A(final int n) {
        GLES20.glViewport(0, 0, this.J, this.K);
        return this.a(this.aa.r(), n, 0L);
    }
    
    public void a(final byte[] at) {
        this.aT = at;
    }
    
    private void b(final byte[] array) {
        if (null == this.aa) {
            TXCLog.e("TXCFilterDrawer", "mI4202RGBAFilter is null!");
            return;
        }
        this.aa.a(array);
    }
    
    public void a() {
        if (!this.aw) {
            if (null != this.aA) {
                this.aA.obtainMessage(1).sendToTarget();
                try {
                    this.d.b();
                }
                catch (InterruptedException ex) {}
            }
        }
        else {
            this.b();
        }
    }
    
    private void b() {
        TXCLog.i("TXCFilterDrawer", "come into releaseInternal");
        this.aS = false;
        if (null != this.aa) {
            this.aa.e();
            this.aa = null;
        }
        if (null != this.ab) {
            this.ab.e();
            this.ab = null;
        }
        this.c();
        if (this.am != null) {
            this.am.e();
            this.am = null;
        }
        if (this.an != null) {
            this.an.a();
            this.an = null;
        }
        if (null != this.at) {
            this.at.e();
            this.at = null;
        }
        if (null != this.as) {
            this.as.e();
            this.as = null;
        }
        if (this.ar != null) {
            this.ar.e();
            this.ar = null;
        }
        if (null != this.ao) {
            this.ao.e();
            this.ao = null;
        }
        if (null != this.ap) {
            this.ap.a();
            this.ap = null;
        }
        if (null != this.aq) {
            this.aq.e();
            this.aq = null;
        }
        if (null != this.au) {
            this.au.e();
            this.au = null;
        }
        if (null != this.b) {
            GLES20.glDeleteFramebuffers(1, this.b, 0);
            this.b = null;
        }
        if (null != this.c) {
            GLES20.glDeleteTextures(1, this.c, 0);
            this.c = null;
        }
        TXCLog.i("TXCFilterDrawer", "come out releaseInternal");
    }
    
    private boolean c(final e.b b) {
        TXCLog.i("TXCFilterDrawer", "come into initInternal");
        this.b();
        this.aw = b.j;
        this.J = b.d;
        this.K = b.e;
        this.Y = b.m;
        final int g = b.g;
        final int f = b.f;
        final int h = b.h;
        this.T = b.i;
        this.Q = b.b;
        this.R = b.c;
        final int a = b.a;
        this.L = b.g;
        this.M = b.f;
        if (h == 90 || h == 270) {
            this.L = b.f;
            this.M = b.g;
        }
        this.X = b.l;
        this.W = b.k;
        this.aQ = new byte[this.Q * this.R * 4];
        TXCLog.i("TXCFilterDrawer", "processWidth mPituScaleRatio is %f, process size: %d x %d", this.P, this.L, this.M);
        if (this.P != 1.0f) {
            final int n = (this.L < this.M) ? this.L : this.M;
            if (n > 368) {
                this.P = 432.0f / n;
            }
            if (this.P > 1.0f) {
                this.P = 1.0f;
            }
        }
        this.N = (int)(this.L * this.P);
        this.O = (int)(this.M * this.P);
        final boolean b2 = true;
        this.a(this.N, this.O, this.aC);
        if (null != this.I && null != this.I.a && null == this.ao) {
            TXCLog.i("TXCFilterDrawer", "reset water mark!");
            this.a(this.I.a, this.I.b, this.I.c, this.I.d);
        }
        if (b2 && (this.ah != null || this.ai != null) && this.am == null) {
            this.a(this.N, this.O, this.aj, this.ah, this.ak, this.ai, this.al);
        }
        this.a(this.Y, g, f, this.N, this.O, this.T, h, this.W);
        this.a(this.L, this.M, this.Q, this.R, a);
        if (!b2) {
            TXCLog.e("TXCFilterDrawer", "initInternal failed! releaseInternal!");
            this.b();
        }
        if (null == this.b) {
            this.b = new int[1];
        }
        else {
            GLES20.glDeleteFramebuffers(1, this.b, 0);
        }
        if (null == this.c) {
            this.c = new int[1];
        }
        else {
            GLES20.glDeleteTextures(1, this.c, 0);
        }
        this.a(this.b, this.c, this.Q, this.R);
        if (3 == com.tencent.liteav.basic.c.j.a()) {
            if (null == this.aR) {
                this.aR = new int[1];
            }
            else {
                TXCLog.i("TXCFilterDrawer", "m_pbo0 is not null, delete Buffers, and recreate");
                GLES30.glDeleteBuffers(1, this.aR, 0);
            }
            TXCLog.i("TXCFilterDrawer", "opengl es 3.0, use PBO");
            com.tencent.liteav.basic.c.j.a(g, f, this.aR);
        }
        TXCLog.i("TXCFilterDrawer", "come out initInternal");
        return b2;
    }
    
    public boolean b(final e.b b) {
        if (!this.aw) {
            if (null == this.aA) {
                TXCLog.e("TXCFilterDrawer", "mThreadHandler is null!");
                return false;
            }
            this.aA.obtainMessage(5, 0, 0, (Object)b).sendToTarget();
        }
        else {
            this.d(b);
        }
        return true;
    }
    
    private void a(final int[] array, final int[] array2, final int n, final int n2) {
        GLES20.glGenFramebuffers(1, array, 0);
        array2[0] = com.tencent.liteav.basic.c.j.a(n, n2, 6408, 6408, array2);
        GLES20.glBindFramebuffer(36160, array[0]);
        GLES20.glFramebufferTexture2D(36160, 36064, 3553, array2[0], 0);
        GLES20.glBindFramebuffer(36160, 0);
    }
    
    private boolean d(final e.b b) {
        if ((1 == b.k || 3 == b.k || 2 == b.k) && null == this.aa) {
            (this.aa = new p(b.k)).a(true);
            if (!this.aa.c()) {
                TXCLog.e("TXCFilterDrawer", "mI4202RGBAFilter init failed!!, break init");
                return false;
            }
            this.aa.a(b.d, b.e);
        }
        if ((1 == b.l || 3 == b.l || 2 == b.l) && null == this.ab) {
            this.ab = new w(b.l);
            if (!this.ab.c()) {
                TXCLog.e("TXCFilterDrawer", "mRGBA2I420Filter init failed!!, break init");
                return false;
            }
            this.ab.a(b.b, b.c);
        }
        return true;
    }
    
    public void c(final int ad) {
        this.aD = ad;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (ad > 0) {
                    com.tencent.liteav.beauty.a.a().b();
                }
                if (com.tencent.liteav.beauty.c.this.ac != null && ad >= 0) {
                    com.tencent.liteav.beauty.c.this.ac.c(ad);
                }
            }
        });
    }
    
    public void d(final int ac) {
        if (this.aC == ac || ac > 3 || ac < 0) {
            return;
        }
        this.aC = ac;
        this.a(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.beauty.c.this.a(com.tencent.liteav.beauty.c.this.N, com.tencent.liteav.beauty.c.this.O, ac);
            }
        });
    }
    
    public void e(final int ae) {
        this.aE = ae;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (ae > 0) {
                    com.tencent.liteav.beauty.a.a().c();
                }
                if (com.tencent.liteav.beauty.c.this.ac != null && ae >= 0) {
                    com.tencent.liteav.beauty.c.this.ac.d(ae);
                }
            }
        });
    }
    
    public void f(final int af) {
        this.aF = af;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (af > 0) {
                    com.tencent.liteav.beauty.a.a().f();
                }
                if (com.tencent.liteav.beauty.c.this.ac != null && af >= 0) {
                    com.tencent.liteav.beauty.c.this.ac.f(af);
                }
            }
        });
    }
    
    public void g(final int ag) {
        this.aG = ag;
        this.a(new Runnable() {
            @Override
            public void run() {
                if (ag > 0) {
                    com.tencent.liteav.beauty.a.a().d();
                }
                if (com.tencent.liteav.beauty.c.this.ac != null && ag >= 0) {
                    com.tencent.liteav.beauty.c.this.ac.e(ag);
                }
            }
        });
    }
    
    public void a(final String s) {
    }
    
    public void b(final boolean b) {
    }
    
    public void a(final String s, final boolean b) {
    }
    
    public void a(final Bitmap bitmap) {
        this.a(1.0f, bitmap, this.aB, null, 0.0f);
    }
    
    public void a(final float n, final Bitmap ah, final float n2, final Bitmap ai, final float n3) {
        if (this.ah != ah || this.ai != ai) {
            this.ah = ah;
            this.ai = ai;
            this.aj = n;
            this.ak = n2;
            this.al = n3;
            this.a(new Runnable() {
                @Override
                public void run() {
                    if (null != com.tencent.liteav.beauty.c.this.am) {
                        com.tencent.liteav.beauty.a.a().e();
                    }
                    if (null == com.tencent.liteav.beauty.c.this.ah && com.tencent.liteav.beauty.c.this.ai == null) {
                        if (com.tencent.liteav.beauty.c.this.am != null) {
                            com.tencent.liteav.beauty.c.this.am.e();
                            com.tencent.liteav.beauty.c.this.am = null;
                        }
                    }
                    else if (null == com.tencent.liteav.beauty.c.this.am) {
                        com.tencent.liteav.beauty.c.this.a(com.tencent.liteav.beauty.c.this.N, com.tencent.liteav.beauty.c.this.O, com.tencent.liteav.beauty.c.this.aj, com.tencent.liteav.beauty.c.this.ah, com.tencent.liteav.beauty.c.this.ak, com.tencent.liteav.beauty.c.this.ai, com.tencent.liteav.beauty.c.this.al);
                    }
                    else {
                        com.tencent.liteav.beauty.c.this.am.a(n, ah, n2, ai, n3);
                    }
                }
            });
        }
        else if (this.am != null && (this.aj != n || this.ak != n2 || this.al != n3)) {
            this.aj = n;
            this.ak = n2;
            this.al = n3;
            this.a(new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.beauty.c.this.am.a(n, n2, n3);
                }
            });
        }
    }
    
    public void h(final int n) {
    }
    
    public void i(final int n) {
    }
    
    public void j(final int n) {
    }
    
    public void k(final int n) {
    }
    
    public void l(final int n) {
    }
    
    public void m(final int n) {
    }
    
    public void n(final int n) {
    }
    
    public void o(final int n) {
    }
    
    public void p(final int n) {
    }
    
    public void q(final int n) {
    }
    
    public void r(final int n) {
    }
    
    public void s(final int n) {
    }
    
    public void t(final int n) {
    }
    
    public void u(final int n) {
    }
    
    public void v(final int n) {
    }
    
    public void w(final int n) {
    }
    
    public void x(final int n) {
    }
    
    public void y(final int n) {
    }
    
    public void z(final int n) {
    }
    
    public void b(final float n) {
        this.a(new Runnable() {
            @Override
            public void run() {
                if (n <= 0.0f) {
                    if (null != com.tencent.liteav.beauty.c.this.aq) {
                        com.tencent.liteav.beauty.c.this.aq.e();
                        com.tencent.liteav.beauty.c.this.aq = null;
                        return;
                    }
                }
                else if (null == com.tencent.liteav.beauty.c.this.aq) {
                    com.tencent.liteav.beauty.c.this.aq = new l();
                    com.tencent.liteav.beauty.c.this.aq.a(true);
                    if (!com.tencent.liteav.beauty.c.this.aq.c()) {
                        TXCLog.e("TXCFilterDrawer", "Gaussian Filter init failed!");
                        return;
                    }
                    com.tencent.liteav.beauty.c.this.aq.a(com.tencent.liteav.beauty.c.this.L, com.tencent.liteav.beauty.c.this.M);
                }
                if (null != com.tencent.liteav.beauty.c.this.aq) {
                    com.tencent.liteav.beauty.c.this.aq.a(n / 4.0f);
                }
            }
        });
    }
    
    private void a(final int n, final int n2, final int n3) {
        TXCLog.i("TXCFilterDrawer", "create Beauty Filter!");
        if (0 == n3) {
            if (null == this.ad) {
                this.ad = new com.tencent.liteav.beauty.b.a.a();
            }
            this.ac = this.ad;
            Log.i("TXCFilterDrawer", "0 BeautyFilter");
        }
        else if (1 == n3) {
            if (null == this.ae) {
                this.ae = new com.tencent.liteav.beauty.b.b.a();
            }
            this.ac = this.ae;
            Log.i("TXCFilterDrawer", "1 BeautyFilter");
        }
        else if (2 == n3) {
            if (null == this.ag) {
                this.ag = new com.tencent.liteav.beauty.b.c.a();
            }
            this.ac = this.ag;
            Log.i("TXCFilterDrawer", "2 BeautyFilter");
        }
        else if (3 == n3) {
            if (null == this.af) {
                this.af = new b.c();
            }
            this.ac = this.af;
            Log.i("TXCFilterDrawer", "3 BeautyFilter");
        }
        if (null == this.ac) {
            TXCLog.e("TXCFilterDrawer", "mBeautyFilter set error!");
            return;
        }
        this.ac.a(true);
        if (this.ac.c(n, n2)) {
            if (this.aD > 0) {
                this.ac.c(this.aD);
            }
            if (this.aE > 0) {
                this.ac.d(this.aE);
            }
            if (this.aG > 0) {
                this.ac.e(this.aG);
            }
            if (this.aF > 0) {
                this.ac.f(this.aF);
            }
        }
        else {
            TXCLog.e("TXCFilterDrawer", "mBeautyFilter init failed!");
        }
    }
    
    private void c() {
        if (this.ad != null) {
            this.ad.e();
            this.ad = null;
        }
        if (this.ae != null) {
            this.ae.e();
            this.ae = null;
        }
        if (this.af != null) {
            this.af.e();
            this.af = null;
        }
        if (this.ag != null) {
            this.ag.e();
            this.ag = null;
        }
        this.ac = null;
    }
    
    private void a(final int n, final int n2, final float n3, final Bitmap bitmap, final float n4, final Bitmap bitmap2, final float n5) {
        if (null == this.am) {
            TXCLog.i("TXCFilterDrawer", "createComLooKupFilter");
            this.am = new s(n3, bitmap, n4, bitmap2, n5);
            if (this.am.c()) {
                this.am.a(true);
                this.am.a(n, n2);
            }
            else {
                TXCLog.e("TXCFilterDrawer", "mLookupFilterGroup init failed!");
            }
        }
    }
    
    private void b(final int n, final int n2) {
        if (null == this.au) {
            TXCLog.i("TXCFilterDrawer", "createRecoverScaleFilter");
            this.au = new h();
            if (this.au.c()) {
                this.au.a(true);
            }
            else {
                TXCLog.e("TXCFilterDrawer", "mRecoverScaleFilter init failed!");
            }
        }
        if (this.au != null) {
            this.au.a(n, n2);
        }
    }
    
    private void a(final Runnable runnable) {
        synchronized (this.av) {
            this.av.add(runnable);
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
    
    private class a extends Handler
    {
        private String b;
        
        a(final Looper looper, final Context context) {
            super(looper);
            this.b = "EGLDrawThreadHandler";
        }
        
        private void a(final Object o) {
            TXCLog.i(this.b, "come into InitEGL");
            final e.b b = (e.b)o;
            this.a();
            com.tencent.liteav.beauty.c.this.aJ = new com.tencent.liteav.beauty.a.a.a();
            com.tencent.liteav.beauty.c.this.aI = new com.tencent.liteav.beauty.a.a.c(com.tencent.liteav.beauty.c.this.aJ, b.g, b.f, false);
            com.tencent.liteav.beauty.c.this.aI.b();
            if (!com.tencent.liteav.beauty.c.this.c(b)) {
                TXCLog.e(this.b, "initInternal failed!");
                return;
            }
            TXCLog.i(this.b, "come out InitEGL");
        }
        
        public void a() {
            TXCLog.i(this.b, "come into releaseEGL");
            if (null != com.tencent.liteav.beauty.c.this.aR && com.tencent.liteav.beauty.c.this.aR[0] > 0) {
                GLES20.glDeleteBuffers(1, com.tencent.liteav.beauty.c.this.aR, 0);
                com.tencent.liteav.beauty.c.this.aR = null;
            }
            com.tencent.liteav.beauty.c.this.b();
            if (null != com.tencent.liteav.beauty.c.this.aI) {
                com.tencent.liteav.beauty.c.this.aI.c();
                com.tencent.liteav.beauty.c.this.aI = null;
            }
            if (null != com.tencent.liteav.beauty.c.this.aJ) {
                com.tencent.liteav.beauty.c.this.aJ.a();
                com.tencent.liteav.beauty.c.this.aJ = null;
            }
            com.tencent.liteav.beauty.c.this.aH = false;
            NativeLoad.getInstance();
            NativeLoad.nativeDeleteYuv2Yuv();
            TXCLog.i(this.b, "come out releaseEGL");
        }
        
        public void handleMessage(final Message message) {
            super.handleMessage(message);
            boolean b = false;
            switch (message.what) {
                case 0: {
                    this.a(message.obj);
                    com.tencent.liteav.beauty.c.this.aH = true;
                    b = true;
                    break;
                }
                case 1: {
                    this.a();
                    com.tencent.liteav.beauty.c.this.d.a();
                    break;
                }
                case 2: {
                    com.tencent.liteav.beauty.c.this.b((byte[])message.obj);
                    break;
                }
                case 3: {
                    com.tencent.liteav.beauty.c.this.A(message.arg1);
                    b = true;
                    break;
                }
                case 4: {
                    com.tencent.liteav.beauty.c.this.aB = (float)(message.arg1 / 100.0);
                    if (com.tencent.liteav.beauty.c.this.am != null) {
                        com.tencent.liteav.beauty.c.this.am.a(com.tencent.liteav.beauty.c.this.aB);
                        break;
                    }
                    break;
                }
                case 5: {
                    com.tencent.liteav.beauty.c.this.d((e.b)message.obj);
                    break;
                }
            }
            synchronized (this) {
                if (b) {
                    this.notify();
                }
            }
        }
        
        void b() {
            synchronized (this) {
                try {
                    this.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
    }
}
