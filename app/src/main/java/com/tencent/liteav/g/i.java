package com.tencent.liteav.g;

import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.editer.*;
import android.media.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;

public class i
{
    public String a;
    private e c;
    private af d;
    private f e;
    private boolean f;
    private boolean g;
    private boolean h;
    private boolean i;
    private MediaFormat j;
    private MediaFormat k;
    public k b;
    
    public void a(final String a) {
        this.a = a;
    }
    
    public void a() {
        if (this.c != null) {
            this.c.o();
        }
    }
    
    public int b() {
        TXCLog.i("VideoExtractConfig", "createMediaExtractor videoSourcePath:" + this.a);
        this.c = new e();
        return this.c.a(this.a);
    }
    
    public void c() {
        TXCLog.i("VideoExtractConfig", "resetVideoMediaExtractor videoSourcePath:" + this.a);
        if (this.c != null) {
            this.c.a(0L);
        }
    }
    
    public void d() {
        TXCLog.i("VideoExtractConfig", "resetAudioMediaExtractor videoSourcePath:" + this.a);
        if (this.c != null) {
            this.c.c(0L);
        }
    }
    
    public MediaFormat e() {
        return (this.j == null) ? this.c.l() : this.j;
    }
    
    public MediaFormat f() {
        return (this.k == null) ? this.c.m() : this.k;
    }
    
    public int g() {
        return this.c.g();
    }
    
    public long h() {
        if (Build.VERSION.SDK_INT >= 16) {
            final MediaFormat e = this.e();
            if (e != null) {
                return e.getLong("durationUs");
            }
        }
        return 0L;
    }
    
    public long i() {
        if (Build.VERSION.SDK_INT >= 16) {
            final MediaFormat f = this.f();
            if (f != null) {
                return f.getLong("durationUs");
            }
        }
        return 0L;
    }
    
    public long j() {
        if (this.f() == null) {
            TXCLog.i("VideoExtractConfig", "getAudioFormat is null");
            return this.h();
        }
        if (this.e() != null) {
            final long h = this.h();
            final long i = this.i();
            TXCLog.i("VideoExtractConfig", "getDuration vd:" + h + ",ad:" + i);
            return (h > i) ? h : i;
        }
        TXCLog.i("VideoExtractConfig", "getVideoFormat is null");
        return 0L;
    }
    
    public void k() {
        TXCLog.i("VideoExtractConfig", "createVideoDecoder videoSourcePath1111:" + this.a);
        if (this.b.c == null) {
            TXCLog.e("VideoExtractConfig", "createVideoDecoder videoGLTextureInfo.surface is null");
            return;
        }
        this.d = new af();
        this.j = this.c.l();
        this.d.a(this.j);
        this.d.a(this.c.l(), this.b.c);
        this.d.a();
        this.f = false;
        this.h = false;
    }
    
    public void l() {
        TXCLog.i("VideoExtractConfig", "destroyVideoDecoder videoSourcePath:" + this.a);
        if (this.d != null) {
            this.d.b();
            this.d = null;
        }
    }
    
    public void m() {
        TXCLog.i("VideoExtractConfig", "createAudioDecoder videoSourcePath:" + this.a);
        this.e = new f();
        this.k = this.c.m();
        this.e.a(this.k);
        this.e.a(this.k, null);
        this.e.a();
        if (this.k == null) {
            this.g = true;
            this.i = true;
        }
        else {
            this.g = false;
            this.i = false;
        }
    }
    
    public void n() {
        TXCLog.i("VideoExtractConfig", "destroyAudioDecoder videoSourcePath:" + this.a);
        if (this.e != null) {
            this.e.b();
            this.e = null;
        }
    }
    
    public boolean o() {
        return this.h;
    }
    
    public boolean p() {
        return this.i;
    }
    
    public void q() {
        if (this.f) {
            TXCLog.i("VideoExtractConfig", "readVideoFrame source:" + this.a + " readEOF!");
            return;
        }
        if (this.d == null) {
            return;
        }
        final CaptureAndEnc.e c = this.d.c();
        if (c == null) {
            return;
        }
        final CaptureAndEnc.e a = this.c.a(c);
        if (a != null) {
            if (this.c.c(a)) {
                this.f = true;
                TXCLog.i("VideoExtractConfig", "readVideoFrame source:" + this.a + " readEOF!");
            }
            this.d.a(a);
        }
    }
    
    public void r() {
        if (this.g) {
            TXCLog.i("VideoExtractConfig", "readAudioFrame source:" + this.a + " readEOF!");
            return;
        }
        if (this.e == null) {
            return;
        }
        final CaptureAndEnc.e c = this.e.c();
        if (c == null) {
            return;
        }
        final CaptureAndEnc.e b = this.c.b(c);
        if (b != null) {
            if (this.c.d(b)) {
                this.g = true;
                TXCLog.i("VideoExtractConfig", "readAudioFrame source:" + this.a + " readEOF!");
            }
            this.e.a(b);
        }
    }
    
    public CaptureAndEnc.e s() {
        if (this.d == null) {
            return null;
        }
        final CaptureAndEnc.e d = this.d.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        this.a(d);
        if (d.p()) {
            TXCLog.i("VideoExtractConfig", "getDecodeVideoFrame frame.isEndFrame");
            this.h = true;
        }
        return d;
    }
    
    public CaptureAndEnc.e t() {
        if (this.e == null) {
            return null;
        }
        final CaptureAndEnc.e d = this.e.d();
        if (d == null) {
            return null;
        }
        if (d.o() == null) {
            return null;
        }
        this.b(d);
        if (d.p()) {
            TXCLog.i("VideoExtractConfig", "getDecodeAudioFrame frame.isEndFrame");
            this.i = true;
        }
        return d;
    }
    
    private void a(final CaptureAndEnc.e e) {
        if (this.c != null && e != null) {
            e.j(this.c.b());
            e.k(this.c.c());
            e.e(this.c.f());
            e.f(this.c.e());
        }
    }
    
    private void b(final CaptureAndEnc.e e) {
        if (this.c != null && e != null) {
            e.g(this.c.h());
            e.h(this.c.i());
        }
    }
}
