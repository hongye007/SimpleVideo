//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.tencent.liteav;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaFormat;
import android.opengl.GLES20;
import android.os.Bundle;
import android.os.Looper;
import android.os.Build.VERSION;
import android.view.Surface;
import com.tencent.liteav.b.b;
import com.tencent.liteav.basic.c.f;
import com.tencent.liteav.basic.c.h;
import com.tencent.liteav.basic.c.m;
import com.tencent.liteav.basic.c.o;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.module.BaseObj;
import com.tencent.liteav.basic.module.Monitor;
import com.tencent.liteav.basic.module.TXCEventRecorderProxy;
import com.tencent.liteav.basic.module.TXCKeyPointReportProxy;
import com.tencent.liteav.basic.module.TXCStatus;
import com.tencent.liteav.basic.structs.TXSNALPacket;
import com.tencent.liteav.basic.structs.TXSVideoFrame;
import com.tencent.liteav.basic.util.TXCTimeUtil;
import com.tencent.liteav.basic.util.MainWaitHandler;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.liteav.beauty.g;
import com.tencent.liteav.beauty.b.w;
import com.tencent.liteav.renderer.TXCGLSurfaceView;
import com.tencent.liteav.videoencoder.TXSVideoEncoderParam;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.ui.TXCloudVideoView;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class CaptureAndEnc extends BaseObj implements b, com.tencent.liteav.basic.b.b, g, q, com.tencent.liteav.videoencoder.d {
    private final MainWaitHandler mMainWaitHandler = new MainWaitHandler(Looper.getMainLooper());
    private p d = null;
    private com.tencent.liteav.beauty.e e = null;
    private boolean f = false;
    private boolean g = false;
    private TXSVideoEncoderParam h = null;
    private com.tencent.liteav.videoencoder.b i = null;
    private int j = 15;
    private boolean k = false;
    private TXSVideoEncoderParam encoderParam = null;
    private com.tencent.liteav.videoencoder.b m = null;
    private Context n = null;
    private i o = null;
    private int p = 0;
    private int q = 0;
    private int r = 0;
    com.tencent.liteav.b a;
    private boolean s = false;
    com.tencent.liteav.b b;
    private int t = 0;
    private int u = 0;
    private boolean v = false;
    private TXCloudVideoView videoView = null;
    private final Object x = new Object();
    private Surface mSurface = null;
    private int z = 0;
    private int A = 0;
    private com.tencent.liteav.basic.c.e B = null;
    private com.tencent.liteav.basic.structs.b C;
    private int D = 0;
    private boolean E = false;
    private boolean F = false;
    private long G = 0L;
    private long H = 0L;
    private int I = 2;
    private WeakReference<t> J;
    private boolean K = false;
    private WeakReference<CaptureAndEnc.a> L = null;
    private h M = null;
    private h N;
    private h O;
    private com.tencent.liteav.beauty.b.p P;
    private byte[] Q;
    private boolean R = false;
    private boolean S = false;
    private boolean T = false;
    private final com.tencent.liteav.beauty.b mBeautyManager;
    private WeakReference<com.tencent.liteav.basic.b.b> V;
    private WeakReference<r> W;
    private int X = 0;
    private int Y = 0;

    public CaptureAndEnc(Context var1) {
        this.n = var1.getApplicationContext();
        this.o = new i();
        this.e = new com.tencent.liteav.beauty.e(this.n, true);
        this.e.a(this);
        this.e.a(this);
        if (this.o.U) {
            this.e.a(com.tencent.liteav.beauty.e.d.a);
        } else if (this.o.T) {
            this.e.a(com.tencent.liteav.beauty.e.d.b);
        } else {
            this.e.a(com.tencent.liteav.beauty.e.d.c);
        }

        this.h = new TXSVideoEncoderParam();
        this.h.encoderMode = 1;
        this.i = null;
        this.encoderParam = new TXSVideoEncoderParam();
        this.encoderParam.encoderMode = 1;
        this.a = new com.tencent.liteav.b(this);
        com.tencent.liteav.basic.license.g var2 = new com.tencent.liteav.basic.license.g(this.n);
        this.mBeautyManager = new com.tencent.liteav.beauty.b(var2);
        this.mBeautyManager.setPreprocessor(this.e);
        com.tencent.liteav.basic.d.b.a().a(this.n);
    }

    public TXBeautyManager getBeautyManager() {
        return this.mBeautyManager;
    }

    public void a(CaptureAndEnc.a var1) {
        this.L = new WeakReference(var1);
    }

    public void a(t var1, int var2) {
        this.I = var2;
        if (var1 != null) {
            this.J = new WeakReference(var1);
        } else {
            this.J = null;
        }

    }

    public void a(boolean var1) {
        this.K = var1;
    }

    public void a(i var1) {
        boolean var2 = var1 != null && (this.o.E != var1.E || this.o.F != var1.F || this.o.G != var1.G || this.o.J != var1.J || this.o.H != var1.H || this.o.I != var1.I);
        boolean var3 = var1 != null && (this.o.a != var1.a || this.o.b != var1.b);
        if (var1 != null) {
            try {
                this.o = (i)var1.clone();
            } catch (CloneNotSupportedException var5) {
                this.o = new i();
                var5.printStackTrace();
            }
        } else {
            this.o = new i();
        }

        TXCLog.i("TXCCaptureAndEnc", String.format("vsize setConfig w*h:%d*%d orientation:%d", this.o.a, this.o.b, this.o.l));
        if (this.d != null) {
            this.d.e(this.o.l);
        }

        this.k(var3 && !this.o.T);
        if (this.j()) {
            this.B();
            if (var2) {
                this.A();
            }
        }

    }

    public void a(com.tencent.liteav.basic.b.b var1) {
        this.V = new WeakReference(var1);
    }

    public void a(r var1) {
        this.W = new WeakReference(var1);
    }

    public int c() {
        return this.h.width;
    }

    public int d() {
        return this.h.height;
    }

    public void setID(String var1) {
        super.setID(var1);
        if (this.i != null) {
            this.i.setID(var1);
        }

        if (this.m != null) {
            this.m.setID(var1);
        }

        if (this.e != null) {
            this.e.setID(var1);
        }

        if (this.d != null) {
            this.d.a(this.getID());
        }

        TXCLog.w("TXCCaptureAndEnc", "setID:" + var1);
    }

    public void setVideoEncRotation(final int var1) {
        TXCLog.i("TXCCaptureAndEnc", "vrotation setVideoEncRotation " + var1);
        this.X = var1;
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    if (CaptureAndEnc.this.i != null) {
                        CaptureAndEnc.this.i.a(var1);
                    }

                    if (CaptureAndEnc.this.m != null) {
                        CaptureAndEnc.this.m.a(var1);
                    }

                }
            });
        } else {
            com.tencent.liteav.videoencoder.b var2 = this.i;
            if (var2 != null) {
                var2.a(var1);
            }

            com.tencent.liteav.videoencoder.b var3 = this.m;
            if (var3 != null) {
                var3.a(var1);
            }
        }

    }

    public void setLocalViewMirror(int var1) {
        TXCLog.i("TXCCaptureAndEnc", "setLocalViewMirror " + var1);
        this.Y = var1;
        if (this.d != null) {
            this.d.d(this.Y);
        }

        if (this.B != null) {
            this.B.b(this.Y);
        }

    }

    public int startWithoutAudio() {
        if (this.j()) {
            TXCLog.w("TXCCaptureAndEnc", "ignore startPush when pushing, status:" + this.r);
            return -2;
        } else {
            TXCDRApi.initCrashReport(this.n);
            this.r = 1;
            TXCLog.i("TXCCaptureAndEnc", "startWithoutAudio");
            this.B();
            TXCDRApi.txReportDAU(this.n, com.tencent.liteav.basic.datareport.a.bu);
            return 0;
        }
    }

    public void stopPush() {
        if (!this.j()) {
            TXCLog.w("TXCCaptureAndEnc", "ignore stopPush when not pushing, status:" + this.r);
        } else {
            TXCLog.i("TXCCaptureAndEnc", "stop");
            this.r = 0;
            this.t();
            this.o.P = false;
            if (this.a != null) {
                this.a.getBeautyManager();
            }

            this.enableBlackStream(false);
            this.C = null;
        }
    }

    public void pausePusher() {
        if (this.r != 1) {
            TXCLog.w("TXCCaptureAndEnc", "ignore pause push when is not pushing, status:" + this.r);
        } else {
            this.r = 2;
            TXCLog.i("TXCCaptureAndEnc", "pausePusher");
            if ((this.o.D & 1) == 1) {
                if (this.a != null && !this.o.M && this.d != null) {
                    this.a.a(this.o.C, this.o.B, this.o.A, this.h.width, this.h.height);
                }

                if (this.d != null) {
                    this.d.c();
                }
            }

        }
    }

    public void resumePusher() {
        if (this.r != 2) {
            TXCLog.w("TXCCaptureAndEnc", "ignore resume push when is not pause, status:" + this.r);
        } else {
            this.r = 1;
            TXCLog.i("TXCCaptureAndEnc", "resumePusher");
            if ((this.o.D & 1) == 1) {
                if (this.a != null && !this.o.M) {
                    this.a.getBeautyManager();
                }

                if (this.d != null) {
                    this.d.b();
                }

                this.A();
            }

        }
    }

    public boolean i() {
        return this.s;
    }

    public void enableBlackStream(boolean var1) {
        TXCLog.i("TXCCaptureAndEnc", "enableBlackStream " + var1);
        this.s = var1;
        if (this.s) {
            if (this.d == null) {
                this.startBlackStream();
            }
        } else {
            this.stopBlackStream();
        }

    }

    private void startBlackStream() {
        TXCLog.i("TXCCaptureAndEnc", " startBlackStream");
        if (this.b == null) {
            this.b = new com.tencent.liteav.b(this);
        }

        this.b.a(10, -1, (Bitmap)null, 64, 64);
    }

    private void stopBlackStream() {
        TXCLog.i("TXCCaptureAndEnc", " stopBlackStream when enableBlackStream " + this.s);
        if (this.b != null) {
            this.b.getBeautyManager();
        }

    }

    public void a(int var1, final int var2, final int var3, final int var4, final int var5, int var6, int var7) {
        com.tencent.liteav.videoencoder.b var8;
        if (var1 == 2) {
            if (this.h.width != 0 && this.h.height != 0 && (var2 != this.h.width || var3 != this.h.height || var4 > this.h.fps)) {
                if (this.d != null) {
                    this.d.a(new Runnable() {
                        public void run() {
                            if (var2 != CaptureAndEnc.this.h.width || var3 != CaptureAndEnc.this.h.height || var4 > CaptureAndEnc.this.h.fps) {
                                if (var2 > var3) {
                                    CaptureAndEnc.this.o.l = 0;
                                } else if (var2 < var3) {
                                    CaptureAndEnc.this.o.l = 1;
                                }

                                p var1 = CaptureAndEnc.this.d;
                                if (var1 != null) {
                                    var1.a(com.tencent.liteav.basic.a.c.a);
                                    var1.b(CaptureAndEnc.this.o.a, CaptureAndEnc.this.o.b);
                                    var1.e(CaptureAndEnc.this.o.l);
                                }

                                CaptureAndEnc.this.o.h = var4;
                                CaptureAndEnc.this.z();
                                TXCLog.e("TXCCaptureAndEnc", String.format("QOS restart big encoder old resolution %dx%d fps %d, new resolution %dx%d fps %d", CaptureAndEnc.this.h.width, CaptureAndEnc.this.h.height, CaptureAndEnc.this.h.fps, var2, var3, var4));
                            }

                        }
                    });
                }
            } else {
                var8 = this.i;
                if (var8 != null) {
                    var8.b(var5, var6);
                    var8.b(var4);
                }
            }

            this.d(var7);
        } else {
            if (this.encoderParam != null && (var2 != this.encoderParam.width || var3 != this.encoderParam.height) && this.d != null) {
                this.d.a(new Runnable() {
                    public void run() {
                        TXCLog.w("TXCCaptureAndEnc", String.format("QOS restart big encoder old resolution %dx%d fps %d, new resolution %dx%d fps %d", CaptureAndEnc.this.encoderParam.width, CaptureAndEnc.this.encoderParam.height, CaptureAndEnc.this.encoderParam.fps, var2, var3, var4));
                        CaptureAndEnc.this.encoderParam.width = var2;
                        CaptureAndEnc.this.encoderParam.height = var3;
                        CaptureAndEnc.this.y();
                    }
                });
            }

            var8 = this.m;
            if (var8 != null) {
                var8.b(var5, var6);
                var8.b(var4);
            }
        }

    }

    public void c(final int var1) {
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    if (CaptureAndEnc.this.i != null) {
                        CaptureAndEnc.this.i.d(var1);
                    }

                    CaptureAndEnc.this.j = var1;
                }
            });
        }
    }

    public void a(final int var1, final int var2, final int var3) {
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    if (var2 != 0 && var3 != 0) {
                        if (CaptureAndEnc.this.d != null) {
                            CaptureAndEnc.this.d.a(com.tencent.liteav.basic.a.c.a);
                            CaptureAndEnc.this.d.b(var2, var3);
                        }
                    }

                    if (var1 != 0 && CaptureAndEnc.this.i != null) {
                        CaptureAndEnc.this.i.c(var1);
                    }

                }
            });
        }
    }

    public void d(final int var1) {
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    if (CaptureAndEnc.this.i != null) {
                        CaptureAndEnc.this.i.e(var1);
                    }

                }
            });
        }
    }

    public boolean j() {
        return this.r != 0;
    }

    public void k() {
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    if (CaptureAndEnc.this.d != null) {
                        CaptureAndEnc.this.d.b(true);
                    }

                    CaptureAndEnc.this.c(CaptureAndEnc.this.h.width, CaptureAndEnc.this.h.height);
                    CaptureAndEnc.this.setVideoEncRotation(CaptureAndEnc.this.X);
                }
            });
        }
    }

    public void startPreview(final TXCloudVideoView var1) {
        if (this.o.M) {
            TXCLog.e("TXCCaptureAndEnc", "enable pure audio push , so can not start preview!");
        } else {
            if (this.a != null) {
                this.a.getBeautyManager();
            }

            this.v = false;
            Object var2 = null;
            boolean var3 = this.o.W;
            if (var1 != null) {
                final TXCGLSurfaceView[] var4 = new TXCGLSurfaceView[1];
                this.a(new Runnable() {
                    public void run() {
                        var4[0] = new TXCGLSurfaceView(var1.getContext());
                        var1.addVideoView(var4[0]);
                    }
                });
                var2 = var4[0];
                var4[0].setNotifyListener(this);
                TXCLog.i("TXCCaptureAndEnc", "start camera preview with GLSurfaceView");
            } else {
                var2 = new f();
                var3 = false;
                TXCLog.i("TXCCaptureAndEnc", "start camera preview with SurfaceTexture");
            }

            this.p = 0;
            this.d = new CameraProxy(this.n, this.o, (m)var2, var3);
            this.stopBlackStream();
            this.j(this.D);
            this.d.a(this.getID());
            this.d.a(this);
            this.d.a(this);
            this.d.a();
            this.d.b(this.t);
            this.d.c(this.u);
            this.d.d(this.Y);
            this.videoView = var1;
            this.mMainWaitHandler.post(new Runnable() {
                public void run() {
                    if (var1 != null) {
                        var1.start(CaptureAndEnc.this.o.K, CaptureAndEnc.this.o.L, CaptureAndEnc.this.d);
                    }

                }
            });
            this.v = false;
            TXCKeyPointReportProxy.a(30003);
        }
    }

    public void c(boolean var1) {
        this.j(var1);
    }

    public int a(boolean var1, int var2, int var3, int var4, int var5, int var6) {
        boolean var7 = this.encoderParam.width != var2 || this.encoderParam.height != var3;
        this.encoderParam.width = var2;
        this.encoderParam.height = var3;
        this.encoderParam.fps = var4;
        this.encoderParam.gop = var6;
        this.encoderParam.encoderProfile = 1;
        this.encoderParam.realTime = this.o.P;
        this.encoderParam.streamType = 3;
        this.encoderParam.bitrate = var5;
        this.encoderParam.annexb = true;
        this.encoderParam.bMultiRef = false;
        if (this.m != null && (var7 || this.k && !var1)) {
            if (this.d != null) {
                this.d.a(new Runnable() {
                    public void run() {
                        CaptureAndEnc.this.y();
                    }
                });
            } else {
                this.y();
            }
        }

        this.k = var1;
        return 0;
    }

    public void e(int var1) {
        if (this.o.h != var1) {
            this.o.h = var1;
            if (this.d != null && this.d.g() < var1) {
                switch(this.p) {
                    case 0:
                        this.k(true);
                        this.t();
                        break;
                    case 1:
                        this.d.f(var1);
                }
            }

        }
    }

    public void a(Surface var1) {
        if (this.videoView != null) {
            TXCLog.w("TXCCaptureAndEnc", "camera preview view is not null, can't set surface");
        } else {
            synchronized(this.x) {
                if (this.mSurface != var1) {
                    TXCLog.i("TXCCaptureAndEnc", "surface-render: set surface " + var1);
                    this.mSurface = var1;
                    if (this.B != null) {
                        this.B.a();
                        this.B = null;
                    }
                } else {
                    TXCLog.i("TXCCaptureAndEnc", "surface-render: set surface the same" + var1);
                }

            }
        }
    }

    public void a(final int var1, final int var2) {
        synchronized(this.x) {
            if (this.B != null) {
                this.B.a(new Runnable() {
                    public void run() {
                        CaptureAndEnc.this.z = var1;
                        CaptureAndEnc.this.A = var2;
                        if (CaptureAndEnc.this.C != null && CaptureAndEnc.this.B != null) {
                            CaptureAndEnc.this.a(CaptureAndEnc.this.C, true);
                        }

                    }
                });
            } else {
                this.z = var1;
                this.A = var2;
            }

        }
    }

    public void a(final o var1) {
        if (this.videoView != null) {
            TXCGLSurfaceView var2 = this.videoView.getGLSurfaceView();
            if (var2 != null) {
                var2.a(new o() {
                    public void onTakePhotoComplete(Bitmap var1x) {
                        if (null != var1) {
                            var1.onTakePhotoComplete(var1x);
                        }

                    }
                });
            } else if (null != var1) {
                var1.onTakePhotoComplete((Bitmap)null);
            }
        } else if (this.B != null) {
            this.B.a(new o() {
                public void onTakePhotoComplete(Bitmap var1x) {
                    if (null != var1) {
                        var1.onTakePhotoComplete(var1x);
                    }

                }
            });
        } else if (null != var1) {
            var1.onTakePhotoComplete((Bitmap)null);
        }

    }

    public void a(com.tencent.liteav.screencapture.a.a var1) {
        if (VERSION.SDK_INT < 21) {
            Bundle var2 = new Bundle();
            var2.putString("EVT_MSG", "Screen recording failed, unsupported Android system version. system version should above 5.0");
            this.onNotifyEvent(-1309, var2);
            TXLog.e("TXCCaptureAndEnc", "Screen capture need running on Android Lollipop or higher version, current:" + VERSION.SDK_INT);
        } else {
            this.p = 1;
            if (this.d == null) {
                this.d = new l(this.n, this.o, var1);
                TXCLog.i("TXCCaptureAndEnc", "create TXCScreenCaptureSource");
            }

            this.mBeautyManager.a(false);
            this.j(this.D);
            this.d.a(this);
            this.d.a(this);
            this.d.a();
            this.d.a(this.getID());
            TXCDRApi.txReportDAU(this.n, com.tencent.liteav.basic.datareport.a.aG);
        }
    }

    public void l() {
        if (this.d != null) {
            this.mBeautyManager.a(true);
            this.j(true);
        }
    }

    private void j(final boolean var1) {
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    if (CaptureAndEnc.this.e != null) {
                        CaptureAndEnc.this.e.b();
                    }

                }
            });
            this.t();
            this.d.a(var1);
            this.d = null;
            TXCLog.i("TXCCaptureAndEnc", "stopped CaptureSource");
            final TXCloudVideoView var2 = this.videoView;
            this.mMainWaitHandler.post(new Runnable() {
                public void run() {
                    if (var2 != null) {
                        var2.stop(var1);
                    }

                }
            });
            this.videoView = null;
            synchronized(this.x) {
                this.mSurface = null;
                if (this.B != null) {
                    this.B.a();
                    this.B = null;
                }
            }

            if (this.a.a()) {
                this.a.getBeautyManager();
            }

            if (this.s) {
                this.startBlackStream();
            }

        }
    }

    public void f(int var1) {
        TXCLog.i("TXCCaptureAndEnc", "setRenderMode " + var1);
        this.u = var1;
        if (this.d != null) {
            this.d.c(var1);
        }

        if (this.B != null) {
            this.B.a(this.u);
        }

    }

    public void d(boolean var1) {
        this.T = var1;
    }

    public void g(int var1) {
        if (this.t != var1) {
            TXCLog.i("TXCCaptureAndEnc", "vrotation setRenderRotation " + var1);
        }

        this.t = var1;
        if (this.d != null) {
            this.d.b(var1);
        }
    }

    public boolean e(boolean var1) {
        return this.d == null ? false : this.d.d(var1);
    }

    public void b(int var1, int var2, int var3) {
        this.mBeautyManager.setBeautyLevel(var1);
        this.mBeautyManager.setWhitenessLevel(var2);
        this.mBeautyManager.setRuddyLevel(var3);
    }

    public void h(int var1) {
        this.mBeautyManager.setBeautyStyle(var1);
    }

    public void a(Bitmap var1, float var2, float var3, float var4) {
        this.o.E = var1;
        this.o.H = var2;
        this.o.I = var3;
        this.o.J = var4;
        this.A();
    }

    public boolean m() {
        return this.d != null ? this.d.h() : false;
    }

    public boolean n() {
        return this.d != null ? this.d.i() : false;
    }

    public boolean o() {
        return this.d != null ? this.d.j() : false;
    }

    public boolean p() {
        return this.d != null ? this.d.k() : false;
    }

    public int q() {
        return this.d == null ? 0 : this.d.e();
    }

    public boolean i(int var1) {
        return this.d == null ? false : this.d.a(var1);
    }

    public void b(int var1, int var2) {
        if (this.d != null) {
            this.d.a(var1, var2);
        }
    }

    public boolean f(boolean var1) {
        this.o.S = var1;
        if (this.d == null) {
            return false;
        } else {
            this.d.c(var1);
            return true;
        }
    }

    public void a(float var1) {
        if (this.d != null) {
            this.d.a(var1);
        }
    }

    public void j(int var1) {
        this.D = var1;
        if (this.d != null) {
            this.d.g(this.D);
        }

    }

    public void g(boolean var1) {
        if (this.E != var1) {
            this.E = var1;
            TXCLog.i("TXCCaptureAndEnc", "trtc_api onVideoConfigChanged enableRps " + this.E);
            if (this.E) {
                this.o.j = 0;
            }

            this.t();
        }
    }

    public void h(boolean var1) {
        this.g = var1;
    }

    public void i(boolean var1) {
        this.F = var1;
    }

    public void k(int var1) {
        com.tencent.liteav.videoencoder.b var2 = null;
        if (var1 == 2) {
            var2 = this.i;
        } else if (var1 == 3) {
            var2 = this.m;
        }

        if (var2 != null) {
            var2.b();
        }

    }

    private void a(int var1, String var2) {
        Bundle var3 = new Bundle();
        var3.putString("EVT_USERID", this.getID());
        var3.putInt("EVT_ID", var1);
        var3.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (var2 != null) {
            var3.putCharSequence("EVT_MSG", var2);
        }

        com.tencent.liteav.basic.util.f.a(this.V, var1, var3);
        if (var1 == -1317) {
            TXCEventRecorderProxy.a(this.getID(), 2002, 4L, -1L, "", this.D);
        } else if (var1 == -1314) {
            TXCEventRecorderProxy.a(this.getID(), 2002, 5L, -1L, "", this.D);
        } else if (var1 == 1003 && this.d != null) {
            TXCEventRecorderProxy.a(this.getID(), 4001, this.d.l() ? 0L : 1L, -1L, "", this.D);
        }

        if (var1 != -1302 && var1 != -1317 && var1 != -1318 && var1 != -1319) {
            if (var1 == -1301 || var1 == -1314 || var1 == -1315 || var1 == -1316) {
                TXCKeyPointReportProxy.b(30003, var1);
                if (this.d != null) {
                    TXCEventRecorderProxy.a(this.getID(), 4002, this.d.l() ? 0L : 1L, (long)var1, "", this.D);
                }
            }
        } else {
            TXCKeyPointReportProxy.b(30002, var1);
        }

    }

    public int a(byte[] var1, int var2, int var3, int var4, long var5) {
        int var7 = this.a(var3, var4, (Object)null);
        if (var7 != 0) {
            return var7;
        } else {
            com.tencent.liteav.videoencoder.b var8 = this.i;
            if (var8 != null) {
                long var9 = var5;
                if (var5 == 0L) {
                    var9 = TXCTimeUtil.generatePtsMS();
                }

                var8.a(var1, var2, var3, var4, var9);
            }

            return 0;
        }
    }

    public int a(byte[] var1, int var2, int var3, int var4, Object var5, long var6, int var8) {
        com.tencent.liteav.basic.structs.b var9 = new com.tencent.liteav.basic.structs.b();
        var9.m = var1;
        var9.b = var2;
        var9.d = true;
        this.a(var9, var3, var4, var5, var6);
        return 0;
    }

    public void r() {
        try {
            if (this.e != null) {
                this.e.b();
            }

            if (this.M != null) {
                this.M.e();
                this.M = null;
            }

            if (this.O != null) {
                this.O.e();
                this.O = null;
            }

            if (this.N != null) {
                this.N.e();
                this.N = null;
            }

            this.z();
            this.y();
        } catch (Exception var2) {
            TXCLog.e("TXCCaptureAndEnc", "stop preprocessor and encoder failed.", var2);
        }

    }

    public int a(int var1, int var2, int var3, Object var4, long var5) {
        int var7 = this.a(var2, var3, var4);
        if (var7 != 0) {
            return var7;
        } else {
            long var8 = var5;
            if (var5 == 0L) {
                var8 = TXCTimeUtil.generatePtsMS();
            }

            com.tencent.liteav.videoencoder.b var10 = this.i;
            if (var10 != null) {
                var10.a(this.o.S);
                var10.a(var1, var2, var3, var8);
            }

            com.tencent.liteav.videoencoder.b var11 = this.m;
            if (var11 != null) {
                var11.a(this.o.S);
                var11.a(var1, var2, var3, var8);
            }

            return 0;
        }
    }

    public int a(int var1, int var2, int var3, Object var4, long var5, int var7) {
        com.tencent.liteav.basic.structs.b var8 = new com.tencent.liteav.basic.structs.b();
        var8.a = var1;
        var8.b = 0;
        this.a(var8, var2, var3, var4, var5);
        return 0;
    }

    private void a(com.tencent.liteav.basic.structs.b var1, int var2, int var3, Object var4, long var5) {
        var1.e = var2;
        var1.f = var3;
        var1.i = this.o.S;
        if (this.o.l == 0) {
            var1.g = this.o.b;
            var1.h = this.o.a;
        } else {
            var1.g = this.o.a;
            var1.h = this.o.b;
        }

        var1.l = com.tencent.liteav.basic.util.f.a(var1.e, var1.f, var1.g, var1.h);

        try {
            this.e.a(this.o.l);
            this.e.a(var4);
            this.e.a(var1, var1.b, 0, var5);
        } catch (Exception var8) {
            TXCLog.e("TXCCaptureAndEnc", "send custom video frame failed." + var8.getMessage());
        }

    }

    public int a(com.tencent.liteav.basic.structs.b var1) {
        if (this.W != null) {
            r var2 = (r)this.W.get();
            if (var2 != null) {
                var1.a = var2.a(var1.a, var1.e, var1.f);
            }
        }

        this.d(var1);
        this.a(var1, false);
        return var1.a;
    }

    public void a(com.tencent.liteav.basic.structs.b var1, long var2) {
        this.a(var1.a, var1.e, var1.f, var2);
    }

    public void b(byte[] var1, int var2, int var3, int var4, long var5) {
    }

    public void onEncodeNAL(TXSNALPacket var1, int var2) {
        if (var2 == 0) {
            if (var1.streamType == 2) {
                this.H = var1.gopIndex;
                this.G = var1.frameIndex;
            }

            if (this.L == null) {
                return;
            }

            CaptureAndEnc.a var3 = (CaptureAndEnc.a)this.L.get();
            if (var3 != null) {
                var3.onEncVideo(var1);
            }
        } else if ((var2 == 10000004 || var2 == 10000005) && this.h.encodeType == 1) {
            Monitor.a(2, String.format("VideoEncoder: hardware encoder error %d, switch to software encoder", var2), "", 0);
            this.t();
            ++this.q;
            this.o.j = 0;
            this.a(1103, "Failed to enable hardware encoder, use software encoder");
        }

    }

    public void onEncodeFormat(MediaFormat var1) {
        if (this.L != null) {
            CaptureAndEnc.a var2 = (CaptureAndEnc.a)this.L.get();
            if (var2 != null) {
                var2.onEncVideoFormat(var1);
            }

        }
    }

    public void onEncodeFinished(int var1, long var2, long var4) {
        if (var1 == 2) {
            this.H = var2;
            this.G = var4;
        }

    }

    public void onRestartEncoder(int var1) {
        if (this.h.width * this.h.height < 518400) {
            this.o.j = 0;
        } else if (this.h.width * this.h.height < 921600 && this.f) {
            this.o.j = 0;
        }

        if (var1 == 3) {
            this.v();
        } else {
            this.f = true;
            this.u();
        }

    }

    public void onEncodeDataIn(int var1) {
    }

    public void onNotifyEvent(int var1, Bundle var2) {
        if (var2 != null) {
            var2.putString("EVT_USERID", this.getID());
        }

        com.tencent.liteav.basic.util.f.a(this.V, var1, var2);
        if (var1 == -1317) {
            TXCEventRecorderProxy.a(this.getID(), 2002, 4L, -1L, "", this.D);
        } else if (var1 == -1314) {
            TXCEventRecorderProxy.a(this.getID(), 2002, 5L, -1L, "", this.D);
        } else if (var1 == 1003) {
            if (this.d != null) {
                TXCEventRecorderProxy.a(this.getID(), 4001, this.d.l() ? 0L : 1L, -1L, "", this.D);
            }
        } else if (var1 == -1308) {
            this.l();
        }

        if (var1 != -1302 && var1 != -1317 && var1 != -1318 && var1 != -1319) {
            if (var1 == -1301 || var1 == -1314 || var1 == -1315 || var1 == -1316) {
                TXCKeyPointReportProxy.b(30003, var1);
                if (this.d != null) {
                    TXCEventRecorderProxy.a(this.getID(), 4002, this.d.l() ? 0L : 1L, (long)var1, "", this.D);
                }
            }
        } else {
            TXCKeyPointReportProxy.b(30002, var1);
        }

    }

    public void a(final Bitmap var1, final ByteBuffer var2, final int var3, final int var4) {
        p var5 = this.d;
        if (var5 != null) {
            var5.a(new Runnable() {
                public void run() {
                    try {
                        if (CaptureAndEnc.this.r != 2) {
                            return;
                        }

                        int var1x = var1.getWidth();
                        int var2x = var1.getHeight();
                        com.tencent.liteav.basic.c.a var3x = com.tencent.liteav.basic.util.f.a(var1x, var2x, var3, var4);
                        CaptureAndEnc.this.e.a(var3x);
                        CaptureAndEnc.this.e.b(false);
                        if (CaptureAndEnc.this.o.W) {
                            CaptureAndEnc.this.e.a(false);
                        }

                        CaptureAndEnc.this.e.a(var3, var4);
                        CaptureAndEnc.this.e.a(var2.array(), var1x, var2x, 0, 2, 0);
                    } catch (Exception var4x) {
                        TXCLog.e("TXCCaptureAndEnc", "processFrame failed." + var4x.getMessage());
                    }

                }
            });
        } else {
            if (this.i == null || this.S || this.h.width != var3 || this.h.height != var4 || this.h.encodeType != 2 || this.h.gop != this.o.i) {
                this.a(var3, var4, 2, (Object)null);
            }

            com.tencent.liteav.videoencoder.b var6 = this.i;
            if (var6 != null) {
                var6.a(var2.array(), 2, var3, var4, TXCTimeUtil.generatePtsMS());
            }
        }

    }

    public void a(final com.tencent.liteav.videoencoder.b var1) {
        p var2 = this.d;
        if (var2 != null) {
            var2.a(new Runnable() {
                public void run() {
                    try {
                        if (var1 != null) {
                            var1.a();
                            var1.a((com.tencent.liteav.videoencoder.d)null);
                        }
                    } catch (Exception var2) {
                        TXCLog.e("TXCCaptureAndEnc", "stop encoder failed.", var2);
                    }

                }
            });
        } else {
            try {
                if (var1 != null) {
                    var1.a();
                    var1.a((com.tencent.liteav.videoencoder.d)null);
                }
            } catch (Exception var4) {
                TXCLog.e("TXCCaptureAndEnc", "stop encoder failed.", var4);
            }
        }

    }

    public void a() {
        if (this.L != null) {
            CaptureAndEnc.a var1 = (CaptureAndEnc.a)this.L.get();
            if (var1 != null) {
                var1.onBackgroudPushStop();
            }
        }
    }

    public void a(SurfaceTexture var1) {
        if (this.e != null) {
            this.e.b();
        }

    }

    public void b(com.tencent.liteav.basic.structs.b var1) {
        if (!this.R) {
            this.R = true;
            TXCKeyPointReportProxy.b(30003, 0);
        }

        if (this.r != 2) {
            p var2 = this.d;
            if (this.e != null && !this.o.M && var2 != null) {
                if (this.h.height != var1.h || this.h.width != var1.g) {
                    this.d(var1.g, var1.h);
                }

                this.e.a(var2.getEGLContext());
                this.e.a(this.o.l);
                this.e.a(var1, var1.b, 0, 0L);
            }

        }
    }

    public void s() {
        TXCLog.i("TXCCaptureAndEnc", "onCaptureDestroy->enter ");
        if (this.e != null) {
            this.e.b();
        }

        if (this.M != null) {
            this.M.e();
            this.M = null;
        }

        if (this.O != null) {
            this.O.e();
            this.O = null;
        }

        if (this.N != null) {
            this.N.e();
            this.N = null;
        }

        if (this.P != null) {
            this.P.e();
            this.P = null;
        }

        this.z();
        this.y();
        if (this.W != null) {
            r var1 = (r)this.W.get();
            if (var1 != null) {
                var1.a();
            }
        }

    }

    private void a(int var1, int var2, int var3, Object var4) {
        TXCLog.i("TXCCaptureAndEnc", "New encode size width = " + var1 + " height = " + var2 + " encType = " + var3 + " eglContext: " + var4);
        this.z();
        com.tencent.liteav.videoencoder.b var5 = new com.tencent.liteav.videoencoder.b(var3);
        TXCStatus.a(this.getID(), 4005, this.D, var3);
        if (var3 == 1) {
            TXCEventRecorderProxy.a(this.getID(), 4004, 1L, -1L, "", this.D);
        } else {
            TXCEventRecorderProxy.a(this.getID(), 4004, 0L, -1L, "", this.D);
        }

        this.S = false;
        this.h.encodeType = var3;
        this.h.width = var1;
        this.h.height = var2;
        this.h.fps = this.o.h;
        this.h.gop = this.o.i;
        this.h.encoderProfile = this.o.n;
        this.h.glContext = var4 != null ? var4 : var5.a(var1, var2);
        this.h.realTime = this.o.P;
        this.h.streamType = this.D;
        this.h.annexb = this.F;
        this.h.bMultiRef = this.E;
        this.h.baseFrameIndex = this.G + 20L;
        this.h.baseGopIndex = (this.H + 1L) % 255L + 1L;
        this.h.bLimitFps = this.g;
        this.h.record = this.T;
        this.h.encFmt = this.o.Y;
        var5.a(this);
        var5.a(this);
        var5.a(this.h);
        var5.c(this.o.c);
        var5.d(this.j);
        var5.setID(this.getID());
        var5.a(this.X);
        this.i = var5;
        TXCStatus.a(this.getID(), 4003, this.D, this.h.width << 16 | this.h.height);
        TXCStatus.a(this.getID(), 13003, this.D, this.h.gop * 1000);
        TXCEventRecorderProxy.a(this.getID(), 4003, (long)this.h.width, (long)this.h.height, "", this.D);
        TXCKeyPointReportProxy.a(40036, this.h.encodeType, this.D);
        TXCKeyPointReportProxy.a(40037, this.h.width << 16 | this.h.height, this.D);
    }

    private void a(Object var1, int var2) {
        this.y();
        com.tencent.liteav.videoencoder.b var3 = new com.tencent.liteav.videoencoder.b(var2);
        TXCStatus.a(this.getID(), 4005, 3, var2);
        if (var2 == 1) {
            TXCEventRecorderProxy.a(this.getID(), 4004, 1L, -1L, "", 3);
        } else {
            TXCEventRecorderProxy.a(this.getID(), 4004, 0L, -1L, "", 3);
        }

        this.encoderParam.glContext = var1 != null ? var1 : var3.a(this.encoderParam.width, this.encoderParam.height);
        this.encoderParam.encodeType = var2;
        TXCLog.i("TXCCaptureAndEnc", "start small video encoder");
        var3.a(this);
        var3.a(this);
        var3.a(this.encoderParam);
        var3.c(this.encoderParam.bitrate);
        var3.setID(this.getID());
        var3.a(this.X);
        this.m = var3;
        TXCStatus.a(this.getID(), 4003, 3, this.encoderParam.width << 16 | this.encoderParam.height);
        TXCStatus.a(this.getID(), 13003, 3, this.encoderParam.gop * 1000);
    }

    private void a(int var1, int var2, int var3, long var4) {
        if (var4 == 0L) {
            var4 = TXCTimeUtil.generatePtsMS();
        }

        this.b(var2, var3, this.e.a());
        com.tencent.liteav.videoencoder.b var6 = this.i;
        if (var6 != null) {
            var6.a(var1, var2, var3, var4);
        }

        com.tencent.liteav.videoencoder.b var7 = this.m;
        if (var7 != null) {
            var7.a(var1, var2, var3, var4);
        }

    }

    private int a(int var1, int var2, Object var3) {
        int var4 = this.o.a;
        int var5 = this.o.b;
        if (this.o.l == 0 || this.o.l == 2) {
            var4 = this.o.b;
            var5 = this.o.a;
        }

        if (var4 > 0 && var5 > 0) {
            if (this.o.M) {
                this.z();
                return -1000;
            } else {
                this.b(var4, var5, var3);
                return 0;
            }
        } else {
            TXCLog.e("TXCCaptureAndEnc", "sendCustomYUVData: invalid video encode resolution");
            return -1;
        }
    }

    private void b(int var1, int var2, Object var3) {
        byte var4 = 2;
        switch(this.o.j) {
            case 0:
                var4 = 2;
                break;
            case 1:
                var4 = 1;
                break;
            case 2:
                var4 = 3;
        }

        if (this.p == 1 && this.q == 0) {
            var4 = 1;
        }

        int var5 = this.o.i;
        if (this.i == null || this.S || this.h.width != var1 || this.h.height != var2 || this.h.encodeType != var4 || this.h.gop != var5) {
            this.a(var1, var2, var4, var3);
        }

        if ((this.m == null || this.encoderParam.encodeType != var4) && this.k) {
            this.a((Object)var3, var4);
        }

    }

    public void t() {
        if (this.i != null) {
            if (this.d != null) {
                this.d.a(new Runnable() {
                    public void run() {
                        CaptureAndEnc.this.z();
                        CaptureAndEnc.this.y();
                    }
                });
            } else {
                this.z();
                this.y();
            }

        }
    }

    public void u() {
        if (this.i != null) {
            if (this.d != null) {
                this.d.a(new Runnable() {
                    public void run() {
                        CaptureAndEnc.this.z();
                    }
                });
            } else {
                this.z();
            }

        }
    }

    public void v() {
        if (this.m != null) {
            if (this.d != null) {
                this.d.a(new Runnable() {
                    public void run() {
                        CaptureAndEnc.this.y();
                    }
                });
            } else {
                this.y();
            }

        }
    }

    private void y() {
        try {
            com.tencent.liteav.videoencoder.b var1 = this.m;
            this.m = null;
            if (var1 != null) {
                var1.a();
                var1.a((com.tencent.liteav.videoencoder.d)null);
            }
        } catch (Exception var2) {
            TXCLog.e("TXCCaptureAndEnc", "stop video encoder failed.", var2);
        }

    }

    private void z() {
        try {
            TXCLog.i("TXCCaptureAndEnc", "stopBigVideoEncoderInGLThread");
            com.tencent.liteav.videoencoder.b var1 = this.i;
            this.i = null;
            if (var1 != null) {
                var1.a();
                var1.a((com.tencent.liteav.videoencoder.d)null);
            }

            this.S = true;
        } catch (Exception var2) {
            TXCLog.e("TXCCaptureAndEnc", "stopBigVideoEncoder failed.", var2);
        }

    }

    private void k(final boolean var1) {
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    p var1x = CaptureAndEnc.this.d;
                    if (var1x != null) {
                        var1x.f(CaptureAndEnc.this.o.h);
                        var1x.e(CaptureAndEnc.this.o.l);
                        var1x.a(CaptureAndEnc.this.o.k);
                        var1x.b(CaptureAndEnc.this.o.a, CaptureAndEnc.this.o.b);
                        var1x.e(CaptureAndEnc.this.o.U);
                        if (var1 && var1x.d()) {
                            var1x.b(false);
                        }

                    }
                }
            });
        }

    }

    private void A() {
        if (this.d != null) {
            this.d.a(new Runnable() {
                public void run() {
                    CaptureAndEnc.this.c(CaptureAndEnc.this.h.width, CaptureAndEnc.this.h.height);
                }
            });
        }

    }

    private void c(int var1, int var2) {
        if (this.o.J != -1.0F) {
            if (this.e != null) {
                this.e.a(this.o.E, this.o.H, this.o.I, this.o.J);
            }
        } else if (this.e != null && var1 != 0 && var2 != 0) {
            this.e.a(this.o.E, (float)this.o.F / (float)var1, (float)this.o.G / (float)var2, this.o.E == null ? 0.0F : (float)this.o.E.getWidth() / (float)var1);
        }

    }

    private void B() {
        if (this.e != null) {
            if (this.o.U) {
                this.e.a(com.tencent.liteav.beauty.e.d.a);
            } else if (this.o.T) {
                this.e.a(com.tencent.liteav.beauty.e.d.b);
            } else {
                this.e.a(com.tencent.liteav.beauty.e.d.c);
            }
        }

    }

    private void d(int var1, int var2) {
        this.c(var1, var2);
    }

    private void e(int var1, int var2) {
        if (!this.v) {
            Bundle var3 = new Bundle();
            var3.putString("EVT_USERID", this.getID());
            var3.putInt("EVT_ID", 2003);
            var3.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
            var3.putCharSequence("EVT_MSG", "Renders the first video frame");
            var3.putInt("EVT_PARAM1", var1);
            var3.putInt("EVT_PARAM2", var2);
            com.tencent.liteav.basic.util.f.a(this.V, 2003, var3);
            TXCLog.i("TXCCaptureAndEnc", "trtc_render render first frame " + this.getID() + ", " + this.D);
            this.v = true;
        }

    }

    private void a(com.tencent.liteav.basic.structs.b var1, boolean var2) {
        this.e(var1.e, var1.f);
        this.C = var1;
        if (this.videoView != null) {
            if (this.d != null) {
                this.d.a(var1);
            }
        } else {
            synchronized(this.x) {
                if (this.mSurface != null && this.B == null && this.d != null && this.d.getEGLContext() != null) {
                    this.B = new com.tencent.liteav.basic.c.e();
                    this.B.a(this.d.getEGLContext(), this.mSurface);
                    this.B.a(this.u);
                    this.B.b(this.Y);
                }

                if (this.B != null && this.d != null) {
                    this.B.a(var1.a, var1.i, this.t, this.z, this.A, var1.e, var1.f, var2, this.d.l());
                }
            }
        }

    }

    private int c(com.tencent.liteav.basic.structs.b var1) {
        if (this.K) {
            return var1.a;
        } else {
            int var2 = var1.a;
            boolean var3 = this.Y == 1;
            h var4;
            if (var3 != var1.i) {
                if (this.N == null) {
                    var4 = new h();
                    var4.c();
                    var4.a(true);
                    var4.a(var1.e, var1.f);
                    if (var1.e > var1.f) {
                        var4.h();
                    } else {
                        var4.g();
                    }

                    this.N = var4;
                }

                var4 = this.N;
                if (var4 != null) {
                    var4.a(var1.e, var1.f);
                    var2 = var4.a(var1.a);
                }
            }

            if (this.t != 0) {
                if (this.O == null) {
                    var4 = new h();
                    var4.c();
                    var4.a(true);
                    var4.a(var1.e, var1.f);
                    this.O = var4;
                }

                var4 = this.O;
                if (var4 != null) {
                    GLES20.glViewport(0, 0, var1.e, var1.f);
                    int var5 = var1.e;
                    int var6 = var1.f;
                    com.tencent.liteav.basic.c.a var7 = com.tencent.liteav.basic.util.f.a(var5, var6, var1.e, var1.f);
                    float[] var8 = var4.a(var5, var6, (FloatBuffer)null, var7, 0);
                    int var9 = (720 - this.t) % 360;
                    int var10 = var9 != 90 && var9 != 270 ? var1.e : var1.f;
                    int var11 = var9 != 90 && var9 != 270 ? var1.f : var1.e;
                    var4.a(var5, var6, var9, var8, (float)var10 / (float)var11, false, false);
                    var4.a(var2);
                    var2 = var4.l();
                }
            }

            return var2;
        }
    }

    private void d(com.tencent.liteav.basic.structs.b var1) {
        final WeakReference var2 = this.J;
        if (var2 != null) {
            int var3 = this.c(var1);
            t var4;
            if (this.I == 2) {
                var4 = (t)var2.get();
                if (var4 != null) {
                    TXSVideoFrame var5 = new TXSVideoFrame();
                    var5.width = var1.e;
                    var5.height = var1.f;
                    var5.textureId = var3;
                    var5.eglContext = this.e.a();
                    var5.pts = TXCTimeUtil.generatePtsMS();
                    var4.onRenderVideoFrame(this.getID(), this.D, var5);
                    if (this.K) {
                        var1.a = var5.textureId;
                    }
                }
            } else if (this.I == 1 || this.I == 4) {
                if (this.M == null) {
                    var4 = null;
                    w var7;
                    if (this.I == 1) {
                        var7 = new w(1);
                    } else {
                        var7 = new w(3);
                    }

                    var7.a(true);
                    if (var7.c()) {
                        var7.a(var1.e, var1.f);
                        var7.a(new com.tencent.liteav.basic.c.h.a() {
                            public void a(int var1) {
                                h var2x = CaptureAndEnc.this.M;
                                t var3 = (t)var2.get();
                                if (var2x != null && var3 != null) {
                                    TXSVideoFrame var4 = new TXSVideoFrame();
                                    var4.width = var2x.o();
                                    var4.height = var2x.p();
                                    var4.pts = TXCTimeUtil.generatePtsMS();
                                    var3.onRenderVideoFrame(CaptureAndEnc.this.getID(), CaptureAndEnc.this.D, var4);
                                    CaptureAndEnc.this.Q = var4.data;
                                }

                            }
                        });
                        this.M = var7;
                    } else {
                        TXCLog.i("TXCCaptureAndEnc", "init filter error ");
                        this.M = null;
                    }
                }

                h var9 = this.M;
                if (var9 != null) {
                    GLES20.glViewport(0, 0, var1.e, var1.f);
                    var9.a(var1.e, var1.f);
                    var9.a(var3);
                }

                if (this.K && this.Q != null) {
                    byte var8 = 1;
                    if (this.I == 1) {
                        var8 = 1;
                    } else if (this.I == 4) {
                        var8 = 3;
                    }

                    com.tencent.liteav.beauty.b.p var6;
                    if (this.P == null) {
                        var6 = new com.tencent.liteav.beauty.b.p(var8);
                        var6.a(true);
                        if (!var6.c()) {
                            TXCLog.w("TXCCaptureAndEnc", " init i420ToRGBA filter failed");
                        }

                        var6.a(var1.e, var1.f);
                        this.P = var6;
                    }

                    var6 = this.P;
                    if (var6 != null) {
                        GLES20.glViewport(0, 0, var1.e, var1.f);
                        var6.a(var1.e, var1.f);
                        var6.a(this.Q);
                        var1.a = var6.r();
                    }

                    this.Q = null;
                }
            }

            if (this.K && this.videoView != null) {
                TXCGLSurfaceView var10 = this.videoView.getGLSurfaceView();
                if (var10 != null) {
                    var10.d();
                }
            }

        }
    }

    public void a(float var1, float var2) {
        if (this.d != null && this.o.K) {
            this.d.a(var1, var2);
        }

    }

    public void l(final int var1) {
        if (var1 < 1) {
            var1 = 1;
        }

        if (var1 > 2) {
            var1 = 2;
        }

        Runnable var3 = new Runnable() {
            public void run() {
                if (CaptureAndEnc.this.h.encoderMode != var1) {
                    CaptureAndEnc.this.h.encoderMode = var1;
                    CaptureAndEnc.this.encoderParam.encoderMode = var1;
                    CaptureAndEnc.this.z();
                    CaptureAndEnc.this.y();
                }
            }
        };
        if (null == this.d) {
            var3.run();
        } else {
            this.d.a(var3);
        }

    }

    private void a(Runnable var1) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            this.mMainWaitHandler.a(var1);
        } else {
            var1.run();
        }

    }

    public interface a {
        void onEncVideo(TXSNALPacket var1);

        void onEncVideoFormat(MediaFormat var1);

        void onBackgroudPushStop();
    }
}
