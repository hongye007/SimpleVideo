package com.tencent.liteav.basic.util;

import android.os.*;

public class g
{
    private Handler a;
    private Looper b;
    private boolean c;
    private Thread d;
    
    public g(final String s) {
        final HandlerThread d = new HandlerThread(s);
        this.c = true;
        d.start();
        this.b = d.getLooper();
        this.a = new Handler(this.b);
        this.d = (Thread)d;
    }
    
    public Handler a() {
        return this.a;
    }
    
    @Override
    protected void finalize() throws Throwable {
        if (this.c) {
            this.a.getLooper().quit();
        }
        super.finalize();
    }
    
    public void a(final Runnable runnable) {
        final boolean[] array = { false };
        if (Thread.currentThread().equals(this.d)) {
            runnable.run();
        }
        else {
            synchronized (this.a) {
                array[0] = false;
                this.a.post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        runnable.run();
                        array[0] = true;
                        synchronized (g.this.a) {
                            g.this.a.notifyAll();
                        }
                    }
                });
                while (!array[0]) {
                    try {
                        this.a.wait();
                    }
                    catch (Exception ex) {}
                }
            }
        }
    }
    
    public void b(final Runnable runnable) {
        this.a.post(runnable);
    }
    
    public void a(final Runnable runnable, final long n) {
        this.a.postDelayed(runnable, n);
    }
}
