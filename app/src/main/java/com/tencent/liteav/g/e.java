package com.tencent.liteav.g;

import android.annotation.*;

import com.tencent.liteav.CameraProxy;
import com.tencent.liteav.CaptureAndEnc;
import com.tencent.liteav.basic.log.*;

import android.text.*;
import android.media.*;

@TargetApi(16)
public class e
{
    private MediaExtractor a;
    private MediaExtractor b;
    private MediaFormat c;
    private MediaFormat d;
    private long e;
    private static int f;
    private static int g;
    private int h;
    private long i;
    private String j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private int q;
    
    public e() {
        this.e = -1L;
        this.k = false;
    }
    
    public e(final boolean k) {
        this.e = -1L;
        this.k = k;
    }
    
    public synchronized int a(final String j) {
        this.j = j;
        TXCLog.i("MediaExtractorWrapper", " setDataSource -> dataSource = " + j + " isOnlyAudio = " + this.k);
        if (this.a != null) {
            this.a.release();
            TXCLog.i("MediaExtractorWrapper", " setDataSource -> VideoExtractor release");
        }
        if (this.b != null) {
            this.b.release();
            TXCLog.i("MediaExtractorWrapper", " setDataSource -> AudioExtractor release");
        }
        if (this.k) {
            this.b = com.tencent.liteav.editer.p.a(j);
            TXCLog.i("MediaExtractorWrapper", " setDataSource -> mAudioExtractor setDataSource success.");
        }
        else {
            this.b = com.tencent.liteav.editer.p.a(j);
            this.a = com.tencent.liteav.editer.p.a(j);
            TXCLog.i("MediaExtractorWrapper", " setDataSource -> mAudioExtractor & mVideoExtractor setDataSource success.");
        }
        return this.s();
    }
    
    private int s() {
        int trackCount = 0;
        if (this.b != null) {
            trackCount = this.b.getTrackCount();
        }
        if (trackCount == 0) {
            TXCLog.i("MediaExtractorWrapper", "prepareMediaFileInfo count == 0");
            return -1001;
        }
        TXCLog.i("MediaExtractorWrapper", " trackCount = " + trackCount);
        for (int i = 0; i < trackCount; ++i) {
            final MediaFormat trackFormat = this.b.getTrackFormat(i);
            TXCLog.i("MediaExtractorWrapper", "prepareMediaFileInfo :" + trackFormat.toString());
            final String string = trackFormat.getString("mime");
            if (string.startsWith("video")) {
                com.tencent.liteav.g.e.f = i;
                this.c = trackFormat;
                if (this.a != null) {
                    this.a.selectTrack(i);
                }
            }
            else if (string.startsWith("audio")) {
                com.tencent.liteav.g.e.g = i;
                this.d = trackFormat;
                this.b.selectTrack(i);
                final int integer = trackFormat.getInteger("channel-count");
                if (integer > 2 || integer < 1) {
                    return -1004;
                }
                if (trackFormat.containsKey("aac-profile")) {
                    final int integer2 = trackFormat.getInteger("aac-profile");
                    TXCLog.i("MediaExtractorWrapper", "prepareMediaFileInfo audio aac profile:" + integer2);
                    if (integer2 == 5 || integer2 == 29) {
                        final int integer3 = trackFormat.getInteger("sample-rate");
                        this.d.setInteger("sample-rate", integer3 * 2);
                        TXCLog.i("MediaExtractorWrapper", "prepareMediaFileInfo HE-AAC samplerate special double:" + integer3 * 2);
                    }
                }
            }
        }
        this.h = this.g();
        if (this.c != null) {
            final int b = this.b();
            final int c = this.c();
            if (((b > c) ? c : b) > 1080) {
                TXCLog.i("MediaExtractorWrapper", "prepareMediaFileInfo W:" + b + ",H:" + c);
            }
        }
        return 0;
    }
    
    public long a() {
        if (this.c == null) {
            return 0L;
        }
        if (this.d == null) {
            try {
                if (this.i == 0L) {
                    this.i = this.c.getLong("durationUs");
                    TXCLog.i("MediaExtractorWrapper", "mDuration = " + this.i);
                }
                return this.i;
            }
            catch (NullPointerException ex) {
                TXCLog.e("MediaExtractorWrapper", "Null pointer exception");
                return 0L;
            }
        }
        try {
            if (this.i == 0L) {
                final long long1 = this.c.getLong("durationUs");
                final long long2 = this.d.getLong("durationUs");
                this.i = ((long1 > long2) ? long1 : long2);
                TXCLog.i("MediaExtractorWrapper", "mDuration = " + this.i);
            }
            return this.i;
        }
        catch (NullPointerException ex2) {
            TXCLog.e("MediaExtractorWrapper", "Null pointer exception");
            return 0L;
        }
    }
    
    public int b() {
        if (this.q != 0) {
            return this.q;
        }
        try {
            if (this.c != null) {
                return this.q = this.c.getInteger("width");
            }
        }
        catch (NullPointerException ex) {
            return 0;
        }
        return 0;
    }
    
    public int c() {
        if (this.p != 0) {
            return this.p;
        }
        try {
            if (this.c != null) {
                return this.p = this.c.getInteger("height");
            }
        }
        catch (NullPointerException ex) {
            return 0;
        }
        return 0;
    }
    
    public int d() {
        if (this.o != 0) {
            return this.o;
        }
        try {
            if (this.c != null) {
                return this.o = this.c.getInteger("i-frame-interval");
            }
        }
        catch (NullPointerException ex) {
            return 0;
        }
        return 0;
    }
    
    public int e() {
        if (this.n != 0) {
            return this.n;
        }
        int n = 0;
        try {
            if (this.c != null) {
                n = this.c.getInteger("frame-rate");
            }
        }
        catch (NullPointerException ex) {
            try {
                n = this.c.getInteger("video-framerate");
            }
            catch (NullPointerException ex2) {
                n = 20;
            }
        }
        return this.n = n;
    }
    
    public int f() {
        return this.h;
    }
    
    public int g() {
        final MediaMetadataRetriever b = com.tencent.liteav.editer.p.b(this.j);
        final String metadata = b.extractMetadata(24);
        int int1;
        if (TextUtils.isEmpty((CharSequence)metadata)) {
            TXCLog.e("MediaExtractorWrapper", "getRotation error: rotation is empty,rotation have been reset to zero");
            int1 = 0;
        }
        else {
            int1 = Integer.parseInt(metadata);
        }
        b.release();
        this.h = int1;
        TXCLog.i("MediaExtractorWrapper", "mRotation=" + this.h + ",rotation=" + int1);
        return int1;
    }
    
    public int h() {
        if (this.m != 0) {
            return this.m;
        }
        try {
            if (this.d != null) {
                return this.m = this.d.getInteger("sample-rate");
            }
        }
        catch (NullPointerException ex) {
            return 0;
        }
        return 0;
    }
    
    public int i() {
        if (this.l != 0) {
            return this.l;
        }
        try {
            if (this.d != null) {
                return this.l = this.d.getInteger("channel-count");
            }
        }
        catch (NullPointerException ex) {
            return 0;
        }
        return 0;
    }
    
    public long j() {
        if (this.c != null) {
            try {
                return this.c.getLong("durationUs");
            }
            catch (Exception ex) {
                return 0L;
            }
        }
        return 0L;
    }
    
    public long k() {
        if (this.d != null) {
            try {
                return this.d.getLong("durationUs");
            }
            catch (Exception ex) {
                return 0L;
            }
        }
        return 0L;
    }
    
    public CaptureAndEnc.e a(final CaptureAndEnc.e e) {
        if (this.a == null) {
            TXCLog.d("MediaExtractorWrapper", "readVideoSampleData mVideoExtractor is null");
            return null;
        }
        final long sampleTime = this.a.getSampleTime();
        if (sampleTime == -1L) {
            e.d(0);
            e.a(0L);
            e.c(4);
            return e;
        }
        e.a(sampleTime);
        final int sampleTrackIndex = this.a.getSampleTrackIndex();
        e.a(sampleTrackIndex);
        e.c(this.a.getSampleFlags());
        e.d(this.a.readSampleData(e.b(), 0));
        e.b().position(0);
        e.f(this.e());
        e.e(this.f());
        e.g(this.h());
        e.h(this.i());
        e.j(this.b());
        e.k(this.c());
        e.a(false);
        CameraProxy.k.a().a(e);
        if (this.e == -1L && sampleTrackIndex == this.n()) {
            this.e = e.e();
        }
        if (e.g() <= 0) {
            e.d(0);
            e.a(0L);
            e.c(4);
        }
        return e;
    }
    
    public CaptureAndEnc.e b(final CaptureAndEnc.e e) {
        if (this.b == null) {
            TXCLog.d("MediaExtractorWrapper", "readAudioSampleData mAudioExtractor is null");
            return null;
        }
        final long sampleTime = this.b.getSampleTime();
        if (sampleTime == -1L) {
            e.d(0);
            e.a(0L);
            e.c(4);
            return e;
        }
        e.a(sampleTime);
        final int sampleTrackIndex = this.b.getSampleTrackIndex();
        e.a(sampleTrackIndex);
        e.c(this.b.getSampleFlags());
        e.d(this.b.readSampleData(e.b(), 0));
        e.b().position(0);
        e.e(this.f());
        e.g(this.h());
        e.h(this.i());
        e.j(this.b());
        e.k(this.c());
        e.a(false);
        if (this.e == -1L && sampleTrackIndex == this.n()) {
            this.e = e.e();
        }
        if (e.g() <= 0) {
            e.d(0);
            e.a(0L);
            e.c(4);
        }
        return e;
    }
    
    public MediaFormat l() {
        return this.c;
    }
    
    public MediaFormat m() {
        return this.d;
    }
    
    public int n() {
        return com.tencent.liteav.g.e.f;
    }
    
    public boolean c(final CaptureAndEnc.e e) {
        if (e.f() == 4) {
            return true;
        }
        if (this.a != null) {
            final boolean advance = this.a.advance();
            if (this.a.getSampleTime() == -1L || !advance) {
                TXCLog.i("MediaExtractorWrapper", "advanceVideo reach end of file");
                e.c(4);
                return true;
            }
        }
        return false;
    }
    
    public boolean d(final CaptureAndEnc.e e) {
        if (e.f() == 4) {
            return true;
        }
        if (this.b != null) {
            final boolean advance = this.b.advance();
            if (this.b.getSampleTime() == -1L || !advance) {
                TXCLog.i("MediaExtractorWrapper", "advanceAudio reach end of file");
                e.c(4);
                return true;
            }
        }
        return false;
    }
    
    public void a(final long n) {
        if (this.a != null) {
            this.a.seekTo(n, 0);
        }
    }
    
    public void b(final long n) {
        if (this.a != null) {
            this.a.seekTo(n, 1);
        }
    }
    
    public void c(final long n) {
        if (this.b != null) {
            this.b.seekTo(n, 0);
        }
    }
    
    public synchronized void o() {
        TXCLog.i("MediaExtractorWrapper", "release start");
        if (this.a != null) {
            this.a.release();
        }
        if (this.b != null) {
            this.b.release();
        }
        TXCLog.i("MediaExtractorWrapper", "release end");
    }
    
    public long p() {
        if (this.a != null) {
            return this.a.getSampleTime();
        }
        return 0L;
    }
    
    public long q() {
        if (this.b != null) {
            return this.b.getSampleTime();
        }
        return 0L;
    }
    
    public long r() {
        if (this.a != null) {
            return this.a.getSampleTime();
        }
        return 0L;
    }
}
