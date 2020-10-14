package com.tencent.ijk.media.player.misc;

import android.media.*;
import android.annotation.*;

public class AndroidMediaFormat implements IMediaFormat
{
    private final MediaFormat mMediaFormat;
    
    public AndroidMediaFormat(final MediaFormat mMediaFormat) {
        this.mMediaFormat = mMediaFormat;
    }
    
    @TargetApi(16)
    @Override
    public int getInteger(final String s) {
        if (this.mMediaFormat == null) {
            return 0;
        }
        return this.mMediaFormat.getInteger(s);
    }
    
    @TargetApi(16)
    @Override
    public String getString(final String s) {
        if (this.mMediaFormat == null) {
            return null;
        }
        return this.mMediaFormat.getString(s);
    }
    
    @TargetApi(16)
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(128);
        sb.append(this.getClass().getName());
        sb.append('{');
        if (this.mMediaFormat != null) {
            sb.append(this.mMediaFormat.toString());
        }
        else {
            sb.append("null");
        }
        sb.append('}');
        return sb.toString();
    }
}
