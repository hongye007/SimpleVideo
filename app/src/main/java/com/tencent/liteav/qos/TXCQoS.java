package com.tencent.liteav.qos;

import com.tencent.liteav.basic.a.*;
import com.tencent.liteav.basic.b.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import com.tencent.liteav.basic.util.*;

public class TXCQoS
{
    static final String TAG = "TXCQos";
    public static final int AUTO_ADJUST_LIVEPUSH_RESOLUTION_STRATEGY = 1;
    public static final int AUTO_ADJUST_REALTIME_VIDEOCHAT_STRATEGY = 5;
    private static final Map<Integer, c> RESOLUTION_MAP;
    private long mInterval;
    private String mUserID;
    private boolean mIsEnableDrop;
    private int mBitrate;
    private int mWidth;
    private int mHeight;
    private int mAutoStrategy;
    private Handler mHandler;
    private Runnable mRunnable;
    private long mInstance;
    private a mListener;
    private b mNotifyListener;
    
    public TXCQoS(final boolean b) {
        this.mInterval = 1000L;
        this.mUserID = "";
        this.mIsEnableDrop = false;
        this.mBitrate = 0;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mAutoStrategy = -1;
        this.mHandler = new Handler();
        this.mRunnable = new Runnable() {
            @Override
            public void run() {
                if (TXCQoS.this.mListener != null) {
                    final int onGetEncoderRealBitrate = TXCQoS.this.mListener.onGetEncoderRealBitrate();
                    final int onGetQueueInputSize = TXCQoS.this.mListener.onGetQueueInputSize();
                    final int onGetQueueOutputSize = TXCQoS.this.mListener.onGetQueueOutputSize();
                    final int onGetVideoQueueMaxCount = TXCQoS.this.mListener.onGetVideoQueueMaxCount();
                    final int onGetVideoQueueCurrentCount = TXCQoS.this.mListener.onGetVideoQueueCurrentCount();
                    final int onGetVideoDropCount = TXCQoS.this.mListener.onGetVideoDropCount();
                    final int onGetBandwidthEst = TXCQoS.this.mListener.onGetBandwidthEst();
                    TXCQoS.this.nativeSetVideoRealBitrate(TXCQoS.this.mInstance, onGetEncoderRealBitrate);
                    TXCQoS.this.nativeAdjustBitrate(TXCQoS.this.mInstance, onGetVideoQueueMaxCount, onGetVideoQueueCurrentCount, onGetVideoDropCount, onGetQueueOutputSize, onGetQueueInputSize, onGetBandwidthEst);
                    final boolean access$400 = TXCQoS.this.nativeIsEnableDrop(TXCQoS.this.mInstance);
                    if (TXCQoS.this.mIsEnableDrop != access$400) {
                        TXCQoS.this.mIsEnableDrop = access$400;
                        TXCQoS.this.mListener.onEnableDropStatusChanged(access$400);
                    }
                    final int access$401 = TXCQoS.this.nativeGetBitrate(TXCQoS.this.mInstance);
                    final int access$402 = TXCQoS.this.nativeGetWidth(TXCQoS.this.mInstance);
                    final int access$403 = TXCQoS.this.nativeGetHeight(TXCQoS.this.mInstance);
                    if (access$402 != TXCQoS.this.mWidth || access$403 != TXCQoS.this.mHeight) {
                        if (TXCQoS.this.mAutoStrategy == 1 || TXCQoS.this.mAutoStrategy == 5) {
                            TXCQoS.this.mListener.onEncoderParamsChanged(access$401, access$402, access$403);
                            if (TXCQoS.this.mNotifyListener != null) {
                                final Bundle bundle = new Bundle();
                                bundle.putCharSequence("EVT_MSG", (CharSequence)("Adjust resolution:new bitrate:" + access$401 + " new resolution:" + access$402 + "*" + access$403));
                                bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                                TXCQoS.this.mNotifyListener.onNotifyEvent(1005, bundle);
                            }
                        }
                    }
                    else if (access$401 != TXCQoS.this.mBitrate) {
                        TXCQoS.this.mListener.onEncoderParamsChanged(access$401, 0, 0);
                        if (TXCQoS.this.mNotifyListener != null) {
                            final Bundle bundle2 = new Bundle();
                            bundle2.putCharSequence("EVT_MSG", (CharSequence)("Adjust encoding bitrate:new bitrate:" + access$401));
                            bundle2.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
                            bundle2.putString("EVT_USERID", TXCQoS.this.mUserID);
                            TXCQoS.this.mNotifyListener.onNotifyEvent(1006, bundle2);
                        }
                    }
                    TXCQoS.this.mBitrate = access$401;
                    TXCQoS.this.mWidth = access$402;
                    TXCQoS.this.mHeight = access$403;
                }
                TXCQoS.this.mHandler.postDelayed((Runnable)this, TXCQoS.this.mInterval);
            }
        };
        this.mInstance = this.nativeInit(b);
    }
    
    @Override
    protected void finalize() throws Throwable {
        try {
            this.nativeDeinit(this.mInstance);
        }
        finally {
            super.finalize();
        }
    }
    
    public void start(final long mInterval) {
        this.mInterval = mInterval;
        this.mHandler.postDelayed(this.mRunnable, this.mInterval);
    }
    
    public void stop() {
        this.mHandler.removeCallbacks(this.mRunnable);
        this.mAutoStrategy = -1;
    }
    
    public String getUserID() {
        return this.mUserID;
    }
    
    public void setUserID(final String mUserID) {
        this.mUserID = mUserID;
    }
    
    public void setNotifyListener(final b mNotifyListener) {
        this.mNotifyListener = mNotifyListener;
    }
    
    public void setListener(final a mListener) {
        this.mListener = mListener;
    }
    
    public void reset(final boolean b) {
        this.nativeReset(this.mInstance, b);
    }
    
    public boolean isEnableDrop() {
        return this.nativeIsEnableDrop(this.mInstance);
    }
    
    public void setHasVideo(final boolean b) {
        this.nativeSetHasVideo(this.mInstance, b);
    }
    
    public void setAutoAdjustBitrate(final boolean b) {
        TXCLog.i("TXCQos", "autoAdjustBitrate is " + (b ? "yes" : "no"));
        this.nativeSetAutoAdjustBitrate(this.mInstance, b);
    }
    
    public void setAutoAdjustStrategy(final int mAutoStrategy) {
        TXCLog.i("TXCQos", "autoAdjustStrategy is " + mAutoStrategy);
        this.nativeSetAutoAdjustStrategy(this.mInstance, mAutoStrategy);
        this.mAutoStrategy = mAutoStrategy;
    }
    
    public void setDefaultVideoResolution(final c c) {
        TXCLog.i("TXCQos", "DefaultVideoResolution is " + c);
        this.mWidth = 0;
        this.mHeight = 0;
        int intValue = 0;
        for (final Map.Entry<Integer, c> entry : TXCQoS.RESOLUTION_MAP.entrySet()) {
            if (entry.getValue() == c) {
                intValue = entry.getKey();
            }
        }
        this.nativeSetVideoDefaultResolution(this.mInstance, intValue);
    }
    
    public void setVideoEncBitrate(final int n, final int n2, final int n3) {
        this.mBitrate = 0;
        this.nativeSetVideoEncBitrate(this.mInstance, n, n2, n3);
    }
    
    public void setVideoExpectBitrate(final int n) {
        this.nativeSetVideoExpectBitrate(this.mInstance, n);
    }
    
    public static c getProperResolutionByVideoBitrate(final boolean b, final int n, final int n2) {
        return TXCQoS.RESOLUTION_MAP.get(nativeGetProperResolutionByVideoBitrate(b, n, n2));
    }
    
    private native long nativeInit(final boolean p0);
    
    private native void nativeDeinit(final long p0);
    
    private native void nativeReset(final long p0, final boolean p1);
    
    private native boolean nativeIsEnableDrop(final long p0);
    
    private native void nativeSetHasVideo(final long p0, final boolean p1);
    
    private native void nativeSetAutoAdjustBitrate(final long p0, final boolean p1);
    
    private native void nativeSetAutoAdjustStrategy(final long p0, final int p1);
    
    private native void nativeAdjustBitrate(final long p0, final int p1, final int p2, final int p3, final int p4, final int p5, final int p6);
    
    private native void nativeSetVideoEncBitrate(final long p0, final int p1, final int p2, final int p3);
    
    private native void nativeSetVideoRealBitrate(final long p0, final int p1);
    
    private native void nativeSetVideoExpectBitrate(final long p0, final int p1);
    
    private native void nativeSetVideoDefaultResolution(final long p0, final int p1);
    
    private native void nativeAddQueueInputSize(final long p0, final int p1);
    
    private native void nativeAddQueueOutputSize(final long p0, final int p1);
    
    private native int nativeGetBitrate(final long p0);
    
    private native int nativeGetWidth(final long p0);
    
    private native int nativeGetHeight(final long p0);
    
    private static native int nativeGetProperResolutionByVideoBitrate(final boolean p0, final int p1, final int p2);
    
    static {
        final HashMap<Integer, c> hashMap = new HashMap<Integer, c>();
        hashMap.put(0, c.b);
        hashMap.put(1, c.c);
        hashMap.put(2, c.d);
        hashMap.put(3, c.e);
        hashMap.put(4, c.f);
        hashMap.put(5, c.g);
        hashMap.put(6, c.h);
        hashMap.put(7, c.i);
        hashMap.put(8, c.j);
        hashMap.put(9, c.k);
        hashMap.put(10, c.l);
        hashMap.put(11, c.m);
        hashMap.put(12, c.n);
        hashMap.put(13, c.o);
        hashMap.put(14, c.p);
        hashMap.put(15, c.q);
        hashMap.put(16, c.r);
        hashMap.put(17, c.s);
        hashMap.put(18, c.t);
        hashMap.put(19, c.u);
        RESOLUTION_MAP = Collections.unmodifiableMap((Map<?, ?>)hashMap);
        f.f();
    }
}
