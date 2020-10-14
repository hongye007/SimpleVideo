package com.tencent.liteav.c;

import java.util.*;
import android.graphics.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;
import com.tencent.liteav.editer.*;
import android.media.*;
import java.io.*;

public class k
{
    private static k b;
    public String a;
    private int c;
    private List<Bitmap> d;
    private int e;
    private e f;
    
    public static k a() {
        if (k.b == null) {
            k.b = new k();
        }
        return k.b;
    }
    
    private k() {
        this.c = 1;
    }
    
    public void a(final List<Bitmap> d, final int e) {
        this.d = d;
        this.e = e;
        this.c = 2;
    }
    
    public List<Bitmap> b() {
        return this.d;
    }
    
    public int c() {
        return this.e;
    }
    
    public int d() {
        return this.c;
    }
    
    public int e() {
        if (TextUtils.isEmpty((CharSequence)this.a)) {
            TXCLog.e("VideoSourceConfig", "checkLegality -> path is null.");
            return -100001;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            try {
                final MediaExtractor a = p.a(this.a);
                if (a == null) {
                    TXCLog.e("VideoSourceConfig", "checkLegality-> source no found!");
                    return -100001;
                }
                final int a2 = this.a(a);
                a.release();
                TXCLog.e("VideoSourceConfig", "checkLegality-> ret = " + a2);
                return a2;
            }
            catch (Exception ex) {
                ex.printStackTrace();
                TXCLog.e("VideoSourceConfig", "checkLegality-> some error happen.");
                return -100002;
            }
        }
        return 0;
    }
    
    public int a(final MediaExtractor mediaExtractor) {
        final int trackCount = mediaExtractor.getTrackCount();
        TXCLog.i("VideoSourceConfig", "checkLegality -> trackCount = " + trackCount);
        if (trackCount < 1) {
            TXCLog.e("VideoSourceConfig", "checkLegality -> trackCount < 1, error!");
            return -100003;
        }
        for (int i = 0; i < trackCount; ++i) {
            final MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
            TXCLog.i("VideoSourceConfig", "checkLegality :" + trackFormat.toString());
            if (trackFormat.getString("mime").startsWith("audio") && trackFormat.containsKey("channel-count")) {
                final int integer = trackFormat.getInteger("channel-count");
                if (integer > 2) {
                    mediaExtractor.release();
                    TXCLog.e("VideoSourceConfig", "checkLegality -> un support audio format. channel = " + integer);
                    return -1004;
                }
            }
        }
        return 0;
    }
    
    public int a(final String s) {
        if (Build.VERSION.SDK_INT < 16) {
            TXCLog.e("VideoSourceConfig", "checkBGMLegality -> un support android version = " + Build.VERSION.SDK_INT);
            return -1001;
        }
        final MediaExtractor a = p.a(s);
        if (a == null) {
            TXCLog.e("VideoSourceConfig", " checkBGMLegality -> setDataSource error. path =  " + s);
            return -100002;
        }
        final int trackCount = a.getTrackCount();
        TXCLog.i("VideoSourceConfig", " checkBGMLegality -> trackCount = " + trackCount);
        for (int i = 0; i < trackCount; ++i) {
            final MediaFormat trackFormat = a.getTrackFormat(i);
            TXCLog.i("VideoSourceConfig", "BGM checkLegality :" + trackFormat.toString());
            if (trackFormat.getString("mime").startsWith("audio") && trackFormat.containsKey("channel-count") && trackFormat.getInteger("channel-count") > 2) {
                TXCLog.i("VideoSourceConfig", " checkBGMLegality -> trackCount > 2 , un support audio format.");
                a.release();
                return -1004;
            }
        }
        a.release();
        return 0;
    }
    
    public boolean f() {
        if (Build.VERSION.SDK_INT < 16) {
            TXCLog.e("VideoSourceConfig", "judgeFullIFrame SDK version is less:16");
            return false;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            final MediaExtractor mediaExtractor = new MediaExtractor();
            try {
                mediaExtractor.setDataSource(this.a);
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            for (int i = 0; i < mediaExtractor.getTrackCount(); ++i) {
                if (mediaExtractor.getTrackFormat(i).getString("mime").startsWith("video/")) {
                    mediaExtractor.selectTrack(i);
                }
            }
            mediaExtractor.seekTo(0L, 0);
            final int sampleFlags = mediaExtractor.getSampleFlags();
            mediaExtractor.advance();
            mediaExtractor.advance();
            mediaExtractor.advance();
            final int sampleFlags2 = mediaExtractor.getSampleFlags();
            mediaExtractor.advance();
            mediaExtractor.advance();
            final int sampleFlags3 = mediaExtractor.getSampleFlags();
            mediaExtractor.release();
            if (sampleFlags == sampleFlags2 && sampleFlags == sampleFlags3 && sampleFlags == 1) {
                return true;
            }
        }
        return false;
    }
    
    public void g() {
        this.c = 1;
        this.a = null;
        this.f = null;
        this.e = 0;
        if (this.d != null) {
            this.d.clear();
        }
    }
    
    public void a(final e f) {
        this.f = f;
    }
    
    public e h() {
        return this.f;
    }
}
