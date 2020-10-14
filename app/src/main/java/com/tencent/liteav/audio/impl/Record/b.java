package com.tencent.liteav.audio.impl.Record;

import java.util.*;
import java.lang.ref.*;
import com.tencent.liteav.audio.*;
import android.annotation.*;
import com.tencent.liteav.basic.log.*;
import android.view.*;
import java.io.*;
import java.nio.*;
import android.media.*;
import com.tencent.liteav.basic.util.*;

public class b extends Thread
{
    private MediaCodec.BufferInfo a;
    private MediaCodecInfo b;
    private MediaFormat c;
    private MediaCodec d;
    private Vector<byte[]> e;
    private WeakReference<d> f;
    private volatile boolean g;
    private volatile boolean h;
    private final Object i;
    private long j;
    private int k;
    private int l;
    private int m;
    private byte[] n;
    
    @TargetApi(16)
    public b() {
        super("TXAudioRecordThread");
        this.g = false;
        this.h = false;
        this.i = new Object();
        this.j = 0L;
        this.k = 48000;
        this.l = 1;
        this.m = 16;
    }
    
    public void a(final int n, final int k, final int l, final int m, final WeakReference<d> f) {
        this.f = f;
        this.a = new MediaCodec.BufferInfo();
        this.e = new Vector<byte[]>();
        this.k = k;
        this.l = l;
        this.m = m;
        this.b();
    }
    
    public void a(final byte[] array, final long n) {
        if (this.e != null && array != null) {
            synchronized (this.e) {
                if (this.e == null) {
                    return;
                }
                this.e.add(array);
            }
        }
        synchronized (this.i) {
            this.i.notify();
        }
    }
    
    public void a() {
        this.c();
    }
    
    private void b() {
        this.b = a("audio/mp4a-latm");
        if (this.b == null) {
            TXCLog.e("AudioCenter:TXCAudioHWEncoder", "Unable to find an appropriate codec for audio/mp4a-latm");
            return;
        }
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "selected codec: " + this.b.getName());
        int n = 32000;
        if (this.k >= 32000) {
            n = 64000;
        }
        (this.c = MediaFormat.createAudioFormat("audio/mp4a-latm", this.k, this.l)).setInteger("bitrate", n);
        this.c.setInteger("channel-count", this.l);
        this.c.setInteger("sample-rate", this.k);
        this.c.setInteger("aac-profile", 2);
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "format: " + this.c);
        try {
            this.d();
        }
        catch (Exception ex) {
            TXCLog.e("AudioCenter:TXCAudioHWEncoder", "start media codec failed.", ex);
        }
        this.start();
    }
    
    private void c() {
        this.h = true;
    }
    
    @TargetApi(16)
    private void d() throws IOException {
        if (this.d != null) {
            return;
        }
        (this.d = MediaCodec.createEncoderByType("audio/mp4a-latm")).configure(this.c, (Surface)null, (MediaCrypto)null, 1);
        this.d.start();
        TXCLog.i("AudioCenter:TXCAudioHWEncoder", "prepare finishing");
        this.g = true;
    }
    
    private void e() {
        if (this.d != null) {
            this.d.stop();
            this.d.release();
            this.d = null;
        }
        this.g = false;
    }
    
    @Override
    public void run() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        while (!this.h) {
            if (this.g) {
                boolean empty = false;
                synchronized (this.e) {
                    empty = this.e.isEmpty();
                }
                if (empty) {
                    try {
                        Thread.sleep(10L);
                    }
                    catch (InterruptedException ex2) {}
                }
                else {
                    byte[] array = null;
                    synchronized (this.e) {
                        array = this.e.remove(0);
                    }
                    if (array == null) {
                        continue;
                    }
                    try {
                        byteBuffer.clear();
                        if (array.length > byteBuffer.capacity()) {
                            byteBuffer = ByteBuffer.allocateDirect(array.length);
                        }
                        byteBuffer.clear();
                        byteBuffer.put(array);
                        byteBuffer.flip();
                        this.a(byteBuffer, array.length, this.f());
                    }
                    catch (Exception ex) {
                        TXCLog.e("AudioCenter:TXCAudioHWEncoder", "encode frame failed.", ex);
                    }
                }
            }
            else {
                synchronized (this.i) {
                    try {
                        this.i.wait();
                    }
                    catch (InterruptedException ex3) {}
                }
            }
        }
        this.e();
    }
    
    private void a(final ByteBuffer byteBuffer, final int n, final long n2) {
        if (this.h) {
            return;
        }
        final ByteBuffer[] inputBuffers = this.d.getInputBuffers();
        final int dequeueInputBuffer = this.d.dequeueInputBuffer(10000L);
        if (dequeueInputBuffer >= 0) {
            final ByteBuffer byteBuffer2 = inputBuffers[dequeueInputBuffer];
            byteBuffer2.clear();
            if (byteBuffer != null) {
                byteBuffer2.put(byteBuffer);
            }
            if (n <= 0) {
                TXCLog.i("AudioCenter:TXCAudioHWEncoder", "send BUFFER_FLAG_END_OF_STREAM");
                this.d.queueInputBuffer(dequeueInputBuffer, 0, 0, n2, 4);
            }
            else {
                this.d.queueInputBuffer(dequeueInputBuffer, 0, n, n2, 0);
            }
        }
        else if (dequeueInputBuffer == -1) {}
        ByteBuffer[] array = this.d.getOutputBuffers();
        int i;
        do {
            i = this.d.dequeueOutputBuffer(this.a, 10000L);
            if (i == -1) {
                continue;
            }
            if (i == -3) {
                array = this.d.getOutputBuffers();
            }
            else if (i == -2) {
                this.d.getOutputFormat();
            }
            else {
                if (i < 0) {
                    continue;
                }
                final ByteBuffer byteBuffer3 = array[i];
                if ((this.a.flags & 0x2) != 0x0) {
                    TXCLog.d("AudioCenter:TXCAudioHWEncoder", "drain:BUFFER_FLAG_CODEC_CONFIG");
                    this.a.size = 0;
                }
                if (this.a.size != 0) {
                    this.a.presentationTimeUs = this.f();
                    byteBuffer3.get(this.n = new byte[byteBuffer3.limit()]);
                    this.b(this.n, this.a.presentationTimeUs);
                    this.j = this.a.presentationTimeUs;
                }
                this.d.releaseOutputBuffer(i, false);
            }
        } while (i >= 0);
    }
    
    private long f() {
        long timeTick = TXCTimeUtil.getTimeTick();
        if (timeTick < this.j) {
            timeTick += this.j - timeTick;
        }
        return timeTick;
    }
    
    private static final MediaCodecInfo a(final String s) {
        TXCLog.v("AudioCenter:TXCAudioHWEncoder", "selectAudioCodec:");
        MediaCodecInfo mediaCodecInfo = null;
    Label_0130:
        for (int codecCount = MediaCodecList.getCodecCount(), i = 0; i < codecCount; ++i) {
            final MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
            if (codecInfo.isEncoder()) {
                final String[] supportedTypes = codecInfo.getSupportedTypes();
                for (int j = 0; j < supportedTypes.length; ++j) {
                    TXCLog.i("AudioCenter:TXCAudioHWEncoder", "supportedType:" + codecInfo.getName() + ",MIME=" + supportedTypes[j]);
                    if (supportedTypes[j].equalsIgnoreCase(s) && mediaCodecInfo == null) {
                        mediaCodecInfo = codecInfo;
                        break Label_0130;
                    }
                }
            }
        }
        return mediaCodecInfo;
    }
    
    private void b(final byte[] array, final long n) {
        if (null != this.f) {
            final d d = this.f.get();
            if (null != d) {
                d.onRecordEncData(array, n, this.k, this.l, this.m);
            }
        }
    }
    
    static {
        f.f();
    }
}
