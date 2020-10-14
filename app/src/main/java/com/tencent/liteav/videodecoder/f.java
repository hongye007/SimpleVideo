package com.tencent.liteav.videodecoder;

import com.tencent.liteav.basic.structs.*;

public interface f
{
    void onDecodeFrame(final TXSVideoFrame p0, final int p1, final int p2, final long p3, final long p4, final int p5);
    
    void onDecodeFailed(final int p0);
    
    void onVideoSizeChange(final int p0, final int p1);
}
