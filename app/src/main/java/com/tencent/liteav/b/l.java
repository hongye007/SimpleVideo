package com.tencent.liteav.b;

import android.annotation.*;
import java.nio.*;
import java.util.concurrent.atomic.*;
import com.tencent.liteav.basic.log.*;
import java.io.*;
import android.view.*;
import android.media.*;
import android.os.*;

@TargetApi(16)
public class l implements b
{
    private MediaCodec b;
    private ByteBuffer[] c;
    private ByteBuffer[] d;
    private AtomicBoolean e;
    private long f;
    
    public l() {
        this.f = 1000L;
        this.e = new AtomicBoolean(false);
    }
    
    @Override
    public void a(final MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.e("TXCombineVideoDecoder", "create VideoDecoder error format:" + mediaFormat);
            return;
        }
        final String string = mediaFormat.getString("mime");
        try {
            this.b = MediaCodec.createDecoderByType(string);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void a(final MediaFormat mediaFormat, final Surface surface) {
        if (mediaFormat == null) {
            TXCLog.e("TXCombineVideoDecoder", "configure VideoDecoder error");
            return;
        }
        TXCLog.i("TXCombineVideoDecoder", "format: " + mediaFormat + ", surface: " + surface + ", mMediaCodec: " + this.b);
        mediaFormat.setInteger("rotation-degrees", 0);
        this.b.configure(mediaFormat, surface, (MediaCrypto)null, 0);
    }
    
    @Override
    public void a() {
        TXCLog.i("TXCombineVideoDecoder", "start");
        if (this.b == null) {
            TXCLog.e("TXCombineVideoDecoder", "start VideoDecoder error");
            return;
        }
        this.b.start();
        this.c = this.b.getInputBuffers();
        this.d = this.b.getOutputBuffers();
        this.e.getAndSet(true);
    }
    
    @Override
    public void b() {
        TXCLog.i("TXCombineVideoDecoder", "stop");
        if (this.b == null) {
            TXCLog.e("TXCombineVideoDecoder", "stop VideoDecoder error");
            return;
        }
        try {
            this.b.stop();
            this.b.release();
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXCombineVideoDecoder", "video decoder stop exception: " + ex);
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
        int dequeueInputBuffer = -1;
        try {
            dequeueInputBuffer = this.b.dequeueInputBuffer(this.f);
        }
        catch (Exception ex) {
            TXCLog.e("TXCombineVideoDecoder", "video dequeueInputBuffer exception: " + ex);
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
        return null;
    }
    
    @Override
    public void a(final e e) {
        if (!this.e.get()) {
            return;
        }
        this.b.queueInputBuffer(e.d(), 0, e.g(), e.e(), e.f());
    }
    
    @Override
    public e d() {
        if (!this.e.get()) {
            return null;
        }
        final MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        final int dequeueOutputBuffer = this.b.dequeueOutputBuffer(bufferInfo, this.f);
        if (dequeueOutputBuffer != -1) {
            if (dequeueOutputBuffer == -3) {
                TXCLog.i("TXCombineVideoDecoder", "INFO_OUTPUT_BUFFERS_CHANGED info.size :" + bufferInfo.size);
            }
            else if (dequeueOutputBuffer == -2) {
                TXCLog.i("TXCombineVideoDecoder", "INFO_OUTPUT_FORMAT_CHANGED info.size :" + bufferInfo.size);
            }
            else if (dequeueOutputBuffer >= 0) {
                if (dequeueOutputBuffer >= 0) {
                    this.b.releaseOutputBuffer(dequeueOutputBuffer, true);
                    return new e(null, bufferInfo.size, bufferInfo.presentationTimeUs, dequeueOutputBuffer, bufferInfo.flags, 0);
                }
            }
        }
        return null;
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
        return e2;
    }
}
