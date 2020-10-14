package com.tencent.liteav.basic.c;

import javax.microedition.khronos.egl.*;
import android.graphics.*;

public interface m
{
    void a(final int p0, final boolean p1);
    
    void a();
    
    void setSurfaceTextureListener(final n p0);
    
    EGLContext getGLContext();
    
    SurfaceTexture getSurfaceTexture();
    
    void a(final Runnable p0);
    
    void a(final boolean p0);
    
    void a(final int p0, final boolean p1, final int p2, final int p3, final int p4, final boolean p5);
    
    void a(final byte[] p0);
    
    void setRendMode(final int p0);
    
    void setRendMirror(final int p0);
}
