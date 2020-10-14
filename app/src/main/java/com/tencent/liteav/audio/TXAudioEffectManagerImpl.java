package com.tencent.liteav.audio;

import com.tencent.liteav.basic.log.*;
import android.os.*;
import java.util.*;
import com.tencent.liteav.basic.util.*;

public class TXAudioEffectManagerImpl implements TXAudioEffectManager
{
    private static final String TAG = "AudioCenter:TXAudioEffectManager";
    private static final int OLD_BGM_PLAYER_ID_TYPE = 0;
    private static final int NEW_BGM_PLAYER_ID_TYPE = 1;
    private static final int EFFECT_PLAYER_ID_TYPE = 2;
    private static HashMap<Long, TXMusicPlayObserver> mMusicObserverMap;
    private final int mIdType;
    private List<Long> mPlayingMusicIDList;
    private static final Handler mMainHandler;
    
    public static TXAudioEffectManagerImpl getInstance() {
        return AudioEffectManagerHolder.INSTANCE;
    }
    
    public static TXAudioEffectManagerImpl getCacheInstance() {
        return AudioEffectManagerCacheHolder.INSTANCE;
    }
    
    public static TXAudioEffectManagerImpl getAutoCacheHolder() {
        return AudioEffectManagerAutoCacheHolder.INSTANCE;
    }
    
    private TXAudioEffectManagerImpl(final int mIdType) {
        this.mPlayingMusicIDList = new ArrayList<Long>();
        this.mIdType = mIdType;
    }
    
    @Override
    public void enableVoiceEarMonitor(final boolean b) {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.enableAudioEarMonitoring(b);
    }
    
    @Override
    public void setVoiceEarMonitorVolume(final int audioEarMonitoringVolume) {
        TXCAudioEngine.getInstance();
        TXCAudioEngine.setAudioEarMonitoringVolume(audioEarMonitoringVolume);
    }
    
    @Override
    public void setVoiceReverbType(final TXVoiceReverbType reverbType) {
        TXCAudioEngine.getInstance().setReverbType(reverbType);
    }
    
    @Override
    public void setVoiceChangerType(final TXVoiceChangerType voiceChangerType) {
        TXCAudioEngine.getInstance().setVoiceChangerType(voiceChangerType);
    }
    
    @Override
    public void setVoiceCaptureVolume(final int n) {
        TXCAudioEngine.getInstance().setSoftwareCaptureVolume(n / 100.0f);
    }
    
    public void setAudioPlayoutVolume(final int n) {
        TXCAudioEngine.getInstance().setMixingPlayoutVolume(n / 100.0f);
    }
    
    @Override
    public void setMusicObserver(final int n, final TXMusicPlayObserver txMusicPlayObserver) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (null == txMusicPlayObserver) {
                    TXAudioEffectManagerImpl.mMusicObserverMap.remove(convertIdToInt64(TXAudioEffectManagerImpl.this.mIdType, n));
                }
                else {
                    TXAudioEffectManagerImpl.mMusicObserverMap.put(convertIdToInt64(TXAudioEffectManagerImpl.this.mIdType, n), txMusicPlayObserver);
                }
                TXCLog.i("AudioCenter:TXAudioEffectManager", "setMusicObserver map count: %d", TXAudioEffectManagerImpl.mMusicObserverMap.size());
            }
        };
        if (Looper.myLooper() == TXAudioEffectManagerImpl.mMainHandler.getLooper()) {
            runnable.run();
        }
        else {
            TXAudioEffectManagerImpl.mMainHandler.post((Runnable)runnable);
        }
    }
    
    @Override
    public boolean startPlayMusic(final AudioMusicParam audioMusicParam) {
        TXCLog.i("AudioCenter:TXAudioEffectManager", "startPlay");
        long startTimeMS = audioMusicParam.startTimeMS;
        long endTimeMS = audioMusicParam.endTimeMS;
        if (startTimeMS < 0L) {
            startTimeMS = 0L;
        }
        if (endTimeMS < 0L) {
            endTimeMS = 0L;
        }
        final long convertIdToInt64 = convertIdToInt64(this.mIdType, audioMusicParam.id);
        if (!this.mPlayingMusicIDList.contains(convertIdToInt64)) {
            this.mPlayingMusicIDList.add(convertIdToInt64);
        }
        this.nativeStartPlayRange(convertIdToInt64, startTimeMS, endTimeMS);
        return this.nativeStartPlay(convertIdToInt64, audioMusicParam.path, audioMusicParam.loopCount, audioMusicParam.publish, audioMusicParam.isShortFile);
    }
    
    @Override
    public void stopPlayMusic(final int n) {
        final long convertIdToInt64 = convertIdToInt64(this.mIdType, n);
        this.mPlayingMusicIDList.remove(convertIdToInt64);
        this.nativeStopPlay(convertIdToInt64);
    }
    
    @Override
    public void pausePlayMusic(final int n) {
        final long convertIdToInt64 = convertIdToInt64(this.mIdType, n);
        this.mPlayingMusicIDList.remove(convertIdToInt64);
        this.nativePause(convertIdToInt64);
    }
    
    @Override
    public void resumePlayMusic(final int n) {
        final long convertIdToInt64 = convertIdToInt64(this.mIdType, n);
        if (!this.mPlayingMusicIDList.contains(convertIdToInt64)) {
            this.mPlayingMusicIDList.add(convertIdToInt64);
        }
        this.nativeResume(convertIdToInt64);
    }
    
    public void interruptAllMusics() {
        TXCLog.i("AudioCenter:TXAudioEffectManager", "interruptAllMusics");
        final Iterator<Long> iterator = this.mPlayingMusicIDList.iterator();
        while (iterator.hasNext()) {
            this.nativePause(iterator.next());
        }
    }
    
    public void recoverAllMusics() {
        TXCLog.i("AudioCenter:TXAudioEffectManager", "recoverAllMusics");
        final Iterator<Long> iterator = this.mPlayingMusicIDList.iterator();
        while (iterator.hasNext()) {
            this.nativeResume(iterator.next());
        }
    }
    
    public void stopAllMusics() {
        TXCLog.i("AudioCenter:TXAudioEffectManager", "stopAllMusics");
        for (final long longValue : this.mPlayingMusicIDList) {
            this.nativeStopPlay(longValue);
            TXAudioEffectManagerImpl.mMainHandler.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    TXAudioEffectManagerImpl.mMusicObserverMap.remove(longValue);
                }
            });
        }
        this.mPlayingMusicIDList.clear();
    }
    
    public void setMusicVolume(final int n, final int n2) {
        TXCLog.i("AudioCenter:TXAudioEffectManager", "setMusicVolume " + n2);
        this.nativeSetVolume(convertIdToInt64(this.mIdType, n), n2);
    }
    
    @Override
    public void setMusicPublishVolume(final int n, final int n2) {
        this.nativeSetPublishVolume(convertIdToInt64(this.mIdType, n), n2);
    }
    
    @Override
    public void setMusicPlayoutVolume(final int n, final int n2) {
        this.nativeSetPlayoutVolume(convertIdToInt64(this.mIdType, n), n2);
    }
    
    @Override
    public void setAllMusicVolume(final int n) {
        this.nativeSetAllVolume(n);
    }
    
    @Override
    public void setMusicPitch(final int n, final float n2) {
        this.nativeSetPitch(convertIdToInt64(this.mIdType, n), n2);
    }
    
    @Override
    public void setMusicSpeedRate(final int n, final float n2) {
        this.nativeSetSpeedRate(convertIdToInt64(this.mIdType, n), n2);
    }
    
    public void setMusicPlayoutSpeedRate(final int n, final float n2) {
        this.nativeSetPlayoutSpeedRate(convertIdToInt64(this.mIdType, n), n2);
    }
    
    public void setMusicChangerType(final int n, final int n2) {
        this.nativeSetChangerType(convertIdToInt64(this.mIdType, n), n2);
    }
    
    public void setMusicReverbType(final int n, final int n2) {
        this.nativeSetReverbType(convertIdToInt64(this.mIdType, n), n2);
    }
    
    @Override
    public long getMusicCurrentPosInMS(final int n) {
        return this.nativeGetCurrentPositionInMs(convertIdToInt64(this.mIdType, n));
    }
    
    public void seekMusicToPosInBytes(final int n, final long n2) {
        this.nativeSeekToPosition(convertIdToInt64(this.mIdType, n), n2);
    }
    
    @Override
    public void seekMusicToPosInMS(final int n, final int n2) {
        this.nativeSeekToTime(convertIdToInt64(this.mIdType, n), n2);
    }
    
    @Override
    public long getMusicDurationInMS(final String s) {
        return nativeGetDurationMSByPath(s);
    }
    
    public static void onEffectFinish(final long n, final int n2) {
        TXAudioEffectManagerImpl.mMainHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                TXCLog.i("AudioCenter:TXAudioEffectManager", "onEffectFinish -> effect id = " + n + ", errCode = " + n2);
                if (TXAudioEffectManagerImpl.mMusicObserverMap.get(n) != null) {
                    TXAudioEffectManagerImpl.mMusicObserverMap.get(n).onComplete((int)n, n2);
                }
            }
        });
    }
    
    public static void onEffectStart(final long n, final int n2) {
        TXAudioEffectManagerImpl.mMainHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                TXCLog.i("AudioCenter:TXAudioEffectManager", "onEffectStart -> effect id = " + n + ", errCode = " + n2);
                if (TXAudioEffectManagerImpl.mMusicObserverMap.get(n) != null) {
                    TXAudioEffectManagerImpl.mMusicObserverMap.get(n).onStart((int)n, n2);
                }
            }
        });
    }
    
    public static void onEffectProgress(final long n, final long n2, final long n3) {
        TXAudioEffectManagerImpl.mMainHandler.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (TXAudioEffectManagerImpl.mMusicObserverMap.get(n) != null) {
                    TXAudioEffectManagerImpl.mMusicObserverMap.get(n).onPlayProgress((int)n, n2, n3);
                }
            }
        });
    }
    
    private static long convertIdToInt64(final int n, final int n2) {
        return (long)n << 32 | (long)n2;
    }
    
    private static native void nativeClassInit();
    
    private native boolean nativeStartPlay(final long p0, final String p1, final int p2, final boolean p3, final boolean p4);
    
    private native void nativeStartPlayRange(final long p0, final long p1, final long p2);
    
    private native void nativeStopPlay(final long p0);
    
    private native void nativePause(final long p0);
    
    private native void nativeResume(final long p0);
    
    private native void nativeSetVolume(final long p0, final int p1);
    
    private native void nativeSetPlayoutVolume(final long p0, final int p1);
    
    private native void nativeSetPublishVolume(final long p0, final int p1);
    
    private native void nativeSetAllVolume(final int p0);
    
    private native void nativeSetPitch(final long p0, final float p1);
    
    private native void nativeSetSpeedRate(final long p0, final float p1);
    
    private native void nativeSetPlayoutSpeedRate(final long p0, final float p1);
    
    private native void nativeSetChangerType(final long p0, final int p1);
    
    private native void nativeSetReverbType(final long p0, final int p1);
    
    private native void nativeSeekToTime(final long p0, final int p1);
    
    private native void nativeSeekToPosition(final long p0, final long p1);
    
    private native long nativeGetCurrentPositionInMs(final long p0);
    
    private static native long nativeGetDurationMSByPath(final String p0);
    
    static {
        TXAudioEffectManagerImpl.mMusicObserverMap = new HashMap<Long, TXMusicPlayObserver>();
        mMainHandler = new Handler(Looper.getMainLooper());
        f.f();
        nativeClassInit();
    }
    
    private static class AudioEffectManagerHolder
    {
        private static TXAudioEffectManagerImpl INSTANCE;
        
        static {
            AudioEffectManagerHolder.INSTANCE = new TXAudioEffectManagerImpl(0, null);
        }
    }
    
    private static class AudioEffectManagerCacheHolder
    {
        private static TXAudioEffectManagerImpl INSTANCE;
        
        static {
            AudioEffectManagerCacheHolder.INSTANCE = new TXAudioEffectManagerImpl(2, null);
        }
    }
    
    private static class AudioEffectManagerAutoCacheHolder
    {
        private static TXAudioEffectManagerImpl INSTANCE;
        
        static {
            AudioEffectManagerAutoCacheHolder.INSTANCE = new TXAudioEffectManagerImpl(1, null);
        }
    }
}
