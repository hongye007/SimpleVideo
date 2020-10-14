package com.tencent.rtmp;

import java.util.*;

public class TXVodPlayConfig
{
    int mConnectRetryCount;
    int mConnectRetryInterval;
    int mTimeout;
    String mCacheFolderPath;
    int mMaxCacheItems;
    int mPlayerType;
    Map<String, String> mHeaders;
    boolean enableAccurateSeek;
    boolean autoRotate;
    boolean smoothSwitchBitrate;
    String cacheMp4ExtName;
    int progressInterval;
    int maxBufferSize;
    
    public TXVodPlayConfig() {
        this.mConnectRetryCount = 3;
        this.mConnectRetryInterval = 3;
        this.mTimeout = 10;
        this.enableAccurateSeek = true;
        this.autoRotate = true;
        this.smoothSwitchBitrate = false;
        this.cacheMp4ExtName = "mp4";
        this.maxBufferSize = 0;
    }
    
    public void setConnectRetryCount(final int mConnectRetryCount) {
        this.mConnectRetryCount = mConnectRetryCount;
    }
    
    public void setConnectRetryInterval(final int mConnectRetryInterval) {
        this.mConnectRetryInterval = mConnectRetryInterval;
    }
    
    public void setTimeout(final int mTimeout) {
        this.mTimeout = mTimeout;
    }
    
    public void setCacheFolderPath(final String mCacheFolderPath) {
        this.mCacheFolderPath = mCacheFolderPath;
    }
    
    public void setMaxCacheItems(final int mMaxCacheItems) {
        this.mMaxCacheItems = mMaxCacheItems;
    }
    
    public void setPlayerType(final int mPlayerType) {
        this.mPlayerType = mPlayerType;
    }
    
    public void setHeaders(final Map<String, String> mHeaders) {
        this.mHeaders = mHeaders;
    }
    
    public void setEnableAccurateSeek(final boolean enableAccurateSeek) {
        this.enableAccurateSeek = enableAccurateSeek;
    }
    
    public void setAutoRotate(final boolean autoRotate) {
        this.autoRotate = autoRotate;
    }
    
    public void setSmoothSwitchBitrate(final boolean smoothSwitchBitrate) {
        this.smoothSwitchBitrate = smoothSwitchBitrate;
    }
    
    public void setCacheMp4ExtName(final String cacheMp4ExtName) {
        this.cacheMp4ExtName = cacheMp4ExtName;
    }
    
    public void setProgressInterval(final int progressInterval) {
        this.progressInterval = progressInterval;
    }
    
    public void setMaxBufferSize(final int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }
}
