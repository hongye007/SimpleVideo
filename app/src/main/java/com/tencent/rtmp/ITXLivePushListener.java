package com.tencent.rtmp;

import android.os.*;

public interface ITXLivePushListener
{
    void onPushEvent(final int p0, final Bundle p1);
    
    void onNetStatus(final Bundle p0);
}
