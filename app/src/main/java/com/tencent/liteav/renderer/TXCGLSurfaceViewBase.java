package com.tencent.liteav.renderer;

import android.view.*;
import java.lang.ref.*;
import android.content.*;
import android.util.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import java.io.*;
import javax.microedition.khronos.egl.*;
import javax.microedition.khronos.opengles.*;
import android.opengl.*;

public class TXCGLSurfaceViewBase extends SurfaceView implements SurfaceHolder.Callback
{
    protected boolean b;
    protected boolean c;
    private static final j a;
    protected final WeakReference<TXCGLSurfaceViewBase> d;
    private i g;
    private GLSurfaceView.Renderer h;
    protected boolean e;
    protected boolean f;
    private boolean i;
    private e j;
    private f k;
    private g l;
    private k m;
    private int n;
    private int o;
    private boolean p;
    
    public TXCGLSurfaceViewBase(final Context context) {
        super(context);
        this.b = false;
        this.c = false;
        this.d = new WeakReference<TXCGLSurfaceViewBase>(this);
        this.a();
    }
    
    public TXCGLSurfaceViewBase(final Context context, final AttributeSet set) {
        super(context, set);
        this.b = false;
        this.c = false;
        this.d = new WeakReference<TXCGLSurfaceViewBase>(this);
        this.a();
    }
    
    protected int c() {
        return 0;
    }
    
    protected void finalize() throws Throwable {
        try {
            if (this.g != null) {
                this.g.h();
            }
        }
        finally {
            super.finalize();
        }
    }
    
    private void a() {
        this.getHolder().addCallback((SurfaceHolder.Callback)this);
    }
    
    public void setGLWrapper(final k m) {
        this.m = m;
    }
    
    public void setDebugFlags(final int n) {
        this.n = n;
    }
    
    public int getDebugFlags() {
        return this.n;
    }
    
    public void setPreserveEGLContextOnPause(final boolean p) {
        this.p = p;
    }
    
    public boolean getPreserveEGLContextOnPause() {
        return this.p;
    }
    
    public void setRenderer(final GLSurfaceView.Renderer h) {
        this.g();
        if (this.j == null) {
            this.j = new m(true);
        }
        if (this.k == null) {
            this.k = new c();
        }
        if (this.l == null) {
            this.l = new d();
        }
        this.h = h;
        (this.g = new i(this.d)).start();
        TXCLog.i("TXCGLSurfaceViewBase", "setRenderer-->mGLThread.start");
    }
    
    public void setEGLContextFactory(final f k) {
        this.g();
        this.k = k;
    }
    
    public void setEGLWindowSurfaceFactory(final g l) {
        this.g();
        this.l = l;
    }
    
    public void setEGLConfigChooser(final e j) {
        this.g();
        this.j = j;
    }
    
    public void setEGLConfigChooser(final boolean b) {
        this.setEGLConfigChooser(new m(b));
    }
    
    public void a(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        this.setEGLConfigChooser(new b(n, n2, n3, n4, n5, n6));
    }
    
    public void setEGLContextClientVersion(final int o) {
        this.g();
        this.o = o;
    }
    
    public void setRenderMode(final int n) {
        this.g.a(n);
    }
    
    public int getRenderMode() {
        return this.g.e();
    }
    
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        this.g.f();
        this.setRunInBackground(false);
    }
    
    public void surfaceDestroyed(final SurfaceHolder surfaceHolder) {
        this.setRunInBackground(true);
        if (!this.b) {
            this.g.a(new Runnable() {
                @Override
                public void run() {
                    TXCGLSurfaceViewBase.this.b();
                }
            });
            this.g.g();
        }
    }
    
    protected void b() {
    }
    
    public void surfaceChanged(final SurfaceHolder surfaceHolder, final int n, final int n2, final int n3) {
        this.g.a(n2, n3);
    }
    
    public void c(final boolean b) {
        this.b = b;
        if (!this.b && this.c && this.g != null) {
            TXCLog.w("TXCGLSurfaceViewBase", "background capture destroy surface when not enable background run");
            this.g.a(new Runnable() {
                @Override
                public void run() {
                    TXCGLSurfaceViewBase.this.b();
                }
            });
            this.g.g();
        }
    }
    
    protected void setRunInBackground(final boolean c) {
        this.c = c;
    }
    
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.i && this.h != null) {
            int e = 1;
            if (this.g != null) {
                e = this.g.e();
            }
            this.g = new i(this.d);
            if (e != 1) {
                this.g.a(e);
            }
            this.g.start();
        }
        this.i = false;
    }
    
    protected void onDetachedFromWindow() {
        if (this.b && this.g != null) {
            TXCLog.w("TXCGLSurfaceViewBase", "background capture destroy surface when onDetachedFromWindow");
            this.g.a(new Runnable() {
                @Override
                public void run() {
                    TXCGLSurfaceViewBase.this.b();
                }
            });
            this.g.g();
        }
        if (this.g != null) {
            this.g.h();
        }
        this.i = true;
        super.onDetachedFromWindow();
    }
    
    private void g() {
        if (this.g != null) {
            throw new IllegalStateException("setRenderer has already been called for this instance.");
        }
    }
    
    public boolean d() {
        return this.g.a();
    }
    
    public int e() {
        return this.g.b();
    }
    
    public h getEGLHelper() {
        return this.g.c();
    }
    
    static {
        a = new j();
    }
    
    static class i extends Thread
    {
        private boolean a;
        private boolean b;
        private boolean c;
        private boolean d;
        private boolean e;
        private boolean f;
        private boolean g;
        private boolean h;
        private boolean i;
        private boolean j;
        private boolean k;
        private int l;
        private int m;
        private int n;
        private boolean o;
        private boolean p;
        private ArrayList<Runnable> q;
        private boolean r;
        private h s;
        private WeakReference<TXCGLSurfaceViewBase> t;
        
        i(final WeakReference<TXCGLSurfaceViewBase> t) {
            this.q = new ArrayList<Runnable>();
            this.r = true;
            this.l = 0;
            this.m = 0;
            this.o = true;
            this.n = 1;
            this.t = t;
        }
        
        @Override
        public void run() {
            this.setName("GLThread " + this.getId());
            try {
                this.j();
            }
            catch (InterruptedException ex) {}
            finally {
                TXCGLSurfaceViewBase.a.a(this);
            }
        }
        
        public boolean a() {
            return this.s.c();
        }
        
        public int b() {
            return this.s.d();
        }
        
        public h c() {
            return this.s;
        }
        
        private void j() throws InterruptedException {
            this.s = new h(this.t);
            this.h = false;
            this.i = false;
            try {
                GL10 gl10 = null;
                int n = 0;
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                boolean b = false;
                int n6 = 0;
                int n7 = 0;
                int l = 0;
                int m = 0;
                Runnable runnable = null;
                while (true) {
                    Label_0566: {
                        synchronized (TXCGLSurfaceViewBase.a) {
                            while (!this.a) {
                                Label_0552: {
                                    if (this.q.isEmpty()) {
                                        boolean c = false;
                                        if (this.d != this.c) {
                                            c = this.c;
                                            this.d = this.c;
                                            TXCGLSurfaceViewBase.a.notifyAll();
                                        }
                                        if (this.k) {
                                            this.k();
                                            this.l();
                                            this.k = false;
                                            n7 = 1;
                                        }
                                        if (n4 != 0) {
                                            this.k();
                                            this.l();
                                            n4 = 0;
                                        }
                                        if (c && this.i) {
                                            this.k();
                                        }
                                        if (c && this.h) {
                                            final TXCGLSurfaceViewBase txcglSurfaceViewBase = this.t.get();
                                            if (txcglSurfaceViewBase == null || !txcglSurfaceViewBase.p || TXCGLSurfaceViewBase.a.a()) {
                                                this.l();
                                            }
                                        }
                                        if (c && TXCGLSurfaceViewBase.a.b()) {
                                            this.s.h();
                                        }
                                        if (!this.e && !this.g) {
                                            if (this.i) {
                                                this.k();
                                            }
                                            this.g = true;
                                            this.f = false;
                                            TXCGLSurfaceViewBase.a.notifyAll();
                                        }
                                        if (this.e && this.g) {
                                            this.g = false;
                                            TXCGLSurfaceViewBase.a.notifyAll();
                                        }
                                        if (n6 != 0) {
                                            b = false;
                                            n6 = 0;
                                            this.p = true;
                                            TXCGLSurfaceViewBase.a.notifyAll();
                                        }
                                        if (this.m()) {
                                            if (!this.h) {
                                                if (n7 != 0) {
                                                    n7 = 0;
                                                }
                                                else if (TXCGLSurfaceViewBase.a.b(this)) {
                                                    try {
                                                        this.s.a();
                                                    }
                                                    catch (RuntimeException ex) {
                                                        TXCGLSurfaceViewBase.a.c(this);
                                                        TXCLog.e("TXCGLSurfaceViewBase", "egl start error ", ex);
                                                    }
                                                    this.h = true;
                                                    n = 1;
                                                    TXCGLSurfaceViewBase.a.notifyAll();
                                                }
                                            }
                                            if (this.h && !this.i) {
                                                this.i = true;
                                                n2 = 1;
                                                n3 = 1;
                                                n5 = 1;
                                            }
                                            if (this.i) {
                                                if (this.r) {
                                                    n5 = 1;
                                                    l = this.l;
                                                    m = this.m;
                                                    b = true;
                                                    n2 = 1;
                                                    this.r = false;
                                                }
                                                this.o = false;
                                                TXCGLSurfaceViewBase.a.notifyAll();
                                                break Label_0552;
                                            }
                                        }
                                        TXCGLSurfaceViewBase.a.wait();
                                        continue;
                                    }
                                    runnable = this.q.remove(0);
                                }
                                break Label_0566;
                            }
                            return;
                        }
                    }
                    if (runnable != null) {
                        runnable.run();
                        runnable = null;
                    }
                    else {
                        if (n2 != 0) {
                            if (!this.s.b()) {
                                synchronized (TXCGLSurfaceViewBase.a) {
                                    this.j = true;
                                    this.f = true;
                                    TXCGLSurfaceViewBase.a.notifyAll();
                                }
                                continue;
                            }
                            synchronized (TXCGLSurfaceViewBase.a) {
                                this.j = true;
                                TXCGLSurfaceViewBase.a.notifyAll();
                            }
                            n2 = 0;
                        }
                        if (n3 != 0) {
                            gl10 = (GL10)this.s.e();
                            TXCGLSurfaceViewBase.a.a(gl10);
                            n3 = 0;
                        }
                        if (n != 0) {
                            final TXCGLSurfaceViewBase txcglSurfaceViewBase2 = this.t.get();
                            if (txcglSurfaceViewBase2 != null) {
                                txcglSurfaceViewBase2.h.onSurfaceCreated(gl10, this.s.d);
                            }
                            n = 0;
                        }
                        if (n5 != 0) {
                            final TXCGLSurfaceViewBase txcglSurfaceViewBase3 = this.t.get();
                            if (txcglSurfaceViewBase3 != null) {
                                txcglSurfaceViewBase3.h.onSurfaceChanged(gl10, l, m);
                            }
                            n5 = 0;
                        }
                        int c2 = 12288;
                        final TXCGLSurfaceViewBase txcglSurfaceViewBase4 = this.t.get();
                        if (txcglSurfaceViewBase4 != null) {
                            txcglSurfaceViewBase4.h.onDrawFrame(gl10);
                            c2 = txcglSurfaceViewBase4.c();
                        }
                        switch (c2) {
                            case 12288: {
                                break;
                            }
                            case 12302: {
                                n4 = 1;
                                break;
                            }
                            default: {
                                TXCGLSurfaceViewBase.h.a("GLThread", "eglSwapBuffers", c2);
                                synchronized (TXCGLSurfaceViewBase.a) {
                                    this.f = true;
                                    TXCGLSurfaceViewBase.a.notifyAll();
                                }
                                break;
                            }
                        }
                        if (!b) {
                            continue;
                        }
                        n6 = 1;
                    }
                }
            }
            finally {
                synchronized (TXCGLSurfaceViewBase.a) {
                    this.k();
                    this.l();
                }
            }
        }
        
        private void k() {
            if (this.i) {
                this.i = false;
                this.s.g();
            }
        }
        
        private void l() {
            if (this.h) {
                this.s.h();
                this.h = false;
                final TXCGLSurfaceViewBase txcglSurfaceViewBase = this.t.get();
                if (txcglSurfaceViewBase != null) {
                    txcglSurfaceViewBase.f = false;
                }
                TXCGLSurfaceViewBase.a.c(this);
            }
        }
        
        public boolean d() {
            return this.h && this.i && this.m();
        }
        
        private boolean m() {
            return !this.d && this.e && !this.f && this.l > 0 && this.m > 0 && (this.o || this.n == 1);
        }
        
        public void a(final int n) {
            if (0 > n || n > 1) {
                throw new IllegalArgumentException("renderMode");
            }
            synchronized (TXCGLSurfaceViewBase.a) {
                this.n = n;
                TXCGLSurfaceViewBase.a.notifyAll();
            }
        }
        
        public int e() {
            synchronized (TXCGLSurfaceViewBase.a) {
                return this.n;
            }
        }
        
        public void f() {
            synchronized (TXCGLSurfaceViewBase.a) {
                this.e = true;
                this.j = false;
                TXCGLSurfaceViewBase.a.notifyAll();
                while (this.g && !this.j && !this.b) {
                    try {
                        TXCGLSurfaceViewBase.a.wait();
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        public void g() {
            synchronized (TXCGLSurfaceViewBase.a) {
                this.e = false;
                TXCGLSurfaceViewBase.a.notifyAll();
                while (!this.g && !this.b) {
                    try {
                        TXCGLSurfaceViewBase.a.wait();
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        public void a(final int l, final int m) {
            synchronized (TXCGLSurfaceViewBase.a) {
                this.l = l;
                this.m = m;
                this.r = true;
                this.o = true;
                this.p = false;
                TXCGLSurfaceViewBase.a.notifyAll();
                while (!this.b && !this.d && !this.p && this.d()) {
                    try {
                        TXCGLSurfaceViewBase.a.wait();
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        public void h() {
            synchronized (TXCGLSurfaceViewBase.a) {
                this.a = true;
                TXCGLSurfaceViewBase.a.notifyAll();
                while (!this.b) {
                    try {
                        TXCGLSurfaceViewBase.a.wait();
                    }
                    catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        public void i() {
            this.k = true;
            TXCGLSurfaceViewBase.a.notifyAll();
        }
        
        public void a(final Runnable runnable) {
            if (runnable == null) {
                throw new IllegalArgumentException("r must not be null");
            }
            synchronized (TXCGLSurfaceViewBase.a) {
                this.q.add(runnable);
                TXCGLSurfaceViewBase.a.notifyAll();
            }
        }
    }
    
    static class l extends Writer
    {
        private StringBuilder a;
        
        l() {
            this.a = new StringBuilder();
        }
        
        @Override
        public void close() {
            this.a();
        }
        
        @Override
        public void flush() {
            this.a();
        }
        
        @Override
        public void write(final char[] array, final int n, final int n2) {
            for (int i = 0; i < n2; ++i) {
                final char c = array[n + i];
                if (c == '\n') {
                    this.a();
                }
                else {
                    this.a.append(c);
                }
            }
        }
        
        private void a() {
            if (this.a.length() > 0) {
                TXCLog.v("TXCGLSurfaceViewBase", this.a.toString());
                this.a.delete(0, this.a.length());
            }
        }
    }
    
    private static class j
    {
        private static String a;
        private boolean b;
        private int c;
        private boolean d;
        private boolean e;
        private boolean f;
        private i g;
        
        public synchronized void a(final i i) {
            i.b = true;
            if (this.g == i) {
                this.g = null;
            }
            this.notifyAll();
        }
        
        public boolean b(final i g) {
            if (this.g == g || this.g == null) {
                this.g = g;
                this.notifyAll();
                return true;
            }
            this.c();
            if (this.e) {
                return true;
            }
            if (this.g != null) {
                this.g.i();
            }
            return false;
        }
        
        public void c(final i i) {
            if (this.g == i) {
                this.g = null;
            }
            this.notifyAll();
        }
        
        public synchronized boolean a() {
            return this.f;
        }
        
        public synchronized boolean b() {
            this.c();
            return !this.e;
        }
        
        public synchronized void a(final GL10 gl10) {
            if (!this.d) {
                this.c();
                final String glGetString = gl10.glGetString(7937);
                if (this.c < 131072) {
                    this.e = !glGetString.startsWith("Q3Dimension MSM7500 ");
                    this.notifyAll();
                }
                this.f = !this.e;
                this.d = true;
            }
        }
        
        private void c() {
            this.c = 131072;
            this.e = true;
            this.b = true;
        }
        
        static {
            j.a = "GLThreadManager";
        }
    }
    
    public static class h
    {
        private WeakReference<TXCGLSurfaceViewBase> f;
        EGL10 a;
        EGLDisplay b;
        EGLSurface c;
        EGLConfig d;
        EGLContext e;
        
        public h(final WeakReference<TXCGLSurfaceViewBase> f) {
            this.f = f;
        }
        
        public void a() {
            this.a = (EGL10)EGLContext.getEGL();
            this.b = this.a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.b == EGL10.EGL_NO_DISPLAY) {
                throw new RuntimeException("eglGetDisplay failed");
            }
            if (!this.a.eglInitialize(this.b, new int[2])) {
                throw new RuntimeException("eglInitialize failed");
            }
            final TXCGLSurfaceViewBase txcglSurfaceViewBase = this.f.get();
            if (txcglSurfaceViewBase == null) {
                this.d = null;
                this.e = null;
                TXCLog.w("TXCGLSurfaceViewBase", "start() error when view is null ");
            }
            else {
                this.d = txcglSurfaceViewBase.j.a(this.a, this.b);
                this.e = txcglSurfaceViewBase.k.a(this.a, this.b, this.d);
            }
            if (this.e == null || this.e == EGL10.EGL_NO_CONTEXT) {
                this.e = null;
                this.a("createContext");
            }
            if (txcglSurfaceViewBase != null) {
                txcglSurfaceViewBase.f = true;
            }
            this.c = null;
        }
        
        public boolean b() {
            if (this.a == null) {
                throw new RuntimeException("egl not initialized");
            }
            if (this.b == null) {
                throw new RuntimeException("eglDisplay not initialized");
            }
            if (this.d == null) {
                throw new RuntimeException("mEglConfig not initialized");
            }
            this.i();
            final TXCGLSurfaceViewBase txcglSurfaceViewBase = this.f.get();
            if (txcglSurfaceViewBase != null) {
                this.c = txcglSurfaceViewBase.l.a(this.a, this.b, this.d, txcglSurfaceViewBase.getHolder());
            }
            else {
                this.c = null;
            }
            if (this.c == null || this.c == EGL10.EGL_NO_SURFACE) {
                if (this.a.eglGetError() == 12299) {
                    TXCLog.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
                }
                return false;
            }
            if (!this.a.eglMakeCurrent(this.b, this.c, this.c, this.e)) {
                a("EGLHelper", "eglMakeCurrent", this.a.eglGetError());
                return false;
            }
            if (txcglSurfaceViewBase != null) {
                txcglSurfaceViewBase.e = true;
            }
            return true;
        }
        
        public boolean c() {
            if (!this.a.eglMakeCurrent(this.b, this.c, this.c, this.e)) {
                a("EGLHelper", "eglMakeCurrent", this.a.eglGetError());
                return false;
            }
            return true;
        }
        
        public int d() {
            return this.f();
        }
        
        GL e() {
            GL gl = this.e.getGL();
            final TXCGLSurfaceViewBase txcglSurfaceViewBase = this.f.get();
            if (txcglSurfaceViewBase != null) {
                if (txcglSurfaceViewBase.m != null) {
                    gl = txcglSurfaceViewBase.m.a(gl);
                }
                if ((txcglSurfaceViewBase.n & 0x3) != 0x0) {
                    int n = 0;
                    Writer writer = null;
                    if ((txcglSurfaceViewBase.n & 0x1) != 0x0) {
                        n |= 0x1;
                    }
                    if ((txcglSurfaceViewBase.n & 0x2) != 0x0) {
                        writer = new l();
                    }
                    gl = GLDebugHelper.wrap(gl, n, writer);
                }
            }
            return gl;
        }
        
        public int f() {
            if (!this.a.eglSwapBuffers(this.b, this.c)) {
                return this.a.eglGetError();
            }
            return 12288;
        }
        
        public void g() {
            this.i();
        }
        
        private void i() {
            if (this.c != null && this.c != EGL10.EGL_NO_SURFACE) {
                this.a.eglMakeCurrent(this.b, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                final TXCGLSurfaceViewBase txcglSurfaceViewBase = this.f.get();
                if (txcglSurfaceViewBase != null) {
                    txcglSurfaceViewBase.l.a(this.a, this.b, this.c);
                    txcglSurfaceViewBase.e = false;
                }
                this.c = null;
            }
        }
        
        public void h() {
            if (this.e != null) {
                final TXCGLSurfaceViewBase txcglSurfaceViewBase = this.f.get();
                if (txcglSurfaceViewBase != null) {
                    txcglSurfaceViewBase.k.a(this.a, this.b, this.e);
                }
                this.e = null;
            }
            if (this.b != null) {
                this.a.eglTerminate(this.b);
                this.b = null;
            }
        }
        
        private void a(final String s) {
            a(s, this.a.eglGetError());
        }
        
        public static void a(final String s, final int n) {
            throw new RuntimeException(b(s, n));
        }
        
        public static void a(final String s, final String s2, final int n) {
            TXCLog.w(s, b(s2, n));
        }
        
        public static String b(final String s, final int n) {
            return s + " failed: " + n;
        }
    }
    
    private class b extends a
    {
        private int[] j;
        protected int c;
        protected int d;
        protected int e;
        protected int f;
        protected int g;
        protected int h;
        
        public b(final int c, final int d, final int e, final int f, final int g, final int h) {
            super(new int[] { 12324, c, 12323, d, 12322, e, 12321, f, 12325, g, 12326, h, 12344 });
            this.j = new int[1];
            this.c = c;
            this.d = d;
            this.e = e;
            this.f = f;
            this.g = g;
            this.h = h;
        }
        
        public EGLConfig a(final EGL10 egl10, final EGLDisplay eglDisplay, final EGLConfig[] array) {
            for (final EGLConfig eglConfig : array) {
                final int a = this.a(egl10, eglDisplay, eglConfig, 12325, 0);
                final int a2 = this.a(egl10, eglDisplay, eglConfig, 12326, 0);
                if (a >= this.g && a2 >= this.h) {
                    final int a3 = this.a(egl10, eglDisplay, eglConfig, 12324, 0);
                    final int a4 = this.a(egl10, eglDisplay, eglConfig, 12323, 0);
                    final int a5 = this.a(egl10, eglDisplay, eglConfig, 12322, 0);
                    final int a6 = this.a(egl10, eglDisplay, eglConfig, 12321, 0);
                    if (a3 == this.c && a4 == this.d && a5 == this.e && a6 == this.f) {
                        return eglConfig;
                    }
                }
            }
            return null;
        }
        
        private int a(final EGL10 egl10, final EGLDisplay eglDisplay, final EGLConfig eglConfig, final int n, final int n2) {
            if (egl10.eglGetConfigAttrib(eglDisplay, eglConfig, n, this.j)) {
                return this.j[0];
            }
            return n2;
        }
    }
    
    private class m extends b
    {
        public m(final boolean b) {
            super(8, 8, 8, 0, b ? 16 : 0, 0);
        }
    }
    
    private class c implements f
    {
        private int b;
        
        private c() {
            this.b = 12440;
        }
        
        @Override
        public EGLContext a(final EGL10 egl10, final EGLDisplay eglDisplay, final EGLConfig eglConfig) {
            final int[] array = { this.b, TXCGLSurfaceViewBase.this.o, 12344 };
            return egl10.eglCreateContext(eglDisplay, eglConfig, EGL10.EGL_NO_CONTEXT, (int[])((TXCGLSurfaceViewBase.this.o != 0) ? array : null));
        }
        
        @Override
        public void a(final EGL10 egl10, final EGLDisplay eglDisplay, final EGLContext eglContext) {
            if (!egl10.eglDestroyContext(eglDisplay, eglContext)) {
                TXCLog.e("DefaultContextFactory", "display:" + eglDisplay + " context: " + eglContext);
                TXCGLSurfaceViewBase.h.a("eglDestroyContex", egl10.eglGetError());
            }
        }
    }
    
    private static class d implements g
    {
        @Override
        public EGLSurface a(final EGL10 egl10, final EGLDisplay eglDisplay, final EGLConfig eglConfig, final Object o) {
            EGLSurface eglCreateWindowSurface = null;
            try {
                eglCreateWindowSurface = egl10.eglCreateWindowSurface(eglDisplay, eglConfig, o, (int[])null);
            }
            catch (IllegalArgumentException ex) {
                TXCLog.e("TXCGLSurfaceViewBase", "eglCreateWindowSurface");
                TXCLog.e("TXCGLSurfaceViewBase", ex.toString());
            }
            return eglCreateWindowSurface;
        }
        
        @Override
        public void a(final EGL10 egl10, final EGLDisplay eglDisplay, final EGLSurface eglSurface) {
            egl10.eglDestroySurface(eglDisplay, eglSurface);
        }
    }
    
    private abstract class a implements e
    {
        protected int[] a;
        
        public a(final int[] array) {
            this.a = this.a(array);
        }
        
        @Override
        public EGLConfig a(final EGL10 egl10, final EGLDisplay eglDisplay) {
            final int[] array = { 0 };
            if (!egl10.eglChooseConfig(eglDisplay, this.a, (EGLConfig[])null, 0, array)) {
                throw new IllegalArgumentException("eglChooseConfig failed");
            }
            final int n = array[0];
            if (n <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            final EGLConfig[] array2 = new EGLConfig[n];
            if (!egl10.eglChooseConfig(eglDisplay, this.a, array2, n, array)) {
                throw new IllegalArgumentException("eglChooseConfig#2 failed");
            }
            final EGLConfig a = this.a(egl10, eglDisplay, array2);
            if (a == null) {
                throw new IllegalArgumentException("No config chosen");
            }
            return a;
        }
        
        abstract EGLConfig a(final EGL10 p0, final EGLDisplay p1, final EGLConfig[] p2);
        
        private int[] a(final int[] array) {
            if (TXCGLSurfaceViewBase.this.o != 2) {
                return array;
            }
            final int length = array.length;
            final int[] array2 = new int[length + 2];
            System.arraycopy(array, 0, array2, 0, length - 1);
            array2[length - 1] = 12352;
            array2[length] = 4;
            array2[length + 1] = 12344;
            return array2;
        }
    }
    
    public interface e
    {
        EGLConfig a(final EGL10 p0, final EGLDisplay p1);
    }
    
    public interface g
    {
        EGLSurface a(final EGL10 p0, final EGLDisplay p1, final EGLConfig p2, final Object p3);
        
        void a(final EGL10 p0, final EGLDisplay p1, final EGLSurface p2);
    }
    
    public interface f
    {
        EGLContext a(final EGL10 p0, final EGLDisplay p1, final EGLConfig p2);
        
        void a(final EGL10 p0, final EGLDisplay p1, final EGLContext p2);
    }
    
    public interface k
    {
        GL a(final GL p0);
    }
}
