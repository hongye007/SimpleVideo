package com.tencent.rtmp;

import com.tencent.liteav.basic.b.*;
import com.tencent.rtmp.ui.*;
import com.tencent.liteav.txcvodplayer.*;
import android.content.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.*;
import android.text.*;
import com.tencent.liteav.basic.datareport.*;
import android.net.*;
import java.util.*;
import android.graphics.*;
import android.view.*;
import android.os.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.network.*;

public class TXVodPlayer implements b, g
{
    public static final String TAG = "TXVodPlayer";
    public static final int PLAYER_TYPE_FFPLAY = 0;
    public static final int PLAYER_TYPE_EXO = 1;
    private TXCloudVideoView mTXCloudVideoView;
    private Surface mSurface;
    private TextureRenderView mTextureView;
    private ITXLivePlayListener mListener;
    private ITXVodPlayListener mNewListener;
    private TXVodPlayConfig mConfig;
    private boolean mEnableHWDec;
    private int mRenderMode;
    private int mRenderRotation;
    private String mPlayUrl;
    private boolean mMute;
    private Context mContext;
    private o mPlayer;
    private boolean mIsGainAudioFocus;
    private boolean mAutoPlay;
    private float mRate;
    private boolean mSnapshotRunning;
    private f mNetApi;
    private String mToken;
    private int mBitrateIndex;
    private boolean mMirror;
    private boolean mIsGetPlayInfo;
    private boolean mLoop;
    protected float mStartTime;
    
    public TXVodPlayer(final Context context) {
        this.mEnableHWDec = false;
        this.mPlayUrl = "";
        this.mMute = false;
        this.mIsGainAudioFocus = true;
        this.mAutoPlay = true;
        this.mRate = 1.0f;
        this.mSnapshotRunning = false;
        this.mListener = null;
        this.mNewListener = null;
        TXCCommonUtil.setAppContext(this.mContext = context.getApplicationContext());
        TXCLog.init();
    }
    
    public void setConfig(final TXVodPlayConfig mConfig) {
        this.mConfig = mConfig;
        if (this.mConfig == null) {
            this.mConfig = new TXVodPlayConfig();
        }
        if (this.mPlayer != null) {
            j q = this.mPlayer.q();
            if (q == null) {
                q = new j();
            }
            q.e = this.mConfig.mConnectRetryCount;
            q.f = this.mConfig.mConnectRetryInterval;
            q.r = this.mConfig.mTimeout;
            q.h = this.mEnableHWDec;
            q.n = this.mConfig.mCacheFolderPath;
            q.o = this.mConfig.mMaxCacheItems;
            q.p = this.mConfig.mPlayerType;
            q.q = this.mConfig.mHeaders;
            q.s = this.mConfig.enableAccurateSeek;
            q.t = this.mConfig.autoRotate;
            q.u = this.mConfig.smoothSwitchBitrate;
            q.v = this.mConfig.cacheMp4ExtName;
            q.w = this.mConfig.progressInterval;
            q.x = this.mConfig.maxBufferSize;
            this.mPlayer.a(q);
        }
    }
    
    public void setPlayerView(final TXCloudVideoView mtxCloudVideoView) {
        this.mTXCloudVideoView = mtxCloudVideoView;
        if (this.mPlayer != null) {
            this.mPlayer.a(mtxCloudVideoView);
        }
    }
    
    public void setPlayerView(final TextureRenderView mTextureView) {
        this.mTextureView = mTextureView;
        if (this.mPlayer != null) {
            this.mPlayer.a(mTextureView);
        }
    }
    
    public void setSurface(final Surface mSurface) {
        this.mSurface = mSurface;
        if (this.mPlayer != null) {
            this.mPlayer.a(this.mSurface);
        }
    }
    
    public int startPlay(String string) {
        if (string == null || TextUtils.isEmpty((CharSequence)string)) {
            return -1;
        }
        TXCDRApi.initCrashReport(this.mContext);
        final int mBitrateIndex = this.mBitrateIndex;
        this.stopPlay(false);
        this.mBitrateIndex = mBitrateIndex;
        if (this.mToken != null) {
            final String path = Uri.parse(string).getPath();
            if (path != null) {
                final String[] split = path.split("/");
                if (split.length > 0) {
                    final int lastIndex = string.lastIndexOf(split[split.length - 1]);
                    string = string.substring(0, lastIndex) + "voddrm.token." + this.mToken + "." + string.substring(lastIndex);
                }
            }
        }
        this.mPlayUrl = this.checkPlayUrl(string);
        TXCLog.i("TXVodPlayer", "===========================================================================================================================================================");
        TXCLog.i("TXVodPlayer", "===========================================================================================================================================================");
        TXCLog.i("TXVodPlayer", "=====  StartPlay url = " + this.mPlayUrl + " SDKVersion = " + TXCCommonUtil.getSDKID() + " , " + TXCCommonUtil.getSDKVersionStr() + "    ======");
        TXCLog.i("TXVodPlayer", "===========================================================================================================================================================");
        TXCLog.i("TXVodPlayer", "===========================================================================================================================================================");
        if (this.mPlayer == null) {
            this.mPlayer = new o(this.mContext);
        }
        this.updateConfig();
        if (this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.clearLog();
            this.mTXCloudVideoView.setVisibility(0);
            this.mPlayer.a(this.mTXCloudVideoView);
        }
        else if (this.mSurface != null) {
            this.mPlayer.a(this.mSurface);
        }
        else if (this.mTextureView != null) {
            this.mPlayer.a(this.mTextureView);
        }
        this.mPlayer.f(this.mBitrateIndex);
        this.mPlayer.a(this);
        this.mPlayer.d(this.mIsGainAudioFocus);
        this.mPlayer.e(this.mAutoPlay);
        this.mPlayer.c(this.mStartTime);
        this.mPlayer.a(this.mPlayUrl, 0);
        this.mPlayer.b(this.mMute);
        this.mPlayer.b(this.mRate);
        this.mPlayer.b(this.mRenderRotation);
        this.mPlayer.a(this.mRenderMode);
        this.mPlayer.f(this.mLoop);
        this.setMirror(this.mMirror);
        return 0;
    }
    
    public int startPlay(final TXPlayerAuthBuilder txPlayerAuthBuilder) {
        (this.mNetApi = new f()).a(txPlayerAuthBuilder.isHttps);
        this.mNetApi.a(this);
        return this.mNetApi.a(txPlayerAuthBuilder.appId, txPlayerAuthBuilder.fileId, txPlayerAuthBuilder.timeout, txPlayerAuthBuilder.us, txPlayerAuthBuilder.exper, txPlayerAuthBuilder.sign);
    }
    
    private String checkPlayUrl(String s) {
        if (s.startsWith("http")) {
            try {
                final byte[] bytes = s.getBytes("UTF-8");
                final StringBuilder sb = new StringBuilder(bytes.length);
                for (int i = 0; i < bytes.length; ++i) {
                    final int n = (bytes[i] < 0) ? (bytes[i] + 256) : bytes[i];
                    if (n <= 32 || n >= 127 || n == 34 || n == 37 || n == 60 || n == 62 || n == 91 || n == 125 || n == 92 || n == 93 || n == 94 || n == 96 || n == 123 || n == 124) {
                        if (n == 37) {
                            TXCLog.w("TXVodPlayer", "URL has been transcoded");
                            return s;
                        }
                        sb.append(String.format("%%%02X", n));
                    }
                    else {
                        sb.append((char)n);
                    }
                }
                s = sb.toString();
            }
            catch (Exception ex) {
                TXCLog.e("TXVodPlayer", "get utf-8 string failed.", ex);
            }
        }
        s = s.trim();
        return s;
    }
    
    public int stopPlay(final boolean b) {
        if (b && this.mTXCloudVideoView != null) {
            this.mTXCloudVideoView.setVisibility(8);
        }
        if (this.mPlayer != null) {
            this.mPlayer.a(b);
        }
        this.mPlayUrl = "";
        if (this.mNetApi != null) {
            this.mNetApi.a((g)null);
            this.mNetApi = null;
        }
        this.mBitrateIndex = 0;
        this.mIsGetPlayInfo = false;
        return 0;
    }
    
    public boolean isPlaying() {
        return this.mPlayer != null && this.mPlayer.c();
    }
    
    public void pause() {
        if (this.mPlayer != null) {
            this.mPlayer.a();
        }
    }
    
    public void resume() {
        if (this.mPlayer != null) {
            this.mPlayer.b();
        }
    }
    
    public void seek(final int n) {
        if (this.mPlayer != null) {
            this.mPlayer.e(n);
        }
    }
    
    public void seek(final float n) {
        if (this.mPlayer != null) {
            this.mPlayer.a(n);
        }
    }
    
    public float getCurrentPlaybackTime() {
        if (this.mPlayer != null) {
            return this.mPlayer.h();
        }
        return 0.0f;
    }
    
    public float getBufferDuration() {
        if (this.mPlayer != null) {
            return this.mPlayer.j();
        }
        return 0.0f;
    }
    
    public float getDuration() {
        if (this.mPlayer != null) {
            return this.mPlayer.k();
        }
        return 0.0f;
    }
    
    public float getPlayableDuration() {
        if (this.mPlayer != null) {
            return this.mPlayer.l();
        }
        return 0.0f;
    }
    
    public int getWidth() {
        if (this.mPlayer != null) {
            return this.mPlayer.m();
        }
        return 0;
    }
    
    public int getHeight() {
        if (this.mPlayer != null) {
            return this.mPlayer.n();
        }
        return 0;
    }
    
    @Deprecated
    public void setPlayListener(final ITXLivePlayListener mListener) {
        this.mListener = mListener;
    }
    
    public void setVodListener(final ITXVodPlayListener mNewListener) {
        this.mNewListener = mNewListener;
    }
    
    public void setRenderMode(final int mRenderMode) {
        this.mRenderMode = mRenderMode;
        if (this.mPlayer != null) {
            this.mPlayer.a(mRenderMode);
        }
    }
    
    public void setRenderRotation(final int mRenderRotation) {
        this.mRenderRotation = mRenderRotation;
        if (this.mPlayer != null) {
            this.mPlayer.b(mRenderRotation);
        }
    }
    
    public boolean enableHardwareDecode(final boolean mEnableHWDec) {
        if (mEnableHWDec) {
            if (Build.VERSION.SDK_INT < 18) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, android system build.version = " + Build.VERSION.SDK_INT + ", the minimum build.version should be 18(android 4.3 or later)");
                return false;
            }
            if (this.isAVCDecBlacklistDevices()) {
                TXCLog.e("HardwareDecode", "enableHardwareDecode failed, MANUFACTURER = " + Build.MANUFACTURER + ", MODEL" + Build.MODEL);
                return false;
            }
        }
        this.mEnableHWDec = mEnableHWDec;
        this.updateConfig();
        return true;
    }
    
    public void setMute(final boolean mMute) {
        this.mMute = mMute;
        if (this.mPlayer != null) {
            this.mPlayer.b(mMute);
        }
    }
    
    public boolean setRequestAudioFocus(final boolean mIsGainAudioFocus) {
        this.mIsGainAudioFocus = mIsGainAudioFocus;
        return this.mPlayer == null || this.mPlayer.d(mIsGainAudioFocus);
    }
    
    public void setAutoPlay(final boolean mAutoPlay) {
        this.mAutoPlay = mAutoPlay;
        if (this.mPlayer != null) {
            this.mPlayer.e(mAutoPlay);
        }
    }
    
    public void setRate(final float mRate) {
        this.mRate = mRate;
        if (this.mPlayer != null) {
            this.mPlayer.b(mRate);
        }
    }
    
    public int getBitrateIndex() {
        if (this.mPlayer != null) {
            return this.mPlayer.o();
        }
        return 0;
    }
    
    public void setBitrateIndex(final int mBitrateIndex) {
        if (this.mPlayer != null) {
            this.mPlayer.f(mBitrateIndex);
        }
        this.mBitrateIndex = mBitrateIndex;
    }
    
    public ArrayList<TXBitrateItem> getSupportedBitrates() {
        if (this.mPlayer != null) {
            return this.mPlayer.p();
        }
        return new ArrayList<TXBitrateItem>();
    }
    
    public void snapshot(final TXLivePlayer.ITXSnapshotListener itxSnapshotListener) {
        if (this.mSnapshotRunning || itxSnapshotListener == null) {
            return;
        }
        this.mSnapshotRunning = true;
        TextureView d = null;
        if (this.mPlayer != null) {
            d = this.mPlayer.d();
        }
        if (d != null) {
            final Bitmap bitmap2;
            Bitmap bitmap = bitmap2 = d.getBitmap();
            if (null != bitmap2) {
                final Matrix transform = d.getTransform((Matrix)null);
                if (this.mMirror) {
                    transform.postScale(-1.0f, 1.0f);
                }
                bitmap = Bitmap.createBitmap(bitmap2, 0, 0, bitmap2.getWidth(), bitmap2.getHeight(), transform, true);
                bitmap2.recycle();
            }
            this.postBitmapToMainThread(itxSnapshotListener, bitmap);
        }
        else {
            this.mSnapshotRunning = false;
        }
    }
    
    public void setMirror(final boolean mMirror) {
        if (this.mPlayer != null) {
            this.mPlayer.g(mMirror);
        }
        this.mMirror = mMirror;
    }
    
    public void setStartTime(final float mStartTime) {
        this.mStartTime = mStartTime;
    }
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
        if (n == 15001) {
            if (this.mTXCloudVideoView != null) {
                this.mTXCloudVideoView.setLogText(bundle, null, 0);
            }
            if (this.mListener != null) {
                this.mListener.onNetStatus(bundle);
            }
            if (this.mNewListener != null) {
                this.mNewListener.onNetStatus(this, bundle);
            }
        }
        else {
            if (this.mTXCloudVideoView != null) {
                this.mTXCloudVideoView.setLogText(null, bundle, n);
            }
            if (this.mListener != null) {
                this.mListener.onPlayEvent(n, bundle);
            }
            if (this.mNewListener != null) {
                this.mNewListener.onPlayEvent(this, n, bundle);
            }
        }
    }
    
    private boolean isAVCDecBlacklistDevices() {
        return Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("Che2-TL00");
    }
    
    private void postBitmapToMainThread(final TXLivePlayer.ITXSnapshotListener itxSnapshotListener, final Bitmap bitmap) {
        if (null == itxSnapshotListener) {
            return;
        }
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (null != itxSnapshotListener) {
                    itxSnapshotListener.onSnapshot(bitmap);
                }
                TXVodPlayer.this.mSnapshotRunning = false;
            }
        });
    }
    
    void updateConfig() {
        this.setConfig(this.mConfig);
    }
    
    @Override
    public void onNetSuccess(final f f) {
        if (f != this.mNetApi) {
            return;
        }
        final i a = f.a();
        if (!this.mIsGetPlayInfo) {
            this.startPlay(a.a());
        }
        this.mIsGetPlayInfo = false;
        final Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", 2010);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putString("EVT_MSG", "Requested file information successfully");
        bundle.putString("EVT_PLAY_URL", a.a());
        bundle.putString("EVT_PLAY_COVER_URL", a.b());
        bundle.putString("EVT_PLAY_NAME", a.f());
        bundle.putString("EVT_PLAY_DESCRIPTION", a.g());
        if (a.d() != null) {
            bundle.putInt("EVT_PLAY_DURATION", a.d().c());
        }
        this.onNotifyEvent(2010, bundle);
    }
    
    @Override
    public void onNetFailed(final f f, final String s, final int n) {
        if (f != this.mNetApi) {
            return;
        }
        this.mIsGetPlayInfo = false;
        final Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", 2010);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        bundle.putString("EVT_MSG", s);
        bundle.putInt("EVT_PARAM1", n);
        this.onNotifyEvent(-2306, bundle);
    }
    
    public void setToken(final String mToken) {
        this.mToken = mToken;
    }
    
    public void setLoop(final boolean mLoop) {
        this.mLoop = mLoop;
        if (this.mPlayer != null) {
            this.mPlayer.f(this.mLoop);
        }
    }
    
    public boolean isLoop() {
        return this.mLoop;
    }
}
