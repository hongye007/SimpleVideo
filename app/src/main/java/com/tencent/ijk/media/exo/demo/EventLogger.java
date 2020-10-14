package com.tencent.ijk.media.exo.demo;

import com.google.android.exoplayer2.audio.*;
import com.google.android.exoplayer2.drm.*;
import com.google.android.exoplayer2.video.*;
import java.text.*;
import android.os.*;
import android.util.*;
import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.trackselection.*;
import com.google.android.exoplayer2.source.*;
import com.google.android.exoplayer2.metadata.*;
import com.google.android.exoplayer2.decoder.*;
import android.view.*;
import java.io.*;
import com.google.android.exoplayer2.upstream.*;
import com.google.android.exoplayer2.metadata.id3.*;
import com.google.android.exoplayer2.metadata.emsg.*;
import java.util.*;

public class EventLogger implements ExoPlayer.EventListener, AudioRendererEventListener, DefaultDrmSessionManager.EventListener, MetadataRenderer.Output, AdaptiveMediaSourceEventListener, ExtractorMediaSource.EventListener, VideoRendererEventListener
{
    private static final String TAG = "EventLogger";
    private static final int MAX_TIMELINE_ITEM_LINES = 3;
    private static final NumberFormat TIME_FORMAT;
    private final MappingTrackSelector trackSelector;
    private final Timeline.Window window;
    private final Timeline.Period period;
    private final long startTimeMs;
    private long mBytesLoaded;
    private long mBytesLoadedSeconds;
    private long mLastBytesLoadedTime;
    
    public EventLogger(final MappingTrackSelector trackSelector) {
        this.mBytesLoaded = 0L;
        this.mBytesLoadedSeconds = 0L;
        this.mLastBytesLoadedTime = 0L;
        this.trackSelector = trackSelector;
        this.window = new Timeline.Window();
        this.period = new Timeline.Period();
        this.startTimeMs = SystemClock.elapsedRealtime();
    }
    
    public void onLoadingChanged(final boolean b) {
        Log.d("EventLogger", "loading [" + b + "]");
    }
    
    public void onPlayerStateChanged(final boolean b, final int n) {
        Log.d("EventLogger", "state [" + this.getSessionTimeString() + ", " + b + ", " + getStateString(n) + "]");
    }
    
    public void onPositionDiscontinuity() {
        Log.d("EventLogger", "positionDiscontinuity");
    }
    
    public void onPlaybackParametersChanged(final PlaybackParameters playbackParameters) {
        Log.d("EventLogger", "playbackParameters " + String.format("[speed=%.2f, pitch=%.2f]", playbackParameters.speed, playbackParameters.pitch));
    }
    
    public void onTimelineChanged(final Timeline timeline, final Object o) {
        final int periodCount = timeline.getPeriodCount();
        final int windowCount = timeline.getWindowCount();
        Log.d("EventLogger", "sourceInfo [periodCount=" + periodCount + ", windowCount=" + windowCount);
        for (int i = 0; i < Math.min(periodCount, 3); ++i) {
            timeline.getPeriod(i, this.period);
            Log.d("EventLogger", "  period [" + getTimeString(this.period.getDurationMs()) + "]");
        }
        if (periodCount > 3) {
            Log.d("EventLogger", "  ...");
        }
        for (int j = 0; j < Math.min(windowCount, 3); ++j) {
            timeline.getWindow(j, this.window);
            Log.d("EventLogger", "  window [" + getTimeString(this.window.getDurationMs()) + ", " + this.window.isSeekable + ", " + this.window.isDynamic + "]");
        }
        if (windowCount > 3) {
            Log.d("EventLogger", "  ...");
        }
        Log.d("EventLogger", "]");
    }
    
    public void onPlayerError(final ExoPlaybackException ex) {
        Log.e("EventLogger", "playerFailed [" + this.getSessionTimeString() + "]", (Throwable)ex);
    }
    
    public void onTracksChanged(final TrackGroupArray trackGroupArray, final TrackSelectionArray trackSelectionArray) {
        final MappingTrackSelector.MappedTrackInfo currentMappedTrackInfo = this.trackSelector.getCurrentMappedTrackInfo();
        if (currentMappedTrackInfo == null) {
            Log.d("EventLogger", "Tracks []");
            return;
        }
        Log.d("EventLogger", "Tracks [");
        for (int i = 0; i < currentMappedTrackInfo.length; ++i) {
            final TrackGroupArray trackGroups = currentMappedTrackInfo.getTrackGroups(i);
            final TrackSelection value = trackSelectionArray.get(i);
            if (trackGroups.length > 0) {
                Log.d("EventLogger", "  Renderer:" + i + " [");
                for (int j = 0; j < trackGroups.length; ++j) {
                    final TrackGroup value2 = trackGroups.get(j);
                    Log.d("EventLogger", "    Group:" + j + ", adaptive_supported=" + getAdaptiveSupportString(value2.length, currentMappedTrackInfo.getAdaptiveSupport(i, j, false)) + " [");
                    for (int k = 0; k < value2.length; ++k) {
                        Log.d("EventLogger", "      " + getTrackStatusString(value, value2, k) + " Track:" + k + ", " + Format.toLogString(value2.getFormat(k)) + ", supported=" + getFormatSupportString(currentMappedTrackInfo.getTrackFormatSupport(i, j, k)));
                    }
                    Log.d("EventLogger", "    ]");
                }
                if (value != null) {
                    for (int l = 0; l < value.length(); ++l) {
                        final Metadata metadata = value.getFormat(l).metadata;
                        if (metadata != null) {
                            Log.d("EventLogger", "    Metadata [");
                            this.printMetadata(metadata, "      ");
                            Log.d("EventLogger", "    ]");
                            break;
                        }
                    }
                }
                Log.d("EventLogger", "  ]");
            }
        }
        final TrackGroupArray unassociatedTrackGroups = currentMappedTrackInfo.getUnassociatedTrackGroups();
        if (unassociatedTrackGroups.length > 0) {
            Log.d("EventLogger", "  Renderer:None [");
            for (int n = 0; n < unassociatedTrackGroups.length; ++n) {
                Log.d("EventLogger", "    Group:" + n + " [");
                final TrackGroup value3 = unassociatedTrackGroups.get(n);
                for (int n2 = 0; n2 < value3.length; ++n2) {
                    Log.d("EventLogger", "      " + getTrackStatusString(false) + " Track:" + n2 + ", " + Format.toLogString(value3.getFormat(n2)) + ", supported=" + getFormatSupportString(0));
                }
                Log.d("EventLogger", "    ]");
            }
            Log.d("EventLogger", "  ]");
        }
        Log.d("EventLogger", "]");
    }
    
    public void onMetadata(final Metadata metadata) {
        Log.d("EventLogger", "onMetadata [");
        this.printMetadata(metadata, "  ");
        Log.d("EventLogger", "]");
    }
    
    public void onAudioEnabled(final DecoderCounters decoderCounters) {
        Log.d("EventLogger", "audioEnabled [" + this.getSessionTimeString() + "]");
    }
    
    public void onAudioSessionId(final int n) {
        Log.d("EventLogger", "audioSessionId [" + n + "]");
    }
    
    public void onAudioDecoderInitialized(final String s, final long n, final long n2) {
        Log.d("EventLogger", "audioDecoderInitialized [" + this.getSessionTimeString() + ", " + s + "]");
    }
    
    public void onAudioInputFormatChanged(final Format format) {
        Log.d("EventLogger", "audioFormatChanged [" + this.getSessionTimeString() + ", " + Format.toLogString(format) + "]");
    }
    
    public void onAudioDisabled(final DecoderCounters decoderCounters) {
        Log.d("EventLogger", "audioDisabled [" + this.getSessionTimeString() + "]");
    }
    
    public void onAudioTrackUnderrun(final int n, final long n2, final long n3) {
        this.printInternalError("audioTrackUnderrun [" + n + ", " + n2 + ", " + n3 + "]", null);
    }
    
    public void onVideoEnabled(final DecoderCounters decoderCounters) {
        Log.d("EventLogger", "videoEnabled [" + this.getSessionTimeString() + "]");
    }
    
    public void onVideoDecoderInitialized(final String s, final long n, final long n2) {
        Log.d("EventLogger", "videoDecoderInitialized [" + this.getSessionTimeString() + ", " + s + "]");
    }
    
    public void onVideoInputFormatChanged(final Format format) {
        Log.d("EventLogger", "videoFormatChanged [" + this.getSessionTimeString() + ", " + Format.toLogString(format) + "]");
    }
    
    public void onVideoDisabled(final DecoderCounters decoderCounters) {
        Log.d("EventLogger", "videoDisabled [" + this.getSessionTimeString() + "]");
    }
    
    public void onDroppedFrames(final int n, final long n2) {
        Log.d("EventLogger", "droppedFrames [" + this.getSessionTimeString() + ", " + n + "]");
    }
    
    public void onVideoSizeChanged(final int n, final int n2, final int n3, final float n4) {
    }
    
    public void onRenderedFirstFrame(final Surface surface) {
        Log.d("EventLogger", "renderedFirstFrame [" + surface + "]");
    }
    
    public void onDrmSessionManagerError(final Exception ex) {
        this.printInternalError("drmSessionManagerError", ex);
    }
    
    public void onDrmKeysRestored() {
        Log.d("EventLogger", "drmKeysRestored [" + this.getSessionTimeString() + "]");
    }
    
    public void onDrmKeysRemoved() {
        Log.d("EventLogger", "drmKeysRemoved [" + this.getSessionTimeString() + "]");
    }
    
    public void onDrmKeysLoaded() {
        Log.d("EventLogger", "drmKeysLoaded [" + this.getSessionTimeString() + "]");
    }
    
    public void onLoadError(final IOException ex) {
        this.printInternalError("loadError", ex);
    }
    
    public void onLoadStarted(final DataSpec dataSpec, final int n, final int n2, final Format format, final int n3, final Object o, final long n4, final long n5, final long n6) {
        if (this.mLastBytesLoadedTime == 0L) {
            this.mLastBytesLoadedTime = System.currentTimeMillis();
        }
    }
    
    public void onLoadError(final DataSpec dataSpec, final int n, final int n2, final Format format, final int n3, final Object o, final long n4, final long n5, final long n6, final long n7, final long n8, final IOException ex, final boolean b) {
        this.printInternalError("loadError", ex);
    }
    
    public void onLoadCanceled(final DataSpec dataSpec, final int n, final int n2, final Format format, final int n3, final Object o, final long n4, final long n5, final long n6, final long n7, final long n8) {
    }
    
    public void onLoadCompleted(final DataSpec dataSpec, final int n, final int n2, final Format format, final int n3, final Object o, final long n4, final long n5, final long n6, final long n7, final long n8) {
        final long currentTimeMillis = System.currentTimeMillis();
        if (this.mLastBytesLoadedTime == 0L) {
            return;
        }
        this.logBytesLoadedInSeconds(n8, (float)((currentTimeMillis - this.mLastBytesLoadedTime) / 1000L));
        this.mLastBytesLoadedTime = currentTimeMillis;
    }
    
    private void logBytesLoadedInSeconds(final long n, final float n2) {
        this.mBytesLoaded += n;
        this.mBytesLoadedSeconds += (long)n2;
    }
    
    public int getObservedBitrate() {
        if (this.mBytesLoadedSeconds != 0L) {
            final double n = this.mBytesLoaded / this.mBytesLoadedSeconds * 8.0;
            Log.d("EventLogger", " mBytesLoaded " + this.mBytesLoaded + " in " + this.mBytesLoadedSeconds + " seconds (" + (int)n + " b/s indicated ");
            return (int)n;
        }
        return 0;
    }
    
    public void onUpstreamDiscarded(final int n, final long n2, final long n3) {
    }
    
    public void onDownstreamFormatChanged(final int n, final Format format, final int n2, final Object o, final long n3) {
    }
    
    private void printInternalError(final String s, final Exception ex) {
        Log.e("EventLogger", "internalError [" + this.getSessionTimeString() + ", " + s + "]", (Throwable)ex);
    }
    
    private void printMetadata(final Metadata metadata, final String s) {
        for (int i = 0; i < metadata.length(); ++i) {
            final Metadata.Entry value = metadata.get(i);
            if (value instanceof TextInformationFrame) {
                final TextInformationFrame textInformationFrame = (TextInformationFrame)value;
                Log.d("EventLogger", s + String.format("%s: value=%s", textInformationFrame.id, textInformationFrame.value));
            }
            else if (value instanceof UrlLinkFrame) {
                final UrlLinkFrame urlLinkFrame = (UrlLinkFrame)value;
                Log.d("EventLogger", s + String.format("%s: url=%s", urlLinkFrame.id, urlLinkFrame.url));
            }
            else if (value instanceof PrivFrame) {
                final PrivFrame privFrame = (PrivFrame)value;
                Log.d("EventLogger", s + String.format("%s: owner=%s", privFrame.id, privFrame.owner));
            }
            else if (value instanceof GeobFrame) {
                final GeobFrame geobFrame = (GeobFrame)value;
                Log.d("EventLogger", s + String.format("%s: mimeType=%s, filename=%s, description=%s", geobFrame.id, geobFrame.mimeType, geobFrame.filename, geobFrame.description));
            }
            else if (value instanceof ApicFrame) {
                final ApicFrame apicFrame = (ApicFrame)value;
                Log.d("EventLogger", s + String.format("%s: mimeType=%s, description=%s", apicFrame.id, apicFrame.mimeType, apicFrame.description));
            }
            else if (value instanceof CommentFrame) {
                final CommentFrame commentFrame = (CommentFrame)value;
                Log.d("EventLogger", s + String.format("%s: language=%s, description=%s", commentFrame.id, commentFrame.language, commentFrame.description));
            }
            else if (value instanceof Id3Frame) {
                Log.d("EventLogger", s + String.format("%s", ((Id3Frame)value).id));
            }
            else if (value instanceof EventMessage) {
                final EventMessage eventMessage = (EventMessage)value;
                Log.d("EventLogger", s + String.format("EMSG: scheme=%s, id=%d, value=%s", eventMessage.schemeIdUri, eventMessage.id, eventMessage.value));
            }
        }
    }
    
    private String getSessionTimeString() {
        return getTimeString(SystemClock.elapsedRealtime() - this.startTimeMs);
    }
    
    private static String getTimeString(final long n) {
        return (n == -9223372036854775807L) ? "?" : EventLogger.TIME_FORMAT.format(n / 1000.0f);
    }
    
    private static String getStateString(final int n) {
        switch (n) {
            case 2: {
                return "B";
            }
            case 4: {
                return "E";
            }
            case 1: {
                return "I";
            }
            case 3: {
                return "R";
            }
            default: {
                return "?";
            }
        }
    }
    
    private static String getFormatSupportString(final int n) {
        switch (n) {
            case 3: {
                return "YES";
            }
            case 2: {
                return "NO_EXCEEDS_CAPABILITIES";
            }
            case 1: {
                return "NO_UNSUPPORTED_TYPE";
            }
            case 0: {
                return "NO";
            }
            default: {
                return "?";
            }
        }
    }
    
    private static String getAdaptiveSupportString(final int n, final int n2) {
        if (n < 2) {
            return "N/A";
        }
        switch (n2) {
            case 8: {
                return "YES";
            }
            case 4: {
                return "YES_NOT_SEAMLESS";
            }
            case 0: {
                return "NO";
            }
            default: {
                return "?";
            }
        }
    }
    
    private static String getTrackStatusString(final TrackSelection trackSelection, final TrackGroup trackGroup, final int n) {
        return getTrackStatusString(trackSelection != null && trackSelection.getTrackGroup() == trackGroup && trackSelection.indexOf(n) != -1);
    }
    
    private static String getTrackStatusString(final boolean b) {
        return b ? "[X]" : "[ ]";
    }
    
    static {
        (TIME_FORMAT = NumberFormat.getInstance(Locale.US)).setMinimumFractionDigits(2);
        EventLogger.TIME_FORMAT.setMaximumFractionDigits(2);
        EventLogger.TIME_FORMAT.setGroupingUsed(false);
    }
}
