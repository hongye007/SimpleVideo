package com.tencent.ijk.media.player.misc;

import com.tencent.ijk.media.player.*;
import android.text.*;

public class IjkTrackInfo implements ITrackInfo
{
    private int mTrackType;
    private IjkMediaMeta.IjkStreamMeta mStreamMeta;
    
    public IjkTrackInfo(final IjkMediaMeta.IjkStreamMeta mStreamMeta) {
        this.mTrackType = 0;
        this.mStreamMeta = mStreamMeta;
    }
    
    public void setMediaMeta(final IjkMediaMeta.IjkStreamMeta mStreamMeta) {
        this.mStreamMeta = mStreamMeta;
    }
    
    @Override
    public IMediaFormat getFormat() {
        return new IjkMediaFormat(this.mStreamMeta);
    }
    
    @Override
    public String getLanguage() {
        if (this.mStreamMeta == null || TextUtils.isEmpty((CharSequence)this.mStreamMeta.mLanguage)) {
            return "und";
        }
        return this.mStreamMeta.mLanguage;
    }
    
    @Override
    public int getTrackType() {
        return this.mTrackType;
    }
    
    public void setTrackType(final int mTrackType) {
        this.mTrackType = mTrackType;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + '{' + this.getInfoInline() + "}";
    }
    
    @Override
    public String getInfoInline() {
        final StringBuilder sb = new StringBuilder(128);
        switch (this.mTrackType) {
            case 1: {
                sb.append("VIDEO");
                sb.append(", ");
                sb.append(this.mStreamMeta.getCodecShortNameInline());
                sb.append(", ");
                sb.append(this.mStreamMeta.getBitrateInline());
                sb.append(", ");
                sb.append(this.mStreamMeta.getResolutionInline());
                break;
            }
            case 2: {
                sb.append("AUDIO");
                sb.append(", ");
                sb.append(this.mStreamMeta.getCodecShortNameInline());
                sb.append(", ");
                sb.append(this.mStreamMeta.getBitrateInline());
                sb.append(", ");
                sb.append(this.mStreamMeta.getSampleRateInline());
                break;
            }
            case 3: {
                sb.append("TIMEDTEXT");
                sb.append(", ");
                sb.append(this.mStreamMeta.mLanguage);
                break;
            }
            case 4: {
                sb.append("SUBTITLE");
                break;
            }
            default: {
                sb.append("UNKNOWN");
                break;
            }
        }
        return sb.toString();
    }
}
