package com.tencent.liteav.b;

import android.content.*;

import java.util.concurrent.*;

import com.tencent.liteav.videoencoder.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.log.*;

import java.nio.*;
import android.media.*;
import android.text.*;
import android.opengl.*;
import com.tencent.liteav.editer.*;

public class i
{
    private final String d = "TXCombineEncAndMuxer";
    private Context e;
    protected boolean a;
    protected b b;
    private a f;
    private int g;
    private int h;
    private int i;
    private int j;
    protected c c;
    private String k;
    private boolean l;
    private long m;
    private int n;
    private int o;
    private int p;
    private LinkedBlockingQueue<e> q;
    private boolean r;
    private boolean s;
    private int t;
    private boolean u;
    private g v;
    private e w;
    private int x;
    private int y;
    private TXSVideoEncoderParam z;
    private com.tencent.liteav.editer.g A;
    private d B;
    private u C;
    
    public i(final Context e) {
        this.a = false;
        this.g = 1;
        this.h = 98304;
        this.i = 10000;
        this.j = 48000;
        this.l = false;
        this.n = 13000;
        this.t = 2;
        this.u = true;
        this.x = 0;
        this.y = 0;
        this.A = new com.tencent.liteav.editer.g() {
            @Override
            public void a(final int n) {
            }
        };
        this.B = new d() {
            @Override
            public void onEncodeNAL(final TXSNALPacket txsnalPacket, final int n) {
                if (10000004 == n || 10000005 == n) {
                    if (!com.tencent.liteav.b.i.this.a) {
                        com.tencent.liteav.b.i.this.c();
                    }
                    else {
                        com.tencent.liteav.b.i.this.c(n);
                    }
                    return;
                }
                if (n != 0) {
                    TXCLog.e("TXCombineEncAndMuxer", "mVideoEncodeListener, errCode = " + n);
                    com.tencent.liteav.b.i.this.c(n);
                    return;
                }
                if (txsnalPacket == null || txsnalPacket.nalData == null) {
                    TXCLog.i("TXCombineEncAndMuxer", "===Video onEncodeComplete=== mAudioEncEnd " + com.tencent.liteav.b.i.this.s);
                    com.tencent.liteav.b.i.this.r = true;
                    if (com.tencent.liteav.b.i.this.s) {
                        TXCLog.i("TXCombineEncAndMuxer", "===Video onEncodeComplete=== mAudioEncEnd is true");
                        com.tencent.liteav.b.i.this.a();
                        com.tencent.liteav.b.i.this.d();
                    }
                    return;
                }
                e e = null;
                try {
                    TXCLog.d("TXCombineEncAndMuxer", "onEncodeNAL, before take mVideoQueue size = " + com.tencent.liteav.b.i.this.q.size());
                    e = com.tencent.liteav.b.i.this.q.take();
                }
                catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                synchronized (this) {
                    if (com.tencent.liteav.b.i.this.c != null && txsnalPacket != null && txsnalPacket.nalData != null) {
                        if (com.tencent.liteav.b.i.this.l) {
                            this.a(txsnalPacket, e);
                        }
                        else if (txsnalPacket.nalType == 0) {
                            final MediaFormat a = com.tencent.liteav.basic.util.f.a(txsnalPacket.nalData, com.tencent.liteav.b.i.this.o, com.tencent.liteav.b.i.this.p);
                            if (a != null) {
                                com.tencent.liteav.b.i.this.c.a(a);
                                com.tencent.liteav.b.i.this.c.a();
                                com.tencent.liteav.b.i.this.l = true;
                            }
                            this.a(txsnalPacket, e);
                        }
                    }
                }
                com.tencent.liteav.b.i.this.d(e);
            }
            
            private void a(final TXSNALPacket txsnalPacket, final e e) {
                final long e2 = e.e();
                TXCLog.d("TXCombineEncAndMuxer", "Muxer writeVideoData :" + e2);
                int flags = 0;
                if (txsnalPacket.info == null) {
                    if (txsnalPacket.nalType == 0) {
                        flags = 1;
                    }
                }
                else {
                    flags = txsnalPacket.info.flags;
                }
                if (com.tencent.liteav.b.i.this.c != null) {
                    com.tencent.liteav.b.i.this.c.b(txsnalPacket.nalData, 0, txsnalPacket.nalData.length, e2, flags);
                }
            }
            
            @Override
            public void onEncodeFormat(final MediaFormat mediaFormat) {
                TXCLog.i("TXCombineEncAndMuxer", "Video onEncodeFormat format:" + mediaFormat);
                if (com.tencent.liteav.b.i.this.c != null) {
                    com.tencent.liteav.b.i.this.c.a(mediaFormat);
                    if (com.tencent.liteav.b.i.this.c.d()) {
                        TXCLog.i("TXCombineEncAndMuxer", "Has Audio, Video Muxer start");
                        com.tencent.liteav.b.i.this.c.a();
                        com.tencent.liteav.b.i.this.l = true;
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
        this.C = new u() {
            @Override
            public void a(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
                if (com.tencent.liteav.b.i.this.t == 2 && com.tencent.liteav.b.i.this.u) {
                    com.tencent.liteav.b.i.this.u = false;
                    final MediaFormat a = com.tencent.liteav.basic.util.f.a(com.tencent.liteav.b.i.this.j, com.tencent.liteav.b.i.this.g, 2);
                    if (com.tencent.liteav.b.i.this.c != null) {
                        com.tencent.liteav.b.i.this.c.b(a);
                    }
                }
                com.tencent.liteav.b.i.this.y++;
                if (com.tencent.liteav.b.i.this.c != null) {
                    TXCLog.d("TXCombineEncAndMuxer", "Muxer writeAudioData :" + bufferInfo.presentationTimeUs);
                    com.tencent.liteav.b.i.this.c.a(byteBuffer, bufferInfo);
                }
            }
            
            @Override
            public void a(final MediaFormat mediaFormat) {
                TXCLog.i("TXCombineEncAndMuxer", "Audio onEncodeFormat format:" + mediaFormat);
                if (com.tencent.liteav.b.i.this.c != null) {
                    com.tencent.liteav.b.i.this.c.b(mediaFormat);
                    if (com.tencent.liteav.b.i.this.c.c()) {
                        com.tencent.liteav.b.i.this.c.a();
                        com.tencent.liteav.b.i.this.l = true;
                    }
                }
            }
            
            @Override
            public void a() {
                TXCLog.i("TXCombineEncAndMuxer", "===Audio onEncodeComplete===");
                com.tencent.liteav.b.i.this.s = true;
                if (com.tencent.liteav.b.i.this.r) {
                    TXCLog.i("TXCombineEncAndMuxer", "===Audio onEncodeComplete=== mVideoEncEnd is true");
                    com.tencent.liteav.b.i.this.a();
                    com.tencent.liteav.b.i.this.d();
                }
            }
        };
        this.e = e;
        this.q = new LinkedBlockingQueue<e>();
    }
    
    public void a(final g v) {
        this.v = v;
    }
    
    private void c() {
        TXCLog.i("TXCombineEncAndMuxer", "retryWithSWEncoder");
        if (this.b != null) {
            this.b.a((d)null);
            this.b.a();
            this.b = null;
        }
        this.a = true;
        this.b = new b(2);
        if (this.z != null) {
            this.z.encoderMode = 1;
            this.z.encoderProfile = 3;
            this.z.record = true;
            this.b.c(this.n);
            this.b.a(this.B);
            this.b.a(this.z);
        }
        else {
            TXCLog.e("TXCombineEncAndMuxer", "mVideoEncoderParam is null!!");
        }
    }
    
    public void a(final int o, final int p2) {
        this.o = o;
        this.p = p2;
        this.r = false;
        this.s = false;
        this.a = (this.o < 1280 && this.p < 1280);
        this.b = new b(this.a ? 2 : 1);
        this.c = new c(this.e, this.a ? 0 : 2);
        if (!TextUtils.isEmpty((CharSequence)this.k)) {
            this.c.a(this.k);
        }
    }
    
    public void a(final String k) {
        this.k = k;
        if (this.c != null) {
            this.c.a(this.k);
        }
    }
    
    public void a(final long m) {
        this.m = m;
    }
    
    public void a(final int i) {
        this.i = i;
    }
    
    public void b(final int j) {
        this.j = j;
    }
    
    public void a(final EGLContext glContext) {
        TXCLog.i("TXCombineEncAndMuxer", "OnContextListener onContext");
        final TXSVideoEncoderParam z = new TXSVideoEncoderParam();
        z.width = this.o;
        z.height = this.p;
        z.fps = 20;
        z.glContext = glContext;
        z.enableEGL14 = true;
        z.enableBlackList = false;
        z.appendSpsPps = false;
        z.annexb = true;
        z.fullIFrame = false;
        z.gop = 3;
        if (this.a) {
            z.encoderMode = 1;
            z.encoderProfile = 3;
            z.record = true;
        }
        else {
            z.encoderMode = 3;
            z.encoderProfile = 1;
        }
        this.z = z;
        this.b.c(this.n);
        this.b.a(this.B);
        this.b.a(z);
        (this.f = new a()).a(this.A);
        this.f.a(this.C);
        final t t = new t();
        t.channelCount = this.g;
        t.sampleRate = this.j;
        t.maxInputSize = this.i;
        t.audioBitrate = this.h;
        t.encoderType = this.t;
        TXCLog.i("TXCombineEncAndMuxer", "AudioEncoder.start");
        this.f.a(t);
    }
    
    public void a(final int n, final int n2, final int n3, final e e) {
        if (this.b != null) {
            this.q.add(e);
            this.b.c(n, n2, n3, e.e() / 1000L);
        }
    }
    
    public void a(final e e) {
        ++this.x;
        if (this.f != null) {
            this.f.a(e);
        }
    }
    
    public void b(final e w) {
        this.w = w;
        if (this.f != null) {
            this.f.a(w);
        }
    }
    
    public void c(final e e) {
        this.q.add(e);
        this.b.c();
    }
    
    public void a() {
        TXCLog.i("TXCombineEncAndMuxer", "stopEncAndMuxer()");
        if (this.b != null) {
            this.b.a((d)null);
            this.b.a();
            this.b = null;
        }
        if (this.f != null) {
            this.f.a((u)null);
            this.f.a((com.tencent.liteav.editer.g)null);
            this.f.a();
            this.f = null;
        }
        if (this.c != null) {
            this.c.b();
            this.c = null;
            this.l = false;
        }
        this.k = null;
        this.w = null;
        this.q.clear();
    }
    
    private void d(final e e) {
        if (this.v != null) {
            this.v.a(e.e() * 1.0f / this.m);
        }
    }
    
    private void d() {
        TXCLog.i("TXCombineEncAndMuxer", "onGenerateComplete()");
        if (this.v != null) {
            this.v.a(0, "");
        }
    }
    
    private void c(final int n) {
        TXCLog.i("TXCombineEncAndMuxer", "onGenerateError()");
        if (this.v != null) {
            this.v.a(n, "Encoder Error");
        }
    }
    
    public void b() {
        this.u = (this.t == 2);
        this.w = null;
        this.x = 0;
        this.y = 0;
    }
}
