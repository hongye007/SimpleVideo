package com.tencent.ijk.media.player;

import android.view.*;
import android.annotation.*;
import android.content.*;
import android.net.*;
import java.io.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.ijk.media.player.misc.*;
import com.tencent.ijk.media.player.pragma.*;
import android.os.*;
import java.util.*;
import java.lang.ref.*;
import android.media.*;

public class AndroidMediaPlayer extends AbstractMediaPlayer
{
    private static final String TAG = "AndroidMediaPlayer";
    private final MediaPlayer mInternalMediaPlayer;
    private final AndroidMediaPlayerListenerHolder mInternalListenerAdapter;
    private String mDataSource;
    private MediaDataSource mMediaDataSource;
    private final Object mInitLock;
    private boolean mIsReleased;
    private static MediaInfo sMediaInfo;
    private Surface mSurface;
    
    public AndroidMediaPlayer() {
        synchronized (this.mInitLock = new Object()) {
            this.mInternalMediaPlayer = new MediaPlayer();
        }
        this.mInternalMediaPlayer.setAudioStreamType(3);
        this.mInternalListenerAdapter = new AndroidMediaPlayerListenerHolder(this);
        this.attachInternalListeners();
    }
    
    public MediaPlayer getInternalMediaPlayer() {
        return this.mInternalMediaPlayer;
    }
    
    @Override
    public void setDisplay(final SurfaceHolder display) {
        synchronized (this.mInitLock) {
            if (!this.mIsReleased) {
                this.mInternalMediaPlayer.setDisplay(display);
            }
        }
    }
    
    @TargetApi(14)
    @Override
    public void setSurface(final Surface surface) {
        this.mInternalMediaPlayer.setSurface(surface);
        this.mSurface = surface;
    }
    
    @Override
    public Surface getSurface() {
        return this.mSurface;
    }
    
    @Override
    public void setDataSource(final Context context, final Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mInternalMediaPlayer.setDataSource(context, uri);
    }
    
    @TargetApi(14)
    @Override
    public void setDataSource(final Context context, final Uri uri, final Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mInternalMediaPlayer.setDataSource(context, uri, (Map)map);
    }
    
    @Override
    public void setDataSource(final FileDescriptor dataSource) throws IOException, IllegalArgumentException, IllegalStateException {
        this.mInternalMediaPlayer.setDataSource(dataSource);
    }
    
    @Override
    public void setDataSource(final String s) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mDataSource = s;
        final Uri parse = Uri.parse(s);
        final String scheme = parse.getScheme();
        if (!TextUtils.isEmpty((CharSequence)scheme) && scheme.equalsIgnoreCase("file")) {
            this.mInternalMediaPlayer.setDataSource(parse.getPath());
        }
        else {
            this.mInternalMediaPlayer.setDataSource(s);
        }
    }
    
    @TargetApi(23)
    @Override
    public void setDataSource(final IMediaDataSource mediaDataSource) {
        this.releaseMediaDataSource();
        this.mMediaDataSource = new MediaDataSourceProxy(mediaDataSource);
        this.mInternalMediaPlayer.setDataSource(this.mMediaDataSource);
    }
    
    @Override
    public String getDataSource() {
        return this.mDataSource;
    }
    
    private void releaseMediaDataSource() {
        if (this.mMediaDataSource != null) {
            try {
                this.mMediaDataSource.close();
            }
            catch (IOException ex) {
                TXCLog.e("AndroidMediaPlayer", "close media data source failed.", ex);
            }
            this.mMediaDataSource = null;
        }
    }
    
    @Override
    public void prepareAsync() throws IllegalStateException {
        this.mInternalMediaPlayer.prepareAsync();
    }
    
    @Override
    public void start() throws IllegalStateException {
        this.mInternalMediaPlayer.start();
    }
    
    @Override
    public void stop() throws IllegalStateException {
        this.mInternalMediaPlayer.stop();
    }
    
    @Override
    public void pause() throws IllegalStateException {
        this.mInternalMediaPlayer.pause();
    }
    
    @Override
    public void setScreenOnWhilePlaying(final boolean screenOnWhilePlaying) {
        this.mInternalMediaPlayer.setScreenOnWhilePlaying(screenOnWhilePlaying);
    }
    
    @Override
    public ITrackInfo[] getTrackInfo() {
        return AndroidTrackInfo.fromMediaPlayer(this.mInternalMediaPlayer);
    }
    
    @Override
    public int getVideoWidth() {
        return this.mInternalMediaPlayer.getVideoWidth();
    }
    
    @Override
    public int getVideoHeight() {
        return this.mInternalMediaPlayer.getVideoHeight();
    }
    
    @Override
    public int getVideoSarNum() {
        return 1;
    }
    
    @Override
    public int getVideoSarDen() {
        return 1;
    }
    
    @Override
    public boolean isPlaying() {
        try {
            return this.mInternalMediaPlayer.isPlaying();
        }
        catch (IllegalStateException ex) {
            DebugLog.printStackTrace(ex);
            return false;
        }
    }
    
    @Override
    public void seekTo(final long n) throws IllegalStateException {
        this.mInternalMediaPlayer.seekTo((int)n);
    }
    
    @Override
    public long getCurrentPosition() {
        try {
            return this.mInternalMediaPlayer.getCurrentPosition();
        }
        catch (IllegalStateException ex) {
            DebugLog.printStackTrace(ex);
            return 0L;
        }
    }
    
    @Override
    public long getDuration() {
        try {
            return this.mInternalMediaPlayer.getDuration();
        }
        catch (IllegalStateException ex) {
            DebugLog.printStackTrace(ex);
            return 0L;
        }
    }
    
    @Override
    public void release() {
        this.mIsReleased = true;
        this.mInternalMediaPlayer.release();
        this.releaseMediaDataSource();
        this.resetListeners();
        this.attachInternalListeners();
    }
    
    @Override
    public void reset() {
        try {
            this.mInternalMediaPlayer.reset();
        }
        catch (IllegalStateException ex) {
            DebugLog.printStackTrace(ex);
        }
        this.releaseMediaDataSource();
        this.resetListeners();
        this.attachInternalListeners();
    }
    
    @Override
    public void setLooping(final boolean looping) {
        this.mInternalMediaPlayer.setLooping(looping);
    }
    
    @Override
    public boolean isLooping() {
        return this.mInternalMediaPlayer.isLooping();
    }
    
    @TargetApi(23)
    @Override
    public void setRate(final float n) {
        if (Build.VERSION.SDK_INT >= 23) {
            final PlaybackParams playbackParams = new PlaybackParams();
            playbackParams.setPitch(n);
            playbackParams.setSpeed(n);
            this.mInternalMediaPlayer.setPlaybackParams(playbackParams);
        }
    }
    
    @TargetApi(23)
    @Override
    public float getRate() {
        if (Build.VERSION.SDK_INT >= 23) {
            return this.mInternalMediaPlayer.getPlaybackParams().getSpeed();
        }
        return 1.0f;
    }
    
    @Override
    public void setVolume(final float n, final float n2) {
        this.mInternalMediaPlayer.setVolume(n, n2);
    }
    
    @Override
    public int getAudioSessionId() {
        return this.mInternalMediaPlayer.getAudioSessionId();
    }
    
    @Override
    public MediaInfo getMediaInfo() {
        if (AndroidMediaPlayer.sMediaInfo == null) {
            final MediaInfo sMediaInfo = new MediaInfo();
            sMediaInfo.mVideoDecoder = "android";
            sMediaInfo.mVideoDecoderImpl = "HW";
            sMediaInfo.mAudioDecoder = "android";
            sMediaInfo.mAudioDecoderImpl = "HW";
            AndroidMediaPlayer.sMediaInfo = sMediaInfo;
        }
        return AndroidMediaPlayer.sMediaInfo;
    }
    
    @Override
    public void setLogEnabled(final boolean b) {
    }
    
    @Override
    public boolean isPlayable() {
        return true;
    }
    
    @Override
    public void setWakeMode(final Context context, final int n) {
        this.mInternalMediaPlayer.setWakeMode(context, n);
    }
    
    @Override
    public void setAudioStreamType(final int audioStreamType) {
        this.mInternalMediaPlayer.setAudioStreamType(audioStreamType);
    }
    
    @Override
    public void setKeepInBackground(final boolean b) {
    }
    
    private void attachInternalListeners() {
        this.mInternalMediaPlayer.setOnPreparedListener((MediaPlayer.OnPreparedListener)this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnBufferingUpdateListener((MediaPlayer.OnBufferingUpdateListener)this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener)this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnSeekCompleteListener((MediaPlayer.OnSeekCompleteListener)this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnVideoSizeChangedListener((MediaPlayer.OnVideoSizeChangedListener)this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnErrorListener((MediaPlayer.OnErrorListener)this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnInfoListener((MediaPlayer.OnInfoListener)this.mInternalListenerAdapter);
        this.mInternalMediaPlayer.setOnTimedTextListener((MediaPlayer.OnTimedTextListener)this.mInternalListenerAdapter);
    }
    
    @Override
    public int getBitrateIndex() {
        return 0;
    }
    
    @Override
    public void setBitrateIndex(final int n) {
    }
    
    @Override
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return new ArrayList<IjkBitrateItem>();
    }
    
    @TargetApi(23)
    private static class MediaDataSourceProxy extends MediaDataSource
    {
        private final IMediaDataSource mMediaDataSource;
        
        public MediaDataSourceProxy(final IMediaDataSource mMediaDataSource) {
            this.mMediaDataSource = mMediaDataSource;
        }
        
        public int readAt(final long n, final byte[] array, final int n2, final int n3) throws IOException {
            return this.mMediaDataSource.readAt(n, array, n2, n3);
        }
        
        public long getSize() throws IOException {
            return this.mMediaDataSource.getSize();
        }
        
        public void close() throws IOException {
            this.mMediaDataSource.close();
        }
    }
    
    private class AndroidMediaPlayerListenerHolder implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnTimedTextListener, MediaPlayer.OnVideoSizeChangedListener
    {
        public final WeakReference<AndroidMediaPlayer> mWeakMediaPlayer;
        
        public AndroidMediaPlayerListenerHolder(final AndroidMediaPlayer androidMediaPlayer) {
            this.mWeakMediaPlayer = new WeakReference<AndroidMediaPlayer>(androidMediaPlayer);
        }
        
        public boolean onInfo(final MediaPlayer mediaPlayer, final int n, final int n2) {
            return this.mWeakMediaPlayer.get() != null && AndroidMediaPlayer.this.notifyOnInfo(n, n2);
        }
        
        public boolean onError(final MediaPlayer mediaPlayer, final int n, final int n2) {
            return this.mWeakMediaPlayer.get() != null && AndroidMediaPlayer.this.notifyOnError(n, n2);
        }
        
        public void onVideoSizeChanged(final MediaPlayer mediaPlayer, final int n, final int n2) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnVideoSizeChanged(n, n2, 1, 1);
        }
        
        public void onSeekComplete(final MediaPlayer mediaPlayer) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnSeekComplete();
        }
        
        public void onBufferingUpdate(final MediaPlayer mediaPlayer, final int n) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnBufferingUpdate(n);
        }
        
        public void onCompletion(final MediaPlayer mediaPlayer) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnCompletion();
        }
        
        public void onPrepared(final MediaPlayer mediaPlayer) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            AndroidMediaPlayer.this.notifyOnPrepared();
        }
        
        public void onTimedText(final MediaPlayer mediaPlayer, final TimedText timedText) {
            if (this.mWeakMediaPlayer.get() == null) {
                return;
            }
            IjkTimedText ijkTimedText = null;
            if (timedText != null) {
                ijkTimedText = new IjkTimedText(timedText.getBounds(), timedText.getText());
            }
            AndroidMediaPlayer.this.notifyOnTimedText(ijkTimedText);
        }
    }
}
