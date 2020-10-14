package com.tencent.liteav.audio;

import java.lang.ref.*;
import android.content.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import com.tencent.liteav.audio.impl.Record.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;

public class TXCAudioUGCRecorder implements c
{
    private static final String TAG = "AudioCenter:TXCAudioUGCRecorder";
    private final int AAC_SAMPLE_NUM = 1024;
    private WeakReference<d> mWeakRecordListener;
    protected AtomicInteger mSampleRate;
    protected int mChannels;
    protected int mBits;
    protected int mAACFrameLength;
    protected int mReverbType;
    protected int mVoiceChangerType;
    protected int mAECType;
    protected Context mContext;
    protected boolean mIsEarphoneOn;
    private long mLastPTS;
    private float mVolume;
    private a mBGMRecorder;
    private boolean mEnableBGMRecord;
    private int mShouldClearAACDataCnt;
    private boolean mCurBGMRecordFlag;
    private AtomicReference<Float> mSpeedRate;
    private boolean mIsRunning;
    private boolean mIsPause;
    private boolean mIsMute;
    private final List<byte[]> mEncodedAudioList;
    private static final TXCAudioUGCRecorder INSTANCE;
    
    public static TXCAudioUGCRecorder getInstance() {
        return TXCAudioUGCRecorder.INSTANCE;
    }
    
    private TXCAudioUGCRecorder() {
        this.mSampleRate = new AtomicInteger(48000);
        this.mChannels = 1;
        this.mBits = 16;
        this.mAACFrameLength = 1024 * this.mChannels * this.mBits / 8;
        this.mReverbType = 0;
        this.mVoiceChangerType = 0;
        this.mAECType = 0;
        this.mIsEarphoneOn = false;
        this.mLastPTS = 0L;
        this.mVolume = 1.0f;
        this.mBGMRecorder = null;
        this.mEnableBGMRecord = false;
        this.mShouldClearAACDataCnt = 0;
        this.mCurBGMRecordFlag = false;
        this.mSpeedRate = new AtomicReference<Float>(1.0f);
        this.mIsRunning = false;
        this.mIsPause = false;
        this.mIsMute = false;
        this.mEncodedAudioList = new ArrayList<byte[]>();
        TXCAudioSysRecord.getInstance();
        this.nativeClassInit();
    }
    
    public int startRecord(final Context context) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "startRecord");
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        this.initEffector();
        TXCAudioSysRecord.getInstance().setAudioRecordListener(this);
        this.nativeStartAudioRecord(this.mSampleRate.get(), this.mChannels, this.mBits);
        this.mIsRunning = true;
        this.mLastPTS = 0L;
        return 0;
    }
    
    public int stopRecord() {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "stopRecord");
        TXCAudioSysRecord.getInstance().setAudioRecordListener(null);
        if (this.mBGMRecorder != null) {
            this.mBGMRecorder.a();
            this.mBGMRecorder = null;
        }
        this.nativeStopAudioRecord();
        this.nativeEnableMixMode(false);
        this.nativeSetVolume(1.0f);
        this.uninitEffector();
        synchronized (this.mEncodedAudioList) {
            this.mEncodedAudioList.clear();
        }
        this.mIsRunning = false;
        this.mLastPTS = 0L;
        this.mIsPause = false;
        this.mIsMute = false;
        return this.mShouldClearAACDataCnt = 0;
    }
    
    public void pause() {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "pause");
        TXCAudioEngine.getInstance().pauseLocalAudio();
        synchronized (this.mEncodedAudioList) {
            this.mIsPause = true;
        }
    }
    
    public void resume() {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "resume");
        TXCAudioEngine.getInstance().resumeLocalAudio();
        synchronized (this.mEncodedAudioList) {
            this.mIsPause = false;
        }
        this.nativeEnableMixMode(this.mEnableBGMRecord);
        if (this.mIsMute || this.mEnableBGMRecord) {
            this.nativeSetVolume(0.0f);
        }
        else {
            this.nativeSetVolume(this.mVolume);
        }
    }
    
    public synchronized void setListener(final d d) {
        if (null == d) {
            this.mWeakRecordListener = null;
        }
        else {
            this.mWeakRecordListener = new WeakReference<d>(d);
        }
    }
    
    public d getListener() {
        if (this.mWeakRecordListener != null) {
            return this.mWeakRecordListener.get();
        }
        return null;
    }
    
    public void setChannels(final int mChannels) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setChannels: " + mChannels);
        this.mChannels = mChannels;
    }
    
    public int getChannels() {
        return this.mChannels;
    }
    
    public void setSampleRate(final int n) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setSampleRate: " + n);
        this.mSampleRate.set(n);
    }
    
    public int getSampleRate() {
        return this.mSampleRate.get();
    }
    
    public synchronized void setReverbType(final int mReverbType) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setReverbType: " + mReverbType);
        this.nativeSetReverbType(this.mReverbType = mReverbType);
    }
    
    public void setAECType(final int maecType, final Context context) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setAECType: " + maecType);
        this.mAECType = maecType;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
    }
    
    public void setMute(final boolean mIsMute) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setMute: " + mIsMute);
        this.mIsMute = mIsMute;
        if (mIsMute) {
            this.nativeSetVolume(0.0f);
        }
        else {
            this.nativeSetVolume(this.mVolume);
        }
    }
    
    public void enableBGMRecord(final boolean mEnableBGMRecord) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "enableBGMRecord: " + mEnableBGMRecord);
        if (this.mEnableBGMRecord != mEnableBGMRecord && !mEnableBGMRecord) {
            this.mShouldClearAACDataCnt = 2;
        }
        this.mEnableBGMRecord = mEnableBGMRecord;
    }
    
    public boolean isRecording() {
        return this.mIsRunning;
    }
    
    public boolean isPaused() {
        return this.mIsPause;
    }
    
    public void onEncodedData(final byte[] array) {
        synchronized (this.mEncodedAudioList) {
            this.mEncodedAudioList.add(array);
        }
    }
    
    public synchronized void setVolume(final float mVolume) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setVolume: " + mVolume);
        this.mVolume = mVolume;
        if (this.mIsMute) {
            this.nativeSetVolume(0.0f);
        }
        else {
            this.nativeSetVolume(mVolume);
        }
    }
    
    public synchronized void setSpeedRate(final float n) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setSpeedRate: " + n);
        this.mSpeedRate.set(n);
        this.nativeSetSpeedRate(this.mSpeedRate.get());
    }
    
    public synchronized void clearCache() {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "clearCache");
        synchronized (this.mEncodedAudioList) {
            this.mEncodedAudioList.clear();
        }
    }
    
    @Override
    public void onAudioRecordStart() {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "sys audio record start");
    }
    
    @Override
    public void onAudioRecordStop() {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "sys audio record stop");
    }
    
    @Override
    public void onAudioRecordError(final int n, final String s) {
        TXCLog.e("AudioCenter:TXCAudioUGCRecorder", "sys audio record error: " + n + ", " + s);
        final d listener = this.getListener();
        if (listener != null) {
            listener.onRecordError(n, s);
        }
    }
    
    @Override
    public void onAudioRecordPCM(final byte[] array, final int n, final long n2) {
        long mLastPTS = n2;
        if (this.mLastPTS >= n2) {
            mLastPTS = this.mLastPTS + 2L;
        }
        byte[] array2 = null;
        do {
            synchronized (this.mEncodedAudioList) {
                if (!this.mEncodedAudioList.isEmpty() && !this.mIsPause) {
                    array2 = this.mEncodedAudioList.get(0);
                    this.mEncodedAudioList.remove(0);
                    if (this.mShouldClearAACDataCnt > 0) {
                        array2 = null;
                        --this.mShouldClearAACDataCnt;
                    }
                }
                else {
                    array2 = null;
                }
                if (array2 == null) {
                    continue;
                }
                this.mLastPTS = mLastPTS;
                final d listener = this.getListener();
                if (listener != null) {
                    listener.onRecordEncData(array2, mLastPTS, this.mSampleRate.get(), this.mChannels, this.mBits);
                }
                else {
                    TXCLog.e("AudioCenter:TXCAudioUGCRecorder", "onAudioRecordPCM listener is null");
                }
                final int value = this.mSampleRate.get();
                if (value <= 0) {
                    continue;
                }
                mLastPTS += (long)(1024000.0f * this.mSpeedRate.get() / value);
            }
        } while (array2 != null);
    }
    
    private synchronized void initEffector() {
        boolean b = false;
        if (this.mEnableBGMRecord || this.mAECType == 1) {
            b = true;
        }
        if (!b) {
            this.nativeSetReverbType(this.mReverbType);
            this.nativeSetChangerType(this.mVoiceChangerType);
            if (this.mIsMute) {
                this.nativeSetVolume(0.0f);
            }
            else {
                this.nativeSetVolume(this.mVolume);
            }
        }
        if (b) {
            this.nativeSetVolume(0.0f);
        }
        this.nativeEnableMixMode(b);
        this.nativeSetSpeedRate(this.mSpeedRate.get());
        this.nativeStartAudioProcess(this.mSampleRate.get(), this.mChannels, this.mBits);
    }
    
    private synchronized void uninitEffector() {
        this.nativeStopAudioProcess();
    }
    
    public synchronized void setChangerType(final int mVoiceChangerType) {
        TXCLog.i("AudioCenter:TXCAudioUGCRecorder", "setChangerType: " + mVoiceChangerType);
        this.nativeSetChangerType(this.mVoiceChangerType = mVoiceChangerType);
    }
    
    private native void nativeClassInit();
    
    private native void nativeEnableMixMode(final boolean p0);
    
    private native void nativeStartAudioRecord(final int p0, final int p1, final int p2);
    
    private native void nativeStopAudioRecord();
    
    private native void nativeStopAudioProcess();
    
    private native void nativeStartAudioProcess(final int p0, final int p1, final int p2);
    
    private native void nativeSetReverbType(final int p0);
    
    private native void nativeSetSpeedRate(final float p0);
    
    private native void nativeSetVolume(final float p0);
    
    private native void nativeSetChangerType(final int p0);
    
    static {
        f.f();
        INSTANCE = new TXCAudioUGCRecorder();
    }
}
