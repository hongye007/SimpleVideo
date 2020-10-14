package com.tencent.liteav.screencapture;

import android.annotation.*;
import android.media.projection.*;
import com.tencent.liteav.basic.log.*;
import java.util.*;
import android.os.*;
import com.tencent.liteav.basic.util.*;
import com.tencent.rtmp.video.*;
import android.content.*;
import android.hardware.display.*;
import java.util.concurrent.*;
import android.view.*;

@TargetApi(21)
public class c
{
    private static volatile c a;
    private final Context b;
    private final Handler c;
    private final Map<Surface, a> d;
    private boolean e;
    private MediaProjection f;
    private h g;
    private boolean h;
    private MediaProjection.Callback i;
    private h.a j;
    
    public static c a(final Context context) {
        if (c.a == null) {
            synchronized (c.class) {
                if (c.a == null) {
                    c.a = new c(context);
                }
            }
        }
        return c.a;
    }
    
    public c(final Context context) {
        this.d = new HashMap<Surface, a>();
        this.e = false;
        this.i = new MediaProjection.Callback() {
            public void onStop() {
                TXCLog.e("VirtualDisplayManager", "MediaProjection session is no longer valid");
                final HashMap<Object, a> hashMap = new HashMap<Object, a>(com.tencent.liteav.screencapture.c.this.d);
                com.tencent.liteav.screencapture.c.this.d.clear();
                for (final a a : hashMap.values()) {
                    if (a.d == null) {
                        continue;
                    }
                    if (a.e != null) {
                        a.d.a();
                    }
                    else {
                        a.d.a(false, false);
                    }
                }
                com.tencent.liteav.screencapture.c.this.a(false);
            }
        };
        this.j = new h.a() {
            @Override
            public void a() {
                final boolean a = c.this.b(c.this.b);
                if (c.this.h == a) {
                    return;
                }
                c.this.h = a;
                for (final c.a a2 : c.this.d.values()) {
                    if (a2.d != null) {
                        a2.d.a(a);
                    }
                }
            }
        };
        this.b = context.getApplicationContext();
        this.c = new MainWaitHandler(Looper.getMainLooper());
        this.h = this.b(context);
    }
    
    public void a(final Surface a, final int b, final int c, final b d) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Must call this method in main thread!");
        }
        if (a == null) {
            TXCLog.e("VirtualDisplayManager", "surface is null!");
            d.a(false, false);
            return;
        }
        final a a2 = new a();
        a2.a = a;
        a2.b = b;
        a2.c = c;
        a2.d = d;
        a2.e = null;
        this.d.put(a, a2);
        if (this.f == null) {
            if (!this.e) {
                this.e = true;
                final Intent intent = new Intent(this.b, (Class)TXScreenCapture.TXScreenCaptureAssistantActivity.class);
                intent.addFlags(268435456);
                this.b.startActivity(intent);
            }
        }
        else {
            this.a();
        }
    }
    
    public void a(final Surface surface) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Must call this at main thread!");
        }
        if (surface == null) {
            return;
        }
        final a a = this.d.remove(surface);
        if (a != null && a.e != null) {
            a.e.release();
            TXCLog.i("VirtualDisplayManager", "VirtualDisplay released, " + a.e);
        }
        this.a(true);
    }
    
    private void a() {
        for (final a a : this.d.values()) {
            if (a.e != null) {
                continue;
            }
            a.e = this.f.createVirtualDisplay("TXCScreenCapture", a.b, a.c, 1, 1, a.a, (VirtualDisplay.Callback)null, (Handler)null);
            TXCLog.i("VirtualDisplayManager", "create VirtualDisplay " + a.e);
            if (a.d == null) {
                continue;
            }
            a.d.a(true, false);
        }
    }
    
    private void a(final boolean b) {
        if (!this.d.isEmpty()) {
            return;
        }
        if (b) {
            this.c.postDelayed((Runnable)new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.screencapture.c.this.a(false);
                }
            }, TimeUnit.SECONDS.toMillis(1L));
            return;
        }
        TXCLog.i("VirtualDisplayManager", "stop media projection session " + this.f);
        if (this.f != null) {
            this.f.unregisterCallback(this.i);
            this.f.stop();
            this.f = null;
        }
        if (this.g != null) {
            this.g.a();
            this.g = null;
        }
    }
    
    public void a(final MediaProjection f) {
        this.e = false;
        if (f == null) {
            final HashMap<Object, a> hashMap = new HashMap<Object, a>(this.d);
            this.d.clear();
            for (final a a : hashMap.values()) {
                if (a.d != null) {
                    a.d.a(false, true);
                }
            }
            return;
        }
        TXCLog.i("VirtualDisplayManager", "Got session " + f);
        (this.f = f).registerCallback(this.i, this.c);
        this.a();
        (this.g = new h(Looper.getMainLooper(), this.j)).a(50, 50);
        this.a(true);
    }
    
    private boolean b(final Context context) {
        final WindowManager windowManager = (WindowManager)context.getSystemService("window");
        if (windowManager == null) {
            return true;
        }
        final int rotation = windowManager.getDefaultDisplay().getRotation();
        return rotation == 0 || rotation == 2;
    }
    
    static {
        c.a = null;
    }
    
    private static class a
    {
        public Surface a;
        public int b;
        public int c;
        public b d;
        public VirtualDisplay e;
    }
    
    public interface b
    {
        void a(final boolean p0, final boolean p1);
        
        void a();
        
        void a(final boolean p0);
    }
}
