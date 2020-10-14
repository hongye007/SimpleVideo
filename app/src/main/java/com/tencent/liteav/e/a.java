package com.tencent.liteav.e;

import java.util.concurrent.*;
import com.tencent.liteav.basic.structs.*;
import android.media.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.videoencoder.*;
import com.tencent.liteav.basic.c.*;
import android.opengl.*;
import java.nio.*;

public class a
{
    private final int a = 5;
    private String b;
    private b c;
    private int d;
    private int[] e;
    private LinkedBlockingDeque<Integer> f;
    private LinkedBlockingDeque<a> g;
    private int h;
    private d i;
    private boolean j;
    private int k;
    private int l;
    private h m;
    private boolean n;
    
    public a(final int n, final boolean n2) {
        this.b = a.class.getSimpleName();
        this.d = 2;
        this.h = -1;
        this.j = false;
        this.n = false;
        this.d = ((n > 5) ? 5 : n);
        this.f = new LinkedBlockingDeque<Integer>();
        this.g = new LinkedBlockingDeque<a>();
        this.n = n2;
        (this.c = new b(n2 ? 2 : 1)).a(new d() {
            @Override
            public void onEncodeNAL(final TXSNALPacket txsnalPacket, final int n) {
                if (com.tencent.liteav.e.a.this.i != null) {
                    com.tencent.liteav.e.a.this.i.onEncodeNAL(txsnalPacket, n);
                }
            }
            
            @Override
            public void onEncodeFormat(final MediaFormat mediaFormat) {
                if (com.tencent.liteav.e.a.this.i != null) {
                    com.tencent.liteav.e.a.this.i.onEncodeFormat(mediaFormat);
                }
            }
            
            @Override
            public void onEncodeFinished(final int n, final long n2, final long n3) {
                TXCLog.i(com.tencent.liteav.e.a.this.b, "onEncodeFinished");
                if (com.tencent.liteav.e.a.this.i != null) {
                    com.tencent.liteav.e.a.this.i.onEncodeFinished(n, n2, n3);
                }
            }
            
            @Override
            public void onRestartEncoder(final int n) {
                TXCLog.i(com.tencent.liteav.e.a.this.b, "onRestartEncoder");
                if (com.tencent.liteav.e.a.this.i != null) {
                    com.tencent.liteav.e.a.this.i.onRestartEncoder(n);
                }
            }
            
            @Override
            public void onEncodeDataIn(final int n) {
                if (com.tencent.liteav.e.a.this.g.size() > 0) {
                    final a a = com.tencent.liteav.e.a.this.g.pop();
                    try {
                        com.tencent.liteav.e.a.this.f.put(a.a);
                    }
                    catch (InterruptedException ex) {}
                }
            }
        });
    }
    
    public int a(final TXSVideoEncoderParam txsVideoEncoderParam) {
        TXCLog.i(this.b, "start");
        this.k = txsVideoEncoderParam.width;
        this.l = txsVideoEncoderParam.height;
        int a = 10000002;
        if (this.c != null) {
            a = this.c.a(txsVideoEncoderParam);
        }
        if (!this.n) {
            this.d();
        }
        return a;
    }
    
    private void d() {
        this.e();
        (this.m = new h()).a(com.tencent.liteav.basic.c.l.e, com.tencent.liteav.basic.c.l.a(com.tencent.liteav.basic.c.k.a, true, true));
        if (!this.m.c()) {
            this.m = null;
            TXCLog.i(this.b, "init encodefilter fail");
        }
    }
    
    public void a(final int n, final int b, final int c, final long d) throws InterruptedException {
        if (this.c != null) {
            if (!this.n) {
                final int intValue = this.f.take();
                GLES20.glBindFramebuffer(36160, this.h);
                GLES20.glFramebufferTexture2D(36160, 36064, 3553, intValue, 0);
                this.m.a(this.k, this.l, 0, null, this.k / (float)this.l, false, false);
                this.m.b(n);
                GLES20.glBindTexture(3553, 0);
                GLES20.glBindFramebuffer(36160, 0);
                final a a = new a();
                a.a = intValue;
                a.b = b;
                a.c = c;
                a.d = d;
                this.g.put(a);
                this.c.b(intValue, b, c, d);
            }
            else {
                this.c.b(n, b, c, d);
            }
        }
    }
    
    public void a(final int n) {
        if (this.c != null) {
            this.c.c(n);
        }
    }
    
    public void a() {
        TXCLog.i(this.b, "signalEOSAndFlush");
        if (this.c != null) {
            this.c.c();
        }
    }
    
    public void b() {
        TXCLog.i(this.b, "destroyTextures");
        if (this.n) {
            return;
        }
        if (this.m != null) {
            this.m.e();
            this.m = null;
        }
        if (this.h != -1) {
            GLES20.glDeleteFramebuffers(1, new int[] { this.h }, 0);
            GLES20.glDeleteTextures(this.d, this.e, 0);
            this.h = -1;
            this.e = null;
        }
    }
    
    public void c() {
        TXCLog.i(this.b, "stop");
        if (this.c != null) {
            this.c.a();
        }
    }
    
    public void a(final d i) {
        this.i = i;
    }
    
    private void e() {
        TXCLog.i(this.b, "createTextures");
        final int[] e = new int[this.d];
        final int[] array = { 0 };
        GLES20.glGenTextures(this.d, e, 0);
        GLES20.glGenFramebuffers(1, array, 0);
        this.h = array[0];
        this.e = e;
        for (int i = 0; i < this.d; ++i) {
            GLES20.glBindTexture(3553, e[i]);
            this.f.push(e[i]);
            this.a("glBindTexture mFrameBufferTextureID");
            GLES20.glTexImage2D(3553, 0, 6408, this.k, this.l, 0, 6408, 5121, (Buffer)null);
            GLES20.glTexParameterf(3553, 10241, 9729.0f);
            GLES20.glTexParameterf(3553, 10240, 9729.0f);
            GLES20.glTexParameteri(3553, 10242, 33071);
            GLES20.glTexParameteri(3553, 10243, 33071);
            this.a("glTexParameter");
            GLES20.glBindFramebuffer(36160, this.h);
            GLES20.glFramebufferTexture2D(36160, 36064, 3553, e[i], 0);
            GLES20.glBindTexture(3553, 0);
            GLES20.glBindFramebuffer(36160, 0);
        }
    }
    
    private void a(final String s) {
        final int glGetError;
        if ((glGetError = GLES20.glGetError()) != 0) {
            TXCLog.e(this.b, s + ": glError " + glGetError);
            throw new RuntimeException(s + ": glError " + glGetError);
        }
    }
    
    static class a
    {
        public int a;
        public int b;
        public int c;
        public long d;
    }
}
