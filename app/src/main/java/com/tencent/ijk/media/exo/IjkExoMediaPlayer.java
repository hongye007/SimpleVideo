package com.tencent.ijk.media.exo;

import android.content.*;
import com.tencent.ijk.media.exo.demo.*;
import android.os.*;
import android.net.*;
import com.google.android.exoplayer2.audio.*;
import com.google.android.exoplayer2.video.*;
import com.google.android.exoplayer2.metadata.*;
import android.view.*;
import java.io.*;
import com.google.android.exoplayer2.decoder.*;
import android.text.*;
import com.google.android.exoplayer2.util.*;
import com.google.android.exoplayer2.source.smoothstreaming.*;
import com.google.android.exoplayer2.source.dash.*;
import com.google.android.exoplayer2.source.hls.*;
import com.google.android.exoplayer2.extractor.*;
import com.google.android.exoplayer2.upstream.*;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.*;
import java.util.*;
import com.tencent.ijk.media.player.*;
import com.tencent.ijk.media.player.misc.*;

public class IjkExoMediaPlayer extends AbstractMediaPlayer implements ExoPlayer.EventListener
{
    private Context mAppContext;
    private SimpleExoPlayer player;
    private EventLogger eventLogger;
    private Handler mainHandler;
    private Uri mDataSource;
    private int mVideoWidth;
    private int mVideoHeight;
    private Surface mSurface;
    private DefaultTrackSelector trackSelector;
    private DataSource.Factory mediaDataSourceFactory;
    private static final DefaultBandwidthMeter BANDWIDTH_METER;
    private SimplePlayerListener mSimpleListener;
    
    public IjkExoMediaPlayer(final Context context) {
        this.mSimpleListener = new SimplePlayerListener();
        this.mAppContext = context.getApplicationContext();
        this.mediaDataSourceFactory = this.buildDataSourceFactory(true);
        this.mainHandler = new Handler();
        final DefaultRenderersFactory defaultRenderersFactory = new DefaultRenderersFactory(context);
        this.trackSelector = new DefaultTrackSelector((TrackSelection.Factory)new AdaptiveTrackSelection.Factory((BandwidthMeter)IjkExoMediaPlayer.BANDWIDTH_METER));
        (this.player = ExoPlayerFactory.newSimpleInstance((RenderersFactory)defaultRenderersFactory, (TrackSelector)this.trackSelector)).addListener((ExoPlayer.EventListener)this);
        this.eventLogger = new EventLogger((MappingTrackSelector)this.trackSelector);
        this.player.addListener((ExoPlayer.EventListener)this.eventLogger);
        this.player.setAudioDebugListener((AudioRendererEventListener)this.eventLogger);
        this.player.setVideoDebugListener((VideoRendererEventListener)this.eventLogger);
        this.player.setMetadataOutput((MetadataRenderer.Output)this.eventLogger);
        this.player.setVideoListener((SimpleExoPlayer.VideoListener)this.mSimpleListener);
    }
    
    public SimpleExoPlayer getPlayer() {
        return this.player;
    }
    
    public void setDisplay(final SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            this.setSurface(null);
        }
        else {
            this.setSurface(surfaceHolder.getSurface());
        }
    }
    
    public void setSurface(final Surface surface) {
        this.mSurface = surface;
        if (this.player != null) {
            this.player.setVideoSurface(surface);
        }
    }
    
    public Surface getSurface() {
        return this.mSurface;
    }
    
    public void setDataSource(final Context context, final Uri mDataSource) {
        this.mDataSource = mDataSource;
    }
    
    public void setDataSource(final Context context, final Uri uri, final Map<String, String> map) {
        this.setDataSource(context, uri);
    }
    
    public void setDataSource(final String s) {
        this.setDataSource(this.mAppContext, Uri.parse(s));
    }
    
    public void setDataSource(final FileDescriptor fileDescriptor) {
        throw new UnsupportedOperationException("no support");
    }
    
    public String getDataSource() {
        return this.mDataSource.toString();
    }
    
    public void prepareAsync() {
        this.player.prepare(this.buildMediaSource(this.mDataSource, null));
        this.player.setPlayWhenReady(false);
    }
    
    public void start() {
        if (this.player == null) {
            return;
        }
        this.player.setPlayWhenReady(true);
    }
    
    public void stop() throws IllegalStateException {
        if (this.player == null) {
            return;
        }
        this.player.release();
    }
    
    public void pause() throws IllegalStateException {
        if (this.player == null) {
            return;
        }
        this.player.setPlayWhenReady(false);
    }
    
    public void setWakeMode(final Context context, final int n) {
    }
    
    public void setScreenOnWhilePlaying(final boolean b) {
    }
    
    public IjkTrackInfo[] getTrackInfo() {
        return null;
    }
    
    public int getVideoWidth() {
        return this.mVideoWidth;
    }
    
    public int getVideoHeight() {
        return this.mVideoHeight;
    }
    
    public boolean isPlaying() {
        if (this.player == null) {
            return false;
        }
        switch (this.player.getPlaybackState()) {
            case 2:
            case 3: {
                return this.player.getPlayWhenReady();
            }
            default: {
                return false;
            }
        }
    }
    
    public void seekTo(final long n) throws IllegalStateException {
        if (this.player == null) {
            return;
        }
        this.player.seekTo(n);
    }
    
    public long getCurrentPosition() {
        if (this.player == null) {
            return 0L;
        }
        return this.player.getCurrentPosition();
    }
    
    public long getDuration() {
        if (this.player == null) {
            return 0L;
        }
        return this.player.getDuration();
    }
    
    public int getVideoSarNum() {
        return 1;
    }
    
    public int getVideoSarDen() {
        return 1;
    }
    
    public void reset() {
        if (this.player != null) {
            this.player.release();
            this.player.removeListener((ExoPlayer.EventListener)this.eventLogger);
            this.player = null;
        }
        this.mSurface = null;
        this.mDataSource = null;
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }
    
    public void setLooping(final boolean b) {
        throw new UnsupportedOperationException("no support");
    }
    
    public boolean isLooping() {
        return false;
    }
    
    public void setRate(final float n) {
        this.player.setPlaybackParameters(new PlaybackParameters(n, n));
    }
    
    public float getRate() {
        return this.player.getPlaybackParameters().speed;
    }
    
    public void setVolume(final float n, final float n2) {
        this.player.setVolume((n + n2) / 2.0f);
    }
    
    public int getAudioSessionId() {
        return 0;
    }
    
    public MediaInfo getMediaInfo() {
        return null;
    }
    
    public void setLogEnabled(final boolean b) {
    }
    
    public boolean isPlayable() {
        return true;
    }
    
    public void setAudioStreamType(final int n) {
    }
    
    public void setKeepInBackground(final boolean b) {
    }
    
    public void release() {
        if (this.player != null) {
            this.reset();
            this.eventLogger = null;
        }
    }
    
    public int getBufferedPercentage() {
        if (this.player == null) {
            return 0;
        }
        return this.player.getBufferedPercentage();
    }
    
    public Format getVideoFormat() {
        if (this.player == null) {
            return null;
        }
        return this.player.getVideoFormat();
    }
    
    public int getObservedBitrate() {
        return this.eventLogger.getObservedBitrate();
    }
    
    public DecoderCounters getVideoDecoderCounters() {
        return this.player.getVideoDecoderCounters();
    }
    
    public MediaSource buildMediaSource(final Uri uri, final String s) {
        final int n = TextUtils.isEmpty((CharSequence)s) ? Util.inferContentType(uri) : Util.inferContentType("." + s);
        switch (n) {
            case 1: {
                return (MediaSource)new SsMediaSource(uri, this.buildDataSourceFactory(false), (SsChunkSource.Factory)new DefaultSsChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, (AdaptiveMediaSourceEventListener)this.eventLogger);
            }
            case 0: {
                return (MediaSource)new DashMediaSource(uri, this.buildDataSourceFactory(false), (DashChunkSource.Factory)new DefaultDashChunkSource.Factory(this.mediaDataSourceFactory), this.mainHandler, (AdaptiveMediaSourceEventListener)this.eventLogger);
            }
            case 2: {
                return (MediaSource)new HlsMediaSource(uri, this.mediaDataSourceFactory, this.mainHandler, (AdaptiveMediaSourceEventListener)this.eventLogger);
            }
            case 3: {
                return (MediaSource)new ExtractorMediaSource(uri, this.mediaDataSourceFactory, (ExtractorsFactory)new DefaultExtractorsFactory(), this.mainHandler, (ExtractorMediaSource.EventListener)this.eventLogger);
            }
            default: {
                throw new IllegalStateException("Unsupported type: " + n);
            }
        }
    }
    
    private DataSource.Factory buildDataSourceFactory(final boolean b) {
        final DefaultBandwidthMeter defaultBandwidthMeter = b ? IjkExoMediaPlayer.BANDWIDTH_METER : null;
        return (DataSource.Factory)new DefaultDataSourceFactory(this.mAppContext, (TransferListener)defaultBandwidthMeter, (DataSource.Factory)this.buildHttpDataSourceFactory(defaultBandwidthMeter));
    }
    
    public HttpDataSource.Factory buildHttpDataSourceFactory(final DefaultBandwidthMeter defaultBandwidthMeter) {
        return (HttpDataSource.Factory)new DefaultHttpDataSourceFactory(Util.getUserAgent(this.mAppContext, "ExoPlayerDemo"), (TransferListener)defaultBandwidthMeter, 8000, 8000, true);
    }
    
    public void onTimelineChanged(final Timeline timeline, final Object o) {
    }
    
    public void onTracksChanged(final TrackGroupArray trackGroupArray, final TrackSelectionArray trackSelectionArray) {
    }
    
    public void onLoadingChanged(final boolean b) {
    }
    
    public void onPlayerStateChanged(final boolean b, final int n) {
        switch (n) {
            case 1: {
                this.notifyOnCompletion();
                break;
            }
            case 2: {
                this.notifyOnInfo(701, this.player.getBufferedPercentage());
                break;
            }
            case 3: {
                this.notifyOnPrepared();
                break;
            }
            case 4: {
                this.notifyOnCompletion();
                break;
            }
        }
    }
    
    public void onPlayerError(final ExoPlaybackException ex) {
        this.notifyOnError(1, 1);
    }
    
    public void onPositionDiscontinuity() {
    }
    
    public void onPlaybackParametersChanged(final PlaybackParameters playbackParameters) {
    }
    
    public int getBitrateIndex() {
        return 0;
    }
    
    public void setBitrateIndex(final int n) {
    }
    
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return new ArrayList<IjkBitrateItem>();
    }
    
    static {
        BANDWIDTH_METER = new DefaultBandwidthMeter();
    }
    
    private class SimplePlayerListener implements SimpleExoPlayer.VideoListener
    {
        public void onVideoSizeChanged(final int n, final int n2, final int n3, final float n4) {
            IjkExoMediaPlayer.this.mVideoWidth = n;
            IjkExoMediaPlayer.this.mVideoHeight = n2;
            AbstractMediaPlayer.this.notifyOnVideoSizeChanged(n, n2, 1, 1);
            if (n3 > 0) {
                AbstractMediaPlayer.this.notifyOnInfo(10001, n3);
            }
        }
        
        public void onRenderedFirstFrame() {
            AbstractMediaPlayer.this.notifyOnInfo(3, 0);
        }
    }
}
