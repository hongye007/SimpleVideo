package com.tencent.liteav.basic.c;

import com.tencent.liteav.basic.util.*;
import android.view.*;
import android.opengl.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import javax.microedition.khronos.egl.*;

public class g extends e
{
    public int a;
    public int b;
    public Surface c;
    private a i;
    public boolean d;
    public c e;
    public EGLContext f;
    public b g;
    public javax.microedition.khronos.egl.EGLContext h;
    
    public static void a(final Handler handler, final HandlerThread handlerThread) {
        if (handler == null || handlerThread == null) {
            return;
        }
        final Message message = new Message();
        message.what = 101;
        message.obj = new Runnable() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (handler != null) {
                            handler.removeCallbacksAndMessages((Object)null);
                        }
                        if (handlerThread != null) {
                            if (Build.VERSION.SDK_INT >= 18) {
                                handlerThread.quitSafely();
                            }
                            else {
                                handlerThread.quit();
                            }
                        }
                    }
                });
            }
        };
        handler.sendMessage(message);
    }
    
    public g(final Looper looper) {
        super(looper);
        this.a = 720;
        this.b = 1280;
        this.c = null;
        this.i = null;
        this.d = false;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
    }
    
    public void a(final a i) {
        this.i = i;
    }
    
    public javax.microedition.khronos.egl.EGLContext a() {
        return (this.g != null) ? this.g.d() : null;
    }
    
    public Surface b() {
        return this.c;
    }
    
    public void c() {
        if (this.g != null) {
            this.g.a();
        }
        if (this.e != null) {
            this.e.d();
        }
    }
    
    public void d() {
        if (this.g != null) {
            this.g.b();
        }
        if (this.e != null) {
            this.e.b();
        }
    }
    
    public void handleMessage(final Message message) {
        if (message == null) {
            return;
        }
        switch (message.what) {
            case 100: {
                this.a(message);
                break;
            }
            case 102: {
                this.c(message);
                break;
            }
            case 101: {
                this.b(message);
                break;
            }
        }
        if (message != null && message.obj != null) {
            ((Runnable)message.obj).run();
        }
    }
    
    private void a(final Message message) {
        try {
            this.e();
        }
        catch (Exception ex) {
            TXCLog.e("TXGLThreadHandler", "surface-render: init egl context exception " + this.c);
            this.c = null;
        }
    }
    
    private void b(final Message message) {
        this.f();
    }
    
    private void c(final Message message) {
        try {
            if (this.i != null) {
                this.i.d();
            }
        }
        catch (Exception ex) {
            TXCLog.e("TXGLThreadHandler", "onMsgRend Exception " + ex.getMessage());
        }
    }
    
    private boolean e() {
        TXCLog.i("TXGLThreadHandler", String.format("init egl size[%d/%d]", this.a, this.b));
        if (!this.d) {
            this.g = com.tencent.liteav.basic.c.b.a(null, this.h, this.c, this.a, this.b);
        }
        else {
            this.e = com.tencent.liteav.basic.c.c.a(null, this.f, this.c, this.a, this.b);
        }
        if (this.g == null && this.e == null) {
            return false;
        }
        TXCLog.w("TXGLThreadHandler", "surface-render: create egl context " + this.c);
        if (this.i != null) {
            this.i.c();
        }
        return true;
    }
    
    private void f() {
        TXCLog.w("TXGLThreadHandler", "surface-render: destroy egl context " + this.c);
        if (this.i != null) {
            this.i.e();
        }
        if (this.g != null) {
            this.g.c();
            this.g = null;
        }
        if (this.e != null) {
            this.e.c();
            this.e = null;
        }
        this.c = null;
    }
    
    interface a
    {
        void c();
        
        void d();
        
        void e();
    }
}
