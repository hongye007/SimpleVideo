package com.tencent.liteav.g;

import java.util.concurrent.*;

import com.tencent.liteav.CameraProxy;

import android.content.*;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.editer.*;
import android.opengl.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.videoencoder.*;
import java.nio.*;

import android.media.*;
import com.tencent.liteav.basic.structs.*;
import java.util.*;
import android.view.*;
import android.os.*;

public class p
{
    private LinkedBlockingQueue<e> a;
    private Context b;
    private boolean c;
    private l d;
    private n e;
    private q f;
    private t g;
    private s h;
    private b i;
    private c.a j;
    private com.tencent.liteav.videoencoder.b k;
    private a l;
    private com.tencent.liteav.muxer.c m;
    private boolean n;
    private int o;
    private j p;
    private boolean q;
    private r r;
    private com.tencent.liteav.g.c s;
    private com.tencent.liteav.g.a t;
    private g u;
    private com.tencent.liteav.editer.j v;
    private u w;
    private d x;
    private com.tencent.liteav.g.d y;
    private com.tencent.liteav.editer.n z;
    private Handler A;
    
    public p(final Context b) {
        this.o = 2;
        this.q = true;
        this.r = new r() {
            @Override
            public void a(final EGLContext glContext) {
                TXCLog.i("VideoJoinGenerate", "OnContextListener onContext");
                if (com.tencent.liteav.g.p.this.k != null) {
                    final TXSVideoEncoderParam txsVideoEncoderParam = new TXSVideoEncoderParam();
                    txsVideoEncoderParam.width = com.tencent.liteav.g.p.this.h.h.a;
                    txsVideoEncoderParam.height = com.tencent.liteav.g.p.this.h.h.b;
                    txsVideoEncoderParam.fps = com.tencent.liteav.g.p.this.h.j();
                    txsVideoEncoderParam.glContext = glContext;
                    txsVideoEncoderParam.enableEGL14 = true;
                    txsVideoEncoderParam.enableBlackList = false;
                    txsVideoEncoderParam.appendSpsPps = false;
                    txsVideoEncoderParam.annexb = true;
                    txsVideoEncoderParam.fullIFrame = false;
                    txsVideoEncoderParam.gop = com.tencent.liteav.g.p.this.h.k();
                    if (com.tencent.liteav.g.p.this.c) {
                        txsVideoEncoderParam.encoderMode = 1;
                        txsVideoEncoderParam.encoderProfile = 3;
                        txsVideoEncoderParam.record = true;
                    }
                    else {
                        txsVideoEncoderParam.encoderMode = 3;
                        txsVideoEncoderParam.encoderProfile = 1;
                    }
                    com.tencent.liteav.g.p.this.k.c(com.tencent.liteav.g.p.this.h.i());
                    com.tencent.liteav.g.p.this.k.a(com.tencent.liteav.g.p.this.x);
                    com.tencent.liteav.g.p.this.k.a(txsVideoEncoderParam);
                }
                com.tencent.liteav.g.p.this.l = new a();
                com.tencent.liteav.g.p.this.l.a(com.tencent.liteav.g.p.this.u);
                com.tencent.liteav.g.p.this.l.a(com.tencent.liteav.g.p.this.w);
                final com.tencent.liteav.editer.t t = new com.tencent.liteav.editer.t();
                t.channelCount = com.tencent.liteav.g.p.this.h.b;
                t.sampleRate = com.tencent.liteav.g.p.this.h.a;
                t.maxInputSize = com.tencent.liteav.g.p.this.h.c;
                t.encoderType = com.tencent.liteav.g.p.this.o;
                t.audioBitrate = com.tencent.liteav.g.p.this.h.h();
                com.tencent.liteav.g.p.this.l.a(t);
                if (com.tencent.liteav.g.p.this.d != null) {
                    com.tencent.liteav.g.p.this.d.a(com.tencent.liteav.g.p.this.p);
                    com.tencent.liteav.g.p.this.d.a(com.tencent.liteav.g.p.this.s);
                    com.tencent.liteav.g.p.this.d.a(com.tencent.liteav.g.p.this.t);
                    com.tencent.liteav.g.p.this.d.a();
                }
            }
            
            @Override
            public void b(final EGLContext eglContext) {
            }
        };
        this.s = new com.tencent.liteav.g.c() {
            @Override
            public void a(final e e, final i i) {
                try {
                    com.tencent.liteav.g.p.this.a.put(e);
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (com.tencent.liteav.g.p.this.e != null) {
                    com.tencent.liteav.g.p.this.e.a(e, i);
                }
            }
        };
        this.t = new com.tencent.liteav.g.a() {
            @Override
            public void a(final e e, final i i) {
                if (com.tencent.liteav.g.p.this.i != null) {
                    com.tencent.liteav.g.p.this.i.a(e);
                }
            }
        };
        this.u = new g() {
            @Override
            public void a(final int n) {
                com.tencent.liteav.g.p.this.d.a(n <= 5);
            }
        };
        this.v = new com.tencent.liteav.editer.j() {
            @Override
            public void a(final e e) {
                if (e == null) {
                    return;
                }
                TXCLog.d("VideoJoinGenerate", "didAudioProcessFrame frame:" + e.e());
                if (e.p() && com.tencent.liteav.g.p.this.k != null) {
                    com.tencent.liteav.g.p.this.k.c();
                    TXCLog.i("VideoJoinGenerate", "signalEOSAndFlush");
                    return;
                }
                if (com.tencent.liteav.g.p.this.l != null) {
                    com.tencent.liteav.g.p.this.l.a(e);
                }
                if (com.tencent.liteav.g.p.this.i != null) {
                    com.tencent.liteav.g.p.this.i.i();
                }
            }
        };
        this.w = new u() {
            @Override
            public void a(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
                if (com.tencent.liteav.g.p.this.o == 2 && com.tencent.liteav.g.p.this.q) {
                    com.tencent.liteav.g.p.this.q = false;
                    final MediaFormat a = com.tencent.liteav.basic.util.f.a(com.tencent.liteav.g.p.this.h.a, com.tencent.liteav.g.p.this.h.b, 2);
                    if (com.tencent.liteav.g.p.this.m != null) {
                        com.tencent.liteav.g.p.this.m.b(a);
                    }
                }
                if (com.tencent.liteav.g.p.this.m != null) {
                    com.tencent.liteav.g.p.this.m.a(byteBuffer, bufferInfo);
                }
            }
            
            @Override
            public void a(final MediaFormat mediaFormat) {
                TXCLog.i("VideoJoinGenerate", "Audio onEncodeFormat format:" + mediaFormat);
                if (com.tencent.liteav.g.p.this.m != null) {
                    com.tencent.liteav.g.p.this.m.b(mediaFormat);
                    if (com.tencent.liteav.g.p.this.m.c()) {
                        com.tencent.liteav.g.p.this.m.a();
                        com.tencent.liteav.g.p.this.n = true;
                    }
                }
            }
            
            @Override
            public void a() {
                TXCLog.i("VideoJoinGenerate", "===Audio onEncodeComplete===");
                com.tencent.liteav.g.p.this.b();
                com.tencent.liteav.g.p.this.c();
            }
        };
        this.x = new d() {
            @Override
            public void onEncodeNAL(final TXSNALPacket txsnalPacket, final int n) {
                if (n != 0) {
                    TXCLog.e("VideoJoinGenerate", "mVideoEncodeListener, errCode = " + n);
                    return;
                }
                if (txsnalPacket == null || txsnalPacket.nalData == null) {
                    TXCLog.i("VideoJoinGenerate", "nal is null ===Video onEncodeComplete===");
                    com.tencent.liteav.g.p.this.b();
                    com.tencent.liteav.g.p.this.c();
                    return;
                }
                e e = null;
                try {
                    e = com.tencent.liteav.g.p.this.a.take();
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                if (e.p()) {
                    TXCLog.i("VideoJoinGenerate", "frame.isEnd===Video onEncodeComplete===:" + e.p() + ", nal:" + txsnalPacket);
                    com.tencent.liteav.g.p.this.b();
                    com.tencent.liteav.g.p.this.c();
                    return;
                }
                synchronized (this) {
                    if (com.tencent.liteav.g.p.this.m != null && txsnalPacket != null && txsnalPacket.nalData != null) {
                        if (com.tencent.liteav.g.p.this.n) {
                            this.a(txsnalPacket, e);
                        }
                        else if (txsnalPacket.nalType == 0) {
                            final MediaFormat a = com.tencent.liteav.basic.util.f.a(txsnalPacket.nalData, com.tencent.liteav.g.p.this.h.h.a, com.tencent.liteav.g.p.this.h.h.b);
                            if (a != null) {
                                com.tencent.liteav.g.p.this.m.a(a);
                                com.tencent.liteav.g.p.this.m.a();
                                com.tencent.liteav.g.p.this.n = true;
                            }
                            this.a(txsnalPacket, e);
                        }
                    }
                }
                com.tencent.liteav.g.p.this.a(e.e());
            }
            
            private void a(final TXSNALPacket txsnalPacket, final e e) {
                int flags = 0;
                if (txsnalPacket.info == null) {
                    if (txsnalPacket.nalType == 0) {
                        flags = 1;
                    }
                }
                else {
                    flags = txsnalPacket.info.flags;
                }
                if (com.tencent.liteav.g.p.this.m != null) {
                    com.tencent.liteav.g.p.this.m.b(txsnalPacket.nalData, 0, txsnalPacket.nalData.length, e.e(), flags);
                }
            }
            
            @Override
            public void onEncodeFormat(final MediaFormat mediaFormat) {
                TXCLog.i("VideoJoinGenerate", "Video onEncodeFormat format:" + mediaFormat);
                if (com.tencent.liteav.g.p.this.m != null) {
                    com.tencent.liteav.g.p.this.m.a(mediaFormat);
                    if (com.tencent.liteav.g.p.this.m.d()) {
                        TXCLog.i("VideoJoinGenerate", "Has Audio, Video Muxer start");
                        com.tencent.liteav.g.p.this.m.a();
                        com.tencent.liteav.g.p.this.n = true;
                    }
                }
            }
            
            @Override
            public void onEncodeFinished(final int n, final long n2, final long n3) {
            }
            
            @Override
            public void onRestartEncoder(final int n) {
            }
            
            @Override
            public void onEncodeDataIn(final int n) {
            }
        };
        this.y = new com.tencent.liteav.g.d() {
            @Override
            public void a(final List<Surface> list) {
                TXCLog.i("VideoJoinGenerate", "IVideoJoinRenderListener onSurfaceTextureAvailable");
                if (com.tencent.liteav.g.p.this.f != null) {
                    com.tencent.liteav.g.p.this.f.a();
                    com.tencent.liteav.g.p.this.f.b();
                }
            }
            
            @Override
            public void a(final int n, final int n2) {
                if (com.tencent.liteav.g.p.this.f != null) {
                    final CaptureAndEnc.g g = new CaptureAndEnc.g();
                    final int e = CameraProxy.j.a().e();
                    if (e == 90 || e == 270) {
                        g.a = n2;
                        g.b = n;
                    }
                    else {
                        g.a = n;
                        g.b = n2;
                    }
                    com.tencent.liteav.g.p.this.f.a(g);
                }
            }
            
            @Override
            public void b(final List<Surface> list) {
                TXCLog.i("VideoJoinGenerate", "IVideoJoinRenderListener onSurfaceTextureDestroy");
                if (com.tencent.liteav.g.p.this.f != null) {
                    com.tencent.liteav.g.p.this.f.c();
                    com.tencent.liteav.g.p.this.f.d();
                }
            }
            
            @Override
            public int a(final int n, final float[] array, final e e) {
                if (com.tencent.liteav.g.p.this.f != null) {
                    com.tencent.liteav.g.p.this.f.a(array);
                    com.tencent.liteav.g.p.this.f.a(n, e);
                }
                return 0;
            }
        };
        this.z = new com.tencent.liteav.editer.n() {
            @Override
            public void a(final int n, final e e) {
                TXCLog.d("VideoJoinGenerate", "didProcessFrame frame:" + e.e());
                if (e.p() && com.tencent.liteav.g.p.this.k != null) {
                    com.tencent.liteav.g.p.this.k.c();
                    TXCLog.i("VideoJoinGenerate", "signalEOSAndFlush");
                    return;
                }
                if (com.tencent.liteav.g.p.this.k != null) {
                    com.tencent.liteav.g.p.this.k.c(n, e.m(), e.n(), e.e() / 1000L);
                }
                com.tencent.liteav.g.p.this.d.c();
            }
            
            @Override
            public int b(final int n, final e e) {
                return n;
            }
        };
        this.A = new Handler(Looper.getMainLooper());
        this.b = b;
        this.a = new LinkedBlockingQueue<e>();
        this.e = new n();
        this.d = new l();
        (this.f = new q(b)).a(this.z);
        this.g = com.tencent.liteav.g.t.a();
        this.h = com.tencent.liteav.g.s.r();
        this.c = com.tencent.liteav.basic.util.f.h();
    }
    
    public void a(final c.a j) {
        this.j = j;
    }
    
    public void a() {
        TXCLog.i("VideoJoinGenerate", "start");
        this.q = (this.o == 2);
        this.h.g();
        this.a.clear();
        this.h.k = this.g.o();
        (this.i = new b("join")).a();
        this.i.a(this.v);
        final MediaFormat i = this.g.i();
        if (i != null) {
            this.h.d(i);
            this.i.a(i);
        }
        final CaptureAndEnc.g a = this.h.a(this.g.j());
        this.h.h = a;
        this.f.a(a);
        (this.p = new j()).a(com.tencent.liteav.g.t.a().d());
        this.e.a(this.p);
        this.e.a(a);
        this.e.a(this.r);
        this.e.a(this.y);
        this.e.a();
        TXCLog.i("VideoJoinGenerate", "mUseSWEncoder:" + this.c);
        if (this.k == null) {
            this.k = new com.tencent.liteav.videoencoder.b(this.c ? 2 : 1);
        }
        (this.m = new com.tencent.liteav.muxer.c(this.b, this.c ? 0 : 2)).a(this.h.i);
    }
    
    public void b() {
        TXCLog.i("VideoJoinGenerate", "stop");
        if (this.d != null) {
            this.d.a((com.tencent.liteav.g.c)null);
            this.d.a((com.tencent.liteav.g.a)null);
            this.d.b();
        }
        if (this.e != null) {
            this.e.a((r)null);
            this.e.a((com.tencent.liteav.g.d)null);
            this.e.b();
            this.e.a(new Runnable() {
                @Override
                public void run() {
                    TXCLog.i("VideoJoinGenerate", "stop: runOnOpenGLThread -> release filter");
                    if (com.tencent.liteav.g.p.this.f != null) {
                        com.tencent.liteav.g.p.this.f.c();
                        com.tencent.liteav.g.p.this.f.d();
                    }
                }
            });
        }
        if (this.i != null) {
            this.i.d();
            this.i.a((com.tencent.liteav.editer.j)null);
            this.i.b();
            this.i = null;
        }
        if (this.k != null) {
            this.k.a((d)null);
            this.k.a();
        }
        if (this.l != null) {
            this.l.a((u)null);
            this.l.a((g)null);
            this.l.a();
        }
        TXCLog.i("VideoJoinGenerate", "stop muxer :" + this.n);
        this.n = false;
        if (this.m != null) {
            this.m.b();
            this.m = null;
        }
    }
    
    private void c() {
        com.tencent.liteav.editer.q.a().b();
        this.A.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.g.p.this.j != null) {
                    final com.tencent.liteav.i.a.d d = new com.tencent.liteav.i.a.d();
                    d.a = 0;
                    d.b = "Join Complete";
                    TXCLog.i("VideoJoinGenerate", "===onJoinComplete===");
                    com.tencent.liteav.g.p.this.j.a(d);
                }
            }
        });
    }
    
    private void a(final long n) {
        this.A.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.g.p.this.j != null) {
                    final long k = com.tencent.liteav.g.p.this.h.k;
                    if (k > 0L) {
                        final float n = n * 1.0f / k;
                        TXCLog.d("VideoJoinGenerate", "onJoinProgress timestamp:" + n + ",progress:" + n + ",duration:" + k);
                        com.tencent.liteav.g.p.this.j.a(n);
                    }
                }
            }
        });
    }
}
