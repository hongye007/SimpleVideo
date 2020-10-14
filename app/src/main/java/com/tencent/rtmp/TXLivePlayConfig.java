package com.tencent.rtmp;

import java.util.*;

public class TXLivePlayConfig
{
    float mCacheTime;
    float mMaxAutoAdjustCacheTime;
    float mMinAutoAdjustCacheTime;
    int mVideoBlockThreshold;
    int mConnectRetryCount;
    int mConnectRetryInterval;
    boolean mAutoAdjustCacheTime;
    boolean mEnableAec;
    boolean mEnableNearestIP;
    boolean mEnableMessage;
    boolean mEnableMetaData;
    String mFlvSessionKey;
    int mRtmpChannelType;
    String mCacheFolderPath;
    int mMaxCacheItems;
    boolean mAutoRotate;
    protected Map<String, String> mHeaders;
    
    public TXLivePlayConfig() {
        this.mCacheTime = 5.0f;
        this.mMaxAutoAdjustCacheTime = 5.0f;
        this.mMinAutoAdjustCacheTime = 1.0f;
        this.mVideoBlockThreshold = 800;
        this.mConnectRetryCount = 3;
        this.mConnectRetryInterval = 3;
        this.mAutoAdjustCacheTime = true;
        this.mEnableAec = false;
        this.mEnableNearestIP = true;
        this.mEnableMessage = false;
        this.mEnableMetaData = false;
        this.mFlvSessionKey = "";
        this.mRtmpChannelType = 0;
        this.mAutoRotate = true;
    }
    
    public void setAutoAdjustCacheTime(final boolean mAutoAdjustCacheTime) {
        this.mAutoAdjustCacheTime = mAutoAdjustCacheTime;
    }
    
    public void setCacheTime(final float mCacheTime) {
        this.mCacheTime = mCacheTime;
    }
    
    public void setMaxAutoAdjustCacheTime(final float mMaxAutoAdjustCacheTime) {
        this.mMaxAutoAdjustCacheTime = mMaxAutoAdjustCacheTime;
    }
    
    public void setMinAutoAdjustCacheTime(final float mMinAutoAdjustCacheTime) {
        this.mMinAutoAdjustCacheTime = mMinAutoAdjustCacheTime;
    }
    
    public void setVideoBlockThreshold(final int mVideoBlockThreshold) {
        this.mVideoBlockThreshold = mVideoBlockThreshold;
    }
    
    public void setConnectRetryCount(final int mConnectRetryCount) {
        this.mConnectRetryCount = mConnectRetryCount;
    }
    
    public void setConnectRetryInterval(final int mConnectRetryInterval) {
        this.mConnectRetryInterval = mConnectRetryInterval;
    }
    
    public void setEnableMessage(final boolean mEnableMessage) {
        this.mEnableMessage = mEnableMessage;
    }
    
    public void enableAEC(final boolean mEnableAec) {
        this.mEnableAec = mEnableAec;
    }
    
    public void setEnableMetaData(final boolean mEnableMetaData) {
        this.mEnableMetaData = mEnableMetaData;
    }
    
    public void setFlvSessionKey(final String mFlvSessionKey) {
        this.mFlvSessionKey = mFlvSessionKey;
    }
    
    @Deprecated
    public void setEnableNearestIP(final boolean mEnableNearestIP) {
        this.mEnableNearestIP = mEnableNearestIP;
    }
    
    @Deprecated
    public void setRtmpChannelType(final int mRtmpChannelType) {
        this.mRtmpChannelType = mRtmpChannelType;
    }
    
    @Deprecated
    public void setCacheFolderPath(final String mCacheFolderPath) {
        this.mCacheFolderPath = mCacheFolderPath;
    }
    
    @Deprecated
    public void setMaxCacheItems(final int mMaxCacheItems) {
        this.mMaxCacheItems = mMaxCacheItems;
    }
    
    @Deprecated
    public void setHeaders(final Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }
}
