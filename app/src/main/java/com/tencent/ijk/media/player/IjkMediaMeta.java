package com.tencent.ijk.media.player;

import android.os.*;
import android.text.*;
import java.util.*;

public class IjkMediaMeta
{
    public static final String IJKM_KEY_FORMAT = "format";
    public static final String IJKM_KEY_DURATION_US = "duration_us";
    public static final String IJKM_KEY_START_US = "start_us";
    public static final String IJKM_KEY_BITRATE = "bitrate";
    public static final String IJKM_KEY_VIDEO_STREAM = "video";
    public static final String IJKM_KEY_AUDIO_STREAM = "audio";
    public static final String IJKM_KEY_TIMEDTEXT_STREAM = "timedtext";
    public static final String IJKM_KEY_TYPE = "type";
    public static final String IJKM_VAL_TYPE__VIDEO = "video";
    public static final String IJKM_VAL_TYPE__AUDIO = "audio";
    public static final String IJKM_VAL_TYPE__TIMEDTEXT = "timedtext";
    public static final String IJKM_VAL_TYPE__UNKNOWN = "unknown";
    public static final String IJKM_KEY_LANGUAGE = "language";
    public static final String IJKM_KEY_CODEC_NAME = "codec_name";
    public static final String IJKM_KEY_CODEC_PROFILE = "codec_profile";
    public static final String IJKM_KEY_CODEC_LEVEL = "codec_level";
    public static final String IJKM_KEY_CODEC_LONG_NAME = "codec_long_name";
    public static final String IJKM_KEY_CODEC_PIXEL_FORMAT = "codec_pixel_format";
    public static final String IJKM_KEY_CODEC_PROFILE_ID = "codec_profile_id";
    public static final String IJKM_KEY_WIDTH = "width";
    public static final String IJKM_KEY_HEIGHT = "height";
    public static final String IJKM_KEY_FPS_NUM = "fps_num";
    public static final String IJKM_KEY_FPS_DEN = "fps_den";
    public static final String IJKM_KEY_TBR_NUM = "tbr_num";
    public static final String IJKM_KEY_TBR_DEN = "tbr_den";
    public static final String IJKM_KEY_SAR_NUM = "sar_num";
    public static final String IJKM_KEY_SAR_DEN = "sar_den";
    public static final String IJKM_KEY_BITRATE_INDEX = "bitrate_index";
    public static final String IJKM_KEY_SAMPLE_RATE = "sample_rate";
    public static final String IJKM_KEY_CHANNEL_LAYOUT = "channel_layout";
    public static final String IJKM_KEY_STREAMS = "streams";
    public static final String IJKM_KEY_PROGRAMS = "programs";
    public static final String IJKM_KEY_M3U8 = "m3u8";
    public static final long AV_CH_FRONT_LEFT = 1L;
    public static final long AV_CH_FRONT_RIGHT = 2L;
    public static final long AV_CH_FRONT_CENTER = 4L;
    public static final long AV_CH_LOW_FREQUENCY = 8L;
    public static final long AV_CH_BACK_LEFT = 16L;
    public static final long AV_CH_BACK_RIGHT = 32L;
    public static final long AV_CH_FRONT_LEFT_OF_CENTER = 64L;
    public static final long AV_CH_FRONT_RIGHT_OF_CENTER = 128L;
    public static final long AV_CH_BACK_CENTER = 256L;
    public static final long AV_CH_SIDE_LEFT = 512L;
    public static final long AV_CH_SIDE_RIGHT = 1024L;
    public static final long AV_CH_TOP_CENTER = 2048L;
    public static final long AV_CH_TOP_FRONT_LEFT = 4096L;
    public static final long AV_CH_TOP_FRONT_CENTER = 8192L;
    public static final long AV_CH_TOP_FRONT_RIGHT = 16384L;
    public static final long AV_CH_TOP_BACK_LEFT = 32768L;
    public static final long AV_CH_TOP_BACK_CENTER = 65536L;
    public static final long AV_CH_TOP_BACK_RIGHT = 131072L;
    public static final long AV_CH_STEREO_LEFT = 536870912L;
    public static final long AV_CH_STEREO_RIGHT = 1073741824L;
    public static final long AV_CH_WIDE_LEFT = 2147483648L;
    public static final long AV_CH_WIDE_RIGHT = 4294967296L;
    public static final long AV_CH_SURROUND_DIRECT_LEFT = 8589934592L;
    public static final long AV_CH_SURROUND_DIRECT_RIGHT = 17179869184L;
    public static final long AV_CH_LOW_FREQUENCY_2 = 34359738368L;
    public static final long AV_CH_LAYOUT_MONO = 4L;
    public static final long AV_CH_LAYOUT_STEREO = 3L;
    public static final long AV_CH_LAYOUT_2POINT1 = 11L;
    public static final long AV_CH_LAYOUT_2_1 = 259L;
    public static final long AV_CH_LAYOUT_SURROUND = 7L;
    public static final long AV_CH_LAYOUT_3POINT1 = 15L;
    public static final long AV_CH_LAYOUT_4POINT0 = 263L;
    public static final long AV_CH_LAYOUT_4POINT1 = 271L;
    public static final long AV_CH_LAYOUT_2_2 = 1539L;
    public static final long AV_CH_LAYOUT_QUAD = 51L;
    public static final long AV_CH_LAYOUT_5POINT0 = 1543L;
    public static final long AV_CH_LAYOUT_5POINT1 = 1551L;
    public static final long AV_CH_LAYOUT_5POINT0_BACK = 55L;
    public static final long AV_CH_LAYOUT_5POINT1_BACK = 63L;
    public static final long AV_CH_LAYOUT_6POINT0 = 1799L;
    public static final long AV_CH_LAYOUT_6POINT0_FRONT = 1731L;
    public static final long AV_CH_LAYOUT_HEXAGONAL = 311L;
    public static final long AV_CH_LAYOUT_6POINT1 = 1807L;
    public static final long AV_CH_LAYOUT_6POINT1_BACK = 319L;
    public static final long AV_CH_LAYOUT_6POINT1_FRONT = 1739L;
    public static final long AV_CH_LAYOUT_7POINT0 = 1591L;
    public static final long AV_CH_LAYOUT_7POINT0_FRONT = 1735L;
    public static final long AV_CH_LAYOUT_7POINT1 = 1599L;
    public static final long AV_CH_LAYOUT_7POINT1_WIDE = 1743L;
    public static final long AV_CH_LAYOUT_7POINT1_WIDE_BACK = 255L;
    public static final long AV_CH_LAYOUT_OCTAGONAL = 1847L;
    public static final long AV_CH_LAYOUT_STEREO_DOWNMIX = 1610612736L;
    public static final int FF_PROFILE_H264_CONSTRAINED = 512;
    public static final int FF_PROFILE_H264_INTRA = 2048;
    public static final int FF_PROFILE_H264_BASELINE = 66;
    public static final int FF_PROFILE_H264_CONSTRAINED_BASELINE = 578;
    public static final int FF_PROFILE_H264_MAIN = 77;
    public static final int FF_PROFILE_H264_EXTENDED = 88;
    public static final int FF_PROFILE_H264_HIGH = 100;
    public static final int FF_PROFILE_H264_HIGH_10 = 110;
    public static final int FF_PROFILE_H264_HIGH_10_INTRA = 2158;
    public static final int FF_PROFILE_H264_HIGH_422 = 122;
    public static final int FF_PROFILE_H264_HIGH_422_INTRA = 2170;
    public static final int FF_PROFILE_H264_HIGH_444 = 144;
    public static final int FF_PROFILE_H264_HIGH_444_PREDICTIVE = 244;
    public static final int FF_PROFILE_H264_HIGH_444_INTRA = 2292;
    public static final int FF_PROFILE_H264_CAVLC_444 = 44;
    public Bundle mMediaMeta;
    public String mFormat;
    public long mDurationUS;
    public long mStartUS;
    public long mBitrate;
    public ArrayList<IjkBitrateItem> mBitrateItems;
    public final ArrayList<IjkStreamMeta> mStreams;
    public IjkStreamMeta mVideoStream;
    public IjkStreamMeta mAudioStream;
    
    public IjkMediaMeta() {
        this.mStreams = new ArrayList<IjkStreamMeta>();
    }
    
    public String getString(final String s) {
        return this.mMediaMeta.getString(s);
    }
    
    public int getInt(final String s) {
        return this.getInt(s, 0);
    }
    
    public int getInt(final String s, final int n) {
        final String string = this.getString(s);
        if (TextUtils.isEmpty((CharSequence)string)) {
            return n;
        }
        try {
            return Integer.parseInt(string);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public long getLong(final String s) {
        return this.getLong(s, 0L);
    }
    
    public long getLong(final String s, final long n) {
        final String string = this.getString(s);
        if (TextUtils.isEmpty((CharSequence)string)) {
            return n;
        }
        try {
            return Long.parseLong(string);
        }
        catch (NumberFormatException ex) {
            return n;
        }
    }
    
    public ArrayList<Bundle> getParcelableArrayList(final String s) {
        return (ArrayList<Bundle>)this.mMediaMeta.getParcelableArrayList(s);
    }
    
    public String getDurationInline() {
        final long n = (this.mDurationUS + 5000L) / 1000000L;
        final long n2 = n / 60L;
        return String.format(Locale.US, "%02d:%02d:%02d", n2 / 60L, n2 % 60L, n % 60L);
    }
    
    public Map<String, String> getMetaM3U8() {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        for (final String s : this.mMediaMeta.keySet()) {
            if (s.startsWith("m3u8:")) {
                hashMap.put(s, this.mMediaMeta.getString(s));
            }
        }
        return hashMap;
    }
    
    public static IjkMediaMeta parse(final Bundle mMediaMeta) {
        if (mMediaMeta == null) {
            return null;
        }
        final IjkMediaMeta ijkMediaMeta = new IjkMediaMeta();
        ijkMediaMeta.mMediaMeta = mMediaMeta;
        ijkMediaMeta.mFormat = ijkMediaMeta.getString("format");
        ijkMediaMeta.mDurationUS = ijkMediaMeta.getLong("duration_us");
        ijkMediaMeta.mStartUS = ijkMediaMeta.getLong("start_us");
        ijkMediaMeta.mBitrate = ijkMediaMeta.getLong("bitrate");
        final int int1 = ijkMediaMeta.getInt("video", -1);
        final int int2 = ijkMediaMeta.getInt("audio", -1);
        ijkMediaMeta.getInt("timedtext", -1);
        final ArrayList<Bundle> parcelableArrayList = ijkMediaMeta.getParcelableArrayList("streams");
        if (parcelableArrayList == null) {
            return ijkMediaMeta;
        }
        ijkMediaMeta.mBitrateItems = new ArrayList<IjkBitrateItem>();
        int n = -1;
        for (final Bundle mMeta : parcelableArrayList) {
            ++n;
            if (mMeta == null) {
                continue;
            }
            final IjkStreamMeta ijkStreamMeta = new IjkStreamMeta(n);
            ijkStreamMeta.mMeta = mMeta;
            ijkStreamMeta.mType = ijkStreamMeta.getString("type");
            ijkStreamMeta.mLanguage = ijkStreamMeta.getString("language");
            if (TextUtils.isEmpty((CharSequence)ijkStreamMeta.mType)) {
                continue;
            }
            ijkStreamMeta.mCodecName = ijkStreamMeta.getString("codec_name");
            ijkStreamMeta.mCodecProfile = ijkStreamMeta.getString("codec_profile");
            ijkStreamMeta.mCodecLongName = ijkStreamMeta.getString("codec_long_name");
            ijkStreamMeta.mBitrate = ijkStreamMeta.getInt("bitrate");
            if (ijkStreamMeta.mType.equalsIgnoreCase("video")) {
                ijkStreamMeta.mWidth = ijkStreamMeta.getInt("width");
                ijkStreamMeta.mHeight = ijkStreamMeta.getInt("height");
                ijkStreamMeta.mFpsNum = ijkStreamMeta.getInt("fps_num");
                ijkStreamMeta.mFpsDen = ijkStreamMeta.getInt("fps_den");
                ijkStreamMeta.mTbrNum = ijkStreamMeta.getInt("tbr_num");
                ijkStreamMeta.mTbrDen = ijkStreamMeta.getInt("tbr_den");
                ijkStreamMeta.mSarNum = ijkStreamMeta.getInt("sar_num");
                ijkStreamMeta.mSarDen = ijkStreamMeta.getInt("sar_den");
                if (int1 == n) {
                    ijkMediaMeta.mVideoStream = ijkStreamMeta;
                }
            }
            else if (ijkStreamMeta.mType.equalsIgnoreCase("audio")) {
                ijkStreamMeta.mSampleRate = ijkStreamMeta.getInt("sample_rate");
                ijkStreamMeta.mChannelLayout = ijkStreamMeta.getLong("channel_layout");
                if (int2 == n) {
                    ijkMediaMeta.mAudioStream = ijkStreamMeta;
                }
            }
            ijkMediaMeta.mStreams.add(ijkStreamMeta);
        }
        final ArrayList<Bundle> parcelableArrayList2 = ijkMediaMeta.getParcelableArrayList("programs");
        if (parcelableArrayList2 == null) {
            return ijkMediaMeta;
        }
        for (final Bundle bundle : parcelableArrayList2) {
            if (bundle == null) {
                continue;
            }
            final IjkBitrateItem ijkBitrateItem = new IjkBitrateItem();
            ijkBitrateItem.index = Integer.parseInt(bundle.getString("bitrate_index", "0"));
            ijkBitrateItem.width = Integer.parseInt(bundle.getString("width", "0"));
            ijkBitrateItem.height = Integer.parseInt(bundle.getString("height", "0"));
            ijkBitrateItem.bitrate = Integer.parseInt(bundle.getString("bitrate", "0"));
            ijkMediaMeta.mBitrateItems.add(ijkBitrateItem);
        }
        return ijkMediaMeta;
    }
    
    public static class IjkStreamMeta
    {
        public Bundle mMeta;
        public final int mIndex;
        public String mType;
        public String mLanguage;
        public String mCodecName;
        public String mCodecProfile;
        public String mCodecLongName;
        public long mBitrate;
        public int mWidth;
        public int mHeight;
        public int mFpsNum;
        public int mFpsDen;
        public int mTbrNum;
        public int mTbrDen;
        public int mSarNum;
        public int mSarDen;
        public int mSampleRate;
        public long mChannelLayout;
        
        public IjkStreamMeta(final int mIndex) {
            this.mIndex = mIndex;
        }
        
        public String getString(final String s) {
            return this.mMeta.getString(s);
        }
        
        public int getInt(final String s) {
            return this.getInt(s, 0);
        }
        
        public int getInt(final String s, final int n) {
            final String string = this.getString(s);
            if (TextUtils.isEmpty((CharSequence)string)) {
                return n;
            }
            try {
                return Integer.parseInt(string);
            }
            catch (NumberFormatException ex) {
                return n;
            }
        }
        
        public long getLong(final String s) {
            return this.getLong(s, 0L);
        }
        
        public long getLong(final String s, final long n) {
            final String string = this.getString(s);
            if (TextUtils.isEmpty((CharSequence)string)) {
                return n;
            }
            try {
                return Long.parseLong(string);
            }
            catch (NumberFormatException ex) {
                return n;
            }
        }
        
        public String getCodecLongNameInline() {
            if (!TextUtils.isEmpty((CharSequence)this.mCodecLongName)) {
                return this.mCodecLongName;
            }
            if (!TextUtils.isEmpty((CharSequence)this.mCodecName)) {
                return this.mCodecName;
            }
            return "N/A";
        }
        
        public String getCodecShortNameInline() {
            if (!TextUtils.isEmpty((CharSequence)this.mCodecName)) {
                return this.mCodecName;
            }
            return "N/A";
        }
        
        public String getResolutionInline() {
            if (this.mWidth <= 0 || this.mHeight <= 0) {
                return "N/A";
            }
            if (this.mSarNum <= 0 || this.mSarDen <= 0) {
                return String.format(Locale.US, "%d x %d", this.mWidth, this.mHeight);
            }
            return String.format(Locale.US, "%d x %d [SAR %d:%d]", this.mWidth, this.mHeight, this.mSarNum, this.mSarDen);
        }
        
        public String getFpsInline() {
            if (this.mFpsNum <= 0 || this.mFpsDen <= 0) {
                return "N/A";
            }
            return String.valueOf(this.mFpsNum / (float)this.mFpsDen);
        }
        
        public String getBitrateInline() {
            if (this.mBitrate <= 0L) {
                return "N/A";
            }
            if (this.mBitrate < 1000L) {
                return String.format(Locale.US, "%d bit/s", this.mBitrate);
            }
            return String.format(Locale.US, "%d kb/s", this.mBitrate / 1000L);
        }
        
        public String getSampleRateInline() {
            if (this.mSampleRate <= 0) {
                return "N/A";
            }
            return String.format(Locale.US, "%d Hz", this.mSampleRate);
        }
        
        public String getChannelLayoutInline() {
            if (this.mChannelLayout <= 0L) {
                return "N/A";
            }
            if (this.mChannelLayout == 4L) {
                return "mono";
            }
            if (this.mChannelLayout == 3L) {
                return "stereo";
            }
            return String.format(Locale.US, "%x", this.mChannelLayout);
        }
    }
}
