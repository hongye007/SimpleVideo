package com.tencent.liteav.videoediter.ffmpeg;

import com.tencent.liteav.videoediter.ffmpeg.jni.*;
import android.os.*;
import com.tencent.liteav.basic.log.*;
import android.text.*;
import java.io.*;
import java.util.*;

public class b implements TXFFQuickJointerJNI.a
{
    private volatile boolean a;
    private volatile boolean b;
    private volatile boolean c;
    private volatile boolean d;
    private TXFFQuickJointerJNI e;
    private Handler f;
    private Handler g;
    private HandlerThread h;
    private String i;
    private volatile a j;
    
    public b(final String s) {
        (this.e = new TXFFQuickJointerJNI()).setOnJoinerCallback(this);
        this.f = new Handler(Looper.getMainLooper());
        this.d = false;
        this.i = s + "JoinBg";
    }
    
    private void g() {
        if (this.h == null || !this.h.isAlive() || this.h.isInterrupted()) {
            (this.h = new HandlerThread(this.i)).start();
            this.g = new Handler(this.h.getLooper());
        }
    }
    
    private void h() {
        if (this.h != null) {
            this.g.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    com.tencent.liteav.videoediter.ffmpeg.b.this.h.quit();
                    com.tencent.liteav.videoediter.ffmpeg.b.this.h = null;
                    com.tencent.liteav.videoediter.ffmpeg.b.this.g.removeCallbacksAndMessages((Object)null);
                    com.tencent.liteav.videoediter.ffmpeg.b.this.g = null;
                }
            });
        }
    }
    
    public int a(final String dstPath) {
        if (this.c) {
            TXCLog.e("TXQuickJoiner", "quick jointer is started, you must stop first!");
            return -1;
        }
        if (TextUtils.isEmpty((CharSequence)dstPath)) {
            TXCLog.e("TXQuickJoiner", "quick jointer setDstPath empty\uff01\uff01\uff01");
            return -1;
        }
        final File file = new File(dstPath);
        if (file.exists()) {
            file.delete();
        }
        try {
            if (!file.createNewFile()) {
                this.a = false;
                return -1;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
            this.a = false;
            return -1;
        }
        this.e.setDstPath(dstPath);
        this.a = !TextUtils.isEmpty((CharSequence)dstPath);
        return 0;
    }
    
    public boolean a() {
        return this.e.verify() == 0;
    }
    
    public int a(final List<String> srcPaths) {
        if (this.c) {
            TXCLog.e("TXQuickJoiner", "qucik jointer is started, you must stop frist!");
            return -1;
        }
        this.e.setSrcPaths(srcPaths);
        this.b = true;
        return 0;
    }
    
    public int b() {
        if (!this.b || !this.a) {
            return -1;
        }
        if (this.c) {
            TXCLog.e("TXQuickJoiner", "qucik jointer is started, you must stop frist!");
            return -1;
        }
        this.g();
        this.g.post((Runnable)new Runnable() {
            @Override
            public void run() {
                com.tencent.liteav.videoediter.ffmpeg.b.this.c = true;
                if (com.tencent.liteav.videoediter.ffmpeg.b.this.e.verify() != 0) {
                    com.tencent.liteav.videoediter.ffmpeg.b.this.a(-1, "Not in compliance with fast composition requirements");
                    return;
                }
                final int start = com.tencent.liteav.videoediter.ffmpeg.b.this.e.start();
                if (com.tencent.liteav.videoediter.ffmpeg.b.this.c) {
                    if (start < 0) {
                        com.tencent.liteav.videoediter.ffmpeg.b.this.a(-2, "Composition failed");
                    }
                    else {
                        com.tencent.liteav.videoediter.ffmpeg.b.this.a(0, "Composition succeeded");
                    }
                    com.tencent.liteav.videoediter.ffmpeg.b.this.c = false;
                }
                else {
                    com.tencent.liteav.videoediter.ffmpeg.b.this.a(1, "Cancel composition");
                }
            }
        });
        return 0;
    }
    
    public int c() {
        if (this.c) {
            this.e.stop();
            this.h();
            this.c = false;
            return 0;
        }
        return -1;
    }
    
    public void d() {
        if (!this.d) {
            this.c();
            this.e.setOnJoinerCallback(null);
            this.e.destroy();
            this.j = null;
            this.e = null;
            this.d = true;
        }
    }
    
    public int e() {
        if (this.e != null) {
            return this.e.getVideoWidth();
        }
        return 0;
    }
    
    public int f() {
        if (this.e != null) {
            return this.e.getVideoHeight();
        }
        return 0;
    }
    
    private void a(final int n, final String s) {
        if (this.j != null) {
            this.f.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.videoediter.ffmpeg.b.this.j != null) {
                        com.tencent.liteav.videoediter.ffmpeg.b.this.j.a(com.tencent.liteav.videoediter.ffmpeg.b.this, n, s);
                    }
                }
            });
        }
    }
    
    public void a(final a j) {
        this.j = j;
    }
    
    @Override
    public void a(final float n) {
        if (this.j != null) {
            this.f.post((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (com.tencent.liteav.videoediter.ffmpeg.b.this.j != null) {
                        com.tencent.liteav.videoediter.ffmpeg.b.this.j.a(com.tencent.liteav.videoediter.ffmpeg.b.this, n);
                    }
                }
            });
        }
    }
    
    public interface a
    {
        void a(final b p0, final int p1, final String p2);
        
        void a(final b p0, final float p1);
    }
}
