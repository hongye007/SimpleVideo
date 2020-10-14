package com.tencent.ijk.media.player;

import android.graphics.*;

public interface ISurfaceTextureHolder
{
    void setSurfaceTexture(final SurfaceTexture p0);
    
    SurfaceTexture getSurfaceTexture();
    
    void setSurfaceTextureHost(final ISurfaceTextureHost p0);
}
