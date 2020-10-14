package com.tencent.liteav.audio;

import java.lang.ref.*;
import com.tencent.liteav.audio.impl.Play.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;

public class TXCUGCBGMPlayer implements TXAudioEffectManager.TXMusicPlayObserver
{
    private static final String TAG = "AudioCenter:TXCUGCBGMPlayer";
    private WeakReference<e> mWeakListener;
    private static final int PLAY_SUCCESS = 0;
    private static final int PLAY_ERR_OPEN = -1;
    private boolean mIsRunning;
    private float mVolume;
    private float mSpeedRate;
    private long mStartTimeMS;
    private long mEndTimeMS;
    private long mSeekBytes;
    private int mBGMId;
    
    public static TXCUGCBGMPlayer getInstance() {
        return a.a();
    }
    
    private TXCUGCBGMPlayer() {
        this.mWeakListener = null;
        this.mIsRunning = false;
        this.mVolume = 1.0f;
        this.mSpeedRate = 1.0f;
        this.mStartTimeMS = 0L;
        this.mEndTimeMS = 0L;
        this.mSeekBytes = 0L;
        this.mBGMId = Integer.MIN_VALUE;
        TXCMultAudioTrackPlayer.getInstance();
    }
    
    public synchronized void setOnPlayListener(final e e) {
        if (e == null) {
            this.mWeakListener = null;
        }
        this.mWeakListener = new WeakReference<e>(e);
    }
    
    public void startPlay(final String s, final boolean publish) {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "startPlay:" + s + "record:" + publish);
        if (s == null || s.isEmpty()) {
            return;
        }
        if (this.mIsRunning) {
            TXCLog.w("AudioCenter:TXCUGCBGMPlayer", "BGM is playing, restarting...");
            this.stopPlay();
        }
        this.mSeekBytes = 0L;
        this.mIsRunning = true;
        final TXAudioEffectManager.AudioMusicParam audioMusicParam = new TXAudioEffectManager.AudioMusicParam(this.mBGMId, s);
        audioMusicParam.publish = publish;
        audioMusicParam.loopCount = 0;
        audioMusicParam.startTimeMS = this.mStartTimeMS;
        audioMusicParam.endTimeMS = this.mEndTimeMS;
        audioMusicParam.isShortFile = true;
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "start bgm play : filePath = " + s + " publish:" + publish + " startTimeMS:" + this.mStartTimeMS + " endTimeMS:" + this.mEndTimeMS + " isShortFile:" + audioMusicParam.isShortFile + "mVolume:" + this.mVolume);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(this.mBGMId, (int)(this.mVolume * 100.0f));
        TXAudioEffectManagerImpl.getCacheInstance().setMusicPlayoutSpeedRate(this.mBGMId, this.mSpeedRate);
        final boolean startPlayMusic = TXAudioEffectManagerImpl.getCacheInstance().startPlayMusic(audioMusicParam);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(this.mBGMId, this);
        if (!startPlayMusic) {
            this.onPlayEnd(-1);
            return;
        }
        this.onPlayStart();
    }
    
    public void stopPlay() {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "stopPlay");
        this.mIsRunning = false;
        final long currentTimeMillis = System.currentTimeMillis();
        synchronized (this) {
            TXAudioEffectManagerImpl.getCacheInstance().setMusicObserver(this.mBGMId, null);
            TXAudioEffectManagerImpl.getCacheInstance().stopPlayMusic(this.mBGMId);
        }
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "stopBGMPlay cost(MS): " + (System.currentTimeMillis() - currentTimeMillis));
    }
    
    public void pause() {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "pause");
        TXAudioEffectManagerImpl.getCacheInstance().pausePlayMusic(this.mBGMId);
    }
    
    public void resume() {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "resume");
        TXAudioEffectManagerImpl.getCacheInstance().resumePlayMusic(this.mBGMId);
    }
    
    public void setVolume(final float mVolume) {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "setVolume:" + mVolume);
        this.mVolume = mVolume;
        TXAudioEffectManagerImpl.getCacheInstance().setMusicVolume(this.mBGMId, (int)(mVolume * 100.0f));
    }
    
    public void setSpeedRate(final float mSpeedRate) {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "setSpeedRate:" + mSpeedRate);
        this.mSpeedRate = mSpeedRate;
        TXAudioEffectManagerImpl.getCacheInstance().setMusicPlayoutSpeedRate(this.mBGMId, mSpeedRate);
    }
    
    public void setChangerType(final int n) {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "changerType:" + n);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicChangerType(this.mBGMId, n);
    }
    
    public void setReverbType(final int n) {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "int reverbType:" + n);
        TXAudioEffectManagerImpl.getCacheInstance().setMusicReverbType(this.mBGMId, n);
    }
    
    public void playFromTime(final long mStartTimeMS, final long mEndTimeMS) {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "startPlayRange:" + mStartTimeMS + ", " + mEndTimeMS);
        this.mStartTimeMS = mStartTimeMS;
        this.mEndTimeMS = mEndTimeMS;
    }
    
    public void seekBytes(long mSeekBytes) {
        TXCLog.i("AudioCenter:TXCUGCBGMPlayer", "seekBytes:" + mSeekBytes);
        if (mSeekBytes < 0L) {
            mSeekBytes = 0L;
            TXCLog.e("AudioCenter:TXCUGCBGMPlayer", "seek bytes can not be negative. change to 0");
        }
        this.mSeekBytes = mSeekBytes;
        TXAudioEffectManagerImpl.getCacheInstance().seekMusicToPosInBytes(this.mBGMId, mSeekBytes);
    }
    
    public long getDurationMS(final String s) {
        return TXAudioEffectManagerImpl.getCacheInstance().getMusicDurationInMS(s);
    }
    
    private void onPlayStart() {
        e e = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                e = this.mWeakListener.get();
            }
        }
        if (e != null) {
            e.onPlayStart();
        }
    }
    
    private void onPlayEnd(final int n) {
        e e = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                e = this.mWeakListener.get();
            }
        }
        if (e != null) {
            e.onPlayEnd(n);
        }
    }
    
    private void onPlayProgress(final long n, final long n2) {
        e e = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                e = this.mWeakListener.get();
            }
        }
        if (e != null) {
            e.onPlayProgress(n, n2);
        }
    }
    
    @Override
    public void onPlayProgress(final int n, final long n2, final long n3) {
        this.onPlayProgress(n2, n3);
    }
    
    @Override
    public void onStart(final int n, final int n2) {
        this.onPlayStart();
    }
    
    @Override
    public void onComplete(final int n, final int n2) {
        this.onPlayEnd(n2);
    }
    
    static {
        f.f();
    }
    
    private static class a
    {
        private static TXCUGCBGMPlayer a;
        
        public static TXCUGCBGMPlayer a() {
            return TXCUGCBGMPlayer.a.a;
        }
        
        static {
            TXCUGCBGMPlayer.a.a = new TXCUGCBGMPlayer(null);
        }
    }
}
