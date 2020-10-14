package com.tencent.rtmp.sharp.jni;

import java.nio.*;
import android.view.*;
import android.media.*;
import android.annotation.*;
import android.os.*;

public class MediaCodecDecoder
{
    private static final String TAG = "MediaCodecDecoder";
    private MediaCodec mAudioAACDecoder;
    private int mChannels;
    private int mSampleRate;
    ByteBuffer mInputBuffer;
    ByteBuffer mOutputBuffer;
    private MediaCodec.BufferInfo mAACDecBufferInfo;
    private ByteBuffer mDecInBuffer;
    private ByteBuffer mDecOutBuffer;
    private byte[] mTempBufDec;
    
    public MediaCodecDecoder() {
        this.mAudioAACDecoder = null;
        this.mChannels = 2;
        this.mSampleRate = 44100;
        this.mInputBuffer = null;
        this.mOutputBuffer = null;
        this.mAACDecBufferInfo = null;
        this.mDecInBuffer = ByteBuffer.allocateDirect(16384);
        this.mDecOutBuffer = ByteBuffer.allocateDirect(16384);
        this.mTempBufDec = new byte[16384];
    }
    
    @SuppressLint({ "NewApi" })
    public int createAACDecoder(final int n, final int n2) {
        try {
            this.mAudioAACDecoder = MediaCodec.createDecoderByType("audio/mp4a-latm");
            final MediaFormat audioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", n, n2);
            audioFormat.setInteger("sample-rate", n);
            audioFormat.setInteger("channel-count", n2);
            audioFormat.setInteger("aac-profile", 2);
            audioFormat.setByteBuffer("csd-0", ByteBuffer.wrap(new byte[] { 17, -112 }));
            this.mAudioAACDecoder.configure(audioFormat, (Surface)null, (MediaCrypto)null, 0);
            if (this.mAudioAACDecoder != null) {
                this.mAudioAACDecoder.start();
                this.mAACDecBufferInfo = new MediaCodec.BufferInfo();
                if (QLog.isColorLevel()) {
                    QLog.w("MediaCodecDecoder", 2, "createAACDecoder succeed!!! : (" + n + ", " + n2 + ")");
                }
            }
            return 0;
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "Error when creating aac decode stream");
            }
            return -1;
        }
    }
    
    public int decodeAACFrame(final int n) {
        this.mDecInBuffer.get(this.mTempBufDec, 0, n);
        int decodeInternalAACFrame = this.decodeInternalAACFrame(n);
        this.mDecOutBuffer.rewind();
        if (decodeInternalAACFrame > 0) {
            this.mDecOutBuffer.put(this.mTempBufDec, 0, decodeInternalAACFrame);
        }
        else {
            decodeInternalAACFrame = 0;
        }
        return decodeInternalAACFrame;
    }
    
    @SuppressLint({ "NewApi" })
    public int decodeInternalAACFrame(final int n) {
        while (true) {
            try {
                final int dequeueInputBuffer = this.mAudioAACDecoder.dequeueInputBuffer(200L);
                if (dequeueInputBuffer >= 0) {
                    if (Build.VERSION.SDK_INT >= 21) {
                        this.mInputBuffer = this.mAudioAACDecoder.getInputBuffer(dequeueInputBuffer);
                    }
                    else {
                        this.mInputBuffer = this.mAudioAACDecoder.getInputBuffers()[dequeueInputBuffer];
                    }
                    this.mInputBuffer.clear();
                    this.mInputBuffer.put(this.mTempBufDec, 0, n);
                    this.mDecInBuffer.rewind();
                    this.mAudioAACDecoder.queueInputBuffer(dequeueInputBuffer, 0, n, 0L, 0);
                }
                final int dequeueOutputBuffer = this.mAudioAACDecoder.dequeueOutputBuffer(this.mAACDecBufferInfo, 10000L);
                if (dequeueOutputBuffer < 0) {
                    break;
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    this.mOutputBuffer = this.mAudioAACDecoder.getOutputBuffer(dequeueOutputBuffer);
                }
                else {
                    this.mOutputBuffer = this.mAudioAACDecoder.getOutputBuffers()[dequeueOutputBuffer];
                }
                final int size = this.mAACDecBufferInfo.size;
                try {
                    this.mOutputBuffer.limit(size);
                    this.mOutputBuffer.get(this.mTempBufDec, 0, size);
                    this.mOutputBuffer.position(0);
                    this.mAudioAACDecoder.releaseOutputBuffer(dequeueOutputBuffer, false);
                    return size;
                }
                catch (Exception ex) {
                    if (QLog.isColorLevel()) {
                        QLog.e("TRAE", 2, "Error when decoding aac stream");
                    }
                }
            }
            catch (Exception ex2) {
                final int n2 = 0;
                if (QLog.isColorLevel()) {
                    QLog.e("TRAE", 2, "decode failed.");
                }
                return n2;
            }
        }
        return 0;
    }
    
    @SuppressLint({ "NewApi" })
    public int releaseAACDecoder() {
        try {
            if (this.mAudioAACDecoder != null) {
                this.mAudioAACDecoder.stop();
                this.mAudioAACDecoder.release();
                this.mAudioAACDecoder = null;
                if (QLog.isColorLevel()) {
                    QLog.w("MediaCodecDecoder", 2, "releaseAACDecoder, release aac decode stream succeed!!");
                }
                return 0;
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("TRAE", 2, "release aac decoder failed.");
            }
        }
        if (QLog.isColorLevel()) {
            QLog.e("TRAE", 2, "releaseAACDecoder, Error when releasing aac decode stream");
        }
        return -1;
    }
}
