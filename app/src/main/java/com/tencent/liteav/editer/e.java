package com.tencent.liteav.editer;

import android.content.*;
import android.view.*;

import com.tencent.liteav.CameraProxy;

import java.util.concurrent.*;

import android.opengl.*;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.videoencoder.*;
import com.tencent.liteav.basic.structs.*;
import java.nio.*;
import android.media.*;
import java.io.*;
import java.util.*;
import android.graphics.*;

public abstract class e
{
    private final String n = "BasicVideoGenerate";
    protected Context a;
    protected boolean b;
    protected d c;
    protected s d;
    protected ad e;
    private f o;
    protected k f;
    protected b g;
    private Surface p;
    protected a h;
    private com.tencent.liteav.editer.a q;
    protected c i;
    private boolean r;
    private LinkedBlockingQueue<CaptureAndEnc.e> s;
    protected CameraProxy.c j;
    protected j k;
    protected i l;
    private CaptureAndEnc.e t;
    private CaptureAndEnc.e u;
    private Object v;
    private boolean w;
    private int x;
    private boolean y;
    private r z;
    private m A;
    private h B;
    private l C;
    protected w m;
    private o D;
    private n E;
    private com.tencent.liteav.editer.j F;
    private com.tencent.liteav.videoencoder.d G;
    private u H;
    private g I;
    
    public e(final Context a, final String s) {
        this.b = false;
        this.r = false;
        this.v = new Object();
        this.x = 2;
        this.y = true;
        this.z = new r() {
            @Override
            public void a(final EGLContext glContext) {
                TXCLog.i("BasicVideoGenerate", "OnContextListener onContext");
                if (com.tencent.liteav.editer.e.this.p == null) {
                    return;
                }
                synchronized (com.tencent.liteav.editer.e.this.v) {
                    if (com.tencent.liteav.editer.e.this.h != null) {
                        final TXSVideoEncoderParam txsVideoEncoderParam = new TXSVideoEncoderParam();
                        txsVideoEncoderParam.width = com.tencent.liteav.editer.e.this.l.h.a;
                        txsVideoEncoderParam.height = com.tencent.liteav.editer.e.this.l.h.b;
                        txsVideoEncoderParam.fps = com.tencent.liteav.editer.e.this.l.j();
                        txsVideoEncoderParam.glContext = glContext;
                        txsVideoEncoderParam.enableEGL14 = true;
                        txsVideoEncoderParam.enableBlackList = false;
                        txsVideoEncoderParam.appendSpsPps = false;
                        txsVideoEncoderParam.annexb = true;
                        txsVideoEncoderParam.fullIFrame = com.tencent.liteav.editer.e.this.l.m;
                        txsVideoEncoderParam.gop = com.tencent.liteav.editer.e.this.l.k();
                        if (com.tencent.liteav.editer.e.this.b) {
                            txsVideoEncoderParam.encoderMode = 1;
                            txsVideoEncoderParam.encoderProfile = 3;
                            txsVideoEncoderParam.record = true;
                        }
                        else {
                            txsVideoEncoderParam.encoderMode = 3;
                            txsVideoEncoderParam.encoderProfile = 1;
                        }
                        txsVideoEncoderParam.forceSetBitrateMode = true;
                        com.tencent.liteav.editer.e.this.h.a(com.tencent.liteav.editer.e.this.l.i());
                        com.tencent.liteav.editer.e.this.h.a(com.tencent.liteav.editer.e.this.G);
                        com.tencent.liteav.editer.e.this.h.a(txsVideoEncoderParam);
                    }
                }
                if (com.tencent.liteav.editer.e.this.l.l()) {
                    com.tencent.liteav.editer.e.this.q = new a();
                    com.tencent.liteav.editer.e.this.q.a(com.tencent.liteav.editer.e.this.I);
                    com.tencent.liteav.editer.e.this.q.a(com.tencent.liteav.editer.e.this.H);
                    final t t = new t();
                    t.channelCount = com.tencent.liteav.editer.e.this.l.b;
                    t.sampleRate = com.tencent.liteav.editer.e.this.l.a;
                    t.maxInputSize = com.tencent.liteav.editer.e.this.l.c;
                    t.encoderType = com.tencent.liteav.editer.e.this.x;
                    t.audioBitrate = com.tencent.liteav.editer.e.this.l.h();
                    TXCLog.i("BasicVideoGenerate", "AudioEncoder.start");
                    com.tencent.liteav.editer.e.this.q.a(t);
                    if (com.tencent.liteav.editer.e.this.g != null) {
                        com.tencent.liteav.editer.e.this.g.e();
                    }
                }
                if (CameraProxy.k.a().d() == 1 && com.tencent.liteav.editer.e.this.c != null) {
                    com.tencent.liteav.editer.e.this.c.a(com.tencent.liteav.editer.e.this.p);
                    com.tencent.liteav.editer.e.this.c.a(com.tencent.liteav.editer.e.this.A);
                    com.tencent.liteav.editer.e.this.c.a(com.tencent.liteav.editer.e.this.B);
                    com.tencent.liteav.editer.e.this.c.l();
                }
                else if (CameraProxy.k.a().d() == 2 && com.tencent.liteav.editer.e.this.d != null) {
                    com.tencent.liteav.editer.e.this.d.a(com.tencent.liteav.editer.e.this.C);
                    com.tencent.liteav.editer.e.this.d.d();
                }
                com.tencent.liteav.j.b.h();
                com.tencent.liteav.j.e.a().b();
            }
            
            @Override
            public void b(final EGLContext eglContext) {
                TXCLog.i("BasicVideoGenerate", "OnContextListener onContextDestroy");
                if (com.tencent.liteav.editer.e.this.h != null) {
                    com.tencent.liteav.editer.e.this.h.b();
                }
            }
        };
        this.A = new m() {
            @Override
            public void a(final CaptureAndEnc.e e) {
                com.tencent.liteav.j.b.a();
                com.tencent.liteav.j.e.a().b(e.e());
                try {
                    e.this.s.put(e);
                }
                catch (InterruptedException ex) {}
                if (e.this.e != null) {
                    e.this.e.a(e);
                }
            }
            
            @Override
            public void a(final String s) {
                TXCLog.e("BasicVideoGenerate", "onDecoderError -> msg = " + s);
                com.tencent.liteav.editer.e.this.g();
            }
        };
        this.B = new h() {
            @Override
            public void a(final CaptureAndEnc.e e) {
                com.tencent.liteav.j.b.b();
                com.tencent.liteav.j.e.a().a(e.e());
                if (e.this.g != null) {
                    e.this.g.a(e);
                }
            }
        };
        this.C = new l() {
            @Override
            public void a(final CaptureAndEnc.e e) {
                TXCLog.d("BasicVideoGenerate", "mPicDecListener, onDecodeBitmapFrame  frame:" + e.e() + ", flag : " + e.f());
                try {
                    e.this.s.put(e);
                }
                catch (InterruptedException ex) {}
                if (e.this.e != null) {
                    e.this.e.b(e);
                }
            }
        };
        this.m = new w() {
            @Override
            public void a(final CaptureAndEnc.e e) {
                if (e.this.g != null) {
                    e.this.g.a(e);
                }
            }
            
            @Override
            public void b(final CaptureAndEnc.e e) {
                TXCLog.d("BasicVideoGenerate", "TailWaterMarkListener onDecodeVideoFrame  frame:" + e.e() + ", flag : " + e.f() + ", reverse time = " + e.u());
                try {
                    e.this.s.put(e);
                }
                catch (InterruptedException ex) {}
                if (e.this.e != null) {
                    e.this.e.a(e);
                }
            }
        };
        this.D = new o() {
            @Override
            public void a(final Surface surface) {
                TXCLog.i("BasicVideoGenerate", "IVideoRenderListener onSurfaceTextureAvailable");
                com.tencent.liteav.editer.e.this.p = surface;
                if (com.tencent.liteav.editer.e.this.f != null) {
                    com.tencent.liteav.editer.e.this.f.a();
                    com.tencent.liteav.editer.e.this.f.b();
                }
            }
            
            @Override
            public void a(final int n, final int n2) {
                if (com.tencent.liteav.editer.e.this.f != null) {
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
                    com.tencent.liteav.editer.e.this.f.a(g);
                }
            }
            
            @Override
            public void b(final Surface surface) {
                TXCLog.i("BasicVideoGenerate", "IVideoRenderListener onSurfaceTextureDestroy");
                com.tencent.liteav.editer.e.this.f();
                com.tencent.liteav.editer.e.this.p = null;
                if (com.tencent.liteav.editer.e.this.f != null) {
                    com.tencent.liteav.editer.e.this.f.c();
                    com.tencent.liteav.editer.e.this.f.d();
                }
                if (com.tencent.liteav.editer.e.this.o != null) {
                    com.tencent.liteav.editer.e.this.o.a();
                }
            }
            
            @Override
            public int a(int a, final float[] array, final CaptureAndEnc.e e) {
                com.tencent.liteav.j.b.e();
                if (e.this.o != null) {
                    a = e.this.o.a(e, CameraProxy.e.a().b(), e.r());
                    e.l(a);
                    e.m(0);
                }
                if (e.this.f != null) {
                    e.this.f.a(array);
                    e.this.f.a(a, e);
                }
                return 0;
            }
            
            @Override
            public void a(final int n) {
            }
        };
        this.E = new n() {
            @Override
            public void a(final int n, final CaptureAndEnc.e e) {
                com.tencent.liteav.j.b.c();
                final long a = com.tencent.liteav.j.e.a(e);
                com.tencent.liteav.j.e.a().d(a);
                if (e.p()) {
                    if (!e.this.k.b()) {
                        if (e.this.l.l()) {
                            final boolean b = CameraProxy.k.a().d() == 2;
                            final boolean b2 = CameraProxy.k.a().d() == 1;
                            if (b || (b2 && !e.this.c.h())) {
                                final boolean j = e.this.g.j();
                                if (e.this.g == null || !j) {
                                    TXCLog.w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,BGM is not end");
                                    return;
                                }
                            }
                        }
                        synchronized (e.this.v) {
                            if (e.this.h != null) {
                                e.this.h.a();
                                TXCLog.i("BasicVideoGenerate", "signalEOSAndFlush");
                                return;
                            }
                        }
                    }
                    else if (e.this.k.j()) {
                        synchronized (e.this.v) {
                            if (e.this.h != null) {
                                e.this.h.a();
                                TXCLog.i("BasicVideoGenerate", "TailWaterMarkListener signalEOSAndFlush");
                                return;
                            }
                        }
                    }
                    else {
                        if (e.this.s != null) {
                            e.this.s.remove(e);
                        }
                        if (e.this.l.l() && (CameraProxy.k.a().d() == 2 || (CameraProxy.k.a().d() == 1 && !e.this.c.h())) && (e.this.g == null || !e.this.g.j())) {
                            TXCLog.w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,BGM is not end");
                            return;
                        }
                        TXCLog.i("BasicVideoGenerate", "Encount EOF Video didProcessFrame appendTailWaterMark, mLastVideoFrame = " + e.this.u);
                        e.this.k.a = e.this.u;
                        e.this.k.b = e.this.t;
                        e.this.k.d();
                        TXCLog.i("BasicVideoGenerate", "mLastVideoFrame width, height = " + e.this.u.m() + ", " + e.this.u.n());
                        return;
                    }
                }
                synchronized (e.this.v) {
                    if (e.this.h != null) {
                        try {
                            e.this.h.a(n, e.m(), e.n(), a / 1000L);
                        }
                        catch (InterruptedException ex) {}
                    }
                }
                if (e.this.l.e()) {
                    try {
                        e.this.s.take();
                    }
                    catch (InterruptedException ex2) {}
                    if (CameraProxy.k.a().d() == 1) {
                        e.this.c.p();
                    }
                }
                else if (CameraProxy.k.a().d() == 1) {
                    if (e.this.c != null) {
                        if (e.this.c.o()) {
                            if (e.this.k.b()) {
                                e.this.k.f();
                            }
                        }
                        else {
                            e.this.c.p();
                        }
                    }
                }
                else if (CameraProxy.k.a().d() == 2) {
                    if (e.this.d.c()) {
                        if (e.this.k.b()) {
                            e.this.k.f();
                        }
                    }
                    else {
                        e.this.d.h();
                    }
                }
                e.this.u = e;
            }
            
            @Override
            public int b(final int n, final CaptureAndEnc.e e) {
                return e.this.a(n, e.m(), e.n(), e.e());
            }
        };
        this.F = new j() {
            @Override
            public void a(final CaptureAndEnc.e e) {
                if (e == null) {
                    return;
                }
                com.tencent.liteav.j.b.d();
                com.tencent.liteav.j.e.a().c(e.e());
                if (e.p()) {
                    if (!e.this.k.b()) {
                        final boolean b = CameraProxy.k.a().d() == 2;
                        final boolean b2 = CameraProxy.k.a().d() == 1;
                        if (b || (b2 && !e.this.c.h())) {
                            final boolean b3 = e.this.d != null && e.this.d.c();
                            final boolean b4 = e.this.c != null && e.this.c.o();
                            if (b) {
                                TXCLog.i("BasicVideoGenerate", "IAudioPreprocessListener sourcePic\uff1a" + b + ",picEnd:" + b3);
                            }
                            if (b2) {
                                TXCLog.i("BasicVideoGenerate", "IAudioPreprocessListener sourceVideo\uff1a" + b2 + ",videoEnd:" + b4);
                            }
                            if (b3 || b4) {
                                synchronized (e.this.v) {
                                    if (e.this.h != null) {
                                        e.this.h.a();
                                        TXCLog.i("BasicVideoGenerate", "signalEOSAndFlush");
                                        return;
                                    }
                                }
                            }
                            if (!b3) {
                                TXCLog.i("BasicVideoGenerate", "picEnd is false");
                                return;
                            }
                        }
                    }
                    else if (e.this.k.j()) {
                        synchronized (e.this.v) {
                            if (e.this.h != null) {
                                e.this.h.a();
                                TXCLog.i("BasicVideoGenerate", "Encount EOF TailWaterMarkListener signalEOSAndFlush");
                                return;
                            }
                        }
                    }
                    else {
                        if (e.this.l.l() && ((CameraProxy.k.a().d() == 2 && !e.this.d.c()) || (CameraProxy.k.a().d() == 1 && !e.this.c.h() && !e.this.c.o()))) {
                            TXCLog.w("BasicVideoGenerate", "Encount EOF Video Has No Audio Append BGM,Video is not end");
                            return;
                        }
                        TXCLog.i("BasicVideoGenerate", "Encount EOF Audio didProcessFrame appendTailWaterMark");
                        e.this.k.a = e.this.u;
                        e.this.k.b = e.this.t;
                        e.this.k.d();
                        return;
                    }
                }
                if (e.this.q != null && e != null) {
                    e.this.q.a(e);
                }
                if (e.this.g != null) {
                    e.this.g.i();
                }
                e.this.t = e;
            }
        };
        this.G = new com.tencent.liteav.videoencoder.d() {
            @Override
            public void onEncodeNAL(final TXSNALPacket txsnalPacket, final int n) {
                if (n != 0) {
                    TXCLog.e("BasicVideoGenerate", "mVideoEncodeListener, errCode = " + n);
                    return;
                }
                com.tencent.liteav.j.b.f();
                if (com.tencent.liteav.editer.e.this.l.e()) {
                    TXCLog.i("BasicVideoGenerate", "mVideoEncodeListener, input is full, output is full");
                    return;
                }
                if (txsnalPacket == null || txsnalPacket.nalData == null) {
                    TXCLog.i("BasicVideoGenerate", "===Video onEncodeComplete===");
                    com.tencent.liteav.editer.e.this.c();
                    com.tencent.liteav.editer.e.this.e();
                    return;
                }
                CaptureAndEnc.e e = null;
                try {
                    e = com.tencent.liteav.editer.e.this.s.take();
                }
                catch (InterruptedException ex) {}
                if (e.p()) {
                    TXCLog.i("BasicVideoGenerate", "===Video onEncodeComplete===:" + e.p() + ", nal:" + txsnalPacket);
                    com.tencent.liteav.editer.e.this.c();
                    com.tencent.liteav.editer.e.this.e();
                    return;
                }
                synchronized (this) {
                    if (com.tencent.liteav.editer.e.this.i != null && txsnalPacket != null && txsnalPacket.nalData != null) {
                        if (com.tencent.liteav.editer.e.this.r) {
                            this.a(txsnalPacket, e);
                        }
                        else if (txsnalPacket.nalType == 0) {
                            final MediaFormat a = com.tencent.liteav.basic.util.f.a(txsnalPacket.nalData, com.tencent.liteav.editer.e.this.l.h.a, com.tencent.liteav.editer.e.this.l.h.b);
                            if (a != null) {
                                com.tencent.liteav.editer.e.this.i.a(a);
                                com.tencent.liteav.editer.e.this.i.a();
                                com.tencent.liteav.editer.e.this.r = true;
                            }
                            this.a(txsnalPacket, e);
                        }
                    }
                }
                com.tencent.liteav.editer.e.this.a(com.tencent.liteav.j.e.a(e));
            }
            
            private void a(final TXSNALPacket txsnalPacket, final CaptureAndEnc.e e) {
                final long a = com.tencent.liteav.j.e.a(e);
                com.tencent.liteav.j.e.a().f(a);
                int flags = 0;
                if (txsnalPacket.info == null) {
                    if (txsnalPacket.nalType == 0) {
                        flags = 1;
                    }
                }
                else {
                    flags = txsnalPacket.info.flags;
                }
                if (e.this.i != null) {
                    e.this.i.b(txsnalPacket.nalData, 0, txsnalPacket.nalData.length, a, flags);
                }
            }
            
            @Override
            public void onEncodeFormat(final MediaFormat mediaFormat) {
                TXCLog.i("BasicVideoGenerate", "Video onEncodeFormat format:" + mediaFormat);
                if (com.tencent.liteav.editer.e.this.l.e()) {
                    return;
                }
                if (com.tencent.liteav.editer.e.this.i != null) {
                    com.tencent.liteav.editer.e.this.i.a(mediaFormat);
                    if (com.tencent.liteav.editer.e.this.l.l()) {
                        if (com.tencent.liteav.editer.e.this.i.d()) {
                            TXCLog.i("BasicVideoGenerate", "Has Audio, Video Muxer start");
                            com.tencent.liteav.editer.e.this.i.a();
                            com.tencent.liteav.editer.e.this.r = true;
                        }
                    }
                    else {
                        TXCLog.i("BasicVideoGenerate", "No Audio, Video Muxer start");
                        com.tencent.liteav.editer.e.this.i.a();
                        com.tencent.liteav.editer.e.this.r = true;
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
        this.H = new u() {
            @Override
            public void a(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
                com.tencent.liteav.j.b.g();
                com.tencent.liteav.j.e.a().e(bufferInfo.presentationTimeUs);
                if (com.tencent.liteav.editer.e.this.x == 2 && com.tencent.liteav.editer.e.this.y) {
                    com.tencent.liteav.editer.e.this.y = false;
                    final MediaFormat a = com.tencent.liteav.basic.util.f.a(com.tencent.liteav.editer.e.this.l.a, com.tencent.liteav.editer.e.this.l.b, 2);
                    if (com.tencent.liteav.editer.e.this.i != null) {
                        com.tencent.liteav.editer.e.this.i.b(a);
                    }
                }
                if (com.tencent.liteav.editer.e.this.i != null) {
                    com.tencent.liteav.editer.e.this.i.a(byteBuffer, bufferInfo);
                }
            }
            
            @Override
            public void a(final MediaFormat mediaFormat) {
                TXCLog.i("BasicVideoGenerate", "Audio onEncodeFormat format:" + mediaFormat);
                if (com.tencent.liteav.editer.e.this.l.e()) {
                    return;
                }
                if (com.tencent.liteav.editer.e.this.i != null) {
                    com.tencent.liteav.editer.e.this.i.b(mediaFormat);
                    if ((CameraProxy.k.a().d() == 2 || com.tencent.liteav.editer.e.this.c.i()) && com.tencent.liteav.editer.e.this.i.c()) {
                        com.tencent.liteav.editer.e.this.i.a();
                        com.tencent.liteav.editer.e.this.r = true;
                    }
                }
            }
            
            @Override
            public void a() {
                TXCLog.i("BasicVideoGenerate", "===Audio onEncodeComplete===");
                com.tencent.liteav.editer.e.this.c();
                com.tencent.liteav.editer.e.this.e();
            }
        };
        this.I = new g() {
            @Override
            public void a(final int n) {
                if (CameraProxy.k.a().d() == 1 && com.tencent.liteav.editer.e.this.c.h()) {
                    com.tencent.liteav.editer.e.this.c.b(n <= 5);
                }
                else if (com.tencent.liteav.editer.e.this.g != null) {
                    com.tencent.liteav.editer.e.this.g.c(n <= 5);
                }
            }
        };
        this.a = a;
        this.e = new ad(s);
        (this.f = new k(a)).a(this.E);
        this.s = new LinkedBlockingQueue<CaptureAndEnc.e>();
        this.l = CameraProxy.i.a();
        this.j = CameraProxy.c.a();
        this.k = com.tencent.liteav.f.j.a();
        this.b = com.tencent.liteav.basic.util.f.h();
    }
    
    public void a(final String s) {
        TXCLog.i("BasicVideoGenerate", "setVideoPath videoPath:" + s);
        try {
            if (this.c == null) {
                this.c = new y("basic");
            }
            this.c.a(s);
            if (this.c.h()) {
                this.l.a(this.c.f());
            }
            this.l.b(this.c.g());
        }
        catch (IOException ex) {
            TXCLog.e("BasicVideoGenerate", "set source failed.", ex);
        }
    }
    
    public void a(final List<Bitmap> list, final int n) {
        (this.d = new s("gene")).a(false);
        this.d.a(list, n);
        this.o = new f(this.a, this.d.a(), this.d.b());
    }
    
    public void a() {
        TXCLog.i("BasicVideoGenerate", "start");
        this.s.clear();
        this.y = (this.x == 2);
        this.h();
        if (this.l.l()) {
            (this.g = new b("basicGene")).a();
            this.g.a(this.F);
            this.g.b(this.l.l);
            if (CameraProxy.k.a().d() == 1) {
                this.g.b(this.c.h());
            }
            else {
                this.g.b(false);
            }
            this.g.c();
            final MediaFormat n = this.l.n();
            if (n != null) {
                this.g.a(n);
            }
            if ((CameraProxy.k.a().d() == 2 || !this.c.h()) && this.i != null) {
                this.i.b(n);
            }
        }
        if (this.f != null) {
            this.f.a(this.l.h);
        }
        if (this.e != null) {
            this.e.a(this.l.h);
            this.e.a(this.z);
            this.e.a(this.D);
            this.e.a();
        }
    }
    
    protected void b() {
        final CaptureAndEnc.g h = new CaptureAndEnc.g();
        if (CameraProxy.k.a().d() == 1) {
            h.a = this.c.d();
            h.b = this.c.e();
            h.c = this.c.n();
        }
        else if (CameraProxy.k.a().d() == 2) {
            h.a = this.d.a();
            h.b = this.d.b();
        }
        if (this.w) {
            this.l.h = h;
        }
        else {
            this.l.h = this.l.a(h);
        }
    }
    
    public void c() {
        TXCLog.i("BasicVideoGenerate", "stop");
        if (this.c != null) {
            this.c.a((m)null);
            this.c.a((h)null);
            this.c.m();
        }
        if (this.d != null) {
            this.d.a((l)null);
            this.d.e();
        }
        if (this.e != null) {
            this.e.b();
        }
        if (this.l.l() && this.g != null) {
            this.g.d();
            this.g.a((com.tencent.liteav.editer.j)null);
            this.g.b();
            this.g = null;
        }
        if (this.h != null) {
            this.h.a((com.tencent.liteav.videoencoder.d)null);
            this.h.c();
        }
        if (this.q != null) {
            this.q.a((u)null);
            this.q.a((g)null);
            this.q.a();
        }
        TXCLog.i("BasicVideoGenerate", "stop muxer :" + this.r);
        this.r = false;
        if (this.i != null) {
            this.i.b();
            this.i = null;
        }
    }
    
    public void d() {
        TXCLog.i("BasicVideoGenerate", "release");
        if (this.c != null) {
            this.c.k();
        }
        this.c = null;
        if (this.d != null) {
            this.d.i();
        }
        this.d = null;
        if (this.e != null) {
            this.e.c();
        }
        this.e = null;
        if (this.f != null) {
            this.f.a((n)null);
        }
        this.f = null;
        synchronized (this.v) {
            this.h = null;
        }
        if (this.q != null) {
            this.q.b();
        }
        this.q = null;
        this.H = null;
        this.F = null;
        this.H = null;
        this.z = null;
        this.A = null;
        this.B = null;
        this.I = null;
        this.C = null;
        this.m = null;
        this.E = null;
        this.G = null;
        this.D = null;
    }
    
    private void h() {
        long c = 0L;
        if (CameraProxy.k.a().d() == 1) {
            c = this.c.c();
        }
        else if (CameraProxy.k.a().d() == 2) {
            c = this.d.a(CameraProxy.e.a().b()) * 1000L;
        }
        TXCLog.i("BasicVideoGenerate", "calculateDuration durationUs:" + c);
        final long b = this.j.b();
        final long c2 = this.j.c();
        if (c2 - b > 0L) {
            c = c2 - b;
            TXCLog.i("BasicVideoGenerate", "calculateDuration Cut durationUs:" + c);
            if (CameraProxy.k.a().d() == 1) {
                this.c.a(b, c2);
            }
            else if (CameraProxy.k.a().d() == 2) {
                this.d.a(b / 1000L, c2 / 1000L);
            }
        }
        else if (CameraProxy.k.a().d() == 1) {
            this.c.a(0L, c);
        }
        else if (CameraProxy.k.a().d() == 2) {
            this.d.a(0L, c / 1000L);
        }
        this.l.k = c;
        this.l.l = c;
        if (com.tencent.liteav.f.g.a().c()) {
            this.l.k = com.tencent.liteav.f.g.a().b(this.l.k);
            this.l.l = this.l.k;
            TXCLog.i("BasicVideoGenerate", "calculateDuration Speed durationUs:" + this.l.k);
        }
        if (this.k.b()) {
            this.l.k += this.k.c();
            TXCLog.i("BasicVideoGenerate", "calculateDuration AddTailWaterMark durationUs:" + this.l.k);
        }
    }
    
    protected abstract void a(final long p0);
    
    protected abstract void e();
    
    protected abstract int a(final int p0, final int p1, final int p2, final long p3);
    
    protected abstract void f();
    
    protected abstract void g();
    
    public void a(final boolean b) {
        if (this.f != null) {
            this.f.a(b);
        }
    }
    
    public void b(final boolean w) {
        this.w = w;
    }
}
