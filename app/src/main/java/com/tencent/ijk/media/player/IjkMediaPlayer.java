package com.tencent.ijk.media.player;

import android.view.*;
import java.lang.ref.*;
import com.tencent.ijk.media.player.pragma.*;
import android.content.*;
import android.net.*;
import android.util.*;
import android.content.res.*;
import android.text.*;
import java.io.*;
import java.lang.reflect.*;
import android.annotation.*;
import com.tencent.ijk.media.player.annotations.*;
import java.security.*;
import com.tencent.ijk.media.player.misc.*;
import android.os.*;
import android.graphics.*;
import java.util.*;
import android.media.*;

public final class IjkMediaPlayer extends AbstractMediaPlayer
{
    private static final String TAG;
    private static final int MEDIA_NOP = 0;
    private static final int MEDIA_PREPARED = 1;
    private static final int MEDIA_PLAYBACK_COMPLETE = 2;
    private static final int MEDIA_BUFFERING_UPDATE = 3;
    private static final int MEDIA_SEEK_COMPLETE = 4;
    private static final int MEDIA_SET_VIDEO_SIZE = 5;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final int MEDIA_ERROR = 100;
    private static final int MEDIA_INFO = 200;
    private static final int MEDIA_HLS_KEY_ERROR = 210;
    private static final int MEDIA_HEVC_VIDEO_DECODER_ERROR = 211;
    private static final int MEDIA_VIDEO_DECODER_ERROR = 212;
    protected static final int MEDIA_SET_VIDEO_SAR = 10001;
    public static final int IJK_LOG_UNKNOWN = 0;
    public static final int IJK_LOG_DEFAULT = 1;
    public static final int IJK_LOG_VERBOSE = 2;
    public static final int IJK_LOG_DEBUG = 3;
    public static final int IJK_LOG_INFO = 4;
    public static final int IJK_LOG_WARN = 5;
    public static final int IJK_LOG_ERROR = 6;
    public static final int IJK_LOG_FATAL = 7;
    public static final int IJK_LOG_SILENT = 8;
    public static final int OPT_CATEGORY_FORMAT = 1;
    public static final int OPT_CATEGORY_CODEC = 2;
    public static final int OPT_CATEGORY_SWS = 3;
    public static final int OPT_CATEGORY_PLAYER = 4;
    public static final int SDL_FCC_YV12 = 842094169;
    public static final int SDL_FCC_RV16 = 909203026;
    public static final int SDL_FCC_RV32 = 842225234;
    public static final int PROP_FLOAT_VIDEO_DECODE_FRAMES_PER_SECOND = 10001;
    public static final int PROP_FLOAT_VIDEO_OUTPUT_FRAMES_PER_SECOND = 10002;
    public static final int FFP_PROP_FLOAT_PLAYBACK_RATE = 10003;
    public static final int FFP_PROP_FLOAT_AVDELAY = 10004;
    public static final int FFP_PROP_FLOAT_AVDIFF = 10005;
    public static final int FFP_PROP_FLOAT_DROP_FRAME_RATE = 10007;
    public static final int FFP_PROP_INT64_SELECTED_VIDEO_STREAM = 20001;
    public static final int FFP_PROP_INT64_SELECTED_AUDIO_STREAM = 20002;
    public static final int FFP_PROP_INT64_SELECTED_TIMEDTEXT_STREAM = 20011;
    public static final int FFP_PROP_INT64_VIDEO_DECODER = 20003;
    public static final int FFP_PROP_INT64_AUDIO_DECODER = 20004;
    public static final int FFP_PROPV_DECODER_UNKNOWN = 0;
    public static final int FFP_PROPV_DECODER_AVCODEC = 1;
    public static final int FFP_PROPV_DECODER_MEDIACODEC = 2;
    public static final int FFP_PROPV_DECODER_VIDEOTOOLBOX = 3;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_DURATION = 20005;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_DURATION = 20006;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_BYTES = 20007;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_BYTES = 20008;
    public static final int FFP_PROP_INT64_VIDEO_CACHED_PACKETS = 20009;
    public static final int FFP_PROP_INT64_AUDIO_CACHED_PACKETS = 20010;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_BACKWARDS = 20201;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_FORWARDS = 20202;
    public static final int FFP_PROP_INT64_ASYNC_STATISTIC_BUF_CAPACITY = 20203;
    public static final int FFP_PROP_INT64_TRAFFIC_STATISTIC_BYTE_COUNT = 20204;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_PHYSICAL_POS = 20205;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_FILE_FORWARDS = 20206;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_FILE_POS = 20207;
    public static final int FFP_PROP_INT64_CACHE_STATISTIC_COUNT_BYTES = 20208;
    public static final int FFP_PROP_INT64_LOGICAL_FILE_SIZE = 20209;
    public static final int FFP_PROP_INT64_BIT_RATE = 20100;
    public static final int FFP_PROP_INT64_TCP_SPEED = 20200;
    public static final int FFP_PROP_INT64_LATEST_SEEK_LOAD_DURATION = 20300;
    @AccessedByNative
    private long mNativeMediaPlayer;
    @AccessedByNative
    private long mNativeMediaDataSource;
    @AccessedByNative
    private long mNativeAndroidIO;
    @AccessedByNative
    private int mNativeSurfaceTexture;
    @AccessedByNative
    private int mListenerContext;
    private SurfaceHolder mSurfaceHolder;
    private EventHandler mEventHandler;
    private PowerManager.WakeLock mWakeLock;
    private boolean mScreenOnWhilePlaying;
    private boolean mStayAwake;
    private int mVideoWidth;
    private int mVideoHeight;
    private int mVideoSarNum;
    private int mVideoSarDen;
    private String mDataSource;
    private Surface mSurface;
    private int mBitrateIndex;
    private static final IjkLibLoader sLocalLibLoader;
    private static volatile boolean mIsLibLoaded;
    private static volatile boolean mIsNativeInitialized;
    private OnControlMessageListener mOnControlMessageListener;
    private OnNativeInvokeListener mOnNativeInvokeListener;
    private OnMediaCodecSelectListener mOnMediaCodecSelectListener;
    
    public static void loadLibrariesOnce(IjkLibLoader sLocalLibLoader) {
        synchronized (IjkMediaPlayer.class) {
            if (!IjkMediaPlayer.mIsLibLoaded) {
                if (sLocalLibLoader == null) {
                    sLocalLibLoader = IjkMediaPlayer.sLocalLibLoader;
                }
                sLocalLibLoader.loadLibrary("txffmpeg");
                sLocalLibLoader.loadLibrary("txsdl");
                sLocalLibLoader.loadLibrary("txplayer");
                IjkMediaPlayer.mIsLibLoaded = true;
            }
        }
    }
    
    private static void initNativeOnce() {
        synchronized (IjkMediaPlayer.class) {
            if (!IjkMediaPlayer.mIsNativeInitialized) {
                native_init();
                IjkMediaPlayer.mIsNativeInitialized = true;
            }
        }
    }
    
    public IjkMediaPlayer() {
        this(IjkMediaPlayer.sLocalLibLoader);
    }
    
    public IjkMediaPlayer(final IjkLibLoader ijkLibLoader) {
        this.mWakeLock = null;
        this.initPlayer(ijkLibLoader);
    }
    
    private void initPlayer(final IjkLibLoader ijkLibLoader) {
        loadLibrariesOnce(ijkLibLoader);
        initNativeOnce();
        final Looper myLooper;
        if ((myLooper = Looper.myLooper()) != null) {
            this.mEventHandler = new EventHandler(this, myLooper);
        }
        else {
            final Looper mainLooper;
            if ((mainLooper = Looper.getMainLooper()) != null) {
                this.mEventHandler = new EventHandler(this, mainLooper);
            }
            else {
                this.mEventHandler = null;
            }
        }
        this.native_setup(new WeakReference(this));
    }
    
    private native void _setFrameAtTime(final String p0, final long p1, final long p2, final int p3, final int p4) throws IllegalArgumentException, IllegalStateException;
    
    private native void _setVideoSurface(final Surface p0);
    
    @Override
    public void setDisplay(final SurfaceHolder mSurfaceHolder) {
        this.mSurfaceHolder = mSurfaceHolder;
        Surface surface;
        if (mSurfaceHolder != null) {
            surface = mSurfaceHolder.getSurface();
        }
        else {
            surface = null;
        }
        this._setVideoSurface(surface);
        this.updateSurfaceScreenOn();
    }
    
    @Override
    public void setSurface(final Surface mSurface) {
        if (this.mScreenOnWhilePlaying && mSurface != null) {
            DebugLog.w(IjkMediaPlayer.TAG, "setScreenOnWhilePlaying(true) is ineffective for Surface");
        }
        this.mSurfaceHolder = null;
        this._setVideoSurface(mSurface);
        this.updateSurfaceScreenOn();
        this.mSurface = mSurface;
    }
    
    @Override
    public Surface getSurface() {
        return this.mSurface;
    }
    
    @Override
    public void setDataSource(final Context context, final Uri uri) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this.setDataSource(context, uri, null);
    }
    
    @TargetApi(14)
    @Override
    public void setDataSource(final Context context, Uri actualDefaultRingtoneUri, final Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        final String scheme = actualDefaultRingtoneUri.getScheme();
        if ("file".equals(scheme)) {
            this.setDataSource(actualDefaultRingtoneUri.getPath());
            return;
        }
        if ("content".equals(scheme) && "settings".equals(actualDefaultRingtoneUri.getAuthority())) {
            actualDefaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.getDefaultType(actualDefaultRingtoneUri));
            if (actualDefaultRingtoneUri == null) {
                throw new FileNotFoundException("Failed to resolve default ringtone");
            }
        }
        AssetFileDescriptor openAssetFileDescriptor = null;
        try {
            openAssetFileDescriptor = context.getContentResolver().openAssetFileDescriptor(actualDefaultRingtoneUri, "r");
            if (openAssetFileDescriptor == null) {
                return;
            }
            if (openAssetFileDescriptor.getDeclaredLength() < 0L) {
                this.setDataSource(openAssetFileDescriptor.getFileDescriptor());
            }
            else {
                this.setDataSource(openAssetFileDescriptor.getFileDescriptor(), openAssetFileDescriptor.getStartOffset(), openAssetFileDescriptor.getDeclaredLength());
            }
            return;
        }
        catch (SecurityException ex) {}
        catch (IOException ex2) {}
        finally {
            if (openAssetFileDescriptor != null) {
                openAssetFileDescriptor.close();
            }
        }
        Log.d(IjkMediaPlayer.TAG, "Couldn't open file on client side, trying server side");
        this.setDataSource(actualDefaultRingtoneUri.toString(), map);
    }
    
    @Override
    public void setDataSource(final String mDataSource) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        this._setDataSource(this.mDataSource = mDataSource, null, null);
    }
    
    public void setDataSource(final String dataSource, final Map<String, String> map) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException {
        if (map != null && !map.isEmpty()) {
            final StringBuilder sb = new StringBuilder();
            for (final Map.Entry<String, String> entry : map.entrySet()) {
                sb.append(entry.getKey());
                sb.append(":");
                if (!TextUtils.isEmpty((CharSequence)entry.getValue())) {
                    sb.append(entry.getValue());
                }
                sb.append("\r\n");
                this.setOption(1, "headers", sb.toString());
                this.setOption(1, "protocol_whitelist", "async,cache,crypto,file,http,https,ijkhttphook,ijkinject,ijklivehook,ijklongurl,ijksegment,ijktcphook,pipe,rtp,tcp,tls,udp,ijkurlhook,data,ijkhttpcache,ijklongurl");
            }
        }
        this.setDataSource(dataSource);
    }
    
    @TargetApi(13)
    @Override
    public void setDataSource(final FileDescriptor fileDescriptor) throws IOException, IllegalArgumentException, IllegalStateException {
        if (Build.VERSION.SDK_INT < 12) {
            int int1;
            try {
                final Field declaredField = fileDescriptor.getClass().getDeclaredField("descriptor");
                declaredField.setAccessible(true);
                int1 = declaredField.getInt(fileDescriptor);
            }
            catch (NoSuchFieldException ex) {
                throw new RuntimeException(ex);
            }
            catch (IllegalAccessException ex2) {
                throw new RuntimeException(ex2);
            }
            this._setDataSourceFd(int1);
        }
        else {
            final ParcelFileDescriptor dup = ParcelFileDescriptor.dup(fileDescriptor);
            try {
                this._setDataSourceFd(dup.getFd());
            }
            finally {
                dup.close();
            }
        }
    }
    
    private void setDataSource(final FileDescriptor dataSource, final long n, final long n2) throws IOException, IllegalArgumentException, IllegalStateException {
        this.setDataSource(dataSource);
    }
    
    @Override
    public void setDataSource(final IMediaDataSource mediaDataSource) throws IllegalArgumentException, SecurityException, IllegalStateException {
        this._setDataSource(mediaDataSource);
    }
    
    public void setAndroidIOCallback(final IAndroidIO androidIO) throws IllegalArgumentException, SecurityException, IllegalStateException {
        this._setAndroidIOCallback(androidIO);
    }
    
    private native void _setDataSource(final String p0, final String[] p1, final String[] p2) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
    
    private native void _setDataSourceFd(final int p0) throws IOException, IllegalArgumentException, SecurityException, IllegalStateException;
    
    private native void _setDataSource(final IMediaDataSource p0) throws IllegalArgumentException, SecurityException, IllegalStateException;
    
    private native void _setAndroidIOCallback(final IAndroidIO p0) throws IllegalArgumentException, SecurityException, IllegalStateException;
    
    private native void _injectCacheNode(final int p0, final long p1, final long p2, final long p3, final long p4);
    
    public void injectCacheNode(final int n, final long n2, final long n3, final long n4, final long n5) {
        this._injectCacheNode(n, n2, n3, n4, n5);
    }
    
    @Override
    public String getDataSource() {
        return this.mDataSource;
    }
    
    @Override
    public void prepareAsync() throws IllegalStateException {
        this._prepareAsync();
    }
    
    public native void _prepareAsync() throws IllegalStateException;
    
    @Override
    public void start() throws IllegalStateException {
        this.stayAwake(true);
        this._start();
    }
    
    private native void _start() throws IllegalStateException;
    
    @Override
    public void stop() throws IllegalStateException {
        this.stayAwake(false);
        this._stop();
    }
    
    private native void _stop() throws IllegalStateException;
    
    @Override
    public void pause() throws IllegalStateException {
        this.stayAwake(false);
        this._pause();
    }
    
    private native void _pause() throws IllegalStateException;
    
    @SuppressLint({ "Wakelock" })
    @Override
    public void setWakeMode(final Context context, final int n) {
        boolean b = false;
        if (this.mWakeLock != null) {
            if (this.mWakeLock.isHeld()) {
                b = true;
                this.mWakeLock.release();
            }
            this.mWakeLock = null;
        }
        (this.mWakeLock = ((PowerManager)context.getSystemService("power")).newWakeLock(n | 0x20000000, IjkMediaPlayer.class.getName())).setReferenceCounted(false);
        if (b) {
            this.mWakeLock.acquire();
        }
    }
    
    @Override
    public void setScreenOnWhilePlaying(final boolean mScreenOnWhilePlaying) {
        if (this.mScreenOnWhilePlaying != mScreenOnWhilePlaying) {
            if (mScreenOnWhilePlaying && this.mSurfaceHolder == null) {
                DebugLog.w(IjkMediaPlayer.TAG, "setScreenOnWhilePlaying(true) is ineffective without a SurfaceHolder");
            }
            this.mScreenOnWhilePlaying = mScreenOnWhilePlaying;
            this.updateSurfaceScreenOn();
        }
    }
    
    @SuppressLint({ "Wakelock" })
    private void stayAwake(final boolean mStayAwake) {
        if (this.mWakeLock != null) {
            if (mStayAwake && !this.mWakeLock.isHeld()) {
                this.mWakeLock.acquire();
            }
            else if (!mStayAwake && this.mWakeLock.isHeld()) {
                this.mWakeLock.release();
            }
        }
        this.mStayAwake = mStayAwake;
        this.updateSurfaceScreenOn();
    }
    
    private void updateSurfaceScreenOn() {
        if (this.mSurfaceHolder != null) {
            this.mSurfaceHolder.setKeepScreenOn(this.mScreenOnWhilePlaying && this.mStayAwake);
        }
    }
    
    @Override
    public IjkTrackInfo[] getTrackInfo() {
        final Bundle mediaMeta = this.getMediaMeta();
        if (mediaMeta == null) {
            return null;
        }
        final IjkMediaMeta parse = IjkMediaMeta.parse(mediaMeta);
        if (parse == null || parse.mStreams == null) {
            return null;
        }
        final ArrayList<IjkTrackInfo> list = new ArrayList<IjkTrackInfo>();
        for (final IjkMediaMeta.IjkStreamMeta ijkStreamMeta : parse.mStreams) {
            final IjkTrackInfo ijkTrackInfo = new IjkTrackInfo(ijkStreamMeta);
            if (ijkStreamMeta.mType.equalsIgnoreCase("video")) {
                ijkTrackInfo.setTrackType(1);
            }
            else if (ijkStreamMeta.mType.equalsIgnoreCase("audio")) {
                ijkTrackInfo.setTrackType(2);
            }
            else if (ijkStreamMeta.mType.equalsIgnoreCase("timedtext")) {
                ijkTrackInfo.setTrackType(3);
            }
            list.add(ijkTrackInfo);
        }
        return list.toArray(new IjkTrackInfo[list.size()]);
    }
    
    public int getSelectedTrack(final int n) {
        switch (n) {
            case 1: {
                return (int)this._getPropertyLong(20001, -1L);
            }
            case 2: {
                return (int)this._getPropertyLong(20002, -1L);
            }
            case 3: {
                return (int)this._getPropertyLong(20011, -1L);
            }
            default: {
                return -1;
            }
        }
    }
    
    public void selectTrack(final int n) {
        this._setStreamSelected(n, true);
    }
    
    public void deselectTrack(final int n) {
        this._setStreamSelected(n, false);
    }
    
    private native void _setStreamSelected(final int p0, final boolean p1);
    
    @Override
    public int getVideoWidth() {
        return this.mVideoWidth;
    }
    
    @Override
    public int getVideoHeight() {
        return this.mVideoHeight;
    }
    
    @Override
    public int getVideoSarNum() {
        return this.mVideoSarNum;
    }
    
    @Override
    public int getVideoSarDen() {
        return this.mVideoSarDen;
    }
    
    @Override
    public native boolean isPlaying();
    
    @Override
    public native void seekTo(final long p0) throws IllegalStateException;
    
    @Override
    public native long getCurrentPosition();
    
    @Override
    public native long getDuration();
    
    @Override
    public void release() {
        this.stayAwake(false);
        this.updateSurfaceScreenOn();
        this.resetListeners();
        new Thread(new Runnable() {
            @Override
            public void run() {
                IjkMediaPlayer.this._release();
            }
        }).start();
    }
    
    private native void _release();
    
    @Override
    public void reset() {
        this.stayAwake(false);
        this._reset();
        this.mEventHandler.removeCallbacksAndMessages((Object)null);
        this.mVideoWidth = 0;
        this.mVideoHeight = 0;
    }
    
    private native void _reset();
    
    @Override
    public void setLooping(final boolean b) {
        final int n = b ? 0 : 1;
        this.setOption(4, "loop", n);
        this._setLoopCount(n);
    }
    
    private native void _setLoopCount(final int p0);
    
    @Override
    public boolean isLooping() {
        return this._getLoopCount() != 1;
    }
    
    private native int _getLoopCount();
    
    public void setSpeed(final float n) {
        this._setPropertyFloat(10003, n);
    }
    
    public float getSpeed() {
        return this._getPropertyFloat(10003, 0.0f);
    }
    
    public int getVideoDecoder() {
        return (int)this._getPropertyLong(20003, 0L);
    }
    
    public float getVideoOutputFramesPerSecond() {
        return this._getPropertyFloat(10002, 0.0f);
    }
    
    public float getVideoDecodeFramesPerSecond() {
        return this._getPropertyFloat(10001, 0.0f);
    }
    
    public long getVideoCachedDuration() {
        return this._getPropertyLong(20005, 0L);
    }
    
    public long getAudioCachedDuration() {
        return this._getPropertyLong(20006, 0L);
    }
    
    public long getVideoCachedBytes() {
        return this._getPropertyLong(20007, 0L);
    }
    
    public long getAudioCachedBytes() {
        return this._getPropertyLong(20008, 0L);
    }
    
    public long getVideoCachedPackets() {
        return this._getPropertyLong(20009, 0L);
    }
    
    public long getAudioCachedPackets() {
        return this._getPropertyLong(20010, 0L);
    }
    
    public long getAsyncStatisticBufBackwards() {
        return this._getPropertyLong(20201, 0L);
    }
    
    public long getAsyncStatisticBufForwards() {
        return this._getPropertyLong(20202, 0L);
    }
    
    public long getAsyncStatisticBufCapacity() {
        return this._getPropertyLong(20203, 0L);
    }
    
    public long getTrafficStatisticByteCount() {
        return this._getPropertyLong(20204, 0L);
    }
    
    public long getCacheStatisticPhysicalPos() {
        return this._getPropertyLong(20205, 0L);
    }
    
    public long getCacheStatisticFileForwards() {
        return this._getPropertyLong(20206, 0L);
    }
    
    public long getCacheStatisticFilePos() {
        return this._getPropertyLong(20207, 0L);
    }
    
    public long getCacheStatisticCountBytes() {
        return this._getPropertyLong(20208, 0L);
    }
    
    public long getFileSize() {
        return this._getPropertyLong(20209, 0L);
    }
    
    public long getBitRate() {
        return this._getPropertyLong(20100, 0L);
    }
    
    public float getAVDelay() {
        return this._getPropertyFloat(10004, 0.0f);
    }
    
    public float getAVDiff() {
        return this._getPropertyFloat(10005, 0.0f);
    }
    
    public long getTcpSpeed() {
        return this._getPropertyLong(20200, 0L);
    }
    
    public long getSeekLoadDuration() {
        return this._getPropertyLong(20300, 0L);
    }
    
    private native float _getPropertyFloat(final int p0, final float p1);
    
    private native void _setPropertyFloat(final int p0, final float p1);
    
    private native long _getPropertyLong(final int p0, final long p1);
    
    private native void _setPropertyLong(final int p0, final long p1);
    
    public float getDropFrameRate() {
        return this._getPropertyFloat(10007, 0.0f);
    }
    
    @Override
    public void setRate(final float speed) {
        this.setSpeed(speed);
    }
    
    @Override
    public float getRate() {
        return this.getSpeed();
    }
    
    @Override
    public native void setVolume(final float p0, final float p1);
    
    @Override
    public native int getAudioSessionId();
    
    @Override
    public MediaInfo getMediaInfo() {
        final MediaInfo mediaInfo = new MediaInfo();
        mediaInfo.mMediaPlayerName = "ijkplayer";
        final String getVideoCodecInfo = this._getVideoCodecInfo();
        if (!TextUtils.isEmpty((CharSequence)getVideoCodecInfo)) {
            final String[] split = getVideoCodecInfo.split(",");
            if (split.length >= 2) {
                mediaInfo.mVideoDecoder = split[0];
                mediaInfo.mVideoDecoderImpl = split[1];
            }
            else if (split.length >= 1) {
                mediaInfo.mVideoDecoder = split[0];
                mediaInfo.mVideoDecoderImpl = "";
            }
        }
        final String getAudioCodecInfo = this._getAudioCodecInfo();
        if (!TextUtils.isEmpty((CharSequence)getAudioCodecInfo)) {
            final String[] split2 = getAudioCodecInfo.split(",");
            if (split2.length >= 2) {
                mediaInfo.mAudioDecoder = split2[0];
                mediaInfo.mAudioDecoderImpl = split2[1];
            }
            else if (split2.length >= 1) {
                mediaInfo.mAudioDecoder = split2[0];
                mediaInfo.mAudioDecoderImpl = "";
            }
        }
        try {
            mediaInfo.mMeta = IjkMediaMeta.parse(this._getMediaMeta());
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
        return mediaInfo;
    }
    
    @Override
    public void setLogEnabled(final boolean b) {
    }
    
    @Override
    public boolean isPlayable() {
        return true;
    }
    
    private native String _getVideoCodecInfo();
    
    private native String _getAudioCodecInfo();
    
    public void setOption(final int n, final String s, final String s2) {
        this._setOption(n, s, s2);
    }
    
    public void setOption(final int n, final String s, final long n2) {
        this._setOption(n, s, n2);
    }
    
    private native void _setOption(final int p0, final String p1, final String p2);
    
    private native void _setOption(final int p0, final String p1, final long p2);
    
    public Bundle getMediaMeta() {
        return this._getMediaMeta();
    }
    
    private native Bundle _getMediaMeta();
    
    public static String getColorFormatName(final int n) {
        return _getColorFormatName(n);
    }
    
    private static native String _getColorFormatName(final int p0);
    
    @Override
    public void setAudioStreamType(final int n) {
    }
    
    @Override
    public void setKeepInBackground(final boolean b) {
    }
    
    private static native void native_init();
    
    private native void native_setup(final Object p0);
    
    private native void native_finalize();
    
    private native void native_message_loop(final Object p0);
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.native_finalize();
    }
    
    @CalledByNative
    private static void postEventFromNative(final Object o, final int n, final int n2, final int n3, final Object o2) {
        if (o == null) {
            return;
        }
        final IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer)((WeakReference)o).get();
        if (ijkMediaPlayer == null) {
            return;
        }
        if (n == 200 && n2 == 2) {
            ijkMediaPlayer.start();
        }
        if (ijkMediaPlayer.mEventHandler != null) {
            ijkMediaPlayer.mEventHandler.sendMessage(ijkMediaPlayer.mEventHandler.obtainMessage(n, n2, n3, o2));
        }
    }
    
    public void setOnControlMessageListener(final OnControlMessageListener mOnControlMessageListener) {
        this.mOnControlMessageListener = mOnControlMessageListener;
    }
    
    public void setOnNativeInvokeListener(final OnNativeInvokeListener mOnNativeInvokeListener) {
        this.mOnNativeInvokeListener = mOnNativeInvokeListener;
    }
    
    @CalledByNative
    private static boolean onNativeInvoke(final Object o, final int n, final Bundle bundle) {
        if (o == null || !(o instanceof WeakReference)) {
            throw new IllegalStateException("<null weakThiz>.onNativeInvoke()");
        }
        final IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer)((WeakReference)o).get();
        if (ijkMediaPlayer == null) {
            throw new IllegalStateException("<null weakPlayer>.onNativeInvoke()");
        }
        final OnNativeInvokeListener mOnNativeInvokeListener = ijkMediaPlayer.mOnNativeInvokeListener;
        if (mOnNativeInvokeListener != null && mOnNativeInvokeListener.onNativeInvoke(n, bundle)) {
            return true;
        }
        switch (n) {
            case 131079: {
                final OnControlMessageListener mOnControlMessageListener = ijkMediaPlayer.mOnControlMessageListener;
                if (mOnControlMessageListener == null) {
                    return false;
                }
                final int int1 = bundle.getInt("segment_index", -1);
                if (int1 < 0) {
                    throw new InvalidParameterException("onNativeInvoke(invalid segment index)");
                }
                final String onControlResolveSegmentUrl = mOnControlMessageListener.onControlResolveSegmentUrl(int1);
                if (onControlResolveSegmentUrl == null) {
                    throw new RuntimeException(new IOException("onNativeInvoke() = <NULL newUrl>"));
                }
                bundle.putString("url", onControlResolveSegmentUrl);
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public void setOnMediaCodecSelectListener(final OnMediaCodecSelectListener mOnMediaCodecSelectListener) {
        this.mOnMediaCodecSelectListener = mOnMediaCodecSelectListener;
    }
    
    @Override
    public void resetListeners() {
        super.resetListeners();
        this.mOnMediaCodecSelectListener = null;
    }
    
    @CalledByNative
    private static String onSelectCodec(final Object o, final String s, final int n, final int n2) {
        if (o == null || !(o instanceof WeakReference)) {
            return null;
        }
        final IjkMediaPlayer ijkMediaPlayer = (IjkMediaPlayer)((WeakReference)o).get();
        if (ijkMediaPlayer == null) {
            return null;
        }
        OnMediaCodecSelectListener onMediaCodecSelectListener = ijkMediaPlayer.mOnMediaCodecSelectListener;
        if (onMediaCodecSelectListener == null) {
            onMediaCodecSelectListener = DefaultMediaCodecSelector.sInstance;
        }
        return onMediaCodecSelectListener.onMediaCodecSelect(ijkMediaPlayer, s, n, n2);
    }
    
    public static native void native_profileBegin(final String p0);
    
    public static native void native_profileEnd();
    
    public static native void native_setLogLevel(final int p0);
    
    @Override
    public int getBitrateIndex() {
        return this.mBitrateIndex;
    }
    
    @Override
    public void setBitrateIndex(final int mBitrateIndex) {
        this._set_bitrate_index(this.mBitrateIndex = mBitrateIndex);
    }
    
    @Override
    public ArrayList<IjkBitrateItem> getSupportedBitrates() {
        return this.getMediaInfo().mMeta.mBitrateItems;
    }
    
    public native void _set_bitrate_index(final int p0);
    
    static {
        TAG = IjkMediaPlayer.class.getName();
        sLocalLibLoader = new IjkLibLoader() {
            @Override
            public void loadLibrary(final String s) throws UnsatisfiedLinkError, SecurityException {
                System.loadLibrary(s);
            }
        };
        IjkMediaPlayer.mIsLibLoaded = false;
        IjkMediaPlayer.mIsNativeInitialized = false;
    }
    
    private static class EventHandler extends Handler
    {
        private final WeakReference<IjkMediaPlayer> mWeakPlayer;
        
        public EventHandler(final IjkMediaPlayer ijkMediaPlayer, final Looper looper) {
            super(looper);
            this.mWeakPlayer = new WeakReference<IjkMediaPlayer>(ijkMediaPlayer);
        }
        
        public void handleMessage(final Message message) {
            final IjkMediaPlayer ijkMediaPlayer = this.mWeakPlayer.get();
            if (ijkMediaPlayer == null || ijkMediaPlayer.mNativeMediaPlayer == 0L) {
                DebugLog.w(IjkMediaPlayer.TAG, "IjkMediaPlayer went away with unhandled events");
                return;
            }
            switch (message.what) {
                case 1: {
                    ijkMediaPlayer.notifyOnPrepared();
                }
                case 2: {
                    ijkMediaPlayer.stayAwake(false);
                    ijkMediaPlayer.notifyOnCompletion();
                }
                case 3: {
                    long n = message.arg1;
                    if (n < 0L) {
                        n = 0L;
                    }
                    long n2 = 0L;
                    final long duration = ijkMediaPlayer.getDuration();
                    if (duration > 0L) {
                        n2 = n * 100L / duration;
                    }
                    if (n2 >= 100L) {
                        n2 = 100L;
                    }
                    ijkMediaPlayer.notifyOnBufferingUpdate((int)n2);
                }
                case 4: {
                    ijkMediaPlayer.notifyOnSeekComplete();
                }
                case 5: {
                    ijkMediaPlayer.mVideoWidth = message.arg1;
                    ijkMediaPlayer.mVideoHeight = message.arg2;
                    ijkMediaPlayer.notifyOnVideoSizeChanged(ijkMediaPlayer.mVideoWidth, ijkMediaPlayer.mVideoHeight, ijkMediaPlayer.mVideoSarNum, ijkMediaPlayer.mVideoSarDen);
                }
                case 100: {
                    DebugLog.e(IjkMediaPlayer.TAG, "Error (" + message.arg1 + "," + message.arg2 + ")");
                    if (!ijkMediaPlayer.notifyOnError(message.arg1, message.arg2)) {
                        ijkMediaPlayer.notifyOnCompletion();
                    }
                    ijkMediaPlayer.stayAwake(false);
                }
                case 200: {
                    switch (message.arg1) {
                        case 3: {
                            DebugLog.i(IjkMediaPlayer.TAG, "Info: MEDIA_INFO_VIDEO_RENDERING_START\n");
                            break;
                        }
                    }
                    ijkMediaPlayer.notifyOnInfo(message.arg1, message.arg2);
                }
                case 99: {
                    if (message.obj == null) {
                        ijkMediaPlayer.notifyOnTimedText(null);
                    }
                    else {
                        ijkMediaPlayer.notifyOnTimedText(new IjkTimedText(new Rect(0, 0, 1, 1), (String)message.obj));
                    }
                }
                case 0: {
                    break;
                }
                case 10001: {
                    ijkMediaPlayer.mVideoSarNum = message.arg1;
                    ijkMediaPlayer.mVideoSarDen = message.arg2;
                    ijkMediaPlayer.notifyOnVideoSizeChanged(ijkMediaPlayer.mVideoWidth, ijkMediaPlayer.mVideoHeight, ijkMediaPlayer.mVideoSarNum, ijkMediaPlayer.mVideoSarDen);
                    break;
                }
                case 210: {
                    ijkMediaPlayer.notifyHLSKeyError();
                    break;
                }
                case 211: {
                    ijkMediaPlayer.notifyHevcVideoDecoderError();
                    break;
                }
                case 212: {
                    ijkMediaPlayer.notifyVideoDecoderError();
                    break;
                }
                default: {
                    DebugLog.e(IjkMediaPlayer.TAG, "Unknown message type " + message.what);
                    break;
                }
            }
        }
    }
    
    public static class DefaultMediaCodecSelector implements OnMediaCodecSelectListener
    {
        public static final DefaultMediaCodecSelector sInstance;
        
        @TargetApi(16)
        @Override
        public String onMediaCodecSelect(final IMediaPlayer mediaPlayer, final String s, final int n, final int n2) {
            if (Build.VERSION.SDK_INT < 16) {
                return null;
            }
            if (TextUtils.isEmpty((CharSequence)s)) {
                return null;
            }
            Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "onSelectCodec: mime=%s, profile=%d, level=%d", s, n, n2));
            final ArrayList<IjkMediaCodecInfo> list = new ArrayList<IjkMediaCodecInfo>();
            for (int codecCount = MediaCodecList.getCodecCount(), i = 0; i < codecCount; ++i) {
                final MediaCodecInfo codecInfo = MediaCodecList.getCodecInfoAt(i);
                Log.d(IjkMediaPlayer.TAG, String.format(Locale.US, "  found codec: %s", codecInfo.getName()));
                if (!codecInfo.isEncoder()) {
                    final String[] supportedTypes = codecInfo.getSupportedTypes();
                    if (supportedTypes != null) {
                        for (final String s2 : supportedTypes) {
                            if (!TextUtils.isEmpty((CharSequence)s2)) {
                                Log.d(IjkMediaPlayer.TAG, String.format(Locale.US, "    mime: %s", s2));
                                if (s2.equalsIgnoreCase(s)) {
                                    final IjkMediaCodecInfo setupCandidate = IjkMediaCodecInfo.setupCandidate(codecInfo, s);
                                    if (setupCandidate != null) {
                                        list.add(setupCandidate);
                                        Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "candidate codec: %s rank=%d", codecInfo.getName(), setupCandidate.mRank));
                                        setupCandidate.dumpProfileLevels(s);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (list.isEmpty()) {
                return null;
            }
            IjkMediaCodecInfo ijkMediaCodecInfo = list.get(0);
            for (final IjkMediaCodecInfo ijkMediaCodecInfo2 : list) {
                if (ijkMediaCodecInfo2.mRank > ijkMediaCodecInfo.mRank) {
                    ijkMediaCodecInfo = ijkMediaCodecInfo2;
                }
            }
            if (ijkMediaCodecInfo.mRank < 600) {
                Log.w(IjkMediaPlayer.TAG, String.format(Locale.US, "unaccetable codec: %s", ijkMediaCodecInfo.mCodecInfo.getName()));
                return null;
            }
            Log.i(IjkMediaPlayer.TAG, String.format(Locale.US, "selected codec: %s rank=%d", ijkMediaCodecInfo.mCodecInfo.getName(), ijkMediaCodecInfo.mRank));
            return ijkMediaCodecInfo.mCodecInfo.getName();
        }
        
        static {
            sInstance = new DefaultMediaCodecSelector();
        }
    }
    
    public interface OnMediaCodecSelectListener
    {
        String onMediaCodecSelect(final IMediaPlayer p0, final String p1, final int p2, final int p3);
    }
    
    public interface OnNativeInvokeListener
    {
        public static final int CTRL_WILL_TCP_OPEN = 131073;
        public static final int CTRL_DID_TCP_OPEN = 131074;
        public static final int CTRL_WILL_HTTP_OPEN = 131075;
        public static final int CTRL_WILL_LIVE_OPEN = 131077;
        public static final int CTRL_WILL_CONCAT_RESOLVE_SEGMENT = 131079;
        public static final int EVENT_WILL_HTTP_OPEN = 1;
        public static final int EVENT_DID_HTTP_OPEN = 2;
        public static final int EVENT_WILL_HTTP_SEEK = 3;
        public static final int EVENT_DID_HTTP_SEEK = 4;
        public static final int CTRL_WILL_DNS_RESOLVE = 131105;
        public static final int CTRL_DID_DNS_RESOLVE = 131106;
        public static final String ARG_URL = "url";
        public static final String ARG_SEGMENT_INDEX = "segment_index";
        public static final String ARG_RETRY_COUNTER = "retry_counter";
        public static final String ARG_ERROR = "error";
        public static final String ARG_FAMILIY = "family";
        public static final String ARG_IP = "ip";
        public static final String ARG_PORT = "port";
        public static final String ARG_FD = "fd";
        public static final String ARG_OFFSET = "offset";
        public static final String ARG_HTTP_CODE = "http_code";
        
        boolean onNativeInvoke(final int p0, final Bundle p1);
    }
    
    public interface OnControlMessageListener
    {
        String onControlResolveSegmentUrl(final int p0);
    }
}
