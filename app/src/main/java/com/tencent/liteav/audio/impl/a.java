package com.tencent.liteav.audio.impl;

import java.util.concurrent.*;
import java.lang.ref.*;
import android.content.*;
import java.util.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import android.telephony.*;

public class a
{
    private static final a a;
    private ConcurrentHashMap<Integer, WeakReference<b>> b;
    private PhoneStateListener c;
    private Context d;
    
    public static a a() {
        return com.tencent.liteav.audio.impl.a.a;
    }
    
    private a() {
        this.b = new ConcurrentHashMap<Integer, WeakReference<b>>();
        this.c = null;
    }
    
    public synchronized void a(final b b) {
        if (b == null) {
            return;
        }
        this.b.put(b.hashCode(), new WeakReference<b>(b));
    }
    
    private synchronized void a(final int n) {
        final Iterator<Map.Entry<Integer, WeakReference<b>>> iterator = this.b.entrySet().iterator();
        while (iterator.hasNext()) {
            final b b = iterator.next().getValue().get();
            if (b != null) {
                b.onCallStateChanged(n);
            }
            else {
                iterator.remove();
            }
        }
    }
    
    public void a(final Context context) {
        if (this.c != null) {
            return;
        }
        this.d = context.getApplicationContext();
        new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.audio.impl.a.this.c != null) {
                    return;
                }
                com.tencent.liteav.audio.impl.a.this.c = new PhoneStateListener() {
                    public void onCallStateChanged(final int n, final String s) {
                        super.onCallStateChanged(n, s);
                        TXCLog.i("AudioCenter:TXCTelephonyMgr", "onCallStateChanged:" + n);
                        com.tencent.liteav.audio.impl.a.this.a(n);
                    }
                };
                ((TelephonyManager)com.tencent.liteav.audio.impl.a.this.d.getSystemService("phone")).listen(com.tencent.liteav.audio.impl.a.this.c, 32);
            }
        });
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        if (this.c != null && this.d != null) {
            new Handler(Looper.getMainLooper()).post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.audio.impl.a.this.c != null && com.tencent.liteav.audio.impl.a.this.d != null) {
                        ((TelephonyManager)com.tencent.liteav.audio.impl.a.this.d.getApplicationContext().getSystemService("phone")).listen(com.tencent.liteav.audio.impl.a.this.c, 0);
                    }
                    com.tencent.liteav.audio.impl.a.this.c = null;
                }
            });
        }
    }
    
    static {
        a = new a();
    }
}
