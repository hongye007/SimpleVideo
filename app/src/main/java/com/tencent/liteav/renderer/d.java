package com.tencent.liteav.renderer;

import android.graphics.*;
import com.tencent.liteav.basic.log.*;

public class d extends f
{
    @Override
    protected void a(final SurfaceTexture surfaceTexture) {
        try {
            if (this.o != null) {
                this.o.onSurfaceTextureAvailable(surfaceTexture);
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCTextureViewRender", "onSurfaceTextureAvailable failed.", ex);
        }
    }
    
    @Override
    protected void b(final SurfaceTexture surfaceTexture) {
        try {
            if (this.o != null) {
                this.o.onSurfaceTextureDestroy(surfaceTexture);
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXCTextureViewRender", "onSurfaceTextureDestroy failed.", ex);
        }
    }
    
    @Override
    public SurfaceTexture a() {
        return (this.d != null && this.d.isAvailable()) ? this.d.getSurfaceTexture() : null;
    }
}
