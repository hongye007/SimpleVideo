package com.tencent.liteav.videoediter.audio;

import com.tencent.liteav.basic.log.*;

public class TXJNIAudioResampler
{
    private static final String TAG = "TXJNIAudioResampler";
    private volatile boolean isInitSuccess;
    private long handle;
    
    private native long init();
    
    private native void uninit(final long p0);
    
    private native void setSpeed(final long p0, final float p1);
    
    private native void setSampleRate(final long p0, final int p1, final int p2);
    
    private native void setChannelCount(final long p0, final int p1);
    
    private native short[] resample(final long p0, final short[] p1);
    
    private native short[] flushBuffer(final long p0);
    
    public TXJNIAudioResampler() {
        this.handle = this.init();
        if (this.handle != -1L) {
            this.isInitSuccess = true;
        }
    }
    
    public synchronized void destroy() {
        if (this.isInitSuccess) {
            this.isInitSuccess = false;
            this.uninit(this.handle);
            this.handle = -1L;
        }
    }
    
    public synchronized void setSpeed(final float n) {
        if (this.isInitSuccess) {
            this.setSpeed(this.handle, n);
        }
        else {
            TXCLog.e("TXJNIAudioResampler", "you must init first!!!");
        }
    }
    
    public synchronized void setSampleRate(final int n, final int n2) {
        if (this.isInitSuccess) {
            this.setSampleRate(this.handle, n, n2);
        }
        else {
            TXCLog.e("TXJNIAudioResampler", "you must init first!!!");
        }
    }
    
    public synchronized void setChannelCount(final int n) {
        if (this.isInitSuccess) {
            this.setChannelCount(this.handle, n);
        }
        else {
            TXCLog.e("TXJNIAudioResampler", "you must init first!!!");
        }
    }
    
    public synchronized short[] resample(final short[] array) {
        if (this.isInitSuccess) {
            return this.resample(this.handle, array);
        }
        TXCLog.e("TXJNIAudioResampler", "you must init first!!!");
        return array;
    }
    
    public synchronized short[] flushBuffer() {
        if (this.isInitSuccess) {
            return this.flushBuffer(this.handle);
        }
        TXCLog.e("TXJNIAudioResampler", "you must init first!!!");
        return null;
    }
}
