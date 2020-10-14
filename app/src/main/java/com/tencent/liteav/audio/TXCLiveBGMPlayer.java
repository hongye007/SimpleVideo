package com.tencent.liteav.audio;

import java.lang.ref.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;

public class TXCLiveBGMPlayer implements TXAudioEffectManager.TXMusicPlayObserver
{
    private static final String TAG = "AudioCenter:TXCLiveBGMPlayer";
    private static final int PLAY_SUCCESS = 0;
    private static final int PLAY_ERR_OPEN = -1;
    private boolean mIsRunning;
    private boolean mIsPause;
    private WeakReference<e> mWeakListener;
    private int mBGMId;
    private final Handler mHandler;
    
    public static TXCLiveBGMPlayer getInstance() {
        return a.a();
    }
    
    private TXCLiveBGMPlayer() {
        this.mIsRunning = false;
        this.mIsPause = false;
        this.mWeakListener = null;
        this.mBGMId = Integer.MAX_VALUE;
        this.mHandler = new Handler(Looper.getMainLooper());
    }
    
    public synchronized void setOnPlayListener(final e e) {
        if (e == null) {
            this.mWeakListener = null;
        }
        this.mWeakListener = new WeakReference<e>(e);
    }
    
    public boolean startPlay(final String s) {
        if (s == null || s.isEmpty()) {
            TXCLog.e("AudioCenter:TXCLiveBGMPlayer", "start live bgm failed! invalid params!");
            return false;
        }
        this.mIsRunning = true;
        final TXAudioEffectManager.AudioMusicParam audioMusicParam = new TXAudioEffectManager.AudioMusicParam(this.mBGMId, s);
        audioMusicParam.publish = true;
        audioMusicParam.loopCount = 0;
        final boolean startPlayMusic = TXAudioEffectManagerImpl.getInstance().startPlayMusic(audioMusicParam);
        TXAudioEffectManagerImpl.getInstance().setMusicObserver(this.mBGMId, this);
        if (!startPlayMusic) {
            this.onPlayEnd(-1);
            return false;
        }
        TXCLog.i("AudioCenter:TXCLiveBGMPlayer", "start bgm play : filePath = " + s);
        return true;
    }
    
    public boolean stopPlay() {
        this.mIsRunning = false;
        final long currentTimeMillis = System.currentTimeMillis();
        TXAudioEffectManagerImpl.getInstance().setMusicObserver(this.mBGMId, null);
        TXAudioEffectManagerImpl.getInstance().stopPlayMusic(this.mBGMId);
        this.mIsPause = false;
        TXCLog.i("AudioCenter:TXCLiveBGMPlayer", "stopBGMPlay cost(MS): " + (System.currentTimeMillis() - currentTimeMillis));
        return true;
    }
    
    public void stopAll() {
        TXAudioEffectManagerImpl.getInstance().stopAllMusics();
        TXAudioEffectManagerImpl.getAutoCacheHolder().stopAllMusics();
        TXAudioEffectManagerImpl.getCacheInstance().stopAllMusics();
    }
    
    public boolean isRunning() {
        return this.mIsRunning && !this.mIsPause;
    }
    
    public boolean isPlaying() {
        return this.mIsRunning;
    }
    
    public boolean pause() {
        TXCLog.i("AudioCenter:TXCLiveBGMPlayer", "pause");
        this.mIsPause = true;
        TXAudioEffectManagerImpl.getInstance().pausePlayMusic(this.mBGMId);
        return true;
    }
    
    public boolean resume() {
        TXCLog.i("AudioCenter:TXCLiveBGMPlayer", "resume");
        this.mIsPause = false;
        TXAudioEffectManagerImpl.getInstance().resumePlayMusic(this.mBGMId);
        return true;
    }
    
    public boolean setVolume(final float n) {
        TXCLog.i("AudioCenter:TXCLiveBGMPlayer", "setVolume");
        TXAudioEffectManagerImpl.getInstance().setMusicVolume(this.mBGMId, (int)(n * 100.0f));
        return true;
    }
    
    public boolean setPlayoutVolume(final float n) {
        TXCLog.i("AudioCenter:TXCLiveBGMPlayer", "setPlayoutVolume:" + n);
        TXAudioEffectManagerImpl.getInstance().setMusicPlayoutVolume(this.mBGMId, (int)(n * 100.0f));
        return true;
    }
    
    public boolean setPublishVolume(final float n) {
        TXAudioEffectManagerImpl.getInstance().setMusicPublishVolume(this.mBGMId, (int)(n * 100.0f));
        return true;
    }
    
    public int getBGMDuration(final String s) {
        return (int)TXAudioEffectManagerImpl.getInstance().getMusicDurationInMS(s);
    }
    
    public long getBGMGetCurrentProgressInMs(final String s) {
        if (s == null) {
            return TXAudioEffectManagerImpl.getInstance().getMusicCurrentPosInMS(this.mBGMId);
        }
        return 0L;
    }
    
    public void setBGMPosition(final int n) {
        TXAudioEffectManagerImpl.getInstance().seekMusicToPosInMS(this.mBGMId, n);
    }
    
    public void setPitch(final float n) {
        TXAudioEffectManagerImpl.getInstance().setMusicPitch(this.mBGMId, n);
    }
    
    private void onPlayStart(final int n) {
        e e = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                e = this.mWeakListener.get();
            }
        }
        this.mHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (e != null) {
                    e.onPlayStart();
                }
            }
        });
    }
    
    private void onPlayEnd(final int n) {
        e e = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                e = this.mWeakListener.get();
            }
        }
        this.mHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (e != null) {
                    e.onPlayEnd(n);
                }
            }
        });
    }
    
    private void onPlayProgress(final long n, final long n2) {
        e e = null;
        synchronized (this) {
            if (this.mWeakListener != null) {
                e = this.mWeakListener.get();
            }
        }
        this.mHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (e != null) {
                    e.onPlayProgress(n, n2);
                }
            }
        });
    }
    
    @Override
    public void onPlayProgress(final int n, final long n2, final long n3) {
        this.onPlayProgress(n2, n3);
    }
    
    @Override
    public void onStart(final int n, final int n2) {
        this.onPlayStart(n2);
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
        private static TXCLiveBGMPlayer a;
        
        public static TXCLiveBGMPlayer a() {
            return TXCLiveBGMPlayer.a.a;
        }
        
        static {
            TXCLiveBGMPlayer.a.a = new TXCLiveBGMPlayer(null);
        }
    }
}
