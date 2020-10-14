package com.tencent.liteav.f;

import javax.microedition.khronos.egl.*;
import android.graphics.*;
import android.opengl.*;

public class d
{
    private int a;
    private int b;
    private EGL10 c;
    private EGLDisplay d;
    private EGLConfig e;
    private EGLSurface f;
    private EGLContext g;
    
    public d() {
        this.a = 12440;
        this.b = 4;
    }
    
    public void a(final SurfaceTexture surfaceTexture) {
        this.c = (EGL10)EGLContext.getEGL();
        this.d = this.c.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.c.eglInitialize(this.d, new int[2]);
        this.e = this.c();
        this.f = this.c.eglCreateWindowSurface(this.d, this.e, (Object)surfaceTexture, (int[])null);
        this.g = this.a(this.c, this.d, this.e, EGL10.EGL_NO_CONTEXT);
        if (this.f == null || this.f == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("GL error:" + GLUtils.getEGLErrorString(this.c.eglGetError()));
        }
        if (!this.c.eglMakeCurrent(this.d, this.f, this.f, this.g)) {
            throw new RuntimeException("GL Make current Error" + GLUtils.getEGLErrorString(this.c.eglGetError()));
        }
    }
    
    public void a() {
        if (this.c == null) {
            return;
        }
        this.c.eglMakeCurrent(this.d, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        this.c.eglDestroyContext(this.d, this.g);
        this.c.eglDestroySurface(this.d, this.f);
        this.c.eglTerminate(this.d);
        this.c = null;
    }
    
    private EGLContext a(final EGL10 egl10, final EGLDisplay eglDisplay, final EGLConfig eglConfig, final EGLContext eglContext) {
        return egl10.eglCreateContext(eglDisplay, eglConfig, eglContext, new int[] { this.a, 2, 12344 });
    }
    
    private EGLConfig c() {
        final int[] array = { 0 };
        final EGLConfig[] array2 = { null };
        if (!this.c.eglChooseConfig(this.d, this.d(), array2, 1, array)) {
            throw new IllegalArgumentException("Failed to choose config:" + GLUtils.getEGLErrorString(this.c.eglGetError()));
        }
        if (array[0] > 0) {
            return array2[0];
        }
        return null;
    }
    
    private int[] d() {
        return new int[] { 12352, this.b, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344 };
    }
    
    public void b() {
        if (this.c != null && this.d != null && this.f != null) {
            this.c.eglSwapBuffers(this.d, this.f);
        }
    }
}
