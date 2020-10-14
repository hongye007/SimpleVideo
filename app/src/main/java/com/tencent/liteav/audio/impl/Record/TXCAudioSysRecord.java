package com.tencent.liteav.audio.impl.Record;

import android.content.*;
import java.lang.ref.*;
import java.util.concurrent.atomic.*;
import com.tencent.liteav.basic.log.*;
import android.media.*;
import java.util.*;
import com.tencent.liteav.basic.util.*;

public class TXCAudioSysRecord implements Runnable
{
    private static final String TAG;
    private static TXCAudioSysRecord instance;
    private Context mContext;
    private int mSampleRate;
    private int mChannels;
    private int mBits;
    private int mAECType;
    private AudioRecord mMic;
    private byte[] mRecordBuffer;
    private WeakReference<c> mWeakRefListener;
    private Thread mRecordThread;
    private boolean mIsRunning;
    private boolean mIsCapFirstFrame;
    private boolean mSendMuteData;
    private AtomicBoolean mPause;
    private Object threadMutex;
    
    public static TXCAudioSysRecord getInstance() {
        if (TXCAudioSysRecord.instance == null) {
            synchronized (TXCAudioSysRecord.class) {
                if (TXCAudioSysRecord.instance == null) {
                    TXCAudioSysRecord.instance = new TXCAudioSysRecord();
                }
            }
        }
        return TXCAudioSysRecord.instance;
    }
    
    private TXCAudioSysRecord() {
        this.mSampleRate = 48000;
        this.mChannels = 1;
        this.mBits = 16;
        this.mAECType = 0;
        this.mRecordBuffer = null;
        this.mRecordThread = null;
        this.mIsRunning = false;
        this.mIsCapFirstFrame = false;
        this.mSendMuteData = false;
        this.mPause = new AtomicBoolean(false);
        this.threadMutex = new Object();
        this.nativeClassInit();
    }
    
    public synchronized void setAudioRecordListener(final c c) {
        if (c == null) {
            this.mWeakRefListener = null;
        }
        else {
            this.mWeakRefListener = new WeakReference<c>(c);
        }
    }
    
    public void start(final int mSampleRate, final int mChannels, final int mBits) {
        TXCLog.i(TXCAudioSysRecord.TAG, "start");
        synchronized (this.threadMutex) {
            this.stop();
            this.mSampleRate = mSampleRate;
            this.mChannels = mChannels;
            this.mBits = mBits;
            this.mIsRunning = true;
            (this.mRecordThread = new Thread(this, "AudioSysRecord Thread")).start();
        }
        TXCLog.i(TXCAudioSysRecord.TAG, "start ok");
    }
    
    public void stop() {
        TXCLog.i(TXCAudioSysRecord.TAG, "stop");
        synchronized (this.threadMutex) {
            this.mIsRunning = false;
            final long currentTimeMillis = System.currentTimeMillis();
            if (this.mRecordThread != null && this.mRecordThread.isAlive() && Thread.currentThread().getId() != this.mRecordThread.getId()) {
                try {
                    this.mRecordThread.join();
                }
                catch (Exception ex) {
                    TXCLog.e(TXCAudioSysRecord.TAG, "record stop Exception: " + ex.getMessage());
                }
            }
            TXCLog.i(TXCAudioSysRecord.TAG, "stop ok,stop record cost time(MS): " + (System.currentTimeMillis() - currentTimeMillis));
            this.mRecordThread = null;
        }
        TXCLog.i(TXCAudioSysRecord.TAG, "stop ok");
    }
    
    public void pause(final boolean mSendMuteData) {
        TXCLog.i(TXCAudioSysRecord.TAG, "system audio record pause");
        this.mPause.set(true);
        this.mSendMuteData = mSendMuteData;
    }
    
    public void resume() {
        TXCLog.i(TXCAudioSysRecord.TAG, "system audio record resume");
        this.mPause.set(false);
    }
    
    public synchronized boolean isRecording() {
        return this.mIsRunning;
    }
    
    private void init() {
        final int mSampleRate = this.mSampleRate;
        final int mChannels = this.mChannels;
        final int mBits = this.mBits;
        TXCLog.i(TXCAudioSysRecord.TAG, String.format("audio record sampleRate = %d, channels = %d, bits = %d, aectype = %d", mSampleRate, mChannels, mBits, this.mAECType));
        int n = 12;
        if (mChannels == 1) {
            n = 16;
        }
        int n2 = 2;
        if (mBits == 8) {
            n2 = 3;
        }
        final int minBufferSize = AudioRecord.getMinBufferSize(mSampleRate, n, n2);
        try {
            TXCLog.i(TXCAudioSysRecord.TAG, "audio record type: system normal");
            this.mMic = new AudioRecord(1, mSampleRate, n, n2, minBufferSize * 2);
        }
        catch (IllegalArgumentException ex) {
            TXCLog.e(TXCAudioSysRecord.TAG, "create AudioRecord failed.", ex);
        }
        if (this.mMic == null || this.mMic.getState() != 1) {
            TXCLog.e(TXCAudioSysRecord.TAG, "audio record: initialize the mic failed.");
            this.uninit();
            this.onRecordError(-1, "microphone permission denied!");
            return;
        }
        final int n3 = 1024 * mChannels * mBits / 8;
        if (n3 > minBufferSize) {
            this.mRecordBuffer = new byte[minBufferSize];
        }
        else {
            this.mRecordBuffer = new byte[n3];
        }
        TXCLog.i(TXCAudioSysRecord.TAG, String.format("audio record: mic open rate=%dHZ, channels=%d, bits=%d, buffer=%d/%d, state=%d", mSampleRate, mChannels, mBits, minBufferSize, this.mRecordBuffer.length, this.mMic.getState()));
        if (this.mMic != null) {
            try {
                this.mMic.startRecording();
            }
            catch (Exception ex2) {
                TXCLog.e(TXCAudioSysRecord.TAG, "mic startRecording failed.", ex2);
                this.onRecordError(-1, "start recording failed!");
            }
        }
    }
    
    private void uninit() {
        if (this.mMic != null) {
            TXCLog.i(TXCAudioSysRecord.TAG, "stop mic");
            try {
                this.mMic.setRecordPositionUpdateListener((AudioRecord.OnRecordPositionUpdateListener)null);
                this.mMic.stop();
                this.mMic.release();
                ((AudioManager)this.mContext.getSystemService("audio")).setMode(0);
            }
            catch (Exception ex) {
                TXCLog.e(TXCAudioSysRecord.TAG, "stop AudioRecord failed.", ex);
            }
        }
        this.mMic = null;
        this.mRecordBuffer = null;
        this.mIsCapFirstFrame = false;
    }
    
    private void onRecordPcmData(final byte[] array, final int n, final long n2) {
        c c = null;
        if (null != this.mWeakRefListener) {
            c = this.mWeakRefListener.get();
        }
        if (null != c) {
            c.onAudioRecordPCM(array, n, n2);
        }
        else {
            TXCLog.e(TXCAudioSysRecord.TAG, "onRecordPcmData:no callback");
        }
    }
    
    private void onRecordError(final int n, final String s) {
        c c = null;
        synchronized (this) {
            if (null != this.mWeakRefListener) {
                c = this.mWeakRefListener.get();
            }
        }
        if (null != c) {
            c.onAudioRecordError(n, s);
        }
        else {
            TXCLog.e(TXCAudioSysRecord.TAG, "onRecordError:no callback");
        }
    }
    
    private void onRecordStart() {
        c c = null;
        synchronized (this) {
            if (null != this.mWeakRefListener) {
                c = this.mWeakRefListener.get();
            }
        }
        if (null != c) {
            c.onAudioRecordStart();
        }
        else {
            TXCLog.e(TXCAudioSysRecord.TAG, "onRecordStart:no callback");
        }
    }
    
    private void onRecordStop() {
        c c = null;
        synchronized (this) {
            if (null != this.mWeakRefListener) {
                c = this.mWeakRefListener.get();
            }
        }
        if (null != c) {
            c.onAudioRecordStop();
        }
        else {
            TXCLog.e(TXCAudioSysRecord.TAG, "onRecordStop:no callback");
        }
    }
    
    @Override
    public void run() {
        if (!this.mIsRunning) {
            TXCLog.w(TXCAudioSysRecord.TAG, "audio record: abandom start audio sys record thread!");
            return;
        }
        this.onRecordStart();
        TXCLog.i(TXCAudioSysRecord.TAG, "start capture audio data ...,mIsRunning:" + this.mIsRunning + " Thread.interrupted:" + Thread.interrupted() + " mMic:" + this.mMic);
        this.init();
        int n = 0;
        int n2 = 0;
        while (this.mIsRunning && !Thread.interrupted() && this.mMic != null && n <= 5) {
            System.currentTimeMillis();
            final int read = this.mMic.read(this.mRecordBuffer, n2, this.mRecordBuffer.length - n2);
            if (read != this.mRecordBuffer.length - n2) {
                if (read <= 0) {
                    TXCLog.e(TXCAudioSysRecord.TAG, "read pcm error, len =" + read);
                    ++n;
                }
                else {
                    n2 += read;
                }
            }
            else {
                n = 0;
                n2 = 0;
                if (!this.mIsCapFirstFrame) {
                    this.onRecordError(-6, "First frame captured#");
                    this.mIsCapFirstFrame = true;
                }
                if (this.mSendMuteData) {
                    Arrays.fill(this.mRecordBuffer, (byte)0);
                }
                if (!this.mPause.get() && !this.mSendMuteData) {
                    continue;
                }
                this.onRecordPcmData(this.mRecordBuffer, this.mRecordBuffer.length, TXCTimeUtil.getTimeTick());
                this.nativeSendSysRecordAudioData(this.mRecordBuffer, this.mRecordBuffer.length, this.mSampleRate, this.mChannels, this.mBits);
            }
        }
        TXCLog.d(TXCAudioSysRecord.TAG, "stop capture audio data ...,mIsRunning:" + this.mIsRunning + " mMic:" + this.mMic + " nFailedCount:" + n);
        this.uninit();
        if (n > 5) {
            this.onRecordError(-1, "read data failed!");
        }
        else {
            this.onRecordStop();
        }
    }
    
    private native void nativeClassInit();
    
    private native void nativeSendSysRecordAudioData(final byte[] p0, final int p1, final int p2, final int p3, final int p4);
    
    static {
        TAG = "AudioCenter:" + TXCAudioSysRecord.class.getSimpleName();
        TXCAudioSysRecord.instance = null;
    }
}
