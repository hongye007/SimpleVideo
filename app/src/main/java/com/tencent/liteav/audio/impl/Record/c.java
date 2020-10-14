package com.tencent.liteav.audio.impl.Record;

public interface c
{
    void onAudioRecordStart();
    
    void onAudioRecordStop();
    
    void onAudioRecordError(final int p0, final String p1);
    
    void onAudioRecordPCM(final byte[] p0, final int p1, final long p2);
}
