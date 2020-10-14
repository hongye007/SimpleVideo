package com.tencent.liteav.editer;

import com.tencent.liteav.CameraProxy;

import java.util.concurrent.*;

import com.tencent.liteav.basic.log.*;
import android.os.*;
import java.io.*;
import android.view.*;
import java.util.*;
import java.nio.*;

import android.media.*;

public class a
{
    private i a;
    private g b;
    private u c;
    private com.tencent.liteav.editer.g d;
    private boolean e;
    private int f;
    private int g;
    private LinkedBlockingDeque<e> h;
    private TreeSet<Long> i;
    private MediaCodec j;
    private Long k;
    private final Object l;
    private int m;
    private t n;
    private ArrayList<e> o;
    private long p;
    private MediaFormat q;
    private int r;
    private Runnable s;
    
    public a() {
        this.l = new Object();
        this.p = -1L;
        this.s = new Runnable() {
            @Override
            public void run() {
                if (!com.tencent.liteav.editer.a.this.e) {
                    return;
                }
                com.tencent.liteav.editer.a.this.g();
            }
        };
        this.b = new g("HWAudioEncoder");
        this.h = new LinkedBlockingDeque<e>();
    }
    
    public void a(final u c) {
        this.c = c;
    }
    
    public void a(final t n) {
        this.n = n;
        this.p = -1L;
        if (this.o == null) {
            this.o = new ArrayList<e>();
        }
        else {
            this.o.clear();
        }
        switch (n.encoderType) {
            case 1: {
                this.q = this.c(n);
                break;
            }
            case 2:
            case 3: {
                this.a = new AudioSWEncoder();
                break;
            }
        }
        synchronized (this) {
            this.b.a(new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.editer.a.this.e) {
                        return;
                    }
                    com.tencent.liteav.editer.a.this.b(n);
                }
            });
        }
    }
    
    public void a() {
        synchronized (this) {
            this.b.a(new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.editer.a.this.e) {
                        com.tencent.liteav.editer.a.this.d();
                        com.tencent.liteav.editer.a.this.b.a().removeCallbacksAndMessages((Object)null);
                    }
                }
            });
        }
    }
    
    private void b(final t t) {
        TXCLog.i("AudioEncoder", "startAudioInner sampleRate:" + t.sampleRate + ",channel:" + t.channelCount);
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        this.h.clear();
        this.e = true;
        this.i = new TreeSet<Long>();
        this.k = 0L;
        if (this.n.encoderType == 1) {
            final MediaCodecInfo a = a("audio/mp4a-latm");
            if (a == null || this.q == null) {
                return;
            }
            final String name = a.getName();
            try {
                this.j = MediaCodec.createByCodecName(name);
            }
            catch (IOException ex) {
                TXCLog.e("AudioEncoder", "create codec failed.", ex);
            }
            this.j.configure(this.q, (Surface)null, (MediaCrypto)null, 1);
            this.j.start();
            this.b.a(this.s, 1L);
        }
        else {
            this.a.a();
            this.a.a(t);
        }
    }
    
    private void d() {
        TXCLog.i("AudioEncoder", "stopInner");
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        if (this.n.encoderType == 1) {
            if (this.j != null) {
                this.j.stop();
                this.j.release();
            }
        }
        else {
            this.a.b();
        }
        this.e = false;
    }
    
    public void b() {
        this.b = null;
    }
    
    public int a(final e e) {
        if (this.p == -1L) {
            this.p = e.e();
        }
        if (this.n.encoderType == 1) {
            this.c(e);
            return 1;
        }
        if ((e.f() & 0x4) != 0x0) {
            this.c(e);
            return 1;
        }
        return this.a(e, this.n.channelCount * 2048);
    }
    
    private int a(final e e, final int n) {
        int n2 = 0;
        this.r += e.g();
        this.o.add(e);
        if (this.r >= n) {
            final int n3 = this.r / n;
            final int n4 = this.r % n;
            n2 = n3;
            final byte[] array = new byte[this.r];
            int n5 = 0;
            for (int i = 0; i < this.o.size(); ++i) {
                final byte[] b = this.b(this.o.get(i));
                if (b.length > 0) {
                    System.arraycopy(b, 0, array, n5, b.length);
                    n5 += b.length;
                }
            }
            this.o.clear();
            int n6 = 0;
            int n7 = n;
            for (int j = 0; j < n3; ++j) {
                final byte[] copyOfRange = Arrays.copyOfRange(array, n6, n7);
                n6 += n;
                n7 += n;
                this.c(this.a(copyOfRange));
            }
            if (n4 > 0) {
                final byte[] copyOfRange2 = Arrays.copyOfRange(array, n6, array.length);
                this.o.add(this.a(copyOfRange2));
                this.r = copyOfRange2.length;
            }
            else {
                this.r = 0;
            }
        }
        return n2;
    }
    
    private e a(final byte[] array) {
        final ByteBuffer wrap = ByteBuffer.wrap(array);
        final e e = new e();
        e.a(wrap);
        e.d(array.length);
        return e;
    }
    
    public byte[] b(final e e) {
        final ByteBuffer b = e.b();
        final int g = e.g();
        final byte[] array = new byte[g];
        b.order(ByteOrder.nativeOrder());
        b.position(0);
        b.limit(g);
        b.get(array, 0, g);
        return array;
    }
    
    private Long e() {
        long p = this.p;
        if (this.m != 0 && this.n != null && this.n.sampleRate != 0) {
            p = this.p + 1024000000L * this.m / this.n.sampleRate;
        }
        ++this.m;
        return p;
    }
    
    private void c(final e e) {
        ++this.f;
        try {
            this.h.put(e);
        }
        catch (InterruptedException ex) {
            TXCLog.e("AudioEncoder", "put frame to queue failed.", ex);
        }
        synchronized (this.l) {
            if (this.i != null) {
                long longValue = this.e();
                final boolean m = CameraProxy.i.a().m();
                final boolean b = CameraProxy.g.a().b();
                if (m && !b) {
                    longValue += CameraProxy.c.a().f();
                }
                this.i.add(longValue);
            }
        }
        while (this.h.size() > 0) {
            if (!this.e) {
                return;
            }
            e f = null;
            if (this.n.encoderType == 1) {
                f = this.f();
                if (f == null) {
                    if (this.d != null) {
                        this.d.a(this.h.size());
                        continue;
                    }
                    continue;
                }
            }
            e e2 = null;
            try {
                e2 = this.h.take();
            }
            catch (InterruptedException ex2) {
                TXCLog.e("AudioEncoder", "take from queue failed.", ex2);
            }
            if (e2 == null) {
                continue;
            }
            if (this.n.encoderType == 1) {
                this.a(f, e2);
            }
            else {
                this.a.a(e);
                final e c = this.a.c();
                if (c == null) {
                    continue;
                }
                final MediaCodec.BufferInfo o = c.o();
                final ByteBuffer b2 = c.b();
                if (b2 == null || b2.remaining() == 0) {
                    TXCLog.d("AudioEncoder", "GOT end of stream");
                    if ((o.flags & 0x4) == 0x0 || this.c == null) {
                        continue;
                    }
                    this.c.a();
                }
                else {
                    o.presentationTimeUs = this.c();
                    if (this.c == null) {
                        continue;
                    }
                    if (b2 != null) {
                        this.c.a(b2, o);
                    }
                    if ((o.flags & 0x4) == 0x0) {
                        continue;
                    }
                    this.c.a();
                }
            }
        }
    }
    
    private e f() {
        if (Build.VERSION.SDK_INT < 16) {
            return null;
        }
        final int dequeueInputBuffer = this.j.dequeueInputBuffer(10000L);
        if (dequeueInputBuffer >= 0) {
            ByteBuffer inputBuffer;
            if (Build.VERSION.SDK_INT >= 21) {
                inputBuffer = this.j.getInputBuffer(dequeueInputBuffer);
            }
            else {
                inputBuffer = this.j.getInputBuffers()[dequeueInputBuffer];
            }
            inputBuffer.clear();
            return new e(inputBuffer, 0, 0L, dequeueInputBuffer, 0, 0);
        }
        return null;
    }
    
    private void a(final e e, final e e2) {
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        final int d = e.d();
        final ByteBuffer b = e.b();
        if (e2.p()) {
            this.j.queueInputBuffer(d, 0, 0, e2.e(), 4);
            return;
        }
        final ByteBuffer duplicate = e2.b().duplicate();
        duplicate.rewind();
        duplicate.limit(e2.g());
        b.rewind();
        if (e2.g() <= b.remaining()) {
            b.put(duplicate);
            this.j.queueInputBuffer(d, 0, e2.g(), e2.e(), 0);
            return;
        }
        final String s = "input size is larger than buffer capacity. should increate buffer capacity by setting MediaFormat.KEY_MAX_INPUT_SIZE while configure. mime = ";
        TXCLog.e("AudioEncoder", s);
        throw new IllegalArgumentException(s);
    }
    
    private void g() {
        if (Build.VERSION.SDK_INT < 16) {
            return;
        }
        if (this.j == null) {
            TXCLog.e("AudioEncoder", "onDecodeOutput, mMediaCodec is null");
            if (this.b != null) {
                this.b.a(this.s, 10L);
            }
            return;
        }
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        final ByteBuffer[] outputBuffers = this.j.getOutputBuffers();
        final int dequeueOutputBuffer = this.j.dequeueOutputBuffer(bufferInfo, 10000L);
        if (dequeueOutputBuffer == -1) {
            if (this.b != null) {
                this.b.b(this.s);
            }
            return;
        }
        if (dequeueOutputBuffer == -3) {
            this.j.getOutputBuffers();
        }
        else if (dequeueOutputBuffer == -2) {
            final MediaFormat outputFormat = this.j.getOutputFormat();
            if (this.c != null) {
                this.c.a(outputFormat);
            }
        }
        else if (dequeueOutputBuffer >= 0) {
            ByteBuffer outputBuffer;
            if (Build.VERSION.SDK_INT >= 21) {
                outputBuffer = this.j.getOutputBuffer(dequeueOutputBuffer);
            }
            else {
                outputBuffer = outputBuffers[dequeueOutputBuffer];
            }
            if (outputBuffer == null) {
                throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null.mime:");
            }
            final byte[] array = new byte[bufferInfo.size];
            outputBuffer.position(bufferInfo.offset);
            outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
            outputBuffer.get(array, 0, bufferInfo.size);
            if ((bufferInfo.flags & 0x2) == 0x2) {
                bufferInfo.size = 0;
            }
            if (this.c != null && bufferInfo.size != 0) {
                ++this.g;
                bufferInfo.presentationTimeUs = this.c();
                final MediaCodec.BufferInfo bufferInfo2 = new MediaCodec.BufferInfo();
                final ByteBuffer wrap = ByteBuffer.wrap(array);
                bufferInfo2.set(bufferInfo.offset, array.length, bufferInfo.presentationTimeUs, bufferInfo.flags);
                this.c.a(wrap, bufferInfo);
            }
            this.j.releaseOutputBuffer(dequeueOutputBuffer, false);
            if ((bufferInfo.flags & 0x4) != 0x0) {
                if (this.c != null) {
                    this.c.a();
                }
                return;
            }
        }
        if (this.b != null) {
            this.b.b(this.s);
        }
    }
    
    protected long c() {
        synchronized (this.l) {
            if (!this.i.isEmpty()) {
                this.k = this.i.pollFirst();
                return this.k;
            }
        }
        this.k += 100L;
        return this.k;
    }
    
    private MediaFormat c(final t n) {
        this.n = n;
        if (Build.VERSION.SDK_INT >= 16) {
            final MediaFormat audioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", n.sampleRate, n.channelCount);
            audioFormat.setInteger("bitrate", n.audioBitrate);
            audioFormat.setInteger("aac-profile", 2);
            final int n2 = 1024 * n.channelCount * 2;
            audioFormat.setInteger("max-input-size", (n2 > 102400) ? n2 : 102400);
            return audioFormat;
        }
        return null;
    }
    
    private static MediaCodecInfo a(final String s) {
        if (Build.VERSION.SDK_INT >= 16) {
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
        }
        return null;
    }
    
    public void a(final com.tencent.liteav.editer.g d) {
        TXCLog.i("AudioEncoder", "setPCMQueueCallback listener:" + d);
        this.d = d;
    }
}
