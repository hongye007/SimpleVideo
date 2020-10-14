package com.tencent.liteav.videoencoder;

import com.tencent.liteav.basic.structs.*;
import android.media.*;

public interface d
{
    void onEncodeNAL(final TXSNALPacket p0, final int p1);
    
    void onEncodeFormat(final MediaFormat p0);
    
    void onEncodeFinished(final int p0, final long p1, final long p2);
    
    void onRestartEncoder(final int p0);
    
    void onEncodeDataIn(final int p0);
}
