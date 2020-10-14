package com.tencent.rtmp.sharp.jni;

import android.content.*;
import java.nio.*;
import com.tencent.liteav.basic.util.*;
import android.os.*;
import android.view.*;
import android.media.*;
import android.annotation.*;
import java.io.*;

public class MediaCodecEncoder
{
    private static final String TAG = "MediaCodecEncoder";
    private Context mContext;
    private MediaCodec mAudioAACEncoder;
    private MediaFormat mAudioFormat;
    private MediaCodec.BufferInfo mAACEncBufferInfo;
    private ByteBuffer mInputBuffer;
    private ByteBuffer mOutputBuffer;
    private ByteBuffer[] mMediaInputBuffers;
    private ByteBuffer[] mMediaOutputBuffers;
    private ByteBuffer mEncInBuffer;
    private ByteBuffer mEncOutBuffer;
    private byte[] mTempBufEncIn;
    private byte[] mTempBufEncOut;
    private int mSampleRate;
    private int mChannels;
    private int mBitrate;
    private int nMaxBitRate;
    private boolean mFormatChangeFlag;
    private File mRecFileDump;
    private FileOutputStream mRecFileOut;
    private static boolean mDumpEnable;
    
    public MediaCodecEncoder() {
        this.mAudioAACEncoder = null;
        this.mAudioFormat = null;
        this.mAACEncBufferInfo = null;
        this.mInputBuffer = null;
        this.mOutputBuffer = null;
        this.mSampleRate = 48000;
        this.mChannels = 1;
        this.mBitrate = 32000;
        this.nMaxBitRate = 256000;
        this.mFormatChangeFlag = false;
        this.mRecFileDump = null;
        this.mRecFileOut = null;
        this.mContext = TXCCommonUtil.getAppContext();
        this.mEncInBuffer = ByteBuffer.allocateDirect(7680);
        this.mTempBufEncIn = new byte[7680];
        this.mEncOutBuffer = ByteBuffer.allocateDirect(this.nMaxBitRate * 2 / 8 / 50 + 100);
        this.mTempBufEncOut = new byte[this.nMaxBitRate * 2 / 8 / 50 + 100];
    }
    
    public MediaCodecEncoder(final Context mContext) {
        this.mAudioAACEncoder = null;
        this.mAudioFormat = null;
        this.mAACEncBufferInfo = null;
        this.mInputBuffer = null;
        this.mOutputBuffer = null;
        this.mSampleRate = 48000;
        this.mChannels = 1;
        this.mBitrate = 32000;
        this.nMaxBitRate = 256000;
        this.mFormatChangeFlag = false;
        this.mRecFileDump = null;
        this.mRecFileOut = null;
        this.mContext = mContext;
        this.mEncInBuffer = ByteBuffer.allocateDirect(7680);
        this.mTempBufEncIn = new byte[7680];
        this.mEncOutBuffer = ByteBuffer.allocateDirect(this.nMaxBitRate * 2 / 8 / 50 + 100);
        this.mTempBufEncOut = new byte[this.nMaxBitRate * 2 / 8 / 50 + 100];
    }
    
    private String getDumpFilePath(final String s) {
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "manufacture:" + Build.MANUFACTURER);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "MODEL:" + Build.MODEL);
        }
        if (this.mContext == null) {
            return null;
        }
        final File externalFilesDir = this.mContext.getExternalFilesDir((String)null);
        if (externalFilesDir == null) {
            return null;
        }
        final String string = externalFilesDir.getPath() + "/MF-" + Build.MANUFACTURER + "-M-" + Build.MODEL + "-" + s;
        final File file = new File(string);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "dump:" + string);
        }
        if (QLog.isColorLevel()) {
            QLog.w("TRAE", 2, "dump replace:" + string.replace(" ", "_"));
        }
        return string.replace(" ", "_");
    }
    
    private void addADTStoPacket(final byte[] array, final int n) {
        final int n2 = 2;
        int n3 = 3;
        if (this.mSampleRate == 48000) {
            n3 = 3;
        }
        else if (this.mSampleRate == 44100) {
            n3 = 4;
        }
        else if (this.mSampleRate == 32000) {
            n3 = 5;
        }
        else if (this.mSampleRate == 24000) {
            n3 = 6;
        }
        else if (this.mSampleRate == 16000) {
            n3 = 8;
        }
        final int mChannels = this.mChannels;
        array[0] = -1;
        array[1] = -7;
        array[2] = (byte)((n2 - 1 << 6) + (n3 << 2) + (mChannels >> 2));
        array[3] = (byte)(((mChannels & 0x3) << 6) + (n >> 11));
        array[4] = (byte)((n & 0x7FF) >> 3);
        array[5] = (byte)(((n & 0x7) << 5) + 31);
        array[6] = -4;
    }
    
    @SuppressLint({ "NewApi" })
    public int createAACEncoder(final int mSampleRate, final int mChannels, final int mBitrate) {
        try {
            this.mAudioAACEncoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
            (this.mAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", mSampleRate, mChannels)).setInteger("aac-profile", 2);
            this.mAudioFormat.setInteger("sample-rate", mSampleRate);
            this.mAudioFormat.setInteger("channel-count", mChannels);
            this.mAudioFormat.setInteger("bitrate", mBitrate);
            this.mAudioAACEncoder.configure(this.mAudioFormat, (Surface)null, (MediaCrypto)null, 1);
            if (this.mAudioAACEncoder != null) {
                this.mAudioAACEncoder.start();
                this.mAACEncBufferInfo = new MediaCodec.BufferInfo();
                this.mSampleRate = mSampleRate;
                this.mChannels = mChannels;
                this.mBitrate = mBitrate;
            }
            if (MediaCodecEncoder.mDumpEnable) {
                this.mRecFileDump = new File(this.getDumpFilePath("jnirecord.aac"));
                try {
                    this.mRecFileOut = new FileOutputStream(this.mRecFileDump);
                }
                catch (FileNotFoundException ex) {
                    if (QLog.isColorLevel()) {
                        QLog.e("MediaCodecEncoder", 2, "open jnirecord.aac file failed.");
                    }
                }
            }
            if (QLog.isColorLevel()) {
                QLog.w("MediaCodecEncoder", 2, "createAACEncoder succeed!!! : (" + mSampleRate + ", " + mChannels + ", " + mBitrate + ")");
            }
            return 0;
        }
        catch (Exception ex2) {
            if (QLog.isColorLevel()) {
                QLog.e("MediaCodecEncoder", 2, "create AAC Encoder failed.");
            }
            if (QLog.isColorLevel()) {
                QLog.e("MediaCodecEncoder", 2, "[ERROR] creating aac encode stream failed!!! : (" + mSampleRate + ", " + mChannels + ", " + mBitrate + ")");
            }
            return -1;
        }
    }
    
    @SuppressLint({ "NewApi" })
    public int encodeAACFrame(final int n) {
        if (this.mFormatChangeFlag) {
            this.mFormatChangeFlag = false;
            this.mAudioAACEncoder.stop();
            this.mAudioFormat.setInteger("bitrate", this.mBitrate);
            this.mAudioAACEncoder.configure(this.mAudioFormat, (Surface)null, (MediaCrypto)null, 1);
            this.mAudioAACEncoder.start();
        }
        this.mEncInBuffer.get(this.mTempBufEncIn, 0, n);
        int encodeInternalAACFrame = this.encodeInternalAACFrame(n);
        this.mEncOutBuffer.rewind();
        if (encodeInternalAACFrame > 0) {
            this.mEncOutBuffer.put(this.mTempBufEncOut, 0, encodeInternalAACFrame);
            if (MediaCodecEncoder.mDumpEnable && this.mRecFileOut != null) {
                try {
                    final int n2 = encodeInternalAACFrame + 7;
                    final byte[] array = new byte[n2];
                    this.addADTStoPacket(array, n2);
                    System.arraycopy(this.mTempBufEncOut, 0, array, 7, encodeInternalAACFrame);
                    this.mRecFileOut.write(array, 0, n2);
                }
                catch (IOException ex) {
                    if (QLog.isColorLevel()) {
                        QLog.e("MediaCodecEncoder", 2, "write file failed.");
                    }
                }
            }
        }
        else {
            encodeInternalAACFrame = 0;
        }
        return encodeInternalAACFrame;
    }
    
    @SuppressLint({ "NewApi" })
    public int encodeInternalAACFrame(final int n) {
        final int n2 = 0;
        int size2;
        try {
            final int dequeueInputBuffer = this.mAudioAACEncoder.dequeueInputBuffer(2000L);
            if (dequeueInputBuffer != -1) {
                if (Build.VERSION.SDK_INT >= 21) {
                    this.mInputBuffer = this.mAudioAACEncoder.getInputBuffer(dequeueInputBuffer);
                }
                else {
                    this.mMediaInputBuffers = this.mAudioAACEncoder.getInputBuffers();
                    this.mInputBuffer = this.mMediaInputBuffers[dequeueInputBuffer];
                }
                this.mInputBuffer.clear();
                this.mInputBuffer.put(this.mTempBufEncIn, 0, n);
                this.mAudioAACEncoder.queueInputBuffer(dequeueInputBuffer, 0, n, 0L, 0);
                this.mEncInBuffer.rewind();
            }
            final int dequeueOutputBuffer = this.mAudioAACEncoder.dequeueOutputBuffer(this.mAACEncBufferInfo, 0L);
            if (dequeueOutputBuffer < 0) {
                return n2;
            }
            final int size = this.mAACEncBufferInfo.size;
            if (Build.VERSION.SDK_INT >= 21) {
                this.mOutputBuffer = this.mAudioAACEncoder.getOutputBuffer(dequeueOutputBuffer);
            }
            else {
                this.mMediaOutputBuffers = this.mAudioAACEncoder.getOutputBuffers();
                this.mOutputBuffer = this.mMediaOutputBuffers[dequeueOutputBuffer];
            }
            if ((this.mAACEncBufferInfo.flags & 0x2) == 0x2) {
                size2 = 0;
            }
            else {
                size2 = this.mAACEncBufferInfo.size;
            }
            try {
                this.mOutputBuffer.position(this.mAACEncBufferInfo.offset);
                this.mOutputBuffer.limit(this.mAACEncBufferInfo.offset + size);
                this.mOutputBuffer.get(this.mTempBufEncOut, 0, size2);
                this.mOutputBuffer.position(0);
                this.mAudioAACEncoder.releaseOutputBuffer(dequeueOutputBuffer, false);
                return size2;
            }
            catch (Exception ex2) {
                if (QLog.isColorLevel()) {
                    QLog.e("MediaCodecEncoder", 2, "[ERROR] encoding aac stream failed!!!");
                }
            }
        }
        catch (Exception ex) {
            size2 = 0;
            if (QLog.isColorLevel()) {
                QLog.e("MediaCodecEncoder", 2, "encode failed." + ex.getMessage());
            }
        }
        return size2;
    }
    
    @SuppressLint({ "NewApi" })
    public int releaseAACEncoder() {
        try {
            if (this.mAudioAACEncoder != null) {
                this.mAudioAACEncoder.stop();
                this.mAudioAACEncoder.release();
                this.mAudioAACEncoder = null;
                if (QLog.isColorLevel()) {
                    QLog.w("MediaCodecEncoder", 2, "releaseAACEncoder, release aac encode stream succeed!!");
                }
                return 0;
            }
        }
        catch (Exception ex) {
            if (QLog.isColorLevel()) {
                QLog.e("MediaCodecEncoder", 2, "release aac encoder failed." + ex.getMessage());
            }
        }
        if (QLog.isColorLevel()) {
            QLog.e("MediaCodecEncoder", 2, "[ERROR] releaseAACEncoder, release aac encode stream failed!!!");
        }
        return -1;
    }
    
    @SuppressLint({ "NewApi" })
    public int setAACEncodeBitrate(final int mBitrate) {
        if (this.mAudioAACEncoder != null && this.mBitrate != mBitrate) {
            this.mFormatChangeFlag = true;
            this.mBitrate = mBitrate;
            if (QLog.isColorLevel()) {
                QLog.w("MediaCodecEncoder", 2, "Set AAC bitrate = " + mBitrate);
            }
        }
        return 0;
    }
    
    static {
        MediaCodecEncoder.mDumpEnable = false;
    }
}
