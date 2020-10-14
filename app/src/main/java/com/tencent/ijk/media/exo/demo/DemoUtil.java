package com.tencent.ijk.media.exo.demo;

import com.google.android.exoplayer2.*;
import com.google.android.exoplayer2.util.*;
import android.text.*;
import java.util.*;

final class DemoUtil
{
    public static String buildTrackName(final Format format) {
        String s;
        if (MimeTypes.isVideo(format.sampleMimeType)) {
            s = joinWithSeparator(joinWithSeparator(joinWithSeparator(buildResolutionString(format), buildBitrateString(format)), buildTrackIdString(format)), buildSampleMimeTypeString(format));
        }
        else if (MimeTypes.isAudio(format.sampleMimeType)) {
            s = joinWithSeparator(joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildAudioPropertyString(format)), buildBitrateString(format)), buildTrackIdString(format)), buildSampleMimeTypeString(format));
        }
        else {
            s = joinWithSeparator(joinWithSeparator(joinWithSeparator(buildLanguageString(format), buildBitrateString(format)), buildTrackIdString(format)), buildSampleMimeTypeString(format));
        }
        return (s.length() == 0) ? "unknown" : s;
    }
    
    private static String buildResolutionString(final Format format) {
        return (format.width == -1 || format.height == -1) ? "" : (format.width + "x" + format.height);
    }
    
    private static String buildAudioPropertyString(final Format format) {
        return (format.channelCount == -1 || format.sampleRate == -1) ? "" : (format.channelCount + "ch, " + format.sampleRate + "Hz");
    }
    
    private static String buildLanguageString(final Format format) {
        return (TextUtils.isEmpty((CharSequence)format.language) || "und".equals(format.language)) ? "" : format.language;
    }
    
    private static String buildBitrateString(final Format format) {
        return (format.bitrate == -1) ? "" : String.format(Locale.US, "%.2fMbit", format.bitrate / 1000000.0f);
    }
    
    private static String joinWithSeparator(final String s, final String s2) {
        return (s.length() == 0) ? s2 : ((s2.length() == 0) ? s : (s + ", " + s2));
    }
    
    private static String buildTrackIdString(final Format format) {
        return (format.id == null) ? "" : ("id:" + format.id);
    }
    
    private static String buildSampleMimeTypeString(final Format format) {
        return (format.sampleMimeType == null) ? "" : format.sampleMimeType;
    }
    
    private DemoUtil() {
    }
}
