package com.tencent.liteav.basic.c;

import android.annotation.*;
import android.view.*;
import com.tencent.liteav.basic.log.*;
import android.util.*;
import android.opengl.*;

@TargetApi(17)
public class c
{
    private static int a;
    private static final String b;
    private EGLDisplay c;
    private EGLContext d;
    private EGLConfig e;
    private int f;
    private int g;
    private boolean h;
    private EGLSurface i;
    private int j;
    private static int[] k;
    private static int[] l;
    
    public c() {
        this.c = EGL14.EGL_NO_DISPLAY;
        this.d = EGL14.EGL_NO_CONTEXT;
        this.e = null;
        this.f = 0;
        this.g = 0;
        this.j = -1;
    }
    
    public static c a(final EGLConfig eglConfig, final EGLContext eglContext, final Surface surface, final int f, final int g) {
        final c c = new c();
        c.f = f;
        c.g = g;
        if (c.a(eglConfig, eglContext, surface)) {
            return c;
        }
        return null;
    }
    
    public void a() {
        final int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            TXCLog.e(com.tencent.liteav.basic.c.c.b, "EGL error:" + eglGetError);
        }
    }
    
    public void b() {
        if (this.c == EGL14.EGL_NO_DISPLAY) {
            Log.d(com.tencent.liteav.basic.c.c.b, "NOTE: makeCurrent w/o display");
        }
        if (!EGL14.eglMakeCurrent(this.c, this.i, this.i, this.d)) {
            TXCLog.e(com.tencent.liteav.basic.c.c.b, "eglMakeCurrent failed");
        }
    }
    
    public void c() {
        if (this.c != EGL14.EGL_NO_DISPLAY) {
            EGL14.eglMakeCurrent(this.c, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
            EGL14.eglDestroySurface(this.c, this.i);
            EGL14.eglDestroyContext(this.c, this.d);
            this.d = EGL14.EGL_NO_CONTEXT;
            EGL14.eglReleaseThread();
            EGL14.eglTerminate(this.c);
        }
        this.c = EGL14.EGL_NO_DISPLAY;
    }
    
    public boolean d() {
        return EGL14.eglSwapBuffers(this.c, this.i);
    }
    
    private boolean a(final EGLConfig e, EGLContext egl_NO_CONTEXT, final Surface surface) {
        this.c = EGL14.eglGetDisplay(0);
        if (this.c == EGL14.EGL_NO_DISPLAY) {
            TXCLog.e(com.tencent.liteav.basic.c.c.b, "unable to get EGL14 display");
        }
        final int[] array = new int[2];
        if (!EGL14.eglInitialize(this.c, array, 0, array, 1)) {
            this.c = null;
            TXCLog.e(com.tencent.liteav.basic.c.c.b, "unable to initialize EGL14");
        }
        if (e != null) {
            this.e = e;
        }
        else {
            final EGLConfig[] array2 = { null };
            if (!EGL14.eglChooseConfig(this.c, (surface == null) ? com.tencent.liteav.basic.c.c.l : com.tencent.liteav.basic.c.c.k, 0, array2, 0, array2.length, new int[1], 0)) {
                return false;
            }
            this.e = array2[0];
        }
        if (egl_NO_CONTEXT != null) {
            this.h = true;
        }
        else {
            egl_NO_CONTEXT = EGL14.EGL_NO_CONTEXT;
        }
        try {
            this.d = a(this.c, this.e, 2, egl_NO_CONTEXT);
        }
        catch (d d2) {
            TXCLog.i(com.tencent.liteav.basic.c.c.b, "failed to create EGLContext of OpenGL ES 2.0, try 3.0");
            try {
                this.d = a(this.c, this.e, 3, egl_NO_CONTEXT);
            }
            catch (d d) {
                TXCLog.e(com.tencent.liteav.basic.c.c.b, "failed to create EGLContext of 3.0. " + d);
                return false;
            }
        }
        final int[] array3 = { 12344 };
        if (surface == null) {
            this.i = EGL14.eglCreatePbufferSurface(this.c, this.e, new int[] { 12375, this.f, 12374, this.g, 12344 }, 0);
        }
        else {
            this.i = EGL14.eglCreateWindowSurface(this.c, this.e, (Object)surface, array3, 0);
        }
        this.a();
        if (!EGL14.eglMakeCurrent(this.c, this.i, this.i, this.d)) {
            this.a();
            return false;
        }
        return true;
    }
    
    private static EGLContext a(final EGLDisplay eglDisplay, final EGLConfig eglConfig, final int n, final EGLContext eglContext) throws d {
        final EGLContext eglCreateContext = EGL14.eglCreateContext(eglDisplay, eglConfig, eglContext, new int[] { 12440, n, 12344 }, 0);
        f();
        return eglCreateContext;
    }
    
    private static void f() throws d {
        final int eglGetError = EGL14.eglGetError();
        if (eglGetError != 12288) {
            throw new d(eglGetError);
        }
    }
    
    public void a(final long n) {
        EGLExt.eglPresentationTimeANDROID(this.c, this.i, n);
    }
    
    public EGLContext e() {
        return this.d;
    }
    
    static {
        c.a = 2;
        b = c.class.getSimpleName();
        c.k = new int[] { 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12352, (c.a == 2) ? 4 : 68, 12610, 1, 12344 };
        c.l = new int[] { 12339, 1, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12352, (c.a == 2) ? 4 : 68, 12610, 1, 12344 };
    }
}
