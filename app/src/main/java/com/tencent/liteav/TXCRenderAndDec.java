package com.tencent.liteav;

import android.content.*;
import com.tencent.liteav.videodecoder.*;
import java.lang.ref.*;

import com.tencent.liteav.basic.log.*;
import android.graphics.*;
import android.view.*;
import android.os.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.liteav.basic.structs.*;
import com.tencent.liteav.basic.module.*;

import android.opengl.*;

public class TXCRenderAndDec extends BaseObj implements com.tencent.liteav.basic.b.b, com.tencent.liteav.renderer.a.a, g, f
{
    public static final String TAG = "TXCRenderAndDec";
    private Context mContext;
    private j mConfig;
    private boolean mEnableLimitHWDecCache;
    private TXCVideoDecoder mVideoDecoder;
    private boolean mEnableDecoderChange;
    private boolean mEnableRestartDecoder;
    private com.tencent.liteav.renderer.f mVideoRender;
    private WeakReference<com.tencent.liteav.basic.b.b> mNotifyListener;
    private WeakReference<b> mRenderAndDecDelegate;
    private h mVideoFrameFilter;
    private com.tencent.liteav.basic.a.b mVideoFrameFormat;
    private t mVideoFrameListener;
    private boolean mRealTime;
    private boolean mIsRendering;
    private int mStreamType;
    private long mFrameDecErrCnt;
    private long mLastReqKeyFrameTS;
    private boolean mFirstRender;
    private int mRenderMode;
    private int mRenderRotation;
    private long mLastRenderCalculateTS;
    private long mRenderFrameCount;
    private long mLastRenderFrameCount;
    private a mDecListener;
    
    public TXCRenderAndDec(final Context mContext) {
        this.mContext = null;
        this.mConfig = null;
        this.mEnableLimitHWDecCache = false;
        this.mVideoDecoder = null;
        this.mEnableDecoderChange = false;
        this.mEnableRestartDecoder = false;
        this.mVideoRender = null;
        this.mVideoFrameFilter = null;
        this.mVideoFrameFormat = com.tencent.liteav.basic.a.b.a;
        this.mRealTime = false;
        this.mIsRendering = false;
        this.mStreamType = 0;
        this.mFrameDecErrCnt = 0L;
        this.mLastReqKeyFrameTS = 0L;
        this.mFirstRender = false;
        this.mRenderMode = 0;
        this.mRenderRotation = 0;
        this.mLastRenderCalculateTS = 0L;
        this.mRenderFrameCount = 0L;
        this.mLastRenderFrameCount = 0L;
        this.mDecListener = null;
        this.mContext = mContext;
        com.tencent.liteav.basic.d.b.a().a(this.mContext);
    }
    
    public void setVideoRender(final com.tencent.liteav.renderer.f mVideoRender) {
        TXCLog.i("TXCRenderAndDec", "set video render " + mVideoRender + " id " + this.getID() + ", " + this.mStreamType);
        this.mVideoRender = mVideoRender;
        if (this.mVideoRender == null) {
            return;
        }
        this.mVideoRender.setID(this.getID());
        this.mVideoRender.a(this.mStreamType);
        this.mVideoRender.a((com.tencent.liteav.basic.b.b)this);
        this.mVideoRender.c(this.mRenderMode);
        this.mVideoRender.d(this.mRenderRotation);
        if (this.mVideoFrameListener != null && this.mVideoRender instanceof com.tencent.liteav.renderer.a) {
            ((com.tencent.liteav.renderer.a)this.mVideoRender).b(this);
        }
        if (this.mConfig != null) {
            this.mVideoRender.b(this.mConfig.d);
        }
    }
    
    public void setNotifyListener(final com.tencent.liteav.basic.b.b b) {
        this.mNotifyListener = new WeakReference<com.tencent.liteav.basic.b.b>(b);
    }
    
    public void setRenderAndDecDelegate(final b b) {
        this.mRenderAndDecDelegate = new WeakReference<b>(b);
    }
    
    public void setVideoFrameListener(final t mVideoFrameListener, final com.tencent.liteav.basic.a.b mVideoFrameFormat) {
        this.mVideoFrameListener = mVideoFrameListener;
        this.mVideoFrameFormat = mVideoFrameFormat;
        TXCLog.i("TXCRenderAndDec", "setVideoFrameListener->enter listener: " + mVideoFrameListener + ", format: " + mVideoFrameFormat);
        if (this.mVideoRender != null && this.mVideoRender instanceof com.tencent.liteav.renderer.a) {
            if (mVideoFrameListener == null) {
                TXCLog.i("TXCRenderAndDec", "setCustomRenderListener-> clean listener.");
                ((com.tencent.liteav.renderer.a)this.mVideoRender).b((com.tencent.liteav.renderer.a.a)null);
            }
            else {
                TXCLog.i("TXCRenderAndDec", "setCustomRenderListener-> set listener.");
                ((com.tencent.liteav.renderer.a)this.mVideoRender).b(this);
            }
        }
    }
    
    public void setConfig(final j mConfig) {
        this.mConfig = mConfig;
        if (this.mVideoRender != null) {
            this.mVideoRender.b(this.mConfig.d);
        }
    }
    
    public j getConfig() {
        return this.mConfig;
    }
    
    @Override
    public void setID(final String s) {
        super.setID(s);
        if (this.mVideoRender != null) {
            this.mVideoRender.setID(this.getID());
        }
        if (this.mVideoDecoder != null) {
            this.mVideoDecoder.setUserId(s);
        }
    }
    
    public void setDecListener(final a mDecListener) {
        this.mDecListener = mDecListener;
    }
    
    public void enableDecoderChange(final boolean mEnableDecoderChange) {
        this.mEnableDecoderChange = mEnableDecoderChange;
    }
    
    public void enableRestartDecoder(final boolean mEnableRestartDecoder) {
        this.mEnableRestartDecoder = mEnableRestartDecoder;
    }
    
    public void start(final boolean mRealTime) {
        TXCLog.i("TXCRenderAndDec", "start render dec " + this.getID() + ", " + this.mStreamType);
        this.mRealTime = mRealTime;
        this.mFrameDecErrCnt = 0L;
        this.mLastReqKeyFrameTS = 0L;
        if (this.mVideoRender != null) {
            this.mVideoRender.a((g)this);
            this.mVideoRender.f();
            this.mVideoRender.setID(this.getID());
        }
        (this.mVideoDecoder = new TXCVideoDecoder()).setUserId(this.getID());
        this.mVideoDecoder.setStreamType(this.mStreamType);
        this.mVideoDecoder.setListener(this);
        this.mVideoDecoder.setNotifyListener(this);
        this.mVideoDecoder.enableChange(this.mEnableDecoderChange);
        this.mVideoDecoder.enableLimitDecCache(this.mEnableLimitHWDecCache);
        this.mVideoDecoder.enableRestart(this.mEnableRestartDecoder);
        this.startDecode();
        this.mIsRendering = true;
    }
    
    public void startVideo() {
        this.stopVideo();
        this.mRealTime = true;
        this.mFrameDecErrCnt = 0L;
        this.mLastReqKeyFrameTS = 0L;
        if (this.mVideoRender != null) {
            this.mVideoRender.a((g)this);
            this.mVideoRender.f();
            this.mVideoRender.setID(this.getID());
        }
        TXCLog.i("TXCRenderAndDec", "start video dec " + this.getID() + ", " + this.mStreamType);
        (this.mVideoDecoder = new TXCVideoDecoder()).setUserId(this.getID());
        this.mVideoDecoder.setStreamType(this.mStreamType);
        this.mVideoDecoder.setListener(this);
        this.mVideoDecoder.setNotifyListener(this);
        this.mVideoDecoder.enableChange(this.mEnableDecoderChange);
        this.mVideoDecoder.enableRestart(this.mEnableRestartDecoder);
        this.mVideoDecoder.enableLimitDecCache(this.mEnableLimitHWDecCache);
        this.startDecode();
        this.mIsRendering = true;
    }
    
    public void stopVideo() {
        this.mIsRendering = false;
        if (this.mVideoDecoder != null) {
            TXCLog.i("TXCRenderAndDec", "stop video dec " + this.getID() + ", " + this.mStreamType);
            this.mVideoDecoder.setListener(null);
            this.mVideoDecoder.setNotifyListener(null);
            this.mVideoDecoder.stop();
        }
        if (this.mVideoRender != null) {
            this.mVideoRender.a(false);
            this.mVideoRender.a((g)null);
        }
    }
    
    public void stop() {
        TXCLog.i("TXCRenderAndDec", "stop video render dec " + this.getID() + ", " + this.mStreamType);
        this.mIsRendering = false;
        this.mRealTime = false;
        if (this.mVideoDecoder != null) {
            this.mVideoDecoder.setListener(null);
            this.mVideoDecoder.setNotifyListener(null);
            this.mVideoDecoder.stop();
        }
        if (this.mVideoRender != null) {
            this.mVideoRender.a(true);
            this.mVideoRender.a((g)null);
        }
    }
    
    public void muteVideo(final boolean b) {
        if (!b && this.mVideoRender != null) {
            this.mVideoRender.n();
        }
    }
    
    public boolean isRendering() {
        return this.mIsRendering;
    }
    
    public void decVideo(final TXSNALPacket txsnalPacket) {
    }
    
    public void setRenderMode(final int mRenderMode) {
        this.mRenderMode = mRenderMode;
        if (this.mVideoRender != null) {
            this.mVideoRender.c(mRenderMode);
        }
    }
    
    public void setRenderRotation(final int mRenderRotation) {
        TXCLog.i("TXCRenderAndDec", "vrotation setRenderRotation " + mRenderRotation);
        this.mRenderRotation = mRenderRotation;
        if (this.mVideoRender != null) {
            this.mVideoRender.d(mRenderRotation);
        }
    }
    
    public void setBlockInterval(final int n) {
        if (this.mVideoRender != null) {
            this.mVideoRender.e(n);
        }
    }
    
    public long getVideoCacheDuration() {
        return this.getIntValue(6010, 2);
    }
    
    public long getVideoCacheFrameCount() {
        return this.getIntValue(6011, 2);
    }
    
    public int getVideoDecCacheFrameCount() {
        return this.getIntValue(6012, 2);
    }
    
    public long getAVPlayInterval() {
        return this.getLongValue(6013, 2);
    }
    
    public long getAVNetRecvInterval() {
        return this.getLongValue(6014, 2);
    }
    
    public int getVideoGop() {
        return this.getIntValue(7120);
    }
    
    public void updateLoadInfo() {
        if (this.mVideoDecoder != null) {
            this.setStatusValue(5002, this.mStreamType, (long)(this.mVideoDecoder.isHardwareDecode() ? 1 : 0));
        }
        if (this.mVideoRender != null) {
            this.mVideoRender.o();
        }
        else {
            final long currentTimeMillis = System.currentTimeMillis();
            final long n = currentTimeMillis - this.mLastRenderCalculateTS;
            if (n >= 1000L) {
                final double n2 = (this.mRenderFrameCount - this.mLastRenderFrameCount) * 1000.0 / n;
                this.mLastRenderFrameCount = this.mRenderFrameCount;
                this.mLastRenderCalculateTS = currentTimeMillis;
                this.setStatusValue(6002, this.mStreamType, n2);
            }
        }
    }
    
    public com.tencent.liteav.renderer.f getVideoRender() {
        return this.mVideoRender;
    }
    
    public void setStreamType(final int mStreamType) {
        this.mStreamType = mStreamType;
        if (this.mVideoRender != null) {
            this.mVideoRender.a(this.mStreamType);
        }
        if (this.mVideoDecoder != null) {
            this.mVideoDecoder.setStreamType(this.mStreamType);
        }
    }
    
    public int getStreamType() {
        return this.mStreamType;
    }
    
    public void enableLimitDecCache(final boolean mEnableLimitHWDecCache) {
        this.mEnableLimitHWDecCache = mEnableLimitHWDecCache;
        final TXCVideoDecoder mVideoDecoder = this.mVideoDecoder;
        if (mVideoDecoder != null) {
            mVideoDecoder.enableLimitDecCache(mEnableLimitHWDecCache);
        }
    }
    
    private void startDecode(final SurfaceTexture surfaceTexture) {
        final TXCVideoDecoder mVideoDecoder = this.mVideoDecoder;
        if (mVideoDecoder != null) {
            mVideoDecoder.stop();
            mVideoDecoder.enableHWDec(this.mConfig.h);
            mVideoDecoder.config(this.mConfig.y);
            TXCLog.i("TXCRenderAndDec", "trtc_ start decode " + surfaceTexture + ", hw: " + this.mConfig.h + ", id " + this.getID() + "_" + this.mStreamType);
            if (surfaceTexture != null) {
                mVideoDecoder.setup(surfaceTexture, null, null, !this.mRealTime);
                mVideoDecoder.setUserId(this.getID());
                mVideoDecoder.start();
            }
            else if (!this.mConfig.h) {
                mVideoDecoder.setup((Surface)null, null, null, !this.mRealTime);
                mVideoDecoder.setUserId(this.getID());
                mVideoDecoder.start();
            }
        }
    }
    
    private void startDecode() {
        this.startDecode((this.mVideoRender != null) ? this.mVideoRender.a() : null);
    }
    
    private void notifyEvent(final int n, final String s) {
        final Bundle bundle = new Bundle();
        bundle.putString("EVT_USERID", this.getID());
        bundle.putInt("EVT_ID", n);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (s != null) {
            bundle.putCharSequence("EVT_MSG", (CharSequence)s);
        }
        bundle.putInt("EVT_STREAM_TYPE", this.mStreamType);
        com.tencent.liteav.basic.util.f.a(this.mNotifyListener, n, bundle);
    }
    
    private void switchToSoftDecoder() {
        final TXCVideoDecoder mVideoDecoder = this.mVideoDecoder;
        if (mVideoDecoder != null) {
            TXCLog.w("TXCRenderAndDec", "switch to soft decoder when hw error");
            mVideoDecoder.stop();
            mVideoDecoder.enableChange(this.mConfig.h = false);
            this.startDecode();
        }
    }
    
    public void restartDecoder() {
        final TXCVideoDecoder mVideoDecoder = this.mVideoDecoder;
        if (mVideoDecoder != null && mVideoDecoder.isHevc()) {
            mVideoDecoder.restart(true);
        }
    }
    
    @Override
    public void onNotifyEvent(final int n, final Bundle bundle) {
        if (n == 2106) {
            this.switchToSoftDecoder();
        }
        else if (n == 2020) {
            TXCLog.e("TXCRenderAndDec", "decoding too many frame(>40) without output! request key frame now.");
            this.requestKeyFrame();
            return;
        }
        bundle.putInt("EVT_STREAM_TYPE", this.mStreamType);
        com.tencent.liteav.basic.util.f.a(this.mNotifyListener, n, bundle);
    }
    
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surfaceTexture) {
        TXCLog.w("TXCRenderAndDec", "play decode when surface texture create hw " + this.mConfig.h);
        final TXCVideoDecoder mVideoDecoder = this.mVideoDecoder;
        if (mVideoDecoder != null) {
            mVideoDecoder.setup(surfaceTexture, null, null, !this.mRealTime);
        }
        if (this.mConfig.h) {
            this.startDecode(surfaceTexture);
        }
    }
    
    @Override
    public void onSurfaceTextureDestroy(final SurfaceTexture surfaceTexture) {
        try {
            TXCLog.w("TXCRenderAndDec", "play:stop decode when surface texture release");
            if (this.mConfig.h && this.mVideoDecoder != null) {
                this.mVideoDecoder.stop();
            }
            if (this.mVideoFrameFilter != null) {
                this.mVideoFrameFilter.startWithoutAudio();
                this.mVideoFrameFilter = null;
            }
            if (this.mDecListener != null) {
                this.mDecListener.a(surfaceTexture);
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCRenderAndDec", "onSurfaceTextureDestroy failed.", ex);
        }
    }
    
    @Override
    public void onDecodeFrame(final TXSVideoFrame txsVideoFrame, final int n, final int n2, final long n3, final long n4, int n5) {
        if (n5 == 0 || n5 == 1 || n5 == 2 || n5 == 3) {
            n5 = 360 - n5 * 90;
        }
        final t mVideoFrameListener = this.mVideoFrameListener;
        if (mVideoFrameListener != null && txsVideoFrame != null && (this.mVideoFrameFormat == com.tencent.liteav.basic.a.b.b || this.mVideoFrameFormat == com.tencent.liteav.basic.a.b.e)) {
            TXSVideoFrame clone = txsVideoFrame;
            if (this.mVideoRender != null) {
                clone = txsVideoFrame.clone();
            }
            clone.rotation = (n5 + this.mRenderRotation) % 360;
            if (this.mVideoFrameFormat == com.tencent.liteav.basic.a.b.e) {
                clone.loadNV21BufferFromI420Buffer();
            }
            mVideoFrameListener.onRenderVideoFrame(this.getID(), this.mStreamType, clone);
        }
        if (!this.mFirstRender) {
            this.mFirstRender = true;
            TXCEventRecorderProxy.a(this.getID(), 5007, -1L, -1L, "", this.mStreamType);
            if (this.mVideoRender == null) {
                TXCKeyPointReportProxy.a(this.getID(), 40022, 0L, this.mStreamType);
            }
            if (this.mVideoDecoder != null) {
                TXCKeyPointReportProxy.a(this.getID(), 40029, this.mVideoDecoder.GetDecodeFirstFrameTS(), this.mStreamType);
            }
        }
        if (this.mVideoRender != null) {
            this.mVideoRender.a(txsVideoFrame, n, n2, n5);
        }
        else if (this.mLastRenderCalculateTS == 0L) {
            this.mLastRenderCalculateTS = System.currentTimeMillis();
            this.mLastRenderFrameCount = 0L;
            this.mRenderFrameCount = 0L;
        }
        else {
            ++this.mRenderFrameCount;
        }
    }
    
    @Override
    public void onVideoSizeChange(final int n, final int n2) {
        if (this.mVideoRender != null) {
            this.mVideoRender.b(n, n2);
        }
        final Bundle bundle = new Bundle();
        bundle.putCharSequence("EVT_MSG", (CharSequence)("Resolution changed to" + n + "x" + n2));
        bundle.putInt("EVT_PARAM1", n);
        bundle.putInt("EVT_PARAM2", n2);
        bundle.putString("EVT_USERID", this.getID());
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        this.onNotifyEvent(2009, bundle);
        this.setStatusValue(5003, this.mStreamType, n << 16 | n2);
        TXCEventRecorderProxy.a(this.getID(), 4003, n, n2, "", this.mStreamType);
        TXCKeyPointReportProxy.a(this.getID(), 40002, n, this.mStreamType);
        TXCKeyPointReportProxy.a(this.getID(), 40003, n2, this.mStreamType);
    }
    
    @Override
    public void onDecodeFailed(final int n) {
        TXCLog.e("TXCRenderAndDec", "video decode failed " + n);
        this.requestKeyFrame();
        final int n2 = 17014;
        final int mStreamType = this.mStreamType;
        final long mFrameDecErrCnt = this.mFrameDecErrCnt + 1L;
        this.mFrameDecErrCnt = mFrameDecErrCnt;
        this.setStatusValue(n2, mStreamType, mFrameDecErrCnt);
    }
    
    @Override
    public void onTextureProcess(final int textureId, final int width, final int height, final int n) {
        final t mVideoFrameListener = this.mVideoFrameListener;
        if (mVideoFrameListener != null) {
            if (this.mVideoFrameFormat == com.tencent.liteav.basic.a.b.c) {
                final TXSVideoFrame txsVideoFrame = new TXSVideoFrame();
                txsVideoFrame.width = width;
                txsVideoFrame.height = height;
                txsVideoFrame.pts = TXCTimeUtil.getTimeTick();
                txsVideoFrame.rotation = (n + this.mRenderRotation) % 360;
                txsVideoFrame.textureId = textureId;
                if (this.mVideoRender instanceof com.tencent.liteav.renderer.a) {
                    txsVideoFrame.eglContext = ((com.tencent.liteav.renderer.a)this.mVideoRender).b();
                }
                mVideoFrameListener.onRenderVideoFrame(this.getID(), this.mStreamType, txsVideoFrame);
            }
            else {
                final TXCVideoDecoder mVideoDecoder = this.mVideoDecoder;
                if (mVideoDecoder != null && !mVideoDecoder.isHardwareDecode()) {
                    return;
                }
                if (this.mVideoFrameFilter == null) {
                    if (this.mVideoFrameFormat == com.tencent.liteav.basic.a.b.e) {
                        this.mVideoFrameFilter = new w(3);
                    }
                    else {
                        this.mVideoFrameFilter = new w(1);
                    }
                    this.mVideoFrameFilter.a(true);
                    if (this.mVideoFrameFilter.c()) {
                        this.mVideoFrameFilter.a(width, height);
                        this.mVideoFrameFilter.a(new h.a() {
                            @Override
                            public void a(final int n) {
                                final h access$000 = TXCRenderAndDec.this.mVideoFrameFilter;
                                final t access$2 = TXCRenderAndDec.this.mVideoFrameListener;
                                if (access$000 != null && access$2 != null) {
                                    final TXSVideoFrame txsVideoFrame = new TXSVideoFrame();
                                    txsVideoFrame.width = access$000.o();
                                    txsVideoFrame.height = access$000.p();
                                    txsVideoFrame.pts = TXCTimeUtil.getTimeTick();
                                    txsVideoFrame.rotation = (n + TXCRenderAndDec.this.mRenderRotation) % 360;
                                    access$2.onRenderVideoFrame(TXCRenderAndDec.this.getID(), TXCRenderAndDec.this.mStreamType, txsVideoFrame);
                                }
                            }
                        });
                    }
                    else {
                        TXCLog.i("TXCRenderAndDec", "throwVideoFrame->release mVideoFrameFilter");
                        this.mVideoFrameFilter = null;
                    }
                }
                if (this.mVideoFrameFilter != null) {
                    GLES20.glViewport(0, 0, width, height);
                    this.mVideoFrameFilter.a(width, height);
                    this.mVideoFrameFilter.setVideoEncRotation(textureId);
                }
            }
        }
    }
    
    private void requestKeyFrame() {
        final long timeTick = TXCTimeUtil.getTimeTick();
        if (timeTick > this.mLastReqKeyFrameTS + 3000L) {
            this.mLastReqKeyFrameTS = timeTick;
            TXCLog.e("TXCRenderAndDec", "requestKeyFrame: " + this.getID());
            if (this.mRenderAndDecDelegate != null) {
                final b b = this.mRenderAndDecDelegate.get();
                if (b != null) {
                    b.onRequestKeyFrame(this.getID(), this.mStreamType);
                }
            }
        }
    }
    
    public interface a
    {
        void a(final SurfaceTexture p0);
    }
    
    public interface b
    {
        void onRequestKeyFrame(final String p0, final int p1);
    }
}
