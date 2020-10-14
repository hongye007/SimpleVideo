package com.tencent.liteav.audio;

public interface d
{
    void onRecordRawPcmData(final byte[] p0, final long p1, final int p2, final int p3, final int p4, final boolean p5);
    
    void onRecordPcmData(final byte[] p0, final long p1, final int p2, final int p3, final int p4);
    
    void onRecordEncData(final byte[] p0, final long p1, final int p2, final int p3, final int p4);
    
    void onRecordError(final int p0, final String p1);
}
