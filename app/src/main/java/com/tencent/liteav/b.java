package com.tencent.liteav;

import com.tencent.liteav.basic.structs.*;

import java.lang.ref.*;
import com.tencent.liteav.basic.log.*;
import com.tencent.liteav.basic.util.*;
import android.media.*;
import android.graphics.drawable.*;
import android.graphics.*;
import java.nio.*;
import android.os.*;

public class b implements f, CaptureAndEnc
{
    private static final String a;
    private int b;
    private long c;
    private a d;
    private HandlerThread e;
    private boolean f;
    private b g;
    private TXSNALPacket h;
    private e i;
    private ByteBuffer j;
    private Bitmap k;
    private int l;
    private int m;
    private WeakReference<b> n;
    
    @Override
    public int willAddWatermark(final int n, final int n2, final int n3) {
        return 0;
    }
    
    @Override
    public void didProcessFrame(final int n, final int n2, final int n3, final long n4) {
        TXCLog.w(com.tencent.liteav.b.a, "bkgpush: got texture");
        if (this.g != null) {
            this.g.a(n, n2, n3, TXCTimeUtil.generatePtsMS());
        }
    }
    
    @Override
    public void didProcessFrame(final byte[] array, final int n, final int n2, final int n3, final long n4) {
    }
    
    @Override
    public void onEncodeNAL(final TXSNALPacket h, final int n) {
        this.h = h;
        TXCLog.w(com.tencent.liteav.b.a, "bkgpush: got nal type: " + ((h != null) ? Integer.valueOf(h.nalType) : h));
        if (this.g != null) {
            this.g.a((CaptureAndEnc)null);
            final b g = this.g;
            try {
                if (this.n != null) {
                    final b b = this.n.get();
                    if (b != null) {
                        b.a(g);
                    }
                }
            }
            catch (Exception ex) {
                TXCLog.e(com.tencent.liteav.b.a, "onReleaseEncoder failed.", ex);
            }
        }
    }
    
    @Override
    public void onEncodeFormat(final MediaFormat mediaFormat) {
    }
    
    @Override
    public void onEncodeFinished(final int n, final long n2, final long n3) {
    }
    
    @Override
    public void onRestartEncoder(final int n) {
    }
    
    @Override
    public void onEncodeDataIn(final int n) {
    }
    
    public b(final b b) {
        this.b = 300;
        this.c = 0L;
        this.f = false;
        this.i = null;
        this.j = null;
        this.k = null;
        this.l = 0;
        this.m = 0;
        this.n = null;
        this.n = new WeakReference<b>(b);
    }
    
    public void a(final int n, final int n2) {
        if (this.f) {
            TXCLog.w(com.tencent.liteav.b.a, "bkgpush: start background publish return when started");
            return;
        }
        this.f = true;
        this.b(n, n2);
        this.d();
        if (this.d != null) {
            this.d.sendEmptyMessageDelayed(1001, (long)this.b);
        }
        TXCLog.w(com.tencent.liteav.b.a, "bkgpush: start background publish with time:" + (this.c - System.currentTimeMillis()) / 1000L + ", interval:" + this.b);
    }
    
    public void a(final int n, final int n2, final Bitmap bitmap, final int l, final int m) {
        if (this.f) {
            TXCLog.w(com.tencent.liteav.b.a, "bkgpush: start background publish return when started");
            return;
        }
        try {
            Bitmap bitmap2 = bitmap;
            if (bitmap2 == null) {
                TXCLog.w(com.tencent.liteav.b.a, "bkgpush: background publish img is empty, add default img " + l + "*" + m);
                final ColorDrawable colorDrawable = new ColorDrawable(-16777216);
                bitmap2 = Bitmap.createBitmap(l, m, Bitmap.Config.ARGB_8888);
                colorDrawable.draw(new Canvas(bitmap2));
            }
            TXCLog.w(com.tencent.liteav.b.a, "bkgpush: generate bitmap " + l + "*" + m);
            this.k = bitmap2;
        }
        catch (Exception ex) {
            TXCLog.e(com.tencent.liteav.b.a, "save bitmap failed.", ex);
        }
        catch (Error error) {
            TXCLog.e(com.tencent.liteav.b.a, "save bitmap failed.", error);
        }
        this.l = l;
        this.m = m;
        this.a(n, n2);
    }
    
    public boolean a() {
        return this.f;
    }
    
    public void b() {
        this.f = false;
        this.j = null;
        this.k = null;
        TXCLog.w(com.tencent.liteav.b.a, "bkgpush: stop background publish");
        this.startWithoutAudio();
    }
    
    private void b(int n, final int n2) {
        if (n > 0) {
            if (n >= 8) {
                n = 8;
            }
            else if (n <= 3) {
                n = 3;
            }
            this.b = 1000 / n;
        }
        else {
            this.b = 200;
        }
        final long n3 = n2;
        if (n2 > 0) {
            this.c = System.currentTimeMillis() + n3 * 1000L;
        }
        else if (n2 == 0) {
            this.c = System.currentTimeMillis() + 300000L;
        }
        else {
            this.c = -1L;
        }
    }
    
    private void d() {
        this.startWithoutAudio();
        (this.e = new HandlerThread("TXImageCapturer")).start();
        this.d = new a(this.e.getLooper(), this.b, this.c);
    }
    
    private void e() {
        if (this.d != null) {
            this.d.removeCallbacksAndMessages((Object)null);
            this.d = null;
        }
        if (this.e != null) {
            this.e.quit();
            this.e = null;
        }
    }
    
    private void f() {
        int width = 0;
        int height = 0;
        try {
            if (this.n != null && this.f) {
                final b b = this.n.get();
                if (b != null) {
                    final Bitmap k = this.k;
                    ByteBuffer j = this.j;
                    if (j == null && k != null) {
                        width = k.getWidth();
                        height = k.getHeight();
                        j = ByteBuffer.allocateDirect(width * height * 4);
                        k.copyPixelsToBuffer((Buffer)j);
                        j.rewind();
                        this.j = j;
                    }
                    if (k != null && j != null) {
                        b.a(k, j, this.l, this.m);
                    }
                }
            }
        }
        catch (Exception ex) {
            TXCLog.w(com.tencent.liteav.b.a, "bkgpush: generate bitmap pixel exception " + width + "*" + height);
        }
        catch (Error error) {
            TXCLog.w(com.tencent.liteav.b.a, "bkgpush: generate bitmap pixel error " + width + "*" + height);
        }
    }
    
    static {
        a = b.class.getSimpleName();
    }
    
    private class a extends Handler
    {
        private int b;
        private long c;
        
        public a(final Looper looper, final int b, final long c) {
            super(looper);
            this.b = 300;
            this.c = 0L;
            this.b = b;
            this.c = c;
            TXCLog.w(com.tencent.liteav.b.a, "bkgpush:init publish time delay:" + this.b + ", end:" + this.c);
        }
        
        public void handleMessage(final Message message) {
            if (message.what == 1001) {
                try {
                    com.tencent.liteav.b.this.stopPush();
                    if (this.c < 0L || System.currentTimeMillis() < this.c) {
                        this.sendEmptyMessageDelayed(1001, (long)this.b);
                    }
                    else {
                        TXCLog.w(com.tencent.liteav.b.a, "bkgpush:stop background publish when timeout");
                        if (com.tencent.liteav.b.this.n != null && com.tencent.liteav.b.this.f) {
                            final b b = (b)com.tencent.liteav.b.this.n.get();
                            if (b != null) {
                                b.a();
                            }
                            com.tencent.liteav.b.this.f = false;
                        }
                    }
                }
                catch (Exception ex) {
                    TXCLog.e(com.tencent.liteav.b.a, "publish image failed." + ex.getMessage());
                }
            }
        }
    }
    
    public interface b
    {
        void a(final Bitmap p0, final ByteBuffer p1, final int p2, final int p3);
        
        void a(final com.tencent.liteav.videoencoder.b p0);
        
        void a();
    }
}
