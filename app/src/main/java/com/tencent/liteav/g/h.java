package com.tencent.liteav.g;

import android.media.*;
import com.tencent.liteav.videoediter.ffmpeg.*;
import com.tencent.liteav.editer.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;
import android.graphics.*;

public class h
{
    private MediaMetadataRetriever a;
    private a b;
    private e c;
    
    public void a(final String s) {
        (this.c = new e()).a(s);
        this.a = p.b(s);
        if (!p.c(s)) {
            (this.b = new a()).a(s);
        }
    }
    
    public long a() {
        String metadata = "";
        if (this.a != null) {
            metadata = this.a.extractMetadata(9);
        }
        if (TextUtils.isEmpty((CharSequence)metadata)) {
            if (this.b != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getDuration -> duration is empty,use ff to get!");
                return (this.k() > this.j()) ? this.k() : this.j();
            }
            if (this.c != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getDuration -> duration is empty,use wrap to get!");
                return this.c.a();
            }
        }
        try {
            return Long.parseLong(metadata);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("MediaMetadataRetrieverW", "getDuration -> parse fail. sDuration = " + metadata);
            return 0L;
        }
    }
    
    private long j() {
        if (this.b != null) {
            return this.b.h();
        }
        if (this.c != null) {
            return this.c.k();
        }
        TXCLog.w("MediaMetadataRetrieverW", "getAudioDuration -> mTXFFMediaRetriever is null.");
        return 0L;
    }
    
    private long k() {
        if (this.b != null) {
            return this.b.f();
        }
        if (this.c != null) {
            return this.c.j();
        }
        TXCLog.w("MediaMetadataRetrieverW", "getAudioDuration -> mTXFFMediaRetriever is null.");
        return 0L;
    }
    
    public int b() {
        String metadata = null;
        if (Build.VERSION.SDK_INT >= 17 && this.a != null) {
            metadata = this.a.extractMetadata(24);
        }
        if (TextUtils.isEmpty((CharSequence)metadata)) {
            if (this.b != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getRotation -> rotation is empty,use ff to get!");
                return this.b.a();
            }
            if (this.c != null) {
                return this.c.f();
            }
        }
        try {
            return Integer.parseInt(metadata);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("MediaMetadataRetrieverW", "getRotation -> parse fail. sRotation = " + metadata);
            return 0;
        }
    }
    
    public int c() {
        String metadata = "";
        if (this.a != null) {
            metadata = this.a.extractMetadata(19);
        }
        if (TextUtils.isEmpty((CharSequence)metadata)) {
            if (this.b != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getHeight -> height is empty,use ff to get!");
                return this.b.c();
            }
            if (this.c != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getHeight -> height is empty,use wrap to get!");
                return this.c.c();
            }
        }
        try {
            return Integer.parseInt(metadata);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("MediaMetadataRetrieverW", "getHeight -> parse fail. sHeight = " + metadata);
            return 0;
        }
    }
    
    public int d() {
        String metadata = "";
        if (this.a != null) {
            metadata = this.a.extractMetadata(18);
        }
        if (TextUtils.isEmpty((CharSequence)metadata)) {
            if (this.b != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getWidth -> width is empty,use ff to get!");
                return this.b.b();
            }
            if (this.c != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getWidth -> width is empty,use warp to get!");
                return this.c.b();
            }
        }
        try {
            return Integer.parseInt(metadata);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("MediaMetadataRetrieverW", "getWidth -> parse fail. sWidth = " + metadata);
            return 0;
        }
    }
    
    public float e() {
        String metadata = "";
        if (this.a != null) {
            metadata = this.a.extractMetadata(25);
        }
        if (TextUtils.isEmpty((CharSequence)metadata)) {
            if (this.b != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getFPS -> fps is empty,use ff to get!");
                return this.b.d();
            }
            if (this.c != null) {
                TXCLog.e("MediaMetadataRetrieverW", "getFPS -> fps is empty,use wrap to get!");
                return (float)this.c.e();
            }
        }
        try {
            return Float.parseFloat(metadata);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("MediaMetadataRetrieverW", "getFPS -> parse fail. sFPS = " + metadata);
            return 0.0f;
        }
    }
    
    public long f() {
        String metadata = "";
        if (this.a != null) {
            metadata = this.a.extractMetadata(20);
        }
        if (TextUtils.isEmpty((CharSequence)metadata) && this.b != null) {
            TXCLog.e("MediaMetadataRetrieverW", "getVideoBitRate -> bitrate is empty,use ff to get!");
            return this.b.e();
        }
        try {
            return Integer.parseInt(metadata);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            TXCLog.e("MediaMetadataRetrieverW", "getVideoBitRate -> parse fail. sBitRate = " + metadata);
            return 0L;
        }
    }
    
    public int g() {
        if (this.b != null) {
            return this.b.g();
        }
        if (this.c != null) {
            return this.c.h();
        }
        return 0;
    }
    
    public Bitmap a(final long n) {
        if (this.a != null) {
            return this.a.getFrameAtTime(n, 3);
        }
        return null;
    }
    
    public Bitmap h() {
        if (this.a != null) {
            return this.a.getFrameAtTime();
        }
        return null;
    }
    
    public void i() {
        if (this.a != null) {
            this.a.release();
        }
        if (this.c != null) {
            this.c.o();
        }
    }
}
