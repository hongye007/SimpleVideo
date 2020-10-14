package com.tencent.liteav.videoediter.a;

import android.os.*;
import java.util.*;
import java.io.*;
import java.nio.*;

public class c implements Runnable
{
    private a a;
    private Handler b;
    private List<String> c;
    private String d;
    private long e;
    private long f;
    private boolean g;
    private long h;
    private Runnable i;
    
    @Override
    public void run() {
        if (!this.g) {
            return;
        }
        if (this.c == null || this.c.size() <= 0) {
            this.a(-1, "Video source not set");
            return;
        }
        if (this.d == null || this.d.isEmpty()) {
            this.a(-1, "Output path not set");
            return;
        }
        final d d = new d();
        final com.tencent.liteav.videoediter.a.a a = new com.tencent.liteav.videoediter.a.a();
        try {
            d.a(this.c);
            a.a(this.d);
            final long c = d.c();
            d.f();
            final b b = new b();
            try {
                b.a(this.c.get(0));
            }
            catch (IOException ex) {
                ex.printStackTrace();
                this.a(-1, "Failed to get data format");
                return;
            }
            if (b.a() != null) {
                a.a(b.a());
            }
            if (b.b() != null) {
                a.b(b.b());
            }
            b.e();
            final int c2 = a.c();
            if (c2 < 0) {
                String s = null;
                switch (c2) {
                    case -4: {
                        s = "Failed to start muxer";
                        break;
                    }
                    case -5: {
                        s = "Unsupported video format";
                        break;
                    }
                    case -6: {
                        s = "muxer add videotrack error";
                        break;
                    }
                    case -7: {
                        s = "Unsupported audio format";
                        break;
                    }
                    case -8: {
                        s = "muxer add audiotrack error";
                        break;
                    }
                    default: {
                        s = "Failed to stop muxer";
                        break;
                    }
                }
                this.a(-1, s);
                return;
            }
            d.a(this.e);
            final e e = new e();
            e.a(ByteBuffer.allocate(512000));
            do {
                d.a(e);
                if ((e.f() & 0x4) == 0x0) {
                    if (this.f > 0L && e.e() > this.f) {
                        break;
                    }
                    if (e.a().startsWith("video")) {
                        a.a(e.b(), e.o());
                        ++this.h;
                        if (this.h < 50L) {
                            continue;
                        }
                        this.a(e.o().presentationTimeUs - this.e, (this.f < 0L) ? c : (this.f - this.e));
                        this.h = 0L;
                    }
                    else {
                        a.b(e.b(), e.o());
                    }
                }
            } while (this.g && (e.f() & 0x4) == 0x0);
            if (a.d() < 0) {
                this.a(-1, "Failed to stop muxer");
            }
            else if (this.g) {
                this.a(0, "");
            }
        }
        finally {
            d.e();
            this.g = false;
        }
    }
    
    private void a(final long n, final long n2) {
        if (this.i != null || this.a == null) {
            return;
        }
        this.i = new Runnable() {
            final /* synthetic */ float a = (n2 > 0L && n <= n2) ? (1.0f * n / n2) : 1.0f;
            
            @Override
            public void run() {
                if (com.tencent.liteav.videoediter.a.c.this.a != null) {
                    com.tencent.liteav.videoediter.a.c.this.a.a(this.a);
                }
                com.tencent.liteav.videoediter.a.c.this.i = null;
            }
        };
        this.b.post(this.i);
    }
    
    private void a(final int n, final String s) {
        this.b.post((Runnable)new Runnable() {
            @Override
            public void run() {
                if (com.tencent.liteav.videoediter.a.c.this.a != null) {
                    com.tencent.liteav.videoediter.a.c.this.a.a(n, s);
                }
            }
        });
    }
    
    public interface a
    {
        void a(final int p0, final String p1);
        
        void a(final float p0);
    }
}
