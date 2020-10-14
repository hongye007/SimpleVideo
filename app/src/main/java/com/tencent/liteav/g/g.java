package com.tencent.liteav.g;

import android.annotation.*;
import java.nio.*;
import java.util.concurrent.atomic.*;
import com.tencent.liteav.basic.log.*;
import java.io.*;
import android.view.*;
import android.media.*;
import android.os.*;

@TargetApi(19)
public class g implements b
{
    private MediaCodec b;
    private ByteBuffer[] c;
    private ByteBuffer[] d;
    private AtomicBoolean e;
    private long f;
    
    public g() {
        this.f = 1000L;
        this.e = new AtomicBoolean(false);
    }
    
    @Override
    public void a(final MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            TXCLog.e("TXHWAudioDecoder", "create AudioDecoder error format:" + mediaFormat);
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
            TXCLog.e("TXHWAudioDecoder", "configure AudioDecoder error");
            return;
        }
        this.b.configure(mediaFormat, (Surface)null, (MediaCrypto)null, 0);
    }
    
    @Override
    public void a() {
        if (this.b == null) {
            TXCLog.e("TXHWAudioDecoder", "start AudioDecoder error");
            return;
        }
        this.b.start();
        this.d = this.b.getInputBuffers();
        this.c = this.b.getOutputBuffers();
        this.e.getAndSet(true);
    }
    
    @Override
    public void b() {
        if (this.b == null) {
            TXCLog.e("TXHWAudioDecoder", "stop AudioDecoder error");
            return;
        }
        try {
            this.b.stop();
            this.b.release();
        }
        catch (IllegalStateException ex) {
            TXCLog.e("TXHWAudioDecoder", "audio decoder stop exception: " + ex);
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
            TXCLog.e("TXHWAudioDecoder", "audio dequeueInputBuffer exception: " + ex);
        }
        if (dequeueInputBuffer >= 0) {
            ByteBuffer inputBuffer;
            if (Build.VERSION.SDK_INT >= 21) {
                inputBuffer = this.b.getInputBuffer(dequeueInputBuffer);
            }
            else {
                inputBuffer = this.d[dequeueInputBuffer];
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
            if (dequeueOutputBuffer != -3) {
                if (dequeueOutputBuffer != -2) {
                    if (dequeueOutputBuffer >= 0) {
                        if (dequeueOutputBuffer >= 0) {
                            final e e = new e();
                            e.a(1);
                            e.a(bufferInfo.presentationTimeUs);
                            e.c(bufferInfo.flags);
                            e.d(bufferInfo.size);
                            final ByteBuffer allocateDirect = ByteBuffer.allocateDirect(bufferInfo.size);
                            if (Build.VERSION.SDK_INT >= 21) {
                                allocateDirect.put(this.b.getOutputBuffer(dequeueOutputBuffer));
                            }
                            else {
                                final ByteBuffer byteBuffer = this.b.getOutputBuffers()[dequeueOutputBuffer];
                                byteBuffer.rewind();
                                byteBuffer.limit(bufferInfo.size);
                                allocateDirect.put(byteBuffer);
                            }
                            allocateDirect.position(0);
                            if (e.g() >= 0) {
                                allocateDirect.limit(e.g());
                            }
                            e.a(allocateDirect);
                            this.b.releaseOutputBuffer(dequeueOutputBuffer, false);
                            return e;
                        }
                    }
                }
            }
        }
        return null;
    }
}
