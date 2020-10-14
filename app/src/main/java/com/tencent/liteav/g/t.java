package com.tencent.liteav.g;

import java.util.*;
import android.text.*;
import com.tencent.liteav.basic.log.*;
import android.os.*;
import android.annotation.*;
import android.media.*;

public class t
{
    private static t a;
    private final ArrayList<i> b;
    private ArrayList<String> c;
    private int d;
    private int e;
    
    public static t a() {
        if (t.a == null) {
            t.a = new t();
        }
        return t.a;
    }
    
    private t() {
        this.d = 0;
        this.e = 0;
        this.c = new ArrayList<String>();
        this.b = new ArrayList<i>();
    }
    
    public void a(final List<String> list) {
        this.b.clear();
        this.d = 0;
        this.c.clear();
        this.c.addAll(list);
    }
    
    public List<String> b() {
        return this.c;
    }
    
    public int c() {
        int b = 0;
        for (int i = 0; i < this.c.size(); ++i) {
            final String s = this.c.get(i);
            if (TextUtils.isEmpty((CharSequence)s)) {
                return -100001;
            }
            final i j = new i();
            j.a(s);
            b = j.b();
            this.b.add(j);
            if (b != 0) {
                TXCLog.e("VideoSourceListConfig", "checkLegality source:" + s + " is illegal");
                break;
            }
        }
        if (b != 0) {
            for (int size = this.b.size(), k = 0; k < size; ++k) {
                this.b.get(k).a();
            }
        }
        return b;
    }
    
    public List<i> d() {
        return this.b;
    }
    
    public i e() {
        TXCLog.i("VideoSourceListConfig", "getCurrentVideoExtractConfig mCurrentVideoIndex:" + this.d);
        return this.b.get(this.d);
    }
    
    public i f() {
        TXCLog.i("VideoSourceListConfig", "getCurrentAudioExtractConfig mCurrentAudioIndex:" + this.e);
        return this.b.get(this.e);
    }
    
    public boolean g() {
        ++this.d;
        TXCLog.i("VideoSourceListConfig", "nextVideo mCurrentVideoIndex:" + this.d);
        if (this.d >= this.b.size()) {
            TXCLog.i("VideoSourceListConfig", "nextVideo get fail");
            return false;
        }
        TXCLog.i("VideoSourceListConfig", "nextVideo get succ");
        return true;
    }
    
    @TargetApi(16)
    public boolean h() {
        if (Build.VERSION.SDK_INT < 16) {
            return false;
        }
        for (int i = 0; i < this.b.size(); ++i) {
            if (this.b.get(i).f() == null) {
                return false;
            }
        }
        return true;
    }
    
    public MediaFormat i() {
        int n = 0;
        int n2 = 0;
        if (Build.VERSION.SDK_INT >= 16) {
            for (int i = 0; i < this.b.size(); ++i) {
                final MediaFormat f = this.b.get(i).f();
                if (f != null) {
                    final int integer = f.getInteger("sample-rate");
                    final int integer2 = f.getInteger("channel-count");
                    if (integer > n2) {
                        n2 = integer;
                    }
                    if (integer2 > n) {
                        n = integer2;
                    }
                }
            }
        }
        if (n == 0) {
            n = 2;
        }
        if (n2 == 0) {
            n2 = 48000;
        }
        MediaFormat audioFormat = null;
        if (Build.VERSION.SDK_INT >= 16) {
            audioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", n2, n);
        }
        return audioFormat;
    }
    
    public boolean j() {
        boolean b = true;
        if (Build.VERSION.SDK_INT >= 16) {
            for (int i = 0; i < this.b.size(); ++i) {
                final i j = this.b.get(i);
                final MediaFormat e = j.e();
                final int integer = e.getInteger("width");
                final int integer2 = e.getInteger("height");
                final int g = j.g();
                if (g == 0 || g == 180) {
                    if (integer2 > integer) {
                        b = false;
                    }
                }
                else if (integer > integer2) {
                    b = false;
                }
            }
        }
        return b;
    }
    
    public boolean k() {
        ++this.e;
        TXCLog.i("VideoSourceListConfig", "nextAudio mCurrentAudioIndex:" + this.e);
        if (this.e >= this.b.size()) {
            TXCLog.i("VideoSourceListConfig", "nextAudio get fail");
            return false;
        }
        TXCLog.i("VideoSourceListConfig", "nextAudio get succ");
        return true;
    }
    
    public boolean l() {
        return this.d == this.b.size() - 1;
    }
    
    public boolean m() {
        return this.e == this.b.size() - 1;
    }
    
    public void n() {
        this.d = 0;
        this.e = 0;
    }
    
    public long o() {
        long n = 0L;
        if (Build.VERSION.SDK_INT >= 16) {
            for (int i = 0; i < this.b.size(); ++i) {
                n += this.b.get(i).e().getLong("durationUs");
            }
        }
        return n;
    }
    
    public long p() {
        long n = 0L;
        if (Build.VERSION.SDK_INT >= 16) {
            for (int i = 0; i < this.b.size(); ++i) {
                final long long1 = this.b.get(i).e().getLong("durationUs");
                if (n == 0L) {
                    n = long1;
                }
                if (n > long1) {
                    n = long1;
                }
            }
        }
        return n;
    }
}
