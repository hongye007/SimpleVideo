package com.tencent.liteav.basic.c;

import javax.microedition.khronos.egl.*;
import android.view.*;
import com.tencent.liteav.basic.log.*;

public class b
{
    public static final String a;
    private EGL10 b;
    private EGLDisplay c;
    private EGLConfig d;
    private boolean e;
    private EGLContext f;
    private boolean g;
    private EGLSurface h;
    private int i;
    private int j;
    private int[] k;
    private static int[] l;
    private static int[] m;
    
    private b() {
        this.i = 0;
        this.j = 0;
        this.k = new int[2];
    }
    
    public static b a(final EGLConfig eglConfig, final EGLContext eglContext, final Surface surface, final int i, final int j) {
        final b b = new b();
        b.i = i;
        b.j = j;
        if (b.a(eglConfig, eglContext, surface)) {
            return b;
        }
        return null;
    }
    
    public boolean a() {
        final boolean eglSwapBuffers = this.b.eglSwapBuffers(this.c, this.h);
        this.e();
        return eglSwapBuffers;
    }
    
    public void b() {
        this.b.eglMakeCurrent(this.c, this.h, this.h, this.f);
        this.e();
    }
    
    public void c() {
        this.b.eglMakeCurrent(this.c, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
        if (this.h != null) {
            this.b.eglDestroySurface(this.c, this.h);
        }
        if (this.f != null) {
            this.b.eglDestroyContext(this.c, this.f);
        }
        this.b.eglTerminate(this.c);
        this.e();
        this.c = null;
        this.h = null;
        this.c = null;
    }
    
    private boolean a(final EGLConfig d, final EGLContext eglContext, final Surface surface) {
        this.b = (EGL10)EGLContext.getEGL();
        this.c = this.b.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        this.b.eglInitialize(this.c, this.k);
        if (d == null) {
            final int[] array = { 0 };
            final EGLConfig[] array2 = { null };
            this.b.eglChooseConfig(this.c, (surface == null) ? com.tencent.liteav.basic.c.b.l : com.tencent.liteav.basic.c.b.m, array2, 1, array);
            this.d = array2[0];
            this.e = true;
        }
        else {
            this.d = d;
        }
        if (eglContext != null) {
            this.g = true;
        }
        try {
            this.f = this.a(this.c, this.d, 2, eglContext);
        }
        catch (d d3) {
            TXCLog.i(com.tencent.liteav.basic.c.b.a, "failed to create EGLContext of OpenGL ES 2.0, try 3.0");
            try {
                this.f = this.a(this.c, this.d, 3, eglContext);
            }
            catch (d d2) {
                TXCLog.e(com.tencent.liteav.basic.c.b.a, "failed to create EGLContext of 3.0. " + d2);
                return false;
            }
        }
        final int[] array3 = { 12375, this.i, 12374, this.j, 12344 };
        if (surface == null) {
            this.h = this.b.eglCreatePbufferSurface(this.c, this.d, array3);
        }
        else {
            this.h = this.b.eglCreateWindowSurface(this.c, this.d, (Object)surface, (int[])null);
        }
        if (this.h == EGL10.EGL_NO_SURFACE) {
            this.e();
            return false;
        }
        if (!this.b.eglMakeCurrent(this.c, this.h, this.h, this.f)) {
            this.e();
            return false;
        }
        return true;
    }
    
    public EGLContext d() {
        return this.f;
    }
    
    public void e() {
        final int eglGetError = this.b.eglGetError();
        if (eglGetError != 12288) {
            TXCLog.e(com.tencent.liteav.basic.c.b.a, "EGL error: 0x" + Integer.toHexString(eglGetError));
        }
    }
    
    private EGLContext a(final EGLDisplay eglDisplay, final EGLConfig eglConfig, final int n, EGLContext egl_NO_CONTEXT) throws d {
        final int[] array = { 12440, n, 12344 };
        if (egl_NO_CONTEXT == null) {
            egl_NO_CONTEXT = EGL10.EGL_NO_CONTEXT;
        }
        final EGLContext eglCreateContext = this.b.eglCreateContext(eglDisplay, eglConfig, egl_NO_CONTEXT, array);
        this.f();
        return eglCreateContext;
    }
    
    private void f() throws d {
        final int eglGetError = this.b.eglGetError();
        final EGL10 b = this.b;
        if (eglGetError != 12288) {
            throw new d(eglGetError);
        }
    }
    
    static {
        a = b.class.getSimpleName();
        b.l = new int[] { 12339, 1, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12352, 4, 12344 };
        b.m = new int[] { 12339, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12352, 4, 12610, 1, 12344 };
    }
}
