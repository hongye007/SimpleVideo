package com.tencent.liteav.muxer.jni;

import com.tencent.liteav.basic.log.*;
import java.nio.*;

public class TXSWMuxerJNI
{
    private long a;
    private volatile boolean b;
    private volatile boolean c;
    
    public TXSWMuxerJNI() {
        this.a = -1L;
        this.a = this.init();
        this.b = true;
    }
    
    public void a(final AVOptions avOptions) {
        if (this.b) {
            this.setAVParams(this.a, avOptions);
        }
        else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }
    
    public void a(final String s) {
        if (this.b) {
            this.setDstPath(this.a, s);
        }
        else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }
    
    public void a(final ByteBuffer byteBuffer, final int n, final ByteBuffer byteBuffer2, final int n2) {
        if (this.b) {
            this.setVideoCSD(this.a, this.b(byteBuffer, n), this.b(byteBuffer2, n2));
        }
        else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }
    
    public void a(final ByteBuffer byteBuffer, final int n) {
        if (this.b) {
            this.setAudioCSD(this.a, this.b(byteBuffer, n));
        }
        else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }
    
    public int a(final ByteBuffer byteBuffer, final int n, final int n2, final int n3, final int n4, final long n5) {
        if (!this.b) {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
            return -1;
        }
        if (this.c) {
            final int writeFrame = this.writeFrame(this.a, this.b(byteBuffer, n3), n, n2, n3, n4, n5);
            if (writeFrame != 0) {
                TXCLog.e("TXSWMuxerJNI", "Muxer write frame error!");
            }
            return writeFrame;
        }
        TXCLog.e("TXSWMuxerJNI", "Muxer isn't start yet!");
        return -1;
    }
    
    public int a() {
        if (this.b) {
            final int start = this.start(this.a);
            if (start == 0) {
                this.c = true;
            }
            else {
                TXCLog.e("TXSWMuxerJNI", "Start Muxer Error!!!");
            }
            return start;
        }
        TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        return -1;
    }
    
    public int b() {
        if (!this.b) {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
            return -1;
        }
        if (this.c) {
            this.c = false;
            final int stop = this.stop(this.a);
            if (stop != 0) {
                TXCLog.e("TXSWMuxerJNI", "Stop Muxer Error!!!");
            }
            return stop;
        }
        TXCLog.e("TXSWMuxerJNI", "Muxer isn't start yet!");
        return -1;
    }
    
    public void c() {
        if (this.b) {
            this.release(this.a);
            this.b = false;
            this.c = false;
        }
        else {
            TXCLog.e("TXSWMuxerJNI", "Muxer isn't init yet!");
        }
    }
    
    private byte[] b(final ByteBuffer byteBuffer, final int n) {
        if (byteBuffer == null) {
            return null;
        }
        final byte[] array = new byte[n];
        byteBuffer.get(array);
        return array;
    }
    
    private native long init();
    
    private native void setAVParams(final long p0, final AVOptions p1);
    
    private native void setDstPath(final long p0, final String p1);
    
    private native int writeFrame(final long p0, final byte[] p1, final int p2, final int p3, final int p4, final int p5, final long p6);
    
    private native void setAudioCSD(final long p0, final byte[] p1);
    
    private native void setVideoCSD(final long p0, final byte[] p1, final byte[] p2);
    
    private native int start(final long p0);
    
    private native int stop(final long p0);
    
    private native void release(final long p0);
    
    public static class AVOptions
    {
        public int videoWidth;
        public int videoHeight;
        public int videoGOP;
        public int audioSampleRate;
        public int audioChannels;
        
        public AVOptions() {
            this.videoWidth = 960;
            this.videoHeight = 540;
            this.videoGOP = 12;
            this.audioSampleRate = 0;
            this.audioChannels = 0;
        }
    }
}
