package com.tencent.liteav.videoediter.a;

import android.annotation.*;
import java.util.concurrent.*;
import com.tencent.liteav.basic.log.*;
import java.io.*;
import java.nio.*;
import android.media.*;

@TargetApi(18)
public class a
{
    private final boolean a = false;
    private MediaMuxer b;
    private String c;
    private MediaFormat d;
    private MediaFormat e;
    private int f;
    private int g;
    private int h;
    private int i;
    private boolean j;
    private boolean k;
    private ConcurrentLinkedQueue<a> l;
    private ConcurrentLinkedQueue<a> m;
    private long n;
    private long o;
    private long p;
    
    public a() {
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = 1;
        this.g = 0;
        this.h = 0;
        this.i = 0;
        this.j = false;
        this.k = false;
        this.l = new ConcurrentLinkedQueue<a>();
        this.m = new ConcurrentLinkedQueue<a>();
        this.n = -1L;
        this.o = -1L;
        this.p = -1L;
    }
    
    public synchronized void a(final MediaFormat d) {
        TXCLog.i("TXCMP4Muxer", "addVideoTrack:" + d);
        this.d = d;
        this.g |= 0x1;
        this.l.clear();
    }
    
    public synchronized void b(final MediaFormat e) {
        TXCLog.i("TXCMP4Muxer", "addAudioTrack:" + e);
        this.e = e;
        this.g |= 0x2;
        this.m.clear();
    }
    
    public synchronized boolean a() {
        return (this.f & 0x1) == 0x0 || (this.g & 0x1) != 0x0;
    }
    
    public synchronized boolean b() {
        return (this.f & 0x2) == 0x0 || (this.g & 0x2) != 0x0;
    }
    
    public synchronized int c() {
        if (this.c == null || this.c.isEmpty()) {
            TXCLog.e("TXCMP4Muxer", "target path not set yet!");
            return -1;
        }
        if (!this.a()) {
            TXCLog.e("TXCMP4Muxer", "video track not set yet!");
            return -2;
        }
        if (!this.b()) {
            TXCLog.e("TXCMP4Muxer", "audio track not set yet!");
            return -3;
        }
        if (this.b != null) {
            TXCLog.e("TXCMP4Muxer", "start has been called. stop must be called before start");
            return 0;
        }
        TXCLog.i("TXCMP4Muxer", "start");
        try {
            this.b = new MediaMuxer(this.c, 0);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            TXCLog.e("TXCMP4Muxer", "create MediaMuxer exception:" + ex);
            return -4;
        }
        if (this.d != null) {
            try {
                this.i = this.b.addTrack(this.d);
            }
            catch (IllegalArgumentException ex2) {
                TXCLog.e("TXCMP4Muxer", "addVideoTrack IllegalArgumentException: " + ex2);
                return -5;
            }
            catch (IllegalStateException ex3) {
                TXCLog.e("TXCMP4Muxer", "addVideoTrack IllegalStateException: " + ex3);
                return -6;
            }
        }
        if (this.e != null) {
            try {
                this.h = this.b.addTrack(this.e);
            }
            catch (IllegalArgumentException ex4) {
                TXCLog.e("TXCMP4Muxer", "addAudioTrack IllegalArgumentException: " + ex4);
                return -7;
            }
            catch (IllegalStateException ex5) {
                TXCLog.e("TXCMP4Muxer", "addAudioTrack IllegalStateException: " + ex5);
                return -8;
            }
        }
        this.b.start();
        this.n = -1L;
        this.j = true;
        this.k = false;
        this.o = -1L;
        this.p = -1L;
        return 0;
    }
    
    public synchronized int d() {
        if (this.b != null) {
            TXCLog.i("TXCMP4Muxer", "stop. start flag = " + this.j + ", video key frame set = " + this.k);
            try {
                if (this.j && this.k) {
                    this.b.stop();
                }
                this.b.release();
            }
            catch (Exception ex) {
                TXCLog.e("TXCMP4Muxer", "muxer stop/release exception: " + ex);
                return -1;
            }
            finally {
                this.j = false;
                this.b = null;
                this.g = 0;
                this.k = false;
                this.l.clear();
                this.m.clear();
                this.d = null;
                this.e = null;
                this.o = -1L;
                this.p = -1L;
            }
        }
        return 0;
    }
    
    public synchronized void a(final String c) {
        this.c = c;
    }
    
    public synchronized void a(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        if (this.b == null) {
            this.a(true, byteBuffer, bufferInfo);
            TXCLog.w("TXCMP4Muxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            return;
        }
        if (this.n < 0L) {
            this.n = this.e();
            this.f();
        }
        this.c(byteBuffer, bufferInfo);
    }
    
    public synchronized void b(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        if (this.b == null || this.n < 0L) {
            TXCLog.w("TXCMP4Muxer", "cache sample before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            this.a(false, byteBuffer, bufferInfo);
            return;
        }
        this.d(byteBuffer, bufferInfo);
    }
    
    private void c(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        final long n = bufferInfo.presentationTimeUs - this.n;
        if (n < 0L) {
            TXCLog.e("TXCMP4Muxer", "drop frame. first frame offset timeus = " + this.n + ", current timeus = " + bufferInfo.presentationTimeUs);
            return;
        }
        if (n < this.o) {
            TXCLog.e("TXCMP4Muxer", "drop frame. current frame's pts(" + n + ") must larger than pre frame's pts(" + this.o + ")");
            return;
        }
        this.o = n;
        bufferInfo.presentationTimeUs = n;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.b.writeSampleData(this.i, byteBuffer, bufferInfo);
            if ((bufferInfo.flags & 0x1) != 0x0) {
                this.k = true;
            }
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXCMP4Muxer", "write frame IllegalStateException: " + ex);
        }
    }
    
    private void d(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        final long n = bufferInfo.presentationTimeUs - this.n;
        if (this.n < 0L || n < 0L) {
            TXCLog.w("TXCMP4Muxer", "drop sample. first frame offset timeus = " + this.n + ", current sample timeus = " + bufferInfo.presentationTimeUs);
            return;
        }
        if (n < this.p) {
            TXCLog.e("TXCMP4Muxer", "drop sample. current sample's pts pts(" + n + ") must larger than pre frame's pts(" + this.p + ")");
            return;
        }
        this.p = n;
        bufferInfo.presentationTimeUs = n;
        try {
            this.b.writeSampleData(this.h, byteBuffer, bufferInfo);
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXCMP4Muxer", "write sample IllegalStateException: " + ex);
        }
    }
    
    private void a(final boolean b, final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        if (byteBuffer == null || bufferInfo == null) {
            return;
        }
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(byteBuffer.capacity());
        byteBuffer.rewind();
        if (bufferInfo.size > 0) {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.size);
        }
        allocateDirect.rewind();
        allocateDirect.put(byteBuffer);
        final MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
        bufferInfo2.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
        final a a = new a(allocateDirect, bufferInfo2);
        if (b) {
            if (this.l.size() < 200) {
                this.l.add(a);
            }
            else {
                TXCLog.e("TXCMP4Muxer", "drop video frame. video cache size is larger than 200");
            }
        }
        else if (this.m.size() < 300) {
            this.m.add(a);
        }
        else {
            TXCLog.e("TXCMP4Muxer", "drop audio frame. audio cache size is larger than 300");
        }
    }
    
    private long e() {
        long presentationTimeUs = 0L;
        if (this.l.size() > 0) {
            presentationTimeUs = this.l.peek().b().presentationTimeUs;
        }
        if (this.m.size() > 0) {
            final a a = this.m.peek();
            if (a != null && a.b() != null) {
                final long presentationTimeUs2 = this.m.peek().b().presentationTimeUs;
                if (presentationTimeUs > presentationTimeUs2) {
                    presentationTimeUs = presentationTimeUs2;
                }
            }
        }
        return presentationTimeUs;
    }
    
    private void f() {
        while (this.l.size() > 0) {
            final a a = this.l.poll();
            this.c(a.a(), a.b());
        }
        while (this.m.size() > 0) {
            final a a2 = this.m.poll();
            this.d(a2.a(), a2.b());
        }
    }
    
    private static class a
    {
        ByteBuffer a;
        MediaCodec.BufferInfo b;
        
        public a(final ByteBuffer a, final MediaCodec.BufferInfo b) {
            this.a = a;
            this.b = b;
        }
        
        public ByteBuffer a() {
            return this.a;
        }
        
        public MediaCodec.BufferInfo b() {
            return this.b;
        }
    }
}
