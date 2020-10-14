package com.tencent.liteav.basic.structs;

public class TXSAVModuleSyncInfo
{
    public long mLatestReceiveTimeMs;
    public long mLatestReceivedCaptureTimestamp;
    public long mCurrentDelayMs;
    
    public TXSAVModuleSyncInfo() {
        this.mLatestReceiveTimeMs = 0L;
        this.mLatestReceivedCaptureTimestamp = 0L;
        this.mCurrentDelayMs = 0L;
    }
}
