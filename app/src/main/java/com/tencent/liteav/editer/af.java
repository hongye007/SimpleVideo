package com.tencent.liteav.editer;

import android.annotation.*;
import java.nio.*;
import java.util.concurrent.atomic.*;
import android.view.*;
import com.tencent.liteav.basic.log.*;
import android.media.*;
import android.os.*;

@TargetApi(16)
public class af implements b
{
    private MediaCodec b;
    private ByteBuffer[] c;
    private ByteBuffer[] d;
    private AtomicBoolean e;
    private long f;
    private MediaFormat g;
    private Surface h;
    private a i;
    private int j;
    private final int k = 40;
    
    public af() {
        this.f = 1000L;
        this.i = com.tencent.liteav.g.b.a;
        this.j = 0;
        this.e = new AtomicBoolean(false);
    }
    
    @Override
    public void a(final MediaFormat mediaFormat) {
    }
    
    @Override
    public void a(final MediaFormat g, final Surface h) {
        this.g = g;
        this.h = h;
    }
    
    @Override
    public void a() {
        TXCLog.i("VideoMediaCodecDecoder", "start");
        final MediaFormat g = this.g;
        if (g == null) {
            TXCLog.e("VideoMediaCodecDecoder", "start -> media format is null.");
            return;
        }
        g.setInteger("rotation-degrees", 0);
        try {
            (this.b = MediaCodec.createDecoderByType(g.getString("mime"))).configure(g, this.h, (MediaCrypto)null, 0);
            this.b.start();
            this.c = this.b.getInputBuffers();
            this.d = this.b.getOutputBuffers();
            this.e.getAndSet(true);
            TXCLog.i("VideoMediaCodecDecoder", "start -> decoder start success.");
        }
        catch (Exception ex) {
            TXCLog.e("VideoMediaCodecDecoder", "start -> decoder start fail.");
            ex.printStackTrace();
            this.e.getAndSet(false);
            this.a("start decoder.");
            this.b("start decoder error.");
        }
    }
    
    @Override
    public void b() {
        TXCLog.i("VideoMediaCodecDecoder", "stop");
        if (this.b == null || !this.e.get()) {
            TXCLog.e("VideoMediaCodecDecoder", "stop VideoDecoder error");
            return;
        }
        try {
            this.b.stop();
            this.b.release();
        }
        catch (Exception ex) {
            TXCLog.e("VideoMediaCodecDecoder", "video decoder stop exception: " + ex);
        }
        finally {
            this.e.getAndSet(false);
        }
    }
    
    @Override
    public e c() {
        if (!this.e.get()) {
            return null;
        }
        try {
            int dequeueInputBuffer = -1;
            try {
                dequeueInputBuffer = this.b.dequeueInputBuffer(this.f);
            }
            catch (Exception ex) {
                TXCLog.e("VideoMediaCodecDecoder", "video dequeueInputBuffer exception: " + ex);
            }
            if (dequeueInputBuffer >= 0) {
                ByteBuffer inputBuffer;
                if (Build.VERSION.SDK_INT >= 21) {
                    inputBuffer = this.b.getInputBuffer(dequeueInputBuffer);
                }
                else {
                    inputBuffer = this.c[dequeueInputBuffer];
                }
                return new e(inputBuffer, 0, 0L, dequeueInputBuffer, 0, 0);
            }
        }
        catch (Exception ex2) {
            TXCLog.e("VideoMediaCodecDecoder", "findFreeFrame error");
            this.f();
        }
        return null;
    }
    
    @Override
    public void a(final e e) {
        if (!this.e.get()) {
            return;
        }
        final int d = e.d();
        final int n = 0;
        final int g = e.g();
        final long e2 = e.e();
        final int f = e.f();
        try {
            this.b.queueInputBuffer(d, n, g, e2, f);
        }
        catch (Exception ex) {
            this.f();
            TXCLog.e("VideoMediaCodecDecoder", "decodeFrame error.");
        }
    }
    
    @Override
    public e d() {
        if (!this.e.get()) {
            return null;
        }
        try {
            final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            final int dequeueOutputBuffer = this.b.dequeueOutputBuffer(bufferInfo, this.f);
            if (dequeueOutputBuffer != -1) {
                if (dequeueOutputBuffer != -3) {
                    if (dequeueOutputBuffer != -2) {
                        if (dequeueOutputBuffer >= 0) {
                            if (dequeueOutputBuffer >= 0) {
                                final e e = new e(null, bufferInfo.size, bufferInfo.presentationTimeUs, dequeueOutputBuffer, bufferInfo.flags, 0);
                                this.b.releaseOutputBuffer(dequeueOutputBuffer, true);
                                return e;
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            TXCLog.e("VideoMediaCodecDecoder", "getDecodedFrame");
            this.f();
        }
        return null;
    }
    
    public void e() {
        if (!this.e.get()) {
            return;
        }
        this.b.flush();
        this.c = this.b.getInputBuffers();
        this.d = this.b.getOutputBuffers();
    }
    
    public e a(final e e, final e e2) {
        if (!this.e.get()) {
            return null;
        }
        e2.k(e.n());
        e2.j(e.m());
        e2.e(e.h());
        e2.f(e.i());
        e2.i(e.l());
        e2.h(e.k());
        e2.g(e.j());
        e2.b(e.e());
        return e2;
    }
    
    public e b(final e e) {
        if (!this.e.get()) {
            return null;
        }
        e.d(0);
        e.a(0L);
        e.c(4);
        TXCLog.i("VideoMediaCodecDecoder", "------appendEndFrame----------");
        return e;
    }
    
    public void a(final a i) {
        if (i != null) {
            this.i = i;
        }
        else {
            this.i = com.tencent.liteav.g.b.a;
        }
    }
    
    private void a(final String s) {
        TXCLog.i("VideoMediaCodecDecoder", "releaseDecoder -> [ " + s + " ] ");
        this.e.set(false);
        if (this.b != null) {
            try {
                this.b.release();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                TXCLog.e("VideoMediaCodecDecoder", "releaseDecoder -> [ " + s + " ]  : release codec error.");
            }
        }
    }
    
    private void b(final String s) {
        final a i = this.i;
        if (i != null) {
            i.a(s);
        }
    }
    
    private void f() {
        if (this.j >= 40) {
            this.j = 0;
            this.b("check decoder error.");
        }
        else {
            ++this.j;
        }
    }
}
