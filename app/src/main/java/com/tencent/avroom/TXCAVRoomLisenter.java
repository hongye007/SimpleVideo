package com.tencent.avroom;

import android.os.*;

public interface TXCAVRoomLisenter
{
    void onVideoStateChange(final long p0, final boolean p1);
    
    void onMemberChange(final long p0, final boolean p1);
    
    void onAVRoomEvent(final long p0, final int p1, final Bundle p2);
    
    void onAVRoomStatus(final long p0, final Bundle p1);
}
