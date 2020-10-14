package com.tencent.liteav.basic.c;

import android.graphics.*;

public interface n
{
    void onSurfaceTextureAvailable(final SurfaceTexture p0);
    
    int onTextureProcess(final int p0, final float[] p1);
    
    void onBufferProcess(final byte[] p0, final float[] p1);
    
    void onSurfaceTextureDestroy(final SurfaceTexture p0);
}
