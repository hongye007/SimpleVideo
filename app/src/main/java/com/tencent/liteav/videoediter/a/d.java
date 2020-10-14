package com.tencent.liteav.videoediter.a;

import com.tencent.liteav.basic.log.*;
import java.io.*;
import android.media.*;
import java.util.*;
import android.annotation.*;

public class d extends b
{
    private ArrayList<String> a;
    private int b;
    private long c;
    private long d;
    
    public d() {
        this.c = 0L;
        this.d = 0L;
        this.a = new ArrayList<String>();
        this.b = -1;
    }
    
    public synchronized void a(final List<String> list) {
        if (list != null && list.size() > 0) {
            this.a.addAll(list);
        }
    }
    
    @Override
    public synchronized void a(final long n) {
        if (n <= 0L) {
            this.g();
            return;
        }
        this.g();
        final long c = 0L;
        if (this.a.size() > 0) {
            final b b = new b();
            int i;
            for (i = 0; i < this.a.size(); ++i) {
                final String s = this.a.get(i);
                try {
                    b.a(s);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    TXCLog.e("TXMultiMediaExtractor", "setDataSource IOException: " + ex);
                    continue;
                }
                if (c + b.c() > n) {
                    break;
                }
            }
            b.e();
            if (i < this.a.size()) {
                this.b = i;
                this.c = c;
                try {
                    super.a(this.a.get(this.b));
                }
                catch (IOException ex2) {
                    ex2.printStackTrace();
                    TXCLog.e("TXMultiMediaExtractor", "setDataSource IOException: " + ex2);
                }
                super.a(n - this.c);
                this.d = super.d();
            }
        }
    }
    
    @TargetApi(16)
    public int f() {
        MediaFormat mediaFormat = null;
        MediaFormat mediaFormat2 = null;
        if (this.a.size() > 0) {
            final b b = new b();
            for (final String s : this.a) {
                try {
                    b.a(s);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    TXCLog.e("TXMultiMediaExtractor", "setDataSource IOException: " + ex);
                    continue;
                }
                final MediaFormat a = b.a();
                final MediaFormat b2 = b.b();
                if (mediaFormat == null && mediaFormat2 == null) {
                    mediaFormat = a;
                    mediaFormat2 = b2;
                }
                else {
                    if ((mediaFormat != null && a == null) || (mediaFormat == null && a != null) || (mediaFormat2 != null && b2 == null) || (mediaFormat2 == null && b2 != null)) {
                        return -2;
                    }
                    try {
                        if (mediaFormat != null && a != null) {
                            if (Math.abs(mediaFormat.getInteger("frame-rate") - a.getInteger("frame-rate")) > 3) {
                                return -4;
                            }
                            if (mediaFormat.getInteger("width") != a.getInteger("width")) {
                                return -5;
                            }
                            if (mediaFormat.getInteger("height") != a.getInteger("height")) {
                                return -6;
                            }
                            continue;
                        }
                        else {
                            if (mediaFormat2 == null || b2 == null) {
                                continue;
                            }
                            if (mediaFormat2.getInteger("sample-rate") != b2.getInteger("sample-rate")) {
                                return -7;
                            }
                            if (mediaFormat2.getInteger("channel-count") != b2.getInteger("channel-count")) {
                                return -8;
                            }
                            continue;
                        }
                    }
                    catch (NullPointerException ex2) {
                        return -3;
                    }
                }
            }
            b.e();
            return 0;
        }
        return -1;
    }
    
    @Override
    public synchronized long c() {
        long n = 0L;
        if (this.a.size() > 0) {
            final b b = new b();
            for (int i = 0; i < this.a.size(); ++i) {
                final String s = this.a.get(i);
                try {
                    b.a(s);
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                    TXCLog.e("TXMultiMediaExtractor", "setDataSource IOException: " + ex);
                    continue;
                }
                n += b.c();
            }
            b.e();
        }
        return n;
    }
    
    @Override
    public synchronized int a(final e e) {
        int n = super.a(e);
        while (n < 0 && this.b < this.a.size() - 1) {
            this.c = this.d + 1000L;
            ++this.b;
            try {
                this.a(this.a.get(this.b));
            }
            catch (IOException ex) {
                TXCLog.e("TXMultiMediaExtractor", "setDataSource IOException: " + ex);
                ex.printStackTrace();
                continue;
            }
            n = super.a(e);
        }
        if (n >= 0) {
            final long d = e.e() + this.c;
            e.a(d);
            if (this.d < d) {
                this.d = d;
            }
        }
        else {
            TXCLog.d("TXMultiMediaExtractor", "readSampleData length = " + n);
        }
        return n;
    }
    
    private synchronized void g() {
        super.e();
        this.b = -1;
        this.c = 0L;
        this.d = 0L;
    }
    
    @Override
    public synchronized void e() {
        super.e();
        this.a.clear();
        this.b = -1;
        this.c = 0L;
        this.d = 0L;
    }
}
