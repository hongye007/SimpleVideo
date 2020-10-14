package com.tencent.liteav.videoencoder;

import com.tencent.liteav.basic.c.*;
import com.tencent.liteav.basic.log.*;
import java.lang.ref.*;
import java.nio.*;
import android.media.*;
import android.opengl.*;
import com.tencent.liteav.beauty.b.*;
import com.tencent.liteav.basic.util.*;

public class TXCSWVideoEncoder extends c
{
    private static final String TAG;
    private long mNativeEncoder;
    private int mBitrate;
    private h mRawFrameFilter;
    private h mResizeFilter;
    private long mPTS;
    private static final boolean DEBUG = false;
    private int mPushIdx;
    private int mRendIdx;
    private int mPopIdx;
    
    public TXCSWVideoEncoder() {
        this.mNativeEncoder = 0L;
        this.mBitrate = 0;
        this.mPTS = 0L;
        this.mPushIdx = 0;
        this.mRendIdx = 0;
        this.mPopIdx = 0;
    }
    
    @Override
    public int start(final TXSVideoEncoderParam txsVideoEncoderParam) {
        super.start(txsVideoEncoderParam);
        final int mInputWidth = (txsVideoEncoderParam.width + 7) / 8 * 8;
        final int mInputHeight = (txsVideoEncoderParam.height + 1) / 2 * 2;
        if (mInputWidth != txsVideoEncoderParam.width || mInputHeight != txsVideoEncoderParam.height) {
            TXCLog.w(TXCSWVideoEncoder.TAG, "Encode Resolution not supportted, transforming...");
            TXCLog.w(TXCSWVideoEncoder.TAG, txsVideoEncoderParam.width + "x" + txsVideoEncoderParam.height + "-> " + mInputWidth + "x" + mInputHeight);
        }
        txsVideoEncoderParam.width = mInputWidth;
        txsVideoEncoderParam.height = mInputHeight;
        this.mOutputWidth = mInputWidth;
        this.mOutputHeight = mInputHeight;
        this.mInputWidth = mInputWidth;
        this.mInputHeight = mInputHeight;
        this.mRawFrameFilter = null;
        this.mResizeFilter = null;
        synchronized (this) {
            this.nativeSetBitrate(this.mNativeEncoder = this.nativeInit(new WeakReference<TXCSWVideoEncoder>(this)), this.mBitrate);
            this.nativeSetID(this.mNativeEncoder, this.getID());
            this.nativeStart(this.mNativeEncoder, txsVideoEncoderParam);
        }
        return 0;
    }
    
    @Override
    public void stop() {
        TXCLog.i(TXCSWVideoEncoder.TAG, "stop->enter with mRawFrameFilter:" + this.mRawFrameFilter);
        this.mGLContextExternal = null;
        long mNativeEncoder = 0L;
        synchronized (this) {
            mNativeEncoder = this.mNativeEncoder;
            this.mNativeEncoder = 0L;
        }
        this.nativeStop(mNativeEncoder);
        this.nativeRelease(mNativeEncoder);
        if (this.mRawFrameFilter != null) {
            this.mRawFrameFilter.e();
            this.mRawFrameFilter = null;
        }
        if (this.mResizeFilter != null) {
            this.mResizeFilter.e();
            this.mResizeFilter = null;
        }
        super.stop();
    }
    
    @Override
    public void setID(final String id) {
        super.setID(id);
        synchronized (this) {
            this.nativeSetID(this.mNativeEncoder, id);
        }
    }
    
    @Override
    public void setFPS(final int n) {
        synchronized (this) {
            this.nativeSetFPS(this.mNativeEncoder, n);
        }
    }
    
    @Override
    public void enableNearestRPS(final int n) {
        synchronized (this) {
            this.nativeEnableNearestRPS(this.mNativeEncoder, n);
        }
    }
    
    @Override
    public void setBitrate(final int mBitrate) {
        this.mBitrate = mBitrate;
        synchronized (this) {
            this.nativeSetBitrate(this.mNativeEncoder, mBitrate);
        }
    }
    
    @Override
    public void setBitrateFromQos(final int mBitrate, final int n) {
        this.mBitrate = mBitrate;
        synchronized (this) {
            this.nativeSetBitrateFromQos(this.mNativeEncoder, mBitrate, n);
        }
    }
    
    @Override
    public void setEncodeIdrFpsFromQos(final int n) {
        synchronized (this) {
            this.nativeSetEncodeIdrFpsFromQos(this.mNativeEncoder, n);
        }
    }
    
    @Override
    public double getRealFPS() {
        synchronized (this) {
            return (double)this.nativeGetRealFPS(this.mNativeEncoder);
        }
    }
    
    @Override
    public long getRealBitrate() {
        synchronized (this) {
            return this.nativegetRealBitrate(this.mNativeEncoder);
        }
    }
    
    @Override
    public long pushVideoFrame(final int n, final int n2, final int n3, final long n4) {
        return this.pushVideoFrameInternal(n, n2, n3, n4, false);
    }
    
    @Override
    public long pushVideoFrameSync(final int n, final int n2, final int n3, final long n4) {
        return this.pushVideoFrameInternal(n, n2, n3, n4, true);
    }
    
    @Override
    public long pushVideoFrameAsync(final int n, final int n2, final int n3, final long n4) {
        return this.pushVideoFrameInternal(n, n2, n3, n4, true);
    }
    
    @Override
    public void signalEOSAndFlush() {
        synchronized (this) {
            this.nativeSignalEOSAndFlush(this.mNativeEncoder);
        }
    }
    
    @Override
    public void setRPSRefBitmap(final int n, final int n2, final long n3) {
        synchronized (this) {
            this.nativeSetRPSRefBitmap(this.mNativeEncoder, n, n2, n3);
        }
    }
    
    @Override
    public void restartIDR() {
        synchronized (this) {
            this.nativeRestartIDR(this.mNativeEncoder);
        }
    }
    
    public static long getAndIncreateSeq() {
        return nativeGetAndIncreaseSeq();
    }
    
    public static long getAndIncreaseGopIndex() {
        return nativeGetAndIncreaseGopIndex();
    }
    
    private static void postEventFromNative(final WeakReference<TXCSWVideoEncoder> weakReference, final byte[] array, final int n, final long n2, final long n3, final long n4, final long n5, final long n6, final long n7, final int n8) {
        final TXCSWVideoEncoder txcswVideoEncoder = weakReference.get();
        if (txcswVideoEncoder != null) {
            txcswVideoEncoder.callDelegate(array, n, n2, n3, n4, n5, n6, n7, n8, null, null);
            if (array != null) {}
        }
    }
    
    private static void onEncodeFinishedFromNative(final WeakReference<TXCSWVideoEncoder> weakReference, final int n, final long n2, final long n3) {
        final TXCSWVideoEncoder txcswVideoEncoder = weakReference.get();
        if (txcswVideoEncoder != null) {
            txcswVideoEncoder.onEncodeFinished(n, n2, n3);
        }
    }
    
    private long pushVideoFrameInternal(final int n, final int mInputWidth, final int mInputHeight, final long mpts, final boolean b) {
        h mResizeFilter = this.mResizeFilter;
        h mRawFrameFilter = this.mRawFrameFilter;
        if (this.mGLContextExternal != null) {
            this.mInputWidth = mInputWidth;
            this.mInputHeight = mInputHeight;
            if (mResizeFilter == null) {
                mResizeFilter = new h();
                (this.mResizeFilter = mResizeFilter).c();
                mResizeFilter.a(true);
            }
            mResizeFilter.a(this.mOutputWidth, this.mOutputHeight);
            GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
            if (mResizeFilter != null) {
                final int n2 = (720 - this.mRotation) % 360;
                mResizeFilter.a(mInputWidth, mInputHeight, n2, null, ((n2 == 90 || n2 == 270) ? this.mOutputHeight : this.mOutputWidth) / (float)((n2 == 90 || n2 == 270) ? this.mOutputWidth : this.mOutputHeight), this.mEnableXMirror, false);
                mResizeFilter.a(n);
            }
            final int n3 = (mResizeFilter != null) ? mResizeFilter.l() : n;
            final int[] array = { 0 };
            this.mPTS = mpts;
            if (mRawFrameFilter == null) {
                TXCLog.i(TXCSWVideoEncoder.TAG, "pushVideoFrameInternal->create mRawFrameFilter");
                mRawFrameFilter = new w(1);
                (this.mRawFrameFilter = mRawFrameFilter).a(true);
                if (!mRawFrameFilter.c()) {
                    TXCLog.i(TXCSWVideoEncoder.TAG, "pushVideoFrameInternal->destroy mRawFrameFilter, init failed!");
                    this.mRawFrameFilter = null;
                    return 10000004L;
                }
                mRawFrameFilter.a(this.mOutputWidth, this.mOutputHeight);
                mRawFrameFilter.a(new h.a() {
                    @Override
                    public void a(final int n) {
                        synchronized (TXCSWVideoEncoder.this) {
                            if (TXCSWVideoEncoder.this.mListener != null) {
                                TXCSWVideoEncoder.this.mListener.onEncodeDataIn(TXCSWVideoEncoder.this.mStreamType);
                            }
                            if (b) {
                                TXCSWVideoEncoder.this.nativeEncodeSync(TXCSWVideoEncoder.this.mNativeEncoder, n3, TXCSWVideoEncoder.this.mOutputWidth, TXCSWVideoEncoder.this.mOutputHeight, TXCSWVideoEncoder.this.mPTS);
                            }
                            else {
                                TXCSWVideoEncoder.this.nativeEncode(TXCSWVideoEncoder.this.mNativeEncoder, n3, TXCSWVideoEncoder.this.mOutputWidth, TXCSWVideoEncoder.this.mOutputHeight, TXCSWVideoEncoder.this.mPTS);
                            }
                        }
                    }
                });
            }
            if (mRawFrameFilter == null) {
                return 10000004L;
            }
            GLES20.glViewport(0, 0, this.mOutputWidth, this.mOutputHeight);
            mRawFrameFilter.a(n3);
            final int n4 = array[0];
            if (n4 != 0) {
                this.callDelegate(n4);
            }
        }
        return 0L;
    }
    
    private native long nativeInit(final WeakReference<TXCSWVideoEncoder> p0);
    
    private native void nativeRelease(final long p0);
    
    private native int nativeStart(final long p0, final TXSVideoEncoderParam p1);
    
    private native void nativeStop(final long p0);
    
    private native void nativeSetID(final long p0, final String p1);
    
    private native int nativeEncode(final long p0, final int p1, final int p2, final int p3, final long p4);
    
    private native int nativeEncodeSync(final long p0, final int p1, final int p2, final int p3, final long p4);
    
    private native void nativeSetFPS(final long p0, final int p1);
    
    private native void nativeSetBitrate(final long p0, final int p1);
    
    private native void nativeSetBitrateFromQos(final long p0, final int p1, final int p2);
    
    private native void nativeSetEncodeIdrFpsFromQos(final long p0, final int p1);
    
    private native void nativeEnableNearestRPS(final long p0, final int p1);
    
    private native void nativeSignalEOSAndFlush(final long p0);
    
    private native long nativeGetRealFPS(final long p0);
    
    private native long nativegetRealBitrate(final long p0);
    
    private native void nativeSetRPSRefBitmap(final long p0, final int p1, final int p2, final long p3);
    
    private native void nativeRestartIDR(final long p0);
    
    private static native void nativeClassInit();
    
    private static native long nativeGetAndIncreaseSeq();
    
    private static native long nativeGetAndIncreaseGopIndex();
    
    static {
        TAG = TXCSWVideoEncoder.class.getSimpleName();
        f.f();
        nativeClassInit();
    }
}
