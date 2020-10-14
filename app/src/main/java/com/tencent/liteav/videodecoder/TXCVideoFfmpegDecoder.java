package com.tencent.liteav.videodecoder;

import java.nio.*;
import java.lang.ref.*;
import android.view.*;
import com.tencent.liteav.basic.structs.*;
import org.json.*;

public class TXCVideoFfmpegDecoder implements b
{
    private long mNativeDecoder;
    private long mNativeNotify;
    private ByteBuffer mSps;
    private ByteBuffer mPps;
    private boolean mFirstDec;
    private int mVideoWidth;
    private int mVideoHeight;
    private byte[] mRawData;
    private f mListener;
    
    @Override
    public void setListener(final f mListener) {
        this.mListener = mListener;
    }
    
    @Override
    public void setNotifyListener(final WeakReference<com.tencent.liteav.basic.b.b> weakReference) {
    }
    
    @Override
    public int config(final Surface surface) {
        return 0;
    }
    
    @Override
    public void decode(final TXSNALPacket txsnalPacket) {
        if (this.mFirstDec) {
            if (this.mSps != null && this.mPps != null) {
                final byte[] array = this.mSps.array();
                final byte[] array2 = this.mPps.array();
                final byte[] array3 = new byte[array.length + array2.length];
                System.arraycopy(array, 0, array3, 0, array.length);
                System.arraycopy(array2, 0, array3, array.length, array2.length);
                this.nativeDecode(array3, txsnalPacket.pts - 1L, txsnalPacket.dts - 1L, txsnalPacket.rotation);
            }
            this.mFirstDec = false;
        }
        this.nativeDecode(txsnalPacket.nalData, txsnalPacket.pts, txsnalPacket.dts, txsnalPacket.rotation);
    }
    
    @Override
    public int start(final ByteBuffer mSps, final ByteBuffer mPps, final boolean b, final boolean b2) {
        this.mSps = mSps;
        this.mPps = mPps;
        this.mFirstDec = true;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
        this.nativeInit(new WeakReference<TXCVideoFfmpegDecoder>(this), b);
        return 0;
    }
    
    @Override
    public void stop() {
        this.nativeRelease();
    }
    
    @Override
    public boolean isHevc() {
        return false;
    }
    
    @Override
    public int GetDecodeCost() {
        return 0;
    }
    
    public void config(final JSONArray jsonArray) {
    }
    
    @Override
    public void enableLimitDecCache(final boolean b) {
    }
    
    private static void postEventFromNative(final WeakReference<TXCVideoFfmpegDecoder> weakReference, final long n, final int n2, final int n3, final long n4, final long n5, final int n6) {
    }
    
    public void loadNativeData(final byte[] array, final long n, final int n2) {
        this.nativeLoadRawData(array, n, n2);
    }
    
    private native void nativeInit(final WeakReference<TXCVideoFfmpegDecoder> p0, final boolean p1);
    
    private native boolean nativeDecode(final byte[] p0, final long p1, final long p2, final long p3);
    
    private native void nativeRelease();
    
    private native void nativeLoadRawData(final byte[] p0, final long p1, final int p2);
    
    private static native void nativeClassInit();
    
    static {
        com.tencent.liteav.basic.util.f.f();
        nativeClassInit();
    }
}
