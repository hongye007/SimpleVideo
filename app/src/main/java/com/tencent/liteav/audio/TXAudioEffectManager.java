package com.tencent.liteav.audio;

public interface TXAudioEffectManager
{
    void enableVoiceEarMonitor(final boolean p0);
    
    void setVoiceEarMonitorVolume(final int p0);
    
    void setVoiceReverbType(final TXVoiceReverbType p0);
    
    void setVoiceChangerType(final TXVoiceChangerType p0);
    
    void setVoiceCaptureVolume(final int p0);
    
    void setMusicObserver(final int p0, final TXMusicPlayObserver p1);
    
    boolean startPlayMusic(final AudioMusicParam p0);
    
    void stopPlayMusic(final int p0);
    
    void pausePlayMusic(final int p0);
    
    void resumePlayMusic(final int p0);
    
    void setMusicPublishVolume(final int p0, final int p1);
    
    void setMusicPlayoutVolume(final int p0, final int p1);
    
    void setAllMusicVolume(final int p0);
    
    void setMusicPitch(final int p0, final float p1);
    
    void setMusicSpeedRate(final int p0, final float p1);
    
    long getMusicCurrentPosInMS(final int p0);
    
    void seekMusicToPosInMS(final int p0, final int p1);
    
    long getMusicDurationInMS(final String p0);
    
    public static class AudioMusicParam
    {
        public int id;
        public String path;
        public int loopCount;
        public boolean publish;
        public boolean isShortFile;
        public long startTimeMS;
        public long endTimeMS;
        
        public AudioMusicParam(final int id, final String path) {
            this.path = path;
            this.id = id;
            this.loopCount = 0;
            this.publish = false;
            this.isShortFile = false;
            this.startTimeMS = 0L;
            this.endTimeMS = -1L;
        }
    }
    
    public enum TXVoiceChangerType
    {
        TXLiveVoiceChangerType_0(0), 
        TXLiveVoiceChangerType_1(1), 
        TXLiveVoiceChangerType_2(2), 
        TXLiveVoiceChangerType_3(3), 
        TXLiveVoiceChangerType_4(4), 
        TXLiveVoiceChangerType_5(5), 
        TXLiveVoiceChangerType_6(6), 
        TXLiveVoiceChangerType_7(7), 
        TXLiveVoiceChangerType_8(8), 
        TXLiveVoiceChangerType_9(9), 
        TXLiveVoiceChangerType_10(10), 
        TXLiveVoiceChangerType_11(11);
        
        private int nativeValue;
        
        private TXVoiceChangerType(final int nativeValue) {
            this.nativeValue = nativeValue;
        }
        
        public int getNativeValue() {
            return this.nativeValue;
        }
    }
    
    public enum TXVoiceReverbType
    {
        TXLiveVoiceReverbType_0(0), 
        TXLiveVoiceReverbType_1(1), 
        TXLiveVoiceReverbType_2(2), 
        TXLiveVoiceReverbType_3(3), 
        TXLiveVoiceReverbType_4(4), 
        TXLiveVoiceReverbType_5(5), 
        TXLiveVoiceReverbType_6(6), 
        TXLiveVoiceReverbType_7(7);
        
        private int nativeValue;
        
        private TXVoiceReverbType(final int nativeValue) {
            this.nativeValue = nativeValue;
        }
        
        public int getNativeValue() {
            return this.nativeValue;
        }
    }
    
    public interface TXMusicPlayObserver
    {
        void onStart(final int p0, final int p1);
        
        void onPlayProgress(final int p0, final long p1, final long p2);
        
        void onComplete(final int p0, final int p1);
    }
}
