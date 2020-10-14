package com.tencent.liteav.qos;

public interface a
{
    int onGetEncoderRealBitrate();
    
    int onGetQueueInputSize();
    
    int onGetQueueOutputSize();
    
    int onGetVideoQueueMaxCount();
    
    int onGetVideoQueueCurrentCount();
    
    int onGetVideoDropCount();
    
    int onGetBandwidthEst();
    
    void onEncoderParamsChanged(final int p0, final int p1, final int p2);
    
    void onEnableDropStatusChanged(final boolean p0);
}
