package com.tencent.rtmp.sharp.jni;

import java.nio.*;
import android.view.*;
import com.tencent.liteav.basic.log.*;
import java.io.*;
import android.util.*;
import android.media.*;
import android.os.*;

public class AudioDecoder
{
    private static final String TAG = "AudioDecoder";
    private String srcPath;
    private MediaCodec mediaDecode;
    private MediaExtractor mediaExtractor;
    private ByteBuffer[] decodeInputBuffers;
    private ByteBuffer[] decodeOutputBuffers;
    private MediaCodec.BufferInfo decodeBufferInfo;
    private OnCompleteListener onCompleteListener;
    private OnProgressListener onProgressListener;
    private long fileTotalMs;
    private RingBuffer decRingBuffer;
    int sampleRate;
    int channels;
    int nFrameSize;
    boolean IsTenFramesReady;
    int nFirstThreeFrameInfo;
    int m_nIndex;
    private boolean codeOver;
    
    public AudioDecoder() {
        this.mediaDecode = null;
        this.mediaExtractor = null;
        this.decodeInputBuffers = null;
        this.decodeOutputBuffers = null;
        this.decodeBufferInfo = null;
        this.onCompleteListener = null;
        this.onProgressListener = null;
        this.fileTotalMs = 0L;
        this.decRingBuffer = null;
        this.sampleRate = 0;
        this.channels = 0;
        this.nFrameSize = 3840;
        this.IsTenFramesReady = false;
        this.nFirstThreeFrameInfo = 3;
        this.m_nIndex = 0;
        this.codeOver = true;
    }
    
    public int getSampleRate() {
        return this.sampleRate;
    }
    
    public int getChannels() {
        return this.channels;
    }
    
    public long getFileTotalMs() {
        return this.fileTotalMs;
    }
    
    public int getFrameSize() {
        return this.nFrameSize;
    }
    
    public void setIOPath(final String srcPath) {
        this.srcPath = srcPath;
    }
    
    public void setIndex(final int nIndex) {
        this.m_nIndex = nIndex;
    }
    
    public int prepare(final int n) {
        if (this.srcPath == null) {
            return -1;
        }
        return this.initMediaDecode(n);
    }
    
    private int initMediaDecode(final int n) {
        try {
            (this.mediaExtractor = new MediaExtractor()).setDataSource(this.srcPath);
            if (this.mediaExtractor.getTrackCount() > 1) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " initMediaDecode mediaExtractor container video, getTrackCount: " + this.mediaExtractor.getTrackCount());
                }
                this.codeOver = true;
                return -2;
            }
            int i = 0;
            while (i < this.mediaExtractor.getTrackCount()) {
                final MediaFormat trackFormat = this.mediaExtractor.getTrackFormat(i);
                final String string = trackFormat.getString("mime");
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " initMediaDecode mediaExtractor audio type:" + string);
                }
                if (string.startsWith("audio/mpeg")) {
                    this.mediaExtractor.selectTrack(i);
                    (this.mediaDecode = MediaCodec.createDecoderByType(string)).configure(trackFormat, (Surface)null, (MediaCrypto)null, 0);
                    this.sampleRate = trackFormat.getInteger("sample-rate");
                    this.channels = trackFormat.getInteger("channel-count");
                    this.fileTotalMs = trackFormat.getLong("durationUs") / 1000L;
                    this.nFrameSize = this.sampleRate * this.channels * 2 * 20 / 1000;
                    this.decRingBuffer = new RingBuffer(n * this.nFrameSize);
                    if (QLog.isColorLevel()) {
                        QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " initMediaDecode open succeed, mp3 format:(" + this.sampleRate + "," + this.channels + "), fileTotalMs:" + this.fileTotalMs + "ms RingBufferFrame:" + n);
                        break;
                    }
                    break;
                }
                else {
                    ++i;
                }
            }
        }
        catch (IOException ex) {
            TXCLog.e("AudioDecoder", "init media decode failed.", ex);
            this.codeOver = true;
            return -1;
        }
        if (this.mediaDecode == null) {
            Log.e("AudioDecoder", "m_nIndex: " + this.m_nIndex + " initMediaDecode create mediaDecode failed");
            this.codeOver = true;
            return -1;
        }
        if (this.decRingBuffer == null) {
            Log.e("AudioDecoder", "m_nIndex: " + this.m_nIndex + " initMediaDecode create decRingBuffer failed");
            this.codeOver = true;
            return -1;
        }
        this.mediaDecode.start();
        this.decodeInputBuffers = this.mediaDecode.getInputBuffers();
        this.decodeOutputBuffers = this.mediaDecode.getOutputBuffers();
        this.decodeBufferInfo = new MediaCodec.BufferInfo();
        this.codeOver = false;
        this.IsTenFramesReady = false;
        this.nFirstThreeFrameInfo = 3;
        return 0;
    }
    
    private void srcAudioFormatToPCM() {
        if (this.decodeInputBuffers.length <= 1) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM decodeInputBuffers.length to small," + this.decodeInputBuffers.length);
            }
            this.codeOver = true;
            return;
        }
        final int dequeueInputBuffer = this.mediaDecode.dequeueInputBuffer(-1L);
        if (dequeueInputBuffer < 0) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM decodeInputBuffers.inputIndex <0");
            }
            this.codeOver = true;
            return;
        }
        final int sdk_INT = Build.VERSION.SDK_INT;
        ByteBuffer inputBuffer;
        if (sdk_INT >= 21) {
            inputBuffer = this.mediaDecode.getInputBuffer(dequeueInputBuffer);
        }
        else {
            inputBuffer = this.decodeInputBuffers[dequeueInputBuffer];
        }
        inputBuffer.clear();
        final int sampleData = this.mediaExtractor.readSampleData(inputBuffer, 0);
        if (sampleData < 0) {
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM readSampleData over,end");
            }
            this.codeOver = true;
        }
        else {
            this.mediaDecode.queueInputBuffer(dequeueInputBuffer, 0, sampleData, 0L, 0);
            this.mediaExtractor.advance();
        }
        for (int i = this.mediaDecode.dequeueOutputBuffer(this.decodeBufferInfo, 10000L); i >= 0; i = this.mediaDecode.dequeueOutputBuffer(this.decodeBufferInfo, 10000L)) {
            ByteBuffer outputBuffer;
            if (sdk_INT >= 21) {
                outputBuffer = this.mediaDecode.getOutputBuffer(i);
            }
            else {
                outputBuffer = this.decodeOutputBuffers[i];
            }
            final byte[] array = new byte[this.decodeBufferInfo.size];
            try {
                outputBuffer.get(array);
            }
            catch (Exception ex) {
                if (QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " srcAudioFormatToPCM wrong outputIndex: " + i);
                }
                this.codeOver = true;
                return;
            }
            outputBuffer.clear();
            if (this.decRingBuffer != null && this.decodeBufferInfo.size > 0) {
                this.decRingBuffer.Push(array, this.decodeBufferInfo.size);
                if (this.nFirstThreeFrameInfo-- > 0 && QLog.isColorLevel()) {
                    QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " DecodeOneFrame size: " + this.decodeBufferInfo.size + " Remain: " + this.decRingBuffer.RemainRead() / this.nFrameSize);
                }
            }
            this.mediaDecode.releaseOutputBuffer(i, false);
            if (this.decodeBufferInfo.size > 0) {
                break;
            }
        }
    }
    
    public int SeekTo(int n) {
        if (this.mediaExtractor != null) {
            final long sampleTime = this.mediaExtractor.getSampleTime();
            n += this.decRingBuffer.RemainRead() * 20 / this.nFrameSize;
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " current PlayMs: " + sampleTime / 1000L + " SeekTo: " + n);
            }
            this.mediaExtractor.seekTo((long)(n * 1000), 2);
            final long sampleTime2 = this.mediaExtractor.getSampleTime();
            final int n2 = (int)((sampleTime2 - sampleTime) / 1000L);
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " total SeekTo time: " + n2 + " t2:" + sampleTime2 / 1000L);
            }
            return n2;
        }
        return 0;
    }
    
    public int ReadOneFrame(final byte[] array, final int n) {
        int n2 = -1;
        if (!this.IsTenFramesReady) {
            int n3 = 20;
            while (this.decRingBuffer.RemainRead() / this.nFrameSize < 10 && n3-- > 0 && !this.codeOver) {
                this.srcAudioFormatToPCM();
            }
            if (QLog.isColorLevel()) {
                QLog.w("TRAE", 2, "m_nIndex: " + this.m_nIndex + " 10 FramesReady Remain frame: " + this.decRingBuffer.RemainRead() / this.nFrameSize);
            }
            this.IsTenFramesReady = true;
        }
        int n4 = 20;
        while (!this.codeOver && this.decRingBuffer.RemainRead() / this.nFrameSize < 10 && n4-- > 0) {
            this.srcAudioFormatToPCM();
        }
        if (this.decRingBuffer.RemainRead() >= n) {
            this.decRingBuffer.Pop(array, n);
            n2 = n;
        }
        return n2;
    }
    
    public void release() {
        if (this.mediaDecode != null) {
            this.mediaDecode.stop();
            this.mediaDecode.release();
            this.mediaDecode = null;
        }
        if (this.mediaExtractor != null) {
            this.mediaExtractor.release();
            this.mediaExtractor = null;
        }
        if (this.onCompleteListener != null) {
            this.onCompleteListener = null;
        }
        if (this.onProgressListener != null) {
            this.onProgressListener = null;
        }
        this.showLog("release");
    }
    
    public void setOnCompleteListener(final OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }
    
    public void setOnProgressListener(final OnProgressListener onProgressListener) {
        this.onProgressListener = onProgressListener;
    }
    
    private void showLog(final String s) {
        Log.e("AudioCodec", s);
    }
    
    public interface OnProgressListener
    {
        void progress();
    }
    
    public interface OnCompleteListener
    {
        void completed();
    }
}
