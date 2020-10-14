package com.tencent.ijk.media.player;

import android.content.*;
import android.net.*;
import android.annotation.*;
import java.io.*;
import android.view.*;
import com.tencent.ijk.media.player.misc.*;
import java.util.*;

public interface IMediaPlayer
{
    public static final int MEDIA_INFO_UNKNOWN = 1;
    public static final int MEDIA_INFO_STARTED_AS_NEXT = 2;
    public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
    public static final int MEDIA_INFO_VIDEO_TRACK_LAGGING = 700;
    public static final int MEDIA_INFO_BUFFERING_START = 701;
    public static final int MEDIA_INFO_BUFFERING_END = 702;
    public static final int MEDIA_INFO_NETWORK_BANDWIDTH = 703;
    public static final int MEDIA_INFO_BAD_INTERLEAVING = 800;
    public static final int MEDIA_INFO_NOT_SEEKABLE = 801;
    public static final int MEDIA_INFO_METADATA_UPDATE = 802;
    public static final int MEDIA_INFO_TIMED_TEXT_ERROR = 900;
    public static final int MEDIA_INFO_UNSUPPORTED_SUBTITLE = 901;
    public static final int MEDIA_INFO_SUBTITLE_TIMED_OUT = 902;
    public static final int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;
    public static final int MEDIA_INFO_AUDIO_RENDERING_START = 10002;
    public static final int MEDIA_INFO_MEDIA_ACCURATE_SEEK_COMPLETE = 10100;
    public static final int MEDIA_INFO_FIRST_VIDEO_PACKET = 10011;
    public static final int MEDIA_ERROR_UNKNOWN = 1;
    public static final int MEDIA_ERROR_SERVER_DIED = 100;
    public static final int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;
    public static final int MEDIA_ERROR_IO = -1004;
    public static final int MEDIA_ERROR_MALFORMED = -1007;
    public static final int MEDIA_ERROR_UNSUPPORTED = -1010;
    public static final int MEDIA_ERROR_TIMED_OUT = -110;
    
    void setDisplay(final SurfaceHolder p0);
    
    void setDataSource(final Context p0, final Uri p1) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
    
    @TargetApi(14)
    void setDataSource(final Context p0, final Uri p1, final Map<String, String> p2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
    
    void setDataSource(final FileDescriptor p0) throws IOException, IllegalArgumentException, IllegalStateException;
    
    void setDataSource(final String p0) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
    
    String getDataSource();
    
    void prepareAsync() throws IllegalStateException;
    
    void start() throws IllegalStateException;
    
    void stop() throws IllegalStateException;
    
    void pause() throws IllegalStateException;
    
    void setScreenOnWhilePlaying(final boolean p0);
    
    int getVideoWidth();
    
    int getVideoHeight();
    
    boolean isPlaying();
    
    void seekTo(final long p0) throws IllegalStateException;
    
    long getCurrentPosition();
    
    long getDuration();
    
    void release();
    
    void reset();
    
    void setRate(final float p0);
    
    float getRate();
    
    void setVolume(final float p0, final float p1);
    
    int getAudioSessionId();
    
    MediaInfo getMediaInfo();
    
    @Deprecated
    void setLogEnabled(final boolean p0);
    
    @Deprecated
    boolean isPlayable();
    
    void setOnPreparedListener(final OnPreparedListener p0);
    
    void setOnCompletionListener(final OnCompletionListener p0);
    
    void setOnBufferingUpdateListener(final OnBufferingUpdateListener p0);
    
    void setOnSeekCompleteListener(final OnSeekCompleteListener p0);
    
    void setOnVideoSizeChangedListener(final OnVideoSizeChangedListener p0);
    
    void setOnErrorListener(final OnErrorListener p0);
    
    void setOnInfoListener(final OnInfoListener p0);
    
    void setOnTimedTextListener(final OnTimedTextListener p0);
    
    void setOnHLSKeyErrorListener(final OnHLSKeyErrorListener p0);
    
    void setOnHevcVideoDecoderErrorListener(final OnHevcVideoDecoderErrorListener p0);
    
    void setOnVideoDecoderErrorListener(final OnVideoDecoderErrorListener p0);
    
    void setAudioStreamType(final int p0);
    
    @Deprecated
    void setKeepInBackground(final boolean p0);
    
    int getVideoSarNum();
    
    int getVideoSarDen();
    
    @Deprecated
    void setWakeMode(final Context p0, final int p1);
    
    void setLooping(final boolean p0);
    
    boolean isLooping();
    
    ITrackInfo[] getTrackInfo();
    
    void setSurface(final Surface p0);
    
    Surface getSurface();
    
    void setDataSource(final IMediaDataSource p0);
    
    int getBitrateIndex();
    
    void setBitrateIndex(final int p0);
    
    ArrayList<IjkBitrateItem> getSupportedBitrates();
    
    public interface OnVideoDecoderErrorListener
    {
        void onVideoDecoderError(final IMediaPlayer p0);
    }
    
    public interface OnHevcVideoDecoderErrorListener
    {
        void onHevcVideoDecoderError(final IMediaPlayer p0);
    }
    
    public interface OnHLSKeyErrorListener
    {
        void onHLSKeyError(final IMediaPlayer p0);
    }
    
    public interface OnTimedTextListener
    {
        void onTimedText(final IMediaPlayer p0, final IjkTimedText p1);
    }
    
    public interface OnInfoListener
    {
        boolean onInfo(final IMediaPlayer p0, final int p1, final int p2);
    }
    
    public interface OnErrorListener
    {
        boolean onError(final IMediaPlayer p0, final int p1, final int p2);
    }
    
    public interface OnVideoSizeChangedListener
    {
        void onVideoSizeChanged(final IMediaPlayer p0, final int p1, final int p2, final int p3, final int p4);
    }
    
    public interface OnSeekCompleteListener
    {
        void onSeekComplete(final IMediaPlayer p0);
    }
    
    public interface OnBufferingUpdateListener
    {
        void onBufferingUpdate(final IMediaPlayer p0, final int p1);
    }
    
    public interface OnCompletionListener
    {
        void onCompletion(final IMediaPlayer p0);
    }
    
    public interface OnPreparedListener
    {
        void onPrepared(final IMediaPlayer p0);
    }
}
