package com.tencent.liteav.videodecoder;

import java.lang.ref.*;
import android.view.*;
import com.tencent.liteav.basic.structs.*;
import java.nio.*;

public interface b
{
    void setListener(final f p0);
    
    void setNotifyListener(final WeakReference<com.tencent.liteav.basic.b.b> p0);
    
    int config(final Surface p0);
    
    void decode(final TXSNALPacket p0);
    
    int start(final ByteBuffer p0, final ByteBuffer p1, final boolean p2, final boolean p3);
    
    void stop();
    
    boolean isHevc();
    
    int GetDecodeCost();
    
    void enableLimitDecCache(final boolean p0);
}
