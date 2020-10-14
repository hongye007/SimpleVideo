package com.tencent.liteav.basic.structs;

import android.opengl.*;
import java.nio.*;

public class TXSVideoFrame
{
    public ByteBuffer buffer;
    public byte[] data;
    public int width;
    public int height;
    public int frameType;
    public int rotation;
    public long pts;
    public int textureId;
    public Object eglContext;
    
    public void finalize() throws Throwable {
        this.release();
        super.finalize();
    }
    
    public TXSVideoFrame clone() {
        final TXSVideoFrame txsVideoFrame = new TXSVideoFrame();
        txsVideoFrame.width = this.width;
        txsVideoFrame.height = this.height;
        txsVideoFrame.frameType = this.frameType;
        txsVideoFrame.rotation = this.rotation;
        txsVideoFrame.pts = this.pts;
        txsVideoFrame.data = this.data;
        txsVideoFrame.textureId = this.textureId;
        txsVideoFrame.eglContext = this.eglContext;
        txsVideoFrame.nativeClone(this.buffer);
        return txsVideoFrame;
    }
    
    public void loadYUVArray(final byte[] array) {
        if (array == null || array.length < this.width * this.height * 3 / 2) {
            return;
        }
        if (this.buffer == null) {
            GLES20.glReadPixels(0, 0, this.width, this.height * 3 / 8, 6408, 5121, (Buffer)ByteBuffer.wrap(array));
        }
        else {
            this.nativeLoadArrayFromBuffer(array, this.width * this.height * 3 / 2);
        }
    }
    
    public void loadYUVBufferFromGL() {
        this.nativeLoadBufferFromGL(this.width, this.height);
    }
    
    public void loadNV21BufferFromI420Buffer() {
        this.nativeLoadNV21BufferFromI420Buffer(this.width, this.height);
    }
    
    public byte[] I420toNV21(final byte[] array, byte[] array2, final int n, final int n2) {
        if (array2 == null) {
            array2 = new byte[array.length];
        }
        final int n3 = n * n2;
        final int n4 = n3 + n3 / 4;
        System.arraycopy(array, 0, array2, 0, n3);
        for (int i = n3, n5 = n4, n6 = n3; i < n4; ++i, ++n5, n6 += 2) {
            array2[n6] = array[n5];
            array2[n6 + 1] = array[i];
        }
        return array2;
    }
    
    private native void nativeLoadNV21BufferFromI420Buffer(final int p0, final int p1);
    
    private native void nativeLoadArrayFromBuffer(final byte[] p0, final int p1);
    
    public native void release();
    
    public native void nativeClone(final ByteBuffer p0);
    
    private native void nativeLoadBufferFromGL(final int p0, final int p1);
    
    private native void nativeLoadArrayFromGL(final byte[] p0, final int p1, final int p2);
}
