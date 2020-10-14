package com.tencent.ijk.media.player.misc;

import com.tencent.ijk.media.player.*;
import android.text.*;
import android.annotation.*;
import java.util.*;

public class IjkMediaFormat implements IMediaFormat
{
    public static final String KEY_IJK_CODEC_LONG_NAME_UI = "ijk-codec-long-name-ui";
    public static final String KEY_IJK_CODEC_NAME_UI = "ijk-codec-name-ui";
    public static final String KEY_IJK_BIT_RATE_UI = "ijk-bit-rate-ui";
    public static final String KEY_IJK_CODEC_PROFILE_LEVEL_UI = "ijk-profile-level-ui";
    public static final String KEY_IJK_CODEC_PIXEL_FORMAT_UI = "ijk-pixel-format-ui";
    public static final String KEY_IJK_RESOLUTION_UI = "ijk-resolution-ui";
    public static final String KEY_IJK_FRAME_RATE_UI = "ijk-frame-rate-ui";
    public static final String KEY_IJK_SAMPLE_RATE_UI = "ijk-sample-rate-ui";
    public static final String KEY_IJK_CHANNEL_UI = "ijk-channel-ui";
    public static final String CODEC_NAME_H264 = "h264";
    public final IjkMediaMeta.IjkStreamMeta mMediaFormat;
    private static final Map<String, Formatter> sFormatterMap;
    
    public IjkMediaFormat(final IjkMediaMeta.IjkStreamMeta mMediaFormat) {
        IjkMediaFormat.sFormatterMap.put("ijk-codec-long-name-ui", new Formatter() {
            public String doFormat(final IjkMediaFormat ijkMediaFormat) {
                return IjkMediaFormat.this.mMediaFormat.getString("codec_long_name");
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-codec-name-ui", new Formatter() {
            public String doFormat(final IjkMediaFormat ijkMediaFormat) {
                return IjkMediaFormat.this.mMediaFormat.getString("codec_name");
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-bit-rate-ui", new Formatter() {
            @Override
            protected String doFormat(final IjkMediaFormat ijkMediaFormat) {
                final int integer = ijkMediaFormat.getInteger("bitrate");
                if (integer <= 0) {
                    return null;
                }
                if (integer < 1000) {
                    return String.format(Locale.US, "%d bit/s", integer);
                }
                return String.format(Locale.US, "%d kb/s", integer / 1000);
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-profile-level-ui", new Formatter() {
            @Override
            protected String doFormat(final IjkMediaFormat ijkMediaFormat) {
                String s = null;
                switch (ijkMediaFormat.getInteger("codec_profile_id")) {
                    case 66: {
                        s = "Baseline";
                        break;
                    }
                    case 578: {
                        s = "Constrained Baseline";
                        break;
                    }
                    case 77: {
                        s = "Main";
                        break;
                    }
                    case 88: {
                        s = "Extended";
                        break;
                    }
                    case 100: {
                        s = "High";
                        break;
                    }
                    case 110: {
                        s = "High 10";
                        break;
                    }
                    case 2158: {
                        s = "High 10 Intra";
                        break;
                    }
                    case 122: {
                        s = "High 4:2:2";
                        break;
                    }
                    case 2170: {
                        s = "High 4:2:2 Intra";
                        break;
                    }
                    case 144: {
                        s = "High 4:4:4";
                        break;
                    }
                    case 244: {
                        s = "High 4:4:4 Predictive";
                        break;
                    }
                    case 2292: {
                        s = "High 4:4:4 Intra";
                        break;
                    }
                    case 44: {
                        s = "CAVLC 4:4:4";
                        break;
                    }
                    default: {
                        return null;
                    }
                }
                final StringBuilder sb = new StringBuilder();
                sb.append(s);
                final String string = ijkMediaFormat.getString("codec_name");
                if (!TextUtils.isEmpty((CharSequence)string) && string.equalsIgnoreCase("h264")) {
                    final int integer = ijkMediaFormat.getInteger("codec_level");
                    if (integer < 10) {
                        return sb.toString();
                    }
                    sb.append(" Profile Level ");
                    sb.append(integer / 10 % 10);
                    if (integer % 10 != 0) {
                        sb.append(".");
                        sb.append(integer % 10);
                    }
                }
                return sb.toString();
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-pixel-format-ui", new Formatter() {
            @Override
            protected String doFormat(final IjkMediaFormat ijkMediaFormat) {
                return ijkMediaFormat.getString("codec_pixel_format");
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-resolution-ui", new Formatter() {
            @Override
            protected String doFormat(final IjkMediaFormat ijkMediaFormat) {
                final int integer = ijkMediaFormat.getInteger("width");
                final int integer2 = ijkMediaFormat.getInteger("height");
                final int integer3 = ijkMediaFormat.getInteger("sar_num");
                final int integer4 = ijkMediaFormat.getInteger("sar_den");
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                if (integer3 <= 0 || integer4 <= 0) {
                    return String.format(Locale.US, "%d x %d", integer, integer2);
                }
                return String.format(Locale.US, "%d x %d [SAR %d:%d]", integer, integer2, integer3, integer4);
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-frame-rate-ui", new Formatter() {
            @Override
            protected String doFormat(final IjkMediaFormat ijkMediaFormat) {
                final int integer = ijkMediaFormat.getInteger("fps_num");
                final int integer2 = ijkMediaFormat.getInteger("fps_den");
                if (integer <= 0 || integer2 <= 0) {
                    return null;
                }
                return String.valueOf(integer / (float)integer2);
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-sample-rate-ui", new Formatter() {
            @Override
            protected String doFormat(final IjkMediaFormat ijkMediaFormat) {
                final int integer = ijkMediaFormat.getInteger("sample_rate");
                if (integer <= 0) {
                    return null;
                }
                return String.format(Locale.US, "%d Hz", integer);
            }
        });
        IjkMediaFormat.sFormatterMap.put("ijk-channel-ui", new Formatter() {
            @Override
            protected String doFormat(final IjkMediaFormat ijkMediaFormat) {
                final int integer = ijkMediaFormat.getInteger("channel_layout");
                if (integer <= 0) {
                    return null;
                }
                if (integer == 4L) {
                    return "mono";
                }
                if (integer == 3L) {
                    return "stereo";
                }
                return String.format(Locale.US, "%x", integer);
            }
        });
        this.mMediaFormat = mMediaFormat;
    }
    
    @TargetApi(16)
    @Override
    public int getInteger(final String s) {
        if (this.mMediaFormat == null) {
            return 0;
        }
        return this.mMediaFormat.getInt(s);
    }
    
    @Override
    public String getString(final String s) {
        if (this.mMediaFormat == null) {
            return null;
        }
        if (IjkMediaFormat.sFormatterMap.containsKey(s)) {
            return IjkMediaFormat.sFormatterMap.get(s).format(this);
        }
        return this.mMediaFormat.getString(s);
    }
    
    static {
        sFormatterMap = new HashMap<String, Formatter>();
    }
    
    private abstract static class Formatter
    {
        public String format(final IjkMediaFormat ijkMediaFormat) {
            final String doFormat = this.doFormat(ijkMediaFormat);
            if (TextUtils.isEmpty((CharSequence)doFormat)) {
                return this.getDefaultString();
            }
            return doFormat;
        }
        
        protected abstract String doFormat(final IjkMediaFormat p0);
        
        protected String getDefaultString() {
            return "N/A";
        }
    }
}
