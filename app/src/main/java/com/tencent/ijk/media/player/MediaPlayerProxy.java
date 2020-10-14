package com.tencent.ijk.media.player;

import android.view.*;
import android.annotation.*;
import android.content.*;
import android.net.*;
import java.io.*;
import com.tencent.ijk.media.player.misc.*;
import java.util.*;

public class MediaPlayerProxy implements IMediaPlayer
{
    protected final IMediaPlayer mBackEndMediaPlayer;
    
    public MediaPlayerProxy(final IMediaPlayer mBackEndMediaPlayer) {
        this.mBackEndMediaPlayer = mBackEndMediaPlayer;
    }
    
    public IMediaPlayer getInternalMediaPlayer() {
        return this.mBackEndMediaPlayer;
    }
    
    @Override
    public void setDisplay(final SurfaceHolder display) {
        this.mBackEndMediaPlayer.setDisplay(display);
    }
    
    @TargetApi(14)
    @Override
    public void setSurface(final Surface surface) {
        this.mBackEndMediaPlayer.setSurface(surface);
    }
    
    @Override
    public Surface getSurface() {
        return this.mBackEndMediaPlayer.getSurface();
    }
    
    @Override
    public void setDataSource(final Context context, final Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mBackEndMediaPlayer.setDataSource(context, uri);
    }
    
    @TargetApi(14)
    @Override
    public void setDataSource(final Context context, final Uri uri, final Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mBackEndMediaPlayer.setDataSource(context, uri, map);
    }
    
    @Override
    public void setDataSource(final FileDescriptor dataSource) throws IOException, IllegalArgumentException, IllegalStateException {
        this.mBackEndMediaPlayer.setDataSource(dataSource);
    }
    
    @Override
    public void setDataSource(final String dataSource) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.mBackEndMediaPlayer.setDataSource(dataSource);
    }
    
    @Override
    public void setDataSource(final IMediaDataSource dataSource) {
        this.mBackEndMediaPlayer.setDataSource(dataSource);
    }
    
    @Override
    public String getDataSource() {
        return this.mBackEndMediaPlayer.getDataSource();
    }
    
    @Override
    public void prepareAsync() throws IllegalStateException {
        this.mBackEndMediaPlayer.prepareAsync();
    }
    
    @Override
    public void start() throws IllegalStateException {
        this.mBackEndMediaPlayer.start();
    }
    
    @Override
    public void stop() throws IllegalStateException {
        this.mBackEndMediaPlayer.stop();
    }
    
    @Override
    public void pause() throws IllegalStateException {
        this.mBackEndMediaPlayer.pause();
    }
    
    @Override
    public void setScreenOnWhilePlaying(final boolean screenOnWhilePlaying) {
        this.mBackEndMediaPlayer.setScreenOnWhilePlaying(screenOnWhilePlaying);
    }
    
    @Override
    public int getVideoWidth() {
        return this.mBackEndMediaPlayer.getVideoWidth();
    }
    
    @Override
    public int getVideoHeight() {
        return this.mBackEndMediaPlayer.getVideoHeight();
    }
    
    @Override
    public boolean isPlaying() {
        return this.mBackEndMediaPlayer.isPlaying();
    }
    
    @Override
    public void seekTo(final long n) throws IllegalStateException {
        this.mBackEndMediaPlayer.seekTo(n);
    }
    
    @Override
    public long getCurrentPosition() {
        return this.mBackEndMediaPlayer.getCurrentPosition();
    }
    
    @Override
    public long getDuration() {
        return this.mBackEndMediaPlayer.getDuration();
    }
    
    @Override
    public void release() {
        this.mBackEndMediaPlayer.release();
    }
    
    @Override
    public void reset() {
        this.mBackEndMediaPlayer.reset();
    }
    
    @Override
    public void setRate(final float rate) {
        this.mBackEndMediaPlayer.setRate(rate);
    }
    
    @Override
    public float getRate() {
        return this.mBackEndMediaPlayer.getRate();
    }
    
    @Override
    public void setVolume(final float n, final float n2) {
        this.mBackEndMediaPlayer.setVolume(n, n2);
    }
    
    @Override
    public int getAudioSessionId() {
        return this.mBackEndMediaPlayer.getAudioSessionId();
    }
    
    @Override
    public MediaInfo getMediaInfo() {
        return this.mBackEndMediaPlayer.getMediaInfo();
    }
    
    @Override
    public void setLogEnabled(final boolean b) {
    }
    
    @Override
    public boolean isPlayable() {
        return false;
    }
    
    @Override
    public void setOnPreparedListener(final OnPreparedListener onPreparedListener) {
        if (onPreparedListener != null) {
            this.mBackEndMediaPlayer.setOnPreparedListener(new OnPreparedListener() {
                @Override
                public void onPrepared(final IMediaPlayer mediaPlayer) {
                    onPreparedListener.onPrepared(MediaPlayerProxy.this);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnPreparedListener(null);
        }
    }
    
    @Override
    public void setOnCompletionListener(final OnCompletionListener onCompletionListener) {
        if (onCompletionListener != null) {
            this.mBackEndMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
                @Override
                public void onCompletion(final IMediaPlayer mediaPlayer) {
                    onCompletionListener.onCompletion(MediaPlayerProxy.this);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnCompletionListener(null);
        }
    }
    
    @Override
    public void setOnBufferingUpdateListener(final OnBufferingUpdateListener onBufferingUpdateListener) {
        if (onBufferingUpdateListener != null) {
            this.mBackEndMediaPlayer.setOnBufferingUpdateListener(new OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(final IMediaPlayer mediaPlayer, final int n) {
                    onBufferingUpdateListener.onBufferingUpdate(MediaPlayerProxy.this, n);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnBufferingUpdateListener(null);
        }
    }
    
    @Override
    public void setOnSeekCompleteListener(final OnSeekCompleteListener onSeekCompleteListener) {
        if (onSeekCompleteListener != null) {
            this.mBackEndMediaPlayer.setOnSeekCompleteListener(new OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(final IMediaPlayer mediaPlayer) {
                    onSeekCompleteListener.onSeekComplete(MediaPlayerProxy.this);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnSeekCompleteListener(null);
        }
    }
    
    @Override
    public void setOnVideoSizeChangedListener(final OnVideoSizeChangedListener onVideoSizeChangedListener) {
        if (onVideoSizeChangedListener != null) {
            this.mBackEndMediaPlayer.setOnVideoSizeChangedListener(new OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(final IMediaPlayer mediaPlayer, final int n, final int n2, final int n3, final int n4) {
                    onVideoSizeChangedListener.onVideoSizeChanged(MediaPlayerProxy.this, n, n2, n3, n4);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnVideoSizeChangedListener(null);
        }
    }
    
    @Override
    public void setOnErrorListener(final OnErrorListener onErrorListener) {
        if (onErrorListener != null) {
            this.mBackEndMediaPlayer.setOnErrorListener(new OnErrorListener() {
                @Override
                public boolean onError(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                    return onErrorListener.onError(MediaPlayerProxy.this, n, n2);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnErrorListener(null);
        }
    }
    
    @Override
    public void setOnInfoListener(final OnInfoListener onInfoListener) {
        if (onInfoListener != null) {
            this.mBackEndMediaPlayer.setOnInfoListener(new OnInfoListener() {
                @Override
                public boolean onInfo(final IMediaPlayer mediaPlayer, final int n, final int n2) {
                    return onInfoListener.onInfo(MediaPlayerProxy.this, n, n2);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnInfoListener(null);
        }
    }
    
    @Override
    public void setOnTimedTextListener(final OnTimedTextListener onTimedTextListener) {
        if (onTimedTextListener != null) {
            this.mBackEndMediaPlayer.setOnTimedTextListener(new OnTimedTextListener() {
                @Override
                public void onTimedText(final IMediaPlayer mediaPlayer, final IjkTimedText ijkTimedText) {
                    onTimedTextListener.onTimedText(MediaPlayerProxy.this, ijkTimedText);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnTimedTextListener(null);
        }
    }
    
    @Override
    public void setOnHLSKeyErrorListener(final OnHLSKeyErrorListener onHLSKeyErrorListener) {
        if (onHLSKeyErrorListener != null) {
            this.mBackEndMediaPlayer.setOnHLSKeyErrorListener(new OnHLSKeyErrorListener() {
                @Override
                public void onHLSKeyError(final IMediaPlayer mediaPlayer) {
                    onHLSKeyErrorListener.onHLSKeyError(MediaPlayerProxy.this);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnHLSKeyErrorListener(null);
        }
    }
    
    @Override
    public void setOnHevcVideoDecoderErrorListener(final OnHevcVideoDecoderErrorListener onHevcVideoDecoderErrorListener) {
        if (onHevcVideoDecoderErrorListener != null) {
            this.mBackEndMediaPlayer.setOnHevcVideoDecoderErrorListener(new OnHevcVideoDecoderErrorListener() {
                @Override
                public void onHevcVideoDecoderError(final IMediaPlayer mediaPlayer) {
                    onHevcVideoDecoderErrorListener.onHevcVideoDecoderError(MediaPlayerProxy.this);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnHevcVideoDecoderErrorListener(null);
        }
    }
    
    @Override
    public void setOnVideoDecoderErrorListener(final OnVideoDecoderErrorListener onVideoDecoderErrorListener) {
        if (onVideoDecoderErrorListener != null) {
            this.mBackEndMediaPlayer.setOnVideoDecoderErrorListener(new OnVideoDecoderErrorListener() {
                @Override
                public void onVideoDecoderError(final IMediaPlayer mediaPlayer) {
                    onVideoDecoderErrorListener.onVideoDecoderError(MediaPlayerProxy.this);
                }
            });
        }
        else {
            this.mBackEndMediaPlayer.setOnVideoDecoderErrorListener(null);
        }
    }
    
    @Override
    public void setAudioStreamType(final int audioStreamType) {
        this.mBackEndMediaPlayer.setAudioStreamType(audioStreamType);
    }
    
    @Override
    public void setKeepInBackground(final boolean keepInBackground) {
        this.mBackEndMediaPlayer.setKeepInBackground(keepInBackground);
    }
    
    @Override
    public int getVideoSarNum() {
        return this.mBackEndMediaPlayer.getVideoSarNum();
    }
    
    @Override
    public int getVideoSarDen() {
        return this.mBackEndMediaPlayer.getVideoSarDen();
    }
    
    @Override
    public void setWakeMode(final Context context, final int n) {
        this.mBackEndMediaPlayer.setWakeMode(context, n);
    }
    
    @Override
    public ITrackInfo[] getTrackInfo() {
        return this.mBackEndMediaPlayer.getTrackInfo();
    }
    
    @Override
    public void setLooping(final boolean looping) {
        this.mBackEndMediaPlayer.setLooping(looping);
    }
    
    @Override
    public boolean isLooping() {
        return this.mBackEndMediaPlayer.isLooping();
    }
    
    @Override
    public int getBitrateIndex() {
        return this.mBackEndMediaPlayer.getBitrateIndex();
    }
    
    @Override
    public void setBitrateIndex(final int bitrateIndex) {
        this.mBackEndMediaPlayer.setBitrateIndex(bitrateIndex);
    }
    
    @Override
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return this.mBackEndMediaPlayer.getSupportedBitrates();
    }
}
