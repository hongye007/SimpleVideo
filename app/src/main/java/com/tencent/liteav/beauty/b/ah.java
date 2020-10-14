package com.tencent.liteav.beauty.b;

import android.graphics.*;
import android.content.res.*;
import android.media.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;

public class ah
{
    private static final String b;
    private SurfaceTexture c;
    private int d;
    private boolean e;
    private MediaExtractor f;
    private AssetFileDescriptor g;
    SurfaceTexture.OnFrameAvailableListener a;
    private int h;
    private int i;
    private int j;
    private int k;
    private long l;
    private MediaCodec m;
    private boolean n;
    private boolean o;
    private Handler p;
    private Object q;
    
    ah() {
        this.d = -1;
        this.e = false;
        this.h = -1;
        this.i = -1;
        this.j = -1;
        this.k = -1;
        this.n = false;
        this.q = new Object();
    }
    
    synchronized void a() {
        synchronized (this.q) {
            if (this.p != null) {
                if (Looper.myLooper() == this.p.getLooper()) {
                    this.c();
                }
                else {
                    final Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            synchronized (ah.this.q) {
                                ah.this.c();
                                ah.this.q.notify();
                            }
                        }
                    };
                    this.p.removeCallbacksAndMessages((Object)null);
                    this.p.post((Runnable)runnable);
                    this.p.getLooper().quitSafely();
                    while (true) {
                        try {
                            this.q.wait();
                        }
                        catch (InterruptedException ex) {
                            continue;
                        }
                        break;
                    }
                }
            }
        }
    }
    
    private void b() {
        if (this.e) {
            this.e = false;
            if (this.f != null) {
                this.f.release();
                this.f = null;
            }
            try {
                this.m.stop();
            }
            catch (Exception ex) {
                TXCLog.e(ah.b, "stop decoder Exception: " + ex.toString());
                try {
                    this.m.release();
                }
                catch (Exception ex2) {
                    TXCLog.e(ah.b, "release decoder exception: " + ex2.toString());
                }
                finally {
                    this.m = null;
                }
            }
            finally {
                try {
                    this.m.release();
                }
                catch (Exception ex3) {
                    TXCLog.e(ah.b, "release decoder exception: " + ex3.toString());
                    this.m = null;
                }
                finally {
                    this.m = null;
                }
            }
        }
    }
    
    private void c() {
        this.b();
        this.a = null;
        this.l = 0L;
        this.o = false;
        if (this.c != null) {
            this.c.release();
            this.c = null;
        }
        synchronized (this.q) {
            if (this.p != null) {
                this.p.removeCallbacksAndMessages((Object)null);
                this.p.getLooper().quit();
                this.p = null;
                this.q.notify();
            }
        }
        if (this.g != null) {
            try {
                this.g.close();
            }
            catch (Exception ex) {}
            this.g = null;
        }
    }
    
    static {
        b = ah.class.getSimpleName();
    }
}
