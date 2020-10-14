package com.tencent.liteav.muxer;

import android.annotation.*;
import java.util.concurrent.*;
import com.tencent.liteav.basic.log.*;
import android.text.*;
import java.io.*;
import java.nio.*;
import android.media.*;

@TargetApi(18)
public class b implements com.tencent.liteav.muxer.a
{
    private int e;
    private MediaMuxer f;
    private String g;
    private MediaFormat h;
    private MediaFormat i;
    private int j;
    private int k;
    private boolean l;
    private boolean m;
    private ConcurrentLinkedQueue<a> n;
    private ConcurrentLinkedQueue<a> o;
    private long p;
    private long q;
    private long r;
    public static float a;
    public static float b;
    public static float c;
    public static float d;
    
    public b() {
        this.e = 2;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = 0;
        this.k = 0;
        this.l = false;
        this.m = false;
        this.n = new ConcurrentLinkedQueue<a>();
        this.o = new ConcurrentLinkedQueue<a>();
        this.p = -1L;
        this.q = -1L;
        this.r = -1L;
    }
    
    @Override
    public void a(final int e) {
        this.e = e;
    }
    
    @Override
    public synchronized void a(final MediaFormat h) {
        TXCLog.d("TXCMP4HWMuxer", "addVideoTrack:" + h);
        this.h = h;
        this.n.clear();
    }
    
    @Override
    public synchronized void b(final MediaFormat i) {
        TXCLog.d("TXCMP4HWMuxer", "addAudioTrack:" + i);
        this.i = i;
        this.o.clear();
    }
    
    @Override
    public synchronized boolean c() {
        return this.h != null;
    }
    
    @Override
    public synchronized boolean d() {
        return this.i != null;
    }
    
    @Override
    public synchronized int a() {
        if (this.g == null || this.g.isEmpty()) {
            TXCLog.e("TXCMP4HWMuxer", "target path not set yet!");
            return -1;
        }
        if (!this.c()) {
            TXCLog.e("TXCMP4HWMuxer", "video track not set yet!");
            return -2;
        }
        if (this.f != null) {
            TXCLog.w("TXCMP4HWMuxer", "start has been called. stop must be called before start");
            return 0;
        }
        TXCLog.d("TXCMP4HWMuxer", "start");
        try {
            this.f = new MediaMuxer(this.g, 0);
        }
        catch (IOException ex) {
            TXCLog.e("TXCMP4HWMuxer", "create MediaMuxer exception:" + ex);
            return -4;
        }
        if (this.h != null) {
            try {
                this.k = this.f.addTrack(this.h);
            }
            catch (IllegalArgumentException ex2) {
                TXCLog.e("TXCMP4HWMuxer", "addVideoTrack IllegalArgumentException: " + ex2);
                return -5;
            }
            catch (IllegalStateException ex3) {
                TXCLog.e("TXCMP4HWMuxer", "addVideoTrack IllegalStateException: " + ex3);
                return -6;
            }
        }
        if (this.i != null) {
            try {
                this.j = this.f.addTrack(this.i);
            }
            catch (IllegalArgumentException ex4) {
                TXCLog.e("TXCMP4HWMuxer", "addAudioTrack IllegalArgumentException: " + ex4);
                return -7;
            }
            catch (IllegalStateException ex5) {
                TXCLog.e("TXCMP4HWMuxer", "addAudioTrack IllegalStateException: " + ex5);
                return -8;
            }
        }
        this.f.start();
        this.p = -1L;
        this.l = true;
        this.m = false;
        this.q = -1L;
        this.r = -1L;
        return 0;
    }
    
    @Override
    public synchronized int b() {
        if (this.f != null) {
            TXCLog.d("TXCMP4HWMuxer", "stop. start flag = " + this.l + ", video key frame set = " + this.m);
            try {
                if (this.l && this.m) {
                    this.f.stop();
                }
                this.f.release();
            }
            catch (Exception ex) {
                TXCLog.e("TXCMP4HWMuxer", "muxer stop/release exception: " + ex);
                return -1;
            }
            finally {
                this.l = false;
                this.f = null;
                this.m = false;
                this.n.clear();
                this.o.clear();
                this.h = null;
                this.i = null;
                this.q = -1L;
                this.r = -1L;
            }
        }
        return 0;
    }
    
    @Override
    public synchronized void a(final String g) {
        this.g = g;
        if (!TextUtils.isEmpty((CharSequence)this.g)) {
            final File file = new File(this.g);
            final File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            if (file.exists()) {
                file.delete();
            }
            try {
                file.createNewFile();
            }
            catch (IOException ex) {
                TXCLog.e("TXCMP4HWMuxer", "create new file failed.", ex);
            }
        }
    }
    
    @Override
    public synchronized void b(final byte[] array, final int n, final int size, final long presentationTimeUs, final int flags) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(size);
        allocateDirect.put(array, n, size);
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = presentationTimeUs;
        bufferInfo.offset = 0;
        bufferInfo.size = size;
        bufferInfo.flags = flags;
        this.b(allocateDirect, bufferInfo);
    }
    
    @Override
    public synchronized void a(final byte[] array, final int n, final int size, final long presentationTimeUs, final int flags) {
        final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(size);
        allocateDirect.put(array, n, size);
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        bufferInfo.presentationTimeUs = presentationTimeUs;
        bufferInfo.offset = 0;
        bufferInfo.size = size;
        bufferInfo.flags = flags;
        this.a(allocateDirect, bufferInfo);
    }
    
    public synchronized void b(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        if (this.f == null) {
            this.a(true, byteBuffer, bufferInfo);
            TXCLog.w("TXCMP4HWMuxer", "cache frame before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            return;
        }
        if (this.p < 0L) {
            this.a(true, byteBuffer, bufferInfo);
            this.p = this.e();
            TXCLog.d("TXCMP4HWMuxer", "first frame offset = " + this.p);
            this.f();
        }
        else {
            this.c(byteBuffer, bufferInfo);
        }
    }
    
    @Override
    public synchronized void a(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        if (this.f == null || this.p < 0L) {
            TXCLog.d("TXCMP4HWMuxer", "cache sample before muexer ready. ptsUs: " + bufferInfo.presentationTimeUs);
            this.a(false, byteBuffer, bufferInfo);
            return;
        }
        this.d(byteBuffer, bufferInfo);
    }
    
    private void c(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        long n = bufferInfo.presentationTimeUs - this.p;
        if (n < 0L) {
            TXCLog.e("TXCMP4HWMuxer", "pts error! first frame offset timeus = " + this.p + ", current timeus = " + bufferInfo.presentationTimeUs);
            n = ((this.q > 0L) ? this.q : 0L);
        }
        if (n < this.q) {
            TXCLog.w("TXCMP4HWMuxer", "video is not in chronological order. current frame's pts(" + n + ") smaller than pre frame's pts(" + this.q + ")");
        }
        else {
            this.q = n;
        }
        if (this.e != 2) {
            if (this.e == 3) {
                n *= (long)com.tencent.liteav.muxer.b.b;
            }
            else if (this.e == 4) {
                n *= (long)com.tencent.liteav.muxer.b.a;
            }
            else if (this.e == 1) {
                n *= (long)com.tencent.liteav.muxer.b.c;
            }
            else if (this.e == 0) {
                n *= (long)com.tencent.liteav.muxer.b.d;
            }
        }
        bufferInfo.presentationTimeUs = n;
        try {
            byteBuffer.position(bufferInfo.offset);
            byteBuffer.limit(bufferInfo.offset + bufferInfo.size);
            this.f.writeSampleData(this.k, byteBuffer, bufferInfo);
            if ((bufferInfo.flags & 0x1) != 0x0) {
                this.m = true;
            }
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXCMP4HWMuxer", "write frame IllegalStateException: " + ex);
        }
        catch (IllegalArgumentException ex2) {
            TXCLog.e("TXCMP4HWMuxer", "write frame IllegalArgumentException: " + ex2);
        }
    }
    
    private void d(final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        long n = bufferInfo.presentationTimeUs - this.p;
        if (this.p < 0L || n < 0L) {
            TXCLog.w("TXCMP4HWMuxer", "drop sample. first frame offset timeus = " + this.p + ", current sample timeus = " + bufferInfo.presentationTimeUs);
            return;
        }
        if (n < this.r) {
            TXCLog.e("TXCMP4HWMuxer", "audio is not in chronological order. current audio's pts pts(" + n + ") must larger than pre audio's pts(" + this.r + ")");
            n = this.r + 1L;
        }
        else {
            this.r = n;
        }
        if (this.e != 2) {
            if (this.e == 3) {
                n *= (long)com.tencent.liteav.muxer.b.b;
            }
            else if (this.e == 4) {
                n *= (long)com.tencent.liteav.muxer.b.a;
            }
            else if (this.e == 1) {
                n *= (long)com.tencent.liteav.muxer.b.c;
            }
            else if (this.e == 0) {
                n *= (long)com.tencent.liteav.muxer.b.d;
            }
        }
        bufferInfo.presentationTimeUs = n;
        try {
            this.f.writeSampleData(this.j, byteBuffer, bufferInfo);
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXCMP4HWMuxer", "write sample IllegalStateException: " + ex);
        }
        catch (IllegalArgumentException ex2) {
            TXCLog.e("TXCMP4HWMuxer", "write sample IllegalArgumentException: " + ex2);
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
            if (this.n.size() < 200) {
                this.n.add(a);
            }
            else {
                TXCLog.e("TXCMP4HWMuxer", "drop video frame. video cache size is larger than 200");
            }
        }
        else if (this.o.size() < 600) {
            this.o.add(a);
        }
        else {
            TXCLog.e("TXCMP4HWMuxer", "drop audio frame. audio cache size is larger than 600");
        }
    }
    
    private long e() {
        long presentationTimeUs = 0L;
        if (this.n.size() > 0) {
            presentationTimeUs = this.n.peek().b().presentationTimeUs;
        }
        if (this.o.size() > 0) {
            final a a = this.o.peek();
            if (a != null && a.b() != null) {
                final long presentationTimeUs2 = this.o.peek().b().presentationTimeUs;
                if (presentationTimeUs > presentationTimeUs2) {
                    presentationTimeUs = presentationTimeUs2;
                }
            }
        }
        return presentationTimeUs;
    }
    
    private void f() {
        while (this.n.size() > 0) {
            final a a = this.n.poll();
            this.c(a.a(), a.b());
        }
        while (this.o.size() > 0) {
            final a a2 = this.o.poll();
            this.d(a2.a(), a2.b());
        }
    }
    
    static {
        com.tencent.liteav.muxer.b.a = 0.5f;
        com.tencent.liteav.muxer.b.b = 0.8f;
        com.tencent.liteav.muxer.b.c = 1.25f;
        com.tencent.liteav.muxer.b.d = 2.0f;
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
