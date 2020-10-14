package com.tencent.liteav.txcvodplayer;

import android.view.*;
import android.support.annotation.*;
import com.tencent.ijk.media.player.*;

public interface a
{
    View getView();
    
    boolean shouldWaitForResize();
    
    void setVideoSize(final int p0, final int p1);
    
    void setVideoSampleAspectRatio(final int p0, final int p1);
    
    void setVideoRotation(final int p0);
    
    void setAspectRatio(final int p0);
    
    void addRenderCallback(@NonNull final a p0);
    
    void removeRenderCallback(@NonNull final a p0);
    
    public interface a
    {
        void a(@NonNull final b p0, final int p1, final int p2);
        
        void a(@NonNull final b p0, final int p1, final int p2, final int p3);
        
        void a(@NonNull final b p0);
    }
    
    public interface b
    {
        void a(final IMediaPlayer p0);
        
        @NonNull
        a a();
    }
}
