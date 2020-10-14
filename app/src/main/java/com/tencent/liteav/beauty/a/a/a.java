package com.tencent.liteav.beauty.a.a;

import com.tencent.liteav.basic.log.*;
import javax.microedition.khronos.egl.*;

public class a
{
    private EGL10 a;
    private final EGLContext b;
    private EGLDisplay c;
    private EGLConfig d;
    private EGLSurface e;
    
    public a() {
        this(null);
    }
    
    public a(final EGLConfig d) {
        this.a = (EGL10)EGLContext.getEGL();
        this.c = this.a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        if (this.c == EGL10.EGL_NO_DISPLAY) {
            throw new RuntimeException("unable to get EGL10 display");
        }
        if (!this.a.eglInitialize(this.c, new int[2])) {
            this.c = null;
            throw new RuntimeException("unable to initialize EGL10");
        }
        if (null != d) {
            this.d = d;
        }
        else {
            this.d = this.b();
        }
        this.b = this.a.eglCreateContext(this.c, this.d, EGL10.EGL_NO_CONTEXT, new int[] { 12440, 2, 12344 });
    }
    
    private EGLConfig b() {
        final int[] array = { 12339, 1, 12325, 16, 12326, 0, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12344 };
        final EGLConfig[] array2 = { null };
        if (!this.a.eglChooseConfig(this.c, array, array2, array2.length, new int[1])) {
            TXCLog.w("ImageEglSurface", "unable to find RGB8888  EGLConfig");
            return null;
        }
        return array2[0];
    }
    
    public void a() {
        this.a.eglMakeCurrent(this.c, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        this.a.eglDestroyContext(this.c, this.b);
        this.a.eglTerminate(this.c);
    }
    
    public void a(final EGLSurface eglSurface) {
        this.a.eglDestroySurface(this.c, eglSurface);
    }
    
    public EGLSurface a(final int n, final int n2) {
        this.e = this.a.eglCreatePbufferSurface(this.c, this.d, new int[] { 12375, n, 12374, n2, 12344 });
        this.a("eglCreatePbufferSurface");
        if (this.e == null) {
            throw new RuntimeException("surface was null");
        }
        return this.e;
    }
    
    public void b(final EGLSurface eglSurface) {
        final EGLDisplay c = this.c;
        if (c == EGL11.EGL_NO_DISPLAY) {
            TXCLog.i("EglCore", "NOTE: makeCurrent w/o display");
        }
        if (!this.a.eglMakeCurrent(c, eglSurface, eglSurface, this.b)) {
            throw new RuntimeException("eglMakeCurrent failed");
        }
    }
    
    private void a(final String s) {
        final int eglGetError;
        if ((eglGetError = this.a.eglGetError()) != 12288) {
            throw new RuntimeException(s + ": EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }
}
