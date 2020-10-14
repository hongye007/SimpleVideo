package com.tencent.liteav.audio.impl.Play;

import android.content.*;
import com.tencent.liteav.basic.log.*;
import android.media.*;
import com.tencent.liteav.audio.*;
import java.util.*;
import java.nio.*;

public class TXCMultAudioTrackPlayer
{
    private static final String TAG;
    private AudioTrackThread mAudioThread;
    private boolean mMute;
    private volatile boolean mIsStarted;
    private volatile boolean mAudioTrackStarted;
    private Context mContext;
    private int mAudioMode;
    private int mSampleRate;
    private int mChannel;
    private int mBits;
    
    public static TXCMultAudioTrackPlayer getInstance() {
        return TXCMultAudioTrackPlayerHolder.getInstance();
    }
    
    private TXCMultAudioTrackPlayer() {
        this.mAudioThread = null;
        this.mMute = false;
        this.mIsStarted = false;
        this.mAudioTrackStarted = false;
        this.mContext = null;
        this.mAudioMode = 0;
        this.mSampleRate = 48000;
        this.mChannel = 2;
        this.mBits = 16;
        this.nativeClassInit();
    }
    
    public void start() {
        TXCLog.w(TXCMultAudioTrackPlayer.TAG, "mult-track-player start!");
        if (this.mIsStarted) {
            TXCLog.e(TXCMultAudioTrackPlayer.TAG, "mult-track-player can not start because of has started!");
            return;
        }
        if (this.mSampleRate == 0 || this.mChannel == 0) {
            TXCLog.e(TXCMultAudioTrackPlayer.TAG, "strat mult-track-player failed with invalid audio info , samplerate:" + this.mSampleRate + ", channels:" + this.mChannel);
            return;
        }
        this.mIsStarted = true;
        if (this.mAudioThread == null) {
            (this.mAudioThread = new AudioTrackThread("AUDIO_TRACK") {
                @Override
                public void run() {
                    AudioTrack audioTrack;
                    try {
                        int n = 3;
                        if (TXCMultAudioTrackPlayer.this.mChannel == 1) {
                            n = 2;
                        }
                        int n2 = 2;
                        if (TXCMultAudioTrackPlayer.this.mBits == 8) {
                            n2 = 3;
                        }
                        final int minBufferSize = AudioTrack.getMinBufferSize(TXCMultAudioTrackPlayer.this.mSampleRate, n, n2);
                        audioTrack = new AudioTrack(3, TXCMultAudioTrackPlayer.this.mSampleRate, n, n2, minBufferSize, 1);
                        TXCLog.i(TXCMultAudioTrackPlayer.TAG, "create audio track, samplerate:" + TXCMultAudioTrackPlayer.this.mSampleRate + ", channels:" + TXCMultAudioTrackPlayer.this.mChannel + ", bits:" + TXCMultAudioTrackPlayer.this.mBits + " mMinBufferLength:" + minBufferSize);
                    }
                    catch (Exception ex) {
                        TXCLog.e(TXCMultAudioTrackPlayer.TAG, "create AudioTrack failed.", ex);
                        return;
                    }
                    try {
                        audioTrack.play();
                    }
                    catch (Exception ex2) {
                        TXCLog.e(TXCMultAudioTrackPlayer.TAG, "start play failed.", ex2);
                        return;
                    }
                    TXCMultAudioTrackPlayer.this.mAudioTrackStarted = true;
                    TXCMultAudioTrackPlayer.this.setAudioMode(TXCMultAudioTrackPlayer.this.mContext, TXCMultAudioTrackPlayer.this.mAudioMode);
                    int n3 = 0;
                    final int n4 = 800;
                    int n5 = 100;
                    while (this.mIsLooping) {
                        final byte[] access$800 = TXCMultAudioTrackPlayer.this.nativeGetMixedTracksDataToAudioTrack();
                        if (access$800 != null && access$800.length > 0) {
                            TXCAudioEngine.onCorePlayPcmData(access$800, 0L, TXCMultAudioTrackPlayer.this.mSampleRate, TXCMultAudioTrackPlayer.this.mChannel);
                            if (TXCMultAudioTrackPlayer.this.mMute) {
                                Arrays.fill(access$800, (byte)0);
                            }
                            if (n5 != 0 && n3 < n4) {
                                final short[] array = new short[access$800.length / 2];
                                ByteBuffer.wrap(access$800).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(array);
                                for (int i = 0; i < array.length; ++i) {
                                    final short[] array2 = array;
                                    final int n6 = i;
                                    array2[n6] /= (short)n5;
                                }
                                ByteBuffer.wrap(access$800).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().put(array);
                                n3 += access$800.length / (2 * TXCMultAudioTrackPlayer.this.mSampleRate / 1000);
                                n5 = n5 * (n4 - n3) / n4;
                            }
                            audioTrack.write(access$800, 0, access$800.length);
                        }
                        else {
                            try {
                                Thread.sleep(5L);
                            }
                            catch (InterruptedException ex4) {}
                        }
                    }
                    try {
                        audioTrack.pause();
                        audioTrack.flush();
                        audioTrack.stop();
                        audioTrack.release();
                    }
                    catch (Exception ex3) {
                        TXCLog.e(TXCMultAudioTrackPlayer.TAG, "stop AudioTrack failed.", ex3);
                    }
                    TXCLog.e(TXCMultAudioTrackPlayer.TAG, "mult-player thread stop finish!");
                }
            }).startLoop();
            this.mAudioThread.start();
        }
        TXCLog.w(TXCMultAudioTrackPlayer.TAG, "mult-track-player thread start finish!");
    }
    
    public void stop() {
        TXCLog.w(TXCMultAudioTrackPlayer.TAG, "mult-track-player stop!");
        if (!this.mIsStarted) {
            TXCLog.w(TXCMultAudioTrackPlayer.TAG, "mult-track-player can not stop because of not started yet!");
            return;
        }
        if (this.mAudioThread != null) {
            this.mAudioThread.stopLoop();
            this.mAudioThread = null;
        }
        this.mAudioMode = 0;
        this.mContext = null;
        this.mAudioTrackStarted = false;
        this.mIsStarted = false;
        TXCLog.w(TXCMultAudioTrackPlayer.TAG, "mult-track-player stop finish!");
    }
    
    public synchronized void setAudioMode(final Context mContext, final int mAudioMode) {
        this.mContext = mContext;
        this.mAudioMode = mAudioMode;
        if (this.mAudioTrackStarted) {
            TXCLog.w(TXCMultAudioTrackPlayer.TAG, "mult-track-player setAudioRoute~");
        }
        else {
            TXCLog.w(TXCMultAudioTrackPlayer.TAG, "mult-track-player do'not setAudioRoute~");
        }
    }
    
    public boolean isPlaying() {
        return this.mIsStarted;
    }
    
    public void setMute(final boolean mMute) {
        this.mMute = mMute;
    }
    
    private native void nativeClassInit();
    
    private native byte[] nativeGetMixedTracksDataToAudioTrack();
    
    static {
        TAG = "AudioCenter:" + TXCMultAudioTrackPlayer.class.getSimpleName();
    }
    
    class AudioTrackThread extends Thread
    {
        volatile boolean mIsLooping;
        
        public AudioTrackThread(final String s) {
            super(s);
            this.mIsLooping = false;
        }
        
        public void startLoop() {
            this.mIsLooping = true;
        }
        
        public void stopLoop() {
            this.mIsLooping = false;
        }
    }
    
    private static class TXCMultAudioTrackPlayerHolder
    {
        private static TXCMultAudioTrackPlayer instance;
        
        public static TXCMultAudioTrackPlayer getInstance() {
            return TXCMultAudioTrackPlayerHolder.instance;
        }
        
        static {
            TXCMultAudioTrackPlayerHolder.instance = new TXCMultAudioTrackPlayer(null);
        }
    }
}
