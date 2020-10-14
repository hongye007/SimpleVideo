package com.tencent.ijk.media.player.misc;

import android.os.*;
import android.media.*;
import android.annotation.*;

public class AndroidTrackInfo implements ITrackInfo
{
    private final MediaPlayer.TrackInfo mTrackInfo;
    
    public static AndroidTrackInfo[] fromMediaPlayer(final MediaPlayer mediaPlayer) {
        if (Build.VERSION.SDK_INT >= 16) {
            return fromTrackInfo(mediaPlayer.getTrackInfo());
        }
        return null;
    }
    
    private static AndroidTrackInfo[] fromTrackInfo(final MediaPlayer.TrackInfo[] array) {
        if (array == null) {
            return null;
        }
        final AndroidTrackInfo[] array2 = new AndroidTrackInfo[array.length];
        for (int i = 0; i < array.length; ++i) {
            array2[i] = new AndroidTrackInfo(array[i]);
        }
        return array2;
    }
    
    private AndroidTrackInfo(final MediaPlayer.TrackInfo mTrackInfo) {
        this.mTrackInfo = mTrackInfo;
    }
    
    @TargetApi(19)
    @Override
    public IMediaFormat getFormat() {
        if (this.mTrackInfo == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 19) {
            return null;
        }
        final MediaFormat format = this.mTrackInfo.getFormat();
        if (format == null) {
            return null;
        }
        return new AndroidMediaFormat(format);
    }
    
    @TargetApi(16)
    @Override
    public String getLanguage() {
        if (this.mTrackInfo == null) {
            return "und";
        }
        return this.mTrackInfo.getLanguage();
    }
    
    @TargetApi(16)
    @Override
    public int getTrackType() {
        if (this.mTrackInfo == null) {
            return 0;
        }
        return this.mTrackInfo.getTrackType();
    }
    
    @TargetApi(16)
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(this.getClass().getSimpleName());
        sb.append('{');
        if (this.mTrackInfo != null) {
            sb.append(this.mTrackInfo.toString());
        }
        else {
            sb.append("null");
        }
        sb.append('}');
        return sb.toString();
    }
    
    @TargetApi(16)
    @Override
    public String getInfoInline() {
        if (this.mTrackInfo != null) {
            return this.mTrackInfo.toString();
        }
        return "null";
    }
}
