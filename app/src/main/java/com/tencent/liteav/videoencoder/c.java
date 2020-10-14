package com.tencent.liteav.videoencoder;

import com.tencent.liteav.basic.module.*;
import com.tencent.liteav.basic.c.*;
import org.json.*;
import java.nio.*;
import com.tencent.liteav.basic.structs.*;
import android.media.*;

public class c extends a
{
    protected d mListener;
    protected int mInputWidth;
    protected int mInputHeight;
    protected int mOutputWidth;
    protected int mOutputHeight;
    protected h mInputFilter;
    protected int mInputTextureID;
    protected h mEncodeFilter;
    protected boolean mInit;
    protected Object mGLContextExternal;
    private long mVideoGOPEncode;
    private boolean mEncodeFirstGOP;
    protected int mStreamType;
    protected int mRotation;
    protected JSONArray mEncFmt;
    protected boolean mEnableXMirror;
    
    public c() {
        this.mListener = null;
        this.mInputWidth = 0;
        this.mInputHeight = 0;
        this.mOutputWidth = 0;
        this.mOutputHeight = 0;
        this.mInputTextureID = -1;
        this.mGLContextExternal = null;
        this.mVideoGOPEncode = 0L;
        this.mEncodeFirstGOP = false;
        this.mStreamType = 2;
        this.mRotation = 0;
        this.mEncFmt = null;
        this.mEnableXMirror = false;
    }
    
    public int start(final TXSVideoEncoderParam txsVideoEncoderParam) {
        if (txsVideoEncoderParam != null) {
            this.mOutputWidth = txsVideoEncoderParam.width;
            this.mOutputHeight = txsVideoEncoderParam.height;
            this.mInputWidth = txsVideoEncoderParam.width;
            this.mInputHeight = txsVideoEncoderParam.height;
            this.mGLContextExternal = txsVideoEncoderParam.glContext;
            this.mStreamType = txsVideoEncoderParam.streamType;
            this.mEncFmt = txsVideoEncoderParam.encFmt;
        }
        this.mVideoGOPEncode = 0L;
        this.mEncodeFirstGOP = false;
        return 10000002;
    }
    
    public void stop() {
    }
    
    public void setListener(final d mListener) {
        this.mListener = mListener;
    }
    
    public void setFPS(final int n) {
    }
    
    public void setBitrate(final int n) {
    }
    
    public void setBitrateFromQos(final int n, final int n2) {
    }
    
    public void setRotation(final int mRotation) {
        this.mRotation = mRotation;
    }
    
    public void setEncodeIdrFpsFromQos(final int n) {
    }
    
    public void setRPSRefBitmap(final int n, final int n2, final long n3) {
    }
    
    public void restartIDR() {
    }
    
    public void enableNearestRPS(final int n) {
    }
    
    public double getRealFPS() {
        return 0.0;
    }
    
    public long getRealBitrate() {
        return 0L;
    }
    
    public int getVideoWidth() {
        return this.mOutputWidth;
    }
    
    public int getVideoHeight() {
        return this.mOutputHeight;
    }
    
    public int getEncodeCost() {
        return 0;
    }
    
    public long pushVideoFrame(final int n, final int n2, final int n3, final long n4) {
        return 10000002L;
    }
    
    public long pushVideoFrameSync(final int n, final int n2, final int n3, final long n4) {
        return 10000002L;
    }
    
    public long pushVideoFrameAsync(final int n, final int n2, final int n3, final long n4) {
        return 10000002L;
    }
    
    public void signalEOSAndFlush() {
    }
    
    protected void callDelegate(final int n) {
        this.callDelegate(null, 0, 0L, 0L, 0L, 0L, 0L, 0L, n, null, null);
    }
    
    protected void callDelegate(final byte[] nalData, final int nalType, final long gopIndex, final long gopFrameIndex, final long frameIndex, final long refFremeIndex, final long pts, final long dts, final int n, final ByteBuffer byteBuffer, final MediaCodec.BufferInfo bufferInfo) {
        final ByteBuffer buffer = (byteBuffer == null) ? null : byteBuffer.asReadOnlyBuffer();
        final MediaCodec.BufferInfo info = (bufferInfo == null) ? null : new MediaCodec.BufferInfo();
        if (info != null) {
            info.set(bufferInfo.offset, bufferInfo.size, bufferInfo.presentationTimeUs, bufferInfo.flags);
        }
        final d mListener = this.mListener;
        if (mListener != null) {
            final TXSNALPacket txsnalPacket = new TXSNALPacket();
            txsnalPacket.nalData = nalData;
            txsnalPacket.nalType = nalType;
            txsnalPacket.gopIndex = gopIndex;
            txsnalPacket.gopFrameIndex = gopFrameIndex;
            txsnalPacket.frameIndex = frameIndex;
            txsnalPacket.refFremeIndex = refFremeIndex;
            txsnalPacket.pts = pts;
            txsnalPacket.dts = dts;
            txsnalPacket.buffer = buffer;
            txsnalPacket.streamType = this.mStreamType;
            if (info != null) {
                txsnalPacket.info = info;
            }
            mListener.onEncodeNAL(txsnalPacket, n);
            if (nalType == 0) {
                if (this.mVideoGOPEncode != 0L) {
                    this.mEncodeFirstGOP = true;
                    this.setStatusValue(4006, this.mVideoGOPEncode);
                }
                this.mVideoGOPEncode = 1L;
            }
            else {
                ++this.mVideoGOPEncode;
                if (!this.mEncodeFirstGOP) {
                    this.setStatusValue(4006, this.mVideoGOPEncode);
                }
            }
        }
    }
    
    protected void callDelegate(final MediaFormat mediaFormat) {
        if (this.mListener != null) {
            this.mListener.onEncodeFormat(mediaFormat);
        }
    }
    
    protected void onEncodeFinished(final int n, final long n2, final long n3) {
        if (this.mListener != null) {
            this.mListener.onEncodeFinished(n, n2, n3);
        }
    }
    
    public void setXMirror(final boolean mEnableXMirror) {
        this.mEnableXMirror = mEnableXMirror;
    }
}
