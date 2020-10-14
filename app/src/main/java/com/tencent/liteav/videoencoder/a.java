package com.tencent.liteav.videoencoder;

import android.view.*;
import com.tencent.liteav.basic.module.*;
import com.tencent.liteav.basic.log.*;
import android.annotation.*;
import android.opengl.*;
import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.structs.*;
import java.util.*;
import java.nio.*;
import android.media.*;
import org.json.*;
import android.os.*;

public class a extends c
{
    private int a;
    private long b;
    private double c;
    private long d;
    private long e;
    private int f;
    private boolean g;
    private boolean h;
    private long i;
    private long j;
    private long k;
    private long l;
    private long m;
    private boolean n;
    private long o;
    private long p;
    private MediaCodec q;
    private g r;
    private Runnable s;
    private Runnable t;
    private Runnable u;
    private ArrayDeque<Long> v;
    private Object w;
    private Surface x;
    private boolean y;
    private boolean z;
    private boolean A;
    private ByteBuffer[] B;
    private byte[] C;
    private volatile long D;
    private long E;
    private long F;
    private int G;
    private int H;
    private int I;
    private int J;
    private int K;
    private boolean L;
    private boolean M;
    private boolean N;
    private int O;
    private int P;
    private int Q;
    private long R;
    private int S;
    private int T;
    private int U;
    private boolean V;
    private ArrayList<Long> W;
    private int X;
    private long Y;
    private int Z;
    private int aa;
    private boolean ab;
    private boolean ac;
    private long ad;
    private Runnable ae;
    
    public a() {
        this.a = 0;
        this.b = 0L;
        this.c = 0.0;
        this.d = 0L;
        this.e = 0L;
        this.f = 0;
        this.g = false;
        this.h = true;
        this.i = 0L;
        this.j = 0L;
        this.k = 0L;
        this.l = 0L;
        this.m = 0L;
        this.o = 0L;
        this.p = 0L;
        this.q = null;
        this.r = null;
        this.s = new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.a.this.e();
            }
        };
        this.t = new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.a.this.b(10);
            }
        };
        this.u = new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.a.this.b(1);
            }
        };
        this.v = new ArrayDeque<Long>(10);
        this.x = null;
        this.y = true;
        this.z = true;
        this.A = false;
        this.B = null;
        this.C = null;
        this.D = 0L;
        this.E = 0L;
        this.F = 0L;
        this.M = true;
        this.N = false;
        this.O = 0;
        this.P = 0;
        this.Q = 0;
        this.R = 0L;
        this.S = 0;
        this.T = 0;
        this.U = -1;
        this.V = false;
        this.X = 0;
        this.Y = 0L;
        this.Z = 3;
        this.aa = 0;
        this.ab = false;
        this.ac = true;
        this.ad = 0L;
        this.ae = new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.a.this.ad = System.currentTimeMillis();
                com.tencent.liteav.videoencoder.a.this.b();
                com.tencent.liteav.videoencoder.a.this.d();
                com.tencent.liteav.videoencoder.a.this.c();
            }
        };
        this.r = new g("HWVideoEncoder");
    }
    
    @Override
    public int start(final TXSVideoEncoderParam txsVideoEncoderParam) {
        super.start(txsVideoEncoderParam);
        final boolean[] array = { false };
        if (Build.VERSION.SDK_INT < 18) {
            array[0] = false;
        }
        else {
            synchronized (this) {
                this.r.a(new Runnable() {
                    @Override
                    public void run() {
                        String s = "unknown";
                        String s2 = "unknown";
                        switch (txsVideoEncoderParam.encoderMode) {
                            case 1: {
                                s = "CBR";
                                break;
                            }
                            case 2: {
                                s = "VBR";
                                break;
                            }
                            case 3: {
                                s = "CQ";
                                break;
                            }
                        }
                        switch (txsVideoEncoderParam.encoderProfile) {
                            case 1: {
                                s2 = "Baseline";
                                break;
                            }
                            case 2: {
                                s2 = "Main";
                                break;
                            }
                            case 3: {
                                s2 = "High";
                                break;
                            }
                        }
                        final String format = String.format("VideoEncoder[%d]: Start [type:hardware][resolution:%d*%d][fps:%d][bitrate:%dkbps][gop:%d][rateControl:%s][profile:%s][rps:%s][streamType:%d]", com.tencent.liteav.videoencoder.a.this.hashCode(), txsVideoEncoderParam.width, txsVideoEncoderParam.height, txsVideoEncoderParam.fps, txsVideoEncoderParam.bitrate, txsVideoEncoderParam.gop, s, s2, txsVideoEncoderParam.bMultiRef ? "true" : "false", txsVideoEncoderParam.streamType);
                        Monitor.a(2, format, "", 0);
                        TXCLog.i("TXCHWVideoEncoder", "start:" + format);
                        if (com.tencent.liteav.videoencoder.a.this.mInit) {
                            com.tencent.liteav.videoencoder.a.this.e();
                        }
                        array[0] = com.tencent.liteav.videoencoder.a.this.a(txsVideoEncoderParam);
                    }
                });
            }
        }
        if (array[0]) {
            Monitor.a(2, String.format("VideoEncoder[%d]: Start successfully, streamType:%d", this.hashCode(), txsVideoEncoderParam.streamType), "streamType: 2-big, 3-small, 7-sub", 0);
        }
        if (!array[0]) {
            this.callDelegate(10000004);
        }
        return array[0] ? 0 : 10000004;
    }
    
    @Override
    public void stop() {
        this.z = true;
        synchronized (this) {
            this.r.a(new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.videoencoder.a.this.mInit) {
                        Monitor.a(2, String.format("VideoEncoder[%d]: Stop, streamType:%d", com.tencent.liteav.videoencoder.a.this.hashCode(), com.tencent.liteav.videoencoder.a.this.mStreamType), "streamType: 2-big, 3-small, 7-sub", 0);
                        com.tencent.liteav.videoencoder.a.this.e();
                    }
                }
            });
        }
    }
    
    @Override
    public void setFPS(final int n) {
        this.r.b(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.a.this.d(n);
            }
        });
    }
    
    @Override
    public void setBitrate(final int a) {
        this.a = a;
        this.r.b(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.a.this.c(a);
            }
        });
    }
    
    @Override
    public void setBitrateFromQos(final int a, final int n) {
        this.a = a;
        this.r.b(new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoencoder.a.this.c(a);
            }
        });
    }
    
    @Override
    public void setEncodeIdrFpsFromQos(final int n) {
    }
    
    @Override
    public double getRealFPS() {
        return this.c;
    }
    
    @Override
    public long getRealBitrate() {
        return this.b;
    }
    
    @Override
    public int getEncodeCost() {
        return this.X;
    }
    
    @Override
    public long pushVideoFrame(final int u, final int mInputWidth, final int mInputHeight, final long d) {
        if (this.z) {
            return 10000004L;
        }
        GLES20.glFinish();
        ++this.Q;
        this.D = d;
        this.U = u;
        this.mInputWidth = mInputWidth;
        this.mInputHeight = mInputHeight;
        if (this.L) {
            this.f();
        }
        if (!this.M || this.V) {
            ++this.T;
            this.r.b(this.t);
            this.V = false;
        }
        if (this.O > this.P + 30) {
            TXCLog.e("TXCHWVideoEncoder", String.format("hw encoder error when render[%d] pop[%d]", this.O, this.P));
            if (this.mListener != null) {
                this.mListener.onRestartEncoder(this.mStreamType);
            }
        }
        if (this.R + 5000L < System.currentTimeMillis()) {
            this.R = System.currentTimeMillis();
            if (this.S != 0 && this.S == this.O) {
                TXCLog.i("TXCHWVideoEncoder", String.format("hw encoder error when push[%d] render task[%d] render[%d] pop[%d]", this.Q, this.T, this.O, this.P));
                if (this.mListener != null) {
                    this.mListener.onRestartEncoder(this.mStreamType);
                }
            }
            this.S = this.O;
        }
        return 0L;
    }
    
    @Override
    public long pushVideoFrameAsync(final int n, final int n2, final int n3, final long n4) {
        if (this.z) {
            return 10000004L;
        }
        GLES20.glFinish();
        if (this.L) {
            this.f();
        }
        this.r.a().post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.videoencoder.a.this.y || com.tencent.liteav.videoencoder.a.this.w == null) {
                    return;
                }
                final int a = n;
                final long b = n4;
                com.tencent.liteav.videoencoder.a.this.a(b);
                final int n = (720 - com.tencent.liteav.videoencoder.a.this.mRotation) % 360;
                com.tencent.liteav.videoencoder.a.this.mEncodeFilter.a(com.tencent.liteav.videoencoder.a.this.mInputWidth, com.tencent.liteav.videoencoder.a.this.mInputHeight, n, null, ((n == 90 || n == 270) ? com.tencent.liteav.videoencoder.a.this.mOutputHeight : com.tencent.liteav.videoencoder.a.this.mOutputWidth) / (float)((n == 90 || n == 270) ? com.tencent.liteav.videoencoder.a.this.mOutputWidth : com.tencent.liteav.videoencoder.a.this.mOutputHeight), com.tencent.liteav.videoencoder.a.this.mEnableXMirror, true);
                com.tencent.liteav.videoencoder.a.this.mEncodeFilter.b(a);
                if (com.tencent.liteav.videoencoder.a.this.w instanceof com.tencent.liteav.basic.c.c) {
                    ((com.tencent.liteav.basic.c.c)com.tencent.liteav.videoencoder.a.this.w).a(b * 1000000L);
                    ((com.tencent.liteav.basic.c.c)com.tencent.liteav.videoencoder.a.this.w).d();
                }
                if (com.tencent.liteav.videoencoder.a.this.w instanceof b) {
                    ((b)com.tencent.liteav.videoencoder.a.this.w).a();
                }
                if (com.tencent.liteav.videoencoder.a.this.mListener != null) {
                    com.tencent.liteav.videoencoder.a.this.mListener.onEncodeDataIn(com.tencent.liteav.videoencoder.a.this.mStreamType);
                }
                int c;
                while ((c = com.tencent.liteav.videoencoder.a.this.a(1)) > 0) {}
                if (c == -1 || c == -2) {
                    if (c == -1) {
                        com.tencent.liteav.videoencoder.a.this.callDelegate(10000005);
                    }
                    com.tencent.liteav.videoencoder.a.this.y = true;
                    com.tencent.liteav.videoencoder.a.this.e();
                    return;
                }
                com.tencent.liteav.videoencoder.a.this.O++;
            }
        });
        return 0L;
    }
    
    @Override
    public long pushVideoFrameSync(final int u, final int n, final int n2, final long d) {
        if (this.z) {
            return 10000004L;
        }
        GLES20.glFinish();
        this.D = d;
        this.U = u;
        if (this.L) {
            this.f();
        }
        this.r.a(this.u);
        return 0L;
    }
    
    @Override
    public void signalEOSAndFlush() {
        if (this.z) {
            return;
        }
        this.r.a(new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.videoencoder.a.this.q == null) {
                    return;
                }
                try {
                    com.tencent.liteav.videoencoder.a.this.q.signalEndOfInputStream();
                }
                catch (Exception ex) {
                    TXCLog.e("TXCHWVideoEncoder", "signalEndOfInputStream failed.", ex);
                }
                while (com.tencent.liteav.videoencoder.a.this.a(10) >= 0) {}
                com.tencent.liteav.videoencoder.a.this.e();
            }
        });
    }
    
    @TargetApi(16)
    private MediaFormat a(final int n, final int n2, final int n3, final int n4, final int n5) {
        if (n == 0 || n2 == 0 || n3 == 0 || n4 == 0) {
            return null;
        }
        final MediaFormat videoFormat = MediaFormat.createVideoFormat("video/avc", n, n2);
        videoFormat.setInteger("bitrate", n3 * 1024);
        videoFormat.setInteger("frame-rate", n4);
        videoFormat.setInteger("color-format", 2130708361);
        videoFormat.setInteger("i-frame-interval", n5);
        return videoFormat;
    }
    
    @TargetApi(16)
    private MediaFormat a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        final MediaFormat a = this.a(n, n2, n3, n4, n5);
        if (a == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            final MediaCodecInfo a2 = a("video/avc");
            if (a2 == null) {
                return a;
            }
            final MediaCodecInfo.CodecCapabilities capabilitiesForType = a2.getCapabilitiesForType("video/avc");
            final MediaCodecInfo.EncoderCapabilities encoderCapabilities = capabilitiesForType.getEncoderCapabilities();
            if (b) {
                a.setInteger("bitrate-mode", n6);
            }
            else if (encoderCapabilities.isBitrateModeSupported(n6)) {
                a.setInteger("bitrate-mode", n6);
            }
            else if (this.L) {
                if (encoderCapabilities.isBitrateModeSupported(1)) {
                    a.setInteger("bitrate-mode", 1);
                }
                else if (encoderCapabilities.isBitrateModeSupported(2)) {
                    a.setInteger("bitrate-mode", 2);
                }
            }
            else if (encoderCapabilities.isBitrateModeSupported(2)) {
                a.setInteger("bitrate-mode", 2);
            }
            a.setInteger("complexity", (int)encoderCapabilities.getComplexityRange().clamp((Comparable)5));
            int profile = 0;
            if (Build.VERSION.SDK_INT >= 23) {
                for (final MediaCodecInfo.CodecProfileLevel codecProfileLevel : capabilitiesForType.profileLevels) {
                    if (codecProfileLevel.profile <= n7 && codecProfileLevel.profile > profile) {
                        profile = codecProfileLevel.profile;
                        a.setInteger("profile", codecProfileLevel.profile);
                        a.setInteger("level", codecProfileLevel.level);
                    }
                }
            }
        }
        return a;
    }
    
    @TargetApi(16)
    private static MediaCodecInfo a(final String s) {
        for (int codecCount = MediaCodecList.getCodecCount(), i = 0; i < codecCount; ++i) {
            final MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (codecInfo.isEncoder()) {
                final String[] supportedTypes = codecInfo.getSupportedTypes();
                for (int length = supportedTypes.length, j = 0; j < length; ++j) {
                    if (supportedTypes[j].equalsIgnoreCase(s)) {
                        return codecInfo;
                    }
                }
            }
        }
        return null;
    }
    
    private void a(final long n) {
        this.v.add(n);
    }
    
    private long a() {
        final Long n = this.v.poll();
        return (n == null) ? 0L : n;
    }
    
    private boolean a(final Surface surface, final int n, final int n2) {
        if (surface == null) {
            return false;
        }
        TXCLog.i("TXCHWVideoEncoder", "HWVideoEncode createGL: " + this.mGLContextExternal);
        if (this.mGLContextExternal != null && this.mGLContextExternal instanceof EGLContext) {
            this.w = com.tencent.liteav.basic.c.c.a(null, (EGLContext)this.mGLContextExternal, surface, n, n2);
        }
        else {
            this.w = com.tencent.liteav.basic.c.b.a(null, (javax.microedition.khronos.egl.EGLContext)this.mGLContextExternal, surface, n, n2);
        }
        if (this.w == null) {
            return false;
        }
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        (this.mEncodeFilter = new h()).a(com.tencent.liteav.basic.c.l.e, com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, false, false));
        if (!this.mEncodeFilter.c()) {
            this.mEncodeFilter = null;
            return false;
        }
        GLES20.glViewport(0, 0, n, n2);
        return true;
    }
    
    private void b() {
        TXCLog.i("TXCHWVideoEncoder", "HWVideoEncode destroyGL");
        if (this.mEncodeFilter != null) {
            this.mEncodeFilter.e();
            this.mEncodeFilter = null;
        }
        if (this.w instanceof b) {
            ((b)this.w).c();
            this.w = null;
        }
        if (this.w instanceof com.tencent.liteav.basic.c.c) {
            ((com.tencent.liteav.basic.c.c)this.w).c();
            this.w = null;
        }
    }
    
    @TargetApi(18)
    private boolean a(final TXSVideoEncoderParam txsVideoEncoderParam) {
        this.z = false;
        this.y = false;
        this.b = 0L;
        this.c = 0.0;
        this.d = 0L;
        this.e = 0L;
        this.f = 0;
        this.i = 0L;
        this.j = txsVideoEncoderParam.baseGopIndex;
        this.k = 0L;
        this.l = txsVideoEncoderParam.baseFrameIndex;
        this.m = 0L;
        this.o = 0L;
        this.p = 0L;
        this.B = null;
        this.C = null;
        this.D = 0L;
        this.G = -1;
        this.mOutputWidth = txsVideoEncoderParam.width;
        this.mOutputHeight = txsVideoEncoderParam.height;
        this.H = txsVideoEncoderParam.gop;
        this.I = txsVideoEncoderParam.fps;
        TXCLog.i("TXCHWVideoEncoder", "init with fps " + this.I);
        this.L = txsVideoEncoderParam.fullIFrame;
        this.n = txsVideoEncoderParam.syncOutput;
        this.A = txsVideoEncoderParam.enableEGL14;
        this.N = txsVideoEncoderParam.forceSetBitrateMode;
        this.v.clear();
        this.M = txsVideoEncoderParam.bLimitFps;
        if (txsVideoEncoderParam == null || txsVideoEncoderParam.width == 0 || txsVideoEncoderParam.height == 0 || txsVideoEncoderParam.fps == 0 || txsVideoEncoderParam.gop == 0) {
            this.y = true;
            return false;
        }
        this.g = txsVideoEncoderParam.annexb;
        this.h = txsVideoEncoderParam.appendSpsPps;
        if (this.a == 0) {
            this.a = (int)(Math.sqrt(txsVideoEncoderParam.width * txsVideoEncoderParam.width * 1.0 + txsVideoEncoderParam.height * txsVideoEncoderParam.height) * 1.2);
        }
        this.i = this.a;
        this.f = txsVideoEncoderParam.fps;
        int j = 2;
        switch (txsVideoEncoderParam.encoderMode) {
            case 1: {
                j = 2;
                break;
            }
            case 2: {
                j = 1;
                break;
            }
            case 3: {
                j = 0;
                break;
            }
        }
        if (com.tencent.liteav.basic.d.b.a().c() == 1) {
            txsVideoEncoderParam.encoderProfile = 1;
        }
        switch (txsVideoEncoderParam.encoderProfile) {
            case 1: {}
            case 2: {}
        }
        final int k = 1;
        this.J = j;
        this.K = k;
        if (!this.c()) {
            return false;
        }
        this.mInit = true;
        if (this.M) {
            this.U = -1;
            this.r.b(this.t);
        }
        this.W = new ArrayList<Long>();
        this.X = 0;
        return true;
    }
    
    private int a(final int n) {
        if (this.q == null) {
            return -1;
        }
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int dequeueOutputBuffer;
        try {
            dequeueOutputBuffer = this.q.dequeueOutputBuffer(bufferInfo, (long)(n * 1000));
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXCHWVideoEncoder", "dequeueOutputBuffer failed." + ex.getMessage());
            return -1;
        }
        int n2 = 1;
        if (dequeueOutputBuffer == -1) {
            return 0;
        }
        if (dequeueOutputBuffer == -3) {
            this.B = this.q.getOutputBuffers();
            return 1;
        }
        if (dequeueOutputBuffer == -2) {
            this.callDelegate(this.q.getOutputFormat());
            return 1;
        }
        if (dequeueOutputBuffer < 0) {
            return -1;
        }
        final long timeTick = TXCTimeUtil.getTimeTick();
        final ByteBuffer byteBuffer = this.B[dequeueOutputBuffer];
        if (byteBuffer == null) {
            n2 = -1;
        }
        else {
            byte[] a = new byte[bufferInfo.size];
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            byteBuffer.get(a, 0, bufferInfo.size);
            final byte[] array = a;
            int n3 = 0;
            int length = a.length;
            if (bufferInfo.size > 5 && a[0] == 0 && a[1] == 0 && a[2] == 0 && a[3] == 0 && a[4] == 0 && a[5] == 0) {
                for (int i = 3; i < a.length - 4; ++i) {
                    if (a[i] == 0 && a[i + 1] == 0 && a[i + 2] == 0 && a[i + 3] == 1) {
                        n3 = i;
                        length -= i;
                        break;
                    }
                }
                final byte[] array2 = new byte[length];
                System.arraycopy(a, n3, array2, 0, length);
                a = array2;
            }
            if (bufferInfo.size == 0) {
                if ((bufferInfo.flags & 0x4) != 0x0) {
                    if (this.mListener != null) {
                        this.mListener.onEncodeNAL(null, 0);
                    }
                    n2 = -2;
                }
                else {
                    n2 = -1;
                }
            }
            else {
                int n4 = 1;
                if ((bufferInfo.flags & 0x2) == 0x2) {
                    if (this.g) {
                        this.C = a.clone();
                    }
                    else {
                        this.C = this.a(a.clone());
                    }
                    n2 = 1;
                }
                else {
                    if ((bufferInfo.flags & 0x1) == 0x1) {
                        n4 = 0;
                        this.G = -1;
                        if (this.g) {
                            final byte[] array3 = new byte[this.C.length + a.length];
                            System.arraycopy(this.C, 0, array3, 0, this.C.length);
                            System.arraycopy(a, 0, array3, this.C.length, a.length);
                            a = array3;
                        }
                        else {
                            final byte[] a2 = this.a(a);
                            final byte[] array4 = new byte[this.C.length + a2.length];
                            System.arraycopy(this.C, 0, array4, 0, this.C.length);
                            System.arraycopy(a2, 0, array4, this.C.length, a2.length);
                            a = array4;
                        }
                    }
                    else if (!this.g) {
                        a = this.a(a);
                    }
                    if (!this.L && ++this.G == this.f * this.H) {
                        this.f();
                    }
                    long a3 = this.a();
                    final long e = bufferInfo.presentationTimeUs / 1000L;
                    if (this.F == 0L) {
                        this.F = a3;
                    }
                    if (this.E == 0L) {
                        this.E = e;
                    }
                    final long n5 = e + (this.F - this.E);
                    if (a3 <= this.m) {
                        a3 = this.m + 1L;
                    }
                    if (a3 > n5) {
                        a3 = n5;
                    }
                    this.m = a3;
                    final long timeTick2 = TXCTimeUtil.getTimeTick();
                    if (n4 == 0) {
                        if (timeTick2 > this.d + 1000L) {
                            this.b = (long)(this.o * 8000.0 / (timeTick2 - this.d) / 1024.0);
                            this.o = 0L;
                            this.d = timeTick2;
                            this.g();
                        }
                        ++this.j;
                        if (this.j % 256L == 0L) {
                            ++this.j;
                        }
                        this.k = 0L;
                    }
                    else {
                        ++this.k;
                    }
                    this.o += a.length;
                    if (timeTick2 > this.e + 2000L) {
                        this.c = this.p * 1000.0 / (timeTick2 - this.e);
                        this.p = 0L;
                        this.e = timeTick2;
                        long longValue = 0L;
                        for (final Long n6 : this.W) {
                            if (n6 > longValue) {
                                longValue = n6;
                            }
                        }
                        this.W.clear();
                        this.X = (int)(longValue * 3L);
                    }
                    ++this.p;
                    byteBuffer.position(bufferInfo.offset);
                    ++this.l;
                    if (this.mListener != null) {
                        this.mListener.onEncodeFinished(2, this.j, this.k);
                    }
                    if (this.h) {
                        this.callDelegate(a, n4, this.j, this.k, this.l, (n4 == 0) ? this.k : (this.k - 1L), n5, n5, 0, byteBuffer, bufferInfo);
                    }
                    else {
                        this.callDelegate(array, n4, this.j, this.k, this.l, (n4 == 0) ? this.k : (this.k - 1L), n5, n5, 0, byteBuffer, bufferInfo);
                    }
                    ++this.P;
                    if ((bufferInfo.flags & 0x4) != 0x0) {
                        if (this.mListener != null) {
                            this.mListener.onEncodeNAL(null, 0);
                        }
                        n2 = -2;
                    }
                    this.W.add(TXCTimeUtil.getTimeTick() - timeTick);
                }
            }
        }
        try {
            if (this.q != null) {
                this.q.releaseOutputBuffer(dequeueOutputBuffer, false);
            }
        }
        catch (IllegalStateException ex2) {
            TXCLog.e("TXCHWVideoEncoder", "releaseOutputBuffer failed." + ex2.getMessage());
        }
        return n2;
    }
    
    private byte[] a(final byte[] array) {
        final int length = array.length;
        int n = 0;
        int n2 = 0;
        final byte[] array2 = new byte[length + 20];
        int i;
        for (i = 0; i < length; ++i) {
            if (array[i] == 0 && array[i + 1] == 0 && array[i + 2] == 1) {
                n = this.a(i, n2, array2, array, n);
                i += 3;
                n2 = i;
            }
            else if (array[i] == 0 && array[i + 1] == 0 && array[i + 2] == 0 && array[i + 3] == 1) {
                n = this.a(i, n2, array2, array, n);
                i += 4;
                n2 = i;
            }
            if (i == length - 4 && (array[i + 1] != 0 || array[i + 2] != 0 || array[i + 3] != 1)) {
                i = length;
                break;
            }
        }
        final int a = this.a(i, n2, array2, array, n);
        final byte[] array3 = new byte[a];
        System.arraycopy(array2, 0, array3, 0, a);
        return array3;
    }
    
    private int a(final int n, final int n2, final byte[] array, final byte[] array2, int n3) {
        try {
            if (n2 > 0 && n > n2) {
                final int n4 = n - n2;
                final ByteBuffer wrap = ByteBuffer.wrap(new byte[4]);
                wrap.asIntBuffer().put(n4);
                wrap.order(ByteOrder.BIG_ENDIAN);
                System.arraycopy(wrap.array(), 0, array, n3, 4);
                System.arraycopy(array2, n2, array, n3 + 4, n4);
                n3 += n4 + 4;
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCHWVideoEncoder", "setNalData exception");
        }
        return n3;
    }
    
    @TargetApi(18)
    private void b(final int n) {
        if (this.y || this.w == null) {
            return;
        }
        final int u = this.U;
        if (this.M) {
            this.U = -1;
            if (u == -1) {
                this.V = true;
                return;
            }
            ++this.T;
            this.r.a(this.t, 1000 / this.I);
        }
        this.a(this.D);
        final int n2 = (720 - this.mRotation) % 360;
        this.mEncodeFilter.a(this.mInputWidth, this.mInputHeight, n2, null, ((n2 == 90 || n2 == 270) ? this.mOutputHeight : this.mOutputWidth) / (float)((n2 == 90 || n2 == 270) ? this.mOutputWidth : this.mOutputHeight), this.mEnableXMirror, true);
        this.mEncodeFilter.b(u);
        if (this.w instanceof com.tencent.liteav.basic.c.c) {
            ((com.tencent.liteav.basic.c.c)this.w).a(this.D * 1000000L);
            ((com.tencent.liteav.basic.c.c)this.w).d();
        }
        if (this.w instanceof b) {
            ((b)this.w).a();
        }
        int a;
        while ((a = this.a(n)) > 0) {}
        if (a == -1 || a == -2) {
            if (a == -1) {
                this.callDelegate(10000005);
            }
            this.y = true;
            this.e();
            return;
        }
        ++this.O;
    }
    
    private boolean c() {
        if (Build.VERSION.SDK_INT < 18) {
            return false;
        }
        this.ac = (com.tencent.liteav.basic.d.b.a().a("Video", "CheckVideoEncDownBps") > 0L);
        this.O = 0;
        this.P = 0;
        this.R = 0L;
        this.S = 0;
        this.Q = 0;
        this.T = 0;
        int n = 0;
        ++n;
        try {
            final MediaFormat a = this.a(this.mOutputWidth, this.mOutputHeight, this.a, this.I, this.H, this.J, this.K, this.N);
            if (a == null) {
                this.y = true;
                return false;
            }
            this.q = MediaCodec.createEncoderByType("video/avc");
            ++n;
            if (this.mEncFmt != null) {
                try {
                    for (int i = 0; i < this.mEncFmt.length(); ++i) {
                        final JSONObject jsonObject = this.mEncFmt.getJSONObject(i);
                        a.setInteger(jsonObject.optString("key"), jsonObject.optInt("value"));
                    }
                }
                catch (Exception ex) {
                    TXCLog.w("TXCHWVideoEncoder", "config custom format error " + ex.toString());
                }
            }
            try {
                this.q.configure(a, (Surface)null, (MediaCrypto)null, 1);
                TXCLog.i("TXCHWVideoEncoder", "configure format =  " + a.toString());
            }
            catch (Exception ex2) {
                ex2.printStackTrace();
                if (this.N) {
                    final MediaFormat a2 = this.a(this.mOutputWidth, this.mOutputHeight, this.a, this.I, this.H, this.J, this.K, false);
                    try {
                        this.q.configure(a2, (Surface)null, (MediaCrypto)null, 1);
                    }
                    catch (Exception ex3) {
                        if (ex3 instanceof IllegalArgumentException || (Build.VERSION.SDK_INT >= 21 && ex3 instanceof MediaCodec.CodecException)) {
                            this.q.configure(this.a(this.mOutputWidth, this.mOutputHeight, this.a, this.I, this.H), (Surface)null, (MediaCrypto)null, 1);
                        }
                        ex2.printStackTrace();
                    }
                }
                else {
                    if (!(ex2 instanceof IllegalArgumentException) && (Build.VERSION.SDK_INT < 21 || !(ex2 instanceof MediaCodec.CodecException))) {
                        throw ex2;
                    }
                    this.q.configure(this.a(this.mOutputWidth, this.mOutputHeight, this.a, this.I, this.H), (Surface)null, (MediaCrypto)null, 1);
                }
            }
            ++n;
            this.x = this.q.createInputSurface();
            ++n;
            this.q.start();
            ++n;
            this.B = this.q.getOutputBuffers();
            ++n;
        }
        catch (Exception ex4) {
            TXCLog.e("TXCHWVideoEncoder", "create encode format failed.", ex4);
            try {
                if (n >= 5 && this.q != null) {
                    this.q.stop();
                }
                this.q = null;
                if (this.x != null) {
                    this.x.release();
                }
                this.x = null;
            }
            catch (Exception ex5) {}
        }
        if (this.q == null || this.B == null || this.x == null) {
            this.y = true;
            return false;
        }
        if (!this.a(this.x, this.mOutputWidth, this.mOutputHeight)) {
            this.y = true;
            return false;
        }
        return true;
    }
    
    private void d() {
        if (this.q == null) {
            return;
        }
        try {
            this.q.stop();
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXCHWVideoEncoder", "stop encoder failed.", ex);
            try {
                this.q.release();
                if (this.x != null) {
                    this.x.release();
                }
                this.x = null;
            }
            catch (Exception ex2) {
                TXCLog.e("TXCHWVideoEncoder", "release encoder failed.", ex2);
            }
        }
        finally {
            try {
                this.q.release();
                if (this.x != null) {
                    this.x.release();
                }
                this.x = null;
            }
            catch (Exception ex3) {
                TXCLog.e("TXCHWVideoEncoder", "release encoder failed.", ex3);
            }
        }
        this.q = null;
    }
    
    private void e() {
        if (!this.mInit) {
            return;
        }
        this.y = true;
        this.z = true;
        this.b();
        this.d();
        this.U = -1;
        this.b = 0L;
        this.c = 0.0;
        this.d = 0L;
        this.e = 0L;
        this.f = 0;
        this.i = 0L;
        this.j = 0L;
        this.k = 0L;
        this.l = 0L;
        this.m = 0L;
        this.o = 0L;
        this.p = 0L;
        this.mGLContextExternal = null;
        this.B = null;
        this.C = null;
        this.D = 0L;
        this.mOutputWidth = 0;
        this.mOutputHeight = 0;
        this.mInit = false;
        this.mListener = null;
        this.v.clear();
        this.W.clear();
        this.X = 0;
    }
    
    private void c(final int n) {
        if (this.i == this.a) {
            return;
        }
        boolean b = false;
        if (this.a < this.i && this.ac) {
            if (this.ab) {
                b = true;
                final String string = "restart video hw encoder when down bps\u3002[module:" + Build.MODEL + "] [Hardware:" + Build.HARDWARE + "] [osVersion:" + Build.VERSION.RELEASE + "]";
                TXCLog.w("TXCHWVideoEncoder", string);
                Monitor.a(2, string, "", 0);
            }
            else {
                this.Z = 3;
                this.Y = System.currentTimeMillis();
                this.aa = this.a;
            }
        }
        this.i = this.a;
        if (Build.VERSION.SDK_INT >= 19 && this.q != null) {
            if (b) {
                this.r.a().removeCallbacks(this.ae);
                final long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.ad >= 2000L) {
                    this.ae.run();
                }
                else {
                    this.r.a(this.ae, 2000L - (currentTimeMillis - this.ad));
                }
            }
            else {
                final Bundle parameters = new Bundle();
                parameters.putInt("video-bitrate", this.a * 1024);
                this.q.setParameters(parameters);
            }
        }
    }
    
    private void f() {
        if (Build.VERSION.SDK_INT >= 19 && this.q != null) {
            final Bundle parameters = new Bundle();
            parameters.putInt("request-sync", 0);
            this.q.setParameters(parameters);
        }
    }
    
    private void d(final int i) {
        if (i == 0 || this.I == i) {
            return;
        }
        if (Build.VERSION.SDK_INT < 18) {
            return;
        }
        TXCLog.i("TXCHWVideoEncoder", "set fps " + i + ", restart encoder.");
        this.b();
        this.d();
        this.I = i;
        this.c();
    }
    
    private void g() {
        if (this.Y > 0L) {
            final int n = this.I - (int)this.c;
            int n2 = this.I / 2;
            if (n2 < 5) {
                n2 = 5;
            }
            if (n <= n2 && System.currentTimeMillis() - this.Y > 2000 * (3 - this.Z + 1)) {
                final long n3 = this.aa - this.b;
                long n4 = this.i / 2L;
                if (n4 < 100L) {
                    n4 = 100L;
                }
                if (n3 > n4) {
                    this.ab = true;
                    final String string = "real bitrate is too much lower than target bitrate![targetBr:" + this.aa + "] [realBr:" + this.b + "]. restart encoder. [module:" + Build.MODEL + "] [Hardware:" + Build.HARDWARE + "] [osVersion:" + Build.VERSION.RELEASE + "]";
                    TXCLog.e("TXCHWVideoEncoder", string);
                    Monitor.a(3, string, "", 0);
                    if (this.r != null) {
                        this.r.b(this.ae);
                    }
                    this.Y = 0L;
                }
                else {
                    --this.Z;
                    if (this.Z <= 0) {
                        this.Y = 0L;
                    }
                }
            }
        }
    }
}
