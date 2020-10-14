package com.tencent.rtmp;

import android.os.*;

public interface ITXVodPlayListener
{
    void onPlayEvent(final TXVodPlayer p0, final int p1, final Bundle p2);
    
    void onNetStatus(final TXVodPlayer p0, final Bundle p1);
}
