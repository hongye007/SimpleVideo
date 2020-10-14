package com.tencent.liteav.videoediter.ffmpeg.jni;

import java.util.concurrent.atomic.*;
import java.nio.*;
import com.tencent.liteav.basic.log.*;

public class TXFFAudioDecoderJNI
{
    private static final String TAG = "TXFFAudioDecoderJNI";
    private long handle;
    private AtomicBoolean isInitSuccess;
    
    private native long init();
    
    private native int configureInput(final long p0, final int p1, final byte[] p2, final int p3, final int p4);
    
    private native FFDecodedFrame decode(final long p0, final byte[] p1, final long p2, final int p3);
    
    private native void release(final long p0);
    
    private native void configureOutput(final long p0, final int p1, final int p2);
    
    public TXFFAudioDecoderJNI() {
        this.handle = this.init();
        this.isInitSuccess = new AtomicBoolean(false);
    }
    
    public synchronized int configureInput(final int n, final ByteBuffer byteBuffer, final int n2, final int n3, final int n4) {
        final int configureInput = this.configureInput(this.handle, n, this.getBuffer(byteBuffer, n2), n3, n4);
        if (configureInput == 0) {
            TXCLog.i("TXFFAudioDecoderJNI", "init native decoder success!");
            this.isInitSuccess.set(true);
        }
        else {
            TXCLog.e("TXFFAudioDecoderJNI", "init native decoder error!");
            this.isInitSuccess.set(true);
        }
        return configureInput;
    }
    
    public synchronized void configureOutput(final int n, final int n2) {
        this.configureOutput(this.handle, n, n2);
    }
    
    public synchronized FFDecodedFrame decode(final byte[] array, final long n, final int n2) {
        if (this.isInitSuccess.get()) {
            return this.decode(this.handle, array, n, n2);
        }
        TXCLog.e("TXFFAudioDecoderJNI", "decoder not init yet!");
        return null;
    }
    
    public synchronized void release() {
        if (this.isInitSuccess.get()) {
            TXCLog.i("TXFFAudioDecoderJNI", "release decoder!");
            this.release(this.handle);
            this.isInitSuccess.set(false);
        }
        else {
            TXCLog.e("TXFFAudioDecoderJNI", "decoder not init yet!");
        }
    }
    
    private byte[] getBuffer(final ByteBuffer byteBuffer, final int n) {
        if (byteBuffer == null) {
            return null;
        }
        final byte[] array = new byte[n];
        byteBuffer.position(0);
        byteBuffer.get(array);
        return array;
    }
}
