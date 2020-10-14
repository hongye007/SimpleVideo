package com.tencent.liteav.g;

import android.annotation.*;
import java.util.concurrent.atomic.*;
import android.media.*;
import com.tencent.liteav.basic.log.*;

import android.view.*;

@TargetApi(16)
public class f implements b
{
    private AtomicBoolean b;
    private b c;
    private static final String[] d;
    private boolean e;
    
    public f() {
        this.b = new AtomicBoolean(false);
    }
    
    private boolean b(final MediaFormat mediaFormat) {
        final String string = mediaFormat.getString("mime");
        TXCLog.i("TXAudioDecoderWrapper", " mime type = " + string);
        if (string != null && com.tencent.liteav.videoediter.ffmpeg.c.a(string)) {
            TXCLog.i("TXAudioDecoderWrapper", "isUseSw: support mime type! use sw decoder!");
            return true;
        }
        TXCLog.i("TXAudioDecoderWrapper", "isUseSw: use hw decoder!");
        return false;
    }
    
    @Override
    public void a(final MediaFormat mediaFormat) {
        if (mediaFormat == null) {
            this.b.set(false);
            return;
        }
        this.b.set(true);
        TXCLog.i("TXAudioDecoderWrapper", "createDecoderByFormat: " + mediaFormat.toString());
        this.e = this.b(mediaFormat);
        if (this.e) {
            this.c = new c();
        }
        else {
            this.c = new g();
        }
        this.c.a(mediaFormat);
    }
    
    @Override
    public void a(final MediaFormat mediaFormat, final Surface surface) {
        if (mediaFormat == null) {
            this.b.set(false);
            return;
        }
        this.b.set(true);
        this.c.a(mediaFormat, surface);
    }
    
    @Override
    public void a() {
        if (this.b.get()) {
            this.c.a();
        }
    }
    
    @Override
    public void b() {
        if (this.b.get()) {
            this.c.b();
        }
    }
    
    @Override
    public e c() {
        if (this.b.get()) {
            return this.c.c();
        }
        return null;
    }
    
    @Override
    public void a(final e e) {
        if (this.b.get()) {
            this.c.a(e);
        }
    }
    
    @Override
    public e d() {
        if (this.b.get()) {
            return this.c.d();
        }
        return null;
    }
    
    public e a(final e e, final e e2) {
        if (this.b.get()) {
            e2.k(e.n());
            e2.j(e.m());
            e2.f(e.i());
            e2.e(e.h());
            e2.i(e.l());
            e2.h(e.k());
            e2.g(e.j());
            return e2;
        }
        return null;
    }
    
    public e b(final e e) {
        if (this.b.get()) {
            e.c(4);
            TXCLog.i("TXAudioDecoderWrapper", "------appendEndFrame----------");
            return e;
        }
        return null;
    }
    
    static {
        d = new String[] { "Xiaomi - MI 3" };
    }
}
